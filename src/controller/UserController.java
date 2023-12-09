package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import admin_view.AdminHomePage;
import admin_view.AdminHomePage.AdminHomePageVar;
import computer_technician_view.ComputerTechnicianHomePage;
import database.UserModel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;
import view.LoginPage;
import view.LoginPage.LoginVar;
import view.RegisterPage.RegisterVar;

public class UserController {
	UserModel userModel = new UserModel();
	ResultSet rs;
	
	public void handling_regis(RegisterVar registerVar) {
		registerVar.button_regis.setOnAction(e -> {
			String u = registerVar.username_tf.getText();
			String p = registerVar.pass_pf.getText();
			String confP = registerVar.confpass_pf.getText();
			int a = registerVar.age_spin.getValue();

			if(u == null || p == null || a == 0) {
				registerVar.alert.setContentText("Please fill all of the fields!");
				registerVar.alert.showAndWait();
			}
	        else if(u.length() < 7) {
	        	registerVar.alert.setContentText("UserName must be 7 characters or more!");
	        	registerVar.alert.showAndWait();
	        }
	        else if(p.length() < 6) {
	        	registerVar.alert.setContentText("Password must be 6 characters or more!");
	        	registerVar.alert.showAndWait();
	        }
	        else if(!p.toUpperCase().contains("A") &&
	                !p.toUpperCase().contains("B") &&
	                !p.toUpperCase().contains("C") &&
	                !p.toUpperCase().contains("D") &&
	                !p.toUpperCase().contains("E") &&
	                !p.toUpperCase().contains("F") &&
	                !p.toUpperCase().contains("G") &&
	                !p.toUpperCase().contains("H") &&
	                !p.toUpperCase().contains("I") &&
	                !p.toUpperCase().contains("J") &&
	                !p.toUpperCase().contains("K") &&
	                !p.toUpperCase().contains("L") &&
	                !p.toUpperCase().contains("M") &&
	                !p.toUpperCase().contains("N") &&
	                !p.toUpperCase().contains("O") &&
	                !p.toUpperCase().contains("P") &&
	                !p.toUpperCase().contains("Q") &&
	                !p.toUpperCase().contains("R") &&
	                !p.toUpperCase().contains("S") &&
	                !p.toUpperCase().contains("T") &&
	                !p.toUpperCase().contains("U") &&
	                !p.toUpperCase().contains("V") &&
	                !p.toUpperCase().contains("W") &&
	                !p.toUpperCase().contains("X") &&
	                !p.toUpperCase().contains("Y") &&
	                !p.toUpperCase().contains("Z") &&
	                !p.contains("0") &&
	                !p.contains("1") &&
	                !p.contains("2") &&
	                !p.contains("3") &&
	                !p.contains("4") &&
	                !p.contains("5") &&
	                !p.contains("6") &&
	                !p.contains("7") &&
	                !p.contains("8") &&
	                !p.contains("9")) {
	        	registerVar.alert.setContentText("Password must only contain alphanumeric characters [A-Z and/or 0-9]");
	        	registerVar.alert.showAndWait();
	        }
	        else if(!p.equals(confP)) {
	        	registerVar.alert.setContentText("Confirm Password must be the same with Password!");
	        	registerVar.alert.showAndWait();
	        }
	        else if(a < 13 || a > 65) {
	        	registerVar.alert.setContentText("Age must be between 13-65");
	        	registerVar.alert.showAndWait();
	        }
	        else if(userModel.checkUserExist(u) == true) {
	        	registerVar.alert.setContentText("Username already exists!");
	        	registerVar.alert.showAndWait();
	        }
	        else {
				userModel.regis(new User(u, p, a, "Customer"));
				new LoginPage(registerVar.stage);
			}
		});
	}
	
	public void handling_login(LoginVar loginVar) {		
		loginVar.button_login.setOnAction(e -> {
			String u = loginVar.username_tf.getText();
			String p = loginVar.password_pf.getText();
			if(u.isEmpty()|| p.isEmpty()) {
				loginVar.alert.setContentText("Fill in all fields!");
				loginVar.alert.showAndWait();
			}
			else if(userModel.login(u, p) == false) {
				loginVar.alert.setContentText("Invalid username or password!");
				loginVar.alert.showAndWait();
			}
			else if(userModel.login(u, p) == true) {
				rs = userModel.getUser(u, p);
				User user = new User("technician1", "technician1", 20, "Technician");
				try {
					rs.next();
					String role = rs.getString("UserRole");
					
					// tinggal tambahin new ..HomePage() tiap role nya disini
					if(role.equals("Customer")) {
						
					}
					else if(role.equals("Technician")) {
						new ComputerTechnicianHomePage(loginVar.stage, user);
					}
					else if(role.equals("Operator")) {
						
					}
					else if(role.equals("Admin")) {
						new AdminHomePage(loginVar.stage);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void getAllStaffController(AdminHomePageVar adminHomePageVar) {
		ArrayList<User> userList = new ArrayList<>();
		
        rs = userModel.getAllStaffModel();
        
        try {
			while(rs.next()) {
				String u = rs.getString("UserName");
				String p = rs.getString("UserPassword");
				Integer a = rs.getInt("UserAge");
				String r = rs.getString("UserRole");
				
				userList.add(new User(u, p, a, r));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        adminHomePageVar.vb = new VBox();
		adminHomePageVar.table = new TableView<User>();
		adminHomePageVar.username_col = new TableColumn<>("username");
		adminHomePageVar.age_col = new TableColumn<>("age");
		adminHomePageVar.role_col = new TableColumn<>("role");
		adminHomePageVar.table.getColumns().addAll(adminHomePageVar.username_col, adminHomePageVar.age_col, adminHomePageVar.role_col);
        
		for (User user : userList) {
        	adminHomePageVar.table.getItems().add(user);
		}
		
		adminHomePageVar.username_col.setCellValueFactory(new PropertyValueFactory<>("UserName"));
		adminHomePageVar.age_col.setCellValueFactory(new PropertyValueFactory<>("UserAge"));
		adminHomePageVar.role_col.setCellValueFactory(new PropertyValueFactory<>("UserRole"));

        
		adminHomePageVar.table.setMaxHeight(150);
		adminHomePageVar.username_col.setMinWidth(200);
		adminHomePageVar.age_col.setPrefWidth(200);
        
        
		adminHomePageVar.vb.getChildren().add(adminHomePageVar.table);
        // atas kanan bawah kiri
		adminHomePageVar.vb.setPadding(new Insets(20, 30, 30, 30));
        
		adminHomePageVar.bp = new BorderPane();
		adminHomePageVar.hb = new HBox();
		adminHomePageVar.vb1 = new VBox();
		adminHomePageVar.vb2 = new VBox();
		
		adminHomePageVar.title1 = new Label("Update User Role");
		adminHomePageVar.username = new Label("Username");
		adminHomePageVar.username_tf = new TextField();
		adminHomePageVar.role = new Label("Role");
		adminHomePageVar.role_tf = new TextField();
		adminHomePageVar.btnUpdate = new Button("UPDATE");
		
		adminHomePageVar.title2 = new Label("Delete User");
		adminHomePageVar.username_del = new Label("Username");
		adminHomePageVar.username_tf2 = new TextField();
		adminHomePageVar.btnDelete = new Button("DELETE");
		
		adminHomePageVar.vb1.getChildren().addAll(adminHomePageVar.title1, adminHomePageVar.username, adminHomePageVar.username_tf, adminHomePageVar.role, adminHomePageVar.role_tf, adminHomePageVar.btnUpdate);
		adminHomePageVar.vb2.getChildren().addAll(adminHomePageVar.title2, adminHomePageVar.username_del, adminHomePageVar.username_tf2, adminHomePageVar.btnDelete);
		
		adminHomePageVar.hb.getChildren().addAll(adminHomePageVar.vb1, adminHomePageVar.vb2);
		adminHomePageVar.hb.setPadding(new Insets(0, 0, 0, 30));
		
		adminHomePageVar.vb1.setSpacing(10);
		adminHomePageVar.vb2.setSpacing(10);
		adminHomePageVar.hb.setSpacing(50);
		
		adminHomePageVar.bp.setTop(adminHomePageVar.vb);
		adminHomePageVar.bp.setCenter(adminHomePageVar.hb);
		adminHomePageVar.scene = new Scene(adminHomePageVar.bp, 650, 650);
	}
	
	public void handling_admin(AdminHomePageVar adminHomePageVar) {		
		adminHomePageVar.btnUpdate.setOnAction(e -> {
			String u = adminHomePageVar.username_tf.getText();
			String newRole = adminHomePageVar.role_tf.getText();
			if(u.isEmpty()|| newRole.isEmpty()) {
				adminHomePageVar.alert.setContentText("Fill in all fields!");
				adminHomePageVar.alert.showAndWait();
			}
			else if(!(newRole.equals("Admin") || newRole.equals("Operator") || newRole.equals("Technician"))) {
				adminHomePageVar.alert.setContentText("Invalid role!");
				adminHomePageVar.alert.showAndWait();
			}
			else if(userModel.checkUserExist(u)) {
				userModel.updateRole(u, newRole);
				new AdminHomePage(adminHomePageVar.stage);
			}
		});
	}
}
