package container;


import java.sql.Connection;
import java.sql.Date;
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


public class AdminEditUserController {
 @FXML
    private Button AdminEditUserBackBtn0;

    @FXML
    private Button AdminEditUserConfirmBtn0;

    @FXML
    private Button AdminEditUserSearchBtn0;

    @FXML
    private DatePicker EditUserBirthdate;

    @FXML
    private TextField EditUserEmail;

    @FXML
    private TextField EditUserFname;

    @FXML
    private TextField EditUserID;

    @FXML
    private TextField EditUserLname;

    @FXML
    private Label EditUserMessage1;

    @FXML
    private Label EditUserMessage11;

    @FXML
    private TextField EditUserPassword;

    @FXML
    private ComboBox<String> EditUserRole;

    @FXML
    private TextField EditUserSearch1;

    private int userId; // Store the user id here


    @FXML
    private TextField EditUserUsername;
    private Connection connect;
    private Dataabase D1 = new Dataabase();
    private ResultSet Ls;


    @FXML
    void EditUserRole0(ActionEvent event) {
        String selectedRole = EditUserRole.getSelectionModel().getSelectedItem();
        System.out.println("Selected Role: " + selectedRole);}
    public void initialize() {

    ObservableList<String> registerList = FXCollections.observableArrayList(
                "User", "Admin");
                EditUserRole.setItems(registerList);
                EditUserRole.setValue("Select --------"); }


    @FXML
    void AdminEditUserBackBtn0(ActionEvent event) throws Exception{
                // Load the sign-in interface
            Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/HR.fxml"));
            Scene signInScene = new Scene(signInRoot);

                // Create a new stage for the sign-in interface
            Stage signInStage = new Stage();
            signInStage.initStyle(StageStyle.TRANSPARENT);
            signInStage.setScene(signInScene);
            signInStage.show();

                // Close the splash screen stage
            Stage splashSignUpStage = (Stage) AdminEditUserConfirmBtn0.getScene().getWindow();
            splashSignUpStage.close();
          }






    @FXML
    void AdminEditUserConfirmBtn(ActionEvent event)  throws Exception{
        try {
            connect = D1.connectDb();
            if (connect == null) {
                System.out.println("Connection is not established!");
                return;
            }

              // Check if username or password fields are empty
            String first_name = EditUserFname.getText().trim();
            String last_name = EditUserLname.getText().trim();
            String email = EditUserEmail.getText().trim();
            String role = EditUserRole.getValue();
            LocalDate birthdate = EditUserBirthdate.getValue();
            String username = EditUserUsername.getText().trim(); 
            String password = EditUserPassword.getText().trim();

            if (first_name.isEmpty() || last_name.isEmpty() ||email.isEmpty() || role.isEmpty() || birthdate == null || username.isEmpty() ||password.isEmpty()){
                EditUserMessage1.setText("Please enter all the user informations!");

                return;
            }


        boolean usernameError = false;

        try {
            PreparedStatement checkStatement = connect.prepareStatement("SELECT username FROM admin WHERE username = ?");
            checkStatement.setString(1, username);

            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                usernameError = true; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
                 }
                    EditUserMessage1.setText("");
                    EditUserMessage11.setText("");

        if (usernameError) {
            EditUserMessage11.setText("Username Already Used");
            return;
        }



            Date sqlBirthdate = Date.valueOf(birthdate);

            PreparedStatement statement  = connect.prepareStatement("UPDATE admin SET first_name=?,last_name=?,email=?,role=?,birthdate=?,username=?,password=? WHERE id=?");
           
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, email);
            statement.setString(4, role);
            statement.setDate(5, sqlBirthdate);
            statement.setString(6, username);
            statement.setString(7, password);
            statement.setInt(8, userId); // Use the stored id value

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected==1)
            {
               // EditUserID.setText("");
                EditUserFname.setText("");
                EditUserLname.setText("");
                EditUserEmail.setText("");
                EditUserRole.setValue(null);
                EditUserBirthdate.setValue(null);
                EditUserUsername.setText("");
                EditUserPassword.setText("");

                EditUserMessage1.setText("Sucess !! User has been updaeted");





                    // Load the sign-in interface
                Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/AdminEditUserNotif.fxml"));
                Scene signInScene = new Scene(signInRoot);

                    // Create a new stage for the sign-in interface
                Stage signInStage = new Stage();
                signInStage.initStyle(StageStyle.TRANSPARENT);
                signInStage.setScene(signInScene);
                signInStage.show();

                    // Close the splash screen stage
                Stage splashSignUpStage = (Stage) AdminEditUserBackBtn0.getScene().getWindow();
                splashSignUpStage.close();

            
                  }
            
            else
            {
                EditUserMessage1.setText("Updated with success");


    
              }        } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

}







    @FXML
    void AdminEditUserSearchBtn(ActionEvent event) {
  try {
            connect = D1.connectDb();
            if (connect == null) {
                System.out.println("Connection is not established!");
                return;
            }




            String idString = EditUserSearch1.getText().trim();
            if (idString.isEmpty()) {
                EditUserMessage1.setText("Please enter a valid user ID!");
                
                return;
            }


            int id;
              try {
                  id = Integer.parseInt(idString);
              } catch (NumberFormatException e) {
                  EditUserMessage1.setText("Please enter a valid user ID!");
                  return;
              }




            PreparedStatement statement  = connect.prepareStatement("SELECT * FROM admin WHERE id = ? ");
            statement.setInt(1, id);

            Ls = statement.executeQuery();


            if (Ls.next())
            {
                EditUserMessage1.setText("User ID Found");
                userId = Ls.getInt("id"); // Store the id value
                EditUserFname.setText(Ls.getString("first_name"));
                EditUserLname.setText(Ls.getString("last_name"));
                EditUserEmail.setText(Ls.getString("email"));
                EditUserRole.setValue(Ls.getString("role"));
                Date birthdateSql = Ls.getDate("birthdate");
                    if (birthdateSql != null) {
                    LocalDate birthdate = birthdateSql.toLocalDate();
                    EditUserBirthdate.setValue(birthdate);
                    } else {
                    EditUserBirthdate.setValue(null);
                    }
                EditUserUsername.setText(Ls.getString("username"));
                EditUserPassword.setText(Ls.getString("password"));


                  }
            
            else
            {
                EditUserMessage1.setText("User not found");

                EditUserFname.setText("");
                EditUserLname.setText("");
                EditUserEmail.setText("");
                EditUserRole.setValue(null);
                EditUserBirthdate.setValue(null);
                EditUserUsername.setText("");
                EditUserPassword.setText("");
            


    
            }        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            EditUserMessage1.setText("An error occurred while searching for the user");

        }
    }

}
