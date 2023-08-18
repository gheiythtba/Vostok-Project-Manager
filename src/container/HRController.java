package container;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HRController {


    @FXML
    private Button DeleteUserButton1;


    @FXML
    private Button HRAddUserBttn11;

    @FXML
    private Button HRHomerBtn1;

    @FXML
    private Button HRModUserButton1;
    
    @FXML
    private Button SendEmailButton1;

    
    @FXML
    private TableView<user> tableHR;

    @FXML
    private TableColumn<user, Date> colomunbirthdate;

    @FXML
    private TableColumn<user, String> colomunemail;

    @FXML
    private TableColumn<user, String> colomunfirst;

    @FXML
    private TableColumn<user, String> colomunfonction;

    @FXML
    private TableColumn<user, Integer> colomunid;

    @FXML
    private TableColumn<user, String> colomunsecond;

    private ObservableList<user> userList = FXCollections.observableArrayList();

 @FXML
    void initialize() {
        loadDate();
    }

        private void loadDate() {

        try (Connection connection = new Dataabase().connectDb()) {
            String query = "SELECT * FROM admin";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<user> users = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");
                 Date birthdateSql = resultSet.getDate("birthdate");
                LocalDate birthdate = birthdateSql != null ? birthdateSql.toLocalDate() : null;
            users.add(new user(id, firstName, lastName, email, role, birthdate));
        }


            userList.addAll(users);

            colomunid.setCellValueFactory(new PropertyValueFactory<>("id"));
            colomunfirst.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            colomunsecond.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            colomunemail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colomunfonction.setCellValueFactory(new PropertyValueFactory<>("role"));
            colomunbirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));

          


            tableHR.setItems(userList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }



  
    @FXML
    void SendEmailButton1(ActionEvent event)throws Exception{
      
           // Load the sign-in interface
        Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/sendemail.fxml"));
        Scene signInScene = new Scene(signInRoot);

        // Create a new stage for the sign-in interface
        Stage signInStage = new Stage();
        signInStage.initStyle(StageStyle.TRANSPARENT);
        signInStage.setScene(signInScene);
        signInStage.show();

        // Close the splash screen stage
        Stage splashSignUpStage = (Stage) SendEmailButton1.getScene().getWindow();
        splashSignUpStage.close();
    }


  @FXML
    void HRHomerBtn1(ActionEvent event) throws Exception{
      
           // Load the sign-in interface
        Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/AdminDashboard.fxml"));
        Scene signInScene = new Scene(signInRoot);

        // Create a new stage for the sign-in interface
        Stage signInStage = new Stage();
        signInStage.initStyle(StageStyle.TRANSPARENT);
        signInStage.setScene(signInScene);
        signInStage.show();

        // Close the splash screen stage
        Stage splashSignUpStage = (Stage) HRHomerBtn1.getScene().getWindow();
        splashSignUpStage.close();
    }



    @FXML
    void DeleteUserButton(ActionEvent event) throws Exception{
      
           // Load the sign-in interface
        Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/DeleteUser.fxml"));
        Scene signInScene = new Scene(signInRoot);

        // Create a new stage for the sign-in interface
        Stage signInStage = new Stage();
        signInStage.initStyle(StageStyle.TRANSPARENT);
        signInStage.setScene(signInScene);
        signInStage.show();

        // Close the splash screen stage
        Stage splashSignUpStage = (Stage) DeleteUserButton1.getScene().getWindow();
        splashSignUpStage.close();
    }

    @FXML
    void HRAddUserButton1(ActionEvent event) throws Exception{
      
           // Load the sign-in interface
        Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/newuser1.fxml"));
        Scene signInScene = new Scene(signInRoot);

        // Create a new stage for the sign-in interface
        Stage signInStage = new Stage();
        signInStage.initStyle(StageStyle.TRANSPARENT);
        signInStage.setScene(signInScene);
        signInStage.show();

        // Close the splash screen stage
        Stage splashSignUpStage = (Stage) HRAddUserBttn11.getScene().getWindow();
        splashSignUpStage.close();
    }


    @FXML
    void HRModUserButton(ActionEvent event)throws Exception{
      
           // Load the sign-in interface
        Parent signInRoot = FXMLLoader.load(getClass().getResource("/interface/EditUser.fxml"));
        Scene signInScene = new Scene(signInRoot);

        // Create a new stage for the sign-in interface
        Stage signInStage = new Stage();
        signInStage.initStyle(StageStyle.TRANSPARENT);
        signInStage.setScene(signInScene);
        signInStage.show();

        // Close the splash screen stage
        Stage splashSignUpStage = (Stage) HRAddUserBttn11.getScene().getWindow();
        splashSignUpStage.close();
    }

 

















}
