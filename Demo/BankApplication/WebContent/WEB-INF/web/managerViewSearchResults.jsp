<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>Manager - View Search Results</title>
<script type="text/javascript">
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
    }
</script>

</head>


<body>
<div>
<label id="searchResultsLabel">Search Results</label>
<br/>
	<table id="SearchResultsTable" style="width: 86%">
		<thead>
			<tr>
				<th style="width: 120px">Name</th>
				<th style="width: 90px">AccountNumber</th>	
				<th colspan="2">Decision</th>			
			</tr>
		</thead>
		<tbody>
		<tr>
			<td style="width: 120px;"></td>
			<td style="width: 90px;"></td>
			<td style="width: 75px">
				<sf:form action="${pageContext.request.contextPath}/viewSearchResults" method="POST" commandName="">
					<input name="accountID" type="hidden" value="${user.accountID}"/>
					<input name="ViewButton1" type="submit" value="View" style="width: 75px" />
				</sf:form>
			</td>
			<td style="width: 75px">
				<sf:form action="${pageContext.request.contextPath}/updateUserDetails" method="POST" commandName="">
					<input name="accountID" type="hidden" value="${user.accountID}"/>
					<input name="UpdateButton" type="submit" value="Update" style="width: 75px" />
				</sf:form>
			</td>
		</tr>
	</tbody>
	</table>
	<input onclick="addRow('SearchResultsTable')" type="button" value="Add Row">
</div>

</body>


</html>