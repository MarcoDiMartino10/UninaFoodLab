package dto;

import java.sql.Timestamp;
import java.util.*;

public class Sessione_in_presenza extends Sessione {

	// Attributi
	private String luogo;
	private int max_posti;
	private LinkedList<Ricetta> ricette = new LinkedList<Ricetta>();

	// Costruttore
	public Sessione_in_presenza(String luogo, int max_posti, Timestamp orario_inizio, Timestamp orario_fine, int id_Corso, LinkedList<Ricetta> ricette) {
		super(orario_inizio, orario_fine, id_Corso);
		this.luogo = luogo;
		this.max_posti = max_posti;
		this.ricette = (ricette != null) ? ricette : new LinkedList<Ricetta>();
	}

	// Getters
	@Override
	public String getLuogo() {
		return luogo;
	}
	public int getMax_posti() {
		return max_posti;
	}
	public LinkedList<Ricetta> getRicette() {
		return ricette;
	}

	// Setters
	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}
	public void setMax_posti(int max_posti) {
		this.max_posti = max_posti;
	}
	public void setRicette(LinkedList<Ricetta> ricette) {
		this.ricette = ricette;
	}

	// toString
	@Override
	public String toString() {
		return "Sessione_in_presenza[luogo= " + luogo + ", orario_inizio=" + getOrario_inizio() + ", orario_fine=" + getOrario_fine() + ']';
	}

}
