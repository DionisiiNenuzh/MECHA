package Graphical;

import Calculate.Vector2;
import java.awt.Color;
import java.util.List;

public abstract class Body {
  private Color color;
  private Vector2 position;
  private float mass;
  private List<Vector2> forces;
  private String id;

  public abstract void update(double dt);
  public abstract void draw();

  public Vector2 getPosition() {
    return position;
  }

  public float getMass() {
    return mass;
  }

  public String getId() {
    return id;
  }

  public Color getColor() {
    return this.color;
  }
  public void setColor(Color color) {
    this.color = color;
  }




}
