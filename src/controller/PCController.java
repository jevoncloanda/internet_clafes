package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import admin_view.AddJobPage.AddJobPageVar;
import customer_view.CustomerHomePage.CustomerHomePageVar;
import database.PCModel;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.PC;
import view.MakeReportPage.MakeReportPageVar;
import operator_view.OperatorHomePage.OperatorHomePageVar;

public class PCController {
	PCModel pcModel = new PCModel();
	public void handling_addPC() {

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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (PC pc : pcList) {
			cv.pcTable.getItems().add(pc);
		}

		cv.pcID_col.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		cv.pcCondition_col.setCellValueFactory(new PropertyValueFactory<>("PC_Condition"));

		cv.vb1.getChildren().addAll(cv.title2, cv.pcTable);
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
