<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User - Transfer Page</title>
</head>
<body>

<div id="container">
	<div id="top" style="border: double">
		<br />
		<br />
		Select your Account Type: <select style="width: 250px">
		<option value="checking">Checking</option>
		<option value="savings">Savings</option>
		</select> <br />
		<br />
		Checking Account Funds: $ <c:out value="${balance.savings}"/>
		<br />
		Savings Account Funds: $ <c:out value="${balance.checking}"/>
	</div>
	<hr />
    <div id="middle" style="border: double">
		<sf:form action="" method="POST" commandName="">
			Recipient Details<br />
			Account No: <input style="width: 200px" type="text" value=""><br />
			Amount in $: <input style="width: 200px" type="text" value=""><br />
			<br />
			<input name="Transfer" type="submit" value="Transfer">
		</sf:form>
	</div>
	<hr />
	<div id="bottom" style="border: double">
		<div id="messages">
			<div id="StatusArea">
				Transaction Status: <c:out value="${transaction.status}"/></div>
		</div>
	</div>
</div>

</body>
</html>