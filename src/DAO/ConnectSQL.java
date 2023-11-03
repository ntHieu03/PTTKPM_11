/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ConnectSQL {
    public static String databaseName = "DoAnCoSoQLTCF";
    public static Connection getConnection() {
    String url = "net.sourceforge.jtds.jdbc.Driver";
        try {
            Class.forName(url);
            String dbUrl = "jdbc:jtds:sqlserver://localhost:1433/DoAnCoSoQLTCF";
            return DriverManager.getConnection(dbUrl,"sa","1234");
//          return DriverManager.getConnection(dbUrl,user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnectSQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public static void main(String[] args) {
        ConnectSQL.getConnection();
    }
}