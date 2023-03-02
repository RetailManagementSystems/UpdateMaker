package hu.vodafone.OIT.dataexport;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

public class FetchMail extends TimerTask {

    /** Construct and use a TimerTask and Timer. */
    private static final long fONCE_PER_DAY = 1000*60*60*24;

    private static final int fONE_DAY = 1;
    private static final int fFOUR_AM = 8;
    private static final int fZERO_MINUTES = 0;



    /**
     * Implements TimerTask's abstract run method.
     */
    @Override public void run(){
        //toy implementation
        System.out.println("Fetching mail...");
    }

    // PRIVATE

    //expressed in milliseconds


    public static Date getTomorrowMorning4am(){
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