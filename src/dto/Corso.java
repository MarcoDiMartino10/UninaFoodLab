package dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Corso {

	// Attributi
	private int ID;
	private String Nome;
	private String Categoria;
	private LocalDate Data_inizio;
	private String Frequenza;
	private int Numero_sessioni;
	private BigDecimal Costo;
	private String EmailChef;
	LinkedList<Sessione> sessioni = new LinkedList<Sessione>();
	
	// Costruttore
	public Corso(int ID, String Nome, String Categoria, LocalDate Data_inizio, String Frequenza, BigDecimal Costo, int numeroSessioni, String EmailChef, LinkedList<Sessione> sessioni) {
		this.ID = ID;
		this.Nome = Nome;
		this.Categoria = Categoria;
		this.Data_inizio = Data_inizio;
		this.Frequenza = Frequenza;
		this.Costo = Costo;
		this.Numero_sessioni = numeroSessioni;
		this.EmailChef = EmailChef;
		this.sessioni = (sessioni != null) ? sessioni : new LinkedList<Sessione>();
	}
	
	// Getters
	public int getID() {
		return ID;
	}
	public String getNome() {
		return Nome;
	}
	public String getCategoria() {
		return Categoria;
	}
	public LocalDate getData_inizio() {
		return Data_inizio;
	}
	public String getData_inizio_formato() {
		return Data_inizio.getDayOfMonth() + "/" + Data_inizio.getMonthValue() + "/" + Data_inizio.getYear();
	}
	public String getFrequenza() {
		return Frequenza;
	}
	public BigDecimal getCosto() {
		return Costo;
	}
	public LinkedList<Sessione> getSessioni() {
		return sessioni;
	}
	public String getEmailChef() {
		return EmailChef;
	}
	public int getNumero_sessioni() {
		return Numero_sessioni;
	}
	
	// Setters
	public void setID(int ID) {
		this.ID = ID;
	}
	public void setNome(String Nome) {
		this.Nome = Nome;
	}
	public void setCategoria(String Categoria) {
		this.Categoria = Categoria;
	}
	public void setData_inizio(LocalDate Data_inizio) {
		this.Data_inizio = Data_inizio;
	}
	public void setFrequenza(String Frequenza) {
		this.Frequenza = Frequenza;
	}
	public void setCosto(BigDecimal Costo) {
		this.Costo = Costo;
	}
	public void setSessioni(LinkedList<Sessione> sessioni) {
		this.sessioni = sessioni;
	}
	public void setEmailChef(String emailChef) {
		this.EmailChef = emailChef;
	}
	public void setNumero_sessioni(int numeroSessioni) {
		this.Numero_sessioni = numeroSessioni;
	}
	
	// toString
	@Override
	public String toString() {
		return "Corso [Nome= " + Nome + ", Categoria= " + Categoria + ", Data_inizio= " + Data_inizio + "]";
	}
	
	// Aggiungi sessione
	public void aggiungiSessione(Sessione sessione) {
		if (sessione != null) {
			sessioni.add(sessione);
		}
	}

}