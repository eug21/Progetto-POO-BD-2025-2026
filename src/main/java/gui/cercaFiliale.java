package gui;

import javax.swing.*;
import controller.Controller;
import exception.DatiFilialeNonValidaException;
import exception.FilialeNonTrovataException;
import model.Filiale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * The type Cerca filiale.
 * Schermata che consente di cercare una filiale mediante il suo codice identificativo e eventualmente modificarne i campi.
 */
public class cercaFiliale extends JFrame{
    private JPanel cercaFiliale;
    private JTextField testoCodiceTextField;
    private JButton cercaButton;
    private JTextField testoViaTextField;
    private JTextField testoCittaTextField;
    private JTextField testoCAPTextField;
    private JTextField testoTelefonoTextField;
    private JButton salvaModificheButton;

    private Controller controller;

    /**
     * Instantiates a new Cerca filiale.
     *
     * @param controllerHome the controller home
     */
    public cercaFiliale(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Cerca Filiale");
        setContentPane(cercaFiliale);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        testoCAPTextField.setVisible(false);
        testoCittaTextField.setVisible(false);
        testoTelefonoTextField.setVisible(false);
        testoViaTextField.setVisible(false);
        salvaModificheButton.setVisible(false);

        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codice = testoCodiceTextField.getText().trim().toUpperCase();
                if (!codice.matches("^[A-Z0-9]+$")) {
                    JOptionPane.showMessageDialog(null, "Il codice filiale deve contenere solo lettere e numeri.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(codice.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try{
                    Filiale filiale = controller.cercaConIdFiliale(codice);
                    testoCAPTextField.setText(filiale.getCap());
                    testoCittaTextField.setText(filiale.getCitta());
                    testoViaTextField.setText(filiale.getVia());
                    testoTelefonoTextField.setText(filiale.getNumeroTelefono());

                    testoCAPTextField.setVisible(true);
                    testoCittaTextField.setVisible(true);
                    testoTelefonoTextField.setVisible(true);
                    testoViaTextField.setVisible(true);
                    salvaModificheButton.setVisible(true);
                } catch (FilialeNonTrovataException eccezione){
                    JOptionPane.showMessageDialog(null, eccezione.getMessage(), "La filiale non esiste", JOptionPane.ERROR_MESSAGE);
                }catch (Exception eccezione) {
                    JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);
    
                }


            }
        });
        salvaModificheButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codice = testoCodiceTextField.getText().trim().toUpperCase();
                String via = testoViaTextField.getText().trim();
                String citta = testoCittaTextField.getText().trim();
                String cap = testoCAPTextField.getText().trim();
                if(!cap.matches("^[0-9]{5}$")){
                    JOptionPane.showMessageDialog(null, "Il CAP deve contenere 5 numeri", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String telefono = testoTelefonoTextField.getText().trim();
                if(telefono.matches("^[0-9]{9,11}$")){
                    JOptionPane.showMessageDialog(null, "Numero telefono non valido", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(codice.isEmpty() || via.isEmpty() || citta.isEmpty() || cap.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try{
                    controller.modificaDatiFiliale(codice, via, cap, citta, telefono, codice);
                    JOptionPane.showMessageDialog(null, "Dati modificati con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                } catch (DatiFilialeNonValidaException eccezione){
                    JOptionPane.showMessageDialog(null, "I dati inseriti non sono validi", "Errore", JOptionPane.ERROR_MESSAGE);
                } catch (FilialeNonTrovataException eccezione){
                    JOptionPane.showMessageDialog(null, "Filiale non trovata", "Errore", JOptionPane.ERROR_MESSAGE);
                }
                    catch (Exception eccezione) {
                      JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);
    
                    }
            }
        });
    }
}
