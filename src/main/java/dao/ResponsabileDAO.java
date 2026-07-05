
package  dao;

import model.Responsabile;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia Responsabile dao.
 */
public interface ResponsabileDAO{
    /**
     * Salva il responsabile.
     *
     * @param responsabile  responsabile
     */
    void save(Responsabile responsabile);

    /**
     * Trova il responsabile per id responsabile.
     *
     * @param idResponsabile  id responsabile
     * @return  responsabile
     */
    Responsabile trovaPerID(String idResponsabile);

    /**
     * Elimina il responsabile .
     *
     * @param idResponsabile  id responsabile
     * @return  boolean
     */
    boolean delete(String idResponsabile) ;

    /**
     * Trova tutti i responsabili.
     *
     * @return  list
     */
    List<Responsabile> findAll();

    /**
     * Modifica il responsabile .
     *
     * @param responsabile  responsabile
     * @return  boolean
     */
    boolean update(Responsabile responsabile);

    /**
     * Assegna a una filiale il responsabile.
     *
     * @param idResponsabile  id responsabile
     * @param codiceFiliale   codice filiale
     * @return  boolean
     */
    boolean assegnaFiliale(String idResponsabile, String codiceFiliale);

    /**
     * Rimuove da una filiale .
     *
     * @param idResponsabile  id responsabile
     * @return  boolean
     */
    boolean rimuoviDaFiliale(String idResponsabile);

    /**
     * Modifica email del responsabile.
     *
     * @param id     id
     * @param email  email
     * @return  boolean
     */
    boolean modificaEmail(String id, String email);

    /**
     * Recupera la filiale del responsabile.
     *
     * @param idResponsabile  id responsabile
     * @return  string
     */
    String ottieniFiliale(String idResponsabile);
}