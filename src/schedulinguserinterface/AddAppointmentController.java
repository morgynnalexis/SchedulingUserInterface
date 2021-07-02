/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulinguserinterface;

import java.io.IOException;
import java.net.URL;
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
import static objects.AppointmentDB.getAllCustomerIds;
import objects.User;

/**
 * FXML Controller class
 *
 * @author Morgynn
 */
public class AddAppointmentController implements Initializable {
    
    Appointment appt;
    User user;
    
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
    void cancel(ActionEvent event) throws IOException {
        
        //cancel adding new appointment action and go back to previous page
        
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
    void saveAppt(ActionEvent event) throws IOException {
        
        this.appt = appt;
        LocalTime businessStart = LocalTime.of(9, 0);
        LocalTime businessEnd = LocalTime.of(17, 0);
        boolean inBusinessHours = false;
        boolean isValid = true;
        boolean notOverlapping = true;
        
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
        
        if(dateField.getText().trim().isEmpty()){
            isValid = false;
        }
        String date = dateField.getText();
        
        if(startField.getText().trim().isEmpty()){
            isValid = false;
        }
        String start = startField.getText();
        
        if(endField.getText().trim().isEmpty()){
            isValid = false;
        }
        String end = endField.getText();
        
        LocalDate apptDate = LocalDate.parse(date);
        LocalTime apptStart = LocalTime.parse(start);
        LocalTime apptEnd = LocalTime.parse(end);
        
        //check for overlapping appointment
        if(AppointmentDB.checkOverlap(apptStart, apptDate)){
            notOverlapping = false;
        }
        if(AppointmentDB.checkOverlap(apptEnd, apptDate)){
            notOverlapping = false;
        }
        
        //check if appointment is within business hours
        if(!((apptEnd.isBefore(businessStart) || apptEnd.isAfter(businessEnd)) && (apptStart.isBefore(businessStart) || apptStart.isAfter(businessEnd)))){
            inBusinessHours = true;
        }
        
        if(inBusinessHours && isValid && notOverlapping){
            //add appointment to DB 
            AppointmentDB.saveAppointment(custId, userId, title, desc, location, contact, apptType, url, LocalDateTime.of(apptDate, apptStart), LocalDateTime.of(apptDate, apptEnd));

            //go back to previous page

            Stage stage;
            Parent root;
            stage = (Stage) saveBttn.getScene().getWindow();
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
