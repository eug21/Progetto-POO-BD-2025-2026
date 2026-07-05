package gui;

import controller.Controller;
import exception.ClienteNonTrovatoException;
import exception.ContrattoNonValidoException;
import exception.VeicoloNonTrovatoException;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The type Cerca per cliente.
 * Schermata che consente di cercare i contratti di un cliente a partire dal numero di patente. La visualizzazione
 * costituisce sia uno storico che una visione dei contratti attivi.
 * Un contratto può essere terminato mediante apposito pulsante.
 */
public class CercaPerCliente extends JFrame {
    private JPanel cercaPerCliente;
    private JTextField numeroPatenteTextField;
    private JButton cercaButton;
    private JScrollPane scroll;
    private JTable tabContratti;
    private JButton chiudiSelezionatoButton;

    private Controller controller;

    private List <Contratto> contrattiDelCliente;

    /**
     * Instantiates a new Cerca per cliente.
     *
     * @param controllerHome the controller home
     */
    public CercaPerCliente(Controller controllerHome) {
        this.controller = controllerHome;
        setTitle("Ricerca contratti per cliente");
        setContentPane(cercaPerCliente);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);


        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patente = numeroPatenteTextField.getText().trim().toUpperCase();
                if(patente.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(!patente.matches("^[A-Z0-9]{9,10}$")){
                    JOptionPane.showMessageDialog(null, "Formato patente non valido, rispettare quello Europeo.","Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                try{
                    Cliente cliente = controller.ricercaPerPatente(patente);
                    contrattiDelCliente = controller.getContrattiCliente(cliente);

                    if(contrattiDelCliente == null || contrattiDelCliente.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Il cliente non ha contratti", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String [] colonne = {"Filiale Ritiro", "Filiale Consegna", "Targa","Marca", "Modello", "Porte", "Cilindrata", "Carico", "Prezzo"};
                    DefaultTableModel modello = new DefaultTableModel(null, colonne);
                        for (Contratto c: contrattiDelCliente) {
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

                            } catch (Exception exception) {
                                JOptionPane.showMessageDialog(null, "Impossibile recuperare il veicolo" + targa, "Attenzione", JOptionPane.WARNING_MESSAGE);
                            }

                            Object[] riga = {c.getIdFilialeRitiro(),
                                    c.getIdFilialeConsegna(),
                                    targa,
                                    marca,
                                    modelloV,
                                    porte,
                                    cilindrata,
                                    carico,
                                    c.getPrezzo() + "€"};
                            modello.addRow(riga);
                        }

                    tabContratti.setModel(modello);
                } catch (ClienteNonTrovatoException eccezione){
                    JOptionPane.showMessageDialog(null,"Il cliente non esite", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
                    catch (Exception eccezione) {
                     JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);
    
                    }

            }
        });
        chiudiSelezionatoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int riga = tabContratti.getSelectedRow();
                if(riga == -1){
                    JOptionPane.showMessageDialog(null, "Devi selezionare un contratto", "Attenzione",  JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Contratto daChiudere = contrattiDelCliente.get(riga);
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
