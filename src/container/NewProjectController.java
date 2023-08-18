package container;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewProjectController {
    @FXML
    private Button AddNewProjectBtn1;

    @FXML
    private DatePicker AddProjectDate;

    @FXML
    private TextArea AddProjectDescription;

    @FXML
    private TextField AddProjectID;

    @FXML
    private ComboBox<String> AddProjectManager;

    @FXML
    private ComboBox<String> AddProjectMaterial;

    @FXML
    private Label AddProjectMessage;

    @FXML
    private Label AddProjectMessageID;

    @FXML
    private Button BackNewProjectBtn1;


    @FXML
   private ComboBox<String> AddProjectStatus;
   private ObservableList<String> managerList = FXCollections.observableArrayList();
   private ObservableList<String> materialList = FXCollections.observableArrayList();

      private Connection connect;
    private Dataabase D1 = new Dataabase();





    @FXML
    void AddNewProjectBtn(ActionEvent event)throws Exception{
      try {
            connect = D1.connectDb();
            if (connect == null) {
                System.out.println("Connection is not established!");
                return;
            }
    
            int id = Integer.parseInt(AddProjectID.getText().trim());
            String manager = AddProjectManager.getValue();
            String material = AddProjectMaterial.getValue();
            String status = AddProjectStatus.getValue();
            LocalDate startdate = AddProjectDate.getValue();
            String desc = AddProjectDescription.getText().trim();
         
    
            if (id <= 0 || manager.isEmpty() || material.isEmpty() || status.isEmpty() || startdate == null || desc.isEmpty() )
             {
               AddProjectMessage.setText("Please enter all user information!");
                return;
            }
    
            boolean idError = false;

            try {
                PreparedStatement checkStatement = connect.prepareStatement("SELECT project_id FROM project WHERE project_id = ?");
                checkStatement.setInt(1, id);
                ResultSet resultSet = checkStatement.executeQuery();
    
                if (resultSet.next()) {
                    idError = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            AddProjectMessageID.setText("");
            AddProjectMessage.setText("");
    
            if (idError) {
                AddProjectMessageID.setText("ID Already Used");
                return;
            }
    
    
            PreparedStatement statement = connect.prepareStatement("INSERT INTO project (project_id, project_desc, project_manager, project_startdate, project_status, project_material) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, id);
            statement.setString(2, desc);
            statement.setString(3, manager);
            java.sql.Date sqlStartDate = java.sql.Date.valueOf(startdate);
            statement.setDate(4, sqlStartDate);     
            statement.setString(5, status);
            statement.setString(6, material);

    
            int rowsAffected = statement.executeUpdate();
    
            if (rowsAffected == 1) {
               AddProjectMessage.setText("Great! Registration succeeded");
                // Reset the input fields
                AddProjectID.setText("");
                AddProjectDescription.setText("");
                AddProjectManager.setValue(null);
                AddProjectDate.setValue(null);
                AddProjectStatus.setValue(null);
                AddProjectMaterial.setValue(null);
    
                // Show success notification
                Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/SuccessAddProject.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) AddNewProjectBtn1.getScene().getWindow();
   splashSignInStage.close();
            } else {
                AddProjectMessage.setText("Failed to add user");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }



}

    @FXML
    void BackNewProjectBtn(ActionEvent event) throws Exception{
      
      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/Project.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) BackNewProjectBtn1.getScene().getWindow();
   splashSignInStage.close();
}


@FXML
void select0(ActionEvent event) {
    String selectedRole = AddProjectManager.getSelectionModel().getSelectedItem();
    System.out.println("Selected Role: " + selectedRole);
}
@FXML
void select1(ActionEvent event) {
String selectedRole1 = AddProjectMaterial.getSelectionModel().getSelectedItem();
    System.out.println("Selected Role: " + selectedRole1);
}
@FXML
void select2(ActionEvent event) {
String selectedRole2 = AddProjectStatus.getSelectionModel().getSelectedItem();
    System.out.println("Selected Role: " + selectedRole2);
}

public void initialize() {
   Dataabase database = new Dataabase();
  
   List<String> managerNames = database.fetchUsernamesFromAdminTable();
   managerList.addAll(managerNames);
   AddProjectManager.setItems(managerList);
   AddProjectManager.setValue("Select -------"); 



   List<String> materialNames = database.fetchMaterialnamesFromMaterialTable();
   materialList.addAll(materialNames);
   AddProjectMaterial.setItems(materialList);
   AddProjectMaterial.setValue("Select -------"); 


  ObservableList<String> statusList = FXCollections.observableArrayList(
                "Not Started", "In Progress", "On Hold", "Completed", "Cancelled",
                "Delayed", "In Review", "In Negotiation",
                "Under Development", "Pending Resources", "Completed with Issues",
                "Failed", "On Track", "Off Track");
        AddProjectStatus.setItems(statusList);
        AddProjectStatus.setValue("Select --------"); 
}

}


