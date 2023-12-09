package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobModel {
	Connect con = Connect.getInstance();
	PreparedStatement ps;
	ResultSet rs;
	
	public int getUserID(String userName) {
		String query = "SELECT UserID FROM users WHERE UserName = ?";
		ps = con.prepareStatment(query);
		try {
			ps.setString(1, userName);
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt("UserID");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public ResultSet getJob(String userName) {
		int userID = getUserID(userName);
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
}
