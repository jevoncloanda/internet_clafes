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
	
	// Function untuk memasukkan data user ke database
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
	
	// Mengambil id user menggunakan user name
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
	
	// Mengambil role user menggunakan id
	public String getUserRole(Integer id) {
		String query = "SELECT UserRole FROM users WHERE UserID = ?";
		ps = con.prepareStatment(query);
		try {
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rs.next();
			return rs.getString("UserRole");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	// Cek user ada di database menggunakan user name
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
	
	// Cek user ada di database menggunakan user id
	public boolean checkUserExist(Integer id) {
		String query = "SELECT EXISTS(SELECT * FROM users WHERE UserID = ?)";
		ps = con.prepareStatment(query);
		try {
			ps.setInt(1, id);
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
	
	// Cek kalau data user ada di database
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
	
	// Mengambil data user dari database menggunakan parameter username dan password
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
	
	// Untuk update role user
	public void updateRole(String userName, String newRole) {
		String query = "UPDATE users SET UserRole = ? WHERE UserName = ?";
		ps = con.prepareStatment(query);
		try {
			ps.setString(1, newRole);
			ps.setString(2, userName);
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Ambil semua data staff dari database (user dengan role Admin / Technician / Operator)
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
	
	// Mengambil semua data technician (user dengan role Technician)
	public ResultSet getAllTechnicians() {
		String query = "SELECT * FROM users WHERE UserRole = 'Technician'";
		
		ps = con.prepareStatment(query);
		
		try {
			rs = ps.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
