package com.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ValidateOtp extends HttpServlet {

	 /**
     * 
     * Handles the POST Requests, checks the OTP to confirm user is valid or not tom register
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

		RequestDispatcher dispatcher = null;

		int value = Integer.parseInt(request.getParameter("otp"));
		HttpSession session = request.getSession();

		String otpString = (String) session.getAttribute("otp");
		int otp = Integer.parseInt(otpString);

		if (value == otp) {
			request.setAttribute("status", "success");
			dispatcher = request.getRequestDispatcher("newPassword.jsp");
			dispatcher.forward(request, response);

		}
		else {
			request.setAttribute("wrongOtpStatus", "wrong OTP");
			dispatcher = request.getRequestDispatcher("enterOTP.jsp");
			dispatcher.forward(request, response);

		}

	}

}
