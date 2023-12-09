package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.TransactionDetail;

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

}
