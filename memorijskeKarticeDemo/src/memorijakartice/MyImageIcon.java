package memorijakartice;
import javax.swing.*;

public class MyImageIcon extends ImageIcon {
    public int number;
    public String name;
    public String absolutePath;

    public MyImageIcon(String URLslike){
        super(URLslike);
    }
}
