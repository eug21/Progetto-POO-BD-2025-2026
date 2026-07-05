package gui;

import controller.Controller;
import exception.ResponsabileNonTrovatoException;
import model.Responsabile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Cerca res.
 * Schermata che consente di cercare un responsabile mediante il suo codice identificativo univoco e di eventualmente
 * modificarne la mail.
 */
public class CercaRes extends JFrame {
    private JPanel cercaRes;
    private JTextField mailTextField;
    private JTextField idTextField;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JButton cercaButton;
    private JButton modificaEmailButton;
    private JTextField filialeTextField;

    private Controller controller;

    /**
     * Instantiates a new Cerca res.
     *
     * @param controllerHome the controller home
     */
    public CercaRes(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Cerca un responsabile");
        setContentPane(cercaRes);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        nomeTextField.setVisible(false);
        cognomeTextField.setVisible(false);
        mailTextField.setVisible(false);
        filialeTextField.setVisible(false);
        nomeTextField.setEditable(false);
        cognomeTextField.setEditable(false);
        filialeTextField.setEditable(false);
        mailTextField.setEditable(true);


        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idRes = idTextField.getText().trim().toUpperCase();
                if(idRes.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!idRes.matches("^[A-Z0-9]+$")) {
                    JOptionPane.showMessageDialog(null, "L'ID Responsabile deve contenere solo lettere e numeri.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try{
                    Responsabile responsabile = controller.cercaResponsabile(idRes);
                    nomeTextField.setText(responsabile.getNome());
                    cognomeTextField.setText(responsabile.getCognome());
                    mailTextField.setText(responsabile.getMail());
                    filialeTextField.setText(controller.filialeResponsabile(idRes));
                    nomeTextField.setVisible(true);
                    cognomeTextField.setVisible(true);
                    mailTextField.setVisible(true);
                    filialeTextField.setVisible(true);


                } catch (ResponsabileNonTrovatoException eccezione){
                    JOptionPane.showMessageDialog(null,"Il responsabile non esiste", "Errore", JOptionPane.ERROR_MESSAGE);
                    idTextField.setText("");
                }catch (Exception eccezione) {
                    JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);

                }

            }
        });
        modificaEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idResponsabile = idTextField.getText().trim().toUpperCase();
                String nuovaEmail = mailTextField.getText().trim();
            if(idResponsabile.isEmpty() || nuovaEmail.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            if (!idResponsabile.matches("^[A-Z0-9]+$")) {
                    JOptionPane.showMessageDialog(null, "L'ID Responsabile deve contenere solo lettere e numeri.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            if (!nuovaEmail.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "Formato email non valido.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            try{
                controller.modificaEmail(idResponsabile, nuovaEmail);
                JOptionPane.showMessageDialog(null, "Email modificata con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
            } catch (ResponsabileNonTrovatoException exception){
                JOptionPane.showMessageDialog(null, "Impossibile trovare il responsabile", "Errore", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception eccezione) {
                JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);

            }

            }
        });
    }
}
