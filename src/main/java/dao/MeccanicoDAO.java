
 package dao;

import model.Meccanico;
import java.sql.SQLException;
import java.util.List;

 /**
  * Interfaccia  Meccanico dao.
  */
 public interface MeccanicoDAO{
     /**
      * Salva il meccanico.
      *
      * @param meccanico  meccanico
      */
     void save(Meccanico meccanico);

     /**
      * Trova il meccanico per id meccanico.
      *
      * @param idMeccanico  id meccanico
      * @return  meccanico
      */
     Meccanico trovaPerID(String idMeccanico);

     /**
      * Elimina il meccanico.
      *
      * @param idMeccanico  id meccanico
      * @return  boolean
      */
     boolean delete(String idMeccanico) ;

     /**
      * Trova tutti i meccanici .
      *
      * @return  list
      */
     List<Meccanico> findAll() ;
}