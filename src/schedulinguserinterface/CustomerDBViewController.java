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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import objects.Customer;
import objects.CustomerDB;

/**
 * FXML Controller class
 *
 * @author Morgynn
 */
public class CustomerDBViewController implements Initializable {
    
    Customer cust;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private Button addCustBttn;

    @FXML
    private Button updateCustBttn;

    @FXML
    private Button delCustBttn;

    @FXML
    private Button backBttn;
    
    @FXML
    private TextField searchBox;
    
    @FXML
    private ObservableList<Customer> searchResults = FXCollections.observableArrayList();

    @FXML
    void addCust(ActionEvent event) throws IOException {
        
        Stage stage;
        Parent root;
        stage = (Stage) addCustBttn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddCustomer.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void delCust(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure you want to delete this customer from the database?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            int selectedCustId = selectedCustomer.getCustId();
            CustomerDB.deleteCustomer(selectedCustId);
            customerTable.setItems(CustomerDB.getAllCustomers());
        }

    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        
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
    void updateCust(ActionEvent event) throws IOException {
        
        Stage stage;
        Parent root;
        stage = (Stage) updateCustBttn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UpdateCustomer.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        //send selected part to next page
        UpdateCustomerController controller = loader.getController();
        cust = customerTable.getSelectionModel().getSelectedItem();
        controller.setCust(cust);

    }
    
    @FXML
    void searchCustomers(ActionEvent event) {

        String searchItem = searchBox.getText();
        
        searchResults = CustomerDB.searchCustomers(searchItem);
        customerTable.setItems(searchResults);
        
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("custPhone"));
        
        customerTable.setItems(CustomerDB.getAllCustomers());
    }    
    
}
