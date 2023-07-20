package Calculate.Force;

import Calculate.Vector2;
import Graphical.Rect;

public class Gravity implements ForceGenerator {

    private final Vector2 gravity;

    // gravity is added for every single object on the canvas
    public Gravity(Vector2 force) {
        this.gravity = force;
    }

    @Override
    public void updateForce(Rect rect, double dt) {
        // f = m *a
        rect.addForce(new Vector2(gravity).mulRet(rect.getMass()));
    }
}
