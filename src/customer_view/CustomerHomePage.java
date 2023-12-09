package customer_view;

import java.sql.Date;
import java.util.ArrayList;

import controller.PCController;
import controller.TransactionController;
import database.Connect;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PC;
import model.TransactionDetail;
import model.User;

public class CustomerHomePage {
	PCController pcController = new PCController();
	TransactionController transactionController = new TransactionController();

	public class CustomerHomePageVar {
		public Stage stage;
		Scene scene;
		BorderPane bp;
		GridPane gp;
		public VBox vb, vb1, vb2;
		HBox hb;
		MenuBar menuBar;
		Menu menu;
//		public MenuItem menuItemLogin;
		public Label title1, title2, title3, pcID_lbl;
		public TextField pcID_tf;
		public DatePicker bookedTime_pick;
		public TableView<PC> pcTable;
		public TableView<TransactionDetail> tdTable;
		public TableColumn<PC, Integer> pcID_col;
		public TableColumn<PC, String> pcCondition_col;
		public TableColumn<TransactionDetail, Integer> tdID_col, tdPcID_col;
		public TableColumn<TransactionDetail, String> tdCustomerName_col;
		public TableColumn<TransactionDetail, Date> tdBookedTime_col;
		public Button button_book;
		public Alert alert;
	}

//	private void initializeMenu(CustomerHomePageVar cv) {
//		cv.menuBar = new MenuBar();
//		cv.menu = new Menu("Menu");
//		cv.menuItemRegister = new MenuItem("Register");
//		cv.menuBar.getMenus().add(cv.menu);
//		cv.menu.getItems().add(cv.menuItemRegister);
//	}

	private void initializeAlert(CustomerHomePageVar cv) {
		cv.alert = new Alert(AlertType.ERROR);
		cv.alert.setTitle("ERROR");
	}

	private void initialize(CustomerHomePageVar cv) {
		cv.bp = new BorderPane();
		cv.vb2 = new VBox();

		cv.title3 = new Label("Book a PC");
		cv.pcID_lbl = new Label("Input the PC ID you want to Book");
		cv.pcID_tf = new TextField();
		cv.bookedTime_pick = new DatePicker();
		cv.button_book = new Button("Book");
		
		cv.vb2.getChildren().addAll(cv.title3, cv.pcID_lbl, cv.pcID_tf, cv.bookedTime_pick, cv.button_book);

		cv.bp.setTop(cv.vb);
		cv.bp.setCenter(cv.vb1);
		cv.bp.setBottom(cv.vb2);
		cv.scene = new Scene(cv.bp, 700, 700);
	}
	
	public CustomerHomePage(Stage stage, User currentUser) {
		CustomerHomePageVar cv = new CustomerHomePageVar();
		pcController.handling_viewPC(cv);
		transactionController.handling_viewTransactionDetailByCustomer(cv, currentUser);
		initialize(cv);
		initializeAlert(cv);
		transactionController.handling_bookPC(cv, currentUser);
		cv.stage = stage;
		cv.stage.setResizable(true);
		cv.stage.setScene(cv.scene);
		cv.stage.show();
	}
}
