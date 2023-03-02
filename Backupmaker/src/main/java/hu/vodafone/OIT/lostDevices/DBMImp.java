package hu.vodafone.OIT.lostDevices;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class DBMImp implements DatabaseMethodsInterface{
    private Statement s = null;
    private ResultSet rs = null;

    DatabaseConnection databaseConnection = new DatabaseConnection();
    @Override
    public ArrayList<String> getLostDevices() {
        ArrayList<String> allLostDevice = new ArrayList<>();
        databaseConnection.connection();
        String sql = "Select id,last_seen from device_status where last_seen !='OK'";
        try{
            s = databaseConnection.getConnection().createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()){
                allLostDevice.add(rs.getString("id"));
                allLostDevice.add(rs.getString("last_seen"));
            }rs.close();

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return allLostDevice;
    }

    @Override
    public ArrayList<String> checkLastseen(ArrayList<String> data) {
        ArrayList<String> checkdatas = new ArrayList<>();
        String sDate1 = "";
        String sDate2 = String.valueOf(LocalDateTime.now().minusDays(5));
        SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatter7=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date2 = new Date();
        Date date1 = new Date();

        for (int i = 1; i <data.size(); i=i+2) {
            try {
                sDate1 = data.get(i);
                date1 = formatter7.parse(sDate1);
                date2 = formatter6.parse(sDate2);

                if(date1.before(date2)){
                    checkdatas.add(data.get(i-1));
                    System.out.println("ID: "+data.get(i-1)+", Last_seen: "+date1);
                }

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            
        }
        return checkdatas;
    }
    public ArrayList<String> getAllDataFromLostDevices(ArrayList<String> data) {
        ArrayList<String> allLostDevice = new ArrayList<>();
        databaseConnection.connection();
        for (int i = 0; i < data.size(); i++) {


            String sql = "Select id,ip,hostname,last_seen,fanumber from device_status where id="+data.get(i)+"";
            try {
                s = databaseConnection.getConnection().createStatement();
                rs = s.executeQuery(sql);
                while (rs.next()) {
                    allLostDevice.add(rs.getString("id"));
                    allLostDevice.add(rs.getString("ip"));
                    allLostDevice.add(rs.getString("hostname"));
                    allLostDevice.add(rs.getString("last_seen"));
                    allLostDevice.add(rs.getString("fanumber"));
                }
                rs.close();

            }
            catch (SQLException ex) {
                System.out.println(ex);
            }
        }
            return allLostDevice;
        }


    /*

     private void fillTheArrays() {
        databaseConnection.connection();
        String sql = "Select ip,hostname,serial_number from ip_addresses";
        System.out.println("Boolean alatt vagyok");
        try {
            System.out.println("Try-ba");
            s = databaseConnection.getConnection().createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                System.out.println("While-ba");
                ips.add(rs.getString("ip"));
                hostnames.add(rs.getString("hostname"));
                serials.add(rs.getString("serial_number"));

            }
            s.close();
            databaseConnection.getConnection().close();
            rs.close();
        }
        catch (SQLException ex) {
            System.out.println(ex);
            isInTheDatabaseWithSameAttributes = false;
        }
    }


     */
}
