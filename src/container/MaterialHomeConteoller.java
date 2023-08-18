package container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.Dataabase;
import Database.material;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class MaterialHomeConteoller {

   @FXML
   private TableColumn<material, Integer> HomeMaterielColid;

   @FXML
   private TableColumn<material, String > HomeMaterielColname;

   @FXML
   private TableColumn <material,String>  HomeMaterielColquantity;

   @FXML
   private TableColumn<material,String > HomeMaterielcategory;
   
   @FXML
   private TableView<material> tableMaterielle;

   @FXML
    private Label HomeMaterialMessage;


    @FXML
    private TextField HomeMaterialSearchField;
    
    @FXML
    private Button MaterialAddBtn0;

    @FXML
    private Button MaterialDeleteBtn0;

    @FXML
    private Button MaterialEditBtn0;

    @FXML
    private Button MaterialHomeBtn0;

    @FXML
    private Button ProjectSearchBtn1;
   


 @FXML
    private ObservableList<material> materialList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        // Set the cell value factories for the table columns
        HomeMaterielColid.setCellValueFactory(new PropertyValueFactory<>("material_id"));
        HomeMaterielColname.setCellValueFactory(new PropertyValueFactory<>("material_name"));
        HomeMaterielColquantity.setCellValueFactory(new PropertyValueFactory<>("material_quantity"));
        HomeMaterielcategory.setCellValueFactory(new PropertyValueFactory<>("material_category"));


        // Load all materials initially
        loadMaterials();
    }   





    @FXML
    void MaterialAddBtn(ActionEvent event) throws Exception{
      
      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/AddMaterial.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) MaterialAddBtn0.getScene().getWindow();
   splashSignInStage.close();
}

    @FXML
    void MaterialDeleteBtn(ActionEvent event)  throws Exception{
      
        // Load the sign-Up interface
     Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/DeleteMaterial.fxml"));
     Scene signUpScene = new Scene(signUpRoot);
     
  
     // Create a new stage for the sign-in interface
     Stage signUpStage = new Stage();
     signUpStage.initStyle(StageStyle.TRANSPARENT);
     signUpStage.setScene(signUpScene);
     signUpStage.show();
  
     // Close the splash screen stage
     Stage splashSignInStage = (Stage) MaterialDeleteBtn0.getScene().getWindow();
     splashSignInStage.close();
  }
    @FXML
    void MaterialEditBtn(ActionEvent event)  throws Exception{
      
        // Load the sign-Up interface
     Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/EditMaterial.fxml"));
     Scene signUpScene = new Scene(signUpRoot);
     
  
     // Create a new stage for the sign-in interface
     Stage signUpStage = new Stage();
     signUpStage.initStyle(StageStyle.TRANSPARENT);
     signUpStage.setScene(signUpScene);
     signUpStage.show();
  
     // Close the splash screen stage
     Stage splashSignInStage = (Stage)  MaterialEditBtn0.getScene().getWindow();
     splashSignInStage.close();
  }

    @FXML
    void MaterialHomeBtn0(ActionEvent event) throws Exception{
      
      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/AdminDashboard.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) MaterialHomeBtn0.getScene().getWindow();
   splashSignInStage.close();
}

@FXML
void ProjectSearchBtn(ActionEvent event) {
    String input = HomeMaterialSearchField.getText().trim();
        if (input.isEmpty()) {
            // If the search field is empty, load all materials
            loadMaterials();
            HomeMaterialMessage.setText("ID material  Found"); // Clear any previous error messages
        } else {
            try {
                int material_id = Integer.parseInt(input);
                material foundMaterial = getMaterialDetails(material_id);

                if (foundMaterial == null) {
                    HomeMaterialMessage.setText("ID material not Found");
                    materialList.clear();
                } else {
                    materialList.clear();
                    materialList.add(foundMaterial);
                }
            } catch (NumberFormatException e) {
                HomeMaterialMessage.setText("Invalid Input Please enter a valid material ID");
                materialList.clear();
            }
        }
}

private void loadMaterials() {
    try (Connection connection = new Dataabase().connectDb()) {
        String query = "SELECT * FROM material";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        materialList.clear();

        while (resultSet.next()) {
            int material_id = resultSet.getInt("material_id");
            String material_name = resultSet.getString("material_name");
            String material_category = resultSet.getString("material_category");
            int material_quantity = resultSet.getInt("material_quantity");

            materialList.add(new material(material_id, material_name, material_category, material_quantity));
        }

        tableMaterielle.setItems(materialList);
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
        // Handle exceptions appropriately
    }
}
private material getMaterialDetails(int materialId) {
    try (Connection connection = new Dataabase().connectDb()) {
        String query = "SELECT * FROM material WHERE material_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, materialId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int material_id = resultSet.getInt("material_id");
            String material_name = resultSet.getString("material_name");
            String material_category = resultSet.getString("material_category");
            int material_quantity = resultSet.getInt("material_quantity");

            return new material(material_id, material_name, material_category, material_quantity);
        }
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
    return null;
}

}

