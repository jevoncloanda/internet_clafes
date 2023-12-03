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
	Stage stage;
	Scene scene;
	VBox vb;
	BorderPane bp;
	GridPane gp;
	MenuBar menuBar;
	Menu menu;
	MenuItem menuItemRegister;
	Label title, username, password;
	TextField username_tf;
	PasswordField password_tf;
	Button submit;
	
	private void initializeMenu() {
		menuBar = new MenuBar();
		menu = new Menu("Menu");
		menuItemRegister = new MenuItem("Register");
		menuBar.getMenus().add(menu);
		menu.getItems().add(menuItemRegister);
	}
	
	private void initialize() {
		bp = new BorderPane();
		vb = new VBox();
		gp = new GridPane();
		
		title = new Label("Login");
		username = new Label("Username");
		username_tf = new TextField();
		password = new Label("Password");
		password_tf = new PasswordField();
		submit = new Button("LOGIN");
		
		initializeMenu();
		vb.getChildren().add(title);
		vb.setAlignment(Pos.CENTER);
		gp.add(vb, 0, 1);
		gp.add(username, 0, 2);
		gp.add(username_tf, 0, 3);
		gp.add(password, 0, 4);
		gp.add(password_tf, 0, 5);
		gp.add(submit, 0, 6);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		bp.setTop(menuBar);
		bp.setCenter(gp);
		scene = new Scene(bp, 650, 650);
	}
	
	private void handling() {
		Connect con = Connect.getInstance();
		
		submit.setOnAction(e->{
			ResultSet rs = con.selectData("SELECT * FROM User");
			
			try {
				while(rs.next()) {
					String u = rs.getString("Username");
					String p = rs.getString("Password");
					
					if(username_tf.getText().equals(u) && password_tf.getText().equals(p)) {
						new HomePage(stage);
					}
					
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		menuItemRegister.setOnAction(e->{
			try {
				new RegisterPage().start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	private void setStyle() {
		title.setStyle("-fx-font-weight: bold;" + "-fx-font-family: Serif;" + "-fx-font-size: 35px;");
		submit.setStyle("-fx-background-color: blue;" + "-fx-text-fill: white;" + "-fx-min-width: 480px;" + "-fx-font-weight: bold;");
	}
	
	public LoginPage(Stage stage) {
		initialize();
		handling();
		setStyle();
		this.stage = stage;
		this.stage.setResizable(false);
		this.stage.setScene(scene);
		this.stage.show();
	}
}
