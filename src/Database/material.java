package Database;



public class material {
int material_id;
String material_name;
String material_category;
int material_quantity;

public material (int material_id, String material_name, String material_category, int material_quantity)
       {
            this.material_id = material_id;
            this.material_name = material_name;
            this.material_category = material_category;
            this.material_quantity = material_quantity;
        }

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    public String getMaterial_category() {
        return material_category;
    }

    public void setMaterial_category(String material_category) {
        this.material_category = material_category;
    }

    public int getMaterial_quantity() {
        return material_quantity;
    }

    public void setMaterial_quantity(int material_quantity) {
        this.material_quantity = material_quantity;
    }




}






