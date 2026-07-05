package exception;

/**
 * Eccezione date contratto non valide.
 */
public class DateContrattoNonValideException extends RuntimeException {
    /**
     * Viene lanciata quando un contratto presenta date non valide. Esempio: data di inzio successiva a quella di fine.
     *
     * @param message  message
     */
    public DateContrattoNonValideException(String message) {
        super(message);
    }
}
