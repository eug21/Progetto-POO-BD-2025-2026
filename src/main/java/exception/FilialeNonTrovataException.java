package exception;

/**
 * Eccezione filiale non trovata.
 */
public class FilialeNonTrovataException extends RuntimeException {
    /**
     * Viene lanciata quando una filiale non viene trovata.
     *
     * @param message the message
     */
    public FilialeNonTrovataException(String message) {
        super(message);
    }
}
