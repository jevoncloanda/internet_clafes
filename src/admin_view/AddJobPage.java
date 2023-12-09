package admin_view;

import admin_view.AdminHomePage.AdminHomePageVar;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class AddJobPage {
	public class AddJobPageVar {
		public Stage stage;
		public Scene scene;
		BorderPane bp;
		public VBox vb;
		public VBox vb1;
		public TableView<User> table;
		public TableColumn<User, String> username_col, role_col;
		public TableColumn<User, Integer> age_col;
		public Label title1, title2, username, password, username_del, role;
		public TextField username_tf, role_tf;
		public Button addJobBtn;
		public MenuBar menuBar;
		public Menu menu;
		public MenuItem logout, home;
		public Alert alert;
	}
	
	public void initializeMenu(AddJobPageVar ajv) {
		ajv.menuBar = new MenuBar();
		ajv.menu = new Menu("Menu");
		ajv.logout = new MenuItem("Logout");
		ajv.home = new MenuItem("Home Page");
		ajv.menuBar.getMenus().add(ajv.menu);
		ajv.menu.getItems().addAll(ajv.logout,
				ajv.home);
	}
	
	public void initialize(AddJobPageVar ajv) {
		ajv.bp = new BorderPane();
		ajv.vb1 = new VBox();
	}
}
