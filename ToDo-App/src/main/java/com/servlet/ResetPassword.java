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

public class ResetPassword extends HttpServlet {

	 /**
     * 
     * Handles the POST Requests, resets the password.
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
		String newPassword = request.getParameter("newPassword");
		String confPassword = request.getParameter("confirmNewPassword");
		String email = (String) session.getAttribute("email");
		RequestDispatcher requestDispatcher = null;

		UserDAO dao = new UserDAO(DBConnect.getConn());

		if (newPassword != null && confPassword != null && newPassword.equals(confPassword)) {

			boolean flag = dao.validatePassword(email, newPassword);

			if (flag) {
				request.setAttribute("passwordResetStatus", "password reset successfully");
				requestDispatcher = request.getRequestDispatcher("userLogIn.jsp");
				requestDispatcher.forward(request, response);

			} else {
				request.setAttribute("resetFailed", "password reset unsuccessfully");
				requestDispatcher = request.getRequestDispatcher("newPassword.jsp");
				requestDispatcher.forward(request, response);
			}

		} else {
			request.setAttribute("wrongPassword", "Password's didn't match");
			requestDispatcher = request.getRequestDispatcher("newPassword.jsp");
			requestDispatcher.forward(request, response);
		}

	}

}
