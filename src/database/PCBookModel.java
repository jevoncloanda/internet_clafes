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

	// Menambahkan pc book baru ke database
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

	// Cek pc book ada di database
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
	
	// Cek kalau pc book id ada menggunakan pc book id
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

	// Mengambil semua data pc book dari database
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

	// Mengambil data pc book berdasarkan pc book id tergentung
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
	
	// Memperbarui data pc book tertentu
	public void updatePCBook(Integer bookID, Integer newPcID) {
		String query = "UPDATE pcbooks SET PC_ID = ? WHERE BookID = ?";
		ps = con.prepareStatment(query);
		try {
			ps.setInt(1, newPcID);
			ps.setInt(2, bookID);
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	// Menghapus data pc book tertentu dari database
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
