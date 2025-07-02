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
	private int ID_Chef;
	LinkedList<Sessione> sessioni = new LinkedList<Sessione>();
	
	// Costruttore
	public Corso(int ID, String Nome, String Categoria, LocalDate Data_inizio, String Frequenza, BigDecimal Costo, int ID_Chef, LinkedList<Sessione> sessioni) {
		this.ID = ID;
		this.Nome = Nome;
		this.Categoria = Categoria;
		this.Data_inizio = Data_inizio;
		this.Frequenza = Frequenza;
		this.Costo = Costo;
		this.ID_Chef = ID_Chef;
		this.sessioni = sessioni;
		this.Numero_sessioni = sessioni.size();
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
	public String getFrequenza() {
		return Frequenza;
	}
	public BigDecimal getCosto() {
		return Costo;
	}
	public int getID_Chef() {
		return ID_Chef;
	}
	public LinkedList<Sessione> getSessioni() {
		return sessioni;
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
	public void setID_Chef(int ID_Chef) {
		this.ID_Chef = ID_Chef;
	}
	public void setSessioni(LinkedList<Sessione> sessioni) {
		this.sessioni = sessioni;
	}
	
	// toString
	@Override
	public String toString() {
		return "Corso [Nome= " + Nome + ", Categoria= " + Categoria + ", Data_inizio= " + Data_inizio + "]";
	}

}