<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- <%@ taglib uri="http://java.sun.com/js/p/js/tl/core" prefix="c" %> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="java.net.URLDecoder"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="resources/css/css/select2.min.css"	rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/css/tempusdominus-bootstrap-4.min.css" />
<!-- <script src="/plugins/jquery/js/jQuery-2.1.4.min.js"></script> -->


</head>
<style type="text/css">
#pendingdocs {
	width: 105% !important;
}

.col-lg-2 {
	padding-right: 5px;
}

.form-control {
	font-size: 12px;
}
</style>
<style type="text/css">
.field-error .control-label,
.field-error .help-block,
.field-error .form-control-feedback {
     color: red;
}
.field-success .control-label,
.field-success .help-block,
.field-success .form-control-feedback {
   color:  #3c763d;
}
#comment:focus�{
 border-color:red;
�/* background:�yellow;
��color:�red; */
}

</style>
<body>
	<div id="right-panel" class="right-panel">
		<div class="content">
			<!-- Animated -->
			<div class="animated fadeIn">
				<div class="row">
					<div class="col">
						<div class="card">
							<h5 class="card-header">Search All Timesheet</h5>
							<div class="card-body">
								<div class="row">
									<div class="col-lg-12">
										<input type="hidden" name="emptype" id="emptype"
											value="${emptype}"> <input type="hidden" name="role"
											id="role" value="${role}"> 
											<input type="hidden"	name="dept" id="dept" value="${dept}">
												<input type="hidden" name="lgworkAuth" id="lgworkAuth" value="${lgworkAuth}">
											<input type="hidden" name="lgfullname" id="lgfullname" value="${lgfullname}">
											<input type="hidden" name="loginUserid" id="loginUserid" value="${loginUserid}">
											
											
										<fieldset>
											<form action="timesheet" method="post"
												class="form-horizontal">
												<div class="form-group row">
													<div class="col-lg-2">
														<select id="year" name="year" class="form-control"
															style="height: 39px; border: 2px solid #ccc;">
															<script type="text/javascript">
														var myDate = new Date();
														var year = myDate.getFullYear();
														for(y = year-1; y <= year + 1; y++) {
														  var optn = document.createElement("OPTION");
														  optn.text = y; optn.value = y;
       													 if (y == year) {   // if year is 2015 selected
         												   optn.selected = true;
      														  }
        												document.getElementById('year').options.add(optn);
																}
															</script>
															<!-- <script>
											   var myDate = new Date();
												var year = myDate.getFullYear();
												for (var i = year-1; i < year + 2; i++) {
												document.write('<option value="'+i+'">'+ i+ '</option>');
													}
												</script> -->
														</select>
													</div>
													 <div class="col-lg-2" id="mnt">
														<select id="month" autocomplete="off" name="month"
															class="form-control" onchange="getPeriod()"
															style="height: 39px; border: 2px solid #ccc;">
															<option value="">--Select Month--</option>
															<c:forEach var="ml" items="${monthsList}">
															<option value="${ml}" ${ml eq month ? 'selected="selected"' : ''}>${ml}</option>
																<%-- <option value="${ml}">${ml}</option> --%>
															</c:forEach>
														</select>
													</div>

													<%--<div class="col-lg-2" id="mnt">
														<select id="period" autocomplete="off" name="period"
															class="form-control"
															style="height: 39px; border: 2px solid #ccc;">
															<option value="">--Select Period--</option>

														</select>
													</div> --%>
													
											   <div class="col-lg-2">
											     <select name="customertype" autoComplete="off" class="form-control text-left" id="customertype" >
										             <option value="" selected="selected">--Select Customer--</option>
										             <c:forEach var="custl" items="${cutomerList}">
															<%-- <option value="${custl.companyId}">${custl.companyName}</option> --%>
															
															<option value="${custl.companyId}" ${custl.companyId eq customertype ? 'selected="selected"' : ''}>${custl.companyName}</option>
														</c:forEach>
										         </select>
										    </div> 
										   
													<c:if test="${emptype eq 'Admin'}">
													<div class="col-lg-2" id="mnt" style="display: none">
														<select id="status" autocomplete="off" name="status"
															class="form-control" 
															style="height: 39px; border: 2px solid #ccc;">
															<option value="">--Select Status--</option>
															<option value="Submitted">Submitted</option>
															<option value="Approved">Approved</option>
															<option value="Rejected">Rejected</option>
														</select>
													</div></c:if>
													<c:if test="${emptype ne 'Admin'}">
													<div class="col-lg-2" id="mnt">
														<select id="status" autocomplete="off" name="status"
															class="form-control" 
															style="height: 39px; border: 2px solid #ccc;">
															<option value="">--Select Status--</option>
															<option value="Submitted">Submitted</option>
															<option value="Approved">Approved</option>
															<option value="Rejected">Rejected</option>
														</select>
													</div></c:if>
													<div class="col-sm-2">
														<input type="submit"
															style="background: #205081; height: 39px; border: 2px solid #ccc;"
															class="btn btn-info btn-sm" value="Search"> <br>
													</div>
												</div>
											</form>
										</fieldset>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12">
								<div class="card">
									<h5 class="card-header" style="padding: .2rem;">View All Timesheet
										Details</h5>
									<div class="card-body">
										<div style="text-align: center;"></div>
										<div
											style="max-width: 1200px; overflow-x: auto; white-space: nowrap;">
											<table id="employeeTableId"
												class="table table-hover table-bordered"
												style="text-align: center;">
												<thead>
													<tr>
													<th style="text-align: center">Action</th>
													<th style="text-align: center">CandidateName</th>
														<th style="text-align: center; display: none;">Year</th>
														<th style="text-align: center; display: none;">Month</th>
														<th style="text-align: center">From Date</th>
														<th style="text-align: center">To Date</th>
														
														<th style="text-align: center">Customer Name</th>
														<th style="text-align: center; display: none">Week</th>
														<th style="text-align: center;display: none">Timesheet Status</th>
														<th style="text-align: center">Manager Status</th>
														<th style="text-align: center">HR Status</th>
														 <th style="text-align: center; display: none;">Manager Name</th>
														<th style="text-align: center; display: none;">HR Name</th>
														
														<th style="text-align: center; display: none;">ManagerComments</th>
														<th style="text-align: center;  display: none;">HRComments</th>
														

													</tr>
												</thead>
												<tbody>

													<c:forEach var="rlist" items="${timesheetList}">
														<tr>
														<input type="hidden" id="canid" value="${rlist.candidate_id}">
														<input type="hidden" id="tsid" value="${rlist.timesheet_id}">
														<td><button class="btn btn-success btn-sm"
																	onclick="getDetailsByCanId('${rlist.candidate_id}','${rlist.timesheet_id}')"
																	data-toggle="modal"
																	style="padding: 2px 1px; font-size: 12px;">View</button>
																	
															</td>
														<td>${rlist.fullname}</td>
															<td id="yeartd${rlist.timesheet_id}" style="display: none">${rlist.year}</td>
															<td id="monthtd${rlist.timesheet_id}" style="display: none">${rlist.month}</td>
															<td id="fdatetd${rlist.timesheet_id}">${rlist.fromdate}</td>
															<td id="tdatetd${rlist.timesheet_id}">${rlist.todate}</td>
															<c:if test="${empty rlist.customer_name}">
															<td>NA</td>
															</c:if>
															<c:if test="${not empty rlist.customer_name}">
															<td>${rlist.customer_name}</td>
															</c:if>
															
															
															<td id="periodtd${rlist.timesheet_id}" style="display: none">${rlist.week_or_period}</td>
															<td id="ts_status${rlist.timesheet_id}" style="display: none">${rlist.timesheet_status}</td>
															<td>${rlist.manager_status}</td>
															<td>${rlist.hr_status}</td>
															<td style="display: none">${rlist.manager_name}</td>
															<td style="display: none">${rlist.hr_name}</td>
															
															<td id="mgr_com${rlist.timesheet_id}" style="display: none;">${rlist.manager_comments}</td>
															<td id="hr_com${rlist.timesheet_id}" style="display: none;">${rlist.hr_comments}</td>
															
															
														</tr>
													</c:forEach>

												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="statusmodal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true">
					<div class="modal-dialog modal-lg" style="max-width:79%" role="document">
						<div class="modal-content">
							<div class="modal-header" style="padding:.5rem;">
							<button class="close" data-dismiss="modal"
									aria-label="Close"><span aria-hidden="true">&times;</span>
								</button>
									<h5 class="modal-title" id="exampleModalLabel"
										style="font-size: 15px">
										<b>Timesheet Details</b>
									</h5>
							</div>
							<div class="modal-body">
								<form action="/"  id="formid" name="formid" enctype="multipart/form-data">
									<div class="row">
										<div class="col-md-10 col-sm-12">
										<input type="hidden" id="candidate_id" name="canidateId">
										<input type="hidden" id="timesheetId" name="timesheetId">
										<input type="hidden" id="fdate" name="fdate">
										<input type="hidden" id="tdate" name="tdate">
										<!-- <input type="text" id="tablength" name="tablength"> -->
										
										<table id="i9fromTable"
											class="table table-hover table-bordered"
											style="text-align: center;">
											<thead>
												<tr>
													<th style="text-align: center">Date</th>
													<th style="text-align: center">Day</th>
													<th style="text-align: center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;BillableHours</th>
													<th style="text-align: center">NonBillableHours</th>
													<th style="text-align: center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;OverTime</th>
													<th style="text-align: center">Leaves</th>
													<th style="text-align: center">Comments</th>
												</tr>
											</thead>
											<tbody id="tsdetails">
												
											</tbody>
										</table>
										<div class="form-group row">
														<label class="col-sm-4 col-form-label">
															Manager Approved Timesheet:</label>
														<div class="col-sm-8">
													<!-- <br> -->
															<span id="mgrfilename" style="color: blue" onclick="downloadManagerfile(this)"></span>
														</div>
												</div>
										<div class="form-group row">
														<label class="col-sm-4 col-form-label">
															Leave Letter:</label>
														<div class="col-sm-8">
															<span id="leavefilename" style="color: blue"></span>	
														</div>
												</div>
												<div class="col-md-12 col-sm-12" id="comid" style="display: none">
													<center><table 
														class="table table-hover table-bordered"
														style="text-align: center;">
														<thead>
															<tr>
																<th style="text-align: center">Manager Comments</th>
																<th style="text-align: center">HR Comments</th>
															</tr>
														</thead>
														<tbody>
															<tr>
															<td><span id="amgr_com"></span></td>
															<td><span id="ahr_com"></span></td></tr>
														</tbody>
													</table></center>
													</div>
												
									<div class="form-group row">
										<label class="col-sm-4 col-form-label"> <span id="tscommid" style="color:red;">*</span>Comments</label>
										<div class="col-sm-3">
											<textarea id="tscomments" name="tscomments" class="form-control" rows="4 "style="width: 180px;"></textarea>
										</div>
									</div>
									<div class="form-group row">
										<label class="col-sm-4 col-form-label">Mail Reminder History</label>
										<div class="col-sm-3">
											<a href="#" onclick="viewHistory()"class="label label-success" style="color:white;" 
										title="Mail Reminder History">
											<img src="resources/ICONS/empHistory.png" style="max-width: 7%;"></a>
										</div>
									</div>
									<div class="form-group row">
										<label class="col-sm-4 col-form-label">Approve/Reject</label>
										<div class="col-sm-3">
											<select id="statusid" autocomplete="off" name="tsstatus"
												required class="form-control">
												<option value="">--Select Status--</option>
												<option value="Approved">Approve</option>
												<option value="Rejected">Reject</option>
											</select>
										</div>
									</div>
								</div>
									</div>
										
									<div class="form-group row" id="update_id">
										<div class="col-sm-9">
											<center>
											<button type="button" class="btn btn-primary btn-sm"
														id="updateid" onclick="updateStatus()">Submit</button>
											<%-- <c:if test="${(dept ne 0) && (role == 2 || role ==5)&& (emptype eq 'W2 Admin')}">
													<button type="button" class="btn btn-primary btn-sm"
														id="updateid" onclick="updateStatus()">Submit</button>
												 </c:if>
										 <c:if test="${(dept==1) && (role == 4)&& (emptype eq 'W2 Admin' || emptype eq 'Admin')}">
													<button type="button" class="btn btn-primary btn-sm"
														id="updateid" onclick="updateStatus()">Submit</button>
												 </c:if> --%>
												<button type="button" class="btn btn-secondary btn-sm"
													data-dismiss="modal">Close</button>
											</center>
										</div>
									</div>
									
									
								</form>
							</div>
						</div>
					</div>
				</div>
	
	<div class="modal fade" id="historymodal" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg" style="max-width: 50%"
				role="document">
				<div class="modal-content">
					<div class="modal-header" style="padding: .5rem;">
						<button class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h5 class="modal-title" id="exampleModalLabel"
							style="font-size: 15px">
							<b>Mail Reminder History</b>
						</h5>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-10 col-sm-10">
								<table id="historytable"
									class="table table-hover table-bordered"
									style="text-align: center;">
									<thead>
										<tr>
											<th style="text-align: center">From Date</th>
											<th style="text-align: center">To Date</th>
											<th style="text-align: center">Submitted Date</th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- <form action="gettimesheet" method="post" id="vformid" > -->
	<form action="timesheet" method="post" id="vformid" >
		    <input type="hidden" name="year" value="${year}">
		    <input type="hidden" name="status" value="${status}">
		    <input type="hidden" name="month" value="${month}">
		    <input type="hidden" name="customertype" value="${customertype}">
		    
			</form>

	
<script src="resources/js/js/jquery-2.1.4.min.js"></script> 
	<script src="resources/js/newjs/bootstrap.min.js"></script>
	<script src="resources/js/newjs/formValidation.min.js"></script>
	<script src="resources/js/newjs/frameworkbootstrap.min.js"></script>
	<script src="resources/js/js/bootstrap-datepicker.min.js"></script>
	<script src="resources/js/newdt/jquery.dataTables.min.js"></script>
    <script src="resources/js/newdt/dataTables.bootstrap4.min.js"></script>
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
		<script>
function updateStatus(){
var status=$('#statusid').val();
var candidate_id=$('#candidate_id').val();
var timesheetId=$('#timesheetId').val();
var tscomments=$('#tscomments').val();
var fdate=$('#fdate').val();
var tdate=$('#tdate').val();
$('#formid').formValidation('validate');
if( !$('#formid').data('formValidation').isValid())
	 {
	 }
else{ 
$.ajax({
	    url: "updatetsstatus", //this is the submit URL
	    type: "POST",
	    asyn : false,
	    data: {"candidateid":candidate_id,"status":status,"timesheetId":timesheetId,"tscomments":tscomments,"fdate":fdate,"tdate":tdate},
	    success: function(response){
	    	 if(response!=null || response!=""){
  	 alert(response)
  	document.getElementById("vformid").submit();
	    }else{
           	window.location.href="loginpage";
   	            }
	    }
	});
}}
	</script>
	<script>
	$('#formid').formValidation({
	    framework: 'bootstrap',
	    row: {
	        valid: 'field-success',
	        invalid: 'field-error'
	    },
	    fields: {	
	    	/* leaveletter: {
	            validators: {
	            	isEmpty: {
	                   // message: 'Upload leave letter'    
	                }
	            }
	        },
	        mgrapprovedts:{
	            validators: {
	            	isEmpty: {
	                    message: 'Upload manager apporved timesheet'
	                }
	            }
	        }, */
	        status:{
	            validators: {
	            	notEmpty: {
	                    message: 'Select status'
	                }
	            }
	        },
	        tscomments:{
	        	enabled: false,
	            validators: {
	            	notEmpty: {
	                    message: 'Enter comments'
	                }
	            }
	        },
	        'billablehrs[]' : {
	            validators: {
	            	notEmpty: {
	                    message: 'Billable hour is mandatory'
	                },
	                stringLength: {
	                    min: 1,
	                    max: 2,
	                    message: 'Billable hours length 5'
	                },
	                regexp: {
	                	  regexp: /^[0-9]$/,
	                	  message: 'Accepts only numbers'
	                },
	            }
	        },
	        'nonbillablehrs[]': {
	            validators: {
	            	notEmpty: {
	                    message: 'Non billable hour is mandatory'
	                },
	                stringLength: {
	                    min: 1,
	                    max: 2,
	                    message: 'Non billable hours length 5'
	                },
	                regexp: {
	                   regexp: /^[0-9]$/,
	                   message: 'Accepts only numbers'
	                },
	            }
	        },
	        'comments[]':{
	            validators: {
	            	notEmpty: {
	                    message: 'Please enter comments'
	                }
	            }
	        },
	        'leave[]':{
	            validators: {
	            	notEmpty: { 
	                    message: 'Please enter leave'
	                }
	               /*  regexp: {
	                	  regexp: /^[0-9]$/,
	                   message: 'Accept only numbers'
	                } */
	            }
	        }
	    }
	});
</script>
	<script>
	var lgworkAuth=$('#lgworkAuth').val();
	var lgemptype=$('#emptype').val();
	var loginUserid=$('#loginUserid').val();
	var role=$('#role').val();
	var dept=$('#dept').val();	
	

var table = $('#i9fromTable').DataTable({
	"paging":   false,
    "ordering": false,
    "info":     false,
    "searching": false
   /*  bLengthChange: true,
    paging: false */
 }); 


function UpdateTimesheet(){
 	$('#formid').formValidation('validate');
		if( !$('#formid').data('formValidation').isValid())
			 {
			 }
		else{ 
			var array=[];
			var	rowvalue=true;
			var	billablerowvalue=true;
			var arrayleave=[];
			var arraybillable=[];
			var	rowvalueleave=true;var arrayovertime=[];
			var	overtimeValue=true;
	      $('#i9fromTable tr').each(function(row, tr){
	          if(row!=0){
		    	    date = $(tr).find('td:eq(0)').text()
	          		dayname =$(tr).find('td:eq(1)').text()
	          		billhrs =$(tr).find('td:eq(2) input').val()
	       			nonbillhrs =$(tr).find('td:eq(3) input').val()
	       			overtime =$(tr).find('td:eq(4) input').val()
	     			leave = $(tr).find('td:eq(5) input').val()
	     			comments =$(tr).find('td:eq(6) textarea').val()
	     			
				var hrs = billhrs.substring(1, 2);
		    	    if((hrs>8) && comments==""){
		          		 $(tr).find('td:eq(6) textarea').focusout();
		          		billablerowvalue=false;
		          		arraybillable.push(billablerowvalue);
		     	           		} 
		    	    
	     		   if((hrs<8 && hrs!="") && comments==""){
	     		 $(tr).find('td:eq(6) textarea').focus();
	     		billablerowvalue=false;
	     		arraybillable.push(billablerowvalue);
		           		}  
	     		   
		   	           		
				if((nonbillhrs!="" && nonbillhrs!="00:00") && comments==""){
					     $(tr).find('td:eq(6) textarea').focus();
					     rowvalue=false;
					     array.push(rowvalue);
					}
				if((overtime!="" && overtime!="00:00") && comments==""){
					$(tr).find('td:eq(6) textarea').focus();
					overtimeValue=false;
					arrayovertime.push(overtimeValue);
				}
				if((leave!="" && leave!=0)){
					
					rowvalueleave=false;
					arrayleave.push(rowvalueleave);
					}
	          }
		           }); 
	         var tablength = document.getElementById("i9fromTable").rows.length;
	         var leavefilename = $('#leaveletter').val();
	       	var mgrapprovedfile = $('#mgrapprovedts').val();
	      if(array.includes(false) && arraybillable.includes(false)){
	    	  if(mgrapprovedfile==""){
	    		  $('#formid').bootstrapValidator('enableFieldValidators','mgrapprovedts',true);
	    	  }else{
	    		  
	    	  } 
	          
	          }else{
	          	 
	              if(arraybillable.includes(false) && array.includes(false)){
	            	 // alert("if");
	            	  if(mgrapprovedfile==""){
	            		  $('#formid').bootstrapValidator('enableFieldValidators','mgrapprovedts',true);
	            	  }else{
	            		  
	            	  } 
	                  }
	              else if(arrayovertime.includes(false)){
	            	  //alert("if 111111");
	            	  if(mgrapprovedfile==""){
	            		  $('#formid').bootstrapValidator('enableFieldValidators','mgrapprovedts',true);
	            	  }else{
	            		  
	            	  }
	                    }
	              else if(arrayleave.includes(false) && leavefilename==""){
	            	  //alert("if 3333");
	            		  $('#formid').bootstrapValidator('enableFieldValidators','leaveletter',true);
	                  }
	              else if(arraybillable.includes(false) && arrayleave.includes(false)){
	            	  //alert("if 44"); 
	            	  if(mgrapprovedfile==""){
	            		  $('#formid').bootstrapValidator('enableFieldValidators','mgrapprovedts',true);
	            	  }
	            	  if(leavefilename==""){
	            		  $('#formid').bootstrapValidator('enableFieldValidators','leaveletter',true);
	            	  }
	            	  else{
	            		  $('#tablength').val(tablength-1)
	       				fnTimesheetSubmit()  
	            	  }
	                    }
	              else{
	            	  //alert("else");
	                      
	                  	$('#formid').bootstrapValidator('enableFieldValidators','leaveletter',false); 
	         	 			 $('#tablength').val(tablength-1)
	         				fnTimesheetSubmit()
	         			}
	              }
	      
		}
	}
function fnUpdateTimesheet(){
	$("#candidate_id").val($("#candidate_id").val());
	$("#timesheetId").val($("#timesheetId").val())
	$("#submitid").attr("disabled",true);
	 var form = $('#formid')[0];
    var data = new FormData(form);
	$.ajax({
           url: "updatetimesheet", //this is the submit URL
           type: "POST",
           enctype: 'multipart/form-data',
           data: data,
           processData: false,
           contentType: false,
           cache: false,
           success: function(response){
        	   if(response!=null || response!=""){
  	            alert(response);
  	          document.getElementById("vformid").submit();
           }else{
           	window.location.href="loginpage";
   	            }
           }
       });
}
$('#tscommid').hide();
$( "#statusid" ).change(function() {
	var status=$('#statusid').val();
	if(status=="Rejected"){
		  $('#tscommid').show();
		  $('#formid').bootstrapValidator('enableFieldValidators','tscomments',true);
	}
	else{
		 $('#tscommid').hide();
		  $('#formid').bootstrapValidator('enableFieldValidators','tscomments',false);
		}
}); 
	
/* $( "#statusid" ).change(function() {
	var status=$('#statusid').val();
	var tsid=$('#tsid').val();
	 var db_status=$("#ts_status"+tsid).html();
	if(status==db_status){
		 $('#updateid').attr("disabled",true);
	}
	else{
		 $('#updateid').attr("disabled",false);
		}
	}); */
$('#comid').hide();
	
function getDetailsByCanId(candidateid,timesheet_id){
	$('#leavefilename').empty();
	$('#timesheetId').val(timesheet_id);
	 $('#candidate_id').val(candidateid);
	 var fdate=$("#fdatetd"+timesheet_id).html();
	 var tdate=$("#tdatetd"+timesheet_id).html();
	 
	 $('#fdate').val(fdate);
	 $('#tdate').val(tdate);
	
	 var ts_status=$("#ts_status"+timesheet_id).html();
	 $('#statusid').val('')
	 var mgr_com=$("#mgr_com"+timesheet_id).html();
	 var hr_com=$("#hr_com"+timesheet_id).html();
	 $("#amgr_com").text(''); $("#ahr_com").text('');
	 if(mgr_com!="" || hr_com!=""){
		 $('#comid').show();
		 $("#amgr_com").text(mgr_com); $("#ahr_com").text(hr_com);
		 }

	  $.ajax({
 	    url: "getTimesheetDetailsById", //this is the submit URL
 	    type: "POST",
 	    asyn : false,
 	    data: {"candidateid":candidateid,"timesheet_id":timesheet_id},
 		//contentType: "application/json",
 	    success: function(response){
 	    	if(response!=null || response!=""){
 	 	 	    var tsDetails=response.tsDetails;
 	 	 	 	var detailedTSList=response.detailedTSList;
 	 	 		var leavefilesList=response.leavefilesList;
 	 	 	    var mgr_appr_filename=tsDetails.client_mgr_approved_fie;
 	 	 	    var basicObj=response.basicObj;

 	 	 		if(mgr_appr_filename!=""){
 	 	 	    	$('#mgrfilename').text(mgr_appr_filename); 
 	 	 	 	    }else{
 	 	 	 	    $('#mgrfilename').text("NA");
 	 	 	 	    }
	 	 	 	    
 	 	 		//$('#tscomments').val(tsDetails.manager_comments);
 	 	 		//$("#statusid").find('option:selected').removeAttr("selected","selected");
 	 	 		//$("statusid option").prop("selected", false);
 	 	 		if ((lgemptype=="W2 Admin" || lgemptype=="Admin") && role == 4 && dept==1) {
 	 	 			
 	 	 	 		if(tsDetails.manager_status=="Approved"){
 	 	 	 		$('#updateid').attr("disabled",true);
 	 	 	 	 		}else{
 	 	 	 	 		$('#updateid').attr("disabled",false);
 	 	 	 	 	 		}
 	 	 	 	 
 	 	 		 var $dropdown1 = $("select[name='tsstatus']"); 
                 $dropdown1.find('option[value="' + tsDetails.manager_status + '"]').text(tsDetails.manager_status).attr('selected', true); 

 	 	 			
 	 	 			//�$dd.find('option[value="'�+�tsDetails.manager_status�+�'"]').text(tsDetails.manager_status).attr('selected', true);
 	 	 			$('#tscomments').text('');
 	 	 	/* 	}else if(role == 5 && lgemptype=="W2 Admin"){ */
 	 	 		}else if(role ==1 && dept==2){
 	 	 			if(basicObj.emp_type=="W2"){
 	 	 				$('#updateid').show();
 	 	 				if(tsDetails.hr_status=="Approved"){
 	 	 	 	 	 		$('#updateid').attr("disabled",true);
 	 	 	 	 	 	 	}else{
 	 	 	 	 	 	 		$('#updateid').attr("disabled",false);
 	 	 	 	 	 	 	}
 	 	 			}else{
 	 	 				$('#updateid').hide();
 	 	 			}
 	 	 			var�$dd�=�$("select[name='tsstatus']");
 	 	 			
 	 	 		 var $dropdown1 = $("select[name='tsstatus']"); 
                 $dropdown1.find('option[value="' + tsDetails.hr_status + '"]').text(tsDetails.hr_status).attr('selected', true); 
                 
 	 	 			$('#tscomments').text(tsDetails.hr_comments);
 	 	 	 	}

 	 	 	 if (table) table.clear();
   	    	 $.each(detailedTSList, function(i, item) {
                  table.row.add([
                 	 '<input type="text" name="dates[]" id="dates" readonly value='+ item.date+' max="5" style="width: 100px;" class="form-control" disabled>'
                 	 ,'<input type="text" name="dayname[]" id="dayname" readonly value="'+ item.day_name+'" min="5" max="5" style="width: 100px;" class="form-control" disabled>'
                 	 ,'<div class="input-group date" id="datetimepicker'+ i+'" data-target-input="nearest"><input type="text" value="'+ item.billable_hours+'" disabled class="form-control" onkeydown="return false" datetimepicker-input" name="billablehrs[]" id="billablehrs'+i+'" data-target="#datetimepicker'+i+'"><div class="input-group-append" data-target="#datetimepicker'+ i+'" data-toggle="datetimepicker"><div class="input-group-text"><i class="fa fa-clock-o"></i></div></div>'
                  	 ,'<div class="input-group date" id="datetimepickernon'+i+'" data-target-input="nearest"><input type="text"  value="'+ item.nonbillable_hours+'" disabled class="form-control" onkeydown="return false" datetimepicker-input" name="nonbillablehrs[]" id="nonbillablehrs'+i+'" data-target="#datetimepickernon'+i+'"><div class="input-group-append" data-target="#datetimepickernon'+ i+'" data-toggle="datetimepicker"><div class="input-group-text"><i class="fa fa-clock-o"></i></div></div>'
                     ,'<div class="input-group date" id="overtimedatetimepicker'+i+'" data-target-input="nearest"><input type="text" value="'+ item.over_time+'" disabled class="form-control"  onkeydown="return false" datetimepicker-input" name="over_time[]" id="over_time'+i+'" data-target="#overtimedatetimepicker'+i+'"><div class="input-group-append" data-target="#overtimedatetimepicker'+ i+'" data-toggle="datetimepicker"><div class="input-group-text"><i class="fa fa-clock-o"></i></div></div>'
                  	 /*  ,'<input type="number" name="billablehrs[]" id="billablehrs" value="'+ item.billable_hours+'"  step="0.5" min="0.5" max="24" style="width: 90px;" class="form-control" disabled>'
                 	 ,'<input type="number" name="nonbillablehrs[]" id="nonbillablehrs" value="'+ item.nonbillable_hours+'"   step="0.5" min="0.5" max="24"  style="width: 90px;" class="form-control" disabled>' */
                 	 ,'<input type="number" name="leave[]" id="leave" onkeydown="return false" value="'+ item.leaves+'" step="0.5" min="0" max="1" style="width: 90px;" class="form-control" disabled>'
                 	 ,'<textarea name="comments[]" id="comments" value="'+ item.comments+'" style="width: 240px;" class="form-control" disabled>'+ item.comments+'</textarea>'])
                 	 .draw();

                   $('#overtimedatetimepicker'+i).datetimepicker({
                  	  use24hours: true,
                      format: 'HH:mm',
                      defaultTime: false,
                      pickDate: false,
                      pickSeconds: false,
                     }); 
                  
             	 $('#datetimepicker'+i).datetimepicker({
                 	  use24hours: true,
                     format: 'HH:mm',
                     pickDate: false,
                     pickSeconds: false,
                 });
             	 
           	 $('#datetimepickernon'+i).datetimepicker({
                	  use24hours: true,
                    format: 'HH:mm',
                    pickDate: false,
                    pickSeconds: false,
                });


              	 }); 
       	 
   	    	$('#statusmodal').modal('show');
         	 if(leavefilesList!=""){
             	 var ftype="";
         		$.each(leavefilesList, function(i, item) {
         			  let node = document.createElement('li');
         			  node.innerHTML = '<span id="files'+item.leaves_id+'" onclick="downloadFile('+item.leaves_id+')">'+item.leave_filename+'</span>';
         			  document.getElementById('leavefilename').appendChild(node);
   	     	});
   	    }else{
   	     $('#leavefilename').text("NA");
   	   	    }
 	 	 	    

 	   }else{
        	window.location.href="loginpage";
	            }
     	  
 	    }
 	});
}
function Delete(leaveid,canid,tsid){
	$.ajax({
	    url: "deleteLeaveLetter", //this is the submit URL
	    type: "POST",
	    asyn : false,
	    data: {"leaves_id":leaveid},
	    success: function(response){
	    	if(response!=null || response!=""){
	    	getDetailsByCanId(canid,tsid);
	    	$('#leavefilename').empty();
	    }else{
         	window.location.href="loginpage";
	            }
	    }
	});
	
}
function downloadFile(fid){
	var filetype="";
	var id =$('#files'+fid).text();
	var candidate_id =$('#candidate_id').val();
	if(id=="" || id=="NA"){
		alert("File not available");
		}else{
	var res = encodeURIComponent(id);
	window.location.href="downloadocs?filename="+res+"&filetype="+filetype+"&candidate_id="+candidate_id;
		}
	}

function downloadManagerfile(ele){
	var filetype="";
	var id =$('#mgrfilename').text();
	var candidate_id =$('#candidate_id').val();
	if(id=="" || id=="NA"){
		alert("File not available");
		}else{
	var res = encodeURIComponent(id);
	window.location.href="downloadocs?filename="+res+"&filetype="+filetype+"&candidate_id="+candidate_id;
		}
	}
function viewHistory(){
	var fromtd =$('#fdate').val();
	var totd =$('#tdate').val();
	var candidate_id =$('#candidate_id').val();
	$.ajax({
	    url: "getmailhistory", //this is the submit URL
	    type: "GET",
	    asyn : false,
	    data: {"candidate_id":candidate_id,
	    		"fdate":fromtd,
	    		"tdate":totd,
	    	  },
	    success: function(response){
	    	if(response!=null || response!=""){
	    		$('#historytable').dataTable().fnClearTable();
	    		if (historytable)historytable.clear();
	    		for (var i = 0; i < response.length; i++) {
	    			historytable.row.add(
							[ '' + response[i].week_fromdate + '',
							  '' + response[i].week_todate + '',
							  '' + response[i].dates+ '' ]).draw();
				}
	    		$('#historymodal').modal('show');
	    }else{
         	window.location.href="loginpage";
	            }
	    }
	});
}
var historytable = $('#historytable').DataTable({
	"order" : [ [ 0, "desc" ] ],
	lengthMenu : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
	bLengthChange : true,
	paging : true
});
</script>


</body>
</html>
