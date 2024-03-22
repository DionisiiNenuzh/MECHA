package GUI;

import Calculate.Vector2;
import Graphical.Constants;
import Graphical.GraphicsEngine;

public class TimeDisplay {

    // time displayed
    public double time = 0;
    // position of where to draw
    public Vector2 Position = new Vector2(Constants.SCREEN_OFFSET, 50);
    public boolean restarted = true;

    // update time with each frame
    public void update(double dt) {
        this.time += dt;
        if (this.restarted) {
            this.restarted = false;
        }
    }

    // draws a string with time up to 3 decimal places
    public void draw(GraphicsEngine ge) {
        ge.drawText(String.format("%.3f", this.time),
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

    public boolean getRestarted() {
        return this.restarted;
    }
}
