package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import admin_view.AdminHomePage;
import admin_view.PCManagementPage;
import admin_view.AdminHomePage.AdminHomePageVar;
import admin_view.PCManagementPage.PCManagementPageVar;
import admin_view.AddJobPage.AddJobPageVar;
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
import view.MakeReportPage.MakeReportPageVar;
import operator_view.OperatorHomePage.OperatorHomePageVar;

public class PCController {
	PCModel pcModel = new PCModel();
	public boolean checkIfStringContainsLetters(String str) {
		str = str.toUpperCase();
		if(str.contains("A")==true
				|| str.contains("B")==true
				|| str.contains("C")==true
				|| str.contains("D")==true
				|| str.contains("E")==true
				|| str.contains("F")==true
				|| str.contains("G")==true
				|| str.contains("H")==true
				|| str.contains("I")==true
				|| str.contains("J")==true
				|| str.contains("K")==true
				|| str.contains("L")==true
				|| str.contains("M")==true
				|| str.contains("N")==true
				|| str.contains("O")==true
				|| str.contains("P")==true
				|| str.contains("Q")==true
				|| str.contains("R")==true
				|| str.contains("S")==true
				|| str.contains("T")==true
				|| str.contains("U")==true
				|| str.contains("V")==true
				|| str.contains("W")==true
				|| str.contains("X")==true
				|| str.contains("Y")==true
				|| str.contains("Z")==true
				|| str.contains("`")==true
				|| str.contains("~")==true
				|| str.contains("!")==true
				|| str.contains("@")==true
				|| str.contains("#")==true
				|| str.contains("$")==true
				|| str.contains("%")==true
				|| str.contains("^")==true
				|| str.contains("&")==true
				|| str.contains("*")==true
				|| str.contains("(")==true
				|| str.contains(")")==true
				|| str.contains("-")==true
				|| str.contains("_")==true
				|| str.contains("+")==true
				|| str.contains("=")==true
				|| str.contains("{")==true
				|| str.contains("[")==true
				|| str.contains("}")==true
				|| str.contains("]")==true
				|| str.contains("|")==true
				|| str.contains("\\")==true
				|| str.contains(":")==true
				|| str.contains(";")==true
				|| str.contains("\"")==true
				|| str.contains("'")==true
				|| str.contains("<")==true
				|| str.contains(",")==true
				|| str.contains(">")==true
				|| str.contains(".")==true
				|| str.contains("?")==true
				|| str.contains("/")==true) {
			return true;
		}
		return false;
	}
	public void handling_addPC(PCManagementPageVar pcManagementPageVar) {
		pcManagementPageVar.button_add.setOnAction(e -> {
			String pcIDText = pcManagementPageVar.pcIDAdd_tf.getText().toUpperCase();
			Integer pcID = 0;
			if(checkIfStringContainsLetters(pcIDText)==false) {
				pcID = Integer.parseInt(pcIDText);
			}
			
			if(checkIfStringContainsLetters(pcIDText)==true) {
				pcManagementPageVar.alert.setContentText("Input must only contain numbers!");
				pcManagementPageVar.alert.showAndWait();
			}
			else if(pcIDText == null) {
				pcManagementPageVar.alert.setContentText("Please fill in all fields!");
				pcManagementPageVar.alert.showAndWait();
			}
			else if(pcModel.checkPCExist(pcID)==true) {
				pcManagementPageVar.alert.setContentText("PC ID already exists!");
				pcManagementPageVar.alert.showAndWait();
			}
			else {
				pcModel.addPC(pcID);
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
			String pcIDText = pcManagementPageVar.pcID_tf.getText();
			Integer id=0;
			String newCondition = pcManagementPageVar.newPCCondition_tf.getText();
			if(checkIfStringContainsLetters(pcIDText)==false) {
				id = Integer.parseInt(pcIDText);
			}
			
			if(checkIfStringContainsLetters(pcIDText)==true) {
				pcManagementPageVar.alert.setContentText("Input must only contain numbers!");
				pcManagementPageVar.alert.showAndWait();
			}
			else if(pcIDText.isEmpty() || newCondition.isEmpty()) {
				pcManagementPageVar.alert.setContentText("Please fill all the fields!");
				pcManagementPageVar.alert.showAndWait();
			}
			else if(!newCondition.equals("Usable") && !newCondition.equals("Maintenance") && !newCondition.equals("Broken")) {
				pcManagementPageVar.alert.setContentText("Condition must either be Usable / Maintenance / Broken !");
				pcManagementPageVar.alert.showAndWait();
			}
			else if(pcModel.checkPCExist(id)==false) {
				pcManagementPageVar.alert.setContentText("Invalid PC ID!");
				pcManagementPageVar.alert.showAndWait();
			}
			else if(pcModel.checkPCExist(id)==true) {
				pcModel.updatePC(id, newCondition);
				new PCManagementPage(pcManagementPageVar.stage);
			}
		});
	}
	
	public void handling_viewPC(MakeReportPageVar mrp) {
		ArrayList<PC> pcList = new ArrayList<>();

		mrp.vb1 = new VBox();
		mrp.pcTable = new TableView<PC>();
		mrp.pcID_col = new TableColumn<>("PC ID");
		mrp.pcCondition_col = new TableColumn<>("Status");
		mrp.pcTable.getColumns().addAll(mrp.pcID_col, mrp.pcCondition_col);

		ResultSet rs = pcModel.getAllPC();

		try {
			while (rs.next()) {
				Integer i = rs.getInt("PC_ID");
				String c = rs.getString("PC_Condition");

				pcList.add(new PC(i, c));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (PC pc : pcList) {
			mrp.pcTable.getItems().add(pc);
		}

		mrp.pcID_col.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		mrp.pcCondition_col.setCellValueFactory(new PropertyValueFactory<>("PC_Condition"));

		mrp.vb1.getChildren().add(mrp.pcTable);
	}
	
	public void handling_viewPCAddJob(AddJobPageVar ajv) {
		ArrayList<PC> pcList = new ArrayList<>();
		
		ajv.vbPC = new VBox();
		ajv.pcTable = new TableView<PC>();
		ajv.titlePCTable = new Label("PC Table");
		ajv.pcID_col = new TableColumn<>("PC ID");
		ajv.pcCondition_col = new TableColumn<>("Status");
		ajv.pcTable.getColumns().addAll(ajv.pcID_col, ajv.pcCondition_col);
		
		ResultSet rs = pcModel.getAllPC();
		
		try {
			while (rs.next()) {
				Integer i = rs.getInt("PC_ID");
				String c = rs.getString("PC_Condition");
				
				pcList.add(new PC(i, c));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (PC pc : pcList) {
			ajv.pcTable.getItems().add(pc);
		}
		
		ajv.pcID_col.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		ajv.pcCondition_col.setCellValueFactory(new PropertyValueFactory<>("PC_Condition"));
		
		ajv.vbPC.getChildren().addAll(ajv.titlePCTable, ajv.pcTable);
	}
	
	public void handling_viewPCOperator(OperatorHomePageVar ov) {
		ArrayList<PC> pcList = new ArrayList<>();
		
		ov.vb4 = new VBox();
		ov.pcTable = new TableView<PC>();
		ov.titlePCTable = new Label("PC Table");
		ov.pcID_col = new TableColumn<>("PC ID");
		ov.pcCondition_col = new TableColumn<>("Status");
		ov.pcTable.getColumns().addAll(ov.pcID_col, ov.pcCondition_col);
		
		ResultSet rs = pcModel.getAllPC();
		
		try {
			while (rs.next()) {
				Integer i = rs.getInt("PC_ID");
				String c = rs.getString("PC_Condition");
				
				pcList.add(new PC(i, c));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (PC pc : pcList) {
			ov.pcTable.getItems().add(pc);
		}
		
		ov.pcID_col.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		ov.pcCondition_col.setCellValueFactory(new PropertyValueFactory<>("PC_Condition"));
		
		ov.vb4.getChildren().addAll(ov.titlePCTable, ov.pcTable);
	}
	
	public void updatePCCondition(AddJobPageVar ajv, String Condition) {
		Integer pcID = ajv.pcIDUpdate_spin.getValue();
		if(pcModel.checkPCExist(pcID)==false) {
			ajv.updateJobAlert.setContentText("Invalid PC ID!");
			ajv.updateJobAlert.showAndWait();
		}
		else {
			pcModel.updatePCCondition(pcID, Condition);
		}
	}

}
