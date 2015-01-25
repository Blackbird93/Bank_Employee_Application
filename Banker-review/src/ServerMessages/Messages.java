/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerMessages;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 *
 * @author victor
 */
public class Messages {
    private static String host = "192.168.154.227";
    private static int port = 9999;
    
    // suzdavane na getters and setters 

    public static String getHost() {
        return host;
    }

    public static void setHost(String aHost) {
        host = aHost;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int aPort) {
        port = aPort;
    }
    
    // statichniq metod za vruzka sus server-a
    public static class Svurzvane{ 
        public static Socket connection;
        public static void  doConnection(){
            try {
                 connection = new Socket(host,port);
            } catch (Exception ex) {
                Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
            }
         
        }
     }               
}   

