package com.tekskills.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tekskills.Dao.EmployeesDao;
import com.tekskills.Dao.H1BDao;
import com.tekskills.Dao.ImmigrationDao;
import com.tekskills.Dto.BasicDetailsDto;
import com.tekskills.Dto.CandidateH1BInitiateDetailsDto;
import com.tekskills.Dto.ClientVendorLetterQueDto;
import com.tekskills.Dto.ContractsPanelDetails_DTO;
import com.tekskills.Dto.DocumentHistoryDto;
import com.tekskills.Dto.DocumentTypeDto;
import com.tekskills.Dto.DocumentsDTO;
import com.tekskills.Dto.EndClientDetialDto;
import com.tekskills.Dto.ImmicommentsDTO;
import com.tekskills.Dto.PAFDetailsDto;
import com.tekskills.Dto.ProfileMasterDTO;
import com.tekskills.Dto.PublicBenefitsChecklistDTO;
import com.tekskills.Dto.commentHistoryDto;
import com.tekskills.Entity.CandidateH1BCheckList;
import com.tekskills.Entity.CandidateH1BInitiateDetailsEntity;
import com.tekskills.Entity.CandidateScannedDocumentsEntity;
import com.tekskills.Entity.ClientDetailsEntity;
import com.tekskills.Entity.ClientVendorQuestionnarieAddressEntity;
import com.tekskills.Entity.ClientVendorQuestionnarieEntity;
import com.tekskills.Entity.CompanyAdressEntity;
import com.tekskills.Entity.EmpDocumentsEntity;
import com.tekskills.Entity.EmpDocumentsHistoryEntity;
import com.tekskills.Entity.EmpPayRateDetails;
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
import com.tekskills.Entity.LCADetailsEntity;
import com.tekskills.Entity.PublicBenefitsChecklistEntity;
import com.tekskills.Entity.SOCcodeMasterEntity;
import com.tekskills.Entity.TaskDetailsEntity;
import com.tekskills.Service.H1BService;
import com.tekskills.Util.Commons;


@Service
public class H1BServiceImpl implements H1BService {
	private static final Logger logger = Logger.getLogger(H1BServiceImpl.class);
	@Autowired
	ImmigrationDao immiDao;
	@Autowired
	H1BDao h1bDao;
	@Autowired
	Commons commonObj;
	@Autowired
	EmployeesDao empDao;
	
	@Override
	public List<ImmiProfileLableMaster> getInitiateTemplateList() {
		List<ImmiProfileLableMaster> profileList = null;
		try {
			profileList = h1bDao.getInitiateTemplateList();
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return profileList;	
	}
	@Override
	public List<ImmiDocumentTypeEntity> getAllDocuments() {
		List<ImmiDocumentTypeEntity> docList = null;
		try {
			docList = h1bDao.getAllDocuments();
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return docList;	
	}
	@Override
	public List<DocumentsDTO> getSubDocumentList() {
		List<DocumentsDTO> subdocList = null;
		try {
			subdocList = h1bDao.getSubDocumentList();
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return subdocList;	
	}
	@Override
	public String checkimmiprofilemaster(String profilename) {
		String profile = null;
		try {
			/*Integer profile_id=0;
			if (profilename!="") {
				profile_id = Integer.parseInt(profilename);
			}*/
			profile = h1bDao.checkimmiprofilemaster(profilename);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return profile;
	}
	@Override
	public List<ImmiDocumentSubTypeEntity> getSubDocumentsByDocid(String docuid) {
		List<ImmiDocumentSubTypeEntity> subcatList = null;
		try {
			Integer doc_id=0;
			if (docuid!="") {
				doc_id = Integer.parseInt(docuid);
			}
			subcatList = h1bDao.getSubDocumentsByDocid(doc_id);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return subcatList;
	}
	@Override
	public String saveImmiTemplateMaster(Integer lableId, String subcategoryId, Integer lgempno) {
		String msg=null;;
		try {
			ImmiProfileMasterCheckList profileMasterObj=new ImmiProfileMasterCheckList();
			
			profileMasterObj.setPosted_by(lgempno);
			profileMasterObj.setSubmitted_date(new Date());
			profileMasterObj.setProfile_id(lableId);
			profileMasterObj.setSubcategory_id(Integer.parseInt(subcategoryId));
			h1bDao.saveImmiTemplateMaster(profileMasterObj);
			
			msg="Documents Added Successfully";
			}
		 catch (Exception e) {
			logger.info(e.getMessage(),e);		
			}
	return msg;
	}
	@Override
	public List<DocumentsDTO> getInitiatedTemplates() {
		List<DocumentsDTO> profileList = null;
		try {
			profileList = h1bDao.getInitiatedTemplates();
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return profileList;	
	}
	@Override
	public String getProfileNameById(String profileid) {
		String profilename = null;
		try {
			Integer profile_Id=0;
			if (profileid!="") {
				profile_Id = Integer.parseInt(profileid);
			}
			profilename = h1bDao.getProfileNameById(profile_Id);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return profilename;
	}
	
	@Override
	public String getProfileIsDependentById(String profileid) {
		String isdependent = null;
		try {
			Integer profile_Id=0;
			if (profileid!="") {
				profile_Id = Integer.parseInt(profileid);
			}
			isdependent = h1bDao.getProfileIsDependentById(profile_Id);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return isdependent;
	}
	
	@Override
	public List<ImmiProfileMasterCheckList> getProfileMasterListById(String profileid) {
		List<ImmiProfileMasterCheckList> masterList = null;
		try {
			Integer profile_Id=0;
			if (profileid!="") {
				profile_Id = Integer.parseInt(profileid);
			}
			masterList = h1bDao.getProfileMasterListById(profile_Id);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return masterList;
	}
	@Override
	public List<ProfileMasterDTO> getProfileListByProfileId(String profileid) {
		List<ProfileMasterDTO> profileList = null;
		try {
			Integer profile_Id=0;
			if (profileid!="") {
				profile_Id = Integer.parseInt(profileid);
			}
			profileList = h1bDao.getProfileListByProfileId(profile_Id);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return profileList;	
	}
	@Override
	public String updateprofilemaster(Integer profileid, String subcategoryId, Integer lgempno) {
		String msg=null;;
		try {Integer subcatid=0;
			if(subcategoryId!="") {
				subcatid=Integer.parseInt(subcategoryId);	
			}
			
			List<ImmiProfileMasterCheckList> profilelist=h1bDao.getProfileListByProfileSubcatId(profileid,subcatid);
			if(profilelist.size()==0) {
				ImmiProfileMasterCheckList profileMasterObj=new ImmiProfileMasterCheckList();
				profileMasterObj.setPosted_by(lgempno);
				profileMasterObj.setSubmitted_date(new Date());
				profileMasterObj.setProfile_id(profileid);
				profileMasterObj.setSubcategory_id(Integer.parseInt(subcategoryId));
				h1bDao.saveImmiTemplateMaster(profileMasterObj);
		
			}
			msg="Template Updated Successfully";
			
			}
		 catch (Exception e) {
			logger.info(e.getMessage(),e);		
			}
	return msg;
	}
	@Override
	public List<Integer> getSubCategoriesByProfileId(String profile_id) {
		List<Integer> mastersubList = null;
		try {
			Integer profileid =0; if(profile_id!="") { profileid = Integer.parseInt(profile_id); }
			 mastersubList = h1bDao.getSubCategoiesByProfileId(profileid);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return mastersubList;	
	}
	@Override
	public void deleteMasterListById(Integer profileid, Integer scatid) {
	try {
			
		h1bDao.deleteMasterListById(profileid, scatid);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return;
	}
	@Override
	public ContractsPanelDetails_DTO getimmidocumentlist(String candidateid) {
		ContractsPanelDetails_DTO docprofileList=new ContractsPanelDetails_DTO();
		try {
			Integer candidate_id=0;
			if(candidateid!="") {
				candidate_id=Integer.parseInt(candidateid);	
			}
			Integer initiateId=h1bDao.getInitiateDetailsTopRow(candidate_id);
			if(initiateId!=null) {
			CandidateH1BInitiateDetailsEntity initiateObj=h1bDao.getInitiateDetailsByInitiateId(initiateId);
			
			List<ContractsPanelDetails_DTO> adminList = h1bDao.getimmiDocsAdminList(initiateId, initiateObj.getProfile_id(),candidate_id);
			//List<ContractsPanelDetails_DTO> candidateList = h1bDao.getimmiDocsCandidateList(initiateId, initiateObj.getProfile_id(),candidate_id);
			
			List<ContractsPanelDetails_DTO> candidateList = h1bDao.getimmiDocsCandidateList(initiateId, initiateObj.getProfile_id(),candidate_id);
			CandidateH1BInitiateDetailsEntity iniateObj=h1bDao.getInitiateDetailsByCandidateId(candidate_id);
			
			
						
			ImmiCandidateFormsStatusEntity clientQuestionnairObj=h1bDao.getFormStatusByType(candidate_id,"Client Vendor Questionnaire Letter");
			ImmiCandidateFormsStatusEntity h1BQuestionnariesObj=h1bDao.getFormStatusByType(candidate_id,"H1B Questionnaries");
			ImmiCandidateFormsStatusEntity pbcObj=h1bDao.getFormStatusByType(candidate_id,"Public Benefits Checklist");
			
			List<ContractsPanelDetails_DTO> candidatedocTypeList = h1bDao.getCandidateDocumentTypeList(initiateId, initiateObj.getProfile_id(),candidate_id,"Applicant");
			
			if(initiateObj.getFilingforspouse()!=null) {
			if(initiateObj.getFilingforspouse().equalsIgnoreCase("Yes")) {
				List<ContractsPanelDetails_DTO> candidateSpouseList = h1bDao.getCandidateDocumentTypeList(initiateId, initiateObj.getSpouse_profile_id(),candidate_id,"Spouse");
				docprofileList.setCandidateSpouseList(candidateSpouseList);
			  }
			/*  if(initiateObj.getFilingspouse_ead().equalsIgnoreCase("Yes")) {
		     	List<ContractsPanelDetails_DTO> candidateSpouseEADList = h1bDao.getCandidateDocumentTypeList(initiateId, initiateObj.getEad_profile_id(),candidate_id,"Spouse");
			    docprofileList.setCandidateSpouseEadList(candidateSpouseEADList);
			   }*/
			}
			if(initiateObj.getFilingforkid()!=null) {
			if(initiateObj.getFilingforkid().equalsIgnoreCase("Yes")) {
				List<ContractsPanelDetails_DTO> candidateKidsList = h1bDao.getCandidateDocumentTypeList(initiateId, initiateObj.getKids_profile_id(),candidate_id,"Kids");
				docprofileList.setCandidateKidsList(candidateKidsList);
			   }
			 }
			
			List<ContractsPanelDetails_DTO> admindocTypeList = h1bDao.getAdminDocumentTypeList(initiateId, initiateObj.getProfile_id(),candidate_id);
			List<ContractsPanelDetails_DTO> initiatedList = h1bDao.h1initiationhistory(candidate_id);
			
			docprofileList.setInitiateObj(iniateObj);
			docprofileList.setAdminList(adminList);
			docprofileList.setCandidateList(candidateList);
			docprofileList.setClientObj(clientQuestionnairObj);
			docprofileList.setH1bObj(h1BQuestionnariesObj);
			docprofileList.setPbcObj(pbcObj);
			docprofileList.setAdmindocTypeList(admindocTypeList);
			docprofileList.setCandidatedocTypeList(candidatedocTypeList);
			docprofileList.setInitiatedList(initiatedList);
			}
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return docprofileList;	
		
		
	}
	@Override
	public CandidateH1BInitiateDetailsDto getinitiatebycandidate(String candidateid) {
		CandidateH1BInitiateDetailsDto initateDto=new CandidateH1BInitiateDetailsDto();
		try {
			Integer candidate_id=0;
			if(candidateid!="") {
				candidate_id=Integer.parseInt(candidateid);	
			}
			//CandidateH1BInitiateDetailsDto candidateinitateDto=h1bDao.getCandidateH1BInitiateDetailsById(candidate_id);
			CandidateH1BInitiateDetailsEntity initateObj=h1bDao.getCandidateH1BInitiateDetailsById(candidate_id);
			if(initateObj!=null) {
				String profileName=h1bDao.getProfileNameById(initateObj.getProfile_id());
				initateDto.setProfile_name(profileName);
				initateDto.setInitateInfo(initateObj);
			}
			
			List<ImmiProfileLableMaster> initiateList = h1bDao.getInitiateTemplateList();
			List<ImmiProfileLableMaster> dependentinitiateList = h1bDao.getDependentInitiateTemplateList();
			initateDto.setProfileList(initiateList);
			initateDto.setDependentprofileList(dependentinitiateList);
			
			} catch (Exception e) {
				logger.info(e.getMessage(),e);
			}
			return initateDto;
	}
	
	//By Purna
	
	@Override
	public Integer savebenefits(String applicantincome, String spouseincome, String ssi, String spouse_ssi, String tanf,
			String spouse_tanf, String ga, String spouse_ga, String snap, String spouse_snap, String housing_assistance,
			String spouse_housing_assistance, String project_rental_assistance, String spouse_project_rental_assistance,
			String public_housing_act, String spouse_public_housing_act, String ffm_medicalemergency,
			String spouse_ffm_medicalemergency, String ffm_ideact, String spouse_ffm_ideact, String ffm_schoolbenefits,
			String spouse_ffm_schoolbenefits, String ffm_uderage21, String spouse_ffm_uderage21,
			String ffm_pregnant_period60, String spouse_ffm_pregnant_period60, Integer candidateid) {
		
		Integer savebenefits=0;
		try {
			PublicBenefitsChecklistEntity applicantObj=new PublicBenefitsChecklistEntity();
			PublicBenefitsChecklistEntity spouseObj=new PublicBenefitsChecklistEntity();
			
			Date date = new Date(); 
			
			applicantObj.setBeneficiary_type("Applicant");
			applicantObj.setCandidate_id(candidateid);
			applicantObj.setIncome_maintenance(applicantincome);
			applicantObj.setSsi(ssi);
			applicantObj.setTanf(tanf);
			applicantObj.setGa(ga);
			applicantObj.setSnap(snap);
			applicantObj.setHousing_assistance(housing_assistance);
			applicantObj.setProject_rental_assistance(project_rental_assistance);
			applicantObj.setPublic_housing_act(public_housing_act);
			applicantObj.setFfm_medicalemergency(ffm_medicalemergency);
			applicantObj.setFfm_ideact(ffm_ideact);
			applicantObj.setFfm_schoolbenefits(ffm_schoolbenefits);
			applicantObj.setFfm_uderage21(ffm_uderage21);
			applicantObj.setFfm_pregnant_period60(ffm_pregnant_period60);
			applicantObj.setSubmitted_on(date);
			//applicantObj.setPosted_by(empno);
			savebenefits=h1bDao.savepublicbenefits(applicantObj);
			
			
			if(spouseincome!=null) {
				spouseObj.setBeneficiary_type("Spouse");
				spouseObj.setCandidate_id(candidateid);
				spouseObj.setIncome_maintenance(spouseincome);
				spouseObj.setSsi(spouse_ssi);
				spouseObj.setTanf(spouse_tanf);
				spouseObj.setGa(spouse_ga);
				spouseObj.setSnap(spouse_snap);
				spouseObj.setHousing_assistance(spouse_housing_assistance);
				spouseObj.setProject_rental_assistance(spouse_project_rental_assistance);
				spouseObj.setPublic_housing_act(spouse_public_housing_act);
				spouseObj.setFfm_medicalemergency(spouse_ffm_medicalemergency);
				spouseObj.setFfm_ideact(spouse_ffm_ideact);
				spouseObj.setFfm_schoolbenefits(spouse_ffm_schoolbenefits);
				spouseObj.setFfm_uderage21(spouse_ffm_uderage21);
				spouseObj.setFfm_pregnant_period60(spouse_ffm_pregnant_period60);
				spouseObj.setSubmitted_on(date);
				//spouseObj.setPosted_by(empno);
				savebenefits=h1bDao.savepublicbenefits(spouseObj);
			}
			/*PublicBenefitsChecklistDTO pbcdto=new PublicBenefitsChecklistDTO();
			pbcdto.setApplicantObj(applicantObj);
			pbcdto.setSpouseObj(spouseObj);
			pbcdto.setChildObj(childObj);*/
			
			ImmiCandidateFormsStatusEntity formStatus=new ImmiCandidateFormsStatusEntity();
			formStatus.setCandidate_id(candidateid);
			formStatus.setCandidate_status("Submitted");
			formStatus.setCandidate_submitted_on(new Date());
			formStatus.setForm_type("Public Benefits Checklist");
			formStatus.setVerifier_status("Pending");
			Integer statusid=h1bDao.saveImmiFormsStatus(formStatus);
			
		}
		catch(Exception e) {
			logger.error("Error in H1BService implementation " + e.getMessage());
		}
	
		return savebenefits;
	}
	@Override
	public List<PublicBenefitsChecklistEntity> getPublicBenefitsSizeByCanId(Integer candidateid) {
		List<PublicBenefitsChecklistEntity> pbcsizelist = null;
		try {
			pbcsizelist = h1bDao.getPublicBenefitsSizeByCanId(candidateid);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return pbcsizelist;
	}
	@Override
	public PublicBenefitsChecklistDTO PublicBenefitsByCanId(Integer candidateid) {
		PublicBenefitsChecklistDTO pbcDto = new PublicBenefitsChecklistDTO();
		try {
			PublicBenefitsChecklistEntity applicantObj=h1bDao.getPublicBenefitsApplicantTypeCanId(candidateid,"Applicant");
			PublicBenefitsChecklistEntity SpouseObj=h1bDao.getPublicBenefitsApplicantTypeCanId(candidateid,"Spouse");
			List<PublicBenefitsChecklistEntity> benfitschildList=h1bDao.getPublicBenefitsKidsApplicantTypeCanId(candidateid,"Kids");
			
			pbcDto.setApplicantObj(applicantObj);
			pbcDto.setSpouseObj(SpouseObj);
			pbcDto.setBenfitschildList(benfitschildList);
			
			CandidateH1BInitiateDetailsEntity iniateObj=h1bDao.getInitiateDetailsByCandidateId(candidateid);
			pbcDto.setInitiateObj(iniateObj);

			H1BQuestionnariesEntity h1bApplObj=h1bDao.getH1BQuestionnariesByType(candidateid,"Applicant");
			H1BQuestionnariesEntity h4spouseObj=h1bDao.getH1BQuestionnariesByType(candidateid,"Spouse");
			pbcDto.setH1bApplObj(h1bApplObj);
			if(h4spouseObj!=null) {
			pbcDto.setH4spouseObj(h4spouseObj);
			}
			if(iniateObj.getKids_status()!=null) {
			if(iniateObj.getKids_status().equalsIgnoreCase("Yes")) {
				List<H1BQuestionnariesEntity> childList=h1bDao.getH1BQuestionnariesByChild(candidateid,"Kids");
				pbcDto.setChildList(childList);
			   }
			}
			List<PublicBenefitsChecklistDTO> allbenfitsList=h1bDao.getPublicBenefitByCanId(candidateid);
			pbcDto.setAllbenfitsList(allbenfitsList);
			ImmiCandidateFormsStatusEntity clientQuestionnairObj=h1bDao.getFormStatusByType(candidateid,"Public Benefits Checklist");
			pbcDto.setFormObj(clientQuestionnairObj);
			
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return pbcDto;
	}
	/*	@Override
   public PublicBenefitsChecklistDTO updatebenefits(String applicantincome, String spouseincome, String kidsincome, String ssi,
			String spouse_ssi, String kids_ssi, String tanf, String spouse_tanf, String kids_tanf, String ga,
			String spouse_ga, String kids_ga, String snap, String spouse_snap, String kids_snap,
			String housing_assistance, String spouse_housing_assistance, String kids_housing_assistance,
			String project_rental_assistance, String spouse_project_rental_assistance,
			String kids_project_rental_assistance, String public_housing_act, String spouse_public_housing_act,
			String kids_public_housing_act, String ffm_medicalemergency, String spouse_ffm_medicalemergency,
			String kids_ffm_medicalemergency, String ffm_ideact, String spouse_ffm_ideact, String kids_ffm_ideact,
			String ffm_schoolbenefits, String spouse_ffm_schoolbenefits, String kids_ffm_schoolbenefits,
			String ffm_uderage21, String spouse_ffm_uderage21, String kids_ffm_uderage21, String ffm_pregnant_period60,
			String spouse_ffm_pregnant_period60, String kids_ffm_pregnant_period60, Integer candidateid) {*/
	@Override
	public PublicBenefitsChecklistDTO updatebenefits(String applicantincome, String spouseincome, String ssi,
			String spouse_ssi, String tanf, String spouse_tanf, String ga, String spouse_ga, String snap,
			String spouse_snap, String housing_assistance, String spouse_housing_assistance,
			String project_rental_assistance, String spouse_project_rental_assistance, String public_housing_act,
			String spouse_public_housing_act, String ffm_medicalemergency, String spouse_ffm_medicalemergency,
			String ffm_ideact, String spouse_ffm_ideact, String ffm_schoolbenefits, String spouse_ffm_schoolbenefits,
			String ffm_uderage21, String spouse_ffm_uderage21, String ffm_pregnant_period60,
			String spouse_ffm_pregnant_period60, Integer candidateid) {
		//Integer updatebenefits=0;
		PublicBenefitsChecklistDTO pbcdto=new PublicBenefitsChecklistDTO();
		try {
			PublicBenefitsChecklistEntity applicantObj=h1bDao.getPublicBenefitsApplicantTypeCanId(candidateid,"Applicant");
			PublicBenefitsChecklistEntity spouseObj=h1bDao.getPublicBenefitsApplicantTypeCanId(candidateid,"Spouse");
			Date date = new Date(); 
			if(applicantObj!=null) {
			applicantObj.setBeneficiary_type("Applicant");
			applicantObj.setCandidate_id(candidateid);
			applicantObj.setIncome_maintenance(applicantincome);
			applicantObj.setSsi(ssi);
			applicantObj.setTanf(tanf);
			applicantObj.setGa(ga);
			applicantObj.setSnap(snap);
			applicantObj.setHousing_assistance(housing_assistance);
			applicantObj.setProject_rental_assistance(project_rental_assistance);
			applicantObj.setPublic_housing_act(public_housing_act);
			applicantObj.setFfm_medicalemergency(ffm_medicalemergency);
			applicantObj.setFfm_ideact(ffm_ideact);
			applicantObj.setFfm_schoolbenefits(ffm_schoolbenefits);
			applicantObj.setFfm_uderage21(ffm_uderage21);
			applicantObj.setFfm_pregnant_period60(ffm_pregnant_period60);
			//applicantObj.setSubmitted_on(date);
			//applicantObj.setPosted_by(empno);
			h1bDao.updatepublicbenefits(applicantObj);
			}
			if(spouseObj!=null) {
			spouseObj.setBeneficiary_type("Spouse");
			spouseObj.setCandidate_id(candidateid);
			spouseObj.setIncome_maintenance(spouseincome);
			spouseObj.setSsi(spouse_ssi);
			spouseObj.setTanf(spouse_tanf);
			spouseObj.setGa(spouse_ga);
			spouseObj.setSnap(spouse_snap);
			spouseObj.setHousing_assistance(spouse_housing_assistance);
			spouseObj.setProject_rental_assistance(spouse_project_rental_assistance);
			spouseObj.setPublic_housing_act(spouse_public_housing_act);
			spouseObj.setFfm_medicalemergency(spouse_ffm_medicalemergency);
			spouseObj.setFfm_ideact(spouse_ffm_ideact);
			spouseObj.setFfm_schoolbenefits(spouse_ffm_schoolbenefits);
			spouseObj.setFfm_uderage21(spouse_ffm_uderage21);
			spouseObj.setFfm_pregnant_period60(spouse_ffm_pregnant_period60);
			//spouseObj.setSubmitted_on(date);
			//spouseObj.setPosted_by(empno);
			h1bDao.updatepublicbenefits(spouseObj);
			}
		
			PublicBenefitsChecklistEntity applicantObjdb=h1bDao.getPublicBenefitsApplicantTypeCanId(candidateid,"Applicant");
			PublicBenefitsChecklistEntity spouseObjdb=h1bDao.getPublicBenefitsApplicantTypeCanId(candidateid,"Spouse");
			List<PublicBenefitsChecklistEntity> benfitschildList=h1bDao.getPublicBenefitsKidsApplicantTypeCanId(candidateid,"Kids");
			
			CandidateH1BInitiateDetailsEntity iniateObj=h1bDao.getInitiateDetailsByCandidateId(candidateid);
			pbcdto.setInitiateObj(iniateObj);
			
			H1BQuestionnariesEntity h1bApplObj=h1bDao.getH1BQuestionnariesByType(candidateid,"Applicant");
			H1BQuestionnariesEntity h4spouseObj=h1bDao.getH1BQuestionnariesByType(candidateid,"Spouse");
			pbcdto.setH1bApplObj(h1bApplObj);
			if(h4spouseObj!=null) {
				pbcdto.setH4spouseObj(h4spouseObj);
			}
			if(iniateObj.getKids_status()!=null) {
			if(iniateObj.getKids_status().equalsIgnoreCase("Yes")) {
				List<H1BQuestionnariesEntity> childList=h1bDao.getH1BQuestionnariesByChild(candidateid,"Kids");
				pbcdto.setChildList(childList);
			   }
			}
			pbcdto.setApplicantObj(applicantObjdb);
			pbcdto.setSpouseObj(spouseObjdb);
			pbcdto.setBenfitschildList(benfitschildList);
			
		}
		catch(Exception e) {
			logger.error("Error in H1BService implementation updatebenefits method " + e.getMessage());
		}
		return pbcdto;
	}
	
	@Override
	public String updateimmiformstaus(String approveStatus,Integer lgemppno,String candidate_id, String form_type, String comment,Integer logincanid) {
		String  msg="";
		try {
			Integer candidateId=0;
			if(candidate_id!="") {
				candidateId=Integer.parseInt(candidate_id);
			}
		ImmiCandidateFormsStatusEntity formsobj=h1bDao.getFormsSatusByCandidateId(candidateId,form_type);
		if(formsobj!=null) {
		formsobj.setVerifier_status(approveStatus);
		formsobj.setVerifier_by(lgemppno);
		formsobj.setVerifier_submitted_on(new Date());
		h1bDao.updateFormsStatus(formsobj);
		
		ImmiFormsCommentsHistoryEntity cmntHistory=new ImmiFormsCommentsHistoryEntity();
		cmntHistory.setCandidate_id(candidateId);
		cmntHistory.setStatus_id(formsobj.getStatus_id());
		cmntHistory.setComments(comment);
		cmntHistory.setStatus(approveStatus);
		cmntHistory.setSubmitted_by(logincanid);
		cmntHistory.setSubmitted_date(new Date());
		h1bDao.saveImmiComments(cmntHistory);
		msg="Status updated successfully";
		}else{
			msg="Status not updated";
		}
		}catch(Exception e) {
			logger.error("Error in updateimmiformstaus method " + e.getMessage());
		}
		return msg;
	}
	
	@Override
	public List<ImmicommentsDTO> viewCommentHistoryBycandId(String candId, String form_type) {
		List<ImmicommentsDTO> commentHistoryList = null;
		try {
			Integer cand_Id = 0;
			if (candId != "") {
				cand_Id = Integer.parseInt(candId);
			}
			commentHistoryList = h1bDao.viewCommentHistoryBycandId(cand_Id,  form_type);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return commentHistoryList;
	}
	
	
	@Override
	public Integer saveH1bApplDeatils(String familyName, String firstName, String middleName,
			String countryofcitizenship, String telephoneNo, String ssnNo, String i94No, String countryofbirth,
			String cityofbirth, String yearsofItExp, String highestEdu, String specialization, String officialemailid,
			String personalemailid, String passportNo, String passportissueDate, String passportexpDate, String line1,
			String line2, String zipcode, String city, String state, String permanentAddr, String immigrantstatus,
			String dob, String dateoffirstEntry, String stausoffirstEntry, String dateofrecentEntry,
			String stausofrecentEntry, String visastamping, String petitionfiled_behalf, Integer candidateid) {
		    Integer hid=0;
		try {
			    H1BQuestionnariesEntity h1bqueApplObj=new H1BQuestionnariesEntity();
			    h1bqueApplObj.setCandidate_id(candidateid);
				h1bqueApplObj.setFamily_name(familyName);
				h1bqueApplObj.setFirst_name(firstName);
				h1bqueApplObj.setMiddle_name(middleName);
				h1bqueApplObj.setCountry_citizenship(countryofcitizenship);
				h1bqueApplObj.setUs_phonenumber(telephoneNo);
				h1bqueApplObj.setI94_number(i94No);
				h1bqueApplObj.setCountry_birth(countryofbirth);
				h1bqueApplObj.setCity_birth(cityofbirth);
				h1bqueApplObj.setHighest_education(highestEdu);
				h1bqueApplObj.setSpecialization(specialization);
				h1bqueApplObj.setOfficial_email(officialemailid);
				h1bqueApplObj.setPersonal_email(personalemailid);
				h1bqueApplObj.setPassport_number(passportNo);
				h1bqueApplObj.setPassport_issuedate(passportissueDate);
				h1bqueApplObj.setPassport_expirydate(passportexpDate);
				h1bqueApplObj.setLine1(line1);
				h1bqueApplObj.setLine2(line2);
				h1bqueApplObj.setCity(city);
				h1bqueApplObj.setState(state);
				h1bqueApplObj.setZipcode(zipcode);
				h1bqueApplObj.setOutside_us_address(permanentAddr);
				h1bqueApplObj.setDob(dob);
				h1bqueApplObj.setFirstentry_dateinUS(dateoffirstEntry);
				h1bqueApplObj.setRecententry_dateinUS(dateofrecentEntry);
				h1bqueApplObj.setFirstentry_statusinUS(stausoffirstEntry);
				h1bqueApplObj.setRecententry_statusinUS(stausofrecentEntry);
				h1bqueApplObj.setConsulate_visastamping(visastamping);
				h1bqueApplObj.setPetitionfiled_behalf(petitionfiled_behalf);
				h1bqueApplObj.setApplicant_type("Applicant");
				h1bqueApplObj.setPosted_by(candidateid);
				h1bqueApplObj.setSubmitted_date(new Date());
				h1bqueApplObj.setSsn(ssnNo);
				h1bqueApplObj.setCurrent_non_immiStatus(immigrantstatus);
				h1bqueApplObj.setIt_experience(yearsofItExp);
				h1bqueApplObj.setFull_name(firstName+" "+familyName);
				h1bqueApplObj.setStatus("Active");
				hid=h1bDao.saveH1bQuestionnaries(h1bqueApplObj);
				if(hid>0) {
					ImmiCandidateFormsStatusEntity formStatusObjDb=h1bDao.getFormsSatusByCandidateId(candidateid, "H1B Questionnaries");
					if(formStatusObjDb==null) {
						ImmiCandidateFormsStatusEntity formStatusObj=new ImmiCandidateFormsStatusEntity();
						formStatusObj.setForm_type("H1B Questionnaries");
						formStatusObj.setCandidate_status("Submitted");
						formStatusObj.setCandidate_id(candidateid);
						formStatusObj.setVerifier_status("Pending");
						formStatusObj.setCandidate_submitted_on(new Date());
						h1bDao.saveFormsStatus(formStatusObj);
					  }
					PublicBenefitsChecklistEntity applPbcObj=new PublicBenefitsChecklistEntity();
					applPbcObj.setH1que_id(hid);
					applPbcObj.setBeneficiary_type("Applicant");
					applPbcObj.setCandidate_id(candidateid);
					applPbcObj.setSubmitted_on(new Date());
					h1bDao.savepublicbenefits(applPbcObj);
				  }
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return hid;
	}
	@Override
	public void saveH1bEntryExitDetails(String arrivalDate, String arrivalVisa, String departureDate,
			String departureVisa, Integer h1queid, String beneficiary_type, Integer empno) {
		try {
			EntryandExitDatesEntity h1bObj=new EntryandExitDatesEntity();
			h1bObj.setH1questionnrie_id(h1queid);
			h1bObj.setCandidate_id(empno);
			h1bObj.setArrival_date(arrivalDate);
			h1bObj.setArrival_visa(arrivalVisa);
			h1bObj.setDeparture_date(departureDate);
			h1bObj.setDeparture_visa(departureVisa);
			h1bObj.setSubmitted_on(new Date());
			h1bObj.setPosted_by(empno);
			h1bObj.setStatus("Active");
			h1bObj.setBeneficiary_type(beneficiary_type);
			h1bDao.saveH1bEntryExitDetails(h1bObj);
	} catch (Exception e) {
		logger.info(e.getMessage(),e);
	}
 }
	@Override
	public Integer saveH1bSpouseChildlDeatils(String familyName, String firstName, String middleName,
			String countryofcitizenship, String telephoneNo, String ssnNo, String i94No, String countryofbirth,
			String cityofbirth, String personalemailid, String passportNo, String passportissueDate,
			String passportexpDate, String line1, String line2, String zipcode, String city, String state,
			String permanentAddr, String immigrantstatus, String dob, String dateofrecentEntry,
			String stausofrecentEntry, Integer candidateid, String beneficiary_type) {
		 Integer hid=0;
			try {
				   System.out.println("stausofrecentEntry"+stausofrecentEntry);
				    H1BQuestionnariesEntity h1bqueApplObj=new H1BQuestionnariesEntity();
				    h1bqueApplObj.setCandidate_id(candidateid);
					h1bqueApplObj.setFamily_name(familyName);
					h1bqueApplObj.setFirst_name(firstName);
					h1bqueApplObj.setMiddle_name(middleName);
					h1bqueApplObj.setCountry_citizenship(countryofcitizenship);
					h1bqueApplObj.setUs_phonenumber(telephoneNo);
					h1bqueApplObj.setI94_number(i94No);
					h1bqueApplObj.setCountry_birth(countryofbirth);
					h1bqueApplObj.setCity_birth(cityofbirth);
					h1bqueApplObj.setPersonal_email(personalemailid);
					h1bqueApplObj.setPassport_number(passportNo);
					h1bqueApplObj.setPassport_issuedate(passportissueDate);
					h1bqueApplObj.setPassport_expirydate(passportexpDate);
					h1bqueApplObj.setLine1(line1);
					h1bqueApplObj.setLine2(line2);
					h1bqueApplObj.setCity(city);
					h1bqueApplObj.setState(state);
					h1bqueApplObj.setZipcode(zipcode);
					h1bqueApplObj.setOutside_us_address(permanentAddr);
					h1bqueApplObj.setDob(dob);
					h1bqueApplObj.setRecententry_dateinUS(dateofrecentEntry);
					h1bqueApplObj.setRecententry_statusinUS(stausofrecentEntry);
					h1bqueApplObj.setApplicant_type(beneficiary_type);
					h1bqueApplObj.setCandidate_id(candidateid);
					h1bqueApplObj.setPosted_by(candidateid);
					h1bqueApplObj.setSubmitted_date(new Date());
					h1bqueApplObj.setSsn(ssnNo);
					h1bqueApplObj.setCurrent_non_immiStatus(immigrantstatus);
					h1bqueApplObj.setFull_name(firstName+" "+familyName);
					h1bqueApplObj.setStatus("Active");
					hid=h1bDao.saveH1bQuestionnaries(h1bqueApplObj);
					ImmiCandidateFormsStatusEntity formStatusObjDb=h1bDao.getFormsSatusByCandidateId(candidateid, "H1B Questionnaries");
					if(formStatusObjDb==null) {
						ImmiCandidateFormsStatusEntity formStatusObj=new ImmiCandidateFormsStatusEntity();
						formStatusObj.setForm_type("H1B Questionnaries");
						formStatusObj.setCandidate_status("Submitted");
						formStatusObj.setCandidate_id(candidateid);
						formStatusObj.setVerifier_status("Pending");
						formStatusObj.setCandidate_submitted_on(new Date());
						h1bDao.saveFormsStatus(formStatusObj);
					  }
					
					PublicBenefitsChecklistEntity applPbcObj=new PublicBenefitsChecklistEntity();
					applPbcObj.setH1que_id(hid);
					applPbcObj.setBeneficiary_type(beneficiary_type);
					applPbcObj.setCandidate_id(candidateid);
					applPbcObj.setSubmitted_on(new Date());
					h1bDao.savepublicbenefits(applPbcObj);
					
					if(beneficiary_type.equalsIgnoreCase("Kids")) {
						CandidateH1BInitiateDetailsEntity iniateObj=h1bDao.getInitiateDetailsByCandidateId(candidateid);
					if(iniateObj.getKids_profile_id()>0) {
					System.out.println("came in Kids");
					List<ProfileMasterDTO> dependentmastercheckList=h1bDao.getProfileListByDependentProfileId(iniateObj.getKids_profile_id());
					for (ProfileMasterDTO depchecklistObj : dependentmastercheckList) {
						CandidateH1BCheckList  kidsObj=new CandidateH1BCheckList();
						kidsObj.setCandidate_id(iniateObj.getCandidate_id());
						kidsObj.setInitiate_id(iniateObj.getInitiate_id());
						kidsObj.setProfile_id(iniateObj.getKids_profile_id());
						kidsObj.setDocumenttype_id(depchecklistObj.getCategoryid());
						kidsObj.setSubdoctype_id(depchecklistObj.getSubcategoryid());
						kidsObj.setPosted_by(iniateObj.getPosted_by());
						kidsObj.setSubmitted_date(new Date());
						kidsObj.setProfile_type("Kids");
						kidsObj.setQuestionnarie_childid(hid);
						Integer h1bchecklistid=h1bDao.saveCandidateH1BCheckList(kidsObj);
					     }
					   }
					}
					
			} catch (Exception e) {
				logger.info(e.getMessage(),e);
			}
			return hid;
	}
	@Override
	public Integer saveClientVendorLetterQuestionnaire(String benef_full_name, String benef_job_title, String startdate,
			String enddate, String workinglocation, String remote_line1, String remote_city, String remote_state,
			String remote_line2, String remote_zipcode, String reportingTo, String reporting_mgr_name,
			String reporting_mgr_title, String reporting_mgr_contact, String reporting_mgr_email, String jobduties,
			Integer candidateid, String client_name, String line1, String city, String state, String line2,
			String zipcode) {
		 Integer clientid=0;
			try {
				   ClientVendorQuestionnarieEntity cvlqueObj=new ClientVendorQuestionnarieEntity();
					cvlqueObj.setBeneficiary_name(benef_full_name);
					cvlqueObj.setBeneficiary_job_title(benef_job_title);
					cvlqueObj.setStart_date(startdate);
					cvlqueObj.setEnd_date(enddate);
					cvlqueObj.setJob_duties(jobduties);
					cvlqueObj.setWorking_location(workinglocation);
					if(workinglocation!=null && workinglocation.equalsIgnoreCase("Remote/Home")) {
						cvlqueObj.setLine1(remote_line1);
						cvlqueObj.setLine2(remote_line2);
						cvlqueObj.setCity(remote_city);
						cvlqueObj.setState(remote_state);
						cvlqueObj.setZipcode(remote_zipcode);
					}
					cvlqueObj.setReporting_to(reportingTo);
					cvlqueObj.setReporting_manager_name(reporting_mgr_name);
					cvlqueObj.setReporting_manager_title(reporting_mgr_title);
					cvlqueObj.setReporting_manager_emailid(reporting_mgr_email);
					cvlqueObj.setReporting_manager_contact(reporting_mgr_contact);
					cvlqueObj.setCandidate_id(candidateid);
					cvlqueObj.setSubmittedOn(new Date());
					cvlqueObj.setStatus(1);
					clientid=h1bDao.saveClientVendorLetterQuestionnaire(cvlqueObj);
					
					ClientVendorQuestionnarieAddressEntity cvlAddressObj = new ClientVendorQuestionnarieAddressEntity();
					cvlAddressObj.setName(client_name);
					cvlAddressObj.setCompany_type("End Client");
					cvlAddressObj.setClient_id(clientid);
					cvlAddressObj.setCandidate_id(candidateid);
					cvlAddressObj.setLine1(line1);
					cvlAddressObj.setLine2(line2);
					cvlAddressObj.setCity(city);
					cvlAddressObj.setState(state);
					cvlAddressObj.setZipcode(zipcode);
					cvlAddressObj.setStatus("Active");
					cvlAddressObj.setSubmitted_on(new Date());
					h1bDao.saveClientVendorLetterQuestionnaireAddress(cvlAddressObj);
					
					
					
					if(clientid>0) {
						ImmiCandidateFormsStatusEntity formStatusObj=new ImmiCandidateFormsStatusEntity();
						formStatusObj.setForm_type("Client Vendor Questionnaire Letter");
						formStatusObj.setCandidate_status("Submitted");
						formStatusObj.setVerifier_status("Pending");
						formStatusObj.setCandidate_submitted_on(new Date());
						formStatusObj.setCandidate_id(candidateid);
						h1bDao.saveFormsStatus(formStatusObj);
					  }
			} catch (Exception e) {
				logger.info(e.getMessage(),e);
			}
			return clientid;
	}
	@Override
	public void saveClientVendorLetterQuestionnaireAddress(Integer client_id, String othr_client_name,
			String othr_client_type, Integer candidateid, String othr_line1, String othr_line2, String othr_city,
			String othr_state, String othr_zipcode) {
		try {
			ClientVendorQuestionnarieAddressEntity cvlAddressObj = new ClientVendorQuestionnarieAddressEntity();
			cvlAddressObj.setName(othr_client_name);
			cvlAddressObj.setCompany_type(othr_client_type);
			cvlAddressObj.setClient_id(client_id);
			cvlAddressObj.setCandidate_id(candidateid);
			cvlAddressObj.setLine1(othr_line1);
			cvlAddressObj.setLine2(othr_line2);
			cvlAddressObj.setCity(othr_city);
			cvlAddressObj.setState(othr_state);
			cvlAddressObj.setZipcode(othr_zipcode);
			cvlAddressObj.setStatus("Active");
			cvlAddressObj.setSubmitted_on(new Date());
			h1bDao.saveClientVendorLetterQuestionnaireAddress(cvlAddressObj);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
	}
	@Override
	public ClientVendorLetterQueDto getClientVendorLetterQuestionarieById(Integer candidateid) {
		ClientVendorLetterQueDto cvlqObj =new ClientVendorLetterQueDto();
		try {
			ClientVendorQuestionnarieEntity cvlqueObj =h1bDao.getClientVendorLetterQuestionarieById(candidateid);
			cvlqObj.setCvlqObj(cvlqueObj);
			if(cvlqueObj!=null) {
				ClientVendorQuestionnarieAddressEntity endclientObj=h1bDao.getClientVendorQuestionnarieByClientIdType(cvlqueObj.getClient_id(),"End Client");
				cvlqObj.setCvlendclientObj(endclientObj);
				List<ClientVendorQuestionnarieAddressEntity> cvladdrList = h1bDao.getClientVendorLetterQuestionarieAddressesById(candidateid,cvlqueObj.getClient_id());
				cvlqObj.setCvlAddressList(cvladdrList);
			}
			
			ImmiCandidateFormsStatusEntity clientQuestionnairObj=h1bDao.getFormStatusByType(candidateid,"Client Vendor Questionnaire Letter");
			cvlqObj.setFormObj(clientQuestionnairObj);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return cvlqObj;	
	}
	@Override
	public void updateClientVendorLetterQuestionnaireAddress(String client_name, String othrclienttype, String line1,
			String line2, String city, String state, String zipcode, Integer otheraddress_id) {
		try {
			ClientVendorQuestionnarieAddressEntity cvlAddressObj=h1bDao.getClientVendorQuestionnarieAddressById(otheraddress_id);
			if(cvlAddressObj!=null) {
				cvlAddressObj.setLine1(line1);
				cvlAddressObj.setLine2(line2);
				cvlAddressObj.setCompany_type(othrclienttype);
				cvlAddressObj.setCity(city);
				cvlAddressObj.setState(state);
				cvlAddressObj.setZipcode(zipcode);
				cvlAddressObj.setSubmitted_on(new Date());
				h1bDao.updateClientVendorLetterQuestionnaireAddress(cvlAddressObj);
			  }
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
	}
	@Override
	public void updateClientVendorLetterQuestionnaire(String benef_full_name, String benef_job_title, String startdate,
			String enddate, String workinglocation, String remote_line1, String remote_line2, String remote_city,
			String remote_state, String remote_zipcode, String reportingTo, String reporting_mgr_name,
			String reporting_mgr_title, String reporting_mgr_contact, String reporting_mgr_email, String jobduties,
			String line1, String line2, String city, String state, String zipcode, String endclient_name,
			String uaddress_id, Integer clientid) {
		try {
			ClientVendorQuestionnarieEntity cvlqueObj=h1bDao.getClientVendorQuestionnarieById(clientid);
			if(cvlqueObj!=null) {
				cvlqueObj.setBeneficiary_name(benef_full_name);
				cvlqueObj.setBeneficiary_job_title(benef_job_title);
				cvlqueObj.setStart_date(startdate);
				cvlqueObj.setEnd_date(enddate);
				cvlqueObj.setJob_duties(jobduties);
				cvlqueObj.setWorking_location(workinglocation);
				if(workinglocation!=null) {
				   if(workinglocation.equalsIgnoreCase("Remote/Home")) {
					  cvlqueObj.setLine1(remote_line1);
					  cvlqueObj.setLine2(remote_line2);
					  cvlqueObj.setCity(remote_city);
					  cvlqueObj.setState(remote_state);
					  cvlqueObj.setZipcode(remote_zipcode);
					   }else {
						     cvlqueObj.setLine1(null);
							  cvlqueObj.setLine2(null);
							  cvlqueObj.setCity(null);
							  cvlqueObj.setState(null);
							  cvlqueObj.setZipcode(null);
					   }
				 }
				cvlqueObj.setReporting_to(reportingTo);
				cvlqueObj.setReporting_manager_name(reporting_mgr_name);
				cvlqueObj.setReporting_manager_title(reporting_mgr_title);
				cvlqueObj.setReporting_manager_emailid(reporting_mgr_email);
				cvlqueObj.setReporting_manager_contact(reporting_mgr_contact);
				cvlqueObj.setSubmittedOn(new Date());
				h1bDao.updateClientVendorLetterQuestionnaire(cvlqueObj);
				
				Integer address_id = 0;
				if (uaddress_id != "") {
					address_id = Integer.parseInt(uaddress_id);
				}
				ClientVendorQuestionnarieAddressEntity cvlAddressObj=h1bDao.getClientVendorQuestionnarieAddressById(address_id);
				if(cvlAddressObj!=null) {
					cvlAddressObj.setLine1(line1);
					cvlAddressObj.setLine2(line2);
					cvlAddressObj.setCity(city);
					cvlAddressObj.setState(state);
					cvlAddressObj.setZipcode(zipcode);
					cvlAddressObj.setSubmitted_on(new Date());
					h1bDao.updateClientVendorLetterQuestionnaireAddress(cvlAddressObj);
				  }
			  }
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
	}
	@Override
	public ClientVendorLetterQueDto getAllQuestionariesByCandidateId(Integer candidateid) {
		ClientVendorLetterQueDto questionaryDto =new ClientVendorLetterQueDto();
		try {
			H1BQuestionnariesEntity h1bApplObj=h1bDao.getH1BQuestionnariesByType(candidateid,"Applicant");
			H1BQuestionnariesEntity spouseObj=h1bDao.getH1BQuestionnariesByType(candidateid,"Spouse");
			List<H1BQuestionnariesEntity> childList=h1bDao.getH1BQuestionnariesByChild(candidateid,"Kids");
			
			List<EntryandExitDatesEntity> applEntryList=h1bDao.getentryexitList(candidateid,"Applicant");
			List<EntryandExitDatesEntity> spouseEntryList=h1bDao.getentryexitList(candidateid,"Spouse");
			List<EntryandExitDatesEntity> childEntryList=h1bDao.getentryexitList(candidateid,"Kids");
			
			CandidateH1BInitiateDetailsEntity iniateObj=h1bDao.getInitiateDetailsByCandidateId(candidateid);
			questionaryDto.setInitiateObj(iniateObj);
			
			questionaryDto.setH1bApplObj(h1bApplObj);
			questionaryDto.setSpouseObj(spouseObj);
			questionaryDto.setChildList(childList);
			
			questionaryDto.setApplEntryList(applEntryList);
			questionaryDto.setSpouseEntryList(spouseEntryList);
			questionaryDto.setChildEntryList(childEntryList);
			
			ImmiCandidateFormsStatusEntity formObj=h1bDao.getFormStatusByType(candidateid,"H1B Questionnaries");
			questionaryDto.setFormObj(formObj);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return questionaryDto;	
	}
	@Override
	public void updateH1bEntryExitDetails(String uarrivalDate, String uarrivalVisa, String udepartureDate,
			String udepartureVisa, Integer applEntryid) {
		try {
			EntryandExitDatesEntity h1bObj=h1bDao.getEntryandExitDatesDetailsById(applEntryid);
			if(h1bObj!=null) {
				h1bObj.setArrival_date(uarrivalDate);
				h1bObj.setArrival_visa(uarrivalVisa);
				h1bObj.setDeparture_date(udepartureDate);
				h1bObj.setDeparture_visa(udepartureVisa);
				h1bObj.setSubmitted_on(new Date());
				h1bDao.updateH1bEntryExitDetails(h1bObj);
			  }
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
	}
	@Override
	public void updateH1bApplDeatils(String familyName, String firstName, String middleName,
			String countryofcitizenship, String telephoneNo, String ssnNo, String i94No, String countryofbirth,
			String cityofbirth, String yearsofItExp, String highestEdu, String specialization, String officialemailid,
			String personalemailid, String passportNo, String passportissueDate, String passportexpDate, String line1,
			String line2, String zipcode, String city, String state, String permanentAddr, String immigrantstatus,
			String dob, String dateoffirstEntry, String stausoffirstEntry, String dateofrecentEntry,
			String stausofrecentEntry, String visastamping, String petitionfiled_behalf, Integer candidateid,
			Integer applh1queid) {
		try {
			H1BQuestionnariesEntity h1bqueApplObj=h1bDao.getH1BQuestionnarieDetailsById(applh1queid);
			if(h1bqueApplObj!=null) {
				    h1bqueApplObj.setCandidate_id(candidateid);
					h1bqueApplObj.setFamily_name(familyName);
					h1bqueApplObj.setFirst_name(firstName);
					h1bqueApplObj.setMiddle_name(middleName);
					h1bqueApplObj.setCountry_citizenship(countryofcitizenship);
					h1bqueApplObj.setUs_phonenumber(telephoneNo);
					h1bqueApplObj.setI94_number(i94No);
					h1bqueApplObj.setCountry_birth(countryofbirth);
					h1bqueApplObj.setCity_birth(cityofbirth);
					h1bqueApplObj.setHighest_education(highestEdu);
					h1bqueApplObj.setSpecialization(specialization);
					h1bqueApplObj.setOfficial_email(officialemailid);
					h1bqueApplObj.setPersonal_email(personalemailid);
					h1bqueApplObj.setPassport_number(passportNo);
					h1bqueApplObj.setPassport_issuedate(passportissueDate);
					h1bqueApplObj.setPassport_expirydate(passportexpDate);
					h1bqueApplObj.setLine1(line1);
					h1bqueApplObj.setLine2(line2);
					h1bqueApplObj.setCity(city);
					h1bqueApplObj.setState(state);
					h1bqueApplObj.setZipcode(zipcode);
					h1bqueApplObj.setOutside_us_address(permanentAddr);
					h1bqueApplObj.setDob(dob);
					h1bqueApplObj.setFirstentry_dateinUS(dateoffirstEntry);
					h1bqueApplObj.setRecententry_dateinUS(dateofrecentEntry);
					h1bqueApplObj.setFirstentry_statusinUS(stausoffirstEntry);
					h1bqueApplObj.setRecententry_statusinUS(stausofrecentEntry);
					h1bqueApplObj.setConsulate_visastamping(visastamping);
					h1bqueApplObj.setPetitionfiled_behalf(petitionfiled_behalf);
					h1bqueApplObj.setPosted_by(candidateid);
					h1bqueApplObj.setSubmitted_date(new Date());
					h1bqueApplObj.setSsn(ssnNo);
					h1bqueApplObj.setCurrent_non_immiStatus(immigrantstatus);
					h1bqueApplObj.setIt_experience(yearsofItExp);
					h1bqueApplObj.setFull_name(firstName+" "+familyName);
					h1bqueApplObj.setStatus("Active");
					h1bDao.updateH1bQuestionnaries(h1bqueApplObj);
			  }
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
	}
	@Override
	public void updateH1bSpouseDeatils(String familyName, String firstName, String middleName,
			String countryofcitizenship, String telephoneNo, String ssnNo, String i94No, String countryofbirth,
			String cityofbirth, String personalemailid, String passportNo, String passportissueDate,
			String passportexpDate, String line1, String line2, String zipcode, String city, String state,
			String permanentAddr, String immigrantstatus, String dob, String dateofrecentEntry,
			String stausofrecentEntry, Integer candidateid, Integer applh1queid) {
		try {
			H1BQuestionnariesEntity h1bqueApplObj=h1bDao.getH1BQuestionnarieDetailsById(applh1queid);
			if(h1bqueApplObj!=null) {
				    h1bqueApplObj.setCandidate_id(candidateid);
					h1bqueApplObj.setFamily_name(familyName);
					h1bqueApplObj.setFirst_name(firstName);
					h1bqueApplObj.setMiddle_name(middleName);
					h1bqueApplObj.setCountry_citizenship(countryofcitizenship);
					h1bqueApplObj.setUs_phonenumber(telephoneNo);
					h1bqueApplObj.setI94_number(i94No);
					h1bqueApplObj.setCountry_birth(countryofbirth);
					h1bqueApplObj.setCity_birth(cityofbirth);
					h1bqueApplObj.setPersonal_email(personalemailid);
					h1bqueApplObj.setPassport_number(passportNo);
					h1bqueApplObj.setPassport_issuedate(passportissueDate);
					h1bqueApplObj.setPassport_expirydate(passportexpDate);
					h1bqueApplObj.setLine1(line1);
					h1bqueApplObj.setLine2(line2);
					h1bqueApplObj.setCity(city);
					h1bqueApplObj.setState(state);
					h1bqueApplObj.setZipcode(zipcode);
					h1bqueApplObj.setOutside_us_address(permanentAddr);
					h1bqueApplObj.setDob(dob);
					h1bqueApplObj.setRecententry_dateinUS(dateofrecentEntry);
					h1bqueApplObj.setRecententry_statusinUS(stausofrecentEntry);
					h1bqueApplObj.setPosted_by(candidateid);
					h1bqueApplObj.setSubmitted_date(new Date());
					h1bqueApplObj.setSsn(ssnNo);
					h1bqueApplObj.setCurrent_non_immiStatus(immigrantstatus);
					h1bqueApplObj.setFull_name(firstName+" "+familyName);
					h1bqueApplObj.setStatus("Active");
					h1bDao.updateH1bQuestionnaries(h1bqueApplObj);
			  }
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
	}
	@Override
	public ClientVendorLetterQueDto getchilddetailsbyid(String h1bqueid) {
		ClientVendorLetterQueDto questionaryDto =new ClientVendorLetterQueDto();
		try {
			    Integer h1queid=0;
				if(!h1bqueid.isEmpty()) {
					h1queid=Integer.parseInt(h1bqueid);
				  }
			H1BQuestionnariesEntity childObj=h1bDao.getH1BQuestionnarieDetailsById(h1queid);
			List<EntryandExitDatesEntity> childEntryList=h1bDao.getentryexitListById(h1queid);
			questionaryDto.setChildObj(childObj);
			questionaryDto.setChildEntryList(childEntryList);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return questionaryDto;	
	}
	@Override
	public String saveh1bdocumentdetails(String documentsubtype, String doctype, String candidateid,
			String intiateid, String h1RecNumber, String h1bvFromDate, String h1bvToDate,
			String i94Number, String i94vFromDate,	String i94vToDate, MultipartFile h1bdocument,
			Integer lgcandidateid, String profileid, String bentype) {
		String msg=null;;
		try {
			Integer documentsubtypeid=commonObj.ParseInt(documentsubtype);
			Integer candidate_id=commonObj.ParseInt(candidateid);
			Integer doctypeid=commonObj.ParseInt(doctype);
			Integer intiate_id=commonObj.ParseInt(intiateid);
			Integer profile_id=commonObj.ParseInt(profileid);
			
			Integer chklistid=h1bDao.getCheckListIdByids(candidate_id, doctypeid,intiate_id,documentsubtypeid,profile_id,bentype,0);
			List<ContractsPanelDetails_DTO> adminList = h1bDao.getimmiDocsAdminList(intiate_id, profile_id,candidate_id);
			String printStatus="";
			for (ContractsPanelDetails_DTO adminObj : adminList) {
				if(adminObj.getPrinted_status()!=null) {
				printStatus=adminObj.getPrinted_status();
				}
			}
			System.out.println("printStatus*********"+printStatus);
			
			H1BDetailsEntity h1bObj=new H1BDetailsEntity();
			h1bObj.setReceipt_number(h1RecNumber);
			h1bObj.setH1b_fromdate(h1bvFromDate);
			h1bObj.setH1b_todate(h1bvToDate);
			h1bObj.setI94_number(i94Number);
			h1bObj.setI94_fromdate(i94vFromDate);
			h1bObj.setI94_todate(i94vToDate);
			h1bObj.setCandidate_id(candidate_id);
			h1bObj.setPosted_by(lgcandidateid);
			h1bObj.setStatus("Active");
			h1bObj.setSubmitted_date(new Date());
			Integer h1bid=h1bDao.saveH1BDetails(h1bObj);
			if(h1bid>0) {
				 if(h1bdocument!=null) {
					 if(!h1bdocument.getOriginalFilename().isEmpty()) {
	          	     EmpDocumentsEntity docobj=new EmpDocumentsEntity();
	 			     docobj.setCandidate_id(candidate_id);
	 			     docobj.setPostedBy(lgcandidateid);
	 			     docobj.setSubmit_date(new Date());
	 			     docobj.setStatus("Active");
	 			     docobj.setFile_name(h1bdocument.getOriginalFilename());
	 			     docobj.setFiletype("H-1B");
	 			     docobj.setDocumenttype_id(doctypeid);
	 			     docobj.setDocumentsubtype_id(documentsubtypeid);
	 			     docobj.setInitiate_id(intiate_id);
	 			     docobj.setH1b_id(h1bid);
	 			     docobj.setH1b_checklist_id(chklistid);
	 			     commonObj.saveH1BfilesToDirectory(candidate_id,h1bdocument);
	 			     empDao.saveDocuments(docobj);
	                   } 
				 }
			 }
			 msg="Documents Added Successfully";
			 
			 List<Integer> h1bChkObjList = h1bDao.getCheckListIdsByIds(candidate_id, doctypeid,intiate_id,profile_id,bentype);
			 for (Integer chkid : h1bChkObjList) {
				 CandidateH1BCheckList h1bChkObj=h1bDao.getCandidateH1BCheckListById(chkid);
			     h1bChkObj.setVerifier_status("Approved");
			     if(!printStatus.isEmpty()) {
			    	 h1bChkObj.setPrinted_status(printStatus); 
			     }
				 h1bDao.updateCandidateH1BCheckList(h1bChkObj);
			  }
			 
			}
		 catch (Exception e) {
			logger.info(e.getMessage(),e);		
			}
	return msg;
	}
	
	@Override
	public String saveh1bdocumentdetails1(String documentsubtype, String doctype, String candidateid,
			String intiateid, MultipartFile h1bdocument, Integer lgempno, String profileid, String bentype) {
		String msg=null;;
		try {
			Integer documentsubtypeid=commonObj.ParseInt(documentsubtype);
			Integer candidate_id=commonObj.ParseInt(candidateid);
			Integer doctypeid=commonObj.ParseInt(doctype);
			Integer intiate_id=commonObj.ParseInt(intiateid);
			Integer profile_id=commonObj.ParseInt(profileid);
			
			Integer h1bchecklistid=h1bDao.getCheckListIdByids(candidate_id, doctypeid,intiate_id,documentsubtypeid,profile_id,bentype,0);
			List<ContractsPanelDetails_DTO> adminList = h1bDao.getimmiDocsAdminList(intiate_id, profile_id,candidate_id);
			String printStatus="";
			for (ContractsPanelDetails_DTO adminObj : adminList) {
				if(adminObj.getPrinted_status()!=null) {
				printStatus=adminObj.getPrinted_status();
				}
			}
			System.out.println("printStatus*********"+printStatus+"h1bchecklistid*********"+h1bchecklistid);
			if(h1bchecklistid!=null) {
				
			}else {
				CandidateH1BCheckList  subcatObj=new CandidateH1BCheckList();
				subcatObj.setCandidate_id(candidate_id);
				subcatObj.setInitiate_id(intiate_id);
				subcatObj.setProfile_id(profile_id);
				subcatObj.setProfile_type(bentype);
				subcatObj.setDocumenttype_id(doctypeid);
				subcatObj.setSubdoctype_id(documentsubtypeid);
				subcatObj.setPosted_by(lgempno);
				subcatObj.setSubmitted_date(new Date());
				h1bchecklistid=h1bDao.saveCandidateH1BCheckList(subcatObj);
			}
			
			if(h1bchecklistid>0) {
				 if(h1bdocument!=null) {
					 if(!h1bdocument.getOriginalFilename().isEmpty()) {
	          	     EmpDocumentsEntity docobj=new EmpDocumentsEntity();
	 			     docobj.setCandidate_id(candidate_id);
	 			     docobj.setPostedBy(lgempno);
	 			     docobj.setSubmit_date(new Date());
	 			     docobj.setStatus("Active");
	 			     docobj.setFile_name(h1bdocument.getOriginalFilename());
	 			     docobj.setFiletype("H-1B");
	 			     docobj.setDocumenttype_id(doctypeid);
	 			     docobj.setDocumentsubtype_id(documentsubtypeid);
	 			     docobj.setInitiate_id(intiate_id);
	 			     docobj.setH1b_checklist_id(h1bchecklistid);
	 			     commonObj.saveH1BfilesToDirectory(candidate_id,h1bdocument);
	 			     empDao.saveDocuments(docobj);
	                   } 
				 }
			 }
			 msg="Documents Added Successfully";
			 /*CandidateH1BCheckList h1bChkObj=h1bDao.getCandidateH1BCheckListById(h1bchecklistid);
		     h1bChkObj.setVerifier_status("Approved");
			 h1bDao.updateCandidateH1BCheckList(h1bChkObj);*/
			 List<Integer> h1bChkObjList = h1bDao.getCheckListIdsByIds(candidate_id, doctypeid,intiate_id,profile_id,bentype);
			 for (Integer chkid : h1bChkObjList) {
				 CandidateH1BCheckList h1bChkObj=h1bDao.getCandidateH1BCheckListById(chkid);
			     h1bChkObj.setVerifier_status("Approved");
			     if(!printStatus.isEmpty()) {
			    	 h1bChkObj.setPrinted_status(printStatus); 
			     }
			  //   h1bChkObj.setPrinted_status();
				 h1bDao.updateCandidateH1BCheckList(h1bChkObj);
			  }
			}
		 catch (Exception e) {
			logger.info(e.getMessage(),e);		
			}
	return msg;
	}
	
	
	/*@Override
	public ContractsPanelDetails_DTO getimmisubdocumentBydocid(String candidateid, String document_id, String intiateid) {
		ContractsPanelDetails_DTO docprofileList=new ContractsPanelDetails_DTO();
		try {
			Integer candidate_id=commonObj.ParseInt(candidateid);
			Integer doctypeid=commonObj.ParseInt(document_id);
			Integer intiate_id=commonObj.ParseInt(intiateid);
			List<DocumentTypeDto> subDocTypelist = h1bDao.getimmiSubDocumentByDocTypeId(candidate_id, doctypeid,intiate_id);
			docprofileList.setSubDocumentTypeList(subDocTypelist);
			} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return docprofileList;	
	}*/
	@Override
	public String saveH1InitiationDetails(Integer lgcandidateid, String candidateid, String h1initVal, String marital_status,
			String spousefiling, String spousefilingead, String kids_status, String kidsfiling) {
	String msg = null;
			try {
				Integer candidate_id=0;
				if (candidateid!="") {
					candidate_id = Integer.parseInt(candidateid);
				}
				Integer profileid=0;
				if(!h1initVal.isEmpty()) {
					 System.out.println("h1initVal......."+ h1initVal);
					 profileid = Integer.parseInt(h1initVal);
				}
				
				Integer spouseprofileid=0;
				Integer spouse_ead_profileid=0;
				Integer kidsprofileid=0;
				if(marital_status!=null) {
				if(marital_status.equalsIgnoreCase("Married")) {
				if(spousefiling.equalsIgnoreCase("Yes")) {
					 spouseprofileid =h1bDao.getProfileTemplateIdByName("H4");
					 if(spousefilingead.equalsIgnoreCase("Yes")) {
						 spouse_ead_profileid=h1bDao.getProfileTemplateIdByName("H4 EAD");
					  }
				  }
				if(kids_status.equalsIgnoreCase("Yes")) {
				if(kidsfiling.equalsIgnoreCase("Yes")) {
					 kidsprofileid = h1bDao.getProfileTemplateIdByName("H4");
				   }
				 }
			   }
    		}
				
				String[] h1btasks;
				//Integer profileid=h1bDao.getProfileTemplateIdByName(h1initVal);
				if(profileid>0) {
				CandidateH1BInitiateDetailsEntity initiateDetObj=h1bDao.getInitiateDetailsByCandidateId(candidate_id);
				if(initiateDetObj!=null) {
					initiateDetObj.setStatus("InActive");
					h1bDao.updateInitiateDetails(initiateDetObj);
				 }
				CandidateH1BInitiateDetailsEntity initiateObj=new CandidateH1BInitiateDetailsEntity();
				initiateObj.setCandidate_id(candidate_id);
				initiateObj.setProfile_id(profileid);
				if(marital_status!=null) {
				initiateObj.setMarital_status(marital_status);
				if(marital_status.equalsIgnoreCase("Married")) {
					initiateObj.setFilingforspouse(spousefiling);
					if(spousefiling.equalsIgnoreCase("Yes")) {
						  initiateObj.setSpouse_profile_id(spouseprofileid);
						  if(spousefilingead.equalsIgnoreCase("Yes")) {
							  initiateObj.setEad_profile_id(spouse_ead_profileid); 
						  }
						  initiateObj.setFilingspouse_ead(spousefilingead);
					}else {
						  initiateObj.setSpouse_profile_id(0);
					}
					if(kids_status.equalsIgnoreCase("Yes")) {
						initiateObj.setFilingforkid(kidsfiling);
						if(kidsfiling.equalsIgnoreCase("Yes")) {
							  initiateObj.setKids_profile_id(kidsprofileid);
						}else {
							  initiateObj.setKids_profile_id(0);
						}
					}
					initiateObj.setKids_status(kids_status);	
				  }
			   }
				initiateObj.setSubmitted_date(new Date());
				initiateObj.setPosted_by(lgcandidateid);
				initiateObj.setStatus("Active");
				Integer iniateId=h1bDao.saveInitiateDetails(initiateObj);
				if(iniateId>0) {
					// Application checklist
				List<ProfileMasterDTO> mastercheckList=h1bDao.getProfileListByProfileId(profileid);
				for (ProfileMasterDTO checklistObj : mastercheckList) {
					CandidateH1BCheckList  candchecklistObj=new CandidateH1BCheckList();
					candchecklistObj.setCandidate_id(candidate_id);
					candchecklistObj.setInitiate_id(iniateId);
					candchecklistObj.setProfile_id(profileid);
					candchecklistObj.setDocumenttype_id(checklistObj.getCategoryid());
					candchecklistObj.setSubdoctype_id(checklistObj.getSubcategoryid());
					candchecklistObj.setPosted_by(lgcandidateid);
					candchecklistObj.setProfile_type("Applicant");
					candchecklistObj.setSubmitted_date(new Date());
					
					Integer h1bchecklistid=h1bDao.saveCandidateH1BCheckList(candchecklistObj);
				   }
				//Dependent checklist
				if(spouseprofileid>0) {
					System.out.println("came in spouse H4");
				List<ProfileMasterDTO> dependentmastercheckList=h1bDao.getProfileListByDependentProfileId(spouseprofileid);
				for (ProfileMasterDTO depchecklistObj : dependentmastercheckList) {
					System.out.println("depchecklistObj.getCategoryid()"+depchecklistObj.getCategoryid()+"depchecklistObj.getSubcategoryid()"+depchecklistObj.getSubcategoryid());
					CandidateH1BCheckList  dependentcandchecklistObj=new CandidateH1BCheckList();
					dependentcandchecklistObj.setCandidate_id(candidate_id);
					dependentcandchecklistObj.setInitiate_id(iniateId);
					dependentcandchecklistObj.setProfile_id(spouseprofileid);
					dependentcandchecklistObj.setDocumenttype_id(depchecklistObj.getCategoryid());
					dependentcandchecklistObj.setSubdoctype_id(depchecklistObj.getSubcategoryid());
					dependentcandchecklistObj.setPosted_by(lgcandidateid);
					dependentcandchecklistObj.setSubmitted_date(new Date());
					dependentcandchecklistObj.setProfile_type("Spouse");
					Integer h1bchecklistid=h1bDao.saveCandidateH1BCheckList(dependentcandchecklistObj);
				     }
				   }
				
				if(spouse_ead_profileid>0) {
					System.out.println("came in Spouse EAD");
					List<ProfileMasterDTO> dependentmastercheckList=h1bDao.getProfileListByDependentProfileId(spouse_ead_profileid);
					for (ProfileMasterDTO depchecklistObj : dependentmastercheckList) {
						CandidateH1BCheckList  kidsObj=new CandidateH1BCheckList();
						kidsObj.setCandidate_id(candidate_id);
						kidsObj.setInitiate_id(iniateId);
						kidsObj.setProfile_id(spouse_ead_profileid);
						kidsObj.setDocumenttype_id(depchecklistObj.getCategoryid());
						kidsObj.setSubdoctype_id(depchecklistObj.getSubcategoryid());
						kidsObj.setPosted_by(lgcandidateid);
						kidsObj.setSubmitted_date(new Date());
						kidsObj.setProfile_type("Spouse");
						Integer h1bchecklistid=h1bDao.saveCandidateH1BCheckList(kidsObj);
					     }
					}
				}
				
				/* H1BDetailsDto h1bDtoObj=h1bDao.getLcaTopRecord();
				 Integer hibid=0;
				if(h1bDtoObj!=null) {
					 Integer referenceLca=h1bDtoObj.getH1b_reference();
					 referenceLca=referenceLca+1;
					 H1BDetailsEntity h1OBj=new H1BDetailsEntity();
					 h1OBj.setCandidate_id(candidate_id);
					 h1OBj.setPosted_by(loginempno);
					 h1OBj.setStatus("Active");
					 h1OBj.setH1b_reference(referenceLca++);
					 h1OBj.setSubmitted_date(new Date());
					 hibid=h1bDao.saveH1BDetails(h1OBj);
					 
				 }else {
					 Integer referenceLca=1;
					 H1BDetailsEntity h1OBj=new H1BDetailsEntity();
					 h1OBj.setCandidate_id(candidate_id);
					 h1OBj.setPosted_by(loginempno);
					 h1OBj.setStatus("Active");
					 h1OBj.setH1b_reference(referenceLca);
					 h1OBj.setSubmitted_date(new Date());
					 System.out.println("referenceLca......."+ referenceLca);
					 hibid=h1bDao.saveH1BDetails(h1OBj);
					 
				 }*/
				
				List<String> documentList=h1bDao.getDocumentNameByProfileId(profileid);
				if(documentList.size()>0) {
				// for(int i=0; i< documentList.length; i++){
				for (String docname : documentList) {
				      TaskDetailsEntity taskObj=new TaskDetailsEntity();
				      
				      taskObj.setTask_name(docname);
				      taskObj.setTask_description(docname);
				      taskObj.setCreated_date(new Date());
				      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				      Calendar c = Calendar.getInstance();
				      c.setTime(new Date()); // Now use today date.
				      c.add(Calendar.DATE, 7); // Adding 7 days
				      String date = sdf.format(c.getTime());
				      
				      taskObj.setDue_date(date);
				      taskObj.setTask_status("Open");
				      taskObj.setDocumenttype_name("H-1B");
				      taskObj.setPosted_by(lgcandidateid);
				      taskObj.setPayrate_id(0);
				      taskObj.setCandidate_id(candidate_id);
				      taskObj.setClient_id(0);
				      immiDao.saveTaskDetails(taskObj);
				      msg="H1B Initiated Successfully";
				    }
			}
				/* for(int i=0; i< h1btasks.length; i++){
				     
					  DocumentListMasterEntity docmasterObj=new DocumentListMasterEntity();
				      docmasterObj.setDocument_name(h1btasks[i]);
				      docmasterObj.setPosted_by(loginempno);
				      docmasterObj.setCandidate_id(candidate_id);
				      docmasterObj.setClient_id(0);
				      docmasterObj.setCandidate_status("");
				      docmasterObj.setApproval_status("");
				      immiDao.saveDocumentListMaster(docmasterObj);
				    }*/
				}
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
			}
			return msg;
		}
	
	@Override
	public String updateH1InitiationDetails(Integer lgcandidateid, String initiate_id, String marital_status,
			String spousefiling, String spousefilingead, String kids_status, String kidsfiling) {
	String msg = null;
			try {
				Integer initiateid=0;
				if (initiate_id!="") {
					initiateid = Integer.parseInt(initiate_id);
				}
				
				Integer spouseprofileid=0;
				Integer kidsprofileid=0;
				Integer spouse_ead_profileid=0;
				if(marital_status!=null) {
					if(marital_status.equalsIgnoreCase("Married")) {
					if(spousefiling.equalsIgnoreCase("Yes")) {
						 spouseprofileid =h1bDao.getProfileTemplateIdByName("H4");
						 if(spousefilingead.equalsIgnoreCase("Yes")) {
							 spouse_ead_profileid=h1bDao.getProfileTemplateIdByName("H4 EAD");
						  }
					  }
					if(kids_status.equalsIgnoreCase("Yes")) {
					if(kidsfiling.equalsIgnoreCase("Yes")) {
						 kidsprofileid = h1bDao.getProfileTemplateIdByName("H4");
					   }
					 }
				   }
	    		}
				
				
				
				/*String[] h1btasks;
				Integer profileid=h1bDao.getProfileTemplateIdByName(h1initVal);*/
			//	if(profileid>0) {
				CandidateH1BInitiateDetailsEntity initiateObj=h1bDao.getInitiateDetailsByInitiateId(initiateid);
				if(marital_status!=null) {
				initiateObj.setMarital_status(marital_status);
				if(marital_status.equalsIgnoreCase("Married")) {
					initiateObj.setFilingforspouse(spousefiling);
					if(spousefiling.equalsIgnoreCase("Yes")) {
						  initiateObj.setSpouse_profile_id(spouseprofileid);
						  if(spousefilingead.equalsIgnoreCase("Yes")) {
							  initiateObj.setEad_profile_id(spouse_ead_profileid); 
						  }
						  initiateObj.setFilingspouse_ead(spousefilingead);
					}else {
						  initiateObj.setSpouse_profile_id(0);
						  initiateObj.setEad_profile_id(0);
					}
					if(kids_status.equalsIgnoreCase("Yes")) {
						initiateObj.setFilingforkid(kidsfiling);
						if(kidsfiling.equalsIgnoreCase("Yes")) {
							  initiateObj.setKids_profile_id(kidsprofileid);
						}else {
							  initiateObj.setKids_profile_id(0);
						}
					}
					initiateObj.setKids_status(kids_status);	
				  }
			    }
				initiateObj.setSubmitted_date(new Date());
				initiateObj.setPosted_by(lgcandidateid);
				initiateObj.setStatus("Active");
				h1bDao.updateInitiateDetails(initiateObj);
				 msg="Dependant details updated Successfully";
				if(spouseprofileid>0) {
					System.out.println("came in spouse H4");
					List<CandidateH1BCheckList> candidateSpouseList = h1bDao.getCandidateH1BCheckList(initiateid, initiateObj.getSpouse_profile_id(),initiateObj.getCandidate_id(),"Spouse");
					System.out.println("Data in H4 CandidateH1BCheckList "+candidateSpouseList.size());
					if(candidateSpouseList.size()>0) {
						
					}else {
						List<ProfileMasterDTO> dependentmastercheckList=h1bDao.getProfileListByDependentProfileId(spouseprofileid);
						for (ProfileMasterDTO depchecklistObj : dependentmastercheckList) {
							System.out.println("No Data depchecklistObj.getCategoryid()"+depchecklistObj.getCategoryid()+"depchecklistObj.getSubcategoryid()"+depchecklistObj.getSubcategoryid());
							CandidateH1BCheckList  dependentcandchecklistObj=new CandidateH1BCheckList();
							dependentcandchecklistObj.setCandidate_id(initiateObj.getCandidate_id());
							dependentcandchecklistObj.setInitiate_id(initiateObj.getInitiate_id());
							dependentcandchecklistObj.setProfile_id(spouseprofileid);
							dependentcandchecklistObj.setDocumenttype_id(depchecklistObj.getCategoryid());
							dependentcandchecklistObj.setSubdoctype_id(depchecklistObj.getSubcategoryid());
							dependentcandchecklistObj.setPosted_by(lgcandidateid);
							dependentcandchecklistObj.setSubmitted_date(new Date());
							dependentcandchecklistObj.setProfile_type("Spouse");
							Integer h1bchecklistid=h1bDao.saveCandidateH1BCheckList(dependentcandchecklistObj);
						     }
					   }
				   }
				
				if(spouse_ead_profileid>0) {
					System.out.println("came in Spouse EAD");
					List<CandidateH1BCheckList> candidateSpouseList = h1bDao.getCandidateH1BCheckList(initiateid, initiateObj.getEad_profile_id(),initiateObj.getCandidate_id(),"Spouse");
					System.out.println("Data in EAD CandidateH1BCheckList "+candidateSpouseList.size());
					if(candidateSpouseList.size()>0) {
						
					}else {
					List<ProfileMasterDTO> dependentmastercheckList=h1bDao.getProfileListByDependentProfileId(spouse_ead_profileid);
					for (ProfileMasterDTO depchecklistObj : dependentmastercheckList) {
						CandidateH1BCheckList  kidsObj=new CandidateH1BCheckList();
						kidsObj.setCandidate_id(initiateObj.getCandidate_id());
						kidsObj.setInitiate_id(initiateObj.getInitiate_id());
						kidsObj.setProfile_id(spouse_ead_profileid);
						kidsObj.setDocumenttype_id(depchecklistObj.getCategoryid());
						kidsObj.setSubdoctype_id(depchecklistObj.getSubcategoryid());
						kidsObj.setPosted_by(lgcandidateid);
						kidsObj.setSubmitted_date(new Date());
						kidsObj.setProfile_type("Spouse");
						Integer h1bchecklistid=h1bDao.saveCandidateH1BCheckList(kidsObj);
					     }
				      }
					}
				
				/* if(spouseprofileid>0) {*/
				/*	if(spouseprofileid == initiateObj.getSpouse_profile_id()) {
						System.out.println("came in spouseprofileid same");
						initiateObj.setCandidate_id(initiateObj.getCandidate_id());
						initiateObj.setProfile_id(initiateObj.getProfile_id());
						initiateObj.setMarital_status(marital_status);
						if(marital_status.equalsIgnoreCase("Married")) {
							initiateObj.setFilingforspouse(spousefiling);
							if(spousefiling.equalsIgnoreCase("Yes")) {
								  initiateObj.setSpouse_profile_id(spouseprofileid);
							}else {
								  initiateObj.setSpouse_profile_id(0);
							}
							initiateObj.setKids_status(kids_status);
							if(kids_status.equalsIgnoreCase("Yes")) {
								initiateObj.setFilingforkid(kidsfiling);
								if(kidsfiling.equalsIgnoreCase("Yes")) {
									  initiateObj.setKids_profile_id(kidsprofileid);
								}else {
									  initiateObj.setKids_profile_id(0);
								}
							}
						}else {
							initiateObj.setKids_status(null);
							initiateObj.setFilingforspouse(null);
							initiateObj.setFilingforkid(null);
						}
						
						initiateObj.setSubmitted_date(new Date());
						initiateObj.setPosted_by(loginempno);
						initiateObj.setStatus("Active");
						h1bDao.updateInitiateDetails(initiateObj);
						msg="H1B Initiate Details Updated Successfully";
						
				   }else {
					    System.out.println("came in spouseprofileid not same");
					    initiateObj.setCandidate_id(initiateObj.getCandidate_id());
						initiateObj.setProfile_id(initiateObj.getProfile_id());
						initiateObj.setMarital_status(marital_status);
						if(marital_status.equalsIgnoreCase("Married")) {
							initiateObj.setFilingforspouse(spousefiling);
							if(spousefiling.equalsIgnoreCase("Yes")) {
								  initiateObj.setSpouse_profile_id(spouseprofileid);
							}else {
								  initiateObj.setSpouse_profile_id(0);
							}
							initiateObj.setKids_status(kids_status);
							if(kids_status.equalsIgnoreCase("Yes")) {
								initiateObj.setFilingforkid(kidsfiling);
								if(kidsfiling.equalsIgnoreCase("Yes")) {
									  initiateObj.setKids_profile_id(kidsprofileid);
								}else {
									  initiateObj.setKids_profile_id(0);
								}
							}
						}else {
							initiateObj.setKids_status(null);
							initiateObj.setFilingforspouse(null);
							initiateObj.setFilingforkid(null);
						}
						
						initiateObj.setSubmitted_date(new Date());
						initiateObj.setPosted_by(loginempno);
						initiateObj.setStatus("Active");
						h1bDao.updateInitiateDetails(initiateObj);
						msg="H1B Initiate Details Updated Successfully";
					    List<ProfileMasterDTO> dependentmastercheckList=h1bDao.getProfileListByDependentProfileId(spouseprofileid);
						for (ProfileMasterDTO depchecklistObj : dependentmastercheckList) {
							CandidateH1BCheckList  dependentcandchecklistObj=new CandidateH1BCheckList();
							dependentcandchecklistObj.setCandidate_id(initiateObj.getCandidate_id());
							dependentcandchecklistObj.setInitiate_id(initiateObj.getInitiate_id());
							dependentcandchecklistObj.setProfile_id(spouseprofileid);
							dependentcandchecklistObj.setDocumenttype_id(depchecklistObj.getCategoryid());
							dependentcandchecklistObj.setSubdoctype_id(depchecklistObj.getSubcategoryid());
							dependentcandchecklistObj.setPosted_by(loginempno);
							dependentcandchecklistObj.setSubmitted_date(new Date());
							dependentcandchecklistObj.setProfile_type("Spouse");
							Integer h1bchecklistid=h1bDao.saveCandidateH1BCheckList(dependentcandchecklistObj);
						     }
					}*/
			//	}
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
			}
			return msg;
		}
	
	@Override
	public Integer savebenefitsKids(String kidsincome, String kids_ssi, String kids_tanf, String kids_ga, String kids_snap,
			String kids_housing_assistance, String kids_project_rental_assistance, String kids_public_housing_act,
			String kids_ffm_medicalemergency, String kids_ffm_ideact, String kids_ffm_schoolbenefits,
			String kids_ffm_uderage21, String kids_ffm_pregnant_period60, Integer candidateid) {
		Integer savebenefits=0;
		try {
			PublicBenefitsChecklistEntity childObj=new PublicBenefitsChecklistEntity();
			Date date = new Date(); 
			if(kidsincome!=null) {
			childObj.setBeneficiary_type("Kids");
			childObj.setCandidate_id(candidateid);
			childObj.setIncome_maintenance(kidsincome);
			childObj.setTanf(kids_tanf);
			childObj.setSsi(kids_ssi);
			childObj.setGa(kids_ga);
			childObj.setSnap(kids_snap);
			childObj.setHousing_assistance(kids_housing_assistance);
			childObj.setProject_rental_assistance(kids_project_rental_assistance);
			childObj.setPublic_housing_act(kids_public_housing_act);
			childObj.setFfm_medicalemergency(kids_ffm_medicalemergency);
			childObj.setFfm_ideact(kids_ffm_ideact);
			childObj.setFfm_schoolbenefits(kids_ffm_schoolbenefits);
			childObj.setFfm_uderage21(kids_ffm_uderage21);
			childObj.setFfm_pregnant_period60(kids_ffm_pregnant_period60);
			childObj.setSubmitted_on(date);
			//childObj.setPosted_by(empno);
			savebenefits=h1bDao.savepublicbenefits(childObj);
			}
		}
		catch(Exception e) {
			logger.error("Error in savebenefitsKids H1BService implementation  " + e.getMessage());
		}
		return savebenefits;
	 }
	@Override
	public void updatebenefitsKids(String kidsincome, String kids_ssi, String kids_tanf, String kids_ga,
			String kids_snap, String kids_housing_assistance, String kids_project_rental_assistance,
			String kids_public_housing_act, String kids_ffm_medicalemergency, String kids_ffm_ideact,
			String kids_ffm_schoolbenefits, String kids_ffm_uderage21, String kids_ffm_pregnant_period60,
			Integer benfitid) {
		try {
			PublicBenefitsChecklistEntity childObj=h1bDao.getPublicBenefitsApplicantTypeBenifitId(benfitid);
			
			if(childObj!=null) {
				childObj.setCandidate_id(childObj.getCandidate_id());
				childObj.setTanf(kids_tanf);
				childObj.setSsi(kids_ssi);
				childObj.setIncome_maintenance(kidsincome);
				childObj.setGa(kids_ga);
				childObj.setSnap(kids_snap);
				childObj.setHousing_assistance(kids_housing_assistance);
				childObj.setProject_rental_assistance(kids_project_rental_assistance);
				childObj.setPublic_housing_act(kids_public_housing_act);
				childObj.setFfm_medicalemergency(kids_ffm_medicalemergency);
				childObj.setFfm_ideact(kids_ffm_ideact);
				childObj.setFfm_schoolbenefits(kids_ffm_schoolbenefits);
				childObj.setFfm_uderage21(kids_ffm_uderage21);
				childObj.setFfm_pregnant_period60(kids_ffm_pregnant_period60);
				//childObj.setSubmitted_on(date);
				//childObj.setPosted_by(empno);
				h1bDao.updatepublicbenefits(childObj);
				}
			
			
			ImmiCandidateFormsStatusEntity formStatusObjDb=h1bDao.getFormsSatusByCandidateId(childObj.getCandidate_id(), "Public Benefits Checklist");
			if(formStatusObjDb==null) {
				ImmiCandidateFormsStatusEntity formStatus=new ImmiCandidateFormsStatusEntity();
				formStatus.setCandidate_id(childObj.getCandidate_id());
				formStatus.setCandidate_status("Submitted");
				formStatus.setCandidate_submitted_on(new Date());
				formStatus.setForm_type("Public Benefits Checklist");
				formStatus.setVerifier_status("Pending");
				Integer statusid=h1bDao.saveImmiFormsStatus(formStatus);
			}
		}catch(Exception e) {
			logger.error("Error in updatebenefitsKids H1BService implementation  " + e.getMessage());
		}
	}
	@Override
	public Integer saveImmiGroupingProfileName(String profilename, String isdependent, Integer lgempno) {
		Integer profileId = 0;
		try {
			ImmiProfileLableMaster lableObj = new ImmiProfileLableMaster();
			String uppercaseString = uppercaseString(profilename);
			lableObj.setProfile_name(uppercaseString);
			lableObj.setStatus("Active");
			lableObj.setIsdependent(isdependent);
			lableObj.setPosted_by(lgempno);
			lableObj.setSubmitted_date(new Date());

			profileId = h1bDao.saveImmiGroupingProfileName(lableObj);

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return profileId;
	}
	public String uppercaseString(String name) {
		String uppercasename=null;;
		try {
			 uppercasename = name.substring(0, 1).toUpperCase() + name.substring(1);
			}
		 catch (Exception e) {
			logger.info(e.getMessage(),e);		
			}
	return uppercasename;
	}
	@Override
	public void updateprofileName(String profile_id, String profilename, String isdependent) {
		try {
			Integer profileId=0;
			if (profile_id!="") {
				profileId = Integer.parseInt(profile_id);
			}
			ImmiProfileLableMaster lableObj=h1bDao.getImmiProfileLableMasterById(profileId);
			String uppercaseString=uppercaseString(profilename);
			lableObj.setProfile_name(uppercaseString);;
			lableObj.setIsdependent(isdependent);
			h1bDao.updateImmiprofileName(lableObj);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		
		
	}
	
	/**********************************************By PurnaChandar*********************************1 start*/
	@Override
	public ContractsPanelDetails_DTO getimmisubdocumentBydocid(String candidateid, String document_id, 
			String intiateid, String benficiary_type, String profile_id, String questionnarie_id) {
		ContractsPanelDetails_DTO documentsDto=new ContractsPanelDetails_DTO();
		try {
			
			Integer candidate_id=commonObj.ParseInt(candidateid);
			Integer documentid=commonObj.ParseInt(document_id);
			Integer intiate_id=commonObj.ParseInt(intiateid);
			Integer profileid=commonObj.ParseInt(profile_id);
			Integer questionnarieId=0;
			if(benficiary_type.equalsIgnoreCase("Kids")) {
				 questionnarieId=commonObj.ParseInt(questionnarie_id);
			}
			else{
				questionnarieId=0;
			}
		
			
			List<ContractsPanelDetails_DTO> subdoclist = h1bDao.getimmisubdocumentBydocid(candidate_id, documentid,intiate_id,benficiary_type,profileid,questionnarieId);
			List<ContractsPanelDetails_DTO> subdocSubmittedlist = h1bDao.getSubmittedSubdocsbyIds(candidate_id, documentid,intiate_id,benficiary_type,profileid,questionnarieId);
			
			List<ImmiDocumentSubTypeEntity> subdocutypeList = h1bDao.getSubDocumentsByDocid(documentid);
			List<ImmiDocumentSubTypeEntity> subdocttypeListAdmin = h1bDao.getSubDocumentsByDocidSubmittedByAdmin(documentid);
			System.out.println("subdocttypeListAdmin"+subdocttypeListAdmin.size());
			
			List<Integer> uniqueSubcatList = new ArrayList<>();
			List<Integer> can_subdoclist = h1bDao.getCanSubDocumentIdCheckList(candidate_id, documentid,intiate_id,benficiary_type);
			List<Integer> subdocId_List = h1bDao.getSubDocumentIds(documentid);
			List<DocumentTypeDto> subdocumentList =new ArrayList<DocumentTypeDto>();
			
			uniqueSubcatList.addAll(subdocId_List.stream().filter(str -> !can_subdoclist.contains(str)).collect(Collectors.toList()));
			for (Integer subid : uniqueSubcatList) {
				DocumentTypeDto catDto=new DocumentTypeDto();
				ImmiDocumentSubTypeEntity subdocObj=h1bDao.getSubDocumentNameBysubdoctypeId(subid);
				catDto.setSubdoctypeid(subid);
				catDto.setSubdoctypename(subdocObj.getDocument_type());
				subdocumentList.add(catDto);
			}
			
			documentsDto.setCandidateList(subdoclist);
			documentsDto.setCandidatedocTypeList(subdocSubmittedlist);
			documentsDto.setSubdocttypeListAdmin(subdocttypeListAdmin);
			documentsDto.setSubdocttypeList(subdocutypeList);
			documentsDto.setSubDocumentTypeList(subdocumentList);
			
			} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return documentsDto;	
	}
	@Override
	public String saveimmidocumentbydocIdinitiateId(String initiateid, String documentid, String[] subdocumentid,
			String candidateId, Integer lgcandidateid, MultipartFile[] filename, String[] h1b_checklist_id) {
	
		String msg="";
		
		Integer initiate_Id=commonObj.ParseInt(initiateid);
		Integer doc_Id=commonObj.ParseInt(documentid);
		Integer candidate_Id=commonObj.ParseInt(candidateId);
		
			if(filename.length>0) {
				for(int i=0;i<filename.length;i++) {
					EmpDocumentsEntity subCatDocObj=new EmpDocumentsEntity();
					if(!filename[i].getOriginalFilename().isEmpty()) {
						Integer subdocument_id=0;
						Integer h1bchecklistid=0;
						if (subdocumentid[i]!="") {
							subdocument_id = Integer.parseInt(subdocumentid[i]);
						}
						if (h1b_checklist_id[i]!="") {
							h1bchecklistid = Integer.parseInt(h1b_checklist_id[i]);
						}
						 System.out.println(h1b_checklist_id[i]+"h1bchecklistid*****"+h1bchecklistid);
						Date date =new Date();
						subCatDocObj.setCandidate_id(candidate_Id);
						subCatDocObj.setDocumenttype_id(doc_Id);
						subCatDocObj.setDocumentsubtype_id(subdocument_id);
						subCatDocObj.setH1b_checklist_id(h1bchecklistid);
						subCatDocObj.setInitiate_id(initiate_Id);
						subCatDocObj.setSubmit_date(date);
						subCatDocObj.setFile_name(filename[i].getOriginalFilename());
						subCatDocObj.setPostedBy(lgcandidateid);
						subCatDocObj.setFiletype("H-1B");
						subCatDocObj.setStatus("Active");
			   
						 empDao.saveDocuments(subCatDocObj);
						 commonObj.saveH1BfilesToDirectory(candidate_Id,filename[i]);
						 
						 CandidateH1BCheckList h1bChkObj=h1bDao.getCandidateH1BCheckListById(h1bchecklistid);
						 System.out.println("h1bChkObj*****"+h1bChkObj);
					     h1bChkObj.setCandidate_status("Submitted");
						 h1bDao.updateCandidateH1BCheckList(h1bChkObj);
						 /*if(h1bChkObj.getVerifier_status().equalsIgnoreCase("Rejected")) {
						 
						 }*/
					}
				}
				msg="Documents added successfully";
			}
		return msg;
	}
	@Override
	public String saveimmidocumentbydocIdinitiateId1(String initiateid, Integer lgcandidateid,String documentid,String candidateid, String[] input,
			MultipartFile[] filename, String profileid, String benf_type,String questionnarie_id) {
		String msg="";
		
		Integer doc_Id=0;
		if (documentid!="") {
			doc_Id = Integer.parseInt(documentid);
		}
		
		Integer initiate_Id=0,candidate_id=0;
		
		if (initiateid!="") {
			initiate_Id = Integer.parseInt(initiateid);
		}
		if (candidateid!="") {
			candidate_id = Integer.parseInt(candidateid);
		}
		Integer profile_id=commonObj.ParseInt(profileid);
		
	   Integer documentsubtypeid=h1bDao.getSubDocumentTypeId(doc_Id);
	   Integer questionnarieId=0;
		if(benf_type.equalsIgnoreCase("Kids")) {
			 questionnarieId=commonObj.ParseInt(questionnarie_id);
		}
		else{
			questionnarieId=0;
		}
	   
	   Integer h1bchecklistid=h1bDao.getCheckListIdByids(candidate_id, doc_Id,initiate_Id,documentsubtypeid,profile_id,benf_type,questionnarieId);
			if(filename.length>0) {
				for(int i=0;i<filename.length;i++) {
					EmpDocumentsEntity subCatDocObj=new EmpDocumentsEntity();
					if(!filename[i].getOriginalFilename().isEmpty()) {
						Date date =new Date();
						subCatDocObj.setCandidate_id(candidate_id);
						subCatDocObj.setDocumenttype_id(doc_Id);
						subCatDocObj.setDocumentsubtype_id(documentsubtypeid);
						subCatDocObj.setInputvalue(input[i]);
						subCatDocObj.setSubmit_date(date);
						subCatDocObj.setFile_name(filename[i].getOriginalFilename());
						subCatDocObj.setPostedBy(lgcandidateid);
						subCatDocObj.setInitiate_id(initiate_Id);
						subCatDocObj.setFiletype("H-1B");
						subCatDocObj.setStatus("Active");
						subCatDocObj.setH1b_checklist_id(h1bchecklistid);
						empDao.saveDocuments(subCatDocObj);
						commonObj.saveH1BfilesToDirectory(commonObj.ParseInt(candidateid),filename[i]);
					  }
				}
				msg="Documents added successfully";
			}
			 CandidateH1BCheckList h1bChkObj=h1bDao.getCandidateH1BCheckListById(h1bchecklistid);
		     h1bChkObj.setCandidate_status("Submitted");
			 h1bDao.updateCandidateH1BCheckList(h1bChkObj);
			
		return msg;
  }
	

	@Override
	public String updatefilebydocid(String docid, MultipartFile filename1) {
		String msg="";
		Integer document_id=0;
		if(docid!="") {
			document_id=Integer.parseInt(docid);	
		}
	
			
		EmpDocumentsEntity subCatDocObj=empDao.getDocumentByDocId(document_id);
		if(subCatDocObj!=null) {
			EmpDocumentsHistoryEntity empObj=new EmpDocumentsHistoryEntity();
			
			empObj.setDocument_id(document_id);
			empObj.setCandidate_id(subCatDocObj.getCandidate_id());
			empObj.setDocumenttype_id(subCatDocObj.getDocumenttype_id());
			empObj.setDocumentsubtype_id(subCatDocObj.getDocumentsubtype_id());
			empObj.setH1b_checklist_id(subCatDocObj.getH1b_checklist_id());
			empObj.setInitiate_id(subCatDocObj.getInitiate_id());
			empObj.setSubmit_date(subCatDocObj.getSubmit_date());
			empObj.setPostedBy(subCatDocObj.getPostedBy());
			empObj.setFiletype(subCatDocObj.getFiletype());
			empObj.setStatus(subCatDocObj.getStatus());
			empObj.setFile_name(subCatDocObj.getFile_name());
			empDao.saveDocumentsHistory(empObj);
			
			EmpDocumentsEntity empObjdb=empDao.getDocumentByDocId(document_id);
			empObjdb.setFile_name(filename1.getOriginalFilename());
			empDao.updateDocument(empObjdb);
			
			commonObj.saveH1BfilesToDirectory(subCatDocObj.getCandidate_id(),filename1);
			
			msg="Doucument updated successfully";
			
			CandidateH1BCheckList h1bChkObjDb=h1bDao.getCandidateH1BCheckListById(subCatDocObj.getH1b_checklist_id());
			List<CandidateH1BCheckList>	docList=h1bDao.getCandidateH1BCheckListByIds(h1bChkObjDb.getDocumenttype_id(),h1bChkObjDb.getCandidate_id(),h1bChkObjDb.getInitiate_id(),h1bChkObjDb.getProfile_id(),h1bChkObjDb.getProfile_type());
			for (CandidateH1BCheckList chkObj : docList) {
				CandidateH1BCheckList h1bChkObj=h1bDao.getCandidateH1BCheckListById(chkObj.getH1b_checklist_id());
					h1bChkObj.setCandidate_status("Submitted");
					if(h1bChkObj.getVerifier_status()!=null) {
					if(h1bChkObj.getVerifier_status().equalsIgnoreCase("Rejected")) {
						h1bChkObj.setVerifier_status("Pending");
					   }
					 }
				    h1bDao.updateCandidateH1BCheckList(h1bChkObj);
			}
	   }
		else {
			msg="updated failed";
		}
		
		
		
		return msg;
	}
	
	@Override
	public String updateformbydocid(String inputvalue, String document_id, MultipartFile file ) {
		
	String msg=null;
		try {
			Integer documentid=commonObj.ParseInt(document_id);
		
			EmpDocumentsEntity subCatDocObj=empDao.getDocumentByDocId(documentid);
			if(subCatDocObj!=null) {
				EmpDocumentsHistoryEntity formupdate=new EmpDocumentsHistoryEntity();
				
				formupdate.setDocument_id(documentid);
				formupdate.setCandidate_id(subCatDocObj.getCandidate_id());
				formupdate.setDocumenttype_id(subCatDocObj.getDocumenttype_id());
				formupdate.setDocumentsubtype_id(subCatDocObj.getDocumenttype_id());
				formupdate.setH1b_checklist_id(subCatDocObj.getH1b_checklist_id());
				formupdate.setInitiate_id(subCatDocObj.getInitiate_id());
				formupdate.setSubmit_date(subCatDocObj.getSubmit_date());
				formupdate.setPostedBy(subCatDocObj.getPostedBy());
				formupdate.setFiletype(subCatDocObj.getFiletype());
				formupdate.setStatus(subCatDocObj.getStatus());
				formupdate.setInputvalue(subCatDocObj.getInputvalue());
				formupdate.setFile_name(subCatDocObj.getFile_name());
				empDao.saveDocumentsHistory(formupdate);
				
				subCatDocObj.setFile_name(file.getOriginalFilename());
				empDao.updateDocument(subCatDocObj);
				
				commonObj.saveH1BfilesToDirectory(subCatDocObj.getCandidate_id(),file);
				msg="Documets Details Updated";
				
				
				
				CandidateH1BCheckList h1bChkObjDb=h1bDao.getCandidateH1BCheckListById(subCatDocObj.getH1b_checklist_id());
				List<CandidateH1BCheckList>	docList=h1bDao.getCandidateH1BCheckListByIds(h1bChkObjDb.getDocumenttype_id(),h1bChkObjDb.getCandidate_id(),h1bChkObjDb.getInitiate_id(),h1bChkObjDb.getProfile_id(),h1bChkObjDb.getProfile_type());
				for (CandidateH1BCheckList chkObj : docList) {
					CandidateH1BCheckList h1bChkObj=h1bDao.getCandidateH1BCheckListById(chkObj.getH1b_checklist_id());
						h1bChkObj.setCandidate_status("Submitted");
						if(h1bChkObj.getVerifier_status()!=null) {
						if(h1bChkObj.getVerifier_status().equalsIgnoreCase("Rejected")) {
							h1bChkObj.setVerifier_status("Pending");
						   }
						 }
					    h1bDao.updateCandidateH1BCheckList(h1bChkObj);
				}
				
			}else {
				msg="Update Failed!!!";
			}
		} catch(Exception e) {
			logger.info(e.getMessage(),e);
		}
	
		return msg;
	}
	@Override
	public  List<EmpDocumentsEntity> getinputvaluebydocid(String intiateid, String candidateid,
			String documentid, String benficiary_type, String profile_id, String questionnarie_id) {
		List<EmpDocumentsEntity> inputvallist=null;
		try {
		
		Integer intiate_id=commonObj.ParseInt(intiateid);
		Integer candidate_id=commonObj.ParseInt(candidateid);
		Integer document_id=commonObj.ParseInt(documentid);
		Integer profileid=commonObj.ParseInt(profile_id);
		Integer questionnarieId=0;
		if(benficiary_type.equalsIgnoreCase("Kids")) {
			 questionnarieId=commonObj.ParseInt(questionnarie_id);
		}
		else{
			questionnarieId=0;
		}
		Integer documentsubtypeid=h1bDao.getSubDocumentTypeId(document_id);
		Integer h1bchecklistid=h1bDao.getCheckListIdByids(candidate_id, document_id,intiate_id,documentsubtypeid,profileid,benficiary_type,questionnarieId);
		inputvallist=h1bDao.getallinputvals(intiate_id,candidate_id,document_id,h1bchecklistid);
	} catch(Exception e) {
		logger.info(e.getMessage(),e);
	}
		return inputvallist;
	}
	@Override
	public List<EmpDocumentsEntity> viewfilehistory(String docid, String subdocid, String initiateid, String candid,String checklistid) {
		
		List<EmpDocumentsEntity> filehistory=null;
		try {
			Integer candidate_id=commonObj.ParseInt(candid);
			Integer document_id=commonObj.ParseInt(docid);
			Integer  subdocument_id=commonObj.ParseInt(subdocid);
			Integer initiate_id=commonObj.ParseInt(initiateid);
			Integer checklist_id=commonObj.ParseInt(checklistid);
			
			filehistory = (List<EmpDocumentsEntity>) h1bDao.viewfilehistory(candidate_id,document_id,subdocument_id,initiate_id,checklist_id);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return filehistory;
	}
	@Override
	public List<DocumentsDTO> getdocumentshistory(String document_id) {
		List<DocumentsDTO> docList = null;
		try {
			Integer docid=commonObj.ParseInt(document_id);
			docList = h1bDao.getdocumentshistory(docid);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return docList;	
	}
	/*******************************************************************************1 close*/
	
	@Override
	public List<DocumentHistoryDto> getCandidateDocumentsByIds(String docTypeId, String candidateid,
			String initiateid,String profileid, String profile_type) {
		 List<DocumentHistoryDto> historydocs=null;
			try {
				Integer docType_Id=0;
				if(!docTypeId.isEmpty()) {
					docType_Id=Integer.parseInt(docTypeId);
				}
				Integer candidate_Id = 0;
				if (candidateid!="") {
					candidate_Id = Integer.parseInt(candidateid);
				}
				Integer initiate_id = 0;
				if (initiateid!="") {
					initiate_id = Integer.parseInt(initiateid);
				}
				Integer profile_id = 0;
				if (profileid!="") {
					profile_id = Integer.parseInt(profileid);
				}
				historydocs=h1bDao.getCandidateDocumentsByIds(docType_Id,candidate_Id,initiate_id,profile_id,profile_type);
			} catch (Exception e) {
				logger.info(e.getMessage(),e);
			}
			return historydocs;
	}
	@Override
	public String updateCandDocVerifierStatus(String docTypeId, String candidateid, String initiateid,
			Integer lgempno, String verfierstatus, String verfiercomments, String profileid, String profile_type) {
		String message=null;
		try {
			Integer docType_id=0;
			if(!docTypeId.isEmpty()) {
				docType_id=Integer.parseInt(docTypeId);
			}
			Integer candidate_id = 0;
			if (candidateid!="") {
				candidate_id = Integer.parseInt(candidateid);
			}
			Integer initiate_id = 0;
			if (initiateid!="") {
				initiate_id = Integer.parseInt(initiateid);
			}
			Integer profile_id = 0;
			if (profileid!="") {
				profile_id = Integer.parseInt(profileid);
			}
			
			
			List<CandidateH1BCheckList>	docList=h1bDao.getCandidateH1BCheckListByIds(docType_id,candidate_id,initiate_id,profile_id,profile_type);
			System.out.println("docList******"+docList.size());
			for (CandidateH1BCheckList chkObj : docList) {
				System.out.println("chkObj******"+chkObj.getH1b_checklist_id());
				CandidateH1BCheckList h1bChkObj=h1bDao.getCandidateH1BCheckListById(chkObj.getH1b_checklist_id());
				if(verfierstatus.equalsIgnoreCase("Rejected")) {
					h1bChkObj.setCandidate_status("Pending");
				 }else if(verfierstatus.equalsIgnoreCase("Approved")) {
						h1bChkObj.setCandidate_status("Submitted");
					 }
				h1bChkObj.setVerifier_status(verfierstatus);
				h1bDao.updateCandidateH1BCheckList(h1bChkObj);
			}
			
			ImmiCheckListCommentsHistory commentObj=new ImmiCheckListCommentsHistory();
			commentObj.setCandidate_id(candidate_id);
			commentObj.setInitiate_id(initiate_id);
			commentObj.setDoctype_id(docType_id);
			commentObj.setPosted_by(lgempno);
			commentObj.setSubmitted_date(new Date());
			commentObj.setStatus(verfierstatus);
			commentObj.setComments(verfiercomments);
			commentObj.setProfile_id(profile_id);
			commentObj.setProfile_type(profile_type);
			Integer commentid=h1bDao.saveImmiCheckListComments(commentObj);
			if(commentid>0) {
				message="Document Status updated Successfully";
			}else {
				message="Document Status not updated";
			  }
		}catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return message;
	  }
	@Override
	public List<commentHistoryDto> getImmiCandidateDocumentsByIds(String docTypeId, String candidateid,
			String initiateid, String profileid, String profile_type) {
		List<commentHistoryDto> commentHistoryList = null;
		try {
			Integer docType_id=0;
			if(!docTypeId.isEmpty()) {
				docType_id=Integer.parseInt(docTypeId);
			}
			Integer candidate_id = 0;
			if (candidateid!="") {
				candidate_id = Integer.parseInt(candidateid);
			}
			Integer initiate_id = 0;
			if (initiateid!="") {
				initiate_id = Integer.parseInt(initiateid);
			}
			Integer profile_id = 0;
			if (profileid!="") {
				profile_id = Integer.parseInt(profileid);
			}
			
			commentHistoryList = h1bDao.getImmiCandidateDocumentsByIds(docType_id,candidate_id,initiate_id,profile_id,profile_type);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return commentHistoryList;
	}
	@Override
	public String saveimmiSubdocumentstoChecklist(String initiateid, String documentid, String subdocumentid,
			String candidateId, Integer lgcandidateid, MultipartFile filename, String profileid, String benf_type) {
         String msg="";
         try {
         Integer initiate_Id=commonObj.ParseInt(initiateid);
		Integer doc_Id=commonObj.ParseInt(documentid);
		Integer candidate_Id=commonObj.ParseInt(candidateId);
		Integer subdocument_id=commonObj.ParseInt(subdocumentid);
		Integer profile_id=commonObj.ParseInt(profileid);
		
		CandidateH1BCheckList  subcatObj=new CandidateH1BCheckList();
		subcatObj.setCandidate_id(candidate_Id);
		subcatObj.setInitiate_id(initiate_Id);
		subcatObj.setProfile_id(profile_id);
		subcatObj.setProfile_type(benf_type);
		subcatObj.setDocumenttype_id(doc_Id);
		subcatObj.setSubdoctype_id(subdocument_id);
		subcatObj.setPosted_by(lgcandidateid);
		subcatObj.setSubmitted_date(new Date());
		Integer h1bchecklistid=h1bDao.saveCandidateH1BCheckList(subcatObj);
		
			if(filename!=null) {
				EmpDocumentsEntity empDocumentObj=new EmpDocumentsEntity();
				if(h1bchecklistid>0) {
				if(!filename.getOriginalFilename().isEmpty()) {
					empDocumentObj.setCandidate_id(candidate_Id);
					empDocumentObj.setDocumenttype_id(doc_Id);
					empDocumentObj.setDocumentsubtype_id(subdocument_id);
					empDocumentObj.setH1b_checklist_id(h1bchecklistid);
					empDocumentObj.setInitiate_id(initiate_Id);
					empDocumentObj.setSubmit_date(new Date());
					empDocumentObj.setFile_name(filename.getOriginalFilename());
					empDocumentObj.setPostedBy(lgcandidateid);
					empDocumentObj.setFiletype("H-1B");
					empDocumentObj.setStatus("Active");
		   
					empDao.saveDocuments(empDocumentObj);
					commonObj.saveH1BfilesToDirectory(candidate_Id,filename);
					CandidateH1BCheckList h1bChkObj=h1bDao.getCandidateH1BCheckListById(h1bchecklistid);
				    h1bChkObj.setCandidate_status("Submitted");
					h1bDao.updateCandidateH1BCheckList(h1bChkObj);
				 }
			  }
				msg="Documents added successfully";
			}
         }catch (Exception e) {
					logger.info(e.getMessage(),e);		
					}
		return msg;
	}
	@Override
	public String saveH1BDocuments(String documentsubtype, String doctype, String[] h1documentInputs,
			CommonsMultipartFile[] rfeh1document, Integer lgempno, String candidateid, String intiateid,
			String profileid, String bentype) {
		String msg=null;;
		try {
			Integer h1bchecklistid=0;
			Integer documentsubtypeid=commonObj.ParseInt(documentsubtype);
			Integer candidate_id=commonObj.ParseInt(candidateid);
			Integer doctypeid=commonObj.ParseInt(doctype);
			Integer intiate_id=commonObj.ParseInt(intiateid);
			Integer profile_id=commonObj.ParseInt(profileid);
			h1bchecklistid=h1bDao.getCheckListIdByids(candidate_id, doctypeid,intiate_id,documentsubtypeid,profile_id,bentype,0);
			List<ContractsPanelDetails_DTO> adminList = h1bDao.getimmiDocsAdminList(intiate_id, profile_id,candidate_id);
			String printStatus="";
			for (ContractsPanelDetails_DTO adminObj : adminList) {
				if(adminObj.getPrinted_status()!=null) {
				printStatus=adminObj.getPrinted_status();
				}
			}
			System.out.println("printStatus*********"+printStatus+"h1bchecklistid*********"+h1bchecklistid);
			if(h1bchecklistid!=null) {
				
			}else {
				CandidateH1BCheckList  subcatObj=new CandidateH1BCheckList();
				subcatObj.setCandidate_id(candidate_id);
				subcatObj.setInitiate_id(intiate_id);
				subcatObj.setProfile_id(profile_id);
				subcatObj.setDocumenttype_id(doctypeid);
				subcatObj.setSubdoctype_id(documentsubtypeid);
				subcatObj.setProfile_type(bentype);
				subcatObj.setPosted_by(lgempno);
				subcatObj.setSubmitted_date(new Date());
				h1bchecklistid=h1bDao.saveCandidateH1BCheckList(subcatObj);
			}
			

		if(h1bchecklistid>0) {
			if(rfeh1document!=null) {
			 for (int i=0;i<rfeh1document.length;i++) {
					 if(!rfeh1document[i].getOriginalFilename().isEmpty()) {
	          	     EmpDocumentsEntity docobj=new EmpDocumentsEntity();
	 			     docobj.setCandidate_id(candidate_id);
	 			     docobj.setPostedBy(lgempno);
	 			     docobj.setSubmit_date(new Date());
	 			     docobj.setStatus("Active");
	 			     docobj.setFile_name(rfeh1document[i].getOriginalFilename());
	 			     docobj.setFiletype("H-1B");
	 			     docobj.setDocumenttype_id(doctypeid);
	 			     docobj.setDocumentsubtype_id(documentsubtypeid);
	 			     docobj.setInitiate_id(intiate_id);
	 			     docobj.setH1b_checklist_id(h1bchecklistid);
	 			    docobj.setInputvalue(h1documentInputs[i]);
	 			     commonObj.saveH1BfilesToDirectory(candidate_id,rfeh1document[i]);
	 			     empDao.saveDocuments(docobj);
	                 } 
			    }
			 }
			 /*CandidateH1BCheckList h1bChkObj=h1bDao.getCandidateH1BCheckListById(h1bchecklistid);
		     h1bChkObj.setVerifier_status("Approved");
			 h1bDao.updateCandidateH1BCheckList(h1bChkObj);*/
			List<Integer> h1bChkObjList = h1bDao.getCheckListIdsByIds(candidate_id, doctypeid,intiate_id,profile_id,bentype);
			 for (Integer chkid : h1bChkObjList) {
				 CandidateH1BCheckList h1bChkObj=h1bDao.getCandidateH1BCheckListById(chkid);
			     h1bChkObj.setVerifier_status("Approved");
			     if(!printStatus.isEmpty()) {
			    	 h1bChkObj.setPrinted_status(printStatus); 
			     }
				 h1bDao.updateCandidateH1BCheckList(h1bChkObj);
			  }
		  }
		 msg="Documents Added Successfully";
		}
		 catch (Exception e) {
			logger.info(e.getMessage(),e);		
			}
	return msg;
	}
	@Override
	public PAFDetailsDto pafDetails(String lcaid) {
		PAFDetailsDto pafObj=new PAFDetailsDto();
		try {
			EndClientDetialDto endClient = new EndClientDetialDto();
			Integer lca_id = 0;
			if (!lcaid.isEmpty()) {
				lca_id = Integer.parseInt(lcaid);
			}
			String endClientlocation="";
			String remotelocation="";
			LCADetailsEntity lcaObj = immiDao.getLcaDetailsById(lca_id);
			Integer docCount=immiDao.getDocumentCountByLcaId(lca_id);
			ClientDetailsEntity clientObj = immiDao.getEndClientDetailsById(lcaObj.getCandidate_id(),lcaObj.getClient_id());
			if(clientObj!=null) {
				endClient.setLcaObj(lcaObj);
				endClient.setClientObj(clientObj);
				endClient.setDocCount(docCount);
				EmpPayRateDetails payRateObj=empDao.getPayrateDetailsByPayrateId(lcaObj.getPayrate_id());
				endClient.setPayRateObj(payRateObj);
				CompanyAdressEntity endclientaddresObj=immiDao.getEndclientAddressByclientId(lcaObj.getClient_id());
				CompanyAdressEntity remoteaddressObj=immiDao.getRemoteAddressByclientId(lcaObj.getClient_id());
				endClient.setEndclientaddres(endclientaddresObj);
				endClient.setRemoteaddress(remoteaddressObj);
				
				if(endClient.getEndclientaddres().getLine2()!=null) {
					endClientlocation=endClient.getEndclientaddres().getLine1()+" , "+endClient.getEndclientaddres().getLine2()+" , "+endClient.getEndclientaddres().getCity()+" , "+endClient.getEndclientaddres().getState()+" , "+endClient.getEndclientaddres().getZipcode();	
				}else {
					endClientlocation=endClient.getEndclientaddres().getLine1()+" , "+endClient.getEndclientaddres().getCity()+" , "+endClient.getEndclientaddres().getState()+" , "+endClient.getEndclientaddres().getZipcode();
				}
				if(endClient.getRemoteaddress()!=null) {
					if(endClient.getEndclientaddres().getLine2()!=null) {
						remotelocation=endClient.getRemoteaddress().getLine1()+" , "+endClient.getRemoteaddress().getLine2()+" , "+endClient.getRemoteaddress().getCity()+" , "+endClient.getRemoteaddress().getState()+" , "+endClient.getRemoteaddress().getZipcode();	
					}else {
						remotelocation=endClient.getRemoteaddress().getLine1()+" , "+endClient.getRemoteaddress().getCity()+" , "+endClient.getRemoteaddress().getState()+" , "+endClient.getRemoteaddress().getZipcode();
					}
				}else {
					remotelocation="";
				 }
				pafObj.setEndclientAddress(endClientlocation);
				pafObj.setRemoteaddress(remotelocation);
			  }
			
			SOCcodeMasterEntity socObj=h1bDao.getSocCodeDetailsBySocid(clientObj.getSoc_code());
			//List<BasicDetailsDto> activeempList=h1bDao.getActiveEmployeeList();
			pafObj.setLcaObj(lcaObj);
			pafObj.setClientObj(clientObj);
			pafObj.setSocObj(socObj);
			//pafObj.setBasicDetails(activeempList);
		}
			catch (Exception e) {			
				logger.error("Error while  downloadlcapdf ", e);
			}
			return pafObj;
	}
	@Override
	public List<BasicDetailsDto> getActiveEmployeeList() {
		List<BasicDetailsDto> activeempList = null;
		try {
			 activeempList=h1bDao.getActiveEmployeeList();
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return activeempList;
	}
	@Override
	public List<SOCcodeMasterEntity> getSocCodeMasterList() {
		List<SOCcodeMasterEntity> socList = null;
		try {
			socList=h1bDao.getSocCodeMasterList();
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return socList;
	}
	
	@Override
	public SOCcodeMasterEntity getSOCDetailsById(String socid) {
		SOCcodeMasterEntity socObj= null;
		try {
			Integer soc_id = 0;
			if (socid!="") {
				soc_id = Integer.parseInt(socid);
			}
			socObj=h1bDao.getSOCDetailsById(soc_id);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return socObj;
	}
	@Override
	public String checkSocCode(String soc_code) {
		String soccode = null;
		try {
			soccode = h1bDao.checkSocCode(soc_code);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return soccode;
	}
	
	@Override
	public String saveupdatesoc(String soc_code, String soccode_title, String duties, String from_wagerate,
			String to_wagerate, String occ_clasfi, Integer loginempno, String socid) {
		String message = "";
		try {
			if (!socid.isEmpty()) {
				Integer soc_id = 0;
				if (socid != "") {
					soc_id = Integer.parseInt(socid);
				}
				SOCcodeMasterEntity socObj = h1bDao.getSOCDetailsById(soc_id);
				if (socObj != null) {
					socObj.setSoc_code(soc_code);
					socObj.setSoccode_title(soccode_title);
					socObj.setDuties(duties);
					socObj.setFrom_wage_range(from_wagerate);
					socObj.setTo_wage_range(to_wagerate);
					socObj.setOccupation_calssification(occ_clasfi);
					h1bDao.updateSocCodeMaster(socObj);
					message = "SOC Details Updated Successfully";
				}
			} else {
				SOCcodeMasterEntity socaddObj = new SOCcodeMasterEntity();
				socaddObj.setSoc_code(soc_code);
				socaddObj.setSoccode_title(soccode_title);
				socaddObj.setDuties(duties);
				socaddObj.setFrom_wage_range(from_wagerate);
				socaddObj.setTo_wage_range(to_wagerate);
				socaddObj.setOccupation_calssification(occ_clasfi);
				socaddObj.setSubmitted_on(new Date());
				socaddObj.setSubmitted_by(loginempno);
				Integer soc_Id = h1bDao.saveSocCodeMaster(socaddObj);
				if (soc_Id > 0) {
					message = "SOC Details Added Successfully";
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return message;
	
	}
	@Override
	public String checkpetitionPacket(String candidateid, String initiateid) {
		String petitinstatus = null;
		try {
			Integer candidate_id=0 ;Integer initiate_id=0;
			if (candidateid != "") {
				candidate_id = Integer.parseInt(candidateid);
				}
			if (initiateid != "") {
				initiate_id = Integer.parseInt(initiateid);
				}
			List<String> docstatus = h1bDao.checkpetitionPacket(initiate_id,candidate_id);
			if(docstatus.size()>0) {
				if(docstatus.contains(null) || docstatus.contains("Rejected")) {
					petitinstatus="Some documents are yet to be approved/yet to be uploaded by candidate";
				}else {
					petitinstatus="Approved";
				}
			}else {
				petitinstatus="No Documet to download";
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return petitinstatus;
	}
	@Override
	public List<String> getpetitionPacketDocuments(String candidateid, String initiateid) {
		List<String> docList = new ArrayList<String>();
		try {
			Integer candidate_id=0 ;Integer initiate_id=0;
			if (candidateid != "") {
				candidate_id = Integer.parseInt(candidateid);
				}
			if (initiateid != "") {
				initiate_id = Integer.parseInt(initiateid);
				}
			List<CandidateH1BCheckList> h1bcheckList = h1bDao.getpetitionPacketDocuments(initiate_id,candidate_id);
			for (CandidateH1BCheckList cklist : h1bcheckList) {
				Integer checklistid=cklist.getH1b_checklist_id();
				List<String> docListobj=h1bDao.getDocumentByChecklistId(checklistid);
				for (String filename : docListobj) {
					//String filename=empObjdb.getFile_name();
					docList.add(filename);
				}
			}
			
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return docList;
	}
	@Override
	public String getProfileNameByinitiateid(String initiateid) {
		String profilename= null;
		try {
			Integer initiate_id = 0;
			if (initiateid!="") {
				initiate_id = Integer.parseInt(initiateid);
			}
			CandidateH1BInitiateDetailsEntity initiateObj=h1bDao.getInitiateDetailsByInitiateId(initiate_id);
			if(initiateObj!=null) {
				profilename=h1bDao.getProfileNameById(initiateObj.getProfile_id());	
			}
			
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return profilename;
	}
	@Override
	public List<TaskDetailsEntity> getalltaskdetailsbycandidateid(String candidateid) {
		List<TaskDetailsEntity> taskList = null;
		try {
			Integer candidate_Id = 0;;
			if (candidateid!="") {
				candidate_Id = Integer.parseInt(candidateid);
			}
				taskList = h1bDao.getalltaskdetailsbycandidateid(candidate_Id);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return taskList;	
	}
	@Override
	public String getdocumentTypeById(String documenttypeid) {
		String documentypeName= null;
		try {
			Integer documenttype_id = 0;
			if (documenttypeid!="") {
				documenttype_id = Integer.parseInt(documenttypeid);
			}
				documentypeName=h1bDao.getdocumentTypeByDocId(documenttype_id);	
			
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return documentypeName;
	}
	@Override
	public String updateprintnostatus(String initiateid, String candidateid, String documenttypeid, String profileid,
			String profiletype, String printedvalue) {
		String message =null;
		try {
			Integer initiate_id=0;Integer candidate_id=0;Integer documenttype_id=0;Integer profile_id=0;
			if (profileid!="") {
				profile_id = Integer.parseInt(profileid);}
			if (initiateid!="") {
				initiate_id = Integer.parseInt(initiateid);}
			if (candidateid!="") {
				candidate_id = Integer.parseInt(candidateid);}
			if (documenttypeid!="") {
				documenttype_id = Integer.parseInt(documenttypeid);}
			List<CandidateH1BCheckList>	docList=h1bDao.getCandidateH1BCheckListByIds(documenttype_id,candidate_id,initiate_id,profile_id,profiletype);
			if(docList.size()>0) {
			for (CandidateH1BCheckList chkObj : docList) {
				System.out.println("chkObj******"+chkObj.getH1b_checklist_id());
				CandidateH1BCheckList h1bChkObj=h1bDao.getCandidateH1BCheckListById(chkObj.getH1b_checklist_id());
				if(h1bChkObj!=null) {
					h1bChkObj.setPrinted_status(printedvalue);
					h1bDao.updateCandidateH1BCheckList(h1bChkObj);	
				}
			}
			message="Documents are not printed";
		}
			else {
				message="Document are not available to print";
			}
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return message;
	}
	@Override
	public String updateprintyesstatus(String initiateid, String candidateid, String documenttypeid, String profileid,
			String profiletype, String printedvalue) {
		String message =null;
		try {
			Integer initiate_id=0;Integer candidate_id=0;Integer documenttype_id=0;Integer profile_id=0;
			if (profileid!="") {
				profile_id = Integer.parseInt(profileid);}
			if (initiateid!="") {
				initiate_id = Integer.parseInt(initiateid);}
			if (candidateid!="") {
				candidate_id = Integer.parseInt(candidateid);}
			if (documenttypeid!="") {
				documenttype_id = Integer.parseInt(documenttypeid);}
			List<CandidateH1BCheckList>	docList=h1bDao.getCandidateH1BCheckListByIds(documenttype_id,candidate_id,initiate_id,profile_id,profiletype);
			if(docList.size()>0) {
			for (CandidateH1BCheckList chkObj : docList) {
				System.out.println("chkObj******"+chkObj.getH1b_checklist_id());
				CandidateH1BCheckList h1bChkObj=h1bDao.getCandidateH1BCheckListById(chkObj.getH1b_checklist_id());
				if(h1bChkObj!=null) {
					h1bChkObj.setPrinted_status(printedvalue);
					h1bDao.updateCandidateH1BCheckList(h1bChkObj);	
				}
			 }
			message="Documents are printed";
		   }
			else {
				message="Document are not available to print";
			}
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return message;
	}
	@Override
	public List<String> getdocumentsByCategoryId(String candidateid, String initiateid, String documenttypeid) {
		List<String> docList = new ArrayList<String>();
		try {
			Integer candidate_id=0 ;Integer initiate_id=0;Integer documenttype_id=0;
			if (candidateid != "") {
				candidate_id = Integer.parseInt(candidateid);
				}
			if (initiateid != "") {
				initiate_id = Integer.parseInt(initiateid);
				}
			if (documenttypeid != "") {
				documenttype_id = Integer.parseInt(documenttypeid);
				}
			List<CandidateH1BCheckList> h1bcheckList = h1bDao.getdocumentsByCategoryId(initiate_id,candidate_id,documenttype_id);
			for (CandidateH1BCheckList cklist : h1bcheckList) {
				Integer checklistid=cklist.getH1b_checklist_id();
				List<String> empdocObj=h1bDao.getDocumentByChecklistId(checklistid);
				for (String string : empdocObj) {
					docList.add(string);
				}
				
			}
			
		} catch (Exception e) {
		}
		return docList;
	}
	
	
	
	@Override
	public String scandetailsform(String candidateId, Integer lgempno, String[] input,
			MultipartFile[] filename,String initiateid) {
		String msg=null;
		Integer candId=0;Integer initiate_id=0;
		if (candidateId!="") {
			candId = Integer.parseInt(candidateId);}
		if (initiateid!="") {
			initiate_id = Integer.parseInt(initiateid);}
		if(filename.length>0) {
			for(int i=0;i<filename.length;i++) {
				CandidateScannedDocumentsEntity scdoc=new CandidateScannedDocumentsEntity();
				if(!filename[i].getOriginalFilename().isEmpty()) {
					Date date =new Date();
					
					scdoc.setCandidate_id(candId);
					scdoc.setFile_name(filename[i].getOriginalFilename());
					scdoc.setInput_value(input[i]);
					scdoc.setPosted_by(lgempno);
					scdoc.setSubmitted_date(date);
					scdoc.setInitiate_id(initiate_id);
					
	
					h1bDao.saveScanneddoc(scdoc);
					commonObj.saveH1BfilesToDirectory(commonObj.ParseInt(candidateId),filename[i]);
				  }
			}
			msg="Documents added successfully";
		}
		return msg;
	}
	@Override
	public List<DocumentHistoryDto> getsclist(String initiateid, String candidateId) {
		List<DocumentHistoryDto> sclist = null;
		try {
			Integer candId=0;Integer initiate_id=0;
			if (initiateid!="") {
				initiate_id = Integer.parseInt(initiateid);}
			if (candidateId!="") {
				candId = Integer.parseInt(candidateId);}
			sclist = h1bDao.getsclist(candId,initiate_id);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return sclist;	
		
		
	}
	@Override
	public List<String> getScannedDocuments(String candidateid, String initiateid) {
		List<String> docList = new ArrayList<String>();
		try {
			Integer candidate_id=0 ;Integer initiate_id=0;
			if (candidateid != "") {
				candidate_id = Integer.parseInt(candidateid);
				}
			if (initiateid != "") {
				initiate_id = Integer.parseInt(initiateid);
				}
			List<String> scannedList = h1bDao.getScannedDocuments(initiate_id,candidate_id);
			for (String cklist : scannedList) {
					docList.add(cklist);
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return docList;
	}
	@Override
	public H1BDetailsEntity geth1bDetalsById(String h1bId) {
		H1BDetailsEntity h1bObj= null;
		try {
			Integer h1b_Id=commonObj.ParseInt(h1bId);
			h1bObj=h1bDao.getH1BDetailsById(h1b_Id);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return h1bObj;
	}
	@Override
	public String updateh1bapproveddocumentdetails(String documentid, String h1bid, String h1bRecieptNumber,
			String h1bvFromDate, String h1bvToDate, String i94Number, String i94vFromDate, String i94vToDate,
			MultipartFile h1bdocument, Integer lgcandidateid) {
		String  msg="";
		try {
			Integer docid=commonObj.ParseInt(documentid);
			Integer h1b_Id=commonObj.ParseInt(h1bid);
		H1BDetailsEntity h1bObj=h1bDao.getH1BDetailsById(h1b_Id);
		if(h1bObj!=null) {
			h1bObj.setReceipt_number(h1bRecieptNumber);
			h1bObj.setH1b_fromdate(h1bvFromDate);
			h1bObj.setH1b_todate(h1bvToDate);
			h1bObj.setI94_number(i94Number);
			h1bObj.setI94_fromdate(i94vFromDate);
			h1bObj.setI94_todate(i94vToDate);
			h1bObj.setPosted_by(lgcandidateid);
			h1bObj.setStatus("Active");
			h1bObj.setSubmitted_date(new Date());
			h1bDao.updateH1BDetails(h1bObj);
		  }
		 EmpDocumentsEntity docobj=empDao.getDocumentByDocId(docid);
		 if(h1bdocument!=null) {
			 if(!h1bdocument.getOriginalFilename().isEmpty()) {
      	     EmpDocumentsHistoryEntity empObj=new EmpDocumentsHistoryEntity();
   			empObj.setDocument_id(docobj.getDocument_id());
   			empObj.setCandidate_id(docobj.getCandidate_id());
   			empObj.setDocumenttype_id(docobj.getDocumenttype_id());
   			empObj.setDocumentsubtype_id(docobj.getDocumentsubtype_id());
   			empObj.setH1b_checklist_id(docobj.getH1b_checklist_id());
   			empObj.setInitiate_id(docobj.getInitiate_id());
   			empObj.setSubmit_date(docobj.getSubmit_date());
   			empObj.setPostedBy(docobj.getPostedBy());
   			empObj.setFiletype(docobj.getFiletype());
   			empObj.setStatus(docobj.getStatus());
   			empObj.setFile_name(docobj.getFile_name());
   			empDao.saveDocumentsHistory(empObj);
   			EmpDocumentsEntity docObjdb=empDao.getDocumentByDocId(docid);
   			docObjdb.setFile_name(h1bdocument.getOriginalFilename());
   			empDao.updateDocument(docObjdb);
   			commonObj.saveH1BfilesToDirectory(docobj.getCandidate_id(),h1bdocument);
             } 
		 }
		msg="Details updated successfully";
		}catch(Exception e) {
			logger.error("Error in updateimmiformstaus method " + e.getMessage());
		}
		return msg;
	}
	@Override
	public String checksubdocsbyids(String initiateid, String candidateId, String profileid, String benf_type,
			String documentid, String subdocid) {
		String message = null;
		try {
			Integer documentsubtypeid=commonObj.ParseInt(subdocid);
			Integer candidate_id=commonObj.ParseInt(candidateId);
			Integer doctypeid=commonObj.ParseInt(documentid);
			Integer intiate_id=commonObj.ParseInt(initiateid);
			Integer profile_id=commonObj.ParseInt(profileid);
			Integer chklistid=h1bDao.getCheckListIdByids(candidate_id, doctypeid,intiate_id,documentsubtypeid,profile_id,benf_type,0);
			Integer docCount = h1bDao.getDocCountByH1bChkId(chklistid);
			if(docCount>0) {
				message="Exists";
			}else {
				message="Not Exists";
			 }
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return message;
	}
	@Override
	public String checkSelectedFileNameById(String filename, String candidateid) {
		String fName = null;
		try {
			Integer candidate_id=commonObj.ParseInt(candidateid);
			fName = h1bDao.checkSelectedFileNameById(filename,candidate_id);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return fName;
	}
  }
