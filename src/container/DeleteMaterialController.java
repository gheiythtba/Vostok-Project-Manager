package container;

import java.io.IOException;
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

public class DeleteMaterialController {

    @FXML
    private Button DeleteMaterialBackBtn0;
  
    @FXML
    private Label DeleteMaterialMessage;
  
    @FXML
    private Button DeleteMaterialDeleteBtn0;

    
    @FXML
    private TextField DeleteMaterielSerachId;

    @FXML
    private Button DeleteMaterialSearchBtn0;

    
   @FXML
   private TableColumn<material, Integer> material_idcol;

   @FXML
   private TableColumn<material, String > material_namecol;

   @FXML
   private TableColumn <material,String>  material_categorycol;

   @FXML
   private TableColumn<material,Integer > material_quantitycol;
   
   @FXML
    private TableView<material> TableDeleteMat;
    private Connection connect;
    private Dataabase D2 = new Dataabase();
    private int foundMaterialId = 0;

    private ObservableList<material> materialList = FXCollections.observableArrayList();


       @FXML
    void initialize() {
        // Set the cell value factories for the table columns
        material_idcol.setCellValueFactory(new PropertyValueFactory<>("material_id"));
        material_namecol.setCellValueFactory(new PropertyValueFactory<>("material_name"));
        material_categorycol.setCellValueFactory(new PropertyValueFactory<>("material_category"));
        material_quantitycol.setCellValueFactory(new PropertyValueFactory<>("material_quantity"));

        // Load all materials initially
        loadMaterials();
    }   



    @FXML
    void DeleteMaterialBackBtn(ActionEvent event) throws Exception{
      
      // Load the sign-Up interface
   Parent signUpRoot = FXMLLoader.load(getClass().getResource("/interface/MaterialHome.fxml"));
   Scene signUpScene = new Scene(signUpRoot);
   

   // Create a new stage for the sign-in interface
   Stage signUpStage = new Stage();
   signUpStage.initStyle(StageStyle.TRANSPARENT);
   signUpStage.setScene(signUpScene);
   signUpStage.show();

   // Close the splash screen stage
   Stage splashSignInStage = (Stage) DeleteMaterialBackBtn0.getScene().getWindow();
   splashSignInStage.close();
}



@FXML
void DeleteMaterialDeleteBtn(ActionEvent event) throws IOException {
    if (foundMaterialId != 0) {
        try {
            connect = D2.connectDb();
            if (connect == null) {
                System.out.println("Connection is not established!");
                return;
            }

            PreparedStatement deleteStatement = connect.prepareStatement("DELETE FROM material WHERE material_id = ?");
            deleteStatement.setInt(1, foundMaterialId);

            int rowsAffected = deleteStatement.executeUpdate();

            if (rowsAffected == 1) {
                DeleteMaterialMessage.setText("Material deleted successfully!");
                DeleteMaterielSerachId.setText("");

            } else {
                DeleteMaterialMessage.setText("Failed to delete the Material!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DeleteMaterialMessage.setText("An error occurred while deleting the material");
        }
    } else {
        DeleteMaterialMessage.setText("Please search for a material before attempting to delete");
    }
}





    @FXML
    void DeleteMaterialSearchBtn(ActionEvent event) {
        String input = DeleteMaterielSerachId.getText().trim();
    if (input.isEmpty()) {
        loadMaterials();
        DeleteMaterialMessage.setText("Enter an ID to search"); 
    } else {
        try {
            int material_id = Integer.parseInt(input);
            material foundMaterial = getMaterialDetails(material_id);

            if (foundMaterial == null) {
                DeleteMaterialMessage.setText("ID material not Found");
                materialList.clear();
                foundMaterialId = 0; 
            } else {
                materialList.clear();
                materialList.add(foundMaterial);
                foundMaterialId = foundMaterial.getMaterial_id(); 
                DeleteMaterialMessage.setText("ID material Found");
            }
        } catch (NumberFormatException e) {
            DeleteMaterialMessage.setText("Invalid Input Please enter a valid material ID");
            materialList.clear();
            foundMaterialId = 0; 
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

        TableDeleteMat.setItems(materialList);
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