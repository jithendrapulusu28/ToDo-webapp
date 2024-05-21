<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.entity.User"%>
<%@page import="com.entity.TodoDetails"%>
<%@page import="com.DAO.TodoDAO"%>
<%@page import="com.db.DBConnect"%>
<%@include file="./common/navbar.jsp"%>
<%@include file="./common/footer.html"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Change Password</title>
<style>
<%@include file ="./common/all.css"%>

body {
	position: static;
}

.titles {
	margin: 0px;
}

<%@
include file ="./common/form.css"%>
.wrapper h2 {
	font-size: 22px;
}

.animate-label {
	margin-top: 32px;
}
</style>
</head>
<body>
	<div class="wrapper">
		<h2>Wanna change password?</h2>
		<%
		Object changePasswordFailed = request.getAttribute("changePasswordFailed");
		Object PasswordMatchFailed = request.getAttribute("PasswordMatchFailed");
		Object OldPasswordMatchFailed = request.getAttribute("OldPasswordMatchFailed");

		if (changePasswordFailed != null) {
		%>
		<div style="color: red; text-align: center;"><%=changePasswordFailed%></div>
		<%
		}
		if (PasswordMatchFailed != null) {
		%>
		<div style="color: red; text-align: center;"><%=PasswordMatchFailed%></div>
		<%
		}
		if (OldPasswordMatchFailed != null) {
		%>
		<div style="color: red; text-align: center;"><%=OldPasswordMatchFailed%></div>
		<%
		}
		%>
		<form id="loginForm" action="./changePassword" method="post">
			<div class="animate-label">
				<input class="form-control" type="password" name="oldPassword" id="oldPassword" required> 
				    <label for="oldPassword">Old Password</label>
			</div>
			<div class="animate-label">
				<input class="form-control" type="password" name="newPassword" id="newPassword" required> 
				    <label for="newPassword">New Password</label>
			</div>
			<div class="animate-label">
				<input class="form-control" type="password" name="confirmNewPassword" id="confirmNewPassword" required>
				<label for="confirmNewPassword">Confirm New Password</label>
			</div>
			<div class="flex margin-top">
				<button type="submit">change</button>
			</div>
		</form>
	</div>
</body>
</html>