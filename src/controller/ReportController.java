package controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import admin_view.ViewReportsPage.ViewReportsPageVar;
import database.PCModel;
import database.ReportModel;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.PC;
import model.Report;
import model.User;
import view.MakeReportPage;
import view.MakeReportPage.MakeReportPageVar;

public class ReportController {
	ReportModel reportModel = new ReportModel();
	
	// Function untuk mengurus insert report baru
	public void addNewReport(MakeReportPageVar mrp, User user) {
		mrp.makeReportBtn.setOnAction(e->{
			// Menamgbil semua value form report
			PCModel pcModel = new PCModel();
			String userRole = user.getUserRole();
			Integer pcID = mrp.pcID_spin.getValue();
			String reportNote = mrp.reportNote_tf.getText();
			
			// Validasi
			if(reportNote.isEmpty()) {
				// Kalau data kosong
				mrp.alert.setContentText("Please fill all of the fields!");
				mrp.alert.showAndWait();
			}
			else if(pcModel.checkPCExist(pcID)==false) {
				// Kalau pc tidak ada
				mrp.alert.setContentText("Invalid PC ID!");
				mrp.alert.showAndWait();
			}
			else {
				// Insert report baru ke database melalui report model
				reportModel.addNewReport(userRole, pcID, reportNote);
				mrp.success.setContentText("Report successfully sent!");
				mrp.success.showAndWait();
				new MakeReportPage(mrp.stage, user);
			}
		});
	}
	
	// Function untuk menampilkan tabel report pada view report page untuk admin
	public void getAllReportData(ViewReportsPageVar vr) {
		ArrayList<Report> reportList = new ArrayList<>();
		// Inisialisasi semua variabel tabel
		vr.vb1 = new VBox();
		vr.reportTable = new TableView<Report>();
		vr.tableTitle = new Label("Report Table");
		vr.reportID_col = new TableColumn<>("Report ID");
		vr.userRole_col = new TableColumn<>("User Role");
		vr.pcID_col = new TableColumn<>("PC ID");
		vr.reportNote_col = new TableColumn<>("Report Note");
		vr.reportDate_col = new TableColumn<>("Report Date");
		vr.reportTable.getColumns().addAll(vr.reportID_col, vr.userRole_col, vr.pcID_col, vr.reportNote_col, vr.reportDate_col);

		// Mengambil semua data report
		ResultSet rs = reportModel.getAllReportData();
		
		// Memasukkan tiap data report ke tabel
		try {
			while (rs.next()) {
				Integer rid = rs.getInt("Report_ID");
				String role = rs.getString("UserRole");
				Integer pcid = rs.getInt("PC_ID");
				String note = rs.getString("ReportNote");
				Date date = rs.getDate("ReportDate");

				reportList.add(new Report(rid, role, pcid, note, date));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (Report report : reportList) {
			vr.reportTable.getItems().add(report);
		}

		vr.reportID_col.setCellValueFactory(new PropertyValueFactory<>("Report_ID"));
		vr.userRole_col.setCellValueFactory(new PropertyValueFactory<>("UserRole"));
		vr.pcID_col.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		vr.reportNote_col.setCellValueFactory(new PropertyValueFactory<>("ReportNote"));
		vr.reportDate_col.setCellValueFactory(new PropertyValueFactory<>("ReportDate"));

		vr.vb1.getChildren().addAll(vr.tableTitle, vr.reportTable);
	}
}
