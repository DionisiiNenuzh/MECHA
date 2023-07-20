package Graphical;

import Calculate.Vector2;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KL implements KeyListener {

    //methods needed to capture
    private final boolean[] KeyPressedArr = new boolean[128];
    private String character;
    private char c;
    private int code;
    //Offload of the screen to be able to move around
    // and inspect the behaviour of objects
    private Vector2 moved = new Vector2(0, 0);

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        KeyPressedArr[e.getKeyCode()] = true;
        this.code = e.getKeyCode();
        //increases the move vector depending on the typed key
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.moved.add(new Vector2(0, -10));
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.moved.add(new Vector2(0, 10));
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.moved.add(new Vector2(10, 0));
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.moved.add(new Vector2(-10, 0));
        }
        // limits of the movement, so it cannot go any further
        if (this.moved.getX() > 1000) {
            this.moved = new Vector2(1000, this.moved.getY());
        } else if (this.moved.getX() < -2000) {
            this.moved = new Vector2(-2000, this.moved.getY());
        }
        if (this.moved.getY() > 1000) {
            this.moved = new Vector2(this.moved.getX(), 1000);
        } else if (this.moved.getY() < -2000) {
            this.moved = new Vector2(this.moved.getX(), -2000);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.moved = new Vector2(0, 0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        KeyPressedArr[e.getKeyCode()] = false;
    }

    public String getCharacter() {
        return this.character;
    }

    public Character getKey() {
        return this.c;
    }

    public void offCharacter(int i) {
        this.KeyPressedArr[i] = false;
    }

    public boolean IsKeyPressed(int keyCode) {
        return KeyPressedArr[keyCode];
    }

    public Vector2 getMoved() {
        return new Vector2(this.moved);
    }
}
