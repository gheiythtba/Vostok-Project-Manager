package container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.Date;

public class AddUserController {

        @FXML
    private Button AddUserBttn1;

    @FXML
    private Button BackaddUserBttn1;

    @FXML
    private DatePicker NewUserBirthdate;

    @FXML
    private TextField NewUserEmail;

    @FXML
    private TextField NewUserFname;

    @FXML
    private TextField NewUserID;

    @FXML
    private TextField NewUserLname;

    @FXML
    private TextField NewUserPassword;

    @FXML
    private ComboBox<String> NewUserRole;

    @FXML
    private TextField NewUserUsername;

    @FXML
    private Label NewUserMessage1;
    @FXML
    private Label NewUserMessage2;

    @FXML
    private Label NewUserMessage3;


    private Connection connect;
    private Dataabase D1 = new Dataabase();

    







    @FXML
    void register(ActionEvent event) {
        String selectedRole = NewUserRole.getSelectionModel().getSelectedItem();
        System.out.println("Selected Role: " + selectedRole);}
    

       public void initialize() {

  ObservableList<String> registerList = FXCollections.observableArrayList(
                "User", "Admin");
                NewUserRole.setItems(registerList);
                NewUserRole.setValue("Select --------"); 
}

    @FXML
    void AddUserButton1(ActionEvent event) throws Exception {
        try {
            connect = D1.connectDb();
            if (connect == null) {
                System.out.println("Connection is not established!");
                return;
            }
    
            // Check if username or password fields are empty
            int id = Integer.parseInt(NewUserID.getText().trim());
            String first_name = NewUserFname.getText().trim();
            String last_name = NewUserLname.getText().trim();
            String email = NewUserEmail.getText().trim();
            String role = NewUserRole.getValue();
            LocalDate birthdate = NewUserBirthdate.getValue();
            String username = NewUserUsername.getText().trim();
            String password = NewUserPassword.getText().trim();
    
            // Validate user input
            if (id <= 0 || first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || role  == null || birthdate == null || username.isEmpty() || password.isEmpty()) {
                NewUserMessage1.setText("Please enter all user information!");
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
                        idError = true; // Set the ID error flag
                    }
                    if (existingUsername.equalsIgnoreCase(username)) {
                        usernameError = true; // Set the username error flag
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            NewUserMessage1.setText("");
            NewUserMessage2.setText("");
            NewUserMessage3.setText("");
    
            if (idError) {
                NewUserMessage2.setText("ID Already Used");
            }
    
            if (usernameError) {
                NewUserMessage3.setText("Username Already Used");
            }
    
            if (idError || usernameError) {
                return;
            }
    
            Date sqlBirthdate = Date.valueOf(birthdate);
            PreparedStatement statement = connect.prepareStatement("INSERT INTO admin (id, first_name, last_name, email, role, birthdate, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, id);
            statement.setString(2, first_name);
            statement.setString(3, last_name);
            statement.setString(4, email);
            statement.setString(5, role);
            statement.setDate(6, sqlBirthdate);
            statement.setString(7, username);
            statement.setString(8, password);
    
            int rowsAffected = statement.executeUpdate();
    
            if (rowsAffected == 1) {
                NewUserMessage1.setText("Great! Registration succeeded");
                // Reset the input fields
                NewUserID.setText("");
                NewUserFname.setText("");
                NewUserLname.setText("");
                NewUserEmail.setText("");
                NewUserRole.setValue(null);
                NewUserBirthdate.setValue(null);
                NewUserUsername.setText("");
                NewUserPassword.setText("");
    
                // Show success notification
                Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/AddSuccessNotif.fxml"));
                Scene signInScene = new Scene(signInRoot);
                Stage signInStage = new Stage();
                signInStage.initStyle(StageStyle.TRANSPARENT);
                signInStage.setScene(signInScene);
                signInStage.show();
    
                // Close the current stage
                Stage currentStage = (Stage) AddUserBttn1.getScene().getWindow();
                currentStage.close();
            } else {
                NewUserMessage1.setText("Failed to add user");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
  

    @FXML
    void BackUserButton1(ActionEvent event) throws Exception{
      
           // Load the sign-in interface
        Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/HR.fxml"));
        Scene signInScene = new Scene(signInRoot);

        // Create a new stage for the sign-in interface
        Stage signInStage = new Stage();
        signInStage.initStyle(StageStyle.TRANSPARENT);
        signInStage.setScene(signInScene);
        signInStage.show();

        // Close the splash screen stage
        Stage splashSignUpStage = (Stage) BackaddUserBttn1.getScene().getWindow();
        splashSignUpStage.close();
    }



}