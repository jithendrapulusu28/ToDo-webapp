<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
	integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<style>
<%@include file ="./common/form.css"%>
<%@include file ="./common/otp.css"%>
<%@include file ="./common/all.css"%>


</style>

<title>User Authentication</title>
</head>
<body>
	<div class="wrapper">
		<div class="forgot">
			<div class="lock-icon">
				<i class="fa-solid fa-lock"></i>
			</div>
			<h2 class="text-align">Enter OTP</h2>
			<%
			if (session.getAttribute("email") == null) {
				response.sendRedirect("userLogIn.jsp");
			} else {
				String email = (String) session.getAttribute("email");
				String startingSubstr = email.substring(0, 2);
				String endingSubstr = email.substring(email.length() - 11, email.length());
				if (email != null && email != "") {
			%>
			<p class="text-align">An otp has been sent to <%=startingSubstr%>****<%=endingSubstr%></p>
			<%
			}
			%>
		</div>
		<%
		String wrongOtpStatus = (String)request.getAttribute("wrongOtpStatus");
		if (wrongOtpStatus != null) {
		%>
		<div style="color: red; text-align: center;"><%=wrongOtpStatus%></div>
		<%
		}
		}
		%>
		<form id="loginForm" action="./userRegister" method="post">
			<div class="animate-label">
				<input class="form-control" type="text" name="otp" id="otp" required>
				<label for="otp">Please enter OTP</label>
				<line></line>
			</div>
			<div class="flex margin-top">
				<button class="register-label" type="submit">Verify</button>
			</div>
		</form>
	</div>
</body>
</html>