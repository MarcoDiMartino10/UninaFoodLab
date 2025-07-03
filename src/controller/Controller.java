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
    private AggiungiSessioneOnlineDialog aggiungiSessioneOnlineDialog;
    private AggiungiSessioneInPresenzaDialog aggiungiSessioneInPresenzaDialog;
    private ReportMensileFrame reportMensileFrame;

    //Costruttore
    public Controller(Connection conn) {
    	this.conn = conn;
    	//loginFrame = new LoginFrame(this);
    	//loginFrame.setVisible(true);
        chefDAO = new ChefDAO(conn, this);
        test();
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
    	aggiungiRicettaDialog = new AggiungiRicettaDialog(this, false);
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
    
    public void apriAggiungiSessioneOnlineDialog() {
		aggiungiSessioneOnlineDialog = new AggiungiSessioneOnlineDialog(this);
		aggiungiSessioneOnlineDialog.setVisible(true);
	}
    
    public void chiudiAggiungiSessioneOnlineDialog() {
    	aggiungiSessioneOnlineDialog.dispose();
    }
    
    public void apriAggiungiSessioneInPresenzaDialog() {
		aggiungiSessioneInPresenzaDialog = new AggiungiSessioneInPresenzaDialog(this);
		aggiungiSessioneInPresenzaDialog.setVisible(true);
	}
    
    public void chiudiAggiungiSessioneInPresenzaDialog() {
    	aggiungiSessioneInPresenzaDialog.dispose();
    }
    
    private void aggiornaInfoCorsoFrame() {
    	infoCorsoFrame.dispose();
    	infoCorsoFrame = new InfoCorsoFrame(this);
    	infoCorsoFrame.setVisible(true);
	}
    
    public void apriAggiungiRicettaFrameBySessione() {
    	aggiungiRicettaDialog = new AggiungiRicettaDialog(this, true);
		aggiungiRicettaDialog.setVisible(true);
	}
    
    public void apriReportMensileFrame() {
		reportMensileFrame = new ReportMensileFrame(this);
		reportMensileFrame.setVisible(true);
		homepageFrame.setVisible(false);
	}
    
    public void chiudiReportMensileFrame() {
    	reportMensileFrame.dispose();
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
    
    // Metodo per ottenere le categorie dei corsi
    public LinkedList<Corso> getCorsiFiltratiPerCategoria(String categoria) {
        LinkedList<Corso> corsiFiltrati = new LinkedList<>();
        LinkedList<Corso> corsi = chef.getCorso();
        for (int i = 0; i < corsi.size(); i++) {
            Corso corso = corsi.get(i);
            if (categoria.equals("Tutti") || corso.getCategoria().toLowerCase().equals(categoria.toLowerCase())) {
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
		RicettaDAO ricettaDAO = new RicettaDAO(conn, this);
		return ricettaDAO.ultimoIdRicetta();
	}
    
    public int nuovoIdIngrediente() {
    	IngredienteDAO ingredienteDAO = new IngredienteDAO(conn);
    	return ingredienteDAO.ultimoIdIngrediente();
    }
    
    public void aggiungiRicetta(String nomeRicetta, LinkedList<Ingrediente> ingredienti) {
    	RicettaDAO ricettaDAO = new RicettaDAO(conn, this);
		ricettaDAO.saveRicetta(sessione_in_presenza.getLuogo(), sessione_in_presenza.getOrario_inizio_timestamp(), nomeRicetta, ingredienti);
		aggiungiRicettaAllaSessione(nomeRicetta, ingredienti);
		aggiornaRicetteFrame();
    }
    
    public void aggiungiCorso(String nomeCorso, String categoria, LocalDate dataInizio, String frequenza, BigDecimal costo, int numSessioni) {
		CorsoDAO corsoDAO = new CorsoDAO(conn, this);
		int idCorso = corsoDAO.ultimoIdCorso();
		Corso corso = new Corso(idCorso, nomeCorso, categoria, dataInizio, frequenza, costo, numSessioni, chef.getID());
		corsoDAO.saveCorso(corso);
		aggiungiCorsoAlloChef(corso);
		aggiungiCorsoDialog.dispose();
		aggiornaHomepageFrame();
	}
    
    public void aggiungiSessioneOnline(String link, Timestamp inizio, Timestamp fine) {
    	Sessione_onlineDAO sessione_onlineDAO = new Sessione_onlineDAO(conn);
		Sessione_online sessione = new Sessione_online(link, inizio, fine, corso.getID());
		sessione_onlineDAO.saveSessione(sessione);
		aggiungiSessioneAlCorso(sessione);
		aggiungiSessioneOnlineDialog.dispose();
		aggiornaInfoCorsoFrame();
	}
    
    public void aggiungiSessioneInPresenza(String luogo, Timestamp inizio, Timestamp fine, int maxPosti) {
    	sessione_in_presenza = new Sessione_in_presenza(luogo, maxPosti, inizio, fine, corso.getID());
    	apriAggiungiRicettaFrameBySessione();
    	aggiornaInfoCorsoFrame();
    }
    
    public void aggiungiSessioneRicetta(String nomeRicetta, LinkedList<Ingrediente> ingredienti) {
    	Sessione_in_presenzaDAO sessione_in_presenzaDAO = new Sessione_in_presenzaDAO(conn, this);
    	sessione_in_presenzaDAO.saveSessioneAndRicetta(sessione_in_presenza, nomeRicetta, ingredienti);
    	aggiungiSessioneAlCorso(sessione_in_presenza);
    	aggiungiRicettaAllaSessione(nomeRicetta, ingredienti);
    }
    
    public LinkedList<Corso> getCorsiToDatabase(int id) {
    	CorsoDAO corsoDAO = new CorsoDAO(conn, this);
    	return corsoDAO.getCorsiByChefId(id);
    }
    
    public LinkedList<Sessione> getSessioniOnlineToDatabase(int id) {
    	Sessione_onlineDAO sessione_onlineDAO = new Sessione_onlineDAO(conn);
    	Sessione_in_presenzaDAO sessione_in_presenzaDAO = new Sessione_in_presenzaDAO(conn, this);
    	LinkedList<Sessione> sessioni = new LinkedList<Sessione>(sessione_onlineDAO.getSessioniOnline(id));
		sessioni.addAll(sessione_in_presenzaDAO.getSessioniPresenza(id));
		return sessioni;
    }
    
    public LinkedList<Ricetta> getRicetteToDatabase(String luogo, Timestamp orarioInizio) {
		RicettaDAO ricettaDAO = new RicettaDAO(conn, this);
		return ricettaDAO.getRicettabyLuogoAndData(luogo, orarioInizio);
	}
    
    public LinkedList<Ingrediente> getIngredientiToDatabase(int id) {
		IngredienteDAO ingredienteDAO = new IngredienteDAO(conn);
		return ingredienteDAO.getIngredienteByRicettaId(id);
	}
    
    public boolean setRicetteToDatabase(Sessione_in_presenza sessione_in_presenza, String nomeRicetta, LinkedList<Ingrediente> ingredienti) {
    	RicettaDAO ricettaDAO = new RicettaDAO(conn, this);
		return ricettaDAO.saveRicetta(sessione_in_presenza.getLuogo(), sessione_in_presenza.getOrario_inizio_timestamp(), nomeRicetta, ingredienti);
    }
    
    public boolean setIngredienteToDatabase(LinkedList<Ingrediente> ingredienti, int idRicetta) {
    	IngredienteDAO ingredienteDAO = new IngredienteDAO(conn);
    	return ingredienteDAO.saveIngredienti(ingredienti, idRicetta);
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
    
    public void aggiungiCorsoAlloChef(Corso corso) {
    	chef.getCorso().add(corso);
    }
    
    private void aggiungiSessioneAlCorso(Sessione sessione) {
    	corso.getSessioni().add(sessione);
    	if (corso.getSessioni().size() > corso.getNumero_sessioni()) {
			corso.setNumero_sessioni(corso.getSessioni().size());
		}
	}
    
    
    
    /*------------------------------------------ Metodi per accedere alle DAO -----------------------------------------------*/
    
    
    
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