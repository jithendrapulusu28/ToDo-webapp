package com.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

	private static Connection conn;
	private static String url = "jdbc:postgresql://localhost:5432/todo";
	private static String name = "postgres";
	private static String pass = "admin";

	public static Connection getConn() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, name, pass);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
