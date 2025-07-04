package controller;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

import dao.*;
import db_connection.*;
import gui.*;
import dto.*;
import exception.*;

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
    	loginFrame = new LoginFrame(this);
    	loginFrame.setVisible(true);
        chefDAO = new ChefDAO(conn);
        //test();
    }
    
    /*------------------------------------------ Metodi per per il passaggio tra le finestre -----------------------------------------------*/
    
 // Metodo per effettuare il login
    public void login() {
        String email = loginFrame.getEmail();
        String password = loginFrame.getPassword();
        chef = getAllChefToDatabase(email, password);
        if (chef != null) {
        	loginFrame.dispose();
            homepageFrame = new HomepageFrame(Controller.this);
            homepageFrame.setVisible(true);
        } else {
        	loginFrame.credenzialiErrate();
        }
    }
    
    // Chiudi homepage e riapri login
    public void logout() {
    	homepageFrame.dispose();
    	loginFrame = new LoginFrame(this);
    	loginFrame.setVisible(true);
	}
    
    // Apri infoCorsoFrame e chiudi homepageFrame
    public void apriInfoCorso(Corso corso) {
      this.corso = corso;
      infoCorsoFrame = new InfoCorsoFrame(this);
      infoCorsoFrame.setVisible(true);
      homepageFrame.setVisible(false);
    }
    
    // Chiudi infoCorsoFrame e riapri homepageFrame
    public void chiudiInfoCorso() {
	  infoCorsoFrame.dispose();
	  homepageFrame = new HomepageFrame(this);
	  homepageFrame.setVisible(true);
	}
    
    // Apri ricetteFrame e chiudi infoCorsoFrame
    public void apriRicetteFrame(Sessione_in_presenza sessione) {
    	this.sessione_in_presenza = sessione;
    	ricetteFrame = new RicetteFrame(this);
    	ricetteFrame.setVisible(true);
    	infoCorsoFrame.setVisible(false);
    }
    
    // Chiudi ricetteFrame e riapri infoCorsoFrame
    public void chiudiRicetteFrame() {
    	ricetteFrame.dispose();
    	infoCorsoFrame = new InfoCorsoFrame(this);
    	infoCorsoFrame.setVisible(true);
    }
    
    // Apri aggiungiRicettaDialog
    public void apriAggiungiRicettaFrame() {
    	aggiungiRicettaDialog = new AggiungiRicettaDialog(this, false);
		aggiungiRicettaDialog.setVisible(true);
	}
    
    // Chiudi aggiungiRicettaDialog
    public void chiudiAggiungiRicettaFrame() {
    	aggiungiRicettaDialog.dispose();
    }
    
    // Aggiorna ricetteFrame dopo l'aggiunta di una nuova ricetta
    public void aggiornaRicetteFrame() {
		ricetteFrame.dispose();
		ricetteFrame = new RicetteFrame(this);
		ricetteFrame.setVisible(true);
	}
    
    // Apri aggiungiCorsoDialog per aggiungere un nuovo corso
    public void apriAggiungiCorsoDialog() {
    	aggiungiCorsoDialog = new AggiungiCorsoDialog(this);
    	aggiungiCorsoDialog.setVisible(true);
    }
    
    // Chiudi aggiungiCorsoDialog dopo l'aggiunta di un nuovo corso
    public void chiudiAggiungiCorsoDialog() {
		aggiungiCorsoDialog.dispose();
	}
    
    // Aggiorna homepageFrame dopo l'aggiunta di un nuovo corso
    public void aggiornaHomepageFrame() {
		homepageFrame.dispose();
		homepageFrame = new HomepageFrame(this);
		homepageFrame.setVisible(true);
	}
    
    // Apri aggiungiSessioneOnlineDialog per aggiungere una sessione online
    public void apriAggiungiSessioneOnlineDialog() {
		aggiungiSessioneOnlineDialog = new AggiungiSessioneOnlineDialog(this);
		aggiungiSessioneOnlineDialog.setVisible(true);
	}
    
    // Chiudi aggiungiSessioneOnlineDialog dopo l'aggiunta di una sessione online
    public void chiudiAggiungiSessioneOnlineDialog() {
    	aggiungiSessioneOnlineDialog.dispose();
    }
    
    // Apri aggiungiSessioneInPresenzaDialog per aggiungere una sessione in presenza
    public void apriAggiungiSessioneInPresenzaDialog() {
		aggiungiSessioneInPresenzaDialog = new AggiungiSessioneInPresenzaDialog(this);
		aggiungiSessioneInPresenzaDialog.setVisible(true);
	}
    
    // Chiudi aggiungiSessioneInPresenzaDialog dopo l'aggiunta di una sessione in presenza
    public void chiudiAggiungiSessioneInPresenzaDialog() {
    	aggiungiSessioneInPresenzaDialog.dispose();
    }
    
    // Aggiorna infoCorsoFrame dopo l'aggiunta di una nuova sessione
    private void aggiornaInfoCorsoFrame() {
    	infoCorsoFrame.dispose();
    	infoCorsoFrame = new InfoCorsoFrame(this);
    	infoCorsoFrame.setVisible(true);
	}
    
    // Apri aggiungiRicettaFrame per aggiungere una ricetta alla sessione in presenza
    public void apriAggiungiRicettaFrameBySessione() {
    	aggiungiRicettaDialog = new AggiungiRicettaDialog(this, true);
		aggiungiRicettaDialog.setVisible(true);
	}
    
    // Apri reportMensileFrame e chiudi homepageFrame
    public void apriReportMensileFrame() {
		reportMensileFrame = new ReportMensileFrame(this);
		reportMensileFrame.setVisible(true);
		homepageFrame.setVisible(false);
	}
    
    // Chiudi reportMensileFrame e riapri homepageFrame
    public void chiudiReportMensileFrame() {
    	reportMensileFrame.dispose();
    	homepageFrame = new HomepageFrame(this);
    	homepageFrame.setVisible(true);
    }
    
    /*------------------------------------------- Metodi per ottenere i dati per le interfacce grafiche ----------------------------------------------*/
    
    // Getters per Chef
    public Chef getChef() {
		return chef;
	}
    
    // Getters per Corso
    public Corso getCorso() {
		return corso;
	}
    
    // Getters per Sessione_in_presenza
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
    
    // Metodo per chiamare il metodo per aprire apriRicettaFrame della sessione selezionata
    public void sessioneSelezionata(String luogo, String orarioInizio) {
        for (Sessione s : corso.getSessioni()) {
            if (s instanceof Sessione_in_presenza && s.getLuogo().equals(luogo) && s.getOrario_inizio().equals(orarioInizio)) {
                apriRicetteFrame((Sessione_in_presenza) s);
                return;
            }
        }
    }
    
    /*------------------------------------------- Metodi di accesso al database ----------------------------------------------*/
    
    // Metodo per ottenere il nuovo ID di ricetta
    public int nuovoIdRicetta() {
		RicettaDAO ricettaDAO = new RicettaDAO(conn);
		try {
			return ricettaDAO.nuovoIdRicetta();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
    
    // Metodo per ottenere il nuovo ID di ingrediente
    public int nuovoIdIngrediente() {
    	IngredienteDAO ingredienteDAO = new IngredienteDAO(conn);
    	try {
    		return ingredienteDAO.nuovoIdIngrediente();
    	} catch (SQLException e) {
    		throw new DatabaseException(e);
    	}
    }
    
    // Metodo per aggiungere una nuova ricetta
    public void aggiungiRicetta(String nomeRicetta, LinkedList<Ingrediente> ingredienti) {
    	RicettaDAO ricettaDAO = new RicettaDAO(conn);
    	IngredienteDAO ingredienteDAO = new IngredienteDAO(conn);
    	try {
    		ricettaDAO.saveRicetta(sessione_in_presenza.getLuogo(), sessione_in_presenza.getOrario_inizio_timestamp(), nomeRicetta, ingredienti);
    		ingredienteDAO.saveIngredienti(ingredienti, ingredienti.get(0).getID_ricetta());
    	} catch (SQLException e) {
    		throw new DatabaseException(e);
    	}
		aggiungiRicettaAllaSessione(nomeRicetta, ingredienti);
		aggiornaRicetteFrame();
    }
    
    // Metodo per aggiungere un nuovo corso
    public void aggiungiCorso(String nomeCorso, String categoria, LocalDate dataInizio, String frequenza, BigDecimal costo, int numSessioni) {
		CorsoDAO corsoDAO = new CorsoDAO(conn);
		int idCorso = corsoDAO.ultimoIdCorso();
		Corso corso = new Corso(idCorso, nomeCorso, categoria, dataInizio, frequenza, costo, numSessioni, chef.getID());
		corsoDAO.saveCorso(corso);
		aggiungiCorsoAlloChef(corso);
		aggiungiCorsoDialog.dispose();
		aggiornaHomepageFrame();
	}
    
    // Metodo per aggiungere una sessione online
    public void aggiungiSessioneOnline(String link, Timestamp inizio, Timestamp fine) {
    	Sessione_onlineDAO sessione_onlineDAO = new Sessione_onlineDAO(conn);
		Sessione_online sessione = new Sessione_online(link, inizio, fine, corso.getID());
		try {
			sessione_onlineDAO.saveSessione(sessione);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		aggiungiSessioneAlCorso(sessione);
		aggiungiSessioneOnlineDialog.dispose();
		aggiornaInfoCorsoFrame();
	}
    
    // Metodo per aggiungere una sessione in presenza
    public void aggiungiSessioneInPresenza(String luogo, Timestamp inizio, Timestamp fine, int maxPosti) {
    	sessione_in_presenza = new Sessione_in_presenza(luogo, maxPosti, inizio, fine, corso.getID());
    	apriAggiungiRicettaFrameBySessione();
    	aggiornaInfoCorsoFrame();
    }
    
    // Metodo per aggiungere una sessione in presenza con ricetta e ingredienti
    public void aggiungiSessioneRicetta(String nomeRicetta, LinkedList<Ingrediente> ingredienti) {
    	Sessione_in_presenzaDAO sessione_in_presenzaDAO = new Sessione_in_presenzaDAO(conn);
    	RicettaDAO ricettaDAO = new RicettaDAO(conn);
    	IngredienteDAO ingredienteDAO = new IngredienteDAO(conn);
    	try {
    		sessione_in_presenzaDAO.saveSessione(sessione_in_presenza);
    		ricettaDAO.saveRicetta(sessione_in_presenza.getLuogo(), sessione_in_presenza.getOrario_inizio_timestamp(), nomeRicetta, ingredienti);
    		ingredienteDAO.saveIngredienti(ingredienti, ingredienti.get(0).getID_ricetta());
    	} catch (SQLException e) {
    		throw new DatabaseException(e);
    	}
    	aggiungiSessioneAlCorso(sessione_in_presenza);
    	aggiungiRicettaAllaSessione(nomeRicetta, ingredienti);
    }
    
    // Metodo per ottenere TUTTI i dati dello chef dal db, quindi anche i corsi, le sessioni, le ricette e gli ingredienti
    public Chef getAllChefToDatabase(String email, String password) {
        try {
            Chef nuovoChef = chefDAO.getChef(email, password);
            if (nuovoChef != null) {
                nuovoChef.setCorso(getCorsiToDatabase(nuovoChef.getID()));
                for (Corso corso : nuovoChef.getCorso()) {
                    corso.setSessioni(getSessioniToDatabase(corso.getID()));
                    for (Sessione sessione : corso.getSessioni()) {
                        if (sessione instanceof Sessione_in_presenza) {
                            Sessione_in_presenza sessioneInPresenza = (Sessione_in_presenza) sessione;

                            sessioneInPresenza.setRicette(
                                getRicetteToDatabase(
                                    sessioneInPresenza.getLuogo(),
                                    sessioneInPresenza.getOrario_inizio_timestamp()
                                )
                            );

                            for (Ricetta ricetta : sessioneInPresenza.getRicette()) {
                                ricetta.setIngredienti(getIngredientiToDatabase(ricetta.getID()));
                            }
                        }
                    }
                }

                return nuovoChef;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    
    // Metodo per ottenere i corsi dello chef dal database
    public LinkedList<Corso> getCorsiToDatabase(int id) {
    	CorsoDAO corsoDAO = new CorsoDAO(conn);
    	try {
			return corsoDAO.getCorsi(id);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
    }
    
    // Metodo per ottenere le sessioni di un corso dal database
    public LinkedList<Sessione> getSessioniToDatabase(int id) {
    	Sessione_onlineDAO sessione_onlineDAO = new Sessione_onlineDAO(conn);
    	Sessione_in_presenzaDAO sessione_in_presenzaDAO = new Sessione_in_presenzaDAO(conn);
    	try {
    		LinkedList<Sessione> sessioni = new LinkedList<Sessione>(sessione_onlineDAO.getSessioniOnline(id));
    		sessioni.addAll(sessione_in_presenzaDAO.getSessioniPresenza(id));
    		return sessioni;
    	} catch (SQLException e) {
			throw new DatabaseException(e);
		}
    }
    
    // Metodo per ottenere le ricette di una sessione in presenza dal database
    public LinkedList<Ricetta> getRicetteToDatabase(String luogo, Timestamp orarioInizio) {
		RicettaDAO ricettaDAO = new RicettaDAO(conn);
		try {
			return ricettaDAO.getRicetta(luogo, orarioInizio);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
    
    // Metodo per ottenere gli ingredienti di una ricetta dal database
    public LinkedList<Ingrediente> getIngredientiToDatabase(int id) {
		IngredienteDAO ingredienteDAO = new IngredienteDAO(conn);
		try {
			return ingredienteDAO.getIngrediente(id);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
    
    // Metodo per salvare gli ingredienti di una ricetta nel database
    public boolean setIngredienteToDatabase(LinkedList<Ingrediente> ingredienti, int idRicetta) {
    	IngredienteDAO ingredienteDAO = new IngredienteDAO(conn);
    	try {
    		return ingredienteDAO.saveIngredienti(ingredienti, idRicetta);
    	} catch (SQLException e) {
    		throw new DatabaseException(e);
    	}
    }
    
    /*----------------------------------------- Metodi di modifiche delle dto ------------------------------------------------*/
    
    // Metodo per aggiungere una ricetta alla sessione in presenza
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
    
	// Metodo per aggiungere un corso alla lista dei corsi dello chef
    public void aggiungiCorsoAlloChef(Corso corso) {
    	chef.getCorso().add(corso);
    }
    
    // Metodo per aggiungere una sessione al corso
    private void aggiungiSessioneAlCorso(Sessione sessione) {
    	corso.getSessioni().add(sessione);
    	if (corso.getSessioni().size() > corso.getNumero_sessioni()) {
			corso.setNumero_sessioni(corso.getSessioni().size());
		}
	}  
    
    // Metodo per aggiungere una ricetta e gli ingredienti alla lista delle ricette della sessione in presenza
    public void aggiungiIngredientiAlleRicette() {
		for (Ricetta ricetta : sessione_in_presenza.getRicette()) {
			LinkedList<Ingrediente> ingredienti = getIngredientiToDatabase(ricetta.getID());
			ricetta.setIngredienti(ingredienti);
		}
	}
    
    /*----------------------------------------- main ------------------------------------------------*/
   
    public void test() {
    	String email = "anna.verdi@gmail.com";
        String password = "Verdi89@";
        chef = getAllChefToDatabase(email, password);
        chef.setCorso(getCorsiToDatabase(chef.getID()));
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