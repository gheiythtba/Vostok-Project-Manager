package container;

import java.io.IOException;

import Database.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserHomeController {

    @FXML
    private Button UserHomeLogoutBtn;

    @FXML
    private Button UserHomeProfileBtn;

    @FXML
    private Button UserHomeProjectsBtn;


    private user loggedInAdmin;

    public void setLoggedInAdmin(user user) {
        this.loggedInAdmin = user;
    }


    @FXML
    void UserHomeLogout(ActionEvent event) throws IOException {
      
      Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/LogoutNotif.fxml"));  
      Scene signUpScene = new Scene(signUpRoot);
          // Create a new stage for the sign-in interface
        Stage signUpStage = new Stage();
        signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();
          // Close the splash screen stage
      Stage splashSignInStage = (Stage) UserHomeLogoutBtn.getScene().getWindow();
      splashSignInStage.close();
    }





    @FXML
    void UserHomeProfile(ActionEvent event) throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/interface/userAccount.fxml"));
      Parent accountSettingsRoot = loader.load();

      UserAccountSettingsController accountSettingsController = loader.getController();
      accountSettingsController.setLoggedInAdmin(loggedInAdmin);

      Scene accountSettingsScene = new Scene(accountSettingsRoot);
      Stage accountSettingsStage = new Stage();
      accountSettingsStage.initStyle(StageStyle.TRANSPARENT);
      accountSettingsStage.setScene(accountSettingsScene);
      accountSettingsStage.show();

      // Close the current stage
      Stage currentStage = (Stage) UserHomeProfileBtn.getScene().getWindow();
      currentStage.close();
  }







  @FXML
  void UserHomeProjects(ActionEvent event) {
      try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/interface/userProjects.fxml"));
          Parent projectRoot = loader.load();
    
          UserProjectController projectController = loader.getController();
          projectController.setLoggedInAdmin(loggedInAdmin);
    
          Scene projectScene = new Scene(projectRoot);
          Stage projectStage = new Stage();
          projectStage.initStyle(StageStyle.TRANSPARENT);
          projectStage.setScene(projectScene);
          projectStage.show();
    
          // Close the current stage
          Stage currentStage = (Stage) UserHomeProjectsBtn.getScene().getWindow();
          currentStage.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
  
  
}
