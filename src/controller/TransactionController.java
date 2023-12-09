package controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import customer_view.CustomerHomePage.CustomerHomePageVar;
import database.PCModel;
import database.TransactionModel;
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

			Date today = new Date(System.currentTimeMillis());

			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
			String strBookedTime = dateFormat.format(bookedTime);
			String strToday = dateFormat.format(today);

			System.out.println(strBookedTime + " " + strToday + " " + bookedTime.compareTo(today));
			System.out.println(!strBookedTime.equals(strToday));

			if (!pcModel.checkPCExist(pcID)) {
				cv.alert.setContentText("PC Not Found");
				cv.alert.showAndWait();
			} else if (bookedTime.before(today)) {
				cv.alert.setContentText("Date must be at least today");
				cv.alert.showAndWait();
			} else {
				transactionModel.addTransactionDetail(new TransactionDetail(pcID, customerName, bookedTime));
			}
		});
	}
}
