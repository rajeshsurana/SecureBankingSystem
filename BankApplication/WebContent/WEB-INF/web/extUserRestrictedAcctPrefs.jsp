<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User - Update Critical Information</title>
</head>
<body>
	<div class="auto-style6">
		<br /> <span class="auto-style9">Update Critical Account Details</span><br
			class="auto-style8" /> <br />
	</div>
	<div class="auto-style3" style="height: 332px">
		<sf:form action="${pageContext.request.contextPath}/homeExternalUser.do" method="post">
		<table class="auto-style4" style="width: 100%; height: 308px;">
			<tr>
				<td>Username</td>
				<td><c:out value="${user.userName}"></c:out></td>
				<td> <input name="UsernameField" style="width: 329px" type="text" /></td>
			</tr>
			<tr>
				<td>First Name</td>
				<td><c:out value="${user.FirstName}"></c:out></td>
				<td><input name="FnameField" style="width: 329px" type="text" /></td>
			</tr>
			<tr>
				<td>Last Name</td>
				<td><c:out value="${user.LastName}"></c:out></td>
				<td><input name="LnameField" style="width: 329px" type="text" /></td>
			</tr>
			<tr>
				<td>SSN</td>
				<td><c:out value="${user.SSN}"></c:out></td>
				<td><input name="SSNField" style="width: 329px" type="text" /></td>
			</tr>
		</table>
		<input name="Reset1" type="reset" value="Reset" class="auto-style10" style="width: 160px;" /> 
		<input name="Update1" type="submit" value="Update" style="width: 160px; height: 30px;" />
		</sf:form>
	</div>
</body>
</html>