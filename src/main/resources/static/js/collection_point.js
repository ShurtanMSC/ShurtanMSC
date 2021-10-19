let collectionPointsList = []

function getAllCollectionPoints() {
    axios.get("/api/collection_point/all/uppg/" + 1)
        .then(function (response) {
            if (response.data.message === "OK") {
                collectionPointsList = response.data.object
            }
            document.getElementById("collectionPointTable").innerHTML = createViewTable(response.data.object)
        })
        .catch(function (error) {
            console.log(error)
        })
}

document.getElementById('addCollectionPointBtn').addEventListener('click', addCollectionPointBtn);

function addCollectionPointBtn() {
    document.getElementById('addOrEditCollectionPointH3').innerText = 'Добавить Сборный пункт'
    document.getElementById('addOrEditCollectionPointBtn').innerText = 'Добавить'
}

function resetAndCloseForm() {
    document.getElementById('closeFormBtn').click();
    document.getElementById('addOrEditCollectionPointForm').reset();
}

function addOrEditCollectionPoint(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    const data = {}
    formData.forEach((value, key) => (data[key] = value));
    console.log(data)
    let config = {
        method: '',
        url: '',
        data
    };

    if (data.id === "" || data.id == null) {

        config.method = 'post';
        config.url = '/api/collection_point/add'
    } else {

        config.method = 'put';
        config.url = '/api/collection_point/edit'
    }

    axios(config)
        .then(function (response) {
            getAllCollectionPoints();
            resetAndCloseForm();
            // console.log(response.data);
        })
        .catch(function (error) {
            console.log(error.response.data);
        });
}

function editCollectionPoint(id) {
    document.getElementById('addOrEditCollectionPointH3').innerText = 'Редактировать Сборный пункт'
    document.getElementById('addOrEditCollectionPointBtn').innerText = 'Редактировать'
    let editCollectionPoint = collectionPointsList.find(collectionPoint => collectionPoint.id == id)
    let formField = document.getElementById('addOrEditCollectionPointForm')

    formField['id'].value = editCollectionPoint.id;
    formField['name'].value = editCollectionPoint.name;
    formField['miningSystemId'].value = editCollectionPoint.miningSystemId;
}

function deleteCollectionPoint(id) {
    axios.delete("/api/collection_point/delete/" + id)
        .then(function (response) {
            // console.log(response.data)
            getAllCollectionPoints()
        })
        .catch(function (error) {
            console.log(error.response.data)
        })
}

function createViewTable(collectionPoints) {
    let out = "";
    collectionPoints.map(collectionPoint => {
        out += "<tr class=\"collectionPoint_table_row\">\n" +
            "   <td class=\"sorting_1\">" + collectionPoint.id + "</td>\n" +
            "    <td>" + collectionPoint.name + "</td>\n" +
            "     <td hidden value='" + collectionPoint.uppgId + "'>" + collectionPoint.uppgId + "</td>\n" +
            "     <td><button data-target=\"#exampleModalCenter\" data-toggle=\"modal\" class='btn btn-success mt-1' id='btn-edit-collectionPoint' value='" + collectionPoint.id + "' onclick='editCollectionPoint(this.value)'>Редактировать</button>\n" +
            "      <button class='btn btn-danger ml-2 mt-1' id='btn-edit-collectionPoint' value='" + collectionPoint.id + "' onclick='deleteCollectionPoint(this.value)'>Удалить</button></td>\n" +
            "   </tr>"
    })
    return out;
}
