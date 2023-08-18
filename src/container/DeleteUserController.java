package container;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Database.Dataabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Database.user;

public class DeleteUserController {

    @FXML
    private Button DeleteUserDeleteBttn1;
    
    @FXML
    private TableView<user> tableHr;

    @FXML
    private TableColumn<user, Date> DeleteUserColbirthdate;

    @FXML
    private TableColumn<user, String> DeleteUserColemail;

    @FXML
    private TableColumn<user, String> DeleteUserColfname;

    @FXML
    private TableColumn<user, String> DeleteUserColid;

    @FXML
    private TableColumn<user, String> DeleteUserCollname;

    @FXML
    private TableColumn<user, String> DeleteUserColrole;


    @FXML
    private Button DeleteUserHomeBtn1;

    @FXML
    private TextField DeleteUserSearchBar;

    @FXML
    private Label DeleteUserMessage;
    private Connection connect;
    private Dataabase D1 = new Dataabase();
    private int foundUserId = 0;

    @FXML
    private Button DeleteUserSearchBtn1;


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

            DeleteUserColid.setCellValueFactory(new PropertyValueFactory<>("id"));
            DeleteUserColfname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            DeleteUserCollname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            DeleteUserColemail.setCellValueFactory(new PropertyValueFactory<>("email"));
            DeleteUserColrole.setCellValueFactory(new PropertyValueFactory<>("role"));
            DeleteUserColbirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));

          


            tableHr.setItems(userList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }


    @FXML
    void DeleteUserDeleteBttn(ActionEvent event)throws Exception{
        if (foundUserId != 0) {
            try {
                connect = D1.connectDb();
                if (connect == null) {
                    System.out.println("Connection is not established!");
                    return;
                }
    
                PreparedStatement deleteStatement = connect.prepareStatement("DELETE FROM admin WHERE id = ?");
                deleteStatement.setInt(1, foundUserId);
    
                int rowsAffected = deleteStatement.executeUpdate();
    
                if (rowsAffected == 1) {
                    DeleteUserMessage.setText("User deleted successfully!");
                    DeleteUserSearchBar.setText("");
                        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/DeleteNotification.fxml"));
                        Scene signUpScene = new Scene(signUpRoot);
        
                                // Create a new stage for the sign-in interface
                        Stage signUpStage = new Stage();
                        signUpStage.initStyle(StageStyle.TRANSPARENT);
                        signUpStage.setScene(signUpScene);
                        signUpStage.show();

                                // Close the splash screen stage
                        Stage splashSignInStage = (Stage)  DeleteUserDeleteBttn1.getScene().getWindow();
                        splashSignInStage.close();
                    foundUserId = 0;
                }
                 else 
                {
                    DeleteUserMessage.setText("Failed to delete the user!");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                DeleteUserMessage.setText("An error occurred while deleting the user");
            }
        } else {
            DeleteUserMessage.setText("Please search for a user before attempting to delete");
        }
   
    }
    @FXML
    void DeleteUserHomeBtn(ActionEvent event) throws Exception{
      
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/HR.fxml"));
        Scene signUpScene = new Scene(signUpRoot);
        
        // Create a new stage for the sign-in interface
        Stage signUpStage = new Stage();
        signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();

        // Close the splash screen stage
        Stage splashSignInStage = (Stage)  DeleteUserHomeBtn1.getScene().getWindow();
        splashSignInStage.close();
    }

    @FXML
    void DeleteUserSearchBtn(ActionEvent event) {
        try {
            connect = D1.connectDb();
            if (connect == null) {
                System.out.println("Connection is not established!");
                return;
            }
    
            String idString = DeleteUserSearchBar.getText().trim();
            if (idString.isEmpty()) {
                DeleteUserMessage.setText("Please enter a valid user ID!");
                return;
            }
    
            int userIdToDelete = Integer.parseInt(idString);
    
            PreparedStatement searchStatement = connect.prepareStatement("SELECT id FROM admin WHERE id = ?");
            searchStatement.setInt(1, userIdToDelete);
    
            ResultSet resultSet = searchStatement.executeQuery();
            if (resultSet.next()) {
                DeleteUserMessage.setText("User found!");
                foundUserId = userIdToDelete;
            } else {
                DeleteUserMessage.setText("User not found!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DeleteUserMessage.setText("An error occurred while searching for the user");
        }
}




}
