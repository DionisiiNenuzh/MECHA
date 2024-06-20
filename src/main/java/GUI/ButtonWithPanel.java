package GUI;

import Calculate.Vector2;
import Graphical.GraphicsEngine;

public class ButtonWithPanel extends Button{
  private final Panel panel;
  private final boolean horizontal = false;

  public ButtonWithPanel(Vector2 position, Vector2 size, String name,
      ButtonResponse response, Panel panel) {
    super(position, size, name, response);
    this.panel = panel;
    Vector2 panelPosition = new Vector2(position);
    // another comment

    if (horizontal) {
      panelPosition.add(new Vector2(0, this.getSize().getY())) ;
    } else {
      panelPosition.add(new Vector2(this.getSize().getX(), 0)) ;
    }

    this.panel.setPosition(panelPosition);
  }

  @Override
  public void setPosition(Vector2 newPos) {
    super.setPosition(newPos);

    Vector2 updatedPos = new Vector2(newPos);
    updatedPos.add(new Vector2(this.size.getX(), 0));
    this.panel.setPosition(updatedPos);

  }

  public void add(Button b) {

    panel.add(b);
  }



  @Override
  public void draw(GraphicsEngine ge) {
    super.draw(ge);
    if (this.isActivated()) {
      panel.draw(ge);
    }
  }

  @Override
  public void onButton(Vector2 position, boolean click) {
    super.onButton(position, click);

    if (this.isActivated()) {
      System.out.println("checking on button");
      this.panel.onMenu(position, click, "");
    }
  }

}
