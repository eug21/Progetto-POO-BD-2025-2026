package implementazionePostgresDAO;

import dao.ResponsabileDAO;
import model.Responsabile;
import database.ConnessioneDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Responsabile dao.
 */
public class ResponsabileDAOImpl implements ResponsabileDAO{

    private Connection connection;

    /**
     * Instantiates a new Responsabile dao.
     */
    public ResponsabileDAOImpl(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void save(Responsabile responsabile) {
        String sql ="INSERT INTO Responsabile (idResponsabile, nome, cognome, email) VALUES (?,?,?,?)";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, responsabile.getIdResponsabileID());
            statement.setString(2, responsabile.getNome());
            statement.setString(3, responsabile.getCognome());
            statement.setString(4, responsabile.getMail());
            statement.executeUpdate();

        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile salvare il responsabile.", ex);
        }

    }

    @Override
    public Responsabile trovaPerID(String idResponsabile){
        String sql = "SELECT * FROM Responsabile WHERE idResponsabile = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, idResponsabile);

            try(ResultSet result =  statement.executeQuery()){
                if(result.next()){
                    return estraiResponsabile(result);
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile trovare il responsabile.", ex);
        }
        return null;
    }

    @Override

    public boolean delete(String idResponsabile){
        String sql = "DELETE FROM Responsabile WHERE idResponsabile =?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, idResponsabile);
            statement.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile eliminare il responsabile.", ex);
        }
        return  true;
    }

    @Override
    public List<Responsabile> findAll(){
        List<Responsabile> lista = new ArrayList<>();
        String sql = "SELECT * FROM Responsabile";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet result = statement.executeQuery();
            while (result.next()){
                lista.add(estraiResponsabile(result));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile generare la lista dei responsabili.", ex);
        }
        return lista;
    }

    @Override
    public boolean update(Responsabile responsabile){
        String sql = "UPDATE Responsabile SET nome= ?, cognome=?, email = ? WHERE idResponsabile = ?";

   try (PreparedStatement statement = connection.prepareStatement(sql)){
       statement.setString(1, responsabile.getNome());
       statement.setString(2, responsabile.getCognome());
       statement.setString(3, responsabile.getMail());
       statement.setString(4, responsabile.getIdResponsabileID());
       statement.executeUpdate();
   }
   catch(SQLException ex){
       ex.printStackTrace();
       throw new RuntimeException("Impossibile modificare il responsabile.", ex);
   }
   return true;
}

    @Override
    public boolean assegnaFiliale(String idResponsabile, String codiceFiliale) {
        String sql = "UPDATE Responsabile SET codiceFiliale = ? WHERE idResponsabile = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, codiceFiliale);
            statement.setString(2, idResponsabile);
            statement.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile assegnare il responsabile.", ex);
        }
        return true;
    }

    @Override
    public boolean rimuoviDaFiliale(String idResponsabile){
        String sql = "UPDATE Responsabile SET codiceFiliale = NULL WHERE idResponsabile = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
        statement.setString(1, idResponsabile);
        statement.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile rimuovere il responsabile.", ex);
        }
        return true;
    }

    @Override
    public boolean modificaEmail(String idResponsabile, String email){
        String sql = "UPDATE Responsabile SET email = ? WHERE idResponsabile = ? ";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(2, idResponsabile);
            statement.setString(1, email);
            statement.executeUpdate();
            return true;

        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Impossibile modificare i dati.");
        }
    }

    @Override
    public String ottieniFiliale(String idResponsabile){
        String sql = "SELECT codiceFiliale FROM Responsabile WHERE idResponsabile = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, idResponsabile);
            try(ResultSet result = statement.executeQuery()){
                if(result.next()){
                    String codice = result.getString("codiceFiliale");
                    if(codice != null){
                        return codice;
                    }
                }
            }
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }

    private Responsabile estraiResponsabile(ResultSet result) throws SQLException{
        return new Responsabile(
                result.getString("idResponsabile"),
                result.getString("nome"),
                result.getString("cognome"),
                result.getString("email")
        );

    }
}

