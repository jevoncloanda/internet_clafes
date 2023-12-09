package model;

import java.sql.Date;

public class PCBook {
	private Integer PC_ID;
	private Integer UserID;
	private Date BookedDate;
	
	public PCBook(Integer pC_ID, Integer userID, Date bookedDate) {
		super();
		PC_ID = pC_ID;
		UserID = userID;
		BookedDate = bookedDate;
	}
	
	public Integer getPC_ID() {
		return PC_ID;
	}
	public void setPC_ID(Integer pC_ID) {
		PC_ID = pC_ID;
	}
	public Integer getUserID() {
		return UserID;
	}
	public void setUserID(Integer userID) {
		UserID = userID;
	}
	public Date getBookedDate() {
		return BookedDate;
	}
	public void setBookedDate(Date bookedDate) {
		BookedDate = bookedDate;
	}
	
	
}
