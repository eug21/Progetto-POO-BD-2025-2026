package gui;

import javax.swing.*;
import controller.Controller;
import model.Auto;
import model.Furgone;
import model.Moto;
import model.StatoVeicolo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

/**
 * The type Aggiunta veicolo.
 * Schermata che consente di aggiungere un veicolo nel sistema scegliendone il tipo (auto, moto, furgone) e sbloccando cosi
 * i rispettivi attributi specifici della tipologia (numero di porte, capacità di carico, cilindrata).
 */
public class AggiuntaVeicolo extends JFrame {
    private JPanel aggiuntaVeicolo;
    private JTextField targaTesto;
    private JTextField marcaTesto;
    private JTextField modelloTesto;
    private JTextField tariffaTesto;
    private JTextField porteTesto;
    private JTextField cilindrataTesto;
    private JTextField capacitaTesto;
    private JButton aggiungiButton;
    private JComboBox tipoCombo;

    private Controller controller;

    /**
     * Instantiates a new Aggiunta veicolo.
     *
     * @param controllerHome the controller home
     */
    public AggiuntaVeicolo(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Aggiungi Veicolo");
        setContentPane(aggiuntaVeicolo);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        tipoCombo.addItem("Auto");
        tipoCombo.addItem("Moto");
        tipoCombo.addItem("Furgone");

        porteTesto.setVisible(true);
        cilindrataTesto.setVisible(false);
        capacitaTesto.setVisible(false);


        tipoCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String tipo = (String) tipoCombo.getSelectedItem();
                    porteTesto.setVisible(tipo.equals("Auto"));
                    cilindrataTesto.setVisible(tipo.equals("Moto"));
                    capacitaTesto.setVisible(tipo.equals("Furgone"));
            }
            });


        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String targa = targaTesto.getText().trim().toUpperCase();
                if(!targa.matches("^[A-Z]{2}[0-9]{3}[A-Z]{2}$") && !targa.matches("^[A-Z]{2}[0-9]{5}$")){
                    JOptionPane.showMessageDialog(null, "Formato targa non valido, rispettare quello Europeo.","Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String marca = marcaTesto.getText().trim();
                String modello = modelloTesto.getText().trim();
                String tipo = (String) tipoCombo.getSelectedItem();

                if(targa.isEmpty() || marca.isEmpty() || modello.isEmpty() || tipo.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;

                }

                try {
                    BigDecimal tariffa = new BigDecimal(tariffaTesto.getText().trim());
                    String aggiunta = "";
                    switch(tipo){
                        case "Auto":
                            aggiunta = porteTesto.getText().trim();
                            break;
                        case "Moto":
                            aggiunta = cilindrataTesto.getText().trim();
                            break;
                        case "Furgone":
                            aggiunta = capacitaTesto.getText().trim();
                            break;
                    }
                    controller.aggiungiVeicolo(tipo, targa, marca, modello, tariffa, aggiunta);

                    JOptionPane.showMessageDialog(null, "Veicolo aggiuinto con successo","Successo", JOptionPane.INFORMATION_MESSAGE);

                    targaTesto.setText("");
                    modelloTesto.setText("");
                    marcaTesto.setText("");
                    tariffaTesto.setText("");
                    porteTesto.setText("");
                    cilindrataTesto.setText("");
                    capacitaTesto.setText("");
                } catch (NumberFormatException eccezione){
                    JOptionPane.showMessageDialog(null, "Campo numerico non valido", "Errore", JOptionPane.ERROR_MESSAGE);

                } catch (Exception eccezione) {
                    JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);
    
                }
            }
        });
    }
}
