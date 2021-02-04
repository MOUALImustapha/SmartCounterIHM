/**
 * 
 */

$(document).ready(function () {
    let factureElement = $('#facture')[0];
    html2pdf().set({
        filename: document.querySelector("#idFacture").textContent
    }).from(factureElement).save();
});