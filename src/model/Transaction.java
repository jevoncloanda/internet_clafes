package model;

import java.sql.Date;

public class Transaction {
	private Integer PC_ID;
	private String CustomerName;
	private Date TransactionDate;
	private Integer StaffID;
	private String StaffName;

	public Transaction(Integer pC_ID, String customerName, Date transactionDate, Integer staffID, String staffName) {
		super();
		PC_ID = pC_ID;
		CustomerName = customerName;
		TransactionDate = transactionDate;
		StaffID = staffID;
		StaffName = staffName;
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

	public Date getTransactionDate() {
		return TransactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		TransactionDate = transactionDate;
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

}
