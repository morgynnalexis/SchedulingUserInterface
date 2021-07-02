/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulinguserinterface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Morgynn
 */
public class SelectViewController implements Initializable {

    @FXML
    private Button viewCustBttn;

    @FXML
    private Button viewCalBttn;

    @FXML
    private Button logoutBttn;

    @FXML
    private Button viewApptsBttn;
    
    @FXML
    private Button generateReportsBttn;
   

    @FXML
    void ViewCal(ActionEvent event) throws IOException {
        
        Stage stage;
        Parent root;
        stage = (Stage) viewCalBttn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CalendarView.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void ViewCust(ActionEvent event) throws IOException {
        
        Stage stage;
        Parent root;
        stage = (Stage) viewCustBttn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CustomerDBView.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        
        Stage stage;
        Parent root;
        stage = (Stage) logoutBttn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginPage.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void viewAppts(ActionEvent event) throws IOException {
        
        Stage stage;
        Parent root;
        stage = (Stage) viewApptsBttn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AppointmentDBView.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    
    @FXML
    void viewReports(ActionEvent event) throws IOException {
        
        Stage stage;
        Parent root;
        stage = (Stage) generateReportsBttn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GenerateReports.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
