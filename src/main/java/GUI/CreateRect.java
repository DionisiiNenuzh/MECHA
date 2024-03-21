package GUI;

import Calculate.Vector2;
import Graphical.GraphicsEngine;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class CreateRect extends Button {

    //InputPanel panel = new InputPanel((new Vector2(400,300)));
    //InputField positionX = new InputField(new Vector2(400,300),new Vector2(250,50),"x-value",new ButtonResponse(),4);
    public CreateRect(Vector2 position, Vector2 size, String name, ButtonResponse response) {
        super(position, size, name, response);
        //this.response.panel = new InputPanel((new Vector2(400,300)));
        //this.response.panel.add(new InputField(new Vector2(400,300),new Vector2(250,50),"x-value",new ButtonResponse(),4));
    }

    public void setInputWindow(MakeRectangle win) {
        if (win != null && this.response.Active) {
            win.setVisible(true);
        }

    }

    @Override
    public void draw(GraphicsEngine ge) {
        ge.setColor(Color.pink);

        ge.drawRectangle(this.position,this.size, true);
        ge.setColor(Color.darkGray);
        ge.drawText(this.name, new Vector2(this.position.getX() + 20, this.position.getY() + 20));
        if (this.highlighted) {
            this.highlight(ge, Color.yellow);
        }
        if (this.response.getActive()) {
            //System.out.println("draws");
            this.response.panel.draw(ge);

            //System.out.println(this.response.panel.position.x+" "+this.response.panel.position.y);
        }
        if (this.response.getActive()) {
            //this.positionX.draw(g2);
        }
    }

//    public void draw(Graphics2D g2) {
//        g2.setColor(Color.pink);
//        g2.setStroke(new BasicStroke(5));
//
//        g2.fillRect((int) this.position.getX(), (int) this.position.getY(), (int) this.size.getX(),
//            (int) this.size.getY());
//        g2.setColor(Color.darkGray);
//        g2.drawString(this.name, (int) this.position.getX() + 20, (int) this.position.getY() + 20);
//        if (this.highlighted) {
//            this.highlight(g2, Color.yellow);
//        }
//        if (this.response.getActive()) {
//            //System.out.println("draws");
//            this.response.panel.draw(g2);
//
//            //System.out.println(this.response.panel.position.x+" "+this.response.panel.position.y);
//        }
//        if (this.response.getActive()) {
//            //this.positionX.draw(g2);
//        }
//    }


}
