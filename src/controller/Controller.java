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
    private Sessione_in_presenza sessione_in_presenza;
    private Sessione_online sessione_online;
    private RicettaDAO ricettaDAO;
    private IngredienteDAO ingredienteDAO;
    private Sessione_onlineDAO sessione_onlineDAO;
    private Sessione_in_presenzaDAO sessione_in_presenzaDAO;
    private ChefDAO chefDAO;
    private CorsoDAO corsoDAO;
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
        //new ChefDAO(conn);
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
	
	// Apri RegistrazioneFrame da LoginFrame
	public void apriRegistrazioneFrame() {
  		registrazioneFrame = new RegistrazioneFrame(this);
  		registrazioneFrame.setVisible(true);
  	}
    
	// Apre HomepageFrame da LoginFrame
    public void apriHomepageFrame() {
    	homepageFrame = new HomepageFrame(this);
    	homepageFrame.setVisible(true);
    }
    
    // Apri infoCorsoFrame e chiudi homepageFrame
    public void apriInfoCorsoFrame() {
    	infoCorsoFrame = new InfoCorsoFrame(this);
    	infoCorsoFrame.setVisible(true);
    }
    
 // Apri aggiungiCorsoDialog per aggiungere un nuovo corso
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
    
    // Apri aggiungiSessioneInPresenzaDialog per aggiungere una sessione in presenza
    public void apriAggiungiSessioneInPresenzaDialog() {
		aggiungiSessioneInPresenzaDialog = new AggiungiSessioneInPresenzaDialog(this);
		aggiungiSessioneInPresenzaDialog.setVisible(true);
	}
    
    // Apri aggiungiRicettaFrame per aggiungere una ricetta alla sessione in presenza
    public void apriAggiungiRicettaFrameBySessione() {
    	aggiungiRicettaDialog = new AggiungiRicettaDialog(this, true);
		aggiungiRicettaDialog.setVisible(true);
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
    	sessione_in_presenza = new Sessione_in_presenza(luogo, maxPosti, inizio, fine, corso.getID(), null);
    }
    
    /*------------------------------------------- Metodo utili alle interfacce grafiche ----------------------------------------------*/
    
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
    
    /*------------------------------------------- Metodi di accesso al database ----------------------------------------------*/
    
    // Metodo per ottenere il nuovo ID di ricetta
    public int nuovoIdRicetta() {
		ricettaDAO = new RicettaDAO(conn);
		try {
			return ricettaDAO.nuovoIdRicetta();
		} catch (SQLException e) {
			throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER RECUPERARE UN NUOVO ID PER LA RICETTA", e);
		}
	}
    
    // Metodo per ottenere il nuovo ID di ingrediente
    public int nuovoIdIngrediente() {
    	ingredienteDAO = new IngredienteDAO(conn);
    	try {
    		return ingredienteDAO.nuovoIdIngrediente();
    	} catch (SQLException e) {
    		throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER RECUPERARE UN NUOVO ID PER GLI INGREDIENTI", e);
    	}
    }
    
    // Metodo per salvare lo chef nel database
    public boolean saveChef(String nome, String cognome, String email, String password, String biografia, String telefono) {
		chefDAO = new ChefDAO(conn);
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
    	ricettaDAO = new RicettaDAO(conn);
    	ingredienteDAO = new IngredienteDAO(conn);
    	int idRicetta = ingredienti.get(0).getID_ricetta(); // Assunto: già assegnato correttamente

    	try {
    		ricettaDAO.saveRicetta(idRicetta, nomeRicetta, sessione_in_presenza.getLuogo(), sessione_in_presenza.getOrario_inizio_timestamp());
    		ingredienteDAO.saveIngredienti(ingredienti, ingredienti.get(0).getID_ricetta());
    	} catch (SQLException e) {
    		throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER INSERIRE UNA RICETTA O UN INGREDIENTE", e);
    	}
		aggiungiRicettaAllaSessione(nomeRicetta, ingredienti);
    }
    
    // Metodo per aggiungere un nuovo corso
    public void saveCorso(String nomeCorso, String categoria, LocalDate dataInizio, String frequenza, BigDecimal costo, int numSessioni) {
		corsoDAO = new CorsoDAO(conn);
		int idCorso;
		try {
			idCorso = corsoDAO.nuovoIdCorso();
		} catch (SQLException e) {
			throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER RECUPERARE UN NUOVO ID PER IL CORSO", e);
		}
		Corso corso = new Corso(idCorso, nomeCorso, categoria, dataInizio, frequenza, costo, numSessioni, chef.getEmail(), null);
		try {
			corsoDAO.saveCorso(corso);
		} catch (SQLException e) {
			throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER INSERIRE UN CORSO", e);
		}
		chef.addCorso(corso);
		
	}
    
    // Metodo per aggiungere una sessione online
    public void saveSessioneOnline(String link, Timestamp inizio, Timestamp fine) {
    	sessione_onlineDAO = new Sessione_onlineDAO(conn);
		sessione_online = new Sessione_online(link, inizio, fine, corso.getID());
		try {
			sessione_onlineDAO.saveSessioneOnline(sessione_online);
		} catch (SQLException e) {
			throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER INSERIRE UNA SESSIONE ONLINE", e);
		}
		aggiungiSessioneAlCorso(sessione_online);
	}
    
    // Metodo per aggiungere una sessione in presenza con ricetta e ingredienti
    public void saveSessioneAndRicettaAndIngrediente(String nomeRicetta, LinkedList<Ingrediente> ingredienti) {
    	sessione_in_presenzaDAO = new Sessione_in_presenzaDAO(conn);
    	ricettaDAO = new RicettaDAO(conn);
    	ingredienteDAO = new IngredienteDAO(conn);
    	try {
    		sessione_in_presenzaDAO.saveSessioneInPresenza(sessione_in_presenza);
    		ricettaDAO.saveRicetta(ingredienti.get(0).getID_ricetta(), nomeRicetta, sessione_in_presenza.getLuogo(), sessione_in_presenza.getOrario_inizio_timestamp());
    		ingredienteDAO.saveIngredienti(ingredienti, ingredienti.get(0).getID_ricetta());
    	} catch (SQLException e) {
    		throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER INSERIRE UNA SESSIONE IN PRESENZA O UNA RICETTA O UN INGREDIENTE", e);
    	}
    	aggiungiSessioneAlCorso(sessione_in_presenza);
    	aggiungiRicettaAllaSessione(nomeRicetta, ingredienti);
    }
    
    // Metodo per ottenere tutte le informazioni dello chef dal database
    public Chef getAllInfoChef(String email, String password) {
        chefDAO = new ChefDAO(conn);
        corsoDAO = new CorsoDAO(conn);
        sessione_onlineDAO = new Sessione_onlineDAO(conn);
        sessione_in_presenzaDAO = new Sessione_in_presenzaDAO(conn);
        ricettaDAO = new RicettaDAO(conn);
        ingredienteDAO = new IngredienteDAO(conn);
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
    private void aggiungiRicettaAllaSessione(String nomeRicetta, LinkedList<Ingrediente> ingredienti) {
		for (Corso corso1 : chef.getCorso()) {
			if (corso1.getNome().equals(corso.getNome())) {
				for (Sessione sessione : corso1.getSessioni()) {
					if (sessione instanceof Sessione_in_presenza) {
	                    Sessione_in_presenza sessioneInPresenza = (Sessione_in_presenza) sessione;
	                    if (sessioneInPresenza.getLuogo().equals(sessione_in_presenza.getLuogo()) && sessioneInPresenza.getOrario_inizio_timestamp().equals(sessione_in_presenza.getOrario_inizio_timestamp())) {
	                        sessioneInPresenza.aggiungiRicetta(new Ricetta(ingredienti.get(0).getID_ricetta(), nomeRicetta, sessioneInPresenza.getLuogo(), sessioneInPresenza.getOrario_inizio_timestamp(), ingredienti));
	                        return;
	                    }
	                }
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