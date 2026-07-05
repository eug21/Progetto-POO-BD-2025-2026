package gui;

import javax.swing.*;
import controller.Controller;
import model.TipoPatente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Gestione clienti.
 * Schermata che consente di gestire le diverse operazioni relative ai clienti: aggiunta, ricerca, lista.
 */
public class GestioneClienti extends JFrame {
    private JPanel gestioneClienti;
    private JButton listaClientiButton;
    private JButton cercaClienteButton;
    private JButton aggiungiClienteButton;

    private Controller controller;

    /**
     * Instantiates a new Gestione clienti.
     *
     * @param controllerHome the controller home
     */
    public GestioneClienti(Controller controllerHome) {
        this.controller = controllerHome;
        setTitle("Gestione Clienti");
        setContentPane(gestioneClienti);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);



        aggiungiClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             AggiungiCliente frame = new AggiungiCliente(controller);
             frame.setVisible(true);
            }
        });



        listaClientiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaClienti frame = new ListaClienti(controller);
                frame.setVisible(true);

            }
        });
        cercaClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CercaCliente frame = new CercaCliente(controller);
                frame.setVisible(true);
            }
        });
    }
}
