package exception;

/**
 * Eccezione dati riparazione non validi.
 */
public class DatiRiparazioneNonValidiException extends RuntimeException {
    /**
     * Viene lanciata quando una riparazione presenta dei dati non validi
     *
     * @param message message
     */
    public DatiRiparazioneNonValidiException(String message) {
        super(message);
    }
}
