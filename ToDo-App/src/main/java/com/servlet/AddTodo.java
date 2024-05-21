package com.servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.TodoDAO;
import com.db.DBConnect;
import com.entity.User;

public class AddTodo extends HttpServlet {

	 /**
     * 
     * Handles the POST Requests, adds the ToDo item to the List.
     * @param request  the HttpServletRequest object that contains the request
     *                 the client made to the servlet
     * @param response the HttpServletResponse object that contains the response
     *                 the servlet returns to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error occurs while the servlet
     *                          is handling the POST request
     */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String title = req.getParameter("title");
		String description = req.getParameter("description");
		Date targetDate = Date.valueOf(req.getParameter("targetDate"));
		String status = req.getParameter("status");
		RequestDispatcher requestDispatcher = null;

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("userObj");

		String userTable = (String) session.getAttribute("userTable");


		TodoDAO dao = new TodoDAO(DBConnect.getConn());

		boolean flag = dao.addTodo(title, description, targetDate, status, user.getEmail(), userTable);

		if (flag) {
			req.setAttribute("successMSG", "Todo Added Successfully");
			requestDispatcher = req.getRequestDispatcher("addTodo.jsp");
			requestDispatcher.forward(req, resp);

		} else {
			session.setAttribute("failedMSG", "something wrong on server");
			requestDispatcher = req.getRequestDispatcher("addTodo.jsp");
			requestDispatcher.forward(req, resp);

		}
	}

}
