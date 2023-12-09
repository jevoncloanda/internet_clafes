package admin_view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import view.RegisterPage;
import view.LoginPage.LoginVar;

public class AdminHomePage {
	UserController userController = new UserController();
	public class AdminHomePageVar{
		public Stage stage;
		public Scene scene;
		public BorderPane bp;
		public HBox hb;
		public VBox vb1, vb2;
		public VBox vb;
		public TableView<User> table;
		public TableColumn<User, String> username_col, role_col;
		public TableColumn<User, Integer> age_col;
		public Label title1, title2, username, password, username_del, role;
		public TextField username_tf, username_tf2, role_tf;
		public PasswordField pass_pf;
		public Button btnUpdate, btnDelete;
		public MenuBar menuBar;
		public Menu menu;
		public MenuItem menuItemRegister;
		public Alert alert;
	}
	
	private void initializeMenu(AdminHomePageVar adminHomePageVar) {
		adminHomePageVar.menuBar = new MenuBar();
		adminHomePageVar.menu = new Menu("Menu");
		adminHomePageVar.menuItemRegister = new MenuItem("Register");
		adminHomePageVar.menuBar.getMenus().add(adminHomePageVar.menu);
		adminHomePageVar.menu.getItems().add(adminHomePageVar.menuItemRegister);
	}
	
	private void initializeAlert(AdminHomePageVar adminHomePageVar) {
		adminHomePageVar.alert = new Alert(AlertType.ERROR);
		adminHomePageVar.alert.setTitle("Homepage");
	}
	
	private void initialize(AdminHomePageVar adminHomePageVar) {
		viewAllStaff(adminHomePageVar);
		initializeAlert(adminHomePageVar);
		handle(adminHomePageVar);
	}
	
	public AdminHomePage(Stage stage) {
		AdminHomePageVar adminHomePageVar = new AdminHomePageVar();
		initialize(adminHomePageVar);
		adminHomePageVar.stage = stage;
		adminHomePageVar.stage.setResizable(false);
		adminHomePageVar.stage.setScene(adminHomePageVar.scene);
		adminHomePageVar.stage.show();
	}
	
	private void viewAllStaff(AdminHomePageVar adminHomePageVar) {
		
		userController.getAllStaffController(adminHomePageVar);
	}
	
	private void handle(AdminHomePageVar adminHomePageVar) {
		userController.handling_admin(adminHomePageVar);
	}
	
	
	
}
