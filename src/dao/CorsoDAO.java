package dao;

import java.sql.*;
import java.util.*;

import controller.Controller;

import java.sql.Date;

import dto.*;

public class CorsoDAO {
    
	// Attributi
	private Connection conn;
	private Controller controller;
	
	// Attributi statici
	public static final String TABLE_NAME = "Corso";

	// Costruttore
    public CorsoDAO(Connection conn, Controller controller) {
        this.conn = conn;
        this.controller = controller;
    }
    
    // Metodi
    public LinkedList<Corso> getCorsiByChefId(int idChef) {
    	LinkedList<Corso> corsi = new LinkedList<Corso>();
        String sql = """
            SELECT id, nome, categoria, data_inizio, frequenza, costo, numero_sessioni
            FROM Corso
            WHERE id_chef = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idChef);
            try (ResultSet rs = ps.executeQuery()) {
            	while (rs.next()) {
            		int id = rs.getInt("id");
            	    Corso corso = new Corso(id, rs.getString("nome"), rs.getString("categoria"), rs.getDate("data_inizio").toLocalDate(), rs.getString("frequenza"), rs.getBigDecimal("costo"), rs.getInt("numero_sessioni"), idChef, controller.getSessioniToDatabase(id));
            	    corsi.add(corso);
            	}
            	return corsi;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean saveCorso(Corso corso) {
    	String sql = "INSERT INTO Corso (Id, Nome, Categoria, Data_inizio, Frequenza, Costo, Numero_sessioni, Id_chef) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, corso.getID());
			ps.setString(2, corso.getNome());
			ps.setString(3, corso.getCategoria());
			ps.setDate(4, Date.valueOf(corso.getData_inizio()));
			ps.setString(5, corso.getFrequenza());
			ps.setBigDecimal(6, corso.getCosto());
			ps.setInt(7, corso.getNumero_sessioni());
			ps.setInt(8, corso.getID_Chef());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
    }
    
    public int ultimoIdCorso() {
		String sql = "SELECT MAX(id) AS max_id FROM Corso";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
            	int maxID = 1;
        		if (rs.next()) {
        		    maxID = rs.getInt("max_id") + 1;
        		}
            	return maxID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return -1;
	}
    
}