package implementazionePostgresDAO;

import dao.FilialeDAO;
import model.Filiale;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Filiale dao.
 */
public class FilialeDAOImpl implements FilialeDAO {

    private Connection connection;

    /**
     * Instantiates a new Filiale dao.
     */
    public FilialeDAOImpl(){
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void save(Filiale filiale) {
        String sql = "INSERT INTO Filiale (codiceFiliale, via, citta, cap, numeroTelefono)" + "VALUES (?, ?, ?, ?,?)";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, filiale.getCodiceFiliale());
            statement.setString(2, filiale.getVia());
            statement.setString(3, filiale.getCitta());
            statement.setInt(4, Integer.parseInt(filiale.getCap()));
            statement.setString(5, filiale.getNumeroTelefono());
            statement.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile salvare la filiale.", ex);
        }
    }

    @Override
    public Filiale trovaPerCodice(String codiceFiliale) {
        String sql = "SELECT * FROM Filiale WHERE codiceFiliale = ? ";
        try(PreparedStatement statement= connection.prepareStatement(sql)){
            statement.setString(1, codiceFiliale);

            try(ResultSet result = statement.executeQuery()){
                if(result.next()){
                    return estraiResult(result);
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile trovare la filiale.", ex);
        }
        return null;
    }



    @Override
    public void update(Filiale filiale) {
        String sql = "UPDATE Filiale SET via = ? , citta = ? , cap = ?, numeroTelefono = ? " + "WHERE codiceFiliale  = ? ";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, filiale.getVia());
            statement.setString(2, filiale.getCitta());
            statement.setString(3, filiale.getCap());
            statement.setString(4, filiale.getNumeroTelefono());
            statement.setString(5, filiale.getCodiceFiliale());
            statement.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile aggiornare la filiale.", ex);
        }

    }

    @Override
    public List<Filiale> getAll()  {
        String sql = "SELECT * FROM Filiale";
        List <Filiale> lista = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet result = statement.executeQuery();

            while(result.next()){
                lista.add(estraiResult(result));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile generare la lista delle filiali.", ex);
        }
        return lista;
    }

    private Filiale estraiResult(ResultSet result) throws SQLException{
        return new Filiale(result.getString("codiceFiliale"), result.getString("via"), result.getString("citta"), result.getString("cap"), result.getString("numeroTelefono"));
    }
}
