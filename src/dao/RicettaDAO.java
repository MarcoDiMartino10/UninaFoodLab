package dao;

import java.sql.*;
import java.util.LinkedList;

import dto.*;

public class RicettaDAO {
	
	// Attributi
	private Connection conn;
	private IngredienteDAO ingredienteDAO;
	
	// Costruttore
	public RicettaDAO(Connection conn) {
		this.conn = conn;
		this.ingredienteDAO = new IngredienteDAO(conn);
	}
	
	// Metodi
	public LinkedList<Ricetta> getRicettabyLuogoAndData(int ID) {
    	LinkedList<Ricetta> ricette = new LinkedList<Ricetta>();
    	String sql = """
    			SELECT r.id, r.Nome
    		    FROM Ricetta r
    		    JOIN Preparazione p ON r.ID = p.ID_ricetta
    		    WHERE r.ID = ?
    		""";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ID);
            try (ResultSet rs = ps.executeQuery()) {
            	while (rs.next()) {
            		int id = rs.getInt("id");
            	    Ricetta ricetta = new Ricetta(id, rs.getString("nome"), ingredienteDAO.getIngredienteByRicettaId(id));
            	    ricette.add(ricetta);
            	}
            	return ricette;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
