package dao;

import java.sql.*;
import java.util.LinkedList;

import dto.Corso;

public class CorsoDAO {
    
	// Attributo
	private Connection conn;
	
	// Costruttore
    public CorsoDAO(Connection conn) {
        this.conn = conn;
    }
    
    // Recupera i corsi di uno chef dal database
    public LinkedList<Corso> getCorsi(String emailChef) throws SQLException {
        LinkedList<Corso> corsi = new LinkedList<>();
        String sql = "SELECT * FROM Corso WHERE email_chef = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emailChef);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    corsi.add(new Corso(rs.getInt("id"), rs.getString("nome"), rs.getString("categoria"), rs.getDate("data_inizio").toLocalDate(), rs.getString("frequenza"), rs.getBigDecimal("costo"), rs.getInt("numero_sessioni"), rs.getString("email_chef"), null));
                }
            }
        }
        return corsi;
    }
    
    // Inserisci un nuovo corso nel database
    public void saveCorso(Corso corso) throws SQLException {
    	String sql = "INSERT INTO Corso (Id, Nome, Categoria, Data_inizio, Frequenza, Costo, Numero_sessioni, Email_chef) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, corso.getID());
			ps.setString(2, corso.getNome());
			ps.setString(3, corso.getCategoria());
			ps.setDate(4, Date.valueOf(corso.getData_inizio()));
			ps.setString(5, corso.getFrequenza());
			ps.setBigDecimal(6, corso.getCosto());
			ps.setInt(7, corso.getNumero_sessioni());
			ps.setString(8, corso.getEmailChef());
			ps.executeUpdate();
		}
    }
    
    // Calcola il nuovo ID per un corso
    public int nuovoIdCorso() throws SQLException {
		String sql = "SELECT MAX(id) AS max_id FROM Corso";
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