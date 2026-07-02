package dao;
import model.Filiale;

import java.sql.SQLException;
import java.util.List;

public interface FilialeDAO {

    void save(Filiale filiale);
    Filiale trovaPerCodice(String codiceFiliale);
    void update (Filiale filiale);
    List <Filiale> getAll();

}
