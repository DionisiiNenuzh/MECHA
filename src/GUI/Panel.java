package GUI;

import Calculate.Force.CollisionDetector;
import Calculate.Vector2;
import Graphical.Rect;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Panel {

    // attributes needed to add functionality of the queue
    public ArrayList<Button> buttons = new ArrayList<Button>();
    // position to draw and size to check
    // whether any button is clicked on the menu
    public Vector2 position, size;
    //direction where next button will be drawn
    private final Vector2 buttonSpacing = new Vector2(0, 30);

    public Panel(Vector2 v) {
        //
        this.position = new Vector2(v);
        //this.size = new Vector2(this.buttonSpacing.y);
    }

    public void init() {

        //creates each button with position one after another
        this.buttons.get(0).setPosition(this.position);
        // increases size by size of one button
        //this.size.zero();
        //this.size.add(this.buttons.get(0).size);

        // places each button i spacings down from
        // the position of the menu
        if (this.buttons.size() > 1) {
            for (int i = 1; i < this.buttons.size(); i++) {
                Vector2 pos = new Vector2(this.buttons.get(i - 1).getPosition());
                pos.add(this.buttonSpacing);
                this.buttons.get(i).setPosition(pos);
                //this.size.add(this.buttons.get(i).size);
                //this.size.add(this.buttonSpacing);
                //this.size.sub(new Vector2(this.buttons.get(i).getSize().getX(),0));
            }
        }
    }

    // adds a new button to be drawn
    public void add(Button b) {
        this.buttons.add(b);
        if (this.buttons.size() == 1) {
            this.size = b.getSize();
        } else {
            this.size.add(this.buttonSpacing);
        }
    }

    public void draw(Graphics2D g2) {
        // draws each button consecutively
        this.init();
        //g2.drawRect((int)this.position.getX(),(int)this.position.getY(),(int)this.size.getX(),(int)this.size.y);
        for (int i = 0; i < this.buttons.size(); i++) {
            this.buttons.get(i).draw(g2);
        }
    }

    //checks whether the point is clicked on menu's area
    public void OnMenu(Vector2 point, boolean click, String ch) {
        boolean isOne = false;
        CollisionDetector c = new CollisionDetector();
        for (int i = 0; i < this.buttons.size(); i++) {
            if (this.buttons.get(i).response.getPanel() != null) {
                //System.out.println("panel "+this.buttons.get(i).name);
                if (this.buttons.get(i).response.panel.getOnMenu(point, c)) {
                    this.buttons.get(i).response.panel.OnMenu(point, click, ch);
                    isOne = true;
                }
            }
        }

        if (CollisionDetector.pointInRectangle(point, new Rect((int) this.position.x,
                (int) this.position.y, (int) this.size.y, (int) this.size.x,
                10, Color.red, "hello"))) {
            for (int i = 0; i < this.buttons.size(); i++) {
                this.buttons.get(i).CheckHighlight(point, click, ch);
                if (this.buttons.get(i).isHighlighted(point, c) &&
                        this.buttons.get(i).response.panel != null) {
                    this.buttons.get(i).response.panel.OnMenu(point, click, ch);
                }
            }
        } else if (!isOne) { // can be used to disable highlight
            this.offHighlight();
        }

    }

    public void offHighlight() {
        for (int i = 0; i < this.buttons.size(); i++) {
            this.buttons.get(i).SetHighlight = false;
        }
    }

    public boolean getOnMenu(Vector2 point, CollisionDetector c) {
        //System.out.println(this.size.x+" "+this.size.y);
        return CollisionDetector.pointInRectangle(point, new Rect((int) this.position.x,
                (int) this.position.y, (int) this.size.y, (int) this.size.x,
                10, Color.red, "hel"));
    }

    public void setPosition(Vector2 v) {
        this.position = new Vector2(v);
    }

    public void inputKey(String c) {
    }

    public void off() {
        for (int i = 0; i < this.buttons.size(); i++) {
            this.buttons.get(i);
        }
    }

}
