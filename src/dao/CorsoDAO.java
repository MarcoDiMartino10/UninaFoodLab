package dao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

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
            SELECT id, nome, categoria, data_inizio, frequenza, costo, numero_sessioni
            FROM Corso
            WHERE id_chef = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idChef);
            try (ResultSet rs = ps.executeQuery()) {
            	while (rs.next()) {
            		int id = rs.getInt("id");
            	    Corso corso = new Corso(id, rs.getString("nome"), rs.getString("categoria"), rs.getDate("data_inizio").toLocalDate(), rs.getString("frequenza"), rs.getBigDecimal("costo"), rs.getInt("numero_sessioni"), idChef, sessioneDAO.getSessioniByCorsoId(id));
            	    corsi.add(corso);
            	}
            	return corsi;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean saveCorso(int Id_chef, String nomeCorso, String categoria, LocalDate dataInizio, String frequenza, BigDecimal costo, int numSessioni, int idCorso) {
    	String sql = "INSERT INTO Corso (Id, Nome, Categoria, Data_inizio, Frequenza, Costo, Numero_sessioni, Id_chef) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, idCorso);
			ps.setString(2, nomeCorso);
			ps.setString(3, categoria);
			ps.setDate(4, Date.valueOf(dataInizio));
			ps.setString(5, frequenza);
			ps.setBigDecimal(6, costo);
			ps.setInt(7, numSessioni);
			ps.setInt(8, Id_chef);
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