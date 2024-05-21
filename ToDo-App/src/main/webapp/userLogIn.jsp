<%@page import="java.sql.Connection"%>
<%@page import="com.entity.TodoDetails"%>
<%@page import="com.db.DBConnect"%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>ToDO Home</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style>
<%@include file ="./common/form.css"%>
body {
	font-family: 'Helvetica', sans-serif;
	background-image: url('images/todo-img.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: 100% 100%;
} 
 
.wrapper {
	width: 350px;
	height: 400px;
	top: 50%;
	left: 75%;
	transform: translate(-50%, -50%);
	padding: 50px;
}

.login-link-container {
	margin-top: 20px;
	justify-content: space-between;
	align-items: center;
}

#remember-container {
	align-items: flex-end;
}

#remember-container a {
	margin-top: 0;
}

.remember label {
	margin-left: 3px;
}

.register-label span {
	font-size: 14px;
}

input[type="checkbox"] {
	width: 10%;
}

.login-link-container a {
	text-decoration: none;
	margin-top: 30px;
	font-size: 14px;
}

p {
	margin-top: 0;
	margin-bottom: 20px;
	font-size: 14px; 
	line-height: 22px;
}
</style>
</head>
<body>
	<div class="wrapper">
		<h1 class="titles">Welcome Back!</h1>
		<p>Ready to dive back into productivity? Just log in with your credentials.</p>
		<%
		Object loginStatus = request.getAttribute("errorMsg");
		if (loginStatus != null) {
		%>
		<div style="color: red; text-align: center;"><%=loginStatus%></div>
		<%
		}
		%>
		<%
		Object logoutStatus = request.getAttribute("logout");
		if (logoutStatus != null) {
		%>
		<div style="color: green; text-align: center;"><%=logoutStatus%></div>
		<%
		}
		Object passwordResetStatus = request.getAttribute("passwordResetStatus");
		if (passwordResetStatus != null) {
		%>
		<div style="color: green; text-align: center;"><%= passwordResetStatus %></div>
		<%
		}
		Object changePasswordSuccess = request.getAttribute("changePasswordSuccess");
		if (changePasswordSuccess != null) {
		%>
		<div style="color: green; text-align: center;"><%=changePasswordSuccess%></div>
		<%
		}
		Object inValidUser = request.getAttribute("inValidUser");
		if (inValidUser != null) {
		%>
		<div style="color: red; text-align: center;"><%=inValidUser%></div>
		<%
		}
		%>
		<form id="loginForm" action="./userLogin" method="post">
			<div class="animate-label">
				<input type="email" id="email" name="email" required /> 
				<label for="email"> Email</label>
				<line></line>
			</div>
			<div class="animate-label">
				<input type="password" id="password" name="password" required /> 
				<label for="password"> Password </label>
				<line></line>
			</div>
			<div class="flex login-link-container" id="remember-container">
				<div class="flex remember">
					<input type="checkbox" value="lsRememberMe" name="rememberMe" id="rememberMe">
					    <label for="rememberMe">Remember me</label>
				</div>
				<a href="forgotPassword.jsp"> Forgot Password? </a>
			</div>
			<div class="flex login-link-container">
				<div class="register-label">
					<span> Didn't have account?</span> 
					<a href="userRegister.jsp" class="register">Register Here</a>
				</div>
				<button class="pulse">Sign In</button>
			</div>
		</form>
	</div>
</body>
</html>