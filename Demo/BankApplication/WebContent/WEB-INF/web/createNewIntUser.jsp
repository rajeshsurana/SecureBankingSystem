<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.spring.util.StaticConstants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Internal User</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/overcast/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <script>
  $(function() {
    $( "#dob_intUser" ).datepicker();
  });
  </script>
</head>
<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="navbar-brand">
			<img class="img-responsive"
				src="<c:url value="/resources/Images/logotan3.jpg" />" width="20%"/>
		</div>
	</div>
	<div class="container">
		<div class="btn pull-right">
			<a href="${pageContext.request.contextPath}/goToHome" class="btn btn-primary" role="button">Home</a>
			<a href="${pageContext.request.contextPath}/logout" class="btn btn-primary" role="button">Logout</a>
		</div>
		<div class="panel panel-default">
		    <div class="panel-heading">
		        <h2>Create a New Internal User</h2>
		    </div>
			<div class="panel-body">
				<sf:form cssClass="form-horizontal" action="${pageContext.request.contextPath}/CommitNewEmployee.do"
			method="post" commandName="bankUser">
			
				<div class="form-group">
					<label>Username</label>
					<sf:input type="text" cssClass="form-control" placeholder="Enter your User Name Here"
					path="<%=StaticConstants.bankuserUsername%>"
					name="<%=StaticConstants.bankuserUsername%>" style="width: 250px" pattern=".{6,20}" title="6 to 20 characters" maxlength="20"/>
					<sf:errors path="<%=StaticConstants.bankuserUsername%>"
					cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<div class="form-group">
					<label>Email</label>
					<sf:input type="email" cssClass="form-control" placeholder="Enter Your Email Id"
					path="<%=StaticConstants.bankuserEmail%>"
					name="<%=StaticConstants.bankuserEmail%>" style="width: 250px" maxlength="30"/>
					<sf:errors path="<%=StaticConstants.bankuserEmail%>"
					cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<div class="form-group">
					<label>Phone Number</label>
					<sf:input type="text" cssClass="form-control" placeholder="Enter Your Phone Number"
					path="<%=StaticConstants.bankuserPhoneNumber%>"
					name="<%=StaticConstants.bankuserPhoneNumber%>"
					style="width: 250px" pattern="[0-9]{10}" title="Must contain 10 digits only"/>
					<sf:errors path="<%=StaticConstants.bankuserPhoneNumber%>"
					cssStyle="color: #ff0000;"></sf:errors>
				</div>				
				<div class="form-group">
					<label>First Name</label>
					<sf:input type="text" cssClass="form-control" placeholder="Enter Your First Name"
					path="<%=StaticConstants.bankuserFirstName%>"
					name="<%=StaticConstants.bankuserFirstName%>" style="width: 250px" maxlength="20"/>
					<sf:errors path="<%=StaticConstants.bankuserFirstName%>"
					cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<div class="form-group">
					<label>Last Name</label>
					<sf:input type="text" cssClass="form-control" placeholder="Enter Your Last Name"
					path="<%=StaticConstants.bankuserLastName%>"
					name="<%=StaticConstants.bankuserLastName%>" style="width: 250px" maxlength="20"/>
					<sf:errors path="<%=StaticConstants.bankuserLastName%>"
					cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<div class="form-group">
					<label>Date Of Birth</label>
					<sf:input type="text" cssClass="form-control" placeholder="Enter Your Date Of Birth" id="dob_intUser"
					path="<%=StaticConstants.bankuserDateOfBirth%>"
					name="<%=StaticConstants.bankuserDateOfBirth%>"
					style="width: 250px" />
					<sf:errors path="<%=StaticConstants.bankuserDateOfBirth%>"
					cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<div class="form-group">
					<label>SSN</label>
					<sf:input type="text" cssClass="form-control" placeholder="Enter Your SSN"
					path="<%=StaticConstants.bankuserSsn%>"
					name="<%=StaticConstants.bankuserSsn%>" style="width: 250px" pattern=".{8,11}" title="Length in range - 8 to 11"/>
					<sf:errors path="<%=StaticConstants.bankuserSsn%>"
					cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<div class="form-group">
					<label>Residential Address</label>
					<sf:input type="text" cssClass="form-control" placeholder="Enter Your Address"
					path="<%=StaticConstants.bankuserResidentialAddress%>"
					name="<%=StaticConstants.bankuserResidentialAddress%>"
					style="width: 250px" maxlength="150"/>
					<sf:errors path="<%=StaticConstants.bankuserResidentialAddress%>"
					cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<div class="form-group">
					<label>Mailing Address</label>
					<sf:input type="text" cssClass="form-control" placeholder="Enter Your Mailing Address"
					path="<%=StaticConstants.bankuserMailingAddress%>"
					name="<%=StaticConstants.bankuserMailingAddress%>"
					style="width: 250px" maxlength="150"/>
					<sf:errors path="<%=StaticConstants.bankuserMailingAddress%>"
					cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<input name="Submit1" type="submit" value="Create" class="btn btn-primary"/>
				<input name="Reset" type="reset" value="Reset"
				title="WARNING: Resets information Entered" class="btn btn-warning"/>
				</sf:form>
			</div>
		</div>
	</div>

</body>
</html>