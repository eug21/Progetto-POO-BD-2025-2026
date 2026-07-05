package exception;

/**
 * Eccezione responsabile non trovato.
 */
public class ResponsabileNonTrovatoException extends Exception {

    /**
     * Viene lanciata quando un responsabile non viene trovato.
     *
     * @param messaggio  messaggio
     */
    public ResponsabileNonTrovatoException(String messaggio){
        super(messaggio);
    }
}
