package Calculate;

public class EquationSolver {

    // used to get normals to the vectors
    public static Vector2 equation(float a, float b, float c) {
        // a*x+b*y = c
        if (a == 0) {
            return new Vector2(1, 0);
        } else if (b == 0) {
            return new Vector2(0, 1);
        } else {
            return new Vector2(b, (c * b - a));
        }
    }

    public static Vector2 simultaneous_equations(float a1, float b1, float c1, float a2, float b2,
            float c2) {
        // check whether there is a solution or not
        // uses matrices to get the solution for the equation
        return new Vector2(
                Multiply.transform(new Matrix2(a1, b1, a2, b2).inverse(), new Vector2(c1, c2)));
    }
}
