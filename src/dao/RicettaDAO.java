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
	        SELECT id, Nome
	        FROM Ricetta
	        WHERE Luogo = ? AND Orario_inizio = ?
	    """;
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, luogo);
	        ps.setTimestamp(2, orario_inizio);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                ricette.add(new Ricetta(rs.getInt("id"), rs.getString("nome"), luogo, orario_inizio, null));
	            }
	        }
	    }
	    return ricette;
	}
	
	// Inserisce una nuova ricetta nel database
		public void saveRicetta(int id_ricetta, String nome, String luogo, Timestamp orario_inizio) throws SQLException {
			String sql = "INSERT INTO Ricetta (ID, Nome, luogo, orario_inizio) VALUES (?, ?, ?, ?)";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, id_ricetta);
				ps.setString(2, nome);
				ps.setString(3, luogo);
				ps.setTimestamp(4, orario_inizio);
				ps.executeUpdate();
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
