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
    public boolean highlighted = false;
    // specific response which can be activated
    public ButtonResponse response;
    // has it input window which can be triggered if the button is pressed
    public MakeRectangle input;
    // constructor

    public Button(Vector2 position, Vector2 size, String name, ButtonResponse response) {
        this.position = position;
        this.name = name;
        this.size = size;
        this.response = response;
        this.input = null;
    }

    // constructor to make a copy of a button
    public Button(Button b) {
        this.position = b.getPosition();
        this.name = b.name;
        this.size = b.getSize();
        this.response = b.response;
    }

    public void draw(GraphicsEngine ge) {
        // sets color and width of the line
        ge.setColor(Color.CYAN);
        // draws a rectangle with size of the button
        ge.drawRectangle(this.position, this.size, true);
        // draws the name of the button on it
        ge.setColor(Color.darkGray);
        Vector2 textPosition = new Vector2(this.position);
        textPosition.add(new Vector2(20, 20));
        ge.drawText(this.name, textPosition);

        // draws yellow outline if the mouse is on
        if (this.highlighted) {
            this.highlight(ge, Color.yellow);
        }

        //TODO remove it to a ButtonWithInput
        //activates an input window if it is possible
        if (this.response.getActive()) {
            this.setInputWindow();
            if (this.response.panel != null) {
                Vector2 relativePos = new Vector2(this.position);
                relativePos.add(this.size);
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

    public void setInputWindow() {
        // makes the input window visible if it exists
        if (this.input != null) {
            this.input.setVisible(this.response.Active);
        }
    }
    // method to get are details of the input window completed or not

    // checked
    public void checkHighlight(Vector2 p, boolean click, String ch) {

        if (CollisionDetector.pointInRectangle(p, position, size)) {
//            System.out.println("Ther has been a detection on button" + this.toString() + " at point: " + p.toString());
            // if the point where the mouse pointer is now is inside
            // the rectangle
            this.highlighted = true;
            if (click) {
                System.out.println("pressed " + this.name);
                this.response.activate();
            }
        } else {
            this.highlighted = false;
        }
        // checks whether it is clicked on the dropdown panel
        /*
        if (this.response.panel!=null) {
            this.response.panel.OnMenu(p, click,ch);
            if (this.response.getActive()) {
                System.out.println(this.name);
            }
            }}
         */
    }

    //getters and setters
    public boolean isHighlighted(Vector2 point) {
        return CollisionDetector.pointInRectangle(point, new Rect((int) this.getPosition().x,
                (int) this.getPosition().y, (int) this.getSize().y,
                (int) this.getSize().x, 10, Color.red, ""));
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
        System.out.println("The button has changed its position it is at " + this.toString());

        if (this.response.getPanel() != null) {
            Vector2 updatedPos = new Vector2(position);
            updatedPos.add(new Vector2(this.size.getX(), 0));
            this.response.getPanel().setPosition(updatedPos);
        }
    }

    public Vector2 getSize() {
        return new Vector2(this.size);
    }

    public void setSize(Vector2 size) {

        this.size = new Vector2(size);
    }

}


