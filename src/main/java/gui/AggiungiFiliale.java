package gui;

import controller.Controller;
import exception.DatiFilialeNonValidaException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Aggiungi filiale.
 * Schermata che consente di aggiungere una filiale nel sistema.
 */
public class AggiungiFiliale extends JFrame{
    private JPanel aggiungiFiliale;
    private JTextField testoCodice;
    private JTextField testoVia;
    private JTextField testoCitta;
    private JTextField testoCAP;
    private JTextField testoTelefono;
    private JButton aggiungiButton;

    private Controller controller;

    /**
     * Instantiates a new Aggiungi filiale.
     *
     * @param controllerHome the controller home
     */
    public AggiungiFiliale(Controller controllerHome) {
        this.controller = controllerHome;
        setTitle("Aggiunta filiale");
        setContentPane(aggiungiFiliale);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String codice = testoCodice.getText().trim().toUpperCase();
                String via = testoVia.getText().trim();
                String citta = testoCitta.getText().trim();
                String cap = testoCAP.getText().trim();
                String telefono = testoTelefono.getText().trim();

                if (codice.isEmpty() || via.isEmpty() || citta.isEmpty() || cap.isEmpty() || telefono.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!codice.matches("^[A-Z0-9]+$")) {
                    JOptionPane.showMessageDialog(null, "Il codice filiale deve contenere solo lettere e numeri.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (telefono.matches("^[0-9]{9,11}$")) {
                    JOptionPane.showMessageDialog(null, "Numero telefono non valido", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!cap.matches("^[0-9]{5}$")) {
                    JOptionPane.showMessageDialog(null, "Il CAP deve contenere 5 numeri", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    controller.aggiungiFiliale(codice, via, citta, cap, telefono);
                    JOptionPane.showMessageDialog(null, "Filiale aggiunta con successo! ", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    testoCodice.setText("");
                    testoVia.setText("");
                    testoCAP.setText("");
                    testoTelefono.setText("");
                    testoCitta.setText("");

                } catch (DatiFilialeNonValidaException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Errore dati.", JOptionPane.ERROR_MESSAGE);
                } catch (Exception eccezione) {
                    JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);

                }

            }
        });
    }
}

