package exception;

/**
 * Eccezione dati filiale non valida.
 */
public class DatiFilialeNonValidaException extends RuntimeException {
    /**
     * Viene lanciata quando una filiale presenta dei dati non validi.
     *
     * @param message  message
     */
    public DatiFilialeNonValidaException(String message) {
        super(message);
    }
}
