package Graphical;

import Calculate.Vector2;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Graphics2DInterpreter implements GraphicsEngine{
  private Graphics2D g2;
  public Graphics2DInterpreter(Graphics2D g2) {
    this.g2 = g2;
    g2.setStroke(5);
  }

  @Override
  public void drawPoint(Vector2 point) {

  }

  @Override
  public void drawLine(Vector2 point1, Vector2 point2) {
    g2.draw(new Line2D.Float(point1.getX() + Constants.SCREEN_OFFSET, point1.getY(),
        point2.getX() + Constants.SCREEN_OFFSET, point2.getY()));

  }

  @Override
  public void setColor(Color c) {
    g2.setColor(c);
  }
}
