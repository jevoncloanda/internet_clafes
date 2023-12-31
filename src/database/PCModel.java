package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.PC;

public class PCModel {
	Connect con = Connect.getInstance();
	PreparedStatement ps;
	ResultSet rs;
	
	// Menambahkan pc baru ke database
	public void addPC(Integer id) {
		
		String query ="INSERT INTO pcs Value(?,'Usable')";
		
		ps = con.prepareStatment(query);
		
		try {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Memperbarui pc condition di database dengan pc id
	public void updatePCCondition(Integer pcID, String Condition) {
		String query = "UPDATE pcs SET PC_Condition=? WHERE PC_ID=?";
		ps = con.prepareStatment(query);
		try {
			ps.setString(1, Condition);
			ps.setInt(2, pcID);
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Cek kalau pc ada di database
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
	
	// Mengambil semua data pc dari database
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
	
	// Mengambil data pc tertentu menggunakan pc id
	public ResultSet getPC(Integer id) {
		String query = "SELECT * FROM pcs WHERE PC_ID = ?";
		ps = con.prepareStatment(query);
		try {
			ps.setInt(1, id);
			rs = ps.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
