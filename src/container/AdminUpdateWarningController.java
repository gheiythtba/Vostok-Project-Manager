package container;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminUpdateWarningController {

    @FXML
    private Button NoUpdateNotifBtn0;

    @FXML
    private Button YesUpdateNotifBtn0;

    @FXML
    void NoUpdateNotifBtn(ActionEvent event) throws Exception{
      
      // Load the sign-in interface
   Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/AdminAccount.fxml"));
   Scene signInScene = new Scene(signInRoot);

   // Create a new stage for the sign-in interface
   Stage signInStage = new Stage();
   signInStage.initStyle(StageStyle.TRANSPARENT);
   signInStage.setScene(signInScene);
   signInStage.show(); 

   // Close the splash screen stage
   Stage splashSignUpStage = (Stage) NoUpdateNotifBtn0.getScene().getWindow();
   splashSignUpStage.close();
}
    @FXML
    void YesUpdateNotifBtn(ActionEvent event) throws Exception{
      
      // Load the sign-in interface
   Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/AdminUpdateSuccess.fxml"));
   Scene signInScene = new Scene(signInRoot);

   // Create a new stage for the sign-in interface
   Stage signInStage = new Stage();
   signInStage.initStyle(StageStyle.TRANSPARENT);
   signInStage.setScene(signInScene);
   signInStage.show();

   // Close the splash screen stage
   Stage splashSignUpStage = (Stage) YesUpdateNotifBtn0.getScene().getWindow();
   splashSignUpStage.close();
}
}
