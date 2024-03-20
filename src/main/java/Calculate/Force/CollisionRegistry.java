package Calculate.Force;

import Graphical.Rect;
import java.util.ArrayList;

public class CollisionRegistry {

    public ArrayList<Rect> registry1, registry2;
    public CollisionDetector detector = new CollisionDetector();
    public Impulse Momentum = new Impulse();

    public CollisionRegistry() {
        this.registry1 = new ArrayList<>();
        this.registry2 = new ArrayList<>();
    }

    public void add(Rect body1, Rect body2) {
        this.registry1.add(body1);
        this.registry2.add(body2);

    }

    public void check() {

        for (int i = 0; i < this.registry2.size(); i++) {

            if (detector.BoxAndBox(this.registry1.get(i), this.registry2.get(i))) {
//                System.out.println("Collision between " + this.registry1.get(i).getId() + " and "
//                        + this.registry2.get(i).getId());
                // checks whether one of the objects is stationary and assign stationary to be the first parameter
                if (this.registry1.get(i).getMass() == 0f) {
                    this.Momentum.stationary(this.registry1.get(i), this.registry2.get(i));
                    this.registry2.get(i).collided = true;

                } else if (this.registry2.get(i).getMass() == 0f) {
                    this.Momentum.stationary(this.registry2.get(i), this.registry1.get(i));
                    this.registry1.get(i).collided = true;
                }

                //this.registry1.remove(i);
                //this.registry2.remove(i);
            } else {
                this.registry2.get(i).collided = false;
                this.registry1.get(i).collided = false;
            }
        }
    }

    public void clear() {
        this.registry1.clear();
        this.registry2.clear();
    }
}
