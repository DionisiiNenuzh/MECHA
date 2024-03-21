package GUI;

import Calculate.Force.CollisionDetector;
import Calculate.Vector2;
import Graphical.GraphicsEngine;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Panel {

    public final static int BUTTON_SPACING = 3;
    // attributes needed to add functionality of the queue
    public ArrayList<Button> buttons = new ArrayList<Button>();
    // position to draw and size to check
    // whether any button is clicked on the menu
    public Vector2 position, size;
    private boolean horizontal = false;
    //direction where next button will be drawn
    private final Vector2 buttonSpacing = new Vector2(0, 30);

    public Panel(Vector2 v) {
        this.position = new Vector2(v);
        this.size = new Vector2(0,0);
    }

    public void add(Button b) {
        // update button based on panel
        Vector2 newPos;
        if (horizontal) {
            newPos = new Vector2(this.position.getX() + this.size.getX() + BUTTON_SPACING, this.position.getY());
        } else {
            newPos = new Vector2(this.position.getX(), this.position.getY() + this.size.getY() + BUTTON_SPACING);
        }
        b.setPosition(newPos);

        // update panel for the button
        if (this.buttons.isEmpty()) {
            this.size = b.getSize();
        } else {
            // TODO make size vector immutable
            if (horizontal) {
                this.size.x += b.size.getX();
            } else {
                this.size.y += b.size.getY();
            }
        }
        // adding buttons to the list
        this.buttons.add(b);

        System.out.println("SIZE is " + size.toString());
    }
//
//    public void draw(Graphics2D g2) {
//        this.buttons.forEach(b -> b.draw(g2));
//    }

    public void draw(GraphicsEngine ge) {
        this.buttons.forEach(b -> b.draw(ge));
    }

    //checks whether the point is clicked on menu's area
    public void OnMenu(Vector2 point, boolean click, String ch) {
        boolean isOne = false;
        // checks whether it is the cur
        for (Button b : this.buttons) {
            if (b.response.getPanel() != null) {
                //System.out.println("panel "+this.buttons.get(i).name);
                if (b.response.getActive() && b.response.panel.getOnMenu(point)) {
                    b.response.panel.OnMenu(point, click, ch);
                    isOne = true;
                }
            }
        }

        if (getOnMenu(point)) {
            for (Button b : this.buttons) {
                b.CheckHighlight(point, click, ch);
                if (b.isHighlighted(point) && b.response.panel != null) {
                    b.response.panel.OnMenu(point, click, ch);
                }
            }
        } else if (!isOne) { // can be used to disable highlight
            this.offHighlight();
        }

    }

    public void offHighlight() {
        for (Button b : buttons) {
            b.highlighted = false;
        }
    }

    // TODO add this method to the collision detector class
    public boolean getOnMenu(Vector2 point) {
        //System.out.println(this.size.x+" "+this.size.y);
        return CollisionDetector.pointInRectangle(point, position, size);
    }

    public void setPosition(Vector2 v) {

        this.position = new Vector2(v);

        Vector2 currentPosition = new Vector2(v);
        for (Button b : buttons) {
            b.setPosition(currentPosition);
            if (horizontal) {
                currentPosition.add(new Vector2(b.getSize().getX() + BUTTON_SPACING, 0));
            } else {
                currentPosition.add(new Vector2(0, b.getSize().getY() + BUTTON_SPACING));
            }
        }

    }

    public void inputKey(String c) {
        // TODO implement keys pressing using buttons
    }

}
