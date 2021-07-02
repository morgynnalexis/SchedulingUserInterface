/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Morgynn
 */
public class DBConnection {
    
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//3.227.166.251/U06Mwl";
    
    //JBC url
    private static final String jdbcUrl = protocol + vendorName + ipAddress;
    
    //Driver interface
    private static final String MYSQLJBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;
    
    //username and password 
    private static final String userName = "U06Mwl";
    private static final String passWord = "53688806881";
    
    //start connection
    public static Connection startConnection(){
        
        if(conn != null){
            return conn;
        }
        try{
            
            Class.forName(MYSQLJBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcUrl, userName, passWord);
            System.out.println("Connection successful");
        } catch(ClassNotFoundException e){
            
            System.out.println(e.getMessage());
        } catch(SQLException e){
            
            System.out.println(e.getMessage());
        }
        
        return conn;
        
        
    }
    
    //close connection
    public static void closeConnection() {
        
        try{
            conn.close();
            System.out.println("Connection closed");
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
    
}
