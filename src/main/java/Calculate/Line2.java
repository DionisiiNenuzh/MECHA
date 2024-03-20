package Calculate;

public class Line2 {

    private final Vector2 end;
    private final Vector2 start;
    //from start to the end
    private final Vector2 direction;

    public Line2(Vector2 end, Vector2 start) {
        //assign all attributes
        this.end = new Vector2(end);
        this.start = new Vector2(start);
        this.direction = new Vector2(end);
        this.direction.sub(start);
    }

    // methods to access private attributes
    public Vector2 getEnd() {
        return end;
    }

    public Vector2 getStart() {
        return start;
    }

    public Vector2 getDirection() {
        return direction;
    }
}
