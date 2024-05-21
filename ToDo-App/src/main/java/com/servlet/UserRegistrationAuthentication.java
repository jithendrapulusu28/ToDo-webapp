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
import com.utils.EmailAuthentication;

public class UserRegistrationAuthentication extends HttpServlet {
	 /**
     * 
     * Handles the POST Requests, Sends the OTP to authenticate the user.
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

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		UserDAO dao = new UserDAO(DBConnect.getConn());
		User user = dao.isRegisteredUser(email);

		if (user != null) {
			request.setAttribute("errorMsg", "You have already registered...");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("userRegister.jsp");
			requestDispatcher.forward(request, response);
		} else {

			session.setAttribute("name", name);
			session.setAttribute("email", email);
			session.setAttribute("password", password);

			String otpValue = EmailAuthentication.generateOtp().toString(); // generating the otp

			session.setAttribute("registrationOtpValue", otpValue);
			EmailAuthentication.initiate(); // setting up the properties and getting the session

			String subject = "Authentication to register for the To-Do App";
			String textBody = "Dear " + name + "," + "\n Please use the below OTP to register for the To-Do App"
					+ "\n\n"

					+ "\n\n" + "Your OTP :" + otpValue + "\n\n"

					+ "Thank You,\n" + "Team ToDo.";

			EmailAuthentication.emailCreator(subject, textBody, email); // sending the email

			response.sendRedirect("UserAuthentication.jsp");

		}

	}
}
