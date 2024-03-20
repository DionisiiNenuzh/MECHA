package GUI;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class InputWindow extends JFrame {

    Container container;
    JTextField InputX, InputY, InputMass, InputSize;
    JPanel panel;
    JLabel X, Y, M, Size;

    public InputWindow() {
        this.setSize(300, 200);
        this.setLocation(600, 500);
        this.setTitle("input window");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setResizable(false);
        this.initComponents();
        //this.setLayout(new SpringLayout());
    }

    public void initComponents() {
        this.X = new JLabel("X position");
        this.InputX = new JTextField(10);
        this.InputX.setToolTipText("Enter X location");
        this.Y = new JLabel("Y position");
        this.InputY = new JTextField(10);
        this.InputY.setToolTipText("Enter Y location");
        this.M = new JLabel("Mass of the object");
        this.InputMass = new JTextField("Mass");
        Container pane = this.getContentPane();
        SpringLayout layout = new SpringLayout();
        layout.putConstraint(SpringLayout.WEST, this.X,
                5,
                SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, this.Y,
                5,
                SpringLayout.NORTH, pane);
        pane.setLayout(layout);
        pane.add(this.X);
        pane.add(this.InputX);
        pane.add(this.Y);
        pane.add(this.InputY);
        pane.add(this.M);
        pane.add(this.InputMass);
        this.pack();

    }

}
