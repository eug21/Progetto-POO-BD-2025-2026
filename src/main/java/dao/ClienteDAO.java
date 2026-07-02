package dao;

import model.Cliente;
import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    void save(Cliente cliente);
    Cliente trovaPerPatente (String numeroPatente) ;
    List <Cliente> findAll() ;
    boolean rinnovoPatente (String patenteVecchia, String patenteNuova);
}
