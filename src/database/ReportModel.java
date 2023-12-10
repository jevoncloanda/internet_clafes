package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportModel {
	Connect con = Connect.getInstance();
	PreparedStatement ps;
	ResultSet rs;
	
	public void addNewReport(String userRole, Integer pcID, String reportNote) {
		String query ="INSERT INTO reports Value('0',?,?,?,?)";
		Date date = new Date(System.currentTimeMillis());
		ps = con.prepareStatment(query);
		
		try {
			ps.setString(1, userRole);
			ps.setInt(2, pcID);
			ps.setString(3, reportNote);
			ps.setDate(4, date);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet getAllReportData() {
		String query = "SELECT * FROM reports";
		ps = con.prepareStatment(query);
		try {
			rs = ps.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
