package Calculate;

public class Rotate {

    // this angle is an objects that does all rotation procedures
    // angle is passed in radians
    public static Matrix2 RotMatrix(float angle) {
        return new Matrix2(Math.cos(angle), -Math.sin(angle), Math.sin(angle), Math.cos(angle));

    }
    // rotates a point relative to the certain centre

    public static Vector2 rotation(double angle, Vector2 point, Vector2 origin) {
        //rotation matrix initialisation
        Matrix2 mat = new Matrix2(Math.cos(angle), -Math.sin(angle), Math.sin(angle),
                Math.cos(angle));
        // storage for the new point relative to centre
        Vector2 temp = new Vector2(point);
        //
        temp.sub(origin);
        //actual rotation
        temp = Multiply.transform(mat, temp);
        //this adds the origin so the point is now relative to the origin of the screen (0,0)
        temp.add(origin);
        return temp;

    }
}
