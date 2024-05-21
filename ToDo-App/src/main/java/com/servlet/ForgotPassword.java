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

public class ForgotPassword extends HttpServlet {

	 /**
     * 
     * Handles the POST Requests, checks the user is valid or not and sends OTP for forgot password request.
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

		String email = request.getParameter("email");
		RequestDispatcher requestDispatcher = null;
		HttpSession mySession = request.getSession();
		UserDAO dao = new UserDAO(DBConnect.getConn());

		User user = dao.isRegisteredUser(email); // checking whether email is present in db or not

		if ((email != null || !email.equals("")) && user != null) {

			String otpValue = EmailAuthentication.generateOtp().toString(); // generating the otp

			EmailAuthentication.initiate(); // setting up the properties and getting the session

			String subject = "Password Change Request: OTP Sent for Verification";
			String textBody = "Dear " + user.getName() + "," + "\n\n" + "Your OTP :" + otpValue + "\n\n"

					+ "Thank You,\n" + "Team ToDo";

			EmailAuthentication.emailCreator(subject, textBody, email); // sending the email

			request.setAttribute("otpSentStatus", "OTP is sent to your email id");
			mySession.setAttribute("email", email);
			mySession.setAttribute("otp", otpValue);
			requestDispatcher = request.getRequestDispatcher("enterOTP.jsp");
			requestDispatcher.forward(request, response);
		} else {
			request.setAttribute("message", "Invalid User, you don't have an account.");
			requestDispatcher = request.getRequestDispatcher("forgotPassword.jsp");
			requestDispatcher.forward(request, response);
		}
	}
}
