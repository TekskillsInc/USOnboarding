package com.tekskills.Dto;

public class DocumentHistoryDto {

	private int document_id;
	private String file_name;
	private String submit_date;	
	private String filetype;
	private String inputval;  
	private String startdate;  
	private String expirydate;  
	private String docStatus;
	private String posted_by;
	private String custName;
	private String venName;
	private String doctypename;
	private String subdoctypename;
	private String beneficiary_Name;
	private int initiate_id;
	private int candidateId;
	private Integer h1bId;
	
	public String getDoctypename() {
		return doctypename;
	}
	public void setDoctypename(String doctypename) {
		this.doctypename = doctypename;
	}
	public String getSubdoctypename() {
		return subdoctypename;
	}
	public void setSubdoctypename(String subdoctypename) {
		this.subdoctypename = subdoctypename;
	}
	public int getDocument_id() {
		return document_id;
	}
	public void setDocument_id(int document_id) {
		this.document_id = document_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getSubmit_date() {
		return submit_date;
	}
	public void setSubmit_date(String submit_date) {
		this.submit_date = submit_date;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public String getInputval() {
		return inputval;
	}
	public void setInputval(String inputval) {
		this.inputval = inputval;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getExpirydate() {
		return expirydate;
	}
	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}
	public String getDocStatus() {
		return docStatus;
	}
	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}
	public String getPosted_by() {
		return posted_by;
	}
	public void setPosted_by(String posted_by) {
		this.posted_by = posted_by;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getVenName() {
		return venName;
	}
	public void setVenName(String venName) {
		this.venName = venName;
	}
	public String getBeneficiary_Name() {
		return beneficiary_Name;
	}
	public void setBeneficiary_Name(String beneficiary_Name) {
		this.beneficiary_Name = beneficiary_Name;
	}
	public int getInitiate_id() {
		return initiate_id;
	}
	public void setInitiate_id(int initiate_id) {
		this.initiate_id = initiate_id;
	}
	public int getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}
	public Integer getH1bId() {
		return h1bId;
	}
	public void setH1bId(Integer h1bId) {
		this.h1bId = h1bId;
	}
	
}