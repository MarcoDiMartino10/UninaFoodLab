package controller;

import java.sql.*;
import java.util.LinkedList;

import dao.ChefDAO;
import db_connection.*;
import gui.*;
import dto.*;

public class Controller {
	
	// Attributi
    Connection conn;
    
    private Chef chef;
    private Corso corso;
    private Sessione_in_presenza sessione_in_presenza;
    
    private ChefDAO chefDAO;
    
    private LoginFrame loginFrame;
    private HomepageFrame homepageFrame;
    private InfoCorsoFrame infoCorsoFrame;
    private RicetteFrame ricetteFrame;

    //Costruttore
    public Controller(Connection conn) {
    	this.conn = conn;
    	//loginFrame = new LoginFrame(this);
        chefDAO = new ChefDAO(conn);
        //loginFrame.setVisible(true);
        test();
    }
    
    /*-----------------------------------------------------------------------------------------*/

    // Metodi dei Frame
    public void login() {
      String email = loginFrame.getEmail();
      String password = loginFrame.getPassword();
      chef = chefDAO.getChefByEmailAndPassword(email, password);
      if (chef != null) {
      	loginFrame.dispose();
          homepageFrame = new HomepageFrame(Controller.this);
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
      this.corso = corso;
      infoCorsoFrame = new InfoCorsoFrame(this);
      infoCorsoFrame.setVisible(true);
      homepageFrame.setVisible(false);
    }
    
    public void chiudiInfoCorso() {
	  infoCorsoFrame.dispose();
	  homepageFrame = new HomepageFrame(Controller.this);
	  homepageFrame.setVisible(true);
	}
    
    public void apriRicetteFrame(Sessione_in_presenza sessione) {
    	this.sessione_in_presenza = sessione;
    	ricetteFrame = new RicetteFrame(this);
    	ricetteFrame.setVisible(true);
    	infoCorsoFrame.setVisible(false);
    }
    
    public void chiudiRicetteFrame(Corso corso) {
    	ricetteFrame.dispose();
    	infoCorsoFrame = new InfoCorsoFrame(this);
    	infoCorsoFrame.setVisible(true);
    }
    
    /*-----------------------------------------------------------------------------------------*/
    
    // Metodi per ottenere i dati
    public Chef getChef() {
		return chef;
	}
    
    public Corso getCorso() {
		return corso;
	}
    
    public Sessione_in_presenza getSessione() {
    	return sessione_in_presenza;
    }
    
    // Metodo per ottenere i corsi dello chef
    public LinkedList<Corso> getCorsiFiltratiPerCategoria(String categoria) {
        LinkedList<Corso> corsiFiltrati = new LinkedList<>();
        LinkedList<Corso> corsi = chef.getCorso();
        for (int i = 0; i < corsi.size(); i++) {
            Corso corso = corsi.get(i);
            if (categoria.equals("Tutti") || corso.getCategoria().equals(categoria)) {
                corsiFiltrati.add(corso);
            }
        }
        return corsiFiltrati;
    }
    
    // Metodo per ottenere la sessione selezionata
    public void sessioneSelezionata(String luogo, String orarioInizio) {
        for (Sessione s : corso.getSessioni()) {
            if (s instanceof Sessione_in_presenza && s.getLuogo().equals(luogo) && s.getOrario_inizio().equals(orarioInizio)) {
                apriRicetteFrame((Sessione_in_presenza) s);
                return;
            }
        }
    }
    
    /*-----------------------------------------------------------------------------------------*/
   
    public void test() {
    	String email = "anna.verdi@gmail.com";
        String password = "Verdi89@";
        chef = chefDAO.getChefByEmailAndPassword(email, password);
        this.corso = chef.getCorso().get(0);
        infoCorsoFrame = new InfoCorsoFrame(this);
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