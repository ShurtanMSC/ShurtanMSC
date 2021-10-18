function getAllMiningSystem() {
    axios.get("/api/mining_system/all/actions")
        .then(function (response) {
            console.log(response)
            console.log(response.data)
            console.log(response.data.object)
            document.getElementById("miningTable").innerHTML=createViewTable(response.data.object)
        })
        .catch(function (error) {
            console.log(error)
        })
}



function createViewTable(miningSystems) {
    let out="";
    miningSystems.map(mining=>{
        console.log(mining)
        out+="<tr class=\"user_table_row\">\n" +
            "                                    <td class=\"sorting_1\">"+mining.objectDto.id+"</td>\n" +
            "                                    <td>"+mining.objectDto.name+"</td>\n" +
            "                                    <td>"+(mining.objectActionDto?mining.objectActionDto.expend:"")+"</td>\n" +
            "                                    <td><button class='btn btn-success' id='btn-edit-user'>Редактировать</button>" +
            "<a class='btn btn-info ml-2' id='btn-edit-user'>Действие</a>" +
            "<button class='btn btn-danger ml-2' id='btn-edit-user'>Удалить</button>" +
            "</td>\n" +
            "                                </tr>"
    })
    return out;
}