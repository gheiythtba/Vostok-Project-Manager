package container;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class SplashController   {



   @FXML
    private Button signInButton1;

   @FXML
    private Button SplashExsitBtn1;


   @FXML
    private Button signUpButton1;


     @FXML
    void SplashExsitBtn(ActionEvent event) {
      System.exit(0);
    }
    

    @FXML
    private void affichelogin1()  throws Exception{
      
           // Load the sign-in interface
        Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/signin.fxml"));
        Scene signInScene = new Scene(signInRoot);


        // Create a new stage for the sign-in interface
        Stage signInStage = new Stage();
        signInStage.initStyle(StageStyle.TRANSPARENT);
        signInStage.setScene(signInScene);
        signInStage.show();

        // Close the splash screen stage
        Stage splashScreenStage = (Stage) signInButton1.getScene().getWindow();
        splashScreenStage.close();
    }
         

    @FXML
    void afficheregister1()  throws Exception{
      
           // Load the sign-in interface
        Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/register.fxml"));
        Scene signUpScene = new Scene(signInRoot);

        // Create a new stage for the sign-in interface
        Stage signUpStage = new Stage();
      signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();

        // Close the splash screen stage
        Stage splashScreenStage = (Stage) signUpButton1.getScene().getWindow();
        splashScreenStage.close();
    }


  } 
