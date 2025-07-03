/*package dao;

import java.sql.*;
import java.util.*;

import dto.*;;

public class SessioneDAO {
	
	// Attributi
	private Sessione_onlineDAO sessioneOnlineDAO;
	private Sessione_in_presenzaDAO sessioneInPresenzaDAO;
	
	// Costruttore
	public SessioneDAO(Connection conn) {
		this.sessioneOnlineDAO = new Sessione_onlineDAO(conn);
		this.sessioneInPresenzaDAO = new Sessione_in_presenzaDAO(conn);
	}
	
	// Metodi
	public LinkedList<Sessione> getSessioniByCorsoId(int idCorso) {
		LinkedList<Sessione> sessioni = new LinkedList<Sessione>(sessioneOnlineDAO.getSessioniOnline(idCorso));
		sessioni.addAll(sessioneInPresenzaDAO.getSessioniPresenza(idCorso));
		return sessioni;
	}
}*/