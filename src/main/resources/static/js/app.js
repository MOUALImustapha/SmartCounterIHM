
$(document).ready(function () {
 	
    var MONTHS = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    var color = Chart.helpers.color;
    
    let $canvas = $('#semaine-consomation-chart');
    let ctx = $canvas[0].getContext('2d');
    let url = `http://localhost:9080/api/mesures/search/findTop7ByCompteur_IdOrderByIdDesc?id=${window.location.pathname.split('/')[2]}`;
    $.getJSON(url,(data) => {
        console.log(data);
        var barChartData = {
            labels: data._embedded.mesures.map(e => e.dayName),
            datasets: [{
                label: "Consomation d'éléctricité",
                backgroundColor: color("rgb(54, 162, 235)").alpha(0.5).rgbString(),
                borderColor: "rgb(54, 162, 235)",
                borderWidth: 1,
                data: data._embedded.mesures.map(e => e.valeur.toFixed(2))
            }]
    
        };
        window.myBar = new Chart(ctx, {
            type: 'bar',
            data: barChartData,
            options: {
                responsive: true,
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Week Data'
                }
            }
        });
    });
    $('select[name="dob-month"],select[name="bar-type"],select[name="dob-year"]').on('change',() => {
        let year = $('select[name="dob-year"]').val() == '' ? 2021 : $('select[name="dob-year"]').val();
        let month = $('select[name="dob-month"]').val() == '' ? 1 : $('select[name="dob-month"]').val();
        console.log("change")
        getDataByMonthAndYear(year,month, $('select[name="bar-type"]').val(),color);
    })
    $('#nav-profile-tab').on('click', () => {
        let year = 2021, month = 1;
        getDataByMonthAndYear(2021,1,'day',color);
    })
    
});
function getTr(trancheID,totale, coeff){
    return  `
    <tr>
        <td scope="row">Tranche ${trancheID} </td>
        <td>${totale}</td>
        <td>${(totale * coeff).toFixed(2)}</td>
    </tr>
    `
}
function setConsomationTable($,mesurs){
    let rows = ``;
    let totale = mesurs.reduce((a, b) => a + b, 0);
    console.log(totale);
    if(totale > 1 && totale <= 150 ){
        if(totale <= 100){
            rows = getTr(1,100, 0.8496)
        }else{
            rows = getTr(1,100, 0.8496)
            rows += getTr(2,totale - 100, 1.0220);
            rows += `
            <tr>
                <td scope="row"> </td>
                <td colspan='2'>${(100 * 0.8496).toFixed(2) + (totale - 100 * 1.0220).toFixed(2)}</td>
            </tr>
            `
        }
    }else if(totale > 150 && totale <= 210 ){
        rows = getTr(3,totale,1.0220)
    }else if(totale > 210 && totale <= 350 ){
        rows = getTr(4,totale,1.1119)
    }else if(totale > 350 && totale <= 510 ){
        rows = getTr(5,totale,1.3157)
    }else{
        rows = getTr(6,totale,1.5193)
    }
    let table = `
        <table class="table">
            <thead>
                <tr>
                    <th>Facture</th>
                
                </tr>
                <tr>
                    <th scope="row">Tranche</td>
                    <th>Consommation</td>
                    <th>Prix</td>
                </tr>
            </thead>
            <tbody>
                ${rows}
            </tbody>
        </table>
    `
    $('#facture-a').append(table);
}
function getDataByMonthAndYear(year,month,barType,color){
    let url = `http://localhost:9080/api/mesures/search/getByYearAndMonth?year=${year}&month=${month}&id=1`;
        let $canvas = $('#mensuelle-consomation-chart');
        let ctx = $canvas[0].getContext('2d');
        $.getJSON(url,(data) => {
            console.log(data);
            var barChartData = {
                labels: barType == 'day' ? data._embedded.mesures.map(e => e.dayName) : ['Week 1','Week 2','Week 3','Week 4'],
                datasets: [{
                    label: "Consomation d'éléctricité",
                    backgroundColor: color("rgb(54, 162, 235)").alpha(0.5).rgbString(),
                    borderColor: "rgb(54, 162, 235)",
                    borderWidth: 1,
                    data: barType == 'day' ? data._embedded.mesures.map(e => e.valeur.toFixed(2)) : [700,350,210,12]
                }]
        
            };
            setConsomationTable($,data._embedded.mesures.map(e => parseFloat(e.valeur.toFixed(2)) ) )
            window.myBar = new Chart(ctx, {
                type: 'bar',
                data: barChartData,
                options: {
                    responsive: true,
                    legend: {
                        position: 'top',
                    },
                    title: {
                        display: true,
                        text: 'Month Data'
                    }
                }
            });
        });
        
}