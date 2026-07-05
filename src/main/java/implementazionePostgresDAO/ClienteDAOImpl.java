package implementazionePostgresDAO;

import dao.ClienteDAO;
import model.Cliente;
import model.TipoPatente;
import database.ConnessioneDatabase;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * The type Cliente dao.
 */
public class ClienteDAOImpl implements ClienteDAO {

    private Connection connection;

    /**
     * Instantiates a new Cliente dao.
     */
    public ClienteDAOImpl(){
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    @Override
    public void save(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nome, cognome, codiceFiscale, tipoPatente, numeroPatente)" + "VALUES (?, ?, ?, ?, ?) ";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getCognome());
            statement.setString(3, cliente.getCodiceFiscale());
            statement.setString(4, String.valueOf(cliente.getTipoPatente()));
            statement.setString(5, cliente.getNumeroPatente());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossibile salvare il cliente", ex);

        }

    }

    @Override
    public Cliente trovaPerPatente(String numeroPatente) {
        String sql = "SELECT * FROM Cliente WHERE numeroPatente = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, numeroPatente);
            try(ResultSet result = statement.executeQuery()){
                if(result.next()){
                    return estraiResultCliente(result);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile trovare il cliente.",e);
        }
        return null;
    }



    @Override
    public List<Cliente> findAll(){
        String sql = "SELECT * FROM Cliente";
        List <Cliente> lista = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet result = statement.executeQuery();

            while(result.next()){
                lista.add(estraiResultCliente(result)   );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel caricamento dei clienti.", e);
        }
        return lista;
    }

    @Override
    public boolean rinnovoPatente(String patenteVecchia, String patenteNuova) {
        String sql = "UPDATE Cliente SET numeroPatente = ? WHERE numeroPatente = ? ";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, patenteNuova);
            statement.setString(2, patenteVecchia);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel rinnovo patente.", e);
        }
    }

    private Cliente estraiResultCliente(ResultSet result)throws SQLException{
        return new Cliente(result.getString("nome") , result.getString("cognome"), result.getString("codiceFiscale"), TipoPatente.valueOf(result.getString("tipoPatente")), result.getString("numeroPatente"));
    }
}
