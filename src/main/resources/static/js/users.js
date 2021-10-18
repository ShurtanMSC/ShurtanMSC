function getAllUsers() {
    axios.get("/api/admin/user/all")
        .then(function (response) {
            console.log(response.data)
            document.getElementById("userTable").innerHTML = createViewTable(response.data.object)
        })
        .catch(function (error) {
            console.log(error)
        })
}

function getAllRoles() {
    axios.get("/api/admin/role/all")
        .then(function (response) {
            // console.log(response.data)
        })
        .catch(function (error) {
            console.log(error)
        })
}

function resetAndCloseForm() {
    document.getElementById('closeFormBtn').click();
    document.getElementById('addOrEditUserForm').reset();
}

function addOrEditUser(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    const data = {}
    formData.forEach((value, key) => (data[key] = value));
    console.log(data);

    let config = {
        method: '',
        url: '',
        data
    };

    if (data.id === "" || data.id == null) {
        config.method = 'post';
        config.url = '/api/admin/user/add'
    } else {
        config.method = 'put';
        config.url = '/api/admin/user/edit'
    }

    axios(config)
        .then(function () {
            getAllUsers();
            resetAndCloseForm();
        })
        .catch(function (error) {
            console.log(error);
        });
}

function deleteUser(id) {
    axios.delete("/api/admin/user/delete/"+id)
        .then(function (response) {
            console.log(response.data)
            getAllUsers()
        })
        .catch(function (error) {
            console.log(error.response.data)
        })
}

function createViewTable(users) {
    let out = "";
    users.map(user => {
        out += "<tr class=\"user_table_row\">\n" +
            "                                    <td class=\"sorting_1\">" + user.id + "</td>\n" +
            "                                    <td>" + user.fio + "</td>\n" +
            "                                    <td>" + user.username + "</td>\n" +
            "                                    <td>" + user.roleName + "</td>\n" +
            "                                    <td>" + user.phone + "</td>\n" +
            "                                    <td>" + user.email + "</td>\n" +
            "                                    <td><button class='btn btn-success' id='btn-edit-user'>Редактировать</button></td>\n" +
            "                                    <td><button class='btn btn-danger' id='btn-edit-user' value='"+user.id+"' onclick='deleteUser(this.value)'>Удалить</button></td>\n" +
            "                                </tr>"
    })
    return out;
}