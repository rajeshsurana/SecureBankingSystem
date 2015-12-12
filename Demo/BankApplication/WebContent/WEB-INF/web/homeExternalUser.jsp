<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Home Page</title>
</head>
<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="navbar-brand">
			<img class="img-responsive"
				src="<c:url value="/resources/Images/logotan3.jpg" />" width="20%" />
		</div>
	</div>
	<div class="container">
		<div class="username">Welcome <%= (String)session.getAttribute("sUserName") %>!		<div class="pull-right"><a href="${pageContext.request.contextPath}/downloadPDF" class="btn btn-primary " role="button">Download Statement</a>
			<a href="${pageContext.request.contextPath}/goToHome" class="btn btn-primary" role="button">Home</a>
			<a href="${pageContext.request.contextPath}/logout" class="btn btn-primary" role="button">Logout</a></div>
		<br/><br/>
		</div>
		<div class="panel panel-default">
			
		    <div class="panel-heading">
		        Checking
		    </div>
			<div class="panel-body">
		      <sf:form method="POST" commandName="">
					<div class="form-group">
						<label class="form-control">Account Number :  </label>  <label id="Label1" class="form-control"><strong><c:out value="${checkingAccNo}"/></strong></label><br/>
						<label class="form-control"> Balance :</label>  <label id="Label1" class="form-control"><strong><c:out value="${checkingBal}"/></strong></label>
					</div>
				</sf:form>
				<form class="form-inline" method="POST" action="${pageContext.request.contextPath}/transferToSaving.do">
					 <div class="form-group">
					 	<span>${transferToSaving}</span><br/>
					   	<label class="sr-only" for="exampleInputAmount">Amount (in dollars)</label>
					    <div class="input-group">
					      <div class="input-group-addon">$</div>
					      <input type="text" class="form-control" placeholder="Enter amount" id="transferToSaving" name="transferToSaving">
					      <div class="input-group-addon">.00</div>
					    </div>
					    <input type="submit" class="btn btn-primary" value="Transfer to Saving Account"></input>
					  </div>
				</form>
				<sf:form cssClass="form-inline" action="${pageContext.request.contextPath}/debit.do" method ="POST" commandName="bankTransaction">
					<div class="form-group">
					  	<span>${debitResult}</span><br/>
					    <label class="sr-only" for="exampleInputAmount">Amount (in dollars)</label>
					    <div class="input-group">
					      <div class="input-group-addon">$</div>
					      <sf:input type="text" cssClass="form-control" placeholder="Enter amount" id="debitAmount" name="debitAmount" path="transactionAmount"/>
					      <div class="input-group-addon">.00</div>
					      <sf:errors path="transactionAmount"/>
					    </div>
					    <input type="submit" class="btn btn-primary" value="Debit"></input>
					</div>
				</sf:form>
				<sf:form cssClass="form-inline" action="${pageContext.request.contextPath}/credit.do" method ="POST" commandName="bankTransaction">
					<div class="form-group">
					  	<span>${creditResult}</span><br/>
					    <label class="sr-only" for="exampleInputAmount">Amount (in dollars)</label>
					    <div class="input-group">
					      <div class="input-group-addon">$</div>
					      <sf:input type="text" cssClass="form-control" placeholder="Enter amount" id="creditAmount" name="creditAmount" path="transactionAmount"/>
					      <div class="input-group-addon">.00</div>
					      <sf:errors path="transactionAmount"/>
					    </div>
					    <input type="submit" class="btn btn-primary" value="Credit"></input>
					</div>
				</sf:form>
				<sf:form cssClass="form-inline" action="${pageContext.request.contextPath}/transfer.do" method ="POST" commandName="bankTransaction">
					<div class="form-group">
					  	<span>${transferResult}</span><br/>
					    <label class="sr-only" for="exampleInputAmount">Amount (in dollars)</label>
					    <div class="input-group">
					      <div class="input-group-addon">$</div>
					      <sf:input type="text" cssClass="form-control" placeholder="Enter amount" id="transferAmount" name="transferAmount" path="transactionAmount"/>
					      <div class="input-group-addon">.00</div>
					      <input type="text" placeholder="Email of Recepient" id="email" name="email" class="form-control">
					      <sf:errors path="transactionAmount"/>
					    </div>
					    <input type="submit" class="btn btn-primary" value="Transfer"></input>
					    		
					</div>
				</sf:form>

				<br/>
					    <br/>
					    <sf:form action="${pageContext.request.contextPath}/viewPendingApproval" method="Get" commandName="bankTransaction">
							<span>${pendingAuthUrlInjection }</span>
							<input type="Submit" class="btn btn-primary" value="View Pending Approvals To be Authorized for Merchant">
						</sf:form>
						<br/>
						<sf:form action="${pageContext.request.contextPath}/generatedeletePKI.do" method="POST" commandName="bankTransaction">
							<input type="Submit" class="btn btn-primary" value="DeActivate Account">
						</sf:form>
			<br/>
						<sf:form method="POST" action="${pageContext.request.contextPath}/customerUpdateProfile.do">
							<span>${UpdateError}</span>
							<input name="submit" type="submit" class="btn btn-primary" value="Edit My Profile" />
						</sf:form>
			</div>
			<div class="panel-heading">Savings</div>
			<div class="panel-body">
				<c:choose>
					<c:when test="${savingAccNo==-1}">
						<sf:form cssClass="form-inline" action="${pageContext.request.contextPath}/createSavingAccount.do" method ="POST" commandName="bankAccount">
							<span>${createSavingAccResult}</span><br/>
							<input type="Submit" class="btn btn-primary" value="Create Savings Account"/>
						</sf:form>
					</c:when>
					<c:otherwise>
						<span>${createSavingAccResult}</span><br/>
						<label>Account Number</label>:
						<span><label id="Label1"><strong><c:out value="${savingAccNo}"/></strong></label></span><br/>
						<label>Balance : <label id="Label2"><strong><c:out value="${savingBal}"/></strong></label></label><br/>
							
						<form Class="form-inline" action="${pageContext.request.contextPath}/transferToChecking.do" method ="POST">
							<div class="form-group">
								<span>${transferToChecking}</span><br/>
								<div class="input-group">
									<div class="input-group-addon">$</div>
									<input type="text" class="form-control" placeholder="Enter amount" id="transferToChecking" name="transferToChecking"/>
									<div class="input-group-addon">.00</div>
									<input type="Submit" class="btn btn-primary" value="Transfer to Checking Account"/>
								</div>
							</div>
						</form>
						<br/>
					</c:otherwise>
				</c:choose>
			
			</div>
		</div><!--pannel-->
	</div>
</body>
</html>