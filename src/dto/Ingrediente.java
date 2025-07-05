package dto;

import java.math.BigDecimal;

public class Ingrediente {

	// Attributi
	private int ID;
	private String nome;
	private BigDecimal quantità;
	private String unità_di_misura;
	private int ID_ricetta;
	
	// Costruttore
	public Ingrediente(int ID, String nome, BigDecimal quantità, String unità_di_misura, int ID_ricetta) {
		this.ID = ID;
		this.nome = nome;
		this.quantità = quantità;
		this.unità_di_misura = unità_di_misura;
		this.ID_ricetta = ID_ricetta;
	}
	
	// Getters
	public int getID() {
		return ID;
	}
	public String getNome() {
		return nome;
	}
	public BigDecimal getQuantità() {
		return quantità;
	}
	public String getUnità_di_misura() {
		return unità_di_misura;
	}
	public int getID_ricetta() {
		return ID_ricetta;
	}
	
	// Setters
	public void setID(int ID) {
		this.ID = ID;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setQuantità(BigDecimal quantità) {
		this.quantità = quantità;
	}
	public void setUnità_di_misura(String unità_di_misura) {
		this.unità_di_misura = unità_di_misura;
	}
	public void setID_ricetta(int ID_ricetta) {
		this.ID_ricetta = ID_ricetta;
	}
	
	// toString
	@Override
	public String toString() {
		return "Nome: " + nome + ", Quantità: " + quantità + " " + unità_di_misura;
	}
}
