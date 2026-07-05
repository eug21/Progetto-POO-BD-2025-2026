package exception;

/**
 * Eccezione meccanico non trovato.
 */
public class MeccanicoNonTrovatoException extends Exception{

    /**
     * Viene lanciata quando un meccanico non viene trovato.
     *
     * @param messaggio  messaggio
     */
    public MeccanicoNonTrovatoException(String messaggio){
        super(messaggio);
    }

}
