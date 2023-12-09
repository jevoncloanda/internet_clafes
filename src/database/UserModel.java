package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.User;

public class UserModel {
	Connect con = Connect.getInstance();
	PreparedStatement ps;
	ResultSet rs;
	public void regis(User user) {
		
		String query ="INSERT INTO users Value('0',?,?,?,?)";
		
		ps = con.prepareStatment(query);
		
		try {
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getUserPassword());
			ps.setInt(3, user.getUserAge());
			ps.setString(4, user.getUserRole());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean checkUserExist(String userName) {
		String query = "SELECT EXISTS(SELECT * FROM users WHERE UserName = ?)";
		ps = con.prepareStatment(query);
		try {
			ps.setString(1, userName);
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
	
	public boolean login(String userName, String pass) {
		String query = "SELECT EXISTS(SELECT * FROM users WHERE UserName = ? AND UserPassword = ?)";
		ps = con.prepareStatment(query);
		try {
			ps.setString(1, userName);
			ps.setString(2, pass);
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
	
	public ResultSet getUser(String userName, String pass) {
		String query = "SELECT * FROM users WHERE UserName = ? AND UserPassword = ?";
		ps = con.prepareStatment(query);
		try {
			ps.setString(1, userName);
			ps.setString(2, pass);
			rs = ps.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public void updateRole(String userName, String newRole) {
		String query = "UPDATE users SET UserRole = ? WHERE UserName = ?";
		ps = con.prepareStatment(query);
		try {
			ps.setString(1, newRole);
			ps.setString(2, userName);
			ps.executeUpdate();
//			rs.next();
//			rs.getString("UserName");
//			rs.getString("UserPassword");
//			rs.getString("UserAge");
//			rs.getString("UserRole");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getAllStaffModel() {
		String query = "SELECT * FROM users WHERE UserRole = 'Admin' OR UserRole = 'Technician' OR UserRole = 'Operator'";
		
		ps = con.prepareStatment(query);
		
		try {
			rs = ps.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
