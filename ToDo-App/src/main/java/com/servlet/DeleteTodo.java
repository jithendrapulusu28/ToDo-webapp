package com.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.TodoDAO;
import com.db.DBConnect;

public class DeleteTodo extends HttpServlet {

	/**
	 * 
	 * Handles the GET Requests, deletes the Todo Item from the list.
	 * 
	 * @param request  the HttpServletRequest object that contains the request the
	 *                 client made to the servlet
	 * @param response the HttpServletResponse object that contains the response the
	 *                 servlet returns to the client
	 * @throws ServletException if the request could not be handled
	 * @throws IOException      if an input or output error occurs while the servlet
	 *                          is handling the POST request
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher requestDispatcher = null;

		int id = Integer.parseInt(request.getParameter("id"));

		TodoDAO dao = new TodoDAO(DBConnect.getConn());
		HttpSession session = request.getSession();
		String userTable = (String) session.getAttribute("userTable");
		boolean flag = dao.deleteTodo(id, userTable);

		if (flag) {

			request.setAttribute("deleteTodoSuccess", "Todo deleted Successfully");
			requestDispatcher = request.getRequestDispatcher("viewTodo.jsp");
			requestDispatcher.forward(request, response);

		} else {

			request.setAttribute("deleteTodoFailed", "Failed to delete Todo");
			requestDispatcher = request.getRequestDispatcher("viewTodo.jsp");
			requestDispatcher.forward(request, response);

		}

	}

}