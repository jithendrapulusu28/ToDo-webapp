package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.entity.TodoDetails;

public class TodoDAO {
	private Connection conn;

	public TodoDAO(Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * Inserts the data entered by the user to the user table.
	 *
	 * @param  title the title of ToDo item.
	 * @param  description the description of ToDo item.
	 * @param  targetDate the end of ToDo item.
	 * @param  status the status of ToDo item.
	 * @param  email the email of current user.
	 * @param  userTable the current user table.
	 * @return True if the details are inserted, False otherwise.
	 */
	public boolean addTodo(String title, String description, Date targetDate, String status, String email,
			String userTable) {

		boolean f = false;

		try {
			String sql = "INSERT INTO public.\"" + userTable
					+ "\" (title, description, \"targetDate\", status, email) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, title);
			ps.setString(2, description);
			ps.setDate(3, targetDate);
			ps.setString(4, status);
			ps.setString(5, email);
			int row = ps.executeUpdate();

			if (row == 1) {
				f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;

	}

	/**
	 * Retrieves  the all ToDo items of current user.
	 *
	 * @param  requestedUserTable the current user table.
	 * @return the TodoDetails List.
	 */
	public List<TodoDetails> getTodo(String requestedUserTable) {

		List<TodoDetails> list = new ArrayList<>();
		TodoDetails t = null;
		try {
			String sql = "select * from public.\"" + requestedUserTable + "\"";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				t = new TodoDetails();
				t.setId(rs.getInt(1));
				t.setTitle(rs.getString(2));
				t.setDescription(rs.getString(3));
				t.setTargetDate(rs.getDate(4));
				t.setStatus(rs.getString(5));
				t.setUserEmail(rs.getString(6));
				list.add(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * Retrieves  the ToDo item based on Id.
	 *
	 * @param  id the id of ToDo item.
	 * @param  requestedUser the current user table.
	 * @return the TodoDetails List.
	 */
	public TodoDetails getTodoById(int id, String requestedUser) {
		TodoDetails t = null;
		try {
			String sql = "select * from public.\"" + requestedUser + "\" where id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				t = new TodoDetails();
				t.setId(rs.getInt(1));
				t.setTitle(rs.getString(2));
				t.setDescription(rs.getString(3));
				t.setTargetDate(rs.getDate(4));
				t.setStatus(rs.getString(5));
				t.setUserEmail(rs.getString(6));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * Updates the ToDo item.
	 *
	 * @param  t the bean class of ToDo items.
	 * @param  userTable the current user table.
	 * @return True if the oDo item is Updated, False otherwise.
	 */
	public boolean updateTodo(TodoDetails t, String userTable) {
		boolean flag = false;
		try {
			String sql = "update public.\"" + userTable
					+ "\" set title=?, description=?, \"targetDate\"=?, status=? where id=?";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, t.getTitle());
			ps.setString(2, t.getDescription());
			ps.setDate(3, t.getTargetDate());
			ps.setString(4, t.getStatus());
			ps.setInt(5, t.getId());

			int row = ps.executeUpdate();
			if (row == 1) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * Deletes the ToDo item.
	 *
	 * @param  id the id of ToDo item to be deleted.
	 * @param  userTable the current user table.
	 * @return True if the oDo item is deleted, False otherwise.
	 */
	public boolean deleteTodo(int id, String userTable) {
		boolean flag = false;
		try {

			String sql = "delete from public.\"" + userTable + "\" where id=?";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);
			int row = ps.executeUpdate();

			if (row == 1) {
				flag= true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}
}
