package GUI;

import Calculate.Force.CollisionDetector;
import Calculate.Vector2;
import Graphical.Rect;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

// object which is located on the screen toolbar
public class Button {

    // it has position of where to draw and identifier
    public Vector2 position, size;
    public String name;
    public boolean SetHighlight = false;
    // specific response which can be activated
    public ButtonResponse response;
    // has it input window which can be triggered if the button is pressed
    public MakeRectangle input;

    // constructor
    public Button(Vector2 position, Vector2 size,
            String name, ButtonResponse response) {
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

    // the procedure of drawing
    public void draw(Graphics2D g2) {
        // sets color and width of the line
        g2.setColor(Color.CYAN);
        g2.setStroke(new BasicStroke(5));
        // draws a rectangle with size of the button
        g2.fillRect((int) this.position.getX(), (int) this.position.getY(),
                (int) this.size.getX(), (int) this.size.getY());
        // draws the name of the button on it
        g2.setColor(Color.darkGray);
        g2.drawString(this.name, (int) this.position.getX() + 20, (int) this.position.getY() + 20);
        // draws yellow outline if the mouse is on
        if (this.SetHighlight) {
            this.highlight(g2, Color.yellow);
        }
        //activates an input window if it is possible
        if (this.response.getActive()) {
            this.setInputWindow();
        }
        // only if the dropdown panel exists the panel should be drawn
        if (this.response.getActive() && this.response.panel != null) {
            this.highlight(g2, Color.green);
            //adds a panel position to the right of the button
            //this.response.getPanel().position = new Vector2(this.position);
            Vector2 relativePos = new Vector2(this.position);
            relativePos.add(this.size);
            this.response.panel.setPosition(relativePos);
            this.response.panel.draw(g2);
        }
    }

    public void highlight(Graphics2D g2, Color color) {
        // switches the color specified
        // made to differ pressed button
        // and the button which has a mouse pointer on it
        g2.setColor(color);
        g2.setStroke(new BasicStroke(5));
        g2.drawRect((int) this.position.getX(), (int) this.position.getY(),
                (int) this.size.getX(), (int) this.size.getY());
    }

    public void setInputWindow() {
        // makes the input window visible if it exists
        if (this.input != null) {
            this.input.setVisible(this.response.Active);
        }
    }
    // method to get are details of the input window completed or not

    // checked
    public void CheckHighlight(Vector2 p, boolean click, String ch) {

        if (CollisionDetector.pointInRectangle(p, new Rect((int) this.getPosition().x,
                (int) this.getPosition().y, (int) this.getSize().y,
                (int) this.getSize().x, 10, Color.red, ""))) {
            System.out.println("Ther has been a detection on button" + this.toString() + " at point: " + p.toString());
            // if the point where the mouse pointer is now is inside
            // the rectangle
            this.SetHighlight = true;
            if (click) {
                System.out.println("pressed " + this.name);
                this.response.activate();
            }
        } else {
            this.SetHighlight = false;
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
        return "Button{" +
                ", name='" + name +
                "position=" + position +
                ", size=" + size +'\'' +
                '}';
    }

    public Vector2 getPosition() {
        return new Vector2(this.position);
    }

    public void setPosition(Vector2 position) {
        this.position = new Vector2(position);
    }

    public Vector2 getSize() {
        return new Vector2(this.size);
    }

    public void setSize(Vector2 size) {
        this.size = new Vector2(size);
    }

}


