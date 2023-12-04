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
	
	public String checkRegister(String u, String p, String confP, Integer a) {
		String res="";
		if(u == null || p == null || a == null) {
			res = "Please fill all of the fields!";
		}
		else if(u.length() < 7) {
			res = "UserName must be 7 characters or more!";
		}
		else if(p.length() < 6) {
			res = "Password must be 6 characters or more!";
		}
		else if(!p.toUpperCase().contains("A") &&
				!p.toUpperCase().contains("B") &&
				!p.toUpperCase().contains("C") &&
				!p.toUpperCase().contains("D") &&
				!p.toUpperCase().contains("E") &&
				!p.toUpperCase().contains("F") &&
				!p.toUpperCase().contains("G") &&
				!p.toUpperCase().contains("H") &&
				!p.toUpperCase().contains("I") &&
				!p.toUpperCase().contains("J") &&
				!p.toUpperCase().contains("K") &&
				!p.toUpperCase().contains("L") &&
				!p.toUpperCase().contains("M") &&
				!p.toUpperCase().contains("N") &&
				!p.toUpperCase().contains("O") &&
				!p.toUpperCase().contains("P") &&
				!p.toUpperCase().contains("Q") &&
				!p.toUpperCase().contains("R") &&
				!p.toUpperCase().contains("S") &&
				!p.toUpperCase().contains("T") &&
				!p.toUpperCase().contains("U") &&
				!p.toUpperCase().contains("V") &&
				!p.toUpperCase().contains("W") &&
				!p.toUpperCase().contains("X") &&
				!p.toUpperCase().contains("Y") &&
				!p.toUpperCase().contains("Z") &&
				!p.contains("0") &&
				!p.contains("1") &&
				!p.contains("2") &&
				!p.contains("3") &&
				!p.contains("4") &&
				!p.contains("5") &&
				!p.contains("6") &&
				!p.contains("7") &&
				!p.contains("8") &&
				!p.contains("9")) {
			res = "Password must only contain alphanumeric characters [A-Z and/or 0-9]";
		}
		else if(!p.equals(confP)) {
			res = "Confirm Password must be the same with Password!";
		}
		else if(a < 13 || a > 65) {
			res = "Age must be between 13-65";
		}
		
		return res;
	}
}
