package dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Sessione {
	
	// Attributi
	private Timestamp orario_inizio;
	private Timestamp orario_fine;
	private int id_Corso;
	
	// Costruttore
	public Sessione(Timestamp orario_inizio, Timestamp orario_fine, int id_Corso) {
		this.orario_inizio = orario_inizio;
		this.orario_fine = orario_fine;
		this.id_Corso = id_Corso;
	}
	
	// Getters
	public String getOrario_inizio() {
		SimpleDateFormat newFormato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return newFormato.format(orario_inizio);
	}
	public Timestamp getOrario_inizio_timestamp() {
		return orario_inizio;
	}
	public Timestamp getOrario_fine_timestamp() {
		return orario_fine;
	}
	public String getOrario_fine() {
		SimpleDateFormat newFormato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return newFormato.format(orario_fine);
	}
	public int getId_Corso() {
		return id_Corso;
	}
	
	// Setters
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
