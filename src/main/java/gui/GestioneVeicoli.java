package gui;

import javax.swing.*;
import controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Gestione veicoli.
 * Schermata che consente di gestire le operazioni relative ai veicoli: aggiunta, lista, ricerca.
 */
public class GestioneVeicoli extends JFrame {
    private JPanel gestioneVeicoli;
    private JButton aggiungiVeicoloButton;
    private JButton cercaVeicoloButton;
    private JButton listaVeicoliButton;

    private Controller controller;

    /**
     * Instantiates a new Gestione veicoli.
     *
     * @param controllerHome the controller home
     */
    public GestioneVeicoli(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Gestione Veicoli");
        setContentPane(gestioneVeicoli);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        aggiungiVeicoloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AggiuntaVeicolo frame = new AggiuntaVeicolo(controller);
                frame.setVisible(true);

            }
        });


        listaVeicoliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaVeicolo frame = new ListaVeicolo(controller);
                frame.setVisible(true);

            }
        });


        cercaVeicoloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CercaVeicolo frame = new CercaVeicolo(controller);
                frame.setVisible(true);

            }
        });
    }

}
