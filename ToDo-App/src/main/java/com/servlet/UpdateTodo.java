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
import com.entity.TodoDetails;

public class UpdateTodo extends HttpServlet {

	 /**
     * 
     * Handles the POST Requests, updates the details in the ToDo items. 
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

		RequestDispatcher requestDispatcher = null;

		int id = Integer.parseInt(request.getParameter("id"));

		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Date targetDate = Date.valueOf(request.getParameter("targetDate"));
		String status = request.getParameter("status");

		TodoDAO dao = new TodoDAO(DBConnect.getConn());
		TodoDetails td = new TodoDetails();
		td.setId(id);
		td.setTitle(title);
		td.setDescription(description);
		td.setTargetDate(targetDate);
		td.setStatus(status);
		HttpSession session = request.getSession();
		String userTable = (String) session.getAttribute("userTable");
		boolean flag = dao.updateTodo(td, userTable);

		if (flag) {

			request.setAttribute("updateTodoSuccess", "Todo Updated Successfully");
			requestDispatcher = request.getRequestDispatcher("editTodo.jsp");
			requestDispatcher.forward(request, response);

		} else {

			request.setAttribute("updateTodoFailed", "Failed to update Todo");
			requestDispatcher = request.getRequestDispatcher("editTodo.jsp");
			requestDispatcher.forward(request, response);

		}

	}

}
