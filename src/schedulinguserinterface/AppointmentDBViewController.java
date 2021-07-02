/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulinguserinterface;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import objects.Appointment;
import objects.AppointmentDB;
import objects.Customer;
import objects.CustomerDB;

/**
 * FXML Controller class
 *
 * @author Morgynn
 */
public class AppointmentDBViewController implements Initializable {
    
    Appointment appt;
    
    @FXML
    private TableView<Appointment> apptTable;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, String> custIdCol;

    @FXML
    private TableColumn<Appointment, String> startTimeCol;
    
    @FXML
    private TableColumn<Appointment, String> endTimeCol;

    @FXML
    private Button addApptBttn;

    @FXML
    private Button updateApptBttn;

    @FXML
    private Button delApptBttn;

    @FXML
    private Button backBttn;

    @FXML
    void addAppt(ActionEvent event) throws IOException {
        
        Stage stage;
        Parent root;
        stage = (Stage) addApptBttn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddAppointment.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void delAppt(ActionEvent event) {
        
        //delete appointment from the DB
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure you want to delete this appointment from the database?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Appointment selectedAppt = apptTable.getSelectionModel().getSelectedItem();
            int selectedApptId = selectedAppt.getApptId();
            AppointmentDB.deleteAppointment(selectedApptId);
            apptTable.setItems(AppointmentDB.getAllAppts());
        }

    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        
        //go back to previous page
        Stage stage;
        Parent root;
        stage = (Stage) backBttn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SelectView.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void updateAppt(ActionEvent event) throws IOException {
        
        //take user to update appointment page
        Stage stage;
        Parent root;
        stage = (Stage) updateApptBttn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UpdateAppointment.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        //send selected part to next page
        UpdateAppointmentController controller = loader.getController();
        appt = apptTable.getSelectionModel().getSelectedItem();
        controller.setAppt(appt);
            
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
        
        apptTable.setItems(AppointmentDB.getAllAppts());
    }    
    
}
