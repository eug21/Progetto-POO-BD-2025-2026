package dao;

import model.Contratto;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaccia  Contratto dao.
 */
public interface ContrattoDAO {
    /**
     * Salva il contratto.
     *
     * @param contratto      contratto
     * @param numeroPatente  numero patente
     */
    void save (Contratto contratto, String numeroPatente);

    /**
     * Trova i contratti per cliente  .
     *
     * @param numeroPatente  numero patente
     * @return  list
     */
    List <Contratto> trovaPerCliente (String numeroPatente);

    /**
     * Trova i contratti per filiale.
     *
     * @param codiceFiliale  codice filiale
     * @return  list
     */
    List <Contratto> trovaPerFiliale (String codiceFiliale);

    /**
     * Trova i contratti per periodo compreso tra le due date.
     *
     * @param dataInizio  data inizio
     * @param dataFine    data fine
     * @return  list
     */
    List <Contratto> trovaPerPeriodo (LocalDate dataInizio, LocalDate dataFine);
}
