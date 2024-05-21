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
import com.utils.EmailAuthentication;

public class UserRegister extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		register(req, resp);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("userRegister.jsp");
	}

	 /**
     * 
     * Register the user after Authentication.
     * @param request  the HttpServletRequest object that contains the request
     *                 the client made to the servlet
     * @param response the HttpServletResponse object that contains the response
     *                 the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error occurs while the servlet
     *                          is handling the POST request
     */
	private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		UserDAO dao = new UserDAO(DBConnect.getConn());

		int value = Integer.parseInt(req.getParameter("otp"));
		HttpSession session = req.getSession();
		String name = (String) session.getAttribute("name");
		String email = (String) session.getAttribute("email");
		String password = (String) session.getAttribute("password");

		int otpValue = Integer.parseInt((String) session.getAttribute("registrationOtpValue"));

		if (value == otpValue) {

			String[] parts = email.split("@");

			String userTable = parts[0];

			boolean flag = dao.registerUser(name, email, password, userTable);

			if (flag) {
				req.setAttribute("SIGNUPSTATUS", "Registered Successfully!");
				session.setAttribute("userTable", userTable);
				EmailAuthentication.initiate();
				String subject = "Thank You for Registering with Our To-Do App!";
				String textBody = "Dear " + name + ",\n\n"
						+ "We wanted to take a moment to extend our heartfelt thanks for registering with our To-Do App! \n\n"
						+ "Your decision to join our community means a lot to us, and we're thrilled to have you on board. We're committed to providing you with a seamless experience to help you stay organized and productive. \n \n"
						+ "Your feedback and suggestions are invaluable to us, so please don't hesitate to reach out with any questions, ideas, or concerns.\n\n"
						+ "Once again, thank you for choosing our To-Do App. We're excited to see how it helps you accomplish your goals and tasks effectively!\n\n"

						+ "Best regards,\n" + "Team ToDo";

				EmailAuthentication.emailCreator(subject, textBody, email); 
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("userRegister.jsp");
				requestDispatcher.forward(req, resp);

			} else {

				req.setAttribute("wrongOtpStatus", "Wrong OTP!");
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("UserAuthentication.jsp");
				requestDispatcher.forward(req, resp);
			}

		} else {
			req.setAttribute("errorMsg", "You haven't registered yet...");
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("userRegister.jsp");
			requestDispatcher.forward(req, resp);
		}

	}
}
