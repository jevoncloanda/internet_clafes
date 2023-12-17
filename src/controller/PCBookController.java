package controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import database.PCBookModel;
import database.PCModel;
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
	PCModel pcModel = new PCModel();

	// Function untuk melihatkan tabel pc book di Operator Home Page
	public void handling_viewAllPCBooks(OperatorHomePageVar ov) {
		ArrayList<PCBook> pcBookList = new ArrayList<>();
		// Inisialisasi semua variable tabel
		ov.vb1 = new VBox();
		ov.pcBookTable = new TableView<PCBook>();
		ov.title1 = new Label("Booked PC Table");
		ov.pbBookID_col = new TableColumn<>("Book ID");
		ov.pbPcID_col = new TableColumn<>("PC ID");
		ov.pbUserID_col = new TableColumn<>("Customer ID");
		ov.pbBookedDate_col = new TableColumn<>("Booked Date");
		ov.pcBookTable.getColumns().addAll(ov.pbBookID_col, ov.pbPcID_col, ov.pbUserID_col, ov.pbBookedDate_col);

		// Mengambil semua data pc book dari database menggunakan pc book model
		ResultSet rs = pcBookModel.getAllPCBooks();

		// Memasukkan tiap row data pc book ke tabel
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

	// Function untuk mengurus form finish book pada operator home page
	public void handling_finishBook(OperatorHomePageVar ov, User currentUser) {
		ov.button_finish.setOnAction(e -> {
			// Mengambil input value dari form finish book
			Integer bookID = Integer.parseInt(ov.bookID_tf.getText());
			String staffName = currentUser.getUserName();
			Integer userID = userModel.getUserID(staffName);
			
			// Mengambil pc book id dari database
			ResultSet rs = pcBookModel.getPCBookByID(bookID);

			Date transactionDate = null;
			
			// Mengambil transaction date dari data pc book
			try {
				rs.next();
				transactionDate = rs.getDate("BookedDate");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Validasi
			if (!pcBookModel.checkPCBookIDExist(bookID)) {
				// Kalau pc book tidak ada
				ov.alert.setContentText("Booking Not Found");
				ov.alert.showAndWait();
			} else {
				// Menghapus data pc book melalui pc book model
				pcBookModel.deletePCBook(bookID);
				
				//Membuat transction header baru melalui transaction model
				transactionModel.addTransactionHeader(new TransactionHeader(0, userID, staffName, transactionDate));
				
				// Reload page
				new OperatorHomePage(ov.stage, currentUser);
			}

		});
	}

	// Function untuk mengurus cancel book pada Operator Home Page
	public void handling_cancelBook(OperatorHomePageVar ov, User currentUser) {
		ov.button_cancel.setOnAction(e -> {
			// Mengambil input value dari form cancel book
			Integer bookID = Integer.parseInt(ov.bookID_tf.getText());

			// Validasi
			if (!pcBookModel.checkPCBookIDExist(bookID)) {
				// Kalau pc book tidak ada
				ov.alert.setContentText("Booking Not Found");
				ov.alert.showAndWait();
			} else {
				// Menghapus data pc book melalui pc book model
				pcBookModel.deletePCBook(bookID);
				
				// Reload page
				new OperatorHomePage(ov.stage, currentUser);
			}

		});
	}

	// Function untuk mengurus assign pc to another user pada operator home page
	public void handling_assignPC(OperatorHomePageVar ov, User currentUser) {
		ov.button_assign.setOnAction(e -> {
			// Mengambil input value dari form assign pc
			Integer bookID = Integer.parseInt(ov.bookID2_tf.getText());
			Integer targetPcID = Integer.parseInt(ov.targetPC_tf.getText());

			ResultSet rs = pcBookModel.getPCBookByID(bookID);
			Date bookedTime = null;
			LocalDate today = java.time.LocalDate.now();
			
			// Mengambil booked time dari data pc book
			try {
				rs.next();
				bookedTime = rs.getDate("BookedDate");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Mengambil pc condtition dari data pc
			rs = pcModel.getPC(targetPcID);
			String pcCondition = null;
			try {
				rs.next();
				pcCondition = rs.getString("PC_Condition");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Validasi
			if (!pcBookModel.checkPCBookIDExist(bookID)) {
				// Kalau pc book id tidak ada
				ov.alert.setContentText("Booking Not Found");
				ov.alert.showAndWait();
			} else if (!pcModel.checkPCExist(targetPcID)) {
				// Kalau pc tidak ada
				ov.alert.setContentText("PC Not Found");
				ov.alert.showAndWait();
			} else if (bookedTime.before(Date.valueOf(today))) {
				// Kalau booked time sebelum hari ini
				ov.alert.setContentText("Date must be at least today");
				ov.alert.showAndWait();
			} else if (pcBookModel.checkPCBookExist(targetPcID, bookedTime)) {
				// Kalau pc book sudah ada
				ov.alert.setContentText("PC is already booked for that date!");
				ov.alert.showAndWait();
			} else if (!pcCondition.equals("Usable")) {
				// Kalau pc condition bukan Usable
				ov.alert.setContentText("PC is currently not usable!");
				ov.alert.showAndWait();
			} else {
				// Memperbarui data pc book menggunakan pc book model
				pcBookModel.updatePCBook(bookID, targetPcID);
				
				// Reload page
				new OperatorHomePage(ov.stage, currentUser);
			}
		});
	}
}
