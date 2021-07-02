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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schedulinguserinterface.CalendarViewController;
import util.DBConnection;
import util.DBQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javafx.scene.control.Alert;

/**
 *
 * @author Morgynn
 */
public class AppointmentDB {
    
    public static ObservableList<Appointment> getAllAppts(){ 
        
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        
        //connect to DB
        Connection conn = DBConnection.startConnection();
        
        //select all records from appointment table
        String selectStatement = "SELECT * FROM appointment ORDER BY start";
        
            try {
                PreparedStatement ps = conn.prepareStatement(selectStatement);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                while(rs.next()){
                    int apptId = rs.getInt("appointmentId");
                    int custId = rs.getInt("customerId");
                    int userId = rs.getInt("userId");
                    String title = rs.getString("title");
                    String desc = rs.getString("description");
                    String location = rs.getString("location");
                    String contact = rs.getString("contact");
                    String apptType = rs.getString("type");
                    String url = rs.getString("url");
                    LocalDateTime apptStart = rs.getTimestamp("start").toLocalDateTime();
                    apptStart = convertToSystemDefault(apptStart);
                    LocalDateTime apptEnd= rs.getTimestamp("end").toLocalDateTime();
                    apptEnd = convertToSystemDefault(apptEnd);
                    Appointment appt = new Appointment(apptId, custId, userId, title, desc, location, contact, apptType, url, apptStart, apptEnd);
                    allAppointments.add(appt);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        return allAppointments;
    }
    
    public static ObservableList<Integer> getAllCustomerIds(){
        
        //get all customers in the DB
        
        ObservableList<Integer> allCustomerIds = FXCollections.observableArrayList();
        
        Connection conn = DBConnection.startConnection();
        
        String selectStatement = "SELECT customerId FROM customer";
        try{
            PreparedStatement ps = conn.prepareStatement(selectStatement);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                int custId = rs.getInt("customerId");
                allCustomerIds.add(custId);
            }
            
        } catch(SQLException e){
            e.printStackTrace();
        }
        
        return allCustomerIds;
        
    }
    
    public static boolean saveAppointment(int custId, int userId, String title, String desc, String location, String contact, String apptType, String url, LocalDateTime apptStart, LocalDateTime apptEnd){
        
        //connect to DB
        Connection conn = DBConnection.startConnection();
        
        //convert times back to UTC before saving in DB
        apptStart = convertToUTC(apptStart);
        apptEnd = convertToUTC(apptEnd);
        
        String insertStatement = "INSERT INTO appointment (customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES( " + custId + " , " + userId + ", '" + title + "', '" + desc + "', '" + location + "', '" + contact + "', '" + apptType + "', '" + url + "', '" + apptStart + "', '" + apptEnd + "', NOW(), '', NOW(), '')";
        
        //excute statement
        try{
            PreparedStatement ps = conn.prepareStatement(insertStatement);
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return true;
    }
    
    public static boolean updateAppointment(int apptId, int custId, int userId, String title, String desc, String location, String contact, String apptType, String url, LocalDateTime apptStart, LocalDateTime apptEnd){
        
         //connect to DB
        Connection conn = DBConnection.startConnection();
        
        //convert times back to UTC before saving in DB
        apptStart = convertToUTC(apptStart);
        apptEnd = convertToUTC(apptEnd);
        
        //Statement to save the new appointment data in the database
        String updateStatement = "UPDATE appointment SET customerId = " + custId + ", userId = " + userId + ", title = '" + title + "', description = '" + desc + "', location = '" + location + "', contact = '" + contact + "', type = '" + apptType + "', url = '" + url + "', start = '" + apptStart + "', end = '" + apptEnd + "',createdBy = '', createDate = NOW(), lastUpdate = NOW(), lastUpdateBy = " + userId + " WHERE appointmentId = " + apptId;
        
        //pass prepared statement to the DB
        try{
            PreparedStatement ps = conn.prepareStatement(updateStatement);
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return true;
    }
    
    public static boolean deleteAppointment(int apptId){
        //delte appointment from the DB
        
        Connection conn = DBConnection.startConnection();
        String deleteStatement = "DELETE FROM appointment WHERE appointmentId = " + apptId;
        
        try{
            PreparedStatement ps = conn.prepareStatement(deleteStatement);
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return true;
    }
    
    public static LocalDateTime convertToSystemDefault(LocalDateTime ldt){
        //convert to system default time 
        
        ZoneId zi = ZoneId.of("UTC");
        ZonedDateTime zdt = ldt.atZone(zi);
        ZoneId zsd = ZoneId.systemDefault();
        ZonedDateTime zdti = zdt.withZoneSameInstant(zsd);
        ldt = zdti.toLocalDateTime();

        return ldt;
    }

    
    public static LocalDateTime convertToUTC(LocalDateTime ldt){
        //convert to UTC time
        
        ZoneId zi = ZoneId.systemDefault();
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zi);
        ZoneId zutc = ZoneId.of("UTC");
        ZonedDateTime zdti = zdt.withZoneSameInstant(zutc);
        ldt = zdti.toLocalDateTime();
        return ldt;
    }
    
    public static boolean checkOverlap(LocalTime time, LocalDate date){
        boolean isOverlap = true;
        LocalDateTime dateTime = convertToUTC(LocalDateTime.of(date, time));
        String selectStatement = "SELECT * FROM appointment WHERE '" + dateTime + "' between start AND end";
        Connection conn = DBConnection.startConnection();
        try{
            PreparedStatement ps = conn.prepareStatement(selectStatement);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if(rs.next() == false)
                isOverlap = false;
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return isOverlap;
    }
}
