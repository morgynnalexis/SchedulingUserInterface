/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulinguserinterface;

import com.mysql.jdbc.Connection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.DBConnection;
import util.DBQuery;
import objects.User;

/**
 *
 * @author Morgynn
 */
public class LoginPageController implements Initializable {
    
    User user;
    
    @FXML
    private Button loginBttn;

    @FXML
    private Label pwLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label errorMessage;
    
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField pwField;
    
    
    

    @FXML
    void Login(ActionEvent event) throws IOException, SQLException {
        
        //get info entered by user
        String username = usernameField.getText();
        String pass = pwField.getText();
        
        //start connection
        Connection conn = DBConnection.startConnection();
        
        //get username and password data from DB
        String selectStatement = "SELECT * FROM user";
        
        //set prepared statement
        DBQuery.setPreparedStatement(conn, selectStatement);
        
        //get prepared statement reference and execute it
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        
        //get results
        ResultSet rs = ps.getResultSet();
        int userId = 0;
        boolean gotUser = false;
        String dbUsername = "";
        while(rs.next()){
            
            dbUsername = rs.getString("userName");
            String dbPw = rs.getString("password"); 
            userId = rs.getInt("userId");

            if(username.equals(dbUsername) && pass.equals(dbPw)){
                gotUser = true;
                
            }
        }
        if(gotUser){
                //check if there is an appointment within the next 15 minutes
                String checkAppt = "SELECT * FROM appointment WHERE start >= NOW() AND start <= (NOW() + INTERVAL 15 MINUTE) AND userId = " + userId;
                try{
                    PreparedStatement ps2 = conn.prepareStatement(checkAppt);
                    ps2.execute();
                    ResultSet rs2 = ps2.getResultSet();
                    if(rs2.next()){
                        //alert the user of the appointment
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Appointment Alert");
                        ResourceBundle rb = ResourceBundle.getBundle("resources/Nat");
                        alert.setContentText(rb.getString("APPT_ALERT"));
                        alert.show();
                    }
                    
                } catch(SQLException e){
                    e.printStackTrace();
                }
                
        //record users login to text file
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(new File("LoginRecord.txt"),true));
            LocalDateTime today = LocalDateTime.now();
            pw.append("Login time: " + today + ", User: " + dbUsername + "\n");
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
                
                //open the next page if uesrname and password are correct
                Stage stage;
                Parent root;
                stage = (Stage) loginBttn.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SelectView.fxml"));
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            
        }else{
                try{
                    //Get default language on comp
                    ResourceBundle rb = ResourceBundle.getBundle("resources/Nat");

                    //check which language to display tex
                    errorMessage.setText(rb.getString("LOGIN_ERR"));
                } catch(Exception e){
                    e.printStackTrace();
                }
        }       
               

    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        try{
        //Get default language on comp
        rb = ResourceBundle.getBundle("resources/Nat");

        //check which language to display text in
        
            pwLabel.setText(rb.getString("PASSWORD"));
            usernameLabel.setText(rb.getString("USERNAME"));
            loginBttn.setText(rb.getString("LOGIN"));
    
        } catch(Exception e){
            e.printStackTrace();
        }
    }    
    
}
