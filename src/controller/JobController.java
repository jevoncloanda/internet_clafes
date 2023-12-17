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
	
	// Function untuk menambahkan job technician
	public void addJobs(AddJobPageVar ajv) {
		ajv.addJobBtn.setOnAction(e->{
			// Mengambil value input form add job
			Integer userID = ajv.userID_spin.getValue();
			Integer pcID = ajv.pcID_spin.getValue();
			String jobStatus = ajv.jobStatus2_tf.getText();
			String userRole = userModel.getUserRole(userID);
			
			// Validasi
			if(jobStatus.isEmpty()) {
				// Kalau data kosong
				ajv.addJobAlert.setContentText("Please fill all of the fields!");
				ajv.addJobAlert.showAndWait();
			}
			else if(userModel.checkUserExist(userID)==false || !userRole.equals("Technician")) {
				// Kalau user tidak ada / user bukan technician
				ajv.addJobAlert.setContentText("User ID is Invalid!");
				ajv.addJobAlert.showAndWait();
			}
			else if(pcModel.checkPCExist(pcID)==false) {
				// Kalau pc tidak ada
				ajv.addJobAlert.setContentText("PC ID is Invalid!");
				ajv.addJobAlert.showAndWait();
			}
			else if(!(jobStatus.equals("Complete") || jobStatus.equals("UnComplete"))) {
				// Kalau input job status bukan Complete / UnComplete
				ajv.addJobAlert.setContentText("Job Status must be either Complete or UnComplete!");
				ajv.addJobAlert.showAndWait();
			}
			else if(jobModel.checkJobExists(userID, pcID)) {
				// Kalau job sudah ada
				ajv.addJobAlert.setContentText("Job already exists!");
				ajv.addJobAlert.showAndWait();
			}
			else {
				// Menambahkan job baru ke database melalui job model
				jobModel.addJob(new Job(0, userID, pcID, jobStatus));
				
				// Mengganti status pc tergantung job status nya
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
	
	// Function untuk memperbarui job technician
	public void updateJob(AddJobPageVar ajv) {
		ajv.updateJobBtn.setOnAction(e->{	
			// Mengambil input value dari form update technician job
			Integer uID = ajv.userIDUpdate_spin.getValue();
			Integer pID = ajv.pcIDUpdate_spin.getValue();
			Integer jID = ajv.jobID_spin.getValue();
			String jStat = ajv.jobStatus_tf.getText();
			String userRole = userModel.getUserRole(uID);
			
			// Validasi
			if(jStat.isEmpty()) {
				// Kalau data kosong
				ajv.updateJobAlert.setContentText("Please fill all of the fields!");
				ajv.updateJobAlert.showAndWait();
			}
			else if(userModel.checkUserExist(uID)==false || !userRole.equals("Technician")) {
				// Kalau user tidak ada / bukan technician
				ajv.updateJobAlert.setContentText("Invalid User ID!");
				ajv.updateJobAlert.showAndWait();
			}
			else if(pcModel.checkPCExist(pID)==false) {
				// Kalau pc tidak ada
				ajv.updateJobAlert.setContentText("PC ID is Invalid!");
				ajv.updateJobAlert.showAndWait();
			}
			else if(!(jStat.equals("Complete") || jStat.equals("UnComplete"))) {
				// Kalau kondisi baru bukan Complete / UnComplete
				ajv.updateJobAlert.setContentText("Job Status must be either Complete or UnComplete!");
				ajv.updateJobAlert.showAndWait();
			}
			else if(jobModel.checkJobExists(jID)==false) {
				// Kalau job tidak ada
				ajv.updateJobAlert.setContentText("Invalid Job ID!");
				ajv.updateJobAlert.showAndWait();
			}
			else if(jobModel.checkJobExists(jID, uID, pID)==false) {
				// Kalau job tidak ada
				ajv.updateJobAlert.setContentText("Job doesn't exist! Make sure all id inputs are correct!");
				ajv.updateJobAlert.showAndWait();
			}
			else {
				// Memperbarui status job technician melalui job model
				jobModel.updateJobStatus(jID, jStat);
				
				// Memberbarui kondisi pc melalui pc model tergantung status job
				if(jStat.equals("Complete")) {
					pcController.updatePCCondition(ajv, "Usable");
				}
				else {
					pcController.updatePCCondition(ajv, "Maintenance");
				}
				
				// Reload page
				new AddJobPage(ajv.stage);
			}
		});
	}
	
	// Function untuk melihatkan tabel jobs technician tertentu
	public void viewJobs(ComputerTechnicianHomePageVar technicianHomePageVar, User user) {
		ArrayList<Job> jobList = new ArrayList<>();
		// Inisialisasi semua variabel table
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
        
		// Mengambil semua data job technician tertentu
        ResultSet rs = jobModel.getJob(user.getUserName());
        
        // Memasukkan tiap row data job technician ke tabel
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
	
	// Function untuk menunjukkan tabel job di add job page
	public void viewAllJobs(AddJobPageVar ajv) {
		ArrayList<Job> jobList = new ArrayList<>();
		// Inisialisasi semua variabel tabel
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
        
		// Mengambil semua data job
        ResultSet rs = jobModel.getAllJobs();
        
        // Memasukkan tiap row data job ke tabel
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
	
	// Function yang mengurus complete job di computer technician page
	public void completeJob(ComputerTechnicianHomePageVar tv, User user) {
		tv.btnComplete.setOnAction(e->{
			// Mengambil semua value input form complete job
			Integer id = tv.job_spin.getValue();
			Integer userid = userModel.getUserID(user.getUserName());
			Integer pcID = null;
			
			// Ambil data job dari database menggunakan job model
			rs = jobModel.getJob(user.getUserName());
			try {
				rs.next();
				pcID = rs.getInt("PC_ID");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Validasi
			if(jobModel.checkJobID(id, userid) == false) {
				// Kalau job id tidak ada di database
				tv.alert.setContentText("Invalid Job ID!");
	        	tv.alert.showAndWait();
			}
			else {
				// Update data job dan pc melalui job
				jobModel.completeJob(id);
				pcModel.updatePCCondition(pcID, "Usable");
				new ComputerTechnicianHomePage(tv.stage, user);
			}
		});
	}
}
