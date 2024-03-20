package Calculate.Force;

import Graphical.Rect;

public class ForceRegistration {

    public ForceGenerator fg = null;
    public Rect rect = null;

    public ForceRegistration(ForceGenerator fg, Rect rect) {
        this.fg = fg;
        this.rect = rect;

    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != ForceRegistration.class) {
            return false;
        }
        ForceRegistration fr = (ForceRegistration) other;
        return fr.rect == this.rect && fr.fg == this.fg;
    }

}
