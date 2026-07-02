package dao;

import model.Veicolo;
import model.StatoVeicolo;
import java.sql.SQLException;
import java.util.List;

public interface VeicoloDAO {

    void save(Veicolo veicolo);
    Veicolo trovaPerTarga (String targa);
    void update (Veicolo veicolo);
    List <Veicolo> cercaStato (StatoVeicolo stato);
}
