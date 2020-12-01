$(document).ready(function () {

    let map = document.getElementById("map");

    function updateMap() {
        $.get("http://localhost:8080/map", function (data) {
            for (let i = 0; i < Math.sqrt(data.length); i++) {
                for (let j = 0; j < Math.sqrt(data.length); j++) {
                    let rgb = 'rgb(' + Math.floor(data[i * Math.sqrt(data.length) + j].weights[0] * 10) + ',' + Math.floor(data[i * Math.sqrt(data.length) + j].weights[1] * 10) + ',' + Math.floor(data[i * Math.sqrt(data.length) + j].weights[2] * 10) + ")"
                    map.rows[i].cells[j].style.backgroundColor = rgb;
                    map.rows[i].cells[j].textContent = data[i * Math.sqrt(data.length) + j].numOfSamples;
                    map.rows[i].cells[j].onmouseenter = function () {
                        mapNode = data[i * Math.sqrt(data.length) + j];
                        $("#weights").text(mapNode.weights)
                        $("#k1").find(".value").text(mapNode.kmeansCounter[1])
                        $("#k2").find(".value").text(mapNode.kmeansCounter[2])
                        $("#k3").find(".value").text(mapNode.kmeansCounter[3])
                        $("#k4").find(".value").text(mapNode.kmeansCounter[4])
                        $("#k5").find(".value").text(mapNode.kmeansCounter[5])

                        $("#k1").find(".percent").text(getPercent(mapNode.kmeansCounter, 1).toFixed(2))
                        $("#k2").find(".percent").text(getPercent(mapNode.kmeansCounter, 2).toFixed(2))
                        $("#k3").find(".percent").text(getPercent(mapNode.kmeansCounter, 3).toFixed(2))
                        $("#k4").find(".percent").text(getPercent(mapNode.kmeansCounter, 4).toFixed(2))
                        $("#k5").find(".percent").text(getPercent(mapNode.kmeansCounter, 5).toFixed(2))

                        $("#global_percent").text("Total: " + (getSum(mapNode.kmeansCounter) / 82000 * 100).toFixed(2) + "%")
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

function getPercent(array, index) {
    let sum = getSum(array);
    if (sum === 0) {
        return sum;
    }
    return array[index] / sum * 100;
}

function getSum(array) {
    let sum = 0;
    for (let i = 0; i <array.length; i++) {
        sum += array[i];
    }
    return sum;
}