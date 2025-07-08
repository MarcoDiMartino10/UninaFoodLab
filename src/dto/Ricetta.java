package dto;

import java.sql.Timestamp;
import java.util.*;

public class Ricetta {
	
	// Attributi
	private int ID;
	private String nome;
	private String luogo;
	private Timestamp orario_inizio;
	private LinkedList<Ingrediente> ingredienti = new LinkedList<Ingrediente>();
	
	// Costruttore
	public Ricetta(int ID, String nome, String luogo, Timestamp orario_inizio, LinkedList<Ingrediente> ingredienti) {
		this.ID = ID;
		this.nome = nome;
		this.luogo = luogo;
		this.orario_inizio = orario_inizio;
		this.ingredienti = ingredienti;
	}
	
	// Getters
	public int getID() {
		return ID;
	}
	public String getNome() {
		return nome;
	}
	public String getLuogo() {
		return luogo;
	}
	public Timestamp getOrario_inizio() {
		return orario_inizio;
	}
	public LinkedList<Ingrediente> getIngredienti() {
		return ingredienti;
	}
	
	// Setters
	public void setID(int ID) {
		this.ID = ID;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}
	public void setOrario_inizio(Timestamp orario_inizio) {
		this.orario_inizio = orario_inizio;
	}
	public void setIngredienti(LinkedList<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}
	
	// toString
	@Override
	public String toString() {
		return "Nome: " + nome;
	}
}
