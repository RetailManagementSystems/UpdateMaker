package hu.vodafone.actionview.actiongetFromDB;

import hu.vodafone.actionview.actions.ActionsImp;
import hu.vodafone.actionview.database.DbConnection;
import hu.vodafone.actionview.readWrite.ReadWrite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ActionGetFromDb {
    ReadWrite readWrite = new ReadWrite();
    ActionsImp actionsImp = new ActionsImp();
    private Statement s = null;
    private ResultSet rs = null;
    DbConnection dbConnection = new DbConnection();
    String ip = "";
    String hostname = "";
    String deviceactions = "";
    String action_date = "";

    public String actionFromDB(String hostnamer) {

        String serial = actionsImp.serialnum();
        dbConnection.connection(readWrite.getPIDfromFile());
        String sql = "Select ips,hostname,deviceactions,action_date from device_actions where hostname = '" + hostnamer + "'";
        try {

            s = dbConnection.getConnection().createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                ip = rs.getString("ips");
                deviceactions = rs.getString("deviceactions");
                action_date = rs.getString("action_date");
            }
            rs.close();
            s.close();
            dbConnection.getConnection().close();
            System.out.println("Kapcsolat lezárva");
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }


        return deviceactions;
    }

    public void delete(String hostname) {
        String sql = "DELETE FROM device_actions where hostname ='" + hostname + "'";
        try {
            dbConnection.connection(readWrite.getPIDfromFile());
            s = dbConnection.getConnection().createStatement();
            s.execute(sql);
            s.close();
            dbConnection.getConnection().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getMacFromDB(String hostname) {
        String sql = "select serial_number from ip_addresses where hostname ='" + hostname + "'";
        String serial = "";
        try {
            dbConnection.connection(readWrite.getPIDfromFile());
            s = dbConnection.getConnection().createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                serial = rs.getString("serial_number");
            }
            rs.close();
            s.close();
            dbConnection.getConnection().close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return serial;
    }

    public String getNewHostname(String serial_number) {
        String sql = "Select newHostname from new_hostname where serial_num = '" + serial_number + "'";
        String newhostname = "";
        try {
            dbConnection.connection(readWrite.getPIDfromFile());
            s = dbConnection.getConnection().createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                newhostname = rs.getString("newHostname");
            }
            rs.close();
            s.close();
            dbConnection.getConnection().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return newhostname;
    }

    public Boolean hasAnInteraction(String serial_number) {
        String sql = "Select newHostname from new_hostname where serial_num = '" + serial_number + "'";
        String newhostname = "";
        boolean hasAnInteraction = false;
        try {
            dbConnection.connection(readWrite.getPIDfromFile());
            s = dbConnection.getConnection().createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                newhostname = rs.getString("newHostname");
            }
            rs.close();
            s.close();
            dbConnection.getConnection().close();
            if (!newhostname.isEmpty()) {
                hasAnInteraction = true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return hasAnInteraction;
    }

    public ArrayList<String> getAllDataFromNewHostname(String serial_number) {
        ArrayList<String> alldata = new ArrayList<>();
        String sql = "Select * from new_hostname where serial_num ='" + serial_number + "'";
        try {
            dbConnection.connection(readWrite.getPIDfromFile());
            s = dbConnection.getConnection().createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                alldata.add(rs.getString("serial_num"));
                alldata.add(rs.getString("oldhostname"));
                alldata.add(rs.getString("newhostname"));
            }
            rs.close();
            s.close();
            dbConnection.getConnection().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return alldata;
    }

    public void deleteAndArchive(String serial_number) {
        ArrayList<String> alldata = new ArrayList<>(getAllDataFromNewHostname(serial_number));
        System.out.println(serial_number);
        String deletesql = "delete from new_hostname where serial_num = '" + serial_number + "'";
        String archivesql = "INSERT INTO archivedHostname(serial_num,oldhostname,newhostname,datum) VALUES('" + alldata.get(0) + "','" + alldata.get(1) + "','" + alldata.get(2) + "','" + LocalDate.now() + "')";
        try {
            dbConnection.connection(readWrite.getPIDfromFile());
            s = dbConnection.getConnection().createStatement();
            s.execute(archivesql);
            s.execute(deletesql);
            s.close();
            dbConnection.getConnection().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatusIpAddresses(String hostname, String serial_number) {
        String sql = "UPDATE device_status set hostname = '" + hostname + "' where serial_number = '" + serial_number + "'";
        String sqlIp = "UPDATE ip_addresses set hostname = '" + hostname + "' where serial_number = '" + serial_number + "'";
        try {
            dbConnection.connection(readWrite.getPIDfromFile());
            s = dbConnection.getConnection().createStatement();
            s.execute(sql);
            s.execute(sqlIp);
            s.close();
            dbConnection.getConnection().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void updateLastRun(String hostname) {
        String sql = "UPDATE ip_addresses set act_last_run = '" + LocalDateTime.now() + "' where hostname = '" + hostname + "'";

        try {
            dbConnection.connection(readWrite.getPIDfromFile());
            s = dbConnection.getConnection().createStatement();
            s.execute(sql);

            s.close();
            dbConnection.getConnection().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void updateusages(String hostname){
        String memprc = actionsImp.getMemprc();
        String procprc = actionsImp.getPrcprc();
        String strprc = actionsImp.runCommandReturn("./storageproc.sh");
        String sql = "UPDATE device_status set memprc = '"+memprc+"' where hostname = '"+hostname+"'";
        String sql2 = "UPDATE device_status set procprc = '"+procprc+"' where hostname = '"+hostname+"'";
        String sql3 = "UPDATE device_status set strprc = '"+strprc+"' where hostname = '"+hostname+"'";
        try {
            dbConnection.connection(readWrite.getPIDfromFile());
            s = dbConnection.getConnection().createStatement();
            s.execute(sql);
            s.execute(sql2);
            s.execute(sql3);
            s.close();
            dbConnection.getConnection().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
