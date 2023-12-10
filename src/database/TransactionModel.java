package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.TransactionDetail;
import model.TransactionHeader;
import model.User;

public class TransactionModel {
	Connect con = Connect.getInstance();
	PreparedStatement ps;
	ResultSet rs;
	
	public void addTransactionDetail(TransactionDetail td) {
		
		String query ="INSERT INTO transactiondetails Value('0',?,?,?)";
		
		ps = con.prepareStatment(query);
		
		try {
			ps.setInt(1, td.getPC_ID());
			ps.setString(2, td.getCustomerName());
			ps.setDate(3, td.getBookedTime());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addTransactionHeader(TransactionHeader th) {
		
		String query ="INSERT INTO transactionheaders Value('0',?,?,?)";
		
		ps = con.prepareStatment(query);
		
		try {
			ps.setInt(1, th.getStaffID());
			ps.setString(2, th.getStaffName());
			ps.setDate(3, th.getTransactionDate());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet getTransactionDetailByCustomer(User currentUser) {
		String query = "SELECT * FROM transactiondetails WHERE CustomerName = ?";
		
		ps = con.prepareStatment(query);
		
		try {
			ps.setString(1, currentUser.getUserName());
			rs = ps.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getAllTransactionDetail() {
		String query = "SELECT * FROM transactiondetails";
		
		ps = con.prepareStatment(query);
		
		try {
			rs = ps.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getAllTransactionHeader() {
		String query = "SELECT * FROM transactionheaders";
		
		ps = con.prepareStatment(query);
		
		try {
			rs = ps.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

}
