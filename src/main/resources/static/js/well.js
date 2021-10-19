let wellsList = []

function getAllWells() {
    axios.get("/api/well/all/collection_point/" + 1)
        .then(function (response) {
            if (response.data.message === "OK") {
                wellsList = response.data.object
            }
            document.getElementById("wellTable").innerHTML = createViewTable(response.data.object)
        })
        .catch(function (error) {
            console.log(error)
        })
}

document.getElementById('addWellBtn').addEventListener('click', addWellBtn);

function addWellBtn() {
    document.getElementById('addOrEditWellH3').innerText = 'Добавить cкважина'
    document.getElementById('addOrEditWellBtn').innerText = 'Добавить'
}

function resetAndCloseForm() {
    document.getElementById('closeFormBtn').click();
    document.getElementById('addOrEditWellForm').reset();
}

function addOrEditWell(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    const data = {}
    formData.forEach((value, key) => (data[key] = value));

    let config = {
        method: '',
        url: '',
        data
    };

    if (data.id === "" || data.id == null) {
        config.method = 'post';
        config.url = '/api/well/add'
    } else {
        config.method = 'put';
        config.url = '/api/well/edit'
    }

    axios(config)
        .then(function () {
            getAllWells();
            resetAndCloseForm();
        })
        .catch(function (error) {
            console.log(error);
        });
}

function editWell(id) {
    document.getElementById('addOrEditWellH3').innerText = 'Редактировать cкважина'
    document.getElementById('addOrEditWellBtn').innerText = 'Редактировать'
    let editWell = wellsList.find(well => well.id == id)
    let formField = document.getElementById('addOrEditWellForm')

    formField['id'].value = editWell.id;
    formField['number'].value = editWell.number;
    formField['collectionPointId'].value = editWell.collectionPointId;
    formField['commissioningDate'].value = editWell.commissioningDate;
    formField['drillingStartDate'].value = editWell.drillingStartDate;
    formField['horizon'].value = editWell.horizon;
    formField['altitude'].value = editWell.altitude;
    formField['depth'].value = editWell.depth;
    formField['x'].value = editWell.x;
    formField['y'].value = editWell.y;
    formField['c'].value = editWell.c;
    formField['category'].value = editWell.category;
}

function deleteWell(id) {
    axios.delete("/api/well/delete/" + id)
        .then(function (response) {
            // console.log(response.data)
            getAllWells()
        })
        .catch(function (error) {
            console.log(error.response.data)
        })
}

function createViewTable(wells) {
    let out = "";
    wells.map(well => {

        const commissioningDate = new Date(''+well.commissioningDate+'');
        const commissioningDayOfMonth = commissioningDate.getDate();
        const commissioningMonth = commissioningDate.getMonth(); // Be careful! January is 0, not 1
        const commissioningYear = commissioningDate.getFullYear();
        const commissioningDateString = commissioningDayOfMonth + "-" + (commissioningMonth + 1) + "-" + commissioningYear;

        const drillingStartDate = new Date(''+well.drillingStartDate+'');
        const drillingStartDayOfMonth = commissioningDate.getDate();
        const drillingStartMonth = commissioningDate.getMonth(); // Be careful! January is 0, not 1
        const drillingStartYear = commissioningDate.getFullYear();
        const drillingStartDateString = drillingStartDayOfMonth + "-" + (drillingStartMonth + 1) + "-" + drillingStartYear;

        console.log(commissioningDate.getDate())
        out += "<tr class=\"well_table_row\">\n" +
            "   <td class=\"sorting_1\">" + well.id + "</td>\n" +
            "    <td>" + well.number + "</td>\n" +
            "     <td hidden value='" + well.collectionPointId + "'>" + well.collectionPointId + "</td>\n" +
            "     <td >" + commissioningDateString + "</td>\n" +
            "     <td >" + drillingStartDateString + "</td>\n" +
            "     <td >" + well.horizon + "</td>\n" +
            "     <td >" + well.altitude + "</td>\n" +
            "     <td >" + well.depth + "</td>\n" +
            "     <td >" + well.x + "</td>\n" +
            "     <td >" + well.y + "</td>\n" +
            "     <td >" + well.c + "</td>\n" +
            "     <td >" + well.category + "</td>\n" +
            "     <td><button data-target=\"#exampleModalCenter\" data-toggle=\"modal\" class='btn btn-success mt-1' id='btn-edit-well' value='" + well.id + "' onclick='editWell(this.value)'>Редактировать</button>\n" +
            "      <button class='btn btn-danger ml-2 mt-1' id='btn-edit-well' value='" + well.id + "' onclick='deleteWell(this.value)'>Удалить</button></td>\n" +
            "   </tr>"
    })
    return out;
}
