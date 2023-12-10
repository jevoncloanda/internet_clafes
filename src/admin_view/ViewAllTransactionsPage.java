package admin_view;

import java.sql.Date;

import admin_view.AdminHomePage.AdminHomePageVar;
import controller.TransactionController;
import customer_view.CustomerHomePage.CustomerHomePageVar;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PC;
import model.Transaction;
import model.TransactionDetail;
import model.User;
import view.LoginPage;

public class ViewAllTransactionsPage {
	TransactionController transactionController = new TransactionController();
	public class ViewAllTransactionPageVar {
		public Stage stage;
		Scene scene;
		BorderPane bp;
		public VBox vb1;
		MenuBar menuBar;
		Menu menu;
		public MenuItem logout, home, addJob;
		public Label tableTitle;
		public TableView<Transaction> tdTable;
		public TableColumn<Transaction, Integer> pcID_col, tdID_col, staffID_col;
		public TableColumn<Transaction, String> customerName_col, staffName_col;
		public TableColumn<Transaction, Date> tdDate_col;
	}
	
	public void initializeMenu(ViewAllTransactionPageVar vav) {
		vav.menuBar = new MenuBar();
		vav.menu = new Menu("Menu");
		vav.home = new MenuItem("Home Page");
		vav.logout = new MenuItem("Logout");
		vav.addJob = new MenuItem("Add Technician Job");
		vav.menuBar.getMenus().add(vav.menu);
		vav.menu.getItems().addAll(vav.logout, vav.home,
				vav.addJob);
	}
	
	private void initialize(ViewAllTransactionPageVar vav) {
		vav.bp = new BorderPane();
		
		initializeMenu(vav);
		
		vav.bp.setTop(vav.menuBar);
		vav.bp.setCenter(vav.vb1);
		vav.scene = new Scene(vav.bp, 700, 700);
	}
	
	private void handle(ViewAllTransactionPageVar vav) {
		vav.logout.setOnAction(e->{
            try {
                new LoginPage(vav.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		vav.home.setOnAction(e->{
            try {
                new AdminHomePage(vav.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		vav.addJob.setOnAction(e->{
            try {
                new AddJobPage(vav.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
	}
	
	public ViewAllTransactionsPage(Stage stage) {
		ViewAllTransactionPageVar vav = new ViewAllTransactionPageVar();
		transactionController.handling_viewTransactionsAdmin(vav);
		initialize(vav);
		handle(vav);
		vav.stage = stage;
		vav.stage.setResizable(true);
		vav.stage.setScene(vav.scene);
		vav.stage.show();
	}
}
