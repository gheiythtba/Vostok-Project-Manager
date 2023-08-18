package container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.Dataabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegisterController {

    @FXML
    private Label RegisterMessage;

    @FXML
    private Label RegisterMessage1;

    @FXML
    private Label RegisterMessage2;




    @FXML
    private Button SignUpBtn0;

    @FXML
    private TextField registerEmail;

    @FXML
    private ComboBox<String> registerFunction;

    @FXML
    private TextField registerID;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private TextField registerUsername;

    @FXML
    private Hyperlink signInButton2;

    private Connection connect;
    private Dataabase D1 = new Dataabase();

  


    public void initialize() {

  ObservableList<String> registerList = FXCollections.observableArrayList(
                "User", "Admin");
                registerFunction.setItems(registerList);
                registerFunction.setValue("Select --------"); 
}

    @FXML
    void register(ActionEvent event) {
        String selectedRole = registerFunction.getSelectionModel().getSelectedItem();
    System.out.println("Selected Role: " + selectedRole);}
    




    @FXML
    void SignUpBtn(ActionEvent event)  throws Exception{
      
    try {
            connect = D1.connectDb();
            if (connect == null) {
                System.out.println("Connection is not established!");
                return;
            }

              // Check if username or password fields are empty
            int id = Integer.parseInt(registerID.getText().trim());
            String username = registerUsername.getText().trim();
            String email = registerEmail.getText().trim();
            String password = registerPassword.getText().trim();
            String role = registerFunction.getValue();
            
            
            if (id <= 0 || username.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
                RegisterMessage.setText("Please enter your informations!");
                RegisterMessage1.setText("");
                RegisterMessage2.setText("");

                return;
            }
            boolean idError = false; // Flag to indicate if there's an ID error
            boolean usernameError = false; // Flag to indicate if there's a username error
        
            try {

                PreparedStatement checkStatement = connect.prepareStatement("SELECT id, username FROM admin WHERE id = ? OR username = ?");
                checkStatement.setInt(1, id);
                checkStatement.setString(2, username);
                ResultSet resultSet = checkStatement.executeQuery();
        
                while (resultSet.next()) {
                    int existingID = resultSet.getInt("id");
                    String existingUsername = resultSet.getString("username");
        
                    if (existingID == id) {
                        idError = true; 
                    }
                     if (existingUsername.equalsIgnoreCase(username)) {
                        usernameError = true; // Set the username error flag
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        
            // Check and display error messages if needed
            if (idError) {
                RegisterMessage1.setText("ID Already Used");
            } else {
                RegisterMessage1.setText("");
            }
        
            if (usernameError) {
                RegisterMessage2.setText("Username Already Used");
            } else {
                RegisterMessage2.setText("");
            }
        
            // Return if there's any error
            if (idError || usernameError) {
                return;
            }                      
            PreparedStatement statement  = connect.prepareStatement("INSERT INTO admin (id,username,email,password,role)VALUES(?,?,?,?,?)");
            statement.setInt(1, id);
            statement.setString(2, username);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setString(5, role);
            int rowsAffected = statement.executeUpdate();


            if (rowsAffected==1)
            {
                RegisterMessage.setText("Registration succeeded");
                RegisterMessage1.setText("");
                RegisterMessage2.setText("");

                registerID.setText("");
                registerUsername.setText("");
                registerEmail.setText("");
                registerPassword.setText("");
                registerFunction.setValue(null);



                    Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/RegistrationSucceeded.fxml"));
                    Scene signUpScene = new Scene(signUpRoot);
      
                    Stage signInStage = new Stage();
                    signInStage.initStyle(StageStyle.TRANSPARENT);
                    signInStage.setScene(signUpScene);
                    signInStage.show();

                    Stage splashSignInStage = (Stage) SignUpBtn0.getScene().getWindow();
                    splashSignInStage.close();
            
                  }
            
            else
                {
                RegisterMessage.setText("Registration not succeeded");
                RegisterMessage1.setText("");
                RegisterMessage2.setText("");

                }
              } catch (SQLException e) {
                e.printStackTrace();
            }

  

           // Load the sign-Up interface
   
    }


    @FXML
    void affichelogin2()  throws Exception{
      
           // Load the sign-in interface
        Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/signin.fxml"));
        Scene signInScene = new Scene(signInRoot);

        // Create a new stage for the sign-in interface
        Stage signInStage = new Stage();
        signInStage.initStyle(StageStyle.TRANSPARENT);
        signInStage.setScene(signInScene);
        signInStage.show();

        // Close the splash screen stage
        Stage splashSignUpStage = (Stage) signInButton2.getScene().getWindow();
        splashSignUpStage.close();
    }

}
