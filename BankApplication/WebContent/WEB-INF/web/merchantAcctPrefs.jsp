<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta content="en-us" http-equiv="Content-Language" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>Merchant - Account Preferences</title>
</head>

<body>
	<div class="auto-style6">
		<br /> <span class="auto-style9">Account Details</span><br
			class="auto-style8" /> <br />
	</div>
	<div class="auto-style3" style="height: 332px">
		<sf:form action="${pageContext.request.contextPath}/update.do" method="post">
		<table class="auto-style4" style="width: 100%; height: 308px;">
			<tr>
				<td class="auto-style1" style="width: 30%;">Username</td>
				<td class="auto-style2" style="width: 30%;"></td>
				<td></td>
			</tr>
			<tr>
				<td class="auto-style1" style="width: 30%">Account Number</td>
				<td class="auto-style2" style="width: 30%">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="auto-style1" style="width: 30%">First Name</td>
				<td class="auto-style2" style="width: 30%">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="auto-style1" style="width: 30%;">Last Name</td>
				<td class="auto-style2" style="width: 30%;"></td>
				<td></td>
			</tr>
			<tr>
				<td class="auto-style1" style="width: 30%;">Registration Date</td>
				<td class="auto-style2" style="width: 30%;">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="auto-style1" style="width: 30%;">Merchant SSN</td>
				<td class="auto-style2" style="width: 30%;"></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="auto-style1" style="width: 30%;">Email</td>
				<td class="auto-style2" style="width: 30%;"></td>
				<td><input name="emailUpdateText" style="width: 329px" type="text" /></td>
			</tr>
			<tr>
				<td class="auto-style1" style="width: 30%">Phone Number</td>
				<td class="auto-style2" style="width: 30%">&nbsp;</td>
				<td><input name="phoneNumText" style="width: 329px" type="text" /></td>
			</tr>
			<tr>
				<td class="auto-style1" style="width: 30%">Head Office Address</td>
				<td class="auto-style2" style="width: 30%">&nbsp;</td>
				<td><input name="rAddressText" style="width: 329px" type="text" /></td>
			</tr>
			<tr>
				<td class="auto-style1" style="width: 30%;">Mailing Address</td>
				<td class="auto-style2" style="width: 30%;"></td>
				<td><input name="mAddressText" style="width: 329px" type="text" /></td>
			</tr>
			<tr>
				<td class="auto-style1" style="width: 30%;">Password</td>
				<td class="auto-style2" style="width: 30%;">&nbsp;</td>
				<td><input name="passwordField" style="width: 329px" type="password" /></td>
			</tr>
						<tr>
				<td class="auto-style1" style="width: 30%;">Re-Password</td>
				<td class="auto-style2" style="width: 30%;">&nbsp;</td>
				<td><input name="confirmPasswordField" style="width: 329px" type="password" /></td>
			</tr>
		</table>
		<input name="Reset1" type="reset" value="Reset" class="auto-style10"
			style="width: 160px; height: 30px;" /> <input name="Update1"
			type="submit" value="Update" style="width: 160px; height: 30px;" />
		</sf:form>
	</div>
</body>
</html>