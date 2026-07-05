package exception;

/**
 * Eccezione dati responsabile non validi.
 */
public class DatiResponsabileNonValidiException extends RuntimeException {
    /**
     * Viene lanciata quando un responsabile presenta dei dati non validi.
     *
     * @param message  message
     */
    public DatiResponsabileNonValidiException(String message) {
        super(message);
    }
}
