package gui;

import controller.Controller;
import exception.ClienteNonTrovatoException;
import exception.DateContrattoNonValideException;
import exception.VeicoloNonDisponibileException;
import exception.VeicoloNonTrovatoException;
import model.Cliente;
import model.Veicolo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * The type Aggiungi contratto.
 * Schermata che consente di aggiungere un contratto nel sistema. Consente inoltre di cercare il cliente mediante numero di patente
 * e di cercare il veicolo mediante numero di targa, di calcolare il prezzo del contratto.
 */
public class AggiungiContratto extends JFrame {
    private JPanel aggiungiContratto;
    private JTextField testoPatenteTextField;
    private JTextField testoTargaTextField;
    private JTextField ritiroTextField;
    private JTextField consegnaTextField;
    private JTextField inizioTextField;
    private JTextField fineTextField;
    private JButton calcolaPrezzoButton;
    private JButton confermaButton;
    private JLabel stima;
    private JButton cercaClienteButton;
    private JButton cercaVeicoloButton;
    private JLabel cliente;
    private JLabel veicolo;

    private Controller controller; 

    private Cliente clienteTrovato = null;
    private Veicolo veicoloTrovato = null;
    private final DateTimeFormatter formatoDataItalia = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Instantiates a new Aggiungi contratto.
     *
     * @param controllerHome the controller home
     */
    public AggiungiContratto(Controller controllerHome) {
        this.controller = controllerHome;
        setTitle("Aggiungi Contratto");
        setContentPane(aggiungiContratto);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        ritiroTextField.setVisible(true);
        consegnaTextField.setVisible(true);
        inizioTextField.setVisible(true);
        fineTextField.setVisible(true);
        calcolaPrezzoButton.setVisible(true);
        stima.setVisible(true);
        confermaButton.setVisible(true);

        cercaClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patente = testoPatenteTextField.getText().trim().toUpperCase();
                if(!patente.matches("^[A-Z0-9]{9,10}$")){
                    JOptionPane.showMessageDialog(null, "Formato patente non valido, rispettare quello Europeo.","Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(patente.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                    try {
                        clienteTrovato = controller.ricercaPerPatente(patente);
                        cliente.setText(clienteTrovato.getNome() + " " + clienteTrovato.getCognome());

                    } catch (ClienteNonTrovatoException ex) {
                        JOptionPane.showMessageDialog(null,"Cliente non trovato.", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                        clienteTrovato = null;
                        cliente.setText("Nessun cliente selezionato,");
                    }


            }
        });
        cercaVeicoloButton.addActionListener(new ActionListener() {
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
                    veicoloTrovato = controller.cercaTarga(targa);

                    if(!veicoloTrovato.verificaDisponibile()){
                        JOptionPane.showMessageDialog(null, "Il veicolo trovato non e' disponibile", "Errore", JOptionPane.ERROR_MESSAGE);
                        veicoloTrovato = null;
                        return;
                    }
                    veicolo.setText(veicoloTrovato.getMarca() + " " + veicoloTrovato.getModello());

                } catch (VeicoloNonTrovatoException eccezione){
                    JOptionPane.showMessageDialog(null, "Il veicolo non esiste", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                    veicoloTrovato = null;
                    veicolo.setText("Nessun veicolo selezionato.");
                }
            }
        });
        calcolaPrezzoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(veicoloTrovato == null){
                    JOptionPane.showMessageDialog(null,"Seleziona un veicolo", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try{
                    LocalDate dataInizio = LocalDate.parse(inizioTextField.getText().trim(), formatoDataItalia);
                    LocalDate dataFine = LocalDate.parse(fineTextField.getText().trim(), formatoDataItalia);
                    BigDecimal prezzo = controller.calcolaCostoNoleggio(veicoloTrovato, dataInizio, dataFine);

                    stima.setText("Prezzo stimato: € " + prezzo.toString());
                    stima.setVisible(true);
                    confermaButton.setVisible(true);

                } catch (DateContrattoNonValideException eccezione){
                    JOptionPane.showMessageDialog(null, "Date non valide", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(clienteTrovato == null || veicoloTrovato == null){
                        JOptionPane.showMessageDialog(null, "Cerca un cliente e un veicolo", "Attenzione", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    String filialeRitiro = ritiroTextField.getText().trim();
                    String filialeConsegna = consegnaTextField.getText().trim();
                    LocalDate inizio = LocalDate.parse(inizioTextField.getText().trim(), formatoDataItalia);
                    LocalDate fine = LocalDate.parse(fineTextField.getText().trim(), formatoDataItalia);

                    controller.aggiungiContratto(filialeRitiro, filialeConsegna, inizio, fine, BigDecimal.ZERO, clienteTrovato, veicoloTrovato);
                    JOptionPane.showMessageDialog(null, "Contratto creato con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);

                    inizioTextField.setText("");
                    fineTextField.setText("");
                    confermaButton.setVisible(false);

                } catch (Exception eccezione) {
                 JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);
    
                }
            }
        });


    }
}
