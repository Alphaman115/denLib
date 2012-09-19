package denoflionsx.denLib.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class FileWrite {

    public static void write(String file, ArrayList<String> w) {
        try {
            FileWriter outFile = new FileWriter(file);
            PrintWriter out = new PrintWriter(outFile);
            Iterator t = w.iterator();
            while (t.hasNext()) {
                out.println(t.next().toString());
            }
            out.close();
        } catch (IOException e) {
            //write(file,w);
        }
    }
}
