import Graphical.Window;

public class Main {

    public static void main(String[] args) {

        Window Canvas = new Window();
        //routine that runs  canvas
        Thread t1 = new Thread(Canvas);

        Canvas.run();


    }

}
