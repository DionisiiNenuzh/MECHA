package Graphical;

import Calculate.Vector2;
import GUI.Button;
import GUI.ButtonResponse;
import GUI.CreateButton;
import GUI.MakeForce;
import GUI.MakeRectangle;
import GUI.MakeSurface;
import GUI.MakeVelocity;
import GUI.ObjectsDisplay;
import GUI.OutputResponse;
import GUI.OutputWin;
import GUI.Panel;
import GUI.RestartButton;
import GUI.TimeDisplay;
import fileSaver.FileSaver;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;

public class Window extends JFrame implements Runnable {

    // class that uses graphics
    Graphics2D g2;
    // objects to track keys pressed and mouse buttons
    KL keyListener = new KL();
    ML ml = new ML();
    MouseWheel scroll = new MouseWheel();
    // the container of all objects
    ObjectsRegister objects;
    // buttons on the panel
    Button play, create, restart;
    Vector2 Pointer;
    // container of the buttons
    Panel menu;
    CoordinateAxes axes = new CoordinateAxes();
    // window that displays characteristics
    OutputWin Out;
    ObjectsDisplay Display;
    TimeDisplay timer = new TimeDisplay();
    // input windows responsible for adding new objects
    MakeRectangle Inputwindow = null;
    MakeForce InputForForce = null;
    MakeVelocity InputForVelocity = null;
    MakeSurface InputSurface = null;

    public Window() {
        // initialisation of the window
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setTitle(Constants.SCREEN_TITLE);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("MECHAICON.png"));
        this.addKeyListener(this.keyListener);
        this.addMouseWheelListener(this.scroll);
        g2 = (Graphics2D) this.getGraphics();
        // sets up the object display
        this.Display = new ObjectsDisplay();
        this.objects = new ObjectsRegister(this.Display);
        this.objects.add(new Rect(0, 0, 20, 20, 20, Color.yellow, "hello"));
        // creation of buttons characteristics
        this.play = new Button(new Vector2(0, 0), new Vector2(147, 25), "play",
                new ButtonResponse());
        this.create = new Button(new Vector2(0, 0), new Vector2(147, 25), "Create",
                new CreateButton(new Vector2(0, 0),
                        new Vector2(0, 0)));
        this.restart = new Button(new Button(new Vector2(0, 0), new Vector2(147, 25), "Restart",
                new RestartButton(this.timer, this.play, this.objects)));
        // put the buttons into panel
        this.menu = new Panel(new Vector2(0, 30));
        this.menu.add(this.play);
        this.menu.add(this.create);
        this.menu.add(this.restart);

        // initialisation of the window
        this.Out = new OutputWin(new Vector2(Constants.SCREEN_WIDTH - 300, 35),
                new Vector2(300, 200), "Output Window", new OutputResponse());

        // set input windows buttons after which they respond
        this.Inputwindow = new MakeRectangle(this.objects,
                this.create.response.panel.buttons.get(0));
        this.Inputwindow.initComponents();
        this.InputSurface = new MakeSurface(this.objects,
                this.create.response.panel.buttons.get(1));
        this.InputSurface.initComponents();
        this.InputForForce = new MakeForce(this.objects, this.create.response.panel.buttons.get(2));
        this.InputForForce.initComponents();
        this.InputForVelocity = new MakeVelocity(this.objects,
                this.create.response.panel.buttons.get(3));
        this.InputForVelocity.initComponents();
    }

    // updates input window and objects for the next frame
    public void update(double dt, Vector2 move) {
        // checks whether the window needs to be shown

        this.Inputwindow.update(this.timer);
        this.InputForForce.update(this.timer);
        this.InputForVelocity.update(this.timer);
        this.InputSurface.update(this.timer);
        this.restart.response.update(this.objects);
        // stores an image the which will be
        // pinned to screen
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();

        this.draw(dbg, move);
        if (this.play.response.getActive()) {

            this.timer.update(dt);
        }
        this.objects.update(dt, this.timer, this.play, this.restart);

        // needed for smoother frame rate
        this.g2.drawImage(dbImage, 0, 0, this);

    }

    // method that draws all objects and buttons
    public void draw(Graphics g, Vector2 move) {
        // accesses the graphics of the window
        Graphics2D g2 = (Graphics2D) g;
        GraphicsEngine ge = new Graphics2DAdapter(g2);
        ge.setColor(Color.LIGHT_GRAY);
        ge.drawRectangle(new Vector2(Constants.SCREEN_OFFSET, 0),
            new Vector2(Constants.SCREEN_WIDTH - Constants.SCREEN_OFFSET, Constants.SCREEN_HEIGHT),
            true);
        this.axes.draw(ge, move);

        this.objects.draw(ge, move);
        this.Out.draw(ge);
        this.menu.draw(ge);
        this.timer.draw(ge);
    }


    public void run() {
        // get time and assign mouse listeners to initialised object
        double lastFrameTime = 0.0;
        this.addMouseListener(this.ml);
        this.addKeyListener(this.keyListener);

      try {
        FileSaver.restartFromFile(this.objects, new File("src/samples/oneObject.txt"));
        FileSaver.SaveTo(new File("src/RestartFile"), this.objects);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      boolean Click;
        //LOOP that runs continuously
        while (true) {
            // gets the location of the cursor
            Pointer = new Vector2(this.ml.getLocation());
            Click = ml.getClick();
            // checks whether buttons on the panel should be highlighted or pressed
            this.menu.OnMenu(Pointer, Click, this.keyListener.getCharacter());
            this.Out.CheckHighlight(Pointer, Click);
            this.Display.update(Pointer, Click, this.scroll.getRotation());
            this.objects.changeSubject(this.Out);

            // gets the time of one frame to update frame depending on dt
            double time = Time.getTime();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;
            // updates for the frame time
            this.update(deltaTime, this.keyListener.getMoved());
            //System.out.println(this.keyListener.getMoved().getY());
        }
    }
}
