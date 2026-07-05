package dao;

import model.Veicolo;
import model.StatoVeicolo;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia Veicolo dao.
 */
public interface VeicoloDAO {

    /**
     * Salva il veicolo.
     *
     * @param veicolo  veicolo
     */
    void save(Veicolo veicolo);

    /**
     * Trova per targa un veicolo.
     *
     * @param targa  targa
     * @return  veicolo
     */
    Veicolo trovaPerTarga (String targa);

    /**
     * Modifica un veicolo.
     *
     * @param veicolo  veicolo
     */
    void update (Veicolo veicolo);

    /**
     * Cerca un veicolo in base al suo stato.
     *
     * @param stato  stato
     * @return  list
     */
    List <Veicolo> cercaStato (StatoVeicolo stato);
}
