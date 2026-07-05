package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Gestione riparazione.
 * Schermata che consente di gestire le operazioni relative alle riparazioni: aggiunta, lista, ricerca.
 */
public class GestioneRiparazione extends JFrame{
    private JPanel gestioneRiparazione;
    private JButton aggiungiButton;
    private JButton cercaButton;
    private JButton listaButton;

    private Controller controller;

    /**
     * Instantiates a new Gestione riparazione.
     *
     * @param controllerHome the controller home
     */
    public GestioneRiparazione(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Scegli una opzione");
        setContentPane(gestioneRiparazione);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AggiungiRiparazione frame = new AggiungiRiparazione(controller);
                frame.setVisible(true);
            }
        });
        listaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaRiparazioni frame = new ListaRiparazioni(controller);
                frame.setVisible(true);
            }
        });
        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CercaRiparazione frame = new CercaRiparazione(controller);
                frame.setVisible(true);
            }
        });
    }
}
