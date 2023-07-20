package Test;

import Calculate.EquationSolver;
import Calculate.Force.CollisionDetector;
import Calculate.Force.Impulse;
import Calculate.Line2;
import Calculate.Ray2;
import Calculate.RaycastResult;
import Calculate.Vector2;
import Graphical.Rect;
import java.awt.Color;

public class equations {

    public static void main(String[] args) {
        //equationcheck();
        //rayAndLine();
        //physics(2,2,4.0026);
        impulseCheck();

    }

    public static void equationcheck() {
        EquationSolver e = new EquationSolver();
        Vector2 z = EquationSolver.simultaneous_equations(1, 1, -1, 2, 5, 9);
        System.out.println(z.x + " " + z.y);

    }

    public static void rayAndLine() {
        CollisionDetector c = new CollisionDetector();
        Ray2 r = new Ray2(new Vector2(0, 0), new Vector2(3, 5));
        Line2 l = new Line2(new Vector2(2, 0), new Vector2(2, 10));
        RaycastResult res = c.LineAndRay(r, l);
        if (res.getHit()) {
            System.out.println(res.getPoint().getX() + " " + res.getPoint().getY());
        }
        System.out.println(res.getPoint().getX() + " " + res.getPoint().getY());

    }

    public static void impulseCheck() {
        Rect rm = new Rect(30, 0, 20, 5, 10, Color.RED, "hell");
        Rect rs = new Rect(0, 15, 40, 60, 0f, Color.RED, "hello");
        Impulse imp = new Impulse();
        imp.stationary(rs, rm);

    }

    public static void physics(int a, int b, double mass) {
        double val = (a * 1.0080 + b * 1.0087 - mass);

        System.out.println(val);
        // energy
        System.out.println(val * 2.998 * 2.998 * 1.661);

    }
}
