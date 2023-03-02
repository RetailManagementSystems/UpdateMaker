package hu.vodafone.Database;

import hu.vodafone.ReadWriteData.ReadWriteImp;
import hu.vodafone.ReadWriteData.ReadWriteInt;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class DatabaseManipulationImplementation implements DatabaseManipulationInterface {
    DatabaseConnection databaseConnection = new DatabaseConnection();
    ReadWriteInt readWriteInt = new ReadWriteImp();
    private Statement s = null;

    @Override
    public void insertDeviceIPandHostname(String hostname, String serial, String version) {
        String sql = "Insert into ip_addresses (ip,hostname,version,serial_number) VALUES ('" + getIP() + "','" + hostname + "','" + version + "','" + serial + "')";
        try {
            databaseConnection.connection(readWriteInt.getPIDfromFile());
            s = databaseConnection.getConnection().createStatement();
            s.execute(sql);
            s.close();
            databaseConnection.getConnection().close();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void updateDeviceIP(String hostname) {
        String sql = "UPDATE ip_addresses SET ip ='" + getIP() + "' where hostname ='" + hostname + "'";
        try {
            databaseConnection.connection(readWriteInt.getPIDfromFile());
            s = databaseConnection.getConnection().createStatement();
            s.execute(sql);

            System.out.println("Az IP-m" + getIP());
            System.out.println("A hostnevem" + hostname);
            databaseConnection.getConnection().close();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void updateLastRun(String hostname) {
        String sql = "UPDATE ip_addresses SET upt_last_run ='" + LocalDateTime.now() + "' where hostname ='" + hostname + "'";
        try {
            databaseConnection.connection(readWriteInt.getPIDfromFile());
            s = databaseConnection.getConnection().createStatement();
            s.execute(sql);

            databaseConnection.getConnection().close();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private String networkInterfaces() {
        String interfacetype = "";
        ArrayList<String> allInterface = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)) {
                allInterface.add(netint.getName());
            }
        }
        catch (SocketException ex) {
            System.out.println(ex);
        }
        for (int i = 0; i < allInterface.size(); i++) {
            if (allInterface.get(i).equals("wlp2s0")) {
                interfacetype = "wlp2s0";
            }
            else if (allInterface.get(i).equals("wlp1s0")) {
                interfacetype = "wlp1s0";
            }
            else if (allInterface.get(i).equals("wlp0s20f3")) {
                interfacetype = "wlp0s20f3";
            }
        }
        return interfacetype;
    }

    @Override
    public String getIP() {
        String ip = "";
        NetworkInterface ni = null;
        String name_of_Interface = networkInterfaces();

        try {
            ni = NetworkInterface.getByName(name_of_Interface);
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
        while (inetAddresses.hasMoreElements()) {
            InetAddress ia = inetAddresses.nextElement();
            if (!ia.isLinkLocalAddress()) {
                ip = ia.getHostAddress();
                // System.out.println("IP: " + ia.getHostAddress());
            }
        }
        return ip;
    }
}