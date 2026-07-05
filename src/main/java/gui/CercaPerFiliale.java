package gui;

import controller.Controller;
import exception.ContrattoNonValidoException;
import exception.FilialeNonTrovataException;
import exception.VeicoloNonTrovatoException;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The type Cerca per filiale.
 * Schermata che consente di cercare i contratti di una filiale a partire dal suo codice identificativo, è possibile inoltre
 * chiudere un contratto mediante apposito pulsante. Vengono mostrati sia i contratti attivi che non attivi.
 */
public class CercaPerFiliale extends JFrame{
    private JPanel cercaPerFiliale;
    private JTextField codiceFilialeTextField;
    private JButton cercaButton;
    private JScrollPane scroll;
    private JTable tabContratti;
    private JButton chiudiSelezionatoButton;

    private Controller controller;

    private List <Contratto> contratti;

    /**
     * Instantiates a new Cerca per filiale.
     *
     * @param controllerHome the controller home
     */
    public CercaPerFiliale(Controller controllerHome) {
        this.controller = controllerHome;

        setTitle("Cerca contratti per filiale");
        setContentPane(cercaPerFiliale);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);


        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codice = codiceFilialeTextField.getText().trim().toUpperCase();
                if(codice.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Inserire un codice filiale", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!codice.matches("^[A-Z0-9]+$")) {
                    JOptionPane.showMessageDialog(null, "Il codice filiale deve contenere solo lettere e numeri.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                try{
                    Filiale filiale = controller.cercaConIdFiliale(codice);
                    contratti = controller.getContrattiFiliale(filiale);

                    if(contratti == null || contratti.isEmpty()){
                        JOptionPane.showMessageDialog(null, "La filiale non presenta contratti", "" , JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    String [] colonne = {"Filiale Ritiro", "Filiale Consegna", "Targa","Marca", "Modello", "Porte", "Cilindrata", "Carico", "Data Inizio", "Data Fine", "Prezzo"};
                    DefaultTableModel modello = new DefaultTableModel(null, colonne);
                    for (Contratto c : contratti){
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
                        modello.addRow(new Object[]{
                                c.getIdFilialeRitiro(),
                                c.getIdFilialeConsegna(),
                                c.getTargaVeicolo(),
                                marca,
                                modelloV,
                                porte,
                                cilindrata,
                                carico,
                                c.getDataInizio(),
                                c.getDataFine(),
                                c.getPrezzo() + "€"
                        });
                    }
                    tabContratti.setModel(modello);
                } catch (FilialeNonTrovataException eccezione){
                    JOptionPane.showMessageDialog(null, "Filiale non trovata", "Errore", JOptionPane.ERROR_MESSAGE);
                } catch (Exception eccezione) {
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
