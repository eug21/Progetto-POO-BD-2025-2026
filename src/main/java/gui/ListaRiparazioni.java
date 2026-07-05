package gui;

import controller.Controller;
import exception.ContrattoNonValidoException;
import exception.DatiRiparazioneNonValidiException;
import exception.RiparazioneNonTrovateException;
import exception.VeicoloNonTrovatoException;
import model.Contratto;
import model.Riparazione;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The type Lista riparazioni.
 * Schermata che visualizza la lista delle riparazioni presenti nel sistema con possibilità di terminazione previa selezione.
 */
public class ListaRiparazioni extends JFrame {
    private JPanel lista;
    private JButton aggiornaListaButton;
    private JButton chiudiSelezionataButton;
    private JScrollPane scrool;
    private JTable table1;

    private Controller controller;
    private List <Riparazione> riparazioni;

    /**
     * Instantiates a new Lista riparazioni.
     *
     * @param controllerHome the controller home
     */
    public ListaRiparazioni(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Lista riparazioni");
        setContentPane(lista);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        aggiornaListaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String [] colonne = {"Problema", "Costo stimato", "Targa", "Costo Finale", "Data riparazione"};
                DefaultTableModel modello = new DefaultTableModel(null, colonne);
                riparazioni = controller.getTutteRiparazioni();
                if(riparazioni != null){
                    for (Riparazione r: riparazioni){
                        modello.addRow(new Object[]{ r.getDescrizioneProblema(),
                                r.getCostoStimato(),
                                r.getTargaVeicolo(),
                                r.getCostoFinale(),
                                r.getDataRiparazione()});
                    }
                }
                table1.setModel(modello);
            }
        });
        chiudiSelezionataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    int riga = table1.getSelectedRow();
                    if(riga == -1){
                        JOptionPane.showMessageDialog(null, "Devi selezionare un contratto", "Attenzione",  JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Riparazione daChiudere = riparazioni.get(riga);
                    if(daChiudere.getCostoFinale() > 0){
                        JOptionPane.showMessageDialog(null, "Riparazione gia chiusa", "Attenzione", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    int conferma = JOptionPane.showConfirmDialog(null, "Sei sicuro di volere chiudere la riparazione ?", "Conferma", JOptionPane.YES_NO_OPTION);
                    if(conferma != JOptionPane.YES_OPTION){
                        return;
                    }

                    String inputPrezzo = JOptionPane.showInputDialog(null, "Riparazione della targa: " + daChiudere.getTargaVeicolo() + ".\n Inserisci il costo finale della riparazione: ", "Chiusura riparazione", JOptionPane.QUESTION_MESSAGE);
                    if(inputPrezzo == null || inputPrezzo.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Inserisci un costo per continuare", "Attenzione", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    try{
                       inputPrezzo =  inputPrezzo.replace(",", ".");
                        float costoFinale = Float.parseFloat(inputPrezzo);
                        if(costoFinale < 0){
                            JOptionPane.showMessageDialog(null, "Costo non valido", "Attenzione", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        controller.chiudiRiparazioneVeicolo(daChiudere.getTargaVeicolo(), costoFinale, daChiudere.getDataRiparazione());
                        JOptionPane.showMessageDialog(null, "Riparazione chiusa con successo", "Riparazione chiusa", JOptionPane.INFORMATION_MESSAGE);
                        aggiornaListaButton.doClick();

                    } catch (RiparazioneNonTrovateException eccezione){
                        JOptionPane.showMessageDialog(null, "La riparazione non e' stata trovata", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                    } catch (DatiRiparazioneNonValidiException eccezione){
                        JOptionPane.showMessageDialog(null, "Dati non validi", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                    }
                    catch (Exception eccezione) {
                        JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);

                    }
            }
        });
        aggiornaListaButton.doClick();
    }
}
