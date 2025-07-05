package dao;

import java.sql.*;
import java.util.*;

import dto.*;

public class Sessione_onlineDAO {
	
	// Attributo
	private Connection conn;
	
	// Costruttore
	public Sessione_onlineDAO(Connection conn) {
		this.conn = conn;
	}
	
	// Recupera le sessioni online di un corso dal database
	public LinkedList<Sessione_online> getSessioniOnline(int idCorso) throws SQLException {
	    LinkedList<Sessione_online> sessioni = new LinkedList<>();
	    String sql = "SELECT * FROM sessione_online WHERE id_corso = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, idCorso);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                sessioni.add(new Sessione_online(rs.getString("link"), rs.getTimestamp("orario_inizio"), rs.getTimestamp("orario_fine"), idCorso));
	            }
	        }
	    }
	    return sessioni;
	}
	
	// Inserisce una nuova sessione online nel database
	public void saveSessioneOnline(Sessione_online sessione) throws SQLException {
		String sql = "INSERT INTO sessione_online (link, orario_inizio, orario_fine, id_corso) VALUES (?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, sessione.getLink());
			ps.setTimestamp(2, sessione.getOrario_inizio_timestamp());
			ps.setTimestamp(3, sessione.getOrario_fine_timestamp());
			ps.setInt(4, sessione.getId_Corso());
			ps.executeUpdate();
		}
	}
	
}
