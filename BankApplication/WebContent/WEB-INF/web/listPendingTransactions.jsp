<%@page import="com.spring.util.StaticConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>listPendingTransactions</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>
</head>
<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="navbar-brand">
			<img class="img-responsive" src="<c:url value="/resources/Images/logotan3.jpg" />" width="20%" />
		</div>
	</div>
	<div class="container">
		<div class="btn pull-right">
			<a href="${pageContext.request.contextPath}/goToHome" class="btn btn-primary" role="button">Home</a>
			<a href="${pageContext.request.contextPath}/logout" class="btn btn-primary" role="button">Logout</a>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">List of Pending transactions</div>
			<div class="panel-body">
				<c:if test="${!empty transactionError}">
					<script>
						alert('${transactionError}')
					</script>
				</c:if>
				<sf:form name="pendingTransactionList" id="pendingTransactionList"
					method="POST">
					<c:choose>
						<c:when test="${empty bankTransactionList}">
							<h3>There are no pending Transactions!</h3>
						</c:when>
						<c:otherwise>
							<div class="table-responsive">
							<table border="1" class="table">
								<tr>
									<th>Recipient</th>
									<th>Benefactor</th>
									<th>Amount</th>
									<th>Action</th>
								</tr>
								<c:forEach items="${bankTransactionList}" var="transaction">
									<tr>
										<td><c:out value="${transaction.recipient.userName}"></c:out></td>
										<td><c:out value="${transaction.benefactor.userName}"></c:out></td>
										<td><c:out value="${transaction.transactionAmount}"></c:out></td>
										<td>
										<!-- 
											<spring:url value="/transaction/approve/${transaction.bankTransactionId}/" var="approveTransactionUrl" /> 
							  				<spring:url value="/transaction/decline/${transaction.bankTransactionId}/" var="declineTransactionUrl" />
											<button class="button.Approve" name="approveTransaction" onclick="location.href='${approveTransactionUrl}'">Approve</button> 
											<button class="button.Decline" name="declineTransaction" onclick="location.href='${declineTransactionUrl}'">Decline</button> 
										-->
										<a href="transaction/approve/${transaction.bankTransactionId}/">Approve</a>
										<a href="transaction/decline/${transaction.bankTransactionId}/">Decline</a>
										</td>
									</tr>
								</c:forEach>
							</table>
							</div>
						</c:otherwise>
					</c:choose>
				</sf:form>
			</div>
		</div>
	</div>
</body>
</html>