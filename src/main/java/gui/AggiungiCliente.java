package gui;

import controller.Controller;
import model.TipoPatente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Aggiungi cliente.
 * Schermata che consente l'aggiunta di un cliente nel sistema compilando i campi.
 */
public class AggiungiCliente extends JFrame{
    private JPanel aggiungiCliente;
    private JComboBox tipoCombo;
    private JTextField testoNome;
    private JTextField testoCognome;
    private JTextField testoCodice;
    private JTextField testoNumeroP;
    private JButton aggiungiButton;

    private Controller controller;

    /**
     * Instantiates a new Aggiungi cliente.
     *
     * @param controllerHome the controller home
     */
    public AggiungiCliente(Controller controllerHome) {
        setTitle("Aggiungi Clienti");
        setContentPane(aggiungiCliente);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        this.controller = controllerHome;

        for(TipoPatente tipo : TipoPatente.values()){
            tipoCombo.addItem(tipo);
        }
        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = testoNome.getText().trim();
                String cognome =testoCognome.getText().trim();
                String codiceFiscale = testoCodice.getText().trim().toUpperCase();
                String numeroPatente = testoNumeroP.getText().trim().toUpperCase();
                TipoPatente tipoPatente = (TipoPatente) tipoCombo.getSelectedItem();
                if(nome.isEmpty() || cognome.isEmpty() || codiceFiscale.isEmpty() || numeroPatente.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!codiceFiscale.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) {
                    JOptionPane.showMessageDialog(null, "Formato Codice Fiscale non valido", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!numeroPatente.matches("^[A-Z0-9]{9,10}$")) {
                    JOptionPane.showMessageDialog(null, "Numero patente non valido, usa il formato europeo.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    controller.registraCliente(nome, cognome, codiceFiscale, tipoPatente, numeroPatente);
                    JOptionPane.showMessageDialog(null, "Cliente registrato con successo!", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                    testoNome.setText("");
                    testoCognome.setText("");
                    testoCodice.setText("");
                    testoNumeroP.setText("");

                } catch (Exception eccezione) {
                    JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }
}
