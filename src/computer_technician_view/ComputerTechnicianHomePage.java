package computer_technician_view;

import controller.JobController;
import javafx.geometry.Insets;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Job;
import model.User;
import view.LoginPage;
import view.MakeReportPage;
import view.LoginPage.LoginVar;
import view.RegisterPage.RegisterVar;

public class ComputerTechnicianHomePage {
	JobController jobController = new JobController();
	public class ComputerTechnicianHomePageVar{
		public Stage stage;
		Scene scene;
		BorderPane bp;
		GridPane gp;
		public VBox vb1, vb2;
		MenuBar menuBar;
		Menu menu;
		public MenuItem menuItemLogout;
		public TableView<Job> table;
		public TableColumn<Job, Integer> jobID_col;
		public TableColumn<Job, Integer> userID_col;
		public TableColumn<Job, Integer> pcID_col;
		public TableColumn<Job, String> jobStatus_col;
		public Label jobID, userID, pcID, jobStatus, title;
		public Spinner<Integer> job_spin;
		public Button btnComplete;
		public Alert alert;
	}
	
	private void initializeMenu(ComputerTechnicianHomePageVar tv) {
		tv.menuBar = new MenuBar();
		tv.menu = new Menu("Menu");
		tv.menuItemLogout = new MenuItem("Logout");
		tv.menuBar.getMenus().add(tv.menu);
		tv.menu.getItems().add(tv.menuItemLogout);
	}
	
	private void initializeAlert(ComputerTechnicianHomePageVar tv) {
		tv.alert = new Alert(AlertType.ERROR);
		tv.alert.setTitle("Login");
	}
	
	private void initialize(ComputerTechnicianHomePageVar tv) {
		
		tv.bp = new BorderPane();
		tv.vb2 = new VBox();
		
		// Untuk form complete job
		tv.title = new Label("Complete Job");
		tv.jobStatus = new Label("Input Job ID");
		tv.job_spin = new Spinner<>(1, 10000000, 1);
		tv.btnComplete = new Button("COMPLETE JOB");
		
		initializeMenu(tv);
		tv.vb2.getChildren().addAll(tv.title, 
				tv.jobStatus, 
				tv.job_spin, 
				tv.btnComplete);
		
		tv.bp.setTop(tv.menuBar);
		tv.bp.setCenter(tv.vb1);
		tv.bp.setBottom(tv.vb2);
		tv.scene = new Scene(tv.bp, 600, 600);
	}
	
	private void handle(ComputerTechnicianHomePageVar tv, User user) {
		// Koneksi dengan job controller
		jobController.completeJob(tv, user);
		
		tv.menuItemLogout.setOnAction(e->{
			try {
				new LoginPage(tv.stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	public ComputerTechnicianHomePage(Stage stage, User user) {
		ComputerTechnicianHomePageVar technicianHomePageVar = new ComputerTechnicianHomePageVar();
		
		// Untuk generate tabel jobs technician tertentu melalui job controller
		jobController.viewJobs(technicianHomePageVar, user);
		initialize(technicianHomePageVar);
		initializeAlert(technicianHomePageVar);
		handle(technicianHomePageVar, user);
		
		technicianHomePageVar.stage = stage;
		technicianHomePageVar.stage.setResizable(true);
		technicianHomePageVar.stage.setScene(technicianHomePageVar.scene);
		technicianHomePageVar.stage.show();
	}
}
