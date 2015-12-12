<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manager - HomePage</title>

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
		<div class="btn pull-right">
			<a href="${pageContext.request.contextPath}/goToHome" class="btn btn-primary" role="button">Home</a>
			<a href="${pageContext.request.contextPath}/logout" class="btn btn-primary" role="button">Logout</a>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Manager home</div>
			<div class="panel-body" align="center"><h3>Manager - Home Page</h3>
				<div class="form-group">
					<div class="table-responsive">
						<table class="table">
							<tr>
								<td colspan='2' align="right">
									<sf:form method="POST" action="addNewCustomer.do">
									<input name="submit" type="submit" class="btn btn-primary" value="New Customer Registration" />
									</sf:form>
								</td>
								<td colspan='2'>
									<sf:form method="POST" action="addNewMerchant.do">
									<input name="submit" type="submit" class="btn btn-primary" value="New Merchant Registration" />
									</sf:form>
								</td>
							</tr>
							<tr>
								<td colspan='2' align="right">
									<sf:form method="POST" action="ApproveNewCustomer.do">
									<input name="submit" type="submit" class="btn btn-primary" value="Approve Customer Registration" />
									</sf:form>
								</td>
								<td colspan='2'>
									<sf:form method="POST" action="ApproveNewMerchant.do">
									<input name="submit" type="submit" class="btn btn-primary" value="Approve Merchant Registration" />
									</sf:form>
								</td>
							</tr>
							<tr>
								<td colspan='2' align="right">
									<sf:form method="POST" action="generatePKI.do">
									<input name="submit" type="submit" class="btn btn-primary" value="Show List Of Pending Approvals" />
									</sf:form>
								</td>
								<td colspan='2' align="left">
									<sf:form method="POST" action="ShowListOfPendingTransactions.do">
									<input name="submit" type="submit" class="btn btn-primary" value="Show Pending Transactions" />
									</sf:form>
								</td>
							</tr>
							<tr>
								<td colspan='2' align="right">
									<sf:form method="POST" action="SearchUsersByAccountNumber.do">
									<input name="submit" type="submit" class="btn btn-primary" value="Search User by Account Number" />
									</sf:form>
								</td>
								<td colspan="2" align="left">
									<sf:form method="POST" action="updateUserProfile.do">
									<input name="submit" type="submit" class="btn btn-primary" value="Update User Profile By Account Number" />
									</sf:form>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="right">
									<sf:form method="POST" action="viewMyProfile.do">
									<input name="submit" type="submit" class="btn btn-primary" value="View My Profile" />
									</sf:form>
								</td>
								<td colspan="2" align="left">
									<sf:form method="POST" action="UpdateEmployeeProfile.do">
									<input name="submit" type="submit" class="btn btn-primary" value="Edit My Profile" />
									</sf:form>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="right">
									<sf:form method="POST" action="deleteMerchantAccounts.do">
									<input name="submit" type="submit" class="btn btn-primary" value="Approve the Deletion of the requested Merchant's Deactivation" />
									</sf:form>
								</td>
								<td colspan="2" align="left">
									<sf:form method="POST" action="deleteUserAccounts.do">
									<input name="submit" type="submit" class="btn btn-primary" value="Approve the Deletion of the requested User's Deactivation" /> 
									</sf:form>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>