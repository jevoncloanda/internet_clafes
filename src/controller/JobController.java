package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import computer_technician_view.ComputerTechnicianHomePage;
import computer_technician_view.ComputerTechnicianHomePage.ComputerTechnicianHomePageVar;
import database.JobModel;
import database.UserModel;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Job;
import model.User;

public class JobController {
	JobModel jobModel = new JobModel();
	UserModel userModel = new UserModel();
	ResultSet rs;
	
	public void viewJobs(ComputerTechnicianHomePageVar technicianHomePageVar, User user) {
		ArrayList<Job> jobList = new ArrayList<>();
		technicianHomePageVar.vb1 = new VBox();
		technicianHomePageVar.table = new TableView<Job>();
		technicianHomePageVar.jobID_col = new TableColumn<>("Job ID");
		technicianHomePageVar.userID_col = new TableColumn<>("User ID");
		technicianHomePageVar.pcID_col = new TableColumn<>("PC ID");
		technicianHomePageVar.jobStatus_col = new TableColumn<>("Job Status");
		technicianHomePageVar.table.getColumns().addAll(technicianHomePageVar.jobID_col,
				technicianHomePageVar.userID_col,
				technicianHomePageVar.pcID_col,
				technicianHomePageVar.jobStatus_col);
        
        ResultSet rs = jobModel.getJob(user.getUserName());
        
        try {
			while(rs.next()) {
				Integer jid = rs.getInt("Job_ID");
				Integer uid = rs.getInt("UserID");
				Integer pcid = rs.getInt("PC_ID");
				String jsts = rs.getString("JobStatus");
				
				jobList.add(new Job(jid, uid, pcid, jsts));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        for (Job job : jobList) {
        	technicianHomePageVar.table.getItems().add(job);
		}
		
        technicianHomePageVar.jobID_col.setCellValueFactory(new PropertyValueFactory<>("Job_ID"));
        technicianHomePageVar.userID_col.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        technicianHomePageVar.pcID_col.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
        technicianHomePageVar.jobStatus_col.setCellValueFactory(new PropertyValueFactory<>("JobStatus"));
        
        
        technicianHomePageVar.vb1.getChildren().add(technicianHomePageVar.table);
	}
	
	public void completeJob(ComputerTechnicianHomePageVar tv, User user) {
		tv.btnComplete.setOnAction(e->{
			Integer id = tv.job_spin.getValue();
			Integer userid = userModel.getUserID(user.getUserName());
			if(jobModel.checkJobID(id, userid) == false) {
				tv.alert.setContentText("Invalid Job ID!");
	        	tv.alert.showAndWait();
			}
			else {
				jobModel.completeJob(id);
				new ComputerTechnicianHomePage(tv.stage, user);
			}
		});
	}
}
