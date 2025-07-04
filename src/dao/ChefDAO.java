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

    public int nuovoIdChef() throws SQLException {
        String sql = "SELECT MAX(id) AS max_id FROM Chef";
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
    
    public boolean saveChef(Chef chef) throws SQLException {
		String sql = "INSERT INTO Chef (id, nome, cognome, email, password, biografia, numero_tel) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, chef.getID());
			ps.setString(2, chef.getNome());
			ps.setString(3, chef.getCognome());
			ps.setString(4, chef.getEmail());
			ps.setString(5, chef.getPassword());
			ps.setString(6, chef.getBiografia());
			ps.setString(7, chef.getNumeroTel());
			return ps.executeUpdate() > 0;
		}
	}
   
}