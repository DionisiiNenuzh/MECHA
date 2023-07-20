package Test;

import Calculate.Force.CollisionDetector;
import Calculate.Line2;
import Calculate.Vector2;
import Graphical.Rect;
import java.awt.Color;

public class Collisions {

    public CollisionDetector collisions = new CollisionDetector();

    public Collisions() {
        this.collisions = new CollisionDetector();
    }

    public static void main(String[] args) {

        //pointLinecheck();
        pointInBoxNoRot();
        pointInBox();
        //RaycastAgainstLine();
        //LineAndBox();
        //GetIntervals();
        //OverlapOnIntervals();
        //NotRotatedRectangles();

    }

    public static void pointLinecheck() {
        CollisionDetector c = new CollisionDetector();
        // the output should be true
        if (CollisionDetector.pointOnLine(new Vector2(1, 1), new Line2(new Vector2(2, 2), new Vector2(4, 4)))) {
            System.out.println("It is true");
        } else {
            System.out.println("no it is not");
        }
        // this output should be false
        if (CollisionDetector.pointOnLine(new Vector2(2, 1), new Line2(new Vector2(2, 2), new Vector2(4, 4)))) {
            System.out.println("It is true");
        } else {
            System.out.println("no it is not");
        }
    }

    public static void pointInBoxNoRot() {
        CollisionDetector c = new CollisionDetector();
        Rect r = new Rect(0, 0, 17, 50, 45, Color.RED, "hello");
        Display.points(r.getPoints());
        //r.setRotationCurrent((float)Math.PI/4);
        // should be true for point with 1<x<49 and 1<y<16 which is included in this loop
        for (int i = 0; i < 100; i++) {
            if (CollisionDetector.pointInRectangle(new Vector2(0.9f * i, 10), r)) {
                System.out.println(i + " It is true");
            } else {
                //System.out.println("no it is not");
            }
        }

    }

    public static void pointInBox() {
        CollisionDetector c = new CollisionDetector();
        Rect r = new Rect(0, 0, 17, 50, 45, Color.RED, "hello");
        r.setRotationCurrent((float) Math.PI / 6);
        r.rotate();
        Display.points(r.getPoints());
        // should be true for point with 1<x<49 and 1<y<16 which is included in this loop
        for (int i = 0; i < 100; i++) {
            if (CollisionDetector.pointInRectangle(new Vector2(0.5f * i, 20), r)) {
                System.out.println(i + " It is true");
            } else {
                //System.out.println("no it is not");
            }
        }

    }

    public static void RaycastAgainstLine() {

    }

    public static void LineAndBox() {

    }

    public static void GetIntervals() {

    }

    public static void OverlapOnIntervals() {

    }

    public static void NotRotatedRectangles() {

        System.out.println();
        CollisionDetector c = new CollisionDetector();
        Rect r = new Rect(0, 0, 20, 20, 1, Color.RED, "hello");
        Rect b = new Rect(30, 0, 20, 20, 1, Color.RED, "hello");
        // the output should out put false
        if (c.BoxAndBox(r, b)) {
            System.out.println(" It is true");
        } else {
            System.out.println("no it is not");
        }
        // they should collide as they are on the same position
        r.position = new Vector2(40, 10);
        r.updatePoints();
        r.setRotationCurrent((float) Math.PI / 3.7F);
        r.rotate();
        b.setRotationCurrent((float) Math.PI / 8);
        b.rotate();
        Display.points(r.getPoints());
        Display.points(b.getPoints());

        if (c.BoxAndBox(r, b)) {
            System.out.println(" It is true");
        } else {
            System.out.println("no it is not");
        }


    }

}
