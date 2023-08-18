package container;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WarningEditProjectController {

    @FXML
    private Button NoWarningEditProjectBtn1;

    @FXML
    private Button YesWarningEditProjectBtn1;

    @FXML
    void NoWarningEditProjectBtn(ActionEvent event) throws Exception{
      
      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/Project.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) NoWarningEditProjectBtn1.getScene().getWindow();
   splashSignInStage.close();
}

    @FXML
    void YesWarningEditProjectBtn(ActionEvent event) throws Exception{
      
      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/SuccessEditProject.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) YesWarningEditProjectBtn1.getScene().getWindow();
   splashSignInStage.close();
}

}
