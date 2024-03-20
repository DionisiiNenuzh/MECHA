package Calculate.Force;

import Graphical.Rect;

public interface ForceGenerator {

    //TODO make a it applicable to any object
    void updateForce(Rect rect, double dt);
}
