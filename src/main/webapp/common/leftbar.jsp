<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body class="sidebar-mini fixed">
<aside id="left-panel" class="left-panel" > <nav
		class="navbar navbar-expand-sm navbar-default">
	<div id="main-menu" class="main-menu collapse navbar-collapse">
		<ul class="nav navbar-nav">
		<c:choose>
		<c:when test="${not empty lgemailid}">
		<li><a href="bgvFormsUser"><i class="menu-icon fa fa-laptop"></i>Dashboard
		    </a></li>
		</c:when>
		<c:otherwise>
		<li><a href="dashboard"><i class="menu-icon fa fa-laptop"></i>Dashboard
				</a></li>
		</c:otherwise>
		</c:choose>
		
				
		       
				
				<c:if test="${candidates eq 'candidates'}">
				<c:if test="${(dept == 2 || dept == 1)&& (role == 2 || role == 4)}">
				     <li><a href="addEmployees"><i class="menu-icon fa fa-user"></i>Add Employees</a></li>
				</c:if>
				<c:if test="${(dept == 5 || dept == 1) && (role == 2 || role == 4) && (emptype eq 'W2 Admin' || emptype eq 'Admin')}">
					  <li><a href="addContractors"><i class="menu-icon fa fa-user"></i>Add Contractors</a></li>
				</c:if>
				<c:if test="${(dept == 5 || dept == 1 || dept == 2) && (role == 2 || role == 4)}">
					<li><a href="search"><i class="menu-icon fa fa-group"></i>Candidate Info</a></li>
					<li><a href="addCustomer"><i class="menu-icon fa fa-circle"></i>Add Customer</a></li>
					<li><a href="addVendor"><i class="menu-icon fa fa-circle"></i>Add Vendor</a></li>
				</c:if>
				</c:if>
				
				<c:if test="${stsreports eq 'stsreports'}">
				<c:if test="${dept == 1 && role == 4}">
				    <!--  <li><a href="addTemplate"><i class="menu-icon fa fa-plus"></i>Status Report Template</a></li> -->
				    <!--  <li><a href="viewreports"><i class="menu-icon fa fa-calendar-check"></i>View My Status Reports</a></li> --> 
				     <li><a href="allstatus"><i class="menu-icon fa fa-calendar-check"></i>All Status Reports</a></li>
				</c:if>
				<%-- <c:if test="${(lgworkAuth ne 'US Citizen' || lgworkAuth ne 'Green Card') && (emptype ne 'Admin')}"> --%>
				<c:if test="${(emptype eq 'W2 Admin' || emptype eq 'W2') && (emptype ne 'Admin')}">
				       <li><a href="addreport"><i class="menu-icon fa fa-plus"></i>Add Status Report</a></li>
				       <li><a href="viewreports"><i class="menu-icon fa fa-calendar-check"></i>View My Status Reports</a></li>
				</c:if>
				<c:if test="${emptype eq 'W2 Admin' && role == 2 && dept == 2}">
				     <li><a href="allstatus"><i class="menu-icon fa fa-calendar-check"></i>All Status Reports</a></li>
				</c:if>
				</c:if>
				
			<c:if test="${timesheet eq 'timesheet'}">
				<%-- <c:if test="${dept == 1 && role == 4}">
				     <li><a href="addtstemplate"><i class="menu-icon fa fa-columns"></i>Add Timesheet Template</a></li>
				     <li><a href="viewtstemplate"><i class="menu-icon fa fa-calendar-week"></i>View Timesheet Template</a></li>
				     
				  </c:if> --%>
				  <c:if test="${dept==1 && role==4 && (emptype eq 'W2 Admin' || emptype eq 'Admin')}">
				   <li><a href="viewtimesheet"><i class="menu-icon fa fa-calendar"></i>View All Timesheets</a></li> 
				 <!--    <li><a href="viewtimesheet"><i class="menu-icon fa fa-calendar"></i>Candidate Pending TimeSheets</a></li> --> 
				  </c:if>
				  <c:if test="${emptype ne 'Admin'}">
					<li><a href="addtimesheet"><i class="menu-icon fa fa-calendar-plus"></i>Add My Timesheet</a></li>
					<li><a href="mytimesheet"><i class="menu-icon fa fa-eye"></i>View My Timesheets</a></li>
					<!-- <li><a href="viewmytimesheet"><i class="menu-icon fa fa-eye"></i>View My Timesheets</a></li> -->
				</c:if>
			</c:if>
			
			<c:if test="${templates eq 'templates'}">
				<c:if test="${dept == 1 && role == 4}">
				     <li><a href="addtstemplate"><i class="menu-icon fa fa-columns"></i>Add Timesheet Template</a></li>
				     <li><a href="viewtstemplate"><i class="menu-icon fa fa-calendar-week"></i>View Timesheet Template</a></li>
				     
				  </c:if>
				  
			</c:if>
			
			<c:if test="${mdashboard eq 'mdashboard'}">
				<c:if test="${emptype eq 'Admin'}">
				     <li><a href="myDashboard"><i class="menu-icon fa fa-columns"></i>Expiring Documents</a></li>
				     <li><a href="expiredDocuments"><i class="menu-icon fa fa-calendar-week"></i>Expired Documents</a></li>
				  </c:if>
			</c:if>
			
			<c:if test="${bgc eq 'bgc'}">
			<c:if test="${(dept == 5 || dept == 1) && (role == 2 || role == 4) && (emptype eq 'W2 Admin' || emptype eq 'Admin')}">
				<%-- <c:if test="${dept == 5 && role == 2 || (emptype eq 'Admin' && role eq 4)}"> --%>
				     <li><a href="category"><i class="menu-icon fa fa-columns"></i>Add/View Category</a></li>
				     <li><a href="subcategory"><i class="menu-icon fa fa-calendar-week"></i>Add/View SubCategory</a></li>
				     <li><a href="tempreg"><i class="menu-icon fa fa-group"></i>Candidate Registration</a></li>
				    <li><a href="profilemaster"><i class="menu-icon fa fa-calendar-week"></i>Grouping Profile</a></li>
				     <li><a href="assignprofile"><i class="menu-icon fa fa-group"></i>Assign Profile</a></li>
				  <li><a href="contractspanel"><i class="menu-icon fa fa-calendar-week"></i>Contracts Panel</a></li>
				  <li><a href="bgcpendinglist"><i class="menu-icon fa fa-calendar-week"></i>BGC Pending List</a></li>
				   <li><a href="profilesbycandidate"><i class="menu-icon fa fa-calendar-week"></i>Profiles by Candidate</a></li>
				   <li><a href="candoctype"><i class="menu-icon fa fa-calendar-week"></i>Search Document</a></li>
				    <li><a href="documenttype"><i class="menu-icon fa fa-calendar-week"></i>Document Type Master</a></li>
				  	</c:if>
			</c:if>
				<c:if test="${(dept == 5) && (role == 1) && (emptype eq 'W2 Admin')}">
				<li><a href="tempreg"><i class="menu-icon fa fa-group"></i>Candidate Registration</a></li>
				<li><a href="assignprofile"><i class="menu-icon fa fa-group"></i>Assign Profile</a></li>
				  <li><a href="contractspanel"><i class="menu-icon fa fa-calendar-week"></i>Contracts Panel</a></li>
				  <li><a href="bgcpendinglist"><i class="menu-icon fa fa-calendar-week"></i>BGC Pending List</a></li>
				   <li><a href="profilesbycandidate"><i class="menu-icon fa fa-calendar-week"></i>Profiles by Candidate</a></li>
				    <li><a href="candoctype"><i class="menu-icon fa fa-calendar-week"></i>Search Document</a></li>
			</c:if>
			<c:if test="${not empty lgemailid}">
			<li><a href="profilehistory"><i class="menu-icon fa fa-calendar-week"></i>Profiles History</a></li>
			
			</c:if>
			<c:if test="${immigration eq 'immigration'}">
			<li><a href="immigration"><i class="menu-icon fa fa-columns"></i>Immigration</a></li>
			 <li><a href="immitempreg"><i class="menu-icon fa fa-calendar-week"></i>Prospect Registration</a></li>
			 <li><a href="immidocumenttype"><i class="menu-icon fa fa-calendar-week"></i>Document Type Master</a></li>
			 <li><a href="initiatetemplate"><i class="menu-icon fa fa-calendar-week"></i>H1B Documents Template</a></li>
			<li><a href="soccode"><i class="menu-icon fa fa-circle"></i>SOC Code Master</a></li>
			</c:if>
			<c:if test="${myimmidocs eq 'myimmidocs'}">
			<li><a href="mydocuments"><i class="menu-icon fa fa-file-upload"></i>  My Documents</a></li>
			</c:if>
		</ul>
	</div>
	</nav> </aside> 
</body>
</html>