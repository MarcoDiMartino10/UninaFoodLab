package dao;

import java.sql.*;
import java.util.*;

import dto.*;

public class CorsoDAO {
    
	// Attributi
	private Connection conn;
	private SessioneDAO sessioneDAO;

	// Costruttore
    public CorsoDAO(Connection conn) {
        this.conn = conn;
        this.sessioneDAO = new SessioneDAO(conn);
    }
    
    // Metodi
    public LinkedList<Corso> getCorsiByChefId(int idChef) {
    	LinkedList<Corso> corsi = new LinkedList<Corso>();
        String sql = """
            SELECT id, nome, categoria, data_inizio, frequenza, costo
            FROM Corso
            WHERE id_chef = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idChef);
            try (ResultSet rs = ps.executeQuery()) {
            	while (rs.next()) {
            		int id = rs.getInt("id");
            	    Corso corso = new Corso(id, rs.getString("nome"), rs.getString("categoria"), rs.getDate("data_inizio").toLocalDate(), rs.getString("frequenza"), rs.getBigDecimal("costo"), idChef, sessioneDAO.getSessioniByCorsoId(id));
            	    corsi.add(corso);
            	}
            	return corsi;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}