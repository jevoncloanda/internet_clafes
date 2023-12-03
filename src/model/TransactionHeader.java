package model;

import java.sql.Date;

public class TransactionHeader {
	private Integer TransactionID;
	private Integer StaffID;
	private String StaffName;
	private Date TransactionDate;
	public TransactionHeader(Integer transactionID, Integer staffID, String staffName, Date transactionDate) {
		super();
		TransactionID = transactionID;
		StaffID = staffID;
		StaffName = staffName;
		TransactionDate = transactionDate;
	}
	public Integer getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(Integer transactionID) {
		TransactionID = transactionID;
	}
	public Integer getStaffID() {
		return StaffID;
	}
	public void setStaffID(Integer staffID) {
		StaffID = staffID;
	}
	public String getStaffName() {
		return StaffName;
	}
	public void setStaffName(String staffName) {
		StaffName = staffName;
	}
	public Date getTransactionDate() {
		return TransactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		TransactionDate = transactionDate;
	}
	
	
}
