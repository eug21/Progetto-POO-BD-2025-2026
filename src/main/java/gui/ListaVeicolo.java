package gui;

import controller.Controller;
import exception.VeicoloNonDisponibileException;
import exception.VeicoloNonTrovatoException;
import model.Veicolo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ListaVeicolo extends JFrame {
    private JPanel listaVeicolo;
    private JButton noleggiatiButton;
    private JButton tuttiButton;
    private JButton disponibiliButton;
    private JButton manutenzioneButton;
    private JTable tabellaVeicoli;
    private JButton eliminaSelezionatoButton;

    private Controller controller; 

    public ListaVeicolo(Controller controllerHome){
        this.controller = controllerHome;
        setTitle("Lista veicoli");
        setContentPane(listaVeicolo);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        String[] colonne = {"Targa", "Marca", "Modello", "Tariffa", "Stato"};

        tuttiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                List < Veicolo> tuttiVeicoli = new ArrayList<>();
                List <Veicolo> disponibili = ListaVeicolo.this.controller.getVeicoliDisponibili();
                if(disponibili != null){
                    tuttiVeicoli.addAll(disponibili);
                }
                List <Veicolo> noleggiati = ListaVeicolo.this.controller.getVeicoliNoleggiati();
                if(noleggiati != null) {
                    tuttiVeicoli.addAll(noleggiati);
                }
                List <Veicolo> manutenzione = ListaVeicolo.this.controller.getVeicoliManutenzione();
                if(manutenzione != null){
                    tuttiVeicoli.addAll(manutenzione);
                }
                riempiTabella(tuttiVeicoli, colonne);


            }
        });
        disponibiliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List <Veicolo> lista = ListaVeicolo.this.controller.getVeicoliDisponibili();
                riempiTabella(lista, colonne);
            }
        });
        manutenzioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List <Veicolo> lista = ListaVeicolo.this.controller.getVeicoliManutenzione();
                riempiTabella(lista, colonne);
            }
        });
        noleggiatiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List <Veicolo> lista = ListaVeicolo.this.controller.getVeicoliNoleggiati();
                riempiTabella(lista, colonne);
            }
        });

    }
    private void riempiTabella (List <Veicolo> lista, String [] colonne){
        DefaultTableModel modello = new DefaultTableModel(null, colonne);
        if(lista != null){
            for (Veicolo v : lista){
                modello.addRow(new Object[] {
                        v.getTarga(),
                        v.getMarca(),
                        v.getModello(),
                        v.getTariffaDie(),
                        v.getStatoVeicolo().toString()
                });
            }
        }
        tabellaVeicoli.setModel(modello);
    }
}
