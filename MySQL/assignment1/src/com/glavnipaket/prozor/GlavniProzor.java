package com.glavnipaket.prozor;
import com.glavnipaket.BazaPodataka;
import com.glavnipaket.Zaposleni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.ArrayList;


public class GlavniProzor extends JFrame {
    int duzina, visina;
    private JTextArea textArea;
    private BazaPodataka baza;
    private String imeTabele;

    public GlavniProzor(int duzina, int visina, BazaPodataka baza, String imeTabele){
        this.duzina = duzina;
        this.visina = visina;
        this.baza = baza;
        this.imeTabele = imeTabele;

        this.setSize(duzina, visina);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new GridLayout(2, 1));
        JPanel panelPrikaz = new JPanel();

        textArea = new JTextArea(10, 50);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelPrikaz.add(scrollPane, BorderLayout.CENTER);
        this.getContentPane().add(panelPrikaz);


        JPanel panelIzbor = new JPanel(new GridLayout(6, 1));
        JButton dugmeUnosNovog = new JButton("Unesite novog zaposlenog");
        dugmeUnosNovog.addActionListener(this::unesiZaposlenog);
        panelIzbor.add(dugmeUnosNovog);

        JButton dugmeIzmenaNaOsnovuId = new JButton("Izmenite podatke na osnovu id-a");
        dugmeIzmenaNaOsnovuId.addActionListener(this::izmeniNaOsnovuId);
        panelIzbor.add(dugmeIzmenaNaOsnovuId);

        JButton dugmeIzbrisiNaOsnovuId = new JButton("Izbrisite zaposlenog na osnovu id-a");
        dugmeIzbrisiNaOsnovuId.addActionListener(this::izbrisiNaOsnovuId);
        panelIzbor.add(dugmeIzbrisiNaOsnovuId);

        JButton dugmePrikazSvih = new JButton("Podaci o svim zaposlenima");
        dugmePrikazSvih.addActionListener(this::prikaziSveZaposlene);
        panelIzbor.add(dugmePrikazSvih);

        JButton dugmePretraga = new JButton("Pretraga zaposlenih");
        dugmePretraga.addActionListener(this::pretraziZaposlene);
        panelIzbor.add(dugmePretraga);

        JButton dugmeObrisi = new JButton("Obrisite tekst.");
        dugmePretraga.addActionListener((ActionEvent event) -> {textArea.setText("");});
        this.getContentPane().add(panelIzbor);
        this.setVisible(true);
    }

    public JFrame napraviSporedniProzor(int duzina, int visina){
        this.setEnabled(false);
        JFrame f = napraviProzor(duzina, visina);
        GlavniProzor thisProzor = this;
        f.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e1){
                thisProzor.setEnabled(true);
            }
        });

        return f;
    }

    public JFrame napraviProzor(int duzina, int visina){
        JFrame f = new JFrame();
        f.setSize(duzina, visina);
        f.setLocationRelativeTo(null);

        return f;
    }



    public void unesiZaposlenog(ActionEvent event){
        JFrame f = napraviSporedniProzor(duzina, visina);
        JPanel panelPodaci = new JPanel(new GridLayout(4, 2));
        
        JLabel labelaIme = new JLabel("ime i prezime: ");
        panelPodaci.add(labelaIme);
        JTextField tfIme = new JTextField();
        panelPodaci.add(tfIme);
        
        JLabel labelaGodine = new JLabel("godine zaposlenog: ");
        panelPodaci.add(labelaGodine);
        JTextField tfGodine = new JTextField();
        panelPodaci.add(tfGodine);
        
        JLabel labelaAdresa = new JLabel("adresa: ");
        panelPodaci.add(labelaAdresa);
        JTextField tfAdresa = new JTextField();
        panelPodaci.add(tfAdresa);
        
        JLabel labelaVisinaDohotka = new JLabel("visina dohotka: ");
        panelPodaci.add(labelaVisinaDohotka);
        JTextField tfVisinaDohotka = new JTextField();
        panelPodaci.add(tfVisinaDohotka);


        JButton dugme = new JButton("Unesi u bazu.");
        f.getContentPane().add(dugme, BorderLayout.PAGE_END);
        dugme.addActionListener((ActionEvent e1) -> {
             String ime = tfIme.getText();
             if (ime.isEmpty() || ime.isBlank()){
                 JOptionPane.showMessageDialog(null, "Morate uneti ime zaposlenog.");
                 return;
             }

             String adresa = tfAdresa.getText();
             if(adresa.isEmpty() || adresa.isBlank()){
                 JOptionPane.showMessageDialog(null, "Morate uneti adresu.");
                 return;
             }

             int godine=-1;
             try{
                 godine = Integer.parseInt(tfGodine.getText());
             } catch(Exception e){
                 JOptionPane.showMessageDialog(null, "Niste uneli broj kako treba.");
                 return;
             }
             if(godine<0){
                 JOptionPane.showMessageDialog(null, "Nesto sa unosom godina nije proslo kako treba, jeste li uneli pozitivan broj?");
                 return;
             }

             BigDecimal visinaDohotka;
             try{
                 double visinaDohotkaDouble = Double.parseDouble(tfVisinaDohotka.getText());
                 visinaDohotka = new BigDecimal(visinaDohotkaDouble);
             } catch(Exception e){
                 JOptionPane.showMessageDialog(null, "Niste uneli visinu dohotka kako treba.");
                 return;
             }
             if (visinaDohotka == null){
                 JOptionPane.showMessageDialog(null, "Obrada visine dohotka nije prosla kako treba.");
                 return;
             }


             try{
                 Zaposleni zaposleni = new Zaposleni(ime, godine, adresa, visinaDohotka);
                 baza.ubaciteZaposlenogUBazu(zaposleni, imeTabele);
                 this.setEnabled(true);
                 f.dispose();
             } catch(Exception e){
                 e.printStackTrace();
                 JOptionPane.showMessageDialog(null, "Nesto nije radilo.");
             }

        });


        f.getContentPane().add(panelPodaci, BorderLayout.CENTER);
        f.setVisible(true);
    }






    public void izmeniNaOsnovuId(ActionEvent event){
        JFrame f = napraviSporedniProzor(duzina, visina);

        f.setVisible(true);
    }






    public void izbrisiNaOsnovuId(ActionEvent event){
        JFrame f = napraviSporedniProzor(duzina, visina);
        f.setLayout(new FlowLayout());

        JLabel labela = new JLabel("Unesite id koji zelite da izbrisete: ");
        JTextField tf = new JTextField(10);
        JButton dugme= new JButton("Izbrisi zaposlenog.");

        f.getContentPane().add(labela);
        f.getContentPane().add(tf);
        f.getContentPane().add(dugme);

        dugme.addActionListener((ActionEvent e1) -> {
            int id=-1;
            try{
                id = Integer.parseInt(tf.getText());
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Niste uneli ispravan broj.");
            }

            try{
                baza.izbrisiteZaposlenog(id, imeTabele);
                this.setEnabled(true);
                f.dispose();
            } catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Greska prilikom brisanja zaposlenog.");
            }
        });

        f.setVisible(true);
    }





    public void prikaziSveZaposlene(ActionEvent event){
        ispisiZaposlene(baza.prikaziSveZaposlene(imeTabele));
    }


    public void pretraziZaposlene(ActionEvent event){
        JFrame f = napraviSporedniProzor(duzina, visina);

        f.setVisible(true);
    }


    public void ispisiZaposlene(ArrayList<Zaposleni> zaposleni){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<zaposleni.size(); i++){
            sb.append(zaposleni.get(i));
            sb.append("\n");
        }

        textArea.setText(sb.toString());
    }

}


