package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

import controller.Controller ;
import exception.FilialeNonTrovataException;
import model.Filiale;

/**
 * The type Lista filiali.
 * Schermata che visualizza la lista delle filiali presenti nel sistema.
 */
public class ListaFiliali extends  JFrame{
    private JPanel listaFiliali;
    private JButton aggiornaListaButton;
    private JTable filialiTable;
    private JButton eliminaSelezionataButton;

    private Controller controller;

    /**
     * Instantiates a new Lista filiali.
     *
     * @param controllerHome the controller home
     */
    public ListaFiliali(Controller controllerHome) {
        this.controller = controllerHome;
        setTitle("Lista filiali");
        setContentPane(listaFiliali);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        aggiornaListaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String [] colonne = {"CODICE", "VIA", "CITTA", "CAP", "TELEFONO"};

                DefaultTableModel modello = new DefaultTableModel(null, colonne);
                List<Filiale> lista = controller.getFiliali();
                if(lista != null){
                    for (Filiale f: lista){
                       modello.addRow(new Object[]{ f.getCodiceFiliale(),
                        f.getVia(),
                        f.getCitta(),
                        f.getCap(),
                        f.getNumeroTelefono()});
                    }
                }
                filialiTable.setModel(modello);
            }
        });


    }
}
