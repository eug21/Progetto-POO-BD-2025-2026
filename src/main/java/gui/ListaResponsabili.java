package gui;

import controller.Controller;
import exception.FilialeNonTrovataException;
import exception.ResponsabileNonDisponibileException;
import exception.ResponsabileNonTrovatoException;
import model.Responsabile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The type Lista responsabili.
 * Schermata che visualizza la lista dei responsabili presenti nel sistema con possibilità di eliminazione previa selezione.
 */
public class ListaResponsabili extends JFrame {
    private JPanel listaResponsabili;
    private JButton aggiornaListaButton;
    private JButton eliminaSelezionatoButton;
    private JTable table1;
    private JButton sollevaDaIncaricoButton;

    private Controller controller;

    /**
     * Instantiates a new Lista responsabili.
     *
     * @param controllerHome the controller home
     */
    public ListaResponsabili(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Lista responsabili");
        setContentPane(listaResponsabili);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);


        aggiornaListaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String [] colonne = {"Id Responsabile", "Nome", "Cognome", "Mail", "Filiale"};
                DefaultTableModel modello = new DefaultTableModel(null, colonne);
                List<Responsabile> lista = ListaResponsabili.this.controller.getTuttiResponsabili();
                if(lista != null){
                    for (Responsabile r: lista){
                        String codiceFiliale = controller.filialeResponsabile(r.getIdResponsabileID());
                        modello.addRow(new Object[]{ r.getIdResponsabileID(),
                                r.getNome(),
                                r.getCognome(),
                                r.getMail(),
                                codiceFiliale
                        });
                    }
                }
                table1.setModel(modello);
            }


        });
        eliminaSelezionatoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int riga = table1.getSelectedRow();
                if(riga == -1){
                    JOptionPane.showMessageDialog(null, "Devi selezionare un responsabile", "Attenzione",  JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String idRes = (String) table1.getValueAt(riga,0);
                int conferma = JOptionPane.showConfirmDialog(null, "Sei sicuro di volere eliminare il responsabile " + idRes +  "?", "Conferma", JOptionPane.YES_NO_OPTION);
                if(conferma != JOptionPane.YES_OPTION){
                    return;
                }
                try{
                    ListaResponsabili.this.controller.eliminaResponsabile(idRes);
                    JOptionPane.showMessageDialog(null, "Responsabile eliminato con successo", "Responsabile eliminato", JOptionPane.INFORMATION_MESSAGE);
                    aggiornaListaButton.doClick();

                } catch (ResponsabileNonTrovatoException eccezione){
                    JOptionPane.showMessageDialog(null, "Il responsabile non e' stato trovato", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                } catch (FilialeNonTrovataException eccezione){
                    JOptionPane.showMessageDialog(null, eccezione.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }catch(ResponsabileNonDisponibileException eccezione){
                    JOptionPane.showMessageDialog(null, eccezione.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception eccezione) {
                    JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);
    
                }
            }
        });
        aggiornaListaButton.doClick();
        sollevaDaIncaricoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int riga = table1.getSelectedRow();
                if(riga == -1){
                    JOptionPane.showMessageDialog(null, "Devi selezionare un responsabile", "Attenzione",  JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String idRes = (String) table1.getValueAt(riga, 0);
                String codiceFiliale = (String) table1.getValueAt(riga, 4);
                if(codiceFiliale.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Il responsabile non ha una filiale assegnata", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int conferma = JOptionPane.showConfirmDialog(null, "Sei sicuro di volere sollevare il responsabile " + idRes +  " dal suo incarico ?", "Conferma", JOptionPane.YES_NO_OPTION);
                if(conferma != JOptionPane.YES_OPTION){
                    return;
                }
                try{
                    controller.rimuoviResponsabileDaFiliale(idRes, codiceFiliale);
                    JOptionPane.showMessageDialog(null, "Operazione conclusa con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    aggiornaListaButton.doClick();
                } catch(ResponsabileNonTrovatoException eccezione){
                    JOptionPane.showMessageDialog(null, eccezione.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception eccezione) {
                    JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }
}
