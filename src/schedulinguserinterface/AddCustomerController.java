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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.AppointmentDB;
import objects.Customer;
import objects.CustomerDB;

/**
 * FXML Controller class
 *
 * @author Morgynn
 */
public class AddCustomerController implements Initializable {
    
    Customer cust;
    
    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button saveBttn;

    @FXML
    private Button cancelBttn;

    @FXML
    private TextField cityField;

    @FXML
    private TextField zipField;

    @FXML
    private Label errorLabel;
    
    @FXML
    private ComboBox<String> typeField;
    
    @FXML
    void cancel(ActionEvent event) throws IOException {
        //go back to previous page
        
        Stage stage;
        Parent root;
        stage = (Stage) cancelBttn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CustomerDBView.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void saveCust(ActionEvent event) throws IOException {
        
        this.cust = cust;
        
        //marker for valid data
        boolean isValid = true;
        
        if(nameField.getText().trim().isEmpty()){
            isValid = false;
        }
        String custName = nameField.getText();
        
        if(addressField.getText().trim().isEmpty()){
            isValid = false;
        }
        String address = addressField.getText();
        
        if(phoneField.getText().trim().isEmpty()){
            isValid = false;
        }
        String phone = phoneField.getText();
        
        if(cityField.getText().trim().isEmpty()){
            isValid = false;
        }
        String city = cityField.getText();
        
        if(zipField.getText().trim().isEmpty()){
            isValid = false;
        }
        String zip = zipField.getText();
        
        String custType = typeField.getValue();
        
        if(isValid){
            //save customer to DB
            CustomerDB.saveCustomer(custName, address, phone, zip, city, custType);

            //go back to previous page

            Stage stage;
            Parent root;
            stage = (Stage) saveBttn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CustomerDBView.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();     
        } else {
            errorLabel.setText("Enter a valid value in every field");
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        typeField.setItems(CustomerDB.getCustomerTypes());
    }    
    
}
