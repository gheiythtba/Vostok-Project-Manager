package container;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DeleteNotificationController {
   
   @FXML
   private Button SccAdminDeleteBtn1;

   @FXML
   private Button WarAdminDeleteBtn1;

    @FXML
    void WarAdminDeleteBtn(ActionEvent event)  throws Exception{
      
           // Load the sign-in interface
        Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/DeleteUser.fxml"));
        Scene signInScene = new Scene(signInRoot);

        // Create a new stage for the sign-in interface
        Stage signInStage = new Stage();
        signInStage.initStyle(StageStyle.TRANSPARENT);
        signInStage.setScene(signInScene);
        signInStage.show();

        // Close the splash screen stage
        Stage splashSignUpStage = (Stage) WarAdminDeleteBtn1.getScene().getWindow();
        splashSignUpStage.close();
    }


    @FXML
    void SccAdminDeleteBtn1(ActionEvent event) throws Exception{
      
      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/DeleteNotif.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) SccAdminDeleteBtn1.getScene().getWindow();
   splashSignInStage.close();
}
}
