package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.PCBook;

public class PCBookModel {
	Connect con = Connect.getInstance();
	PreparedStatement ps;
	ResultSet rs;
	
	public void addPCBook(PCBook pcBook) {
		String query ="INSERT INTO pcbooks Value('0',?,?,?)";
		
		ps = con.prepareStatment(query);
		
		try {
			ps.setInt(1, pcBook.getPC_ID());
			ps.setInt(2, pcBook.getUserID());
			ps.setDate(3, pcBook.getBookedDate());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean checkPCBookExist(Integer id, Date bookedDate) {
		String query = "SELECT EXISTS(SELECT * FROM pcbooks WHERE PC_ID = ? AND BookedDate = ?)";
		ps = con.prepareStatment(query);
		try {
			ps.setInt(1, id);
			ps.setDate(2, bookedDate);
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
}