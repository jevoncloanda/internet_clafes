package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class HomePage {
	Stage stage;
	Scene scene;
	BorderPane bp;
	HBox hb;
	VBox vb1, vb2;
	VBox vb;
	TableView<User> table;
	TableColumn<User, String> username_col;
	TableColumn<User, Integer> age_col;
	Label title1, title2, username, password, username_del;
	TextField username_tf, username_tf2;
	PasswordField pass_pf;
	Button btnUpdate, btnDelete;
	
	private void getData() {
		ArrayList<User> userList = new ArrayList<>();
		Connect con = Connect.getInstance();
		
		vb = new VBox();
		table = new TableView<User>();
		username_col = new TableColumn<>("username");
		age_col = new TableColumn<>("age");
        table.getColumns().addAll(username_col, age_col);
        
        ResultSet rs = con.selectData("SELECT * FROM USER");
        
        try {
			while(rs.next()) {
				String u = rs.getString("UserName");
				String p = rs.getString("UserPassword");
				Integer a = rs.getInt("UserAge");
				String r = rs.getString("UserRole");
				
				userList.add(new User(u, p, a, r));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        for (User user : userList) {
        	table.getItems().add(user);
		}
		
        username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
        age_col.setCellValueFactory(new PropertyValueFactory<>("age"));
        
        table.setMaxHeight(150);
        username_col.setMinWidth(200);
        age_col.setPrefWidth(200);
        
        
        vb.getChildren().add(table);
        // atas kanan bawah kiri
        vb.setPadding(new Insets(20, 30, 30, 30));
	}
	
	private void initialize() {
		getData();
		
		bp = new BorderPane();
		hb = new HBox();
		vb1 = new VBox();
		vb2 = new VBox();
		
		title1 = new Label("Update User");
		username = new Label("Username");
		username_tf = new TextField();
		password = new Label("Password");
		pass_pf = new PasswordField();
		btnUpdate = new Button("UPDATE");
		
		title2 = new Label("Delete User");
		username_del = new Label("Username");
		username_tf2 = new TextField();
		btnDelete = new Button("DELETE");
		
		vb1.getChildren().addAll(title1, username, username_tf, password, pass_pf, btnUpdate);
		vb2.getChildren().addAll(title2, username_del, username_tf2, btnDelete);
		
		hb.getChildren().addAll(vb1, vb2);
		hb.setPadding(new Insets(0, 0, 0, 30));
		
		vb1.setSpacing(10);
		vb2.setSpacing(10);
		hb.setSpacing(50);
		
		bp.setTop(vb);
		bp.setCenter(hb);
		scene = new Scene(bp, 650, 650);
	}
	
	private void setStyle() {
		title1.setStyle("-fx-font-weight: bold;" + "-fx-font-family: Serif;" + "-fx-font-size: 20px;");
		title2.setStyle("-fx-font-weight: bold;" + "-fx-font-family: Serif;" + "-fx-font-size: 20px;");
	}
	
	public HomePage(Stage stage) {
		initialize();
		setStyle();
		this.stage = stage;
		this.stage.setResizable(false);
		this.stage.setScene(scene);
		this.stage.show();
	}
}
