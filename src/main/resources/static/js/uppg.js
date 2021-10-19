let uppgsList = []

function getAllUppgs() {
    axios.get("/api/uppg/all/mining_system/" + 1)
        .then(function (response) {
            if (response.data.message === "OK") {
                uppgsList = response.data.object
            }
            document.getElementById("uppgTable").innerHTML = createViewTable(response.data.object)
        })
        .catch(function (error) {
            console.log(error)
        })
}

document.getElementById('addUppgBtn').addEventListener('click', addUppgBtn)

function addUppgBtn() {
    document.getElementById('addOrEditUppgH3').innerText = 'Добавить пользователя'
    document.getElementById('addOrEditUppgBtn').innerText = 'Добавить'
}

function resetAndCloseForm() {
    document.getElementById('closeFormBtn').click();
    document.getElementById('addOrEditUppgForm').reset();
}

function addOrEditUppg(event) {
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
        config.url = '/api/uppg/add'
    } else {

        config.method = 'put';
        config.url = '/api/uppg/edit'
    }

    axios(config)
        .then(function () {
            getAllUppgs();
            resetAndCloseForm();
        })
        .catch(function (error) {
            console.log(error);
        });
}

function editUppg(id) {
    document.getElementById('addOrEditUppgH3').innerText = 'Редактировать'
    document.getElementById('addOrEditUppgBtn').innerText = 'Редактировать'
    let editUppg = uppgsList.find(uppg => uppg.id == id)
    let formField = document.getElementById('addOrEditUppgForm')

    formField['id'].value = editUppg.id;
    formField['name'].value = editUppg.name;
    // formField['email'].value = editUppg.email;
}

function deleteUppg(id) {
    axios.delete("/api/uppg/delete/" + id)
        .then(function (response) {
            // console.log(response.data)
            getAllUppgs()
        })
        .catch(function (error) {
            console.log(error.response.data)
        })
}

function createViewTable(uppgs) {
    let out = "";
    uppgs.map(uppg => {
        out += "<tr class=\"uppg_table_row\">\n" +
            "   <td class=\"sorting_1\">" + uppg.id + "</td>\n" +
            "    <td>" + uppg.name + "</td>\n" +
            // "     <td>" + uppg.miningsiystemId + "</td>\n" +
            "     <td><button data-target=\"#exampleModalCenter\" data-toggle=\"modal\" class='btn btn-success' id='btn-edit-uppg' value='" + uppg.id + "' onclick='editUppg(this.value)'>Редактировать</button></td>\n" +
            "      <td><button class='btn btn-danger' id='btn-edit-uppg' value='" + uppg.id + "' onclick='deleteUppg(this.value)'>Удалить</button></td>\n" +
            "   </tr>"
    })
    return out;
}
