package Graphical;

import Calculate.Vector2;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// class needed for tracking the position of the cursor
// this object implements MouseListener class which means
// it can use the same methods for and can be linked to the window
public class ML implements MouseListener {

    //boolean values to have a smooth click function
    public boolean pressed, released, clicked;

    @Override
    public void mouseClicked(MouseEvent e) {
        //this method usage in multiple responses during
        // the time it has been pressed
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.released = true;
        this.pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // this method gets coordinates of the mouse
    public Vector2 getLocation() {
        return new Vector2(MouseInfo.getPointerInfo().getLocation().x,
                MouseInfo.getPointerInfo().getLocation().y);
    }

    // this ensures that it is clicked once and not more until the mouse is pressed for another time
    public boolean getClick() {
        if (!this.pressed && this.released) {
            this.clicked = true;
            this.released = false;
        } else {
            this.clicked = false;
        }
        return this.clicked;
    }

}
