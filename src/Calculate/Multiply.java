package Calculate;

public class Multiply {

    public static Vector2 transform(Matrix2 mat, Vector2 vec) {
        return new Vector2(vec.getX() * mat.getA() + vec.getY() * mat.getB(),
                vec.getX() * mat.getC() + vec.getY() * mat.getD());
    }

    public static Matrix2 multiplyMatrices(Matrix2 mat1, Matrix2 mat2) {
        // [a b] * [x y] = [ax+bz ay+bw]
        // [c d] * [z w]   [cx+dz cz+dw]
        return new Matrix2(mat1.getA() * mat2.getA() + mat1.getB() * mat2.getC(),
                mat1.getA() * mat2.getB() + mat1.getB() * mat2.getD(),
                mat1.getC() * mat2.getA() + mat1.getD() * mat2.getC(),
                mat1.getC() * mat2.getB() + mat1.getD() * mat2.getD());
    }

    public static float dotProduct(Vector2 vec1, Vector2 vec2) {
        return vec1.getX() * vec2.getX() + vec1.getY() * vec2.getY();
    }    // this part returns a vector perpendicular to the given one

    public static Vector2 normal(Vector2 vec) {
        if (vec.x == 0f) {
            return new Vector2(1, 0);
        } else if (vec.y == 0f) {
            return new Vector2(0, 1);
        } else {
            return new Vector2(1, -(vec.x / vec.y));
        }
    }

    //It is matrix on vector multiplication to get transformations
    //Is used to rotate coordinates around certain pivot or to enlarge from a certain point
    //in case vec and matrix are passed in different order
    public Vector2 transform(Vector2 vec, Matrix2 mat) {
        return new Vector2(vec.getX() * mat.getA() + vec.getY() * mat.getB(),
                vec.getX() * mat.getC() + vec.getY() * mat.getD());
    }
}