let opcServersList = []

function getAllOpcServers() {
    axios.get("/api/admin/opc_server/all")
        .then(function (response) {
            if (response.data.message === "OK") {
                opcServersList = response.data.object
            }
            document.getElementById("opcServerTable").innerHTML = createViewTable(response.data.object)
        })
        .catch(function (error) {
            console.log(error)
        })
}

document.getElementById('addOpcServerBtn').addEventListener('click',addOpcServerBtn)

function addOpcServerBtn() {
    document.getElementById('addOrEditOpcServerH3').innerText = 'Добавить ОПС сервер'
    document.getElementById('addOrEditOpcServerBtn').innerText = 'Добавить'
}
function resetAndCloseForm() {
    document.getElementById('closeFormBtn').click();
    document.getElementById('addOrEditOpcServerForm').reset();
}

function addOrEditOpcServer(event) {
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
        config.url = '/api/admin/opc_server/add'
    } else {

        config.method = 'put';
        config.url = '/api/admin/opc_server/edit'
    }

    axios(config)
        .then(function () {
            getAllOpcServers();
            resetAndCloseForm();
        })
        .catch(function (error) {
            console.log(error);
        });
}

function editOpcServer(id) {
    document.getElementById('addOrEditOpcServerH3').innerText = 'Редактировать ОПС сервер'
    document.getElementById('addOrEditOpcServerBtn').innerText = 'Редактировать'
    let editOpcServer = opcServersList.find(opcServer => opcServer.id == id)
    let formField = document.getElementById('addOrEditOpcServerForm')

    formField['id'].value = editOpcServer.id;
    formField['name'].value = editOpcServer.name;
    formField['description'].value = editOpcServer.description;
    formField['address'].value = editOpcServer.address;
    formField['url'].value = editOpcServer.url;

}

function deleteOpcServer(id) {
    axios.delete("/api/admin/opc_server/delete/" + id)
        .then(function (response) {
            // console.log(response.data)
            getAllOpcServers()
        })
        .catch(function (error) {
            console.log(error.response.data)
            alert(error.response.data.message)
        })
}

function createViewTable(opcServers) {
    let out = "";
    opcServers.map(opcServer => {
        out += "<tr class=\"opcServer_table_row\">\n" +
            "                                    <td class=\"sorting_1\">" + opcServer.id + "</td>\n" +
            "                                    <td>" + opcServer.name + "</td>\n" +
            "                                    <td>" + opcServer.description + "</td>\n" +
            "                                    <td>" + opcServer.address + "</td>\n" +
            "                                    <td>" + opcServer.url + "</td>\n" +
            "                                    <td><button data-target=\"#exampleModalCenter\" data-toggle=\"modal\" class='btn btn-success mt-1' id='btn-edit-opcServer' value='" + opcServer.id + "' onclick='editOpcServer(this.value)'>Редактировать</button>\n" +
            "                                    <button class='btn btn-danger ml-2 mt-1' id='btn-edit-opcServer' value='" + opcServer.id + "' onclick='deleteOpcServer(this.value)'>Удалить</button></td>\n" +
            "                                </tr>"
    })
    return out;
}

