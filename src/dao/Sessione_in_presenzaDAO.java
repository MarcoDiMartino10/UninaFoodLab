package dao;

import java.sql.*;
import java.util.*;

import dto.*;

public class Sessione_in_presenzaDAO {
	
	// Attributi
	private Connection conn;
	
	// Costruttore
	public Sessione_in_presenzaDAO(Connection conn) {
		this.conn = conn;
	}
	
	// Metodi
	public LinkedList<Sessione_in_presenza> getSessioniPresenza(int idCorso) throws SQLException {
	    LinkedList<Sessione_in_presenza> sessioni = new LinkedList<>();
	    String sql = "SELECT * FROM sessione_in_presenza WHERE id_corso = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, idCorso);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                sessioni.add(new Sessione_in_presenza(rs.getString("luogo"), rs.getInt("max_posti"), rs.getTimestamp("orario_inizio"), rs.getTimestamp("orario_fine"), idCorso, null));
	            }
	        }
	    }
	    return sessioni;
	}

	
	public boolean saveSessione(Sessione_in_presenza sessione) throws SQLException {
		String sql = "INSERT INTO sessione_in_presenza (luogo, orario_inizio, orario_fine, max_posti, id_corso) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, sessione.getLuogo());
			ps.setTimestamp(2, sessione.getOrario_inizio_timestamp());
			ps.setTimestamp(3, sessione.getOrario_fine_timestamp());
			ps.setInt(4, sessione.getMax_posti());
			ps.setInt(5, sessione.getId_Corso());
			ps.executeUpdate();
			return true;
		}
	}
	
}