package gui;

import controller.Controller;
import exception.MeccanicoNonTrovatoException;
import model.Meccanico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The type Lista meccanici.
 * Schermata ceh visualizza la lista dei meccanici presenti nel sistema  con possibilità di eliminazione previa selezione.
 */
public class ListaMeccanici extends JFrame{
    private JButton aggiornaListaButton;
    private JPanel lista;
    private JScrollPane scroll;
    private JTable tab;
    private JButton eliminaSelezionatoButton;

    private Controller controller;

    /**
     * Instantiates a new Lista meccanici.
     *
     * @param controllerHome the controller home
     */
    public ListaMeccanici(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Lista meccanici");
        setContentPane(lista);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);


        aggiornaListaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String [] colonne = {"ID Meccanico", "Nome", "Cognome"};
                DefaultTableModel modello = new DefaultTableModel(null, colonne);
                List<Meccanico> lista = ListaMeccanici.this.controller.listaMeccanici();
                if(lista != null){
                    for (Meccanico m: lista){
                        modello.addRow(new Object[]{ m.getIdMeccanico(),
                                m.getNome(),
                                m.getCognome()});
                    }
                }
                tab.setModel(modello);
            }

        });
        eliminaSelezionatoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int riga = tab.getSelectedRow();
                if(riga == -1){
                    JOptionPane.showMessageDialog(null, "Seleziona un meccanico da eliminare", "Attenzione" ,JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String idMec  = (String) tab.getValueAt(riga, 0);
                int conferma  = JOptionPane.showConfirmDialog(null, "Sei sicuro di volere eliminare questo meccanico: " + idMec + "?", "Eliminazione", JOptionPane.YES_NO_OPTION);

                if(conferma != JOptionPane.YES_OPTION){
                    return;
                }
                try {
                    ListaMeccanici.this.controller.eliminaMeccanico(idMec);
                    JOptionPane.showMessageDialog(null, "Meccanico eliminato con successo! ");
                    aggiornaListaButton.doClick();
                } catch (MeccanicoNonTrovatoException eccezione){
                    JOptionPane.showMessageDialog(null, "Il meccanico non esiste", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                } catch (Exception eccezione) {
                    JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);
    
                }
            }
        });
        aggiornaListaButton.doClick();
    }
}
