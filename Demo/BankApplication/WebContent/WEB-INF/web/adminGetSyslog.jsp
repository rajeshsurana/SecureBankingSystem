<%@page import="com.spring.util.StaticConstants"%>
<%@page import="com.spring.util.SuspiciousActivityType"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List System Log</title>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>

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
			<div class="panel-heading">System Logs</div>
			<div class="panel-body">
				<sf:form name="SystemAccessLog" id="SystemAccessLog" method="POST">
					<c:choose>
						<c:when test="${empty accessLogList.accessLog}">
							<h4>System Access Log Information</h4>
							<H4>No Access to the System has been Logged!</H4>
						</c:when>
						<c:otherwise>
						<h3>Glossary for the suspicious Activity</h3>
							<table class="table table-condensed">
								<%
									for (SuspiciousActivityType activity : SuspiciousActivityType.values()) {
								%><tr>
									<td><%=activity.getValue()%></td>
									<td><%=activity.name()%></td>
								</tr>
								<%
									}
								%>
							</table>
							<hr/>
							<h2>System Access Log Information</h2>
							<table class="table table-striped">
								<tr>
									<th>accessLogId</th>
									<th>userName</th>
									<th>IPAddress</th>
									<th>deviceId</th>
									<th>accessedOn</th>
									<th>suspiciousActivity</th>
								</tr>
								<c:forEach items="${accessLogList.accessLog}" var="tempaccessLog" varStatus="status">
									<tr>
										<td><c:out value="${tempaccessLog.accessLogId}"></c:out></td>
										<td><c:out value="${tempaccessLog.userName}"></c:out></td>
										<td><c:out value="${tempaccessLog.IPAddress}"></c:out></td>
										<td><c:out value="${tempaccessLog.deviceId}"></c:out></td>
										<td><c:out value="${tempaccessLog.accessedOn}"></c:out></td>
										<td><c:out value="${tempaccessLog.suspiciousActivity}"></c:out></td>
									</tr>
								</c:forEach>
							</table>
						</c:otherwise>
					</c:choose>
				</sf:form>
			</div>
		</div>
	</div>
</body>
</html>