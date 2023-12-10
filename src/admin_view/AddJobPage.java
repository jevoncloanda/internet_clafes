package admin_view;

import java.sql.Date;

import admin_view.AdminHomePage.AdminHomePageVar;
import controller.JobController;
import controller.PCController;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Job;
import model.PC;
import model.TransactionDetail;
import model.User;
import view.LoginPage;
import view.LoginPage.LoginVar;

public class AddJobPage {
	JobController jobController = new JobController();
	UserController userController = new UserController();
	PCController pcController = new PCController();
	public class AddJobPageVar {
		public Stage stage;
		public Scene scene;
		public BorderPane bp;
		public HBox hb, hbtabel;
		public VBox vb;
		public VBox vb1, vb2, vbJob;
		
		public TableView<User> table;
		public TableColumn<User, String> username_col, role_col;
		public TableColumn<User, Integer> age_col, userid_col;
		public Label addJobTitle, updateJobTitle, userID, pcID, jobID, jobStatus, jobStatus2;
		Label userIDUpdate, pcIDUpdate;
		public TextField jobStatus_tf, jobStatus2_tf;
		public Spinner<Integer> userID_spin, pcID_spin, jobID_spin;
		public Spinner<Integer> userIDUpdate_spin, pcIDUpdate_spin;
		public Button addJobBtn, updateJobBtn;
		
		// ADD TABEL PC
		public TableView<PC> pcTable;
		public TableColumn<PC, Integer> pcID_col;
		public TableColumn<PC, String> pcCondition_col;
		public VBox vbPC, vbGabung;
		public Label titleUserTable, titlePCTable;
		
		// ADD TABEL JOB
		public TableView<Job> tableJob;
		public TableColumn<Job, Integer> jobID_col;
		public TableColumn<Job, Integer> userIDJob_col;
		public TableColumn<Job, Integer> pcIDJob_col;
		public TableColumn<Job, String> jobStatus_col;
		
		MenuBar menuBar;
		Menu menu;
		public MenuItem logout, home;
		public Alert addJobAlert, updateJobAlert;
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
	
	private void initializeAlert(AddJobPageVar ajv) {
		ajv.addJobAlert = new Alert(AlertType.ERROR);
		ajv.addJobAlert.setTitle("Add Job");
		
		ajv.updateJobAlert = new Alert(AlertType.ERROR);
		ajv.updateJobAlert.setTitle("Update Job");
	}
	
	public void initialize(AddJobPageVar ajv) {
		ajv.bp = new BorderPane();
		ajv.hb = new HBox();
		ajv.hbtabel = new HBox();
		ajv.vb1 = new VBox();
		ajv.vb2 = new VBox();
		ajv.vbGabung = new VBox();
		
		ajv.addJobTitle = new Label("Add Technician Job");
		ajv.userID = new Label("User ID");
		ajv.userID_spin = new Spinner<>(1, 99999999, 1);
		ajv.pcID = new Label("PC ID");
		ajv.pcID_spin = new Spinner<>(1, 99999999, 1);
		ajv.jobStatus2 = new Label("Job Status");
		ajv.jobStatus2_tf = new TextField();
		ajv.addJobBtn = new Button("ADD JOB");
		
		ajv.updateJobTitle = new Label("Update Technician Job Status");
		ajv.userIDUpdate = new Label("Choose User ID");
		ajv.userIDUpdate_spin = new Spinner<>(1, 99999999, 1);
		ajv.pcIDUpdate = new Label("Choose PC ID");
		ajv.pcIDUpdate_spin = new Spinner<>(1, 99999999, 1);
		ajv.jobID = new Label("Choose Job ID");
		ajv.jobID_spin = new Spinner<>(1, 99999999, 1);
		ajv.jobStatus = new Label("Input Job Status");
		ajv.jobStatus_tf = new TextField();
		ajv.updateJobBtn = new Button("UPDATE JOB");
		
		initializeMenu(ajv);
		ajv.vb1.getChildren().addAll(ajv.addJobTitle, ajv.userID, ajv.userID_spin, ajv.pcID, ajv.pcID_spin, ajv.jobStatus2, ajv.jobStatus2_tf, ajv.addJobBtn);
		ajv.vb2.getChildren().addAll(ajv.updateJobTitle, ajv.userIDUpdate, ajv.userIDUpdate_spin, ajv.pcIDUpdate, ajv.pcIDUpdate_spin, ajv.jobID, ajv.jobID_spin, ajv.jobStatus, ajv.jobStatus_tf, ajv.updateJobBtn);
		
		ajv.hbtabel.getChildren().addAll(ajv.vb, ajv.vbPC);
		ajv.vbGabung.getChildren().addAll(ajv.vbJob, ajv.hbtabel);
		ajv.hb.getChildren().addAll(ajv.vb1, ajv.vb2);
		ajv.hb.setPadding(new Insets(0, 0, 0, 30));
		
		ajv.vb1.setSpacing(10);
		ajv.vb2.setSpacing(10);
		ajv.hb.setSpacing(50);
		
		ajv.bp.setTop(ajv.menuBar);
		ajv.bp.setCenter(ajv.vbGabung);
		ajv.bp.setBottom(ajv.hb);
		ajv.scene = new Scene(ajv.bp, 600, 600);
	}
	
	public void handle(AddJobPageVar ajv) {
		jobController.addJobs(ajv);
		jobController.updateJob(ajv);
		
		ajv.logout.setOnAction(e->{
            try {
                new LoginPage(ajv.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		ajv.home.setOnAction(e->{
            try {
                new AdminHomePage(ajv.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
	}
	
	public AddJobPage(Stage stage) {
		AddJobPageVar ajv = new AddJobPageVar();
		jobController.viewAllJobs(ajv);
		userController.getAllTechnicians(ajv);
		pcController.handling_viewPCAddJob(ajv);
		initialize(ajv);
		initializeAlert(ajv);
		handle(ajv);
		ajv.stage = stage;
		ajv.stage.setResizable(true);
		ajv.stage.setScene(ajv.scene);
		ajv.stage.show();
	}
}
