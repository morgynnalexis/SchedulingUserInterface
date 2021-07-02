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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Customer;
import objects.CustomerDB;
import objects.ExistingCustomer;
import objects.NewCustomer;

/**
 * FXML Controller class
 *
 * @author Morgynn
 */
public class UpdateCustomerController implements Initializable {
    
    Customer cust;
    
    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField zipField;
    
    @FXML
    private Button saveBttn;

    @FXML
    private Button cancelBttn;
    
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
        
        //this.cust = cust;
        int custId, addressId, cityId;
        
        //marker for valid data
        boolean isValid = true;
        
        String custType = typeField.getValue();
        
        custId = cust.getCustId();
        addressId = cust.getAdressId();
        cityId = cust.getCityId();
        
        //get information user entered
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

        if(zipField.getText().trim().isEmpty()){
            isValid = false;
        }
        String zip = zipField.getText();
        
        if(cityField.getText().trim().isEmpty()){
            isValid = false;
        }
        String city = cityField.getText();
        
        
        if(isValid){
            //update customer
            CustomerDB.updateCustomer(custName, address, phone, custId, addressId, zip, city, cityId, custType);
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
    
    public void setCust(Customer cust){
        
        //set info of selected customer
        this.cust = cust;
        
        if(typeField.getSelectionModel().toString() == "New"){
            
            NewCustomer castCust = (NewCustomer) cust;
            
            String custName = castCust.getCustName();
            nameField.setText(custName);

            String address = castCust.getCustAddress();
            addressField.setText(address);

            String phone = castCust.getCustPhone();
            phoneField.setText(phone);

            String city = castCust.getCity();
            cityField.setText(city);

            String zip = castCust.getZip();
            zipField.setText(zip);

            typeField.setItems(CustomerDB.getCustomerTypes());
            typeField.getSelectionModel().select(castCust.getStatus());
            
        } else {
            
            ExistingCustomer castCust = (ExistingCustomer) cust;
            
            String custName = castCust.getCustName();
            nameField.setText(custName);

            String address = castCust.getCustAddress();
            addressField.setText(address);

            String phone = castCust.getCustPhone();
            phoneField.setText(phone);

            String city = castCust.getCity();
            cityField.setText(city);

            String zip = castCust.getZip();
            zipField.setText(zip);

            typeField.setItems(CustomerDB.getCustomerTypes());
            typeField.getSelectionModel().select(castCust.getStatus());
            
        }
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
