/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import static objects.AppointmentDB.convertToSystemDefault;
import static objects.AppointmentDB.convertToUTC;
import util.DBConnection;

/**
 *
 * @author Morgynn
 */
public class CustomerDB {
    
    
    public static ObservableList<Customer> getAllCustomers(){
        
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        
        //connect to DB
        Connection conn = DBConnection.startConnection();
        
        //select all records from customer and address table by doing an inner join
        String selectStatement = "SELECT customer.customerId, customer.customerName, customer.customerType, address.addressId, address.phone, address.address, city.cityId, city.city, address.postalCode FROM customer INNER JOIN address ON address.addressId = customer.addressId INNER JOIN city ON address.cityId = city.cityId";
        
            try {
                PreparedStatement ps = conn.prepareStatement(selectStatement);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                while(rs.next()){
                    String custName = rs.getString("customerName");
                    String address = rs.getString("address");
                    String phoneNum = rs.getString("phone");
                    int custId = rs.getInt("customerId");
                    int addressId = rs.getInt("addressId");
                    String zip = rs.getString("postalCode");
                    String city = rs.getString("city");
                    int cityId = rs.getInt("cityId");
                    String custType = rs.getString("customerType");
                    Customer cust;
                    if(custType == "New")
                        cust = new NewCustomer(custName, address, phoneNum, custId, addressId, zip, city, cityId);
                    else
                        cust = new ExistingCustomer(custName, address, phoneNum, custId, addressId, zip, city, cityId);
                    allCustomers.add(cust);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        return allCustomers;
        
    }
    
    public static ObservableList<Customer> searchCustomers(String searchItem){
        
        ObservableList<Customer> searchResults = FXCollections.observableArrayList();
        
        Connection conn = DBConnection.startConnection();
        
        String selectStatement = "SELECT customer.customerId, customer.customerName, customer.customerType, address.addressId, address.phone, address.address, city.cityId, city.city, address.postalCode FROM customer INNER JOIN address ON address.addressId = customer.addressId INNER JOIN city ON address.cityId = city.cityId WHERE customerName LIKE '%" + searchItem + "%'";
        
        try{
            PreparedStatement ps = conn.prepareStatement(selectStatement);
            ps.execute();
            ResultSet rs = ps.getResultSet();
                while(rs.next()){
                    String custName = rs.getString("customerName");
                    String address = rs.getString("address");
                    String phoneNum = rs.getString("phone");
                    int custId = rs.getInt("customerId");
                    int addressId = rs.getInt("addressId");
                    String zip = rs.getString("postalCode");
                    String city = rs.getString("city");
                    int cityId = rs.getInt("cityId");
                    String custType = rs.getString("customerType");
                    Customer cust;
                    if(custType == "New")
                        cust = new NewCustomer(custName, address, phoneNum, custId, addressId, zip, city, cityId);
                    else
                        cust = new ExistingCustomer(custName, address, phoneNum, custId, addressId, zip, city, cityId);
                    searchResults.add(cust);
                }
                
        } catch (SQLException e) {
                e.printStackTrace();
            }
        
        return searchResults;
        
    }
    
    public static boolean saveCustomer(String custName, String custAddress, String custPhone,String zip, String city, String custType){
        
        //connect to DB
        Connection conn = DBConnection.startConnection();
        
        //create city for customer
        String insertCityStatement = "INSERT INTO city(city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES('" + city + "', 2, NOW(), '', NOW(), '')";
        int cityId = 0;
        //excute statement
        try{
            PreparedStatement ps = conn.prepareStatement(insertCityStatement, Statement.RETURN_GENERATED_KEYS);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            cityId = rs.getInt(1);
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        
        //add customer address info to DB
        String insertAddressStatement = "INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES('" + custAddress + "', '', '" + cityId + "', '" + zip + "', '" + custPhone + "', NOW(), '', NOW(), '')";
        int addressId = 0;
        try{
            PreparedStatement ps = conn.prepareStatement(insertAddressStatement, Statement.RETURN_GENERATED_KEYS);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            addressId = rs.getInt(1);
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        

        
        //add customer info to DB
        String insertCustStatement = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy, customerType) VALUES( '" + custName + "' , " + addressId + ", 1, NOW(), '', NOW(), '', '" + custType + "')";
        
        //excute statement
        try{
            PreparedStatement ps = conn.prepareStatement(insertCustStatement);
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return true;
    }
    
    public static boolean updateCustomer(String custName, String address, String phone, int custId, int addressId, String zip, String city, int cityId, String custType){
        
         //connect to DB
        Connection conn = DBConnection.startConnection();
        
        //update city
        String updateCityStatement = "UPDATE city SET city = '" + city + "' WHERE cityId = " + cityId;
        
        //pass prepared statement to the DB
        try{
            PreparedStatement ps = conn.prepareStatement(updateCityStatement);
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        //update address
        String updateAddressStatement = "UPDATE address SET address = '" + address + "', postalCode = '" + zip + "', phone = '" + phone +  "' WHERE addressId = " + addressId;
        
        //pass prepared statement to the DB
        try{
            PreparedStatement ps = conn.prepareStatement(updateAddressStatement);
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        
        //Statement to save the new customer data in the database
        String updateCustStatement = "UPDATE customer SET customerName = '" + custName + "', customerType = '" + custType + "' WHERE customerId = " + custId;
        
        //pass prepared statement to the DB
        try{
            PreparedStatement ps = conn.prepareStatement(updateCustStatement);
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return true;
    }
    
    public static boolean deleteCustomer(int custId){
        
        //delete customer from the DB
        Connection conn = DBConnection.startConnection();
        String deleteStatement = "DELETE FROM customer WHERE customerId = " + custId;
        
        try{
            PreparedStatement ps = conn.prepareStatement(deleteStatement);
            ps.execute();
        } catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("You cannot delete this customer because they are still linked to one or more appointments");
            alert.showAndWait();
            e.printStackTrace();
        }
        
        return true;
    }
    
    
    public static ObservableList<String> getCustomerTypes(){
    
        ObservableList<String> customerTypes = FXCollections.observableArrayList();
        customerTypes.add("New");
        customerTypes.add("Existing");
        return customerTypes;
    
    }
    
}
