package db_connection;

import java.io.*;
import java.sql.*;

public class DBConnection {
    
	// Attributi
	private static DBConnection dbcon = null;
    private Connection conn = null;
    
    // Costruttore
    private DBConnection(){}

    // Metodi
    public static DBConnection getDBConnection() {
        if (dbcon == null) {
            dbcon = new DBConnection();
        }
        return dbcon;
    }
    
    /*public Connection getConnection() {
        String pwd = null;
        BufferedReader b = null;
        try {
            if(conn==null || conn.isClosed()) {
                b = new BufferedReader(new FileReader(new File("pwd_DB/pwdfile")));
                pwd = b.readLine();
                String s_url = "jdbc:postgresql://localhost:5432/UninaFoodLab?currentSchema=schema_progetto";
                conn = DriverManager.getConnection(s_url, "postgres", pwd);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }*/
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