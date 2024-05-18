package	tests.APITest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import abstractComponents.JsonUtils;

public class DBTest {
    public static void verifyEncryptedPasswordInDB(WebDriver driver) throws Exception {

        String url = "jdbc:sqlserver://localhost:1433;databaseName=mydatabase";
        String username = "yourUsername";
        String password = "yourPassword";
        String expectedPassword = JsonUtils.getPassword();
        String actualPassword = "";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {

            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection established.");

            stmt = conn.createStatement();
            String sql = "SELECT password FROM tabke"; 
            rs = stmt.executeQuery(sql);
            System.out.println("SQL query executed.");

            if (rs.next()) {
                actualPassword = rs.getString("password");
                System.out.println("Password retrieved from database: " + actualPassword);
            } else {
                System.out.println("No data found in the database.");
            }

            Assert.assertNotEquals(actualPassword, expectedPassword, "Password should be encrypted in DB");
            System.out.println("Password validation passed.");

        } catch (SQLException e) {
            System.err.println("SQL exception occurred:");
            e.printStackTrace();
        } finally {
            // Close resources
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Database resources closed.");
        }
    }
}
