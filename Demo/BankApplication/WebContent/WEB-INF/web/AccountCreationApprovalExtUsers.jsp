<%@page import="com.spring.util.StaticConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Show Pending Approvals</title>
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
			<div class="panel-heading">	List of New Account Holders that require approval </div>
			<div class="panel-body">
				<sf:form name="newBankUserApproval" id="newBankUserApproval" action=""
					method="POST" commandName="userList">
					<c:choose>
						<c:when test="${empty bankuserList.users}">
							<H2>You do not have any Users to approve!!! Enjoy your day!!</H2>
						</c:when>
						<c:otherwise>
							<table class="table table-responsive">
								<tr>
									<th>userName</th>
									<th>FirstName</th>
									<th>LastName</th>
									<th>Date of Birth</th>						
									<th>Email</th>
									<th>Phone Number</th>
									<th>MailingAddress</th>
									<th>Residential Address</th>
									<th>User Created on</th>
									<th>Account Status</th>
									<th>Approve / Deny</th>
								</tr>
								<c:forEach items="${bankuserList.users}" var="newbankuser"
									varStatus="status">
									<tr>
										<td><c:out value="${newbankuser.userName}"></c:out></td>
										<td><c:out value="${newbankuser.firstName}"></c:out></td>
										<td><c:out value="${newbankuser.lastName}"></c:out></td>
										<td><c:out value="${newbankuser.dateOfBirth}"></c:out></td>							
										<td><c:out value="${newbankuser.email}"></c:out></td>
										<td><c:out value="${newbankuser.phoneNumber}"></c:out></td>
										<td><c:out value="${newbankuser.mailingAddress}"></c:out></td>
										<td><c:out value="${newbankuser.residentialAddress}"></c:out></td>
										<td><c:out value="${newbankuser.userCreatedOn}"></c:out></td>
										<td><c:out value="${newbankuser.accountStatus}"></c:out></td>
										<td><sf:input type="checkBox" class="control" name="Approve"
												id="Approve" path="chosenList" value="${newbankuser.userName}" />
											<sf:errors path="chosenList"  value="${chosenList}" cssStyle="color: #ff0000;"></sf:errors>
									</tr>
								</c:forEach>
							</table>
							<span style="color: #ff0000;">${chosenList}</span>
							<br />
							<br />
							<input type="submit" class="control btn btn-primary"
								onclick="form.action='${pageContext.request.contextPath}/approveConfirmationForExtUsers.do'"
								value="Approve" />
							<input type="submit" class="control btn btn-primary"
								onclick="form.action='${pageContext.request.contextPath}/DenyNewExternalCustomer.do'"
								value="Deny" />
						</c:otherwise>
					</c:choose>
				</sf:form>	
			</div>
		</div>
	</div>

</body>
</html>