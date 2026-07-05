package exception;

/**
 * Eccezione contratto non valido.
 */
public class ContrattoNonValidoException extends RuntimeException {
    /**
     * Viene lanciata quando un contratto non viene considerato valido
     *
     * @param message  message
     */
    public ContrattoNonValidoException(String message) {
        super(message);
    }
}
