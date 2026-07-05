package gui;

import controller.Controller;
import exception.ClienteNonTrovatoException;
import exception.DatiClienteNonValidi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Rinnovo patente.
 * Schermata che consente il rinnovo della patente del cliente ossia il cambio numero.
 */
public class rinnovoPatente extends JFrame {
    private JPanel rinnovoPatente;
    private JTextField patenteAttualeTextField;
    private JTextField nuovaPatenteTextField;
    private JButton confermaButton;
    private JButton annullaButton;

    private Controller controller;


    /**
     * Instantiates a new Rinnovo patente.
     *
     * @param vecchiaPatente the vecchia patente
     * @param controllerHome the controller home
     */
    public rinnovoPatente(String vecchiaPatente, Controller controllerHome){
        this.controller = controllerHome;
        setTitle("PROCEDURA RINNOVO PATENTE");
        setContentPane(rinnovoPatente);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();


        patenteAttualeTextField.setText(vecchiaPatente);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuovaPatente = nuovaPatenteTextField.getText().trim().toUpperCase();
                if(!nuovaPatente.matches("^[A-Z0-9]{9,10}$")){
                    JOptionPane.showMessageDialog(null, "Formato patente non valido, rispettare quello Europeo.","Attenzione", JOptionPane.WARNING_MESSAGE);
                    dispose();
                }
                if(nuovaPatente.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    dispose();
                }
                try{
                    controller.cambioPatente(vecchiaPatente, nuovaPatente);
                    JOptionPane.showMessageDialog(null, "Patente rinnovata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (DatiClienteNonValidi | ClienteNonTrovatoException exception){
                    JOptionPane.showMessageDialog(null, "Impossibile rinnovare la patente.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception eccezione) {
                JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);
    
                }

            }
        });
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });
    }
}
