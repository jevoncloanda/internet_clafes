package view;

import controller.UserController;
import database.Connect;
import javafx.application.Application;
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
import model.User;

public class RegisterPage extends Application{
	Stage stage;
	Scene scene;
	VBox vb;
	BorderPane bp;
	GridPane gp;
	MenuBar menuBar;
	Menu menu;
	MenuItem menuItemLogin;
	Label title, username, password, confirmPassword, age;
	TextField username_tf;
	Spinner<Integer> age_tf;
	PasswordField password_tf, confirmPassword_tf;
	Button submit;
	
	UserController uc = new UserController();
	
	private void initializeMenu() {
		menuBar = new MenuBar();
		menu = new Menu("Menu");
		menuItemLogin = new MenuItem("Login");
		menuBar.getMenus().add(menu);
		menu.getItems().add(menuItemLogin);
	}
	
	private void initialize() {
		bp = new BorderPane();
		vb = new VBox();
		gp = new GridPane();
		
		title = new Label("Registration");
		username = new Label("Username");
		username_tf = new TextField();
		password = new Label("Password");
		password_tf = new PasswordField();
		confirmPassword = new Label("Confirm Password");
		confirmPassword_tf = new PasswordField();
		age = new Label("Age");
		age_tf = new Spinner<>(1,100,17);
		submit = new Button("REGISTER");
		
		initializeMenu();
		vb.getChildren().add(title);
		vb.setAlignment(Pos.CENTER);
		gp.add(vb, 0, 1);
		gp.add(username, 0, 2);
		gp.add(username_tf, 0, 3);
		gp.add(password, 0, 4);
		gp.add(password_tf, 0, 5);
		gp.add(confirmPassword, 0, 6);
		gp.add(confirmPassword_tf, 0, 7);
		gp.add(age, 0, 8);
		gp.add(age_tf, 0, 9);
		gp.add(submit, 0, 10);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		bp.setTop(menuBar);
		bp.setCenter(gp);
		scene = new Scene(bp, 650, 650);
	}
	
	private void customerRegister(String u, String p, Integer a) {
		uc.customerRegister(u, p, a);
	}
	
	private void handling() {
		submit.setOnAction(e->{
			if(password_tf.getText().equals(confirmPassword_tf.getText())) {
				customerRegister(username_tf.getText(), password_tf.getText(), age_tf.getValue());
				new LoginPage(stage);				
			} else {
				System.out.println("Error");
			}
		});
		
		menuItemLogin.setOnAction(e->{
			new LoginPage(stage);
		});
	}
	
	private void setStyle() {
		title.setStyle("-fx-font-weight: bold;" + "-fx-font-family: Serif;" + "-fx-font-size: 35px;");
		submit.setStyle("-fx-background-color: blue;" + "-fx-text-fill: white;" + "-fx-min-width: 480px;" + "-fx-font-weight: bold;");
		age_tf.setStyle("-fx-min-width: 480px");
	}

	@Override
	public void start(Stage stage) throws Exception {
		initialize();
		handling();
		setStyle();
		this.stage = stage;
		this.stage.setScene(scene);
		this.stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
