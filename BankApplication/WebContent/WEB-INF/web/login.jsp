<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>

<!DOCTYPE html>

<html>

<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>

<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
<script type="text/javascript">
	var RecaptchaOptions = {
		theme : 'clean'
	};
</script>
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/jquery.keypad.css"
	rel="stylesheet"/>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/script/jquery.plugin.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/script/jquery.keypad.min.js"></script>
<script 
    src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
<script 
    src="${pageContext.request.contextPath}/resources/script/main.js"></script>
<script>
	$(document).ready(function() {
		$('#userPassword').keypad({
			keypadOnly : false,
			layout : $.keypad.qwertyLayout
		});
	});
</script>
<!--<style>
		body  {
		    background-image: url("<c:url value="/resources/Images/bg.jpg" />");
		    background-color: #cccccc;
		    background-size: cover;
    		background-repeat: no-repeat;
		}
	</style>-->
</head>

<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="navbar-brand">
			<img class="img-responsive"
				src="<c:url value="/resources/Images/logotan3.jpg" />" width="20%" />
		</div>
	</div>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">Login</div>
			<div class="panel-body">

				<sf:form method="POST" commandName="bankUser">
					<div class="form-group">
						<font color="red">${messageDevice}</font><font color="green">${messageLoggedOut}</font>
						<font color="red">${deactiveUser}</font>
						<font color="red">${sessionScope.inputError}</font>
						<font color="red">${multipleSession}</font>
						<c:remove var="inputError" scope="session" />
					</div>
					
					<div class="form-group">
					
						<label for="userName" cssClass="form-control">UserName:</label>
						<sf:input type="text" cssClass="form-control"
							placeholder="Enter your User Name Here" path="userName"
							name="userName" pattern=".{6,20}" title="6 to 20 characters" maxlength="20"/>
						<sf:errors path="userName" cssStyle="color: #ff0000;"></sf:errors>
					</div>
					
					<div class="form-group">
						<label for="userPassword" cssClass="form-control">Password
							:</label>
						<sf:input type="password" cssClass="form-control"
							placeholder="Enter your password Here" path="userPassword"
							name="userPassword" id="userPassword" pattern=".{8,20}" title="8 to 20 characters" maxlength="20"/>
						<sf:errors path="userPassword" cssStyle="color: #ff0000;"></sf:errors>
					</div>
					<!--<tr>
						<td>Password :</td>
						<td><sf:input type="password" placeholder="Enter your password Here" path="userPassword" name="userPassword"/><sf:errors path="userPassword"></sf:errors></td>
					</tr>-->
					<div class="form-group">
						Are you a Sun Devil? <font color="red">${message}</font>
					</div>
					<div class="form-group">
					<table>
						<tr>
							<td id="captcha_paragraph" colspan='2'>
<%-- 								<c:if
									test="${invalidRecaptcha == true}">
									<span class="error_form_validation"> <!--<spring:message code="invalid.captcha" text="Invalid captcha please try again"/>-->
									</span>
								</c:if>
								 <%
									if(request.getScheme().equals("https"))
									{
	
									 	ReCaptcha c = ReCaptchaFactory.newSecureReCaptcha("6LcXhA4TAAAAAIDLbdnaOa8W_CsYAx3F40lKIHTJ",
									 				"6LcXhA4TAAAAAAtCUGzDbyzqCJ8A19Y5-PQGPlLy", false);
									 		out.print(c.createRecaptchaHtml(null, null));
									}else{
									 	ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LcXhA4TAAAAAIDLbdnaOa8W_CsYAx3F40lKIHTJ",
								 				"6LcXhA4TAAAAAAtCUGzDbyzqCJ8A19Y5-PQGPlLy", false);
								 		out.print(c.createRecaptchaHtml(null, null));
									} 
								  %> --%>
								  
								  <%@ page import="java.util.Base64" %>
								  <img style="margin-bottom:10px;" class="captcha" src="data:image/png;base64,${imageString}" id="captcha_img">
								  
								  	<a class="btn btn-success" href="${pageContext.request.contextPath}/">Refresh</a>
								  
								  <br/>
								  <input type="text" class="form-control"placeholder="Enter Captcha" name="captchaCode" id="captchaCode"/>
							</td>
						</tr>
					</table>
					</div>
					<div class="form-group">
						<input name="submit" type="submit" value="Login"
							class="btn btn-primary" /> <a class="btn btn-primary"
							href="${pageContext.request.contextPath}/resetPassword"
							role="button">Reset Password</a>
							
					</div>
				 	<%-- <%@ page import="java.net.*"%>
					<%@ page import="java.util.*"%>
					<%@ page import="java.io.*"%>
					<%@ page import="java.util.*"%>


					<%
						InetAddress inetAddress;
							StringBuilder sb = new StringBuilder();
							String ipAddress = "", macAddress = "";
							int i = 0;
							try {
								inetAddress = InetAddress.getLocalHost();
								ipAddress = inetAddress.getHostAddress();
								NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
								byte[] hw = network.getHardwareAddress();
								for (i = 0; i < hw.length; i++)
									sb.append(String.format("%02X%s", hw[i], (i < hw.length - 1) ? "-" : ""));
								macAddress = sb.toString();
							} catch (Exception e) {
								out.print("<br/>" + e.toString());
								macAddress = "-";
							}
							out.print("<br/>IP Address*: " + ipAddress);
							out.print("<br/>MAC Address*: " + macAddress);
							out.print(
									"<br/>*Disclaimer: We use this information for security purposes only. None of this information is shared with third party.");
					%>
					<input type="hidden" name="ipAddress" id="ipAddress"
						value="<%=ipAddress%>" />
					<input type="hidden" name="macAddress" id="macAddress"
						value="<%=macAddress%>"> --%>
				</sf:form>

			</div>
		</div>
	</div>
</body>
</html>
