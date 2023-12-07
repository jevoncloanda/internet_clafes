package view;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPage {
	public class LoginVar{
		Stage stage;
		Scene scene;
		VBox vb1, vb2;
		BorderPane bp;
		GridPane gp;
		MenuBar menuBar;
		Menu menu;
		MenuItem menuItemRegister;
		Label title, username_lbl, password_lbl;
		TextField username_tf;
		PasswordField password_pf;
		Button button_submit;
	}
	
	private void initializeMenu(LoginVar loginVar) {
		loginVar.menuBar = new MenuBar();
		loginVar.menu = new Menu("Menu");
		loginVar.menuItemRegister = new MenuItem("Register");
		loginVar.menuBar.getMenus().add(loginVar.menu);
		loginVar.menu.getItems().add(loginVar.menuItemRegister);
	}
	
	private void initialize(LoginVar loginVar) {
		loginVar.bp = new BorderPane();
		loginVar.vb1 = new VBox();
		loginVar.vb2 = new VBox();
		loginVar.gp = new GridPane();
		
		loginVar.title = new Label("Login");
		loginVar.username_lbl = new Label("UserName");
		loginVar.username_tf = new TextField();
		loginVar.password_lbl = new Label("Password");
		loginVar.password_pf = new PasswordField();
		loginVar.button_submit = new Button("LOGIN");
		
		initializeMenu(loginVar);
		loginVar.vb1.getChildren().add(loginVar.title);
		loginVar.vb2.getChildren().addAll(loginVar.username_lbl, 
				loginVar.username_tf,
				loginVar.password_lbl, 
				loginVar.password_pf,
				loginVar.button_submit);

		loginVar.gp.add(loginVar.vb1, 0, 0);
		loginVar.gp.add(loginVar.vb2, 0, 1);

		loginVar.bp.setTop(loginVar.menuBar);
		loginVar.bp.setCenter(loginVar.gp);
		
		loginVar.scene = new Scene(loginVar.bp, 600, 600);
	}
	
	private void handle(LoginVar loginVar) {
		Connect con = Connect.getInstance();
		
		loginVar.button_submit.setOnAction(e->{
//			ResultSet rs = con.selectData("SELECT * FROM users");
//			
//			try {
//				while(rs.next()) {
//					String u = rs.getString("Username");
//					String p = rs.getString("Password");
//					
//					if(loginVar.username_tf.getText().equals(u) && loginVar.password_pf.getText().equals(p)) {
//						new HomePage(loginVar.stage);
//					}
//					
//				}
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		});
		
		loginVar.menuItemRegister.setOnAction(e->{
			try {
				new RegisterPage().start(loginVar.stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	public LoginPage(Stage stage) {
		LoginVar loginVar = new LoginVar();
		initialize(loginVar);
		handle(loginVar);
		loginVar.stage = stage;
		loginVar.stage.setResizable(false);
		loginVar.stage.setScene(loginVar.scene);
		loginVar.stage.show();
	}
}
