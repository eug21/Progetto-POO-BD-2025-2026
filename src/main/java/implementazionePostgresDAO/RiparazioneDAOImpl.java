package implementazionePostgresDAO;

import dao.RiparazioneDAO;
import model.Riparazione;
import model.Veicolo;
import database.ConnessioneDatabase;

import java.sql.*;

import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

/**
 * The type Riparazione dao.
 */
public class RiparazioneDAOImpl implements RiparazioneDAO {

    private Connection connection;

    /**
     * Instantiates a new Riparazione dao.
     */
    public RiparazioneDAOImpl() {
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void save(Riparazione riparazione) {
        String sql = "INSERT INTO Riparazione (targaVeicolo, descrizioneProblema," +
                "costoStimato, costoFinale, dataRiparazione)" + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, riparazione.getTargaVeicolo());
            statement.setString(2, riparazione.getDescrizioneProblema());
            statement.setFloat(3, riparazione.getCostoStimato());
            statement.setFloat(4, riparazione.getCostoFinale());
            statement.setDate(5, new java.sql.Date(riparazione.getDataRiparazione().getTime()));


            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossibile salvare la riparazione.", ex);
        }
    }

    @Override
    public boolean update(Riparazione riparazione) {
        String sql = "UPDATE Riparazione SET costoFinale=?, descrizioneProblema=? " +
                "WHERE targaVeicolo = ? AND dataRiparazione = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setFloat(1, riparazione.getCostoFinale());
            statement.setString(2, riparazione.getDescrizioneProblema());
            statement.setString(3, riparazione.getTargaVeicolo());
            statement.setDate(4, new Date(riparazione.getDataRiparazione().getTime()));
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossibile aggiornare la riparazione.", ex);
        }
        return true;
    }




    @Override
    public List<Riparazione> findAll() {
        List<Riparazione> lista = new ArrayList();
        String sql = "SELECT * FROM Riparazione";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                lista.add(estraiRiparazione(result));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossibile generare la lista delle riparazioni.", ex);
        }
        return lista;
    }
    @Override
    public Riparazione cercaPerTarga (String targa, java.util.Date data){
        String sql = "SELECT * FROM Riparazione WHERE targaVeicolo = ? AND dataRiparazione = ? ORDER BY dataRiparazione DESC LIMIT 1 ";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, targa);
            statement.setDate(2, new Date(data.getTime()));
            try(ResultSet result = statement.executeQuery()){
                if(result.next()){
                    return estraiRiparazione(result);
                }
            }

        }
        catch (SQLException exception){
            exception.printStackTrace();
            throw new RuntimeException("Impossibile trovare la riparazione");
        }
        return null;
    }

    private Riparazione estraiRiparazione(ResultSet result) throws SQLException {
        return new Riparazione(result.getFloat("costoStimato"), result.getString("descrizioneProblema"), result.getFloat("costoFinale"), result.getDate("dataRiparazione"), result.getString("targaVeicolo"));
    }
}
