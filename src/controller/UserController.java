package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import customer_view.CustomerHomePage;
import admin_view.AdminHomePage;
import admin_view.AddJobPage;
import admin_view.AddJobPage.AddJobPageVar;
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
import operator_view.OperatorHomePage;
import view.LoginPage;
import view.LoginPage.LoginVar;
import view.RegisterPage.RegisterVar;

public class UserController {
	UserModel userModel = new UserModel();
	User currentUser;
	ResultSet rs;
	
	// Function untuk mengurus register
	public void handling_regis(RegisterVar registerVar) {
		registerVar.button_regis.setOnAction(e -> {
			// Mengambil semua value dari form
			String u = registerVar.username_tf.getText();
			String p = registerVar.pass_pf.getText();
			String confP = registerVar.confpass_pf.getText();
			int a = registerVar.age_spin.getValue();

			//Validasi
			if(u == null || p == null || a == 0) {
				// Validasi data kosong
				registerVar.alert.setContentText("Please fill all of the fields!");
				registerVar.alert.showAndWait();
			}
	        else if(u.length() < 7) {
	        	// Validasi panjang username
	        	registerVar.alert.setContentText("UserName must be 7 characters or more!");
	        	registerVar.alert.showAndWait();
	        }
	        else if(p.length() < 6) {
	        	// validasi panjang password
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
	        	// Validasi password hanya ada alpha numeric characters
	        	registerVar.alert.setContentText("Password must only contain alphanumeric characters [A-Z and/or 0-9]");
	        	registerVar.alert.showAndWait();
	        }
	        else if(!p.equals(confP)) {
	        	// Validasi password sama dengan confirm password
	        	registerVar.alert.setContentText("Confirm Password must be the same with Password!");
	        	registerVar.alert.showAndWait();
	        }
	        else if(a < 13 || a > 65) {
	        	// Validasi umur diantara 13-65
	        	registerVar.alert.setContentText("Age must be between 13-65");
	        	registerVar.alert.showAndWait();
	        }
	        else if(userModel.checkUserExist(u) == true) {
	        	// Validasi kalau user sudah ada
	        	registerVar.alert.setContentText("Username already exists!");
	        	registerVar.alert.showAndWait();
	        }
	        else {
	        	// Insert data user melalui user model
				userModel.regis(new User(0, u, p, a, "Customer"));
				new LoginPage(registerVar.stage);
			}
		});
	}
	
	// Function untuk mengurus login
	public void handling_login(LoginVar loginVar) {		
		loginVar.button_login.setOnAction(e -> {
			// Mengambil value form login
			String u = loginVar.username_tf.getText();
			String p = loginVar.password_pf.getText();
			
			// Validasi
			if(u.isEmpty()|| p.isEmpty()) {
				// Validasi data kosong
				loginVar.alert.setContentText("Fill in all fields!");
				loginVar.alert.showAndWait();
			}
			else if(userModel.login(u, p) == false) {
				// Validasi salah input username / password
				loginVar.alert.setContentText("Invalid username or password!");
				loginVar.alert.showAndWait();
			}
			else if(userModel.login(u, p) == true) {
				// Validasi kalau username / passwrod sudah benar
				
				// Ambil data user dari database melalui user model
				rs = userModel.getUser(u, p);
				try {
					rs.next();
					String username = rs.getString("UserName");
					String password = rs.getString("UserPassword");
					Integer age = rs.getInt("UserAge");
					String role = rs.getString("UserRole");
					currentUser = new User(0, username, password, age, role);
					
					// Validasi untuk memasukkan user ke home page user role mana
					if(role.equals("Customer")) {
						new CustomerHomePage(loginVar.stage, currentUser);
					}
					else if(role.equals("Technician")) {
						new ComputerTechnicianHomePage(loginVar.stage, currentUser);
					}
					else if(role.equals("Operator")) {
						new OperatorHomePage(loginVar.stage, currentUser);					
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
	
	// Function untuk melihatkan semua data staff di tabel
	public void getAllStaffController(AdminHomePageVar adminHomePageVar) {
		ArrayList<User> userList = new ArrayList<>();
		AdminHomePage adminPage = null;
		
		// Ambil semua data staff dari database menggunakan user model
        rs = userModel.getAllStaffModel();
        
        // Memasukkan tiap row data staff ke tabel
        try {
			while(rs.next()) {
				String u = rs.getString("UserName");
				String p = rs.getString("UserPassword");
				Integer a = rs.getInt("UserAge");
				String r = rs.getString("UserRole");
				
				userList.add(new User(0, u, p, a, r));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		for (User user : userList) {
        	adminHomePageVar.table.getItems().add(user);
		}
		
		adminHomePageVar.username_col.setCellValueFactory(new PropertyValueFactory<>("UserName"));
		adminHomePageVar.age_col.setCellValueFactory(new PropertyValueFactory<>("UserAge"));
		adminHomePageVar.role_col.setCellValueFactory(new PropertyValueFactory<>("UserRole"));
	}
	
	// Function untuk mengurus form update role di admin home page
	public void handling_admin(AdminHomePageVar adminHomePageVar) {		
		adminHomePageVar.btnUpdate.setOnAction(e -> {
			// Mengambil semua value dari form update role
			String u = adminHomePageVar.username_tf.getText();
			String newRole = adminHomePageVar.role_tf.getText();
			
			// Validasi
			if(u.isEmpty()|| newRole.isEmpty()) {
				// Kalau data kosong
				adminHomePageVar.alert.setContentText("Fill in all fields!");
				adminHomePageVar.alert.showAndWait();
			}
			else if(!(newRole.equals("Admin") || newRole.equals("Operator") || newRole.equals("Technician"))) {
				// Kalau input role bukan Admin / Operator / Technician
				adminHomePageVar.alert.setContentText("Invalid role!");
				adminHomePageVar.alert.showAndWait();
			}
			else if(userModel.checkUserExist(u)) {
				// Kalau user ada
				
				// Update role staff melalui user model
				userModel.updateRole(u, newRole);
				new AdminHomePage(adminHomePageVar.stage);
			}
		});
	}

	// Function untuk menampilkan tabel technicians di add job page
	public void getAllTechnicians(AddJobPageVar ajv) {
		ArrayList<User> userList = new ArrayList<>();
		AddJobPage addJobPage = null;
		
		// Mengambil semua data technicians dari database menggunakan user model
        rs = userModel.getAllTechnicians();
        
        // Memasukkan tiap row data technician ke tabel
        try {
			while(rs.next()) {
				Integer id = rs.getInt("UserID");
				String u = rs.getString("UserName");
				String p = rs.getString("UserPassword");
				Integer a = rs.getInt("UserAge");
				String r = rs.getString("UserRole");
				
				userList.add(new User(id, u, p, a, r));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        ajv.vb = new VBox();
        ajv.table = new TableView<User>();
        ajv.userid_col = new TableColumn<>("id");
        ajv.username_col = new TableColumn<>("username");
        ajv.age_col = new TableColumn<>("age");
        ajv.role_col = new TableColumn<>("role");
        ajv.titleUserTable = new Label("User Table");
        ajv.table.getColumns().addAll(ajv.userid_col, ajv.username_col, ajv.age_col, ajv.role_col);
        
		for (User user : userList) {
			ajv.table.getItems().add(user);
		}
		
		ajv.userid_col.setCellValueFactory(new PropertyValueFactory<>("UserID"));
		ajv.username_col.setCellValueFactory(new PropertyValueFactory<>("UserName"));
		ajv.age_col.setCellValueFactory(new PropertyValueFactory<>("UserAge"));
		ajv.role_col.setCellValueFactory(new PropertyValueFactory<>("UserRole"));

		ajv.vb.getChildren().addAll(ajv.titleUserTable, ajv.table);
        
	}
}
