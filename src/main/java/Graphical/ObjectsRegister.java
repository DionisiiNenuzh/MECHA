package Graphical;

import Calculate.Force.FreeForce;
import Calculate.Force.PhysicsHandler;
import Calculate.Force.Velocity;
import Calculate.Vector2;
import GUI.Button;
import GUI.ObjectsDisplay;
import GUI.OutputWin;
import GUI.TimeDisplay;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
// Purpose is to make all objects processes be done by register object which holds Rect objects
public class ObjectsRegister {

    // Object which adds visual panel on the toolbar
    public ObjectsDisplay DisplayList;
    // Addition of the velocities or forces added by user
    public ArrayList<Velocity> velocities;
    public ArrayList<FreeForce> forces;
    // queue structure characteristics
    private int pointer;
    private final int size;
    // this holds rectangles
    private final ArrayList<Rect> queue;
    // this holds colors of the rectangles
    private final ArrayList<Color> colors = new ArrayList<Color>();
    // this object adds forces and checks collisions
    private final PhysicsHandler PhSystem;
    // checks whether it should save it or reload
    private boolean restarted = false;
    private boolean Changed = true;

    // Constructor of the register
    public ObjectsRegister(ObjectsDisplay objectsDisplay) {

        // initialisation of all variables
        this.pointer = 0;
        this.size = Constants.MAXNUM_OBJECTS_ON_SCREEN;
        this.queue = new ArrayList<>(Constants.MAXNUM_OBJECTS_ON_SCREEN);
        this.DisplayList = objectsDisplay;
        this.initColors();
        this.velocities = new ArrayList<Velocity>();
        this.forces = new ArrayList<FreeForce>();

        this.PhSystem = new PhysicsHandler(new Vector2(0, Constants.GRAVITY_g));

    }

    // determines the order of colors of rectangles
    public void initColors() {
        this.colors.addAll(List.of(
            Color.RED, Color.ORANGE, Color.MAGENTA,
            Color.PINK, Color.green, Color.yellow, Color.CYAN));
    }

    // adds an object to the queue
    public void add(Rect rect) {
        this.Changed = true;
        // checks whether it has not exceeded the limit of objects
        if (this.pointer < this.size) {
            // if mass is 0 the object is stationary, it has a specific color
            if (rect.getMass() != 0) {
                rect.setColor(this.colors.get(this.queue.size() % this.colors.size()));
            } else {
                rect.setColor(Color.black);
            }
            // adds it to a queue, display panel and physics handler
            this.queue.add(rect);
            this.pointer += 1;
            this.DisplayList.add(rect);
            this.PhSystem.addObject(rect);
            // validation of the object that has been added
            System.out.println(this.queue.get(this.queue.size() - 1).getId());
        }
    }

    // checks whether there are any not velocities added which have not been applied yet
    public void checkVelocities(Rect rect) {
        // if there are no element there is no reason to check
        if (!this.velocities.isEmpty()) {
          for (Velocity velocity : this.velocities) {
            if (!velocity.getUsed()) {
              // to do implement a stack of all forces and another array where
              // the velocity will be deleted once it has been applied
              // checks is the velocity has been used
              if (rect.getId().equals(velocity.getName())) {
                rect.setLinearVel(velocity.getSpeed());
                velocity.setUsed(true);
              }
            }
          }
        }
    }

    public void changeSubject(OutputWin out) {
        // if there is selected object
        if (out.subject != null) {// and the subject has not changed or system has not been restarted
            if (!out.subject.getId().equals(this.DisplayList.getSelected()) || this.restarted) {
                //get a selected object
                out.ChangeSubject(this.findObjectByName(this.DisplayList.getSelected()));
                this.restarted = false;
            }// in other case change it to the object selected on the object display
        } else if (this.DisplayList.getSelected() != null) {
            out.ChangeSubject(this.findObjectByName(this.DisplayList.getSelected()));
        }

    }

    public Rect findObjectByName(String name) {
        if (name != null) {
            int found = -1;
            int i = 0;
            while (found == -1 || i < this.queue.size()) {
                if (this.queue.get(i).getId().equals(name)) {
                    found = i;
                }
                i += 1;
            }
            if (found == -1) {
                return null;
            }
            return this.queue.get(found);
        }
        return null;
    }

    public interface RectOperation {
        void apply(Rect r);
    }
    public void iterate(RectOperation rectOperation) {
        for (Rect rect : this.queue) {
            rectOperation.apply(rect);
        }
    }

    // get the object on specific position in the queue
    public Rect getItem(int i) {
        return this.queue.get(i);
    }

    // provides access to the queue size
    public int getSize() {
        return this.queue.size();
    }

    // adds user added velocity to the array of them taking the input from the input window
    public void addVelocity(float[] properties, String name) {
        this.velocities.add(new Velocity(properties[0], properties[1], name));
    }

    // adds user added force to the array of them taking the input from the input window
    public void addForce(float[] properties, String name) {
        float xValue = (float) Math.cos(properties[1]);
        this.forces.add(new FreeForce(properties[0], properties[1], name));
        this.PhSystem.addFreeForce(new FreeForce(properties[0], properties[1], name));
    }

    // updates all rectangles' positions depending on the forces
    public void update(double dt, TimeDisplay time, Button play, Button restart) {
        if (this.DisplayList.getRemove()) {
            this.removeFromDisplay(time, play, restart);
        }
        // checks whether new velocities were applied
        if (play.response.getActive()) {
          for (Rect rect : this.queue) {
            this.checkVelocities(rect);
          }
            // updates all forces
            this.PhSystem.update(dt);
        }
    }

    public void removeFromDisplay(TimeDisplay time, Button play, Button restart) {
        // can only remove if the time is 0, hence has been restarted
        if (time.getTime() == 0) {
            // shows
            this.Changed = true;
            // gets a rectangle selected, involves a check for null value
            Rect toRemove = this.findObjectByName(this.DisplayList.getSelected());
            // removes object from both physics system and object register
            this.queue.remove(toRemove);
            this.PhSystem.RemoveObject(toRemove);
            // deletes and icon of teh selected object
            this.DisplayList.isRemoved();
            // decreases pointer by one so it cannot add more objects
            this.pointer -= 1;
            // saves the simulation for the next time
            restart.response.Save();
        } else {
            //it stops the simulation if it is not stopped yet
            if (play.response.getActive()) {
                play.response.activate();
            }// shows an error message and further guidance
            JOptionPane.showMessageDialog(null, "To remove restart it first");
            // deactivates remove button
            this.DisplayList.getOptions().buttons.get(0).response.activate();
        }
    }

    public void clear() {
        // deletes all objects
        this.queue.clear();
        this.pointer = 0;
        // deletes all objects from Phsystem
        this.PhSystem.clear();
        // deletes all icons
        this.DisplayList.clear();
        // reloads velocities at the start
      for (Velocity velocity : this.velocities) {
        velocity.setUsed(false);
      }
        this.restarted = true;
    }

    // draws object and display panel
    public void draw(GraphicsEngine ge, Vector2 move) {

      for (Rect rect : this.queue) {
        rect.draw(ge, move);
      }

      this.DisplayList.draw(ge);
    }

    public void restartFreeForces() {
        this.PhSystem.RestartFreeForces();
    }

    public void Save() {
        this.Changed = false;
    }

    public boolean isChanged() {
        return this.Changed;
    }
}


