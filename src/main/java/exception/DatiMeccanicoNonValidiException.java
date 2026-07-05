package exception;

/**
 * Eccezione dati meccanico non validi.
 */
public class DatiMeccanicoNonValidiException extends RuntimeException {
    /**
     * Viene lanciata quando un meccanico presenta dei dati non validi.
     *
     * @param message  message
     */
    public DatiMeccanicoNonValidiException(String message) {
        super(message);
    }
}
