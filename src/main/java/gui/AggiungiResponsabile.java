package gui;

import controller.Controller;
import exception.DatiResponsabileNonValidiException;
import model.Responsabile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Aggiungi responsabile.
 * Schermata che consente di aggiungere un responsabile nel sistema.
 */
public class AggiungiResponsabile extends JFrame{
    private JPanel aggiungiResponsabile;
    private JButton aggiungiButton;
    private JTextField testoidTextField;
    private JTextField testoNomeTextField;
    private JTextField testoCognomeTextField;
    private JTextField testoMailTextField;

    private Controller controller;

    /**
     * Instantiates a new Aggiungi responsabile.
     *
     * @param controllerHome the controller home
     */
    public AggiungiResponsabile(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Aggiungi un nuovo responsabile");
        setContentPane(aggiungiResponsabile);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);


        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idResponsabile = testoidTextField.getText().trim().toUpperCase();
                String nome = testoNomeTextField.getText().trim();
                String cognome = testoCognomeTextField.getText().trim();
                String mail = testoMailTextField.getText().trim();

                if(idResponsabile.isEmpty() || nome.isEmpty() || cognome.isEmpty() || mail.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!idResponsabile.matches("^[A-Z0-9]+$")) {
                    JOptionPane.showMessageDialog(null, "L'ID Responsabile deve contenere solo lettere e numeri.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!mail.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "Formato email non valido.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try{
                    controller.aggiungiResponsabile(idResponsabile,nome, cognome, mail);
                    JOptionPane.showMessageDialog(null, "Responsabile aggiunto con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    testoidTextField.setText("");
                    testoNomeTextField.setText("");
                    testoCognomeTextField.setText("");
                    testoMailTextField.setText("");

                }
                catch(DatiResponsabileNonValidiException eccezione){
                    JOptionPane.showMessageDialog(null, "Dati inseriti non validi", "Errore", JOptionPane.ERROR_MESSAGE);
                }catch (Exception eccezione) {
                    JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }
}
