package exception;

/**
 * Eccezione responsabile non disponibile.
 */
public class ResponsabileNonDisponibileException extends RuntimeException {
    /**
     * Viene lanciata quando un responsabile è già assegnato a una filiale, quindi non disponibile.
     *
     * @param message  message
     */
    public ResponsabileNonDisponibileException(String message) {
        super(message);
    }
}
