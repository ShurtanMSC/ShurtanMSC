let constantsList = []
let miningSystemId = 1;
let uppgId = 1
let collectionPointId = 1
let wellId = 1
let constantOf = "MINING_SYSTEM";

function getAllMiningSystemConstants() {
    axios.get("/api/constant/mining/all/" + miningSystemId)
        .then(function (response) {
            // console.log(response.data)
            if (response.data.message === "OK") {
                constantsList = response.data.object
                document.getElementById("constantTable").innerHTML = createViewTable(response.data.object)
            }
        })
        .catch(function (error) {
            console.log(error)
        })
}

function getAllUppgConstants() {
    axios.get("/api/constant/uppg/all/" + uppgId)
        .then(function (response) {
            // console.log(response.data)
            if (response.data.message === "OK") {
                constantsList = response.data.object
                document.getElementById("constantTable").innerHTML = createViewTable(response.data.object)
            }
        })
        .catch(function (error) {
            console.log(error)
        })
}

function getAllCollectionPointConstants() {
    axios.get("/api/constant/collection_point/all/" + collectionPointId)
        .then(function (response) {
            // console.log(response.data)
            if (response.data.message === "OK") {
                constantsList = response.data.object
                document.getElementById("constantTable").innerHTML = createViewTable(response.data.object)
            }
        })
        .catch(function (error) {
            console.log(error)
        })
}

function getAllWellConstants() {
    axios.get("/api/constant/well/all/" + wellId)
        .then(function (response) {
            // console.log(response.data)
            if (response.data.message === "OK") {
                constantsList = response.data.object
                document.getElementById("constantTable").innerHTML = createViewTable(response.data.object)
            }
        })
        .catch(function (error) {
            console.log(error)
        })
}

function selectHandleConstantOf() {
    constantOf = document.getElementById('constantOf').value;
    if (constantOf === "MINING_SYSTEM") {
        document.getElementById('miningSelect').style.display = 'block';
        document.getElementById('uppgSelect').style.display = 'none';
        document.getElementById('collectionPointSelect').style.display = 'none';
        document.getElementById('wellSelect').style.display = 'none';
        getAllMiningSystemConstants();
    } else if (constantOf === "UPPG") {
        document.getElementById('miningSelect').style.display = 'block';
        document.getElementById('uppgSelect').style.display = 'block';
        document.getElementById('collectionPointSelect').style.display = 'none';
        document.getElementById('wellSelect').style.display = 'none';
        getAllUppgConstants();
    } else if (constantOf === "COLLECTION_POINT") {
        document.getElementById('miningSelect').style.display = 'block';
        document.getElementById('uppgSelect').style.display = 'block';
        document.getElementById('collectionPointSelect').style.display = 'block';
        document.getElementById('wellSelect').style.display = 'none';
        getAllCollectionPointConstants();
    } else if (constantOf === "WELL") {
        document.getElementById('miningSelect').style.display = 'block';
        document.getElementById('uppgSelect').style.display = 'block';
        document.getElementById('collectionPointSelect').style.display = 'block';
        document.getElementById('wellSelect').style.display = 'block';
        getAllWellConstants();
    }
}

function selectHandleMining() {
    miningSystemId = document.getElementById('miningSelect').value;
    getAllMiningSystemConstants()
    getAllUppgs();
}

function selectHandleUppg() {
    uppgId = document.getElementById('uppgSelect').value;
    getAllUppgConstants()
    getAllCollectionPoints()
}

function selectHandleSP() {
    collectionPointId = document.getElementById('collectionPointSelect').value;
    document.getElementsByName("collectionPointId").value = collectionPointId;
    getAllCollectionPointConstants()
    getAllWells()
}

function selectHandleWell() {
    wellId = document.getElementById('wellSelect').value;
    getAllWellConstants()
}

function getAllMiningSystems() {
    axios.get("/api/mining_system/all")
        .then(function (response) {
            if (response.data.message === "OK") {
                document.getElementById("miningSelect").innerHTML = createViewMiningOrUppgOrSPSelect(response.data.object)
                document.getElementById("miningSelectForMolar").innerHTML = createViewMiningOrUppgOrSPSelect(response.data.object)
                miningSystemId = document.getElementById('miningSelect').value;
                getAllUppgs()
            }
        })
        .catch(function (error) {
            console.log(error)
        })
}

function getAllUppgs() {
    if (miningSystemId === "" || miningSystemId == null) {
        document.getElementById("uppgSelect").innerHTML = "<option value=''>Нет УППГ</option>"
        document.getElementById("collectionPointSelect").innerHTML = "<option value=''>Нет СП</option>"
        document.getElementById("wellSelect").innerHTML = "<option value=''>Нет Скважина</option>"
    } else {
        axios.get("/api/uppg/all/mining_system/" + miningSystemId)
            .then(function (response) {
                if (response.data.message === "OK") {
                    document.getElementById("uppgSelect").innerHTML = createViewMiningOrUppgOrSPSelect(response.data.object)
                    uppgId = document.getElementById('uppgSelect').value;
                    getAllCollectionPoints()
                }
            })
            .catch(function (error) {
                console.log(error)
            })
    }
}

function getAllCollectionPoints() {
    if (uppgId === "" || uppgId == null) {
        document.getElementById("uppgSelect").innerHTML = "<option value=''>Нет УППГ</option>"
        document.getElementById("collectionPointSelect").innerHTML = "<option value=''>Нет СП</option>"
        document.getElementById("wellSelect").innerHTML = "<option value=''>Нет Скважина</option>"

    } else {
        axios.get("/api/collection_point/all/uppg/" + uppgId)
            .then(function (response) {
                if (response.data.message === "OK") {
                    document.getElementById("collectionPointSelect").innerHTML = createViewMiningOrUppgOrSPSelect(response.data.object)
                    collectionPointId = document.getElementById('collectionPointSelect').value;
                    getAllWells()
                }
            })
            .catch(function (error) {
                console.log(error)
            })
    }
}

function getAllWells() {
    axios.get("/api/well/all/collection_point/" + collectionPointId)
        .then(function (response) {
            if (response.data.message === "OK") {
                document.getElementById("wellSelect").innerHTML = createViewWell(response.data.object)
                wellId = document.getElementById('wellSelect').value;
            }
        })
        .catch(function (error) {
            console.log(error)
        })
}

document.getElementById('addConstantBtn').addEventListener('click', addConstantBtn);

function addConstantBtn() {
    document.getElementById('addOrEditConstantH3').innerText = 'Добавить Константы'
    document.getElementById('addOrEditConstantBtn').innerText = 'Добавить'
    // let formField = document.getElementById('addOrEditConstantForm')
    // formField['id'].value = collectionPointId;
}

function resetAndCloseForm() {
    document.getElementById('closeFormBtn').click();
    document.getElementById('addOrEditConstantForm').reset();
}

function addOrEditConstant(event) {
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

        if (constantOf === "MINING_SYSTEM") {
            config.url = '/api/constant/mining/save/' + miningSystemId;
        }
        if (constantOf === "UPPG") {
            config.url = '/api/constant/uppg/save/' + uppgId;
        }
        if (constantOf === "COLLECTION_POINT") {
            config.url = '/api/constant/collection_point/save/' + collectionPointId;
        }
        if (constantOf === "WELL") {
            config.url = '/api/constant/well/save/' + wellId;
        }
    } else {
        config.method = 'put';
        if (constantOf === "MINING_SYSTEM") {
            config.url = '/api/constant/mining/edit/' + miningSystemId;
        }
        if (constantOf === "UPPG") {
            config.url = '/api/constant/uppg/edit/' + uppgId;
        }
        if (constantOf === "COLLECTION_POINT") {
            config.url = '/api/constant/collection_point/edit/' + collectionPointId;
        }
        if (constantOf === "WELL") {
            config.url = '/api/constant/well/edit/' + wellId;
        }
    }

    axios(config)
        .then(function () {
                if (constantOf === "MINING_SYSTEM") {
                    getAllMiningSystemConstants()
                }
                if (constantOf === "UPPG") {
                    getAllUppgConstants()
                }
                if (constantOf === "COLLECTION_POINT") {
                    getAllCollectionPointConstants();
                }
                if (constantOf === "WELL") {
                    getAllWellConstants();
                }
                resetAndCloseForm();
            }
        )
        .catch(function (error) {
            console.log(error.response);
        });
}

function editConstant(id) {
    document.getElementById('addOrEditConstantH3').innerText = 'Редактировать константы'
    document.getElementById('addOrEditConstantBtn').innerText = 'Редактировать'

    let editingConstant = constantsList.find(item => item.id == id);

    let formField = document.getElementById('addOrEditConstantForm')

    formField['id'].value = editingConstant.id;
    formField['name'].value = editingConstant.constant.name;
    formField['value'].value = editingConstant.value;
    formField['description'].value = editingConstant.constant.description;
}

function deleteConstant(id) {
    let config = {
        method: '',
        url: ''
    };
    config.method = 'delete';

    if (constantOf === "MINING_SYSTEM") {
        config.url = '/api/constant/mining/delete/' + id + '/' + 'MINING_SYSTEM';
    }
    if (constantOf === "UPPG") {
        config.url = '/api/constant/uppg/delete/' + id + '/' + 'UPPG';
    }
    if (constantOf === "COLLECTION_POINT") {
        config.url = '/api/constant/collection_point/delete/' + id + '/' + 'COLLECTION_POINT';
    }
    if (constantOf === "WELL") {
        config.url = '/api/constant/well/delete/' + id + '/' + 'WELL';
    }

    axios(config)
        .then(function () {
            if (constantOf === "MINING_SYSTEM") {
                getAllMiningSystemConstants()
            }
            if (constantOf === "UPPG") {
                getAllUppgConstants()
            }
            if (constantOf === "COLLECTION_POINT") {
                getAllCollectionPointConstants()
            }
            if (constantOf === "WELL") {
                getAllWellConstants()
            }
        })
        .catch(function (error) {
            console.log(error.response.data);
        });
}

function createViewTable(constants) {
    let out = "";
    constants.map(item => {

        out += "<tr class=\"constant_table_row\">\n" +
            "   <td class=\"sorting_1\">" + item.id + "</td>\n" +
            "    <td>" + item.constant.name + "</td>\n" +
            "     <td >" + item.value + "</td>\n" +
            "     <td >" + item.constant.description + "</td>\n" +
            "<td><button data-target=\"#exampleModalCenter\" data-toggle=\"modal\" class='btn btn-success mt-1' id='btn-edit-constant' value='" + item.id + "' onclick='editConstant(this.value)'>Редактировать</button>\n" +
            "<button class='btn btn-danger ml-2 mt-1' id='btn-edit-constant' value='" + item.id + "' onclick='deleteConstant(this.value)'>Удалить</button></td>\n" +
            "   </tr>"
    })
    return out;
}

function createViewMiningOrUppgOrSPSelect(miningsOrUppgsOrSP) {
    let out = "";
    miningsOrUppgsOrSP.map(item => {
        out += "<option value='" + item.id + "'>" + item.name + "</option>"
    })
    return out;
}

function createViewWell(wells) {
    let out = "";
    wells.map(item => {
        out += "<option value='" + item.id + "'>" + item.number + "</option>"
    })
    return out;
}