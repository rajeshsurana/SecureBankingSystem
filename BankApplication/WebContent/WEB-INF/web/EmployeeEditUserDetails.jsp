<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.spring.util.StaticConstants"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee - Edit Details of the user</title>

<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>

<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/overcast/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <script>
  $(function() {
    $( "#dob_bankUser" ).datepicker();
  });
  </script>

</head>
<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="navbar-brand">
			<img class="img-responsive"
				src="<c:url value="/resources/Images/logotan3.jpg" />" width="20%" /></a>
		</div>
	</div>
		<div class="container">
		<div class="btn pull-right">
			<a href="${pageContext.request.contextPath}/goToHome" class="btn btn-primary" role="button">Home</a>
			<a href="${pageContext.request.contextPath}/logout" class="btn btn-primary" role="button">Logout</a>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Search Results</div>
			<div class="panel-body">
				<sf:form action="${pageContext.request.contextPath}/UpdateCustomer.do"
					method="post" commandName="bankuser">
					<c:choose>
						<c:when test="${empty bankuser}">
							<H2>The requested User does not Exist or you are not
								authorized to access the user. You are only authorized to update
								users assigned to you</H2>					
							<sf:errors path="<%=StaticConstants.bankuserBankUserID%>"
								cssStyle="color: #ff0000;"></sf:errors>
						</c:when>
						<c:otherwise>	
							<H2>User Details</H2>
							<table class="table">
							<tr>
								<td> Bank User ID </td>
								<td> <label><c:out value="${bankuser.bankUserId}"></c:out></label> </td>
								<td> <sf:input path="<%=StaticConstants.bankuserBankUserID%>"
									class="control form-control"
									name="<%=StaticConstants.bankuserBankUserID%>"
									value="${bankuser.bankUserId}" type="hidden" style="width: 250px" /></td>
							</tr>
							<tr>
								<td> Username</td>
								<td> <label><c:out value="${bankuser.userName}"></c:out></label></td>
								<td> <sf:input path="<%=StaticConstants.bankuserUsername%>"
									class="control form-control"
									name="<%=StaticConstants.bankuserUsername%>"
									value="${bankuser.userName}" type="hidden" style="width: 250px" /></td>
							</tr>
							<tr>
								<td> E-mail</td>
								<td> <sf:input type="email" placeholder="Enter Your Email Id"
									class="control form-control"
									path="<%=StaticConstants.bankuserEmail%>"
									name="<%=StaticConstants.bankuserEmail%>"
									value="${bankuser.email}" style="width: 250px" maxlength="30"/>
									<span style="color: red"><c:out value="${emailMessage} "></c:out></span>
									<sf:errors path="<%=StaticConstants.bankuserEmail%>"
									cssStyle="color: #ff0000;"></sf:errors></td>
								<td> </td>
							</tr>
							<tr>
								<td> Phone Number</td>
								<td> <sf:input type="text" placeholder="Enter Your Phone Number"
									class="control form-control"
									path="<%=StaticConstants.bankuserPhoneNumber%>"
									name="<%=StaticConstants.bankuserPhoneNumber%>"
									value="${bankuser.phoneNumber}" style="width: 250px" pattern="[0-9]{10}" title="Must contain 10 digits only"/>
									<sf:errors path="<%=StaticConstants.bankuserPhoneNumber%>"
									cssStyle="color: #ff0000;"></sf:errors></td>
								<td> </td>
							</tr>
							<tr>
								<td> First Name</td>
								<td> <sf:input type="text" placeholder="Enter Your First Name"
									class="control form-control"
									path="<%=StaticConstants.bankuserFirstName%>"
									name="<%=StaticConstants.bankuserFirstName%>"
									value="${bankuser.firstName}" style="width: 250px" maxlength="20"/>
								<sf:errors path="<%=StaticConstants.bankuserFirstName%>"
									cssStyle="color: #ff0000;"></sf:errors></td>
								<td> </td>
							</tr>
							<tr>
								<td> Last Name</td>
								<td> <sf:input type="text" placeholder="Enter Your Last Name"
									class="control form-control"
									path="<%=StaticConstants.bankuserLastName%>"
									name="<%=StaticConstants.bankuserLastName%>"
									value="${bankuser.lastName}" style="width: 250px" maxlength="20" />
								<sf:errors path="<%=StaticConstants.bankuserLastName%>"
									cssStyle="color: #ff0000;"></sf:errors></td>
								<td> </td>
							</tr>
							<tr>
								<td> Date of Birth(Enter in Format("MM-dd-yyyy"))</td>
								<td> <sf:input type="text" placeholder="Enter Your Date Of Birth" id="dob_bankUser"
									class="control form-control"
									path="<%=StaticConstants.bankuserDateOfBirth%>"
									name="<%=StaticConstants.bankuserDateOfBirth%>"
									value="${bankuser.dateOfBirth}" style="width: 250px" />
								<sf:errors path="<%=StaticConstants.bankuserDateOfBirth%>"
									cssStyle="color: #ff0000;"></sf:errors></td>
								<td> </td>
							</tr>
							<tr>
								<td> Residential Address</td>
								<td> <sf:input type="text" placeholder="Enter Your Address"
									path="<%=StaticConstants.bankuserResidentialAddress%>"
									class="control form-control"
									name="<%=StaticConstants.bankuserResidentialAddress%>"
									value="${bankuser.residentialAddress}" style="width: 250px" maxlength="150" />
								<sf:errors path="<%=StaticConstants.bankuserResidentialAddress%>"
									cssStyle="color: #ff0000;"></sf:errors></td>
								<td> </td>
							</tr>
							<tr>
								<td> Mailing Address</td>
								<td> <sf:input type="text" placeholder="Enter Your Mailing Address"
									path="<%=StaticConstants.bankuserMailingAddress%>"
									class="control form-control"
									name="<%=StaticConstants.bankuserMailingAddress%>"
									value="${bankuser.mailingAddress}" style="width: 250px" maxlength="150"/>
								<sf:errors path="<%=StaticConstants.bankuserMailingAddress%>"
									cssStyle="color: #ff0000;"></sf:errors></td>
								<td> </td>
							</tr>
							</table>
							<script>
								$(function() {
									var dateObj = getJSDate("${bankuser.dateOfBirth}");
									$("#dob_bankUser").datepicker("setDate", dateObj);
								});
							</script>
							<br />
							<br />
							<input name="Submit1" type="submit" value="Update" class="btn btn-primary"
								style="width: 150px" />
							<input name="ResetBtn" type="button" value="Reset" class="btn btn-warning"
								style="width: 150px" />
		
						</c:otherwise>
		
					</c:choose>
				</sf:form>
			</div>
		</div>
	</div>
</body>
</html>