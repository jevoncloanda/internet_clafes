package model;

import java.sql.Date;

public class TransactionDetail {
	private Integer PC_ID;
	private String CustomerName;
	private Date BookedTime;

	public TransactionDetail(Integer pC_ID, String customerName, Date bookedTime) {
		super();
		PC_ID = pC_ID;
		CustomerName = customerName;
		BookedTime = bookedTime;
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

	public Date getBookedTime() {
		return BookedTime;
	}

	public void setBookedTime(Date bookedTime) {
		BookedTime = bookedTime;
	}

}
