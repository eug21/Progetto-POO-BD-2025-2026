package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Gestione meccanico.
 * Schermata che consente di gestire le operazioni relative ai meccanici: aggiunta, lista, ricerca.
 */
public class GestioneMeccanico extends JFrame{
    private JPanel gestioneMeccanico;
    private JButton aggiungiButton;
    private JButton cercaButton;
    private JButton listaButton;

    private Controller controller;

    /**
     * Instantiates a new Gestione meccanico.
     *
     * @param controllerHome the controller home
     */
    public GestioneMeccanico(Controller controllerHome){
        this.controller = controllerHome;

        setTitle("Scegli un'opzione");
        setContentPane(gestioneMeccanico);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AggiungiMeccanico frame = new AggiungiMeccanico(controller);
                frame.setVisible(true);
            }
        });
        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CercaMeccanico frame = new CercaMeccanico(controller);
                frame.setVisible(true);
            }
        });
        listaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaMeccanici frame = new ListaMeccanici(controller);
                frame.setVisible(true);
            }
        });
    }
}
