package Graphical;


import Calculate.Vector2;
import java.awt.Color;

public interface GraphicsEngine {

  void drawPoint(Vector2 point);
  void drawLine(Vector2 point1, Vector2 point2);
  void setColor(Color c);



}
