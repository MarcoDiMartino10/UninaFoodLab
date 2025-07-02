package dao;

import java.sql.*;

import dto.*;

public class ChefDAO {
    
	// Attributi
	private Connection conn;
	private CorsoDAO corsoDAO;
	
	// Costruttore
    public ChefDAO(Connection conn) {
        this.conn = conn;
        corsoDAO = new CorsoDAO(conn);
    }
    
    // Metodi
    public Chef getChefByEmailAndPassword(String email, String password) {
        String sql = """
            SELECT c.id, c.nome, c.cognome, c.email, c.password, c.biografia, c.numero_tel
            FROM Chef c
            WHERE c.email = ? AND c.password = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    Chef chef = new Chef(id, rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("password"), rs.getString("biografia"), rs.getString("numero_tel"), corsoDAO.getCorsiByChefId(id));
                    return chef;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
   
}