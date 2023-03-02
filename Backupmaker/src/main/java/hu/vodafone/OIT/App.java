package hu.vodafone.OIT;

import hu.vodafone.OIT.dataexport.Dataexport;
import hu.vodafone.OIT.lostDevices.DBMImp;
import hu.vodafone.OIT.lostDevices.DatabaseMethodsInterface;
import hu.vodafone.OIT.mail.ExcelOps;
import hu.vodafone.OIT.mail.LostDataMail;
import hu.vodafone.OIT.mail.MailEntity;

import java.util.*;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Hello world!
 */
public final class App extends TimerTask {
    Dataexport dataexport = new Dataexport();
    MailEntity mailEntity = new MailEntity();
    LostDataMail lostDataMail = new LostDataMail();
    ExcelOps excelOps = new ExcelOps();
    DatabaseMethodsInterface databaseMethodsInterface = new DBMImp();


    public static void main(String... arguments) {



        TimerTask fetchMail = new App();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(fetchMail, getTomorrowMorning4am(), fONCE_PER_DAY);
    }



    @Override
    public void run() {
        dataexport.exportDatabase();
        mailEntity.sendMail();
        ArrayList<String> data = new ArrayList<>(databaseMethodsInterface.getLostDevices());
        ArrayList<String> sortedDate = new ArrayList<>(databaseMethodsInterface.checkLastseen(data));
        ArrayList<String> finishedListToExcel = new ArrayList<>(databaseMethodsInterface.getAllDataFromLostDevices(sortedDate));
        try {
            excelOps.excelExport(finishedListToExcel);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        lostDataMail.sendMail();


    }

    // PRIVATE

    //expressed in milliseconds
    private final static long fONCE_PER_DAY = 1000 * 60 * 60 * 24;

    private final static int fONE_DAY = 1;
    private final static int fFOUR_AM = 8;
    private final static int fZERO_MINUTES = 0;

    private static Date getTomorrowMorning4am() {
        Calendar tomorrow = new GregorianCalendar();
        tomorrow.add(Calendar.DATE, fONE_DAY);
        Calendar result = new GregorianCalendar(
                tomorrow.get(Calendar.YEAR),
                tomorrow.get(Calendar.MONTH),
                tomorrow.get(Calendar.DATE),
                fFOUR_AM,
                fZERO_MINUTES
        );
        return result.getTime();
    }
}

