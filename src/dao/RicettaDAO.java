package dao;

import java.sql.*;
import java.util.LinkedList;

import controller.Controller;
import dto.*;

public class RicettaDAO {
	
	// Attributi
	private Connection conn;
	private Controller controller;
	
	// Costruttore
	public RicettaDAO(Connection conn, Controller controller) {
		this.conn = conn;
		this.controller = controller;
	}
	
	// Metodi
	public LinkedList<Ricetta> getRicettabyLuogoAndData(String luogo, Timestamp orario_inizio) {
    	LinkedList<Ricetta> ricette = new LinkedList<Ricetta>();
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
            		int id = rs.getInt("id");
            	    Ricetta ricetta = new Ricetta(id, rs.getString("nome"), controller.getIngredientiToDatabase(id));
            	    ricette.add(ricetta);
            	}
            	return ricette;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public boolean saveRicetta(String luogo, Timestamp orario_inizio, String nome, LinkedList<Ingrediente> ingredienti) {
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

	        controller.setIngredienteToDatabase(ingredienti, id_ricetta);

	        conn.commit();
	        return true;

	    } catch (SQLException e) {
	        try {
	            conn.rollback();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            conn.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}
	
	public int ultimoIdRicetta() {
		String sql = "SELECT MAX(id) AS max_id FROM ricetta";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
            	int maxID = 1;
        		if (rs.next()) {
        		    maxID = rs.getInt("max_id") + 1;
        		}
            	return maxID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return -1;
	}

}
