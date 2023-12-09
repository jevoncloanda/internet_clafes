<<<<<<< Updated upstream
//package customer_view;
//
//import java.util.ArrayList;
//
//import database.Connect;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.Menu;
//import javafx.scene.control.MenuBar;
//import javafx.scene.control.MenuItem;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.Spinner;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import model.PC;
//
//public class CustomerHomePage {
//
//	public class CustomerHomePageVar {
//		public Stage stage;
//		Scene scene;
//		BorderPane bp;
//		GridPane gp;
//		public VBox vb1, vb2;
//		MenuBar menuBar;
//		Menu menu;
//		public MenuItem menuItemLogin;
//		public TableView<PC> table;
//		public TableColumn<PC, Integer> pcID_col; 
//		public TableColumn<PC, String> pcStatus_col;
//	}
//	
//	private void initialize() {
=======
package customer_view;

import java.util.ArrayList;

import database.Connect;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PC;

public class CustomerHomePage {

	public class CustomerHomePageVar {
		public Stage stage;
		Scene scene;
		BorderPane bp;
		GridPane gp;
		public VBox vb1, vb2;
		MenuBar menuBar;
		Menu menu;
		public MenuItem menuItemLogin;
		public TableView<PC> table;
		public TableColumn<PC, Integer> pcID_col; 
		public TableColumn<PC, String> pcStatus_col;
	}
	
	private void initialize() {
>>>>>>> Stashed changes
//		getData();
//		
//		bp = new BorderPane();
//		hb = new HBox();
//		vb1 = new VBox();
//		vb2 = new VBox();
//		
//		title1 = new Label("Update User");
//		username = new Label("Username");
//		username_tf = new TextField();
//		password = new Label("Password");
//		pass_pf = new PasswordField();
//		btnUpdate = new Button("UPDATE");
//		
//		title2 = new Label("Delete User");
//		username_del = new Label("Username");
//		username_tf2 = new TextField();
//		btnDelete = new Button("DELETE");
//		
//		vb1.getChildren().addAll(title1, username, username_tf, password, pass_pf, btnUpdate);
//		vb2.getChildren().addAll(title2, username_del, username_tf2, btnDelete);
//		
//		hb.getChildren().addAll(vb1, vb2);
//		hb.setPadding(new Insets(0, 0, 0, 30));
//		
//		vb1.setSpacing(10);
//		vb2.setSpacing(10);
//		hb.setSpacing(50);
//		
//		bp.setTop(vb);
//		bp.setCenter(hb);
//		scene = new Scene(bp, 650, 650);
<<<<<<< Updated upstream
//	}
//	
=======
	}
	
>>>>>>> Stashed changes
//	public HomePage(Stage stage) {
//		initialize();
//		setStyle();
//		this.stage = stage;
//		this.stage.setResizable(false);
//		this.stage.setScene(scene);
//		this.stage.show();
//	}
<<<<<<< Updated upstream
//}
=======
}
>>>>>>> Stashed changes
