package container;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Database.Dataabase;
import Database.project;
import Database.user;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserProjectController {

 

    @FXML
    private Button userProjectBackBtn;

    @FXML
    private Label userProjectNombreMsg;
    @FXML
    private Button userProjectManageBtn;

       @FXML
    private TableView<project> projecthomeview;

    @FXML
    private TableColumn<project, String> userproject_desc;

    @FXML
    private TableColumn<project, Integer> userproject_id;

    @FXML
    private TableColumn<project, String> userproject_material;

    @FXML
    private TableColumn<project, Date> userproject_startdate;

    @FXML
    private TableColumn<project, String> userproject_status;

    private int projectCount = 0;

    private user loggedInAdmin;



    @FXML
    private ObservableList<project> projectList = FXCollections.observableArrayList();

    public void setLoggedInAdmin(user user) {
      this.loggedInAdmin = user;
      loadProjects(); // Load projects associated with the logged-in user
  }
       @FXML
    void initialize() {
        // Set the cell value factories for the table columns
        userproject_id.setCellValueFactory(new PropertyValueFactory<>("project_id"));
        userproject_material.setCellValueFactory(new PropertyValueFactory<>("project_material"));
        userproject_status.setCellValueFactory(new PropertyValueFactory<>("project_status"));
        userproject_startdate.setCellValueFactory(new PropertyValueFactory<>("project_startdate"));
        userproject_desc.setCellValueFactory(new PropertyValueFactory<>("project_desc"));
        // Load all materials initially

        userProjectNombreMsg.setText(String.valueOf(projectCount)); // Display the project count

    }   

    private void loadProjects() {
      if (loggedInAdmin == null) {
          return; // If no logged-in user, return without loading projects
      }
  
      try (Connection connection = new Dataabase().connectDb()) {
          String query = "SELECT * FROM project WHERE project_manager = ?"; // Assuming there's a field 'project_manager' in the project table
          PreparedStatement statement = connection.prepareStatement(query);
          statement.setString(1, loggedInAdmin.getUsername()); // Use the username of the logged-in user
  
          ResultSet resultSet = statement.executeQuery();
  
          projectList.clear();
          projectCount = 0; // Reset the project count

  
          while (resultSet.next()) {
              int project_id = resultSet.getInt("project_id");
              String project_material = resultSet.getString("project_material");
              String project_status = resultSet.getString("project_status");
              String project_desc = resultSet.getString("project_desc");
              Date project_stasql = resultSet.getDate("project_startdate");
              LocalDate project_startdate = project_stasql != null ? project_stasql.toLocalDate() : null;
  
              projectList.add(new project(project_id, project_desc, project_startdate, project_status, project_material));
              projectCount++; // Increment the project count

          }
  
          projecthomeview.setItems(projectList);
        userProjectNombreMsg.setText(String.valueOf(projectCount)); // Display the project count

      } catch (ClassNotFoundException | SQLException e) {
          e.printStackTrace();
      }
  }


  
  /* 
private project getProjectlDetails(int project_id) {
    try (Connection connection = new Dataabase().connectDb()) {
         String query = "SELECT * FROM project WHERE project_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, project_id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            project_id = resultSet.getInt("project_id");
            String project_material = resultSet.getString("project_material");
            String project_status = resultSet.getString("project_status");
            String project_desc = resultSet.getString("project_desc");
            Date project_stasql = resultSet.getDate("project_startdate");
            LocalDate project_startdate = project_stasql != null ? project_stasql.toLocalDate() : null;

            return new project(project_id, project_desc, project_startdate,project_status,project_material);
        }
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
    return null;
}*/




    @FXML
    void userProjectBack(ActionEvent event) throws IOException {

    Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/userHome.fxml"));  
      Scene signUpScene = new Scene(signUpRoot);

      Stage signUpStage = new Stage();
        signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();
          // Close the splash screen stage
      Stage splashSignInStage = (Stage) userProjectBackBtn.getScene().getWindow();
      splashSignInStage.close();
    }



 @FXML
void userProjectManage(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/interface/userManageProjects.fxml"));
    Parent manageProjectsRoot = loader.load();

    ManageProjectController manageProjectsController = loader.getController();
    manageProjectsController.setProjectList(projectList); // Pass the projectList to the manageProjectsController

    Scene manageProjectsScene = new Scene(manageProjectsRoot);
    Stage manageProjectsStage = new Stage();
    manageProjectsStage.initStyle(StageStyle.TRANSPARENT);
    manageProjectsStage.setScene(manageProjectsScene);
    manageProjectsStage.show();

    // Close the current stage
    Stage currentStage = (Stage) userProjectManageBtn.getScene().getWindow();
    currentStage.close();
}


}
