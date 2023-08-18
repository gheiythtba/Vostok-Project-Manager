package container;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import Database.Dataabase;
import Database.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserAccountSettingsController {

    @FXML
    private Button UserBackSettingsBtn;

    @FXML
    private Button UserConfirmSettingsBtn;

    @FXML
    private TextField UserSettingUsername;

    @FXML
    private DatePicker UserSettingsBirthdate;

    @FXML
    private TextField UserSettingsEmaill;

    @FXML
    private TextField UserSettingsFname;

    @FXML
    private TextField UserSettingsLname;

    @FXML
    private TextField UserSettingsPaddword;

    @FXML
    private Label EdituserMessage;

    private user loggedInAdmin;


  @FXML
    void AdminConfirmSettings(ActionEvent event)throws IOException {


    String email = UserSettingsEmaill.getText().trim();
    String firstName = UserSettingsFname.getText().trim();
    String lastName = UserSettingsLname.getText().trim();
    String username = UserSettingUsername.getText().trim();
    String password = UserSettingsPaddword.getText().trim();
    LocalDate birthdate = UserSettingsBirthdate.getValue();

    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || birthdate == null || username.isEmpty() || password.isEmpty()) 
    {
        EdituserMessage.setText("Please fill in all the required fields!");
        return;
    }
    // Update the admin's information in the database
    try (Connection connection = new Dataabase().connectDb()) {
        String query = "UPDATE admin SET first_name=?, last_name=?, email=?, birthdate=?, username=?, password=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setString(3, email);
        statement.setDate(4, Date.valueOf(birthdate));
        statement.setString(5, username);
        statement.setString(6, password);
        statement.setInt(7, loggedInAdmin.getId()); // Use the admin's ID to update the specific rows

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected == 1) {
            EdituserMessage.setText("Admin information updated successfully!");
            Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/userHome.fxml"));
            Scene signUpScene = new Scene(signUpRoot);

            Stage signUpStage = new Stage();
            signUpStage.initStyle(StageStyle.TRANSPARENT);
            signUpStage.setScene(signUpScene);
            signUpStage.show();

            Stage settingsStage = (Stage) UserConfirmSettingsBtn.getScene().getWindow();
            settingsStage.close();
        } else {
            EdituserMessage.setText("Failed to update admin information!");
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        EdituserMessage.setText("An error occurred while updating admin information!");
    }
    }





    public void setLoggedInAdmin(user admin) {
        this.loggedInAdmin = admin;
         UserSettingsFname.setText(admin.getFirstName());
        UserSettingsLname.setText(admin.getLastName());
        UserSettingsEmaill.setText(admin.getEmail());
        UserSettingUsername.setText(admin.getUsername());
        UserSettingsBirthdate.setValue(admin.getBirthdate());
    }





    @FXML
    void AdminBackSettings(ActionEvent event) throws IOException {
    
            // Load the sign-Up interface
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/userHome.fxml"));
        Scene signUpScene = new Scene(signUpRoot);
        

        // Create a new stage for the sign-in interface
        Stage signUpStage = new Stage();
        signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();

        // Close the splash screen stage
        Stage splashSignInStage = (Stage) UserBackSettingsBtn.getScene().getWindow();
        splashSignInStage.close();
    }



  






}
