<%@page import="com.spring.util.StaticConstants"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pending transactions made by Merchant on Your Behalf</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
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
			<div class="panel-heading">View Pending Transactions</div> 
			<div class="panel-body">
				<table class="table table-bordered">
					<tr>
						<th> Recipient </th>
						<th> Amount </th>
						<th colspan="2"> Decision </th>
					</tr>
					<c:forEach items="${pendingMerchantTransactionList}" var="pendingMerchantTransaction">
						<tr>
						
							<td><c:out value="${pendingMerchantTransaction.recipient}" /></td>
							<td><c:out value="${pendingMerchantTransaction.transactionAmount}" /></td>
							<td style="width: 130px; height: 32px;">
								<sf:form action="${pageContext.request.contextPath}/pendingAuth/accept/${pendingMerchantTransaction.transactionId}/" method ="POST">
									<input type="Submit" class="btn btn-primary" value="Accept"/>
								</sf:form>
							</td>
							<td>
								<sf:form action="${pageContext.request.contextPath}/pendingAuth/decline/${pendingMerchantTransaction.transactionId}/" method ="POST">
									<input type="Submit" class="btn btn-warning" value="Decline"/>
								</sf:form>
							</td>
								
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>