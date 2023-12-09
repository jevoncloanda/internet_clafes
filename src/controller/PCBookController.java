package controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.PCBookModel;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.PCBook;
import model.TransactionDetail;
import model.User;
import operator_view.OperatorHomePage.OperatorHomePageVar;

public class PCBookController {
	PCBookModel pcBookModel = new PCBookModel();
	
	public void handling_viewAllPCBooks(OperatorHomePageVar ov) {
		ArrayList<PCBook> pcBookList = new ArrayList<>();

		ov.vb1 = new VBox();
		ov.pcBookTable = new TableView<PCBook>();
		ov.title1 = new Label("Booked PC Table");
		ov.pbBookID_col = new TableColumn<>("Book ID");
		ov.pbPcID_col = new TableColumn<>("PC ID");
		ov.pbUserID_col = new TableColumn<>("Customer ID");
		ov.pbBookedDate_col = new TableColumn<>("Booked Date");
		ov.pcBookTable.getColumns().addAll(ov.pbBookID_col, ov.pbPcID_col, ov.pbUserID_col, ov.pbBookedDate_col);

		ResultSet rs = pcBookModel.getAllPCBooks();

		try {
			while (rs.next()) {
				Integer pbBookID = rs.getInt("BookID");
				Integer pbPcID = rs.getInt("PC_ID");
				Integer pbUserID = rs.getInt("UserID");
				Date pbBookedDate = rs.getDate("BookedDate");

				pcBookList.add(new PCBook(pbBookID, pbPcID, pbUserID, pbBookedDate));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (PCBook pb : pcBookList) {
			ov.pcBookTable.getItems().add(pb);
		}

		ov.pbBookID_col.setCellValueFactory(new PropertyValueFactory<>("BookID"));
		ov.pbPcID_col.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		ov.pbUserID_col.setCellValueFactory(new PropertyValueFactory<>("UserID"));
		ov.pbBookedDate_col.setCellValueFactory(new PropertyValueFactory<>("BookedDate"));

		ov.vb1.getChildren().addAll(ov.title1, ov.pcBookTable);
	}
}
