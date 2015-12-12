<%@page import="com.spring.util.StaticConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a New External User</title>
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
    $( "#dob_extUser" ).datepicker();
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
		    <div class="panel-heading">
		        <h2>Create a New Customer</h2>
		    </div>
			<div class="panel-body">
			<sf:form
				action="${pageContext.request.contextPath}/CommitNewCustomer.do"
				method="post" commandName="bankUser">
				<div class="form-group">
					<label for="username">Username</label>
					<sf:input type="text" placeholder="Enter User Name"
				    	cssClass="form-control" id="usernameInput"
						path="<%=StaticConstants.bankuserUsername%>"
						name="<%=StaticConstants.bankuserUsername%>" style="width: 250px" pattern=".{6,20}" title="6 to 20 characters" maxlength="20"/>
					<sf:errors path="<%=StaticConstants.bankuserUsername%>"
						cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<div class="form-group">
				    <label for="email">Email</label>
					<sf:input type="email" placeholder="Enter Email Id"
						cssClass="form-control" id="emailInput"
						path="<%=StaticConstants.bankuserEmail%>"
						name="<%=StaticConstants.bankuserEmail%>" style="width: 250px" maxlength="30" />
					<sf:errors path="<%=StaticConstants.bankuserEmail%>"
						cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<div class="form-group">
					<label for="phoneNumber">Phone Number</label>
					<sf:input type="text" placeholder="Enter Phone Number"
						cssClass="form-control" id="phoneNumberInput"
						path="<%=StaticConstants.bankuserPhoneNumber%>"
						name="<%=StaticConstants.bankuserPhoneNumber%>" style="width: 250px" pattern="[0-9]{10}" title="Must contain 10 digits only"/>
					<sf:errors path="<%=StaticConstants.bankuserPhoneNumber%>"
						cssStyle="color: #ff0000;"></sf:errors>
				</div>				
				<div class="form-group">
					<label for="firstName">First Name</label>
					<sf:input type="text" placeholder="Enter First Name"
						cssClass="form-control" id="firstNameInput"
						path="<%=StaticConstants.bankuserFirstName%>"
						name="<%=StaticConstants.bankuserFirstName%>" style="width: 250px" maxlength="20"/>
					<sf:errors path="<%=StaticConstants.bankuserFirstName%>"
						cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<div class="form-group">
					<label for="lastName">Last Name</label>
					<sf:input type="text" placeholder="Enter Last Name"
						cssClass="form-control" id="lastNameInput"
						path="<%=StaticConstants.bankuserLastName%>"
						name="<%=StaticConstants.bankuserLastName%>" style="width: 250px" maxlength="20"/>
					<sf:errors path="<%=StaticConstants.bankuserLastName%>"
						cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<div class="form-group">
	    			<label for="companyLaunchDate">Date of Birth</label>
					<sf:input type="text" placeholder="Enter the Date of Birth (mm/dd/yyyy)" 
						cssClass="form-control" id="dob_extUser"
						path="<%=StaticConstants.bankuserDateOfBirth%>"
						name="<%=StaticConstants.bankuserDateOfBirth%>"
						style="width: 250px" />
					<sf:errors path="<%=StaticConstants.bankuserDateOfBirth%>"
						cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<div class="form-group">
					<label for="ssn">SSN</label>
					<sf:input type="text" placeholder="Enter SSN"
						cssClass="form-control" id="ssn"
						path="<%=StaticConstants.bankuserSsn%>"
						name="<%=StaticConstants.bankuserSsn%>" style="width: 250px" pattern=".{8,11}" title="Length in range - 8 to 11"/>
					<sf:errors path="<%=StaticConstants.bankuserSsn%>"
						cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<div class="form-group">
					<label for="residentialAddress">Residential Address</label>
					<sf:input type="text" placeholder="Enter Residential Address"
						cssClass="form-control" id="residentialAddressInput"
						path="<%=StaticConstants.bankuserResidentialAddress%>"
						name="<%=StaticConstants.bankuserResidentialAddress%>"
						style="width: 250px" maxlength="150"/>
					<sf:errors path="<%=StaticConstants.bankuserResidentialAddress%>"
						cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<div class="form-group">
					<label for="localMailingAddress">Mailing Address</label>
					<sf:input type="text" placeholder="Enter Mailing Address"
						cssClass="form-control" id="mailingAddressInput"
						path="<%=StaticConstants.bankuserMailingAddress%>"
						name="<%=StaticConstants.bankuserMailingAddress%>"
						style="width: 250px" maxlength="150"/>
					<sf:errors path="<%=StaticConstants.bankuserMailingAddress%>"
						cssStyle="color: #ff0000;"></sf:errors>
				</div>
				<input name="Submit1" type="submit" value="Create" class="btn btn-primary" style="width: 150px" />
				<input name="Reset" type="reset" value="Reset" class="btn btn-warning" 
					title="WARNING: Resets information Entered" style="width: 150px" />
			</sf:form>
			</div>
		</div>
	</div>
</body>
</html>