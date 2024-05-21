package com.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.DAO.UserDAO;
import com.db.DBConnect;
import com.entity.User;

@MultipartConfig
public class UploadProfilePictureServlet extends HttpServlet {

	/**
     * 
     * Handles the POST Requests, handle file uploads using a temporary directory
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

		Part part = request.getPart("profilePicture");
		String fileName = part.getSubmittedFileName();
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("userObj");

		UserDAO dao = new UserDAO(DBConnect.getConn());

		boolean isUploaded = dao.uploadProfilePicture(fileName, user.getEmail());

		if (isUploaded) {
			user = dao.isRegisteredUser(user.getEmail());

			// Use a temporary directory for file uploads
			String uploadPath = getServletContext().getRealPath("") + File.separator + "temp" + File.separator;
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			// Write the uploaded file to the temporary directory
			try (FileOutputStream fos = new FileOutputStream(uploadPath + fileName);
					InputStream is = part.getInputStream()) {
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = is.read(buffer)) != -1) {
					fos.write(buffer, 0, bytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			session.setAttribute("filename", user.getFileName());
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("viewTodo.jsp");
			requestDispatcher.forward(request, response);
		}
	}

}
