package Calculate.Force;

import Calculate.Vector2;

public class ReactionForce {

    public Vector2 Reaction, contactPoint;

    public ReactionForce(Vector2 Reaction, Vector2 contactPoint) {

        this.contactPoint = new Vector2(contactPoint);
        this.Reaction = new Vector2(Reaction);

    }
}
