package dto;

import java.sql.Timestamp;

public class Sessione_online extends Sessione{
	
	// Attributi
	private String link;
	
	// Costruttore
	public Sessione_online(String link, int ID, Timestamp orario_inizio, Timestamp orario_fine, int id_Corso) {
		super(ID,orario_inizio, orario_fine, id_Corso);
		this.link = link;
	}
	
	// Getters
	@Override
	public String getLink() {
		return link;
	}
	
	// Setters
	public void setLink(String link) {
		this.link = link;
	}
	
	// toString
	@Override
	public String toString() {
		return "Sessione_online[link='" + link + ", orario_inizio=" + getOrario_inizio() + ", orario_fine=" + getOrario_fine() + ']';
	}
	
}
