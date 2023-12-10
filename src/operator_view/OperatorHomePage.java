package operator_view;

import java.sql.Date;

import controller.PCBookController;
import customer_view.CustomerHomePage.CustomerHomePageVar;
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
import model.PCBook;
import model.User;
import view.LoginPage;
import view.MakeReportPage;

public class OperatorHomePage {
	
	PCBookController pcBookController = new PCBookController();
	public class OperatorHomePageVar {
		public Stage stage;
		Scene scene;
		BorderPane bp;
		public VBox vb1, vb2;
		HBox hb;
		MenuBar menuBar;
		Menu menu;
		public MenuItem menuItemLogout, report;
		public Label title1, title2, title3, bookID_lbl;
		public TextField bookID_tf;
		public TableView<PCBook> pcBookTable;
		public TableColumn<PCBook, Integer> pbPcID_col, pbUserID_col, pbBookID_col;
		public TableColumn<PCBook, Date> pbBookedDate_col;
		public Button button_finish, button_cancel;
		public Alert alert;
	}

	private void initializeMenu(OperatorHomePageVar ov) {
		ov.menuBar = new MenuBar();
		ov.menu = new Menu("Menu");
		ov.menuItemLogout = new MenuItem("Logout");
		ov.report = new MenuItem("Report");
		ov.menuBar.getMenus().add(ov.menu);
		ov.menu.getItems().addAll(ov.menuItemLogout, ov.report);
	}

	private void initializeAlert(OperatorHomePageVar ov) {
		ov.alert = new Alert(AlertType.ERROR);
		ov.alert.setTitle("ERROR");
	}

	private void initialize(OperatorHomePageVar ov) {
		ov.bp = new BorderPane();
		ov.vb2 = new VBox();

		ov.title2 = new Label("Manage a Booking");
		ov.bookID_lbl = new Label("Input the Book ID");
		ov.bookID_tf = new TextField();
		ov.button_finish = new Button("Finish Booking");
		ov.button_cancel = new Button("Cancel Booking");

		ov.vb2.getChildren().addAll(ov.title2, ov.bookID_lbl, ov.bookID_tf, ov.button_finish, ov.button_cancel);
		
		initializeMenu(ov);
		ov.bp.setTop(ov.menuBar);
		ov.bp.setCenter(ov.vb1);
		ov.bp.setBottom(ov.vb2);
		ov.scene = new Scene(ov.bp, 700, 700);
	}
	
	private void handle(OperatorHomePageVar ov, User currentUser) {
		pcBookController.handling_finishBook(ov, currentUser);
		pcBookController.handling_cancelBook(ov, currentUser);
		ov.menuItemLogout.setOnAction(e->{
			try {
				new LoginPage(ov.stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		ov.report.setOnAction(e->{
			try {
				new MakeReportPage(ov.stage, currentUser);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	public OperatorHomePage(Stage stage, User currentUser) {
		OperatorHomePageVar ov = new OperatorHomePageVar();
		pcBookController.handling_viewAllPCBooks(ov);
		initialize(ov);
		initializeAlert(ov);
		handle(ov, currentUser);
		ov.stage = stage;
		ov.stage.setResizable(true);
		ov.stage.setScene(ov.scene);
		ov.stage.show();
	}
}
