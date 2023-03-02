package hu.vodafone.Database;

import hu.vodafone.ReadWriteData.ReadWriteImp;
import hu.vodafone.ReadWriteData.ReadWriteInt;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatafromDbImp implements DatafromDb {
    DatabaseConnection databaseConnection = new DatabaseConnection();
    DatabaseManipulationInterface databaseManipulationInterface = new DatabaseManipulationImplementation();
    ReadWriteInt readWriteInt = new ReadWriteImp();
    private Statement s = null;
    private ResultSet rs = null;
    private ArrayList<String> ips = new ArrayList<>();
    private ArrayList<String> hostnames = new ArrayList<>();
    private ArrayList<String> serials = new ArrayList<>();
    private Boolean isInTheDatabaseWithSameAttributes = false;
    private int counter = 0;
    @Getter
    private String serial = "";

    private void fillTheArrays() {
        databaseConnection.connection(readWriteInt.getPIDfromFile());
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

    private String getPCSerial() {
        String uuid = readWriteInt.readUUID();
        return uuid;
    }
//innentől
    public Boolean checkIP(String ip) {
        Boolean ipIsInTheDatase = false;
        for (int i = 0; i < ips.size(); i++) {
            if (ips.get(i).equals(ip)) {
                ipIsInTheDatase = true;
                break;
            }
            else {
                ipIsInTheDatase = false;
            }
        }


        return ipIsInTheDatase;
    }

    public Boolean checkserial(String serial) {
        Boolean serialIsInTheDatase = false;
        for (int i = 0; i < ips.size(); i++) {
            if (serials.get(i).equals(serial)) {
                serialIsInTheDatase = true;
                break;
            }
            else {
                serialIsInTheDatase = false;
            }
        }


        return serialIsInTheDatase;
    }


    @Override
    public Boolean hostnameIsInTheDatabase(String hostname) {
        fillTheArrays();
        String serial = getPCSerial();
        Boolean ipInTheDB;
        Boolean serialinTheDB;

        ipInTheDB = checkIP(databaseManipulationInterface.getIP());
        serialinTheDB = checkserial(serial);

        if (ipInTheDB == true && serialinTheDB == true) {
            isInTheDatabaseWithSameAttributes = true;
            System.out.println("Nem történt semmi");

        }
        else if (serialinTheDB == true && ipInTheDB != true) {
            databaseManipulationInterface.updateDeviceIP(hostname);
            System.out.println("Fel lett frissítve az IP UPDATE VOLT");
            isInTheDatabaseWithSameAttributes = true;
        }
        else if (ipInTheDB != true && serialinTheDB != true) {
            databaseManipulationInterface.insertDeviceIPandHostname(hostname, serial, getVersionFromDatabase());
            System.out.println("Be lett küldve INSERT");
            isInTheDatabaseWithSameAttributes = true;
        }
        return isInTheDatabaseWithSameAttributes;
    }
//idáig lehet a hiba nézd át a kódot, logikailag
    @Override
    public String getVersionFromDatabase() {
        String version = "";
        databaseConnection.connection(readWriteInt.getPIDfromFile());
        String sql = "Select version from app_version";
        try {
            s = databaseConnection.getConnection().createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                version = rs.getString("version");
            }
            s.close();
            databaseConnection.getConnection().close();
            rs.close();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        return version;
    }

    @Override
    public String getVersionofActionViewer() {
        String version = "";
        databaseConnection.connection(readWriteInt.getPIDfromFile());
        String sql = "Select version from updater_actionviewer where app_name = 'Action-Viewer'";
        try {
            s = databaseConnection.getConnection().createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                version = rs.getString("version");
            }
            s.close();
            databaseConnection.getConnection().close();
            rs.close();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        return version;
    }

    @Override
    public String getVersionofUpdateMaker() {
        String version = "";
        databaseConnection.connection(readWriteInt.getPIDfromFile());
        String sql = "Select version from updater_actionviewer where app_name = 'Updater-Maker'";
        try {
            s = databaseConnection.getConnection().createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                version = rs.getString("version");
            }
            s.close();
            databaseConnection.getConnection().close();
            rs.close();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        return version;
    }

    @Override
    public String getVersionofPointer() {
        String version = "";
        databaseConnection.connection(readWriteInt.getPIDfromFile());
        String sql = "Select version from updater_actionviewer where app_name = 'Pointer'";
        try {
            s = databaseConnection.getConnection().createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                version = rs.getString("version");
            }
            s.close();
            databaseConnection.getConnection().close();
            rs.close();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        return version;
    }


    @Override
    public String getVersionFromPC() {
        String version = readWriteInt.readDataFromTextFile();
        return version;
    }
}
