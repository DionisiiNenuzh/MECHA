package GUI;

import Calculate.Vector2;

public class CreateButton extends ButtonResponse {

    public Vector2 position, size;
    public boolean OpenedMenu;

    public CreateButton(Vector2 position, Vector2 size) {
        super();
        // takes the position in the left bottom corner of the button
        Vector2 relativePos = new Vector2(position);
        relativePos.add(size);
        this.panel = new Panel(relativePos);
        this.size = new Vector2(size);
        // input a vector during initialisation it
        // will still change
        // creates one button per object
        Vector2 v = new Vector2(0, 0);
        this.panel.add(new Button(new Vector2(v),
                new Vector2(140, 27), "Moving Object", new ButtonResponse()));
        this.panel.add(new Button(new Vector2(v),
                new Vector2(140, 27), "Stationary ground", new ButtonResponse()));
        this.panel.add(new Button(new Vector2(v),
                new Vector2(140, 27), "Force", new ButtonResponse()));
        this.panel.add(new Button(new Vector2(v),
                new Vector2(140, 27), "Velocity", new ButtonResponse()));
        this.OpenedMenu = false;
    }

    // needed to access internal menu
    public Panel getPanel() {
        return this.panel;
    }

    // method that both opens the menu and activates
    // the response of the button
    public void activate() {
        this.Active = !(this.Active);
        this.OpenedMenu = !(this.OpenedMenu);
    }

}

