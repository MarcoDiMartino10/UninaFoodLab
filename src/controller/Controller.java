package controller;

import java.awt.event.*;
import java.sql.*;

import dao.ChefDAO;
import db_connection.*;
import gui.*;
import dto.*;

public class Controller {
	
	// Attributi
    Connection conn;
    private LoginFrame loginFrame;
    private Chef chef;
    private ChefDAO chefDAO;

    //Costruttore
    public Controller(Connection conn) {
    	this.conn = conn;
    	 loginFrame = new LoginFrame(this);
         chefDAO = new ChefDAO(conn);
    	 loginFrame.premiLogin(login());
    	 loginFrame.setVisible(true);
    }

    // Metodi
    private ActionListener login() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = loginFrame.getEmail();
                String password = loginFrame.getPassword();
                chef = chefDAO.getChefByEmailAndPassword(email, password);
                if (chef != null) {
                	loginFrame.dispose();
//                    HomeFrame home = new HomeFrame(Controller.this, chef);
//                    home.setVisible(true);
                	System.out.println("Login effettuato con successo!");
                } else {
                	loginFrame.credenzialiErrate();
                }
            }
        };
    }
    
   
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