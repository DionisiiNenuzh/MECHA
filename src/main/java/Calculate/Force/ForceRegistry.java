package Calculate.Force;

import Graphical.Rect;
import java.util.ArrayList;
import java.util.List;

public class ForceRegistry {

    private final List<ForceRegistration> registry;

    public ForceRegistry() {
        this.registry = new ArrayList<>();
    }

    // allows us to add forces that are acting on each object
    public void add(Rect rect, ForceGenerator fg) {
        this.registry.add(new ForceRegistration(fg, rect));
    }

    public void add(ForceGenerator fg, Rect rect) {
        this.registry.add(new ForceRegistration(fg, rect));
    }

    public void remove(ForceGenerator fg, Rect rect) {
        ForceRegistration fr = new ForceRegistration(fg, rect);
        registry.remove(fr);
    }

    public void clear() {
        this.registry.clear();
    }

    public void updateForces(float dt) {
        for (ForceRegistration fr : registry) {
            fr.fg.updateForce(fr.rect, dt);
        }
    }

    //might be used for to pause a game
    public void zeroForces() {
        for (ForceRegistration fr : registry) {

        }
    }
}
