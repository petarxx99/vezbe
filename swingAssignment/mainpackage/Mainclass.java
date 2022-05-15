package mainpackage;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalTime;


public class Mainclass extends JFrame {
    JFrame drugiFrejm;
    Timer timer;

    public static void main(String[] args) throws ParseException {
        int daLiDaPokrenemProgram = JOptionPane.showOptionDialog(null, "Izaberite opciju", "Option dialog", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Settings", "Cancel"}, null);
        if (daLiDaPokrenemProgram == 1) System.exit(0);
        
        Mainclass f = new Mainclass( 400, 200);

    }

    public Mainclass(int duzina, int visina) throws ParseException {
        this.setSize(duzina, visina);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BoxLayout layout = new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS);
        this.setLayout(layout);


        JPanel panelTop = new JPanel();
        panelTop.setPreferredSize(new Dimension(duzina, 2*visina/5));
        panelTop.setLayout(new GridLayout(2, 2));

        ButtonGroup onTimeOrCountdown = new ButtonGroup();

        JRadioButton onTime = new JRadioButton("on time");
        onTime.setActionCommand("on time:");
        onTimeOrCountdown.add(onTime);

        JRadioButton countdown = new JRadioButton("countdown (sec):");
        countdown.setActionCommand("countdown");
        countdown.setSelected(true);
        onTimeOrCountdown.add(countdown);

        JFormattedTextField formatiranoVreme = new JFormattedTextField(new MaskFormatter("##:##:##"));
        formatiranoVreme.setPreferredSize(new Dimension(duzina/2, 20));

        JTextField textFieldCountdown = new JTextField("5");

        panelTop.add(onTime);
        panelTop.add(formatiranoVreme);
        panelTop.add(countdown);
        panelTop.add(textFieldCountdown);





        JPanel panelIzaberiBoju = new JPanel();
        panelIzaberiBoju.setPreferredSize(new Dimension(duzina, visina/5));
        panelIzaberiBoju.setLayout(new FlowLayout());

        JLabel labelPrikaziBoju = new JLabel("Niste izabrali boju.");
        Thread threadBlinkanje = new Thread(() -> {
            try{
                while(true){
                    Thread.sleep(1000);
                    labelPrikaziBoju.setForeground(suprotnaBoja(labelPrikaziBoju.getForeground()));
                }
            } catch(InterruptedException exception){
                exception.printStackTrace();
            }
        });
        threadBlinkanje.start();

        JButton izaberiBojuButton = new JButton("Izaberite boju");
        izaberiBojuButton.addActionListener(event -> {
                    Color boja = JColorChooser.showDialog(null, "Izaberite boju", Color.RED);
                    if (boja == null) return;
                    String bojaString = boja.toString();
                    labelPrikaziBoju.setText(bojaString.substring("java.awt.Color".length()));
                    labelPrikaziBoju.setForeground(boja);
                });

        panelIzaberiBoju.add(izaberiBojuButton);
        panelIzaberiBoju.add(labelPrikaziBoju);





        JPanel panelBojaDrugogFrejma = new JPanel();
        panelBojaDrugogFrejma.setLayout(new FlowLayout());
        panelBojaDrugogFrejma.setPreferredSize(new Dimension(duzina, visina/5));
        
        JLabel labelBrzinaMenjanjaBoje = new JLabel("Na koliko sekundi drugi prozor menja boju: ");
        panelBojaDrugogFrejma.add(labelBrzinaMenjanjaBoje);

        JComboBox<Integer> jComboBox = new JComboBox<>();
        for(int i=1; i<=5; i++){
            jComboBox.addItem(i);
        }
        jComboBox.setSelectedIndex(0);
        panelBojaDrugogFrejma.add(jComboBox);



        JPanel panelStartStop = new JPanel();
        panelStartStop.setLayout(new FlowLayout());
        panelStartStop.setPreferredSize(new Dimension(duzina, visina/5));

        JButton pocniOdbrojavanjeButton = new JButton("Start countdown");
        pocniOdbrojavanjeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                if (labelPrikaziBoju.getText().equals("Niste izabrali boju.")){
                    JOptionPane.showMessageDialog(null, "Niste izabrali boju.");
                    return;
                }
                int[] rgb = rgbIzString(labelPrikaziBoju.getText());
                Color bojaFrejma = new Color(rgb[0], rgb[1], rgb[2]);

                int sekundeZaOdbrojavanje = -1;
                if (countdown.isSelected()){
                    try{
                        sekundeZaOdbrojavanje = Integer.parseInt(textFieldCountdown.getText());
                        if(sekundeZaOdbrojavanje < 0){
                            JOptionPane.showMessageDialog(null, "Ne mozete upisati negativan broj");
                            return;
                        }
                    } catch(Exception exception){
                        JOptionPane.showMessageDialog(null, "Greska prilikom parsiranja broja sekundi.");
                        return;
                    }
                } else {
                    int sekund, minut, sat = -1;
                    try {
                        sat = Integer.parseInt(formatiranoVreme.getText().split(":")[0]);
                        minut = Integer.parseInt(formatiranoVreme.getText().split(":")[1]);
                        sekund = Integer.parseInt(formatiranoVreme.getText().split(":")[2]);
                    } catch(Exception exc){
                        JOptionPane.showMessageDialog(null, "Niste uneli sate, minute i sekunde.");
                        return;
                    }

                    if (sat>59 || minut > 59 || sekund > 59){
                        JOptionPane.showMessageDialog(null, "Ne mozete uneti broj veci od 59 za sate, minute i sekunde.");
                        return;
                    }
                    LocalTime sadasnjiMomenat = LocalTime.now();
                    LocalTime momenatZaPokretanje = LocalTime.of(sat, minut, sekund);
                    Duration duration = Duration.between(sadasnjiMomenat, momenatZaPokretanje);
                    sekundeZaOdbrojavanje = (int) duration.getSeconds();
                }

                if (sekundeZaOdbrojavanje < 0){
                    JOptionPane.showMessageDialog(null, "Nesto nije radilo kako treba.");
                    return;
                }

                onTime.setEnabled(false);
                countdown.setEnabled(false);
                formatiranoVreme.setEnabled(false);
                textFieldCountdown.setEnabled(false);
                izaberiBojuButton.setEnabled(false);
                labelPrikaziBoju.setEnabled(false);
                jComboBox.setEnabled(false);
                pocniOdbrojavanjeButton.setEnabled(false);

                int naKolikoSekundiBlinka = (int) jComboBox.getSelectedItem();

                timer = new Timer(naKolikoSekundiBlinka * 1000, new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        if(drugiFrejm == null) {
                            drugiFrejm = new JFrame();
                            drugiFrejm.setSize(200, 200);
                            drugiFrejm.setLocationRelativeTo(null);
                        }

                        if (!drugiFrejm.isVisible()){
                            drugiFrejm.getContentPane().setBackground(bojaFrejma);
                            drugiFrejm.setVisible(true);
                        } else {
                            drugiFrejm.getContentPane().setBackground(suprotnaBoja(drugiFrejm.getContentPane().getBackground()));
                        }
                    }
                });
                timer.setInitialDelay(sekundeZaOdbrojavanje * 1000);
                timer.start();
            }
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                if(drugiFrejm != null) {
                    drugiFrejm.setVisible(false);
                }
                if(timer != null) timer.stop();

                onTime.setEnabled(true);
                countdown.setEnabled(true);
                formatiranoVreme.setEnabled(true);
                textFieldCountdown.setEnabled(true);
                izaberiBojuButton.setEnabled(true);
                labelPrikaziBoju.setEnabled(true);
                jComboBox.setEnabled(true);
                pocniOdbrojavanjeButton.setEnabled(true);
            }
        });

        panelStartStop.add(pocniOdbrojavanjeButton);
        panelStartStop.add(stopButton);

        this.getContentPane().add(panelTop);
        this.getContentPane().add(panelIzaberiBoju);
        this.getContentPane().add(panelBojaDrugogFrejma);
        this.getContentPane().add(panelStartStop);
        this.setVisible(true);
    }


    public static Color suprotnaBoja(Color boja){
        int[] rgb = rgbIzString(boja.toString());
        return new Color(255 - rgb[0], 255 - rgb[1], 255 - rgb[2]);
    }

    public static int[] rgbIzString(String colorToString){
        int indeksOtvaranjaZagrade = colorToString.indexOf('[');
        int indeksZatvaranjaZagrade = colorToString.toString().indexOf(']');
        String deoUnutarZagrade = colorToString.toString().substring(indeksOtvaranjaZagrade + 1, indeksZatvaranjaZagrade);
        int crvena = Integer.parseInt(deoUnutarZagrade.split(",")[0].split("=")[1]);
        int zelena = Integer.parseInt(deoUnutarZagrade.split(",")[1].split("=")[1]);
        int plava = Integer.parseInt(deoUnutarZagrade.split(",")[2].split("=")[1]);

        return new int[]{crvena, zelena, plava};
    }


}
