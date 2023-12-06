package view;

import controller.UserController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

public class RegisterPage extends Application{
	UserController userController = new UserController();
	public class RegisterVar {
		public Stage stage;
		Scene scene;
		BorderPane bp;
		GridPane gp;
		VBox vb1, vb2;
		MenuBar menuBar;
		Menu menu;
		public MenuItem menuItemLogin;
		Label title, username_lbl, password_lbl, age_lbl, confpassword_lbl;
		public TextField username_tf;
		public PasswordField pass_pf, confpass_pf;
		public Spinner<Integer> age_spin;
		public Button button_regis;
		public Alert alert;
	}
	
	private void initializeMenu(RegisterVar registerVar) {
		registerVar.menuBar = new MenuBar();
		registerVar.menu = new Menu("Menu");
		registerVar.menuItemLogin = new MenuItem("Login");
		registerVar.menuBar.getMenus().add(registerVar.menu);
		registerVar.menu.getItems().add(registerVar.menuItemLogin);
	}

	private void initializeAlert(RegisterVar registerVar) {
		registerVar.alert = new Alert(AlertType.ERROR);
		registerVar.alert.setTitle("Register");
	}

	private void initialize(RegisterVar registerVar) {
		registerVar.bp = new BorderPane();
		registerVar.gp = new GridPane();
		registerVar.vb1 = new VBox();
		registerVar.vb2 = new VBox();

		registerVar.title = new Label("Registration");
		registerVar.username_lbl = new Label("Username");
		registerVar.username_tf = new TextField();
		registerVar.password_lbl = new Label("Password");
		registerVar.pass_pf = new PasswordField();
		registerVar.confpassword_lbl = new Label("Confirm Password");
		registerVar.confpass_pf = new PasswordField();
		registerVar.age_lbl = new Label("Age");
		registerVar.age_spin = new Spinner<>(1, 100, 17); // min max n initial
		registerVar.button_regis = new Button("REGISTER");
		
		initializeMenu(registerVar);
		registerVar.vb1.getChildren().add(registerVar.title);
		registerVar.vb2.getChildren().addAll(registerVar.username_lbl, 
				registerVar.username_tf,
				registerVar.password_lbl, 
				registerVar.pass_pf,
				registerVar.confpassword_lbl,
				registerVar.confpass_pf,
				registerVar.age_lbl, 
				registerVar.age_spin,
				registerVar.button_regis);

		registerVar.gp.add(registerVar.vb1, 0, 0);
		registerVar.gp.add(registerVar.vb2, 0, 1);

		registerVar.bp.setTop(registerVar.menuBar);
		registerVar.bp.setCenter(registerVar.gp);

		registerVar.scene = new Scene(registerVar.bp, 600, 600);
	}

	private void handle(RegisterVar registerVar) {
		userController.handling_regis(registerVar);
		
		registerVar.menuItemLogin.setOnAction(e->{
			try {
				new LoginPage(registerVar.stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		RegisterVar registerVar = new RegisterVar();
		initialize(registerVar);
		initializeAlert(registerVar);
		handle(registerVar);

		registerVar.stage = primaryStage;
		registerVar.stage.setScene(registerVar.scene);
		registerVar.stage.setResizable(false);
		registerVar.stage.show();
	}

}
