package Graphical;

import Calculate.Vector2;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class CoordinateAxes {

    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;
    private final int step;

    public CoordinateAxes() {
        // max and min coordinates on the axes
        this.maxX = 2200;
        this.minX = -2000;
        this.maxY = 2000;
        this.minY = -2000;
        this.step = 100;
    }

    public void draw(Graphics2D g2, Vector2 move) {
        // set up axes in the middle of the canvas
        // adding offload
        int Xval = -(int) move.getX();
        int Yval = -(int) move.getY();
        g2.setColor(Color.darkGray);
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(this.minX + Xval + Constants.SCREEN_OFFSET, Yval,
                this.maxX + Xval + Constants.SCREEN_OFFSET, Yval);
        g2.drawLine(Xval + Constants.SCREEN_OFFSET, this.minY + Yval,
                Xval + Constants.SCREEN_OFFSET, this.maxY + Yval);
        // dashed lines and numbers
        // x axis
        int posX = this.minX + Constants.SCREEN_OFFSET;
        for (int i = 0; i < Math.abs((this.maxX - this.minX) / this.step); i++) {
            g2.drawString(String.valueOf(posX - Constants.SCREEN_OFFSET), posX + 10 + Xval,
                    Yval + 15);
            g2.drawLine(posX + Xval, -10 + Yval, posX + Xval, 10 + Yval);
            posX += this.step;
        }
        // y axis
        int posY = this.minY;
        for (int i = 0; i < Math.abs((this.maxX - this.minX) / this.step); i++) {
            g2.drawString(String.valueOf(posY), Xval + 10 + Constants.SCREEN_OFFSET,
                    posY + Yval - 5);
            g2.drawLine(-10 + Xval + Constants.SCREEN_OFFSET, posY + Yval,
                    10 + Xval + Constants.SCREEN_OFFSET, posY + Yval);
            posY += this.step;
        }
    }
}
