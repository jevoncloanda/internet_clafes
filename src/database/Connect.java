package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connect {
	// Variabel untuk koneksi ke database
	private final String username = "root";
	private final String password = "";
	private final String database = "internet_clafes";
	private final String host = "localhost:3306";
	private final String connection = String.format("jdbc:mysql://%s/%s", host, database);
	
	private Connection con;
	public PreparedStatement ps;
	
	public static Connect connect;
	public ResultSet rs;
	
	// Function membuat instance connect (memakai pattern singleton)
	public static Connect getInstance() {
		if(connect == null) {
			connect = new Connect();
		}
		
		return connect;
	}
	
	// Function membuat koneksi ke database
	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(connection, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Function membuat prepared statement
	public PreparedStatement prepareStatment(String query) {
		ps = null;
		
		try {
			ps = con.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ps;
	}
}
