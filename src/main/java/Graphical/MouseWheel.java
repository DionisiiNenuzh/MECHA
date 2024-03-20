package Graphical;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class MouseWheel implements MouseWheelListener {

    // scroll amount by which to shift the object display
    private int Rotation = 0;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //adds points to increase or decrease scroll amount
        this.Rotation += e.getWheelRotation() * e.getScrollAmount();
        if (this.Rotation > 0) {
            this.Rotation = 0;
        } else if (this.Rotation < -100) {
            this.Rotation = -100;
        }
    }

    public int getRotation() {
        return this.Rotation;
    }
}
