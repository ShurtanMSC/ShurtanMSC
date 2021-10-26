let collectionPointsList = []
let miningSystemId = 1;
let uppgId = 1

let opcId;

function handleOpcId() {
    opcId = document.getElementById('inputGroupSelect03').value
}

function getAllMiningSystems() {
    axios.get("/api/mining_system/all")
        .then(function (response) {
            if (response.data.message === "OK") {
                document.getElementById("miningSelect").innerHTML = createViewMiningOrUppgSelect(response.data.object)
            }
        })
        .catch(function (error) {
            console.log(error)
        })
}

function getAllUppgs() {
    axios.get("/api/uppg/all/mining_system/" + miningSystemId)
        .then(function (response) {
            if (response.data.message === "OK") {
                document.getElementById("uppgSelect").innerHTML = createViewMiningOrUppgSelect(response.data.object)
                uppgId = document.getElementById('uppgSelect').value;
                getAllCollectionPoints()
            }
        })
        .catch(function (error) {
            console.log(error)
        })
}

function selectHandleMining() {
    miningSystemId = document.getElementById('miningSelect').value;
    getAllUppgs()
}

function selectHandleUppg() {
    uppgId = document.getElementById('uppgSelect').value;
    getAllCollectionPoints()
}

function getAllCollectionPoints() {
    if (uppgId == "" || uppgId == null) {
        document.getElementById("uppgSelect").innerHTML = "<option value=''>Нет УППГ</option>"
        document.getElementById("collectionPointTable").innerHTML = "<tr class=\"odd\">\n" +
            "                                                <td class=\"sorting_1\">-</td>\n" +
            "                                                <td>-</td>\n" +
            "                                                <td>-</td>\n" +
            "\n" +
            "                                            </tr>"

        alert("В этом месторождении нет УППГ и СП ")
    } else {
        axios.get("/api/admin/collection_point/all/" + uppgId)
            .then(function (response) {
                // console.log(response.data)
                if (response.data.message === "OK") {
                    collectionPointsList = response.data.object
                }
                document.getElementById("collectionPointTable").innerHTML = createViewTable(response.data.object)
            })
            .catch(function (error) {
                console.log(error)
            })
    }
}

function getAllOpcServers() {
    axios.get("/api/admin/opc_server/all")
        .then(function (response) {
            // console.log("response.data")
            // console.log(response.data)
            document.getElementById("inputGroupSelect03").innerHTML = addOptionOpcServers(response.data.object)
        })
        .catch(function (error) {
            console.log(error)
        })
}

document.getElementById('addCollectionPointBtn').addEventListener('click', addCollectionPointBtn);

function addCollectionPointBtn() {
    document.getElementById('addOrEditCollectionPointH3').innerText = 'Добавить Сборный пункт'
    document.getElementById('addOrEditCollectionPointBtn').innerText = 'Добавить'
    let formField = document.getElementById('addOrEditCollectionPointForm')
    formField['uppgId'].value = uppgId;
}

function resetAndCloseForm() {
    document.getElementById('closeFormBtn').click();
    document.getElementById('addOrEditCollectionPointForm').reset();
}

function addOrEditCollectionPoint(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    const data = {}
    formData.forEach((value, key) => (data[key] = value));
    // console.log(data)
    let config = {
        method: '',
        url: '',
        data
    };

    if (data.id === "" || data.id == null) {

        config.method = 'post';
        config.url = '/api/admin/collection_point/add/' + uppgId + '/' + opcId;
    } else {

        config.method = 'put';
        config.url = '/api/admin/collection_point/edit'
    }

    axios(config)
        .then(function (response) {
            getAllCollectionPoints();
            resetAndCloseForm();
            // console.log(response.data);
        })
        .catch(function (error) {
            console.log(error.response.data);
        });
}

function editCollectionPoint(id) {
    document.getElementById('addOrEditCollectionPointH3').innerText = 'Редактировать Сборный пункт'
    document.getElementById('addOrEditCollectionPointBtn').innerText = 'Редактировать'
    let editCollectionPoint = collectionPointsList.find(collectionPoint => collectionPoint.id == id)
    let formField = document.getElementById('addOrEditCollectionPointForm')

    formField['id'].value = editCollectionPoint.id;
    formField['name'].value = editCollectionPoint.name;
    formField['temperatureUnit'].value = editCollectionPoint.temperatureUnit;
    formField['pressureUnit'].value = editCollectionPoint.pressureUnit;
    formField['opcServerId'].value = editCollectionPoint.opcServer.id;
}

function deleteCollectionPoint(id) {
    axios.delete("/api/collection_point/delete/" + id)
        .then(function (response) {
            // console.log(response.data)
            getAllCollectionPoints()
        })
        .catch(function (error) {
            console.log(error.response.data)
        })
}

function createViewTable(collectionPoints) {
    let out = "";
    collectionPoints.map(collectionPoint => {
        out += "<tr class=\"collectionPoint_table_row\">\n" +
            "<td class=\"sorting_1\">" + collectionPoint.id + "</td>\n" +
            "<td>" + collectionPoint.name + "</td>\n" +
            "<td>" + collectionPoint.temperatureUnit + "</td>\n" +
            "<td>" + collectionPoint.pressureUnit + "</td>\n" +
            // "<td hidden>" + collectionPoint.uppg.id + "</td>\n" +
            "<td>" + collectionPoint.opcServer.name + "</td>\n" +
            "<td hidden value='" + collectionPoint.uppgId + "'>" + collectionPoint.uppgId + "</td>\n" +
            "<td><button data-target=\"#exampleModalCenter\" data-toggle=\"modal\" class='btn btn-success mt-1' id='btn-edit-collectionPoint' value='" + collectionPoint.id + "' onclick='editCollectionPoint(this.value)'>Редактировать</button>\n" +
            "<button  class='btn btn-info ml-2 mt-1' id='btn-action-mining' value='" + collectionPoint.id + "' onclick='clickActionBtn(this.value)'>Действие</button>" +
            "<button class='btn btn-danger ml-2 mt-1' id='btn-edit-collectionPoint' value='" + collectionPoint.id + "' onclick='deleteCollectionPoint(this.value)'>Удалить</button></td>\n" +
            "</tr>"
    })
    return out;
}

function createViewMiningOrUppgSelect(miningsOrUppgs) {
    let out = "";
    miningsOrUppgs.map(item => {
        out += "<option value='" + item.id + "'>" + item.name + "</option>"
    })
    return out;
}

function addOptionOpcServers(servers) {
    let out = "<option value=''>ОПС серверы</option>";
    servers.map(item => {
        out += "<option value='"+item.id+"'>"+item.name+"</option>"
    })
    return out;
}