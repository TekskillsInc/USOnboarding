package com.tekskills.Dao;

import java.util.List;

import com.tekskills.Dto.ClientVendorMaster_DTO;
import com.tekskills.Dto.TimesheetDto;
import com.tekskills.Entity.BiWeeklyTemplateEntity1;
import com.tekskills.Entity.BiWeeklyTemplateEntity2;
import com.tekskills.Entity.EmpPayRateDetails;
import com.tekskills.Entity.LeaveAttachmentsEntity;
import com.tekskills.Entity.MailReminderCountEntity;
import com.tekskills.Entity.MonthlyTemplateEntity;
import com.tekskills.Entity.SemiMonthlyTemplateEntity;
import com.tekskills.Entity.StatusReportTemplateEntity;
import com.tekskills.Entity.TimesheetDetailsEntity;
import com.tekskills.Entity.TimesheetEntity;
import com.tekskills.Entity.WeeklyTemplateEntity;

public interface TimesheetDao {

	Integer saveBiWeeklyTimesheet(SemiMonthlyTemplateEntity biweeklyObj);

	Integer saveWeeklyTimesheet(WeeklyTemplateEntity weeklyObj);

	TimesheetDto checkBiweeklyTemplateByMonthYear(int yearid, String month);

	TimesheetDto checkweeklyTemplateByMonthYear(int yearid, String month);

	Integer checktstemplate(int yearid);

	List<TimesheetDto> getPeningTimesheetByCandidateId(Integer candidateId, String payrollType, String emptype, int payrateid);

	TimesheetDto getDetailsByweekId(int week_id, String payrollType);

	Integer savetimesheet(TimesheetEntity timesheetObj);

	void saveDetailedTimeheet(TimesheetDetailsEntity tsDetailsObj);

	void saveLeaveAttachements(LeaveAttachmentsEntity leaveObj);

	List<TimesheetDto> getViewTimesheet(int yearid, String status, Integer role, Integer candidateId, String lgemptype, String month, int custType);

	TimesheetEntity getTimesheetDetailsById(int candidateId, int timesheetId);

	List<TimesheetDetailsEntity> getDetailedTimesheetListById(int candidateId, int timesheetId);

	List<LeaveAttachmentsEntity> getLeaveAttachmentsById(int candidateId, int timesheetId);

	String deleteDocumentByDocId(Integer id);

	void updateTimesheet(TimesheetEntity timesheetObj);

	void updateDetailedTimesheet(TimesheetDetailsEntity tsDetailsObj);

	List<TimesheetDto> getWeeklyTimesheetByYear(Integer yr);

	List<TimesheetDto> getMyTimesheet(int yearid, String status, Integer role, Integer candidateId, String lgemptype, String month, int custType);

	Integer saveMonthlyTemplate(MonthlyTemplateEntity monthObj);

	Integer saveBiWeeklyTimesheet1(BiWeeklyTemplateEntity1 weeklyObj);

	Integer saveBiWeeklyTimesheet2(BiWeeklyTemplateEntity2 weeklyObj);

	Integer saveStatusTemplate(StatusReportTemplateEntity weeklyObj);

	List<TimesheetDto> getMonthlyTemplate(Integer yr);

	List<TimesheetDto> getBiweeklyTemplate_1(Integer yr);

	List<TimesheetDto> getBiweeklyTemplate_2(Integer yr);

	EmpPayRateDetails getEmpPayrateDetailsByCandidateId(Integer candidateId, String emptype, Integer payrate_id);

	List<ClientVendorMaster_DTO> getEmpPayrateListByCandidateId(Integer candidateId, String emptype);

	String getCustomerNameByPayrateId(Integer payrate_id);

	List<TimesheetDto> getPendingViewTimesheet(int yearid, String status, Integer role, Integer candidateId,
			String lgemptype, String month, int custType);

	Integer saveTSmailreminder(MailReminderCountEntity mailObj);

	List<TimesheetDto> getmailhistory(Integer candidate_id, String fdate, String tdate);


	

}
