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
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import objects.Appointment;
import objects.AppointmentDB;
import util.DBConnection;
import util.DBQuery;

/**
 * FXML Controller class
 *
 * @author Morgynn
 */
public class CalendarViewController implements Initializable {

    @FXML
    private Button weekViewBttn;

    @FXML
    private Button monthViewBttn;

    @FXML
    private Button backBttn;

    @FXML
    private TableView<Appointment> apptCalTable;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, String> apptStartTimeCol;

    @FXML
    private TableColumn<Appointment, String> apptEndTimeCol;

    @FXML
    private TableColumn<Appointment, String> custIdCol;

    @FXML
    private ObservableList<Appointment> appointmentsList = AppointmentDB.getAllAppts();

    @FXML
    void displayMonth(ActionEvent event) {
        
        LocalDateTime today = LocalDateTime.now();
        //get a list of appointments within the same month and year
        ObservableList<Appointment> monthList = appointmentsList.filtered(a -> {
            if (a.getApptStart().getYear() == today.getYear() && a.getApptStart().getMonth() == today.getMonth()) {
                return true;
            }
            return false;
        });

        apptCalTable.setItems(monthList);

    }

    @FXML
    void displayWeek(ActionEvent event) {
        
        LocalDateTime today = LocalDateTime.now();
        //get a list of appointments within 7 days of the current day
        LocalDateTime weekEnd = today.plusDays(7);
        ObservableList<Appointment> weekList = appointmentsList.filtered(a -> {
            if(a.getApptStart().isAfter(today) && a.getApptStart().isBefore(weekEnd)){
                return true;
            }
            return false;
        });

        apptCalTable.setItems(weekList);

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        apptEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));

        displayMonth(null);

    }

}
