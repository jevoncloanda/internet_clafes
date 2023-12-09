package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.PCBook;
import model.User;

public class PCBookModel {
	Connect con = Connect.getInstance();
	PreparedStatement ps;
	ResultSet rs;

	public void addPCBook(PCBook pcBook) {
		String query = "INSERT INTO pcbooks Value('0',?,?,?)";

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
			if (rs.getInt(1) == 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean checkPCBookIDExist(Integer id) {
		String query = "SELECT EXISTS(SELECT * FROM pcbooks WHERE BookID = ?)";
		ps = con.prepareStatment(query);
		try {
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rs.next();
			if (rs.getInt(1) == 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public ResultSet getAllPCBooks() {
		String query = "SELECT * FROM pcbooks";

		ps = con.prepareStatment(query);

		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getPCBookByID(Integer bookID) {
		String query = "SELECT * FROM pcbooks WHERE BookID = ?";

		ps = con.prepareStatment(query);

		try {
			ps.setInt(1, bookID);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void deletePCBook(Integer bookID) {
		String query = "DELETE FROM pcbooks WHERE BookID = ?";

		ps = con.prepareStatment(query);

		try {
			ps.setInt(1, bookID);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
