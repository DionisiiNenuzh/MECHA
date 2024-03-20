package Graphical;

public class Time {

    // class used to update screen depending on the time
    public static double timeStarted = System.nanoTime();

    public static double getTime() {
        return (System.nanoTime() - timeStarted) * 1E-9;
    }
}
