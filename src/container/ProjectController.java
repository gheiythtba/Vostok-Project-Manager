package container;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;

import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Database.Dataabase;
import Database.project;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TableView;






public class ProjectController {

   @FXML
   private TableView<project> projecthomeview;

   @FXML
   private Label HomeProjectMessage;

   @FXML
   private TextField PrjectSearchid;

   @FXML
   private Button ProjectAddProjectBtn1;

   @FXML
   private Button ProjectDeleteProjectBtn1;

   @FXML
   private Button ProjectEditProjectBtn1;

   @FXML
   private Button ProjectHomeBtn1;

   @FXML
   private Button ProjectSearchBtn1;

   @FXML
   private TableColumn<project, String> project_desc;

   @FXML
   private TableColumn<project, Integer> project_id;

   @FXML
   private TableColumn<project, String> project_manager;

   @FXML
   private TableColumn<project, String> project_material;

   @FXML
   private TableColumn<project, Date> project_startdate;

   @FXML
   private TableColumn<project, String> project_status;



    @FXML
    private ObservableList<project> projectList = FXCollections.observableArrayList();

    @FXML
    void ProjectAddProjectBtn(ActionEvent event) throws Exception{
      
      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/Newproject.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) ProjectAddProjectBtn1.getScene().getWindow();
   splashSignInStage.close();
}

    @FXML
    void ProjectDeleteProjectBtn(ActionEvent event)throws Exception{
      
      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/DeleteProject.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) ProjectDeleteProjectBtn1.getScene().getWindow();
   splashSignInStage.close();
}

    @FXML
    void ProjectEditProjectBtn(ActionEvent event)  throws Exception{
      
      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/EditProject.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) ProjectEditProjectBtn1.getScene().getWindow();
   splashSignInStage.close();
}

    @FXML
    void ProjectHomeBtn(ActionEvent event)  throws Exception{
      
      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/AdminDashboard.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) ProjectHomeBtn1.getScene().getWindow();
   splashSignInStage.close();
}

    @FXML
    void ProjectSearchBtn(ActionEvent event) {
      Integer input = Integer.parseInt(PrjectSearchid.getText().trim());
      try {
          int project_id = input;
          project foundProject = getProjectlDetails(project_id);
     
          if (foundProject == null) {
              HomeProjectMessage.setText("Projec not found");
              projectList.clear();
          } else {
              projectList.clear();
              projectList.add(foundProject);
              HomeProjectMessage.setText("Projects found"); // Clear any previous error messages
          }
      } catch (NumberFormatException e) {
          HomeProjectMessage.setText("Invalid Input. Please enter a valid Projects ID");
          projectList.clear();
      }
      
}
    



   @FXML
    void initialize() {
        // Set the cell value factories for the table columns
        project_id.setCellValueFactory(new PropertyValueFactory<>("project_id"));
        project_manager.setCellValueFactory(new PropertyValueFactory<>("project_manager"));
        project_material.setCellValueFactory(new PropertyValueFactory<>("project_material"));
        project_status.setCellValueFactory(new PropertyValueFactory<>("project_status"));
        project_startdate.setCellValueFactory(new PropertyValueFactory<>("project_startdate"));
        project_desc.setCellValueFactory(new PropertyValueFactory<>("project_desc"));



        // Load all materials initially
        loadProjects();
    }   


private void loadProjects() {
    try (Connection connection = new Dataabase().connectDb()) {
        String query = "SELECT * FROM project";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        projectList.clear();

        while (resultSet.next()) {
            int project_id = resultSet.getInt("project_id");
            String project_manager = resultSet.getString("project_manager");
            String project_material = resultSet.getString("project_material");
            String project_status = resultSet.getString("project_status");
            String project_desc = resultSet.getString("project_desc");
            Date project_stasql = resultSet.getDate("project_startdate");
            LocalDate project_startdate = project_stasql != null ? project_stasql.toLocalDate() : null;

            projectList.add(new project(project_id, project_desc, project_manager, project_startdate,project_status,project_material));
        }

        projecthomeview.setItems(projectList);
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
        // Handle exceptions appropriately
    }
}
private project getProjectlDetails(int project_id) {
    try (Connection connection = new Dataabase().connectDb()) {
         String query = "SELECT * FROM project WHERE project_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, project_id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            project_id = resultSet.getInt("project_id");
            String project_manager = resultSet.getString("project_manager");
            String project_material = resultSet.getString("project_material");
            String project_status = resultSet.getString("project_status");
            String project_desc = resultSet.getString("project_desc");
            Date project_stasql = resultSet.getDate("project_startdate");
            LocalDate project_startdate = project_stasql != null ? project_stasql.toLocalDate() : null;

            return new project(project_id, project_desc, project_manager, project_startdate,project_status,project_material);
        }
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
    return null;
}













}
