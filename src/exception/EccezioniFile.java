package exception;

public class EccezioniFile extends RuntimeException {
	
	// Attributo
    private static final long serialVersionUID = 1L;
	
    // Costruttore
    public EccezioniFile(Throwable cause) {
        super("ERRORE DURANTE L'ACCESSO AL FILE", cause);
    }
    
}