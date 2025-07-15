package dao;

import java.sql.*;
import java.util.LinkedList;

import dto.*;

public class IngredienteDAO {
	
	// Attributo
	private Connection conn;
	
	// Costruttore
	public IngredienteDAO(Connection conn) {
		this.conn = conn;
	}
	
	// Recupera gli ingredienti di una ricetta dal database
	public LinkedList<Ingrediente> getIngredienti(int idRicetta) throws SQLException {
	    LinkedList<Ingrediente> ingredienti = new LinkedList<>();
	    String sql = "SELECT * FROM Ingrediente WHERE ID_ricetta = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, idRicetta);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                ingredienti.add(new Ingrediente(rs.getInt("id"), rs.getString("Nome"), rs.getBigDecimal("Quantità"), rs.getString("Unità_di_misura")));
	            }
	        }
	    }
	    return ingredienti;
	}

	// Inserisce una lista di ingredienti per una ricetta nel database
	public void saveIngredienti(LinkedList<Ingrediente> ingredienti, int idRicetta) throws SQLException {
	    StringBuilder sql = new StringBuilder("INSERT INTO Ingrediente (ID, Nome, Quantità, Unità_di_misura, ID_ricetta) VALUES ");
	    for (int i = 0; i < ingredienti.size(); i++) {
	        sql.append("(?, ?, ?, ?, ?)");
	        if (i < ingredienti.size() - 1) {
	            sql.append(", ");
	        } else {
	            sql.append(";");
	        }
	    }
	    try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
	        int paramIndex = 1;
	        for (Ingrediente ingr : ingredienti) {
	            ps.setInt(paramIndex++, ingr.getID());
	            ps.setString(paramIndex++, ingr.getNome());
	            ps.setBigDecimal(paramIndex++, ingr.getQuantità());
	            ps.setString(paramIndex++, ingr.getUnità_di_misura());
	            ps.setInt(paramIndex++, idRicetta);
	        }
	        ps.executeUpdate();
	    }
	}

	// Calcola il nuovo ID per un ingrediente
	public int nuovoIdIngrediente() throws SQLException {
	    String sql = "SELECT MAX(id) AS max_id FROM ingrediente";
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
