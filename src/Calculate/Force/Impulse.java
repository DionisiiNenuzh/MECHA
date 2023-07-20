package Calculate.Force;

import Calculate.EquationSolver;
import Calculate.Line2;
import Calculate.Multiply;
import Calculate.Ray2;
import Calculate.RaycastResult;
import Calculate.Vector2;
import Graphical.Constants;
import Graphical.Rect;
import java.util.ArrayList;

public class Impulse {

    public void resolve(Rect r1, Rect r2) {

    }

    public void stationary(Rect rs, Rect rm) {
        // get points of the two rectangles
        ArrayList<Vector2> points = rs.getPoints();
        ArrayList<Vector2> pointsMoving = rm.getPoints();
        // vectors that represent direction of the object collided with
        Vector2 I, dir, C;
        // two points of collisions
        Vector2 point2 = null;
        Vector2 point1 = null;
        C = new Vector2(0, 0);
        // objects needed to get intersections
        // and the direction of the impulse
        EquationSolver e = new EquationSolver();
        CollisionDetector c = new CollisionDetector();
        // new added
        ArrayList<Vector2> contactPoints = new ArrayList<>();

        for (int i = 1; i < points.size(); i++) {
            // direction to check is the direction of the rectangle's side
            dir = new Vector2(points.get(i - 1));
            dir.mul(-1);
            dir.add(points.get(i));
            // check whether it intersects with the line
            /*
            System.out.println("ray direction "+dir.getX()+" "+dir.getY());
            System.out.println("ray origin ("+points.get(i-1).getX()+", "+points.get(i-1).getY()+")");
            System.out.println("Diagonal1 1("+pointsMoving.get(0).getX()+", "+pointsMoving.get(0).getY()+")");
            System.out.println("Diagonal1 2("+pointsMoving.get(2).getX()+", "+pointsMoving.get(2).getY()+")");
            System.out.println("Diagonal2 1("+pointsMoving.get(1).getX()+", "+pointsMoving.get(1).getY()+")");
            System.out.println("Diagonal2 2("+pointsMoving.get(3).getX()+", "+pointsMoving.get(3).getY()+")");*/

            RaycastResult Diagonal1 = c.LineAndRay(new Ray2(points.get(i - 1), dir),
                    new Line2(pointsMoving.get(0), pointsMoving.get(2)));
            RaycastResult Diagonal2 = (c.LineAndRay(new Ray2(points.get(i - 1), dir),
                    new Line2(pointsMoving.get(1), pointsMoving.get(3))));
            // checks whether to add normal reactions or not;
            System.out.println(
                    "origin" + rm.getOrigin().getX() + ", " + rm.getOrigin().getY() + ")");

            if (Diagonal2.getHit()) {
                System.out.println("2nd got hit");
                contactPoints.add(Diagonal2.getPoint());
                System.out.println(
                        "contact point" + Diagonal2.getPoint().getX() + ", " + Diagonal2.getPoint()
                                .getY() + ")");
            }
            if (Diagonal1.getHit()) {
                contactPoints.add(Diagonal1.getPoint());
                System.out.println("1st got hit");
                System.out.println(
                        "contact point" + Diagonal1.getPoint().getX() + ", " + Diagonal1.getPoint()
                                .getY() + ")");
            }

            if (Diagonal1.getHit() || Diagonal2.getHit()) {
                C = new Vector2(dir);
                //System.out.println("direction of an impulse is "+dir.getX()+" "+dir.getY());
            }
        }
        //I is perpendicular to C
        I = new Vector2(EquationSolver.equation(C.x, C.y, 0));

        // get the magnitude of the force in the direction
        // Impulse transfer
        float magF = Multiply.dotProduct(I, rm.getForceAccum());
        // Creates a force applied later
        Vector2 R = new Vector2(I);
        // multiply it so it doesn't move in that direction
        R.mul(-magF / I.getMagnitude());

        // adds a reaction force creating moments
        float length = contactPoints.size();

        for (int i = 0; i < length; i++) {

            Vector2 Tocentre = new Vector2(rs.getOrigin());
            Tocentre.sub(contactPoints.get(i));
            if (Multiply.dotProduct(R, Tocentre) > 0) {
                // changes direction so it is away from the object
                R.mul(-1);
                I.mul(-1);
            }
            Vector2 Rtemp = new Vector2(R);
            Rtemp.mul(1 / length);
            rm.addForceAdditional(Rtemp, rs, contactPoints.get(i));
        }
        /*
        if (point1!=null){
            rm.addForceAdditional(R,rs,point1);
            Vector2 Tocentre=new Vector2(rs.getOrigin());
            Tocentre.sub(point1);
            //TODO look at scalar factors
            if (Multiply.dotProduct(R,Tocentre)<0){
                // changes direction so it is away from the object
                R.mul(-2);
                I.mul(-1);
            }}else   if        (point2!=null){
            // same operation for the 2nd point
            rm.addForceAdditional(R,rs,point2);
            Vector2 Tocentre=new Vector2(rs.getOrigin());
            Tocentre.sub(point2);
            if (Multiply.dotProduct(R,Tocentre)<0){
                R.mul(-2);
                I.mul(-1);}}*/

        // cancelling out the velocity in the direction into the object
        Vector2 V = new Vector2(I);
        float magVel = Multiply.dotProduct(I, rm.linearVel);
        V.mul(-magVel / I.getMagnitude() * (1f + Constants.COEFFICIENT_RESTITUTION_DEFAULT));
        // cancel out the velocity and adds that the body bounces
        rm.linearVel.add(V);
        I.normalise();
    }

    // resolving collisions for two movable bodies
    public void impulse(Rect r1, Rect r2) {
        // we get centres line in vectors
        Vector2 I = new Vector2(r1.getOrigin());
        I.sub(r2.getOrigin());
        Vector2 C = EquationSolver.equation(I.getX(), I.getY(), 0);
        float xa, ya, xb, yb, a1, a2, b1, b2, c1, c2, ca, cb;
        c1 = Constants.COEFFICIENT_RESTITUTION_DEFAULT * (
                Multiply.dotProduct(I, r1.linearVel) - Multiply.dotProduct(I, r2.linearVel));
        //TODO check whether it is zero division
        ca = Multiply.dotProduct(C, r1.linearVel);
        cb = Multiply.dotProduct(C, r2.linearVel);
        c1 -= I.x / C.x * (ca + cb);
        c1 = c1 * Constants.COEFFICIENT_RESTITUTION_DEFAULT;
        a1 = I.y - C.y / C.x * I.x;

        b1 = a1;
        // check whether mass is zero
        c2 = r1.getMass() * Multiply.dotProduct(C, r1.linearVel)
                + r2.getMass() * Multiply.dotProduct(C, r2.linearVel);
        c2 -= ca * r1.getMass() + cb * r2.getMass();
        a2 = r1.getMass() * (-C.y / C.x + C.y);
        b2 = r2.getMass() * (-C.y / C.x + C.y);
        Vector2 result = EquationSolver.simultaneous_equations(a1, b1, c1, a2, b2, c2);
        System.out.println("a1 is " + a1);
        System.out.println("a2 is " + a2);
        System.out.println("b1 is " + b1);
        System.out.println("b2 is " + b2);
        System.out.println("c1 is " + c1);
        System.out.println("c2 is " + c2);

        ya = result.x;
        System.out.println("ya :" + ya);
        yb = result.y;
        System.out.println("yb :" + yb);
        xa = (ca - C.y * ya) / C.x;
        xb = (cb - C.y * yb) / C.x;
        System.out.println(I.x + " " + I.y);

        r1.setLinearVel(new Vector2(xa, ya));
        r2.setLinearVel(new Vector2(xb, yb));
        System.out.println("there is a change in velocity " + r1.linearVel.x + " " + r1.linearVel.y
                + "  and position " + r1.getPosition().x + " " + r1.getPosition().y);

        System.out.println("position " + r2.getPosition().x + " " + r2.getPosition().y);

    }
}
