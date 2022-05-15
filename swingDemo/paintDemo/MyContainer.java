package obnavljanje;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import javax.swing.*;

public class MyContainer extends Component implements KeyListener {
    MyRectangle rectangle1;
    Color rectColor;
    int frameWidth, frameHeight, accUnit, aUnitOfSpeedIsHowManyPixelsPerSecond, frameSleep;
    int numToDraw = 0;
    byte whatToPaint = 0;
    public static byte DEFAULT = 0, MOVING_RECTANGLE = 1, MOVE_LEFT = -1, MOVE_RIGHT = 1, NOT_LEFT_NOT_RIGHT = 0;
    public static byte MOVE_UP = 1, MOVE_DOWN = -1;
    public byte upOrDown = 0, leftOrRight = 0, brakes = 0;
    StringBuilder stringBuilder = new StringBuilder("");

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        switch (whatToPaint){
            case 0: {
                paint0(g2);
                break;
            }
            case 1: {
                paint1(g2);
                break;
            }
            default: break;
        }
    }

    public void paint0(Graphics2D g2){
        AffineTransform saveTransform = g2.getTransform();
        g2.translate(100, 200);
        g2.rotate(Math.PI / 4);
        g2.scale(2, 2);
        Font font = new Font("Arial", Font.PLAIN, 18);
        TextLayout tl = new TextLayout("Ovo je tekst.", font, g2.getFontRenderContext());
        tl.draw(g2, 0, 0);

        g2.setTransform(saveTransform);
        tl = new TextLayout("Ovo je drugi tekst", font, g2.getFontRenderContext());
        tl.draw(g2, 0, 50);

        g2.setColor(Color.RED);
        g2.fillRect(0, 100, 50, 30);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image1 = toolkit.getImage("slika1.png");
        g2.drawImage(image1, 0, 250, 50, 50, this);

        tl = new TextLayout(String.valueOf(numToDraw), font, g2.getFontRenderContext());
        tl.draw(g2, 50, 320);
    }

    public void paint1(Graphics2D g2){

        Color saveColor = g2.getColor();
        g2.setColor(rectangle1.color);
        g2.fillRect(rectangle1.xStart, rectangle1.yStart, rectangle1.width, rectangle1.height);

    }


    @Override
    public void keyTyped(KeyEvent event) {
        Character c = event.getKeyChar();
        if (c == 'c'){
            stringBuilder = new StringBuilder("");
            return;
        }

        if(stringBuilder.length() > 20){
            stringBuilder = new StringBuilder("");
        }

        stringBuilder.append(c);
    }

    @Override
    public void keyPressed(KeyEvent event){
        Character c = event.getKeyChar();
        switch (c) {
            case 'w': {
                if (upOrDown == MOVE_DOWN) {
                    upOrDown = 0;
                } else {
                    upOrDown = MOVE_UP;
                }
                break;
            }
            case 'd': {
                if(leftOrRight == MOVE_LEFT){
                    leftOrRight = NOT_LEFT_NOT_RIGHT;
                } else {
                    leftOrRight = MOVE_RIGHT;
                }
                break;
            }
            case 'a': {
                if (leftOrRight == MOVE_RIGHT){
                    leftOrRight = NOT_LEFT_NOT_RIGHT;
                } else {
                    leftOrRight = MOVE_LEFT;
                }
                break;
            }
            case 's': {
                if (upOrDown == MOVE_UP){
                    upOrDown = 0;
                } else {
                    upOrDown = MOVE_DOWN;
                }
                break;
            }
            case 'f': {
                brakes = 1;
                break;
            }
            default: break;
        }
        System.out.println("Pressed: " + c);
    }

    @Override
    public void keyReleased(KeyEvent event){
        Character c = event.getKeyChar();
        switch (c) {
            case 'w': {
                upOrDown -= 1;
                break;
            }
            case 'd': {
                leftOrRight -= 1;
                break;
            }
            case 'a': {
                leftOrRight += 1;
                break;
            }
            case 's': {
                upOrDown += 1;
                break;
            }
            case 'f': {
                brakes = 0;
                break;
            }
            default: break;
        }

        System.out.println("Released: " + c);
    }

    public void moveRect(MyRectangle r, long sleepMilli, int aUnitOfSpeedIsHowManyPixelsPerSecond){
        //  1s 0-200 na primer sleepMilli : x = 1000 : 200
        double a = (double)(aUnitOfSpeedIsHowManyPixelsPerSecond * sleepMilli) / 1000;
// a predstavlja koliko piksela se predje za 1 frejm, za 1 jedinicu brzine.
// Vx * a je stoga koliko treba piksela da se predje za 1 frejm za datu brzinu

        if(brakes == 1){
            if (r.Vx > 0){
                r.Vx -= 1;
            } else if (r.Vx < 0){
                r.Vx += 1;
            }

            if (r.Vy > 0){
                r.Vy -= 1;
            } else if (r.Vy < 0){
                r.Vy += 1;
            }

            r.newPosition(r.xStart + (int)(r.Vx * a), r.yStart + (int) (r.Vy * a));
            return;
        }
        // Naopacke su minus i plus za y zato sto je koordinatni sistem naopacke.
        r.Vy -= upOrDown;
        r.Vx += leftOrRight;
        r.newPosition(r.xStart + (int)(r.Vx * a), r.yStart + (int) (r.Vy * a));

    }



}
