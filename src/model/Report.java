package model;

import java.sql.Date;

public class Report {
	private Integer Report_ID;
	private String UserRole;
	private Integer PC_ID;
	private String ReportNote;
	private Date ReportDate;
	public Report(Integer report_ID, String userRole, Integer pC_ID, String reportNote, Date reportDate) {
		super();
		Report_ID = report_ID;
		UserRole = userRole;
		PC_ID = pC_ID;
		ReportNote = reportNote;
		ReportDate = reportDate;
	}
	public String getUserRole() {
		return UserRole;
	}
	public void setUserRole(String userRole) {
		UserRole = userRole;
	}
	public Integer getPC_ID() {
		return PC_ID;
	}
	public void setPC_ID(Integer pC_ID) {
		PC_ID = pC_ID;
	}
	public Date getReportDate() {
		return ReportDate;
	}
	public void setReportDate(Date reportDate) {
		ReportDate = reportDate;
	}
	public Integer getReport_ID() {
		return Report_ID;
	}
	public void setReport_ID(Integer report_ID) {
		Report_ID = report_ID;
	}
	public void setReportNote(String reportNote) {
		ReportNote = reportNote;
	}
	public String getReportNote() {
		return ReportNote;
	}
	
	
}
