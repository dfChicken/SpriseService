/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

/**
 *
 * @author dfChicken
 */
import static dal.UserData.registerUser;
import util.Constants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class DBConnection {

    /**
     * Method to create DB Connection
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("finally")
    public static Connection createConnection() throws Exception {
        Connection con = null;
        try {
            Class.forName(Constants.dbClass);
            con = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPwd);
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        } finally {
            return con;
        }
    }

//    public static void main(String[] args) throws SQLException, Exception {
//        long time = System.currentTimeMillis();
//        java.sql.Date date = new java.sql.Date(time);
//        java.sql.Timestamp timestmp = new java.sql.Timestamp(time);
//
//        System.out.println(timestmp.toString());
//        System.out.println(registerUser("coinCard", "promo.chicken@gmail.com", "123", "Card", "Coin", "Hay lam!", 0, 1, 1, timestmp, timestmp));
////        System.out.println(registerUser("dfChicken", "hainam.4795@gmail.com", "123", "Nam", "Nguyen", "Hay lam!", 0, 1, 1, timestmp, timestmp));
////        System.out.println(checkLogin("admin@gmail.com", "123"));
//    }
}
