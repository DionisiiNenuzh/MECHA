package GUI;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputForm extends Frame {

    public JPanel Details;
    private JTextField Xposition;
    private JTextField Yposition;
    private JTextField Mass;
    private JButton createObjectButton;
    private final boolean[] pressed = {false, false, false};
    private final int[] rectangles = new int[3];

    public InputForm() {
        //this.pack();

        //this.setContentPane(new InputForm().Details);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.Xposition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // make the exception
                pressed[0] = true;
                rectangles[0] = Integer.parseInt(Xposition.getText());
                System.out.println("x position in");
            }
        });
        this.Yposition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // make the exception
                pressed[1] = true;
                rectangles[1] = Integer.parseInt(Yposition.getText());
                System.out.println("y position in");
            }
        });
        this.Mass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // make the exception
                pressed[2] = true;
                rectangles[2] = Integer.parseInt(Mass.getText());
                System.out.println(getDetails()[0] + "" + getDetails()[1] + " " + getDetails()[2]);
            }
        });
    }

    public int[] getDetails() {
        return this.rectangles.clone();
    }

    public boolean[] getPressed() {
        return this.pressed.clone();
    }

    public void setFalse(int i) {
        this.pressed[i] = false;
    }
    /*public static void main (String args[]){
        //this.
        JFrame win = new InputWindow();
        win.setContentPane(new InputForm().Details);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.pack();
        win.setVisible(true);

        while(true) {
            for (int i = 0; i < 3; i++) {
                if (win.getPressed()[i]){
                    win.getDetails

                }
            }
        }
        */

}
