package implementazionePostgresDAO;

import dao.ContrattoDAO;
import model.Contratto;

import java.sql.*;

import database.ConnessioneDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Contratto dao.
 */
public class ContrattoDAOImpl implements ContrattoDAO {

    private Connection connection;

    /**
     * Instantiates a new Contratto dao.
     */
    public ContrattoDAOImpl(){
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void save(Contratto contratto, String numeroPatente) {
        String sql = "INSERT INTO Contratto (idFilialeRitiro, idFilialeConsegna, targa, dataInizio, dataFine, prezzo, numeroPatente)" + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, contratto.getIdFilialeRitiro());
            statement.setString(2, contratto.getIdFilialeConsegna());
            statement.setString(3, contratto.getTargaVeicolo());
            statement.setDate(4, Date.valueOf(contratto.getDataInizio()));
            statement.setDate(5, Date.valueOf(contratto.getDataFine()));
            statement.setBigDecimal(6, contratto.getPrezzo());
            statement.setString(7, numeroPatente);
            statement.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile salvare il contratto.", ex);
        }
    }



    @Override
    public List<Contratto> trovaPerCliente(String numeroPatente) {
        String sql = "SELECT * FROM Contratto WHERE numeroPatente = ?";
        List<Contratto> lista = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,numeroPatente);
            try(ResultSet result = statement.executeQuery()){
                while (result.next()){
                    lista.add(estraiResulContratto(result));
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile creare la lista.", ex);
        }
        return lista;
    }

    @Override
    public List<Contratto> trovaPerFiliale(String codiceFiliale) {
            String sql = "SELECT * FROM Contratto WHERE idFilialeRitiro = ? OR idFilialeConsegna = ?";
        List<Contratto> lista = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,codiceFiliale);
            statement.setString(2, codiceFiliale);
            try(ResultSet result = statement.executeQuery()){
                while (result.next()){
                    lista.add(estraiResulContratto(result));
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile creare la lista.", ex);
        }
        return lista;
    }

    @Override
    public List<Contratto> trovaPerPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        String sql = "SELECT * FROM Contratto WHERE dataFine >= ? AND dataInizio <= ? ";
        List<Contratto> lista = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDate(1, Date.valueOf(dataInizio));
            statement.setDate(2,Date.valueOf(dataFine));
            try(ResultSet result = statement.executeQuery()){
                while (result.next()){
                    lista.add(estraiResulContratto(result));
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile creare la lista.", ex);
        }
        return lista;
    }

    private Contratto estraiResulContratto(ResultSet result) throws SQLException{
        return new Contratto(result.getString("idfilialeritiro"), result.getString("idfilialeconsegna"), result.getString("targa"), result.getDate("datainizio").toLocalDate(), result.getDate("datafine").toLocalDate(), result.getBigDecimal("prezzo") );
    }
}
