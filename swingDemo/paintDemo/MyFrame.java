package obnavljanje;
import javax.swing.*;
import java.awt.*;


public class MyFrame extends JFrame {

    public void init(int x, int y, int width, int height){
        this.setLocation(x, y);
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        this.setVisible(true);
    }

    public void drawCounting(MyContainer container, int toWhatNumber){
        container.whatToPaint = MyContainer.DEFAULT;
        try{
            for(int i=1; i<=toWhatNumber; i++){
                container.numToDraw = i;
                container.repaint();
                Thread.sleep(200);
            }
        } catch(InterruptedException exception){
            exception.printStackTrace();
        }
    }

    public void drawMovingRectangle(MyContainer container) {
        int side;
        side = (this.getWidth() < this.getHeight()) ? this.getWidth() / 10 : this.getHeight() / 10;
        int fWidth = this.getWidth();
        int fHeight = this.getHeight();

        container.rectangle1 = new MyRectangle((fWidth - side) / 2, (fWidth + side) / 2, (fHeight - side) / 2, (fHeight + side) / 2, fWidth, fHeight);
        container.rectColor = Color.BLUE;
        container.rectangle1.color = Color.BLUE;

        long frameLasts = 10;
        Thread thread = new Thread(() -> {
            try {
                while (true) {

                    Thread.sleep(frameLasts);
                    container.moveRect(container.rectangle1, frameLasts, 100);
                    container.whatToPaint = MyContainer.MOVING_RECTANGLE;
                    container.repaint();
                }
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        });

        thread.start();
    }

    public static void main(String[] args){
       /* MyContainer container = new MyContainer();
        MyFrame frame = new MyFrame();
        frame.add(container); // BITNO. BEZ OVOGA JE NEBITNO STA SAM NAPISAO U PAINT CONTAINER-A.
        frame.init(200, 400, 700, 300);
        frame.drawCounting(container, 18); */

        MyContainer container2 = new MyContainer();
        MyFrame frame2 = new MyFrame();
        frame2.add(container2);
        frame2.addKeyListener(container2);
        frame2.init(100, 50, 700, 600);
        frame2.drawMovingRectangle(container2);

    }
}
