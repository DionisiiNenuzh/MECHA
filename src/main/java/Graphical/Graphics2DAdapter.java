package Graphical;

import Calculate.Vector2;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Graphics2DAdapter implements GraphicsEngine{
  private Graphics2D g2;
  public Graphics2DAdapter(Graphics2D g2) {
    this.g2 = g2;
    g2.setStroke(new BasicStroke(5));
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

  @Override
  public void drawRectangle(Vector2 topCorner, Vector2 size, boolean fill) {
    if (fill) {
      g2.fillRect((int) topCorner.getX(), (int) topCorner.getY(), (int) size.getX(), (int) size.getY());
    } else {
      g2.drawRect((int) topCorner.getX(), (int) topCorner.getY(), (int) size.getX(), (int) size.getY());
    }
  }

  @Override
  public void drawText(String text, Vector2 position) {
    g2.drawString(text, (int) position.getX(), (int) position.getY());

  }
}
