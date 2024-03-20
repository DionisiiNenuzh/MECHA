package Calculate;

public class Vector2 {

    public float x, y;
    private float magnitude, magnitudeSquared;

    // class which is a datatype using throughout whole programming
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // constructor to pass new vector which is a copy of one stated in parameters
    public Vector2(Vector2 vec) {
        this.x = vec.getX();
        this.y = vec.getY();
    }

    // getters for attributes
    public float getX() {
        return x;
    }

    // changes through setters
    public void setX(float x) {
        this.x = x;
    }

    //Addition and subtraction of the other vector

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void add(Vector2 vec) {
        this.x += vec.getX();
        this.y += vec.getY();
    }

    public void sub(Vector2 vec) {
        this.x -= vec.getX();
        this.y -= vec.getY();
    }

    // scalar multiplication using floats and doubles, as time is passed in double variable
    public void mul(float factor) {
        this.x = this.x * factor;
        this.y = this.y * factor;
    }

    public void mul(double factor) {
        this.x = this.x * (float) factor;
        this.y = this.y * (float) factor;
    }

    // Makes a unit vector of the same direction but magnitude 1
    public void normalise() {
        this.mul(1 / this.getMagnitude());
    }

    // methods to get magnitudes, actual magnitude contains square root whihc takes a lot of cycles
    // so sometimes it is reasonable to use squaredvalue as it is less resource consuming
    public float getMagnitudeSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public float getMagnitude() {
        return (float) Math.sqrt((this.getMagnitudeSquared()));
    }

    // method needed to make the resultant force 0 for negligibly small values
    public void zero() {
        this.x = 0f;
        this.y = 0f;
    }

    //returns vector which has been multiplied by factor, without changing this vector itself
    public Vector2 mulRet(double factor) {
        return new Vector2(this.x * (float) factor, this.y * (float) factor);
    }

    public float dot(Vector2 vec) {
        return this.x * vec.getX() + this.y * vec.getY();
    }


    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
