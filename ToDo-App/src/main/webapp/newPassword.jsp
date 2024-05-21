<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style>
<%@include file ="./common/form.css"%>
<%@include file ="./common/all.css"%>


.wrapper {
	width: 350px;
	height: 230px;
	top: 50%;
	padding: 50px;
}

p {
	margin-top: 0;
	margin-bottom: 20px;
	font-size: 14px;
}

.list-unstyled {
	font-size: 14px;
}

.margin-top {
	align-items: center;
}
</style>
<title>Reset Password</title>
</head>
<body>
	<div class="wrapper">
		<h2>Reset password?</h2>
		<%
		if (request.getAttribute("status") == null) {
			request.setAttribute("inValidUser", "Invalid User, you don't have an account.");
			response.sendRedirect("userLogIn.jsp");
		}
		String wrongPassword = (String)request.getAttribute("wrongPassword");
		String resetFailed = (String)request.getAttribute("resetFailed");
		if (wrongPassword != null) {
		%>
		<div style="color: red; text-align: center;"><%=wrongPassword%></div>
		<%
		}
		if (resetFailed != null) {
		%>
		<div style="color: red; text-align: center;"><%=resetFailed%></div>
		<%
		}
		%>
		<form id="loginForm" action="./resetPassword" method="post">
			<div class="animate-label">
				<input class="form-control" type="password" name="newPassword" id="newPassword" required> 
				<label for="newPassword">New Password</label>
			</div>
			<div class="animate-label">
				<input class="form-control" type="password" name="confirmNewPassword" id="confirmNewPassword" required>
				<label for="confirmNewPassword">Confirm New Password</label>
			</div>
			<div class="flex margin-top">
				<button type="submit">Reset</button>
			</div>
		</form>
	</div>
</body>
</html>