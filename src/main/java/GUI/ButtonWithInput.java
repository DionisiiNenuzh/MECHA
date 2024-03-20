package GUI;

import Calculate.Vector2;
import java.awt.Graphics2D;

public class ButtonWithInput extends Button {
  private MakeRectangle input;

  public ButtonWithInput(Vector2 position, Vector2 size,String name, ButtonResponse response, MakeRectangle input) {
    super(position, size, name, response);
    this.input = input;
  }

  public void draw(Graphics2D g2) {
    super.draw(g2);

    if (this.response.getActive()) {
      this.setInputWindow();
      // this file inside
    }
  }



}
