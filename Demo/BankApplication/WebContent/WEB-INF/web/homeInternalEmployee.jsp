<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Internal Employee - HomePage</title>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>

</head>
<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="navbar-brand">
			<img class="img-responsive"
				src="<c:url value="/resources/Images/logotan3.jpg" />" width="20%" />
		</div>
	</div>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">Internal Employee home
				<div class="btn pull-right">
				<a href="${pageContext.request.contextPath}/goToHome" class="btn btn-primary" role="button">Home</a>
				<a href="${pageContext.request.contextPath}/logout" class="btn btn-primary" role="button">Logout</a>
				</div>
				<br/><br/>
			</div>
			<div class="panel-body">
				<h3>Hello Internal Employee</h3>
				<div class="form-group">
					<sf:form method="POST" action="addNewCustomer.do">
						<table>
							<tr>
								<td colspan='2'><input name="submit" type="submit"
									value="New Customer Registration" class="btn btn-primary" /></td>
							</tr>
						</table>
					</sf:form>
					<br />
					<sf:form method="POST" action="addNewMerchant.do">
						<table>
							<tr>
								<td colspan='2'><input name="submit" type="submit"
									value="New Merchant Registration" class="btn btn-primary" /></td>
							</tr>
						</table>
					</sf:form>
					<br />
					<sf:form method="POST" action="generatePKI.do">
						<table>
							<tr>
								<td colspan='2'><input name="submit" type="submit"
									value="Show List Of Pending Approvals" class="btn btn-primary" /></td>
							</tr>
						</table>
					</sf:form>
					<br />
					<sf:form method="POST" action="ShowListOfPendingTransactions.do">
						<table>
							<tr>
								<td colspan='2'><input name="submit" type="submit"
									class="btn btn-primary"
									value="Show Pending Transactions" class="btn btn-primary" /></td>
							</tr>
						</table>
					</sf:form>
					<br />
					<sf:form method="POST" action="SearchUsersByAccountNumber.do">
						<table>
							<tr>
								<td colspan='2'><input name="submit" type="submit"
									class="btn btn-primary" value="Search User by Account Number" /></td>
							</tr>
						</table>
					</sf:form>
					<br />
					<sf:form method="POST" action="viewMyProfile.do">
						<table>
							<tr>
								<td colspan='2'><input name="submit" type="submit"
									class="btn btn-primary" value="View My Profile" /></td>
							</tr>
						</table>
					</sf:form>
					<br />
						<sf:form method="POST" action="UpdateEmployeeProfile.do">
						<table>
							<tr>
								<td colspan='2'><input name="submit" type="submit"
									class="btn btn-primary" value="Edit My Profile" /></td>
							</tr>
						</table>
					</sf:form>
					<br/>
					<sf:form method="POST" action="updateUserProfile.do">
						<table>
							<tr>
								<td colspan='2'><input name="submit" type="submit"
									class="btn btn-primary"
									value="Update User Profile By Account Number" /></td>
							</tr>
						</table>
					</sf:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>