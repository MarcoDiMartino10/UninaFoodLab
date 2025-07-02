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
    private Chef chef;
    private ChefDAO chefDAO;
    private LoginFrame loginFrame;
    private HomepageFrame homepageFrame;
    private InfoCorsoFrame infoCorsoFrame;

    //Costruttore
    public Controller(Connection conn) {
    	this.conn = conn;
    	 loginFrame = new LoginFrame(this);
         chefDAO = new ChefDAO(conn);
    	 loginFrame.setVisible(true);
    }

    // Metodi
    public void login() {
      String email = loginFrame.getEmail();
      String password = loginFrame.getPassword();
      chef = chefDAO.getChefByEmailAndPassword(email, password);
      if (chef != null) {
      	loginFrame.dispose();
          homepageFrame = new HomepageFrame(Controller.this, chef);
          homepageFrame.setVisible(true);
      } else {
      	loginFrame.credenzialiErrate();
      }
    }
     
    public void logout() {
    	homepageFrame.dispose();
    	loginFrame = new LoginFrame(this);
    	loginFrame.setVisible(true);
	}
    
    public void apriInfoCorso(Corso corso) {
      infoCorsoFrame = new InfoCorsoFrame(this, chef, corso);
      infoCorsoFrame.setVisible(true);
      homepageFrame.setVisible(false);
    }
    
    public void chiudiInfoCorso() {
	  infoCorsoFrame.dispose();
	  homepageFrame = new HomepageFrame(Controller.this, chef);
	  homepageFrame.setVisible(true);
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