package dao;

import java.sql.*;
import java.util.LinkedList;

import dto.*;

public class IngredienteDAO {
	
	// Attributi
	private Connection conn;
	
	// Costruttore
	public IngredienteDAO(Connection conn) {
		this.conn = conn;
	}
	
	// Metodi
		public LinkedList<Ingrediente> getIngredienteByRicettaId(int idRicetta) {
	    	LinkedList<Ingrediente> ingredienti = new LinkedList<Ingrediente>();
	    	String sql = """
	    		    SELECT i.id, i.Nome, i.Quantità, i.Unità_di_misura, i.ID_ricetta
	    		    FROM Ingrediente i
	    		    WHERE i.ID_ricetta = ?
	    		""";

	        try (PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, idRicetta);
	            try (ResultSet rs = ps.executeQuery()) {
	            	while (rs.next()) {
	            	    Ingrediente ingrediente = new Ingrediente(rs.getInt("id"), rs.getString("Nome"), rs.getBigDecimal("Quantità"), rs.getString("Unità_di_misura"), idRicetta);
	            	    ingredienti.add(ingrediente);
	            	}
	            	return ingredienti;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

}
