package Graphical;


import Calculate.Vector2;
import java.awt.Color;

public interface GraphicsEngine {

  void setOffset(Vector2 offset);

  Vector2 getOffset();

  void drawPoint(Vector2 point);
  void drawLine(Vector2 point1, Vector2 point2);
  void setColor(Color c);


  void drawRectangle(Vector2 rightTopPoint, Vector2 size, boolean fill);
  void drawText(String text, Vector2 position);



}
