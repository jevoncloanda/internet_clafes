package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.PC;

public class PCModel {
	Connect con = Connect.getInstance();
	PreparedStatement ps;
	ResultSet rs;
	public void addPC(PC pc) {
		
//		String query ="INSERT INTO users Value('0',?,?,?,?)";
//		
//		ps = con.prepareStatment(query);
//		
//		try {
//			ps.setString(1, user.getUserName());
//			ps.setString(2, user.getUserPassword());
//			ps.setInt(3, user.getUserAge());
//			ps.setString(4, user.getUserRole());
//			ps.executeUpdate();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public boolean checkPCExist(Integer pc_id) {
		String query = "SELECT EXISTS(SELECT * FROM pcs WHERE PC_ID = ?)";
		ps = con.prepareStatment(query);
		try {
			ps.setInt(1, pc_id);
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
	
	public ResultSet getAllPC() {
		String query = "SELECT * FROM pcs";
		ps = con.prepareStatment(query);
		try {
			rs = ps.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
