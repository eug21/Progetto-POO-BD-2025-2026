package gui;

import controller.Controller;
import exception.DatiMeccanicoNonValidiException;
import model.Meccanico;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Aggiungi meccanico.
 * Schermata che consente di aggiungere un meccanico nel sistema.
 */
public class AggiungiMeccanico extends JFrame {
    private JPanel aggiungi;
    private JTextField idTextField;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JButton aggiungiButton;

    private Controller controller;

    /**
     * Instantiates a new Aggiungi meccanico.
     *
     * @param controllerHome the controller home
     */
    public AggiungiMeccanico(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Aggiungi un meccanico");
        setContentPane(aggiungi);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);


        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idMec = idTextField.getText().trim().toUpperCase();
                String nome = nomeTextField.getText().trim();
                String cognome  = cognomeTextField.getText().trim();

                if(idMec.isEmpty() || nome.isEmpty() || cognome.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!idMec.matches("^[A-Z0-9]+$")) {
                    JOptionPane.showMessageDialog(null, "L'ID Meccanico deve contenere solo lettere e numeri.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try{
                    controller.aggiungiMeccanico(idMec, nome, cognome);
                    idTextField.setText("");
                    nomeTextField.setText("");
                    cognomeTextField.setText("");
                    JOptionPane.showMessageDialog(null, "Meccanico aggiunto con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
                } catch (DatiMeccanicoNonValidiException eccezione){
                    JOptionPane.showMessageDialog(null, "Dati meccanico non valid", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                } catch (Exception eccezione) {
                    JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }
}
