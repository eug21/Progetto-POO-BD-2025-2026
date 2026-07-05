package exception;

/**
 * Eccezione dati cliente non validi.
 */
public class DatiClienteNonValidi extends RuntimeException {
    /**
     * Viene lanciata quando un cliente presenta dati non validi.
     *
     * @param message  message
     */
    public DatiClienteNonValidi(String message) {
        super(message);
    }
}
