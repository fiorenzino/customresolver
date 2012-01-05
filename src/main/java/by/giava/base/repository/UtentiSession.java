package by.giava.base.repository;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import by.giava.base.controller.util.DbUtils;
import by.giava.base.model.Utente;

@Named
@Stateless
@LocalBean
public class UtentiSession implements Serializable {

	public UtentiSession() {
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	public List<Utente> getList() {
		Connection con = null;
		Map<String, Utente> utentiMap = new HashMap<String, Utente>();
		try {
			con = DbUtils.getConnection();
			PreparedStatement pageRes = con
					.prepareStatement("SELECT A.USERNAME, A.PASSWORD, A.EMAIL, B.ROLE_NAME FROM user_auth as A left join user_role as B on (A.USERNAME=B.USERNAME)");
			ResultSet rs = pageRes.executeQuery();
			while (rs.next()) {
				String username = rs.getString("USERNAME");
				String password = rs.getString("PASSWORD");
				// String email = rs.getString("EMAIL");
				String role = rs.getString("ROLE_NAME");
				if (utentiMap.containsKey(username)) {
					((Utente) utentiMap.get(username)).addRole(role);
				} else {
					Utente utente = new Utente();
					utente.setUsername(username);
					utente.setPassword(password);
					// utente.setEmail(email);
					utente.setNuovo(false);
					utente.addRole(role);
					utentiMap.put(username, utente);
				}
			}
			List<Utente> lista = new ArrayList<Utente>(utentiMap.values());
			return lista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.closeConnection(con);
		}
		return null;
	}

	public Utente find(String id) {
		Connection con = null;
		Utente utente = null;
		try {
			con = DbUtils.getConnection();
			PreparedStatement pageRes = con
					.prepareStatement("SELECT A.USERNAME, A.PASSWORD, A.EMAIL, B.ROLE_NAME FROM user_auth as A left join user_role as B on (A.USERNAME=B.USERNAME) where A.USERNAME = ?");
			pageRes.setString(1, id);
			ResultSet rs = pageRes.executeQuery();
			while (rs.next()) {
				if (utente == null) {
					utente = new Utente();
					String username = rs.getString("USERNAME");
					String password = rs.getString("PASSWORD");
					// String email = rs.getString("EMAIL");
					utente.setUsername(username);
					utente.setPassword(password);
					// utente.setEmail(email);
					utente.setNuovo(false);
				}
				String role = rs.getString("ROLE_NAME");
				utente.addRole(role);
				// utente.setRuolo(role);
			}
			return utente;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.closeConnection(con);
		}
		return null;
	}

	public Utente save(Utente utente) {
		Connection con = null;
		PreparedStatement pageRes = null;
		int res = 0;
		try {
			con = DbUtils.getConnection();
			pageRes = con
					.prepareStatement("INSERT INTO user_auth (USERNAME ,PASSWORD, EMAIL) VALUES (?, ?, ?)");
			pageRes.setString(1, utente.getUsername());
			pageRes.setString(2, utente.getPassword());
			// pageRes.setString(3, utente.getEmail());
			pageRes.setString(3, utente.getUsername());
			res = pageRes.executeUpdate();
			if (res > 0) {
				if (utente.getRoles() != null) {
					for (String ruolo : utente.getRoles()) {
						pageRes = con
								.prepareStatement("INSERT INTO user_role (USERNAME ,ROLE_NAME) VALUES (?, ?)");
						pageRes.setString(1, utente.getUsername());
						pageRes.setString(2, ruolo);
						res = pageRes.executeUpdate();
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = 0;
		} finally {
			DbUtils.closeConnection(con);
		}
		if (res > 0) {
			utente.setNuovo(false);
			return utente;
		} else
			return null;
	}

	public Utente update(Utente utente) {
		Connection con = null;
		PreparedStatement pageRes = null;
		@SuppressWarnings("unused")
		int res = 0;
		try {
			// update user_auth set PASSWORD = 'sdasda', EMAIL = '32424' where
			// USERNAME = 'pp'
			con = DbUtils.getConnection();
			pageRes = con
					.prepareStatement("UPDATE user_auth set PASSWORD = ? , EMAIL = ? WHERE USERNAME = ? ");
			pageRes.setString(1, utente.getPassword());
			// pageRes.setString(2, utente.getEmail());
			pageRes.setString(2, utente.getUsername());
			pageRes.setString(3, utente.getUsername());
			res = pageRes.executeUpdate();

			pageRes = con
					.prepareStatement("DELETE from user_role WHERE USERNAME = ? ");
			pageRes.setString(1, utente.getUsername());
			res = pageRes.executeUpdate();

			if (utente.getRoles() != null) {
				for (String ruolo : utente.getRoles()) {
					pageRes = con
					.prepareStatement("INSERT INTO user_role (USERNAME ,ROLE_NAME) VALUES (?, ?)");
					pageRes.setString(1, utente.getUsername());
					pageRes.setString(2, ruolo);
					res = pageRes.executeUpdate();
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.closeConnection(con);
		}
		return null;
	}

	public boolean delete(String id) {
		Connection con = null;
		PreparedStatement pageRes = null;
		int res = 0;
		try {
			con = DbUtils.getConnection();

			pageRes = con
					.prepareStatement("DELETE FROM user_role WHERE USERNAME = ?");
			pageRes.setString(1, id);
			res = pageRes.executeUpdate();
			// if (res == 0)
			// return false;

			pageRes = con
					.prepareStatement("DELETE FROM user_auth WHERE USERNAME = ? ");
			pageRes.setString(1, id);
			res = pageRes.executeUpdate();
			if (res == 0)
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = 0;
		} finally {
			DbUtils.closeConnection(con);
		}
		return true;
	}

}
