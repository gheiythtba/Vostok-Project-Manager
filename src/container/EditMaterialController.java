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

public class EditMaterialController {

      @FXML
    private Button EditMaterialBackBtn0;

    private int materialId; // Store the user id here


    @FXML
    private Button EditMaterialEditBtn0;

    @FXML
    private TextField EditMaterialFieldcategory;

    @FXML
    private TextField EditMaterialFieldid;

    @FXML
    private TextField EditMaterialFieldname;

    @FXML
    private TextField EditMaterialFieldquantity;

    @FXML
    private Button EditMaterialSearchBtn0;

    @FXML
    private Label EditMaterialMessage;

    @FXML
    private Label EditMaterialMessage1;

        private Connection connect;
        private Dataabase D1 = new Dataabase();
        private ResultSet Ls;


    @FXML
    void EditMaterialBackBtn(ActionEvent event) throws Exception{
      
      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/MaterialHome.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) EditMaterialSearchBtn0.getScene().getWindow();
   splashSignInStage.close();
}




 @FXML
 void EditMaterialEditBtn(ActionEvent event) throws IOException {
     try {
            connect = D1.connectDb();
            if (connect == null) {
                System.out.println("Connection is not established!");
                return;
            }

              // Check if username or password fields are empty
            String material_name = EditMaterialFieldname.getText().trim();
            String material_category = EditMaterialFieldcategory.getText().trim();
            int material_quantity = Integer.parseInt(EditMaterialFieldquantity.getText().trim());

           

            if ( material_name.isEmpty() ||material_category.isEmpty() || material_quantity <= 0 ){
                EditMaterialMessage1.setText("Please enter all the material informations!");

                return;
            }

            PreparedStatement statement  = connect.prepareStatement("UPDATE material SET material_name=?,material_category=?,material_quantity=? WHERE material_id =?");
           
            statement.setString(1, material_name);
            statement.setString(2, material_category);
            statement.setInt(3, material_quantity);
            statement.setInt(4, materialId); // Use the stored id value

           
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected==1)
            {
                EditMaterialFieldname.setText("");
                EditMaterialFieldcategory.setText("");
                EditMaterialFieldquantity.setText("");
         

                EditMaterialMessage.setText("Sucess !! User has been updaeted");


                    // Load the sign-in interface
                Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/MaterialHome.fxml"));
                Scene signInScene = new Scene(signInRoot);

                    // Create a new stage for the sign-in interface
                Stage signInStage = new Stage();
                signInStage.initStyle(StageStyle.TRANSPARENT);
                signInStage.setScene(signInScene);
                signInStage.show();

                    // Close the splash screen stage
                Stage splashSignUpStage = (Stage) EditMaterialEditBtn0.getScene().getWindow();
                splashSignUpStage.close();

            
                  }
            
            else
            {
                EditMaterialMessage.setText("Updated with success");


    
              }        } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

    }



    @FXML
    void EditMaterialSearchBtn(ActionEvent event) {
 try {
            connect = D1.connectDb();
            if (connect == null) {
                System.out.println("Connection is not established!");
                return;
            }




            String idString = EditMaterialFieldid.getText().trim();
            if (idString.isEmpty()) {
                EditMaterialMessage1.setText("Please enter a valid material ID!");
                
                return;
            }


            int material_id;
              try {
                  material_id = Integer.parseInt(idString);
              } catch (NumberFormatException e) {
                EditMaterialMessage1.setText("Please enter a valid materail ID!");
                  return;
              }




            PreparedStatement statement  = connect.prepareStatement("SELECT * FROM material WHERE material_id = ?");
            statement.setInt(1, material_id);
            Ls = statement.executeQuery();


            if (Ls.next())
            {
                EditMaterialMessage1.setText("User ID Found");

                materialId = Ls.getInt("material_id"); // Store the id value
                EditMaterialFieldname.setText(Ls.getString("material_name"));
                EditMaterialFieldcategory.setText(Ls.getString("material_category"));
                EditMaterialFieldquantity.setText(Ls.getString("material_quantity"));
            }        
            else
            {
                EditMaterialMessage1.setText("User not found");
                EditMaterialFieldname.setText("");
                EditMaterialFieldcategory.setText("");
                EditMaterialFieldquantity.setText("");
            }        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            EditMaterialMessage1.setText("An error occurred while searching for the user");

        }
    }

}
