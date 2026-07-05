package exception;

/**
 * Eccezione veicolo non disponibile.
 */
public class VeicoloNonDisponibileException extends RuntimeException {
    /**
     * Viene lanciata quando un veicolo è già noleggiato o in manutenzione, quindi non disponibile a un nuovo noleggio.
     *
     * @param message  message
     */
    public VeicoloNonDisponibileException(String message) {
        super(message);
    }
}
