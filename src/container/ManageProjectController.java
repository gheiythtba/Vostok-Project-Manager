package container;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ManageProjectController {

    @FXML
    private TextField ManageProjectSearchID;

    @FXML
    private Button manageProjectBackBtn;

    @FXML
    private Button manageProjectSearchBtn;

    @FXML
    private Button confirmbtn;


      @FXML
    private Label manageProjectMessage;


        private Connection connect;
        private Dataabase D1 = new Dataabase();

    @FXML
    private ComboBox<String> manageProjectStatusCombobox;

        private ObservableList<project> projectList;

        public void initialize() {

  ObservableList<String> statusList = FXCollections.observableArrayList(
                "Not Started", "In Progress", "On Hold", "Completed", "Cancelled",
                "Delayed", "In Review", "In Negotiation",
                "Under Development", "Pending Resources", "Completed with Issues",
                "Failed", "On Track", "Off Track");
        manageProjectStatusCombobox.setItems(statusList);
}

    public void setProjectList(ObservableList<project> projectList)
    {
    this.projectList = projectList;
    }

    @FXML
    void ManageProjectCombox(ActionEvent event) {
      String selectedStatus = manageProjectStatusCombobox.getSelectionModel().getSelectedItem();
      int searchProjectID = Integer.parseInt(ManageProjectSearchID.getText()); // Get the entered project ID
  
      // Find the project in projectList based on the searched ID
      for (project proj : projectList) {
          if (proj.getProject_id() == searchProjectID) {
              // Update the status of the selected project in projectList
              proj.setProject_status(selectedStatus);
              break;
          }
      }
    }

    @FXML
    void manageProjectBack(ActionEvent event)throws IOException {
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/userProjects.fxml"));  
      Scene signUpScene = new Scene(signUpRoot);
          // Create a new stage for the sign-in interface
        Stage signUpStage = new Stage();
        signUpStage.initStyle(StageStyle.TRANSPARENT);
        signUpStage.setScene(signUpScene);
        signUpStage.show();
          // Close the splash screen stage
      Stage splashSignInStage = (Stage) manageProjectBackBtn.getScene().getWindow();
      splashSignInStage.close();
    }



    @FXML
    void manageProjectSearch(ActionEvent event) {
      String searchProjectID = ManageProjectSearchID.getText(); 
      project selectedProject = null;
  
      for (project proj : projectList) {
        if (String.valueOf(proj.getProject_id()).equals(searchProjectID)) {
          selectedProject = proj;
              break;
          }
      }
  
      if (selectedProject != null) {
            manageProjectMessage.setText("Project ID found.");
            manageProjectStatusCombobox.setValue(selectedProject.getProject_status());
      } else {
          manageProjectMessage.setText("Project ID not found.");

          
      }
  
    }

    @FXML
    void confirm(ActionEvent event) {
        String selectedStatus = manageProjectStatusCombobox.getSelectionModel().getSelectedItem();
        String searchProjectID = ManageProjectSearchID.getText(); 
    
        for (project proj : projectList) {
            if (String.valueOf(proj.getProject_id()).equals(searchProjectID)) {
                proj.setProject_status(selectedStatus);
    
                // Update the project status in the database
                boolean updated = updateProjectStatusInDatabase(searchProjectID, selectedStatus);
    
                if (updated) {
                    manageProjectMessage.setText("Project status updated and saved.");
                } else {
                    manageProjectMessage.setText("Error updating project status in the database.");
                }
    
                return; 
            }
        }
    
        manageProjectMessage.setText("Project ID not found.");
    }
   



    
    private boolean updateProjectStatusInDatabase(String projectID, String newStatus) {
      // Establish a database connection
      try {
          connect = D1.connectDb();
          if (connect == null) {
              System.out.println("Connection is not established!");
              return false;
          }
  
          // Prepare the SQL update statement
          String updateQuery = "UPDATE project SET project_status = ? WHERE project_id = ?";
          
          PreparedStatement preparedStatement = connect.prepareStatement(updateQuery) ;
              preparedStatement.setString(1, newStatus);
              preparedStatement.setString(2, projectID);
  
              // Execute the update statement
              int rowsAffected = preparedStatement.executeUpdate();
  
              // Check if the update was successful
              return rowsAffected > 0;
          }
       catch (SQLException | ClassNotFoundException e) {
          e.printStackTrace();
          return false; // Update was not successful due to an exception
      }
  }
  
}
