package dao;
import model.Filiale;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia  Filiale dao.
 */
public interface FilialeDAO {

    /**
     * Salva la filiale.
     *
     * @param filiale  filiale
     */
    void save(Filiale filiale);

    /**
     * Trova la filiale per codice filiale.
     *
     * @param codiceFiliale  codice filiale
     * @return  filiale
     */
    Filiale trovaPerCodice(String codiceFiliale);

    /**
     * Modifica la filiale.
     *
     * @param filiale  filiale
     */
    void update (Filiale filiale);

    /**
     * Trova tutte le filiali.
     *
     * @return  list
     */
    List <Filiale> getAll();

}
