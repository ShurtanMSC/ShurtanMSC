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

    document.getElementById('actionWellH1').innerText = "Скважина - " + actionWell.number;
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

    // console.log(data)

    if (data.actionId === "" || data.actionId == null) {
        config.method = 'post';
        config.url = '/api/well/manually/add/action'
    } else {
        config.method = 'put';
        config.url = '/api/well/edit/action'
    }

    axios(config)
        .then(function (res) {
            console.log("res")
            console.log(res)
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

    console.log(editWellAction)

    formField['actionId'].value = editWellAction.actionId;
    formField['pressure'].value = editWellAction.pressure;
    formField['temperature'].value = editWellAction.temperature;
    formField['average_expend'].value = editWellAction.average_expend;
    formField['expend'].value = editWellAction.expend;
    formField['rpl'].value = editWellAction.rpl;
    formField['p_pkr'].value = editWellAction.p_pkr;
    formField['t_pkr'].value = editWellAction.t_pkr;
    formField['p_pr'].value = editWellAction.p_pr;
    formField['t_pr'].value = editWellAction.t_pr;
    formField['ro_otn'].value = editWellAction.ro_otn;
    formField['z'].value = editWellAction.z;
    formField['delta'].value = editWellAction.delta;
    formField['c'].value = editWellAction.c;
    formField['ro_gas'].value = editWellAction.ro_gas;
    formField['ro_air'].value = editWellAction.ro_air;
    formField['status'].value = editWellAction.status;
    formField['perforation_min'].value = editWellAction.perforation_min;
    formField['perforation_max'].value = editWellAction.perforation_max;
    formField['wellId'].value = editWellAction.wellId;
}

function deleteWellAction(id) {
    axios.delete("/api/well/delete/action/" + id)
        .then(function (response) {
            console.log(response)
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
            "    <td>" + Math.round(action.pressure * 1000) / 1000 + "</td>\n" +
            "    <td>" + action.temperature + "</td>\n" +
            "    <td>" + Math.round(action.average_expend * 1000) / 1000 + "</td>\n" +
            "    <td>" + Math.round(action.expend * 1000) / 1000 + "</td>\n" +
            "    <td>" + Math.round(action.rpl * 1000) / 1000 + "</td>\n" +
            "    <td>" + Math.round(action.p_pkr * 1000) / 1000 + "</td>\n" +
            "    <td>" + Math.round(action.t_pkr * 1000) / 1000 + "</td>\n" +
            "    <td>" + Math.round(action.p_pr * 1000) / 1000 + "</td>\n" +
            "    <td>" + Math.round(action.t_pr * 1000) / 1000 + "</td>\n" +
            "    <td>" + Math.round(action.ro_otn * 1000) / 1000 + "</td>\n" +
            "    <td>" + Math.round(action.z * 1000) / 1000 + "</td>\n" +
            "    <td>" + Math.round(action.delta * 1000) / 1000 + "</td>\n" +
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

