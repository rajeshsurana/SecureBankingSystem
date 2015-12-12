<%@page import="com.spring.util.StaticConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manager - Show Pending Merchant Account Approvals</title>
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
			<div class="panel-heading">New User Account Holders that require Approval</div>
			<div class="panel-body">
				<sf:form name="newBankUserApproval" id="newBankUserApproval" action=""
					method="POST" commandName="userList">
					<c:choose>
						<c:when test="${empty bankuserList.bankUsers}">
							<H2>You do not have any accounts that require approval.
							</H2>
						</c:when>
						<c:otherwise>
							<div class="table-responsive">
								<table class = "table table-responsive">
								<tr>
									<th>userName</th>
									<th>Company Name</th>
									<th>CEO Name</th>
									<th>Launch Date</th>						
									<th>Email</th>
									<th>Phone Number</th>
									<th>HeadQuarters</th>
									<th>Mailing Address</th>
									<th>User Created on</th>
									<th>Account Status</th>
									<th>Approve / Deny</th>
								</tr>
								<c:forEach items="${bankuserList.bankUsers}" var="bankUser"
									varStatus="status">
									<tr>
										<td><c:out value="${bankUser.userName}"></c:out></td>
			
										<td><c:out value="${bankUser.firstName}"></c:out></td>
			
										<td><c:out value="${bankUser.lastName}"></c:out></td>
			
										<td><c:out value="${bankUser.dateOfBirth}"></c:out></td>							
			
										<td><c:out value="${bankUser.email}"></c:out></td>
			
										<td><c:out value="${bankUser.phoneNumber}"></c:out></td>
			
										<td><c:out value="${bankUser.mailingAddress}"></c:out></td>
										
										<td><c:out value="${bankUser.residentialAddress}"></c:out></td>
			
										<td><c:out value="${bankUser.userCreatedOn}"></c:out></td>
			
										<td><c:out value="${bankUser.accountStatus}"></c:out></td>
			
										<td><sf:input type="checkBox" class="control" name="Approve"
												id="Approve" path="chosenList" value="${bankUser.userName}" />
											<sf:errors path="chosenList" cssStyle="color: #ff0000;"></sf:errors>
									</tr>
								</c:forEach>
							</table>
							</div>
							<span style="color: #ff0000;">${chosenList}</span>
							<br />
							<br />
							<input type="submit" class="control"
								onclick="form.action='${pageContext.request.contextPath}/DeleteConfirmationForExtUsers.do'"
								value="Approve" />
									&nbsp;
					<input type="submit" class="control"
								onclick="form.action='${pageContext.request.contextPath}/DenyDeactivationRequests.do'"
								value="Deny" />
						</c:otherwise>
					</c:choose>
				</sf:form>
			</div>
		</div>
	</div>
</body>
</html>