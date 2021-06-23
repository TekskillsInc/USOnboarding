package com.tekskills.Dto;

import java.util.List;

import com.tekskills.Entity.CandidateH1BInitiateDetailsEntity;
import com.tekskills.Entity.ClientVendorQuestionnarieAddressEntity;
import com.tekskills.Entity.ClientVendorQuestionnarieEntity;
import com.tekskills.Entity.EntryandExitDatesEntity;
import com.tekskills.Entity.H1BQuestionnariesEntity;
import com.tekskills.Entity.ImmiCandidateFormsStatusEntity;

public class ClientVendorLetterQueDto {
	
	ClientVendorQuestionnarieEntity cvlqObj;
	ClientVendorQuestionnarieAddressEntity cvlendclientObj;

	List<ClientVendorQuestionnarieAddressEntity> cvlAddressList;
	H1BQuestionnariesEntity h1bApplObj;
	H1BQuestionnariesEntity spouseObj;
	H1BQuestionnariesEntity childObj;
	List<H1BQuestionnariesEntity> childList;
	List<EntryandExitDatesEntity> entryexitList;
	
	List<EntryandExitDatesEntity> applEntryList;
	List<EntryandExitDatesEntity> spouseEntryList;
	List<EntryandExitDatesEntity> childEntryList;
	
	
	ImmiCandidateFormsStatusEntity formObj;
	
	CandidateH1BInitiateDetailsEntity initiateObj;
	
	public ClientVendorQuestionnarieAddressEntity getCvlendclientObj() {
		return cvlendclientObj;
	}
	public void setCvlendclientObj(ClientVendorQuestionnarieAddressEntity cvlendclientObj) {
		this.cvlendclientObj = cvlendclientObj;
	}
	
	public CandidateH1BInitiateDetailsEntity getInitiateObj() {
		return initiateObj;
	}
	public void setInitiateObj(CandidateH1BInitiateDetailsEntity initiateObj) {
		this.initiateObj = initiateObj;
	}
	public ImmiCandidateFormsStatusEntity getFormObj() {
		return formObj;
	}
	public void setFormObj(ImmiCandidateFormsStatusEntity formObj) {
		this.formObj = formObj;
	}
	public H1BQuestionnariesEntity getH1bApplObj() {
		return h1bApplObj;
	}
	public void setH1bApplObj(H1BQuestionnariesEntity h1bApplObj) {
		this.h1bApplObj = h1bApplObj;
	}
	public H1BQuestionnariesEntity getSpouseObj() {
		return spouseObj;
	}
	public void setSpouseObj(H1BQuestionnariesEntity spouseObj) {
		this.spouseObj = spouseObj;
	}
	public List<H1BQuestionnariesEntity> getChildList() {
		return childList;
	}
	public void setChildList(List<H1BQuestionnariesEntity> childList) {
		this.childList = childList;
	}
	public List<EntryandExitDatesEntity> getEntryexitList() {
		return entryexitList;
	}
	public void setEntryexitList(List<EntryandExitDatesEntity> entryexitList) {
		this.entryexitList = entryexitList;
	}
	public ClientVendorQuestionnarieEntity getCvlqObj() {
		return cvlqObj;
	}
	public void setCvlqObj(ClientVendorQuestionnarieEntity cvlqObj) {
		this.cvlqObj = cvlqObj;
	}
	public List<ClientVendorQuestionnarieAddressEntity> getCvlAddressList() {
		return cvlAddressList;
	}
	public void setCvlAddressList(List<ClientVendorQuestionnarieAddressEntity> cvlAddressList) {
		this.cvlAddressList = cvlAddressList;
	}
	public List<EntryandExitDatesEntity> getApplEntryList() {
		return applEntryList;
	}
	public void setApplEntryList(List<EntryandExitDatesEntity> applEntryList) {
		this.applEntryList = applEntryList;
	}
	public List<EntryandExitDatesEntity> getSpouseEntryList() {
		return spouseEntryList;
	}
	public void setSpouseEntryList(List<EntryandExitDatesEntity> spouseEntryList) {
		this.spouseEntryList = spouseEntryList;
	}
	public List<EntryandExitDatesEntity> getChildEntryList() {
		return childEntryList;
	}
	public void setChildEntryList(List<EntryandExitDatesEntity> childEntryList) {
		this.childEntryList = childEntryList;
	}
	public H1BQuestionnariesEntity getChildObj() {
		return childObj;
	}
	public void setChildObj(H1BQuestionnariesEntity childObj) {
		this.childObj = childObj;
	}
	
}
