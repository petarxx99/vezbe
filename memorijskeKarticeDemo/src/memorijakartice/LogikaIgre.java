package memorijakartice;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LogikaIgre implements ActionListener {
    public Kartice kartica;

    public static final byte FIRST_CARD = 1, SECOND_CARD = 2, INVALID_NUMBER = -1;
    byte firstOrSecondCard = FIRST_CARD;

    public int clickedImage = INVALID_NUMBER;
    public int numOfPairs = INVALID_NUMBER;
    public int previousLabelIndex = INVALID_NUMBER;
    public boolean previousMethodIsOver = true;
    public boolean restartWasClicked = false;

    @Override
    public void actionPerformed(ActionEvent event){
        if(!previousMethodIsOver) {
            return;
        }
        previousMethodIsOver = false;
        restartWasClicked = false;

        JButton button = (JButton) event.getSource();
        int index = Integer.parseInt(button.getText()) - 1; // zato sto su dugmici od 1 do 24 umesto od 0 do 23.

        kartica.cardLayouts[index].show(kartica.parentPanels[index], "image");

        if(firstOrSecondCard == FIRST_CARD){
            clickedImage = kartica.labeleZaSlike[index].imageNumber;
            previousLabelIndex = index;
            firstOrSecondCard = SECOND_CARD;
            previousMethodIsOver = true;
            return;
        }

        kartica.timeAndMoves.newMove(previousLabelIndex, index);

// Kliknuo je na istu sliku.
        if(clickedImage == kartica.labeleZaSlike[index].imageNumber) {
            numOfPairs -= 1;
            if(numOfPairs <=0){
                kartica.timeAndMoves.victory();

            }
            firstOrSecondCard = FIRST_CARD;
            previousMethodIsOver = true;
            return;
        }

        Thread thread = new Thread(() -> {
            try{Thread.sleep(2000);}catch(InterruptedException exception){exception.printStackTrace();}
            if (restartWasClicked) return;
            kartica.cardLayouts[index].show(kartica.parentPanels[index], "button");
            kartica.cardLayouts[previousLabelIndex].show(kartica.parentPanels[previousLabelIndex], "button");

            previousMethodIsOver = true;
        });
        thread.start();

        firstOrSecondCard = FIRST_CARD;
    }


    public void init(Kartice kartica){
        this.kartica = kartica;
        this.numOfPairs = kartica.parentPanels.length / 2;
        firstOrSecondCard = FIRST_CARD;

        clickedImage = INVALID_NUMBER;
        numOfPairs = INVALID_NUMBER;
        previousLabelIndex = INVALID_NUMBER;
        previousMethodIsOver = true;
        restartWasClicked = true;

    }

    public void gameIsCompleted(){

    }
}
