$(document).ready(function() {
	$(document).on("contextmenu",function(){
	       return false;
	}); 

    function disableBack() { window.history.forward() }
	
    window.onload = disableBack();
    window.onpageshow = function(evt) { if (evt.persisted) disableBack() }
    
    $(document).keydown(function (e) {
        return (e.which || e.keyCode) != 116;
    });
});

function check_field(id) {
	var field = document.getElementById(id);
	if (isNaN(field.value)) {
		 document.getElementById('errors').innerHTML="***Please enter only Integer****";
		 return false;
	} else {
		document.searchUser.action = "SearchUserforPIIByAccountNumber.do";
		return true;
	}
}

function format(inputDate) {
    var date = new Date(inputDate);
    if (!isNaN(date.getTime())) {
        // Months use 0 index.
        return date.getMonth() + 1 + '/' + date.getDate() + '/' + date.getFullYear();
    }
}

function getJSDate(inputDate) {
	try {
		var dateParts = inputDate.split(" ");
		var dayParts = dateParts[0].split("-");
		var date = new Date(dayParts[0], dayParts[1], dayParts[2], 0, 0, 0, 0);
		if(isNaN(date.getTime())) {
			return new Date();
		} else {
			return date;
		}
	} catch (e) {
		return new Date();
	}	
}

