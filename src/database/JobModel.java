package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Job;

public class JobModel {
	Connect con = Connect.getInstance();
	PreparedStatement ps;
	ResultSet rs;
	UserModel userModel = new UserModel();
	
	// Menambahkan technician job baru ke database
	public void addJob(Job job) {
		String query ="INSERT INTO jobs Value('0',?,?,?)";
		
		ps = con.prepareStatment(query);
		
		try {
			ps.setInt(1, job.getUserID());
			ps.setInt(2, job.getPC_ID());
			ps.setString(3, job.getJobStatus());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Mengambil data job dengan username technician
	public ResultSet getJob(String userName) {
		int userID = userModel.getUserID(userName);
		String query = "SELECT * FROM jobs WHERE UserID = ?";
		ps = con.prepareStatment(query);
		try {
			ps.setInt(1, userID);
			rs = ps.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	// Mengambil semua data job
	public ResultSet getAllJobs() {
		String query = "SELECT * FROM jobs";
		ps = con.prepareStatment(query);
		try {
			rs = ps.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	// Cek jika job id ada di database
	public boolean checkJobID(Integer id, Integer userid) {
		String query = "SELECT EXISTS(SELECT * FROM jobs WHERE Job_ID = ? AND UserID = ?)";
		ps = con.prepareStatment(query);
		try {
			ps.setInt(1, id);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			rs.next();
			if(rs.getInt(1)==0) {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	// Cek jika job sudah ada dengan user id dan pc id
	public boolean checkJobExists(Integer userID, Integer pcID) {
		String query = "SELECT EXISTS(SELECT * FROM jobs WHERE UserID = ? AND PC_ID = ?)";
		ps = con.prepareStatment(query);
		try {
			ps.setInt(1, userID);
			ps.setInt(2, pcID);
			rs = ps.executeQuery();
			rs.next();
			if(rs.getInt(1)==0) {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	// Cek jika job sudah ada dengan job id
	public boolean checkJobExists(Integer jobID) {
		String query = "SELECT EXISTS(SELECT * FROM jobs WHERE Job_ID = ?)";
		ps = con.prepareStatment(query);
		try {
			ps.setInt(1, jobID);
			rs = ps.executeQuery();
			rs.next();
			if(rs.getInt(1)==0) {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	// Cek job sudah ada dengan job id, user id, dan pc id
	public boolean checkJobExists(Integer jobID, Integer userID, Integer pcID) {
		String query = "SELECT EXISTS(SELECT * FROM jobs WHERE Job_ID = ? AND UserID = ? AND PC_ID = ?)";
		ps = con.prepareStatment(query);
		try {
			ps.setInt(1, jobID);
			ps.setInt(2, userID);
			ps.setInt(3, pcID);
			rs = ps.executeQuery();
			rs.next();
			if(rs.getInt(1)==0) {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	// Update data job teretntu menjadi Complete
	public void completeJob(Integer id) {
		String query = "UPDATE jobs SET JobStatus='Complete' WHERE Job_ID=?";
		ps = con.prepareStatment(query);
		try {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Memperbarui job technician status pada database
	public void updateJobStatus(Integer jobID, String status) {
		String query = "UPDATE jobs SET JobStatus=? WHERE Job_ID=?";
		ps = con.prepareStatment(query);
		try {
			ps.setString(1, status);
			ps.setInt(2, jobID);
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
