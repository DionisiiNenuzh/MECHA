package Calculate;

public class Matrix2 {

    //matrix represents
    //[a b]
    //[c d]
    private float a, b, c, d;

    public Matrix2(float a, float b, float c, float d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    // this constructor is made for easier conversion from rotation matrices
    public Matrix2(double a, double b, double c, double d) {
        this.a = (float) a;
        this.b = (float) b;
        this.c = (float) c;
        this.d = (float) d;
    }

    //Scalar multiplication
    public void mul(float factor) {
        this.a = this.a * factor;
        this.b = this.b * factor;
        this.c = this.c * factor;
        this.d = this.d * factor;
    }

    // inverse matrix for equation solving
    public Matrix2 inverse() {
        Matrix2 mat = new Matrix2(d, -b, -c, a);
        // determinant division
        mat.mul(1 / (a * d - b * c));
        return mat;
    }

    // getters and setters
    public float getA() {
        return a;
    }

    public float getB() {
        return b;
    }

    public float getC() {
        return c;
    }

    public float getD() {
        return d;
    }
}
