package exception;

/**
 *  Eccezione cliente non trovato.
 */
public class ClienteNonTrovatoException extends Exception {
    /**
     * Viene lanciata quando un cliente non viene trovato nel sistema.
     *
     * @param messaggio  messaggio
     */
    public ClienteNonTrovatoException(String messaggio){
        super(messaggio);
    }
}
