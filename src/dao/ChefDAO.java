package dao;

import java.sql.*;

import dto.Chef;

public class ChefDAO {
    
	// Attributi
	private Connection conn;
	
	// Costruttore
    public ChefDAO(Connection conn) {
        this.conn = conn;
    }
    
    public Chef getChef(String email, String password) throws SQLException {
    	String sql = "SELECT * FROM Chef WHERE email = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
        	ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Chef(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("password"), rs.getString("biografia"), rs.getString("numero_tel"), null);
            }
            return null;
        }
    }
   
}