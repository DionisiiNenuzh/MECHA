package Calculate;

public class RaycastResult {

    private Vector2 point;
    private Vector2 normal;
    private boolean hit;
    private float t;
    // object that returns whether the ray hit the point and its coordinates

    public RaycastResult() {
        this.point = new Vector2(0, 0);
        this.normal = new Vector2(0, 0);
        this.t = -1;
        this.hit = false;
    }
    // sets the value to characteristics of the raycast result

    public static void reset(RaycastResult result) {
        if (result != null) {
            result.point.zero();
            result.normal.zero();
            result.t = -1;
            result.hit = false;
        }
    }
    // reset of the result for the current raycast

    public void init(Vector2 start, Vector2 normal, float t, boolean hit) {
        this.point = new Vector2(start);
        this.normal = new Vector2(normal);
        this.t = t;
        this.hit = hit;
    }
    // getters for private attributes

    public Vector2 getPoint() {
        return this.point;

    }

    public boolean getHit() {
        return this.hit;
    }

}
