package com.tekskills.Controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHighlightColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tekskills.Dto.BasicDetailsDto;
import com.tekskills.Dto.CandidateH1BInitiateDetailsDto;
import com.tekskills.Dto.ClientVendorLetterQueDto;
import com.tekskills.Dto.ContractsPanelDetails_DTO;
import com.tekskills.Dto.DocumentHistoryDto;
import com.tekskills.Dto.DocumentsDTO;
import com.tekskills.Dto.EndClientDetialDto;
import com.tekskills.Dto.H1BDetailsDto;
import com.tekskills.Dto.ImmicommentsDTO;
import com.tekskills.Dto.LCADetailsDto;
import com.tekskills.Dto.PAFDetailsDto;
import com.tekskills.Dto.ProfileMasterDTO;
import com.tekskills.Dto.PublicBenefitsChecklistDTO;
import com.tekskills.Dto.commentHistoryDto;
import com.tekskills.Entity.EmpDocumentsEntity;
import com.tekskills.Entity.H1BDetailsEntity;
import com.tekskills.Entity.ImmiDocumentSubTypeEntity;
import com.tekskills.Entity.ImmiDocumentTypeEntity;
import com.tekskills.Entity.ImmiProfileLableMaster;
import com.tekskills.Entity.ImmiProfileMasterCheckList;
import com.tekskills.Entity.LCADetailsEntity;
import com.tekskills.Entity.PublicBenefitsChecklistEntity;
import com.tekskills.Entity.SOCcodeMasterEntity;
import com.tekskills.Entity.TaskDetailsEntity;
import com.tekskills.Service.ContractorsService;
import com.tekskills.Service.EmployeesService;
import com.tekskills.Service.H1BService;
import com.tekskills.Service.ImmigrationService;
import com.tekskills.Util.SendHtmlEmailNew;

@Controller
@PropertySource("/WEB-INF/mail.properties")
public class H1BController { 
	private static final Logger logger = Logger.getLogger(H1BController.class);
	private static final String UPLOAD_DIRECTORY = "temp_pdf";
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 5; // 5	MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
	@Autowired
	private ImmigrationService immiService;
	@Autowired
	private H1BService h1bService;
	@Autowired
	private ContractorsService contService;
	@Autowired
	private EmployeesService empService;
	@Autowired
	private Environment env;
	@Autowired
	ServletContext context;

	@Autowired
	private SendHtmlEmailNew sendmail;
	
	
	@RequestMapping(value = { "/initiatetemplate" }, method = RequestMethod.GET)
	public String profilemaster(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		try {
			List<ImmiDocumentTypeEntity> documentList = h1bService.getAllDocuments();
			List<DocumentsDTO> subdocumentList = h1bService.getSubDocumentList();
			List<ImmiProfileLableMaster> initiateList = h1bService.getInitiateTemplateList();
			List<DocumentsDTO> templateList = h1bService.getInitiatedTemplates();
			
			model.addAttribute("templateList", templateList);
			model.addAttribute("initiateList", initiateList);
			model.addAttribute("documentList", documentList);
			model.addAttribute("subdocumentList", subdocumentList);
		} catch (Exception e) {
			logger.error("Error in get initiatetemplate page" + e);
		}
		return "H1BInitiateTemplate";
	}
	@RequestMapping(value = { "/checkimmiprofilemaster" }, method = RequestMethod.GET)
	@ResponseBody
	public String checkprofilemaster(Model model, HttpServletRequest request) {
		String chckprofile = null;
		try {
			Integer lgempno = (Integer) request.getSession().getAttribute("empno");
			if (lgempno != null) {
				String profilename = request.getParameter("profilename");
				chckprofile = h1bService.checkimmiprofilemaster(profilename);
			} else {
				chckprofile = null;
			}
		} catch (Exception e) {
			logger.error("Error in checkimmiprofilemaster " + e);
		}
		return chckprofile;
	}
	@RequestMapping(value = { "/getSubDocumentsByDocid" }, method = RequestMethod.GET)
	@ResponseBody
	public List<ImmiDocumentSubTypeEntity> getSubCategoriesByCatid(Model model, HttpServletRequest request) {
		List<ImmiDocumentSubTypeEntity> subcatList = null;
		try {
			String Categoryid = request.getParameter("Categoryid");
			subcatList = h1bService.getSubDocumentsByDocid(Categoryid);

		} catch (Exception e) {
			logger.error("Error in getSubDocumentsByDocid " + e);
		}
		return subcatList;
	}
	
	@RequestMapping(value = { "/immitemplatemaster" }, method = RequestMethod.POST)
	@ResponseBody
	public String saveprofilemaster(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer lgempno = (Integer) request.getSession().getAttribute("empno");
		String msg = "";
		try {
			String profilename = request.getParameter("profilename");
			String isdependent = request.getParameter("isdependent");
			Integer lableId = h1bService.saveImmiGroupingProfileName(profilename, isdependent, lgempno);
			//Integer lableId=0;
			/*if(profilename!="") {
				lableId=Integer.parseInt(profilename);
			}*/
			String[] subcatArray = request.getParameterValues("subcatArray");
			String subcatString = null;
			for (int i = 0; i < subcatArray.length; i++) {
				subcatString = subcatArray[i];
				String[] subcategories = subcatString.split(",");
				for (String subcategoryId : subcategories) {
					msg = h1bService.saveImmiTemplateMaster(lableId, subcategoryId, lgempno);
				}
			}
		} catch (Exception e) {
			logger.error("Error in get saveImmiTemplateMaster page" + e);
		}
		return msg;
	}
	
	@RequestMapping(value = { "/updateimmitemplate" }, method = RequestMethod.GET)
	public String updateprofile(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		try {
			String profileid = request.getParameter("profileid");
			
			List<ImmiDocumentTypeEntity> documentList = h1bService.getAllDocuments();
			List<DocumentsDTO> subdocumentList = h1bService.getSubDocumentList();
			List<ImmiProfileLableMaster> initiateList = h1bService.getInitiateTemplateList();
			List<DocumentsDTO> templateList = h1bService.getInitiatedTemplates();

			List<ProfileMasterDTO> profileMasterList = h1bService.getProfileListByProfileId(profileid);
			List<ImmiProfileMasterCheckList> profileListSize = h1bService.getProfileMasterListById(profileid);

			String profileName = h1bService.getProfileNameById(profileid);
			String isdependent = h1bService.getProfileIsDependentById(profileid);
			ProfileMasterDTO masterdto = new ProfileMasterDTO();
			masterdto.setProfilename(profileName);
			masterdto.setIsdependent(isdependent);
			masterdto.setProfileMasterList(profileMasterList);
			masterdto.setProfileid(Integer.parseInt(profileid));
			
			model.addAttribute("templateList", templateList);
			model.addAttribute("initiateList", initiateList);
			model.addAttribute("documentList", documentList);
			model.addAttribute("subdocumentList", subdocumentList);
			model.addAttribute("masterdto", masterdto);
			model.addAttribute("profileListSize", profileListSize.size());

		} catch (Exception e) {
			logger.error("Error in  updateimmitemplate page" + e);
		}
		return "UpdateImmiTemplateMaster";
	}
	@RequestMapping(value = { "/getImmiProfileMasterListById" }, method = RequestMethod.GET)
	@ResponseBody
	public List<ImmiProfileMasterCheckList> getProfileMasterListById(Model model, HttpServletRequest request) {
		List<ImmiProfileMasterCheckList> profileList = null;
		try {
			String profile_id = request.getParameter("profile_id");
			profileList = h1bService.getProfileMasterListById(profile_id);

		} catch (Exception e) {
			logger.error("Error in getImmiProfileMasterListById" + e);
		}
		return profileList;
	}
	@RequestMapping(value = { "/updateimmitemplate" }, method = RequestMethod.POST)
	@ResponseBody
	public String updateprofilemaster(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Integer lgempno = (Integer) request.getSession().getAttribute("empno");
		String msg = "";
		try {
			String profile_id = request.getParameter("profile_id");
			String profilename = request.getParameter("profilename");
			String isdependent = request.getParameter("isdependent");
			String[] subcatArray = request.getParameterValues("subcatArray");
			String[] uncheckArray = request.getParameterValues("uncheckArray");
			Integer profileid = 0;
			if (profile_id != "") {
				profileid = Integer.parseInt(profile_id);
			}
			h1bService.updateprofileName(profile_id, profilename,isdependent);
			if (subcatArray.length > 0) {
				String subcatString = null;
				if (subcatArray.length > 0) {
					for (int i = 0; i < subcatArray.length; i++) {
						subcatString = subcatArray[i];
						String[] subcategories = subcatString.split(",");
						for (String subcategoryId : subcategories) {
							msg = h1bService.updateprofilemaster(profileid, subcategoryId, lgempno);
						}
					}
				}
			}
			if (uncheckArray.length > 0) {
				List<Integer> commonSubcatList = new ArrayList<Integer>();
				List<Integer> profilesubList = h1bService.getSubCategoriesByProfileId(profile_id);
				List<Integer> unchecklist = new ArrayList<Integer>();
				String subString = null;
				Integer scatid = 0;
				for (int i = 0; i < uncheckArray.length; i++) {
					subString = uncheckArray[i];
					String[] subcategories = subString.split(",");
					for (String subcategoryId : subcategories) {
						if (!subcategoryId.isEmpty()) {
							scatid = Integer.parseInt(subcategoryId);
							h1bService.deleteMasterListById(profileid, scatid);
						}
					}
					msg = "Template Updated Successfully";
				}
				
			}


		} catch (Exception e) {
			logger.error("Error in get updateimmitemplate page" + e);
		}
		return msg;
	}
	@RequestMapping(value = { "/getimmidocumentlist" }, method = RequestMethod.GET)
	@ResponseBody
	public ContractsPanelDetails_DTO getimmidocumentlist(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String userName = (String) request.getSession().getAttribute("username");		
		ContractsPanelDetails_DTO docList=null;
		try {
			if(userName!=null) {
			String candidateid = request.getParameter("candidateid");
			docList = h1bService.getimmidocumentlist(candidateid);
			
			}else {
				docList =null;
			}
		} catch (Exception e) {
			logger.error("Error in getimmidocumentlist " + e);
		}
		return docList;
	}
	@RequestMapping(value = { "/getinitiatebycandidate" }, method = RequestMethod.GET)
	@ResponseBody
	public CandidateH1BInitiateDetailsDto getinitiatebycandidate(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String userName = (String) request.getSession().getAttribute("username");		
		CandidateH1BInitiateDetailsDto initiateDto=null;
		try {
			if(userName!=null) {
			String candidateid = request.getParameter("candidateid");
			initiateDto = h1bService.getinitiatebycandidate(candidateid);
			}else {
				initiateDto =null;
			}
		} catch (Exception e) {
			logger.error("Error in getinitiatebycandidate " + e);
		}
		return initiateDto;
	}
	/*By Purna*/
	
	@RequestMapping(value = { "/savepublicbenefits" }, method = RequestMethod.POST)
	//@ResponseBody
	public String savepublicbenefits(Model model, HttpServletRequest request, HttpServletResponse response) {
		Integer candidateid = (Integer) request.getSession().getAttribute("candidateid");
		
		String msg = "";
		try {
		/*	String applicantincome = request.getParameter("income_maintenance");
			String spouseincome = request.getParameter("spouse_income_maintenance");*/
			String[] benifit_id=request.getParameterValues("benifit_id");
			if (benifit_id!=null) {
				for (int t = 0; t < benifit_id.length; t++) {
					Integer id=Integer.parseInt(benifit_id[t]);
					String income=request.getParameter("income_maintenance"+id);
					String ssi = request.getParameter("ssi"+id);
					String tanf = request.getParameter("tanf"+id);
					String ga = request.getParameter("ga"+id);
					String snap = request.getParameter("snap"+id);
					String housing_assistance = request.getParameter("housing_assistance"+id);
					String project_rental_assistance = request.getParameter("project_rental_assistance"+id);
					String public_housing_act = request.getParameter("public_housing_act"+id);
					String ffm_medicalemergency = request.getParameter("ffm_medicalemergency"+id);
					String ffm_ideact = request.getParameter("ffm_ideact"+id);
					String ffm_schoolbenefits = request.getParameter("ffm_schoolbenefits"+id);
					String ffm_uderage21 = request.getParameter("ffm_uderage21"+id);
					String ffm_pregnant_period60 = request.getParameter("ffm_pregnant_period60"+id);
					//String income_maintenance = kids_income_maintenances[t];
					h1bService.updatebenefitsKids(income,ssi,tanf,ga,snap,housing_assistance,project_rental_assistance,public_housing_act,ffm_medicalemergency,ffm_ideact,ffm_schoolbenefits,ffm_uderage21,ffm_pregnant_period60,id);
				}
			}
			
			//String kidsincome = request.getParameter("kids_income_maintenance");
			//System.out.println("***applicantincome****"+applicantincome+"***spouseincome****"+spouseincome+"***kidsincome****"+kidsincome);			
			
			
			/*String ssi = request.getParameter("ssi");
			String spouse_ssi = request.getParameter("spouse_ssi");
		
						
			String tanf = request.getParameter("tanf");
			String spouse_tanf = request.getParameter("spouse_tanf");
			
					
			String ga = request.getParameter("ga");
			String spouse_ga = request.getParameter("spouse_ga");
		
						
			String snap = request.getParameter("snap");
			String spouse_snap = request.getParameter("spouse_snap");
			
					
			String housing_assistance = request.getParameter("housing_assistance");
			String spouse_housing_assistance = request.getParameter("spouse_housing_assistance");
			
			
			String project_rental_assistance = request.getParameter("project_rental_assistance");
			String spouse_project_rental_assistance = request.getParameter("spouse_project_rental_assistance");
			
			
						
			String public_housing_act = request.getParameter("public_housing_act");
			String spouse_public_housing_act = request.getParameter("spouse_public_housing_act");
			
									
			String ffm_medicalemergency = request.getParameter("ffm_medicalemergency");
			String spouse_ffm_medicalemergency = request.getParameter("spouse_ffm_medicalemergency");
		

			String ffm_ideact = request.getParameter("ffm_ideact");
			String spouse_ffm_ideact = request.getParameter("spouse_ffm_ideact");
			

			String ffm_schoolbenefits = request.getParameter("ffm_schoolbenefits");
			String spouse_ffm_schoolbenefits = request.getParameter("spouse_ffm_schoolbenefits");
			
	
			String ffm_uderage21 = request.getParameter("ffm_uderage21");
			String spouse_ffm_uderage21 = request.getParameter("spouse_ffm_uderage21");
		

			String ffm_pregnant_period60 = request.getParameter("ffm_pregnant_period60");
			String spouse_ffm_pregnant_period60 = request.getParameter("spouse_ffm_pregnant_period60");*/
			//Integer i = h1bService.savebenefits(applicantincome,spouseincome,ssi,spouse_ssi,tanf,spouse_tanf,ga,spouse_ga,snap,spouse_snap,housing_assistance,spouse_housing_assistance,project_rental_assistance,spouse_project_rental_assistance,public_housing_act,spouse_public_housing_act,ffm_medicalemergency,spouse_ffm_medicalemergency,ffm_ideact,spouse_ffm_ideact,ffm_schoolbenefits,spouse_ffm_schoolbenefits,ffm_uderage21,spouse_ffm_uderage21,ffm_pregnant_period60,spouse_ffm_pregnant_period60,candidateid);
			//if(i>0) {
				msg="Details added succesfully";
				PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('"+msg+"');");
                out.println("window.location='publicbenifitschecklist'");
                out.println("</script>");out.close();
			/*}
			else {
				msg="Details not added";
				
				PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('"+msg+"');");
                out.println("window.location='publicbenifitschecklist'");
                out.println("</script>");out.close();
			
			}*/
		} catch (Exception e) {
			logger.error("Error in save public benefits " + e.getMessage());
		}
		return "PublicBenifitsChecklist";
	}
	@RequestMapping(value = { "/updatepublicbenefits" }, method = RequestMethod.POST)
//	@ResponseBody
	public void updatepublicbenefits(Model model, HttpServletRequest request, HttpServletResponse response) {
		Integer candidateid = (Integer) request.getSession().getAttribute("candidateid");
		HttpSession session = request.getSession(true);
		try {
		
			String applicantincome = request.getParameter("income_maintenance");
			String spouseincome = request.getParameter("spouse_income_maintenance");
			//String kidsincome = request.getParameter("kids_income_maintenance");
						
			String ssi = request.getParameter("ssi");
			String spouse_ssi = request.getParameter("spouse_ssi");
			//String kids_ssi = request.getParameter("kids_ssi");
						
			String tanf = request.getParameter("tanf");
			String spouse_tanf = request.getParameter("spouse_tanf");
			//String kids_tanf = request.getParameter("kids_tanf");
					
			String ga = request.getParameter("ga");
			String spouse_ga = request.getParameter("spouse_ga");
			//String kids_ga = request.getParameter("kids_ga");
						
			String snap = request.getParameter("snap");
			String spouse_snap = request.getParameter("spouse_snap");
			//String kids_snap = request.getParameter("kids_snap");
					
			String housing_assistance = request.getParameter("housing_assistance");
			String spouse_housing_assistance = request.getParameter("spouse_housing_assistance");
			//String kids_housing_assistance = request.getParameter("kids_housing_assistance");
			
			String project_rental_assistance = request.getParameter("project_rental_assistance");
			String spouse_project_rental_assistance = request.getParameter("spouse_project_rental_assistance");
			//String kids_project_rental_assistance = request.getParameter("kids_project_rental_assistance");
			
						
			String public_housing_act = request.getParameter("public_housing_act");
			String spouse_public_housing_act = request.getParameter("spouse_public_housing_act");
			//String kids_public_housing_act = request.getParameter("kids_public_housing_act");
									
			String ffm_medicalemergency = request.getParameter("ffm_medicalemergency");
			String spouse_ffm_medicalemergency = request.getParameter("spouse_ffm_medicalemergency");
			//String kids_ffm_medicalemergency = request.getParameter("kids_ffm_medicalemergency");

			String ffm_ideact = request.getParameter("ffm_ideact");
			String spouse_ffm_ideact = request.getParameter("spouse_ffm_ideact");
			//String kids_ffm_ideact = request.getParameter("kids_ffm_ideact");

			String ffm_schoolbenefits = request.getParameter("ffm_schoolbenefits");
			String spouse_ffm_schoolbenefits = request.getParameter("spouse_ffm_schoolbenefits");
			//String kids_ffm_schoolbenefits = request.getParameter("kids_ffm_schoolbenefits");
	
			String ffm_uderage21 = request.getParameter("ffm_uderage21");
			String spouse_ffm_uderage21 = request.getParameter("spouse_ffm_uderage21");
			//String kids_ffm_uderage21 = request.getParameter("kids_ffm_uderage21");

			String ffm_pregnant_period60 = request.getParameter("ffm_pregnant_period60");
			String spouse_ffm_pregnant_period60 = request.getParameter("spouse_ffm_pregnant_period60");
			//String kids_ffm_pregnant_period60 = request.getParameter("kids_ffm_pregnant_period60");
			
			String[] benefits_id=request.getParameterValues("benefits_id");
			if (benefits_id != null) {
				for (int t = 0; t < benefits_id.length; t++) {
					Integer id=Integer.parseInt(benefits_id[t]);
					String kidsincome=request.getParameter("kids_income_maintenance"+id);
					String kids_ssi = request.getParameter("kids_ssi"+id);
					String kids_tanf = request.getParameter("kids_tanf"+id);
					String kids_ga = request.getParameter("kids_ga"+id);
					String kids_snap = request.getParameter("kids_snap"+id);
					String kids_housing_assistance = request.getParameter("kids_housing_assistance"+id);
					String kids_project_rental_assistance = request.getParameter("kids_project_rental_assistance"+id);
					String kids_public_housing_act = request.getParameter("kids_public_housing_act"+id);
					String kids_ffm_medicalemergency = request.getParameter("kids_ffm_medicalemergency"+id);
					String kids_ffm_ideact = request.getParameter("kids_ffm_ideact"+id);
					String kids_ffm_schoolbenefits = request.getParameter("kids_ffm_schoolbenefits"+id);
					String kids_ffm_uderage21 = request.getParameter("kids_ffm_uderage21"+id);
					String kids_ffm_pregnant_period60 = request.getParameter("kids_ffm_pregnant_period60"+id);
					//String kids_income_maintenance = kids_income_maintenances[t];
					System.out.println(id+"***kidsincome****"+kidsincome);
					h1bService.updatebenefitsKids(kidsincome,kids_ssi,kids_tanf,kids_ga,kids_snap,kids_housing_assistance,kids_project_rental_assistance,kids_public_housing_act,kids_ffm_medicalemergency,kids_ffm_ideact,kids_ffm_schoolbenefits,kids_ffm_uderage21,kids_ffm_pregnant_period60,id);
				}
			}
		
		//	PublicBenefitsChecklistDTO pbcdbdto=h1bService.updatebenefits(applicantincome,spouseincome,kidsincome,ssi,spouse_ssi,kids_ssi,tanf,spouse_tanf,kids_tanf,ga,spouse_ga,kids_ga,snap,spouse_snap,kids_snap,housing_assistance,spouse_housing_assistance,kids_housing_assistance,project_rental_assistance,spouse_project_rental_assistance,kids_project_rental_assistance,public_housing_act,spouse_public_housing_act,kids_public_housing_act,ffm_medicalemergency,spouse_ffm_medicalemergency,kids_ffm_medicalemergency,ffm_ideact,spouse_ffm_ideact,kids_ffm_ideact,ffm_schoolbenefits,spouse_ffm_schoolbenefits,kids_ffm_schoolbenefits,ffm_uderage21,spouse_ffm_uderage21,kids_ffm_uderage21,ffm_pregnant_period60,spouse_ffm_pregnant_period60,kids_ffm_pregnant_period60,candidateid);
			PublicBenefitsChecklistDTO pbcdbdto=h1bService.updatebenefits(applicantincome,spouseincome,ssi,spouse_ssi,tanf,spouse_tanf,ga,spouse_ga,snap,spouse_snap,housing_assistance,spouse_housing_assistance,project_rental_assistance,spouse_project_rental_assistance,public_housing_act,spouse_public_housing_act,ffm_medicalemergency,spouse_ffm_medicalemergency,ffm_ideact,spouse_ffm_ideact,ffm_schoolbenefits,spouse_ffm_schoolbenefits,ffm_uderage21,spouse_ffm_uderage21,ffm_pregnant_period60,spouse_ffm_pregnant_period60,candidateid);
			
			String msg="";
			if(pbcdbdto!=null) {
				msg="Details updated succesfully";
			}else {
				msg="Details not updated";
			}
			PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('"+msg+"');");
            out.println("window.location='updatepbc'");
            out.println("</script>");out.close();
			session.setAttribute("pbcDto", pbcdbdto);
		} catch (Exception e) {
			logger.error("Error in update public benefits " + e.getMessage());
		}
		//return "PublicBenifitsChecklist";
		return ;
	}
	@RequestMapping(value = { "/updatepbc" }, method = RequestMethod.GET)
	public String updatepbc(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		try {
			if (userName == null) {
				return "redirect:/loginpage"; 
			} else {
				String candId = request.getParameter("candidateId");
				HttpSession session = request.getSession(true);
			/*	List<DocumentListMasterEntity> docList = immiService.getdocumentlist(candId);*/
				List<PublicBenefitsChecklistEntity> pbcsize = h1bService.getPublicBenefitsSizeByCanId(Integer.parseInt(candId));
				PublicBenefitsChecklistDTO pbcDto = h1bService.PublicBenefitsByCanId(Integer.parseInt(candId));
				session.setAttribute("pbcDto", pbcDto);
				session.setAttribute("pbcsize", pbcsize);
				session.setAttribute("candId", candId);
				/*session.setAttribute("docList", docList);*/
				session.setAttribute("myimmidocs", null);
				session.setAttribute("candidates", null);
				session.setAttribute("templates", null);
				session.setAttribute("stsreports", null);
				session.setAttribute("timesheet", null);
				session.setAttribute("bgc", null);
			 }
		} catch (Exception e) {
			logger.error("Error in ImmigrationController method updatepbc " + e);
		}
		return "PublicBenifitsChecklist";
	}

	@RequestMapping(value = { "/updateimmiformstaus" }, method = RequestMethod.POST)
	//@ResponseBody
	public String saveadminstatus(Model model, HttpServletRequest request, HttpServletResponse response) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer lgempno = (Integer) request.getSession().getAttribute("empno"); //admin
		Integer logincanid = (Integer) request.getSession().getAttribute("loginUserid"); //admin
		try {
			if (userName == null) {
				return "redirect:/loginpage"; 
			} else {
				String approveStatus = request.getParameter("approveStatus");
				String comment = request.getParameter("comment");
				String candidate_id = request.getParameter("candidate_id");
				String form_type = request.getParameter("form_type");
				String message = h1bService.updateimmiformstaus(approveStatus,lgempno,candidate_id,form_type,comment,logincanid);
					PrintWriter out = response.getWriter();
	                out.println("<script type=\"text/javascript\">");
	                out.println("window.location='updatepbc?candidateId="+candidate_id+"'");
	                out.println("alert('"+message+"');");
	                out.println("</script>");out.close();
			}
		} catch (Exception e) {
			logger.error("Error in save admin status " + e.getMessage());
		}
		return "PublicBenifitsChecklist" ;
	}
	@RequestMapping(value = { "/viewCommentHistorybyCandId" }, method = RequestMethod.GET)
	@ResponseBody
	public List<ImmicommentsDTO> viewCommentHistoryByChkListId(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		List<ImmicommentsDTO> commentHistoryList = null;
		String candId = request.getParameter("candId");
		String form_type = request.getParameter("form_type");
		
		commentHistoryList = h1bService.viewCommentHistoryBycandId(candId,form_type);
		return commentHistoryList;
	}
	
	@RequestMapping(value = { "/publicbenifitschecklist" }, method = RequestMethod.GET)
	public String PublicBenifitsChecklis(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer candidateid = (Integer) request.getSession().getAttribute("candidateid");
		try {
			if (userName == null) {
				return "redirect:/loginpage";
			} else {
				HttpSession session = request.getSession(true);
				List<PublicBenefitsChecklistEntity> pbcsize = h1bService.getPublicBenefitsSizeByCanId(candidateid);
				PublicBenefitsChecklistDTO pbcDto = h1bService.PublicBenefitsByCanId(candidateid);
				session.setAttribute("pbcDto", pbcDto);
				session.setAttribute("pbcsize", pbcsize);
				/*List<DocumentListMasterEntity> docList = immiService.getdocumentlist(candidateid.toString());
				session.setAttribute("docList", docList);
				session.setAttribute("myimmidocs", "myimmidocs");
				session.setAttribute("candidates", null);
				session.setAttribute("templates", null);
				session.setAttribute("stsreports", null);
				session.setAttribute("timesheet", null);
				session.setAttribute("bgc", null);*/
			 }
		} catch (Exception e) {
			logger.error("Error in ImmigrationController method PublicBenifitsChecklist " + e);
		}
		return "PublicBenifitsChecklist";
	}
	
	/* By Prashant starts */
	
	
	@RequestMapping(value = { "/clientvendorquestionnaire" }, method = RequestMethod.GET)
	public String ClientVendorQuestionnaire(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer candidateid = (Integer) request.getSession().getAttribute("candidateid");
		try {
			if (userName == null) {
				return "redirect:/loginpage";
			} else {
				ClientVendorLetterQueDto cvlqDto = h1bService.getClientVendorLetterQuestionarieById(candidateid);
				model.addAttribute("cvlqdto", cvlqDto);
				ArrayList<String> clientTypelist = new ArrayList<String>();
				clientTypelist.add("Primary Vendor");
				clientTypelist.add("Secondary Vendor");
				clientTypelist.add("MSP");
				clientTypelist.add("Implementation Partner");
				clientTypelist.add("Vendor Name");
				clientTypelist.add("EndClient Name");
				model.addAttribute("clientTypelist", clientTypelist);
			 }
		} catch (Exception e) {
			logger.error("Error in ImmigrationController method ClientVendorQuestionnaire " + e);
		}
		return "ClientVendorQuestionnaire";
	}
	
	@RequestMapping(value = { "/h1bquestionnaire" }, method = RequestMethod.GET)
	public String H1BQuestionnaire(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer candidateid = (Integer) request.getSession().getAttribute("candidateid");
		try {
			if (userName == null) {
				return "redirect:/loginpage";
			 } else {
				ClientVendorLetterQueDto questionnarieDto=h1bService.getAllQuestionariesByCandidateId(candidateid);
				model.addAttribute("questionnarieDto", questionnarieDto);
				ArrayList<String> immiStatusList = new ArrayList<String>();
				immiStatusList.add("H-1B");
				immiStatusList.add("H-4");
				immiStatusList.add("F-1");
				immiStatusList.add("L-1");
				immiStatusList.add("J-1");
				immiStatusList.add("B-1/B-2");
				immiStatusList.add("O-1");
				immiStatusList.add("P-1");
				immiStatusList.add("TN");
				model.addAttribute("immiStatusList", immiStatusList);
			 }
		} catch (Exception e) {
			logger.error("Error in ImmigrationController method H1BQuestionnaire " + e);
		}
		return "H1BQuestionnaire";
	}
	
	@RequestMapping(value = { "/saveh1bapplicant" }, method = RequestMethod.POST)
	@ResponseBody
	public String saveh1bapplicant(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer candidateid = (Integer) request.getSession().getAttribute("candidateid");
		String msg = null;
		try {
			if(userName!=null) {
			String familyName = request.getParameter("familyName");
			String firstName = request.getParameter("firstName");
			String middleName = request.getParameter("middleName");
			String countryofcitizenship = request.getParameter("countryofcitizenship");
			String telephoneNo = request.getParameter("telephoneNo");
			String ssnNo = request.getParameter("ssnNo");
			String i94No = request.getParameter("i94No");
			String dob = request.getParameter("dob");
			String countryofbirth = request.getParameter("countryofbirth");
			String cityofbirth = request.getParameter("cityofbirth");
			String yearsofItExp = request.getParameter("yearsofItExp");
			String highestEdu = request.getParameter("highestEdu");
			String specialization = request.getParameter("specialization");
			String officialemailid = request.getParameter("officialemailid");
			String personalemailid = request.getParameter("personalemailid");
			String passportNo = request.getParameter("passportNo");
			String passportissueDate = request.getParameter("passportissueDate");
			String passportexpDate = request.getParameter("passportexpDate");
			String line1 = request.getParameter("line1");
			String line2 = request.getParameter("line2");
			String zipcode = request.getParameter("zipcode");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String permanentAddr = request.getParameter("permanentAddr");
			String immigrantstatus = request.getParameter("immigrantstatus");
			String dateoffirstEntry = request.getParameter("dateoffirstEntry");
			String stausoffirstEntry = request.getParameter("stausoffirstEntry");
			String dateofrecentEntry = request.getParameter("dateofrecentEntry");
			String stausofrecentEntry = request.getParameter("stausofrecentEntry");
			String visastamping = request.getParameter("visastamping");
			String petitionfiled_behalf = request.getParameter("petitionfiled_behalf");
			//String[] arrIds = request.getParameterValues("arrId");
			String[] arrivalDates = request.getParameterValues("arrivalDate[]");
			String[] departureDates = request.getParameterValues("departureDate[]");
			String[] arrivalVisas = request.getParameterValues("arrivalVisa[]");
			String[] departureVisas = request.getParameterValues("departureVisa[]");
			Integer h1queid = h1bService.saveH1bApplDeatils(familyName, firstName, middleName, countryofcitizenship, telephoneNo,
					ssnNo, i94No, countryofbirth, cityofbirth, yearsofItExp, highestEdu, specialization,
					officialemailid, personalemailid, passportNo, passportissueDate, passportexpDate,
					line1, line2, zipcode, city, state, permanentAddr, immigrantstatus, dob ,
					dateoffirstEntry, stausoffirstEntry, dateofrecentEntry, stausofrecentEntry,visastamping,petitionfiled_behalf,candidateid);
			//System.out.println("arrId********"+arrIds.length);
			if(h1queid>0) {
				msg="Success";
				if (arrivalDates != null) {
					for (int t = 1; t < arrivalDates.length; t++) {
						//System.out.println("arrId********"+arrIds[t]);
						//if(!arrIds[t].isEmpty()) {
						String arrivalDate = arrivalDates[t];
						String arrivalVisa = arrivalVisas[t];
						String departureDate = departureDates[t];
						String departureVisa = departureVisas[t];
						String beneficiary_type="Applicant";
						h1bService.saveH1bEntryExitDetails(arrivalDate, arrivalVisa, departureDate, departureVisa, h1queid, beneficiary_type, candidateid);
					  //   }
						/*String arrivalDate = request.getParameter("arrivalDate"+t);
						String departureDate = request.getParameter("departureDate"+t);
						String arrivalVisa = request.getParameter("arrivalVisa"+t);
						String departureVisa = request.getParameter("departureVisa"+t);*/
						/*String arrivalDate = request.getParameter("arrivalDate["+t+"]");
						String departureDate = request.getParameter("departureDate["+t+"]");
						String arrivalVisa = request.getParameter("arrivalVisa["+t+"]");
						String departureVisa = request.getParameter("departureVisa["+t+"]");*/
						
						/*String[] arrivalVisas = request.getParameterValues("arrivalVisa[]");
						String[] departureDates = request.getParameterValues("departureDate[]");
						String[] departureVisas = request.getParameterValues("departureVisa[]");

						
						String arrivalDate = arrivalDates[t];
						String arrivalVisa = arrivalVisas[t];
						String departureDate = departureDates[t];
						String departureVisa = departureVisas[t];
						String beneficiary_type="Applicant";
					
						System.out.println("arrivalVisa********"+arrivalVisa);
						System.out.println("departureVisa********"+departureVisa);
						System.out.println("departureDate********"+departureDate);*/
						//h1bService.saveH1bEntryExitDetails(arrivalDate, arrivalVisa, departureDate, departureVisa, h1queid, beneficiary_type, candidateid);
					 }
				  }
			  }else {
				msg="Error";	
			   }
			}else {
				msg =null;
			}
		} catch (Exception e) {
			logger.error("Error in saveh1bApplQueDetails " + e);
		}
		return msg;
	}
	
	@RequestMapping(value = { "/saveh1bspouse" }, method = RequestMethod.POST)
	@ResponseBody
	public String saveh1bspouse(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer candidateid = (Integer) request.getSession().getAttribute("candidateid");
		String msg = null;
		try {
			if(userName!=null) {
			String familyName = request.getParameter("spousefamilyName");
			String firstName = request.getParameter("spousefirstName");
			String middleName = request.getParameter("spousemiddleName");
			String countryofcitizenship = request.getParameter("spousecountryofcitizenship");
			String telephoneNo = request.getParameter("spousetelephoneNo");
			String ssnNo = request.getParameter("spousessnNo");
			String i94No = request.getParameter("spousei94No");
			String dob = request.getParameter("spousedob");
			String countryofbirth = request.getParameter("spousecountryofbirth");
			String cityofbirth = request.getParameter("spousecityofbirth");
			String personalemailid = request.getParameter("spousepersonalemailid");
			String passportNo = request.getParameter("spousepassportNo");
			String passportissueDate = request.getParameter("spousepassportissueDate");
			String passportexpDate = request.getParameter("spousepassportexpDate");
			String line1 = request.getParameter("spouseline1");
			String line2 = request.getParameter("spouseline2");
			String zipcode = request.getParameter("spousezipcode");
			String city = request.getParameter("spousecity");
			String state = request.getParameter("spousestate");
			String permanentAddr = request.getParameter("spousepermanentAddr");
			String immigrantstatus = request.getParameter("spouseimmigrantstatus");
			String dateofrecentEntry = request.getParameter("spousedateofrecentEntry");
			String stausofrecentEntry = request.getParameter("spousestausofrecentEntry");
			String[] arrivalDates = request.getParameterValues("spousearrivalDate[]");
			String[] arrivalVisas = request.getParameterValues("spousearrivalVisa[]");
			String[] departureDates = request.getParameterValues("spousedepartureDate[]");
			String[] departureVisas = request.getParameterValues("spousedepartureVisa[]");
			String beneficiary_type="Spouse";
			Integer h1queid = h1bService.saveH1bSpouseChildlDeatils(familyName, firstName, middleName, countryofcitizenship, telephoneNo,
					ssnNo, i94No, countryofbirth, cityofbirth, personalemailid, passportNo, passportissueDate, passportexpDate,
					line1, line2, zipcode, city, state, permanentAddr, immigrantstatus, dob, dateofrecentEntry, stausofrecentEntry,candidateid,beneficiary_type);
			if(h1queid>0) {
				msg="Success";
				if (arrivalDates!=null) {
					System.out.println("arrivalDates********"+arrivalDates.length);
					for (int t = 1; t < arrivalDates.length; t++) {
						System.out.println("arrivalDates********"+arrivalDates[t]);
						String arrivalDate = arrivalDates[t];
						String arrivalVisa = arrivalVisas[t];
						String departureDate = departureDates[t];
						String departureVisa = departureVisas[t];
						System.out.println("arrivalDates********"+arrivalDate);
						h1bService.saveH1bEntryExitDetails(arrivalDate, arrivalVisa, departureDate, departureVisa, h1queid, beneficiary_type, candidateid);
					}
				}
			  }else {
				msg="Error";	
			   }
			
			}else {
				msg =null;
			}
		} catch (Exception e) {
			logger.error("Error in saveh1bspouse Details " + e);
		}
		return msg;
	}
	
	@RequestMapping(value = { "/saveh1bchild" }, method = RequestMethod.POST)
	@ResponseBody
	public String saveh1bchild(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer candidateid = (Integer) request.getSession().getAttribute("candidateid");
		String msg = null;
		try {
			if(userName!=null) {
			String familyName = request.getParameter("childfamilyName");
			String firstName = request.getParameter("childfirstName");
			String middleName = request.getParameter("childmiddleName");
			String countryofcitizenship = request.getParameter("childcountryofcitizenship");
			String telephoneNo = request.getParameter("childtelephoneNo");
			String ssnNo = request.getParameter("childssnNo");
			String i94No = request.getParameter("childi94No");
			String dob = request.getParameter("childdob");
			String countryofbirth = request.getParameter("childcountryofbirth");
			String cityofbirth = request.getParameter("childcityofbirth");
			String personalemailid = request.getParameter("childpersonalemailid");
			String passportNo = request.getParameter("childpassportNo");
			String passportissueDate = request.getParameter("childpassportissueDate");
			String passportexpDate = request.getParameter("childpassportexpDate");
			String line1 = request.getParameter("childline1");
			String line2 = request.getParameter("childline2");
			String zipcode = request.getParameter("childzipcode");
			String city = request.getParameter("childcity");
			String state = request.getParameter("childstate");
			String permanentAddr = request.getParameter("childpermanentAddr");
			String immigrantstatus = request.getParameter("childimmigrantstatus");
			String dateofrecentEntry = request.getParameter("childdateofrecentEntry");
			String stausofrecentEntry = request.getParameter("childstausofrecentEntry");
			String[] arrivalDates = request.getParameterValues("childarrivalDate[]");
			String[] arrivalVisas = request.getParameterValues("childarrivalVisa[]");
			String[] departureDates = request.getParameterValues("childdepartureDate[]");
			String[] departureVisas = request.getParameterValues("childdepartureVisa[]");
			String beneficiary_type="Kids";
			System.out.println("stausofrecentEntry"+stausofrecentEntry);
			Integer h1queid = h1bService.saveH1bSpouseChildlDeatils(familyName, firstName, middleName, countryofcitizenship, telephoneNo,
					ssnNo, i94No, countryofbirth, cityofbirth, personalemailid, passportNo, passportissueDate, passportexpDate,
					line1, line2, zipcode, city, state, permanentAddr, immigrantstatus, dob, dateofrecentEntry, stausofrecentEntry,candidateid,beneficiary_type);
			if(h1queid>0) {
				msg="Success";
				if (arrivalDates != null) {
					for (int t = 1; t < arrivalDates.length; t++) {
						String arrivalDate = arrivalDates[t];
						String arrivalVisa = arrivalVisas[t];
						String departureDate = departureDates[t];
						String departureVisa = departureVisas[t];
						h1bService.saveH1bEntryExitDetails(arrivalDate, arrivalVisa, departureDate, departureVisa, h1queid, beneficiary_type, candidateid);
					}
				}
			  }else {
				msg="Error";	
			   }
			
			}else {
				msg =null;
			}
		} catch (Exception e) {
			logger.error("Error in saveh1bchild Details " + e);
		}
		return msg;
	}
	
	@RequestMapping(value = { "/saveclientvendorletterquestionnaire" }, method = RequestMethod.POST)
	@ResponseBody
	public String saveclientvendorletterquestionnaire(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer candidateid = (Integer) request.getSession().getAttribute("candidateid");
		String msg = null;
		try {
			if(userName!=null) {
				String remote_line1 = null;
				String remote_line2 = null;
				String remote_city = null;
				String remote_state = null;
				String remote_zipcode = null;	
				
			String benef_full_name = request.getParameter("benef_full_name");
			String benef_job_title = request.getParameter("benef_job_title");
			String startdate = request.getParameter("startdate");
			String enddate = request.getParameter("enddate");
			String client_name = request.getParameter("client_name");
			String line1 = request.getParameter("line1");
			String line2 = request.getParameter("line2");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String zipcode = request.getParameter("zipcode");
			String workinglocation = request.getParameter("workinglocation");
			 if(workinglocation.equalsIgnoreCase("Remote/Home")) {
				 remote_line1 = request.getParameter("remote_line1");
				 remote_line2 = request.getParameter("remote_line2");
				 remote_city = request.getParameter("remote_city");
				 remote_state = request.getParameter("remote_state");
				 remote_zipcode = request.getParameter("remote_zipcode");
			}
			String jobduties = request.getParameter("jobduties");
			String reportingTo = request.getParameter("reportingTo");
			String reporting_mgr_name = request.getParameter("reporting_mgr_name");
			String reporting_mgr_title = request.getParameter("reporting_mgr_title");
			String reporting_mgr_contact = request.getParameter("reporting_mgr_contact");
			String reporting_mgr_email = request.getParameter("reporting_mgr_email");

			Integer client_id = h1bService.saveClientVendorLetterQuestionnaire(benef_full_name, benef_job_title, startdate, enddate, workinglocation,
					remote_line1, remote_city, remote_state, remote_line2, remote_zipcode,	reportingTo, reporting_mgr_name, reporting_mgr_title, reporting_mgr_contact, reporting_mgr_email,jobduties,
					candidateid, client_name, line1, city, state, line2, zipcode);
			if(client_id>0) {
				msg="Success";
			  }else {
				msg="Error";	
			   }
			
			String[] othr_client_types=request.getParameterValues("othr_client_type[]");
			String[] othr_client_names = request.getParameterValues("othr_client_name[]");
			String[] othr_line1s = request.getParameterValues("othr_line1[]");
			String[] othr_line2s = request.getParameterValues("othr_line2[]");
			String[] othr_citys = request.getParameterValues("othr_city[]");
			String[] othr_states = request.getParameterValues("othr_state[]");
			String[] othr_zipcodes = request.getParameterValues("othr_zipcode[]");

			if (othr_client_names != null) {
				for (int t = 1; t < othr_client_names.length; t++) {
					 String othr_client_type = othr_client_types[t];
					 String othr_client_name = othr_client_names[t];
					 String othr_line1=othr_line1s[t];
	    			 String othr_line2=othr_line2s[t];
	    			 String othr_city=othr_citys[t];
	    			 String othr_state=othr_states[t];
	    			 String othr_zipcode=othr_zipcodes[t];
	    			  h1bService.saveClientVendorLetterQuestionnaireAddress(client_id,othr_client_name, othr_client_type, candidateid, othr_line1,othr_line2,othr_city,othr_state,othr_zipcode);
					   }
				   }
			 }
		} catch (Exception e) {
			logger.error("Error in saveclientvendorletterquestionnaire " + e);
		}
		return msg;
	}
	
	@RequestMapping(value = { "/updateclientvendorletterquestionnaire" }, method = RequestMethod.POST)
	@ResponseBody
	public String updateclientvendorletterquestionnaire(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer candidateid = (Integer) request.getSession().getAttribute("candidateid");
		String msg = null;
		try {
			if(userName!=null) {
		   String clientid=request.getParameter("client_id");
		    Integer client_id=0;
			if(!clientid.isEmpty()) {
				client_id=Integer.parseInt(clientid);
			}
			String remote_line1 = null;
			String remote_line2 = null;
			String remote_city = null;
			String remote_state = null;
			String remote_zipcode = null;	
			String benef_full_name = request.getParameter("ubenef_full_name");
			String benef_job_title = request.getParameter("ubenef_job_title");
			String startdate = request.getParameter("ustartdate");
			String enddate = request.getParameter("uenddate");
			String workinglocation = request.getParameter("uworkinglocation");
			String uaddress_id=request.getParameter("uaddress_id");
			String endclient_name=request.getParameter("uclient_name");
			String line1 = request.getParameter("uline1");
			String line2 = request.getParameter("uline2");
			String city = request.getParameter("ucity");
			String state = request.getParameter("ustate");
			String zipcode = request.getParameter("uzipcode");
			
			 if (workinglocation!=null) {
				 if(workinglocation.equalsIgnoreCase("Remote/Home")) {
				    remote_line1 = request.getParameter("uremote_line1");
				    remote_line2 = request.getParameter("uremote_line2");
				    remote_city = request.getParameter("uremote_city");
				    remote_state = request.getParameter("uremote_state");
				    remote_zipcode = request.getParameter("uremote_zipcode");
				    }
				 }
			
			String jobduties = request.getParameter("ujobduties");
			String reportingTo = request.getParameter("ureportingTo");
			String reporting_mgr_name = request.getParameter("ureporting_mgr_name");
			String reporting_mgr_title = request.getParameter("ureporting_mgr_title");
			String reporting_mgr_contact = request.getParameter("ureporting_mgr_contact");
			String reporting_mgr_email = request.getParameter("ureporting_mgr_email");

			h1bService.updateClientVendorLetterQuestionnaire(benef_full_name, benef_job_title, startdate, enddate, workinglocation,
					remote_line1, remote_line2, remote_city, remote_state,  remote_zipcode, reportingTo, reporting_mgr_name, 
					reporting_mgr_title, reporting_mgr_contact, reporting_mgr_email,jobduties,
					line1, line2, city, state,  zipcode, endclient_name , uaddress_id, client_id);
			msg="Success";
			
			String[] otheraddressid=request.getParameterValues("otheraddress_id");
			String[] otherclientid=request.getParameterValues("otherclientid");
			String[] othrclienttypes=request.getParameterValues("uothrclienttype");
  			String[] client_names=request.getParameterValues("uotherclient_name");
  			String[] othrline1 = request.getParameterValues("uotherline1");
			String[] othrline2 = request.getParameterValues("uotherline2");
			String[] othrcity = request.getParameterValues("uothercity");
			String[] othrstate = request.getParameterValues("uotherstate");
			String[] othrzipcode = request.getParameterValues("uotherzipcode");

  			if(otherclientid!=null) {
  			for(int i=0;i<otherclientid.length;i++){
  				 Integer otheraddress_id=Integer.parseInt(otheraddressid[i]);
    			 String othrclienttype=othrclienttypes[i];
    			 String client_name=client_names[i];
    			 String line11=othrline1[i];
    			 String line21=othrline2[i];
    			 String city1=othrcity[i];
    			 String state1=othrstate[i];
    			 String zipcode1=othrzipcode[i];
    	      h1bService.updateClientVendorLetterQuestionnaireAddress(client_name, othrclienttype, line11, line21, city1, state1, zipcode1,otheraddress_id);
  			   }
  			}
			
			String[] othr_client_types=request.getParameterValues("uothr_client_type[]");
			String[] othr_client_names = request.getParameterValues("uothr_client_name[]");
			String[] othr_line1s = request.getParameterValues("uothr_line1[]");
			String[] othr_line2s = request.getParameterValues("uothr_line2[]");
			String[] othr_citys = request.getParameterValues("uothr_city[]");
			String[] othr_states = request.getParameterValues("uothr_state[]");
			String[] othr_zipcodes = request.getParameterValues("uothr_zipcode[]");

			if (othr_client_names != null) {
				for (int t = 1; t < othr_client_names.length; t++) {
					 String othr_client_type = othr_client_types[t];
					 String othr_client_name = othr_client_names[t];
					 String othr_line1=othr_line1s[t];
	    			 String othr_line2=othr_line2s[t];
	    			 String othr_city=othr_citys[t];
	    			 String othr_state=othr_states[t];
	    			 String othr_zipcode=othr_zipcodes[t];
	    			  h1bService.saveClientVendorLetterQuestionnaireAddress(client_id,othr_client_name, othr_client_type, candidateid, othr_line1,othr_line2,othr_city,othr_state,othr_zipcode);
					 }
				 }
			 }
		} catch (Exception e) {
			logger.error("Error in updateclientvendorletterquestionnaire " + e);
		}
		return msg;
	}
	
	@RequestMapping(value = { "/updateh1bapplicant" }, method = RequestMethod.POST)
	@ResponseBody
	public String updateh1bapplicant(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer candidateid = (Integer) request.getSession().getAttribute("candidateid");
		String msg = null;
		try {
			if(userName!=null) {
		   String applh1que_id=request.getParameter("applh1que_id");
		    Integer applh1queid=0;
			if(!applh1que_id.isEmpty()) {
				applh1queid=Integer.parseInt(applh1que_id);
			}
			
			String familyName = request.getParameter("ufamilyName");
			String firstName = request.getParameter("ufirstName");
			String middleName = request.getParameter("umiddleName");
			String countryofcitizenship = request.getParameter("ucountryofcitizenship");
			String telephoneNo = request.getParameter("utelephoneNo");
			String ssnNo = request.getParameter("ussnNo");
			String i94No = request.getParameter("ui94No");
			String dob = request.getParameter("udob");
			
			String countryofbirth = request.getParameter("ucountryofbirth");
			String cityofbirth = request.getParameter("ucityofbirth");
			String yearsofItExp = request.getParameter("uyearsofItExp");
			String highestEdu = request.getParameter("uhighestEdu");
			String specialization = request.getParameter("uspecialization");
			String officialemailid = request.getParameter("uofficialemailid");
			String personalemailid = request.getParameter("upersonalemailid");
			String passportNo = request.getParameter("upassportNo");
			String passportissueDate = request.getParameter("upassportissueDate");
			String passportexpDate = request.getParameter("upassportexpDate");
			
			String line1 = request.getParameter("uline1");
			String line2 = request.getParameter("uline2");
			String city = request.getParameter("ucity");
			String state = request.getParameter("ustate");
			String zipcode = request.getParameter("uzipcode");
			String permanentAddr = request.getParameter("upermanentAddr");
			
			String immigrantstatus = request.getParameter("uimmigrantstatus");
			String dateoffirstEntry = request.getParameter("udateoffirstEntry");
			String stausoffirstEntry = request.getParameter("ustausoffirstEntry");
			String dateofrecentEntry = request.getParameter("udateofrecentEntry");
			String stausofrecentEntry = request.getParameter("ustausofrecentEntry");
			String visastamping = request.getParameter("uvisastamping");
			String petitionfiled_behalf = request.getParameter("upetitionfiled_behalf");
			
			String[] uarrIds=request.getParameterValues("uarrId");
			System.out.println(uarrIds+"uarrIds.length********"+uarrIds.length);
			
			String[] arrivalDates = request.getParameterValues("auarrivalDate[]");
			String[] arrivalVisas = request.getParameterValues("auarrivalVisa[]");
			String[] departureDates = request.getParameterValues("audepartureDate[]");
			String[] departureVisas = request.getParameterValues("audepartureVisa[]");

			 h1bService.updateH1bApplDeatils(familyName, firstName, middleName, countryofcitizenship, telephoneNo,
					ssnNo, i94No, countryofbirth, cityofbirth, yearsofItExp, highestEdu, specialization,
					officialemailid, personalemailid, passportNo, passportissueDate, passportexpDate,
					line1, line2, zipcode, city, state, permanentAddr, immigrantstatus, dob ,
					dateoffirstEntry, stausoffirstEntry, dateofrecentEntry, stausofrecentEntry,visastamping,petitionfiled_behalf,candidateid,applh1queid);
		//	 System.out.println("arrivalDates.length********"+arrivalDates.length);
			if (uarrIds.length>1) {
					for (int t = 1; t < uarrIds.length; t++) {
						String arrivalDate = arrivalDates[t];
						String arrivalVisa = arrivalVisas[t];
						String departureDate = departureDates[t];
						String departureVisa = departureVisas[t];
						String beneficiary_type="Applicant";
						System.out.println("arrivalDate********"+arrivalDate);
						System.out.println("arrivalVisa********"+arrivalVisa);
						System.out.println("departureVisa********"+departureVisa);
						System.out.println("departureDate********"+departureDate);
					  h1bService.saveH1bEntryExitDetails(arrivalDate, arrivalVisa, departureDate, departureVisa, applh1queid, beneficiary_type, candidateid);
					}
				}
			  msg="Success";
			  
			String[] applEntryids=request.getParameterValues("applEntryid");
			String[] uarrivalDates = request.getParameterValues("uarrivalDate");
			String[] uarrivalVisas = request.getParameterValues("uarrivalVisa");
			String[] udepartureDates = request.getParameterValues("udepartureDate");
			String[] udepartureVisas = request.getParameterValues("udepartureVisa");

  			if(applEntryids!=null) {
  			for(int i=0;i<applEntryids.length;i++){
  				 Integer applEntryid=Integer.parseInt(applEntryids[i]);
    			 String uarrivalDate=uarrivalDates[i];
    			 String uarrivalVisa=uarrivalVisas[i];
    			 String udepartureDate=udepartureDates[i];
    			 String udepartureVisa=udepartureVisas[i];
    	         h1bService.updateH1bEntryExitDetails(uarrivalDate, uarrivalVisa, udepartureDate, udepartureVisa,applEntryid);
  			      }
  			  }
		   }
		} catch (Exception e) {
			logger.error("Error in updateh1bapplicant " + e);
		}
		return msg;
	}
	
	@RequestMapping(value = { "/updateh1bspouse" }, method = RequestMethod.POST)
	@ResponseBody
	public String updateh1bspouse(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer candidateid = (Integer) request.getSession().getAttribute("candidateid");
		String msg = null;
		try {
			if(userName!=null) {
		   String applh1que_id=request.getParameter("spouseh1que_id");
		    Integer applh1queid=0;
			if(!applh1que_id.isEmpty()) {
				applh1queid=Integer.parseInt(applh1que_id);
			}
			
			String familyName = request.getParameter("uspousefamilyName");
			String firstName = request.getParameter("uspousefirstName");
			String middleName = request.getParameter("uspousemiddleName");
			String countryofcitizenship = request.getParameter("uspousecountryofcitizenship");
			String telephoneNo = request.getParameter("uspousetelephoneNo");
			String ssnNo = request.getParameter("uspousessnNo");
			String i94No = request.getParameter("uspousei94No");
			String dob = request.getParameter("uspousedob");
			
			String countryofbirth = request.getParameter("uspousecountryofbirth");
			String cityofbirth = request.getParameter("uspousecityofbirth");
			String personalemailid = request.getParameter("uspousepersonalemailid");
			String passportNo = request.getParameter("uspousepassportNo");
			String passportissueDate = request.getParameter("uspousepassportissueDate");
			String passportexpDate = request.getParameter("uspousepassportexpDate");
			
			String line1 = request.getParameter("uspouseline1");
			String line2 = request.getParameter("uspouseline2");
			String zipcode = request.getParameter("uspousezipcode");
			String city = request.getParameter("uspousecity");
			String state = request.getParameter("uspousestate");
			String permanentAddr = request.getParameter("uspousepermanentAddr");
			
			String immigrantstatus = request.getParameter("uspouseimmigrantstatus");
			String dateofrecentEntry = request.getParameter("uspousedateofrecentEntry");
			String stausofrecentEntry = request.getParameter("uspousestausofrecentEntry");
			String[] arrivalDates = request.getParameterValues("auspousearrivalDate[]");
			String[] arrivalVisas = request.getParameterValues("auspousearrivalVisa[]");
			String[] departureDates = request.getParameterValues("auspousedepartureDate[]");
			String[] departureVisas = request.getParameterValues("auspousedepartureVisa[]");
			String beneficiary_type="Spouse";
			 h1bService.updateH1bSpouseDeatils(familyName, firstName, middleName, countryofcitizenship, telephoneNo,
						ssnNo, i94No, countryofbirth, cityofbirth, personalemailid, passportNo, passportissueDate, passportexpDate,
						line1, line2, zipcode, city, state, permanentAddr, immigrantstatus, dob ,
					   dateofrecentEntry, stausofrecentEntry,candidateid,applh1queid);
	
			if (arrivalDates != null) {
					for (int t = 1; t < arrivalDates.length; t++) {
						String arrivalDate = arrivalDates[t];
						String arrivalVisa = arrivalVisas[t];
						String departureDate = departureDates[t];
						String departureVisa = departureVisas[t];
					  h1bService.saveH1bEntryExitDetails(arrivalDate, arrivalVisa, departureDate, departureVisa, applh1queid, beneficiary_type, candidateid);
					}
				}
		    msg="Success";
			  
			String[] applEntryids=request.getParameterValues("spouseEntryid");
			String[] uarrivalDates = request.getParameterValues("uspousearrivalDate[]");
			String[] uarrivalVisas = request.getParameterValues("uspousearrivalVisa[]");
			String[] udepartureDates = request.getParameterValues("uspousedepartureDate[]");
			String[] udepartureVisas = request.getParameterValues("uspousedepartureVisa[]");

  			if(applEntryids!=null) {
  			for(int i=0;i<applEntryids.length;i++){
  				 Integer applEntryid=Integer.parseInt(applEntryids[i]);
    			 String uarrivalDate=uarrivalDates[i];
    			 String uarrivalVisa=uarrivalVisas[i];
    			 String udepartureDate=udepartureDates[i];
    			 String udepartureVisa=udepartureVisas[i];
    	         h1bService.updateH1bEntryExitDetails(uarrivalDate, uarrivalVisa, udepartureDate, udepartureVisa,applEntryid);
  			      }
  			  }
		   }
		} catch (Exception e) {
			logger.error("Error in updateh1bspouse " + e);
		}
		return msg;
	}
	@RequestMapping(value = { "/getchilddetailsbyid" }, method = RequestMethod.GET)
	@ResponseBody
	public ClientVendorLetterQueDto getchilddetailsbyid(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		ClientVendorLetterQueDto h1bchildDto = null;
		try {
			if(userName!=null) {
			String h1bqueid = request.getParameter("h1bqueid");
			h1bchildDto = h1bService.getchilddetailsbyid(h1bqueid);
			}else {
				h1bchildDto =null;
			}
		} catch (Exception e) {
			logger.error("Error in getchilddetailsbyid " + e);
		}
		return h1bchildDto;
   }
	
	@RequestMapping(value = { "/updateh1bchild" }, method = RequestMethod.POST)
	@ResponseBody
	public String updateh1bchild(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer candidateid = (Integer) request.getSession().getAttribute("candidateid");
		String msg = null;
		try {
			if(userName!=null) {
		   String applh1que_id=request.getParameter("childh1que_id");
		    Integer applh1queid=0;
			if(!applh1que_id.isEmpty()) {
				applh1queid=Integer.parseInt(applh1que_id);
			}
			
			String familyName = request.getParameter("uchildfamilyName");
			String firstName = request.getParameter("uchildfirstName");
			String middleName = request.getParameter("uchildmiddleName");
			String countryofcitizenship = request.getParameter("uchildcountryofcitizenship");
			String telephoneNo = request.getParameter("uchildtelephoneNo");
			String ssnNo = request.getParameter("uchildssnNo");
			String i94No = request.getParameter("uchildi94No");
			String dob = request.getParameter("uchilddob");
			
			String countryofbirth = request.getParameter("uchildcountryofbirth");
			String cityofbirth = request.getParameter("uchildcityofbirth");
			String personalemailid = request.getParameter("uchildpersonalemailid");
			String passportNo = request.getParameter("uchildpassportNo");
			String passportissueDate = request.getParameter("uchildpassportissueDate");
			String passportexpDate = request.getParameter("uchildpassportexpDate");
			
			String line1 = request.getParameter("uchildline1");
			String line2 = request.getParameter("uchildline2");
			String zipcode = request.getParameter("uchildzipcode");
			String city = request.getParameter("uchildcity");
			String state = request.getParameter("uchildstate");
			String permanentAddr = request.getParameter("uchildpermanentAddr");
			
			String immigrantstatus = request.getParameter("uchildimmigrantstatus");
			String dateofrecentEntry = request.getParameter("uchilddateofrecentEntry");
			String stausofrecentEntry = request.getParameter("uchildstausofrecentEntry");
			
			String beneficiary_type="Kids";
			 h1bService.updateH1bSpouseDeatils(familyName, firstName, middleName, countryofcitizenship, telephoneNo,
						ssnNo, i94No, countryofbirth, cityofbirth, personalemailid, passportNo, passportissueDate, passportexpDate,
						line1, line2, zipcode, city, state, permanentAddr, immigrantstatus, dob ,
					   dateofrecentEntry, stausofrecentEntry,candidateid,applh1queid);
	
			String[] arrivalDates = request.getParameterValues("auchildarrivalDate[]");
			String[] arrivalVisas = request.getParameterValues("auchildarrivalVisa[]");
			String[] departureDates = request.getParameterValues("auchilddepartureDate[]");
			String[] departureVisas = request.getParameterValues("auchilddepartureVisa[]");
				
			if (arrivalDates != null) {
					for (int t = 1; t < arrivalDates.length; t++) {
						String arrivalDate = arrivalDates[t];
						String arrivalVisa = arrivalVisas[t];
						String departureDate = departureDates[t];
						String departureVisa = departureVisas[t];
					  h1bService.saveH1bEntryExitDetails(arrivalDate, arrivalVisa, departureDate, departureVisa, applh1queid, beneficiary_type, candidateid);
					}
				}
		    msg="Success";
			   
		   }
		} catch (Exception e) {
			logger.error("Error in updateh1bspouse " + e);
		}
		return msg;
	}
	@RequestMapping(value = { "/updatechildEntryExits" }, method = RequestMethod.POST)
	@ResponseBody
	public String updatechildEntryExits(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		String msg = null;
		try {
			if(userName!=null) {
		   String childEntryid=request.getParameter("childEntryid");
		    Integer Entryid=0;
			if(!childEntryid.isEmpty()) {
				Entryid=Integer.parseInt(childEntryid);
			}
			String arrivalDate = request.getParameter("ucharrivalDate");
			String arrivalVisa = request.getParameter("ucharrivalVisa");
			String departureDate = request.getParameter("uchdepartureDate");
			String departureVisa = request.getParameter("uchdepartureVisa");
		    h1bService.updateH1bEntryExitDetails(arrivalDate, arrivalVisa, departureDate, departureVisa,Entryid);
		    msg="Success";
		   }
		} catch (Exception e) {
			logger.error("Error in updatechildEntryExits " + e);
		}
		return msg;
	}
	
	
	@RequestMapping(value = { "/h1bqueadmin" }, method = RequestMethod.GET)
	public String h1bqueadmin(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		try {
			if (userName == null) {
				return "redirect:/loginpage";
			 } else {
				String candId = request.getParameter("candidateId");
				 Integer candidateid=0;
				if(!candId.isEmpty()) {
						candidateid=Integer.parseInt(candId);
				  }
				ClientVendorLetterQueDto questionnarieDto=h1bService.getAllQuestionariesByCandidateId(candidateid);
				model.addAttribute("questionnarieDto", questionnarieDto);
				ArrayList<String> immiStatusList = new ArrayList<String>();
				immiStatusList.add("H-1B");
				immiStatusList.add("H-4");
				immiStatusList.add("F-1");
				immiStatusList.add("L-1");
				immiStatusList.add("J-1");
				immiStatusList.add("B-1/B-2");
				immiStatusList.add("O-1");
				immiStatusList.add("P-1");
				immiStatusList.add("TN");
				model.addAttribute("immiStatusList", immiStatusList);
				HttpSession session = request.getSession(true);
				session.setAttribute("candId", candId);
				session.setAttribute("myimmidocs", null);
				session.setAttribute("candidates", null);
				session.setAttribute("templates", null);
				session.setAttribute("stsreports", null);
				session.setAttribute("timesheet", null);
				session.setAttribute("bgc", null);
			 }
		} catch (Exception e) {
			logger.error("Error in ImmigrationController method h1bqueadmin " + e);
		}
		return "H1BQuestionnaire";
	}
	
	@RequestMapping(value = { "/cvlqueadmin" }, method = RequestMethod.GET)
	public String cvlqueadmin(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		try {
			if (userName == null) {
				return "redirect:/loginpage";
			} else {
				String candId = request.getParameter("candidateId");
				 Integer candidateid=0;
				if(!candId.isEmpty()) {
						candidateid=Integer.parseInt(candId);
				  }
				ClientVendorLetterQueDto cvlqDto = h1bService.getClientVendorLetterQuestionarieById(candidateid);
				model.addAttribute("cvlqdto", cvlqDto);
				ArrayList<String> clientTypelist = new ArrayList<String>();
				clientTypelist.add("Primary Vendor");
				clientTypelist.add("Secondary Vendor");
				clientTypelist.add("MSP");
				clientTypelist.add("Implementation Partner");
				clientTypelist.add("Vendor Name");
				clientTypelist.add("EndClient Name");
				model.addAttribute("clientTypelist", clientTypelist);
				
				HttpSession session = request.getSession(true);
				session.setAttribute("candId", candId);
				session.setAttribute("myimmidocs", null);
				session.setAttribute("candidates", null);
				session.setAttribute("templates", null);
				session.setAttribute("stsreports", null);
				session.setAttribute("timesheet", null);
				session.setAttribute("bgc", null);
			 }
		} catch (Exception e) {
			logger.error("Error in ImmigrationController method cvlqueadmin " + e);
		}
		return "ClientVendorQuestionnaire";
	}

	@RequestMapping(value = { "/updatecvlimmiformstaus" }, method = RequestMethod.POST)
	@ResponseBody
	public String savecvladminstatus(Model model, HttpServletRequest request, HttpServletResponse response) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer lgempno = (Integer) request.getSession().getAttribute("empno"); //admin
		Integer logincanid = (Integer) request.getSession().getAttribute("loginUserid"); //admin
		String message="";
		try {
			if (userName == null) {
				return "redirect:/loginpage"; 
			} else {
				String approveStatus = request.getParameter("approveStatus");
				String comment = request.getParameter("comment");
				String candidate_id = request.getParameter("candidate_id");
				String form_type = request.getParameter("form_type");
				message = h1bService.updateimmiformstaus(approveStatus,lgempno,candidate_id,form_type,comment,logincanid);
			/*	if(form_type.equalsIgnoreCase("Client Vendor Questionnaire Letter")) {
					PrintWriter out = response.getWriter();
	                out.println("<script type=\"text/javascript\">");
	                out.println("window.location='cvlqueadmin?candidateId="+candidate_id+"'");
	                out.println("alert('"+message+"');");
	                out.println("</script>");out.close();
				}else if(form_type.equalsIgnoreCase("H1B Questionnaries")) {
					PrintWriter out = response.getWriter();
	                out.println("<script type=\"text/javascript\">");
	                out.println("window.location='h1bqueadmin?candidateId="+candidate_id+"'");
	                out.println("alert('"+message+"');");
	                out.println("</script>");out.close();
				 }*/
			}
		} catch (Exception e) {
			logger.error("Error in updatecvlimmiformstaus " + e.getMessage());
		}
		return message ;
	}
	/* By Prashant ends */
	
	
	@RequestMapping(value = { "/h1buploaddetails" }, method = RequestMethod.GET)
	@ResponseBody
	public H1BDetailsDto getlcaquestionaries(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		H1BDetailsDto h1bDetailsDto = null;
		try {
			if(userName!=null) {
			String candidateid = request.getParameter("candidateid");
		/*	h1bDetailsDto = immiService.getlcaquestionaries(candidateid);
			Integer lcaId=immiService.getlcaIdByCandidateClientId(candidateid,clientid);
			enclientDto.setLcaId(lcaId);*/
		//	List<LCADetailsDto> alllcaList = immiService.getAllLCAList(candidateid,clientid);
		//	enclientDto.setPendinglcaList(pendinglcaList);
		//	h1bDetailsDto.setAlllcaList(alllcaList);
			}else {
				h1bDetailsDto =null;
			}
		} catch (Exception e) {
			logger.error("Error in h1buploaddetails " + e);
		}
		return h1bDetailsDto;
	}
	
	@RequestMapping(value = { "/saveh1bdocumentdetails" }, method = RequestMethod.POST)
	@ResponseBody
	public String saveh1bdocumentdetails(Model model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam("ah1document") MultipartFile h1bdocument,
			@RequestParam(value="rfeh1document[]",required=false) CommonsMultipartFile rfeh1document[]) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer lgempno = (Integer) request.getSession().getAttribute("empno");
		Integer lgcandidateid = (Integer) request.getSession().getAttribute("candidateid");
		String message="";
		try {
			if (userName == null) {
				return "redirect:/loginpage"; 
			} else {
				String intiateid = request.getParameter("ah1initiated");
				String candidate_id = request.getParameter("ah1candidateid");
				String doctype = request.getParameter("ah1doctypeid");
				String documentsubtype = request.getParameter("ah1document_subtype");
				String hsubdocname = request.getParameter("ah1subdocname");
				String profileid = request.getParameter("ah1profileid");
				String bentype = request.getParameter("ah1bentype");
				if(hsubdocname.equalsIgnoreCase("H1B Approval Copy")) {
					String h1bRecieptNumber = request.getParameter("h1bNumber");
					String h1bvFromDate = request.getParameter("h1bvFromDate");
					String h1bvToDate = request.getParameter("h1bvToDate");
					String i94Number = request.getParameter("i94Number");
					String i94vFromDate = request.getParameter("i94vFromDate");
					String i94vToDate = request.getParameter("i94vToDate");
					message = h1bService.saveh1bdocumentdetails(documentsubtype,doctype,candidate_id,intiateid,h1bRecieptNumber,h1bvFromDate,h1bvToDate,i94Number,i94vFromDate,i94vToDate,h1bdocument,lgcandidateid,profileid,bentype);
				}else if(hsubdocname.equalsIgnoreCase("H1B RFE Copy")) {
					if(rfeh1document!=null) {
			         	   String[] h1documentInputs=request.getParameterValues("rfeh1documentInput[]");
			         	   message=h1bService.saveH1BDocuments(documentsubtype,doctype,h1documentInputs, rfeh1document,lgcandidateid,candidate_id,intiateid,profileid,bentype);
			           }
				}else if(hsubdocname.equalsIgnoreCase("Receipt copy")){
					/*String startdate  = request.getParameter("h1bstartdate");
					String enddate = request.getParameter("h1benddate");*/
					message = h1bService.saveh1bdocumentdetails1(documentsubtype,doctype,candidate_id,intiateid,h1bdocument,lgcandidateid,profileid,bentype);
				}else {
					 if(rfeh1document!=null) {
			         	   String[] h1documentInputs=request.getParameterValues("rfeh1documentInput[]");
			         	   message=h1bService.saveH1BDocuments(documentsubtype,doctype,h1documentInputs, rfeh1document,lgcandidateid,candidate_id,intiateid,profileid,bentype);
			           }
				}
				/*hsubdocname h1bDocName h1bDocument hdocumentsubtype   h1bNumber h1bvFromDate h1bvToDate
				i94Number  i94vFromDate i94vToDate*/
				
			}
		} catch (Exception e) {
			logger.error("Error in saveh1bdocumentdetails " + e.getMessage());
		}
		return message ;
	}	
	@RequestMapping(value = { "/getimmisubdocumentbydoctypeid" }, method = RequestMethod.GET)
	@ResponseBody
	public ContractsPanelDetails_DTO getsubdocsbydocname(Model model, HttpServletRequest request) {
		ContractsPanelDetails_DTO docsDto = null;
		try {
			String candidateid = request.getParameter("candidateid");
			String document_id = request.getParameter("document_id");
			String intiateid = request.getParameter("intiateid");
			String benficiary_type = request.getParameter("benficiary_type");
			String profile_id = request.getParameter("profile_id");
			String questionnarie_id = null;
			docsDto = h1bService.getimmisubdocumentBydocid(candidateid, document_id, intiateid,benficiary_type,profile_id,questionnarie_id);
			List<DocumentHistoryDto> dochistoryList=h1bService.getCandidateDocumentsByIds(document_id,candidateid,intiateid,profile_id,benficiary_type);
			docsDto.setAh1docList(dochistoryList);
		} catch (Exception e) {
			logger.error("Error in getimmisubdocumentBydocid " + e);
		}
		return docsDto;
	}
	
	
	@RequestMapping(value = { "/geth1bDetalsById" }, method = RequestMethod.GET)
	@ResponseBody
	public H1BDetailsEntity geth1bDetalsById(Model model, HttpServletRequest request) {
		H1BDetailsEntity h1bDto = null;
		try {
			String h1bId = request.getParameter("h1bId");
			h1bDto = h1bService.geth1bDetalsById(h1bId);
		} catch (Exception e) {
			logger.error("Error in geth1bDetalsById " + e);
		}
		return h1bDto;
	}

	@RequestMapping(value = { "/saveH1InitiationDetails" }, method = RequestMethod.POST)
	@ResponseBody
	public  String saveH1InitiationDetails(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String userName = (String) request.getSession().getAttribute("username");
		Integer loginempno = (Integer) session.getAttribute("empno");
		Integer lgcandidateid = (Integer) session.getAttribute("candidateid");
		String msg= null;
		try {
			if(userName!=null) {
			String candidateid = request.getParameter("init_h1bcandidateid");
			String h1initVal = request.getParameter("h1initVal");
			String marital_status = request.getParameter("marital_status");
			String spousefiling = request.getParameter("spousefiling");
			String spousefilingead = request.getParameter("spousefilingead");
			String kids_status = request.getParameter("kids_status");
			String kidsfiling = request.getParameter("kidsfiling");
			/*String kidsh4dependVal = request.getParameter("kidsh4dependVal");*/
			System.out.println("h1initVal***"+h1initVal+"***spousefilingead***"+spousefiling+spousefilingead+"***marital_status***"+marital_status+"***kids_status***"+kids_status);
			msg = h1bService.saveH1InitiationDetails(lgcandidateid,candidateid,h1initVal,marital_status,spousefiling,spousefilingead,kids_status,kidsfiling);
			}else {
				msg =null;
			}
		} catch (Exception e) {
			logger.error("Error in saveH1InitiationDetails " + e);
		}
		return msg;
	}
	
	
	@RequestMapping(value = { "/updateH1InitiationDetails" }, method = RequestMethod.POST)
	@ResponseBody
	public  String updateH1InitiationDetails(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String userName = (String) request.getSession().getAttribute("username");
		Integer loginempno = (Integer) session.getAttribute("empno");
		Integer lgcandidateid = (Integer) session.getAttribute("candidateid");
		String msg= null;
		try {
			if(userName!=null) {
			String uinitiate_id = request.getParameter("uinitiate_id");
		//	String h1initVal = request.getParameter("h1initVal");
			/*String spouseh4dependVal = request.getParameter("uspouseh4dependVal");*/
			String marital_status = request.getParameter("umarital_status");
			String spousefiling = request.getParameter("uspousefiling");
			String spousefilingead = request.getParameter("uspousefilingead");
			String kids_status = request.getParameter("ukids_status");
			String kidsfiling = request.getParameter("ukidsfiling");
		/*	String kidsh4dependVal = request.getParameter("ukidsh4dependVal");*/
			System.out.println("***spousefilingead***"+spousefiling+spousefilingead+"***marital_status***"+marital_status+"***kids_status***"+kids_status);
			msg = h1bService.updateH1InitiationDetails(lgcandidateid,uinitiate_id,marital_status,spousefiling,spousefilingead,kids_status,kidsfiling);
			}else {
				msg =null;
			}
		} catch (Exception e) {
			logger.error("Error in updateH1InitiationDetails " + e);
		}
		return msg;
	}
	
	
/********************************By Purnachandar***********************************************1 start */
	
	@RequestMapping(value = { "/getimmisubdocumentBydocid" }, method = RequestMethod.GET)
	@ResponseBody
	//public ContractsPanelDetails_DTO getsubdocsbydocname(Model model, HttpServletRequest request) {
	public ContractsPanelDetails_DTO getimmisubdocumentBydocid(Model model, HttpServletRequest request) {
		ContractsPanelDetails_DTO docsDto = null;
		try {
			String candidateid = request.getParameter("candidateid");
			String document_id = request.getParameter("document_id");
			String intiateid = request.getParameter("intiateid");
			String benficiary_type = request.getParameter("benficiary_type");
			String profile_id = request.getParameter("profile_id");
			String questionnarie_id = request.getParameter("questionnarie_id");
			docsDto = h1bService.getimmisubdocumentBydocid(candidateid, document_id, intiateid,benficiary_type,profile_id,questionnarie_id);
		} catch (Exception e) {
			logger.error("Error in getimmisubdocumentBydocid " + e);
		}
		return docsDto;
	}

	@RequestMapping(value = { "/saveimmidocumentbydocId" }, method = RequestMethod.POST)
//	@ResponseBody
	public String saveimmidocumentbydocIdinitiateId(Model model, HttpServletRequest request,HttpServletResponse response,@RequestParam(name = "filename[]", 
	required = false) MultipartFile[] filename) {
		Integer lgempno = (Integer) request.getSession().getAttribute("empno");
		Integer lgcandidateid = (Integer) request.getSession().getAttribute("candidateid");
		String msg = null;
		try {
			String OtherBtnCount = request.getParameter("OtherBtnCount");
			String subdoclength = request.getParameter("subdoclength");
			String initiateid = request.getParameter("initiateid");
			String candidateId = request.getParameter("candidateId2");
			String profile_id = request.getParameter("profile_id");
			String benf_type = request.getParameter("benf_type");
			String documentid = request.getParameter("docid");
			String questionnarie_id = request.getParameter("questionnarie_id");
			String[] h1BtnCount = request.getParameterValues("h1BtnCount");
			System.out.println(subdoclength+"**OtherBtnCount**"+OtherBtnCount);
			  Integer count=0;
			  if(!OtherBtnCount.isEmpty()) {
				  System.out.println(count+OtherBtnCount);
				count=Integer.parseInt(OtherBtnCount);
				  System.out.println("****"+count+OtherBtnCount);
			  }
			  
			  Integer doclength=0;
			  if(!subdoclength.isEmpty()) {
				  System.out.println("**doclength**"+doclength);
				   doclength=Integer.parseInt(subdoclength);
			   }
			  	System.out.println("lgempno***"+lgempno);
				if (doclength>0 && count==0) {
					System.out.println(count+"saveimmidocumentbydocIdinitiateId****"+doclength);
					String[] h1b_checklist_id = request.getParameterValues("h1b_checklist_id[]");
					String[] subdocumentid = request.getParameterValues("subdocumentid[]");
					msg = h1bService.saveimmidocumentbydocIdinitiateId(initiateid, documentid, subdocumentid, candidateId,lgcandidateid, filename,h1b_checklist_id);
				} 
			    if(doclength == count && count>0) {
			    	System.out.println(count+"saveimmidocumentbydocIdinitiateId1****"+doclength);
			    	String[] input = request.getParameterValues("otherAddtnlinput[]");
					msg = h1bService.saveimmidocumentbydocIdinitiateId1(initiateid,lgcandidateid,documentid, candidateId, input, filename,profile_id,benf_type,questionnarie_id);
				}
			    //Receiving subcategory with you profile
			    System.out.println("h1BtnCount****"+h1BtnCount);
			    if (h1BtnCount!= null) {
					for (int i = 1; i <=h1BtnCount.length; i++) {
						System.out.println(count+"saveimmiSubdocumentstoChecklist****"+doclength);
						MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;	
						String h1DocSelectionId = request.getParameter("h1DocSelectionId" + i + "");
						MultipartFile h1document = multipartRequest.getFile("h1bFile" + i);
						msg = h1bService.saveimmiSubdocumentstoChecklist(initiateid,documentid,h1DocSelectionId, candidateId,lgcandidateid,h1document,profile_id,benf_type);
					   }
				    }
			    PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('"+msg+"');");
                out.println("window.location='mydocuments'");
                out.println("</script>");
                out.close();
			    
		} catch (Exception e) {
			logger.error("Error in saveimmidocumentbydocIdinitiateId " + e);
		}
		return null;
	}
	
	@RequestMapping(value = { "/saveimmidocumentbydocIdForm" }, method = RequestMethod.POST)
	@ResponseBody
	public String saveimmidocumentbydocIdForm(Model model, HttpServletRequest request,HttpServletResponse response,@RequestParam(name = "filename1[]", 
	required = false) MultipartFile[] filename) {
		Integer lgempno = (Integer) request.getSession().getAttribute("empno");
		Integer lgcandidateid = (Integer) request.getSession().getAttribute("candidateid");
		String msg = null;
		try {
			String OtherBtnCount = request.getParameter("OtherBtnCount");
			String subdoclength = request.getParameter("subdoclength");
			String initiateid = request.getParameter("initiateid");
			String candidateId = request.getParameter("candidateId2");
			String profile_id = request.getParameter("profile_id");
			String benf_type = request.getParameter("benf_type");
			String documentid = request.getParameter("docid");
			String questionnarie_id = request.getParameter("questionnarie_id");
			String[] h1BtnCount = request.getParameterValues("h1BtnCount");
			System.out.println(subdoclength+"**OtherBtnCount**"+OtherBtnCount);
			  Integer count=0;
			  if(!OtherBtnCount.isEmpty()) {
				  System.out.println(count+OtherBtnCount);
				count=Integer.parseInt(OtherBtnCount);
				  System.out.println("****"+count+OtherBtnCount);
			  }
			  
			  Integer doclength=0;
			  if(!subdoclength.isEmpty()) {
				  System.out.println("**doclength**"+doclength);
				   doclength=Integer.parseInt(subdoclength);
			   }
			  	System.out.println("lgempno***"+lgempno);
				if (doclength>0 && count==0) {
					System.out.println(count+"saveimmidocumentbydocIdinitiateId****"+doclength);
					String[] h1b_checklist_id = request.getParameterValues("h1b_checklist_id[]");
					String[] subdocumentid = request.getParameterValues("subdocumentid[]");
					msg = h1bService.saveimmidocumentbydocIdinitiateId(initiateid, documentid, subdocumentid, candidateId,lgcandidateid, filename,h1b_checklist_id);
				} 
			    if(doclength == count && count>0) {
			    	System.out.println(count+"saveimmidocumentbydocI dinitiateId1****"+doclength);
			    	String[] input = request.getParameterValues("otherAddtnlinput[]");
					msg = h1bService.saveimmidocumentbydocIdinitiateId1(initiateid,lgcandidateid,documentid, candidateId, input, filename,profile_id,benf_type,questionnarie_id);
				}
			    //Receiving subcategory with you profile
			    System.out.println("h1BtnCount****"+h1BtnCount);
			    if (h1BtnCount!= null) {
					for (int i = 1; i <=h1BtnCount.length; i++) {
						System.out.println(count+"saveimmiSubdocumentstoChecklist****"+doclength);
						MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;	
						String h1DocSelectionId = request.getParameter("h1DocSelectionId" + i + "");
						MultipartFile h1document = multipartRequest.getFile("h1bFile" + i);
						msg = h1bService.saveimmiSubdocumentstoChecklist(initiateid,documentid,h1DocSelectionId, candidateId,lgcandidateid,h1document,profile_id,benf_type);
					   }
				    }
			    /*PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('"+msg+"');");
                out.println("window.location='immigration'");
                out.println("</script>");
                out.close();*/
			    
		} catch (Exception e) {
			logger.error("Error in saveimmidocumentbydocIdinitiateId1 " + e);
		}
		return msg;
	}
	
	@RequestMapping(value = { "/updatedoc" }, method = RequestMethod.POST)
	@ResponseBody
	public   String  updatedoc(Model model, HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name = "filename1", required = false) MultipartFile filename1) {
		String msg=null;
		try {
			String docid = request.getParameter("documentid1");
			msg= h1bService.updatefilebydocid(docid,filename1);
		} catch (Exception e) {
			logger.error("Error in updatedoc " + e.getMessage());
		}
		return msg;
	}
	
	@RequestMapping(value = { "/getinputvaluebydocid" }, method = RequestMethod.GET)
	@ResponseBody
	public   List<EmpDocumentsEntity>  getinputvaluebydocid(Model model, HttpServletRequest request) {
		List<EmpDocumentsEntity> inputval = null;
		try {
			String intiateid = request.getParameter("intiateid");
			String candidateid = request.getParameter("candidateid");
			String documentid = request.getParameter("document_id");
			String benficiary_type = request.getParameter("benficiary_type");
			String profile_id = request.getParameter("profile_id");
			String questionnarie_id = request.getParameter("questionnarie_id");
			inputval = h1bService.getinputvaluebydocid(intiateid,candidateid,documentid,benficiary_type,profile_id,questionnarie_id);
			
		} catch (Exception e) {
			logger.error("Error in getinputvaluebydocid " + e.getMessage());
		}
		return inputval;
	}
	
	
	@RequestMapping(value = { "/updateformbydocid" }, method = RequestMethod.POST)
	@ResponseBody
	public  String  updateformbydocid(Model model, HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name = "filenameid", required = false) MultipartFile filenameid) {
	           String msg=null;
		try {
			String documenttype = request.getParameter("edinputval");
			String document_id = request.getParameter("documentid2");
			msg = h1bService.updateformbydocid(documenttype,document_id,filenameid);
			
		} catch (Exception e) {
			logger.error("Error in updateformbydocid " + e.getMessage());
		}
		return msg;
	}
	

	@RequestMapping(value = { "/viewfilehistory" }, method = RequestMethod.GET)
	@ResponseBody
  	public List<EmpDocumentsEntity>  viewfilehistory(Model model, HttpServletRequest request) {
		List<EmpDocumentsEntity> filehistory=null;
		
	           Integer updatedoc = null;String msg=null;
	           Integer lgempno = (Integer) request.getSession().getAttribute("empno");
		
		try {
			String docid = request.getParameter("docid");
			String subdocid = request.getParameter("subdocid");
			String initiateid = request.getParameter("initiateid");
			String candid = request.getParameter("candid");
			String checklistid = request.getParameter("checklistid");
	
		filehistory = h1bService.viewfilehistory(docid,subdocid,initiateid,candid,checklistid);
			
		} catch (Exception e) {
			logger.error("Error in updateformbydocid " + e.getMessage());
		}

		return filehistory;
	}
	

	@RequestMapping(value = { "/getdocumentshistory" }, method = RequestMethod.GET)
	@ResponseBody
  	public List<DocumentsDTO>  getdocumentshistory(Model model, HttpServletRequest request) {
		List<DocumentsDTO> dochistory=null;
	
		try {
			String document_id = request.getParameter("docid");
			dochistory = h1bService.getdocumentshistory(document_id);
		} catch (Exception e) {
			logger.error("Error in getdocumentshistory " + e.getMessage());
		}
		return dochistory;
	}
	
	
	/*******************************************************************************1 close*/
	
	
	
	@RequestMapping(value = { "/getCandidateDocumentsByIds" }, method = RequestMethod.GET)
	@ResponseBody
	public ContractsPanelDetails_DTO getDocumentHistiryBydocTypeIdCandidateId(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		//List<DocumentHistoryDto> dochistoryList = null;
		ContractsPanelDetails_DTO docsDto = null;
		try {
			 String docTypeId = request.getParameter("docTypeId");
			 String candidateid = request.getParameter("candidateid");
			 String initiateid = request.getParameter("initiateid");
			 String profile_id = request.getParameter("profile_id");
			 String profile_type = request.getParameter("profile_type");
			 docsDto = h1bService.getimmisubdocumentBydocid(candidateid, docTypeId, initiateid,profile_type,profile_id,"");
			 List<DocumentHistoryDto> dochistoryList=h1bService.getCandidateDocumentsByIds(docTypeId,candidateid,initiateid,profile_id,profile_type);
			 docsDto.setDochistoryList(dochistoryList);
		} catch (Exception e) {
			logger.error("Error in getCandidateDocumentsByIds" + e);
		}
		return docsDto;
	}
	
	@RequestMapping(value = { "/updateCandDocVerifierStatus" }, method = RequestMethod.POST)
	@ResponseBody
	public String updateCandDocVerifierStatus(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer lgempno = (Integer) request.getSession().getAttribute("empno");
		String message="";
		try {
			if (userName == null) {
				return "redirect:/loginpage"; 
			} else {
				 String docTypeId = request.getParameter("h1bdoctypeid");
				 String candidateid = request.getParameter("h1bcandidateId");
				 String initiateid = request.getParameter("h1binitiateid");
				 String verfierstatus = request.getParameter("candocapprstatus");
				 String verfiercomments = request.getParameter("h1bcandicomments");
				 String profile_id = request.getParameter("h1bprofile_id");
				 String profile_type = request.getParameter("h1bbenf_type");
				 message = h1bService.updateCandDocVerifierStatus(docTypeId,candidateid,initiateid,lgempno,verfierstatus,verfiercomments,profile_id,profile_type);
			  }
		} catch (Exception e) {
			logger.error("Error in updateCandDocVerifierStatus " + e.getMessage());
		}
		return message ;
	}
	
	@RequestMapping(value = { "/viewImmiDocCommentHistoryByIds" }, method = RequestMethod.GET)
	@ResponseBody
	public List<commentHistoryDto> viewImmiDocCommentHistoryByIds(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		List<commentHistoryDto> commentHistoryList = null;
		try {
			 String docTypeId = request.getParameter("docTypeId");
			 String candidateid = request.getParameter("candidateid");
			 String initiateid = request.getParameter("initiateid");
			 String profile_id = request.getParameter("profile_id");
			 String profile_type = request.getParameter("profile_type");
			 commentHistoryList=h1bService.getImmiCandidateDocumentsByIds(docTypeId,candidateid,initiateid,profile_id,profile_type);
		} catch (Exception e) {
			logger.error("Error in viewImmiDocCommentHistoryByIds" + e);
		}
		return commentHistoryList;
	}
	@RequestMapping(value = { "/mydocuments" }, method = RequestMethod.GET)
	public String mydocuments(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer candidateid = (Integer) request.getSession().getAttribute("candidateid");
		try {
			if (userName == null) {
				return "redirect:/loginpage";
			} else {
				HttpSession session = request.getSession(true);
				/*List<DocumentListMasterEntity> docList = immiService.getdocumentlist(candidateid.toString());*/
				List<PublicBenefitsChecklistEntity> pbcsize = h1bService.getPublicBenefitsSizeByCanId(candidateid);
				/*PublicBenefitsChecklistDTO pbcDto = h1bService.PublicBenefitsByCanId(candidateid);
				session.setAttribute("pbcDto", pbcDto);*/
				ContractsPanelDetails_DTO docandimmiformDto= h1bService.getimmidocumentlist(candidateid.toString());
				
				session.setAttribute("pbcsize", pbcsize);
				session.setAttribute("docandimmiformDto", docandimmiformDto);
				//session.setAttribute("docList", docList);
				session.setAttribute("myimmidocs", "myimmidocs");
				session.setAttribute("candidates", null);
				session.setAttribute("templates", null);
				session.setAttribute("stsreports", null);
				session.setAttribute("timesheet", null);
				session.setAttribute("bgc", null);
			 }
		} catch (Exception e) {
			logger.error("Error in ImmigrationController method mydocuments " + e);
		}
		return "MyImmigrationDocs";
	}
	
	@RequestMapping(value = { "/downloadh1bdocs" }, method = RequestMethod.GET)
	public void downloadbgvdocs(HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			String userName = (String) request.getSession().getAttribute("username");
			if (userName == null) {
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("window.location.href='login.jsp'");
				out.println("</script>");
				out.close();
			} else {
				Integer candidateId = null;
				String candidate_id = request.getParameter("candidate_id");
				String filename = request.getParameter("filename");
				String filepath = null;
				String workingDir = env.getProperty("documents");
				if (!candidate_id.isEmpty()) {
					candidateId = Integer.parseInt(candidate_id);
					String path = workingDir + File.separator + candidateId;
					filepath = path + File.separator + "H-1B" + File.separator + filename;
				}
				System.out.println(filepath);
				File ff = new File(filepath);
				if (ff.exists() == true) {
					FileInputStream fis = new FileInputStream(ff);
					byte[] inputBytes = new byte[(int) ff.length()];
					fis.read(inputBytes);
					response.setContentType("application/octet-stream;");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + filename);
					OutputStream os = response.getOutputStream(); // file output stream
					os.write(inputBytes);
					os.close();
				} else {
					PrintWriter out = response.getWriter();
					out.println("<script type=\"text/javascript\">");
					out.println("alert('No file available');");
					out.println("window.history.back();");
					out.println("</script>");
					out.close();
				}
			}
		} catch (Exception e) {
			logger.error("Error while downloadimmigrationdocs action in ImmigrationController" + e);
		}
	}
	@RequestMapping(value = { "/getpafdetailsbylcaid" }, method = RequestMethod.GET)
	@ResponseBody
	public EndClientDetialDto getpafdetailsbylcaid(Model model, HttpServletRequest request,HttpServletResponse response) {
		
		String userName = (String) request.getSession().getAttribute("username");
		EndClientDetialDto enclientDto = new EndClientDetialDto();
		try {
			if(userName!=null) {
			String candidateid = request.getParameter("candidateid");
			String clientid = request.getParameter("clientid");
			String lcaid = request.getParameter("lcaid");
			List<LCADetailsDto> alllcaList = immiService.getAllLCAList(candidateid,clientid);
			enclientDto.setAlllcaList(alllcaList);
			LCADetailsEntity lcaObj=immiService.getLcaDetailsById(lcaid);
			Integer docCount=immiService.getDocumentCountByLcaId(lcaid);
			enclientDto.setLcaObj(lcaObj);
			enclientDto.setDocCount(docCount);
			}else {
				enclientDto =null;
			}
		} catch (Exception e) {
			logger.error("Error in getpafdetailsbylcaid " + e);
		}
		return enclientDto;
	}
	@RequestMapping(value = { "/downloadlcapdf" }, method = RequestMethod.GET)
	public String downloadpdf(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String userName = (String) request.getSession().getAttribute("username");
		try {
			if (userName != null) {
				String lcaid = request.getParameter("lcaid");
				String doctype = request.getParameter("doctype");
				if(doctype.equalsIgnoreCase("PAF")) {
					LCADetailsEntity lcaObj = immiService.getLcaDetailsById(lcaid);
					if (lcaObj != null) {
						PAFDetailsDto pafObj=h1bService.pafDetails(lcaid);
						 XWPFDocument doc= new XWPFDocument();
						  // the body content
						 /* XWPFParagraph paragraphi = doc.createParagraph();
						  XWPFRun runi=paragraphi.createRun(); 
						  paragraphi.setAlignment(ParagraphAlignment.LEFT);
						  String imgFile = env.getProperty("paflogo");//"D:/logo.png"
						  FileInputStream is = new FileInputStream(imgFile);
						  runi.addPicture(is, Document.PICTURE_TYPE_PNG, imgFile, Units.toEMU(125), Units.toEMU(40)); // 200x200 pixels
						  is.close();
						  runi.setText("________________________________________________________________________________");
						 */
						  XWPFParagraph paragraph = doc.createParagraph();
						  XWPFRun runhl1=paragraph.createRun(); 
						  //run.addBreak();
						  runhl1.setText("NOTICE OF FILING OF LABOR CONDITION APPLICATION");
						  runhl1.setFontSize(14);
						  runhl1.setBold(true);
						  runhl1.setUnderline(UnderlinePatterns.SINGLE);
						  paragraph.setAlignment(ParagraphAlignment.CENTER);
						  runhl1.addBreak();
						  
						  XWPFTable tab = doc.createTable();
						  tab.getCTTbl().getTblPr().getTblBorders().getLeft().setVal(STBorder.NONE);
						  tab.getCTTbl().getTblPr().getTblBorders().getRight().setVal(STBorder.NONE);
						  tab.getCTTbl().getTblPr().getTblBorders().getTop().setVal(STBorder.NONE);
						  tab.getCTTbl().getTblPr().getTblBorders().getBottom().setVal(STBorder.NONE);
						  tab.getCTTbl().getTblPr().getTblBorders().getInsideH().setVal(STBorder.NONE);
						  tab.getCTTbl().getTblPr().getTblBorders().getInsideV().setVal(STBorder.NONE);
						  
						// Long cellWidth = 2000;
						  XWPFTableRow row = tab.getRow(0); // First row  
						  // Columns  
						  row.getCell(0);  
						  row.addNewTableCell();  
						  row.addNewTableCell();
						  row = tab.createRow(); // Second Row 
    					 // row = tab.createRow();
						  row.getCell(0).setText("Position");  
						  row.getCell(1);
						 // XWPFParagraph paratTbl2Row1c2 = Tbl2Row1.getCell(1).getParagraphs().get(0);
				        //  setRunTd(paratTbl2Row1c2.createRun() , "Calibri" , 10, "Job Offer Requirements" , false, false);
						  row.getCell(2).setText(":"+pafObj.getClientObj().getCand_job_title()+"");
						  XWPFTableCell cell=row.getCell(2);
					/*	  CTTblWidth width = cell.getCTTc().addNewTcPr().addNewTcW();
						  width.setType(STTblWidth.DXA);
						  width.setW(BigInteger.valueOf(2000));*/
						 // cell.setColor("FFFF00");
						  
						  
						  row = tab.createRow(); // Third Row  
						  row.getCell(0).setText("Occupational	Classification");  
						  XWPFTableCell cell0R2=row.getCell(0);
						  CTTblWidth width0R2 = cell0R2.getCTTc().addNewTcPr().addNewTcW();
						  width0R2.setType(STTblWidth.DXA);
						  width0R2.setW(BigInteger.valueOf(3000));
						  row.getCell(1);
						  row.getCell(2).setText(": 030");  
						  XWPFTableCell cell1=row.getCell(2);
						 // cell1.setColor("FFFF00");
						//  cell1.setParagraph(paragraph.createRun().setText(""));
						  
						  row = tab.createRow(); 
						  row.getCell(0).setText("Duties ");  
						  row.getCell(1);
						  row.getCell(2).setText(": "+pafObj.getSocObj().getDuties()+".");
						  XWPFTableCell cell3=row.getCell(2);
						 // cell3.setColor("FFFF00");
						  
						  row = tab.createRow(); // Third Row 
						  row.getCell(0).setText("Number of H-1B Workers  ");  
						  row.getCell(1);
						  row.getCell(2).setText(": 1");  
						 
						  row = tab.createRow(); 
						  row.getCell(0).setText("Education & Experience ");  
						  row.getCell(1);
						  row.getCell(2).setText(": Bachelor’s Degree in Computer Sciences / Engineering / Information Systems / Business Administration or a related field.");  
						  
						  row = tab.createRow(); 
						  row.getCell(0).setText("Experience ");  
						  row.getCell(1);
						  row.getCell(2).setText(": Two to Three years of experience in the IT industry.");  
						 
						  row = tab.createRow(); 
						  row.getCell(0).setText("Salary");  
						  row.getCell(1);
						  row.getCell(2).setText(":  $"+pafObj.getLcaObj().getWage_rate()+"/per year");  
						  XWPFTableCell cell4=row.getCell(2);
						 // cell4.setColor("FFFF00");
						  
						  
						  row = tab.createRow(); 
						  row.getCell(0).setText("Location  ");  
						  row.getCell(1);
						  row.getCell(2).setText(": "+pafObj.getEndclientAddress()+"");  
						  XWPFTableCell cell5=row.getCell(2);
						 // cell5.setColor("FFFF00");
						  if(!pafObj.getRemoteaddress().isEmpty()) {
						  row = tab.createRow(); 
						  row.getCell(0);  
						  row.getCell(1);
						  row.getCell(2).setText(": "+pafObj.getRemoteaddress()+"");  
					     }
						  XWPFTableCell cell6=row.getCell(2);
						  if(pafObj.getClientObj().getReason_lca().equalsIgnoreCase("H1B Extension")) {
							  row = tab.createRow(); 
							  row.getCell(0);  
							  row.getCell(1);
							  row.getCell(2).setText(": 5 Independence Way, Suite 13,5Princeton,New Jersey-08540");  
						  }
						  
						//  cell6.setColor("FFFF00");
						  
						  row = tab.createRow(); 
						  row.getCell(0).setText("Employment Period ");  
						  row.getCell(1);
						  row.getCell(2).setText(": "+pafObj.getLcaObj().getLca_begin_date()+ " to " +pafObj.getLcaObj().getLca_end_date()+"");  
						  XWPFTableCell cell7=row.getCell(2);
						//  cell7.setColor("FFFF00");
						  
						  row = tab.createRow(); 
						  row.getCell(0).setText("Contact");  
						  row.getCell(1);
						  row.getCell(2).setText(": Madhavi Manthena, President.");  
						 
						  row = tab.createRow(); 
						  row.getCell(0).setText("Dates posted");  
						  row.getCell(1);
						  row.getCell(2).setText(": "+pafObj.getLcaObj().getLca_submitted_date()+" to _________________");  
						  XWPFTableCell cell8=row.getCell(2);
						//  cell8.setColor("FFFF00");
						  
						  row = tab.createRow(); 
						  row.getCell(0).setText("Signed by");  
						  row.getCell(1);
						  row.getCell(2).setText(": ________________");  
						 
						  row = tab.createRow(); 
						  row.getCell(0);  
						  row.getCell(1);
						  row.getCell(2).setText("Madhavi Manthena");  
						  
						  
					/*	  int[] cols = {3000, 7000};
						  for(int i = 0; i < tab.getNumberOfRows(); i++){
					           XWPFTableRow rowt = tab.getRow(i);
					           int numCells = rowt.getTableCells().size();
					           for(int j = 0; j < numCells; j++){
					               XWPFTableCell cellt = row.getCell(j);
					               cellt.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(cols[j]));
					           }
					       }*/
						 
						//  row = tab.createRow(); 
						  XWPFParagraph paragraph1 = doc.createParagraph();
						  XWPFRun runpg=paragraph1.createRun(); 
						  runpg.setText("Complaints alleging misrepresentation of material facts in the labor condition application and/or failure to comply with the terms of the labor condition application may be filed with any office of the Wage and Hour Division of the United States Department of Labor.");
						  runpg.addBreak(BreakType.PAGE);
						  
						 /* XWPFParagraph paragraphi3 = doc.createParagraph();
						  XWPFRun runi3=paragraphi3.createRun(); 
						  paragraphi3.setAlignment(ParagraphAlignment.LEFT);
						  String imgFilei3 = env.getProperty("paflogo");//"D:/logo.png"
						  FileInputStream is3 = new FileInputStream(imgFilei3);
						  runi3.addPicture(is3, Document.PICTURE_TYPE_JPEG, imgFilei3, Units.toEMU(125), Units.toEMU(40)); // 200x200 pixels
						  is3.close();
						  runi3.setText("________________________________________________________________________________");*/
						  XWPFParagraph paragraph3 = doc.createParagraph();
						  XWPFRun run3=paragraph3.createRun(); 
						  paragraph3.setAlignment(ParagraphAlignment.CENTER);
						  run3.setText("Internal Use:");
						  run3.setBold(true);
						  run3.setUnderline(UnderlinePatterns.SINGLE);
						  run3.addBreak();
						  
						  XWPFTable tab2 = doc.createTable();
						
						  tab2.getCTTbl().getTblPr().getTblBorders().getLeft().setVal(STBorder.NONE);
						  tab2.getCTTbl().getTblPr().getTblBorders().getRight().setVal(STBorder.NONE);
						  tab2.getCTTbl().getTblPr().getTblBorders().getTop().setVal(STBorder.NONE);
						  tab2.getCTTbl().getTblPr().getTblBorders().getBottom().setVal(STBorder.NONE);
						  tab2.getCTTbl().getTblPr().getTblBorders().getInsideH().setVal(STBorder.NONE);
						  tab2.getCTTbl().getTblPr().getTblBorders().getInsideV().setVal(STBorder.NONE);
						  tab2.getCTTbl()
						   .getTblPr()
						   .addNewTblCellSpacing()
						   .setType(
						     org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth.DXA
						   );
						  tab2.getCTTbl()
						   .getTblPr()
						   .getTblCellSpacing()
						   .setW(java.math.BigInteger.valueOf(
						     50 // 180 TWentieths of an Inch Point (Twips) = 180/20 = 9 pt = 9/72 = 0.125"
						   ));
						   
						   XWPFTableRow row2 = tab2.getRow(0); // First row  
						   // Columns  
						   row2.getCell(0).setText("Posted Methods/Location:   ");  
						   row2.addNewTableCell();  
						   row2.addNewTableCell().setText(" LCA posting Summary was posted");
						   
						   row2 = tab2.createRow();
						   row2.getCell(0);  
						   row2.getCell(1);
						   row2.getCell(2).setText(" Notice Board in the Hallway");  
						   row2 = tab2.createRow(); 
						  
						   XWPFParagraph paragraph4 = doc.createParagraph();
						   XWPFRun run4=paragraph4.createRun();
						   
						   run4.setText("This is to certify that the above notice was posted for the period from  ");
						   XWPFRun run41=paragraph4.createRun(); 
						   CTShd cTShd41 = run41.getCTR().addNewRPr().addNewShd();
						   cTShd41.setVal(STShd.CLEAR);
						   cTShd41.setColor("auto");
						   cTShd41.setFill("FFFF00");
						   run41.setText(pafObj.getLcaObj().getLca_submitted_date());
						   run41.setFontFamily("Calibri");
						   run41.setFontSize(12);
						   XWPFRun run42=paragraph4.createRun(); 
						   run42.setText(" to ______________");
						   XWPFRun run422=paragraph4.createRun(); 
						   run422.addBreak();
						   run422.addBreak();
						   run422.addBreak();
						   run422.setText("Signature	: ");
						   XWPFRun run423=paragraph4.createRun(); 
						   run423.addBreak();
						   run423.addBreak();
						   run423.setText("Name		:  Madhavi Manthena ");
						   run423.addBreak();
						   run423.setText("Title		:  President ");
						   run423.addBreak();
						   run423.addBreak();
						// create header start
						  CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
						  XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);
						  XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
						  paragraph = header.getParagraphArray(0);
						  paragraph.setAlignment(ParagraphAlignment.LEFT);
						  XWPFRun runh=paragraph.createRun(); 
						  String himgFile=env.getProperty("paflogo");
						  //"D:/logo.png";
						  XWPFPicture picture = runh.addPicture(new FileInputStream(himgFile), XWPFDocument.PICTURE_TYPE_PNG, himgFile, Units.toEMU(125), Units.toEMU(40));
						  System.out.println(picture); //XWPFPicture is added
						  System.out.println(picture.getPictureData()); //but without access to XWPFPictureData (no blipID)
						  String blipID = "";
						  for(XWPFPictureData picturedata : header.getAllPackagePictures()) {
						   blipID = header.getRelationId(picturedata);
						   System.out.println(blipID); //the XWPFPictureData are already there
						  }
						  picture.getCTPicture().getBlipFill().getBlip().setEmbed(blipID); //now they have a blipID also
						  System.out.println(picture.getPictureData());
						  runh.setText("__________________________________________________________________________________");
						  
						  // create footer start
						  XWPFParagraph footerPara = doc.createParagraph();
						  XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);
						  footerPara = footer.getParagraphArray(0);
						  //paragraph.setAlignment(ParagraphAlignment.LEFT);
						  XWPFRun runf1=footerPara.createRun(); 
						  runf1.setText("__________________________________________________________________________________");
						  runf1.addBreak();
						  XWPFRun runfl=footerPara.createRun(); 
						  runfl.setFontFamily("Arial");
						  runfl.setFontSize(9);
						  runfl.setBold(true);
						  runfl.setColor("8B0000");
						  runfl.setText("5 Independence Way, Suite 135,                           Tel: (732) 640.1204 		URL: ");
						  XWPFRun runfl1=footerPara.createRun();
						  CTShd cTShdfl1 = runfl1.getCTR().addNewRPr().addNewShd();
						  cTShdfl1.setVal(STShd.CLEAR);
						  runfl1.setFontFamily("Arial");
						  runfl1.setBold(true);
						  runfl1.setFontSize(9);
						  runfl1.setColor("00008b");
						  runfl1.setText("www.tekskillsinc.com");  
						  runfl1.addBreak();
						  XWPFRun runf2=footerPara.createRun(); 
						 // runf.setText("Princeton NJ 08540		                                        Fax:	(888) 543.5125			                          Email: info@tekskillsinc.com");
						  runf2.setFontFamily("Arial");
						  runf2.setFontSize(9);
						  runf2.setBold(true);
						  runf2.setColor("8B0000");
						  runf2.setText(" Princeton NJ 08540                                              Fax: (888) 543.5125              Email: ");
						  XWPFRun runfl2=footerPara.createRun();
						  CTShd cTShdfl2 = runfl2.getCTR().addNewRPr().addNewShd();
						  cTShdfl2.setVal(STShd.CLEAR);
						  runfl2.setFontFamily("Arial");
						  runfl2.setUnderline(UnderlinePatterns.SINGLE);
						  runfl2.setFontSize(9);
						  runfl2.setBold(true);
						  runfl2.setColor("00008b");
						  runfl2.setText("info@tekskillsinc.com");  
						  runfl2.addBreak();
						String filetitle ="Tekskills Inc-PAF";
						String filename = filetitle + ".doc";
						
						DiskFileItemFactory factory = new DiskFileItemFactory();
						factory.setSizeThreshold(THRESHOLD_SIZE);
						factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
						ServletFileUpload upload = new ServletFileUpload(factory);
						upload.setFileSizeMax(MAX_FILE_SIZE);
						upload.setSizeMax(MAX_REQUEST_SIZE);
						
						// constructs the directory path to store upload file
						String uploadPath = context.getRealPath("") + UPLOAD_DIRECTORY;

						if (!new File(uploadPath).exists()) {
							new File(uploadPath).mkdir();
							logger.info("directory created  ");
						}
						String desFile = uploadPath + File.separator + filetitle;
						File dest = new File(uploadPath + File.separator + filename);
						try {
						
							  doc.write(new FileOutputStream(dest));
							logger.info("File created with content ");
						} catch (IOException e) {
							e.printStackTrace();
						}
						

						File file = new File(desFile + ".doc");
						FileInputStream in = new FileInputStream(file);
						response.setContentType("application/ocet");
						response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
						response.setHeader("Content-Length", String.valueOf(file.length()));
						FileCopyUtils.copy(in, response.getOutputStream());
					}
				}else if(doctype.equalsIgnoreCase("PAFWithoutNotice")) {
					LCADetailsEntity lcaObj = immiService.getLcaDetailsById(lcaid);
					if (lcaObj != null) {
						PAFDetailsDto pafObj=h1bService.pafDetails(lcaid);
					  XWPFDocument doc= new XWPFDocument();
					/*  XWPFParagraph paragraphi = doc.createParagraph();
					  XWPFRun page1logo=paragraphi.createRun(); 
					  paragraphi.setAlignment(ParagraphAlignment.LEFT);
					  String imgFilep1 =  env.getProperty("paflogo");//"D:/logo.png"
					  FileInputStream isp1 = new FileInputStream(imgFilep1);
					  page1logo.addPicture(isp1, Document.PICTURE_TYPE_PNG, imgFilep1, Units.toEMU(125), Units.toEMU(40)); // 200x200 pixels
					  isp1.close();
					  page1logo.setText("__________________________________________________________________________________");
					 */ XWPFParagraph headerline = doc.createParagraph();
					  XWPFRun runhl=headerline.createRun(); 
					  runhl.setText("DOL PUBLIC ACCESS FILE");
					  runhl.addBreak();
					  runhl.setBold(true);
					  runhl.setFontSize(14);
					  runhl.setUnderline(UnderlinePatterns.SINGLE);
					  headerline.setAlignment(ParagraphAlignment.CENTER);
					  runhl.addBreak();
					  XWPFParagraph paragraph = doc.createParagraph();
					  XWPFRun run1=paragraph.createRun(); 
					  run1.setText("LCA Number 		: ");
					  run1.setFontFamily("Calibri");
					  run1.setFontSize(12);
					  XWPFRun run11=paragraph.createRun(); 
					  CTShd cTShdp1 = run11.getCTR().addNewRPr().addNewShd();
					  cTShdp1.setVal(STShd.CLEAR);
					  cTShdp1.setColor("auto");
					  cTShdp1.setFill("FFFF00");
					  run11.setFontFamily("Calibri");
					  run11.setFontSize(12);
					  run11.setText(""+pafObj.getLcaObj().getLca_number()+"");
					  run11.addBreak();
					 
					  XWPFRun run2=paragraph.createRun(); 
					  run2.setFontFamily("Calibri");
					  run2.setFontSize(12);
					  run2.setText("Location 		: ");
					  XWPFRun run12=paragraph.createRun(); 
					  CTShd cTShdp12 = run12.getCTR().addNewRPr().addNewShd();
					  cTShdp12.setVal(STShd.CLEAR);
					  cTShdp12.setColor("auto");
					  cTShdp12.setFill("FFFF00");
					  run12.setText(""+pafObj.getEndclientAddress()+"");
					  run12.setFontFamily("Calibri");
					  run12.setFontSize(12);
					  run12.addBreak();
					  
					  XWPFRun run3=paragraph.createRun(); 
					  run3.setFontFamily("Calibri");
					  run3.setFontSize(12);
					  run3.setText(" 			: ");
					  XWPFRun run13=paragraph.createRun(); 
					  CTShd cTShdp13 = run13.getCTR().addNewRPr().addNewShd();
					  cTShdp13.setVal(STShd.CLEAR);
					  cTShdp13.setColor("auto");
					  cTShdp13.setFill("FFFF00");
					  run13.setText(""+pafObj.getRemoteaddress()+"");
					  run13.setFontFamily("Calibri");
					  run13.setFontSize(12);
					  run13.addBreak();
					  if(pafObj.getClientObj().getReason_lca().equalsIgnoreCase("H1B Extension")) {
						  XWPFRun run31=paragraph.createRun(); 
						  run31.setFontFamily("Calibri");
						  run31.setFontSize(12);
						  run31.setText(" 			: ");
						  XWPFRun run131=paragraph.createRun(); 
						  CTShd cTShdp131 = run131.getCTR().addNewRPr().addNewShd();
						  cTShdp131.setVal(STShd.CLEAR);
						  cTShdp131.setColor("auto");
						  cTShdp131.setFill("FFFF00");
						  run131.setText("5 Independence Way, Suite 13,5Princeton,New Jersey-08540");
						  run131.setFontFamily("Calibri");
						  run131.setFontSize(12);
						  run131.addBreak();
					  }
					  
					  XWPFRun run4=paragraph.createRun(); 
					  run4.setFontFamily("Calibri");
					  run4.setFontSize(12);
					  run4.setText("Job title 		: ");
					  XWPFRun run14=paragraph.createRun(); 
					  CTShd cTShdp14 = run14.getCTR().addNewRPr().addNewShd();
					  cTShdp14.setVal(STShd.CLEAR);
					  cTShdp14.setColor("auto");
					  cTShdp14.setFill("FFFF00");
					  run14.setText(""+pafObj.getLcaObj().getJob_title()+"");
					  run14.setFontFamily("Calibri");
					  run14.setFontSize(12);
					  run14.addBreak();
					  
					  XWPFRun run5=paragraph.createRun(); 
					  run5.setFontFamily("Calibri");
					  run5.setFontSize(12);
					  run5.setText("Wage Rate 		: ");
					  XWPFRun run15=paragraph.createRun(); 
					  CTShd cTShdp15 = run15.getCTR().addNewRPr().addNewShd();
					  cTShdp15.setVal(STShd.CLEAR);
					  cTShdp15.setColor("auto");
					  cTShdp15.setFill("FFFF00");
					  run15.setText(" $ "+pafObj.getLcaObj().getWage_rate()+"/per year");
					  run15.setFontFamily("Calibri");
					  run15.setFontSize(12);
					  run15.addBreak();
					  
					  XWPFRun run6=paragraph.createRun(); 
					  run6.setFontFamily("Calibri");
					  run6.setFontSize(12);
					  run6.setText("LCA Duration 		: ");
					  XWPFRun run16=paragraph.createRun(); 
					  CTShd cTShdp16 = run16.getCTR().addNewRPr().addNewShd();
					  cTShdp16.setVal(STShd.CLEAR);
					  cTShdp16.setColor("auto");
					  cTShdp16.setFill("FFFF00");
					  run16.setText(""+pafObj.getLcaObj().getLca_begin_date()+" ");
					  run16.setFontFamily("Calibri");
					  run16.setFontSize(12);
					  XWPFRun run17=paragraph.createRun(); 
					  CTShd cTShdp17 = run17.getCTR().addNewRPr().addNewShd();
					  cTShdp17.setVal(STShd.CLEAR);
					  cTShdp17.setColor("auto");
					  cTShdp17.setFill("FFFF00");
					  run17.setText(" To "+pafObj.getLcaObj().getLca_end_date()+"");
					  run17.setFontFamily("Calibri");
					  run17.setFontSize(12);
					  run17.addBreak();
					 
					  XWPFParagraph headerline2 = doc.createParagraph();
					  XWPFRun runhl8=headerline2.createRun(); 
					  runhl8.setText("DOCUMENTS CHECK LIST");
					  runhl8.addBreak();
					  runhl8.setBold(true);
					  runhl8.setFontSize(14);
					  runhl8.setUnderline(UnderlinePatterns.SINGLE);
					  headerline2.setAlignment(ParagraphAlignment.CENTER);
					  runhl8.addBreak();
					  
					  XWPFParagraph paragraph12 = doc.createParagraph();
					  XWPFRun run81=paragraph12.createRun(); 
					  run81.setFontFamily("Calibri");
					  run81.setFontSize(12);
					  run81.setText("1.	LCA Cover Pages");
					  run81.addBreak();
					  run81.setText("2.	Notice of Job Posting");
					  run81.addBreak();
					  run81.setText("3.	Wage Rate Statement");
					  run81.addBreak();
					  run81.setText("4.	Actual Wage Memorandum");
					  run81.addBreak();
					  run81.setText("5.	Check Matrix");
					  run81.addBreak();
					  run81.setText("6.	Benefits Sheet");
					  run81.addBreak();
					  run81.setText("7.	Active Employee List");
					  run81.addBreak();
					  run81.setText("8.	Photo Copy of Complete LCA");
					  run81.addBreak();
					  run81.setText("9.	Prevailing Wage Memorandum");
					 
					  run81.addBreak();
					  run81.addBreak();
					  run81.addBreak();
					  run81.addBreak();
					  run81.addBreak();
					  run81.addBreak();
					  run81.setText("Signature:");
					  
					  run81.addBreak();
					  run81.addBreak();
					  run81.setText(" ________________");
					  run81.addBreak();
					  run81.setText("Madhavi Manthena");
					  run81.addBreak();
					  run81.setText("President");
					 
					  run81.addBreak(BreakType.PAGE);
					  
					 /* XWPFParagraph paragraph2 = doc.createParagraph();
					  XWPFRun page2logo=paragraph2.createRun(); 
					  paragraph2.setAlignment(ParagraphAlignment.LEFT); 
					  String imgFilep2 = env.getProperty("paflogo");//"D:/logo.png"
					  FileInputStream isp2 = new FileInputStream(imgFilep2);
					  page2logo.addPicture(isp2, Document.PICTURE_TYPE_PNG, imgFilep2, Units.toEMU(125), Units.toEMU(40)); // 200x200 pixels
					  isp2.close();
					  page2logo.setText("__________________________________________________________________________________");
					  page2logo.addBreak();*/

					  XWPFParagraph hline2 = doc.createParagraph();
					  XWPFRun runhl2=hline2.createRun(); 
					  runhl2.setText("WAGE RATE STATEMENT");
					  runhl2.addBreak();
					  runhl2.setBold(true);
					  runhl2.setFontSize(14);
					  runhl2.setUnderline(UnderlinePatterns.SINGLE);
					  hline2.setAlignment(ParagraphAlignment.CENTER);
					  runhl2.addBreak();
					  XWPFParagraph paragraphpage2 = doc.createParagraph();
					  XWPFRun runpage2=paragraphpage2.createRun(); 
					  runpage2.setFontFamily("Calibri");
					  runpage2.setFontSize(12);
					  runpage2.setText("The beneficiary will be paid ");
					  XWPFRun runpage21=paragraphpage2.createRun(); 
					  CTShd cTShdp21 = runpage21.getCTR().addNewRPr().addNewShd();
					  cTShdp21.setVal(STShd.CLEAR);
					  cTShdp21.setColor("auto");
					  cTShdp21.setFill("FFFF00");
					  runpage21.setText(" $ "+pafObj.getLcaObj().getWage_rate()+"/per year");
					  runpage21.setFontFamily("Calibri");
					  runpage21.setFontSize(12);
					  XWPFRun runpage212=paragraphpage2.createRun(); 
					  runpage212.setText(" for his/her services in the position of ");
					  XWPFRun runp22=paragraphpage2.createRun(); 
					  CTShd cTShdp22 = runp22.getCTR().addNewRPr().addNewShd();
					  cTShdp22.setVal(STShd.CLEAR);
					  cTShdp22.setColor("auto");
					  cTShdp22.setFill("FFFF00");
					  runp22.setText(""+pafObj.getLcaObj().getJob_title()+".");
					  runp22.setFontFamily("Calibri");
					  runp22.setFontSize(12);
					   
					  
					  XWPFRun runp23=paragraphpage2.createRun(); 
					  runp23.addBreak();
					  runp23.addBreak();
					  runp23.addBreak();
					  runp23.addBreak();
					  runp23.addBreak();
					  runp23.addBreak();
					  runp23.setText("Signature:");
					  runp23.addBreak();
					  runp23.addBreak();
					  runp23.addBreak();
					  runp23.addBreak();
					  runp23.setText(" ________________");
					  runp23.addBreak();
					  runp23.setText("Madhavi Manthena");
					  runp23.addBreak();
					  runp23.setText("President");
					  
					  runp23.addBreak(BreakType.PAGE);
					  
					 /* XWPFParagraph paragraph3 = doc.createParagraph();
					  XWPFRun page3logo=paragraph3.createRun(); 
					  paragraph3.setAlignment(ParagraphAlignment.LEFT);
					  String imgFilep3 = env.getProperty("paflogo");//"D:/logo.png"
					  FileInputStream isp3 = new FileInputStream(imgFilep3);
					  page3logo.addPicture(isp3, Document.PICTURE_TYPE_PNG, imgFilep3, Units.toEMU(125), Units.toEMU(40)); // 200x200 pixels
					  isp3.close();
					  page3logo.setText("__________________________________________________________________________________");
					  page3logo.addBreak();*/
					  
					  
					  XWPFParagraph hline3 = doc.createParagraph();
					  XWPFRun runhl3=hline3.createRun(); 
					  runhl3.setText("ACTUAL WAGE RATE MEMORANDUM");
					  runhl3.addBreak();
					  runhl3.setBold(true);
					  runhl3.setFontFamily("Calibri");
					  runhl3.setFontSize(14);
					  runhl3.setUnderline(UnderlinePatterns.SINGLE);
					  hline3.setAlignment(ParagraphAlignment.CENTER);
					  runhl3.addBreak();
					  XWPFRun runhl31=hline3.createRun(); 
					  runhl31.setText("To whom so ever It May concern");
					  runhl31.addBreak();
					  runhl31.setBold(true);
					  runhl31.setFontFamily("Calibri");
					  runhl31.setFontSize(12);
					  runhl31.setUnderline(UnderlinePatterns.SINGLE);
					  hline3.setAlignment(ParagraphAlignment.CENTER);
					  runhl31.addBreak();
					  XWPFParagraph paragraphpage3 = doc.createParagraph();
					  XWPFRun runpage3=paragraphpage3.createRun(); 
					  runpage3.setFontFamily("Calibri");
					  runpage3.setFontSize(12);
					  runpage3.setBold(true);
					  runpage3.setText("LCA filing date	:  ");
					  XWPFRun runp3=paragraphpage3.createRun(); 
					  CTShd cTShdp3 = runp3.getCTR().addNewRPr().addNewShd();
					  cTShdp3.setVal(STShd.CLEAR);
					  cTShdp3.setColor("auto");
					  cTShdp3.setFill("FFFF00");
					  runp3.setText(""+pafObj.getLcaObj().getLca_submitted_date()+"");
					  runp3.addBreak();
					  XWPFRun runp31=paragraphpage3.createRun(); 
					  runp31.setFontFamily("Calibri");
					  runp31.setFontSize(12);
					  runp31.setBold(true);
					  runp31.setUnderline(UnderlinePatterns.SINGLE);
					  runp31.setText("RE:");
					  runp31.addBreak();
					  XWPFRun runp32=paragraphpage3.createRun(); 
					  runp32.setFontFamily("Calibri");
					  runp32.setFontSize(12);
					  runp32.setText("   There are 0 other employee in our organization, who are ");
					  XWPFRun runp132=paragraphpage3.createRun(); 
					  CTShd cTShdp132 = runp132.getCTR().addNewRPr().addNewShd();
					  cTShdp132.setVal(STShd.CLEAR);
					  cTShdp132.setColor("auto");
					  cTShdp132.setFill("FFFF00");
					  runp132.setText(""+pafObj.getLcaObj().getJob_title()+" ");
					  runp132.setFontFamily("Calibri");
					  runp132.setFontSize(12);
					  XWPFRun runp13=paragraphpage3.createRun(); 
					  runp13.setText(" and perform job duties of ");
					  //runp13.addBreak();
					  XWPFRun runp232=paragraphpage3.createRun(); 
					  CTShd cTShdp232 = runp232.getCTR().addNewRPr().addNewShd();
					  cTShdp232.setVal(STShd.CLEAR);
					  cTShdp232.setColor("auto");
					  cTShdp232.setFill("FFFF00");
					  runp232.setText(""+pafObj.getLcaObj().getJob_title()+". ");
					  runp232.setFontFamily("Calibri");
					  runp232.setFontSize(12);
					  XWPFRun runp2322=paragraphpage3.createRun(); 
					  runp2322.setText("The wage range for this position is ");
					  runp2322.setFontFamily("Calibri");
					  runp2322.setFontSize(12);
					  XWPFRun runp2323=paragraphpage3.createRun(); 
					  CTShd cTShdp2323 = runp2323.getCTR().addNewRPr().addNewShd();
					  cTShdp2323.setVal(STShd.CLEAR);
					  cTShdp2323.setColor("auto");
					  cTShdp2323.setFill("FFFF00");
					  runp2323.setText(" $ "+pafObj.getSocObj().getFrom_wage_range()+" to $ "+pafObj.getSocObj().getTo_wage_range()+".");
					  runp2323.setFontFamily("Calibri");
					  runp2323.setFontSize(12);
					  runp2323.addBreak();
					  runp2323.addBreak();
					  
					  XWPFRun runpp2=paragraphpage3.createRun(); 
					  runpp2.setText("The salary paid by our Company for ");
					  runpp2.setFontFamily("Calibri");
					  runpp2.setFontSize(12);
					  XWPFRun runpp21=paragraphpage3.createRun(); 
					  CTShd cTShdpp21 = runpp21.getCTR().addNewRPr().addNewShd();
					  cTShdpp21.setVal(STShd.CLEAR);
					  cTShdpp21.setColor("auto");
					  cTShdpp21.setFill("FFFF00");
					  runpp21.setText(""+pafObj.getLcaObj().getJob_title()+"");
					  runpp21.setFontFamily("Calibri");
					  runpp21.setFontSize(12);
					  XWPFRun runpp22=paragraphpage3.createRun(); 
					  runpp22.setText(" in 2020 range from ");
					  runpp22.setFontFamily("Calibri");
					  runpp22.setFontSize(12);
					  XWPFRun runpp23=paragraphpage3.createRun(); 
					  CTShd cTShdpp23 = runpp23.getCTR().addNewRPr().addNewShd();
					  cTShdpp23.setVal(STShd.CLEAR);
					  cTShdpp23.setColor("auto");
					  cTShdpp23.setFill("FFFF00");
					  
					  runpp23.setText(" $ "+pafObj.getSocObj().getFrom_wage_range()+" to $ "+pafObj.getSocObj().getTo_wage_range()+"");
					  runpp23.setFontFamily("Calibri");
					  runpp23.setFontSize(12);
					  XWPFRun runpp231=paragraphpage3.createRun(); 
					  runpp231.setText(" annually.");
					  runpp231.setFontFamily("Calibri");
					  runpp231.setFontSize(12);
					  XWPFRun runpp24=paragraphpage3.createRun(); 
					  runpp24.setText(" This wage range was determined on the basis of factors mentioned below:");
					  runpp24.setFontFamily("Calibri");
					  runpp24.setFontSize(12);

					  runpp24.addBreak();
					  XWPFRun runpp25=paragraphpage3.createRun(); 
					  runpp25.setFontFamily("Calibri");
					  runpp25.setFontSize(12);
					  runpp25.setText("1.	Level of Education");
					  runpp25.addBreak();
					  runpp25.setText("2.	Years of Experience in the field");
					  runpp25.addBreak();
					  runpp25.setText("3.	Specific Job Responsibility");
					  runpp25.addBreak();
					  runpp25.setText("4.	Specialized knowledge");
					  runpp25.addBreak();
					  runpp25.setText("5.	Degree of Independent Responsibility");
					  runpp25.addBreak();
					  runpp25.setText("6.	Market Factors");
					  runpp25.addBreak();
					  runpp25.setText("7.	Nature of the duties involved and");
					  runpp25.addBreak();
					  XWPFRun runpp26=paragraphpage3.createRun(); 
					  runpp26.addBreak();
					  runpp26.setText("Salaries of the employees are adjusted on periodic basis, based upon performance.");
					  runpp26.addBreak();
					  XWPFRun runpp27=paragraphpage3.createRun(); 
					  runpp27.addBreak();
					  runpp27.setText("Please note that the employer applies the same methodology to all USA and H1B employees in this job classification when determining wages.");
					  runpp27.addBreak();

					  XWPFRun runpage31=paragraphpage3.createRun(); 
					  runpage31.addBreak();
					  runpage31.setText("Signature:");
					  runpage31.addBreak();
					  runpage31.addBreak();
					  runpage31.addBreak();
					  runpage31.addBreak();
					  runpage31.setText(" ________________");
					  runpage31.addBreak();
					  runpage31.setText("Madhavi Manthena");
					  runpage31.addBreak();
					  runpage31.setText("President");
					  runpage31.addBreak(BreakType.PAGE);
					  
					  
					/*  XWPFParagraph paragraph4 = doc.createParagraph();
					  XWPFRun page4logo=paragraph4.createRun(); 
					  paragraph4.setAlignment(ParagraphAlignment.LEFT);
					  String imgFilep4 = env.getProperty("paflogo");//"D:/logo.png"
					  FileInputStream isp4 = new FileInputStream(imgFilep4);
					  page4logo.addPicture(isp4, Document.PICTURE_TYPE_PNG, imgFilep4, Units.toEMU(125), Units.toEMU(40)); // 200x200 pixels
					  isp4.close();
					  page4logo.setText("__________________________________________________________________________________");
					  *///page4logo.addBreak();
					  
					  XWPFParagraph hline4 = doc.createParagraph();
					  XWPFRun runhl4=hline4.createRun(); 
					  runhl4.setText("CHECK MATRIX:");
					  runhl4.setBold(true);
					  runhl4.setFontFamily("Calibri");
					  runhl4.setFontSize(14);
					  runhl4.setUnderline(UnderlinePatterns.SINGLE);
					  hline4.setAlignment(ParagraphAlignment.LEFT);
					  //  XWPFParagraph para = doc.createParagraph();
					    
					    XWPFTable table1 = doc.createTable(4,4);
					    CTTblWidth width = table1.getCTTbl().addNewTblPr().addNewTblW();
					    width.setType(STTblWidth.DXA);
					    width.setW(BigInteger.valueOf(10000));
						mergeCellsHorizontal(table1, 0, 0, 3);
						XWPFTableRow rowOne = table1.getRow(0);
		                XWPFParagraph paratr1 = rowOne.getCell(0).getParagraphs().get(0);
		                paratr1.setAlignment(ParagraphAlignment.CENTER);
		                setRun(paratr1.createRun() , "Calibri" , 12, "Worksheet for Use in Determining OES Wage Level" , false, false);
					//	table1.getRow(0).getCell(0).setText("Worksheet for Use in Determining OES Wage Level");

						table1.getRow(1).getCell(0).setText("Job Title:");
						table1.getRow(1).getCell(1).setText(""+pafObj.getLcaObj().getJob_title()+"");
						table1.getRow(1).getCell(2);
						table1.getRow(1).getCell(3);
					
						 table1.getRow(2).getCell(0).setText("O*NET Title: ");
						 table1.getRow(2).getCell(1).setText(""+pafObj.getSocObj().getSoccode_title()+"");
						 table1.getRow(2).getCell(2).setText("Date: ");
						 table1.getRow(2).getCell(3).setText(""+pafObj.getLcaObj().getLca_submitted_date()+"");
						 
						table1.getRow(3).getCell(0).setText("O*NET Code: ");
						table1.getRow(3).getCell(1).setText(""+pafObj.getLcaObj().getSoc()+"");
						table1.getRow(3).getCell(2).setText("Reviewer: ");
						table1.getRow(3).getCell(3).setText("Madhavi Manthena");
						
					/*	XWPFTableRow rowOne = table1.getRow(0);
		                XWPFParagraph paratr1 = rowOne.getCell(0).getParagraphs().get(0);
		                paratr1.setAlignment(ParagraphAlignment.CENTER);
		                setRun(paratr1.createRun() , "Calibri" , 12, "Worksheet for Use in Determining OES Wage Level" , false, false);
						
						 XWPFTableRow row2 = table1.getRow(1);
						 XWPFParagraph paratr2c1 = row2.getCell(0).addParagraph();
			             setRunT(paratr2c1.createRun() , "Calibri" , 10, "Job Title:" , false, false);
						 XWPFParagraph paratr2c2 = row2.getCell(1).addParagraph();
			             setRunTd(paratr2c2.createRun() , "Calibri" , 10, pafObj.getLcaObj().getJob_title() , false, false);
						
						  XWPFTableRow row3 = table1.getRow(2);
						 XWPFParagraph paratr3c1 = row3.getCell(0).addParagraph();
						setRunT(paratr3c1.createRun() , "Calibri" , 10, "O*NET Title:" , false, false);
			             XWPFParagraph paratr3c2 = row3.getCell(1).addParagraph();
			            setRunTd(paratr3c2.createRun() , "Calibri" , 10, pafObj.getSocObj().getSoccode_title() , false, false);
			            XWPFParagraph paratr3c3 = row3.getCell(2).addParagraph();
			            setRunT(paratr3c3.createRun() , "Calibri" , 10, "Date: " , false, false);
						
			            XWPFTableRow row4 = table1.getRow(3);
						table1.getRow(2).getCell(0).setText("O*NET Title: ");
						XWPFParagraph paratr4c1 = row4.getCell(0).addParagraph();
						setRunT(paratr4c1.createRun() , "Calibri" , 10, "O*NET Code:" , false, false);
			            XWPFParagraph paratr4c2 = row4.getCell(1).addParagraph();
			            setRunTd(paratr4c2.createRun() , "Calibri" , 10, pafObj.getLcaObj().getSoc() , false, false);
			            XWPFParagraph paratr4c3 = row4.getCell(2).addParagraph();
			            setRunT(paratr4c3.createRun() , "Calibri" , 10, "Reviewer: " , false, true);
						XWPFParagraph paratr4c4 = row4.getCell(3).addParagraph();
						setRunT(paratr4c4.createRun() , "Calibri" , 10, "Madhavi Manthena" , false, false);*/
					
						
						XWPFRun paraT1 = doc.createParagraph().createRun();
						paraT1.addBreak();
						
						XWPFTable table2 = doc.createTable(7,5);
					    CTTblWidth width2 = table2.getCTTbl().addNewTblPr().addNewTblW();
					    width2.setType(STTblWidth.DXA);
					    width2.setW(BigInteger.valueOf(10000));
					    mergeCellsHorizontal(table2, 6, 0, 2);
					    
					 /*    XWPFTableRow Tbl2Row1 = table2.getRow(0);
						XWPFParagraph paratTbl2Row1c1 = Tbl2Row1.getCell(0).getParagraphs().get(0);
						setRunT(paratTbl2Row1c1.createRun() , "Calibri" , 10, "Indicator" , false, false);
			            XWPFParagraph paratTbl2Row1c2 = Tbl2Row1.getCell(1).getParagraphs().get(0);
			            setRunT(paratTbl2Row1c2.createRun() , "Calibri" , 10, "Job Offer Requirements" , false, false);
			            XWPFParagraph paratTbl2Row1c3 = Tbl2Row1.getCell(2).addParagraph();
			            setRunT(paratTbl2Row1c3.createRun() , "Calibri" , 10, "O*Net - Usual Requirements" , false, false);
						XWPFParagraph paratTbl2Row1c4 = Tbl2Row1.getCell(3).getParagraphs().get(0);
						setRunT(paratTbl2Row1c4.createRun() , "Calibri" , 10, "Comments" , false, false);
			            XWPFParagraph paratTbl2Row1c5 = Tbl2Row1.getCell(4).getParagraphs().get(0);
			            setRunT(paratTbl2Row1c5.createRun() , "Calibri" , 10, "Wage Level Result" , false, false);
					    */
			            table2.getRow(0).getCell(0).setText("Indicator");
					    table2.getRow(0).getCell(1).setText("Job Offer Requirements");
					    table2.getRow(0).getCell(2).setText("O*Net - Usual Requirements");
					    table2.getRow(0).getCell(3).setText("Comments");
					    table2.getRow(0).getCell(4).setText("Wage Level Result");
					    
					    /*    
					    table2.getRow(1).getCell(0).setText("Step 1. Requirement");
					    table2.getRow(1).getCell(1).setText(""+pafObj.getSocObj().getDuties()+".");
					    // table2.getRow(1).getCell(2);
					   // table2.getRow(1).getCell(3);
					    table2.getRow(1).getCell(4).setText("Wage Level :"+pafObj.getLcaObj().getWage_level()+"");
					    
					    table2.getRow(2).getCell(0).setText("Step 2. Experience");
					    table2.getRow(2).getCell(1).setText("2 -3 Years of Experience in IT the Industry.");
					    table2.getRow(2).getCell(2).setText("A considerable amount of work-related skill, knowledge, or experience is needed for these occupations.");
					   // table2.getRow(2).getCell(3);
					    table2.getRow(2).getCell(4).setText("1");
					    
					    table2.getRow(3).getCell(0).setText("Step 3. Education");
					    table2.getRow(3).getCell(1).setText("Bachelor’s Degree in Computer Sciences/Engineering/Information Systems/Business Administration or a related field.");
					    table2.getRow(3).getCell(2).setText("Most of these occupations require a four-year bachelor's degree, but some do not. ");
					  //  table2.getRow(3).getCell(3);
					    table2.getRow(3).getCell(4).setText("1");
					    */
					    table2.getRow(4).getCell(0).setText("Step 4. Special Skills (Y/N)");
					  /*  table2.getRow(4).getCell(1);
					    table2.getRow(4).getCell(2);
					    table2.getRow(4).getCell(3);
					    table2.getRow(4).getCell(4);*/
					    
					    table2.getRow(5).getCell(0).setText("Step 5. Supervisory Duties (Y/N)");
				/*	    table2.getRow(5).getCell(1);
					    table2.getRow(5).getCell(2);
					    table2.getRow(5).getCell(3);
					    table2.getRow(5).getCell(4);*/
					    
					    
					    /*table2.getRow(6).getCell(0);
					    table2.getRow(6).getCell(1);
					    table2.getRow(6).getCell(2);*/
					    table2.getRow(6).getCell(3).setText("SUM:");
					    table2.getRow(6).getCell(4).setText("2");
			            
			            
			      	    XWPFTableRow Tbl2Row2 = table2.getRow(1);
						XWPFParagraph paratTbl2Row2c1 = Tbl2Row2.getCell(0).getParagraphs().get(0);
						setRunT(paratTbl2Row2c1.createRun() , "Calibri" , 10, "Step 1. Requirement" , false, false);
			            XWPFParagraph paratTbl2Row2c2 = Tbl2Row2.getCell(1).getParagraphs().get(0);
			            setRunTd(paratTbl2Row2c2.createRun() , "Calibri" , 10, pafObj.getSocObj().getDuties() , false, false);
			            XWPFParagraph paratTbl2Row2c5 = Tbl2Row2.getCell(4).getParagraphs().get(0);
			            setRunT(paratTbl2Row2c5.createRun() , "Calibri" , 10, "Wage Level :"+pafObj.getLcaObj().getWage_level()+"" , false, false);
				
			            XWPFTableRow Tbl2Row3 = table2.getRow(2);
						XWPFParagraph paratTbl2Row3c1 = Tbl2Row3.getCell(0).getParagraphs().get(0);
						setRunT(paratTbl2Row3c1.createRun() , "Calibri" , 10, "Step 2. Experience" , false, false);
			            XWPFParagraph paratTbl2Row3c2 = Tbl2Row3.getCell(1).getParagraphs().get(0);
			            setRunT(paratTbl2Row3c2.createRun() , "Calibri" , 10, "2 -3 Years of Experience in IT the Industry." , false, false);
			            XWPFParagraph paratTbl2Row3c3 = Tbl2Row3.getCell(2).getParagraphs().get(0);
			            setRunT(paratTbl2Row3c3.createRun() , "Calibri" , 10, "A considerable amount of work-related skill, knowledge, or experience is needed for these occupations." , false, false);
						XWPFParagraph paratTbl2Row3c5 = Tbl2Row3.getCell(4).getParagraphs().get(0);
			            setRunTd(paratTbl2Row3c5.createRun() , "Calibri" , 10, "1" , false, false);
					    
			            
			            
			            XWPFTableRow Tbl2Row4 = table2.getRow(3);
						XWPFParagraph paratTbl2Row4c1 = Tbl2Row4.getCell(0).getParagraphs().get(0);
						setRunT(paratTbl2Row4c1.createRun() , "Calibri" , 10, "Step 3. Education" , false, false);
			            XWPFParagraph paratTbl2Row4c2 = Tbl2Row4.getCell(1).getParagraphs().get(0);
			            setRunT(paratTbl2Row4c2.createRun() , "Calibri" , 10, "Bachelor’s Degree in Computer Sciences/Engineering/Information Systems/Business Administration or a related field." , false, false);
			            XWPFParagraph paratTbl2Row4c3 = Tbl2Row4.getCell(2).getParagraphs().get(0);
			            setRunT(paratTbl2Row4c3.createRun() , "Calibri" , 10, "Most of these occupations require a four-year bachelor's degree, but some do not. " , false, false);
						XWPFParagraph paratTbl2Row4c5 = Tbl2Row4.getCell(4).getParagraphs().get(0);
			            setRunT(paratTbl2Row4c5.createRun() , "Calibri" , 10, "1" , false, false);
					  /*  
			            XWPFTableRow Tbl2Row5 = table2.getRow(4);
						XWPFParagraph paratTbl2Row5c1 = Tbl2Row5.getCell(0).getParagraphs().get(0);
						setRunT(paratTbl2Row5c1.createRun() , "Calibri" , 10, "Step 4. Special Skills (Y/N)" , false, false);
					    
						
						XWPFTableRow Tbl2Row6 = table2.getRow(5);
						XWPFParagraph paratTbl2Row6c4 = Tbl2Row6.getCell(0).getParagraphs().get(0);
			            setRunT(paratTbl2Row6c4.createRun() , "Calibri" , 10, "Step 5. Supervisory Duties (Y/N) " , false, false);
						
			            XWPFTableRow Tbl2Row7 = table2.getRow(6);
						XWPFParagraph paratTbl2Row7c4 = Tbl2Row7.getCell(3).getParagraphs().get(0);
			            setRunT(paratTbl2Row7c4.createRun() , "Calibri" , 10, "SUM: " , false, false);
			            XWPFParagraph paratTbl2Row7c5 = Tbl2Row7.getCell(4).addParagraph();
			            setRunTd(paratTbl2Row7c5.createRun() , "Calibri" , 10, "2", false, false);*/
					    
					  /*  XWPFParagraph paragraph5 = doc.createParagraph();
					    XWPFRun page5logo=paragraph5.createRun(); 
					    paragraph5.setAlignment(ParagraphAlignment.LEFT);
					    String imgFilep5 = env.getProperty("paflogo");//"D:/logo.png"
					    FileInputStream isp5 = new FileInputStream(imgFilep5);
					    page5logo.addPicture(isp5, Document.PICTURE_TYPE_PNG, imgFilep5, Units.toEMU(125), Units.toEMU(40)); // 200x200 pixels
					    isp5.close();
					    page5logo.setText("__________________________________________________________________________________");
					  *///  page5logo.addBreak(); 
			           // paraT1.addBreak();
					  XWPFParagraph hline5 = doc.createParagraph();
					  XWPFRun runhl5=hline5.createRun(); 
					  runhl5.addBreak();
					  runhl5.setText("BENEFIT SUMMARY:");
					  runhl5.setBold(true);
					  runhl5.setFontFamily("Calibri");
					  runhl5.setFontSize(14);
					  runhl5.setUnderline(UnderlinePatterns.SINGLE);
					  hline5.setAlignment(ParagraphAlignment.CENTER);
					  
					  XWPFParagraph paragraphpage5 = doc.createParagraph();
					  XWPFRun runpage5=paragraphpage5.createRun(); 
					  runpage5.addBreak();
					  runpage5.addBreak();
					  runpage5.setFontFamily("Calibri");
					  runpage5.setFontSize(12);
					  runpage5.setText("Please find the summary of the benefits offered to employees of Tekskills Inc. All employees must meet eligibility requirements as stated in each plan. There is no distinction between the benefits offered to H-1B employees and other employees.");
					  runpage5.addBreak(); 
					  runpage5.addBreak();
					  runpage5.setText("Benefits offered to H-1B employees are the same as offered to other employees.");
					  runpage5.addBreak();
					  runpage5.addBreak();
					  runpage5.addBreak();

					  runpage5.setText("->	Health Insurance");
					  runpage5.addBreak();
					  runpage5.setText("->	Dental & Vision Insurance");
					  runpage5.addBreak();
					  runpage5.setText("->	Two Weeks Paid Vacation");
					  runpage5.addBreak();
					  runpage5.addBreak(BreakType.PAGE);
					  
					  
					  /*XWPFParagraph paragraph6 = doc.createParagraph();
					  XWPFRun page6logo=paragraph6.createRun(); 
					  paragraph6.setAlignment(ParagraphAlignment.LEFT);
					  String imgFilep6 = env.getProperty("paflogo");//"D:/logo.png"
					  FileInputStream isp6 = new FileInputStream(imgFilep6);
					  page6logo.addPicture(isp6, Document.PICTURE_TYPE_PNG, imgFilep6, Units.toEMU(125), Units.toEMU(40)); // 200x200 pixels
					  isp6.close();
					  page6logo.setText("__________________________________________________________________________________");
					  page6logo.addBreak(); */
					  XWPFParagraph hline6 = doc.createParagraph();
					  XWPFRun runhl6=hline6.createRun(); 
					  runhl6.setText("Active Employees List");
					  runhl6.setBold(true);
					  runhl6.setFontFamily("Calibri");
					  runhl6.setFontSize(14);
					  runhl6.setUnderline(UnderlinePatterns.SINGLE);
					  hline6.setAlignment(ParagraphAlignment.CENTER);
					  runhl6.addBreak();
					  /*XWPFTable table3 = doc.createTable(2,5);
					  XWPFParagraph paratable3 = doc.createParagraph();
					  XWPFRun runt3        = paratable3.createRun();
					  CTTblWidth width3 = table3.getCTTbl().addNewTblPr().addNewTblW();
					  width3.setType(STTblWidth.DXA);
					  width3.setW(BigInteger.valueOf(10000));
					  
					  table3.getRow(0).getCell(0).setText("Name of Employee");
					  table3.getRow(0).getCell(1).setText("Job Title");
					  table3.getRow(0).getCell(2).setText("Immigration Status");
					  table3.getRow(0).getCell(3).setText("From   ");
					  table3.getRow(0).getCell(4).setText("To");
					  runt3.addBreak();*/
					  
					  
					  List<BasicDetailsDto> activeempList=h1bService.getActiveEmployeeList();
					  XWPFTable tableOne = doc.createTable();
					  CTTblWidth width3 = tableOne.getCTTbl().addNewTblPr().addNewTblW();
					  
					  width3.setType(STTblWidth.DXA);
					  width3.setW(BigInteger.valueOf(10000));
					   XWPFTableRow tableOneRowOne = tableOne.getRow(0);
					   tableOneRowOne.getCell(0).setText("Name of Employee");
					   tableOneRowOne.addNewTableCell().setText("Job Title");
					   tableOneRowOne.addNewTableCell().setText("Immigration Status");
					   tableOneRowOne.addNewTableCell().setText("From");
					   tableOneRowOne.addNewTableCell().setText("To");
					   
					   for (BasicDetailsDto basicDetailsDto : activeempList) {
						   Integer candidateid=basicDetailsDto.getCandidate_id();
					   XWPFTableRow tableOneRowTwo = tableOne.createRow();
					   tableOneRowTwo.getCell(0).setText(""+basicDetailsDto.getFullname()+"");
					   if(basicDetailsDto.getDesignation()!=null) {
						   tableOneRowTwo.getCell(1).setText(""+basicDetailsDto.getDesignation()+"");
					   }else {
						   tableOneRowTwo.getCell(1).setText("NA");
					   }
					   tableOneRowTwo.getCell(2).setText(""+basicDetailsDto.getWorkAuth()+"");
					   if(basicDetailsDto.getDoj()!=null) {
						   tableOneRowTwo.getCell(3).setText(""+basicDetailsDto.getDoj()+"");
					   }else {
						   tableOneRowTwo.getCell(3).setText("NA");
					   }
					    
					   tableOneRowTwo.getCell(4).setText("Present");
					   }
					
					  /* for (BasicDetailsDto basicDetailsDto : activeempList) {
						   Integer candidateid=basicDetailsDto.getCandidate_id();
						   System.out.println("candidateid****"+candidateid);
						   LCADetailsDto lcaObj2=immiService.getTopLcaByCandidateId(candidateid);
						   if(lcaObj2!=null) {
							   XWPFTableRow tableOneRowTwo = tableOne.createRow();
							   tableOneRowTwo.getCell(0).setText(""+basicDetailsDto.getFullname()+"");
							   if(lcaObj2.getJob_title()!=null) {
								   tableOneRowTwo.getCell(1).setText(""+lcaObj2.getJob_title()+"");
							   }else {
								   tableOneRowTwo.getCell(1).setText("NA");
							   }
							   tableOneRowTwo.getCell(2).setText(""+basicDetailsDto.getWorkAuth()+"");
							   if(lcaObj2.getLca_begin_date()!=null) {
								   tableOneRowTwo.getCell(3).setText(""+lcaObj2.getLca_begin_date()+"");
							   }else {
								   tableOneRowTwo.getCell(3).setText("NA");
							   }
							   if(lcaObj2.getLca_end_date()!=null) {
								   tableOneRowTwo.getCell(4).setText(""+lcaObj2.getLca_end_date()+"");
							   }else {
								   tableOneRowTwo.getCell(4).setText("NA");
							   }
						   }else {
							   XWPFTableRow tableOneRowTwo = tableOne.createRow();
							   tableOneRowTwo.getCell(0).setText(""+basicDetailsDto.getFullname()+"");
							   tableOneRowTwo.getCell(1).setText("NA");
							   tableOneRowTwo.getCell(2).setText(""+basicDetailsDto.getWorkAuth()+"");
							   tableOneRowTwo.getCell(3).setText("NA");
							   tableOneRowTwo.getCell(4).setText("NA");
							   if(lcaObj2.getJob_title()!=null) {
								   tableOneRowTwo.getCell(1).setText(""+lcaObj2.getJob_title()+"");
							   }else {
								   tableOneRowTwo.getCell(1).setText("NA");
							   }
							   tableOneRowTwo.getCell(2).setText(""+basicDetailsDto.getWorkAuth()+"");
							   if(lcaObj2.getLca_begin_date()!=null) {
								   tableOneRowTwo.getCell(3).setText(""+lcaObj2.getLca_begin_date()+"");
							   }else {
								   tableOneRowTwo.getCell(3).setText("NA");
							   }
							   if(lcaObj2.getLca_end_date()!=null) {
								   tableOneRowTwo.getCell(4).setText(""+lcaObj2.getLca_end_date()+"");
							   }else {
								   tableOneRowTwo.getCell(4).setText("NA");
							   }
						   }
					   
					 }
					  */
					  // create header start
					  CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
					  XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);
					  XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
					  paragraph = header.getParagraphArray(0);
					  paragraph.setAlignment(ParagraphAlignment.LEFT);
					  XWPFRun runh=paragraph.createRun(); 
					  String himgFile=env.getProperty("paflogo");
					  //"D:/logo.png";
					  XWPFPicture picture = runh.addPicture(new FileInputStream(himgFile), XWPFDocument.PICTURE_TYPE_PNG, himgFile, Units.toEMU(125), Units.toEMU(40));
					  System.out.println(picture); //XWPFPicture is added
					  System.out.println(picture.getPictureData()); //but without access to XWPFPictureData (no blipID)
					  String blipID = "";
					  for(XWPFPictureData picturedata : header.getAllPackagePictures()) {
					   blipID = header.getRelationId(picturedata);
					   System.out.println(blipID); //the XWPFPictureData are already there
					  }
					  picture.getCTPicture().getBlipFill().getBlip().setEmbed(blipID); //now they have a blipID also
					  System.out.println(picture.getPictureData());
					  runh.setText("__________________________________________________________________________________");
					  
					  // create footer start
					  XWPFParagraph footerPara = doc.createParagraph();
					  XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);
					  footerPara = footer.getParagraphArray(0);
					  //paragraph.setAlignment(ParagraphAlignment.LEFT);
					  XWPFRun runf1=footerPara.createRun(); 
					  runf1.setText("__________________________________________________________________________________");
					  runf1.addBreak();
					  XWPFRun runfl=footerPara.createRun(); 
					  runfl.setFontFamily("Arial");
					  runfl.setFontSize(9);
					  runfl.setBold(true);
					  runfl.setColor("8B0000");
					  runfl.setText("5 Independence Way, Suite 135,                           Tel: (732) 640.1204 		URL: ");
					  XWPFRun runfl1=footerPara.createRun();
					  CTShd cTShdfl1 = runfl1.getCTR().addNewRPr().addNewShd();
					  cTShdfl1.setVal(STShd.CLEAR);
					  runfl1.setFontFamily("Arial");
					  runfl1.setBold(true);
					  runfl1.setFontSize(9);
					  runfl1.setColor("00008b");
					  runfl1.setText("www.tekskillsinc.com");  
					  runfl1.addBreak();
					  XWPFRun runf2=footerPara.createRun(); 
					 // runf.setText("Princeton NJ 08540		                                        Fax:	(888) 543.5125			                          Email: info@tekskillsinc.com");
					  runf2.setFontFamily("Arial");
					  runf2.setFontSize(9);
					  runf2.setBold(true);
					  runf2.setColor("8B0000");
					  runf2.setText(" Princeton NJ 08540                                              Fax: (888) 543.5125              Email: ");
					  XWPFRun runfl2=footerPara.createRun();
					  CTShd cTShdfl2 = runfl2.getCTR().addNewRPr().addNewShd();
					  cTShdfl2.setVal(STShd.CLEAR);
					  runfl2.setFontFamily("Arial");
					  runfl2.setUnderline(UnderlinePatterns.SINGLE);
					  runfl2.setFontSize(9);
					  runfl2.setBold(true);
					  runfl2.setColor("00008b");
					  runfl2.setText("info@tekskillsinc.com");  
					  runfl2.addBreak(); 
					 /* // create footer start
					  XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);
					  paragraph = footer.getParagraphArray(0);
					  paragraph.setAlignment(ParagraphAlignment.LEFT);
					  XWPFRun runf=paragraph.createRun(); 
					  runf.setText("__________________________________________________________________________________");
					  runf.addBreak();
					  XWPFRun runf1=paragraph.createRun(); 
					  runf1.setFontFamily("Arial");
					  runf1.setFontSize(9);
					  runf1.setColor("ff0000");
					  runf1.setText("5 Independence Way, Suite 135,		                     Tel: (732) 640.1204		                         URL: www.tekskillsinc.com");
					  runf1.addBreak();
					  runf1.setText("Princeton NJ 08540		                                          Fax:	(888) 543.5125			                     Email: info@tekskillsinc.com");*/
					//  doc.write(new FileOutputStream("D:/paflca.docx"));
					  String filetitle =""+pafObj.getLcaObj().getLca_number()+"_PAFWithout Notice of Posting";
						String filename = filetitle + ".doc";
						DiskFileItemFactory factory = new DiskFileItemFactory();
						factory.setSizeThreshold(THRESHOLD_SIZE);
						factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
						ServletFileUpload upload = new ServletFileUpload(factory);
						upload.setFileSizeMax(MAX_FILE_SIZE);
						upload.setSizeMax(MAX_REQUEST_SIZE);
						
						// constructs the directory path to store upload file
						String uploadPath = context.getRealPath("") + UPLOAD_DIRECTORY;

						if (!new File(uploadPath).exists()) {
							new File(uploadPath).mkdir();
							logger.info("directory created  ");
						}
						String desFile = uploadPath + File.separator + filetitle;
						File dest = new File(uploadPath + File.separator + filename);
						try {
						
							  doc.write(new FileOutputStream(dest));
							logger.info("File created with content ");
						} catch (IOException e) {
							e.printStackTrace();
						}
						

						File file = new File(desFile + ".doc");
						FileInputStream in = new FileInputStream(file);
						response.setContentType("application/ocet");
						response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
						response.setHeader("Content-Length", String.valueOf(file.length()));
						FileCopyUtils.copy(in, response.getOutputStream());
				
					
					}
				}
				else if(doctype.equalsIgnoreCase("ApprovedLCA")) {
					LCADetailsEntity lcaObj = immiService.getLcaDetailsById(lcaid);
					String filename ="";
					if(lcaObj!=null) {
						filename = immiService.getSubDocDetailsByName("Approved LCA",lcaid,lcaObj.getCandidate_id());
					}
				
					String filepath = null;
					String workingDir = env.getProperty("documents");
			        String path = workingDir + File.separator + lcaObj.getCandidate_id();
						   filepath = path + File.separator + "Immigration" + File.separator + filename;
					
					System.out.println(filepath);
					File ff = new File(filepath);
					if (ff.exists() == true) {
						FileInputStream fis = new FileInputStream(ff);
						byte[] inputBytes = new byte[(int) ff.length()];
						fis.read(inputBytes);
						response.setContentType("application/octet-stream;");
						response.setHeader("Content-Disposition", "attachment; filename=\"" + filename);
						OutputStream os = response.getOutputStream(); // file output stream
						os.write(inputBytes);
						os.close();
					} else {
						PrintWriter out = response.getWriter();
						out.println("<script type=\"text/javascript\">");
						out.println("alert('No file available');");
						out.println("window.history.back();");
						out.println("</script>");
						out.close();
					}
				}
				

			} else {

			}

		} catch (Exception e) {
			logger.error("Error in downloadpdf " + e);
		}
		return null;
	}
	
	

	public static void mergeCellsHorizontal(XWPFTable table, int row, int startCell, int endCell) {
		for (int i = startCell; i <= endCell; i++) {
			XWPFTableCell cell = table.getRow(row).getCell(i);
			if (i == startCell) {
				// The first merged cell is set with RESTART merge value  
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one, are set with CONTINUE  
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
			}
		}
	}
	
	private static void setRun (XWPFRun run , String fontFamily , int fontSize , String text , boolean bold , boolean addBreak) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
       // run.setColor(colorRGB);
        run.setText(text);
        run.setBold(bold);
       // if (addBreak) run.addBreak();
    }
	private static void setRunT (XWPFRun run , String fontFamily , int fontSize , String text , boolean bold , boolean addBreak) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setText(text);
      //  if (addBreak) run.addBreak();
    }
	
	private static void setRunTd(XWPFRun run , String fontFamily , int fontSize , String text , boolean bold , boolean addBreak) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
       // run.setColor(colorRGB);
        run.setText(text);
       // run.setBold(bold);
        run.getCTR().addNewRPr().addNewHighlight().setVal(STHighlightColor.YELLOW);
       // run.getCTR().addNewRPr().addNewHighlight().setVal(Alignment);
        //if (addBreak) run.addBreak();
    }
	@RequestMapping(value = { "/soccode" }, method = RequestMethod.GET)
	public String addCustomer(Model model, HttpServletRequest request, HttpServletResponse response) {
		String userName = (String) request.getSession().getAttribute("username");
		try {
		if (userName == null) {
			return "redirect:/loginpage";
		} else {
			List<SOCcodeMasterEntity> soccodeList = h1bService.getSocCodeMasterList();
			model.addAttribute("soccodeList", soccodeList);
		}
	}catch (Exception e) {
		logger.error("Error occured in soccode ---", e);
	  }
		return "SOCCodeMaster";
	}
	@RequestMapping(value = { "/saveupdatesoc" }, method = RequestMethod.POST)
	@ResponseBody
	public String saveCustomerMaster(Model model, HttpServletRequest request, HttpServletResponse response) {
		String userName = (String) request.getSession().getAttribute("username");
		String message = "";
		try {
		if (userName == null) {
			return "redirect:/loginpage";
		} else {
			HttpSession session = request.getSession(true);
			Integer loginempno = (Integer) session.getAttribute("empno");
			
			String socid = request.getParameter("socid");
			String soc_code = request.getParameter("soc_code");
			String soccode_title = request.getParameter("soccode_title");
			String duties = request.getParameter("duties");
			String from_wagerate = request.getParameter("from_wagerate");
			String to_wagerate = request.getParameter("to_wagerate");
			String occ_clasfi = request.getParameter("occu_classfication");
			
			message = h1bService.saveupdatesoc(soc_code, soccode_title,duties, from_wagerate, to_wagerate,occ_clasfi,loginempno,socid);
			
		}
		}catch (Exception e) {
			logger.error("Error occured in saveupdatesoc ---", e);
		  }
			return message;
		
	}
	@RequestMapping(value = { "/SOCDetailsById" }, method = RequestMethod.GET)
	@ResponseBody
	public SOCcodeMasterEntity CustomerDetailsById(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		SOCcodeMasterEntity socobj = null;
		try{
			String socid = request.getParameter("socid");
			socobj = h1bService.getSOCDetailsById(socid);
		}catch (Exception e) {
			logger.error("Error occured in SOCDetailsById ---", e);
		  }
		return socobj;
	}
	@RequestMapping(value = { "/checkSocCode" }, method = RequestMethod.GET)
	@ResponseBody
	public String ssncheck(Model model, HttpServletRequest request, HttpServletResponse response) {
		String userName = (String) request.getSession().getAttribute("username");
		HttpSession session = request.getSession(true);
		String soccode =null;
		try {
		String soc_code = request.getParameter("soc_code");
		soccode = h1bService.checkSocCode(soc_code);
		}catch (Exception e) {
			logger.error("Error in checksoccode " + e);
		}
		return soccode;
	}
	

	@RequestMapping(value = { "/checkpetitionPacket" }, method = RequestMethod.GET)
	@ResponseBody
	public String h1initiationhistory(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String docstatus = null;
		try{
			 String candidateid = request.getParameter("candidateid");
			 String initiateid = request.getParameter("initiateid");
			 docstatus = h1bService.checkpetitionPacket(candidateid,initiateid);
		}catch (Exception e) {
			logger.error("Error occured in checkpetitionPacket ---", e);
		  }
		return docstatus;
	}
	
	@RequestMapping(value = { "/downloadpetitionPacket" }, method = RequestMethod.GET)
    public void downloadzip(HttpServletResponse response, HttpServletRequest request) throws Exception {
           try {
                  String userName = (String) request.getSession().getAttribute("username");
                  if (userName == null) {
                        PrintWriter out = response.getWriter();
                        out.println("<script type=\"text/javascript\">");
                        out.println("window.location.href='login.jsp'");
                        out.println("</script>");
                        out.close();
                  } else {
                	  String candidateid = request.getParameter("candidateid");
         			 String initiateid = request.getParameter("initiateid");
                       
         			 List<String> doclist=h1bService.getpetitionPacketDocuments(candidateid,initiateid);
         			 String profilename=h1bService.getProfileNameByinitiateid(initiateid);
                        if(doclist.size()>0) {
                        	FileOutputStream fos = null; ZipOutputStream zipOut = null;        FileInputStream fis = null;
                            try {
                            	String workingDir = env.getProperty("documents");
                                fos = new FileOutputStream(workingDir+ File.separator +""+profilename+"_Petition_Packet.zip");
                                zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
                                for(String filename:doclist){
                                	System.out.println("*******8"+filename);
                                	if(!filename.isEmpty()) {
                                	String path = workingDir+ File.separator +candidateid+ File.separator + "H-1B"+ File.separator +filename;
                                	System.out.println("*****path----**8"+path);
                                	File input = new File(path);
                                	if(input.exists()) {
                                		  fis = new FileInputStream(input);
                                          ZipEntry ze = new ZipEntry(input.getName());
                                          zipOut.putNextEntry(ze);
                                          byte[] tmp = new byte[4*1024];
                                          int size = 0;
                                          while((size = fis.read(tmp)) != -1){
                                              zipOut.write(tmp, 0, size);
                                          }
                                          zipOut.flush();
                                          fis.close();
                                	  }
                                	}
                                }
                                zipOut.close();
                                File ff = new File(workingDir+ File.separator +""+profilename+"_Petition_Packet.zip");
                               
                				if (ff.exists() == true) {
                					FileInputStream fiss = new FileInputStream(ff);
                					byte[] inputBytes = new byte[(int) ff.length()];
                					fiss.read(inputBytes);
                					response.setContentType("application/zip");
                					response.setHeader("Content-Disposition", "attachment; filename="+profilename+"_Petition_Packet.zip");
                					ServletOutputStream os = response.getOutputStream(); // file output stream
                					os.write(inputBytes);
                					os.close();
                					fiss.close();
                					System.out.println("Zip downloaded..");
                				}
                				
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally{
                                try{
                                    if(fos != null) fos.close();
                                } catch(Exception ex){
                                     
                                }

                               }
                        }
                    
                  }
                  } catch (Exception e) {
                  logger.error("Error while downloadpetitionPacket action in H1BController" + e);
           }
    }
	
	@RequestMapping(value = { "/getalltasks" }, method = RequestMethod.GET)
	@ResponseBody
	public List<TaskDetailsEntity> getalltasksbyCandid(Model model, HttpServletRequest request) {
		List<TaskDetailsEntity> taskslist = null;
		try {
			String candidateid = request.getParameter("candidateid");
			taskslist = h1bService.getalltaskdetailsbycandidateid(candidateid);

		} catch (Exception e) {
			logger.error("Error in getalltasks" + e);
		}
		return taskslist;
	}
	
	@RequestMapping(value = { "/downloadcategories" }, method = RequestMethod.GET)
    public void downloadcategories(HttpServletResponse response, HttpServletRequest request) throws Exception {
           try {
                  String userName = (String) request.getSession().getAttribute("username");
                  if (userName == null) {
                        PrintWriter out = response.getWriter();
                        out.println("<script type=\"text/javascript\">");
                        out.println("window.location.href='login.jsp'");
                        out.println("</script>");
                        out.close();
                  } else {
                	  String candidateid = request.getParameter("candidateid");
         			 String initiateid = request.getParameter("initiateid");
         			 String documenttypeid = request.getParameter("documenttypeid");
                       
         			 List<String> doclist=h1bService.getdocumentsByCategoryId(candidateid,initiateid,documenttypeid);
         			 String documentTypeName=h1bService.getdocumentTypeById(documenttypeid);
         					 if(doclist.size()>0) {
                        	FileOutputStream fos = null; ZipOutputStream zipOut = null;        FileInputStream fis = null;
                            try {
                             	String workingDir = env.getProperty("documents");
                                fos = new FileOutputStream(workingDir+ File.separator +""+documentTypeName+".zip");
                                zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
                                for(String filename:doclist){
                                	if(!filename.isEmpty()) {
                                	String path = workingDir+ File.separator +candidateid+ File.separator + "H-1B"+ File.separator +filename;
                                	File input = new File(path);
                                	if(input.exists()) {
                                		  fis = new FileInputStream(input);
                                          ZipEntry ze = new ZipEntry(input.getName());
                                          zipOut.putNextEntry(ze);
                                          byte[] tmp = new byte[4*1024];
                                          int size = 0;
                                          while((size = fis.read(tmp)) != -1){
                                              zipOut.write(tmp, 0, size);
                                          }
                                          zipOut.flush();
                                          fis.close();
                                	  }
                                	}
                                }
                                zipOut.close();
                                File ff = new File(workingDir+ File.separator +""+documentTypeName+".zip");
                               
                				if (ff.exists() == true) {
                					FileInputStream fiss = new FileInputStream(ff);
                					byte[] inputBytes = new byte[(int) ff.length()];
                					fiss.read(inputBytes);
                					 
                					response.setContentType("application/zip");
                					response.setHeader("Content-Disposition", "attachment; filename="+documentTypeName+".zip");
                					ServletOutputStream os = response.getOutputStream(); // file output stream
                					os.write(inputBytes);
                					os.close();
                					fiss.close();
                					System.out.println("Zip downloaded..");
                				}
                				
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally{
                                try{
                                    if(fos != null) fos.close();
                                } catch(Exception ex){
                                     
                                }

                               }
                        }
                    
                  }
                  } catch (Exception e) {
                  logger.error("Error while downloadcategories action in H1BController" + e);
           }
    }
	
	@RequestMapping(value = { "/updateprintnostatus" }, method = RequestMethod.GET)
	@ResponseBody
	public String updateprintnostatus(Model model, HttpServletRequest request) {
		String chckprint = null;
		try {
			Integer lgempno = (Integer) request.getSession().getAttribute("empno");
			if (lgempno != null) {
				String initiateid = request.getParameter("initiateid");
				String candidateid = request.getParameter("candidateid");
				String documenttypeid = request.getParameter("documenttypeid");
				String profileid = request.getParameter("profileid");
				String profiletype = request.getParameter("profiletype");
				String printedvalue = request.getParameter("printedvalue");
				
				chckprint = h1bService.updateprintnostatus(initiateid,candidateid,documenttypeid,profileid,profiletype,printedvalue);
			System.out.println("chckprint******"+chckprint);
			} else {
				chckprint = null;
			}
		} catch (Exception e) {
			logger.error("Error in updateprintnostatus " + e);
		}
		return chckprint;
	}
	@RequestMapping(value = { "/updateprintyesstatus" }, method = RequestMethod.GET)
	@ResponseBody
	public String updateprintyesstatus(Model model, HttpServletRequest request) {
		String chckprint = null;
		try {
			Integer lgempno = (Integer) request.getSession().getAttribute("empno");
			if (lgempno != null) {
				String initiateid = request.getParameter("initiateid");
				String candidateid = request.getParameter("candidateid");
				String documenttypeid = request.getParameter("documenttypeid");
				String profileid = request.getParameter("profileid");
				String profiletype = request.getParameter("profiletype");
				String printedvalue = request.getParameter("printedvalue");
				
				chckprint = h1bService.updateprintyesstatus(initiateid,candidateid,documenttypeid,profileid,profiletype,printedvalue);
			System.out.println("chckprint******"+chckprint);
			} else {
				chckprint = null;
			}
		} catch (Exception e) {
			logger.error("Error in updateprintyesstatus " + e);
		}
		return chckprint;
	}
	
	@RequestMapping(value = { "/addh1bscanDetailsForm" }, method = RequestMethod.POST)
	@ResponseBody
	public String addh1bscanDetailsForm(Model model, HttpServletRequest request,HttpServletResponse response,@RequestParam(name = "filename[]", 
	required = false) MultipartFile[] filename) {
		Integer lgempno = (Integer) request.getSession().getAttribute("empno");
		Integer lgcandidateId = (Integer) request.getSession().getAttribute("candidateid");
		//System.out.println(lgempno+"--"+candidateId);
		String msg = null;
		try {
			String btncount = request.getParameter("buttoncount");
			String initiateid = request.getParameter("sinitiated");
			String candidateId = request.getParameter("scandidateid");
			
			 Integer count=0;
			  if(!btncount.isEmpty()) {
				count=Integer.parseInt(btncount);
			  }
			  String[] input = request.getParameterValues("inputval[]");
			  msg = h1bService.scandetailsform(candidateId,lgcandidateId,input,filename,initiateid);
			  
			/* if( count>0) {
			    	String[] input = request.getParameterValues("inputval[]");
					msg = h1bService.scandetailsform(initiateid,candidateId,lgempno,input,filename);
				}
		
			    PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('"+msg+"');");
                out.println("window.location='immigration'");
                out.println("</script>");
                out.close();*/
			    
		} catch (Exception e) {
			logger.error("Error in addh1bscanDetailsForm " + e);
		}
		return msg;
	}
	
	@RequestMapping(value = { "/updateh1bapproveddocumentdetails" }, method = RequestMethod.POST)
	@ResponseBody
	public String updateh1bapproveddocumentdetails(Model model, HttpServletRequest request,
			HttpServletResponse response,@RequestParam("apph1document") MultipartFile h1bdocument) {
		String userName = (String) request.getSession().getAttribute("username");
		Integer lgempno = (Integer) request.getSession().getAttribute("empno");
		Integer lgcandidateid = (Integer) request.getSession().getAttribute("candidateid");
		String message="";
		try {
			if (userName == null) {
				return "redirect:/loginpage"; 
			} else {
				    String documentid = request.getParameter("apph1documentid");
				    String h1bid = request.getParameter("apph1id");
					String h1bRecieptNumber = request.getParameter("eh1bNumber");
					String h1bvFromDate = request.getParameter("eh1bvFromDate");
					String h1bvToDate = request.getParameter("eh1bvToDate");
					String i94Number = request.getParameter("ei94Number");
					String i94vFromDate = request.getParameter("ei94vFromDate");
					String i94vToDate = request.getParameter("ei94vToDate");
					message = h1bService.updateh1bapproveddocumentdetails(documentid,h1bid,h1bRecieptNumber,h1bvFromDate,h1bvToDate,i94Number,i94vFromDate,i94vToDate,h1bdocument,lgcandidateid);
			  }
		} catch (Exception e) {
			logger.error("Error in updateh1bapproveddocumentdetails " + e.getMessage());
		}
		return message ;
	}	
	
	@RequestMapping(value = { "/updateh1bdoc" }, method = RequestMethod.POST)
	@ResponseBody
	public String  updateh1bdoc(Model model, HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name = "eh1bfilename", required = false) MultipartFile eh1bfilename) {
		String msg=null;
		try {
			String docid = request.getParameter("eh1documentid");
			msg= h1bService.updatefilebydocid(docid,eh1bfilename);
		} catch (Exception e) {
			logger.error("Error in updateh1bdoc " + e.getMessage());
		}
		return msg;
	}
	
	@RequestMapping(value = { "/updateh1rfedoc" }, method = RequestMethod.POST)
	@ResponseBody
	public String  updateh1rfedoc(Model model, HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name = "h1filenameid", required = false) MultipartFile filenameid) {
	           String msg=null;
		try {
			String documenttype = request.getParameter("h1edinputval");
			String document_id = request.getParameter("rfeh1docid");
			msg = h1bService.updateformbydocid(documenttype,document_id,filenameid);
		} catch (Exception e) {
			logger.error("Error in updateh1rfedoc " + e.getMessage());
		}
		return msg;
	}
	
	@RequestMapping(value = { "/checksubdocsbyid" }, method = RequestMethod.GET)
	@ResponseBody
	public String checksubdocsbyid(Model model, HttpServletRequest request) {
		String message = null;
		try {
			Integer lgempno = (Integer) request.getSession().getAttribute("empno");
			if (lgempno != null) {
				String initiateid = request.getParameter("initiateid");
				String candidateId = request.getParameter("candidateid");
				String profile_id = request.getParameter("profile_id");
				String benf_type = request.getParameter("benficiary_type");
				String documentid = request.getParameter("document_id");
				String subdocid = request.getParameter("subdocid");
				message = h1bService.checksubdocsbyids(initiateid,candidateId,profile_id,benf_type,documentid,subdocid);
			} else {
				message = null;
			}
		} catch (Exception e) {
			logger.error("Error in checksubdocsbyid " + e);
		}
		return message;
	}	
	
	@RequestMapping(value = { "/checkSelectedFileNameById" }, method = RequestMethod.GET)
	@ResponseBody
	public String checkSelectedFileNameById(Model model, HttpServletRequest request, HttpServletResponse response) {
		String userName = (String) request.getSession().getAttribute("username");
		String msg = null;
		if (userName == null) {
			return "redirect:/loginpage";
		} else {
			String filename = request.getParameter("filename");
			String candidateid = request.getParameter("candidateid");
			String fname = h1bService.checkSelectedFileNameById(filename,candidateid);
			if (fname != null) {
				msg = "Uploaded";
			} else {
				msg = "NotExists";
			}
			return msg;
		}
	}
	
	
	@RequestMapping(value = { "/getScanlist" }, method = RequestMethod.GET)
	@ResponseBody
  	public List<DocumentHistoryDto>  getScanlist(Model model, HttpServletRequest request) {
		List<DocumentHistoryDto> sclist=null;
	
		try {
			String initiateid = request.getParameter("initiateid");
			String candidateId = request.getParameter("candidate_id");
        sclist = h1bService.getsclist(initiateid,candidateId);
        System.out.println("controller--"+sclist);
		} catch (Exception e) {
			logger.error("Error in getScanlist " + e.getMessage());
		}
		return sclist;
	}
	
	@RequestMapping(value = { "/downloadscanedDocs" }, method = RequestMethod.GET)
    public void downloadscanedDocs(HttpServletResponse response, HttpServletRequest request) throws Exception {
           try {
                  String userName = (String) request.getSession().getAttribute("username");
                  if (userName == null) {
                        PrintWriter out = response.getWriter();
                        out.println("<script type=\"text/javascript\">");
                        out.println("window.location.href='login.jsp'");
                        out.println("</script>");
                        out.close();
                  } else {
                	  String candidateid = request.getParameter("candidateid");
         			 String initiateid = request.getParameter("initiateid");
                       
         			 List<String> doclist=h1bService.getScannedDocuments(candidateid,initiateid);
         			 String profilename=h1bService.getProfileNameByinitiateid(initiateid);
                        if(doclist.size()>0) {
                        	FileOutputStream fos = null; ZipOutputStream zipOut = null;        FileInputStream fis = null;
                            try {
                            	String workingDir = env.getProperty("documents");
                                fos = new FileOutputStream(workingDir+ File.separator +""+profilename+"_ScannedDocuments.zip");
                                zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
                                for(String filename:doclist){
                                	if(!filename.isEmpty()) {
                                	String path = workingDir+ File.separator +candidateid+ File.separator + "H-1B"+ File.separator +filename;
                                	File input = new File(path);
                                	if(input.exists()) {
                                		  fis = new FileInputStream(input);
                                          ZipEntry ze = new ZipEntry(input.getName());
                                          zipOut.putNextEntry(ze);
                                          byte[] tmp = new byte[4*1024];
                                          int size = 0;
                                          while((size = fis.read(tmp)) != -1){
                                              zipOut.write(tmp, 0, size);
                                          }
                                          zipOut.flush();
                                          fis.close();
                                	  }
                                	}
                                }
                                zipOut.close();
                                File ff = new File(workingDir+ File.separator +""+profilename+"_ScannedDocuments.zip");
                               
                				if (ff.exists() == true) {
                					FileInputStream fiss = new FileInputStream(ff);
                					byte[] inputBytes = new byte[(int) ff.length()];
                					fiss.read(inputBytes);
                					response.setContentType("application/zip");
                					response.setHeader("Content-Disposition", "attachment; filename="+profilename+"_ScannedDocuments.zip");
                					ServletOutputStream os = response.getOutputStream(); // file output stream
                					os.write(inputBytes);
                					os.close();
                					fiss.close();
                					System.out.println("Zip downloaded..");
                				}
                				
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally{
                                try{
                                    if(fos != null) fos.close();
                                } catch(Exception ex){
                                     
                                }

                               }
                        }
                    
                  }
                  } catch (Exception e) {
                  logger.error("Error while downloadscanedDocs action in H1BController" + e);
           }
    }
	
	

	
	
}
