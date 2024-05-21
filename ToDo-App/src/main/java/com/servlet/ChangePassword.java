package com.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.UserDAO;
import com.db.DBConnect;
import com.entity.User;

public class ChangePassword extends HttpServlet {

	 /**
     * 
     * Handles the POST Requests, request to change the password.
     * @param request  the HttpServletRequest object that contains the request
     *                 the client made to the servlet
     * @param response the HttpServletResponse object that contains the response
     *                 the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error occurs while the servlet
     *                          is handling the POST request
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String confPassword = request.getParameter("confirmNewPassword");

		RequestDispatcher requestDispatcher = null;

		User user = (User) session.getAttribute("userObj");


		UserDAO dao = new UserDAO(DBConnect.getConn());

		if (oldPassword != null && newPassword != null && confPassword != null) {

			boolean isOldPassword = oldPassword.equals(user.getPassword());

			if (isOldPassword) {

				if (newPassword.equals(confPassword)) {

					boolean flag = dao.validatePassword(user.getEmail(), newPassword);

					if (flag) {
						request.setAttribute("changePasswordSuccess", "password changed successfully");
						requestDispatcher = request.getRequestDispatcher("userLogIn.jsp");
						requestDispatcher.forward(request, response);

					} else {
						request.setAttribute("changePasswordFailed", "password reset unsuccessfully");
						requestDispatcher = request.getRequestDispatcher("changePassword.jsp");
						requestDispatcher.forward(request, response);
					}

				} else {
					request.setAttribute("PasswordMatchFailed", "Password's didn't match");
					requestDispatcher = request.getRequestDispatcher("changePassword.jsp");
					requestDispatcher.forward(request, response);
				}
			} else {
				request.setAttribute("OldPasswordMatchFailed", "Old Password is Wrong");
				requestDispatcher = request.getRequestDispatcher("changePassword.jsp");
				requestDispatcher.forward(request, response);
			}
		}

	}
}
