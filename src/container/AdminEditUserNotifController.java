package container;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class AdminEditUserNotifController {

    @FXML
    private Button AdminEditUserotifBtn1;

    @FXML
    void AdminEditUserotifBtn(ActionEvent event) throws Exception{
      
      // Load the sign-in interface
   Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/HR.fxml"));
   Scene signInScene = new Scene(signInRoot);

   // Create a new stage for the sign-in interface
   Stage signInStage = new Stage();
   signInStage.initStyle(StageStyle.TRANSPARENT);
   signInStage.setScene(signInScene);
   signInStage.show();

   // Close the splash screen stage
   Stage splashSignUpStage = (Stage) AdminEditUserotifBtn1.getScene().getWindow();
   splashSignUpStage.close();
}

}
