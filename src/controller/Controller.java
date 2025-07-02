package controller;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

import dao.*;
import db_connection.*;
import gui.*;
import dto.*;

import javax.swing.UIManager;

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
    private AggiungiRicettaDialog aggiungiRicettaDialog;
    private AggiungiCorsoDialog aggiungiCorsoDialog;

    //Costruttore
    public Controller(Connection conn) {
    	this.conn = conn;
    	loginFrame = new LoginFrame(this);
    	loginFrame.setVisible(true);
        chefDAO = new ChefDAO(conn);
        //test();
    }
    
    /*------------------------------------------ Metodi per aprire o chiudere le finestre -----------------------------------------------*/
     
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
	  homepageFrame = new HomepageFrame(this);
	  homepageFrame.setVisible(true);
	}
    
    public void apriRicetteFrame(Sessione_in_presenza sessione) {
    	this.sessione_in_presenza = sessione;
    	ricetteFrame = new RicetteFrame(this);
    	ricetteFrame.setVisible(true);
    	infoCorsoFrame.setVisible(false);
    }
    
    public void chiudiRicetteFrame() {
    	ricetteFrame.dispose();
    	infoCorsoFrame = new InfoCorsoFrame(this);
    	infoCorsoFrame.setVisible(true);
    }
    
    public void apriAggiungiRicettaFrame() {
    	aggiungiRicettaDialog = new AggiungiRicettaDialog(this);
		aggiungiRicettaDialog.setVisible(true);
	}
    
    public void chiudiAggiungiRicettaFrame() {
    	aggiungiRicettaDialog.dispose();
    }
    
    public void aggiornaRicetteFrame() {
		ricetteFrame.dispose();
		ricetteFrame = new RicetteFrame(this);
		ricetteFrame.setVisible(true);
	}
    
    public void apriAggiungiCorsoDialog() {
    	aggiungiCorsoDialog = new AggiungiCorsoDialog(this);
    	aggiungiCorsoDialog.setVisible(true);
    }
    
    public void chiudiAggiungiCorsoDialog() {
		aggiungiCorsoDialog.dispose();
	}
    
    public void aggiornaHomepageFrame() {
		homepageFrame.dispose();
		homepageFrame = new HomepageFrame(this);
		homepageFrame.setVisible(true);
	}
    
    /*------------------------------------------- Metodi per ottenere i dati per le interfacce grafiche ----------------------------------------------*/
    
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
    
    /*------------------------------------------- Metodi di accesso al database ----------------------------------------------*/
    
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
    
    public int nuovoIdRicetta() {
		RicettaDAO ricettaDAO = new RicettaDAO(conn);
		return ricettaDAO.ultimoIdRicetta();
	}
    
    public int nuovoIdIngrediente() {
    	IngredienteDAO ingredienteDAO = new IngredienteDAO(conn);
    	return ingredienteDAO.ultimoIdIngrediente();
    }
    
    public void aggiungiRicetta(String nomeRicetta, LinkedList<Ingrediente> ingredienti) {
    	RicettaDAO ricettaDAO = new RicettaDAO(conn);
		ricettaDAO.inserisciRicetta(sessione_in_presenza.getLuogo(), sessione_in_presenza.getOrario_inizio_timestamp(), nomeRicetta, ingredienti);
		aggiungiRicettaAllaSessione(nomeRicetta, ingredienti);
		aggiornaRicetteFrame();
    }
    
    public void aggiungiCorso(String nomeCorso, String categoria, LocalDate dataInizio, String frequenza, BigDecimal costo, int numSessioni) {
		CorsoDAO corsoDAO = new CorsoDAO(conn);
		int idCorso = corsoDAO.ultimoIdCorso();
		corsoDAO.saveCorso(chef.getID(), nomeCorso, categoria, dataInizio, frequenza, costo, numSessioni, idCorso);
		aggiungiCorsoAlloChef(nomeCorso, categoria, dataInizio, frequenza, costo, numSessioni, idCorso);
		aggiungiCorsoDialog.dispose();
		aggiornaHomepageFrame();
	}
    
    /*----------------------------------------- Metodi di modifiche delle dto ------------------------------------------------*/
    
    public void aggiungiRicettaAllaSessione(String nomeRicetta, LinkedList<Ingrediente> ingredienti) {
		for (Corso corso1 : chef.getCorso()) {
			if (corso1.getNome().equals(corso.getNome())) {
				for (Sessione sessione : corso1.getSessioni()) {
					if (sessione instanceof Sessione_in_presenza) {
	                    Sessione_in_presenza sessioneInPresenza = (Sessione_in_presenza) sessione;
	                    if (sessioneInPresenza.getLuogo().equals(sessione_in_presenza.getLuogo()) && sessioneInPresenza.getOrario_inizio_timestamp().equals(sessione_in_presenza.getOrario_inizio_timestamp())) {
	                        sessioneInPresenza.getRicette().add(new Ricetta(ingredienti.get(0).getID_ricetta(), nomeRicetta, ingredienti));
	                        return;
	                    }
	                }
				}
			}
		}
	}
    
    public void aggiungiCorsoAlloChef(String nomeCorso, String categoria, LocalDate dataInizio, String frequenza, BigDecimal costo, int numSessioni, int IdCorso) {
    	Corso corso = new Corso(IdCorso, nomeCorso, categoria, dataInizio, frequenza, costo, numSessioni, chef.getID());
    	chef.getCorso().add(corso);
    }
    
    /*----------------------------------------- main ------------------------------------------------*/
   
    public void test() {
    	String email = "anna.verdi@gmail.com";
        String password = "Verdi89@";
        chef = chefDAO.getChefByEmailAndPassword(email, password);
//      this.corso = chef.getCorso().get(0);
        homepageFrame = new HomepageFrame(this);
        homepageFrame.setVisible(true);
    }
    
	public static void perMac() {
		try {
		    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
    
    
    // Main
	public static void main(String[] args) {
		perMac();
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