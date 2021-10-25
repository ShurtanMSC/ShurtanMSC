let uppgActionsList;
let uppgID;

function goOutFromAction() {
    getAllUppgs()
    document.getElementById('actionUppgH1').innerText = "УППГ";
    document.getElementById('addUppgBtn').style.display = 'block';
    document.getElementById('cardTableUppg').style.display = 'block';
    document.getElementById('cardTableUppgAction').style.display = 'none';
    document.getElementById('addActionUppgBtn').style.display = 'none';
    document.getElementById('goOutActionsIcon').style.display = 'none';
}

function clickActionBtn(id) {
    uppgID = id
    let actionUppg = uppgsList.find(uppg => uppg.id == id)

    document.getElementById('actionUppgH1').innerText = actionUppg.name;
    document.getElementById('addUppgBtn').style.display = 'none';
    document.getElementById('cardTableUppg').style.display = 'none';
    document.getElementById('cardTableUppgAction').style.display = 'block';
    document.getElementById('addActionUppgBtn').style.display = 'block';
    document.getElementById('goOutActionsIcon').style.display = 'block';

    getActionsByUppg()
}

function getActionsByUppg() {
    let formField = document.getElementById('addOrEditUppgActionForm');
    formField['uppgId'].value = uppgID;

    axios.get("/api/uppg/actions/" + uppgID)
        .then(function (response) {
            // console.log(response.data.object)
            if (response.data.message === "OK") {
                uppgActionsList = response.data.object
            }
            document.getElementById("uppgTableAction").innerHTML = createViewTableAction(response.data.object)
        })
        .catch(function (error) {
            console.log(error)
        })

}

function resetAndCloseFormAction() {
    document.getElementById('closeFormBtnAction').click();
    document.getElementById('addOrEditUppgActionForm').reset();
}

function addOrEditUppgAction(event) {
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
        config.url = '/api/uppg/add/action'
    } else {
        config.method = 'put';
        config.url = '/api/uppg/edit/action'
    }

    axios(config)
        .then(function () {
            resetAndCloseFormAction();
            getActionsByUppg();
        })
        .catch(function (error) {
            console.log("error.response");
            console.log(error);
        });
}

document.getElementById('addUppgActionBtn').addEventListener('click', addUppgActionBtn);

function addUppgActionBtn() {
    document.getElementById('addOrEditUppgActionH3').innerText = 'Добавить действие'
    document.getElementById('addOrEditUppgActionBtn').innerText = 'Добавить'
}

function editUppgAction(id) {
    document.getElementById('addOrEditUppgActionH3').innerText = 'Редактировать действие'
    document.getElementById('addOrEditUppgActionBtn').innerText = 'Редактировать'

    let editUppgAction = uppgActionsList.find(action => action.actionId == id)
    let formField = document.getElementById('addOrEditUppgActionForm')

    formField['actionId'].value = editUppgAction.actionId;
    formField['expend'].value = editUppgAction.expend;
    formField['uppgId'].value = editUppgAction.uppgId;
}

function deleteUppgAction(id) {
    axios.delete("/api/uppg/delete/action/" + id)
        .then(function (response) {
            // console.log(response.data)
            getActionsByUppg()
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
            "     <td id=\"uppgIdTd\" hidden value='" + action.uppgId + "'>" + action.uppgId + "</td>\n" +
            "     <td><button data-target=\"#exampleModalCenterAction\" data-toggle=\"modal\" class='btn btn-success mt-1' id='btn-edit-action' value='" + action.actionId + "' onclick='editUppgAction(this.value)'>Редактировать</button>\n" +
            "      <button class='btn btn-danger ml-2 mt-1' id='btn-edit-action' value='" + action.actionId + "' onclick='deleteUppgAction(this.value)'>Удалить</button></td>\n" +
            "   </tr>"
    })
    return out;
}

