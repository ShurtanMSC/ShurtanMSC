let gasCompositionsList = [];
let molarList = [];
let oneMolarData;
let miningSystemID = 1;
let gasCompositionId = 1;
// let collectionPointID;
// let PAGENUM = 1;
// let PAGESIZE = 10;

function goOutFromComposition() {
    document.getElementById('compositionConstantH1').innerText = "Константы";
    document.getElementById('selectConstantOf').style.display = 'block';
    document.getElementById('gasCompositionBtn').style.display = 'block';
    document.getElementById('cardTableConstant').style.display = 'block';

    document.getElementById('cardTableGasComposition').style.display = 'none';
    document.getElementById('goOutCompositionBtn').style.display = 'none';
    document.getElementById('gasCompositionOrMolarSelectDiv').style.display = 'none';
    document.getElementById('cardTableMolar').style.display = 'none';
    document.getElementById('gasCompositionOrMolar').value = "GAS_COMPOSITION"

}

function gasCompositionBtn() {
    document.getElementById('compositionConstantH1').innerText = "Газовый состав";
    document.getElementById('selectConstantOf').style.display = 'none';
    document.getElementById('gasCompositionBtn').style.display = 'none';
    document.getElementById('cardTableConstant').style.display = 'none';

    document.getElementById('cardTableGasComposition').style.display = 'block';
    document.getElementById('goOutCompositionBtn').style.display = 'block';
    document.getElementById('gasCompositionOrMolarSelectDiv').style.display = 'block';
    getAllGasCompositions();
}

function getAllGasCompositions() {
    let config = {
        method: 'get',
        url: ''
    };

    config.url = '/api/gas_composition/all/'

    axios(config)
        .then(function (response) {
            console.log(response.data)
            if (response.status === 200) {
                gasCompositionsList = response.data.object
                document.getElementById("gasCompositionsTable").innerHTML = createViewGasCompositionsTable(response.data);
                document.getElementById("gasCompositionNames").innerHTML = addOptionsGasCompositionNames(response.data.object);
            }
        })
        .catch(function (error) {
            console.log(error.response)
        })
}

function getAllMolar() {
    let config = {
        method: 'get',
        url: ''
    };
    config.url = '/api/gas_composition/molar/all'

    axios(config)
        .then(function (response) {
            // console.log(response.data)
            if (response.status === 200) {
                molarList = response.data.object

                oneMolarData = molarList.find(item => item.objectDto.id == miningSystemID)

                document.getElementById("molarTable").innerHTML = createViewMolarTable(oneMolarData.objectActionDto);
                document.getElementById('miningSystemIdForMolar').value = miningSystemID;

            }
        })
        .catch(function (error) {
            console.log(error.response)
        })
}

function resetAndCloseFormAction() {
    document.getElementById('closeGasCompositionFormBtn').click();
    document.getElementById('closeMolarFormBtn').click();
    document.getElementById('addOrEditGasCompositionForm').reset();
    document.getElementById('addOrEditMolarForm').reset();
}

function addOrEditGasComposition(event) {
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

    if (data.id === "" || data.id == null) {
        console.log("add")
        config.method = 'post';
        config.url = '/api/gas_composition/add'
    } else {
        console.log("edit")
        config.method = 'put';
        config.url = '/api/gas_composition/edit'
    }

    axios(config)
        .then(function () {
            resetAndCloseFormAction();
            getAllGasCompositions()
        })
        .catch(function (error) {
            console.log("error.response");
            console.log(error);
        });
}

// document.getElementById('addGasCompositionBtn').addEventListener('click', addGasCompositionBtn);

function addGasCompositionBtn() {
    document.getElementById('addOrEditGasCompositionFormH3').innerText = 'Добавить Газовый состав'
    document.getElementById('addOrEditGasCompositionBtn').innerText = 'Добавить'
}

function editGasComposition(id) {
    document.getElementById('addOrEditGasCompositionFormH3').innerText = 'Редактировать Газовый состав'
    document.getElementById('addOrEditGasCompositionBtn').innerText = 'Редактировать'


    let editGasComposition = gasCompositionsList.find(item => item.id == id)
    let formField = document.getElementById('addOrEditGasCompositionForm')

    console.log("editGasComposition")
    console.log(editGasComposition)

    formField['id'].value = editGasComposition.id;
    formField['name'].value = editGasComposition.name;
    formField['criticalPressure'].value = editGasComposition.criticalPressure;
    formField['criticalTemperature'].value = editGasComposition.criticalTemperature;

}

function deleteGasComposition(id) {
    axios.delete("/api/gas_composition/delete/" + id)
        .then(function (response) {
            // console.log(response.data)
            getAllGasCompositions()
        })
        .catch(function (error) {
            console.log(error.response.data)
            alert(error.response.data.message)
        })
}

function createViewGasCompositionsTable(data) {
    let out = "";
    data.object.map(composition => {
        out += "<tr class=\"composition_table_row\">" +
            "   <td class=\"sorting_1\">" + composition.id + "</td>" +
            "    <td>" + composition.name + "</td>" +
            "    <td>" + composition.criticalPressure + "</td>" +
            "    <td>" + composition.criticalTemperature + "</td>" +
            // "    <td>" + createdAtDateString + "</td>" +
            // "    <td>" + modifiedDateString + "</td>" +
            "     <td><button data-target=\"#modalGasComposition\" data-toggle=\"modal\" class='btn btn-success ml-1 mt-1' id='btn-edit-composition' value='" + composition.id + "' onclick='editGasComposition(this.value)'>Редактировать</button>" +
            "      <button class='btn btn-danger ml-1 mt-1' id='btn-edit-composition' value='" + composition.id + "' onclick='deleteGasComposition(this.value)'>Удалить</button></td>" +
            "   </tr>"
    })

    return out;
}

// ----------------------------------------------------- M O L A R -------------------------------------
function createViewMolarTable(data) {
    let out = "";
    // console.log("createViewMolarTable")
    // console.log(data)
    data.map(molar => {
        out += "<tr class=\"composition_table_row\">" +
            "   <td class=\"sorting_1\">" + molar.id + "</td>" +
            // "    <td>" + molar.name + "</td>" +
            "    <td>" + molar.gasCompositionName + "</td>" +
            "    <td>" + molar.molarFraction + "</td>" +
            // "    <td>" + createdAtDateString + "</td>" +
            // "    <td>" + modifiedDateString + "</td>" +
            "     <td><button data-target=\"#modalForAddOrEditMolar\" data-toggle=\"modal\" class='btn btn-success ml-1 mt-1' id='btn-edit-molar' value='" + molar.id + "' onclick='editMolar(this.value)'>Редактировать</button>" +
            "      <button class='btn btn-danger ml-1 mt-1' id='btn-edit-composition' value='" + molar.id + "' onclick='deleteMolar(this.value)'>Удалить</button></td>" +
            "   </tr>"
    })

    return out;
}

function selectHandleGasCompositionOrMolar() {
    let constantOf2 = document.getElementById('gasCompositionOrMolar').value;

    if (constantOf2 === "GAS_COMPOSITION") {
        document.getElementById('cardTableMolar').style.display = 'none';
        document.getElementById('cardTableGasComposition').style.display = 'block';
        document.getElementById('compositionConstantH1').innerText = "Газовый состав";

    } else if (constantOf2 === "MOLAR") {
        document.getElementById('cardTableGasComposition').style.display = 'none';
        document.getElementById('cardTableMolar').style.display = 'block';
        document.getElementById('compositionConstantH1').innerText = "Молярная доля добываемого газа м/р Шуртан";
        getAllMolar();
    }
}

function selectHandleMiningForMolar() {
    miningSystemID = document.getElementById('miningSelectForMolar').value;
    document.getElementById('miningSystemIdForMolar').value = miningSystemID;

    oneMolarData = molarList.find(item => item.objectDto.id == miningSystemID)

    document.getElementById("molarTable").innerHTML = createViewMolarTable(oneMolarData.objectActionDto);
}

function addOptionsGasCompositionNames(names) {

    let out = "<option value=''>Выберите газового состава</option>";
    names.map(item => {
        out += "<option value='" + item.id + "'>" + item.name + "</option>"
    })
    return out;
}

function handleGasCompositionId() {
    gasCompositionId = document.getElementById('gasCompositionNames').value
}

function addOrEditMolar(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    const data = {}
    formData.forEach((value, key) => (data[key] = value));
    let config = {
        method: '',
        url: '',
        data
    };

    console.log("data mOLAAAr")
    console.log(data)

    if (data.id === "" || data.id == null) {
        console.log("add")
        config.method = 'post';
        config.url = '/api/gas_composition/molar/add'
    } else {
        console.log("edit")
        config.method = 'put';
        config.url = '/api/gas_composition/molar/edit'
    }

    axios(config)
        .then(function () {
            resetAndCloseFormAction();
            getAllMolar();
        })
        .catch(function (error) {
            if (error.response.status == 409) {
                window.alert("Имя газового состава Молярная доля Уже существует");
                resetAndCloseFormAction();
            }
            console.log("error.response");
            console.log(error);
        });
}

function deleteMolar(id) {
    axios.delete("/api/gas_composition/molar/delete/" + id)
        .then(function (response) {
            // console.log(response.data)
            getAllMolar()
        })
        .catch(function (error) {
            console.log(error.response.data)
            alert(error.response.data.message)
        })
}

function editMolar(id) {
    document.getElementById('addOrEditMolarFormH3').innerText = 'Редактировать Молярная доля'
    document.getElementById('addOrEditMolarBtn').innerText = 'Редактировать'

    let findMolar = molarList.find(item => item.objectDto.id == miningSystemID)
    let editMolar = findMolar.objectActionDto.find(item => item.id == id)

    console.log("molarList")
    console.log(molarList)
    console.log("editMolar")
    console.log(editMolar)
    let formField = document.getElementById('addOrEditMolarForm')

    formField['id'].value = editMolar.id;
    formField['gasCompositionId'].value = editMolar.gasCompositionId;
    formField['molarFraction'].value = editMolar.molarFraction;
    formField['miningSystemId'].value = editMolar.miningSystemId;

}