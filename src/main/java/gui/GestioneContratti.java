package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Gestione contratti.
 * Schermata che consente di gestire le operazioni relative ai contratti: aggiunta, ricerca per date, ricerca per cliente, ricerca per filiale.
 */
public class GestioneContratti extends JFrame{
    private JPanel gestioneContratti;
    private JButton nuovoContrattoButton;
    private JButton cercaDate;
    private JButton cercaPerClienteButton;
    private JButton cercaPerFilialeButton;

    private Controller controller;

    /**
     * Instantiates a new Gestione contratti.
     *
     * @param controllerHome the controller home
     */
    public GestioneContratti(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Gestione Contratti");
        setContentPane(gestioneContratti);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        nuovoContrattoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AggiungiContratto frame = new AggiungiContratto(controller);
                frame.setVisible(true);

            }
        });
        cercaDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CercaPerDate frame = new CercaPerDate(controller);
                frame.setVisible(true);

            }
        });
        cercaPerClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CercaPerCliente frame = new CercaPerCliente(controller);
                frame.setVisible(true);

            }
        });
        cercaPerFilialeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CercaPerFiliale frame = new CercaPerFiliale(controller);
                frame.setVisible(true);

            }
        });

    }
}
