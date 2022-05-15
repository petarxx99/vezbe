package memorijakartice;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.*;

public class Kartice extends JFrame {
    LogikaIgre logikaIgre = new LogikaIgre();
    TimeAndMoves timeAndMoves = new TimeAndMoves();
    Thread thread = new Thread();

    MyImageIcon[] slike = new MyImageIcon[12];
    JPanel[] parentPanels = new JPanel[24];
    JPanel[] imagePanels = new JPanel[24];
    JPanel[] buttonPanels = new JPanel[24];
    LabelSlike[] labeleZaSlike = new LabelSlike[24];
    CardLayout[] cardLayouts = new CardLayout[24];

    JLabel labelNumOfMoves = new JLabel("moves = 0");
    JLabel labelTime = new JLabel();

    // Sve se krece od 0 do 23, osim brojeva koji pisu na dugmicima, oni su od 1 do 24.

    public void popuniSlike(){

        File file = new File("/home/bokalovic/Documents/java programi/kurs4/modul2/swingSaInteliJ/");
        System.out.println(file.getName());
        File[] listOfFiles = file.listFiles();

        int counter = 0;
        for(File f : listOfFiles){
            String absolutePath = f.getAbsolutePath();
            int indexForSubstring = absolutePath.lastIndexOf('/') + 1;
            String path = absolutePath.substring(indexForSubstring);

            if (path.indexOf('.') == -1) continue;
            if (path.split("[.]")[1].equals("png")){
                String name = f.getName();
                slike[counter] = new MyImageIcon(path);
                slike[counter].name = name;
                slike[counter].number = counter;
                slike[counter].absolutePath = absolutePath;
                counter++;
                //System.out.println(counter + ": " + name + ", path = " + f.getPath());
            }
        }
    }

    public void addToCenterPanel(JPanel jPanel){

        final byte ADDED = 1, NOT_ADDED = 0;
        byte[] isItAdded = new byte[24]; // Instanciraju se na nulu.

        for(int i=0; i<12; i++){
            for(int j=0; j<2; j++) {

                int random = (int) (Math.random() * 24);

                while (isItAdded[random] == ADDED) {
                    random = (random + 1) % 24;
                }

                labeleZaSlike[random] = new LabelSlike();
                labeleZaSlike[random].imageNumber = i;
                labeleZaSlike[random].setIcon(slike[i]);

                imagePanels[random] = new JPanel();
                imagePanels[random].add(labeleZaSlike[random]);

                cardLayouts[random] = new CardLayout();
                parentPanels[random] = new JPanel();
                parentPanels[random].setLayout(cardLayouts[random]);
               //System.out.println("parent panels["+random+"] layout = " + parentPanels[random].getLayout().toString());
                parentPanels[random].add("image", imagePanels[random]);

                JButton button = new JButton(String.valueOf(random+1));
                button.addActionListener(logikaIgre);

                buttonPanels[random] = new JPanel();
                buttonPanels[random].add(button);
                parentPanels[random].add("button", buttonPanels[random]);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = random % 6;
                gbc.gridy = random / 6;

                cardLayouts[random].show(parentPanels[random], "button");
                jPanel.add(parentPanels[random], gbc);
                isItAdded[random] = ADDED;
            }
        }

        //UnitTests.testCardLayoutPanels(labeleZaSlike, imagePanels, buttonPanels, cardLayouts, buttonPanels);
    }

    public void victory(){

    }

    public static void main(String[] args){
        Kartice kartice = new Kartice(100, 50, 500, 500);

    }

    public Kartice(int x, int y, int width, int height){
        this.setSize(width, height);
        this.setLocation(x, y);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        logikaIgre.init(this);
        popuniSlike();
        //UnitTests.testSlike(slike, 4, 3);

        GridBagLayout jPanelCenterLayout = new GridBagLayout();
        JPanel jPanelCenter = new JPanel(jPanelCenterLayout);
        addToCenterPanel(jPanelCenter);

        JPanel jPanelTop = new JPanel(new FlowLayout());

        JButton exitButton = new JButton("exit");
        jPanelTop.add(exitButton);
        JButton restartButton = new JButton("restart");
        jPanelTop.add(restartButton);

        exitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                System.exit(0);
            }
        });

        Kartice thisKartice = this;
        restartButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                for(int i=0; i<24; i++){
                    jPanelCenter.remove(parentPanels[i]);
                    parentPanels[i].remove(imagePanels[i]);
                    parentPanels[i].remove(buttonPanels[i]);

                    imagePanels[i].remove(labeleZaSlike[i]);

                }
                logikaIgre.init(thisKartice);
                addToCenterPanel(jPanelCenter);
                thisKartice.validate();

                timeAndMoves.init(thisKartice);

            }
        });

        JPanel jPanelTime = new JPanel(new BorderLayout());
        labelNumOfMoves = new JLabel("moves = 0");
        labelTime = new JLabel();

        jPanelTime.add(labelTime, BorderLayout.LINE_START);
        jPanelTime.add(labelNumOfMoves, BorderLayout.LINE_END);

        timeAndMoves.init(this);
        thread = new Thread(timeAndMoves);
        thread.start();

        this.add(jPanelTop, BorderLayout.PAGE_START);
        this.add(jPanelCenter, BorderLayout.CENTER);
        this.add(jPanelTime, BorderLayout.PAGE_END);
        this.setVisible(true);
    }
}
