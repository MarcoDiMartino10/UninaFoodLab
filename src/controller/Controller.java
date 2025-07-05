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
    
	public int count = 3;

    //Costruttore
    public Controller(Connection conn) {
    	this.conn = conn;
    	LoginFrame loginFrame = new LoginFrame(this);
    	loginFrame.setVisible(true);
        new ChefDAO(conn);
        //test();
    }
    
    public void checkcount() {
    	if (count < 0)
    		System.exit(0);
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
		RicettaDAO ricettaDAO = new RicettaDAO(conn);
		try {
			return ricettaDAO.nuovoIdRicetta();
		} catch (SQLException e) {
			throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER RECUPERARE UN NUOVO ID PER LA RICETTA", e);
		}
	}
    
    // Metodo per ottenere il nuovo ID di ingrediente
    public int nuovoIdIngrediente() {
    	IngredienteDAO ingredienteDAO = new IngredienteDAO(conn);
    	try {
    		return ingredienteDAO.nuovoIdIngrediente();
    	} catch (SQLException e) {
    		throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER RECUPERARE UN NUOVO ID PER GLI INGREDIENTI", e);
    	}
    }
    
    // Metodo per salvare lo chef nel database
    public boolean saveChef(String nome, String cognome, String email, String password, String biografia, String telefono) {
		ChefDAO chefDAO = new ChefDAO(conn);
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
    	RicettaDAO ricettaDAO = new RicettaDAO(conn);
    	IngredienteDAO ingredienteDAO = new IngredienteDAO(conn);
    	try {
    		ricettaDAO.saveRicetta(sessione_in_presenza.getLuogo(), sessione_in_presenza.getOrario_inizio_timestamp(), nomeRicetta, ingredienti);
    		ingredienteDAO.saveIngredienti(ingredienti, ingredienti.get(0).getID_ricetta());
    	} catch (SQLException e) {
    		throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER INSERIRE UNA RICETTA O UN INGREDIENTE", e);
    	}
		aggiungiRicettaAllaSessione(nomeRicetta, ingredienti);
    }
    
    // Metodo per aggiungere un nuovo corso
    public void saveCorso(String nomeCorso, String categoria, LocalDate dataInizio, String frequenza, BigDecimal costo, int numSessioni) {
		CorsoDAO corsoDAO = new CorsoDAO(conn);
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
    	Sessione_onlineDAO sessione_onlineDAO = new Sessione_onlineDAO(conn);
		Sessione_online sessione = new Sessione_online(link, inizio, fine, corso.getID());
		try {
			sessione_onlineDAO.saveSessioneOnline(sessione);
		} catch (SQLException e) {
			throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER INSERIRE UNA SESSIONE ONLINE", e);
		}
		aggiungiSessioneAlCorso(sessione);
	}
    
    // Metodo per aggiungere una sessione in presenza con ricetta e ingredienti
    public void saveSessioneAndRicettaAndIngrediente(String nomeRicetta, LinkedList<Ingrediente> ingredienti) {
    	Sessione_in_presenzaDAO sessione_in_presenzaDAO = new Sessione_in_presenzaDAO(conn);
    	RicettaDAO ricettaDAO = new RicettaDAO(conn);
    	IngredienteDAO ingredienteDAO = new IngredienteDAO(conn);
    	try {
    		sessione_in_presenzaDAO.saveSessioneInPresenza(sessione_in_presenza);
    		ricettaDAO.saveRicetta(sessione_in_presenza.getLuogo(), sessione_in_presenza.getOrario_inizio_timestamp(), nomeRicetta, ingredienti);
    		ingredienteDAO.saveIngredienti(ingredienti, ingredienti.get(0).getID_ricetta());
    	} catch (SQLException e) {
    		throw new EccezioniDatabase("ERRORE DURANTE L'ACCESSO AL DATABASE PER INSERIRE UNA SESSIONE IN PRESENZA O UNA RICETTA O UN INGREDIENTE", e);
    	}
    	aggiungiSessioneAlCorso(sessione_in_presenza);
    	aggiungiRicettaAllaSessione(nomeRicetta, ingredienti);
    }
    
    public Chef queryChefFull(String email, String password) {
        ChefDAO chefDAO = new ChefDAO(conn);
        CorsoDAO corsoDAO = new CorsoDAO(conn);
        Sessione_onlineDAO sessioneOnlineDAO = new Sessione_onlineDAO(conn);
        Sessione_in_presenzaDAO sessionePresenzaDAO = new Sessione_in_presenzaDAO(conn);
        RicettaDAO ricettaDAO = new RicettaDAO(conn);
        IngredienteDAO ingredienteDAO = new IngredienteDAO(conn);
        try {
            chef = chefDAO.getChef(email, password);
            if (chef == null) {
                return null;
            }
            LinkedList<Corso> corsi = corsoDAO.getCorsi(email);
            chef.setCorso(corsi);
            for (Corso corso : corsi) {
            	LinkedList<Sessione> tutteSessioni = new LinkedList<>(sessioneOnlineDAO.getSessioniOnline(corso.getID()));
            	tutteSessioni.addAll(sessionePresenzaDAO.getSessioniInPresenza(corso.getID()));
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
	                        sessioneInPresenza.aggiungiRicetta(new Ricetta(ingredienti.get(0).getID_ricetta(), nomeRicetta, ingredienti));
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
   
    public void test() {
    	String email = "anna.verdi@gmail.com";
        String password = "Verdi89@";
        chef = queryChefFull(email, password);
        HomepageFrame homepageFrame = new HomepageFrame(this, new LoginFrame(this));
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