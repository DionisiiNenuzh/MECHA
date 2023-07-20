package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu implements ActionListener {

    int count = 0;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel label = new JLabel("number of clicks:)");

    public Menu() {

        JButton button = new JButton("hello");

        button.addActionListener(this);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        panel.add(button);
        panel.add(label);
        frame.add(panel, BorderLayout.CENTER);
        frame.setTitle("hello");
        frame.pack();
        frame.setVisible(true);

        //button.setVisible(true);
    }

    public static void main(String[] args) {
        new Menu();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        label.setText("number of clicks:" + count);

    }
}
