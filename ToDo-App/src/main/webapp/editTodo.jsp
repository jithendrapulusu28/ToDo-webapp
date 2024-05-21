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
<title>Edit ToDo</title>
<style>
<%@include file ="./common/all.css"%>

body {
position:static;
background-color: #f0f1f2;
}
.titles{margin:0px;}
<%@include file="./common/form.css"%>
</style>
</head>
<body>
	<div class="wrapper">
		<h1 class="titles">Update Todo</h1>
		<%
		int id = Integer.parseInt(request.getParameter("id"));
		TodoDAO dao = new TodoDAO(DBConnect.getConn());
		String requestedUser = (String) session.getAttribute("userTable");
		TodoDetails t = dao.getTodoById(id, requestedUser);

		String updateTodoSuccess = (String) request.getAttribute("updateTodoSuccess");
		String updateTodoFailed = (String) request.getAttribute("updateTodoFailed");

		if (updateTodoSuccess != null) {
		%>
		<p style="color: green; text-align: center; margin: 10px 0px 0px;"><%=updateTodoSuccess%></p>
		<%
		}
		if (updateTodoFailed != null) {
		%>
		<p style="color: red; text-align: center; margin: 10px 0px 0px;"><%=updateTodoFailed%></p>
		<%
		}
		%>
		<form id="loginForm" action="./updateTodo" method="post">
			<input type="hidden" value="<%=t.getId()%>" name="id" />
			<div class="animate-label">
				<input type="text" id="title" name="title" placeholder="Title" value="<%=t.getTitle()%>" required />
				<line></line>
			</div>
			<div class="animate-label">
				<input type="text" id="description" name="description" placeholder="Description" value="<%=t.getDescription()%>" required />
				<line></line>
			</div>
			<div class="animate-label">
				<input type="date" id="targetDate" name="targetDate" placeholder="Target Date" value="<%=t.getTargetDate()%>" required />
				<line></line>
			</div>
			<div class="animate-dropdown">
				<select id="inputState" name="status" required>
					<%
					if ("pending".equals(t.getStatus())) {
					%>
					<option value="pending">Pending</option>
					<option value="complete">Complete</option>

					<%
					} else {
					%>
					<option value="complete">Complete</option>
					<option value="pending">Pending</option>
					<%
					}
					%>
				</select>
				<div class="line"></div>
			</div>
			<div class="flex margin-top">
				<button>Update</button>
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