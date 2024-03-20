package Graphical;

import Calculate.Force.CollisionDetector;
import Calculate.Vector2;
import java.awt.Color;

public class ObjectOnDisplay {

    private boolean highlighted, selected;
    // rectangle where the highlight is checked
    // also it stores a name of the actual object
    // it represents
    private Rect region;
    // variable to store color of the highlight
    private Color color;

    public ObjectOnDisplay(Rect rect) {
        // default highlight color
        this.color = Color.yellow;
        this.region = new Rect(rect);
        this.highlighted = false;
        this.selected = false;
    }

    // alternative constructor to get a copy of an icon
    public ObjectOnDisplay(ObjectOnDisplay obj) {
        this.color = Color.yellow;
        this.highlighted = false;
        this.selected = false;
        this.region = obj.getRegion();
    }

    public void checkClick(Vector2 point, boolean click, CollisionDetector c) {
        // checks whether it is in the area of an icon or not
        if (CollisionDetector.pointInRectangle(point, this.region)) {
            this.highlighted = true;
            // changes color if clicked on
            if (click) {
                this.selected = true;
                this.color = Color.GREEN;
            }
        } else {
            //once it is outside the icon
            this.color = Color.yellow;
            this.highlighted = false;
            this.selected = false;
        }
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public Rect getRegion() {
        return region;
    }

    public void setRegion(Rect region) {
        this.region = region;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
