package container;


import java.io.IOException;

import Database.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminHomeController {

    @FXML
    private Button AdminAccountBtn1;

    @FXML
    private Button AdminHRBtn1;

    @FXML
    private Button AdminLogoutBtn1;

    @FXML
    private Button AdminMRBtn1;

    @FXML
    private Button AdminProjctBtn1;
    
    @FXML
    private Label AdminHomeMessage;


    
    private user loggedInAdmin;

    public void setLoggedInAdmin(user admin) {
        this.loggedInAdmin = admin;
        AdminHomeMessage.setText("Welcome " + admin.getUsername() + " to your space");
    }






 @FXML
void AdminAccountBtn1(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/interface/AdminAccount.fxml"));
    Parent adminAccountSettingRoot = loader.load(); 

    AdminAccountSetting adminAccountSettingController = loader.getController();
    adminAccountSettingController.setLoggedInAdmin(loggedInAdmin); // Pass the logged-in admin to the AdminAccountSetting controller

    Scene adminAccountSettingScene = new Scene(adminAccountSettingRoot);
    Stage stage = new Stage();
    stage.initStyle(StageStyle.TRANSPARENT);
    stage.setScene(adminAccountSettingScene);
    stage.show();

    Stage currentStage = (Stage) AdminAccountBtn1.getScene().getWindow();
    currentStage.close();
}


    @FXML
    void AdminHRBtn(ActionEvent event)  throws Exception{
      
           // Load the sign-Up interface
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/HR.fxml"));
        Scene signUpScene = new Scene(signUpRoot);
        

        // Create a new stage for the sign-in interface
        Stage signUpStage = new Stage();
        signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();

        // Close the splash screen stage
        Stage splashSignInStage = (Stage) AdminHRBtn1.getScene().getWindow();
        splashSignInStage.close();
    }

    @FXML
    void AdminLogoutBtn(ActionEvent event)  throws Exception{
      
           // Load the sign-Up interface
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/LogoutNotif.fxml"));
        Scene signUpScene = new Scene(signUpRoot);
        

        // Create a new stage for the sign-in interface
        Stage signUpStage = new Stage();
        signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();

        // Close the splash screen stage
        Stage splashSignInStage = (Stage) AdminLogoutBtn1.getScene().getWindow();
        splashSignInStage.close();
    }

    @FXML
    void AdminMRBtn(ActionEvent event)  throws Exception{
      
           // Load the sign-Up interface
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/MaterialHome.fxml"));
        Scene signUpScene = new Scene(signUpRoot);
        

        // Create a new stage for the sign-in interface
        Stage signUpStage = new Stage();
        signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();

        // Close the splash screen stage
        Stage splashSignInStage = (Stage) AdminMRBtn1.getScene().getWindow();
        splashSignInStage.close();
    }

    @FXML
    void AdminProjctBtn(ActionEvent event)  throws Exception{
      
           // Load the sign-Up interface
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/Project.fxml"));
        Scene signUpScene = new Scene(signUpRoot);
        

        // Create a new stage for the sign-in interface
        Stage signUpStage = new Stage();
        signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();

        // Close the splash screen stage
        Stage splashSignInStage = (Stage) AdminProjctBtn1.getScene().getWindow();
        splashSignInStage.close();
    }

}
