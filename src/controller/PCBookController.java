package controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.PCBookModel;
import database.TransactionModel;
import database.UserModel;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.PCBook;
import model.TransactionDetail;
import model.TransactionHeader;
import model.User;
import operator_view.OperatorHomePage;
import operator_view.OperatorHomePage.OperatorHomePageVar;

public class PCBookController {
	PCBookModel pcBookModel = new PCBookModel();
	UserModel userModel = new UserModel();
	TransactionModel transactionModel = new TransactionModel();
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

	public void handling_finishBook(OperatorHomePageVar ov, User currentUser) {
		ov.button_finish.setOnAction(e -> {
			Integer bookID = Integer.parseInt(ov.bookID_tf.getText());
			String staffName = currentUser.getUserName();
			Integer userID = userModel.getUserID(staffName);
					
			ResultSet rs = pcBookModel.getPCBookByID(bookID);
			
			Date transactionDate = null;
			
			try {
				rs.next();
			 	transactionDate = rs.getDate("BookedDate");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (!pcBookModel.checkPCBookIDExist(bookID)) {
				ov.alert.setContentText("Booking Not Found");
				ov.alert.showAndWait();
			}
			else {
				pcBookModel.deletePCBook(bookID);
				transactionModel.addTransactionHeader(new TransactionHeader(0, userID, staffName, transactionDate));
				new OperatorHomePage(ov.stage, currentUser);				
			}
			
		});
	}
	
	public void handling_cancelBook(OperatorHomePageVar ov, User currentUser) {
		ov.button_cancel.setOnAction(e -> {
			Integer bookID = Integer.parseInt(ov.bookID_tf.getText());
			
			if (!pcBookModel.checkPCBookIDExist(bookID)) {
				ov.alert.setContentText("Booking Not Found");
				ov.alert.showAndWait();
			}
			else {
				pcBookModel.deletePCBook(bookID);
				new OperatorHomePage(ov.stage, currentUser);				
			}
			
		});
	}
}
