package obnavljanje;
import java.awt.Color;


public class MyRectangle {
    public int xStart, xEnd, yStart, yEnd;
    public int width, frameWidth;
    public int height, frameHeight;
    public Color color;
    public short Vx = 0, Vy = 0;


    public MyRectangle(int xStart, int xEnd, int yStart, int yEnd, int frameWidth, int frameHeight){
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
        this.xEnd = xEnd;
        this.xStart = xStart;
        this.yStart = yStart;
        this.yEnd = yEnd;
        this.width = xEnd - xStart;
        this.height = yEnd - yStart;

    }

    public void newPosition(int xStart, int yStart){
        if(xStart > 0  &&   xStart < frameWidth - width) {
            this.xStart = xStart;
            this.xEnd = xStart + width;
        } else {
            this.Vx = (short) - this.Vx;
        }
        if (yStart > 0 && yStart < frameHeight - height) {
            this.yStart = yStart;
            this.yEnd = yStart + height;
        } else {
            this.Vy = (short) - this.Vy;
        }

    }

    public String showInfo(){
        return "x = " + this.xStart + ", y = " + this.yStart + ", side = " + this.width + ", color = " + this.color;
    }
}
