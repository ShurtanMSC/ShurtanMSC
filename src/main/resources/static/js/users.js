let usersList = []

function getAllUsers() {
    axios.get("/api/admin/user/all")
        .then(function (response) {
            if (response.data.message === "OK") {
                usersList = response.data.object
            }
            document.getElementById("userTable").innerHTML = createViewTable(response.data.object)
        })
        .catch(function (error) {
            console.log(error)
        })
}

function getAllRoles() {
    axios.get("/api/admin/role/all")
        .then(function (response) {
            console.log(response.data)
            document.getElementById("inputGroupSelect03").innerHTML = addOptionRoles(response.data)

        })
        .catch(function (error) {
            console.log(error)
        })
}

document.getElementById('addUserBtn').addEventListener('click',addUserBtn)

function addUserBtn() {
    document.getElementById('addOrEditUserH3').innerText = 'Добавить пользователя'
    document.getElementById('addOrEditUserBtn').innerText = 'Добавить'
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

function editUser(id) {
    document.getElementById('addOrEditUserH3').innerText = 'Редактировать пользователя'
    document.getElementById('addOrEditUserBtn').innerText = 'Редактировать'
    let editUser = usersList.find(user => user.id == id)
    let formField = document.getElementById('addOrEditUserForm')

    formField['id'].value = editUser.id;
    formField['username'].value = editUser.username;
    formField['email'].value = editUser.email;
    formField['password'].value = editUser.password;
    formField['phone'].value = editUser.phone;
    formField['fio'].value = editUser.fio;
    formField['roleId'].value = editUser.roleId;

}

function deleteUser(id) {
    axios.delete("/api/admin/user/delete/" + id)
        .then(function (response) {
            // console.log(response.data)
            getAllUsers()
        })
        .catch(function (error) {
            console.log(error.response.data)
            alert(error.response.data.message)
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
            "                                    <td><button data-target=\"#exampleModalCenter\" data-toggle=\"modal\" class='btn btn-success mt-1' id='btn-edit-user' value='" + user.id + "' onclick='editUser(this.value)'>Редактировать</button>\n" +
            "                                    <button class='btn btn-danger ml-2 mt-1' id='btn-edit-user' value='" + user.id + "' onclick='deleteUser(this.value)'>Удалить</button></td>\n" +
            "                                </tr>"
    })
    return out;
}

function addOptionRoles(roles) {
    let out = "<option value=''>Месторождений</option>";
    roles.map(role => {
        out += "<option value='"+role.id+"'>"+role.roleName+"</option>"
    })
    return out;
}