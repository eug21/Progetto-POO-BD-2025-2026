package exception;

/**
 * Eccezione veicolo non trovato.
 */
public class VeicoloNonTrovatoException extends RuntimeException {
    /**
     * Viene lanciata quando un veicolo non viene trovato.
     *
     * @param message the message
     */
    public VeicoloNonTrovatoException(String message) {
        super(message);
    }
}
