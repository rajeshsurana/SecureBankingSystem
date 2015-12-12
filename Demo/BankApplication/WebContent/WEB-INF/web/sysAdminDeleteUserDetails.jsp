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
<title>Admin - Edit Details of the user</title>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>
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
				src="<c:url value="/resources/Images/logotan3.jpg" />" width="20%" />
		</div>
	</div>
	<div class="container">
		<div class="btn pull-right">
			<a href="${pageContext.request.contextPath}/goToHome" class="btn btn-primary" role="button">Home</a>
			<a href="${pageContext.request.contextPath}/logout" class="btn btn-primary" role="button">Logout</a>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">User Deletion Request</div> 
			<div class="panel-body">
				<sf:form action="${pageContext.request.contextPath}/sysAdminDeleteUserProfileConfirm.do"
					method="post" commandName="bankuser">
					<c:choose>
						<c:when test="${empty bankuser}">
							<H2>The requested User does not Exist or you are not
								authorized to access the user. You are only authorized to update
								Internal Employees</H2>
							<sf:errors path="<%=StaticConstants.bankuserBankUserID%>"
								cssStyle="color: #ff0000;"></sf:errors>
						</c:when>
						<c:otherwise>
							<H2>User Information</H2>
							<table class="table table-responsive">
								<tr>
									<td> Bank User Id </td>
									<td> <label><c:out value="${bankuser.bankUserId}"></c:out></label> </td>
									<td> <sf:input path="<%=StaticConstants.bankuserBankUserID%>"
											class="control form-control"
											name="<%=StaticConstants.bankuserBankUserID%>"
											value="${bankuser.bankUserId}" type="hidden" style="width: 250px" /> </td>
								</tr>
								<tr>
									<td> Bank user Name </td>
									<td> <label><c:out value="${bankuser.userName}"></c:out></label> </td>
									<td> <sf:input path="<%=StaticConstants.bankuserUsername%>"
											class="control form-control"
											name="<%=StaticConstants.bankuserUsername%>"
											value="${bankuser.userName}" type="hidden" style="width: 250px" /> </td>
								</tr>
								<tr>
									<td> Email </td>
									<td> <label><c:out value="${bankuser.email}"></c:out></label> </td>
									<td> <sf:input type="hidden" placeholder="Enter Your Email Id"
											class="control form-control"
											path="<%=StaticConstants.bankuserEmail%>"
											name="<%=StaticConstants.bankuserEmail%>"
											value="${bankuser.email}" style="width: 250px" />
										<sf:errors path="<%=StaticConstants.bankuserEmail%>"
											cssStyle="color: #ff0000;"></sf:errors></td>
								</tr>
								<tr>
									<td> Phone Number </td>
									<td> <label><c:out value="${bankuser.phoneNumber}"></c:out></label> </td>
									<td> <sf:input type="hidden" placeholder="Enter Your Phone Number"
											class="control form-control"
											path="<%=StaticConstants.bankuserPhoneNumber%>"
											name="<%=StaticConstants.bankuserPhoneNumber%>"
											value="${bankuser.phoneNumber}" style="width: 250px" />
										<sf:errors path="<%=StaticConstants.bankuserPhoneNumber%>"
											cssStyle="color: #ff0000;"></sf:errors> </td>
								</tr>
								<tr>
									<td>First Name</td>
									<td><label><c:out value="${bankuser.firstName}"></c:out></label></td>
									<td> <sf:input type="hidden" placeholder="Enter Your First Name"
											class="control form-control"
											path="<%=StaticConstants.bankuserFirstName%>"
											name="<%=StaticConstants.bankuserFirstName%>"
											value="${bankuser.firstName}" style="width: 250px" />
										<sf:errors path="<%=StaticConstants.bankuserFirstName%>"
											cssStyle="color: #ff0000;"></sf:errors> </td>
								</tr>
								<tr>
									<td> Last Name</td>
									<td> <label><c:out value="${bankuser.lastName}"></c:out></label> </td>
									<td> <sf:input type="hidden" placeholder="Enter Your Last Name"
											class="control form-control"
											path="<%=StaticConstants.bankuserLastName%>"
											name="<%=StaticConstants.bankuserLastName%>"
											value="${bankuser.lastName}" style="width: 250px" />
										<sf:errors path="<%=StaticConstants.bankuserLastName%>"
											cssStyle="color: #ff0000;"></sf:errors> </td>
								</tr>
								<tr>
									<td>Residential Address</td>
									<td><label><c:out value="${bankuser.residentialAddress}"></c:out></label></td>
									<td><sf:input type="hidden" placeholder="Enter Your Address"
											path="<%=StaticConstants.bankuserResidentialAddress%>"
											class="control form-control"
											name="<%=StaticConstants.bankuserResidentialAddress%>"
											value="${bankuser.residentialAddress}" style="width: 250px" />
										<sf:errors path="<%=StaticConstants.bankuserResidentialAddress%>"
											cssStyle="color: #ff0000;"></sf:errors></td>
								</tr>
								<tr>
									<td>Mailing Address</td>
									<td> <label><c:out value="${bankuser.mailingAddress}"></c:out></label> </td>
									<td> <sf:input type="hidden" placeholder="Enter Your Mailing Address"
											path="<%=StaticConstants.bankuserMailingAddress%>"
											class="control form-control"
											name="<%=StaticConstants.bankuserMailingAddress%>"
											value="${bankuser.mailingAddress}" style="width: 250px" />
										<sf:errors path="<%=StaticConstants.bankuserMailingAddress%>"
											cssStyle="color: #ff0000;"></sf:errors> </td>
								</tr>
							</table>
							<script>
								$(function() {
									var dateObj = getJSDate("${bankuser.dateOfBirth}");
									$("#dob_bankUser").datepicker("setDate", dateObj);
								});
							</script>
							<input class="btn btn-danger" name="Submit1" type="submit" value="Delete"	style="width: 150px" />
						</c:otherwise>
					</c:choose>
				</sf:form>
			</div>
		</div>
	</div>
</body>
</html>