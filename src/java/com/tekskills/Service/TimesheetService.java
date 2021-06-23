package com.tekskills.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tekskills.Dto.ClientVendorMaster_DTO;
import com.tekskills.Dto.TimesheetDto;
import com.tekskills.Entity.EmpPayRateDetails;

public interface TimesheetService {

	String saveBiWeeklyTimesheetTemplate(String month, String year, Integer loginempno, String periodname,
			String fromdate, String todate, String payroll_fromdate, String payroll_todate2);

	String saveWeeklyTimesheetTemplate(String month, String year, Integer loginempno, String weeks, String fromdate,
			String todate, String payroll_todate, String payroll_todate2);

	String checkBiweeklyTemplateByMonthYear(String year, String month);

	String checkweeklyTemplateByMonthYear(String year, String month);

	String saveweeklytemplate(String year, Integer loginempno);

	String checktstemplate(String year);

	TimesheetDto getPeningTimesheetByCandidateId(Integer candidateId, String payrollType, String emptype, Integer payrate_id);

	List<TimesheetDto> getDetailsByweekId(String weekid, String payrollType);

	String savetimesheet(String[] dates1, String[] dayname1, String[] billablehrs1, String[] nonbillablehrs1, String[] leave1,
			String[] comments1, String canidateId, MultipartFile mgrapprovedts, List<MultipartFile> lLmultipartFiles,
			Integer loginempno, String year, String month, String weeks, String fromdate, String todate, String weekId, String tablength, Integer validateby, String[] over_time, String payrate_id);

	List<TimesheetDto> getViewTimesheet(String year, String status, Integer role, Integer candidateId,
			String lgemptype, String month, String customertype);

	TimesheetDto getTimesheetDetailsById(String candidateid, String timesheet_id);

	String deleteLeaveLetter(String leaves_id);

	String updatetimesheet(String[] billablehrs1, String[] nonbillablehrs1, String[] leave1, String[] comments1,
			String canidateId, MultipartFile mgrapprovedts, List<MultipartFile> lLmultipartFiles, String tablength, String[] over_time, String fdate, String tdate);

	String updatetimesheetstatus(String candidateid, String status, String timesheetId, String comments, Integer role, String lgemptype, Integer dept, Integer lgempno, String fdate, String tdate);

	List<TimesheetDto> getWeeklyTimesheetByYear(Integer yr);

	List<TimesheetDto> getMyTimesheet(String year, String status, Integer role, Integer candidateId, String lgemptype, String month, String customertype);

	List<TimesheetDto> getMonthlyTemplate(Integer yr);

	List<TimesheetDto> getBiweeklyTemplate_1(Integer yr);

	List<TimesheetDto> getBiweeklyTemplate_2(Integer yr);

	List<ClientVendorMaster_DTO> getEmpPayrateListByCandidateId(Integer candidateId, String emptype);

	String getCustomerNameByPayrateId(Integer payrate_id);

	List<TimesheetDto> getPendingViewTimesheet(String year, String status, Integer role, Integer candidateId,
			String lgemptype, String month, String customertype);

	List<TimesheetDto> getAllCandidatePeningTimesheet();

	String tsmailreminder(String candidateid, Integer lgempno, String fdate, String tdate, Integer lgcandidateid,
			String lgemailid, String lgfullname);

	List<TimesheetDto> getmailhistory(String candidateid, String fdate, String tdate);




}
