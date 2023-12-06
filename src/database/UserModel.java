package database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.User;

public class UserModel {
	public void regis(User user) {
		Connect con = Connect.getInstance();
		
		String query ="INSERT INTO users Value('0',?,?,?,?)";
		
		PreparedStatement ps = con.prepareStatment(query);
		
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
}
