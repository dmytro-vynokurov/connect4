
function postToServer(url, callback) {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", url, true);
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            var responseObject = JSON.parse(xmlhttp.responseText);
            if (this.status == 200) {
                callback(responseObject);
            } else {
                alert(responseObject.message);
            }
        }
    };
    xmlhttp.send();
}

function getLowestFreeCellInAColumn(columnId) {
    var column = game.grid.columns[columnId];

    for (var i = 0; i < 7; i++) {
        if (!column.cells[i].owner)return i;
    }
    alert("shit happened: lowest cell cannot be found for column " + columnId);
}

function changeColor(rowId, columnId, color) {
    var selector = "div.c4-rown" + rowId + " div.c4-coln" + columnId + " div.c4-circle";
    var circleElement = document.querySelector(selector);
    circleElement.className = "c4-circle c4-" + color;
}

function drawMove(color, move, callback) {
    var lowestFreeCellInAColumn = move.row;
    var currentRow = 6;
    var interval = setInterval(function () {
        if (currentRow != 6) changeColor(currentRow, move.column, "white");
        currentRow--;
        changeColor(currentRow, move.column, color);
        if (currentRow == lowestFreeCellInAColumn) {
            clearInterval(interval);
            if(callback) callback();
        }
        if (currentRow < 0) {
            clearInterval(interval);
            console.warn("Shit happened - current row < 0");
        }
    }, 150);
}

function userAction(columnId) {
    if (!expectInput) return;
    expectInput = false;
    postToServer("/api/game/" + game.id + "/" + columnId, function (response) {
        game = response;
        if(game.gameStatus == "IN_PROGRESS"){
            drawMove("red", game.firstPlayerLastMove, function(){
                drawMove("yellow", game.secondPlayerLastMove);
                expectInput = true;
            });
        }else{
            if(game.gameStatus=="FIRST_PLAYER_WON") alert("You won! Press OK to start the new game");
            else alert("You lost! Press OK to start the new game");

            window.location = "/";
        }
    });
}

var game;
var expectInput = false;

postToServer("/api/game/initialize", function (response) {
    game = response;
    expectInput = true;
});
