package Graphical;

import Calculate.EquationSolver;
import Calculate.Force.ReactionForce;
import Calculate.Matrix2;
import Calculate.Multiply;
import Calculate.Rotate;
import Calculate.Vector2;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Rect {

    //attributes that are needed for the object
    public final double FORCE_ZERO = 0.01;
    public float mass, inversemass;
    public float height, width;
    public Vector2 position, origin;
    // physical quantities
    public Vector2 ForceAccum;
    public Vector2 forceDisplay = new Vector2(0, 0);
    public Vector2 linearVel;
    public boolean collided;
    public ArrayList<Vector2> reactions;
    public ArrayList<ReactionForce> ReactionForces;
    private Color color;
    private float rotationCurrent, rotationPrevious;
    private Matrix2 rotMat;
    private String id;
    private float angularVel, angularDamping;
    private Vector2 linearDamping;
    private final ArrayList<Vector2> points = new ArrayList<Vector2>();


    // constructor
    public Rect(int xc, int yc, int h, int w, float m, Color c, String name) {
        //assigns all attributes this way uses less computing power each time the new Rectangle is created

        this.ForceAccum = new Vector2(0, 0);
        this.linearVel = new Vector2(0, 0);
        this.angularVel = 0f;
        this.collided = false;
        this.linearDamping = new Vector2(0, 0);
        this.reactions = new ArrayList<Vector2>();

        this.angularDamping = 1f;
        this.height = h;
        this.width = w;
        this.mass = m;
        this.id = name;
        // mass is 0 we use it for static objects
        // also use for validation of to avoid infinity error
        inversemass = (mass == 0.0f)? 0: 1 / mass;
//        if (mass == 0.0f) {
//            this.inversemass = 0;
//        } else {
//            this.inversemass = 1 / this.mass;
//        }
        this.color = c;
        // makes the initial position of the object which depends on parameters passed

        this.position = new Vector2(xc, yc);
        this.origin = new Vector2(xc, yc);
        this.origin.add(new Vector2(width, height).mulRet(0.5));
        this.rotationCurrent = 0f;
        this.rotationPrevious = 0f;
        this.ReactionForces = new ArrayList<ReactionForce>();
        this.rotMat = new Matrix2(Math.cos(rotationCurrent), -Math.sin(rotationCurrent),
            Math.sin(rotationCurrent), Math.cos(rotationCurrent));

        //order of points 0,1,2,3 in left top, right top, right bottom, left bottom
        this.points.add(position);
        this.points.add(new Vector2(position.getX() + width, position.getY()));
        this.points.add(new Vector2(position.getX() + width, position.getY() + height));
        this.points.add(new Vector2(position.getX(), position.getY() + height));

        }

    public Rect(Rect r) {
        this((int) r.getPosition().x, (int) r.getPosition().getY(), r.getHeight(),
                r.getHeight(), r.getMass(), r.getColor(), r.getId());
    }

    public void draw(Graphics2D g2, Vector2 move) {
        g2.setColor(color);
        //  draws a line from 0 to 1 , 1 to 2, 2 to 3, 3 to 0
        // makes the drawing line thicker
        g2.setStroke(new BasicStroke(5));
        // the coordinates are shifted because of toolbar on the left
        // move the forces by offload
        for (int i = 0; i < 4; i++) {
            Vector2 newPoint = new Vector2(this.points.get(i));
            newPoint.sub(move);
            this.points.set(i, newPoint);
        }
        for (int i = 1; i < 4; i += 1) {
            g2.draw(new Line2D.Float(points.get(i - 1).getX() + Constants.SCREEN_OFFSET,
                    points.get(i - 1).getY(),
                    points.get(i).getX() + Constants.SCREEN_OFFSET, points.get(i).getY()));
        }
        // the last line added
        g2.draw(new Line2D.Float(points.get(0).getX() + Constants.SCREEN_OFFSET,
                points.get(0).getY(),
                points.get(3).getX() + Constants.SCREEN_OFFSET, points.get(3).getY()));
        // draws contact points for better understanding
        if (this.ReactionForces.size() > 0) {
            for (int i = 0; i < this.ReactionForces.size(); i++) {
                // gets x and y coordinates from the contact point
                float xPos =
                        this.ReactionForces.get(i).contactPoint.getX() + Constants.SCREEN_OFFSET
                                - move.getX();
                float yPos = this.ReactionForces.get(i).contactPoint.getY() - move.getY();
                // sets so it can be clearly visible
                g2.setStroke(new BasicStroke(5));
                g2.setColor(Color.red);
                // draws a line containing one point
                g2.draw(new Line2D.Float(xPos, yPos, xPos, yPos));
            }
        }
        // return them back for calculations
        for (int i = 0; i < 4; i++) {
            Vector2 newPoint = new Vector2(this.points.get(i));
            newPoint.add(move);
            this.points.set(i, newPoint);
        }
        // clear reactionForces so it can be added on the next frame
        this.ReactionForces.clear();
    }

    public void updatePoints() {
        this.points.clear();
        float halfHeight = this.height / 2;
        float halfWidth = this.width / 2;
        // gets points depending on position from the centre

        this.points.add(
                new Vector2(this.origin.getX() - halfWidth, this.origin.getY() - halfHeight));
        this.points.add(
                new Vector2(this.origin.getX() + halfWidth, this.origin.getY() - halfHeight));
        this.points.add(
                new Vector2(this.origin.getX() + halfWidth, this.origin.getY() + halfHeight));
        this.points.add(
                new Vector2(this.origin.getX() - halfWidth, this.origin.getY() + halfHeight));
        for (int i = 0; i < 4; i++) {
            this.points.set(i, Rotate.rotation(this.rotationCurrent, this.points.get(i),
                    this.origin));
        }
    }

    public void rotcheck(double f) {
        // checks whether the rotation is sufficient
        if (f > 1E-6) {

            for (int i = 0; i < 4; i += 1) {
                updatePoints();
            }
            this.rotationPrevious = this.rotationCurrent;
        }

    }

    public void rotate() {
        // runs rotation procedure for all 4 points around origin
        if (this.rotationCurrent > 7) {
            this.rotationCurrent -= 2 * Math.PI;
            this.rotationPrevious -= 2 * Math.PI;
        } else if (this.rotationCurrent < -7) {
            this.rotationCurrent += 2 * Math.PI;
            this.rotationPrevious += 2 * Math.PI;
        }
        for (int i = 0; i < 4; i++) {
            this.points.set(i, Rotate.rotation(this.rotationCurrent - this.rotationPrevious,
                    this.points.get(i), this.origin));
        }
        this.rotationPrevious = this.rotationCurrent;
    }

    public void updateForce(double dt) {
        //Calculate linear velocity
        //Sorts out whether the object should be rotated or not

        this.Moments(dt);
        for (int i = 0; i < this.reactions.size(); i++) {
            this.ForceAccum.add(reactions.get(i));
        }

        this.forceDisplay = new Vector2(this.ForceAccum);
        //System.out.println(" this happens "+this.forceDisplay.getY());
        Vector2 a = new Vector2(this.ForceAccum);
        //System.out.println(" this happens accel"+a.getY());
        a.mul(this.inversemass);
        if ((-FORCE_ZERO < a.x && a.x < FORCE_ZERO) && (-FORCE_ZERO < a.y && a.y < FORCE_ZERO)) {
            a.zero();
        }
        // increases the linear velocity by the direction of resultant force
        this.linearVel.add(a.mulRet(dt));
        // increases it to the side where the velocity is increased
        this.origin.add(new Vector2(this.linearVel.mulRet(dt)));
        // updates the points relative to its position and rotation
        updatePoints();
        // resets all quantities for the next frame
        zeroAccum();

        //this.ReactionForces.clear();
        this.reactions.clear();
    }

    // adds reaction force to the array
    public void addForceAdditional(Vector2 reaction, Rect box, Vector2 point) {
        this.reactions.add(new Vector2(reaction));
        Vector2 pointContacting;
        this.ReactionForces.add(new ReactionForce(new Vector2(reaction), point));
    }

    // determines the change in rotation depending on the reaction and their point of contact
    public void Moments(double dt) {
        // checks whether it is not empty
        if (this.ReactionForces.size() > 0) {
            // moment is calculated for each reaction force
            // Total stores the sum of them
            // distance is vector from origin to the point of contact
            float moment;
            float Total = 0;
            Vector2 distance;
            EquationSolver e = new EquationSolver();

            for (int i = 0; i < this.ReactionForces.size(); i++) {
                distance = new Vector2(this.ReactionForces.get(i).contactPoint);
                distance.sub(this.origin);
                // the magnitude of the moment
                Vector2 normal = EquationSolver.equation(distance.x, distance.y, 0);
                normal.normalise();
                float momentValue = Multiply.dotProduct(normal,
                        this.ReactionForces.get(i).Reaction);
                moment = Math.abs(momentValue * distance.getMagnitude());

                // determine the clockwise or anticlockwise
                if (distance.getX() > 0) {
                    if (momentValue * normal.getY() <= 0) {
                        moment = -moment;
                    }
                } else if (distance.getX() < 0) {
                    if (momentValue * normal.getY() >= 0) {
                        moment = -moment;
                    }
                } else {
                    if (momentValue * normal.getX() >= 0) {
                        moment = -moment;
                    }
                }
                //System.out.println("moment is "+moment+" "+i);
                Total += moment;
            }
            // process of getting final rotation
            float a = Total;

            float Inertia = (this.width * this.width + this.height * this.height) * 12;
            // multiply by damping factor as we count number of pixels
            a = a * this.inversemass / Inertia * 10000;
            //System.out.println("angular "+a);
            if (-0.3 < a && a < 0.3) {
                a = 0f;
            }
            this.angularVel = a * (float) dt;
            this.rotationCurrent += this.angularDamping * this.angularVel * dt;
        }
    }

    public void addForce(Vector2 force) {
        this.ForceAccum.add(force);
    }

    public void zeroAccum() {
        this.ForceAccum = new Vector2(0f, 0f);
    }

    public void update(double dt) {
        this.updateForce(dt);
        this.rotcheck(dt);
    }

    // getters and setters for all quantities
    public ArrayList<Vector2> getPoints() {
        return this.points;
    }

    public Vector2 getLinearVel() {
        return new Vector2(this.linearVel);
    }

    public void setLinearVel(Vector2 v) {
        if (this.mass != 0) {
            this.linearVel = v;
        }
    }

    public void setRotationCurrent(float rotationCurrent) {
        this.rotationCurrent = rotationCurrent;
    }

    public int getHeight() {
        return (int) this.height;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 val) {
        this.position = new Vector2(val);
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public float getRotation() {
        return rotationCurrent;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public void setSize(Vector2 vec) {
        this.width = (int) vec.getX();
        this.height = (int) vec.getY();
    }

    public float getInversemass() {
        return inversemass;
    }

    public void setInversemass(float inversemass) {
        this.inversemass = inversemass;
    }

    public String getId() {
        return this.id;
    }

    public Vector2 getForceAccum() {
        return new Vector2(this.ForceAccum);
    }

    public Vector2 getForceDisplay() {
        return new Vector2(this.forceDisplay);
    }

    public int getWidth() {
        return (int) this.width;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
