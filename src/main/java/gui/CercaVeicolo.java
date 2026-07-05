package gui;

import controller.Controller;
import exception.VeicoloNonTrovatoException;
import model.StatoVeicolo;
import model.Veicolo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Cerca veicolo.
 * Schermata che consente di cercare un veicolo mediante il numero di targa e di eventualmente modificarne lo stato.
 */
public class CercaVeicolo extends JFrame {
    private JPanel cercaVeicolo;
    private JTextField testoTargaTextField;
    private JTextField testoMarcaTextField;
    private JTextField testoModelloTextField;
    private JTextField testoTariffaTextField;
    private JComboBox comboStato;
    private JButton cercaButton;
    private JButton aggiornaStatoButton;

    private Controller controller;

    /**
     * Instantiates a new Cerca veicolo.
     *
     * @param controllerHome the controller home
     */
    public CercaVeicolo(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Cerca un veicolo");
        setContentPane(cercaVeicolo);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        testoMarcaTextField.setVisible(false);
        testoModelloTextField.setVisible(false);
        testoTariffaTextField.setVisible(false);
        comboStato.setVisible(false);
        aggiornaStatoButton.setVisible(false);

        testoMarcaTextField.setEditable(false);
        testoModelloTextField.setEditable(false);
        testoTariffaTextField.setEditable(false);
        comboStato.setEditable(false);


        comboStato.addItem(StatoVeicolo.Disponibile);
        comboStato.addItem(StatoVeicolo.Manutenzione);
        comboStato.addItem(StatoVeicolo.Noleggiato);


        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String targa = testoTargaTextField.getText().trim().toUpperCase();
                if(!targa.matches("^[A-Z]{2}[0-9]{3}[A-Z]{2}$")){
                    JOptionPane.showMessageDialog(null, "Formato targa non valido, rispettare quello Europeo.","Attenzione", JOptionPane.WARNING_MESSAGE);

                }
                if(targa.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try{
                    Veicolo veicolo =  controller.cercaTarga(targa);

                    testoMarcaTextField.setText(veicolo.getMarca());
                    testoModelloTextField.setText(veicolo.getModello());
                    testoTariffaTextField.setText(veicolo.getTariffaDie().toString());
                    comboStato.setSelectedItem(veicolo.getStatoVeicolo());

                    testoMarcaTextField.setVisible(true);
                    testoModelloTextField.setVisible(true);
                    testoTariffaTextField.setVisible(true);
                    comboStato.setVisible(true);
                    aggiornaStatoButton.setVisible(true);

                    JOptionPane.showMessageDialog(null, "Veicolo trovato", "Successo", JOptionPane.INFORMATION_MESSAGE);

                } catch (VeicoloNonTrovatoException eccezione){
                    JOptionPane.showMessageDialog(null, "Veicolo non trovato", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                    testoMarcaTextField.setVisible(false);
                    testoModelloTextField.setVisible(false);
                    testoTariffaTextField.setVisible(false);
                    comboStato.setVisible(false);
                    aggiornaStatoButton.setVisible(false);

                }

                catch (Exception eccezione) {
                 JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);
    
                }
            }
        });


        aggiornaStatoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String targa = testoTargaTextField.getText().trim().toUpperCase();
                if(!targa.matches("^[A-Z]{2}[0-9]{3}[A-Z]{2}$")){
                    JOptionPane.showMessageDialog(null, "Formato targa non valido, rispettare quello Europeo.","Attenzione", JOptionPane.WARNING_MESSAGE);

                }
                if(targa.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                StatoVeicolo nuovoStato = (StatoVeicolo) comboStato.getSelectedItem();

                try{
                    controller.aggiornaStatoVeicolo(targa, nuovoStato);
                    JOptionPane.showMessageDialog(null, "Stato veicolo aggiornato", "Successo", JOptionPane.INFORMATION_MESSAGE);
                } catch (VeicoloNonTrovatoException eccezione){
                    JOptionPane.showMessageDialog(null, "Veicolo non trovato", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception eccezione) {
                JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);
    
                }

            }
        });
    }
}
