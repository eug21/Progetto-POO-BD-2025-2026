package implementazionePostgresDAO;

import dao.MeccanicoDAO;
import model.Meccanico;
import database.ConnessioneDatabase;
import model.Meccanico;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Meccanico dao.
 */
public class MeccanicoDAOImpl implements MeccanicoDAO{

    private Connection connection;

    /**
     * Instantiates a new Meccanico dao.
     */
    public MeccanicoDAOImpl(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void save(Meccanico meccanico) {
        String sql ="INSERT INTO Meccanico (idMeccanico, nome, cognome) VALUES (?,?,?)";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, meccanico.getIdMeccanico());
            statement.setString(2, meccanico.getNome());
            statement.setString(3, meccanico.getCognome());

            statement.executeUpdate();

        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile salvare il meccanico.", ex);
        }

    }

    @Override
    public Meccanico trovaPerID(String idMeccanico){
        String sql = "SELECT * FROM Meccanico WHERE idMeccanico = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, idMeccanico);

            try(ResultSet result =  statement.executeQuery()){
                if(result.next()){
                    return estraiMeccanico(result);
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile trovare il meccanico.", ex);
        }
        return null;

    }

    @Override

    public boolean delete(String idMeccanico){
        String sql = "DELETE FROM  Meccanico WHERE idMeccanico =?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, idMeccanico);
            statement.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile eliminare il meccanico.", ex);
        }
        return  true;
    }

    @Override
    public List<Meccanico> findAll(){
        List<Meccanico> lista = new ArrayList<>();
        String sql = "SELECT * FROM Meccanico";

        try(PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery()){
            while (result.next()){
                lista.add(estraiMeccanico(result));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile generare la lista meccanici.", ex);
        }
        return lista;
    }

    private Meccanico estraiMeccanico(ResultSet result) throws SQLException{
        return new Meccanico(
                result.getString("idMeccanico"),
                result.getString("nome"),
                result.getString("cognome")
        );

    }
}
