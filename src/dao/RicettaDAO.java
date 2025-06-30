package dao;

import java.sql.*;
import java.util.LinkedList;
import java.math.BigDecimal;

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
