//package controller;
//
//import java.util.ArrayList;
//
//import customer_view.CustomerHomePage.CustomerHomePageVar;
//import database.Connect;
//import javafx.scene.control.TableView;
//import javafx.scene.layout.VBox;
//import model.PC;
//
//public class PCController {
//	public void handling_addPC() {
//
//	}
//
//	public void handling_viewPC(CustomerHomePageVar cv) {
//		ArrayList<PC> pcList = new ArrayList<>();
//		Connect con = Connect.getInstance();
//
//		cv.vb1 = new VBox();
//		cv.table = new TableView<PC>();
//		username_col = new TableColumn<>("username");
//		age_col = new TableColumn<>("age");
//		table.getColumns().addAll(username_col, age_col);
//
//		ResultSet rs = con.selectData("SELECT * FROM USER");
//
//		try {
//			while (rs.next()) {
//				String u = rs.getString("Username");
//				String p = rs.getString("Password");
//				Integer a = rs.getInt("Age");
//
//				userList.add(new User(u, p, a));
//			}
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		for (User user : userList) {
//			table.getItems().add(user);
//		}
//
//		username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
//		age_col.setCellValueFactory(new PropertyValueFactory<>("age"));
//
//		table.setMaxHeight(150);
//		username_col.setMinWidth(200);
//		age_col.setPrefWidth(200);
//
//		vb.getChildren().add(table);
//		// atas kanan bawah kiri
//		vb.setPadding(new Insets(20, 30, 30, 30));
//	}
//
//}
