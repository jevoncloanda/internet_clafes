package view;

import admin_view.AddJobPage;
import controller.JobController;
import controller.PCController;
import controller.ReportController;
import customer_view.CustomerHomePage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PC;
import model.User;
import operator_view.OperatorHomePage;

public class MakeReportPage {
	ReportController reportController = new ReportController();
	PCController pcController = new PCController();
	public class MakeReportPageVar {
		public Stage stage;
		Scene scene;
		public VBox vb1, vb2, vb3;
		BorderPane bp;
		GridPane gp;
		public MenuBar menuBar;
		public Menu menu;
		public MenuItem logout, home;
		public Alert alert, success;
		Label title, pcID_lbl, reportNote_lbl;
		public Spinner<Integer> pcID_spin;
		public TextField reportNote_tf;
		public Button makeReportBtn;
		
		public TableView<PC> pcTable;
		public TableColumn<PC, Integer> pcID_col;
		public TableColumn<PC, String> pcCondition_col;
	}
	
	private void initializeMenu(MakeReportPageVar mrp) {
		mrp.menuBar = new MenuBar();
		mrp.menu = new Menu("Menu");
		mrp.logout = new MenuItem("Logout");
		mrp.home = new MenuItem("Home Page");
		mrp.menuBar.getMenus().add(mrp.menu);
		mrp.menu.getItems().addAll(mrp.logout,mrp.home);
	}
	
	private void initializeAlert(MakeReportPageVar mrp) {
		mrp.alert = new Alert(AlertType.ERROR);
		mrp.alert.setTitle("ERROR");
		
		mrp.success = new Alert(AlertType.INFORMATION);
		mrp.success.setTitle("SUCCESS");
	}
	
	private void initialize(MakeReportPageVar mrp) {
		mrp.bp = new BorderPane();
		mrp.vb2 = new VBox();
		mrp.vb3 = new VBox();
		mrp.gp = new GridPane();
		
		// Untuk form make report
		mrp.title = new Label("Make Report");
		mrp.pcID_lbl = new Label("Input PC ID");
		mrp.pcID_spin = new Spinner<>(1, 999999999, 1);
		mrp.reportNote_lbl = new Label("Report Note");
		mrp.reportNote_tf = new TextField();
		mrp.makeReportBtn = new Button("SEND REPORT");
		
		initializeMenu(mrp);
		mrp.vb2.getChildren().add(mrp.title);
		mrp.vb3.getChildren().addAll(mrp.pcID_lbl, 
				mrp.pcID_spin,
				mrp.reportNote_lbl, 
				mrp.reportNote_tf,
				mrp.makeReportBtn);

		mrp.gp.add(mrp.vb2, 0, 0);
		mrp.gp.add(mrp.vb3, 0, 1);

		mrp.bp.setTop(mrp.menuBar);
		mrp.bp.setCenter(mrp.vb1);
		mrp.bp.setBottom(mrp.gp);
		
		mrp.scene = new Scene(mrp.bp, 600, 600);
	}
	
	private void handle(MakeReportPageVar mrp, User user) {
		// Koneksi ke report controller
		reportController.addNewReport(mrp, user);
		
		// Untuk logout
		mrp.logout.setOnAction(e->{
            try {
                new LoginPage(mrp.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		// Untuk kembali ke home page
		mrp.home.setOnAction(e->{
            try {
            	if(user.getUserRole().equals("Customer")) {
            		new CustomerHomePage(mrp.stage, user);
            	}
            	else if(user.getUserRole().equals("Operator")) {
            		new OperatorHomePage(mrp.stage, user);
            	}
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
	}
	
	public MakeReportPage(Stage stage, User user) {
		MakeReportPageVar mrp = new MakeReportPageVar();
		
		// Untuk generate tabel pc menggunakan pc controller
		pcController.handling_viewPC(mrp);
		initialize(mrp);
		initializeAlert(mrp);
		handle(mrp, user);
		mrp.stage = stage;
		mrp.stage.setResizable(false);
		mrp.stage.setScene(mrp.scene);
		mrp.stage.show();
	}
}
