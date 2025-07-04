package dto;

import java.util.*;

public class Ricetta {
	
	// Attributi
	private int ID;
	private String nome;
	private LinkedList<Ingrediente> ingredienti = new LinkedList<Ingrediente>();
	
	// Costruttore
	public Ricetta(int ID, String nome, LinkedList<Ingrediente> ingredienti) {
		this.ID = ID;
		this.nome = nome;
		this.ingredienti = (ingredienti != null) ? ingredienti : new LinkedList<Ingrediente>();
	}
	
	// Getters
	public int getID() {
		return ID;
	}
	public String getNome() {
		return nome;
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
	public void setIngredienti(LinkedList<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}
	
	// toString
	@Override
	public String toString() {
		return "Nome: " + nome;
	}
}
