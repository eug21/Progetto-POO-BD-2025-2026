package exception;

/**
 * Eccezione riparazione non trovata.
 */
public class RiparazioneNonTrovateException extends RuntimeException{

    /**
     * Viene lanciata quando una riparazione non viene trovata.
     *
     * @param message  message
     */
    public RiparazioneNonTrovateException(String message){
        super(message);
        
    }
}
