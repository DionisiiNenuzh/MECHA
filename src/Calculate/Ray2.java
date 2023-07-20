package Calculate;

public class Ray2 {

    private final Vector2 origin;
    private final Vector2 direction;

    // the object for raycasting process
    public Ray2(Vector2 origin, Vector2 direction) {
        this.origin = origin;
        this.direction = direction;
        this.direction.normalise();
    }

    // public getters for the ray
    public Vector2 getOrigin() {
        return this.origin;
    }

    public Vector2 getDirection() {
        return this.direction;
    }
}
