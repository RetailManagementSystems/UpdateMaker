package hu.vodafone.actionview.actions;

import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {
    public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static ActionsInterface actionsInterface = new ActionsImp();
    public static Timer timer = new Timer();

    public boolean isScheduled() {
        return scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    private boolean scheduled = false;


    private static class MyTimeTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("Reboot");
            actionsInterface.reboot();
            timer.cancel();
        }
    }
    public void runTask() throws ParseException {
        scheduled = true;
        System.out.println("beállítva");
        Date date = df.parse(LocalDate.now()+" 15:08:00");
        timer.schedule(new MyTimeTask(), date);
    }

}

