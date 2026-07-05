package dao;

import model.Riparazione;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Interfaccia  Riparazione dao.
 */
public interface RiparazioneDAO {
    /**
     * Salva la riparazione.
     *
     * @param riparazione  riparazione
     */
    void save(Riparazione riparazione);

    /**
     * Modifica la riparazione .
     *
     * @param riparazione  riparazione
     * @return  boolean
     */
    boolean update(Riparazione riparazione);

    /**
     * Mostra tutte le riparazioni.
     *
     * @return  list
     */
    List<Riparazione> findAll();

    /**
     * Cerca per targa e data una riparazione.
     *
     * @param targa  targa
     * @param data   data
     * @return  riparazione
     */
    Riparazione cercaPerTarga(String targa, java.util.Date data);

}
