let collectionPointActionsList;
let collectionPointID;

function goOutFromAction() {
    getAllCollectionPoints()
    document.getElementById('collectionPointActionH1').innerText = "Сборный пункты";
    document.getElementById('addCollectionPointBtn').style.display = 'block';
    document.getElementById('cardTableCollectionPoint').style.display = 'block';
    document.getElementById('miningSelect').style.display = 'block';
    document.getElementById('uppgSelect').style.display = 'block';
    document.getElementById('cardTableCollectionPointAction').style.display = 'none';
    document.getElementById('addCollectionPointActionBtn').style.display = 'none';
    document.getElementById('goOutActionsIcon').style.display = 'none';
}

function clickActionBtn(id) {
    collectionPointID = id
    let actionCollectionPoint = collectionPointsList.find(collectionPoint => collectionPoint.id == id)

    document.getElementById('collectionPointActionH1').innerText = actionCollectionPoint.name;
    document.getElementById('addCollectionPointBtn').style.display = 'none';
    document.getElementById('miningSelect').style.display = 'none';
    document.getElementById('uppgSelect').style.display = 'none';
    document.getElementById('cardTableCollectionPoint').style.display = 'none';
    document.getElementById('cardTableCollectionPointAction').style.display = 'block';
    document.getElementById('addCollectionPointActionBtn').style.display = 'block';
    document.getElementById('goOutActionsIcon').style.display = 'block';

    getActionsByCollectionPoint()
}

function getActionsByCollectionPoint() {
    let formField = document.getElementById('addOrEditCollectionPointActionForm');
    formField['collectionPointId'].value = collectionPointID;

    axios.get("/api/collection_point/actions/" + collectionPointID)
        .then(function (response) {
            console.log(response.data)
            if (response.data.message === "OK") {
                collectionPointActionsList = response.data.object
            }
            document.getElementById("collectionPointActionsTable").innerHTML = createViewTableAction(response.data.object)
        })
        .catch(function (error) {
            console.log(error)
        })

}

function resetAndCloseFormAction() {
    document.getElementById('closeFormBtnAction').click();
    document.getElementById('addOrEditCollectionPointActionForm').reset();
}

function addOrEditCollectionPointAction(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    const data = {}
    formData.forEach((value, key) => (data[key] = value));
    let config = {
        method: '',
        url: '',
        data
    };

    console.log(data)

    if (data.actionId === "" || data.actionId == null) {
        config.method = 'post';
        config.url = '/api/collection_point/manually/add/action'
    } else {
        config.method = 'put';
        config.url = '/api/collectionPoint/edit/action'
    }

    axios(config)
        .then(function () {
            resetAndCloseFormAction();
            getActionsByCollectionPoint();
        })
        .catch(function (error) {
            console.log("error.response");
            console.log(error);
        });
}

document.getElementById('addCollectionPointActionBtn').addEventListener('click', addCollectionPointActionBtn);

function addCollectionPointActionBtn() {
    document.getElementById('addOrEditCollectionPointActionH3').innerText = 'Добавить действие'
    document.getElementById('addOrEditCollectionPointActionBtn').innerText = 'Добавить'
}

function editCollectionPointAction(id) {
    document.getElementById('addOrEditCollectionPointActionH3').innerText = 'Редактировать действие'
    document.getElementById('addOrEditCollectionPointActionBtn').innerText = 'Редактировать'

    let editCollectionPointAction = collectionPointActionsList.find(action => action.actionId == id)
    let formField = document.getElementById('addOrEditCollectionPointActionForm')

    formField['actionId'].value = editCollectionPointAction.actionId;
    formField['expend'].value = editCollectionPointAction.expend;
    formField['pressure'].value = editCollectionPointAction.pressure;
    formField['temperature'].value = editCollectionPointAction.temperature;
    formField['collectionPointId'].value = editCollectionPointAction.collectionPointId;
    formField['createdAt'].value = editCollectionPointAction.createdAt;

}

function deleteCollectionPointAction(id) {
    axios.delete("/api/collection_point/delete/action/" + id)
        .then(function (response) {
            // console.log(response.data)
            getActionsByCollectionPoint()
        })
        .catch(function (error) {
            console.log(error.response.data)
            alert(error.response.data.message)
        })
}

function createViewTableAction(actions) {
    let out = "";
    actions.map(action => {
        const createdAtDate = new Date('' + action.createdAt + '');
        const createdAtDayOfMonth = createdAtDate.getDate();
        const createdAtMonth = createdAtDate.getMonth(); // Be careful! January is 0, not 1
        const createdAtYear = createdAtDate.getFullYear();
        const createdAtHours = createdAtDate.getHours();
        const createdAtMins = createdAtDate.getMinutes()
        const createdAtDateString = createdAtDayOfMonth + "-" + (createdAtMonth + 1) + "-" + createdAtYear+" " +createdAtHours+":"+createdAtMins;

        out += "<tr class=\"action_table_row\">\n" +
            "   <td class=\"sorting_1\">" + action.actionId + "</td>\n" +
            "    <td>" + action.expend + "</td>\n" +
            "    <td>" + action.pressure + "</td>\n" +
            "    <td>" + action.temperature + "</td>\n" +
            "     <td id=\"collectionPointIdTd\" hidden value='" + action.collectionPointId + "'>" + action.collectionPointId + "</td>\n" +
            "    <td>" + createdAtDateString + "</td>\n" +
            "     <td><button data-target=\"#exampleModalCenterAction\" data-toggle=\"modal\" class='btn btn-success ml-1 mt-1' id='btn-edit-action' value='" + action.actionId + "' onclick='editCollectionPointAction(this.value)'>Редактировать</button>\n" +
            "      <button class='btn btn-danger ml-1 mt-1' id='btn-edit-action' value='" + action.actionId + "' onclick='deleteCollectionPointAction(this.value)'>Удалить</button></td>\n" +
            "   </tr>"
    })
    return out;
}

