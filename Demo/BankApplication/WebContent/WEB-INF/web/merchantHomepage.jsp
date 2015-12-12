<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Merchant - Home Page</title>
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
				<a href="${pageContext.request.contextPath}/downloadPDF" class="btn btn-primary">Download Statement</a>
			<a href="${pageContext.request.contextPath}/goToHome" class="btn btn-primary" role="button">Home</a>
			<a href="${pageContext.request.contextPath}/logout" class="btn btn-primary" role="button">Logout</a>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<div class="username">Welcome <%= (String)session.getAttribute("sUserName") %>!
					<br/>
					<br/>
				</div>
			</div>
			<div class="panel-body">
				<h4>Checking</h4>
				<label>Account Number</label>:
				<span class="auto-style3"><label id="Label1"><strong><c:out value="${checkingAccNo}"/></strong></label></span><br/> 
				<label>Balance</label>:
				<span class="auto-style3"><label id="Label1"><strong><c:out value="${checkingBal}"/></strong></label></span><br/>
				<sf:form action="${pageContext.request.contextPath}/transferToMerchant.do" cssClass="form-inline" method ="POST" commandName="bankTransaction">
					<span>${transferToMerchantResult}</span><br/>
					<div class="input-group">
						<div class="input-group-addon">$</div>
						<sf:input type="text" cssClass="form-control" placeholder="Enter amount" id="transferToMerchant" name="transferToMerchant" path="transactionAmount"/>					      
						<div class="input-group-addon">.00</div>
					</div>
					<input type="text" class="form-control" placeholder="Email of Benefactor" id="email" name="email" style="width:30%">
					<sf:errors path="transactionAmount"/>
					<input type="Submit" class="btn btn-primary" value="TransferToMerchant"/>
				</sf:form>
				
				<sf:form action="${pageContext.request.contextPath}/debit.do" cssClass="form-inline" method ="POST" commandName="bankTransaction">
					<span>${debitResult}</span><br/>
					<div class="input-group">
						<div class="input-group-addon">$</div>
						<sf:input type="text" cssClass="form-control" placeholder="Enter amount" id="debitAmount" name="debitAmount" path="transactionAmount"/>					      
						<div class="input-group-addon">.00</div>
					</div>
					<sf:errors path="transactionAmount"/>
					<input type="Submit" class="btn btn-primary" class="btn btn-primary" value="Debit"/>
				</sf:form>
				
				<sf:form action="${pageContext.request.contextPath}/credit.do" cssClass="form-inline" method ="POST" commandName="bankTransaction">
					<span>${creditResult}</span><br/>
					<div class="input-group">
						<div class="input-group-addon">$</div>
						<sf:input type="text" cssClass="form-control" placeholder="Enter amount" id="creditAmount" name="creditAmount" path="transactionAmount"/>
						<div class="input-group-addon">.00</div>
					</div>
					<sf:errors path="transactionAmount"/>
					<input type="Submit" class="btn btn-primary" value="Credit"/>
				</sf:form>
				
				<sf:form action="${pageContext.request.contextPath}/transfer.do" cssClass="form-inline" method ="POST" commandName="bankTransaction">
					<span>${transferResult}</span><br/>
					<div class="input-group">
						<div class="input-group-addon">$</div>
						<sf:input type="text" cssClass="form-control" placeholder="Enter amount" id="transferAmount" name="transferAmount" path="transactionAmount"/>
						<div class="input-group-addon">.00</div>
					</div>
					<input type="text" class="form-control" placeholder="Email of Recepient" id="email" name="email" style="width:30%">
					<sf:errors path="transactionAmount"/>
					<input type="Submit" class="btn btn-primary" value="Transfer"/>
				</sf:form>
				<br/>
				<br/>
				<sf:form action="${pageContext.request.contextPath}/viewPendingApproval" cssClass="form-inline" method="Get" commandName="bankTransaction">
					<span>${pendingAuthUrlInjection }</span>
					
					<input type="Submit" class="btn btn-primary" value="View Pending Approvals To be Authorized for Merchant">
				</sf:form>
				<br/>
				<br>
				<sf:form action="${pageContext.request.contextPath}/generatedeletePKI.do" cssClass="form-inline" method="POST" commandName="bankTransaction">
					<input type="Submit" class="btn btn-primary" value="Delete Account">
				</sf:form>
				<sf:form method="POST" action="${pageContext.request.contextPath}/customerUpdateProfile.do" cssClass="form-inline">
					<span>${UpdateError}</span><br/>
					<input name="submit" type="submit" class="btn btn-primary" value="Edit My Profile" />
				</sf:form>
				
			</div>
		</div>
	</div>
</body>
</html>
			