package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Scelta dipendente.
 * Schermata che consente di selezionare quale categoria di dipendenti visualizzare: meccanico, responsabile.
 */
public class SceltaDipendente extends JFrame{
    private JPanel sceltaDipendente;
    private JButton responsabileButton;
    private JButton meccanicoButton;

    private Controller controller;


    /**
     * Instantiates a new Scelta dipendente.
     *
     * @param controllerHome the controller home
     */
    public SceltaDipendente(Controller controllerHome) {
        this.controller = controllerHome;

        setTitle("Seleziona un dipendente");
        setContentPane(sceltaDipendente);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        responsabileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestioneResponsabili frame = new GestioneResponsabili(controller);
                frame.setVisible(true);

            }
        });
        meccanicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestioneMeccanico frame = new GestioneMeccanico(controller);
                frame.setVisible(true);

            }
        });
    }
}
