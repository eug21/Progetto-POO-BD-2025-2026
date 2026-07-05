package gui;

import controller.Controller;
import exception.ContrattoNonValidoException;
import exception.DateContrattoNonValideException;
import exception.VeicoloNonTrovatoException;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The type Cerca per date.
 * Schermata che consente di cercare i contratti per un periodo compreso tra due date. I contratti possono essere
 * sia attivi che non attivi, è concessa la chiusura mediante apposito pulsante.
 */
public class CercaPerDate extends JFrame{
    private JPanel cercaPerDate;
    private JButton cerca;
    private JButton chiudi;
    private JScrollPane scroll;
    private JTable tabContratti;
    private JTextField inizioTextField;
    private JTextField fineTextField;

    private Controller controller; 
    private final DateTimeFormatter formatoDataItalia = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private List <Contratto> contratti;


    /**
     * Instantiates a new Cerca per date.
     *
     * @param controllerHome the controller home
     */
    public CercaPerDate(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Cerca contratti per date");
        setContentPane(cercaPerDate);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        cerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               LocalDate dataInizio = LocalDate.parse(inizioTextField.getText().trim(), formatoDataItalia);
               LocalDate dataFine = LocalDate.parse(fineTextField.getText().trim(), formatoDataItalia);

               if(dataInizio.isAfter(dataFine)){
                   JOptionPane.showMessageDialog(null, "La data inzial non può essere successiva alla data finale", "Errore", JOptionPane.ERROR_MESSAGE);
                   return;
               }

               if(dataInizio == null || dataFine == null){
                   JOptionPane.showMessageDialog(null, "Compila i campi.", "Errore", JOptionPane.ERROR_MESSAGE);
                   return;
               }
               try{
                   contratti = controller.contrattiPerPeriodo(dataInizio, dataFine);
                   if(contratti == null || contratti.isEmpty()){
                       JOptionPane.showMessageDialog(null, "Il periodo non presenta contratti", "" , JOptionPane.WARNING_MESSAGE);
                       return;
                   }
                   String [] colonne = {"Data Inizio", "Data Fine", "Targa", "Prezzo", "Filiale Ritiro", "Filiale Consegna", "Marca", "Modello", "Porte", "Cilindrata", "Carico"};
                   DefaultTableModel modello = new DefaultTableModel(null, colonne);
                   for(Contratto c : contratti){
                       String targa = c.getTargaVeicolo();
                       String marca = null;
                       String modelloV = null;
                       String porte = null;
                       String carico = null;
                       String cilindrata = null;
                       try {
                           Veicolo veicolo = controller.cercaTarga(targa);
                           marca = veicolo.getMarca();
                           modelloV = veicolo.getModello();
                           if (veicolo instanceof Auto) {
                               porte = String.valueOf(((Auto) veicolo).getNumeroPorte());
                           } else if (veicolo instanceof Moto) {
                               cilindrata = String.valueOf(((Moto) veicolo).getCilindrata());
                           } else if (veicolo instanceof Furgone) {
                               carico = String.valueOf(((Furgone) veicolo).getCapacitaCarico());
                           }
                       }catch (Exception exception) {
                           JOptionPane.showMessageDialog(null, "Impossibile recuperare il veicolo" + targa, "Attenzione", JOptionPane.WARNING_MESSAGE);
                       }
                       modello.addRow(new Object[]{
                               c.getDataInizio(),
                               c.getDataFine(),
                               c.getTargaVeicolo(),
                               c.getPrezzo() + "€",
                               c.getIdFilialeRitiro(),
                               c.getIdFilialeConsegna(),
                               marca,
                               modelloV,
                               porte,
                               cilindrata,
                               carico,


                       });

                   }
                   tabContratti.setModel(modello);

               } catch(DateContrattoNonValideException eccezione){
                   JOptionPane.showMessageDialog(null, eccezione.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
               }catch (Exception eccezione) {
                   JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);

               }

            }
        });



        chiudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int riga = tabContratti.getSelectedRow();
                if(riga == -1){
                    JOptionPane.showMessageDialog(null, "Devi selezionare un contratto", "Attenzione",  JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Contratto daChiudere = contratti.get(riga);
                int conferma = JOptionPane.showConfirmDialog(null, "Sei sicuro di volere chiudere il contratto ?", "Conferma", JOptionPane.YES_NO_OPTION);
                if(conferma != JOptionPane.YES_OPTION){
                    return;
                }
                try{
                    controller.chiudiContratto(daChiudere);
                    JOptionPane.showMessageDialog(null, "Contratto chiuso con successo", "Contratto chiuso", JOptionPane.INFORMATION_MESSAGE);


                } catch (VeicoloNonTrovatoException eccezione){
                    JOptionPane.showMessageDialog(null, "Il veicolo non e' stato trovato", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                } catch (ContrattoNonValidoException eccezione){
                    JOptionPane.showMessageDialog(null, "Il contratto non e' valido", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception eccezione) {
                 JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);
    
                }
            }
        });
    }
}
