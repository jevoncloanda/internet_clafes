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
import model.TransactionHeader;
import model.User;
import view.LoginPage;

public class ViewAllTransactionsPage {
	TransactionController transactionController = new TransactionController();
	public class ViewAllTransactionPageVar {
		public Stage stage;
		Scene scene;
		BorderPane bp;
		public VBox vb1, vb2;
		MenuBar menuBar;
		Menu menu;
		public MenuItem logout, home, addJob, viewReport, pcManagement;
		public Label tdTitle, thTitle;
		
		//Transaction Detail Table
		public TableView<TransactionDetail> tdTable;
		public TableColumn<TransactionDetail, Integer> tdPcId_col;
		public TableColumn<TransactionDetail, String> tdCustomerName_col;
		public TableColumn<TransactionDetail, Date> tdBookedTime_col;
		
		// Transaction Header table
		public TableView<TransactionHeader> thTable;
		public TableColumn<TransactionHeader, Integer> thId_col, staffId_col;
		public TableColumn<TransactionHeader, String> staffName_col;
		public TableColumn<TransactionHeader, Date> thDate_col;
	}
	
	public void initializeMenu(ViewAllTransactionPageVar vav) {
		vav.menuBar = new MenuBar();
		vav.menu = new Menu("Menu");
		vav.home = new MenuItem("Home Page");
		vav.logout = new MenuItem("Logout");
		vav.addJob = new MenuItem("Add Technician Job");
		vav.viewReport = new MenuItem("View Report");
		vav.pcManagement = new MenuItem("PC Management Page");
		vav.menuBar.getMenus().add(vav.menu);
		vav.menu.getItems().addAll(vav.logout, vav.home,
				vav.addJob, vav.viewReport, vav.pcManagement);
	}
	
	private void initialize(ViewAllTransactionPageVar vav) {
		vav.bp = new BorderPane();
		
		initializeMenu(vav);
		
		vav.bp.setTop(vav.menuBar);
		vav.bp.setCenter(vav.vb1);
		vav.bp.setBottom(vav.vb2);
		vav.scene = new Scene(vav.bp, 700, 700);
	}
	
	private void handle(ViewAllTransactionPageVar vav) {
		// Untuk logout
		vav.logout.setOnAction(e->{
            try {
                new LoginPage(vav.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		// Untuk ke home page
		vav.home.setOnAction(e->{
            try {
                new AdminHomePage(vav.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		// Untuk ke page add job
		vav.addJob.setOnAction(e->{
            try {
                new AddJobPage(vav.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		// Untuk ke page view report
		vav.viewReport.setOnAction(e->{
            try {
                new ViewReportsPage(vav.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		// Untuk ke page pc management
		vav.pcManagement.setOnAction(e->{
			try {
              new PCManagementPage(vav.stage);
          } catch (Exception e1) {
              e1.printStackTrace();
          }
		});
	}
	
	public ViewAllTransactionsPage(Stage stage) {
		ViewAllTransactionPageVar vav = new ViewAllTransactionPageVar();
		// Untuk generate tabel tabel
		transactionController.handling_viewTransactionDetailsAdmin(vav);
		transactionController.handling_viewTransactionHeadersAdmin(vav);
		
		initialize(vav);
		handle(vav);
		vav.stage = stage;
		vav.stage.setResizable(true);
		vav.stage.setScene(vav.scene);
		vav.stage.show();
	}
}
