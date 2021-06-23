package com.tekskills.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Tbl_InitiateH1BDetails")
public class InitiateH1BDetailsEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Column(name="initiate_id")
	private int initiate_id;
	
	@Column(name="candidate_id")
	private int candidate_id;
	  
	@Column(name="profile_id")
	private int profile_id;
	
	@Column(name="status")
	private String status;
	
	@Column(name="posted_by")
	private int posted_by;
		
	@Temporal(TemporalType.DATE)
	@Column(name="submitted_date")
	private Date submitted_date;

	public int getInitiate_id() {
		return initiate_id;
	}

	public void setInitiate_id(int initiate_id) {
		this.initiate_id = initiate_id;
	}

	public int getCandidate_id() {
		return candidate_id;
	}

	public void setCandidate_id(int candidate_id) {
		this.candidate_id = candidate_id;
	}

	public int getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(int profile_id) {
		this.profile_id = profile_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPosted_by() {
		return posted_by;
	}

	public void setPosted_by(int posted_by) {
		this.posted_by = posted_by;
	}

	public Date getSubmitted_date() {
		return submitted_date;
	}

	public void setSubmitted_date(Date submitted_date) {
		this.submitted_date = submitted_date;
	}
	
	
	  
}
