package container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Database.Dataabase;
import Database.user;



public class SigninController {

    @FXML
    private Hyperlink signUpButton2;

    @FXML
    private Button SigninHomerBtn1;

    @FXML
    private Button SignInBtn0;

    @FXML
    private Label SigninMessage;
    @FXML
    private Label SigninMessage1;

    @FXML
    private PasswordField Signinpsswd;

    @FXML
    private TextField Signinuser;
    
    @FXML
    private AnchorPane Signin;

    private Connection connect;
    private Dataabase D1 = new Dataabase();

  




    @FXML
    void SignInBtn0(ActionEvent event) throws Exception{
        // Make sure the connection is established
        try {
            connect = D1.connectDb();
            if (connect == null) {
                System.out.println("Connection is not established!");
                return;
            }

              // Check if username or password fields are empty
            String username = Signinuser.getText().trim();
            String password = Signinpsswd.getText().trim();
            
            if (username.isEmpty() || password.isEmpty()) {
                SigninMessage1.setText("Please enter both username and password!");
                SigninMessage.setText(""); // Clear the other error message

                return;
            }


            PreparedStatement statement = connect.prepareStatement("SELECT * FROM admin WHERE username = ?  and password = ? ");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                // Create a new user object with the retrieved data
                user loggedInAdmin = new user(
                    result.getInt("id"),
                    result.getString("first_name"),
                    result.getString("last_name"),
                    result.getString("email"),
                    result.getString("role"),
                    result.getString("username"),
                    result.getString("password"),
                    result.getDate("birthdate").toLocalDate()
                );

                String userRole = loggedInAdmin.getRole();

                if ("Admin".equals(userRole)) {
                SignInBtn0.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/interface/AdminDashboard.fxml"));
                Parent signUpRoot = loader.load();
                Scene signUpScene = new Scene(signUpRoot);
                AdminHomeController adminhomeController = loader.getController();
                adminhomeController.setLoggedInAdmin(loggedInAdmin);
    
                Stage signUpStage = new Stage();
                signUpStage.initStyle(StageStyle.TRANSPARENT);
                signUpStage.setScene(signUpScene);
                signUpStage.show();


                Stage splashSignUpStage = (Stage) SignInBtn0.getScene().getWindow();
                splashSignUpStage.close();


                } else if ("User".equals(userRole)) {

                SignInBtn0.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/interface/userHome.fxml"));
                Parent signUpRoot = loader.load();
                Scene signUpScene = new Scene(signUpRoot);
    
                UserHomeController userHomeController = loader.getController();
                userHomeController.setLoggedInAdmin(loggedInAdmin);
    
                Stage signUpStage = new Stage();
                signUpStage.initStyle(StageStyle.TRANSPARENT);
                signUpStage.setScene(signUpScene);
                signUpStage.show();


                Stage splashSignUpStage = (Stage) SignInBtn0.getScene().getWindow();
                splashSignUpStage.close();
                }
                    



            } 
            
            else
            {
                SigninMessage.setText("Wrong Informations");
                SigninMessage1.setText(""); // Clear the other error message

    
            }        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    
    }


    
    @FXML
    void SigninHomer(ActionEvent event)throws Exception {
     // Load the sign-Up interface
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/Splash1.fxml"));
        Scene signUpScene = new Scene(signUpRoot);
        

        // Create a new stage for the sign-in interface
        Stage signUpStage = new Stage();
        signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();

        // Close the splash screen stage
        Stage splashSignInStage = (Stage) signUpButton2.getScene().getWindow();
        splashSignInStage.close();
    }



    

    
    @FXML
    void afficheregister1()  throws Exception{
      
           // Load the sign-Up interface
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/register.fxml"));
        Scene signUpScene = new Scene(signUpRoot);
        

        // Create a new stage for the sign-in interface
        Stage signUpStage = new Stage();
        signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();

        // Close the splash screen stage
        Stage splashSignInStage = (Stage) signUpButton2.getScene().getWindow();
        splashSignInStage.close();
    }


}
