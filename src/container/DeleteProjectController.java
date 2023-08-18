package container;

import Database.Dataabase;
import Database.project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;




public class DeleteProjectController {

    @FXML
    private Button DeleteProjectBackBtn0;

    @FXML
    private Button DeleteProjectDeleteBtn0;

    @FXML
    private Label DeleteProjectMessage;

    @FXML
    private Button DeleteProjectSearchBtn0;


    @FXML
    private TableView<project> TableDeleteProject;

    @FXML
    private TableColumn<project, String> project_colDesc;

    @FXML
    private TableColumn<project, String> project_colMaterial;

    @FXML
    private TableColumn<project, Date> project_colStartdate;

    @FXML
    private TableColumn<project, String> project_colStatus;

    @FXML
    private TableColumn<project, Integer> project_colid;

    @FXML
    private TableColumn<project, String> project_colmanager;
    
    @FXML
    private TextField DeleteProjectSerachId;


    private Connection connect;
    private Dataabase D2 = new Dataabase();
    private int foundProjectId = 0;
    private ObservableList<project> projectList = FXCollections.observableArrayList();




    @FXML
    void initialize() {
        // Set the cell value factories for the table columns
        project_colid.setCellValueFactory(new PropertyValueFactory<>("project_id"));
        project_colDesc.setCellValueFactory(new PropertyValueFactory<>("project_desc"));
        project_colmanager.setCellValueFactory(new PropertyValueFactory<>("project_manager"));
        project_colStartdate.setCellValueFactory(new PropertyValueFactory<>("project_startdate"));
        project_colStatus.setCellValueFactory(new PropertyValueFactory<>("project_status"));
        project_colMaterial.setCellValueFactory(new PropertyValueFactory<>("project_material"));

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
            String project_desc = resultSet.getString("project_desc");
            String project_manager = resultSet.getString("project_manager");
            String project_status = resultSet.getString("project_status");
            String project_material = resultSet.getString("project_material");
            Date startdate = resultSet.getDate("project_startdate");
            LocalDate project_startdate = startdate != null ? startdate.toLocalDate() : null;


            projectList.add(new project(project_id, project_desc, project_manager, project_startdate,project_status, project_material));

        }

        TableDeleteProject.setItems(projectList);
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
}


private project getProjectDetails(int projectId) {
  try (Connection connection = new Dataabase().connectDb()) {
      String query = "SELECT * FROM project WHERE project_id = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, projectId);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
         int project_id = resultSet.getInt("project_id");
            String project_desc = resultSet.getString("project_desc");
            String project_manager = resultSet.getString("project_manager");
            String project_status = resultSet.getString("project_status");
            String project_material = resultSet.getString("project_material");
            Date startdate = resultSet.getDate("project_startdate");
            LocalDate project_startdate = startdate != null ? startdate.toLocalDate() : null;

          return new project(project_id, project_desc, project_manager, project_startdate,project_status, project_material);
      }
  } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
  }
  return null;
}

    @FXML
    void DeleteProjectSearchBtn(ActionEvent event) {
      String input = DeleteProjectSerachId.getText().trim();
      if (input.isEmpty()) {
          loadProjects();
          DeleteProjectMessage.setText("Enter an ID to search"); 
      } else {
          try {
              int project_id = Integer.parseInt(input);
              project foundProject = getProjectDetails(project_id);
  
              if (foundProject == null) {
                  DeleteProjectMessage.setText("ID project not Found");
                  projectList.clear();
                  foundProjectId = 0; 
              } else {
                  projectList.clear();
                  projectList.add(foundProject);
                  foundProjectId = foundProject.getProject_id(); 
                  DeleteProjectMessage.setText("ID material Found");
              }
          } catch (NumberFormatException e) {
              DeleteProjectMessage.setText("Invalid Input Please enter a valid material ID");
              projectList.clear();
              foundProjectId = 0; 
          }
      }
    }







    @FXML
    void DeleteProjectDeleteBtn(ActionEvent event)throws Exception{
      

      if (foundProjectId != 0) {
        try {
            connect = D2.connectDb();
            if (connect == null) {
                System.out.println("Connection is not established!");
                return;
            }

            PreparedStatement deleteStatement = connect.prepareStatement("DELETE FROM project WHERE project_id = ?");
            deleteStatement.setInt(1, foundProjectId);

            int rowsAffected = deleteStatement.executeUpdate();

            if (rowsAffected == 1) {
                DeleteProjectMessage.setText("Project deleted successfully!");
          
              Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/WarningDeleteProject.fxml"));
              Scene signUpScene = new Scene(signUpRoot);
   

              Stage signUpStage = new Stage();
              signUpStage.initStyle(StageStyle.TRANSPARENT);
              signUpStage.setScene(signUpScene);
              signUpStage.show();

              Stage splashSignInStage = (Stage) DeleteProjectDeleteBtn0.getScene().getWindow();
              splashSignInStage.close();

            } else {
                DeleteProjectMessage.setText("Failed to delete the Project!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DeleteProjectMessage.setText("An error occurred while deleting the Project");
        }
    } else {
        DeleteProjectMessage.setText("Please search for a Project before attempting to delete");
    }
}




      
    @FXML
    void DeleteProjectBackBtn(ActionEvent event) throws Exception{
      
      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/Project.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) DeleteProjectBackBtn0.getScene().getWindow();
   splashSignInStage.close();
}

}
