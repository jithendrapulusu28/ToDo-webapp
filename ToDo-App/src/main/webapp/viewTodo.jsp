<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.entity.User"%>
<%@include file="./common/navbar.jsp"%>
<%@include file="./common/footer.html"%>
<%@page import="com.db.DBConnect"%>
<%@page import="com.DAO.TodoDAO"%>
<%@page import="com.entity.TodoDetails"%>
<%@page import="com.entity.User"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
<%@include file ="./common/all.css"%>

body {
	position: static;
	background-color: #f0f1f2;
}

.wrapper {
	width: 60%;
	position: fixed;
	top: 30%;
	left: 50%;
	transform: translate(-50%, -50%);
	overflow-y: auto;
}

.table-container::-webkit-scrollbar {
	width: 12px;
}

.table-container::-webkit-scrollbar-track {
	background: #f1f1f1;
}

.table-container::-webkit-scrollbar-thumb {
	background-color: #888;
	border-radius: 10px;
	border: 3px solid #f1f1f1;
}

.table-container::-webkit-scrollbar-thumb:hover {
	background-color: #800080;
}

table {
	border-collapse: collapse;
	width: 90%;
}

tr {
	border-bottom: 1px solid #ddd;
}

th {
	width: 25%;
}

caption {
	font-size: 26px;
	color: #49494c;
	font-weight: bold;
}

th, td {
	padding: 10px;
	text-align: left;
}

.action-btn i {
	cursor: pointer;
	font-size: 20px;
}

.edit-btn a {
	color: #03C03C;
}

.delete-btn a {
	color: #AA0000;
}

.edit-btn a:hover {
	color: #66FF00;
}

.delete-btn a:hover {
	color: #EF0107;
}

</style>
<body>
	<div class="wrapper">
		<table class="table-container">
			<caption>List of Todo's</caption>
			<thead>
				<tr>
					<th style="width: 10%;">Title</th>
					<th style="width: 30%;">Description</th>
					<th style="width: 25%;">Target Date</th>
					<th style="width: 25%;">Status</th>
					<th colspan="2" style="width: 10%;">Actions</th>
				</tr>
			</thead>
			<%
			List<TodoDetails> todo = null;
			TodoDAO dao = new TodoDAO(DBConnect.getConn());
			User enteredUser = (User) session.getAttribute("userObj");
			String requestedUserTable = (String) session.getAttribute("userTable");
			if (requestedUserTable == null) {
				String[] parts = enteredUser.getEmail().split("@");
				String userTable = parts[0];
				todo = dao.getTodo(userTable);
			} else {
				todo = dao.getTodo(requestedUserTable);
			}
			%>
			<tbody>
				<%
				if(todo.isEmpty()){
					%>
				<tr>
                    <td colspan="6" style="text-align: center; color:red; font-weight:bold;">No Todo's added yet...</td>
                </tr>
					<%
				}else{
					
				for (TodoDetails t : todo) {
				%>
				<tr>
					<td data-th="Supplier Code"><%=t.getTitle() %></td>
					<td><%=t.getDescription() %></td>
					<td data-th="Supplier Name"><%=t.getTargetDate() %></td>
					<%
					Date targetDateStr = t.getTargetDate();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String dateString = sdf.format(targetDateStr);
					Date targetDate = null;
					try {
						targetDate = sdf.parse(dateString);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Date currentDate = new Date();
					String displayText = t.getStatus();
					if (targetDate != null && targetDate.before(currentDate)) {
						if ("pending".equals(displayText)) {
							displayText = "Overdue";
					%>
					<td style="color: red;"><%=displayText%></td>
					<%
					} else {
					%>
					<td style="color: green;"><%=displayText%></td>
					<%
					}

					} else {

					if ("complete".equals(displayText)) {
					%>
					<td style="color: green;"><%=displayText%></td>
					<%
					} else {
					%>
					<td style="color: #106eea;"><%=displayText%></td>
					<%
					}
					}
					%>
					<td class="edit-btn action-btn">
					   <a href="editTodo.jsp?id=<%=t.getId()%>"> <i class="fas fa-edit"></i></a>
					</td>
					<td class="delete-btn action-btn">
				        <a href="./deleteTodo?id=<%=t.getId()%>"><i class="fa-solid fa-trash"></i></a>
					</td>
				</tr>
				<%} 
				}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>