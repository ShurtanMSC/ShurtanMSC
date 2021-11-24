let wellActionsList;
let wellID;
let wellActionPageNum = 1;
let wellActionPageSize = 10;

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

function getActionsByWell(page, pageSize) {
    let formField = document.getElementById('addOrEditWellActionForm');
    formField['wellId'].value = wellID;

    if (pageSize === undefined) {
        pageSize = wellActionPageSize;
    }
    if (page === undefined) {
        page = wellActionPageNum;
    }

    let pageNum = page - 1;

    let config = {
        method: 'get',
        url: ''
    };

    config.url = '/api/well/actions/' + wellID + '?page=' + pageNum + '&pageSize=' + pageSize + ''

    axios(config)
        .then(function (response) {
            // console.log(response)
            if (response.data.message === "OK" || response.status === 200) {
                wellActionsList = response.data.object

                wellActionPageNum = response.data.pageNumber + 1;

                document.getElementById("wellTableAction").innerHTML = createViewTableAction(response.data.object)
                document.getElementById("totalPages").innerHTML = createViewPaginationWellAction(response.data.totalPages, wellActionPageNum);
                document.getElementById("dataTableLengthSelect").innerHTML = createViewDataTableLengthSelect(response.data.totalElements);
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
            // console.log("res")
            // console.log(res)
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

    // console.log(editWellAction)

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
    // console.log(actions)
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


function createViewPaginationWellAction(totalPages, pageNumber) {
    let li = "";
    if (pageNumber === 1) {
        li = "<li class=\"paginate_button page-item previous active \"\n" +
            "    id=\"dataTable_previousAction\"><button disabled aria-controls=\"dataTable\"\n" +
            "   data-dt-idx=\"0\" tabindex=\"0\"\n" +
            "   class=\"page-link\">Previous</button>\n" +
            "   </li>";
    } else {
        li = "<li class=\"paginate_button page-item previous \"\n" +
            "    id=\"dataTable_previousAction\"><button value='\" + 0 + \"' onclick='getActionsByWell(wellActionPageNum-1)' " +
            " aria-controls=\"dataTable\"\n" +
            "   data-dt-idx=\"0\" tabindex=\"0\"\n" +
            "   class=\"page-link\">Previous</button>\n" +
            "   </li>";
    }

    for (let i = 1; i <= totalPages; i++) {
        if (i === pageNumber || i === 0) {
            li += "<li class=\"paginate_button page-item active\"><button value='" + i + "' onclick='getActionsByWell(this.value)' " +
                " href=\"#\"\n" +
                "  aria-controls=\"dataTable\"\n" +
                "  data-dt-idx=" + i + "\n" +
                " tabindex=\"0\"\n" +
                "  class=\"page-link\">" + i + "</button>\n" +
                "  </li>"
        } else {
            li += "<li class=\"paginate_button page-item\"><button value='" + i + "' onclick='getActionsByWell(this.value)' " +
                "  aria-controls=\"dataTable\"\n" +
                "  data-dt-idx=" + i + "\n" +
                " tabindex=\"0\"\n" +
                "  class=\"page-link\">" + i + "</button>\n" +
                "  </li>"
        }
    }

    if (pageNumber === totalPages) {
        li += "<li class=\"paginate_button page-item next active\" id=\"dataTable_nextAction\"><button disabled \n" +
            "  href=\"#\" aria-controls=\"dataTable\" data-dt-idx=\"7\" tabindex=\"0\"\n" +
            "  class=\"page-link\">Next</button>\n" +
            " </li>"
    }
    if (pageNumber < totalPages) {
        li += "<li class=\"paginate_button page-item next \" id=\"dataTable_nextAction\"><button value='" + 1 + "' onclick='getActionsByWell(wellActionPageNum+1)' " +
            "  href=\"#\" aria-controls=\"dataTable\" data-dt-idx=\"7\" tabindex=\"0\"\n" +
            "  class=\"page-link\">Next</button>\n" +
            " </li>"
    }

    return li;
}

function createViewDataTableLengthSelect(totalElements) {
    let selectOption = "";
    if (totalElements < 25) {
        selectOption += "<option value=10>10</option>\n"
    } else if (totalElements >= 25 && totalElements < 50) {
        selectOption += "<option value=10>10</option>\n" +
            " <option value=25>25</option>\n"
    } else if (totalElements >= 50 && totalElements < 100) {
        selectOption += "<option value=10>10</option>\n" +
            " <option value=25>25</option>\n" +
            " <option value=50>50</option>\n"
    } else if (totalElements >= 100) {
        selectOption += "<option value=10>10</option>\n" +
            " <option value=25>25</option>\n" +
            " <option value=50>50</option>\n" +
            " <option value=100>100</option>"
    }
    document.getElementById('dataTableLengthSelect').getElementsByTagName('option')[wellActionPageSize].selected = 'selected';

    return selectOption;
}

function handleDataTableLengthSelect(pageSize) {
    // console.log(pageSize)
    wellActionPageSize = parseInt(pageSize)
    getActionsByWell(1, wellActionPageSize);
}