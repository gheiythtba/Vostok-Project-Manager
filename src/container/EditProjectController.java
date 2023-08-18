package container;

import java.sql.Connection;
import java.sql.Date;
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

public class EditProjectController {



      @FXML
    private Button EditProjectBackBtn0;

    @FXML
    private Button EditProjectEditBtn0;

    @FXML
    private Label EditProjectMessage;

    @FXML
    private Button EditProjectSearchBtn;

    @FXML
    private TextArea EditprojectDesc;

    @FXML
    private TextField EditprojectID;

    @FXML
    private ComboBox<String> EditprojectManager;

    @FXML
    private ComboBox<String> EditprojectMaterial;

    @FXML
    private Label EditprojectMessageID;

    @FXML
    private DatePicker EditprojectStartdate;

    @FXML
    private ComboBox<String> EditprojectStatus;

        private Connection connect;
        private Dataabase D1 = new Dataabase();
        private ResultSet Ls;
        private ObservableList<String> managerList = FXCollections.observableArrayList();
        private ObservableList<String> materialList = FXCollections.observableArrayList();

@FXML
void select0(ActionEvent event) {
   String selectedRole = EditprojectManager.getSelectionModel().getSelectedItem();
   System.out.println("Selected Role0: " + selectedRole);
}
@FXML
void select1(ActionEvent event) {
   String selectedRole1 = EditprojectMaterial.getSelectionModel().getSelectedItem();
   System.out.println("Selected Role1: " + selectedRole1);
}
@FXML
void select2(ActionEvent event) {
   String selectedRole2 = EditprojectStatus.getSelectionModel().getSelectedItem();
   System.out.println("Selected Role2: " + selectedRole2);
}

public void initialize() {
   Dataabase database = new Dataabase();
  
   List<String> managerNames = database.fetchUsernamesFromAdminTable();
   managerList.addAll(managerNames);
   EditprojectManager.setItems(managerList);



   List<String> materialNames = database.fetchMaterialnamesFromMaterialTable();
   materialList.addAll(materialNames);
   EditprojectMaterial.setItems(materialList);


  ObservableList<String> statusList = FXCollections.observableArrayList(
                "Not Started", "In Progress", "On Hold", "Completed", "Cancelled",
                "Delayed", "In Review", "In Negotiation",
                "Under Development", "Pending Resources", "Completed with Issues",
                "Failed", "On Track", "Off Track");
        EditprojectStatus.setItems(statusList);
}
    
    @FXML
    void EditProjectSearch(ActionEvent event) {
       try {
            connect = D1.connectDb();
            if (connect == null) {
                System.out.println("Connection is not established!");
                return;
            }

            String idString = EditprojectID.getText().trim();
            if (idString.isEmpty()) {
               EditProjectMessage.setText("Please enter a valid Prject ID!");     
                return;
            }



               int project_id;
            try {
                   project_id = Integer.parseInt(idString);
              } catch (NumberFormatException e) {
               EditprojectMessageID.setText("Please enter a valid project ID!");
                  return;
              }




            PreparedStatement statement  = connect.prepareStatement("SELECT * FROM project WHERE project_id = ?");
            statement.setInt(1, project_id);
            Ls = statement.executeQuery();


            if (Ls.next())
            {
                EditprojectMessageID.setText("project ID Found");

                EditprojectManager.setValue(Ls.getString("project_manager"));
                EditprojectMaterial.setValue(Ls.getString("project_material"));
                EditprojectStartdate.setValue(Ls.getDate("project_startdate").toLocalDate());
                EditprojectStatus.setValue(Ls.getString("project_status"));
                EditprojectDesc.setText(Ls.getString("project_desc"));
            }
            else
            {
               EditprojectMessageID.setText("Project not found");

               EditprojectID.setText("");
               EditprojectManager.setValue(null);
               EditprojectMaterial.setValue(null);
               EditprojectStartdate.setValue(null);
               EditprojectStatus.setValue(null);
               EditprojectDesc.setText("");

            }        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            EditProjectMessage.setText("An error occurred while searching for the project");

        }

    }

    @FXML
    void EditProjectBackBtn(ActionEvent event) throws Exception{

 // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/Project.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) EditProjectBackBtn0.getScene().getWindow();
   splashSignInStage.close();
}


    @FXML
    void EditProjectEditBtn(ActionEvent event) throws Exception{

      try {
         connect = D1.connectDb();
         if (connect == null) {
             System.out.println("Connection is not established!");
             return;
         }

         String project_material = EditprojectMaterial.getValue();
         String project_manager = EditprojectManager.getValue();
         LocalDate project_startdate = EditprojectStartdate.getValue();
         String project_status = EditprojectStatus.getValue();
         String project_desc = EditprojectDesc.getText().trim();

    // Get the project ID from the EditprojectID field
    String idString = EditprojectID.getText().trim();
    if (idString.isEmpty()) {
        EditProjectMessage.setText("Please enter a valid Project ID!");
        return;}


    int project_id;
    try {
        project_id = Integer.parseInt(idString);
         } catch (NumberFormatException e) {
        EditprojectMessageID.setText("Please enter a valid project ID!");
        return; }


         Date sqlstartdate = Date.valueOf(project_startdate);
         PreparedStatement statement  = connect.prepareStatement("UPDATE project SET project_material=?,project_manager=?,project_startdate=?,project_status=?,project_desc=? WHERE project_id =?");
        
         statement.setInt(6, project_id);
         statement.setString(1, project_material);
         statement.setString(2, project_manager);
         statement.setDate(3, sqlstartdate);
         statement.setString(4, project_status);
         statement.setString(5, project_desc);

        
         int rowsAffected = statement.executeUpdate();
         if (rowsAffected==1)
         {
               EditprojectManager.setValue(null);
               EditprojectMaterial.setValue(null);
               EditprojectStartdate.setValue(null);
               EditprojectStatus.setValue(null);
               EditprojectDesc.setText("");

      

               EditProjectMessage.setText("Sucess !! material has been updaeted");

      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/WarningEditProject.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) EditProjectEditBtn0.getScene().getWindow();
   splashSignInStage.close();

         
               }
         
         else
         {
            EditProjectMessage.setText("Update failed");


 
           }        } catch (SQLException | ClassNotFoundException e) {
             e.printStackTrace();
         }

   
   }


}
