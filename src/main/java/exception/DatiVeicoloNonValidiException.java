package exception;

/**
 * Eccezione dati veicolo non validi.
 */
public class DatiVeicoloNonValidiException extends RuntimeException {
    /**
     * Viene lanciata quando un veicolo presenta dei dati non validi.
     *
     * @param message  message
     */
    public DatiVeicoloNonValidiException(String message) {
        super(message);
    }
}
