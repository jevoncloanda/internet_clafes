package controller;

import database.PCModel;
import database.ReportModel;
import model.User;
import view.MakeReportPage;
import view.MakeReportPage.MakeReportPageVar;

public class ReportController {
	public void addNewReport(MakeReportPageVar mrp, User user) {
		mrp.makeReportBtn.setOnAction(e->{
			ReportModel reportModel = new ReportModel();
			PCModel pcModel = new PCModel();
			String userRole = user.getUserRole();
			Integer pcID = mrp.pcID_spin.getValue();
			String reportNote = mrp.reportNote_tf.getText();
			
			if(reportNote.isEmpty()) {
				mrp.alert.setContentText("Please fill all of the fields!");
				mrp.alert.showAndWait();
			}
			else if(pcModel.checkPCExist(pcID)==false) {
				mrp.alert.setContentText("Invalid PC ID!");
				mrp.alert.showAndWait();
			}
			else {
				reportModel.addNewReport(userRole, pcID, reportNote);
				mrp.success.setContentText("Report successfully sent!");
				mrp.success.showAndWait();
				new MakeReportPage(mrp.stage, user);
			}
		});
	}
}
