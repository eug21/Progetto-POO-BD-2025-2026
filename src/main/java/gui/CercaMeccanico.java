package gui;

import controller.Controller;
import exception.MeccanicoNonTrovatoException;
import model.Meccanico;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Cerca meccanico.
 * Schermata che consente di cercare un meccanico mediante codice identificativo e di modificarne lo stato.
 */
public class CercaMeccanico  extends JFrame{
    private JTextField idTextField;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JButton cercaButton;
    private JPanel cerca;
    private JButton aggiornaStatoButton;

    private Controller controller;

    /**
     * Instantiates a new Cerca meccanico.
     *
     * @param controllerHome the controller home
     */
    public CercaMeccanico(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Cerca un meccanico");
        setContentPane(cerca);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        nomeTextField.setEditable(false);
        cognomeTextField.setEditable(false);
        nomeTextField.setVisible(false);
        cognomeTextField.setVisible(false);
        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String idMec =  idTextField.getText().trim().toUpperCase();
                if(idMec.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!idMec.matches("^[A-Z0-9]+$")) {
                    JOptionPane.showMessageDialog(null, "L'ID Meccanico deve contenere solo lettere e numeri.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

               try{
                   Meccanico meccanico = controller.cercaMeccanico(idMec);
                   nomeTextField.setVisible(true);
                   cognomeTextField.setVisible(true);
                   nomeTextField.setText(meccanico.getNome());
                   cognomeTextField.setText(meccanico.getCognome());
                   JOptionPane.showMessageDialog(null ,"Meccanico trovato con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);

               } catch (MeccanicoNonTrovatoException eccezione){
                   JOptionPane.showMessageDialog(null, "Il meccanico non esiste", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                   nomeTextField.setVisible(false);
                   cognomeTextField.setVisible(false);
               } catch (Exception eccezione) {
                   JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);
                   nomeTextField.setVisible(false);
                   cognomeTextField.setVisible(false);
               }


            }

        });
        aggiornaStatoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Meccanico meccanico = null;
                String idMec =  idTextField.getText().trim().toUpperCase();
                if(idMec.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Compila i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!idMec.matches("^[A-Z0-9]+$")) {
                    JOptionPane.showMessageDialog(null, "L'ID Meccanico deve contenere solo lettere e numeri.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try{
                   meccanico = controller.cercaMeccanico(idMec);

                } catch(MeccanicoNonTrovatoException eccezione){
                    JOptionPane.showMessageDialog(null, "Il meccanico non esiste", eccezione.getMessage(), JOptionPane.ERROR_MESSAGE);
                    return;

                }
                Boolean stato = meccanico.isDisponibile();
                if(!stato){
                    JOptionPane.showMessageDialog(null, "Il meccanico non e' disponibile", "Errore", JOptionPane.INFORMATION_MESSAGE);
                }

                try{
                    controller.cambiaStatoMeccanico(meccanico.getIdMeccanico(), stato);
                    JOptionPane.showMessageDialog(null,"Stato cambiato con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);

                }catch (MeccanicoNonTrovatoException eccezione){
                    JOptionPane.showMessageDialog(null, "Il meccanico non e' disponibile", "Errore", JOptionPane.INFORMATION_MESSAGE);

                }catch (Exception eccezione) {
                    JOptionPane.showMessageDialog(null, "ERRORE", "Errore di sistema", JOptionPane.ERROR_MESSAGE);
    
                }

            }
        });
    }
}
