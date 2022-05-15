package mainpackage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Mainclass extends JFrame {
    byte invisibleButton = 12;
    JButton[] buttons = new JButton[12];

    public static void main(String[] args){
        Mainclass main = new Mainclass(300, 200, 500, 500);

    }

    public Mainclass(int x, int y, int width, int height){
        this.setSize(width, height);
        this.setLocation(x, y);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent event){
                System.exit(0);
            }
        });
        GridLayout gridLayout = new GridLayout(3, 4);
        this.setLayout(gridLayout);

        for(int i=1; i<=12; i++){
            buttons[i-1] = new JButton(String.valueOf(i));
            this.add(buttons[i-1]);
            buttons[i-1].addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event){
                    JButton clickedButton = (JButton) event.getSource();
                    int numOfClickedButton = Integer.parseInt(clickedButton.getText());
                    int xClicked = numOfClickedButton % 4;
                    if (xClicked == 0) xClicked = 4;
                    int yClicked = (numOfClickedButton-1) / 4 + 1;

                    int xInvisible = invisibleButton % 4;
                    if (xInvisible == 0) xInvisible = 4;
                    int yInvisible = (invisibleButton-1) / 4 + 1;

                    if (Math.abs(xClicked - xInvisible) != 1 && Math.abs(yClicked - yInvisible) !=1) return;
                    if (Math.abs(xClicked - xInvisible) == 1 && Math.abs(yClicked - yInvisible) != 0 && Math.abs(yClicked - yInvisible) != 1) return;
                    if (Math.abs(yClicked - yInvisible) ==1 && Math.abs(xClicked - xInvisible) !=0 && Math.abs(xClicked - xInvisible) != 1) return;

                    clickedButton.setVisible(false);
                    buttons[invisibleButton - 1].setVisible(true);

                    invisibleButton = (byte) numOfClickedButton;
                }
            });
        }
        buttons[11].setVisible(false);

        this.setVisible(true);
    }
}


/*

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {

      private JPanel mainPanel;
      static JButton invisible;

      static void switchButtons(JButton clickedButton) {
            if(invisible.getLocation().x < (clickedButton.getLocation().x - clickedButton.getSize().width) ||
                        invisible.getLocation().x > (clickedButton.getLocation().x + clickedButton.getSize().width) ||
                        invisible.getLocation().y < (clickedButton.getLocation().y - clickedButton.getSize().height) ||
                        invisible.getLocation().y > (clickedButton.getLocation().y + clickedButton.getSize().height) ||
                        (invisible.getLocation().y != clickedButton.getLocation().y &&
                                    invisible.getLocation().x != clickedButton.getLocation().x)) {
                  return;
            }

            Point tmpLoc = clickedButton.getLocation();
            clickedButton.setLocation(invisible.getLocation());
            invisible.setLocation(tmpLoc);
      }

      public Main() {
            this.setContentPane(mainPanel);
      }

      public static void main(String[] args) {
            Main mainFrame = new Main();
            mainFrame.setVisible(true);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setSize(310, 300);

            LayoutManager layOut = new GridLayout(3, 3);
            mainFrame.setLayout(layOut);

            JButton btn = new JButton();
            for(int i = 1; i < 13; i++) {
                  btn = new JButton(String.valueOf(i));
                  btn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                              switchButtons((JButton) e.getSource());
                        }
                  });
                  mainFrame.add(btn);
            }
            btn.setVisible(false);
            invisible = btn;
   }

}
 */