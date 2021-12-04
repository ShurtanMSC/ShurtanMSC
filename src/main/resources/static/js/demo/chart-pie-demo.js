// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

// Pie Chart Example
// var ctx = document.getElementById("myPieChart");
// var myPieChart = new Chart(ctx, {
//   type: 'doughnut',
//   data: {
//     labels: ["Direct", "Referral", "Social"],
//     datasets: [{
//       data: [55, 30, 15],
//       backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc'],
//       hoverBackgroundColor: ['#2e59d9', '#17a673', '#2c9faf'],
//       hoverBorderColor: "rgba(234, 236, 244, 1)",
//     }],
//   },
//   options: {
//     maintainAspectRatio: false,
//     tooltips: {
//       backgroundColor: "rgb(255,255,255)",
//       bodyFontColor: "#858796",
//       borderColor: '#dddfeb',
//       borderWidth: 1,
//       xPadding: 15,
//       yPadding: 15,
//       displayColors: false,
//       caretPadding: 10,
//     },
//     legend: {
//       display: false
//     },
//     cutoutPercentage: 80,
//   },
// });

function wellStat() {
  axios.get("/api/well/stat/status")
      .then(function (response) {

        let stat=response.data.object;



        var ctx = document.getElementById("myPieChart");
        var myPieChart = new Chart(ctx, {
          type: 'doughnut',
          data: {
            labels: ["в работе", "в простое", "в ремонте","в консервации","в ликвидации"],
            datasets: [{
              data: [stat[0].IN_WORK, stat[0].IN_IDLE, stat[0].IN_REPAIR, stat[0].IN_CONSERVATION, stat[0].IN_LIQUIDATION],
              backgroundColor: ['#3ca152', 'yellow', '#cc3648','#731a80','#000000'],
              hoverBackgroundColor: ['#286e36', '#b0ac1b', '#8d1f2d','#470e52','#000000'],
              hoverBorderColor: "rgba(234, 236, 244, 1)",
            }],
          },
          options: {
            maintainAspectRatio: false,
            tooltips: {
              backgroundColor: "rgb(255,255,255)",
              bodyFontColor: "#858796",
              borderColor: '#dddfeb',
              borderWidth: 1,
              xPadding: 15,
              yPadding: 15,
              displayColors: false,
              caretPadding: 10,
            },
            legend: {
              display: false
            },
            cutoutPercentage: 80,
          },
        });



        let out="";

        let percent=Math.floor(100*(stat[0].IN_WORK)/(stat[0].IN_WORK+stat[0].IN_IDLE+stat[0].IN_REPAIR+stat[0].IN_CONSERVATION+stat[0].IN_LIQUIDATION))
        out+= "<div class=\"col-auto\">\n" +
            "                                                    <div class=\"h5 mb-0 mr-3 font-weight-bold text-gray-800\">"+percent+"%</div>\n" +
            "                                                </div>\n" +
            "                                                <div class=\"col\">\n" +
            "                                                    <div class=\"progress progress-sm mr-2\">\n" +
            "                                                        <div class=\"progress-bar bg-info\" role=\"progressbar\"\n" +
            "                                                            style='width: "+percent+"%' aria-valuenow='"+percent+"' aria-valuemin=\"0\"\n" +
            "                                                            aria-valuemax=\"100\">\n" +
            "                                                        </div>\n" +
            "                                                    </div>\n" +
            "                                                </div>"
        document.getElementById("percent_well").innerHTML=out;
      })
}
wellStat()
