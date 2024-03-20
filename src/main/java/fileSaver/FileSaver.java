package fileSaver;

import Graphical.Rect;
import Graphical.ObjectsRegister;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileSaver {

    public static void SaveTo(File file, ObjectsRegister obj) throws IOException {
        StringBuilder surfaceBuilder = new StringBuilder("Surface\n");
        StringBuilder rectBuilder = new StringBuilder("Rectangle\n");

        obj.iterate((Rect r) -> {
            if (r.getMass() == 0f) {
                surfaceBuilder.append(r + "\n");
            } else {
                rectBuilder.append(r + "\n");
            }
        });

        System.out.println("Here are the contents being saved");

        System.out.println(surfaceBuilder);
        System.out.println(rectBuilder);

        //prepares to put it into a file
        PrintWriter writer = new PrintWriter(file);

        writer.print(surfaceBuilder);
        writer.print(rectBuilder);

        writer.close();
    }


    public static void restartFromFile(ObjectsRegister obj, File text) throws IOException {
        ArrayList<String> objects = new ArrayList<>();
        //adds two possible types of added objects
        objects.add("Rectangle");
        objects.add("Surface");
        int index = -1;
        String line = "";
        // scanner that allows to get information line by line
        Scanner scan = new Scanner(text);
        while (scan.hasNextLine()) {
            //line in text file is saved, so it checks the same line at all places
            line = scan.nextLine();
            if (line.equals(objects.get(0))) {
                index = 0;
            } else if (line.equals(objects.get(1))) {
                index = 1;
            } else {
                // space is added to finish the loop
                if (index == 0) {
                    readForRectangle(line + " ", obj);
                } else if (index == 1) {
                    readForSurface(line + " ", obj);
                }
            }
        }
    }

    public static void readForRectangle(String input, ObjectsRegister obj) {
        String detail, Name;
        int Xpos, Ypos, Width, Height, order;
        // prepares some numbers to be changed after it reads details
        float Mass = 0;
        Xpos = 0;
        Ypos = 0;
        Width = 0;
        Height = 0;
        detail = "";
        Name = "";
        order = 0;
        char c;
        for (int i = 0; i < input.length(); i++) {
            // gets a character to examine
            c = input.charAt(i);
            if (c == ' ') {// depending on the number
                //different details are read
                if (order == 0) {
                    Xpos = Integer.parseInt(detail);
                } else if (order == 1) {
                    Ypos = Integer.parseInt(detail);
                } else if (order == 2) {
                    Width = Integer.parseInt(detail);
                } else if (order == 3) {
                    Height = Integer.parseInt(detail);
                } else if (order == 4) {
                    Mass = Float.parseFloat(detail);
                } else if (order == 5) {
                    Name = detail;
                }
                // makes next detail to be read
                detail = "";
                order += 1;
            } else {// adds a character if has not been added
                detail = detail.concat(String.valueOf(input.charAt(i)));
            }
        }// creates an object with read details
        obj.add(new Rect(Xpos, Ypos, Height, Width, Mass, Color.yellow, Name));
    }

    // this is similar to the previous method, it reads angle instead of mass
    public static void readForSurface(String input, ObjectsRegister obj) {
        String detail, Name;
        int Xpos, Ypos, Width, Height, order;
        float Rotation = 0;
        Xpos = 0;
        Ypos = 0;
        Width = 0;
        Height = 0;
        detail = "";
        Name = "";
        order = 0;
        char c;
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            if (c == ' ') {
                if (order == 0) {
                    Xpos = Integer.parseInt(detail);
                } else if (order == 1) {
                    Ypos = Integer.parseInt(detail);
                } else if (order == 2) {
                    Width = Integer.parseInt(detail);
                } else if (order == 3) {
                    Height = Integer.parseInt(detail);
                } else if (order == 4) {
                    Rotation = Float.parseFloat(detail);
                } else if (order == 5) {
                    Name = detail;
                }
                detail = "";
                order += 1;
            } else {
                detail = detail.concat(String.valueOf(input.charAt(i)));
            }
        }
        Rect rect = new Rect(Xpos, Ypos, Height, Width, 0, Color.yellow, Name);
        rect.setRotationCurrent(Rotation);
        obj.add(rect);
    }
}
