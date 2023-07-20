package Test;

import Calculate.Matrix2;
import Calculate.Vector2;
import java.util.ArrayList;

public class Display {

    public static void points(ArrayList<Vector2> p) {
        for (int i = 0; i < p.size(); i++) {
            //System.out.print("point "+(i+1)+" (x,y) ("+p.get(i).x+","+p.get(i).y+") "+"  ");
            System.out.print("(" + p.get(i).x + "," + p.get(i).y + ")" + ",");

        }
        System.out.println("Points displayed");

    }

    public static void matrix(Matrix2 m) {
        System.out.println(m.getA() + " " + m.getB());
        System.out.println(m.getC() + " " + m.getD());
    }

}
