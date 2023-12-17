package view;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.UserController;
import database.Connect;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import view.RegisterPage.RegisterVar;

public class LoginPage {
	UserController userController = new UserController();
	public class LoginVar{
		public Stage stage;
		Scene scene;
		VBox vb1, vb2;
		BorderPane bp;
		GridPane gp;
		MenuBar menuBar;
		Menu menu;
		MenuItem menuItemRegister;
		Label title, username_lbl, password_lbl;
		public TextField username_tf;
		public PasswordField password_pf;
		public Button button_login;
		public Alert alert;
	}
	
	private void initializeMenu(LoginVar loginVar) {
		loginVar.menuBar = new MenuBar();
		loginVar.menu = new Menu("Menu");
		loginVar.menuItemRegister = new MenuItem("Register");
		loginVar.menuBar.getMenus().add(loginVar.menu);
		loginVar.menu.getItems().add(loginVar.menuItemRegister);
	}
	
	private void initializeAlert(LoginVar loginVar) {
		loginVar.alert = new Alert(AlertType.ERROR);
		loginVar.alert.setTitle("Login");
	}
	
	private void initialize(LoginVar loginVar) {
		loginVar.bp = new BorderPane();
		loginVar.vb1 = new VBox();
		loginVar.vb2 = new VBox();
		loginVar.gp = new GridPane();
		
		// Untuk form login
		loginVar.title = new Label("Login");
		loginVar.username_lbl = new Label("UserName");
		loginVar.username_tf = new TextField();
		loginVar.password_lbl = new Label("Password");
		loginVar.password_pf = new PasswordField();
		loginVar.button_login = new Button("LOGIN");
		
		initializeMenu(loginVar);
		loginVar.vb1.getChildren().add(loginVar.title);
		loginVar.vb2.getChildren().addAll(loginVar.username_lbl, 
				loginVar.username_tf,
				loginVar.password_lbl, 
				loginVar.password_pf,
				loginVar.button_login);

		loginVar.gp.add(loginVar.vb1, 0, 0);
		loginVar.gp.add(loginVar.vb2, 0, 1);

		loginVar.bp.setTop(loginVar.menuBar);
		loginVar.bp.setCenter(loginVar.gp);
		
		loginVar.scene = new Scene(loginVar.bp, 600, 600);
	}
	
	private void handle(LoginVar loginVar) {
		// Koneksi dengan user controller
		userController.handling_login(loginVar);
		
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
		initializeAlert(loginVar);
		handle(loginVar);
		loginVar.stage = stage;
		loginVar.stage.setResizable(false);
		loginVar.stage.setScene(loginVar.scene);
		loginVar.stage.show();
	}
}
