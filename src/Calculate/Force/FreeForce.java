package Calculate.Force;

import Calculate.Vector2;
import Graphical.Rect;

public class FreeForce implements ForceGenerator {

    private final Vector2 force;
    private final String body;

    public FreeForce(Vector2 force, String index) {
        this.force = force;
        this.body = index;
    }

    public FreeForce(float force, float angle, String name) {
        this.force = new Vector2((float) Math.cos(angle / 180 * Math.PI) * force,
                (float) Math.sin(angle / 180 * Math.PI) * force);
        this.body = name;
    }

    // methods to access private attributes
    public String getName() {
        return this.body;
    }

    public Vector2 getForce() {
        return new Vector2(this.force);
    }

    @Override
    public void updateForce(Rect rect, double dt) {
        rect.addForce(this.force);

    }
}
