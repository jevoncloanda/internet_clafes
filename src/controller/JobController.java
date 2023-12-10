package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import admin_view.AddJobPage;
import admin_view.AddJobPage.AddJobPageVar;
import computer_technician_view.ComputerTechnicianHomePage;
import computer_technician_view.ComputerTechnicianHomePage.ComputerTechnicianHomePageVar;
import database.JobModel;
import database.PCModel;
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
	PCModel pcModel = new PCModel();
	PCController pcController = new PCController();
	ResultSet rs;
	
	public void addJobs(AddJobPageVar ajv) {
		ajv.addJobBtn.setOnAction(e->{
			Integer userID = ajv.userID_spin.getValue();
			Integer pcID = ajv.pcID_spin.getValue();
			String jobStatus = ajv.jobStatus2_tf.getText();
			String userRole = userModel.getUserRole(userID);
			
			if(jobStatus.isEmpty()) {
				ajv.addJobAlert.setContentText("Please fill all of the fields!");
				ajv.addJobAlert.showAndWait();
			}
			else if(userModel.checkUserExist(userID)==false || !userRole.equals("Technician")) {
				ajv.addJobAlert.setContentText("User ID is Invalid!");
				ajv.addJobAlert.showAndWait();
			}
			else if(pcModel.checkPCExist(pcID)==false) {
				ajv.addJobAlert.setContentText("PC ID is Invalid!");
				ajv.addJobAlert.showAndWait();
			}
			else if(!(jobStatus.equals("Complete") || jobStatus.equals("UnComplete"))) {
				ajv.addJobAlert.setContentText("Job Status must be either Complete or UnComplete!");
				ajv.addJobAlert.showAndWait();
			}
			else if(jobModel.checkJobExists(userID, pcID)) {
				ajv.addJobAlert.setContentText("Job already exists!");
				ajv.addJobAlert.showAndWait();
			}
			else {
				jobModel.addJob(new Job(0, userID, pcID, jobStatus));
				if(jobStatus.equals("UnComplete")) {
					pcModel.updatePCCondition(pcID, "Maintenance");
				}
				else {
					pcModel.updatePCCondition(pcID, "Usable");
				}
				new AddJobPage(ajv.stage);
			}
		});
	}
	
	public void updateJob(AddJobPageVar ajv) {
		ajv.updateJobBtn.setOnAction(e->{	
			Integer uID = ajv.userIDUpdate_spin.getValue();
			Integer pID = ajv.pcIDUpdate_spin.getValue();
			Integer jID = ajv.jobID_spin.getValue();
			String jStat = ajv.jobStatus_tf.getText();
			String userRole = userModel.getUserRole(uID);
			if(jStat.isEmpty()) {
				ajv.updateJobAlert.setContentText("Please fill all of the fields!");
				ajv.updateJobAlert.showAndWait();
			}
			else if(userModel.checkUserExist(uID)==false || !userRole.equals("Technician")) {
				ajv.updateJobAlert.setContentText("Invalid User ID!");
				ajv.updateJobAlert.showAndWait();
			}
			else if(pcModel.checkPCExist(pID)==false) {
				ajv.updateJobAlert.setContentText("PC ID is Invalid!");
				ajv.updateJobAlert.showAndWait();
			}
			else if(!(jStat.equals("Complete") || jStat.equals("UnComplete"))) {
				ajv.updateJobAlert.setContentText("Job Status must be either Complete or UnComplete!");
				ajv.updateJobAlert.showAndWait();
			}
			else if(jobModel.checkJobExists(jID)==false) {
				ajv.updateJobAlert.setContentText("Invalid Job ID!");
				ajv.updateJobAlert.showAndWait();
			}
			else if(jobModel.checkJobExists(jID, uID, pID)==false) {
				ajv.updateJobAlert.setContentText("Job doesn't exist! Make sure all id inputs are correct!");
				ajv.updateJobAlert.showAndWait();
			}
			else {
				jobModel.updateJobStatus(jID, jStat);
				if(jStat.equals("Complete")) {
					pcController.updatePCCondition(ajv, "Usable");
				}
				else {
					pcController.updatePCCondition(ajv, "Maintenance");
				}
				new AddJobPage(ajv.stage);
			}
		});
	}
	
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
	
	public void viewAllJobs(AddJobPageVar ajv) {
		ArrayList<Job> jobList = new ArrayList<>();
		ajv.vbJob = new VBox();
		ajv.tableJob = new TableView<Job>();
		ajv.jobID_col = new TableColumn<>("Job ID");
		ajv.userIDJob_col = new TableColumn<>("User ID");
		ajv.pcIDJob_col = new TableColumn<>("PC ID");
		ajv.jobStatus_col = new TableColumn<>("Job Status");
		ajv.tableJob.getColumns().addAll(ajv.jobID_col,
				ajv.userIDJob_col,
				ajv.pcIDJob_col,
				ajv.jobStatus_col);
        
        ResultSet rs = jobModel.getAllJobs();
        
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
        	ajv.tableJob.getItems().add(job);
		}
		
        ajv.jobID_col.setCellValueFactory(new PropertyValueFactory<>("Job_ID"));
        ajv.userIDJob_col.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        ajv.pcIDJob_col.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
        ajv.jobStatus_col.setCellValueFactory(new PropertyValueFactory<>("JobStatus"));
        
        
        ajv.vbJob.getChildren().add(ajv.tableJob);
	}
	
	public void completeJob(ComputerTechnicianHomePageVar tv, User user) {
		tv.btnComplete.setOnAction(e->{
			Integer id = tv.job_spin.getValue();
			Integer userid = userModel.getUserID(user.getUserName());
			Integer pcID = null;
			rs = jobModel.getJob(user.getUserName());
			try {
				rs.next();
				pcID = rs.getInt("PC_ID");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(jobModel.checkJobID(id, userid) == false) {
				tv.alert.setContentText("Invalid Job ID!");
	        	tv.alert.showAndWait();
			}
			else {
				jobModel.completeJob(id);
				pcModel.updatePCCondition(pcID, "Usable");
				new ComputerTechnicianHomePage(tv.stage, user);
			}
		});
	}
}
