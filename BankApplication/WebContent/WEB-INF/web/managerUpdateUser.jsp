<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manager - Update User</title>
</head>
<body>
	<div style="height: 332px; width: 822px;">
		<sf:form action="" method="post">
		<table style="width: 100%; height: 308px;">
			<tr>
				<td style="width: 126px">Username</td>
				<td style="width: 247px"><c:out value="${user.username}"/></td>
				<td style="width: 274px">&nbsp;</td>
			</tr>
			<tr>
				<td style="width: 126px">Account Number</td>
				<td style="width: 247px"><c:out value="${user.accountNum}"/></td>
				<td style="width: 274px">&nbsp;</td>
			</tr>
			<tr>
				<td style="width: 126px">First Name</td>
				<td style="width: 247px"><c:out value="${user.fName}"/></td>
				<td style="width: 274px">&nbsp;</td>
			</tr>
			<tr>
				<td style="width: 126px">Last Name</td>
				<td style="width: 247px"><c:out value="${user.lName}"/></td>
				<td style="width: 274px">&nbsp;</td>
			</tr>
			<tr>
				<td style="width: 126px">Date of Birth</td>
				<td style="width: 247px"><c:out value="${user.dateofbirth}"/></td>
				<td style="width: 274px">&nbsp;</td>
			</tr>
			<tr>
				<td style="width: 126px">SSN</td>
				<td style="width: 247px"><c:out value="${user.ssn}"/></td>
				<td style="width: 274px">&nbsp;</td>
			</tr>
			<tr>
				<td style="width: 126px">Email</td>
				<td style="width: 247px"><c:out value="${user.email}"/></td>
				<td style="width: 274px"><input name="Text5" style="height: 23px; width: 329px"
					type="text" /></td>
			</tr>
			<tr>
				<td style="width: 126px">Phone Number</td>
				<td style="width: 247px"><c:out value="${user.phonenum}"/></td>
				<td style="width: 274px"><input name="Text4" style="height: 23px; width: 329px"
					type="text" /></td>
			</tr>
			<tr>
				<td style="width: 126px">Residential Address</td>
				<td style="width: 247px"><c:out value="${user.resaddress}"/></td>
				<td style="width: 274px"><input name="Text3" style="height: 23px; width: 329px"
					type="text" /></td>
			</tr>
			<tr>
				<td style="width: 126px">Mailing	Address</td>
				<td style="width: 247px"><c:out value="${user.mailaddress}"/></td>
				<td style="width: 274px"><input name="Text2" style="height: 23px; width: 329px"
					type="text" /></td>
			</tr>
		</table>
		<input name="Reset1"  type="reset" value="Reset" style="width: 160px; height: 30px;" title="Reset Entered Info"/> 
		<input name="Update1" type="submit" value="Update" style="width: 160px; height: 30px;" title="Update the information to database"/>
		</sf:form>
	</div>
</body>

</html>