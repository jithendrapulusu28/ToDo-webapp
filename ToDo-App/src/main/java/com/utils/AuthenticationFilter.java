package com.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.UserDAO;
import com.db.DBConnect;
import com.entity.User;
/**
 * AuthenticationFilter is a class implements acts as a security layer.
 * 
 */
public class AuthenticationFilter implements Filter {
	 /**
     * Processes each request before passing it down the filter chain.
     * 
     * @param request  the ServletRequest object that contains the request the client made
     * @param response the ServletResponse object that contains the response the servlet returns to the client
     * @param chain    the FilterChain object to pass control to the next filter or servlet
     * @throws IOException      if an input or output error occurs while the filter is processing the request
     * @throws ServletException if the request could not be handled
     */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		RequestDispatcher requestDispatcher = null;
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String requestedPage = uri.substring(contextPath.length() + 1);

		HttpSession session = request.getSession();

		boolean isAuthenticated = false;

		String[] tokenAndUser = getRememberMeToken(request);

		if (tokenAndUser != null && isValidToken(tokenAndUser[0], tokenAndUser[1])) {
			UserDAO dao = new UserDAO(DBConnect.getConn());
			User user = dao.isRegisteredUser(tokenAndUser[1]);
			session.setAttribute("userObj", user);
			
			if ("userLogIn.jsp".equals(requestedPage) || "userRegister.jsp".equals(requestedPage)) {
				String[] parts = user.getEmail().split("@");
				String userTable = parts[0];
				session.setAttribute("userTable", userTable);
				response.sendRedirect("viewTodo.jsp");

			} else {
				chain.doFilter(request, response); // Continue to the requested page
			}
		}

		else {
			User user = (User) session.getAttribute("userObj");

			if (user == null && ("addTodo.jsp".equals(requestedPage) || "changePassword.jsp".equals(requestedPage)
					|| "editTodo.jsp".equals(requestedPage) || "viewTodo.jsp".equals(requestedPage))) {
				response.sendRedirect("userLogIn.jsp");

			} else {
				if ("addTodo.jsp".equals(requestedPage)) {
					String[] parts = user.getEmail().split("@");
					String userTable = parts[0];
					session.setAttribute("userTable", userTable);
				}
				chain.doFilter(request, response);
			}
		}
	}

	/**
	 * Retrieve the value of the remember me cookie.
	 *
	 * @param  request the ServletRequest object that contains the request the client made
	 * @return the String Array which contains the token and Email.
	 */
	private String[] getRememberMeToken(HttpServletRequest request) {
		// Retrieve the value of the remember me token cookie from the request
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("rememberMeToken".equals(cookie.getName())) {
					return cookie.getValue().split(":");
				}
			}
		}
		return null;
	}

	/**
	 * Checks whether the token is valid or not.
	 *
	 * @param String the string which is generated randomly.
	 * @param String the email which is entered by the user.
	 * @return the boolean whether the token is valid or not.
	 */
	private boolean isValidToken(String hashedCode, String email) {

		UserDAO dao = new UserDAO(DBConnect.getConn());
		String token = hashedCode.concat(email);

		boolean isRegisteredUser = dao.isValidToken(token);

		if (isRegisteredUser) {
			return true;
			
		}
		return false;
		
	}
}