package by.giava.base.controller.util;

import it.coopservice.commons2.utils.JSFUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import by.giava.base.model.Page;
import by.giava.base.producer.InitController;

public class DbUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Connection getConnection() {
		InitController init = JSFUtils.getBean(InitController.class);
		try {
			return init.getDs().getConnection();
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

	public static EntityManager getEM() {
		// return getEMF().createEntityManager();
		InitController init = JSFUtils.getBean(InitController.class);
		return init.getEm();
	}

}
