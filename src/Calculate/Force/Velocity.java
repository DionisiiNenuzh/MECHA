package Calculate.Force;

import Calculate.Vector2;

public class Velocity {

    private final Vector2 speed;
    private final String name;
    private boolean used;

    public Velocity(float velocity, float angle, String name) {
        // creates the velocity based on the information given from the input
        this.speed = new Vector2((float) (Math.cos(-angle * Math.PI / 180) * velocity),
                (float) -(Math.sin(angle * Math.PI / 180) * velocity));

        this.name = name;
        this.used = false;
    }

    public String getName() {
        return this.name;
    }

    public Vector2 getSpeed() {
        return new Vector2(this.speed);
    }

    public boolean getUsed() {
        return this.used;
    }

    public void setUsed(boolean val) {
        this.used = val;
    }


}
