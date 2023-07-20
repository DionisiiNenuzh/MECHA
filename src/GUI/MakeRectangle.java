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

// make rectangle extends as JFrame, so that it can appear as
// a separate window
public class MakeRectangle extends JFrame {

    public JPanel Details;
    // object queue to add new rectangles
    public objectsRegister objects;
    // details about a created object
    public String nameOfObject;
    public boolean visible = false;
    // a button which shows the window
    public Button Trigger = null;
    private JTextField Xposition;
    private JTextField Yposition;
    private JTextField Mass;
    private JButton createObjectButton;
    private JTextField NameField;
    // labels that show which field is which
    private JLabel XLabel;
    private JLabel Ylabel;
    private JLabel MassLabel;
    private JLabel Name;
    private JTextField HeightField;
    private JTextField WidthField;
    private JLabel Description;
    // numbers needed for details
    private int Xpos, Ypos, Width, Height;
    private float MassValue;

    public MakeRectangle(objectsRegister obj, Button Trig) {
        // assigns all attributes
        this.objects = obj;
        this.Trigger = Trig;
        // once create object button is pressed
        // it checks whether all details are completed and
        // then creates an objects with these details
        this.createObjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ValidationFull()) {
                    makeAnObject();
                    setCreated();
                }
            }
        });
        this.Description.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(null, "Put details in to the fields on the left\n" +
                        "In the name section put a name for the object you want to create limit 15 characters\n"
                        +
                        "For X, Y, t put integer numbers between -2000 and 2000\n" +
                        "For mass put any positive number. No units here only numeric input\n" +
                        "For width and height put only positive integers recommended 10 and 10");
            }
        });
    }

    public boolean ValidationFull() {
        // gets the name of the existing object
        this.nameOfObject = this.NameField.getText();
        //checks every field
        return this.traverseObjects() && this.integerXCoordinateField()
                && this.integerYCoordinateField() && this.floatMassField()
                && this.integerHeightField() && this.integerWidthField();
    }

    public boolean integerXCoordinateField() {
        // this validation is for float objects with continuous scope
        boolean result = true;
        boolean invalid = false;
        try {
            // try structure prevents the program from
            // crashing if the string cannot be converted
            // to the integer
            this.Xpos = Integer.parseInt(this.Xposition.getText());
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
            this.Ypos = Integer.parseInt(this.Yposition.getText());
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
                    "Y field contains only integer numbers between -2000 and 2000");
        }
        return result;
    }

    public boolean floatMassField() {
        // this validation is for float objects in some range
        boolean result = true;
        boolean invalid = false;
        try {// if this line fails the input is not valid
            this.MassValue = Float.parseFloat(this.Mass.getText());
            if (0 > this.MassValue || this.MassValue > 1000) {
                result = false;
            }
        } catch (Exception e) {
            result = false;
            invalid = true;
            JOptionPane.showMessageDialog(null, "Invalid type for Mass field, type needed numbers");
        }
        if (!result && !invalid) {
            JOptionPane.showMessageDialog(null,
                    "Mass field contains only numbers between 0 and 1000");
        }
        return result;
    }

    public boolean integerWidthField() {
        // this validation is for float objects in some range
        boolean result = true;
        boolean invalid = false;
        try {// if this line fails the input is not valid
            this.Width = Integer.parseInt(this.WidthField.getText());
            if (0 > this.Width || this.Width > 200) {
                result = false;
            }
        } catch (Exception e) {
            result = false;
            invalid = true;
            JOptionPane.showMessageDialog(null,
                    "Invalid type for Width field, type needed numbers");
        }
        if (!result && !invalid) {
            JOptionPane.showMessageDialog(null,
                    "Width field contains only numbers between 0 and 200");
        }
        return result;
    }

    public boolean integerHeightField() {
        // this validation is for float objects in some range
        boolean result = true;
        boolean invalid = false;
        try {// if this line fails the input is not valid
            this.Height = Integer.parseInt(this.HeightField.getText());
            if (0 > this.Height || this.Height > 200) {
                result = false;
            }
        } catch (Exception e) {
            result = false;
            invalid = true;
            JOptionPane.showMessageDialog(null,
                    "Invalid type for Height field, type needed numbers");
        }
        if (!result && !invalid) {
            JOptionPane.showMessageDialog(null,
                    "Height field contains only numbers between 0 and 200");
        }
        return result;
    }

    // method that checks is the name for new object
    // is taken or not
    public boolean traverseObjects() {
        boolean unique = true;
        // TODO only spaces check

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
        // once the object is created sets
        // button trigger inactive
        this.Trigger.response.activate();
    }

    // adds a button which shows it
    public void setButton(Button button) {
        System.out.println("the new button is set");
        this.Trigger = button;
    }

    // creates an object with all details
    public void makeAnObject() {
        this.objects.add(new Rect(this.Xpos, this.Ypos,
                this.Height, this.Width, this.MassValue,
                Color.blue, this.nameOfObject));
    }

    // checks whether the window should be shown or not
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
        //adds all labels and input fields to the window
        this.setContentPane(new MakeRectangle(this.objects, this.Trigger).Details);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();
        //this puts default size into the field
        this.HeightField.setText("15");
        this.WidthField.setText("15");
    }

    public void createUIComponents() {
    }
}
