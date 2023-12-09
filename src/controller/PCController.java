package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import customer_view.CustomerHomePage.CustomerHomePageVar;
import database.PCModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.PC;

public class PCController {
	PCModel pcModel = new PCModel();
	public void handling_addPC() {

	}

	public void handling_viewPC(CustomerHomePageVar cv) {
		ArrayList<PC> pcList = new ArrayList<>();

		cv.vb1 = new VBox();
		cv.table = new TableView<PC>();
		cv.pcID_col = new TableColumn<>("PC ID");
		cv.pcCondition_col = new TableColumn<>("Status");
		cv.table.getColumns().addAll(cv.pcID_col, cv.pcCondition_col);

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
			cv.table.getItems().add(pc);
		}

		cv.pcID_col.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		cv.pcCondition_col.setCellValueFactory(new PropertyValueFactory<>("PC_Condition"));

		cv.vb1.getChildren().add(cv.table);
	}

}
