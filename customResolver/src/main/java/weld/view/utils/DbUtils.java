package weld.view.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import weld.model.Page;

public class DbUtils {

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/weldtest";
			Connection con = DriverManager.getConnection(url, "weldtest",
					"weldtest");
			return con;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static void closeConnection(Connection con) {

		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Page getPage(String id) {
		// Get a Statement object
		Statement stmt;
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement pageRes = con
					.prepareStatement("SELECT * from Page p where p.id = ?");
			pageRes.setString(1, id);
			ResultSet rs = pageRes.executeQuery();
			while (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				String description = rs.getString("description");
				Page page = new Page();
				page.setId(id);
				page.setTitle(title);
				page.setContent(content);
				page.setDescription(description);
				return page;
			}// end while loop
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}

		return null;
	}
}
