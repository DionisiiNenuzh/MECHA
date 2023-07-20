package fileHandle;

import Graphical.Rect;
import Graphical.objectsRegister;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileSaver {

    public static void SaveTo(File file, objectsRegister obj) throws IOException {
        // arrays that input all objects in the register to a file
        ArrayList<String> Rectangles = new ArrayList<>();
        ArrayList<String> Surfaces = new ArrayList<>();
        // traverses all object and adds a surface or a Rectangle
        for (int i = 0; i < obj.getSize(); i++) {
            if (obj.getItem(i).getMass() == 0) {
                Surfaces.add(saveSurface(obj.getItem(i)));
            } else {
                Rectangles.add(saveRectangle(obj.getItem(i)));
            }
        }//prepares to put it into a file
        PrintWriter writer = new PrintWriter(file);
        // inputs all surfaces
        writer.println("Surface");
        for (int i = 0; i < Surfaces.size(); i++) {
            writer.println(Surfaces.get(i));
        }
        // inputs all rectangles
        writer.println("Rectangle");
        for (int i = 0; i < Rectangles.size(); i++) {
            writer.println(Rectangles.get(i));
        }
        // file closes, so it saves
        writer.close();
    }

    public static String saveRectangle(Rect rect) {
        // adds all details in the correct format
        // the same way it is going to be read from
        String answer = String.valueOf((int) rect.getPosition().getX());
        answer += " " + (int) rect.getPosition().getY();
        answer += " " + rect.getWidth() + " " + rect.getHeight();
        answer += " " + rect.getMass();
        answer += " " + rect.getId();
        return answer;
    }

    public static String saveSurface(Rect rect) {
        // adds all details in the correct format
        // the same way it is going to be read from
        String answer = String.valueOf((int) rect.getPosition().getX());
        answer += " " + (int) rect.getPosition().getY();
        answer += " " + rect.getWidth() + " " + rect.getHeight();
        answer += " " + rect.getRotation();// has rotation instead of mass
        answer += " " + rect.getId();
        return answer;
    }
}
