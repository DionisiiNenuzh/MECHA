package GUI;

import Graphical.ObjectsRegister;

public class ButtonResponse {

    public boolean Active = false;
    public boolean respond;
    public Panel panel = null;

    public ButtonResponse() {
        this.respond = true;
    }

    public void setRespond(boolean v) {
        this.respond = v;
    }

    public void activate() {
        this.Active = !this.Active;
    }

    public Panel getPanel() {
        return this.panel;
    }

    public boolean getActive() {
        return this.Active;
    }


    public void DropDown() {

    }

    public void Responding() {
    }

    public void update(ObjectsRegister objects) {
    }

    public void Save() {
    }
}

