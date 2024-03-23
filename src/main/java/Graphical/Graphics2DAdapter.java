package Graphical;

import Calculate.Vector2;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Graphics2DAdapter implements GraphicsEngine{
  private final Graphics2D g2;
  private Vector2 offsetVal;
  public Graphics2DAdapter(Graphics2D g2) {
    this.g2 = g2;
    g2.setStroke(new BasicStroke(5));
    setOffset(new Vector2(0,0));
  }

  @Override
  public void setOffset(Vector2 offset) {
    this.offsetVal = offset;
  }

  @Override
  public Vector2 getOffset() {
    return new Vector2(this.offsetVal);
  }

  @Override
  public void drawPoint(Vector2 point) {

  }

  @Override
  public void drawLine(Vector2 point1, Vector2 point2) {
    Vector2 p1 = getOffset().add(point1);
    Vector2 p2 = getOffset().add(point2);
    g2.draw(new Line2D.Float(p1.getX(), p1.getY(), p2.getX(), p2.getY()));
  }

  @Override
  public void setColor(Color c) {
    g2.setColor(c);
  }

  @Override
  public void drawRectangle(Vector2 topCorner, Vector2 size, boolean fill) {
    Vector2 point = getOffset().add(topCorner);

    if (fill) {
      g2.fillRect((int) point.getX(), (int) topCorner.getY(), (int) size.getX(), (int) size.getY() );
    } else {
      g2.drawRect((int) topCorner.getX(), (int) topCorner.getY() , (int) size.getX() , (int) size.getY() );
    }
  }

  @Override
  public void drawText(String text, Vector2 position) {
    Vector2 point = getOffset().add(position);
    g2.drawString(text, (int) point.getX(), (int) point.getY());

  }
}
