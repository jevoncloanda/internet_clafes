package controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import admin_view.ViewAllTransactionsPage.ViewAllTransactionPageVar;
import customer_view.CustomerHomePage;
import customer_view.CustomerHomePage.CustomerHomePageVar;
import database.PCBookModel;
import database.PCModel;
import database.TransactionModel;
import database.UserModel;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.PC;
import model.PCBook;
import model.Transaction;
import model.TransactionDetail;
import model.User;

public class TransactionController {
	TransactionModel transactionModel = new TransactionModel();
	PCModel pcModel = new PCModel();
	ResultSet rs;

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
	
	public void handling_bookPC(CustomerHomePageVar cv, User currentUser) {
		cv.button_book.setOnAction(e -> {
			String pcIDText = cv.pcID_tf.getText();
			Integer pcID=0;
			if(checkIfStringContainsLetters(pcIDText)==false) {
				pcID = Integer.parseInt(pcIDText);
			}
			String customerName = currentUser.getUserName();
			Date bookedTime = Date.valueOf(cv.bookedTime_pick.getValue());
			String pcCondition = "";
			rs = pcModel.getPC(pcID);
			try {
				rs.next();
				pcCondition = rs.getString("PC_Condition");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			LocalDate today = java.time.LocalDate.now();
			
			PCBookModel pcBookModel = new PCBookModel();
			UserModel userModel = new UserModel();
			rs = userModel.getUser(currentUser.getUserName(), currentUser.getUserPassword());
			Integer userID = null;
			try {
				rs.next();
				userID = rs.getInt("UserID");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			PCBook pcBook;
			
			if (checkIfStringContainsLetters(pcIDText)==true) {
				cv.alert.setContentText("PC ID must only contains numbers!");
				cv.alert.showAndWait();
			}
			else if (!pcModel.checkPCExist(pcID)) {
				cv.alert.setContentText("PC Not Found");
				cv.alert.showAndWait();
			} 
			else if(pcIDText.isEmpty() || bookedTime == null) {
				cv.alert.setContentText("Please fill all the fields");
				cv.alert.showAndWait();
			}
			else if (bookedTime.before(Date.valueOf(today))) {
				cv.alert.setContentText("Date must be at least today");
				cv.alert.showAndWait();
			} 
			else if (pcBookModel.checkPCBookExist(pcID, bookedTime)) {
				cv.alert.setContentText("PC is already booked for that date!");
				cv.alert.showAndWait();
			}
			else if(!pcCondition.equals("Usable")) {
				cv.alert.setContentText("PC is currently not usable!");
				cv.alert.showAndWait();
			}
			else {
				transactionModel.addTransactionDetail(new TransactionDetail(pcID, customerName, bookedTime));
				pcBook = new PCBook(0, pcID, userID, bookedTime);
				pcBookModel.addPCBook(pcBook);
				new CustomerHomePage(cv.stage, currentUser);
			}
		});
	}
	
	public void handling_viewTransactionDetailByCustomer(CustomerHomePageVar cv, User currentUser) {
		ArrayList<TransactionDetail> tdList = new ArrayList<>();

		cv.vb = new VBox();
		cv.tdTable = new TableView<TransactionDetail>();
		cv.title1 = new Label("Transaction Table");
		cv.tdPcID_col = new TableColumn<>("PC ID");
		cv.tdCustomerName_col = new TableColumn<>("Customer Name");
		cv.tdBookedTime_col = new TableColumn<>("Booked Time");
		cv.tdTable.getColumns().addAll(cv.tdPcID_col, cv.tdCustomerName_col, cv.tdBookedTime_col);

		ResultSet rs = transactionModel.getTransactionDetailByCustomer(currentUser);

		try {
			while (rs.next()) {
				Integer tdPcID = rs.getInt("PC_ID");
				String tdCustomerName = rs.getString("CustomerName");
				Date tdBookedTime = rs.getDate("BookedTime");

				tdList.add(new TransactionDetail(tdPcID, tdCustomerName, tdBookedTime));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (TransactionDetail td : tdList) {
			cv.tdTable.getItems().add(td);
		}

		cv.tdPcID_col.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		cv.tdCustomerName_col.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
		cv.tdBookedTime_col.setCellValueFactory(new PropertyValueFactory<>("BookedTime"));

		cv.vb.getChildren().addAll(cv.title1, cv.tdTable);
	}

	public void handling_viewTransactionsAdmin(ViewAllTransactionPageVar vav) {
		ArrayList<Transaction> tdList = new ArrayList<>();

		vav.vb1 = new VBox();
		vav.tdTable = new TableView<Transaction>();
		vav.tableTitle = new Label("All Customer Transactions");
		vav.pcID_col = new TableColumn<>("PC ID");
		vav.customerName_col = new TableColumn<>("Customer Name");
		vav.tdDate_col = new TableColumn<>("Transaction Date");
		vav.staffID_col = new TableColumn<>("Staff ID");
		vav.staffName_col = new TableColumn<>("Staff Name");
		vav.tdTable.getColumns().addAll(vav.pcID_col, vav.customerName_col, vav.tdDate_col, vav.staffID_col, vav.staffName_col);

		rs = transactionModel.getAllTransactionDetail();
		ResultSet rs2 = transactionModel.getAllTransactionHeader();

		try {
			while (rs.next() && rs2.next()) {
				Integer PC_ID = rs.getInt("PC_ID");
				String CustomerName = rs.getString("CustomerName");
				Date TransactionDate = rs.getDate("BookedTime");
				Integer StaffID = rs2.getInt("StaffID");
				String StaffName = rs2.getString("StaffName");

				tdList.add(new Transaction(PC_ID, CustomerName, TransactionDate, StaffID, StaffName));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		for (Transaction td : tdList) {
			vav.tdTable.getItems().add(td);
		}

		vav.pcID_col.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		vav.customerName_col.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
		vav.tdDate_col.setCellValueFactory(new PropertyValueFactory<>("TransactionDate"));
		vav.staffID_col.setCellValueFactory(new PropertyValueFactory<>("StaffID"));
		vav.staffName_col.setCellValueFactory(new PropertyValueFactory<>("StaffName"));

		vav.vb1.getChildren().addAll(vav.tableTitle, vav.tdTable);
	}
}
