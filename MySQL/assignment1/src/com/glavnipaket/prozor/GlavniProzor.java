package com.glavnipaket.prozor;
import com.glavnipaket.BazaPodataka;
import com.glavnipaket.Zaposleni;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private int maxDuzinaImena;
    private int maxDuzinaAdrese;
    private int[] prePosleZapeteDohodak;
    private String maxDohodak;
    private String ddlOgranicenjaTabeleZaposleni;

    public GlavniProzor(int duzina, int visina, BazaPodataka baza, String imeTabele, int maxDuzinaImena, int maxDuzinaAdrese, int[] prePosleZapeteDohodak){
        this.duzina = duzina;
        this.visina = visina;
        this.baza = baza;
        this.imeTabele = imeTabele;
        this.maxDuzinaImena = maxDuzinaImena;
        this.maxDuzinaAdrese = maxDuzinaAdrese;
        this.prePosleZapeteDohodak = prePosleZapeteDohodak;
        this.maxDohodak = "Za dohodak moze najvise da se upise " + prePosleZapeteDohodak[0] + " cifre pre zapete i " + prePosleZapeteDohodak[1] + " cifre posle zapete.";
        this.ddlOgranicenjaTabeleZaposleni = String.format("Ime moze najvise %s karaktera, adresa moze najvise %s karaktera, %s", maxDuzinaImena, maxDuzinaAdrese, maxDohodak);


        this.setSize(duzina, visina);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new GridLayout(2, 1));
        JPanel panelPrikaz = new JPanel();

        textArea = new JTextArea(10, 30);
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

        JButton dugmeObrisi = new JButton("Obrisite tekst");
        dugmeObrisi.addActionListener((ActionEvent event) -> {textArea.setText("");});
        panelIzbor.add(dugmeObrisi);

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
                f.dispose();
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
        JScrollPane scrollPaneObavestenja = napraviScrollPaneObavestenja();
        f.getContentPane().add(scrollPaneObavestenja, BorderLayout.PAGE_START);
        JPanel panelPodaci = new JPanel(new GridLayout(4, 2));
        
        JLabel labelaIme = new JLabel("ime i prezime: ");
        JTextField tfIme = new JTextField();
        
        JLabel labelaGodine = new JLabel("godine zaposlenog: ");
        JTextField tfGodine = new JTextField();

        
        JLabel labelaAdresa = new JLabel("adresa: ");
        JTextField tfAdresa = new JTextField();

        
        JLabel labelaVisinaDohotka = new JLabel("visina dohotka: ");
        JTextField tfVisinaDohotka = new JTextField();
        staviKomponenteNaPanel(panelPodaci, new JComponent[]{labelaIme, tfIme, labelaGodine, tfGodine, labelaAdresa, tfAdresa, labelaVisinaDohotka, tfVisinaDohotka});


        JButton dugme = new JButton("Unesi u bazu.");
        f.getContentPane().add(dugme, BorderLayout.PAGE_END);
        dugme.addActionListener((ActionEvent e1) -> {
             String ime = tfIme.getText();
            if(ime.length() > maxDuzinaImena){
                JOptionPane.showMessageDialog(null, "Ime moze biti najvise " + maxDuzinaImena + " slova");
                return;
            }

             String adresa = tfAdresa.getText();
            if(adresa.length() > maxDuzinaAdrese){
                JOptionPane.showMessageDialog(null, "Adresa moze biti najvise " + maxDuzinaAdrese + " slova");
                return;
            }


             int godine= preuzmiGodine(tfGodine);
             if(godine<0){
                 JOptionPane.showMessageDialog(null, "Nesto sa unosom godina nije proslo kako treba, jeste li uneli pozitivan broj?");
                 return;
             }
             BigDecimal visinaDohotka = preuzmiVisinuDohotka(tfVisinaDohotka);
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
        JScrollPane scrollPaneObavestenja = napraviScrollPaneObavestenja();
        f.getContentPane().add(scrollPaneObavestenja, BorderLayout.PAGE_START);

        JPanel panelPodaci = new JPanel(new GridLayout(5, 3));

        JLabel labelId = new JLabel("id cije podatke menjate: ");
        JTextField tfId = new JTextField(10);
        JLabel labelPrazan = new JLabel();
        staviKomponenteNaPanel(panelPodaci, new JComponent[]{labelId, tfId, labelPrazan});


        JLabel labelIme = new JLabel("ime i prezime: ");
        JTextField tfIme = new JTextField();
        JCheckBox checkBoxIme = new JCheckBox();
        staviKomponenteNaPanel(panelPodaci, new JComponent[]{labelIme, tfIme, checkBoxIme});


        JLabel labelGodine = new JLabel("godine: ");
        JTextField tfGodine = new JTextField();
        JCheckBox checkBoxGodine = new JCheckBox();
        staviKomponenteNaPanel(panelPodaci, new JComponent[]{labelGodine, tfGodine, checkBoxGodine});

        JLabel labelAdresa = new JLabel("adresa: ");
        JTextField tfAdresa = new JTextField();
        JCheckBox checkBoxAdresa = new JCheckBox();
        staviKomponenteNaPanel(panelPodaci, new JComponent[]{labelAdresa, tfAdresa, checkBoxAdresa});

        JLabel labelVisinaDohotka = new JLabel("visina dohotka");
        JTextField tfVisinaDohotka = new JTextField();
        JCheckBox checkBoxVisinaDohotka = new JCheckBox();
        staviKomponenteNaPanel(panelPodaci, new JComponent[]{labelVisinaDohotka, tfVisinaDohotka, checkBoxVisinaDohotka});

        f.getContentPane().add(panelPodaci, BorderLayout.CENTER);

        JButton dugmeIzmeni = new JButton("Izmeni.");
        dugmeIzmeni.addActionListener((ActionEvent e1) -> {
            int id=-1;
            try{
                id = Integer.parseInt(tfId.getText());
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Niste upisali dobar id.");
                return;
            }

            String ime=null, godine=null, adresa=null, visinaDohotka=null;
            if (checkBoxIme.isSelected()){
                ime = tfIme.getText();
                if(ime.length() > maxDuzinaImena){
                    JOptionPane.showMessageDialog(null, "Ime moze biti najduze " + maxDuzinaImena + " karaktera.");
                    return;
                }
            }

            if(checkBoxAdresa.isSelected()){
                adresa = tfAdresa.getText();
                if(adresa.length() > maxDuzinaAdrese){
                    JOptionPane.showMessageDialog(null, "Adresa moze biti najduze " + maxDuzinaAdrese + " karaktera.");
                    return;
                }
            }

            if(checkBoxGodine.isSelected()){
                godine = tfGodine.getText();
            }

            if(checkBoxVisinaDohotka.isSelected()){
                visinaDohotka = tfVisinaDohotka.getText();
            }

            int brojPromenjenihRedova = baza.update(id, ime, godine, adresa, visinaDohotka, imeTabele);
            if(brojPromenjenihRedova>0) {
                this.setEnabled(true);
                f.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Neuspeo upit.");
            }
        });
        f.getContentPane().add(dugmeIzmeni, BorderLayout.PAGE_END);
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
                return;
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

    public void ispisiZaposlene(ArrayList<Zaposleni> zaposleni){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<zaposleni.size(); i++){
            sb.append(zaposleni.get(i));
            sb.append("\n");
        }

        textArea.setText(sb.toString());
    }


    public void pretraziZaposlene(ActionEvent event){
        JFrame f = napraviSporedniProzor(duzina, visina);
        JLabel label = new JLabel("Stiklirajte polja po poljima vrsite pretragu.");
        f.getContentPane().add(label, BorderLayout.PAGE_START);


        JPanel panelPodaci = new JPanel(new GridLayout(4, 1));
        JPanel panelIme = new JPanel(new FlowLayout());
        
        JLabel labelIme = new JLabel("ime: ");
        JTextField tfIme = new JTextField(10);
        JCheckBox checkBoxIme = new JCheckBox();
        staviKomponenteNaPanel(panelIme, new JComponent[]{labelIme, tfIme, checkBoxIme});
        panelPodaci.add(panelIme);

        final int MIN = 0;
        final int MAX = 1;

        final int MAX_GODINE = 200;
        final int MIN_GODINE = 1;
        int[] minMaxGodine = new int[2];
        minMaxGodine[MIN] = MIN_GODINE;
        minMaxGodine[MAX] = MAX_GODINE;

        JPanel panelGodine = new JPanel(new FlowLayout());
        JLabel labelGodine = new JLabel("godine od: ");
        JSpinner spinnerMinGodine = napraviNumberSpinner(MIN_GODINE, MAX_GODINE, 1, MIN_GODINE);
        JLabel godineDo = new JLabel("godine do: ");
        JSpinner spinnerMaxGodine = napraviNumberSpinner(MIN_GODINE, MAX_GODINE, 1, MAX_GODINE);
        JCheckBox checkBoxGodine = new JCheckBox();

        staviKomponenteNaPanel(panelGodine, new JComponent[]{labelGodine, spinnerMinGodine, godineDo, spinnerMaxGodine, checkBoxGodine});
        panelPodaci.add(panelGodine);

        JPanel panelAdresa = new JPanel(new FlowLayout());
        JLabel labelAdresa = new JLabel("adresa: ");
        JTextField tfAdresa = new JTextField(10);
        JCheckBox checkBoxAdresa = new JCheckBox();
        staviKomponenteNaPanel(panelAdresa, new JComponent[]{labelAdresa, tfAdresa, checkBoxAdresa});
        panelPodaci.add(panelAdresa);

        int MIN_DOHODAK = 0;
        int MAX_DOHODAK = 1000000;
        int[] minMaxDohodak = new int[2];
        minMaxDohodak[MIN] = MIN_DOHODAK;
        minMaxDohodak[MAX] = MAX_DOHODAK;

        JPanel panelDohodak = new JPanel(new FlowLayout());
        JLabel labelVisinaDohotkaOd = new JLabel("dohodak od: ");
        JSpinner spinnerMinDohodak = napraviNumberSpinner(MIN_DOHODAK, MAX_DOHODAK, 10000, MIN_DOHODAK);
        JLabel labelVisinaDohotkaDo = new JLabel("do: ");
        JSpinner spinnerMaxDohodak = napraviNumberSpinner(MIN_DOHODAK, MAX_DOHODAK, 10000, 100000);
        JCheckBox checkBoxDohodak = new JCheckBox();

        staviKomponenteNaPanel(panelDohodak, new JComponent[]{labelVisinaDohotkaOd, spinnerMinDohodak, labelVisinaDohotkaDo, spinnerMaxDohodak, checkBoxDohodak});
        panelPodaci.add(panelDohodak);

        f.getContentPane().add(panelPodaci, BorderLayout.CENTER);

        GlavniProzor thisProzor = this;
        JButton dugme = new JButton("Pretrazite");
        f.getContentPane().add(dugme, BorderLayout.PAGE_END);
        dugme.addActionListener((ActionEvent e) -> {
            String ime = null;
            if(checkBoxIme.isSelected()){
                ime = String.format("(ime='%s')", tfIme.getText());
            }

            String godine = null;
            if(checkBoxGodine.isSelected()) {
                int godineMin = (int) spinnerMinGodine.getValue();
                int godineMax = (int) spinnerMaxGodine.getValue();
                godine = String.format("(godine < %d) AND (godine > %d)", godineMax, godineMin);
            }

            String adresa = null;
            if(checkBoxAdresa.isSelected()){
                adresa = String.format("(adresa='%s')", tfAdresa.getText());
            }

            String dohodak = null;
            if(checkBoxDohodak.isSelected()) {
                int minDohodak = (int) spinnerMinDohodak.getValue();
                int maxDohodak = (int) spinnerMaxDohodak.getValue();
                dohodak = String.format("(visina_dohotka < %d) AND (visina_dohotka > %d)", maxDohodak, minDohodak);
            }

            String[] uslovi = new String[]{ime, godine, adresa, dohodak};
            String uslov = napraviUslovOdStringovaKojiNisuNull(uslovi);
            ArrayList<Zaposleni> zaposleni = baza.prikaziZaposlenePoUslovu(imeTabele, uslov);
            if(zaposleni != null){
                ispisiZaposlene(zaposleni);
                thisProzor.setEnabled(true);
                f.dispose();
            }
        });
        f.setVisible(true);
    }



    public void staviKomponenteNaPanel(JPanel kontejner, JComponent[] komponente){
        for(JComponent c : komponente){
            kontejner.add(c);
        }
    }

    public String napraviUslovOdStringovaKojiNisuNull(String[] uslovi){
        StringBuilder sb = new StringBuilder();
        sb.append("(1=1)");
        for(int i=0; i<uslovi.length; i++){
            if(uslovi[i] != null){
                sb.append(" AND ");
                sb.append(uslovi[i]);
            }
        }

        return sb.toString();
    }

    public JSpinner napraviNumberSpinner(int min, int max, int stepSize, int pocetnaVrednost){
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel();
        spinnerNumberModel.setMinimum(min);
        spinnerNumberModel.setMaximum(max);
        spinnerNumberModel.setStepSize(stepSize);
        if(pocetnaVrednost>max){
            spinnerNumberModel.setValue(max);
        } else if (pocetnaVrednost < min){
            spinnerNumberModel.setValue(min);
        } else {
            spinnerNumberModel.setValue(pocetnaVrednost);
        }
        
        return new JSpinner(spinnerNumberModel);
    }


    public int preuzmiGodine(JTextField tfGodine){
        int godine=-1;
        try{
            godine = Integer.parseInt(tfGodine.getText());
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Niste uneli broj kako treba.");
            return -1;
        }
        if(godine<0){
            JOptionPane.showMessageDialog(null, "Nesto sa unosom godina nije proslo kako treba, jeste li uneli pozitivan broj?");
            return -1;
        }

        return godine;
    }

    public BigDecimal preuzmiVisinuDohotka(JTextField tfVisinaDohotka){
        BigDecimal visinaDohotka;
        try{
            double visinaDohotkaDouble = Double.parseDouble(tfVisinaDohotka.getText());
            visinaDohotka = new BigDecimal(visinaDohotkaDouble);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Niste uneli visinu dohotka kako treba.");
            return null;
        }
        if (visinaDohotka == null){
            JOptionPane.showMessageDialog(null, "Obrada visine dohotka nije prosla kako treba.");
            return null;
        }


        return visinaDohotka;
    }

    public JScrollPane napraviScrollPaneObavestenja(){
        JTextArea ta = new JTextArea(ddlOgranicenjaTabeleZaposleni);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ta);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }




}

