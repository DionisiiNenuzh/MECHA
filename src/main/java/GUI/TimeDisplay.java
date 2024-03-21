package GUI;

import Calculate.Vector2;
import Graphical.Constants;
import Graphical.GraphicsEngine;
import java.awt.Graphics2D;

public class TimeDisplay {

    // time displayed
    public double time = 0;
    // position of where to draw
    public Vector2 Position = new Vector2(Constants.SCREEN_OFFSET, 50);
    public boolean restarted = true;

    public TimeDisplay() {

    }

    // update time with each frame
    public void update(double dt) {
        this.time += dt;
        if (this.restarted) {
            this.restarted = false;
        }
    }

    // draws a string with time up to 3 decimal places
    public void draw(GraphicsEngine ge) {
        ge.drawText(this.convert(this.time),
            new Vector2(this.Position.getX(), (int) this.Position.getY() + 15));
    }

    //restarts it
    public void restart() {
        this.time = 0;
        this.restarted = true;
    }

    public double getTime() {
        return this.time;
    }


    public String convert(double num) {
        int factor = 1;
        for (int i = 0; i < 3; i++) {
            factor = factor * 10;
        }
        double val = Math.round(num * factor);
        val = val / factor;
        return String.valueOf(val);
    }

    public boolean getRestarted() {
        return this.restarted;
    }
}
