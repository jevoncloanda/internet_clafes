package controller;

import database.Connect;
import model.User;

public class UserController {
	
	public void customerRegister(String u, String p, Integer a) {
		Connect con = Connect.getInstance();
		String role = "Customer";
		
		if(u.length() >= 7 && p.length() >= 6) {
			con.insertUser("INSERT INTO users(UserName, UserPassword, UserAge, UserRole) VALUES(?,?,?,?)", new User(u, p, a, role));
		}
	}	
}
