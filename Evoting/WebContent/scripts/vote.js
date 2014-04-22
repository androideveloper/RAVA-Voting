$(document).ready(function() {
	var selection = document.getElementById("form:mode:0");
	if (selection.checked) {
		document.getElementById("form:selectGroup").style.display = "none";
	} else {
		document.getElementById("form:selectGroup").style.display = "";
	}
});

function modeChanged(selection) {
	if (selection == "all") {
		document.getElementById("form:selectGroup").style.display = "none";
	} else {
		document.getElementById("form:selectGroup").style.display = "";
	}
}