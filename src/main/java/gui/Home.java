package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Home.
 * Schermata home dove è possibile scegliere le varie operazioni da svolgere.
 */
public class Home {
    private JPanel Home;
    private JLabel titolo;
    private JButton GESTIONECLIENTIButton;
    private JButton GESTIONEDIPENDENTIButton;
    private JButton GESTIONEFILIALIButton;
    private JButton GESTIONEVEICOLIButton;
    private JButton GESTIONERIPARAZIONIButton;
    private JButton GESTIONECONTRATTIButton;

    private Controller controller;

    /**
     * Instantiates a new Home.
     */
    public Home() {
        controller = new Controller();
        GESTIONECLIENTIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestioneClienti frame = new GestioneClienti(controller);
                frame.setVisible(true);

            }
        });
        GESTIONEDIPENDENTIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SceltaDipendente frame = new SceltaDipendente(controller);
                frame.setVisible(true);

            }
        });
        GESTIONEFILIALIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestioneFiliali frame = new GestioneFiliali(controller);
                frame.setVisible(true);
            }
        });
        GESTIONEVEICOLIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestioneVeicoli frame = new GestioneVeicoli(controller);
                frame.setVisible(true);
            }
        });
        GESTIONERIPARAZIONIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestioneRiparazione frame = new GestioneRiparazione(controller);
                frame.setVisible(true);
            }
        });
        GESTIONECONTRATTIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestioneContratti frame = new GestioneContratti(controller);
                frame.setVisible(true);
            }
        });
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Home");
        frame.setContentPane(new Home().Home);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
