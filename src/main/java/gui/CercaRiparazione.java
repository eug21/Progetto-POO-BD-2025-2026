package gui;

import controller.Controller;
import exception.DatiRiparazioneNonValidiException;
import exception.RiparazioneNonTrovateException;
import model.Riparazione;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * The type Cerca riparazione.
 * Schermata che consente di cercare una riparazione mediante la targa del veicolo e la data di effettuazione di tale riparazione.
 */
public class CercaRiparazione extends JFrame{
    private JPanel cerca;
    private JTextField dataTextField;
    private JTextField finaleTextField;
    private JTextField stimaTextField;
    private JTextField problemaTextField;
    private JTextField targaTextField;
    private JButton cercaButton;

    private Controller controller;
    private final DateTimeFormatter formatoDataItalia = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    /**
     * Instantiates a new Cerca riparazione.
     *
     * @param controllerHome the controller home
     */
    public CercaRiparazione(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Cerca una riparazione");
        setContentPane(cerca);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        problemaTextField.setVisible(false);
        stimaTextField.setVisible(false);
        dataTextField.setVisible(true);
        finaleTextField.setVisible(false);

        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String targa = targaTextField.getText().trim().toUpperCase();
                if(!targa.matches("^[A-Z]{2}[0-9]{3}[A-Z]{2}$")){
                    JOptionPane.showMessageDialog(null, "Formato targa non valido, rispettare quello Europeo.","Attenzione", JOptionPane.WARNING_MESSAGE);

                }
                if(targa.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                LocalDate data = LocalDate.parse(dataTextField.getText().trim(), formatoDataItalia);
                ZoneId defaultZ  = ZoneId.systemDefault();
                Date dataParametro = Date.from(data.atStartOfDay(defaultZ).toInstant());

                try{
                    Riparazione riparazione = controller.cercaRiparazionePerTarga(targa,dataParametro );
                    problemaTextField.setVisible(true);
                    stimaTextField.setVisible(true);
                    finaleTextField.setVisible(true);
                    problemaTextField.setText(riparazione.getDescrizioneProblema());
                    stimaTextField.setText(String.valueOf(riparazione.getCostoStimato()));
                    finaleTextField.setText(String.valueOf(riparazione.getCostoFinale()));

                } catch(RiparazioneNonTrovateException eccezione){
                    JOptionPane.showMessageDialog(null, "La riparazione non esiste", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                    problemaTextField.setVisible(false);
                    stimaTextField.setVisible(false);
                    dataTextField.setVisible(false);
                    finaleTextField.setVisible(false);
                } catch (Exception eccezione) {
                    JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);
    
                }
            }
        });
    }
}
