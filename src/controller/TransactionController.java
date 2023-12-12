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
import model.TransactionHeader;
import model.User;

public class TransactionController {
	TransactionModel transactionModel = new TransactionModel();
	PCModel pcModel = new PCModel();
	PCBookModel pcBookModel = new PCBookModel();
	UserModel userModel = new UserModel();
	ResultSet rs;

	public boolean checkIfStringContainsLetters(String str) {
		str = str.toUpperCase();
		if (str.contains("A") == true || str.contains("B") == true || str.contains("C") == true
				|| str.contains("D") == true || str.contains("E") == true || str.contains("F") == true
				|| str.contains("G") == true || str.contains("H") == true || str.contains("I") == true
				|| str.contains("J") == true || str.contains("K") == true || str.contains("L") == true
				|| str.contains("M") == true || str.contains("N") == true || str.contains("O") == true
				|| str.contains("P") == true || str.contains("Q") == true || str.contains("R") == true
				|| str.contains("S") == true || str.contains("T") == true || str.contains("U") == true
				|| str.contains("V") == true || str.contains("W") == true || str.contains("X") == true
				|| str.contains("Y") == true || str.contains("Z") == true || str.contains("`") == true
				|| str.contains("~") == true || str.contains("!") == true || str.contains("@") == true
				|| str.contains("#") == true || str.contains("$") == true || str.contains("%") == true
				|| str.contains("^") == true || str.contains("&") == true || str.contains("*") == true
				|| str.contains("(") == true || str.contains(")") == true || str.contains("-") == true
				|| str.contains("_") == true || str.contains("+") == true || str.contains("=") == true
				|| str.contains("{") == true || str.contains("[") == true || str.contains("}") == true
				|| str.contains("]") == true || str.contains("|") == true || str.contains("\\") == true
				|| str.contains(":") == true || str.contains(";") == true || str.contains("\"") == true
				|| str.contains("'") == true || str.contains("<") == true || str.contains(",") == true
				|| str.contains(">") == true || str.contains(".") == true || str.contains("?") == true
				|| str.contains("/") == true) {
			return true;
		}
		return false;
	}

	public void handling_bookPC(CustomerHomePageVar cv, User currentUser) {
		cv.button_book.setOnAction(e -> {

			String pcIDText = null;
			String customerName = null;
			Integer pcID = null;
			Date bookedTime = null;
			LocalDate today = null;
			String pcCondition = null;
			
			if (cv.pcID_tf.getText().isEmpty() || cv.bookedTime_pick.getValue() == null) {
				cv.alert.setContentText("All fields must be filled");
				cv.alert.showAndWait();
			} else {
				pcIDText = cv.pcID_tf.getText();
				pcID = Integer.parseInt(pcIDText);

				customerName = currentUser.getUserName();
				bookedTime = Date.valueOf(cv.bookedTime_pick.getValue());
				today = java.time.LocalDate.now();
				pcCondition = "";
				
				rs = pcModel.getPC(pcID);
				try {
					rs.next();
					pcCondition = rs.getString("PC_Condition");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if (checkIfStringContainsLetters(pcIDText) == true) {
				cv.alert.setContentText("PC ID must only contains numbers!");
				cv.alert.showAndWait();
			} else if (!pcModel.checkPCExist(pcID)) {
				cv.alert.setContentText("PC Not Found");
				cv.alert.showAndWait();
			} else if (pcIDText.isEmpty() || bookedTime == null) {
				cv.alert.setContentText("Please fill all the fields");
				cv.alert.showAndWait();
			} else if (bookedTime.before(Date.valueOf(today))) {
				cv.alert.setContentText("Date must be at least today");
				cv.alert.showAndWait();
			} else if (pcBookModel.checkPCBookExist(pcID, bookedTime)) {
				cv.alert.setContentText("PC is already booked for that date!");
				cv.alert.showAndWait();
			} else if (!pcCondition.equals("Usable")) {
				cv.alert.setContentText("PC is currently not usable!");
				cv.alert.showAndWait();
			} else {
				
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

	public void handling_viewTransactionDetailsAdmin(ViewAllTransactionPageVar vav) {
		ArrayList<TransactionDetail> tdList = new ArrayList<>();

		vav.vb1 = new VBox();
		vav.tdTable = new TableView<TransactionDetail>();
		vav.tdTitle = new Label("Transaction Detail");
		vav.tdPcId_col = new TableColumn<>("PC ID");
		vav.tdCustomerName_col = new TableColumn<>("Customer Name");
		vav.tdBookedTime_col = new TableColumn<>("Booked Time");
		vav.tdTable.getColumns().addAll(vav.tdPcId_col, vav.tdCustomerName_col, vav.tdBookedTime_col);

		rs = transactionModel.getAllTransactionDetail();

		try {
			while (rs.next()) {
				Integer PC_ID = rs.getInt("PC_ID");
				String CustomerName = rs.getString("CustomerName");
				Date TransactionDate = rs.getDate("BookedTime");

				tdList.add(new TransactionDetail(PC_ID, CustomerName, TransactionDate));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		for (TransactionDetail td : tdList) {
			vav.tdTable.getItems().add(td);
		}

		vav.tdPcId_col.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		vav.tdCustomerName_col.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
		vav.tdBookedTime_col.setCellValueFactory(new PropertyValueFactory<>("BookedTime"));

		vav.vb1.getChildren().addAll(vav.tdTitle, vav.tdTable);
	}
	
	public void handling_viewTransactionHeadersAdmin(ViewAllTransactionPageVar vav) {
		ArrayList<TransactionHeader> thList = new ArrayList<>();
		
		vav.vb2 = new VBox();
		vav.thTable = new TableView<TransactionHeader>();
		vav.thTitle = new Label("Transaction Header");
		vav.thId_col = new TableColumn<>("Transaction Header ID");
		vav.staffId_col = new TableColumn<>("Staff ID");
		vav.staffName_col = new TableColumn<>("Staff Name");
		vav.thDate_col = new TableColumn<>("Transaction Date");
		vav.thTable.getColumns().addAll(vav.thId_col, vav.staffId_col, vav.staffName_col, vav.thDate_col);
		
		rs = transactionModel.getAllTransactionHeader();
		
		try {
			while (rs.next()) {
				Integer th_id = rs.getInt("TransactionID");
				Integer staffId = rs.getInt("StaffID");
				String StaffName = rs.getString("StaffName");
				Date thDate = rs.getDate("TransactionDate");
				
				thList.add(new TransactionHeader(th_id, staffId, StaffName, thDate));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		for (TransactionHeader th : thList) {
			vav.thTable.getItems().add(th);
		}
		
		vav.thId_col.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));
		vav.staffId_col.setCellValueFactory(new PropertyValueFactory<>("StaffID"));
		vav.staffName_col.setCellValueFactory(new PropertyValueFactory<>("StaffName"));
		vav.thDate_col.setCellValueFactory(new PropertyValueFactory<>("TransactionDate"));
		
		vav.vb2.getChildren().addAll(vav.thTitle, vav.thTable);
	}
}
