package hu.vodafone.Database;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    @Getter
    private Connection connection = null;

    public void connection(String ip) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://"+ip+":3306/UpdaterBase?serverTimezone=UTC", "szerver", "UpToDate");
            System.out.println("Siker");
        }
        catch (SQLException ex) {
            //write to file
            System.out.println("szar" + ex);
        }
    }
}
