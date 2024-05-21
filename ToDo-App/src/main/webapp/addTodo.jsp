<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="com.entity.User"%>


<%@include file="./common/navbar.jsp"%>
<%@include file="./common/footer.html"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add ToDo</title>
<style>
<%@include file ="./common/all.css"%>

body {
	position: static;
	background-color: #f0f1f2;
}
.titles{margin:0px;}

<%@include file ="./common/form.css"%>
a {
	margin-top: 0px;
}
</style>
</head>
<body>
	<div class="wrapper">
		<h1 class="titles">Add Todo</h1>
		<%
		String todoSuccessStatus = (String) request.getAttribute("successMSG");
		String todoFailedStatus = (String) request.getAttribute("failedMSG");

		if (todoSuccessStatus != null) {
		%>
		<p style="color: green; text-align: center; margin: 10px 0px 0px;"><%=todoSuccessStatus%></p>
		<%
		}
		if (todoFailedStatus != null) {
		%>
		<p style="color: red; text-align: center; margin: 10px 0px 0px;"><%=todoFailedStatus%></p>
		<%
		}
		%>
		<form id="loginForm" action="./addTodo" method="post">
			<div class="animate-label">
				<input type="text" id="title" name="title" required />
					<label for="email"> Title</label>
				<line></line>
			</div>
			<div class="animate-label">
				<input type="text" id="description" name="description"
					 required />
					<label for="description"> Description</label>
				<line></line>
			</div>
			<div class="animate-label">
				<input type="date" id="targetDate" name="targetDate" placehodler="Target Date" required />		
				<line></line>
			</div>
			<div class="animate-dropdown">
				<select id="inputState" name="status" required>
					<option value="" disabled selected>--select the status--</option>
					<option value="pending">Pending</option>
					<option value="complete">Complete</option>
				</select>
				<div class="line"></div>
			</div>
			<div class="flex margin-top">
				<button class="pulse">Add</button>
			</div>
		</form>
	</div>

	<script>
		document.addEventListener("DOMContentLoaded", function() {
			const selectElement = document.querySelector('#inputState');
			const labelElement = document
					.querySelector('label[for=inputState]');

			selectElement.addEventListener('change', function() {
				if (selectElement.value !== '') {
					labelElement.classList.add('active');
				} else {
					labelElement.classList.remove('active');
				}
			});
		});
	</script>
</body>
</html>