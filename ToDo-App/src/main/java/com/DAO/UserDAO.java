package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.entity.User;

public class UserDAO {

	private Connection conn;

	public UserDAO(Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * inserts the details of user into the db.
	 *
	 * @param  name the name of the current user.
	 *  @param  email the email of the current user.
	 * @param  password the password of the current user.
	 * @param  name of the current user table.
	 * @return True if the details are inserted, False otherwise.
	 */
	public boolean registerUser(String name, String email, String password, String userTable) {
		boolean f = false;

		try {
			String sql = "insert into public.\"user-details\"(name,email,password) values(?,?,?)";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, password);

			int row = ps.executeUpdate();
			if (row == 1) {
				try {
					String query = "CREATE TABLE public.\"" + userTable + "\" (" + "id SERIAL PRIMARY KEY, "
							+ "\"title\" TEXT, " + "\"description\" TEXT, " + "\"targetDate\" DATE, "
							+ "\"status\" TEXT, " + "\"email\" TEXT REFERENCES public.\"user-details\"(email));";

					PreparedStatement psstmt = conn.prepareStatement(query);
					int row1 = psstmt.executeUpdate();

				} catch (Exception e) {
					e.printStackTrace();

				}
				f = true;
			} else {

				f = false;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return f;
	}

	/**
	 * checks the email and password is correct or not.
	 *
	 * @param  email the email of the current user.
	 * @param  password the new password of the current user.
	 * @return True if the email and password is correct, False otherwise.
	 */
	public boolean validate(String email, String password) {
		boolean flag = false;

		try {
			String sql = "select * from public.\"user-details\" where email=? and password=?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * checks the user is registered or not.
	 *
	 * @param  email the email of the current user.
	 * @return User if the user is available, Null otherwise.
	 */
	public User isRegisteredUser(String email) {
		User user = null;

		try {
			String sql = "select * from public.\"user-details\" where email=?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setPassword(rs.getString(4));
				user.setFileName(rs.getString(6));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * Updates the password.
	 *
	 * @param  email the email of the current user.
	 * @param  password the new password of the current user.
	 * @return True if the password is updated, False otherwise.
	 */
	public boolean validatePassword(String email, String password) {

		boolean f = false;
		try {
			String sql = "update public.\"user-details\"  set password = ? where email=?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, password);
			ps.setString(2, email);

			int row = ps.executeUpdate();
			if (row == 1) {
				f = true;
			} else {

				f = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return f;
	}
	/**
	 * Updates the Token in the db
	 *
	 * @param  token the token of the current user.
	 * @param  email the email of the current user.
	 */
	public void storeToken(String token, String email) {
		try {
			String sql = "update public.\"user-details\"  set token = ? where email=?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, token);
			ps.setString(2, email);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * checks whether token is valid or not.
	 *
	 * @param  token the token of the current user.
	 * @return True if the token is valid, False otherwise.
	 */
	public boolean isValidToken(String token) {
		boolean flag = false;
		try {
			String sql = "select token from public.\"user-details\"  where token=?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, token);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				flag = true;

			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}

	/**
	 * Updates the file name in the db.
	 *
	 * @param  fileName the name of the user profile picture.
	 * @param  email the email of the current user.
	 * @return True if the file name is updated, False otherwise.
	 */
	public boolean uploadProfilePicture(String fileName, String email) {
		
		boolean flag = false;

		try {
			String sql = "update public.\"user-details\" set file_name=? where email=?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, fileName);
			ps.setString(2, email);

			int row = ps.executeUpdate();
			if (row == 1) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
