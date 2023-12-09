package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import admin_view.AdminHomePage;
import admin_view.PCManagementPage;
import admin_view.AdminHomePage.AdminHomePageVar;
import admin_view.PCManagementPage.PCManagementPageVar;
import customer_view.CustomerHomePage.CustomerHomePageVar;
import database.PCModel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.PC;
import model.User;
import view.LoginPage;

public class PCController {
	PCModel pcModel = new PCModel();
	public void handling_addPC(PCManagementPageVar pcManagementPageVar) {
		pcManagementPageVar.button_add.setOnAction(e -> {
			String pcCondition = pcManagementPageVar.pcCondition_tf.getText();
			
			if(pcCondition.isEmpty()) {
				pcManagementPageVar.alert.setContentText("Fill in all fields!");
				pcManagementPageVar.alert.showAndWait();
			}
			
			else if(!(pcCondition.equals("Usable") || pcCondition.equals("Maintenance") || pcCondition.equals("Broken"))) {
				pcManagementPageVar.alert.setContentText("Invalid Condition!");
				pcManagementPageVar.alert.showAndWait();
			}
			
			else {
				pcModel.addPC(new PC(0, pcCondition));
				new PCManagementPage(pcManagementPageVar.stage);
			}
	});
	}
	
	public void handling_viewAllPCManagement(PCManagementPageVar pcManagementPageVar) {
		ArrayList<PC> pcList = new ArrayList<>();
		ResultSet rs = pcModel.getAllPC();

		try {
			while (rs.next()) {
				Integer i = rs.getInt("PC_ID");
				String c = rs.getString("PC_Condition");

				pcList.add(new PC(i, c));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		for (PC pc : pcList) {
			pcManagementPageVar.pcTable.getItems().add(pc);
		}
	}

	public void handling_viewPC(CustomerHomePageVar cv) {
		ArrayList<PC> pcList = new ArrayList<>();

		cv.vb1 = new VBox();
		cv.pcTable = new TableView<PC>();
		cv.title2 = new Label("PC Table");
		cv.pcID_col = new TableColumn<>("PC ID");
		cv.pcCondition_col = new TableColumn<>("Status");
		cv.pcTable.getColumns().addAll(cv.pcID_col, cv.pcCondition_col);

		ResultSet rs = pcModel.getAllPC();

		try {
			while (rs.next()) {
				Integer i = rs.getInt("PC_ID");
				String c = rs.getString("PC_Condition");

				pcList.add(new PC(i, c));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		for (PC pc : pcList) {
			cv.pcTable.getItems().add(pc);
		}

		cv.pcID_col.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		cv.pcCondition_col.setCellValueFactory(new PropertyValueFactory<>("PC_Condition"));

		cv.vb1.getChildren().addAll(cv.title2, cv.pcTable);
	}
	
	public void handlingUpdatePC(PCManagementPageVar pcManagementPageVar) {
		pcManagementPageVar.button_update.setOnAction(e->{
			Integer id = Integer.parseInt(pcManagementPageVar.pcID_tf.getText()) ;
			String newCondition = pcManagementPageVar.newPCCondition_tf.getText();
			
			if(id == null|| newCondition.isEmpty()) {
				pcManagementPageVar.alert.setContentText("Fill in all fields!");
				pcManagementPageVar.alert.showAndWait();
			}
			else if(!(newCondition.equals("Usable") || newCondition.equals("Maintenance") || newCondition.equals("Broken"))) {
				pcManagementPageVar.alert.setContentText("Invalid Condition!");
				pcManagementPageVar.alert.showAndWait();
			}
			
			else if(pcModel.checkPCExist(id)) {
				pcModel.updatePC(id, newCondition);
				new PCManagementPage(pcManagementPageVar.stage);
			}
			
		});
	}

}
