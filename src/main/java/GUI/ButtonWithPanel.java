package GUI;

import Calculate.Vector2;

public class ButtonWithPanel extends Button{
  private final Panel panel;

  public ButtonWithPanel(Vector2 position, Vector2 size, String name,
      ButtonResponse response, Panel panel) {
    super(position, size, name, response);
    this.panel = panel;
    this.panel.setPosition(position);
  }

  public void add(Button b) {
    panel.add(b);
  }

  @Override
  public void onButton(Vector2 position, boolean click) {
    super.onButton(position, click);
    if (isActivated()) {
      this.panel.onMenu(position, click, "");
    }
  }

}
