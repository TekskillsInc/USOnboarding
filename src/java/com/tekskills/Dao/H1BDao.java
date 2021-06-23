package com.tekskills.Dao;

import java.util.List;

import com.tekskills.Dto.BasicDetailsDto;
import com.tekskills.Dto.CandidateH1BInitiateDetailsDto;
import com.tekskills.Dto.ContractsPanelDetails_DTO;
import com.tekskills.Dto.DocumentHistoryDto;
import com.tekskills.Dto.DocumentTypeDto;
import com.tekskills.Dto.DocumentsDTO;
import com.tekskills.Dto.H1BDetailsDto;
import com.tekskills.Dto.ImmicommentsDTO;
import com.tekskills.Dto.ProfileMasterDTO;
import com.tekskills.Dto.PublicBenefitsChecklistDTO;
import com.tekskills.Dto.commentHistoryDto;
import com.tekskills.Entity.CandidateH1BCheckList;
import com.tekskills.Entity.CandidateH1BInitiateDetailsEntity;
import com.tekskills.Entity.CandidateScannedDocumentsEntity;
import com.tekskills.Entity.ClientVendorQuestionnarieAddressEntity;
import com.tekskills.Entity.ClientVendorQuestionnarieEntity;
import com.tekskills.Entity.EmpDocumentsEntity;
import com.tekskills.Entity.EntryandExitDatesEntity;
import com.tekskills.Entity.H1BDetailsEntity;
import com.tekskills.Entity.H1BQuestionnariesEntity;
import com.tekskills.Entity.ImmiCandidateFormsStatusEntity;
import com.tekskills.Entity.ImmiCheckListCommentsHistory;
import com.tekskills.Entity.ImmiDocumentSubTypeEntity;
import com.tekskills.Entity.ImmiDocumentTypeEntity;
import com.tekskills.Entity.ImmiFormsCommentsHistoryEntity;
import com.tekskills.Entity.ImmiProfileLableMaster;
import com.tekskills.Entity.ImmiProfileMasterCheckList;
import com.tekskills.Entity.PublicBenefitsChecklistEntity;
import com.tekskills.Entity.SOCcodeMasterEntity;
import com.tekskills.Entity.TaskDetailsEntity;

public interface H1BDao {

	List<ImmiProfileLableMaster> getInitiateTemplateList();

	List<ImmiDocumentTypeEntity> getAllDocuments();

	List<DocumentsDTO> getSubDocumentList();

	String checkimmiprofilemaster(String profilename);

	List<ImmiDocumentSubTypeEntity> getSubDocumentsByDocid(Integer doc_id);

	void saveImmiTemplateMaster(ImmiProfileMasterCheckList profileMasterObj);

	List<DocumentsDTO> getInitiatedTemplates();

	String getProfileNameById(Integer profile_Id);

	List<ImmiProfileMasterCheckList> getProfileMasterListById(Integer profile_Id);

	List<ProfileMasterDTO> getProfileListByProfileId(Integer profile_Id);

	List<ImmiProfileMasterCheckList> getProfileListByProfileSubcatId(Integer profileid, Integer subcatid);

	List<Integer> getSubCategoiesByProfileId(Integer profileid);

	void deleteMasterListById(Integer profileid, Integer scatid);

	Integer getProfileTemplateIdByName(String h1initVal);

	Integer saveInitiateDetails(CandidateH1BInitiateDetailsEntity initiateObj);

	Integer saveCandidateH1BCheckList(CandidateH1BCheckList candchecklistObj);

	List<String> getDocumentNameByProfileId(Integer profileid);

	Integer getInitiateDetailsTopRow(Integer candidate_id);

	CandidateH1BInitiateDetailsEntity getInitiateDetailsByInitiateId(Integer initiateId);

	List<ContractsPanelDetails_DTO> getimmiDocsAdminList(Integer initiateId, int profile_id, Integer candidate_id);

	List<ContractsPanelDetails_DTO> getimmiDocsCandidateList(Integer initiateId, int profile_id, Integer candidate_id);

	ImmiCandidateFormsStatusEntity getFormStatusByType(Integer candidate_id, String string);

	String getinitiateTopName(Integer candidate_id);

	List<ImmicommentsDTO> viewCommentHistoryBycandId(Integer cand_Id, String form_type);

	void updateFormsStatus(ImmiCandidateFormsStatusEntity formsobj);

	PublicBenefitsChecklistEntity getPublicBenefitsApplicantTypeCanId(Integer candidateid, String beneficiaryType);

	Integer savepublicbenefits(PublicBenefitsChecklistEntity childObj);

	Integer saveImmiFormsStatus(ImmiCandidateFormsStatusEntity formStatus);

	List<PublicBenefitsChecklistEntity> getPublicBenefitsSizeByCanId(Integer candidateid);

	void updatepublicbenefits(PublicBenefitsChecklistEntity spouseObj);

	ImmiCandidateFormsStatusEntity getFormsSatusByCandidateId(Integer candidateId, String form_type);

	void saveImmiComments(ImmiFormsCommentsHistoryEntity cmntHistory);

	H1BDetailsDto getLcaTopRecord();

	Integer saveH1BDetails(H1BDetailsEntity h1oBj);


	Integer saveH1bQuestionnaries(H1BQuestionnariesEntity h1bqueApplObj);

	void saveH1bEntryExitDetails(EntryandExitDatesEntity h1bObj);

	Integer saveClientVendorLetterQuestionnaire(ClientVendorQuestionnarieEntity cvlqueObj);

	void saveClientVendorLetterQuestionnaireAddress(ClientVendorQuestionnarieAddressEntity cvlAddressObj);

	void saveFormsStatus(ImmiCandidateFormsStatusEntity formStatusObj);

	ClientVendorQuestionnarieEntity getClientVendorLetterQuestionarieById(Integer candidateid);

	List<ClientVendorQuestionnarieAddressEntity> getClientVendorLetterQuestionarieAddressesById(Integer candidateid, Integer clientid);

	ClientVendorQuestionnarieAddressEntity getClientVendorQuestionnarieAddressById(Integer otheraddress_id);

	void updateClientVendorLetterQuestionnaireAddress(ClientVendorQuestionnarieAddressEntity cvlAddressObj);

	ClientVendorQuestionnarieEntity getClientVendorQuestionnarieById(Integer clientid);

	void updateClientVendorLetterQuestionnaire(ClientVendorQuestionnarieEntity cvlqueObj);

	H1BQuestionnariesEntity getH1BQuestionnariesByType(Integer candidateid, String beneficiaryType);

	List<H1BQuestionnariesEntity> getH1BQuestionnariesByChild(Integer candidateid, String beneficiaryType);

	List<EntryandExitDatesEntity> getentryexitList(Integer candidateid, String beneficiaryType);

	H1BQuestionnariesEntity getH1BQuestionnarieDetailsById(Integer applh1queid);

	void updateH1bQuestionnaries(H1BQuestionnariesEntity h1bqueApplObj);

	EntryandExitDatesEntity getEntryandExitDatesDetailsById(Integer applEntryid);

	void updateH1bEntryExitDetails(EntryandExitDatesEntity h1bObj);

	List<EntryandExitDatesEntity> getentryexitListById(Integer h1queid);

	H1BDetailsEntity getH1BDetailsById(Integer h1b_id);
	
	List<DocumentTypeDto> getimmiSubDocumentByDocTypeId(Integer candidate_id, Integer doctypeid, Integer intiate_id);

	Integer getCheckListIdByids(Integer candidate_id, Integer doctypeid, Integer intiate_id,
			Integer documentsubtypeid, Integer profile_id, String bentype, Integer questionnarieId);

	CandidateH1BInitiateDetailsEntity getInitiateDetailsByCandidateId(Integer candidateid);

	List<PublicBenefitsChecklistEntity> getPublicBenefitsKidsApplicantTypeCanId(Integer candidateid, String beneficiaryType);

	PublicBenefitsChecklistEntity getPublicBenefitsApplicantTypeBenifitId(Integer benfitid);

	List<PublicBenefitsChecklistDTO> getPublicBenefitByCanId(Integer candidateid);

	CandidateH1BInitiateDetailsEntity getCandidateH1BInitiateDetailsById(Integer candidate_id);

	Integer saveImmiGroupingProfileName(ImmiProfileLableMaster lableObj);

	ImmiProfileLableMaster getImmiProfileLableMasterById(Integer profileId);

	void updateImmiprofileName(ImmiProfileLableMaster lableObj);
	
	
	List<EmpDocumentsEntity> getallinputvals(Integer intiate_id, Integer candidate_id, Integer document_id, Integer h1bchecklistid);

	List<EmpDocumentsEntity> viewfilehistory(Integer candidate_id, Integer document_id, Integer subdocument_id,
			Integer initiate_id, Integer checklist_id);

	List<ContractsPanelDetails_DTO> getSubmittedSubdocsbyIds(Integer candidate_id, Integer documentid,
			Integer intiate_id, String benficiary_type, Integer profileid, Integer questionnarieId);

	List<DocumentsDTO> getdocumentshistory(Integer docid);

	List<ContractsPanelDetails_DTO> getimmisubdocumentBydocid(Integer candidate_id, Integer documentid,
			Integer intiate_id, String benficiary_type, Integer profileid, Integer questionnarieId);
	
	List<DocumentHistoryDto> getCandidateDocumentsByIds(Integer docTypeId, Integer candidate_Id,
			Integer initiateid, Integer profile_id, String profile_type);

	List<CandidateH1BCheckList> getCandidateH1BCheckListByIds(Integer docType_Id, Integer candidate_Id,
			Integer initiate_id, Integer profile_id, String profile_type);

	CandidateH1BCheckList getCandidateH1BCheckListById(Integer chklistid);

	Integer saveImmiCheckListComments(ImmiCheckListCommentsHistory commentObj);

	void updateCandidateH1BCheckList(CandidateH1BCheckList h1bChkObj);

	List<commentHistoryDto> getImmiCandidateDocumentsByIds(Integer docType_id, Integer candidate_id,
			Integer initiate_id, Integer profile_id, String profile_type);

	List<ContractsPanelDetails_DTO> getCandidateDocumentTypeList(Integer initiateId, int profile_id,
			Integer candidate_id, String profiletype);

	List<ContractsPanelDetails_DTO> getAdminDocumentTypeList(Integer initiateId, int profile_id, Integer candidate_id);

	/*List<ContractsPanelDetails_DTO> getCandidateSpouseDocumentTypeList(Integer initiateId, Integer dependent_profile_id,
			Integer candidate_id);*/

	List<ProfileMasterDTO> getProfileListByDependentProfileId(Integer dependentprofileid);

	List<ImmiProfileLableMaster> getDependentInitiateTemplateList();

	String getProfileIsDependentById(Integer profile_Id);

	List<Integer> getCanSubDocumentIdCheckList(Integer candidate_id, Integer documentid, Integer intiate_id, String benficiary_type);

	List<Integer> getSubDocumentIds(Integer documentid);

	ImmiDocumentSubTypeEntity getSubDocumentNameBysubdoctypeId(Integer subid);

	void updateInitiateDetails(CandidateH1BInitiateDetailsEntity initiateDetObj);

	Integer getSubDocumentTypeId(Integer doc_Id);

	ClientVendorQuestionnarieAddressEntity getClientVendorQuestionnarieByClientIdType(int client_id, String clienttype);

	List<CandidateH1BCheckList> getCandidateH1BCheckList(Integer initiateid, Integer spouse_profile_id,
			int candidate_id, String ptype);

	SOCcodeMasterEntity getSocCodeDetailsBySocid(String soc);

	List<BasicDetailsDto> getActiveEmployeeList();

	SOCcodeMasterEntity getSOCDetailsById(Integer soc_id);

	List<SOCcodeMasterEntity> getSocCodeMasterList();

	Integer saveSocCodeMaster(SOCcodeMasterEntity socObj);

	String checkSocCode(String soc_code);

	void updateSocCodeMaster(SOCcodeMasterEntity socObj);
	List<ContractsPanelDetails_DTO> h1initiationhistory(Integer candidate_id);

	List<String> checkpetitionPacket(Integer initiateid, Integer candidate_id);

	List<CandidateH1BCheckList> getpetitionPacketDocuments(Integer initiate_id, Integer candidate_id);


	List<TaskDetailsEntity> getalltaskdetailsbycandidateid(Integer candidate_Id);

	List<ImmiDocumentSubTypeEntity> getSubDocumentsByDocidSubmittedByAdmin(Integer documentid);

	String getdocumentTypeByDocId(Integer documenttype_id);

	List<CandidateH1BCheckList> getdocumentsByCategoryId(Integer initiate_id, Integer candidate_id,
			Integer documenttype_id);
	List<String> getDocumentByChecklistId(Integer checklistid);

	List<DocumentHistoryDto> getsclist(Integer candId, Integer initiate_id);

	void saveScanneddoc(CandidateScannedDocumentsEntity scdoc);

	List<String> getScannedDocuments(Integer initiate_id, Integer candidate_id);

	List<Integer> getCheckListIdsByIds(Integer candidate_id, Integer doctypeid, Integer intiate_id,
			Integer profile_id, String bentype);

	String checkSelectedFileNameById(String filename, Integer candidate_id);

	void updateH1BDetails(H1BDetailsEntity h1bObj);

	Integer getDocCountByH1bChkId(Integer chklistid);
}
