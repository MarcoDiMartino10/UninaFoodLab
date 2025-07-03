package dao;

import java.sql.*;
import java.util.*;

import controller.Controller;
import dto.*;

public class Sessione_in_presenzaDAO {
	
	// Attributi
	private Connection conn;
	private Controller controller;
	
	// Costruttore
	public Sessione_in_presenzaDAO(Connection conn, Controller controller) {
		this.conn = conn;
		this.controller = controller;
	}
	
	// Metodi
	public LinkedList<Sessione_in_presenza> getSessioniPresenza(int idCorso) {
		LinkedList<Sessione_in_presenza> sessioni = new LinkedList<Sessione_in_presenza>();
		String sql = """
	            SELECT luogo, max_posti, orario_inizio, orario_fine
	            FROM sessione_in_presenza
	            WHERE id_corso = ?
	        """;
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCorso);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                	String luogo = rs.getString("luogo");
                	Timestamp orarioInizio = rs.getTimestamp("orario_inizio");
                	sessioni.add(new Sessione_in_presenza(luogo, rs.getInt("max_posti"), orarioInizio, rs.getTimestamp("orario_fine"), idCorso, controller.getRicetteToDatabase(luogo, orarioInizio)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return sessioni;
	}
	
	public boolean saveSessione(Sessione_in_presenza sessione) {
		String sql = """
	            INSERT INTO sessione_in_presenza (luogo, orario_inizio, orario_fine, max_posti, id_corso)
	            VALUES (?, ?, ?, ?, ?)
	        """;
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, sessione.getLuogo());
			ps.setTimestamp(2, sessione.getOrario_inizio_timestamp());
			ps.setTimestamp(3, sessione.getOrario_fine_timestamp());
			ps.setInt(4, sessione.getMax_posti());
			ps.setInt(5, sessione.getId_Corso());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}