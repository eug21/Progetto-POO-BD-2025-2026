package implementazionePostgresDAO;

import dao.VeicoloDAO;
import model.*;

import database.ConnessioneDatabase;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeicoloDAOImpl implements VeicoloDAO {

    private Connection connection;

    public VeicoloDAOImpl(){
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void save(Veicolo veicolo) {
        String sql = "INSERT INTO Veicolo (targa, marca, modello, tariffaDie, statoVeicolo," + "tipoVeicolo, numeroPorte, cilindrata, capacitaCarico)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, veicolo.getTarga());
            statement.setString(2, veicolo.getMarca());
            statement.setString(3, veicolo.getModello());
            statement.setBigDecimal(4, veicolo.getTariffaDie());
            statement.setString(5, veicolo.getStatoVeicolo().name());

            if (veicolo instanceof Auto auto){
                statement.setString(6, "Auto");
                statement.setInt(7, auto.getNumeroPorte());
                statement.setNull(8, Types.INTEGER);
                statement.setNull(9, Types.DECIMAL);
            } else if (veicolo instanceof Moto moto){
                statement.setString(6, "Moto");
                statement.setNull(7, Types.INTEGER);
                statement.setInt(8, moto.getCilindrata());
                statement.setNull(9, Types.DECIMAL);
            } else if(veicolo instanceof Furgone furgone){
                statement.setString(6, "Furgone");
                statement.setNull(7, Types.INTEGER);
                statement.setNull(8, Types.INTEGER);
                statement.setFloat(9, furgone.getCapacitaCarico());
            }
            statement.executeUpdate();

        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile salvare il veicolo.", ex);
        }
    }

    @Override
    public Veicolo trovaPerTarga(String targa) {
        String sql = "SELECT * FROM Veicolo WHERE targa = ? ";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, targa);

            try(ResultSet result = statement.executeQuery()){
                if(result.next()){
                    return estraiResultVeicolo(result);
                }
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile trovare il veicolo.", ex);
        }
        return null;
    }

    @Override
    public void update(Veicolo veicolo)  {

        String sql = "UPDATE Veicolo SET marca = ?, modello = ?, tariffaDie = ?, statoVeicolo = ?" + " WHERE targa = ? ";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, veicolo.getMarca());
            statement.setString(2, veicolo.getModello());
            statement.setBigDecimal(3, veicolo.getTariffaDie());
            statement.setString(4, veicolo.getStatoVeicolo().name());
            statement.setString(5, veicolo.getTarga());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile modificare il veicolo.", ex);
        }

    }


    @Override
    public List<Veicolo> cercaStato(StatoVeicolo stato) {
        String sql = "SELECT * FROM Veicolo WHERE statoVeicolo = ?";
        List <Veicolo> lista = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, stato.name());
            try(ResultSet result = statement.executeQuery()){
                while(result.next()){
                    lista.add(estraiResultVeicolo(result));
                }
            }

        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Impossibile trovare il veicolo.", ex);
        }
        return lista;

    }


    private Veicolo estraiResultVeicolo(ResultSet result) throws SQLException{
        String targa = result.getString("targa");
        String modello = result.getString("modello");
        String marca = result.getString("marca");
        BigDecimal tariffa = result.getBigDecimal("tariffaDie");
        StatoVeicolo stato= StatoVeicolo.valueOf(result.getString("statoVeicolo"));
        String tipo = result.getString("tipoVeicolo");

        return switch (tipo){
            case "Auto" -> new Auto(targa, modello, marca, tariffa,stato, result.getInt("numeroPorte"));
            case "Moto" -> new Moto(targa, modello,  marca, tariffa, stato, result.getInt("cilindrata"));
            case "Furgone" -> new Furgone(targa, modello, marca, tariffa, stato, result.getFloat("capacitaCarico"));
            default -> throw new SQLException("Veicolo sconosciuto" + tipo);
        };
    }
}
