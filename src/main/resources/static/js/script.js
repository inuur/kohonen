$(document).ready(function () {

    let map = document.getElementById("map");

    function updateMap() {
        $.get("http://localhost:8080/map", function (data) {
            for (let i = 0; i < Math.sqrt(data.length); i++) {
                for (let j = 0; j < Math.sqrt(data.length); j++) {
                    let rgb = 'rgb(' + Math.floor(data[i * Math.sqrt(data.length) + j].w[0] * 10) + ',' + Math.floor(data[i * Math.sqrt(data.length) + j].w[1] * 10) + ',' + Math.floor(data[i * Math.sqrt(data.length) + j].w[2] * 10) + ")"
                    map.rows[i].cells[j].style.backgroundColor = rgb;
                    map.rows[i].cells[j].onmouseenter = function () {
                        $("#data").text(data[i * Math.sqrt(data.length) + j].w)
                    }
                }
            }
        });
        $.get("http://localhost:8080/map/iteration", function (data) {
            $("#iter-counter").text("Iteration " + data);
        });
    }

    for (let i = 0; i < 25; i++) {
        let row = map.insertRow(i);
        row.style.height = "20px";
        for (let j = 0; j < 25; j++) {
            let newCell = row.insertCell(j);
            newCell.style.width = "40px";
            newCell.style.height = "40px";
            newCell.style.backgroundColor = "pink";
            newCell.style.padding = "0";
            newCell.appendChild(document.createTextNode(""));
        }
    }

    let t = setInterval(updateMap, 500);
});