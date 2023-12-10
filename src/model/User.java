package model;

public class User {
	private Integer UserID;
	private String UserName;
	private String UserPassword;
	private Integer UserAge;
	private String UserRole;

	public User(Integer userID, String userName, String userPassword, Integer userAge, String userRole) {
		super();
		UserID = userID;
		UserName = userName;
		UserPassword = userPassword;
		UserAge = userAge;
		UserRole = userRole;
	}

	public Integer getUserID() {
		return UserID;
	}

	public void setUserID(Integer userID) {
		UserID = userID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserPassword() {
		return UserPassword;
	}

	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}

	public Integer getUserAge() {
		return UserAge;
	}

	public void setUserAge(Integer userAge) {
		UserAge = userAge;
	}

	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String userRole) {
		UserRole = userRole;
	}

}
