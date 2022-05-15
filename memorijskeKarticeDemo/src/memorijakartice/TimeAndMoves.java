package memorijakartice;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.time.*;
import java.time.format.*;

public class TimeAndMoves implements Runnable {
    public Kartice kartica;
    public ArrayList<String> moves = new ArrayList<>();

    public LocalDateTime ldt = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
    public LocalDateTime ldtBegin = LocalDateTime.of(2000, 1, 1, 1, 1, 1);

    public Instant instant = Instant.EPOCH;
    public Instant beginInstant = Instant.EPOCH;

    public boolean gameIsOn = true;
    int moveCount = 0;
    int seconds;


    @Override
    public void run(){
        beginInstant = Instant.now();
        instant = beginInstant;

        try{
            while(gameIsOn){
                instant = Instant.now();
                seconds = (int) (instant.toEpochMilli() - beginInstant.toEpochMilli())/1000;
                kartica.labelTime.setText(seconds / 3600 + ":" + seconds / 60 + ":" + seconds);

                Thread.sleep(120);
            }
        }catch(InterruptedException exception){
            exception.printStackTrace();
        }
    }

    public void newMove(int firstIndex, int secondIndex){
        moves.add(firstIndex + "-" + secondIndex);
        moveCount++;
        kartica.labelNumOfMoves.setText("moves = " + moveCount);

    }

    public void init(Kartice kartica){
        this.kartica = kartica;
        moveCount = 0;
        moves = new ArrayList<String>();
        seconds = 0;

        kartica.labelTime.setText("0:0:0");
        kartica.labelNumOfMoves.setText("moves = 0");

        beginInstant = Instant.now();
        instant = beginInstant;


    }

    public void victory(){
        instant = Instant.now();
        seconds = (int) (instant.toEpochMilli() - beginInstant.toEpochMilli())/1000;
        kartica.labelTime.setText(seconds / 3600 + ":" + seconds / 60 + seconds);

        gameIsOn = false;

    }
}
