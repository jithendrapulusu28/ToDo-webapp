<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
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
 

.flex {
    justify-content: space-between;
    }
    .register-label a{
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
<title>ToDo Registration</title>
</head>
<body>
	<div class="wrapper">
		<h1 class="titles">Welcome to ToDo App!</h1>
		<p>Get ready to streamline your tasks and boost productivity.Register Here..!</p>
		<%
		Object signupStatus = request.getAttribute("SIGNUPSTATUS");
		Object registrationStatus = request.getAttribute("errorMsg");
		Object nouser = request.getAttribute("nouser");

		if (signupStatus != null) {
		%>
		<div style="color: green; text-align: center;"><%=signupStatus%></div>
		<%
		}
		if (registrationStatus != null) {
		%>
		<div style="color: red; text-align: center;"><%=registrationStatus%></div>
		<%
		}
		if (nouser != null) {
		%>
		<div style="color: red; text-align: center;"><%=nouser%></div>
		<%
		}
		%>
		<form id="loginForm" action="./userRegistrationAuthenticate" method="post">
			<div class="animate-label">
				<input type="text" id="name" name="name" required />
				<label for="name"> Full Name </label>
				<line></line>
			</div>
			<div class="animate-label">
				<input type="email" id="email" name="email" required /> 
				<label for="email"> Email </label>
				<line></line>
			</div>
			<div class="animate-label">
				<input type="password" id="password" name="password" required /> 
				<label for="password"> Password </label>
				<line></line>
			</div>
			<div class="flex margin-top">
				<div class="register-label">
					<a href="userLogIn.jsp" class="register">Back to log in</a>
				</div>
			    <button>Sign Up</button>
			</div>
		</form>
	</div>
</body>
</html>