<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>Manager - Authorize Transfers</title>
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

<!-- //#TODO : Populate as per this -> http://stackoverflow.com/questions/19090153/dynamic-database-table-display-using-jstl -->

<body>
==*== MANAGER ACCEPT/DENY PROFILE CHANGE REQUEST ==*==
<br/>
<br/>
<input onclick="addRow('dataTable')" type="button" value="Add Row">
<sf:form action="${pageContext.request.contextPath}/managerViewUserDetails" method="post" commandName="">
<table id="dataTable" border="1" style="width: 550px">
	<thead>
		<tr>
			<th style="width: 200px"> <label id="OldDetails">Old Details</label></th>
			<th style="width: 200px"> <label id="NewDetails">New Details</label></th>
		</tr>
	</thead>
	<c:forEach items="${username}" var="name">
	   <tr>
	       <td>${user.firstName}</td>
	       <td>${user.middleName}</td>
	       <td>${user.lastName}</td>
	    </tr>
	</c:forEach>
</table>
<button name="ButtonUpdate" type="submit" value="Update" style="width: 115px" title="This will update the records">Update</button>
<button name="ButtonReject" type="submit" value="Reject" style="width: 115px" title="This will Reject and delete the request">Reject</button>
</sf:form>


</body>

</html>
