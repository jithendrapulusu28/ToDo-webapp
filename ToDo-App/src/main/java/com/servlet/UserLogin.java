package com.servlet;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import com.DAO.UserDAO;
import com.db.DBConnect;
import com.entity.User;

public class UserLogin extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("userLogIn.jsp");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		authenticate(req, resp);
	}
	
	 /**
     * 
     * Authenticate the user to allow or not.
     * @param request  the HttpServletRequest object that contains the request
     *                 the client made to the servlet
     * @param response the HttpServletResponse object that contains the response
     *                 the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error occurs while the servlet
     *                          is handling the POST request
     */
	private void authenticate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		boolean flag = true;
		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		boolean rememberMe = "lsRememberMe".equals(req.getParameter("rememberMe"));
		
		UserDAO dao = new UserDAO(DBConnect.getConn());

		User registeredUser = dao.isRegisteredUser(email);

		if (registeredUser != null) {
			boolean validatedUser = dao.validate(email, password);

			if (validatedUser) {
				String[] parts = email.split("@");

				// The username part is the first element in the array which will be the table name for that user
				String userTable = parts[0];

				if (rememberMe) {
					String token = generateToken();
					setRememberMeCookie(resp, token, registeredUser);
					dao.storeToken(token.concat(registeredUser.getEmail()), registeredUser.getEmail());
				}
				session.setAttribute("userObj", registeredUser);
				session.setAttribute("userTable", userTable);
				resp.sendRedirect("viewTodo.jsp");
			} else {
				req.setAttribute("errorMsg", "Invalid email or password");
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("userLogIn.jsp");
				requestDispatcher.forward(req, resp);
			}
		} else {
			req.setAttribute("errorMsg", "You haven't registered yet...");
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("userLogIn.jsp");
			requestDispatcher.forward(req, resp);
		}

	}
	
	/**
	 * Generates a secure random token 
	 * @return the String of hashcode.
	 */
	private String generateToken() {
		
		String token = UUID.randomUUID().toString();
		String hashedToken = DigestUtils.sha256Hex(token);
		return hashedToken;
	}

	/**
	 * creates and add cookies to the response object.
	 *
	 * @param response the ServletResponse object that contains the response the servlet returns to the client
	 * @param String the token which is a generated hashcode.
	 * @param User the current user
	 */
	private void setRememberMeCookie(HttpServletResponse response, String token, User user) {
		
		Cookie rememberMeCookie = new Cookie("rememberMeToken", token + ":" + user.getEmail()); 
		
		rememberMeCookie.setMaxAge(10 * 24 * 60 * 60);
		rememberMeCookie.setPath("/");
		rememberMeCookie.setSecure(true);
		rememberMeCookie.setHttpOnly(true);
		response.addCookie(rememberMeCookie);
	}

}
