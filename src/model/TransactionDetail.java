package model;

import java.sql.Time;

public class TransactionDetail {
	private Integer TransactionID;
	private Integer PC_ID;
	private String CustomerName;
	private Time BookedTime;
	public TransactionDetail(Integer transactionID, Integer pC_ID, String customerName, Time bookedTime) {
		super();
		TransactionID = transactionID;
		PC_ID = pC_ID;
		CustomerName = customerName;
		BookedTime = bookedTime;
	}
	public Integer getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(Integer transactionID) {
		TransactionID = transactionID;
	}
	public Integer getPC_ID() {
		return PC_ID;
	}
	public void setPC_ID(Integer pC_ID) {
		PC_ID = pC_ID;
	}
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public Time getBookedTime() {
		return BookedTime;
	}
	public void setBookedTime(Time bookedTime) {
		BookedTime = bookedTime;
	}
	
	
}
