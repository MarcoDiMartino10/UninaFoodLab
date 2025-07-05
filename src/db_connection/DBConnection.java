package db_connection;

import java.io.*;
import java.sql.*;

public class DBConnection {
    
	// Attributi
	private static DBConnection dbcon = null;
    private Connection conn = null;
    
    // Costruttore
    private DBConnection(){}

    // Metodo per avere una sola connessione al database
    public static DBConnection getDBConnection() {
        if (dbcon == null) {
            dbcon = new DBConnection();
        }
        return dbcon;
    }
    
    // Metodo per ottenere la connessione al database
    public Connection getConnection() throws SQLException, IOException {
        if (conn == null || conn.isClosed()) {
            try (BufferedReader b = new BufferedReader(new FileReader("pwd_DB/pwdfile"))) {
                String pwd = b.readLine();
                String s_url = "jdbc:postgresql://localhost:5432/UninaFoodLab?currentSchema=schema_progetto";
                conn = DriverManager.getConnection(s_url, "postgres", pwd);
            }
        }
        return conn;
    }
}