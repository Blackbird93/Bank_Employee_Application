/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 * @author victor
 */
public class NewClass {

    public static class Vrazka {

        public static Connection con;
        public static Statement st;
        public static PreparedStatement pst;
        
        // statichniq metod za connection-a
        public static void connectMethod() {
            try {
                // zarejdane na driver-a
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                // dannite za hosta na bazata danni
                con = DriverManager.getConnection("jdbc:sqlserver://pparvanov.pppoe.optic-com.eu:1433;DatabaseName=e-banking;user=cscb-java;Password=cscb024");
                st = con.createStatement();
                
                Vrazka.pst = pst;
                Vrazka.st = st;
                Vrazka.con = con;
            } catch (Exception ex) {
          
            }
        }
    }
}
