// import axios from "axios";

function getAllUsers() {
    axios.get("/api/admin/user/all")
        .then(function (response) {
            console.log(response)
            console.log(response.data)
            document.getElementById("userTable").innerHTML=createViewTable(response.data.object)
        })
        .catch(function (error) {
            console.log(error)
        })
}

// function addUser(e) {
//     e.preventDefault()
//     const form = document.getElementById('addUser');
//     const name = form.elements['username'];
//     const email = form.elements['email'];
//
// // getting the element's value
//     let fullName = name.value;
//     let emailAddress = email.value;
//     console.log("e.target.valuee.target.value")
//     console.log(fullName+" "+ emailAddress)
// }
//
// function readFormData(){
//     var formDat={}
// }

function getAllRoles() {
    axios.get("/api/admin/role/all")
        .then(function (response) {
            console.log(response)
            console.log(response.data)
        })
        .catch(function (error) {
            console.log(error)
        })
}


function createViewTable(users) {
    let out="";
    users.map(user=>{
        out+="<tr class=\"user_table_row\">\n" +
            "                                    <td class=\"sorting_1\">"+user.id+"</td>\n" +
            "                                    <td>"+user.fio+"</td>\n" +
            "                                    <td>"+user.username+"</td>\n" +
            "                                    <td>"+user.roleName+"</td>\n" +
            "                                    <td>"+user.phone+"</td>\n" +
            "                                    <td>"+user.email+"</td>\n" +
            "                                    <td><button class='btn btn-success' id='btn-edit-user'>Редактировать</button></td>\n" +
            "                                </tr>"
    })
    return out;
}