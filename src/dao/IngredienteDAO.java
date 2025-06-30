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
    		    SELECT i.id, i.Nome, c.Quantità, c.Unità_di_misura
    		    FROM Ingrediente i
    		    JOIN Contenere c ON i.ID = c.ID_ingrediente
    		    WHERE c.ID_ricetta = ?
    		""";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idRicetta);
            try (ResultSet rs = ps.executeQuery()) {
            	while (rs.next()) {
            	    Ingrediente ingrediente = new Ingrediente(rs.getInt("id"), rs.getString("Nome"), rs.getBigDecimal("Quantità"), rs.getString("Unità_di_misura"));
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
