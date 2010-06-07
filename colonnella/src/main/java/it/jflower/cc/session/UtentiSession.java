package it.jflower.cc.session;

import it.jflower.cc.par.Utente;
import it.jflower.cc.utils.DbUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
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
					.prepareStatement("SELECT A.USERNAME, A.PASSWORD, B.ROLE_NAME FROM user_auth as A left join user_role as B on (A.USERNAME=B.USERNAME)");
			ResultSet rs = pageRes.executeQuery();
			while (rs.next()) {
				String username = rs.getString("USERNAME");
				String password = rs.getString("PASSWORD");
				String role = rs.getString("ROLE_NAME");
				if (utentiMap.containsKey(username)) {
					((Utente) utentiMap.get(username)).addRole(role);
				} else {
					Utente utente = new Utente();
					utente.setUsername(username);
					utente.setPassword(password);
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
					.prepareStatement("SELECT A.USERNAME, A.PASSWORD, B.ROLE_NAME FROM user_auth as A left join user_role as B on (A.USERNAME=B.USERNAME) where A.USERNAME = ?");
			pageRes.setString(1, id);
			ResultSet rs = pageRes.executeQuery();
			while (rs.next()) {
				if (utente == null) {
					utente = new Utente();
					String username = rs.getString("USERNAME");
					String password = rs.getString("PASSWORD");
					utente.setUsername(username);
					utente.setPassword(password);
				}
				String role = rs.getString("ROLE_NAME");
				utente.addRole(role);
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
					.prepareStatement("INSERT INTO user_auth (USERNAME ,PASSWORD) VALUES (?, ?)");
			pageRes.setString(1, utente.getUsername());
			pageRes.setString(2, utente.getPassword());
			res = pageRes.executeUpdate();
			if (res > 0) {
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

	public Utente update(Utente utente) {
		Connection con = null;
		PreparedStatement pageRes = null;
		int res = 0;
		try {
			con = DbUtils.getConnection();
			pageRes = con
					.prepareStatement("INSERT INTO user_auth (USERNAME ,PASSWORD) VALUES (?, ?)");
			pageRes.setString(1, utente.getUsername());
			pageRes.setString(2, utente.getPassword());
			res = pageRes.executeUpdate();
			if (res > 0) {
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
}
