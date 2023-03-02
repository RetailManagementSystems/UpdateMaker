package hu.vodafone.OIT.dataexport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Dataexport {
    public void runCommand(String... command) {
        ProcessBuilder processBuilder = new ProcessBuilder().command(command);

        try {
            Process process = processBuilder.start();

            //read the output
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String output = null;
            while ((output = bufferedReader.readLine()) != null) {
                System.out.println(output);
            }

            //wait for the process to complete
            process.waitFor();

            //close the resources
            bufferedReader.close();
            process.destroy();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void exportDatabase()
    {

       /* runCommand("mysqldump","-u","dumpuser","UpdaterBase",">","/home/retailupdateserver/Vodafone/dbserver.sql");
        try {

        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        try{
        String dump = "mysqldump -u dumpuser UpdaterBase > file.sql";
        String[] cmdarray = {"/bin/sh","-c", dump};
        Process p = Runtime.getRuntime().exec(cmdarray);
        if (p.waitFor() == 0) {
          System.out.println("lefutott");
        } else {
            System.out.println("nem");
        }}catch (InterruptedException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println(e);
        }

    }





}
