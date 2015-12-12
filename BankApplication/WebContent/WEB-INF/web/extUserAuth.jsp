<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Add dynamic rows in HTML table for Approval of External User</title>
<script>
        function addRow(tableID) {
 
            var table = document.getElementById(tableID);
            var rowCount = table.rows.length;
            var row = table.insertRow(rowCount);
            var colCount = table.rows[0].cells.length;
 
            for(var i=0; i<colCount; i++) {
                var newcell = row.insertCell(i);
                newcell.innerHTML = table.rows[0].cells[i].innerHTML;
                if(newcell.childNodes[0].type == "label") {
                	newcell.childNodes[0].value = "";
                }
            }
            
            <!-- //#TODO : Add logic to populate data in iterative manner -->
        }
    </script>
</head>

<body>

<input onclick="addRow('dataTable')" type="button" value="Add Row">
<table id="dataTable" border="1" width="750px">
	<tr>
		<sf:form action="" method="post">
		<td style="width: 509px;"><label id="Label1"></label></td>
		<td style="width: 120px;"><input name="Accept" style="width: 120px" type="submit" value="Accept"></td>
		<td><input name="Deny" style="width: 120px" type="submit" value="Deny"></td>
		</sf:form>
	</tr>
</table>
</body>
</html>
