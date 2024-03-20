package test;

import Calculate.EquationSolver;
import Calculate.Matrix2;
import Calculate.Multiply;
import Calculate.Rotate;
import Calculate.Vector2;
import Graphical.Rect;
import java.awt.Color;

public class VecAndMat {

    public static void main(String[] args) {
        //RotationCheck();
        //VectorAddition();
        //MultiplicationsOfMatrices();
        //ScalarFactorAndDotProduct();
        //InverseOfMatrix();
        //MultiplicationsOfMatrices();
        //TransformationsOnVectors();
        //UnitVectorsAndMagnitudeAndNormal();
        Simul();
    }

    public static void VectorAddition() {
        // two vectors to add up
        Vector2 v1 = new Vector2(89, 45);
        System.out.println(v1.x + " " + v1.y);
        // one vector is added to another in result it should be 89+21 = 110 and 45+0.45 = 45.45
        v1.add(new Vector2(21, 0.45f));
        System.out.println(v1.x + " " + v1.y);
        // the substraction
        v1.sub(new Vector2(9, 44));
        // the result of the substraction should be 110-9 = 101 and 45.45-44 = 1.45
        System.out.println(v1.x + " " + v1.y);
    }

    //18
    public static void MultiplicationsOfMatrices() {
        Matrix2 m1 = Rotate.RotMatrix(1f);
        Matrix2 m2 = Rotate.RotMatrix(2f);
        // the resultant should be the same as rotational matrix of rotation 3 rad
        Display.matrix(Multiply.multiplyMatrices(m1, m2));
        // rotational matrix of 3rad
        Display.matrix(Rotate.RotMatrix(3f));
    }

    //19 and 20
    public static void TransformationsOnVectors() {
        Vector2 v1 = new Vector2(1, 0);
        //multiplies by 100 and then rotates by 45 degrees
        v1 = Multiply.transform(new Matrix2(100, 0, 0, 100), v1);
        System.out.println(v1.x + " " + v1.y);
        v1 = Multiply.transform(Rotate.RotMatrix((float) Math.PI / 4), v1);
        // result should be the same coordinates so x = y
        System.out.println(v1.x + " " + v1.y);
        Vector2 v2 = new Vector2(1, 0);
        //multiplies by or extends
        v2 = Multiply.transform(Rotate.RotMatrix((float) Math.PI / 4), v2);
        System.out.println(v2.x + " " + v2.y);
        // outcome should be the same as previous
        v2 = Multiply.transform(new Matrix2(100, 0, 0, 100), v2);
        System.out.println(v2.x + " " + v2.y);


    }

    //22 and 23
    public static void ScalarFactorAndDotProduct() {
        Vector2 v1 = new Vector2(1, 345);
        // the result should output 45.6 and 345*45.6=15732
        v1.mul(45.6);
        System.out.println(v1.x + " " + v1.y);
        // should output 45.6*2-0.4*15732=-6201.6
        float num = Multiply.dotProduct(v1, new Vector2(2, -0.4f));
        System.out.println(num);
    }

    //24 and 25 and 26
    public static void UnitVectorsAndMagnitudeAndNormal() {
        Vector2 v1 = new Vector2(12, 5);
        // the output should be 144+25 = 169 = 13^2 and 13 for the actual magnitude
        System.out.println(v1.getMagnitudeSquared() + " " + v1.getMagnitude());
        Vector2 v2 = new Vector2(3f, 4f);
        v2.normalise();
        System.out.println(v2.x + " " + v2.y);
        // it should give 1 as unit vector magnitude is always 1
        System.out.println(v2.getMagnitudeSquared() + " " + v2.getMagnitude());
    }

    //27
    public static void InverseOfMatrix() {
        Matrix2 mat = new Matrix2(2, 3, 4, 4);
        Display.matrix(mat);
        Matrix2 matinv = mat.inverse();
        Display.matrix(matinv);
        // in result the multiplication should give identity matrix [1,0]
        //                                                          [0,1]
        Display.matrix(Multiply.multiplyMatrices(mat, matinv));

    }

    public static void RotationCheck() {
        Rect r = new Rect(10, 10, 20, 20, 30, Color.RED, "hello");//
        //initial position
        Display.points(r.getPoints());
        //rotation of 2Pi = 360 degrees, hence no rotation
        r.setRotationCurrent((float) Math.PI * 2);
        r.rotate();
        Display.points(r.getPoints());
        //rotation at 45 degrees
        r.setRotationCurrent((float) Math.PI / 6);
        r.rotate();
        Display.points(r.getPoints());
        // rotation at 405 equivalent to 45 degrees
        r.setRotationCurrent((float) Math.PI * 13 / 6);
        r.rotate();
        Display.points(r.getPoints());
    }

    public static void Simul() {
        Vector2 v1 = EquationSolver.simultaneous_equations(1, 2, 0, 2, 1, 1);
        System.out.println(v1.x + " " + v1.y);
    }

}
