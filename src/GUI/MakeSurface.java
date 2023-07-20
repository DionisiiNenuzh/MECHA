package GUI;

import Graphical.Rect;
import Graphical.objectsRegister;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MakeSurface extends JFrame {

    public objectsRegister objects;
    public String nameOfObject;
    public Button Trigger = null;
    private JTextField NameField;
    private JTextField Xfield;
    private JTextField Yfield;
    private JTextField RotationField;
    private JTextField LengthField;
    private JButton createASurfaceButton;
    private JPanel Details;
    private JLabel Description;
    // details for the new surface
    private int Length, Xpos, Ypos;
    private float Angle;
    private boolean visible = false;

    public MakeSurface(objectsRegister obj, Button Trig) {
        this.objects = obj;
        this.Trigger = Trig;

        createASurfaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ValidationFull()) {
                    makeAnObject();
                    setCreated();
                }
            }
        });

        Description.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(null, "Put details in to the fields on the left\n" +
                        "In the name section put a name for the object you want to create limit 15 characters\n"
                        +
                        "For X, Y, t put integer numbers between -2000 and 2000\n" +
                        "For rotation the angle is between positive direction of x axis, clockwise the angle is from 0 to 360\n"
                        +
                        "For Length 800 is recommended");
            }
        });
    }

    public void makeAnObject() {

        System.out.println("surface added");
        Rect rect = new Rect(this.Xpos, this.Ypos, 10, this.Length, 0, Color.blue,
                this.nameOfObject);
        rect.setRotationCurrent(this.Angle / 180 * (float) Math.PI);
        this.objects.add(rect);

    }

    public boolean ValidationFull() {
        // gets the name of the existing object
        this.nameOfObject = this.NameField.getText();
        //checks every field
        return this.traverseObjects() && this.integerXCoordinateField()
                && this.integerYCoordinateField()
                && this.floatAngleField() && this.integerLengthField();
    }

    public boolean integerXCoordinateField() {
        // this validation is for float objects with continuous scope
        boolean result = true;
        boolean invalid = false;
        try {
            // try structure prevents the program from
            // crashing if the string cannot be converted
            // to the integer
            this.Xpos = Integer.parseInt(this.Xfield.getText());
            if (-2000 > this.Xpos || this.Xpos > 2000) {
                result = false;
            }
            // if the string cannot be parsed to Integer
            // this line will be executed
        } catch (Exception e) {
            result = false;
            invalid = true;
            JOptionPane.showMessageDialog(null,
                    "Invalid type for X field, type needed integer numbers");
        }
        if (!result && !invalid) {
            JOptionPane.showMessageDialog(null,
                    "X field contains only integer numbers between -2000 and 2000");
        }
        return result;
    }

    public boolean integerYCoordinateField() {
        // this validation is for integer objects with continuous scope
        boolean result = true;
        boolean invalid = false;
        try {// try structure prevents the program from
            // crashing if the string cannot be converted
            // to the integer
            this.Ypos = Integer.parseInt(this.Yfield.getText());
            if (-2000 > this.Ypos || this.Ypos > 2000) {
                result = false;
            }
        } catch (Exception e) {
            result = false;
            invalid = true;
            JOptionPane.showMessageDialog(null,
                    "Invalid type for Y field, type needed integer numbers");
        }
        if (!result && !invalid) {
            JOptionPane.showMessageDialog(null,
                    "Y field contains only integer numbers between -200 and 2000");
        }
        return result;
    }

    public boolean floatAngleField() {
        // this validation is for float objects in some range
        boolean result = true;
        boolean invalid = false;
        try {
            // if this line fails the input is not valid
            this.Angle = Float.parseFloat(this.RotationField.getText());
            if (0 > this.Angle || this.Angle > 360) {
                result = false;
            }
        } catch (Exception e) {
            result = false;
            invalid = true;
            JOptionPane.showMessageDialog(null,
                    "Invalid type for Rotation field, type needed numbers");
        }
        if (!result && !invalid) {
            JOptionPane.showMessageDialog(null,
                    "Rotation field contains only numbers between 0 and 1000");
        }
        return result;
    }

    public boolean integerLengthField() {
        // this validation is for float objects with continuous scope
        boolean result = true;
        boolean invalid = false;
        try {
            // try structure prevents the program from
            // crashing if the string cannot be converted
            // to the integer
            this.Length = Integer.parseInt(this.LengthField.getText());
            if (0 > this.Length || this.Length > 2000) {
                result = false;
            }
            // if the string cannot be parsed to Integer
            // this line will be executed
        } catch (Exception e) {
            result = false;
            invalid = true;
            JOptionPane.showMessageDialog(null,
                    "Invalid type for Length field, type needed integer numbers");
        }
        if (!result && !invalid) {
            JOptionPane.showMessageDialog(null,
                    "Length field contains only integer numbers between -200 and 2000");
        }
        return result;
    }

    // method that checks is the name for new object
    // is taken or not
    public boolean traverseObjects() {
        boolean unique = true;
        // TODO only spaces check
        this.nameOfObject = this.NameField.getText();

        if (this.nameOfObject.length() > 15) {
            // length check and appropriate message
            JOptionPane.showMessageDialog(null, "Too long. Limit is 15 characters");
        } else if (this.nameOfObject != null) {
            // checks whether it is unique or not and if not shows appropriate message
            for (int i = 0; i < this.objects.getSize(); i++) {
                if (this.nameOfObject.equals(this.objects.getItem(i).getId())) {
                    JOptionPane.showMessageDialog(null, "This name already exists");
                    unique = false;
                }
            }
        }
        // checks if there is no input
        if (this.nameOfObject.equals("") || this.nameOfObject == null) {
            unique = false;
            JOptionPane.showMessageDialog(null, "No name input");
        }
        return unique;
    }

    public void setCreated() {
        System.out.println("name of object is " + this.Trigger.name);
        this.Trigger.response.activate();
        //this.setVisible(false);
    }

    public void update(TimeDisplay time) {
        if (time.getRestarted()) {
            if (this.visible != this.Trigger.response.Active) {
                this.visible = this.Trigger.response.Active;
                this.setVisible(this.visible);
            }
        } else if (this.Trigger.response.getActive()) {
            JOptionPane.showMessageDialog(null, "Click the restart button first");
            this.Trigger.response.activate();
        }
    }

    public void initComponents() {
        this.setContentPane(new MakeSurface(this.objects, this.Trigger).Details);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();
    }
}
