package hu.vodafone.OIT.lostDevices;

import java.util.ArrayList;

public interface DatabaseMethodsInterface {
    ArrayList<String> getLostDevices();
    ArrayList<String> checkLastseen(ArrayList<String> data);
    ArrayList<String> getAllDataFromLostDevices(ArrayList<String> data);
}
