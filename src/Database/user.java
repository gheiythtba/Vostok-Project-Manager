package Database;

import java.time.LocalDate;


public class user {



       int id;
       String first_name;
       String last_name;
       String email;
       String role;
       String username;
       String password;
       LocalDate birthdate;

      public user(int id,String first_name,String last_name,String email, String role,String username,String password,LocalDate birthdate)
      {
            this.id= id;
            this.first_name =first_name;
            this.last_name =last_name;
            this.email =email;
            this.role= role;
            this.username=username;
            this.password=password;
            this.birthdate=birthdate;
      }

      public user(int id,String first_name,String email, String role,LocalDate birthdate)
      {
            this.id= id;
            this.first_name =first_name;
            this.email =email;
            this.role= role;
            this.birthdate=birthdate;
      }

      public user(int id,String first_name,String last_name,String email, String role,LocalDate birthdate)
      {
            this.id= id;
            this.first_name =first_name;
            this.last_name=last_name;
            this.email =email;
            this.role= role;
            this.birthdate=birthdate;
      }





      public int getId()
      {
        return id;    
      }

      public String getFirstName()
      {
            return first_name;
      }

      public String getLastName()
      {
            return last_name;
      }

      public String getEmail()
      {
            return email;
      }

      public String getRole()
      {
            return role;
      }

      public String getUsername()
      {
            return username;
      }

      public String getPassword()
      {
            return password;
      }

      
    public LocalDate getBirthdate() {
      return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
      this.birthdate = birthdate;
  }



}
