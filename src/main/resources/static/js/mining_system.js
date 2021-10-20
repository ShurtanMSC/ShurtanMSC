let mining_systemsList = []

function getAllMiningSystems() {
    axios.get("/api/mining_system/all/actions")
        .then(function (response) {
            console.log(response.data.object)
            if (response.data.message === "OK") {
                mining_systemsList = response.data.object
            }
            document.getElementById("miningTable").innerHTML = createViewTable(response.data.object)
        })
        .catch(function (error) {
            console.log(error)
        })
}

document.getElementById('addMiningSystemBtn').addEventListener('click', addMiningSystemBtn);

function addMiningSystemBtn() {
    document.getElementById('addOrEditMiningSystemH3').innerText = 'Добавить месторождения'
    document.getElementById('addOrEditMiningSystemBtn').innerText = 'Добавить'
}

function resetAndCloseForm() {
    document.getElementById('closeFormBtn').click();
    document.getElementById('addOrEditMiningSystemForm').reset();
}

function addOrEditMiningSystem(event) {
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
        config.url = '/api/mining_system/add'
    } else {

        config.method = 'put';
        config.url = '/api/mining_system/edit'
    }

    axios(config)
        .then(function () {
            getAllMiningSystems();
            resetAndCloseForm();
        })
        .catch(function (error) {
            console.log(error);
        });
}

function editMiningSystem(id) {
    document.getElementById('addOrEditMiningSystemH3').innerText = 'Редактировать месторождения'
    document.getElementById('addOrEditMiningSystemBtn').innerText = 'Редактировать'
    let editMiningSystem = mining_systemsList.find(mining_system => mining_system.objectDto.id == id)
    let formField = document.getElementById('addOrEditMiningSystemForm')

    formField['id'].value = editMiningSystem.objectDto.id;
    formField['name'].value = editMiningSystem.objectDto.name;
}

function deleteMiningSystem(id) {
    axios.delete("/api/mining_system/delete/" + id)
        .then(function (response) {
            // console.log(response.data)
            getAllMiningSystems()
        })
        .catch(function (error) {
            console.log(error.response.data)
                alert(error.response.data.message)
        })
}

function createViewTable(miningSystems) {
    let out = "";
    miningSystems.map(mining => {
        // console.log(mining)
        out += "<tr class=\"user_table_row\">\n" +
            "                                    <td class=\"sorting_1\">" + mining.objectDto.id + "</td>\n" +
            "                                    <td>" + mining.objectDto.name + "</td>\n" +
            "                                    <td>" + (mining.objectActionDto ? mining.objectActionDto.expend : "") + "</td>\n" +
            "                                    <td><button data-target=\"#exampleModalCenter\" data-toggle=\"modal\" class='btn btn-success' id='btn-edit-user' value='" + mining.objectDto.id + "' onclick='editMiningSystem(this.value)'>Редактировать</button>" +
            "<a class='btn btn-info ml-2' id='btn-edit-user'>Действие</a>" +
            "<button class='btn btn-danger ml-2' id='btn-edit-user' value='" + mining.objectDto.id + "' onclick='deleteMiningSystem(this.value)'>Удалить</button>" +
            "</td>\n" +
            "                                </tr>"
    })
    return out;
}