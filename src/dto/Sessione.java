package dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Sessione {
	
	// Attributi
	private int ID;
	private Timestamp orario_inizio;
	private Timestamp orario_fine;
	private int id_Corso;
	
	// Costruttore
	public Sessione(int ID, Timestamp orario_inizio, Timestamp orario_fine, int id_Corso) {
		this.ID = ID;
		this.orario_inizio = orario_inizio;
		this.orario_fine = orario_fine;
		this.id_Corso = id_Corso;
	}
	
	// Getters
	public int getID() {
		return ID;
	}
	public String getOrario_inizio() {
		SimpleDateFormat newFormato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return newFormato.format(orario_inizio);
	}
	public Timestamp getOrario_inizio_timestamp() {
		return orario_inizio;
	}
	public String getOrario_fine() {
		SimpleDateFormat newFormato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return newFormato.format(orario_fine);
	}
	public int getId_Corso() {
		return id_Corso;
	}
	
	// Setters
	public void setID(int ID) {
		this.ID = ID;
	}
	public void setOrario_inizio(Timestamp orario_inizio) {
		this.orario_inizio = orario_inizio;
	}
	public void setOrario_fine(Timestamp orario_fine) {
		this.orario_fine = orario_fine;
	}
	public void setId_Corso(int id_Corso) {
		this.id_Corso = id_Corso;
	}
	
	// toString
	@Override
	public String toString() {
		return "Sessione[" + "orario_inizio=" + orario_inizio + ", orario_fine=" + orario_fine + ']';
	}
	
	// Metodi
	public String getLink() {
		return null;
	}
	
	public String getLuogo() {
		return null;
	}
}
