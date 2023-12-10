package admin_view;

import java.sql.Date;

import controller.ReportController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Report;
import model.User;
import view.LoginPage;

public class ViewReportsPage {
	ReportController reportController = new ReportController();
	public class ViewReportsPageVar{
		public Stage stage;
		Scene scene;
		BorderPane bp;
		public VBox vb1;
		MenuBar menuBar;
		Menu menu;
		public MenuItem logout, home, addJob, viewTransaction, pcManagement;
		public Label tableTitle;
		public TableView<Report> reportTable;
		public TableColumn<Report, Integer> reportID_col, pcID_col;
		public TableColumn<Report, String> userRole_col, reportNote_col;
		public TableColumn<Report, Date> reportDate_col;
	}
	
	public void initializeMenu(ViewReportsPageVar vr) {
		vr.menuBar = new MenuBar();
		vr.menu = new Menu("Menu");
		vr.logout = new MenuItem("Logout");
		vr.home = new MenuItem("Home Page");
		vr.viewTransaction = new MenuItem("View Customer Transactions");
		vr.addJob = new MenuItem("Add Technician Job");
		vr.pcManagement = new MenuItem("PC Management Page");
		vr.menuBar.getMenus().add(vr.menu);
		vr.menu.getItems().addAll(vr.logout, vr.home, vr.viewTransaction,
				vr.addJob, vr.pcManagement);
	}
	
	public void initialize(ViewReportsPageVar vr) {
		vr.bp = new BorderPane();
		initializeMenu(vr);
		
		vr.bp.setTop(vr.menuBar);
		vr.bp.setCenter(vr.vb1);
		vr.scene = new Scene(vr.bp, 700, 700);
	}
	
	public void handle(ViewReportsPageVar vr) {
		vr.logout.setOnAction(e->{
            try {
                new LoginPage(vr.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		vr.home.setOnAction(e->{
            try {
                new AdminHomePage(vr.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		vr.viewTransaction.setOnAction(e->{
            try {
                new ViewAllTransactionsPage(vr.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		vr.addJob.setOnAction(e->{
            try {
                new AddJobPage(vr.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		vr.pcManagement.setOnAction(e->{
			try {
              new PCManagementPage(vr.stage);
          } catch (Exception e1) {
              e1.printStackTrace();
          }
		});
	}
	
	public ViewReportsPage(Stage stage) {
		ViewReportsPageVar vr = new ViewReportsPageVar();
		reportController.getAllReportData(vr);
		initialize(vr);
		handle(vr);
		vr.stage = stage;
		vr.stage.setResizable(true);
		vr.stage.setScene(vr.scene);
		vr.stage.show();
	}
}
