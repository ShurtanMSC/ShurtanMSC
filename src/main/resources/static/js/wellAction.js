let wellActionsList;
let wellID;

function goOutFromAction() {
    getAllWells()
    document.getElementById('actionWellH1').innerText = "Скважина";
    document.getElementById('addWellBtn').style.display = 'block';
    document.getElementById('miningSelect').style.display = 'block';
    document.getElementById('uppgSelect').style.display = 'block';
    document.getElementById('collectionPointSelect').style.display = 'block';
    document.getElementById('cardTableWell').style.display = 'block';
    document.getElementById('cardTableWellAction').style.display = 'none';
    document.getElementById('addWellActionBtn').style.display = 'none';
    document.getElementById('goOutActionsIcon').style.display = 'none';

}

function clickWellActionBtn(id) {
    wellID = id
    let actionWell = wellsList.find(well => well.id == id)

    document.getElementById('actionWellH1').innerText = "Скважина - "+actionWell.number;
    document.getElementById('addWellBtn').style.display = 'none';
    document.getElementById('miningSelect').style.display = 'none';
    document.getElementById('uppgSelect').style.display = 'none';
    document.getElementById('collectionPointSelect').style.display = 'none';
    document.getElementById('cardTableWell').style.display = 'none';
    document.getElementById('cardTableWellAction').style.display = 'block';
    document.getElementById('addWellActionBtn').style.display = 'block';
    document.getElementById('goOutActionsIcon').style.display = 'block';

    getActionsByWell()
}

function getActionsByWell() {
    let formField = document.getElementById('addOrEditWellActionForm');
    formField['wellId'].value = wellID;

    axios.get("/api/well/actions/" + wellID)
        .then(function (response) {
            // console.log(response)
            if (response.data.message === "OK" || response.status === 200) {
                wellActionsList = response.data.object
                document.getElementById("wellTableAction").innerHTML = createViewTableAction(response.data.object)
            }
        })
        .catch(function (error) {
            console.log(error)
        })
}

function resetAndCloseFormAction() {
    document.getElementById('closeFormBtnAction').click();
    document.getElementById('addOrEditWellActionForm').reset();
}

function addOrEditWellAction(event) {
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
        config.url = '/api/well/add/action'
    } else {
        config.method = 'put';
        config.url = '/api/well/edit/action'
    }

    axios(config)
        .then(function () {
            resetAndCloseFormAction();
            getActionsByWell();
        })
        .catch(function (error) {
            console.log("error.response");
            console.log(error);
        });
}

document.getElementById('addWellActionBtn').addEventListener('click', addWellActionBtn);

function addWellActionBtn() {
    document.getElementById('addOrEditWellActionH3').innerText = 'Добавить действие'
    document.getElementById('addOrEditWellActionBtn').innerText = 'Добавить'
}

function editWellAction(id) {
    document.getElementById('addOrEditWellActionH3').innerText = 'Редактировать действие'
    document.getElementById('addOrEditWellActionBtn').innerText = 'Редактировать'

    let editWellAction = wellActionsList.find(action => action.actionId == id)
    let formField = document.getElementById('addOrEditWellActionForm')

    formField['actionId'].value = editWellAction.actionId;
    formField['expend'].value = editWellAction.expend;
    formField['designedPerformance'].value = editWellAction.designedPerformance;
    formField['actualPerformance'].value = editWellAction.actualPerformance;
    formField['condensate'].value = editWellAction.condensate;
    formField['onWater'].value = editWellAction.onWater;
    formField['incomeTemperature'].value = editWellAction.incomeTemperature;
    formField['exitTemperature'].value = editWellAction.exitTemperature;
    formField['incomePressure'].value = editWellAction.incomePressure;
    formField['exitPressure'].value = editWellAction.exitPressure;
    formField['wellId'].value = editWellAction.wellId;
}

function deleteWellAction(id) {
    axios.delete("/api/well/delete/action/" + id)
        .then(function (response) {
            // console.log(response.data)
            getActionsByWell()
        })
        .catch(function (error) {
            console.log(error.response.data)
            alert(error.response.data.message)
        })
}

function createViewTableAction(actions) {
    console.log(actions)
    let out = "";
    actions.map(action => {
        const createdAtDate = new Date('' + action.createdAt + '');
        const createdAtDayOfMonth = createdAtDate.getDate();
        const createdAtMonth = createdAtDate.getMonth(); // Be careful! January is 0, not 1
        const createdAtYear = createdAtDate.getFullYear();
        const createdAtDateString = createdAtDayOfMonth + "-" + (createdAtMonth + 1) + "-" + createdAtYear;

        out += "<tr class=\"action_table_row\">\n" +
            "   <td class=\"sorting_1\">" + action.actionId + "</td>\n" +
            "    <td>" + action.pressure + "</td>\n" +
            "    <td>" + action.temperature + "</td>\n" +
            "    <td>" + action.average_expend + "</td>\n" +
            "    <td>" + action.expend + "</td>\n" +
            "    <td>" + action.rpl + "</td>\n" +
            "    <td>" + action.p_pkr + "</td>\n" +
            "    <td>" + action.t_pkr + "</td>\n" +
            "    <td>" + action.p_pr + "</td>\n" +
            "    <td>" + action.t_pr + "</td>\n" +
            "    <td>" + action.ro_otn + "</td>\n" +
            "    <td>" + action.z + "</td>\n" +
            "    <td>" + action.delta + "</td>\n" +
            "    <td>" + action.c + "</td>\n" +
            "    <td>" + action.ro_gas + "</td>\n" +
            "    <td>" + action.ro_air + "</td>\n" +
            "    <td>" + action.status + "</td>\n" +
            "    <td>" + action.perforation_min + "</td>\n" +
            "    <td>" + action.perforation_max + "</td>\n" +
            "    <td>" + createdAtDateString + "</td>\n" +
            "    <td hidden>" + action.wellId + "</td>\n" +
            "     <td id=\"wellIdTd\" hidden value='" + action.wellId + "'>" + action.wellId + "</td>\n" +
            "     <td><button data-target=\"#exampleModalCenterAction\" data-toggle=\"modal\" class='btn btn-success ml-1 mt-1' id='btn-edit-action' value='" + action.actionId + "' onclick='editWellAction(this.value)'>Редактировать</button>\n" +
            "      <button class='btn btn-danger ml-1 mt-1' id='btn-edit-action' value='" + action.actionId + "' onclick='deleteWellAction(this.value)'>Удалить</button></td>\n" +
            "   </tr>"
    })
    return out;
}

