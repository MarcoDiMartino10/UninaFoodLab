package dto;

import java.sql.Timestamp;

public class Sessione_online extends Sessione{
	
	// Attributo
	private String link;
	
	// Costruttore
	public Sessione_online(String link, Timestamp orario_inizio, Timestamp orario_fine, int id_Corso) {
		super(orario_inizio, orario_fine, id_Corso);
		this.link = link;
	}
	
	// Getter
	@Override
	public String getLink() {
		return link;
	}
	
	// Setter
	public void setLink(String link) {
		this.link = link;
	}
	
	// toString
	@Override
	public String toString() {
		return "Sessione_online[link='" + link + ", orario_inizio=" + getOrario_inizio() + ", orario_fine=" + getOrario_fine() + ']';
	}
	
}
