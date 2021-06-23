package com.tekskills.DaoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tekskills.Dao.TimesheetDao;
import com.tekskills.Dto.ClientVendorMaster_DTO;
import com.tekskills.Dto.ManagerDto;
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

@Repository
public class TimesheetDaoImpl implements TimesheetDao {
	private static final Logger logger = Logger.getLogger(TimesheetDaoImpl.class);
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessfact;
	@Override
	public Integer saveBiWeeklyTimesheet(SemiMonthlyTemplateEntity biweeklyObj) {
		Session session = null;
		Integer biweeklyId = null;
		try {
			session = sessfact.openSession();
			biweeklyId = (Integer) session.save(biweeklyObj);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return biweeklyId;
	}
	@Override
	public Integer saveWeeklyTimesheet(WeeklyTemplateEntity weeklyObj) {
		Session session = null;
		Integer weeklyId = null;
		try {
			session = sessfact.openSession();
			weeklyId = (Integer) session.save(weeklyObj);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return weeklyId;
	}
	@Override
	public TimesheetDto checkBiweeklyTemplateByMonthYear(int yearid, String month) {
		TimesheetDto basicObj = null;
		Session session = null;
		try {
			session = sessfact.openSession();
			String sql = "select distinct year,month from Tbl_SemiMonthlyTemplate where year=" + yearid + " and month='" + month + "'";
			basicObj = (TimesheetDto) session.createSQLQuery(sql).setResultTransformer(new AliasToBeanResultTransformer(TimesheetDto.class)).uniqueResult();

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return basicObj;
	}
	@Override
	public TimesheetDto checkweeklyTemplateByMonthYear(int yearid, String month) {
		TimesheetDto basicObj = null;
		Session session = null;
		try {
			session = sessfact.openSession();
			String sql = "select distinct year,month from Tbl_WeeklyTemplate where year=" + yearid + " and month='" + month + "'";
			basicObj = (TimesheetDto) session.createSQLQuery(sql).setResultTransformer(new AliasToBeanResultTransformer(TimesheetDto.class)).uniqueResult();

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return basicObj;
	}
	@Override
	public Integer checktstemplate(int yearid) {
		Integer yr = null;
		Session session = null;
		try {
			session = sessfact.openSession();
			String sql = "select distinct year from Tbl_WeeklyTemplate where year=" + yearid + "";
			yr = (Integer) session.createSQLQuery(sql).uniqueResult();

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return yr;
	}
	@Override
	public List<TimesheetDto> getPeningTimesheetByCandidateId(Integer candidateId,String payrollType, String emptype, int payrateid) {
		List<TimesheetDto> timesheetList = null;
		Session session = null;
		try {
			String sql =null;
			session = sessfact.openSession();
			if(emptype.equalsIgnoreCase("W2 Admin") && payrollType!=null) {
				if(payrollType.equalsIgnoreCase("Weekly")) {
					sql = " Exec getWeeklyPendingTimesheetByCanId @candidateid="+candidateId+",@payrateid=0,@emptype='"+emptype+"'";
				}
				else if(payrollType.equalsIgnoreCase("Semi-Monthly")) {
					sql = " Exec getSemimonthPendingTSByCanId @candidateid="+candidateId+",@payrateid=0,@emptype='"+emptype+"'";
				}
				else if(payrollType.equalsIgnoreCase("Monthly")) {
					sql = " Exec getMonthlyPendingTSByCanId @candidateid="+candidateId+",@payrateid=0,@emptype='"+emptype+"'";
				}
				else if(payrollType.equalsIgnoreCase("Bi-Weekly1")) {
					sql = " Exec getBiweekly_1PendingTSByCanId @candidateid="+candidateId+",@payrateid=0,@emptype='"+emptype+"'";
				}
				else if(payrollType.equalsIgnoreCase("Bi-Weekly2")) {
					sql = " Exec getBiweekly_2PendingTSByCanId @candidateid="+candidateId+",@payrateid=0,@emptype='"+emptype+"'";
				}
				
			}else {
				System.out.println("-------payrateid---------"+payrateid+"......"+payrollType+"========="+emptype);
				if(payrollType!=null) {
				if(payrollType.equalsIgnoreCase("Weekly")) {
					sql = " Exec getWeeklyPendingTimesheetByCanId @candidateid="+candidateId+",@payrateid="+payrateid+",@emptype='"+emptype+"'";
				}
				else if(payrollType.equalsIgnoreCase("Semi-Monthly")) {
					sql = " Exec getSemimonthPendingTSByCanId @candidateid="+candidateId+",@payrateid="+payrateid+",@emptype='"+emptype+"'";
				}
				else if(payrollType.equalsIgnoreCase("Monthly")) {
					sql = " Exec getMonthlyPendingTSByCanId @candidateid="+candidateId+",@payrateid="+payrateid+",@emptype='"+emptype+"'";
				}
				else if(payrollType.equalsIgnoreCase("Bi-Weekly1")) {
					sql = " Exec getBiweekly_1PendingTSByCanId @candidateid="+candidateId+",@payrateid="+payrateid+",@emptype='"+emptype+"'";
				}
				else if(payrollType.equalsIgnoreCase("Bi-Weekly2")) {
					sql = " Exec getBiweekly_2PendingTSByCanId @candidateid="+candidateId+",@payrateid="+payrateid+",@emptype='"+emptype+"'";
				}
			  }
			}
			timesheetList = (List<TimesheetDto>) session.createSQLQuery(sql).setResultTransformer(new AliasToBeanResultTransformer(TimesheetDto.class)).list();

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return timesheetList;
	}
	@Override
	public TimesheetDto getDetailsByweekId(int week_id, String payrollType) {
		TimesheetDto weekObj = null;
		Session session = null;
		try {
			String sql =null;
			session = sessfact.openSession();
		/*select week_id,year,month,weeks,week_fromdate,week_todate from Tbl_WeeklyTemplate  where week_id=
		select template_id,year,month,from_date,to_date from Tbl_StatusReportTemplate  where template_id=1
		select month_id,year,month,month_no,payroll_fromdate,payroll_todate from Tbl_MonthlyTemplate  where month_id=1
		select biweek_id,year,month,weeks,week_fromdate,week_todate from Tbl_BiWeeklyTemplate1  where biweek_id=1
		select biweek_id,year,month,weeks,week_fromdate,week_todate from Tbl_BiWeeklyTemplate2  where biweek_id=1*/
			if(payrollType.equalsIgnoreCase("Weekly")) {
				
			 sql = "select week_id,year,month,weeks,week_fromdate as week_fromdate,week_todate as week_todate from Tbl_WeeklyTemplate  where week_id=" + week_id + "";
			}
			else if(payrollType.equalsIgnoreCase("Semi-Monthly")) {
				
				sql = "select template_id,year,month,period_name,convert(varchar(15),from_date,101) as week_fromdate,convert(varchar(15),to_date,101) as week_todate from Tbl_StatusReportTemplate  where template_id=" + week_id + "";
			}
			else if(payrollType.equalsIgnoreCase("Monthly")) {
				
				sql = "select month_id,year,month,month_no,payroll_fromdate as week_fromdate,payroll_todate as week_todate from Tbl_MonthlyTemplate  where month_id=" + week_id + "";
			}
			else if(payrollType.equalsIgnoreCase("Bi-Weekly1")) {
				
				sql = "select biweek_id,year,month,weeks,week_fromdate as week_fromdate ,week_todate as week_todate from Tbl_BiWeeklyTemplate1  where biweek_id=" + week_id + "";
			}
			else if(payrollType.equalsIgnoreCase("Bi-Weekly2")) {
				
				sql = "select biweek_id,year,month,weeks,week_fromdate as week_fromdate,week_todate as week_todate from Tbl_BiWeeklyTemplate2  where biweek_id=" + week_id + "";
			}
			
			weekObj=(TimesheetDto) session.createSQLQuery(sql).setResultTransformer(new AliasToBeanResultTransformer(TimesheetDto.class)).uniqueResult();

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return weekObj;
	}
	@Override
	public Integer savetimesheet(TimesheetEntity timesheetObj) {
		Session session = null;
		Integer timesheetId = null;
		try {
			session = sessfact.openSession();
			timesheetId = (Integer) session.save(timesheetObj);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return timesheetId;
		
	}
	@Override
	public void saveDetailedTimeheet(TimesheetDetailsEntity tsDetailsObj) {
		Session session = null;
		try {
			session = sessfact.openSession();
			session.save(tsDetailsObj);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		
	}
	@Override
	public void saveLeaveAttachements(LeaveAttachmentsEntity leaveObj) {
		Session session = null;
		try {
			session = sessfact.openSession();
			session.save(leaveObj);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	@Override
	public List<TimesheetDto> getViewTimesheet(int yearid, String status, Integer role, Integer candidateId,
			String lgemptype,String month,int customerid) {
		List<TimesheetDto> periodList = null;
		Session session = null;
		try {String sql =null;
			session = sessfact.openSession();
			//if(role == 2 || role == 4 || role == 5) {
				Integer canid=0;
				sql = "Exec getTimesheetDetails @year="+yearid+",@status='"+status+"',@candidate_id="+canid+",@empType='W2 Admin',@role="+role+",@month='"+month+"',@customerid="+customerid+" ";
			/*}else {
				sql = "Exec getTimesheetDetails @year="+yearid+",@status='"+status+"',@candidate_id="+candidateId+"";
			}*/
				 
				 periodList = (List<TimesheetDto>) session.createSQLQuery(sql).setResultTransformer(new AliasToBeanResultTransformer(TimesheetDto.class)).list();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return periodList;
	}
	
	@Override
	public List<TimesheetDto> getPendingViewTimesheet(int yearid, String status, Integer role, Integer candidateId,
			String lgemptype,String month,int customerid) {
		List<TimesheetDto> periodList = null;
		Session session = null;
		try {String sql =null;
			session = sessfact.openSession();
			//if(role == 2 || role == 4 || role == 5) {
				Integer canid=0;
				sql = "Exec getPendingTimesheetDetails @year="+yearid+",@status='"+status+"',@candidate_id="+canid+",@empType='W2 Admin',@role="+role+",@month='"+month+"',@customerid="+customerid+" ";
			/*}else {
				sql = "Exec getTimesheetDetails @year="+yearid+",@status='"+status+"',@candidate_id="+candidateId+"";
			}*/
				 
				 periodList = (List<TimesheetDto>) session.createSQLQuery(sql).setResultTransformer(new AliasToBeanResultTransformer(TimesheetDto.class)).list();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return periodList;
	}
	
	@Override
	public TimesheetEntity getTimesheetDetailsById(int candidateId, int timesheetId) {
		TimesheetEntity tsObj = null;
		Session session = null;
		try {
			session = sessfact.openSession();
			String sql = "from TimesheetEntity  where timesheet_id=" + timesheetId + " and candidate_id="+candidateId+"";
			tsObj=(TimesheetEntity) session.createQuery(sql).uniqueResult();

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return tsObj;
	}
	@Override
	public List<TimesheetDetailsEntity> getDetailedTimesheetListById(int candidateId, int timesheetId) {
		List<TimesheetDetailsEntity> tsDtailedList = null;
		Session session = null;
		try {
			session = sessfact.openSession();
			String sql = "from TimesheetDetailsEntity  where timesheet_id=" + timesheetId + " and candidate_id="+candidateId+"";
			tsDtailedList=(List<TimesheetDetailsEntity>) session.createQuery(sql).list();

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return tsDtailedList;
	}
	@Override
	public List<LeaveAttachmentsEntity> getLeaveAttachmentsById(int candidateId, int timesheetId) {
		List<LeaveAttachmentsEntity> leaveList = null;
		Session session = null;
		try {
			session = sessfact.openSession();
			String sql = "from LeaveAttachmentsEntity  where timesheet_id=" + timesheetId + " and candidate_id="+candidateId+"";
			leaveList=(List<LeaveAttachmentsEntity>) session.createQuery(sql).list();

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return leaveList;
	}
	@Override
	public String deleteDocumentByDocId(Integer id) {
		String msg = null;
		Session session = null;
		try {
			session = sessfact.openSession();
			Transaction tx = session.beginTransaction();
			LeaveAttachmentsEntity docidObj = (LeaveAttachmentsEntity) session.get(LeaveAttachmentsEntity.class, id);
			if (docidObj != null) {
				docidObj.setLeaves_id(id);
				session.delete(docidObj);
				tx.commit();
				msg = "Success";
			} else {
				msg = "Fail";
			}
		
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return msg;
	}
	@Override
	public void updateTimesheet(TimesheetEntity timesheetObj) {
		Session session = null;
		try {
			session = sessfact.openSession();
			Transaction tx = session.beginTransaction();
			 session.update(timesheetObj);
			 tx.commit();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return ;
	}
	@Override
	public void updateDetailedTimesheet(TimesheetDetailsEntity tsDetailsObj) {
		Session session = null;
		try {
			session = sessfact.openSession();
			Transaction tx = session.beginTransaction();
			 session.update(tsDetailsObj);
			 tx.commit();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return ;
		
	}
	@Override
	public List<TimesheetDto> getWeeklyTimesheetByYear(Integer yr) {
		List<TimesheetDto> timesheetList = null;
		Session session = null;
		try {
			session = sessfact.openSession();
			String sql = "SELECT year as year,month as month,week_fromdate as fromdate,week_todate as todate from Tbl_WeeklyTemplate where year="+yr+"";
			timesheetList = (List<TimesheetDto>) session.createSQLQuery(sql).setResultTransformer(new AliasToBeanResultTransformer(TimesheetDto.class)).list();

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return timesheetList;
	}
	@Override
	public List<TimesheetDto> getMyTimesheet(int yearid, String status, Integer role, Integer candidateId,
			String lgemptype,String month,int customerid) {
		List<TimesheetDto> periodList = null;
		Session session = null;
		try {
			session = sessfact.openSession();
			String sql = "Exec getMyTimesheetDetails @year="+yearid+",@status='"+status+"',@candidate_id="+candidateId+",@month='"+month+"',@customerid="+customerid+"";
				 periodList = (List<TimesheetDto>) session.createSQLQuery(sql).setResultTransformer(new AliasToBeanResultTransformer(TimesheetDto.class)).list();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return periodList;
	}
	@Override
	public Integer saveMonthlyTemplate(MonthlyTemplateEntity monthObj) {
		Session session = null;
		Integer weeklyId = null;
		try {
			session = sessfact.openSession();
			weeklyId = (Integer) session.save(monthObj);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return weeklyId;
	}
	@Override
	public Integer saveBiWeeklyTimesheet1(BiWeeklyTemplateEntity1 biweeklyObj) {
		Session session = null;
		Integer weeklyId = null;
		try {
			session = sessfact.openSession();
			weeklyId = (Integer) session.save(biweeklyObj);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return weeklyId;
	}
	@Override
	public Integer saveBiWeeklyTimesheet2(BiWeeklyTemplateEntity2 biweeklyObj) {
		Session session = null;
		Integer weeklyId = null;
		try {
			session = sessfact.openSession();
			weeklyId = (Integer) session.save(biweeklyObj);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return weeklyId;
	}
	@Override
	public Integer saveStatusTemplate(StatusReportTemplateEntity reportObj) {
		Session session = null;
		Integer statusid = null;
		try {
			session = sessfact.openSession();
			statusid = (Integer) session.save(reportObj);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return statusid;
	}
	@Override
	public List<TimesheetDto> getMonthlyTemplate(Integer yr) {
		List<TimesheetDto> monthList = null;
		Session session = null;
		try {
			session = sessfact.openSession();
			String sql = "select year as year,month as month,payroll_fromdate as fromdate,payroll_todate as todate  from Tbl_MonthlyTemplate where year="+yr+"";
			monthList = session.createSQLQuery(sql).setResultTransformer(new AliasToBeanResultTransformer(TimesheetDto.class)).list();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return monthList;
	}
	@Override
	public List<TimesheetDto> getBiweeklyTemplate_1(Integer yr) {
		List<TimesheetDto> monthList = null;
		Session session = null;
		try {
			session = sessfact.openSession();
			String sql = "select year as year,month as month,week_fromdate as fromdate,week_todate as todate from Tbl_BiWeeklyTemplate1 where year="+yr+"";
			monthList = session.createSQLQuery(sql).setResultTransformer(new AliasToBeanResultTransformer(TimesheetDto.class)).list();
		
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return monthList;
	}
	@Override
	public List<TimesheetDto> getBiweeklyTemplate_2(Integer yr) {
		List<TimesheetDto> monthList = null;
		Session session = null;
		try {
			session = sessfact.openSession();
			String sql = "select year as year,month as month,week_fromdate as fromdate,week_todate as todate from Tbl_BiWeeklyTemplate2 where year="+yr+"";
			monthList = session.createSQLQuery(sql).setResultTransformer(new AliasToBeanResultTransformer(TimesheetDto.class)).list();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return monthList;
	}
	@Override
	public EmpPayRateDetails getEmpPayrateDetailsByCandidateId(Integer candidateId,String emptype,Integer payrateId) {
		Session session = null;EmpPayRateDetails payrateObj = null;
		try {
			session = sessfact.openSession();
			String sql =null;
			if(payrateId!=null) {
				 sql = "from EmpPayRateDetails  where payrate_id=" + payrateId + " and Status='Active'";	
			}else {
				 sql = "from EmpPayRateDetails  where candidate_id=" + candidateId + " and Status='Active'";
			}
			payrateObj = (EmpPayRateDetails) session.createQuery(sql).uniqueResult();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {session.close();
		}return payrateObj;
	}
	@Override
	public List<ClientVendorMaster_DTO> getEmpPayrateListByCandidateId(Integer candidateId, String emptype) {
		Session session = null;List<ClientVendorMaster_DTO> payrateList = null;
		try {
			session = sessfact.openSession();
			String sql="select p.payrate_id as payrate_id,c.customer_id as custid ,c.customer_name as custname, p.candidate_id as candidate_id,p.payroll_type as payroll_type from Tbl_CustomerMaster c join "
					+ "Tbl_VendorPayRateDetails p on c.customer_id=p.customer_id where  p.status='Active' "
					+ "and p.candidate_id="+candidateId+"";
			//String sql = "from EmpPayRateDetails  where candidate_id=" + candidateId + " and Status='Active'";
			//payrateList = (List<EmpPayRateDetails>) session.createSQLQuery(sql).list();
			payrateList =session.createSQLQuery(sql).setResultTransformer(new AliasToBeanResultTransformer(ClientVendorMaster_DTO.class)).list();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {session.close();
		}return payrateList;
	}
	@Override
	public String getCustomerNameByPayrateId(Integer payrate_id) {
		Session session = null;String payrateObj = null;
		try {
			session = sessfact.openSession();
			
			String sql="select c.customer_name from Tbl_VendorPayRateDetails v join Tbl_CustomerMaster c on v.customer_id=c.customer_id where v.payrate_id="+payrate_id+" and v.status='Active'";
			payrateObj = (String) session.createSQLQuery(sql).uniqueResult();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {session.close();
		}return payrateObj;
	}
	
	@Override
	public Integer saveTSmailreminder(MailReminderCountEntity mailObj) {
		Session session = null;
		Integer reminderid = null;
		try {
			session = sessfact.openSession();
			reminderid = (Integer) session.save(mailObj);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return reminderid;
	}
	/*@Override
	public Integer gettimsheetIdByDetails(Integer candidate_id, String fdate, String tdate) {
		Integer timesheet_id = null;
		Session session = null;
		try {
			session = sessfact.openSession();
			String sql = "select timesheet_id from Tbl_Timesheet where candidate_id=" + candidate_id + " and from_date='"+fdate+"' and to_date='"+tdate+"'";
			timesheet_id = (Integer) session.createSQLQuery(sql).uniqueResult();

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return timesheet_id;
	}*/
	@Override
	public List<TimesheetDto> getmailhistory(Integer candidate_id, String fdate, String tdate) {
		List<TimesheetDto> reminderList = null;
		Session session = null;
		try {String sql =null;
			session = sessfact.openSession();
				sql = "select from_date as week_fromdate, to_date as week_todate,convert(varchar, submitted_on, 9) as dates from Tbl_TimeSheetMailReminderCount where candidate_id="+candidate_id+" and from_date='"+fdate+"' and to_date='"+tdate+"'";
				reminderList =session.createSQLQuery(sql).setResultTransformer(new AliasToBeanResultTransformer(TimesheetDto.class)).list(); 
						
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			session.close();
		}
		return reminderList;
	}
}
