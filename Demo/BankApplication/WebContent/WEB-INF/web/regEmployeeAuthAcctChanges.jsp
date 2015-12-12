<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>Regular Employee - Authorize External User Account Changes</title>
<script>
        function addRow(tableID) {
 
            var table = document.getElementById(tableID);
            var rowCount = table.rows.length;
            var row = table.insertRow(rowCount);
            var colCount = table.rows[1].cells.length;
 
            for(var i=0; i<colCount; i++) {
                var newcell = row.insertCell(i);
                newcell.innerHTML = table.rows[1].cells[i].innerHTML;
                if(newcell.childNodes[0].type == "label") {
                	newcell.childNodes[0].value = "22";
                }
            }
            
            <!-- //#TODO : Add logic to populate data in iterative manner -->
        }
    </script>

</head>

<body>
==*== ACCEPT/DENY PROFILE CHANGE REQUEST ==*==
<br/>
<br/>
<input onclick="addRow('dataTable')" type="button" value="Add Row">
<table id="dataTable" border="1" style="width: 485px">
	<thead>
		<tr>
			<th style="width: 200px"><label id="FromNameHead">From</label></th>
			<th style="width: 150px"><label id="AccountHead">Account</label></th>
		</tr>
	</thead>
	<tr><sf:form action="" method="post">
		<td style="width: 200px"><label id="FromName1"></label></td>
		<td style="width: 150px"><label id="Accnum1"></label></td>
		<td><button name="ViewButton" type="submit" value="View" style="width: 115px">View</button></td>
		</sf:form>
	</tr>
</table>

</body>

</html>
