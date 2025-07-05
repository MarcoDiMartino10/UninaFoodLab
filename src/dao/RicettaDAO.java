package dao;

import java.sql.*;
import java.util.LinkedList;

import dto.*;

public class RicettaDAO {
	
	// Attributo
	private Connection conn;
	
	// Costruttore
	public RicettaDAO(Connection conn) {
		this.conn = conn;
	}
	
	// Recupera le ricette di una sessione dal database
	public LinkedList<Ricetta> getRicette(String luogo, Timestamp orario_inizio) throws SQLException {
	    LinkedList<Ricetta> ricette = new LinkedList<>();
	    String sql = """
	        SELECT r.id, r.Nome
	        FROM Ricetta r
	        JOIN Preparazione p ON r.ID = p.ID_ricetta
	        WHERE p.Luogo = ? AND p.Orario_inizio = ?
	    """;
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, luogo);
	        ps.setTimestamp(2, orario_inizio);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                ricette.add(new Ricetta(rs.getInt("id"), rs.getString("nome"), null));
	            }
	        }
	    }
	    return ricette;
	}
	
	// Inserisce una nuova ricetta nel database
	public void saveRicetta(String luogo, Timestamp orario_inizio, String nome, LinkedList<Ingrediente> ingredienti) throws SQLException {
	    try {
	        conn.setAutoCommit(false);
	        int id_ricetta = ingredienti.get(0).getID_ricetta();
	        String sqlRicetta = "INSERT INTO Ricetta (ID, Nome) VALUES (?, ?)";
	        try (PreparedStatement ps = conn.prepareStatement(sqlRicetta)) {
	            ps.setInt(1, id_ricetta);
	            ps.setString(2, nome);
	            ps.executeUpdate();
	        }
	        String sqlPreparazione = "INSERT INTO Preparazione (ID_ricetta, Luogo, Orario_inizio) VALUES (?, ?, ?)";
	        try (PreparedStatement ps = conn.prepareStatement(sqlPreparazione)) {
	            ps.setInt(1, id_ricetta);
	            ps.setString(2, luogo);
	            ps.setTimestamp(3, orario_inizio);
	            ps.executeUpdate();
	        }
	        conn.commit();

	    } catch (SQLException e) {
	        try {
	            conn.rollback();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        throw e;
	    } finally {
	        try {
	            conn.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	// Calcola il nuovo ID per una ricetta
	public int nuovoIdRicetta() throws SQLException {
	    String sql = "SELECT MAX(id) AS max_id FROM ricetta";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        try (ResultSet rs = ps.executeQuery()) {
	            int maxID = 1;
	            if (rs.next()) {
	                maxID = rs.getInt("max_id") + 1;
	            }
	            return maxID;
	        }
	    }
	}

}
