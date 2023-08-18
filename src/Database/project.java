package Database;

import java.time.LocalDate;

public class project {
      
      
      int project_id;
      String project_desc;
      String project_manager;
      LocalDate project_startdate;
      String project_status;
      String project_material;


public project(int project_id,String project_desc,String project_manager,LocalDate project_startdate,String project_status,String project_material)
{
      this.project_id= project_id;
      this.project_desc =project_desc;
      this.project_manager =project_manager;
      this.project_startdate =project_startdate;
      this.project_status= project_status;
      this.project_material=project_material;
  
}

public project(int project_id,String project_desc,LocalDate project_startdate,String project_status,String project_material)
{
      this.project_id= project_id;
      this.project_desc =project_desc;
      this.project_startdate =project_startdate;
      this.project_status= project_status;
      this.project_material=project_material;
  
}

public int getProject_id() {
      return project_id;
  }

  public String getProject_desc() {
      return project_desc;
  }

  public String getProject_manager() {
      return project_manager;
  }

  public LocalDate getProject_startdate() {
      return project_startdate;
  }

  public String getProject_status() {
      return project_status;
  }

  public String getProject_material() {
      return project_material;
  }

  public void setProject_id(int project_id) {
      this.project_id = project_id;
  }

  public void setProject_desc(String project_desc) {
      this.project_desc = project_desc;
  }

  public void setProject_manager(String project_manager) {
      this.project_manager = project_manager;
  }

  public void setProject_startdate(LocalDate project_startdate) {
      this.project_startdate = project_startdate;
  }

  public void setProject_status(String project_status) {
      this.project_status = project_status;
  }

  public void setProject_material(String project_material) {
      this.project_material = project_material;
  }



}


