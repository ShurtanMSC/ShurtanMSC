let uppgActionsList;
let uppgID;

function goOutFromAction() {
    // getAllUppgs()
    // document.getElementById('actionUppgH1').innerText = "УППГ";
    // document.getElementById('addUppgBtn').style.display = 'block';
    // document.getElementById('cardTableUppg').style.display = 'block';
    // document.getElementById('miningSelect').style.display = 'block';
    // document.getElementById('cardTableUppgAction').style.display = 'none';
    // document.getElementById('addUppgActionBtn').style.display = 'none';
    // document.getElementById('goOutActionsIcon').style.display = 'none';
    window.location.href="/admin/uppg"

}

function clickActionBtn(id) {
    uppgID = id
    let actionUppg = uppgsList.find(uppg => uppg.id == id)

    document.getElementById('actionUppgH1').innerText = actionUppg.name;
    document.getElementById('addUppgBtn').style.display = 'none';
    document.getElementById('miningSelect').style.display = 'none';
    document.getElementById('cardTableUppg').style.display = 'none';
    document.getElementById('cardTableUppgAction').style.display = 'block';
    document.getElementById('addUppgActionBtn').style.display = 'block';
    document.getElementById('goOutActionsIcon').style.display = 'block';

    getActionsByUppg()
}

function getActionsByUppg() {
    const params = new URLSearchParams(window.location.search);
    let page=params.get('page')
    let pageSize=params.get('pageSize')
    let uppg=params.get('uppg')

    let formField = document.getElementById('addOrEditUppgActionForm');
    formField['uppgId'].value = uppgID;

    // axios.get("/api/uppg/actions/" + uppgID)
    axios.get("/api/uppg/actions/" + uppg+"?page="+(page-1)+"&&pageSize="+pageSize)
        .then(function (response) {
            console.log(response.data.object)
            if (response.data.message === "OK") {
                uppgActionsList = response.data.object
            }
            document.getElementById("totalPages").innerHTML = createViewPaginationAction(response.data.totalPages, page-1, uppg, pageSize);
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

    console.log(data)

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
    formField['designedPerformance'].value = editUppgAction.designedPerformance;
    formField['actualPerformance'].value = editUppgAction.actualPerformance;
    formField['condensate'].value = editUppgAction.condensate;
    formField['onWater'].value = editUppgAction.onWater;
    formField['incomeTemperature'].value = editUppgAction.incomeTemperature;
    formField['exitTemperature'].value = editUppgAction.exitTemperature;
    formField['incomePressure'].value = editUppgAction.incomePressure;
    formField['exitPressure'].value = editUppgAction.exitPressure;
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
        const createdAtHours = createdAtDate.getHours();
        const createdAtMins = createdAtDate.getMinutes()
        const createdAtDateString = createdAtDayOfMonth + "-" + (createdAtMonth + 1) + "-" + createdAtYear + " " + createdAtHours + ":" + createdAtMins;

        const modifiedDate = new Date('' + action.modified + '');
        const modifiedDayOfMonth = modifiedDate.getDate();
        const modifiedMonth = modifiedDate.getMonth(); // Be careful! January is 0, not 1
        const modifiedYear = modifiedDate.getFullYear();
        const modifiedHours = modifiedDate.getHours();
        const modifiedMins = modifiedDate.getMinutes()

        const modifiedDateString = action.modified!=null? modifiedDayOfMonth + "-" + (modifiedMonth + 1) + "-" + modifiedYear + " " + modifiedHours + ":" + modifiedMins:0;






        out += "<tr class=\"action_table_row\">\n" +
            "   <td class=\"sorting_1\">" + action.actionId + "</td>\n" +
            "    <td>" + action.expend + "</td>\n" +
            "    <td>" + Math.round(action.designedPerformance*100)/100 + "</td>\n" +
            "    <td>" + Math.round(action.actualPerformance*100)/100 + "</td>\n" +
            // "    <td>" + action.condensate + "</td>\n" +
            // "    <td>" + action.onWater + "</td>\n" +
            "    <td>" + Math.round(action.actualPerformance*100)/100 + "</td>\n" +
            "    <td>" + Math.round(action.lastMonthExpend*100)/100 + "</td>\n" +
            "    <td>" + Math.round(action.incomeTemperature*100)/100 + "</td>\n" +
            "    <td>" + Math.round(action.exitTemperature*100)/100 + "</td>\n" +
            "    <td>" + Math.round(action.incomePressure*100)/100 + "</td>\n" +
            "    <td>" + Math.round(action.exitPressure*100)/100 + "</td>\n" +
            "    <td hidden>" + action.uppgId + "</td>\n" +
            "    <td>" + createdAtDateString + "</td>\n" +
            "    <td>" + modifiedDateString + "</td>\n" +
            "     <td id=\"uppgIdTd\" hidden value='" + action.uppgId + "'>" + action.uppgId + "</td>\n" +
            "     <td><button data-target=\"#exampleModalCenterAction\" data-toggle=\"modal\" class='btn btn-success ml-1 mt-1' id='btn-edit-action' value='" + action.actionId + "' onclick='editUppgAction(this.value)'>Редактировать</button>\n" +
            "      <button class='btn btn-danger ml-1 mt-1' id='btn-edit-action' value='" + action.actionId + "' onclick='deleteUppgAction(this.value)'>Удалить</button></td>\n" +
            "   </tr>"
    })
    return out;
}


function createViewPaginationAction(totalPages, pageNumber, uppg, pageSize) {
    let li = "";

    pageNumber++;
    if (totalPages <= 1) {
        return li=`<li class='paginate_button page-item active'><button ` +
            `   class=\"page-link\" >1</button> </li>`;
    }


    let beforePage = pageNumber - 1;
    let afterPage = pageNumber + 1;

    let activeLi;

    console.log(pageNumber)

    if (pageNumber > 1) {
        li += `<li class='paginate_button page-item previous'><a href="/admin/uppg?uppg=`+uppg+`&&page=`+(pageNumber-1)+`&&pageSize=`+pageSize+`" ` +
            `   class=\"page-link\" >Previous</a> </li>`;
    }

    if (pageNumber > 2) {
        li += "<li class=\"paginate_button page-item previous \"" +
            "><a href='/admin/uppg?uppg="+uppg+"&&page="+1+"&&pageSize="+pageSize+"' " +
            "   class=\"page-link\" >1</a> </li>";
        if (pageNumber > 3) {
            li += "<li class=\"paginate_button page-item previous \"><button " +
                "   class=\"page-link\" >. . . .</button> </li>";
        }
    }

    if (pageNumber === totalPages) {
        beforePage = beforePage - 2;
    } else if (pageNumber === totalPages - 1) {
        beforePage = beforePage - 1;
    }

    if (pageNumber === 1) {
        afterPage = afterPage + 2;
    } else if (pageNumber === 2) {
        afterPage = afterPage + 1;
    }

    for (let i = beforePage; i <= afterPage; i++) {

        if (i > totalPages) {
            continue;
        }
        if (i === -1) {
            continue;
        }
        if (i === 0) {
            i = i + 1;
        }

        if (i === pageNumber) {
            activeLi = "active";
        } else {
            activeLi = "";
        }
        li += `<li class="paginate_button page-item ${activeLi}"><a href="/admin/uppg?uppg=`+uppg+`&&page=`+i+`&&pageSize=`+pageSize+`" ` +
            `  class='page-link'>` + i + `</a> </li>`
    }

    if (pageNumber < totalPages - 1) {
        if (pageNumber < totalPages - 2) {
            li += "<li class=\"paginate_button page-item previous \"><button  " +
                "   class=\"page-link\" >. . . .</button></li>";
        }
        li += `<li class='paginate_button page-item previous ' ` +
            `   ><a href="/admin/uppg?uppg=`+uppg+`&&page=`+totalPages+`&&pageSize=`+pageSize+`" ` +
            `   class='page-link'>${totalPages}</a></li>`;
    }

    if (pageNumber < totalPages) {
        li += `<li class='paginate_button page-item next'><a  href="/admin/uppg?uppg=`+uppg+`&&page=`+(pageNumber+1)+`&&pageSize=`+pageSize+`" class=\"page-link\">Next</a></li>`
    }

    return li;
}
