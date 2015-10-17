package com.Administracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import zkexamples.mvvm.ListBox2.person;

public class Listado extends SelectorComposer<Component>{

	@Wire
	private Listbox box;
	@Wire
	private Button listar,bloquear;
		
	@Listen("onClick=#listar")
	public void submit() {

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
            s.executeQuery ("SELECT nombre,correo FROM usuario");
            ResultSet rs = s.getResultSet();       
            System.out.println("Se hace la consulta");   
            
            while (box.getItemCount() > 0) {
            	box.removeItemAt(0);
            }
            while (rs.next()) 
            {
            	System.out.println("Forma los datos");   
           	Listitem li = new Listitem(); 
            li.appendChild(new Listcell(rs.getString("nombre")));
            li.appendChild(new Listcell(rs.getString("correo")));
            li.appendChild(new Listcell(rs.getString("correo")));
            box.appendChild(li);  
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
	
	@Listen("onClick=#bloquear")
	public void bloquear(){
		Set<Listitem> selectedList = box.getSelectedItems();
		
		if (selectedList.isEmpty()) {
	           alert("Selecciona un item");
	           System.out.println("Falso");
	           return;
	    }
		
		else{
		System.out.println("Verdadero");
		//selectedList.toString();
		System.out.println(selectedList);
		alert(""+ box.getSelectedCount());
		alert(""+selectedList);
	    //box.getItems().removeAll(selectedList);
	   // box.getSelectedItems().iterator().next();
	   // box.getItems().toString();
	    
		}
	    
	}
}
