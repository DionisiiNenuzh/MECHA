package GUI;

import Calculate.Force.CollisionDetector;
import Calculate.Vector2;
import Graphical.Constants;
import Graphical.Rect;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class OutputWin extends Button {

    // identifiers of the objects
    public String name;
    public ArrayList<String> output = new ArrayList<String>();
    public ArrayList<String> data = new ArrayList<String>();
    public Rect subject = null;
    public ButtonResponse response = new ButtonResponse();
    boolean hidden = true;
    // attributes needed to draw it in two states
    private final Vector2 positionHidden;
    private final Vector2 sizeHidden;
    private final Vector2 positionTrue;
    private final Vector2 sizeTrue;
    private Vector2 position, size;
    // boolean vales to hide or highlight
    private boolean SetHighlight = false;
    // the object of the
    private String bodyName;

    public OutputWin(Vector2 position, Vector2 size, String name, ButtonResponse response) {
        super(position, size, name, response);
        this.name = name;
        //position to return to when window is shown
        this.positionTrue = new Vector2(position);
        this.sizeTrue = new Vector2(size);
        // when size and position are hidden
        this.positionHidden = new Vector2(Constants.SCREEN_WIDTH - 100, 30);
        this.sizeHidden = new Vector2(90, 30);
        this.initText();
    }

    public void initText() {
        // adds text to output
        this.output.add("Name of the object");
        this.output.add("Force in coordinates: ");
        this.output.add("Resultant force in N: ");
        this.output.add("acceleration in m/s^2: ");
        this.output.add("speed in coordinates: ");
        this.output.add("speed in m/s: ");

    }

    public void draw(Graphics2D g2) {
        // swaps hidden and open positions depending on the state
        this.hideAndOpen();
        g2.setColor(Color.GRAY);
        g2.fillRect((int) this.position.getX(), (int) this.position.getY(),
                (int) this.size.getX(), (int) this.size.getY());
        if (this.hidden) {
            g2.setStroke(new BasicStroke(5));
            g2.setColor(Color.BLACK);
            g2.drawString(this.name, (int) this.position.getX()
                    + 20, (int) this.position.getY() + 20);
            if (this.subject != null) {
                // creates an array of data from the object
                this.data.add(this.convert(2, this.subject.forceDisplay.x));
                this.data.add(this.convert(2, this.subject.forceDisplay.y));
                this.data.add(this.convert(2, this.subject.getForceDisplay().getMagnitude()));
                this.data.add(this.convert(2,
                        this.subject.getForceDisplay().getMagnitude() * this.subject.inversemass));
                this.data.add(this.convert(2, this.subject.getLinearVel().getX()));
                this.data.add(this.convert(2, this.subject.getLinearVel().getY()));
                this.data.add(this.convert(2, this.subject.getLinearVel().getMagnitude()));
                this.writeDetails(g2);
                this.data.clear();
            } else {
                g2.setColor(Color.red);
                g2.drawString("Object not chosen", (int) this.position.getX() + 20,
                        (int) this.position.getY() + 50);
            }
        } else {
            g2.setColor(Color.BLACK);
            g2.drawString("Output Window", Constants.SCREEN_WIDTH - 95, this.position.getY() + 20);
        }
        // highlights the box if it should be
        if (this.SetHighlight) {
            this.highlight(g2);
        }
    }

    public void writeDetails(Graphics2D g2) {
        // sets up the position of the first object
        int CharSpace = 8;
        int posX = (int) this.position.getX() + 20;
        // sets up the text to be drawn with spacing on the left
        int posY = (int) this.position.getY() + 20 + 4 * CharSpace;
        // draws a first element of the text
        g2.drawString(this.output.get(0), posX, posY);
        String s = this.bodyName;
        // adds data from the object to the left
        // depending on the length of the string
        g2.drawString(s, posX + this.output.get(0).length() * CharSpace, posY);

        for (int i = 1; i < this.output.size(); i++) {
            // shifts the value downwards
            posY += 24;
            drawing(g2, i, posX, posY);
        }
    }

    public void drawing(Graphics2D g2, int index, int x, int y) {
        String s, spaces;
        switch (index) {
            case 1:
                spaces = " ";
                for (int i = 0; i < 12 - this.data.get(0).length(); i++) {
                    spaces += " ";
                }
                s = " x: " + this.data.get(0) + spaces + "y: " + this.data.get(1);
                g2.drawString(this.output.get(1) + s, x, y);
                break;
            case 2:
                s = this.data.get(2);
                g2.drawString(this.output.get(2) + s, x, y);
                break;
            case 3:
                s = this.data.get(3);
                g2.drawString(this.output.get(3) + s, x, y);
                break;
            case 4:
                spaces = " ";
                for (int i = 0; i < 12 - this.data.get(4).length(); i++) {
                    spaces += " ";
                }
                s = " x: " + this.data.get(4) + spaces + "y: " + this.data.get(5);
                g2.drawString(this.output.get(4) + s, x, y);
                break;
            case 5:
                s = this.data.get(6);
                g2.drawString(this.output.get(5) + s, x, y);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + index);
        }
    }

    public void hideAndOpen() {
        // assigns a correct version of coordinates and size
        this.hidden = this.response.Active;
        //TODO resolve true and false contradictions
        if (!this.hidden) {
            this.position = new Vector2(this.positionHidden);
            this.size = new Vector2(this.sizeHidden);
        } else {
            this.position = new Vector2(this.positionTrue);
            this.size = new Vector2(this.sizeTrue);
        }
    }

    public void CheckHighlight(Vector2 p, boolean click) {
        this.hideAndOpen();
        CollisionDetector c = new CollisionDetector();
        if (CollisionDetector.pointInRectangle(p, new Rect((int) this.getPosition().x,
                (int) this.getPosition().y, (int) this.getSize().y, (int) this.getSize().x,
                10, Color.red, ""))) {
            this.SetHighlight = true;
            if (click) {
                this.response.activate();
            }
        } else {
            this.SetHighlight = false;
        }
    }

    public void highlight(Graphics2D g2) {
        g2.setColor(Color.yellow);
        // creates an outline of a different colour around the box
        g2.setStroke(new BasicStroke(5));
        g2.drawRect((int) this.position.getX(), (int) this.position.getY(),
                (int) this.size.getX(), (int) this.size.getY());
    }

    public Vector2 getPosition() {
        return new Vector2(this.position);
    }

    public Vector2 getSize() {
        return new Vector2(this.size);
    }

    public String convert(int dp, float num) {
        int factor = 1;
        for (int i = 0; i < dp; i++) {
            factor = factor * 10;
        }
        float val = Math.round(num * factor);
        val = val / factor;
        return String.valueOf(val);
    }

    public void ChangeSubject(Rect R) {
        if (R != null) {
            this.subject = R;
            this.bodyName = R.getId();
        } else {
            this.subject = null;
        }
    }
}

