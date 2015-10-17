package zkexamples.mvvm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
 
public class ListBox2 {
 
 private List<person> allPersons = new ArrayList<person>();
  private person selectedPerson;
  private List<person> selectedPersons;
  
    public person getSelectedPerson() {
     return selectedPerson;
  }
 
   public void setSelectedPerson(person selectedPerson) {
      this.selectedPerson = selectedPerson;
   }
 
   public List<person> getSelectedPersons() {
        return selectedPersons;
 }
 
   public void setSelectedPersons(List<person> selectedPersons) {
        this.selectedPersons = selectedPersons;
 }
 
   public List<person> getAllPersons() {
     return allPersons;
  }
 
   public void setAllPersons(List<person> allPersons) {
      this.allPersons = allPersons;
   }
 
   public ListBox2() {
       
     Connection conn = null;
     try
     {
         Class.forName ("com.mysql.jdbc.Driver").newInstance ();
         String userName = "root";
         String password = "";
         String url = "jdbc:mysql://localhost/autenticacion";
         
         conn = DriverManager.getConnection (url, userName, password);
         System.out.println ("Conexion de la Base de datos establecida");
         
         Statement s = conn.createStatement ();
         s.executeQuery ("SELECT id_usuario,nombre,correo,status FROM usuario");
         ResultSet rs = s.getResultSet();       
         System.out.println("Se hace la consulta");   
         
         
         while (rs.next()) 
         {
         //System.out.println(rs.getString("nombre"));   
         
         allPersons.add(new person(rs.getString("id_usuario"),rs.getString("nombre"), rs.getString("correo"),rs.getString("status")));
                 
         System.out.println("termina de formar los datos");   
         }
         rs.close ();
         s.close ();
          
     }
     catch (Exception e)
     {
         System.err.println ("ERROR: "+ e.getMessage());
     }
     finally
     {
         if (conn != null)
         {
             try
             {
                 conn.close ();
                 System.out.println ("Conexion de la base de datos terminada");
             }
             catch (Exception e) { /* ignore close errors */ }
         }
     }
    }
    
   
   //activa usuarios
    @Command
    public void activar()
    {
    	//String ids="";
      
        if (this.selectedPersons==null)
     {
           Messagebox.show("Activa un registro");
          return;
     }
 
       if (this.selectedPersons.size()==0)
     {
           Messagebox.show("Activa un registro");
          return;
     }
       
       PreparedStatement stmt;
	    Connection conn = null; 
   	try {
   		String ids="";
   		String status = "";
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection("jdbc:mysql://localhost/autenticacion?user=root&password=");
           stmt = conn.prepareStatement("UPDATE usuario set status = ? WHERE id_usuario = ? "); 
   		        
           for (Iterator<person> i = selectedPersons.iterator();i.hasNext();) 
           {

              person tmp = i.next();
              //ids = (tmp.getId()).concat(",");
              ids = (tmp.getId()).concat("");
              
              status = (tmp.getStatus());
              //ids = tmp;
              //System.out.println(ids);
              System.out.println(status);
              if(status.equals("1"))
              {
            	  status = "0";  
            	  stmt.setString(1, status);
              }
              else if(status.equals("0"))
              {
            	  status = "1";
            	  stmt.setString(1, status);
              }
              
              
             Messagebox.show("Los ID큦 seleccionado es:" + ids);
             
             stmt.setString(2, ids);
             stmt.executeUpdate();
           }
           
           
           //stmt.executeUpdate();
           //Messagebox.show("Los ID큦 seleccionado es:" + ids);   
   		//Messagebox.show("Usuario Actualizado");
   		Executions.sendRedirect("index.zul");
   		//Executions.sendRedirect("index.zul");
   				
   		
   	}
   	catch(Exception e){
           System.out.println(e);
       	}
   //Messagebox.show("Los ID큦 seleccionados son:" + ids);
      

 }
   
    //bloquea usuarios
    /*@Command
    public void bloquear()
    {
    	//String ids="";
      
        if (this.selectedPersons==null)
     {
           Messagebox.show("Bloquea un registro");
          return;
     }
 
       if (this.selectedPersons.size()==0)
     {
           Messagebox.show("Bloquea un registro");
          return;
     }
       
       PreparedStatement stmt;
	    Connection conn = null; 
   	try {
   		String ids="";
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection("jdbc:mysql://localhost/autenticacion?user=root&password=");
           stmt = conn.prepareStatement("UPDATE usuario set status = 0 WHERE id_usuario = ? "); 
   		        
          
           
           for (Iterator<person> i = selectedPersons.iterator();i.hasNext();) 
           {
        	   person tmp = i.next();
               // ids = (tmp.getId()).concat(",");
                ids = (tmp.getId()).concat("");
                
                //ids = tmp;
                System.out.println(ids);
                
                //Messagebox.show("Los ID큦 seleccionado es:" + ids);
                stmt.setString(1, ids);
                stmt.executeUpdate();
              
           }
           
           
           //stmt.executeUpdate();
           //Messagebox.show("Los ID큦 seleccionado es:" + ids);   
   		//Messagebox.show("Usuario Bloqueado");
   		Executions.sendRedirect("index.zul");
   		//Executions.sendRedirect("index.zul");
   				
   		
   	}
   	catch(Exception e){
           System.out.println(e);
       	}
   //Messagebox.show("Los ID큦 seleccionados son:" + ids);
      

 }*/
 
   // inner class
  public class person {
	  private String id;
       private String nombre;
       private String email;
        private String status;
 
       public person(String id, String nombre, String email, String status) {
    	 this.id=id;
         this.nombre = nombre;
         this.email = email;
         this.status = status;
     }
       
       public String getId(){
    	   return id;
       }
 
       public void setId(String id){
    	   this.id = id;
       }
       
       public String getNombre() {
          return nombre;
       }
 
       public void setNombre(String nombre) {
            this.nombre = nombre;
     }
 
       public String getStatus() {
           return status;
        }
 
       public void setStatus(String status) {
          this.status = status;
       }
 
       public String getEmail() {
          return email;
       }
 
       public void setEmail(String email) {
            this.email = email;
     }
 
   }
}
