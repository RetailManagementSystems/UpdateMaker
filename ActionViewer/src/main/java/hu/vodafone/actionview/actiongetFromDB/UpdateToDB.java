package hu.vodafone.actionview.actiongetFromDB;

import hu.vodafone.actionview.actions.ActionsImp;
import hu.vodafone.actionview.actions.ActionsInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UpdateToDB {
    ActionsInterface actionsInterface = new ActionsImp();

    public void UpdateUsages() {
        try {
            Process p = Runtime.getRuntime().exec("/home/pointmediauser/Vodafone/proc.sh");

            p.waitFor();
            BufferedReader buf = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line = "";
            String output = "";

            while ((line = buf.readLine()) != null) {
                output += line + "\n";
            }

            System.out.println(output);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        /*  String[] runterminal = new String[]{"/bin/sh", "/home/pointmediauser/Vodafone/memoryusage.sh"};
        try {
            Process process = Runtime.getRuntime().exec(runterminal);
            System.out.println(process);
        }catch (IOException e) {
            e.printStackTrace();
        }*/


       /* String proc =  actionsInterface.runCommandReturn("/bin/sh/","$[100-$(vmstat 1 2|tail -1|awk '{print $15}')]","%");
        String memory = actionsInterface.runCommandReturn("/pointmediauser/Vodafone/memoryusage.sh");
        System.out.println(proc);
        System.out.println(memory);*/


    }

}
