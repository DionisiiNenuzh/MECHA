package Calculate.Force;

import Calculate.Vector2;
import Graphical.Rect;
import java.util.ArrayList;
import java.util.List;

public class PhysicsHandler {

    private final ForceRegistry registry;
    private final List<Rect> bodies;
    private final Gravity gravity;
    private final double updateDt;
    private final CollisionRegistry collisions;
    private ArrayList<FreeForce> freeForcesArray;

    public PhysicsHandler(Vector2 gravity) {
        // list of forces acting on physical objects and objects themselves
        this.registry = new ForceRegistry();
        this.bodies = new ArrayList<>();
        this.updateDt = 0.016f;
        this.gravity = new Gravity(new Vector2(gravity));
        this.collisions = new CollisionRegistry();
        this.freeForcesArray = new ArrayList<FreeForce>();
    }

    public void passForces(ArrayList<FreeForce> freeForces) {
        this.freeForcesArray = freeForces;

    }

    public void addFreeForce(FreeForce freeForce) {
        System.out.println("adds a force in ph system");
        System.out.println(
                "freeforce " + " " + freeForce.getForce().getX() + " " + freeForce.getForce()
                        .getY());
        this.freeForcesArray.add(freeForce);
        int index = 0;
        for (int i = 0; i < bodies.size(); i++) {
            if (freeForce.getName().equals(this.bodies.get(i).getId())) {
                index = i;
            }
        }
        this.registry.add(freeForce, this.bodies.get(index));
    }

    public void RestartFreeForces() {
        // index of the object for which to add force
        int index;
        for (int f = 0; f < this.freeForcesArray.size(); f++) {
            // -1 means not found object with this
            index = -1;
            for (int i = 0; i < bodies.size(); i++) {
                // if the name in the force matches then
                // set this object as an index
                if (this.freeForcesArray.get(f).getName().equals(
                        this.bodies.get(i).getId())) {
                    index = i;
                }
            }
            // if object found add this force to the registry
            if (index != -1) {
                this.registry.add(this.freeForcesArray.get(f), this.bodies.get(index));
            }
        }
    }


    public String addFreeForces(Rect rect) {
        String hello = "unfound";
        if (this.freeForcesArray.size() > 0) {
            for (int i = 0; i < this.freeForcesArray.size(); i++) {
                if (this.freeForcesArray.get(i).getName().equals(rect.getId())) {
                    hello = rect.getId();
                    break;
                }
            }
        }
        return hello;
    }

    public void RemoveObject(Rect r) {
        this.bodies.remove(r);
    }

    public void update(double dt) {
        //updates every force body pair in registry
        this.registry.updateForces((float) dt);
        // creates every single pair of possible collisions
        this.collisionList(0);
        //checks the collisions are true or not
        this.collisions.check();
        // deletes all to recalculate them for new frame
        this.collisions.clear();

        // update each body position depending on the resultant force acting on it
        for (int i = 0; i < this.bodies.size(); i++) {
            this.bodies.get(i).update(dt);
        }
    }

    public void addObject(Rect rect) {
        // adds new body and adds gravity to it
        this.bodies.add(rect);
        this.registry.add(this.gravity, rect);

    }

    // recursion that adds any possible pair of collisions
    public void collisionList(int pointer) {
        if (pointer >= this.bodies.size() - 1) {
        } else if (this.bodies.size() > 0) {
            for (int i = pointer + 1; i < this.bodies.size(); i++) {
                this.collisions.add(this.bodies.get(pointer), this.bodies.get(i));


            }
            this.collisionList(pointer + 1);
        }
    }

    public void clear() {
        this.bodies.clear();
    }
}
