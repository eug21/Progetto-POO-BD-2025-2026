package gui;

import controller.Controller;
import exception.DatiRiparazioneNonValidiException;
import model.Riparazione;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * The type Aggiungi riparazione.
 * Schermata che consente di aggiungere una riparazione nel sistema.
 */
public class AggiungiRiparazione extends JFrame {
    private JPanel aggiungi;
    private JTextField targaTextField;
    private JTextField problemaTextField;
    private JTextField stimaTextField;
    private JTextField prezzpTextField;
    private JTextField dataTextField;
    private JButton aggiungiButton;

    private Controller controller; 
    private final DateTimeFormatter formatoDataItalia = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Instantiates a new Aggiungi riparazione.
     *
     * @param controllerHome the controller home
     */
    public AggiungiRiparazione(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Aggiungi una riparazione");
        setContentPane(aggiungi);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        prezzpTextField.setEditable(false);


        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String targa = targaTextField.getText().trim().toUpperCase();
                if(!targa.matches("^[A-Z]{2}[0-9]{3}[A-Z]{2}$")){
                    JOptionPane.showMessageDialog(null, "Formato targa non valido, rispettare quello Europeo.","Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String problema = problemaTextField.getText().trim();
                Float prezzo = 0f;
                Float stima = Float.valueOf(stimaTextField.getText().trim());
                LocalDate data = LocalDate.parse(dataTextField.getText().trim(), formatoDataItalia);
                ZoneId defaultZ  = ZoneId.systemDefault();
                Date dataParametro = Date.from(data.atStartOfDay(defaultZ).toInstant());

                if(targa.isEmpty()|| problema.isEmpty() || stima == null ||prezzo == null || data == null){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    controller.aggiungiRiparazione(problema, stima,dataParametro , prezzo, targa);
                    JOptionPane.showMessageDialog(null, "Riparazione aggiunta con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    targaTextField.setText("");
                    problemaTextField.setText("");
                    prezzpTextField.setText("");
                    dataTextField.setText("");
                    stimaTextField.setText("");

                } catch (DatiRiparazioneNonValidiException eccezione){
                    JOptionPane.showMessageDialog(null, "Dati non validi", "Errore", JOptionPane.ERROR_MESSAGE);
                } catch (Exception eccezione) {
                    JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }

}
