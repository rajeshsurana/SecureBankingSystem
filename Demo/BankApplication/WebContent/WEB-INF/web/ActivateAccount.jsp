<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.spring.util.StaticConstants"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activate Account</title>
</head>
<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="navbar-brand">
			<a href="#"><img class="img-responsive"
				src="<c:url value="/resources/Images/logotan3.jpg" />" width="20%" /></a>
		</div>
	</div>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">Activation of the user</div>
			<div class="panel-body">
				<h2>Welcome User to the Southwest Bank!!!</h2>

				<h5>
					The first time login to the Online Banking System requires a
					verification through your registered E-Mail. <br> Click on the
					Send OTP to verify your account and reset your Password Details!!!
				</h5>
				
				<sf:form method="POST" action="ActivateAccount.do"
					commandName="bankUser">
					<div class="form-group">
						<label for="<%=StaticConstants.bankuserEmail%>"
							class="control-label"> Enter your Registered E-mail for
							verification</label>
						<sf:input type="text" placeholder="Enter Your Email Id"
							path="<%=StaticConstants.bankuserEmail%>" cssClass="form-control"
							name="<%=StaticConstants.bankuserEmail%>" style="width: 250px"
							 />
						<sf:errors path="<%=StaticConstants.bankuserEmail%>"
							cssStyle="color: #ff0000;"></sf:errors>
							<br/>
							<br/>
						<input name="submit" type="submit" value="Send OTP"
							style="width: 161px" class="btn btn-primary" />
					</div>
				</sf:form>
			</div>
		</div>
	</div>
</body>
</html>