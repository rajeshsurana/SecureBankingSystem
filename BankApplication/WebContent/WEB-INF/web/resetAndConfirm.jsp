<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Reset Password and Confirm</title>
	<link href="${pageContext.request.contextPath}/resources/css/General.css" rel="stylesheet" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath }/resources/script/jquery-1.11.3.min.js"></script>
	<link type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery.keypad.css" rel="stylesheet"> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.plugin.min.js"></script> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.keypad.min.js"></script>

	<script type="text/javascript">

		$(document).ready(function(){
			$('#password').keypad({keypadOnly: false, 
			    layout: $.keypad.qwertyLayout});
			$('#confirmpass').keypad({keypadOnly: false, 
			    layout: $.keypad.qwertyLayout});
		});
		function onLoad(){
			$("#password").keyup(checkPasswordMatch);
			$("#confirmpass").keyup(checkPasswordMatch);
			$("#details").submit(canSubmit);
		}
		
		function canSubmit(){
			var password = $("#password").val();
			var confirmpass = $("#confirmpass").val();
			return password == confirmpass;
		}
		
		function checkPasswordMatch(){
		
			var password = $("#password").val();
			var confirmpass = $("#confirmpass").val();
			if (password.length > 3 || confirmpass.length > 3) {
		
					if (password == confirmpass) {
						$("#passmatch").text(" <fmt:message key='MatchedPasswords.user.password'></fmt:message>");
						$("#passmatch").addClass("valid");
						$("#passmatch").removeClass("error");
					} else {
						$("#passmatch").text(" <fmt:message key='UnmatchedPasswords.user.password'></fmt:message>");
						$("#passmatch").addClass("error");
						$("#passmatch").removeClass("valid");
					}
				}
			}
			$(document).ready(onLoad);
	</script>
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
			<div class="panel-heading">Enter New Password</div> 
			<div class="panel-body">
				<sf:form id="details" method="POST" action="${pageContext.request.contextPath}/resetAndConfirm" commandName="bankUser">
					<div class="form-group">
						<label for="password" cssClass="form-control">Password:</label>
						<sf:input id="password" cssClass="form-control" path="userPassword" name="password" type="password" pattern=".{8,20}" title="8 to 20 characters"/><br/>
						<div class="error"><sf:errors path="userPassword"></sf:errors></div>
					</div>
					<div class="form-group">
						<label for="confirmpass" cssClass="form-control">Confirm Password:</label>
						<input id="confirmpass" Class="form-control" name="confirmpass" type="password" pattern=".{8,20}" title="8 to 20 characters"/>
						<div class="error"><c:out value="${confirmpass}"></c:out> </div>
						<div id="passmatch"></div>	
					</div>
					<div class="form-group">		
						<input name="submit" class="btn btn-primary" type="submit" value="Submit" />
					</div>
				</sf:form>
			</div>
		</div>
	</div>
</body>
</html>