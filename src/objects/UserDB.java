/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static objects.AppointmentDB.convertToSystemDefault;
import util.DBConnection;

/**
 *
 * @author Morgynn
 */
public class UserDB {
    
    
    public static String getUsername(int userId){
        
        //connect to DB
        Connection conn = DBConnection.startConnection();
        
        //select all records from appointment table
        String selectStatement = "SELECT userName FROM user WHERE userId = " + userId + ";";
        String username = "";
        
            try {
                PreparedStatement ps = conn.prepareStatement(selectStatement);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                username = rs.getString("userName");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return username;
    }
    
    public static int getUserId(String userName){
        
        //connect to DB
        Connection conn = DBConnection.startConnection();
        
        //select all records from appointment table
        String selectStatement = "SELECT userId FROM user WHERE userName = '" + userName + "'";
        int userId = 0;
        
            try {
                PreparedStatement ps = conn.prepareStatement(selectStatement);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                rs.next();
                userId = rs.getInt("userId");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return userId;
    }
    
    public static ObservableList<String> getAllUsernames(){
        
        Connection conn = DBConnection.startConnection();
        
        String selectStatement = "SELECT * FROM user";
        ObservableList<String> allUsernames = FXCollections.observableArrayList();
        
        try{
            PreparedStatement ps = conn.prepareStatement(selectStatement);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                String userName = rs.getString("userName");
                allUsernames.add(userName);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return allUsernames;
    }

    
}
