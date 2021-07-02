/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulinguserinterface;

import com.mysql.jdbc.Connection;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DBConnection;

/**
 *
 * @author Morgynn
 */
public class SchedulingUserInterface extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/LoginPage.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Locale.setDefault(new Locale("en"));
        
        //connect to DB
        DBConnection.startConnection();
        
        launch(args);
        
        //close db connection
        DBConnection.closeConnection();
        
    }
    
}
