package container;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class LogoutNotifController {

    @FXML
    private Button NoLogoutNotifBtn1;

    @FXML
    private Button YesLogoutNotifBtn1;

    @FXML
    void NoLogoutNotifBtn1(ActionEvent event)  throws Exception{
      
           // Load the sign-Up interface
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/AdminDashboard.fxml"));
        Scene signUpScene = new Scene(signUpRoot);
        

        // Create a new stage for the sign-in interface
        Stage signUpStage = new Stage();
        signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();

        // Close the splash screen stage
        Stage splashSignInStage = (Stage) NoLogoutNotifBtn1.getScene().getWindow();
        splashSignInStage.close();
    }

    @FXML
    void YesLogoutNotifBtn(ActionEvent event)  throws Exception{
      
           // Load the sign-Up interface
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/Splash1.fxml"));
        Scene signUpScene = new Scene(signUpRoot);
        

        // Create a new stage for the sign-in interface
        Stage signUpStage = new Stage();
        signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();

        // Close the splash screen stage
        Stage splashSignInStage = (Stage) YesLogoutNotifBtn1.getScene().getWindow();
        splashSignInStage.close();
    }
}
