package container;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import Database.Dataabase;
import Database.user;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminAccountSetting {


    @FXML
    private Button AdminBackSettings0;
    
    @FXML
    private Label AccountSetingsMessage;

    @FXML
    private Button AdminConfirmSettings0;

    @FXML
    private DatePicker AdminSettingsBirthdate;

    @FXML
    private TextField AdminSettingsEmail;

    @FXML
    private TextField AdminSettingsFname;

    @FXML
    private TextField AdminSettingsID;

    @FXML
    private TextField AdminSettingsLname;

    @FXML
    private PasswordField AdminSettingsPassword;

    @FXML
    private ComboBox<String> AdminSettingsRole;

    @FXML
    private TextField AdminSettingsUsername;
    private user loggedInAdmin;


    public void initialize() {
    ObservableList<String> registerList = FXCollections.observableArrayList(
                "User", "Admin");
                AdminSettingsRole.setItems(registerList);
                AdminSettingsRole.setValue("Select --------"); }

    @FXML
    void AdminSettingsRoleSelect(ActionEvent event) {
        String selectedRole = AdminSettingsRole.getSelectionModel().getSelectedItem();
    System.out.println("Selected Role: " + selectedRole);}

    
    public void setLoggedInAdmin(user admin) {
        this.loggedInAdmin = admin;
        // Display the admin's personal information in the input fields
        AdminSettingsFname.setText(admin.getFirstName());
        AdminSettingsLname.setText(admin.getLastName());
        AdminSettingsEmail.setText(admin.getEmail());
        AdminSettingsRole.setValue(admin.getRole());
        AdminSettingsBirthdate.setValue(admin.getBirthdate());
        AdminSettingsUsername.setText(admin.getUsername());
        AdminSettingsPassword.setText(admin.getPassword());

    }


   @FXML
void AdminConfirmSettings(ActionEvent event) throws Exception {
    // Get the modified data from the input fields
    String firstName = AdminSettingsFname.getText().trim();
    String lastName = AdminSettingsLname.getText().trim();
    String email = AdminSettingsEmail.getText().trim();
    String role = AdminSettingsRole.getValue();
    LocalDate birthdate = AdminSettingsBirthdate.getValue();
    String username = AdminSettingsUsername.getText().trim();
    String password = AdminSettingsPassword.getText().trim();

    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || role == null || birthdate == null || username.isEmpty() || password.isEmpty()) {
        AccountSetingsMessage.setText("Please fill in all the required fields!");
        return;
    }

    // Update the admin's information in the database
    try (Connection connection = new Dataabase().connectDb()) {
        String query = "UPDATE admin SET first_name=?, last_name=?, email=?, role=?, birthdate=?, username=?, password=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setString(3, email);
        statement.setString(4, role);
        statement.setDate(5, Date.valueOf(birthdate));
        statement.setString(6, username);
        statement.setString(7, password);
        statement.setInt(8, loggedInAdmin.getId()); // Use the admin's ID to update the specific row

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected == 1) {
            AccountSetingsMessage.setText("Admin information updated successfully!");
            Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/AdminUpdateWarning.fxml"));
            Scene signUpScene = new Scene(signUpRoot);

            Stage signUpStage = new Stage();
            signUpStage.initStyle(StageStyle.TRANSPARENT);
            signUpStage.setScene(signUpScene);
            signUpStage.show();

            Stage settingsStage = (Stage) AdminConfirmSettings0.getScene().getWindow();
            settingsStage.close();
        } else {
            AccountSetingsMessage.setText("Failed to update admin information!");
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        AccountSetingsMessage.setText("An error occurred while updating admin information!");
    }
}



    @FXML
    void AdminBackSettings(ActionEvent event)  throws Exception{
      
           // Load the sign-Up interface
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/AdminDashboard.fxml"));
        Scene signUpScene = new Scene(signUpRoot);
        

        // Create a new stage for the sign-in interface
        Stage signUpStage = new Stage();
        signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();

        // Close the splash screen stage
        Stage splashSignInStage = (Stage) AdminBackSettings0.getScene().getWindow();
        splashSignInStage.close();
    }

   



}
