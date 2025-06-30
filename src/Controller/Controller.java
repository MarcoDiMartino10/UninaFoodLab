package Controller;

import java.sql.*;

import DB_Connection.*;

public class Controller {
	
	// Attributi
    Connection conn;

    //Costruttore
    public Controller(Connection conn) {
    	this.conn = conn;
    }

    // Metodi
   
    // Main
	public static void main(String[] args) {
		DBConnection dbConnection = DBConnection.getDBConnection();
        Connection conn = dbConnection.getConnection();
        if (conn != null) {
            System.out.println("Connessione al database riuscita!");
        } else {
            System.out.println("Connessione al database fallita.");
            return;
        }
        new Controller(conn);
	}

}