package dao;

import java.sql.*;
import java.util.*;

import dto.*;

public class Sessione_onlineDAO {
	
	// Attributi
	private Connection conn;
	
	// Costruttore
	public Sessione_onlineDAO(Connection conn) {
		this.conn = conn;
	}
	
	// Metodi
	public LinkedList<Sessione_online> getSessioniOnline(int idCorso) {
		LinkedList<Sessione_online> sessioni = new LinkedList<Sessione_online>();
		String sql = """
	            SELECT link, orario_inizio, orario_fine
	            FROM sessione_online
	            WHERE id_corso = ?
	        """;
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCorso);
            try (ResultSet rs = ps.executeQuery()) {
            	while (rs.next()) {
            	    sessioni.add(new Sessione_online(rs.getString("link"), rs.getTimestamp("orario_inizio"), rs.getTimestamp("orario_fine"), idCorso));
            	}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return sessioni;
	}
	
	public boolean saveSessione(Sessione_online sessione) {
		String sql = """
	            INSERT INTO sessione_online (link, orario_inizio, orario_fine, id_corso)
	            VALUES (?, ?, ?, ?)
	        """;
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, sessione.getLink());
			ps.setTimestamp(2, sessione.getOrario_inizio_timestamp());
			ps.setTimestamp(3, sessione.getOrario_fine_timestamp());
			ps.setInt(4, sessione.getId_Corso());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
