package Graphical;

import Calculate.Vector2;
import com.sun.source.tree.ConstantCaseLabelTree;
import java.awt.Color;

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

    public void draw(GraphicsEngine ge, Vector2 move) {
        // set up axes in the middle of the canvas
        // adding offload
        int Xval = -(int) move.getX();
        int Yval = -(int) move.getY();
        ge.setColor(Color.darkGray);
//        ge.setOffset(new Vector2(Constants.SCREEN_OFFSET,0));

        ge.drawLine(new Vector2(this.minX + Xval + Constants.SCREEN_OFFSET, Yval),
            new Vector2(this.maxX + Xval + Constants.SCREEN_OFFSET, Yval));
        ge.drawLine(new Vector2(Xval + Constants.SCREEN_OFFSET, this.minY + Yval),
            new Vector2(Xval + Constants.SCREEN_OFFSET, this.maxY + Yval));
        // dashed lines and numbers
        // x axis
        int posX = this.minX + Constants.SCREEN_OFFSET;
        for (int i = 0; i < Math.abs((this.maxX - this.minX) / this.step); i++) {
            ge.drawText(String.valueOf(posX - Constants.SCREEN_OFFSET),
                new Vector2(posX + 10 + Xval, Yval + 15));
            ge.drawLine(
                new Vector2(posX + Xval, -10 + Yval),
                new Vector2(posX + Xval, 10 + Yval));
            posX += this.step;
        }
        // y axis
        int posY = this.minY;
        for (int i = 0; i < Math.abs((this.maxX - this.minX) / this.step); i++) {
            ge.drawText(String.valueOf(posY),
                new Vector2(Xval + 10 + Constants.SCREEN_OFFSET,posY + Yval - 5));
            ge.drawLine(
                new Vector2(-10 + Xval + Constants.SCREEN_OFFSET, posY + Yval),
                new Vector2(10 + Xval + Constants.SCREEN_OFFSET, posY + Yval));
            posY += this.step;
        }
//        ge.setOffset(new Vector2(0, 0));
    }
}
