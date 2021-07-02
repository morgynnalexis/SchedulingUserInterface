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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import objects.Appointment;
import objects.AppointmentDB;
import static objects.AppointmentDB.convertToSystemDefault;
import objects.Customer;
import objects.TypeDisplay;
import objects.User;
import objects.UserDB;
import util.DBConnection;

/**
 * FXML Controller class
 *
 * @author Morgynn
 */
public class GenerateReportsController implements Initializable {
    
    TypeDisplay td;
    
    @FXML
    private Button NoOfApptTypesBttn;

    @FXML
    private Button customersPerCountryBttn;
    
    @FXML
    private ComboBox<String> selectUserBox;
    
    @FXML
    private TableView<TypeDisplay> reportTableOne;
    
    @FXML
    private TableColumn<TypeDisplay, String> typeCol;

    @FXML
    private TableColumn<TypeDisplay, Integer> noCol;

    @FXML
    private TableView<Appointment> reportTableTwo;
    
    @FXML
    private TableColumn<Appointment, Integer> custIdCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableView<TypeDisplay> reportTableThree;
    
    @FXML
    private TableColumn<TypeDisplay, String> countryCol;

    @FXML
    private TableColumn<TypeDisplay, Integer> noCustsCol;
    
    

    @FXML
    private Button backBttn;

    @FXML
    public void generateApptSchedule(ActionEvent event) {
        
        ObservableList<Appointment> userAppts = FXCollections.observableArrayList();
        
        //connection to DB
        Connection conn = DBConnection.startConnection();
        
        //get userId
        String userName = selectUserBox.getValue();
        int userId = UserDB.getUserId(userName);
        
        //select all appointments with userId of selected username
        String selectStatement = "SELECT * FROM appointment WHERE userId = " + userId + " ORDER BY start";
        
        try {
                PreparedStatement ps = conn.prepareStatement(selectStatement);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                while(rs.next()){
                    int apptId = rs.getInt("appointmentId");
                    int custId = rs.getInt("customerId");
                    String title = rs.getString("title");
                    String desc = rs.getString("description");
                    String location = rs.getString("location");
                    String contact = rs.getString("contact");
                    String apptType = rs.getString("type");
                    String url = rs.getString("url");
                    LocalDateTime apptStart = rs.getTimestamp("start").toLocalDateTime();
                    apptStart = convertToSystemDefault(apptStart);
                    LocalDateTime apptEnd= rs.getTimestamp("end").toLocalDateTime();
                    apptEnd = convertToSystemDefault(apptEnd);
                    //Appointment appt = new Appointment(apptId, custId, userId, title, desc, location, contact, apptType, url, apptStart, apptEnd);
                    userAppts.add(new Appointment(apptId, custId, userId, title, desc, location, contact, apptType, url, apptStart, apptEnd));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        
        //set appointment list to table
        reportTableTwo.setItems(userAppts);

    }

    @FXML
    public void generateCustomersPerCountry(ActionEvent event) {
        
        Connection conn = DBConnection.startConnection();
        
        String selectStatement = "SELECT customer.customerId, customer.customerName, address.addressId, city.cityId, country.countryId, country.country FROM customer INNER JOIN address ON customer.addressId = address.addressId INNER JOIN city ON address.cityId = city.cityId INNER JOIN country ON city.countryId = country.countryId";
        
        int mexicoCount = 0, usaCount = 0, franceCount = 0;
        
        try{
            PreparedStatement ps = conn.prepareStatement(selectStatement);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                if(rs.getInt("countryId") == 1){
                    mexicoCount++;
                }else if(rs.getInt("countryId") == 2){
                    usaCount++;
                }else{
                    franceCount++;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        TypeDisplay td1 = new TypeDisplay("USA", usaCount);
        td1.addTypeDisplay(td1);
        TypeDisplay td2 = new TypeDisplay("Mexico", mexicoCount);
        td1.addTypeDisplay(td2);
        TypeDisplay td3 = new TypeDisplay("France", franceCount);
        td1.addTypeDisplay(td3);
        
        reportTableThree.setItems(td1.getAllTypeDisplay());

    }

    @FXML
    public void generateNoOfApptTypes(ActionEvent event) {
        
        Connection conn = DBConnection.startConnection();
        
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMonths(1);
        
        String selectStatement = "SELECT * FROM appointment WHERE start >= '" + start + "' AND end <= '" + end + "'";
        int noPolicy = 0, noEstimate = 0, noQuote = 0;
        
        try {
                PreparedStatement ps = conn.prepareStatement(selectStatement);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                while(rs.next()){
                    if(rs.getString("type").equals("quote")){
                        noQuote++;
                    }else if(rs.getString("type").equals("policy")){
                        noPolicy++;
                    } else{
                        noEstimate++;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        
        TypeDisplay td1 = new TypeDisplay("Quote", noQuote);
        td1.addTypeDisplay(td1);
        TypeDisplay td2 = new TypeDisplay("Policy", noPolicy);
        td1.addTypeDisplay(td2);
        TypeDisplay td3 = new TypeDisplay("Estimate", noEstimate);
        td1.addTypeDisplay(td3);
        
        reportTableOne.setItems(td1.getAllTypeDisplay());

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


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        selectUserBox.setItems(UserDB.getAllUsernames());
        
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        noCol.setCellValueFactory(new PropertyValueFactory<>("noOfType"));
        
        countryCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        noCustsCol.setCellValueFactory(new PropertyValueFactory<>("noOfType"));
    }    
    
}
