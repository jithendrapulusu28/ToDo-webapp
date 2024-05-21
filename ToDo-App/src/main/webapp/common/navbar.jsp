<%@page import="java.sql.PreparedStatement"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.entity.User"%>
<%@ page import="com.db.DBConnect"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="com.db.DBConnect"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
	integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="navbar.css">
<style>
body {
	font-family: "Open Sans", sans-serif;
	color: #444444;
	margin: 0;
}
<%@includefile="navbar.css"%>
</style>
</head>
<body>
	<%
	User user = (User) session.getAttribute("userObj");
	if (user == null) {
		response.sendRedirect("userLogIn.jsp");
	}
	if (user != null) {
	%>
	<header id="header">
		<div class="inner-container ">
			<h2 class="logo">
				<a href="./viewTodo.jsp">Welcome, <span><%=user.getName()%></span></a>
			</h2>
			<nav id="navbar" class="navbar">
				<ul>
					<%
					if (request.getServletPath().equals("/addTodo.jsp")) {
					%>
					<li><a class="nav-link  " href="./viewTodo.jsp">View Todo</a></li>
					<%
					} else if (request.getServletPath().equals("/viewTodo.jsp")) {
					%>
					<li><a class="nav-link  " href="./addTodo.jsp">Add Todo</a></li>
					<%
					} else {
					%>
					<li><a class="nav-link  " href="./viewTodo.jsp">View Todo</a></li>
					<%
					}
					Connection conn = null;
					PreparedStatement ps = null;
					ResultSet rs = null;
					try {
					conn = DBConnect.getConn();
					String sql = "SELECT * FROM public.\"user-details\" WHERE name = ?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, user.getName());
					rs = ps.executeQuery();
					if (rs.next()) {
						String fileName = rs.getString("file_name");
						if (fileName == null) {
					%>
					<img class="user-pic" src="images/profile_img.jpg" onclick="toggleMenu()">
					<%
					} else {
					%>
					<img class="user-pic" src="images/<%=fileName%>" onclick="toggleMenu()">
					<%
					}
					} 
					} catch (SQLException e) {
					e.printStackTrace();
					} finally {
					try {
					if (rs != null)
					rs.close();
					if (ps != null)
					ps.close();
					if (conn != null)
					conn.close();
					} catch (SQLException e) {
					e.printStackTrace();
					}
					}
					%>
					<div class="sub-menu-wrap" id="subMenu">
						<div class="sub-menu">
							<div class="profile-picture-container">
								<div class="profile-label">
									<i class="fa-solid fa-user"></i> 
									<span>Update Profile Picture</span>
								</div>
								<form action="./uploadProfilePictureServlet" method="post" enctype="multipart/form-data">
									<input type="file" name="profilePicture"id="profilePictureInput"> 
									<input type="submit" value="Upload" class="sub-menu-link upload-btn">
								</form>
							</div>
							<hr>
							<a href="./changePassword.jsp" class="sub-menu-link"> 
							   <i class="fa-solid fa-lock"></i>
							   <p>Change password</p>
							</a>
							<hr>
							<a href="./userLogout" class="sub-menu-link">
							    <i class="fa-solid fa-right-from-bracket"></i>
							   <p>LogOut</p>
							</a>
						</div>
					</div>
				</ul>
			</nav>
		</div>
	</header>
	<%
	}
	%>
	<script>
		let subMenu = document.getElementById("subMenu");
		function toggleMenu() {
			subMenu.classList.toggle("open-menu");
		};
	</script>
</body>
</html>