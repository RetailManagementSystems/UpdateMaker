package hu.vodafone.ReadWriteData;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Scanner;

public class ReadWriteImp implements ReadWriteInt {
    @Getter
    @Setter
    private String readedData = "";

    @Override
    public String readDataFromTextFile() {
        try {
            File file = new File("/home/pointmediauser/Vodafone/appVersion.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                readedData = scanner.nextLine();
            }
            scanner.close();
        }
        catch (FileNotFoundException ex) {
            writeDatatoTextFile("/home/pointmediauser/Vodafone/errorlog.txt", String.valueOf(ex));
        }
        return readedData;
    }

    @Override
    public String readUUID() {
        try {
            File file = new File("/home/pointmediauser/serial.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                readedData = scanner.nextLine();
            }
            scanner.close();
        }
        catch (FileNotFoundException ex) {
            writeDatatoTextFile("/home/pointmediauser/updater/errorlog.txt", String.valueOf(ex));
        }
        return readedData;
    }

    @Override
    public String getActionViewerVersion() {
        try {
            File file = new File("/home/pointmediauser/Vodafone/actionViewerVersion.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                readedData = scanner.nextLine();
            }
            scanner.close();
        }
        catch (FileNotFoundException ex) {
            writeDatatoTextFile("/home/pointmediauser/Vodafone/errorlog.txt", String.valueOf(ex));
        }
        return readedData;
    }

    @Override
    public String getPointerVersion() {

        try {
            File file = new File("/home/pointmediauser/Vodafone/Pointerversion.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                readedData = scanner.nextLine();
            }
            scanner.close();
        }
        catch (FileNotFoundException ex) {
            writeDatatoTextFile("/home/pointmediauser/Vodafone/errorlogPointer.txt", String.valueOf(ex));
        }
        return readedData;

    }

    @Override
    public String getUpdateMakerVersion() {
        try {
            File file = new File("/home/pointmediauser/Vodafone/updateMakerVersion.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                readedData = scanner.nextLine();
            }
            scanner.close();
        }
        catch (FileNotFoundException ex) {
            writeDatatoTextFile("/home/pointmediauser/Vodafone/errorlog.txt", String.valueOf(ex));
        }
        return readedData;
    }

    @Override
    public void writeDatatoTextFile(String path, String msg) {
        try {
            FileWriter file = new FileWriter(path);
            file.write(msg);
            file.close();
        }
        catch (IOException ex) {
            System.out.println("ERROR!!!");
            ex.printStackTrace();
        }
    }


    @Override
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

    private void rungetPid() {
        String[] runterminal = new String[]{"/bin/sh", "/home/pointmediauser/PointerLiveDevice/getPid.sh"};
        try {
            Process process = Runtime.getRuntime().exec(runterminal);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean processwithPidIsAlive(String pid) {
        boolean processisalive = false;
        String line = "";
        String output = "";
        try {
            Process p = Runtime.getRuntime().exec("ps -f | grep PointerLiveDevice | grep -v grep");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                output = line;
                if (pid.equals(output)) {
                    processisalive = true;
                }
            }
        }
        catch (Exception err) {
            System.out.println(err);
        }
        return processisalive;
    }
}
