/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulinguserinterface;

import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Appointment;
import objects.AppointmentDB;
import static objects.AppointmentDB.convertToSystemDefault;
import static objects.AppointmentDB.getAllCustomerIds;
import util.DBConnection;

/**
 * FXML Controller class
 *
 * @author Morgynn
 */
public class UpdateAppointmentController implements Initializable {
    
    private Appointment appt;

    @FXML
    private TextField titleField;

    @FXML
    private TextField contactField;

    @FXML
    private Button saveBttn;

    @FXML
    private Button cancelBttn;

    @FXML
    private TextField descField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField urlField;

    @FXML
    private TextField startField;

    @FXML
    private TextField endField;

    @FXML
    private TextField dateField;

    @FXML
    private ComboBox<Integer> custIdBox;

    @FXML
    private ComboBox<String> typeBox;
    
    @FXML
    private Label alertLabel;



    @FXML
    public void cancel(ActionEvent event) throws IOException {
        
        //cancel updating appointment and go back to previous page 
        Stage stage;
        Parent root;
        stage = (Stage) cancelBttn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AppointmentDBView.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void saveAppt(ActionEvent event) throws IOException {
        
        this.appt = appt;
        LocalTime businessStart = LocalTime.of(9, 0);
        LocalTime businessEnd = LocalTime.of(17, 0);
        boolean inBusinessHours = false;
        boolean isValid = true;
        boolean notOverlapping = true;
        
        int apptId = appt.getApptId();
        int custId = custIdBox.getValue();
        int userId = 1;
        
        if(titleField.getText().trim().isEmpty()){
            isValid = false;
        }
        String title = titleField.getText();
        
        if(descField.getText().trim().isEmpty()){
            isValid = false;
        }
        String desc = descField.getText();
        
        if(locationField.getText().trim().isEmpty()){
            isValid = false;
        }
        String location = locationField.getText();
        
        if(contactField.getText().trim().isEmpty()){
            isValid = false;
        }
        String contact = contactField.getText();
        
        String apptType = typeBox.getValue();
        
        if(urlField.getText().trim().isEmpty()){
            isValid = false;
        }
        String url = urlField.getText();
        
        if(startField.getText().trim().isEmpty()){
            isValid = false;
        }
        LocalTime apptStart = LocalTime.parse(startField.getText());
        
        if(endField.getText().trim().isEmpty()){
            isValid = false;
        }
        LocalTime apptEnd = LocalTime.parse(endField.getText());
        
        //check if appointment time is within business hours
        if(!((apptEnd.isBefore(businessStart) || apptEnd.isAfter(businessEnd)) && (apptStart.isBefore(businessStart) || apptStart.isAfter(businessEnd)))){
            inBusinessHours = true;
        }
        LocalDate date = LocalDate.parse(dateField.getText());
        
        //check for overlapping appointment
        if(AppointmentDB.checkOverlap(apptStart, date)){
            notOverlapping = false;
        }
        if(AppointmentDB.checkOverlap(apptEnd, date)){
            notOverlapping = false;
        }
        
        if(inBusinessHours && isValid && notOverlapping){
            //add updated info to appointment DB
            AppointmentDB.updateAppointment(apptId, custId, userId, title, desc, location, contact, apptType, url, LocalDateTime.of(date, apptStart), LocalDateTime.of(date, apptEnd));

            //go back to previous page
            Stage stage;
            Parent root;
            stage = (Stage) cancelBttn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AppointmentDBView.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if(!inBusinessHours){
            //let user know the appointment times entered are not within business hours
            alertLabel.setText("The appointment time you entered is not within business hours");
        } else if(!notOverlapping){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Overlap Alert");
            alert.setContentText("The appointment time conflicts with another appointment");
            alert.show();
        } else {
            alertLabel.setText("Enter a valid value in every field");
        }
    }
    
    public void setAppt(Appointment appt){
        
        //set values with selected appointment
        this.appt = appt;
        
        int custId = appt.getCustId();
        custIdBox.setValue(custId);
        
        String title = appt.getTitle();
        titleField.setText(title);
        
        String desc = appt.getDescription();
        descField.setText(desc);
        
        String location = appt.getLocation();
        locationField.setText(location);
        
        String contact = appt.getContact();
        contactField.setText(contact);
        
        String type = appt.getType();
        typeBox.setValue(type);
        
        String url = appt.getURL();
        urlField.setText(url);
        
        LocalDateTime apptStart = appt.getApptStart();
        
        LocalDate date = apptStart.toLocalDate();
        String dateString = date.toString();
        dateField.setText(dateString);
        
        LocalTime startTime = apptStart.toLocalTime();
        String startString = startTime.toString();
        startField.setText(startString);
        
        LocalDateTime apptEnd = appt.getApptEnd();
        LocalTime endTime = apptEnd.toLocalTime();
        String endString = endTime.toString();
        endField.setText(endString);
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        custIdBox.setItems(getAllCustomerIds());
        typeBox.getItems().addAll("policy", "quote", "estimate");
    }    
    
}
