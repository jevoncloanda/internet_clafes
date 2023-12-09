package controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

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
import model.TransactionDetail;
import model.User;

public class TransactionController {
	TransactionModel transactionModel = new TransactionModel();
	PCModel pcModel = new PCModel();

	public void handling_bookPC(CustomerHomePageVar cv, User currentUser) {
		cv.button_book.setOnAction(e -> {
			Integer pcID = Integer.parseInt(cv.pcID_tf.getText());
			String customerName = currentUser.getUserName();
			Date bookedTime = Date.valueOf(cv.bookedTime_pick.getValue());
			
			LocalDate today = java.time.LocalDate.now();
			
			PCBookModel pcBookModel = new PCBookModel();
			UserModel userModel = new UserModel();
			ResultSet rs = userModel.getUser(currentUser.getUserName(), currentUser.getUserPassword());
			Integer userID = null;
			try {
				rs.next();
				userID = rs.getInt("UserID");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			PCBook pcBook;
			
			if (!pcModel.checkPCExist(pcID)) {
				cv.alert.setContentText("PC Not Found");
				cv.alert.showAndWait();
			} else if (bookedTime.before(Date.valueOf(today))) {
				cv.alert.setContentText("Date must be at least today");
				cv.alert.showAndWait();
			} 
			else if (pcBookModel.checkPCBookExist(pcID, bookedTime) == true) {
				cv.alert.setContentText("PC is already booked for that date!");
				cv.alert.showAndWait();
			}
			else {
				transactionModel.addTransactionDetail(new TransactionDetail(pcID, customerName, bookedTime));
				pcBook = new PCBook(pcID, userID, bookedTime);
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
}
