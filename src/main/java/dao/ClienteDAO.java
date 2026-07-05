package dao;

import model.Cliente;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia Cliente dao.
 */
public interface ClienteDAO {
    /**
     * Salva il cliente
     *
     * @param cliente  cliente
     */
    void save(Cliente cliente);

    /**
     * Trova per patente.
     *
     * @param numeroPatente  numero patente
     * @return  cliente
     */
    Cliente trovaPerPatente (String numeroPatente) ;

    /**
     * Trova tutti i clienti .
     *
     * @return  list
     */
    List <Cliente> findAll() ;

    /**
     * Rinnovo patente.
     *
     * @param patenteVecchia  patente vecchia
     * @param patenteNuova    patente nuova
     * @return  boolean
     */
    boolean rinnovoPatente (String patenteVecchia, String patenteNuova);
}
