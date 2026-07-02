package gui;

import controller.Controller;
import exception.ClienteNonTrovatoException;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListaClienti extends JFrame {
    private JPanel listaClienti;
    private JButton aggiornaListaButton;
    private JTable tabellaClienti;

    private Controller controller;

    public ListaClienti(Controller controllerHome) {
        this.controller = controllerHome;
        setTitle("Lista dei clienti");
        setContentPane(listaClienti);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);


        aggiornaListaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String [] colonne = {"Numero Patente", "Nome", "Cognome", "Codice Fiscale", "Tipo Patente"};
                DefaultTableModel modello = new DefaultTableModel(null, colonne);
                List<Cliente> lista = controller.getTuttiClienti();
                if(lista != null){
                    for (Cliente cliente: lista){
                        modello.addRow(new Object[]{ cliente.getNumeroPatente(),
                                cliente.getNome(),
                                cliente.getCognome(),
                                cliente.getCodiceFiscale(),
                                cliente.getTipoPatente()});
                    }
                }
                tabellaClienti.setModel(modello);
            }

        });


    }
}
