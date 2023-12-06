package controller;

import database.UserModel;
import model.User;
import view.LoginPage;
import view.RegisterPage.RegisterVar;

public class UserController {
	UserModel userModel = new UserModel();
	
	public void handling_regis(RegisterVar registerVar) {
		registerVar.button_regis.setOnAction(e -> {
			String u = registerVar.username_tf.getText();
			String p = registerVar.pass_pf.getText();
			String confP = registerVar.confpass_pf.getText();
			int a = registerVar.age_spin.getValue();

			if(u == null || p == null || a == 0) {
				registerVar.alert.setContentText("Please fill all of the fields!");
				registerVar.alert.showAndWait();
			}
	        else if(u.length() < 7) {
	        	registerVar.alert.setContentText("UserName must be 7 characters or more!");
	        	registerVar.alert.showAndWait();
	        }
	        else if(p.length() < 6) {
	        	registerVar.alert.setContentText("Password must be 6 characters or more!");
	        	registerVar.alert.showAndWait();
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
	        	registerVar.alert.setContentText("Password must only contain alphanumeric characters [A-Z and/or 0-9]");
	        	registerVar.alert.showAndWait();
	        }
	        else if(!p.equals(confP)) {
	        	registerVar.alert.setContentText("Confirm Password must be the same with Password!");
	        	registerVar.alert.showAndWait();
	        }
	        else if(a < 13 || a > 65) {
	        	registerVar.alert.setContentText("Age must be between 13-65");
	        	registerVar.alert.showAndWait();
	        }
	        else {
				userModel.regis(new User(u, p, a, "Customer"));
				new LoginPage(registerVar.stage);
			}
		});
	}
}
