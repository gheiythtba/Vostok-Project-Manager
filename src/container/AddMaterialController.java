package container;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.Dataabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;    
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddMaterialController {

    @FXML
    private TextField AddMaterialCategory;

    @FXML
    private TextField AddMaterialID;

    @FXML
    private Label AddMaterialMessage;

    @FXML
    private Label AddMaterialMessage1;

    @FXML
    private TextField AddMaterialName;

    @FXML
    private TextField AddMaterialQuantity;

    @FXML
    private Button AddNewMaterialBtn1;

    @FXML
    private Button AddNewMaterialHomeBtn1;


    private Connection connect;
    private Dataabase D2 = new Dataabase();



    @FXML
    void AddNewMaterialBtn(ActionEvent event) throws IOException, ClassNotFoundException {
      System.out.println( "we are good here");
         try {
             connect = D2.connectDb();
             if (connect == null) {
                 System.out.println("Connection is not established!");
                 return;
             }
     
             int material_id = Integer.parseInt(AddMaterialID.getText().trim());
             String material_name = AddMaterialName.getText().trim();
             String material_category = AddMaterialCategory.getText().trim();
             int material_quantity = Integer.parseInt(AddMaterialQuantity.getText().trim());
     
             // Validate user input
             if (material_id <= 0 || material_name.isEmpty() || material_category.isEmpty() || material_quantity <= 0) {
                 AddMaterialMessage1.setText("Please enter all material information!");
                  AddMaterialMessage.setText("");

                 return;
             }

             if (isMaterialIDUsed(material_id)) {
               AddMaterialMessage.setText("Material ID already exists!");
               AddMaterialMessage1.setText("");

               return;
           }
   
     
             // Prepare and execute the insert statement
             PreparedStatement statement = connect.prepareStatement("INSERT INTO material (material_id, material_name, material_category, material_quantity) VALUES (?, ?, ?, ?)");
             statement.setInt(1, material_id);
             statement.setString(2, material_name);
             statement.setString(3, material_category);
             statement.setInt(4, material_quantity);
     
             int rowsAffected = statement.executeUpdate();
     
             if (rowsAffected == 1) {
                 AddMaterialMessage1.setText("Great! Material registration succeeded");
                 AddMaterialMessage.setText("");

                 clearInputFields();
             
                     Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/MaterialHome.fxml"));
                     Scene signUpScene = new Scene(signUpRoot);

                // Create a new stage for the sign-in interface
                     Stage signUpStage = new Stage();
                     signUpStage.initStyle(StageStyle.TRANSPARENT);
                     signUpStage.setScene(signUpScene);
                     signUpStage.show();

                // Close the splash screen stage
                     Stage splashSignInStage = (Stage) AddNewMaterialHomeBtn1.getScene().getWindow();
                     splashSignInStage.close();             }
                      else {
                        AddMaterialMessage1.setText("Failed to add material");
                        AddMaterialMessage.setText("");

                           }
         } catch (SQLException e) {
             e.printStackTrace();
             AddMaterialMessage1.setText("Error404 !!!!!!!");
         } 
     }
 
          private void clearInputFields() {
         AddMaterialID.setText("");
         AddMaterialName.setText("");
         AddMaterialCategory.setText("");
         AddMaterialQuantity.setText("");
            }

        private boolean isMaterialIDUsed(int materialId) throws SQLException {
        PreparedStatement checkStatement = connect.prepareStatement("SELECT material_id FROM material WHERE material_id = ?");
        checkStatement.setInt(1, materialId);
        ResultSet resultSet = checkStatement.executeQuery();
        return resultSet.next();
    }
    

    @FXML
    void AddNewMaterialHomeBtn(ActionEvent event) throws Exception{

      // Load the sign-Up interface
      Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/MaterialHome.fxml"));
      Scene signUpScene = new Scene(signUpRoot);
      
   
      // Create a new stage for the sign-in interface
      Stage signUpStage = new Stage();
      signUpStage.initStyle(StageStyle.TRANSPARENT);
      signUpStage.setScene(signUpScene);
      signUpStage.show();
   
      // Close the splash screen stage
      Stage splashSignInStage = (Stage) AddNewMaterialHomeBtn1.getScene().getWindow();
      splashSignInStage.close();
   }
}