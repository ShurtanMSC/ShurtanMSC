let miningSystemActionsList;
let miningSystemID;

function goOutFromAction() {

    getAllMiningSystems()

    document.getElementById('actionMiningSystemH1').innerText = "Месторождений";
    document.getElementById('addMiningSystemBtn').style.display = 'block';
    document.getElementById('cardTableMiningSystem').style.display = 'block';
    document.getElementById('cardTableMiningSystemAction').style.display = 'none';
    document.getElementById('addActionMiningSystemBtn').style.display = 'none';
    document.getElementById('goOutActionsIcon').style.display = 'none';
}

function clickActionBtn(id) {
    miningSystemID = id
    let actionMiningSystem = mining_systemsList.find(mining_system => mining_system.objectDto.id == id)

    document.getElementById('actionMiningSystemH1').innerText = actionMiningSystem.objectDto.name;
    document.getElementById('addMiningSystemBtn').style.display = 'none';
    document.getElementById('cardTableMiningSystem').style.display = 'none';
    document.getElementById('cardTableMiningSystemAction').style.display = 'block';
    document.getElementById('addActionMiningSystemBtn').style.display = 'block';
    document.getElementById('goOutActionsIcon').style.display = 'block';

    getActionsByMiningSystem()
}

function getActionsByMiningSystem() {
    let formField = document.getElementById('addOrEditMiningSystemActionForm');
    formField['miningSystemId'].value = miningSystemID;

    axios.get("/api/admin/mining/allActions/" + miningSystemID)
        .then(function (response) {
            // console.log(response.data.object)
            if (response.data.message === "OK") {
                miningSystemActionsList = response.data.object
            }
            document.getElementById("miningTableAction").innerHTML = createViewTableAction(response.data.object)
        })
        .catch(function (error) {
            console.log(error)
        })

}

function resetAndCloseFormAction() {
    document.getElementById('closeFormBtnAction').click();
    document.getElementById('addOrEditMiningSystemActionForm').reset();
}

function addOrEditMiningSystemAction(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    const data = {}
    formData.forEach((value, key) => (data[key] = value));
    let config = {
        method: '',
        url: '',
        data
    };

    if (data.actionId === "" || data.actionId == null) {
        config.method = 'post';
        config.url = '/api/mining_system/add/action'
    } else {
        config.method = 'put';
        config.url = '/api/mining_system/edit/action'
    }

    axios(config)
        .then(function () {
            resetAndCloseFormAction();
            getActionsByMiningSystem();
        })
        .catch(function (error) {
            console.log("error.response");
            console.log(error);
        });
}

document.getElementById('addMiningSystemActionBtn').addEventListener('click', addMiningSystemActionBtn);

function addMiningSystemActionBtn() {
    document.getElementById('addOrEditMiningSystemActionH3').innerText = 'Добавить действие'
    document.getElementById('addOrEditMiningSystemActionBtn').innerText = 'Добавить'
}

function editMiningSystemAction(id) {
    document.getElementById('addOrEditMiningSystemActionH3').innerText = 'Редактировать действие'
    document.getElementById('addOrEditMiningSystemActionBtn').innerText = 'Редактировать'

    let editMiningSystemAction = miningSystemActionsList.find(action => action.actionId == id)
    let formField = document.getElementById('addOrEditMiningSystemActionForm')

    formField['actionId'].value = editMiningSystemAction.actionId;
    formField['expend'].value = editMiningSystemAction.expend;
    formField['miningSystemId'].value = editMiningSystemAction.miningSystemId;
}

function deleteMiningSystemAction(id) {
    axios.delete("/api/mining_system/delete/action/" + id)
        .then(function (response) {
            // console.log(response.data)
            getActionsByMiningSystem()
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
        const createdAtDateString = createdAtDayOfMonth + "-" + (createdAtMonth + 1) + "-" + createdAtYear;

        out += "<tr class=\"action_table_row\">\n" +
            "   <td class=\"sorting_1\">" + action.actionId + "</td>\n" +
            "    <td>" + action.expend + "</td>\n" +
            "     <td >" + createdAtDateString + "</td>\n" +
            "     <td id=\"miningSystemIdTd\" hidden value='" + action.miningSystemId + "'>" + action.miningSystemId + "</td>\n" +
            "     <td><button data-target=\"#exampleModalCenterAction\" data-toggle=\"modal\" class='btn btn-success mt-1' id='btn-edit-action' value='" + action.actionId + "' onclick='editMiningSystemAction(this.value)'>Редактировать</button>\n" +
            "      <button class='btn btn-danger ml-2 mt-1' id='btn-edit-action' value='" + action.actionId + "' onclick='deleteMiningSystemAction(this.value)'>Удалить</button></td>\n" +
            "   </tr>"
    })
    return out;
}

