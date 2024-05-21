<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title> Forgot Password </title>
<style>
<%@
include file ="./common/form.css"%>
<%@include file ="./common/all.css"%>


.wrapper {
	width: 350px;
	height: 400px;
	top: 50%;
	left: 50%;
	padding: 50px;
}

p {
	margin-top: 0;
	margin-bottom: 20px;
	font-size: 14px;
}

a {
	color: #4285f4;
	text-decoration: none;
	margin-top: 30px;
	font-size: 14px;
}

button {
	width: 150px;
}

.list-unstyled {
	font-size: 14px;
}

.list-unstyled li {
	line-height: 20px
}

.margin-top {
	margin-top: 38px;
	justify-content: space-around;
}

.remember input {
	width: 10%;
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

.register-label {
	margin-top: 30px;
	text-align: center;
}

.register-label span {
	font-size: 14px;
}

.small-text {
	font-size: 12px;
	margin-top: 10px;
}
</style>
</head>
<body>
	<div class="wrapper">
		<div class="forgot">
			<h2>Forgot password?</h2>
			<p>Change your password in three easy steps. This will help you to secure your password!</p>
			<ol class="list-unstyled">
				<li>Enter your email address below.</li>
				<li>Our system will send you an OTP to your email</li>
				<li>Enter the OTP on the next page</li>
			</ol>
		</div>
		<%
		Object userStatus = request.getAttribute("message");
		if (userStatus != null) {
		%>
		<div style="color: red; text-align: center;"><%=userStatus%></div>
		<%
		}
		%>
		<form id="loginForm" action="./forgotPassword" method="post">
			<div class="animate-label">
				<input class="form-control" type="text" name="email" id="email-for-pass" required>
				   <label for="email-for-pass">Enter your email address</label>
				<line></line>
			</div>
			<small class="small-text">Enter the registered email address. Then we'll email a OTP to this address.</small>
			<div class="flex margin-top">
				<button class="pulse" type="submit">Get New Password</button>
			</div>
		</form>
		<div class="register-label">
			<span> Didn't have account?</span> 
			<a href="userRegister.jsp" class="register">Register Here</a>
		</div>
	</div>
</body>
</html>