package dao;

import java.sql.*;
import java.util.*;

import dto.*;

public class Sessione_in_presenzaDAO {
	
	// Attributi
	private Connection conn;
	private RicettaDAO ricettaDAO;
	
	// Costruttore
	public Sessione_in_presenzaDAO(Connection conn) {
		this.conn = conn;
		this.ricettaDAO = new RicettaDAO(conn);
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
                	sessioni.add(new Sessione_in_presenza(luogo, rs.getInt("max_posti"), orarioInizio, rs.getTimestamp("orario_fine"), idCorso, ricettaDAO.getRicettabyLuogoAndData(luogo, orarioInizio)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return sessioni;
	}
	
}