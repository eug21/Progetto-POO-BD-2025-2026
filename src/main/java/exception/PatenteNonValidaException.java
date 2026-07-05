package exception;

/**
 * Eccezione patente non valida.
 */
public class PatenteNonValidaException extends RuntimeException {
    /**
     * Viene lanciata quando una patente non è valida. Esempio: formato sbagliato del numero.
     *
     * @param message  message
     */
    public PatenteNonValidaException(String message) {
        super(message);
    }
}
