package GUI;

import Graphical.ObjectsRegister;
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

public class MakeVelocity extends JFrame {

    public ObjectsRegister objects;
    public String nameOfObject;
    public Button Trigger = null;
    private JPanel Details;
    private JButton CreateVelocity;
    private JTextField NameOfObject;
    private JTextField MagnitudeOfVelocity;
    private JTextField AngleToRotation;
    private JLabel Description;
    private float Angle, Magnitude;
    private boolean visible = false;

    public MakeVelocity(ObjectsRegister obj, Button Trig) {
        this.objects = obj;
        this.Trigger = Trig;
        CreateVelocity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ValidationFull()) {
                    makeAVelocity();
                    setCreated();
                }
            }
        });

        Description.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(null, "Put details in to the fields on the left\n" +
                        "In the name section put a name for the object that is already created\n" +
                        "For magnitude put any positive number between 0 and 500. No units here only numeric input\n"
                        +
                        "For rotation the angle is between positive direction of x axis, clockwise the angle is from 0 to 360");
            }
        });
    }

    public boolean traverseObjects() {
        boolean found = false;
        boolean isShort = true;
        if (this.nameOfObject.length() > 15) {
            isShort = false;
            JOptionPane.showMessageDialog(null, "Too long. Limit is 15 characters");
        } else if (this.nameOfObject != null) {
            for (int i = 0; i < this.objects.getSize(); i++) {
                if (this.nameOfObject.equals(this.objects.getItem(i).getId())) {
                    found = true;
                }
            }
        } else {
            found = false;
        }
        if (!found && isShort) {
            JOptionPane.showMessageDialog(null, "This name does not exist");
        }
        return found;
    }

    public void setButton(Button b) {
        this.Trigger = b;

    }

    public void makeAVelocity() {
        System.out.println("this Velocity has been created for" + this.nameOfObject);
        this.objects.addVelocity(new float[]{this.Magnitude, this.Angle}, this.nameOfObject);
    }

    public void initComponents() {
        this.setContentPane(new MakeVelocity(this.objects, this.Trigger).Details);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();

    }

    public void setCreated() {
        this.Trigger.response.activate();
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

    public boolean ValidationFull() {
        // gets the name of the existing object
        this.nameOfObject = this.NameOfObject.getText();
        //checks every field
        return this.traverseObjects() && this.floatMagnitudeField() && this.floatAngleField();
    }

    public boolean floatAngleField() {
        // this validation is for float objects with continuous scope
        boolean result = true;
        try {
            this.Angle = Float.parseFloat(this.AngleToRotation.getText());
            if (0 > this.Angle || this.Angle > 360) {
                result = false;
            }
        } catch (Exception e) {
            result = false;
        }
        if (!result) {
            JOptionPane.showMessageDialog(null,
                    "Angle field contains only numbers between 0 and 360");
        }
        return result;
    }

    public boolean floatMagnitudeField() {
        // this validation is for float objects with continuous scope
        boolean result = true;
        try {
            this.Magnitude = Float.parseFloat(this.MagnitudeOfVelocity.getText());
            if (0 > this.Magnitude || this.Magnitude > 500) {
                result = false;
            }
        } catch (Exception e) {
            result = false;
        }
        if (!result) {
            JOptionPane.showMessageDialog(null,
                    "Magnitude field contains only numbers between 0 and 500");
        }
        return result;
    }

}
