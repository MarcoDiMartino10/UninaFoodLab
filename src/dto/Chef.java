package dto;

import java.util.*;

public class Chef {
	
	// Attributi
	private String nome;
	private String cognome;
	private String email;
	private String password;
	private String biografia;
	private String numero_tel;
	private LinkedList<Corso> corsi = new LinkedList<Corso>();
	
	// Costruttore
	public Chef(String nome, String cognome, String email, String password, String biografia, String numero_tel, LinkedList<Corso> corsi) {
	    this.nome = nome;
	    this.cognome = cognome;
	    this.email = email;
	    this.password = password;
	    this.biografia = biografia;
	    this.numero_tel = numero_tel;
	    this.corsi = (corsi != null) ? corsi : new LinkedList<>();
	}
	
	// Getters
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getBiografia() {
		return biografia;
	}
    public String getNumeroTel() {
        return numero_tel;
    }    
    public LinkedList<Corso> getCorso() {
		return corsi;
	}

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setBiografia(String biografia) {
		this.biografia = biografia;
	}
    public void setNumeroTel(String numero_tel) {
        this.numero_tel = numero_tel;
    }    
    public void setCorso(LinkedList<Corso> corsi) {
		this.corsi = corsi;
	}
    
    // Aggiungi un corso
    public void addCorso(Corso corso) {
		if (corso != null) {
			this.corsi.add(corso);
		}
	}
    
    // toString
    @Override
    public String toString() {
        return nome + " " + cognome;
    }
}
