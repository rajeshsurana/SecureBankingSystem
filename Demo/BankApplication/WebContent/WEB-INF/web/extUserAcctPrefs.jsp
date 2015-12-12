<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.spring.util.StaticConstants"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta content="en-us" http-equiv="Content-Language" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>User - Account Preferences</title>

<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>

<script>
	$(function() {
		$("#dob_bankUser").datepicker();
	});
</script>
</head>

<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="navbar-brand">
			<a href="#"><img class="img-responsive"
				src="<c:url value="/resources/Images/logotan3.jpg" />" width="20%" /></a>
		</div>
	</div>
		<div class="container">
		<div class="btn pull-right">
			<a href="${pageContext.request.contextPath}/goToHome" class="btn btn-primary" role="button">Home</a>
			<a href="${pageContext.request.contextPath}/logout" class="btn btn-primary" role="button">Logout</a>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Account Details</div>
			<div class="panel-body">
				<c:forEach items="${bankuserList.bankUsers}" var="bankuser"
								varStatus="status">
					<sf:form action="${pageContext.request.contextPath}/customerUpdateProfileConfirm.do" method="post" commandName="bankuser">
					<fieldset>
							<legend>bankUser Id</legend>
							<label><c:out value="${bankuser.bankUserId}"></c:out></label>
							<sf:input type="hidden" placeholder="Enter your User Name Here"
							path="<%=StaticConstants.bankuserBankUserID%>"
							name="<%=StaticConstants.bankuserBankUserID%>" value="${bankuser.bankUserId}"
							style="width: 250px" />
					</fieldset>
					<fieldset>
							<legend>Username</legend>
							<label><c:out value="${bankuser.userName}"></c:out></label>
							<sf:input type="hidden" placeholder="Enter your User Name Here"
							path="<%=StaticConstants.bankuserUsername%>"
							name="<%=StaticConstants.bankuserUsername%>"  value="${bankuser.userName}"
							style="width: 250px" />
					</fieldset>
					<fieldset>
							<legend>E-mail</legend>	
							<label><c:out value="${bankuser.email}"></c:out></label>						
							<sf:input type="hidden" placeholder="Enter your User Name Here"
							path="<%=StaticConstants.bankuserEmail%>" value="${bankuser.email}"
							name="<%=StaticConstants.bankuserEmail%>" style="width: 250px" />
					</fieldset>
					<fieldset>
							<legend>First Name</legend>		
							<label><c:out value="${bankuser.firstName}"></c:out></label>					
							<sf:input type="hidden" placeholder="Enter your User Name Here"
							path="<%=StaticConstants.bankuserFirstName%>"  value="${bankuser.firstName}"
							name="<%=StaticConstants.bankuserFirstName%>"
							style="width: 250px" />
					</fieldset>
					<fieldset>
							<legend>Last Name</legend>			
							<label><c:out value="${bankuser.lastName}"></c:out></label>			
							<sf:input type="hidden" placeholder="Enter your User Name Here"
							path="<%=StaticConstants.bankuserLastName%>" value="${bankuser.lastName}"
							name="<%=StaticConstants.bankuserLastName%>"
							style="width: 250px" />
					</fieldset>
					
					<fieldset>
							<legend>Phone Number</legend>							
							<sf:input type="text" placeholder="Enter your User Name Here"
							path="<%=StaticConstants.bankuserPhoneNumber%>" value="${bankuser.phoneNumber}"
							name="<%=StaticConstants.bankuserPhoneNumber%>"
							style="width: 250px" />
					</fieldset>
					<fieldset>
							<legend>Mailing Address</legend>							
							<sf:input type="text" placeholder="Enter your User Name Here"
							path="<%=StaticConstants.bankuserMailingAddress%>"  value="${bankuser.residentialAddress}"
							name="<%=StaticConstants.bankuserMailingAddress%>"
							style="width: 250px" />
					</fieldset>
					<fieldset>
							<legend>Residential Address</legend>							
							<sf:input type="text" placeholder="Enter your User Name Here"
							path="<%=StaticConstants.bankuserResidentialAddress%>" value="${bankuser.mailingAddress}"
							name="<%=StaticConstants.bankuserResidentialAddress%>"
							style="width: 250px" />
					</fieldset>
					<input name="Submit1" type="submit" value="Update"
					style="width: 161px" />
					</sf:form>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>
