package dao;

import java.sql.*;

import dto.Chef;

public class ChefDAO {
    
	// Attributo
	private Connection conn;
	
	// Costruttore
    public ChefDAO(Connection conn) {
        this.conn = conn;
    }
    
    // Recupera uno Chef dal database
    public Chef getChef(String email, String password) throws SQLException {
    	String sql = "SELECT * FROM Chef WHERE email = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
        	ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Chef(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("password"), rs.getString("biografia"), rs.getString("numero_tel"), null);
            }
            return null;
        }
    }
    
    // Inserisce uno Chef nel database
    public void saveChef(Chef chef) throws SQLException {
		String sql = "INSERT INTO Chef (nome, cognome, email, password, biografia, numero_tel) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, chef.getNome());
			ps.setString(2, chef.getCognome());
			ps.setString(3, chef.getEmail());
			ps.setString(4, chef.getPassword());
			ps.setString(5, chef.getBiografia());
			ps.setString(6, chef.getNumeroTel());
			ps.executeUpdate();
		}
	}
   
}