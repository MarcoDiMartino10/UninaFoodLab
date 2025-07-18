package controller;

import java.io.IOException;
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
    private Sessione_online sessione_online;
    private Sessione_in_presenza sessione_in_presenza;
    
    private ChefDAO chefDAO;
    private CorsoDAO corsoDAO;
    private Sessione_onlineDAO sessione_onlineDAO;
    private Sessione_in_presenzaDAO sessione_in_presenzaDAO;
    private RicettaDAO ricettaDAO;
    private IngredienteDAO ingredienteDAO;
	
    public int count = 3;
	
	private LoginFrame loginFrame;
	private RegistrazioneFrame registrazioneFrame;
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
    	chefDAO = new ChefDAO(conn);
        corsoDAO = new CorsoDAO(conn);
        sessione_onlineDAO = new Sessione_onlineDAO(conn);
        sessione_in_presenzaDAO = new Sessione_in_presenzaDAO(conn);
        ricettaDAO = new RicettaDAO(conn);
        ingredienteDAO = new IngredienteDAO(conn);
    	apriLoginFrame();
    }
    
    public void checkcount() {
    	if (count < 0)
    		System.exit(0);
    }
    
    /*------------------------------------------- Interfacce grafiche ----------------------------------------------*/
    
    public void apriLoginFrame() {
		loginFrame = new LoginFrame(this);
		loginFrame.setVisible(true);
	}
	
	public void apriRegistrazioneFrame() {
  		registrazioneFrame = new RegistrazioneFrame(this);
  		registrazioneFrame.setVisible(true);
  	}
    
    public void apriHomepageFrame() {
    	homepageFrame = new HomepageFrame(this);
    	homepageFrame.setVisible(true);
    }
    
    public void apriInfoCorsoFrame() {
    	infoCorsoFrame = new InfoCorsoFrame(this);
    	infoCorsoFrame.setVisible(true);
    }
    
    public void apriAggiungiCorsoDialog() {
    	aggiungiCorsoDialog = new AggiungiCorsoDialog(this);
    	aggiungiCorsoDialog.setVisible(true);
    }
    
    public void apriReportMensileFrame() {
		reportMensileFrame = new ReportMensileFrame(this);
		reportMensileFrame.setVisible(true);
	}
   
    public void apriRicetteFrame() {
		ricetteFrame = new RicetteFrame(this);
		ricetteFrame.setVisible(true);
	}
   
    public void apriAggiungiRicettaDialog(boolean flag) {
		aggiungiRicettaDialog = new AggiungiRicettaDialog(this, flag);
		aggiungiRicettaDialog.setVisible(true);
    }
    
    public void apriAggiungiSessioneOnlineDialog() {
		aggiungiSessioneOnlineDialog = new AggiungiSessioneOnlineDialog(this);
		aggiungiSessioneOnlineDialog.setVisible(true);
	}
    
    public void apriAggiungiSessioneInPresenzaDialog() {
		aggiungiSessioneInPresenzaDialog = new AggiungiSessioneInPresenzaDialog(this);
		aggiungiSessioneInPresenzaDialog.setVisible(true);
	}
    
    public void apriAggiungiRicettaFrameBySessione() {
    	aggiungiRicettaDialog = new AggiungiRicettaDialog(this, true);
		aggiungiRicettaDialog.setVisible(true);
	}
    
    public void aggiornaHomepageFrame() {
    	homepageFrame.dispose();
    	homepageFrame = new HomepageFrame(this);
    	homepageFrame.setVisible(true);
    }
    public void aggiornaInfoCorsoFrame() {
		infoCorsoFrame.dispose();
		infoCorsoFrame = new InfoCorsoFrame(this);
		infoCorsoFrame.setVisible(true);
	}
    
    public void aggiornaRicetteFrame() {
    	ricetteFrame.dispose();
    	ricetteFrame = new RicetteFrame(this);
    	ricetteFrame.setVisible(true);
    }
    
    /*------------------------------------------- Get e Set ----------------------------------------------*/
    
    public Chef getChefAttribute() {
		return chef;
	}
    
    public Corso getCorsoAttribute() {
		return corso;
	}
    
    public Sessione_in_presenza getSessioneAttribute() {
    	return sessione_in_presenza;
    }
    
    public void setCorsoAttribute(Corso corso) {
		this.corso = corso;
	}
    
    public void setSessioneAttribute(Sessione_in_presenza sessione) {
		this.sessione_in_presenza = sessione;
	}
    
    // Crea sessione e la setta come attributo della classe
    public void setNewSessioneAttribute(String luogo, Timestamp inizio, Timestamp fine, int maxPosti) {
    	sessione_in_presenza = new Sessione_in_presenza(luogo, maxPosti, inizio, fine, null);
    }
    
    /*------------------------------------------- Metodo utili alle interfacce grafiche ----------------------------------------------*/
    
    // Metodo per ottenere le categorie dei corsi
    public LinkedList<Corso> getCorsiFiltratiPerCategoria(String categoria) {
        LinkedList<Corso> corsiFiltrati = new LinkedList<>();
        for (Corso corso : chef.getCorso()) {
            if (categoria.equalsIgnoreCase("Tutti") || corso.getCategoria().equalsIgnoreCase(categoria)) {
                corsiFiltrati.add(corso);
            }
        }
        return corsiFiltrati;
    }
    
    /*------------------------------------------- Metodi di accesso al database ----------------------------------------------*/
    
    // Metodo per ottenere il nuovo ID di ricetta
    public int nuovoIdRicetta() {
		try {
			return ricettaDAO.nuovoIdRicetta();
		} catch (SQLException e) {
			throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER RECUPERARE UN NUOVO ID PER LA RICETTA", e);
		}
	}
    
    // Metodo per ottenere il nuovo ID di ingrediente
    public int nuovoIdIngrediente() {
    	try {
    		return ingredienteDAO.nuovoIdIngrediente();
    	} catch (SQLException e) {
    		throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER RECUPERARE UN NUOVO ID PER GLI INGREDIENTI", e);
    	}
    }
    
    // Metodo per salvare lo chef nel database
    public boolean saveChef(String nome, String cognome, String email, String password, String biografia, String telefono) {
		chef = new Chef(nome, cognome, email, password, biografia, telefono, null);
		try {
			chefDAO.saveChef(chef);
		} catch (SQLException e) {
			if (e.getSQLState().equals("23505")) {
			    return false;
			}
			throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER INSERIRE UNO CHEF", e);
		}
		return true;
    }
    
    // Metodo per aggiungere una nuova ricetta
    public void saveRicettaAndIngrediente(String nomeRicetta, LinkedList<Ingrediente> ingredienti) {
    	int idRicetta;

    	try {
    		idRicetta = ricettaDAO.nuovoIdRicetta();
    		ricettaDAO.saveRicetta(idRicetta, nomeRicetta, sessione_in_presenza.getLuogo(), sessione_in_presenza.getOrario_inizio_timestamp());
    		ingredienteDAO.saveIngredienti(ingredienti, idRicetta);
    	} catch (SQLException e) {
    		throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER INSERIRE UNA RICETTA O UN INGREDIENTE", e);
    	}
		aggiungiRicettaAllaSessione(nomeRicetta, idRicetta, ingredienti);
    }
    
    // Metodo per aggiungere un nuovo corso
    public void saveCorso(String nomeCorso, String categoria, LocalDate dataInizio, String frequenza, BigDecimal costo, int numSessioni) {
		int idCorso;
		try {
			idCorso = corsoDAO.nuovoIdCorso();
			Corso corso = new Corso(idCorso, nomeCorso, categoria, dataInizio, frequenza, costo, numSessioni, null);
			corsoDAO.saveCorso(corso, chef.getEmail());
			chef.addCorso(corso);
		} catch (SQLException e) {
			throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER RECUPERARE UN NUOVO ID PER IL CORSO O DURANTE L'ACCESSO AL DATABASE PER INSERIRE UN CORSO", e);
		}
    }
    
    // Metodo per aggiungere una sessione online
    public void saveSessioneOnline(String link, Timestamp inizio, Timestamp fine) {
		sessione_online = new Sessione_online(link, inizio, fine);
		try {
			sessione_onlineDAO.saveSessioneOnline(sessione_online, corso.getID());
		} catch (SQLException e) {
			throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER INSERIRE UNA SESSIONE ONLINE", e);
		}
		aggiungiSessioneAlCorso(sessione_online);
	}
    
    // Metodo per aggiungere una sessione in presenza con ricetta e ingredienti
    public void saveSessioneAndRicettaAndIngrediente(String nomeRicetta, LinkedList<Ingrediente> ingredienti) {
    	int idRicetta;
    	try {
    		sessione_in_presenzaDAO.saveSessioneInPresenza(sessione_in_presenza, corso.getID());
    		idRicetta = ricettaDAO.nuovoIdRicetta();
    		ricettaDAO.saveRicetta(idRicetta, nomeRicetta, sessione_in_presenza.getLuogo(), sessione_in_presenza.getOrario_inizio_timestamp());
    		ingredienteDAO.saveIngredienti(ingredienti, idRicetta);
    	} catch (SQLException e) {
    		throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER INSERIRE UNA SESSIONE IN PRESENZA O UNA RICETTA O UN INGREDIENTE", e);
    	}
    	aggiungiSessioneAlCorso(sessione_in_presenza);
    	aggiungiRicettaAllaSessione(nomeRicetta, idRicetta, ingredienti);
    }
    
    // Metodo per ottenere tutte le informazioni dello chef dal database
    public Chef getAllInfoChef(String email, String password) {
        try {
            chef = chefDAO.getChef(email, password);
            if (chef == null) {
                return null;
            }
            LinkedList<Corso> corsi = corsoDAO.getCorsi(email);
            chef.setCorso(corsi);
            for (Corso corso : corsi) {
            	LinkedList<Sessione> tutteSessioni = new LinkedList<>(sessione_onlineDAO.getSessioniOnline(corso.getID()));
            	tutteSessioni.addAll(sessione_in_presenzaDAO.getSessioniInPresenza(corso.getID()));
            	corso.setSessioni(tutteSessioni);
                for (Sessione sessione : tutteSessioni) {
                    if (sessione instanceof Sessione_in_presenza) {
                        Sessione_in_presenza sessioneInPresenza = (Sessione_in_presenza) sessione;
                        LinkedList<Ricetta> ricette = ricettaDAO.getRicette(sessioneInPresenza.getLuogo(), sessioneInPresenza.getOrario_inizio_timestamp());
                        sessioneInPresenza.setRicette(ricette);
                        for (Ricetta ricetta : ricette) {
                            LinkedList<Ingrediente> ingredienti = ingredienteDAO.getIngredienti(ricetta.getID());
                            ricetta.setIngredienti(ingredienti);
                        }
                    }
                }
            }
            return chef;
        } catch (SQLException e) {
            throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER RECUPERARE TUTTI I DATI DELLO CHEF", e);
        }
    }
    
    /*----------------------------------------- Metodi di modifiche delle dto ------------------------------------------------*/
    
    // Metodo per aggiungere una ricetta alla sessione in presenza
    private void aggiungiRicettaAllaSessione(String nomeRicetta, int idRicetta, LinkedList<Ingrediente> ingredienti) {
        for (Sessione sessione : corso.getSessioni()) {
            if (sessione instanceof Sessione_in_presenza) {
                Sessione_in_presenza sessioneInPresenza = (Sessione_in_presenza) sessione;
                if (sessioneInPresenza.getLuogo().equals(sessione_in_presenza.getLuogo()) && sessioneInPresenza.getOrario_inizio_timestamp().equals(sessione_in_presenza.getOrario_inizio_timestamp())) {
                    sessioneInPresenza.aggiungiRicetta(new Ricetta(idRicetta, nomeRicetta, ingredienti));
                    return;
                }
            }
        }
    }
    
    // Metodo per aggiungere una sessione al corso
    private void aggiungiSessioneAlCorso(Sessione sessione) {
    	corso.aggiungiSessione(sessione);
    	if (corso.getSessioni().size() > corso.getNumero_sessioni()) {
			corso.setNumero_sessioni(corso.getSessioni().size());
		}
	}
    
    /*----------------------------------------- main ------------------------------------------------*/
    
    // Metodo per visualizzare correttamente le interfacce grafiche su Mac
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
        try {
            DBConnection dbConnection = DBConnection.getDBConnection();
            Connection conn = dbConnection.getConnection();

            System.out.println("Connessione al database riuscita!");
            new Controller(conn);

        } catch (SQLException e) {
            throw new EccezioniDatabase("ERRORE DURANTE LA CREAZIONE DELLA CONNESSIONE AL DATABASE", e);
        } catch (IOException e) {
            throw new EccezioniFile(e);
        }
    }

}