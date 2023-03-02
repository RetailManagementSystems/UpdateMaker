package hu.vodafone.actionview.readWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadWrite {
    public String getPIDfromFile() {

        String pid = "";
        try {
            File file = new File("/home/pointmediauser/Vodafone/ipfromserver.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                pid = scanner.nextLine();
            }
            scanner.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("error:  " + ex);
        }
        return pid;
    }
}
