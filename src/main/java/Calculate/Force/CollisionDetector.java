package Calculate.Force;

import Calculate.EquationSolver;
import Calculate.Line2;
import Calculate.Multiply;
import Calculate.Ray2;
import Calculate.RaycastResult;
import Calculate.Rotate;
import Calculate.Vector2;
import Graphical.Rect;
import java.awt.Color;
import java.util.ArrayList;

public class CollisionDetector {

    public static boolean pointOnLine(Vector2 point, Line2 line) {
        //calculates the gradient by finding difference in y and x between 2 points
        float dy = line.getEnd().getY() - line.getStart().getY();
        float dx = line.getEnd().getX() - line.getStart().getX();
        // checks whether its vertical, there could be an infinity error
        if (dx == 0f) {
            return point.getX() == line.getStart().getX();
        }
        // y = gradient*x + c

        float gradient = dy / dx;
        //calculates the y-intercept
        float c = line.getEnd().getY() - line.getEnd().getX() * gradient;
        return point.getY() == gradient * point.getX() + c;
    }

    public static boolean pointInRectangle(Vector2 point, Vector2 position, Vector2 size) {
        Rect rect = new Rect((int)position.getX(), (int)position.getY(), (int)size.getY(), (int)size.getX(), 10, Color.red, "hello");
        return pointInRectangle(point, rect);
    }

    public static boolean pointInRectangle(Vector2 point, Rect box) {
        // Translate it inside the box space
        Vector2 pointLocalInTHeBox = new Vector2(point);
        pointLocalInTHeBox = Rotate.rotation(-box.getRotation(), pointLocalInTHeBox,
                box.getOrigin());

        // min is right top point max is left bottom
        Vector2 min = new Vector2(box.getOrigin());
        min.sub(new Vector2(box.getWidth(), box.getHeight()).mulRet(0.5));
        Vector2 max = new Vector2(box.getOrigin());
        max.add(new Vector2(box.getWidth(), box.getHeight()).mulRet(0.5));

        // If rotated point is between two minimum and maximum then the point is inside

        return pointLocalInTHeBox.getX() <= max.getX() && min.getX() <= pointLocalInTHeBox.getX() &&
                pointLocalInTHeBox.getY() <= max.getY() &&
                min.getY() <= pointLocalInTHeBox.getY();
    }


    public static boolean lineAndBoxNoRotation(Line2 line, Rect box) {
        // check are the line ends inside the box, if they are we immediately report a collision
        if (pointInRectangle(line.getStart(), box) || pointInRectangle(line.getEnd(), box)) {
            return true;
        }
        Vector2 unitVector = new Vector2(line.getDirection());
        unitVector.normalise();
        //we make direction vector from the start of the line
        // the operation below makes the ray longer if it is not horizontal or vertical
        unitVector.setX((unitVector.getX() != 0) ? 1.0f / unitVector.getX() : 0f);
        unitVector.setY((unitVector.getY() != 0) ? 1.0f / unitVector.getY() : 0f);

        // min is right top point max is left bottom
        Vector2 min = box.getOrigin();
        min.sub(new Vector2(box.width, box.height).mulRet(0.5));
        Vector2 max = box.getOrigin();
        max.add(new Vector2(box.width, box.height).mulRet(0.5));
        // get the minimum and maximum points relative to the start of the line
        // then we multiply its coordinates to get intersections of the vectors
        min.sub(line.getStart());
        min.x = min.x * unitVector.x;
        min.y = min.x * unitVector.y;

        max.sub(line.getStart());
        max.x = max.x * unitVector.x;
        max.y = max.x * unitVector.y;

        //get the points where collision would have happened if the ray is shot from start

        float tmin = Math.max(Math.min(min.getX(), max.getX()), Math.min(min.getY(), max.getY()));
        float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));
        //checks whether it hits the ray
        if (tmax < 0 || tmin > tmax) {
            return false;
        }
        float t = (tmax < 0f) ? tmax : tmin;
        // if it hits, then checks whether this lenght is less than length of the line or not
        return t > 0 && t * t < line.getDirection().getMagnitude();
    }


    public static boolean lineAndRectangle(Line2 line, Rect box) {
        //rotate the line relative to the centre of origin
        float theta = -box.getRotation();
        Vector2 centre = box.getOrigin();
        Vector2 localStart = new Vector2(line.getStart());
        Vector2 localEnd = new Vector2(line.getEnd());

        //make a new line for the rotated points

        localEnd = Rotate.rotation(theta, localStart, centre);
        localStart = Rotate.rotation(theta, localEnd, centre);

        Line2 localLine = new Line2(localStart, localEnd);
        // the same process as for line and box
        return lineAndBoxNoRotation(localLine,
                new Rect((int) box.getPosition().x, (int) box.getPosition().y, box.getWidth(),
                        box.getHeight(), 1, Color.black, "hello"));
        //Ray vs. primitive tests
    }

    public boolean BoxAndBox(Rect b1, Rect b2) {

        Vector2 xAxis = new Vector2(1, 0);
        Vector2 xAxis1 = new Vector2(Rotate.rotation(b1.getRotation(), xAxis, new Vector2(0, 0)));
        Vector2 xAxis2 = new Vector2(Rotate.rotation(b2.getRotation(), xAxis, new Vector2(0, 0)));
        Vector2 yAxis = new Vector2(0, 1);
        Vector2 yAxis1 = new Vector2(Rotate.rotation(b1.getRotation(), yAxis, new Vector2(0, 0)));
        Vector2 yAxis2 = new Vector2(Rotate.rotation(b2.getRotation(), yAxis, new Vector2(0, 0)));
        //System.out.println(xAxis2.x+" "+ xAxis2.y);

        Vector2[] axesToTest = {xAxis1, yAxis1, xAxis2, yAxis2};
        for (int i = 0; i < axesToTest.length; i++) {
            if (!overlapOnAxis(b1, b2, axesToTest[i])) {
                return false;
            }
        }
        return true;

    }

    public boolean overlapOnAxis(Rect b1, Rect b2, Vector2 axis) {
        Vector2 rangeB1 = getInterval(b1, axis);

        Vector2 rangeB2 = getInterval(b2, axis);

        boolean result = (!(rangeB2.x < rangeB1.x) || !(rangeB2.x < rangeB1.y)) &&
                (!(rangeB1.x < rangeB2.y) || !(rangeB1.y < rangeB2.y));

        return result;
    }

    public Vector2 getInterval(Rect rect, Vector2 axis) {
        Vector2 result = new Vector2(0, 0);

        ArrayList<Vector2> vertices = rect.getPoints();

        result.x = Multiply.dotProduct(axis, vertices.get(0));

        result.y = result.x;
        for (int i = 1; i < 4; i++) {
            float value = Multiply.dotProduct(vertices.get(i), axis);

            if (value > result.x) {
                result.x = value;
            }
            if (value < result.y) {
                result.y = value;
            }
        }

        return result;

    }

    public boolean lineAndLine(Line2 l1, Line2 l2) {
        return false;

    }

    public boolean raycast(Ray2 ray, RaycastResult result, Rect box) {
        Vector2 xAxis = new Vector2(
                Rotate.rotation(box.getRotation(), new Vector2(1, 0), new Vector2(0, 0)));
        Vector2 yAxis = new Vector2(
                Rotate.rotation(box.getRotation(), new Vector2(0, 1), new Vector2(0, 0)));
        Vector2 Centre = new Vector2(box.origin);
        Centre.sub(ray.getOrigin());
        float DotYaxis = Multiply.dotProduct(yAxis, Centre);
        float DotXaxis = Multiply.dotProduct(xAxis, Centre);
        float hWidth = (float) box.getWidth() / 2;
        float hHeight = (float) box.getHeight() / 2;
        Vector2 tMin = null;
        return false;
    }

    public RaycastResult LineAndRay(Ray2 ray, Line2 line) {
        float a1, a2, b1, b2, c1, c2, x1, x2, y1, y2;
        Vector2 dir1 = new Vector2(ray.getDirection());
        a1 = -dir1.y;
        b1 = dir1.x;
        c1 = ray.getOrigin().y * dir1.x - ray.getOrigin().x * dir1.y;
        Vector2 dir2 = line.getDirection();
        a2 = -dir2.y;
        b2 = dir2.x;
        c2 = line.getStart().y * dir2.x - line.getStart().x * dir2.y;
        Vector2 intersection = EquationSolver.simultaneous_equations(a1, b1, c1, a2, b2, c2);
        // check whether it is NAN or not
        RaycastResult result = new RaycastResult();
        if (line.getStart().x < line.getEnd().x) {
            x1 = line.getStart().x;
            x2 = line.getEnd().x;
        } else {
            x2 = line.getStart().x;
            x1 = line.getEnd().x;
        }

        if (line.getStart().y < line.getEnd().y) {
            y1 = line.getStart().y;
            y2 = line.getEnd().y;
        } else {
            y2 = line.getStart().y;
            y1 = line.getEnd().y;
        }

        boolean hit =
                (x1 - 0.01f <= intersection.x) && (intersection.x <= x2 + 0.01f) && (y1 - 0.01f
                        <= intersection.y) && (intersection.y <= y2 + 0.01f);
        Vector2 ToHit = new Vector2(intersection);
        ToHit.sub(ray.getOrigin());
        float dot = Multiply.dotProduct(ToHit, ray.getDirection());
        boolean fin = (hit) && (dot >= 0f);
        result.init(new Vector2(intersection), new Vector2(0, 0), ToHit.getMagnitudeSquared(), fin);
        return result;

    }
}








