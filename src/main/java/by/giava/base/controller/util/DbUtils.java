package by.giava.base.controller.util;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import by.giava.base.common.util.JSFUtils;
import by.giava.base.controller.InitHandler;
import by.giava.base.model.Page;

public class DbUtils implements Serializable {

	private static final long serialVersionUID = 1L;
	private static EntityManagerFactory emf = null;

	public static Connection getConnection() {
		InitHandler init = JSFUtils.getBean(InitHandler.class);
		try {
			return init.getDs().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// try {
		// Class.forName("com.mysql.jdbc.Driver");
		// String url = "jdbc:mysql://localhost:3306/colonnella";
		// Connection con = DriverManager.getConnection(url, "colonnella",
		// "colonnella");
		// return con;
		// } catch (ClassNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// return null;
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

	// public static EntityManagerFactory getEMF() {
	// if (emf == null) {
	// emf = Persistence.createEntityManagerFactory("colonnella");
	// }
	// return emf;
	// }

	public static EntityManager getEM() {
		// return getEMF().createEntityManager();
		InitHandler init = JSFUtils.getBean(InitHandler.class);
		return init.getEm();
	}

}
