function getAllFlowMeters() {
    axios.get("/api/fake/all")
        .then(function (response) {
                // document.getElementById("miningSelect").innerHTML = createViewTable(response.data.object)
            if (response.status==200){
                createViewTable(response.data)
            }
        })
        .catch(function (error) {
            console.log("error.response")
            console.log(error)
        })
}

setInterval(getAllFlowMeters, 1000);


function createViewTable(data) {
        console.log(data)

        let out1="";
        let out2="";
        let out1foot="  <tr>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">Itogo</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[0].nakoplenniy_obyom*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[0].nakoplenniy_obyom_s_nachalo_sutok*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[0].nakoplenniy_obyom_za_vchera*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[0].nakoplenniy_obyom_s_nachalo_mesyach*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[0].nakoplenniy_obyom_za_pered_mesyach*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[0].perepad_davleniya*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[0].davleniya*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[0].temperatura*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ 0 +"</th>\n" +
            "\n" +
            "<!--                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">Редактировать</th>-->\n" +
            "                                            </tr>";

        let out2foot="  <tr>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">Itogo</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[1].nakoplenniy_obyom*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[1].nakoplenniy_obyom_s_nachalo_sutok*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[1].nakoplenniy_obyom_za_vchera*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[1].nakoplenniy_obyom_s_nachalo_mesyach*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[1].nakoplenniy_obyom_za_pered_mesyach*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[1].perepad_davleniya*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[1].davleniya*100)/100+"</th>\n" +
            "                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">"+ Math.round(data[1].temperatura*100)/100+"</th>\n" +
            "\n" +
            "<!--                                                <th class=\"uppgs-table-title\" rowspan=\"1\" colspan=\"1\">Редактировать</th>-->\n" +
            "                                            </tr>";



        //
        for (let i = 0; i <data[0].flowMeters.length ; i++) {
            let arr=data[0].flowMeters[i].fakeFlowMeterElements[0].fullName.split(":")
            console.log(arr)
            console.log(arr[0].length)
            console.log(arr[0].length)
            out1 += "<tr class=\"odd\">\n" +
                "   <td class=\"\">" +arr[0].slice(25,arr[0].length)+ "</td>\n" +
                "    <td>" + Math.round(data[0].flowMeters[i].fakeFlowMeterElements[0].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[0].flowMeters[i].fakeFlowMeterElements[1].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[0].flowMeters[i].fakeFlowMeterElements[2].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[0].flowMeters[i].fakeFlowMeterElements[3].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[0].flowMeters[i].fakeFlowMeterElements[4].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[0].flowMeters[i].fakeFlowMeterElements[5].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[0].flowMeters[i].fakeFlowMeterElements[6].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[0].flowMeters[i].fakeFlowMeterElements[7].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[0].flowMeters[i].fakeFlowMeterElements[8].v*100)/100 + "</td>\n" +


                "   </tr>"
        }

        for (let i = 0; i <data[1].flowMeters.length ; i++) {
            let arr=data[1].flowMeters[i].fakeFlowMeterElements[0].fullName.split(":")
            out2 += "<tr class=\"odd\">\n" +
                "   <td class=\"\">"+arr[0].slice(25,arr[0].length)+ "</td>\n" +
                "    <td>" + Math.round(data[1].flowMeters[i].fakeFlowMeterElements[0].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[1].flowMeters[i].fakeFlowMeterElements[1].v*100)/100 + "</td>\n" +
                // "    <td>" + Math.round(data[1].flowMeters[i].fakeFlowMeterElements[5].v*100)/100 + "</td>\n" +
                // "    <td>" + Math.round(data[1].flowMeters[i].fakeFlowMeterElements[6].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[1].flowMeters[i].fakeFlowMeterElements[7].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[1].flowMeters[i].fakeFlowMeterElements[8].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[1].flowMeters[i].fakeFlowMeterElements[9].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[1].flowMeters[i].fakeFlowMeterElements[2].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[1].flowMeters[i].fakeFlowMeterElements[3].v*100)/100 + "</td>\n" +
                "    <td>" + Math.round(data[1].flowMeters[i].fakeFlowMeterElements[4].v*100)/100 + "</td>\n" +

                "   </tr>"
        }
        document.getElementById("uppgTable1").innerHTML=out1;
        document.getElementById("foot1").innerHTML=out1foot;
        document.getElementById("uppgTable2").innerHTML=out2;
    document.getElementById("foot2").innerHTML=out2foot;

    // }


}