package admin_view;

import java.util.ArrayList;

import admin_view.AdminHomePage.AdminHomePageVar;
import controller.PCController;
import controller.UserController;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PC;
import model.TransactionDetail;
import model.User;
import view.LoginPage;

public class PCManagementPage {

	PCController pcController = new PCController();
	public class PCManagementPageVar{
		public Stage stage;
		public Scene scene;
		public BorderPane bp;
		public GridPane gp;
		public VBox vb1, vb2, vb3, vb4;
		public VBox vb;
		public Label title, title2, title3, pcID_lbl, newPCCondition_lbl;
		public TableView<PC> pcTable;
		public TableColumn<PC, Integer> pcID_col;
		public TableColumn<PC, String> pcCondition_col;
		public TextField pcIDAdd_tf, newPCCondition_tf, pcID_tf;
		public PasswordField pass_pf;
		public Button button_add, button_update;
		public MenuBar menuBar;
		public Menu menu;
		public MenuItem logout, home, addJob, viewTransaction, report;
		public Alert alert;
	}
	
	public void initializeMenu(PCManagementPageVar pcManagementPageVar) {
		pcManagementPageVar.menuBar = new MenuBar();
		pcManagementPageVar.menu = new Menu("Menu");
		pcManagementPageVar.logout = new MenuItem("Logout");
		pcManagementPageVar.home = new MenuItem("Home");
		pcManagementPageVar.addJob = new MenuItem("Add Technician Job");
		pcManagementPageVar.viewTransaction = new MenuItem("View Customer Transaction");
		pcManagementPageVar.report = new MenuItem("View Report");
		pcManagementPageVar.menuBar.getMenus().add(pcManagementPageVar.menu);
		pcManagementPageVar.menu.getItems().addAll(pcManagementPageVar.logout,
				pcManagementPageVar.home,
				pcManagementPageVar.addJob,
				pcManagementPageVar.viewTransaction,
				pcManagementPageVar.report);
	}
	
	private void initializeAlert(PCManagementPageVar pcManagementPageVar) {
		pcManagementPageVar.alert = new Alert(AlertType.ERROR);
		pcManagementPageVar.alert.setTitle("PCManagementPage");
	}
	
	private void initialize(PCManagementPageVar pcManagementPageVar) {
		
		// Initialize Add PC
		pcManagementPageVar.bp = new BorderPane();
		pcManagementPageVar.gp = new GridPane();
		pcManagementPageVar.vb1 = new VBox();
		pcManagementPageVar.vb2 = new VBox();
		

		pcManagementPageVar.title = new Label("Add new PC");
		pcManagementPageVar.pcIDAdd_tf = new TextField();
		pcManagementPageVar.button_add = new Button("Add PC");
		
		pcManagementPageVar.vb1.getChildren().add(pcManagementPageVar.title);
		pcManagementPageVar.vb2.getChildren().addAll(pcManagementPageVar.pcIDAdd_tf, pcManagementPageVar.button_add);

		pcManagementPageVar.gp.add(pcManagementPageVar.vb1, 0, 0);
		pcManagementPageVar.gp.add(pcManagementPageVar.vb2, 0, 1);
		
		initializeMenu(pcManagementPageVar);
		pcManagementPageVar.bp.setTop(pcManagementPageVar.menuBar);
		pcManagementPageVar.bp.setCenter(pcManagementPageVar.gp);

		pcManagementPageVar.scene = new Scene(pcManagementPageVar.bp, 600, 600);
		

		// Initialize View All PC
		pcManagementPageVar.vb3 = new VBox();
		pcManagementPageVar.pcTable = new TableView<PC>();
		pcManagementPageVar.title2 = new Label("PC Table");
		pcManagementPageVar.pcID_col = new TableColumn<>("PC ID");
		pcManagementPageVar.pcCondition_col = new TableColumn<>("Status");
		pcManagementPageVar.pcTable.getColumns().addAll(pcManagementPageVar.pcID_col, pcManagementPageVar.pcCondition_col);
		
		pcManagementPageVar.pcID_col.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		pcManagementPageVar.pcCondition_col.setCellValueFactory(new PropertyValueFactory<>("PC_Condition"));
		pcManagementPageVar.vb3.getChildren().addAll(pcManagementPageVar.title2, pcManagementPageVar.pcTable);
		pcManagementPageVar.gp.add(pcManagementPageVar.vb3, 0, 2);
		
		
		// Initialize Update PC Condition
		pcManagementPageVar.vb4 = new VBox();	
		pcManagementPageVar.title3 = new Label("Update PC Condition");
		pcManagementPageVar.pcID_lbl = new Label("PC ID:");
		pcManagementPageVar.pcID_tf = new TextField();
		pcManagementPageVar.newPCCondition_lbl = new Label("New Condition: ");
		pcManagementPageVar.newPCCondition_tf = new TextField();
		pcManagementPageVar.button_update = new Button("UPDATE");
		
		pcManagementPageVar.vb4.getChildren().addAll(pcManagementPageVar.title3, pcManagementPageVar.pcID_lbl, pcManagementPageVar.pcID_tf, pcManagementPageVar.newPCCondition_lbl, pcManagementPageVar.newPCCondition_tf, pcManagementPageVar.button_update);
		pcManagementPageVar.gp.add(pcManagementPageVar.vb4, 0, 3);
	}
	
	private void handle(PCManagementPageVar pcManagementPageVar) {
		// Koneksi ke pc controller
		pcController.handling_addPC(pcManagementPageVar);
		pcController.handling_viewAllPCManagement(pcManagementPageVar);
		pcController.handlingUpdatePC(pcManagementPageVar);
		
		// Untuk logout
		pcManagementPageVar.logout.setOnAction(e->{
            try {
                new LoginPage(pcManagementPageVar.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		// Untuk ke home page
		pcManagementPageVar.home.setOnAction(e->{
            try {
                new AdminHomePage(pcManagementPageVar.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		// Untuk ke page view transaction
		pcManagementPageVar.viewTransaction.setOnAction(e->{
            try {
                new ViewAllTransactionsPage(pcManagementPageVar.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		// Untuk ke page add job
		pcManagementPageVar.addJob.setOnAction(e->{
            try {
                new AddJobPage(pcManagementPageVar.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
		
		// Untuk ke page view report
		pcManagementPageVar.report.setOnAction(e->{
            try {
                new ViewReportsPage(pcManagementPageVar.stage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
	}
	
	public PCManagementPage(Stage stage) {
		PCManagementPageVar pcManagementPageVar = new PCManagementPageVar();
		initialize(pcManagementPageVar);
		initializeAlert(pcManagementPageVar);
		handle(pcManagementPageVar);
		pcManagementPageVar.stage = stage;
		pcManagementPageVar.stage.setResizable(false);
		pcManagementPageVar.stage.setScene(pcManagementPageVar.scene);
		pcManagementPageVar.stage.show();
	}
}
