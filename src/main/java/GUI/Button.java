package GUI;

import Calculate.Force.CollisionDetector;
import Calculate.Vector2;
import Graphical.GraphicsEngine;
import Graphical.Rect;
import java.awt.Color;

// object which is located on the screen toolbar
public class Button {

    // it has position of where to draw and identifier
    public Vector2 position, size;
    public String name;
    private boolean activated;
    public boolean highlighted = false;
    // specific response which can be activated
    public ButtonResponse response;
    // has it input window which can be triggered if the button is pressed
    public MakeRectangle input;


    public Button(Vector2 position, Vector2 size, String name, ButtonResponse response) {
        this.position = position;
        this.name = name;
        this.size = size;
        this.response = response;
        this.input = null;
        this.activated = false;
    }

    // constructor to make a copy of a button
    public Button(Button b) {
        this.position = b.getPosition();
        this.name = b.name;
        this.size = b.getSize();
        this.response = b.response;
        this.activated = false;
    }

    public void draw(GraphicsEngine ge) {
        ge.setColor(Color.CYAN);
        ge.drawRectangle(this.position, this.size, true);
        // draws the name of the button on it

        ge.setColor(Color.darkGray);
        Vector2 textPosition = new Vector2(20, 20).add(this.position);
        ge.drawText(this.name, textPosition);

        // draws yellow outline if the mouse is on
        if (this.highlighted) {
            this.highlight(ge, Color.yellow);
        }

        //TODO remove it to a ButtonWithInput
        //activates an input window if it is possible

        if (this.activated) {

            if (this.input != null) {
                this.input.setVisible(this.activated);
            }

            if (this.response.panel != null) {
                this.response.panel.draw(ge);
            }
        }
    }

    public void highlight(GraphicsEngine ge, Color color) {
        // switches the color specified
        // made to differ pressed button
        // and the button which has a mouse pointer on it
        ge.setColor(color);
        ge.drawRectangle(this.position, this.size, false);
    }

    @Override
    public String toString() {
        return "Button:" + name +
                " pos:" + position +
                ", size:" + size +'\'';
    }

    public Vector2 getPosition() {
        return new Vector2(this.position);
    }

    public void setPosition(Vector2 position) {
        this.position = new Vector2(position);

        if (this.response.getPanel() != null) {
            Vector2 updatedPos = new Vector2(position);
            updatedPos.add(new Vector2(this.size.getX(), 0));
            this.response.getPanel().setPosition(updatedPos);
        }
    }

    public void onButton(Vector2 point, boolean click) {

        if (CollisionDetector.pointInRectangle(point, this.position, this.size)) {

            this.highlighted = true;
            //TODO remove later
            if (click) {
                System.out.println("pressed " + this.name);
                this.response.activate();
            }

            if (click) {
                this.activate();
            }
            if (this.response.panel != null) {
                this.response.panel.onMenu(point, click, "");
            }
        } else {
            this.highlighted = false;
        }
    }

    public void activate() {
        this.activated = !this.activated;
    }

    public boolean isActivated() {
        return this.activated;
    }

    public Vector2 getSize() {
        return new Vector2(this.size);
    }

}


