package hu.vodafone.actionview;

import hu.vodafone.actionview.actiongetFromDB.ActionGetFromDb;
import hu.vodafone.actionview.actiongetFromDB.UpdateToDB;
import hu.vodafone.actionview.actions.ActionsImp;
import hu.vodafone.actionview.actions.ActionsInterface;
import hu.vodafone.actionview.actions.Scheduler;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public class App extends TimerTask {
    public static void main(String[] args) {
        final ActionGetFromDb actionGetFromDb = new ActionGetFromDb();
        final ActionsInterface actionsInterface = new ActionsImp();
        TimerTask fetchMail = new App();
        Scheduler scheduler = new Scheduler();
        UpdateToDB updateToDB = new UpdateToDB();
        String hostname = "";
        String uptime = actionsInterface.getUptime();
        System.out.println(uptime);
        String sDate1 = LocalDate.now()+"T23:00:00";
        String sDate2 = String.valueOf(LocalDateTime.now());
        SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatter7=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = new Date();
        Date date1 = new Date();

        try {
            date1= formatter6.parse(sDate1);
            date2 = formatter6.parse(sDate2);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(uptime.equals("7 days") && date2.after(date1)){
               actionsInterface.reboot();
        }
        try {
            Thread.sleep(300);
            hostname = InetAddress.getLocalHost().getHostName();
            actionGetFromDb.updateLastRun(hostname);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        final String finalHostname = hostname;
        actionGetFromDb.updateusages(finalHostname);
        String serial_number = actionGetFromDb.getMacFromDB(finalHostname);
        System.out.println("NAGYONSZERIAL SZÁM GYANÚÚÚÚÚS" + serial_number);
        //updateToDB.UpdateUsages();
        String newhostname = actionGetFromDb.getNewHostname(actionGetFromDb.getMacFromDB(finalHostname));
        System.out.println("Nézem az akciót");
        String action = actionGetFromDb.actionFromDB(finalHostname);
        System.out.println("67.sor");
        String hostnamechange = actionGetFromDb.getNewHostname(actionGetFromDb.getMacFromDB(finalHostname));
        System.out.println(hostnamechange);
        boolean interaction = actionGetFromDb.hasAnInteraction(actionGetFromDb.getMacFromDB(finalHostname));
        System.out.println("70.sor");
        if (!action.equals(null) || interaction != false) {
            if (action.equals("reboot")) {
                actionsInterface.reboot();
                actionGetFromDb.delete(finalHostname);
            }
            else if (action.equals("apprestart")) {
                actionsInterface.apprestart();
                actionGetFromDb.delete(finalHostname);
            }
            else if (action.equals("systemupdate")) {
                actionsInterface.systemupdate();
                actionGetFromDb.delete(finalHostname);
            }
            else if (action.equals("appUpdate")) {
                actionsInterface.AppUpdate();
                actionGetFromDb.delete(finalHostname);
            }
            else if (action.equals("getIP")) {
                actionsInterface.refreshIP();
                actionGetFromDb.delete(finalHostname);
            }
            else if (interaction != false) {
                System.out.println("94.sor");
                System.out.println(interaction);
                System.out.println("VAN AKCIÓ HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                actionsInterface.hostnamechange(hostnamechange);
                actionGetFromDb.deleteAndArchive(actionGetFromDb.getMacFromDB(finalHostname));
                actionGetFromDb.updateStatusIpAddresses(newhostname, actionGetFromDb.getMacFromDB(finalHostname));
                actionsInterface.reboot();
                interaction = false;
            }
        }
        System.out.println("104.sor");
         System.exit(2);
         System.out.println("kiléptem");



    }
    final ActionsInterface actionsInterface = new ActionsImp();
    @Override
    public void run() {
        actionsInterface.reboot();
        System.out.println("doing");

    }
    private final static long fONCE_PER_DAY = 1;

    private final static int fONE_DAY = 0;
    private final static int fFOUR_AM = 11;
    private final static int fZERO_MINUTES = 18;

    private static Date getTomorrowMorning4am() {
        Calendar tomorrow = new GregorianCalendar();
        tomorrow.add(Calendar.DATE, fONE_DAY);
        Calendar result = new GregorianCalendar(tomorrow.get(Calendar.YEAR),
                tomorrow.get(Calendar.MONTH), tomorrow.get(Calendar.DATE), fFOUR_AM,
                fZERO_MINUTES);
        System.out.println(result);
        return result.getTime();
    }

}
