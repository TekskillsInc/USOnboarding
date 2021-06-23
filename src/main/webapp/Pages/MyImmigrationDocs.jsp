<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- <%@ taglib uri="http://java.sun.com/js/p/js/tl/core" prefix="c" %> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Portal</title>
<link rel="stylesheet" href="resources/css/css/datepicker3.min.css" />
<link rel="stylesheet" href="resources/css/css/select2.min.css"
	rel="stylesheet" />
</head>
<style type="text/css">
.field-error .control-label, .field-error .help-block, .field-error .form-control-feedback
	{
	color: red;
}

.field-success .control-label, .field-success .help-block,
	.field-success .form-control-feedback {
	color: #3c763d;
}

.col-sm-2 {
	flex: 0 0 14.666667%;
	max-width: 23.666667%;
}
</style>
<body class="sidebar-mini fixed">
	<div id="right-panel" class="right-panel">
		<div class="content">
			<!-- Animated -->
			<div class="animated fadeIn">
				<div class="row">
					<div class="col-md-12">
						<div class="card" id="dataTBL">
							<div class="card-body">
								<div style="text-align: center;"></div>
								<!-- 	<form action="" method="post"> -->
								<input type="hidden" id="candidateid" name="candidateid" value="${candidateid}">
								<div style="max-width: 1150px;">
									<center>
										<span><strong style="font-size: 20px;">Questionnaires</strong></span>
									</center>
									<table id="employeeTableId"
										class="table table-hover table-bordered"
										style="text-align: center;">
										<thead>
											<tr>
												<th style="text-align: center">Name</th>
												<th style="text-align: center">My Status</th>
												<th style="text-align: center">Admin/Manager Status</th>
												<th style="text-align: center">Action</th>
											</tr>
										</thead>
										<tbody>
											<%-- 	<c:forEach var="dl" items="${docList}">
												<tr>
													<input type="hidden" id="doc_status${custl.bgc_checklist_id}" value="${custl.document_status}">
													<td>${dl.document_name}</td>
													<td>${dl.candidate_status}</td>
													<td>${dl.approval_status}</td>
													<td><a class="btn btn-secondary btn-sm"
														data-toggle="modal"
														onclick="editDocList('${dl.doc_checklist_id}','${dl.document_name}','${dl.candidate_id}','${dl.client_id}');"
														style="line-height: 0.5; background-color: #15aabf; color: white;"><i
															class="fa fa-eye"></i></a></td>
												</tr>
											</c:forEach> --%>
											<!-- <tr>
											<td>LCA Questionnaire</td>
											<td>Pending</td>
											<td>Pending</td>
											<td></td>
											</tr> -->
											<tr>
												<td>H1B Questionnaire</td>
												<c:if test="${empty docandimmiformDto.h1bObj}">
													<td>Pending</td>
													<td>Pending</td>
												</c:if>
												<c:if test="${not empty docandimmiformDto.h1bObj}">
													<td>${docandimmiformDto.h1bObj.candidate_status}</td>
													<td>${docandimmiformDto.h1bObj.verifier_status}</td>
												</c:if>
												<c:if test="${not empty docandimmiformDto.h1bObj}">
													<td><a href="h1bquestionnaire"
														class="btn btn-secondary btn-sm"
														style="line-height: 0.5; background-color: #15aabf; color: white;"><i
															class="fa fa-edit"></i></a></td>
												</c:if>
												<c:if test="${empty docandimmiformDto.h1bObj }">
													<td><a href="h1bquestionnaire"
														class="btn btn-secondary btn-sm"
														style="line-height: 0.5; background-color: #15aabf; color: white;"><i
															class="fa fa-plus"></i></a></td>
												</c:if>

											</tr>
											<tr>
												<td>Client and Vendor Letter Questionnaire</td>
												<c:if test="${empty docandimmiformDto.clientObj}">
													<td>Pending</td>
													<td>Pending</td>
												</c:if>
												<c:if test="${not empty docandimmiformDto.clientObj}">
													<td>${docandimmiformDto.clientObj.candidate_status}</td>
													<td>${docandimmiformDto.clientObj.verifier_status}</td>
												</c:if>
												<c:if test="${not empty docandimmiformDto.clientObj}">
													<td><a href="clientvendorquestionnaire"
														class="btn btn-secondary btn-sm"
														style="line-height: 0.5; background-color: #15aabf; color: white;"><i
															class="fa fa-edit"></i></a></td>
												</c:if>
												<c:if test="${empty docandimmiformDto.clientObj }">
													<td><a href="clientvendorquestionnaire"
														class="btn btn-secondary btn-sm"
														style="line-height: 0.5; background-color: #15aabf; color: white;"><i
															class="fa fa-plus"></i></a></td>
												</c:if>

											</tr>
											<tr>
												<td>Public Benefits Checklist</td>

												<td>${docandimmiformDto.pbcObj.candidate_status}</td>

												<td>${docandimmiformDto.pbcObj.verifier_status}</td>

												<c:if test="${not empty docandimmiformDto.pbcObj}">
													<td><a href="publicbenifitschecklist"
														class="btn btn-secondary btn-sm"
														style="line-height: 0.5; background-color: #15aabf; color: white;"><i
															class="fa fa-edit"></i></a></td>
												</c:if>
												<c:if test="${empty docandimmiformDto.pbcObj }">
													<td><c:if test="${empty docandimmiformDto.h1bObj}">
															<button class="btn btn-secondary btn-sm"
																style="line-height: 0.5; background-color: #15aabf; color: white;"
																title="To Enable Please Submit H1B Questionnaire Details First"
																disabled>
																<i class="fa fa-plus"></i>
															</button>
														</c:if> <c:if test="${not empty docandimmiformDto.h1bObj}">
															<a href="publicbenifitschecklist"
																class="btn btn-secondary btn-sm"
																style="line-height: 0.5; background-color: #15aabf; color: white;"><i
																class="fa fa-plus"></i></a>
														</c:if></td>
												</c:if>

											</tr>
											<%-- onclick="editDocList('${dl.doc_checklist_id}','${dl.document_name}','${dl.candidate_id}','${dl.client_id}');" --%>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="card" id="dataTBL">
							<div class="card-body">
								<div style="text-align: center;"></div>
								<!-- 	<form action="" method="post"> -->
								<div style="max-width: 1150px;">
									<center>
										<span><strong style="font-size: 20px;">Applicant
												Documents</strong></span>
									</center>
									<table id="employeeTableId"
										class="table table-hover table-bordered"
										style="text-align: center;">
										<thead>
											<tr>
												<th style="text-align: center">Document Name</th>
												<!-- <th style="text-align: center">Sub Document Name</th> -->
												<th style="text-align: center">My Status</th>
												<th style="text-align: center">Admin/Manager Status</th>
												<th style="text-align: center">Action</th>

											</tr>
										</thead>
										<tbody>
											<!--******************************************************************************1 start-->
											<c:forEach var="dl"
												items="${docandimmiformDto.candidatedocTypeList}">
												<tr>
													<input type="hidden" id="doc_is_same${dl.document_id}"
														value="${dl.is_same}">
													<input type="hidden" id="docnametd${dl.document_id}"
														value="${dl.catName}">
													<td>${dl.catName}</td>

													<%-- <c:choose>
													<c:when test="${not empty dl.candidate_status}">
													<td>${dl.candidate_status}</td>
													</c:when>
													<c:otherwise>
													<td>Pending</td>
													</c:otherwise>
													</c:choose> --%>
													<td>${dl.candidate_status}</td>

													<td>${dl.approved_status}</td>
													<td><a class="btn btn-secondary btn-sm"
														data-toggle="modal" data-target="#uploaddocmodal"
														style="line-height: 0.5; background-color: #15aabf; color: white;"
														onclick="uploaddocuments(${dl.candidateId},${dl.document_id},${dl.initiate_id},'Applicant',${dl.profile_id},0)">
															<i class="fa fa-eye"></i>
													</a> <!--******************************************************************************1 close-->


														<%--  <a class="btn btn-secondary btn-sm" data-toggle="modal"  data-target="#editdocmodal" 
													style="line-height: 0.5; background-color: #15aabf; color: white;" onclick="updatedoc(${dl.candidateId},${dl.document_id},${dl.initiate_id},'${dl.catName}')" >
													<i	class="fa fa-edit"></i></a>  --%> <%-- <a class="btn btn-secondary btn-sm"
														data-toggle="modal"
														onclick="editDocList('${dl.doc_checklist_id}','${dl.catName}','${dl.candidate_id}','${dl.client_id}');"
														style="line-height: 0.5; background-color: #15aabf; color: white;"><i
															class="fa fa-eye"></i></a> --%></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<c:if test="${docandimmiformDto.candidateSpouseList.size() gt 0}">
					<div class="row">
						<div class="col-md-12">
							<div class="card" id="dataTBL">
								<div class="card-body">
									<div style="text-align: center;"></div>
									<!-- 	<form action="" method="post"> -->
									<div style="max-width: 1150px;">
										<center>
											<span><strong style="font-size: 20px;">Spouse
													Documents</strong></span>
										</center>
										<table id="vendorTable"
											class="table table-hover table-bordered"
											style="text-align: center;">
											<thead>
												<tr>
													<th style="text-align: center">Visa Type</th>
													<th style="text-align: center">Document Name</th>
													<!-- <th style="text-align: center">Sub Document Name</th> -->
													<th style="text-align: center">My Status</th>
													<th style="text-align: center">Admin/Manager Status</th>
													<th style="text-align: center">Action</th>

												</tr>
											</thead>
											<tbody>
												<c:forEach var="dl"
													items="${docandimmiformDto.candidateSpouseList}">
													<tr>
														<input type="hidden" id="spdoc_is_same${dl.document_id}"
															value="${dl.is_same}">
														<input type="hidden" id="spdocnametd${dl.document_id}"
															value="${dl.catName}">
														<td>${dl.profileName}</td>
														<td>${dl.catName}</td>
														<td>${dl.candidate_status}</td>
														<td>${dl.approved_status}</td>
														<td><a class="btn btn-secondary btn-sm"
															data-toggle="modal" data-target="#uploaddocmodal"
															style="line-height: 0.5; background-color: #15aabf; color: white;"
															onclick="uploaddocuments(${dl.candidateId},${dl.document_id},${dl.initiate_id},'Spouse',${dl.profile_id},0)">
																<i class="fa fa-eye"></i>
														</a></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${docandimmiformDto.candidateKidsList.size() gt 0}">
					<div class="row">
						<div class="col-md-12">
							<div class="card" id="dataTBL">
								<div class="card-body">
									<div style="text-align: center;"></div>
									<!-- 	<form action="" method="post"> -->
									<div style="max-width: 1150px;">
										<center>
											<span><strong style="font-size: 20px;">Child's
													Documents</strong></span>
										</center>
										<table id="customerTable"
											class="table table-hover table-bordered"
											style="text-align: center;">
											<thead>
												<tr>
													<th style="text-align: center">Child Name</th>
													<th style="text-align: center">Document Name</th>
													<!-- <th style="text-align: center">Sub Document Name</th> -->
													<th style="text-align: center">My Status</th>
													<th style="text-align: center">Admin/Manager Status</th>
													<th style="text-align: center">Action</th>

												</tr>
											</thead>
											<tbody>
												<c:forEach var="dl"
													items="${docandimmiformDto.candidateKidsList}">
													<tr>
														<input type="hidden" id="spdoc_is_same${dl.document_id}"
															value="${dl.is_same}">
														<input type="hidden" id="spdocnametd${dl.document_id}"
															value="${dl.catName}">
														<td>${dl.beneficiary_Name}</td>
														<td>${dl.catName}</td>
														<td>${dl.candidate_status}</td>
														<td>${dl.approved_status}</td>
														<td><a class="btn btn-secondary btn-sm"
															data-toggle="modal" data-target="#uploaddocmodal"
															style="line-height: 0.5; background-color: #15aabf; color: white;"
															onclick="uploaddocuments(${dl.candidateId},${dl.document_id},${dl.initiate_id},'Kids',${dl.profile_id},${dl.questionnarie_id})">
																<i class="fa fa-eye"></i>
														</a></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:if>


				<div class="modal fade" id="h1bDocsModal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true">
					<div class="modal-dialog modal-lg" style="max-width: 90%"
						role="document">
						<div class="modal-content">
							<div class="modal-header" style="padding: .5rem;">
								<button class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<center>
									<h5 class="modal-title" id="exampleModalLabel"
										style="font-size: 15px">
										<b></b>
									</h5>
								</center>
							</div>
							<div class="modal-body">
								<form action="/" enctype="multipart/form-data" id="h1docsForm">
									<div class="row">
										<input type="hidden" id="chklistId" name="chklistId">
										<input type="hidden" id="h1candidateid" name="h1candidateid">
										<input type="hidden" id="h1clientid" name="h1clientid">
										<div class="col-md-12 col-sm-12">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><span
													style="color: red"></span>Document Type</label>
												<div class="col-sm-6">
													<select name="document_subtype" autoComplete="off"
														class="form-control text-left" id="document_subtype">
													</select>
												</div>
											</div>

											<div class='form-group row' id="add">
												<label for="inputPassword" class="col-sm-3 control-label">Add
													Documents</label>
												<div class="col-xs-4">
													<button type="button" class="btn btn-success addButton">
														<i class="fa fa-plus"></i>
													</button>
												</div>
											</div>

											<div id="h1Documents" class="form-group"
												style="display: none;">
												<div class="form-group row">
													<div class="col-sm-4">
														<input type="text" id="h1documentInput"
															name="h1documentInput[]"
															placeholder="Enter Document Name" class="form-control" />
													</div>
													<div class="col-sm-4">
														<input type="file" id="h1document" name="h1document[]"
															class="form-control" />
													</div>
													<div class="col-xs-4">
														<button type="button" class="btn btn-danger removeButton">
															<i class="fa fa-minus"></i>
														</button>
													</div>
												</div>
											</div>
											<!-- 		 <div class="form-group row">
													<label class="col-sm-3 control-label"><b>Add Documents</b></label>
													<div class="col-lg-3 control-label">
														<button class="btn btn-success btn-sm" type="button"
															onclick="OtherAdditional();">
															<span class="glyphicon glyphicon-plus" aria-hidden="true">+</span>
														</button>
													</div>
												</div> -->
											<!-- <div id="OtherBtnDiv"></div> -->
											<div class="text-center ml-5">
												<button type="button" class="btn btn-info btn-sm"
													id="h1docsaveid">Save</button>
											</div>
										</div>
									</div>
								</form>
								<center>
									<div class="row" id="h1docslist">
										<div class="col-lg-12">
											<table id="h1bdocstable"
												class="table table-hover table-bordered"
												style="text-align: center;">
												<thead>
													<tr>
														<th style="text-align: center">Document Name</th>
														<th style="text-align: center">Document Type</th>
														<th style="text-align: center">Document Sub Type</th>
														<th style="text-align: center">Document Input</th>
														<th style="text-align: center">Submitted On</th>
														<th style="text-align: center">Submitted By</th>
														<th style="text-align: center">Action</th>
													</tr>
												</thead>
												<tbody>

												</tbody>
											</table>
										</div>
									</div>
								</center>
							</div>
						</div>
					</div>
				</div>
				<!--******************************************************************************2 start-->

				<div class="modal fade" id="uploaddocmodal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true">
					<div class="modal-dialog modal-lg" style="height: 100%;">
						<div class="modal-content" style="width: 100%; height: 80%;overflow-y: auto;overflow-x: hidden;">
							<div class="modal-header" style="padding: .25rem;">
								<button class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								 <h5 class="modal-title" id="exampleModalLabel"
									style="font-size: 15px">
									<b>Employee Documents  <span id="catname" style='color: #00bcd4; font-size: 16px;'></span></b>
								</h5>
							</div>
							<div class="modal-body">
								<!--  <form action="/"  enctype="multipart/form-data" 	id="savedocumentsFormid"> -->
								<form action="saveimmidocumentbydocId"
									enctype="multipart/form-data" method="post" onsubmit="return checkForm(this);">
									<input type="hidden" id="OtherBtnCount" name="OtherBtnCount">
									<input type="hidden" id="subdoclength" name="subdoclength">
									<input type="hidden" id="initiateid1" name="initiateid">
									<input type="hidden" id="candidateId2" name="candidateId2">
									<input type="hidden" id="profile_id" name="profile_id">
									<input type="hidden" id="benf_type" name="benf_type">
									 <input type="hidden" id="docsubdoclength" name="docsubdoclength">
									<input type="hidden" id="docid" name="docid">
									<input type="hidden" id="questionnarie_id" name="questionnarie_id">
								<%-- 	<div class='form-control' style='display: contents;'>
										<center>
											<label style='font-size: 20px;'>Document Name :</label><span
												id="catname" style='color: red; font-size: 20px;'></span>
										</center>
										<br>
									</div> --%>
									
									<div class="form-group row" style="display: none"
										id="subdoclistdiv">
										<label class="col-sm-3 col-form-label"><span
											style="color: red"></span>Document Type</label>
										<div class="col-sm-6">
											<select name="doc_subtype" autoComplete="off"
												class="form-control text-left" id="doc_subtype"
												multiple="multiple" style="width: 100%;">
											</select>
										</div>
									</div>

									<div id="listAbtnDiv"></div>

									<div id="OtherBtnDiv"></div>

									<div class="text-center ml-5">
										<input type='submit' id='savesubmitid'
											class='btn btn-primary btn-sm' value='Save' onclick="checkDuplicates(event)">
									</div>
									<br>
									<div id="edocumentsTable1_wrapper" style="display: none;">
										<table id="edocumentsTable1"
											class="table table-striped text-center ">
											<thead>
												<tr>
													<th>Document Type</th>
													<th>File name</th>
													<th>Status</th>
													<th>Edit</th>
												</tr>
											</thead>
											<tbody id="edit1"></tbody>
										</table>
									</div>

									
								</form>
							</div>
						</div>
					</div>
				</div>

				<div class="modal fade" id="editdocmodal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true" style="top: 180px;">
					<div class="modal-dialog modal-lg" style="max-width: 45%;"
						role="document">
						<div class="modal-content" style="width: 90%; height: 50%;">
							<div class="modal-header" style="padding: .5rem;">
								<button class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h5 class="modal-title" id="exampleModalLabel"
									style="font-size: 15px">
									<b>Edit Documents</b>
								</h5>
							</div>
							<div class="modal-body">

								<form action="/" enctype="multipart/form-data"
									id="updatedocformid">
									<span style='color: blue;' id="subcatname"></span> <span
										style='color: blue;' id="filenameid"></span>
									<!--  <input type="text" id="editdocid" name="editdocid">  -->
									<input type="hidden" id="documentid1" name="documentid1">
									<div class="form-group row">
										<label class="col-sm-4 col-form-label"><span
											style="color: red;"></span>File</label>
										<div class="col-sm-5">
											<input type='file' class="form-control" name='filename1'
												required onchange="ValidateFileSize(this)">
										</div>
										<button type="button" class="btn btn-primary btn-sm"
											id="updatedocid">Update</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>

				<div class="modal fade" id="updatemodal" tabindex="-1" role="dialog"
					aria-labelledby="exampleModalLabel" aria-hidden="true"
					style="top: 50px;">
					<div class="modal-dialog modal-lg" style="max-width: 35%"
						role="document">
						<div class="modal-content">
							<div class="modal-header" style="padding: .5rem;">
								<button class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h5 class="modal-title" id="exampleModalLabel"
									style="font-size: 15px">
									<b>Edit Documents</b>
								</h5>
							</div>
							<div class="modal-body">


								<form action="/" enctype="multipart/form-data"
									id="updateuserdocformid">
									<input type="hidden" id="documentid2" name="documentid2">

									<div class="form-group row">
										<label class="col-sm-4 control-label">Document Type</label>
										<div class="col-sm-5">
											<input type="text" title="only alphabets are allowed"
												name="edinputval" id="edinputval" class="form-control" />
										</div>
										<br>
										<br>
										<br> <label class="col-sm-4 control-label"><span
											style="color: red;"></span>File</label>
										<div class="col-sm-5">
											<input type="file" class="form-control" name="filenameid" onchange="ValidateFileSize(this)"/>
										</div>
									</div>
									<div class="text-center ml-5">
										<button type="button" id="updatebuttonid"
											class="btn btn-primary btn-sm">Update</button>
									</div>
								</form>

							</div>
						</div>
					</div>
				</div>
				<!--******************************************************************************2 close -->



				<div class="modal fade" id="h1bDocModal" tabindex="-1" role="dialog"
					aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog modal-lg" style="max-width: 80%"
						role="document">
						<div class="modal-content">
							<div class="modal-header" style="padding: .5rem;">
								<button class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<center>
									<h5 class="modal-title" id="exampleModalLabel"
										style="font-size: 15px">
										<b></b>
									</h5>
								</center>
							</div>
							<div class="modal-body">
								<div id="h1bDoc" style="display: none;">
									<form action="/" enctype="multipart/form-data" id="h1docForm">
										<div class="row">
											<input type="hidden" id="chklist_Id" name="chklist_Id">
											<input type="hidden" id="h1candidate_id"
												name="h1candidate_id"> <input type="hidden"
												id="h1client_id" name="h1client_id">
											<div class="col-md-12 col-sm-12">
												<div class="form-group row">
													<label class="col-sm-3 col-form-label"><span
														style="color: red"></span>Document Type</label>
													<div class="col-sm-6">
														<select name="doc_subtype" autoComplete="off"
															class="form-control text-left" id="doc_subtype"
															multiple="multiple" style="width: 100%;">
														</select>
													</div>
												</div>
												<div id="listAbtnDiv"></div>

												<div class="text-center ml-5">
													<button type="button" class="btn btn-info btn-sm"
														id="h1docsubmitid">Save</button>
												</div>
											</div>
										</div>
									</form>
								</div>

								<div id="h1bDocId" style="display: none;">
									<form action="/" enctype="multipart/form-data" id="h1bdocForm">
										<div class="row">
											<input type="hidden" id="h1bchklist_Id" name="h1bchklist_Id">
											<input type="hidden" id="h1bcandidate_id"
												name="h1bcandidate_id"> <input type="hidden"
												id="h1bclient_id" name="h1bclient_id">
											<div class="col-md-12 col-sm-12">
												<div class="form-group row">
													<label class="col-sm-3 col-form-label"><span
														style="color: red"></span>Document Type</label>
													<div class="col-sm-6">
														<select name="docsubtype" class="form-control text-left"
															id="docsubtype">
														</select>
													</div>
												</div>
												<div class="form-group row">
													<label class="col-sm-3 col-form-label"><span
														style="color: red"></span>Upload File</label>
													<div class="col-sm-6">
														<input type="file" id="h1bDoc" name="h1bDoc"
															class="form-control" />
													</div>
												</div>
												<div class="text-center ml-5">
													<button type="button" class="btn btn-info btn-sm"
														id="h1bdocsubmitid">Save</button>
												</div>
											</div>
										</div>
									</form>
								</div>

								<div class="row" id="h1bdoclist" style="display: none;">
									<div class="col-lg-12">
										<table id="h1bdoctable"
											class="table table-hover table-bordered"
											style="text-align: center;">
											<thead>
												<tr>
													<th style="text-align: center">Document Name</th>
													<th style="text-align: center">Document Type</th>
													<th style="text-align: center">Document Sub Type</th>
													<th style="text-align: center">Submitted On</th>
													<th style="text-align: center">Submitted By</th>
													<th style="text-align: center">Action</th>
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

				<center>
					<div class="modal fade" id="EditH1BDocumentModal" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalLabel"
						aria-hidden="true" style="top: 100px;">
						<div class="modal-dialog modal-sm" style="max-width: 50%"
							role="document">
							<div class="modal-content">
								<div class="modal-header" style="padding: .5rem;">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<center>
										<h5 class="modal-title" id="exampleModalLabel"
											style="font-size: 15px">
											<b>Document Details</b>
										</h5>
									</center>
								</div>
								<form class='form form-horizontal validate-form' action="/"
									method="post" id="edh1docsForm" style='margin-bottom: 0;'
									enctype="multipart/form-data">
									<input type="hidden" id="documentid" name="documentid">
									<input type="hidden" id="eddocName" name="eddocName"> <input
										type="hidden" id="edcandidateid" name="edcandidateid">
									<input type="hidden" id="edchklistId" name="edchklistId">
									<input type="hidden" id="edclientid" name="edclientid">
									<div class="modal-body">
										<div class="form-group row">
											<label class="col-lg-3 control-label"><Span
												style="color: red;"></Span>Upload File :</label>
											<div class="col-lg-6">
												<input type="file" id="editFile" name="editFile"
													class="form-control text-left"
													onchange="ValidateFile(this)" required /> <span
													id="edfileName" style="color: blue;"></span>
											</div>
										</div>

									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary btn-sm"
											data-dismiss="modal">Close</button>
										<button type="button" id="edh1docsaveid"
											class="btn btn-primary btn-sm">Save changes</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</center>

				<center>
					<div class="modal fade" id="DocumentHistoryModal" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalLabel"
						aria-hidden="true" style="top: 90px;">
						<div class="modal-dialog modal-sm" style="max-width: 60%"
							role="document">
							<div class="modal-content">
								<div class="modal-header" style="padding: .5rem;">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<center>
										<h5 class="modal-title" id="exampleModalLabel"
											style="font-size: 15px">
											<b>Document Details</b>
										</h5>
									</center>
								</div>
								<div class="modal-body">
									<table id="h1bdochisorytable"
										class="table table-hover table-bordered"
										style="text-align: center;">
										<thead>
											<tr>
												<th>Id</th>
												<th>File Name</th>
												<th>Doc Type</th>
												<th>Doc Input</th>
												<th>Submitted On</th>
												<th>Submitted By</th>
												<!-- <th>Status</th>	 -->
											</tr>
										</thead>
										<tbody id="consulatntBody">

										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</center>
				<!--******************************************************************************3 start -->
				<div class="modal fade" id="dochistorymodal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true" style="top: 50px;">
					<div class="modal-dialog modal-lg" role="document">
						<div class="modal-content" style="width: 100%;">
							<div class="modal-header" style="padding: .5rem;">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span>
								</button>
								<center>
									<h5 class="modal-title" id="exampleModalLabel"
										style="font-size: 15px">
										<b>File History Details</b>
									</h5>
								</center>
							</div>
							<form class='form form-horizontal validate-form' action="/"
								id="dochistoryformid" method="post" style='margin-bottom: 0;'
								enctype="multipart/form-data">
								<div class="modal-body">

									<table id="filehistoryTable"
										class="table table-hover table-bordered"
										style="text-align: center;">
										<thead>
											<tr>
												<th>Sub Document Type</th>
												<th>File Name</th>
												<th>Submitted Date</th>
											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
								</div>
							</form>
						</div>
					</div>
				</div>

				<div class="modal fade" id="dochistorymodal1" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true" style="top: 50px;">
					<div class="modal-dialog modal-lg" role="document">
						<div class="modal-content" style="width: 100%;">
							<div class="modal-header" style="padding: .5rem;">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span>
								</button>
								<center>
									<h5 class="modal-title" id="exampleModalLabel"
										style="font-size: 15px">
										<b>File History Details</b>
									</h5>
								</center>
							</div>
							<form class='form form-horizontal validate-form' action="/"
								id="dochistoryformid" method="post" style='margin-bottom: 0;'
								enctype="multipart/form-data">
								<div class="modal-body">

									<table id="filehistoryTable1"
										class="table table-hover table-bordered"
										style="text-align: center;">
										<thead>
											<tr>
												<th>Sub Document Type</th>
												<th>File Name</th>
												<th>Submitted Date</th>
											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
								</div>
							</form>
						</div>
					</div>
				</div>

				<!--******************************************************************************3 close -->
			</div>
		</div>
	</div>
	<script src="resources/js/js/jquery-2.1.4.min.js"></script>
	<script src="resources/js/newjs/bootstrap.min.js"></script>
	<script src="resources/js/newjs/formValidation.min.js"></script>
	<script src="resources/js/newjs/frameworkbootstrap.min.js"></script>
	<script src="resources/js/js/bootstrap-datepicker.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.js"></script>
	<script src="resources/js/newdt/jquery.dataTables.min.js"></script>
	<script src="resources/js/newdt/dataTables.bootstrap4.min.js"></script>
	<script type="text/javascript" src="resources/js/js/select2.min.js"></script>
	<script type="text/javascript">


var tableh1bd = $('#h1bdocstable').DataTable({
	"order" : [ [ 0, "desc" ] ],
	lengthMenu : [ [ 5,10, 15, -1 ], [ 5,10, 15, "All" ] ],
	bLengthChange : true,
	paging : true
});
var tableh1b = $('#h1bdoctable').DataTable({
	"order" : [ [ 0, "desc" ] ],
	lengthMenu : [ [ 5,10, 15, -1 ], [ 5,10, 15, "All" ] ],
	bLengthChange : true,
	paging : true
});

var tabledocse1 = $('#edocumentsTable1').DataTable({
	"order" : [ [ 0, "asc" ] ],
	lengthMenu: [[5,10, 20, 50, -1], [5,10, 20, 50, "All"]],
	bLengthChange : true,
	paging : true
});


$('#edocumentsTable1_wrapper').hide();
function uploaddocuments(candidateId,document_id,intiateid,benf_type,profile_id,questionnarie_id){
	var catname1=$('#docnametd'+document_id).val();
	//$('#savesubmitid').show();
	$('#catname').text(catname1);
	$('#docid').val(document_id);
	$('#initiateid1').val(intiateid);  
	$('#candidateId2').val(candidateId);
	$('#profile_id').val(profile_id);  
	$('#benf_type').val(benf_type);
	$('#questionnarie_id').val(questionnarie_id);
	var is_same=$('#doc_is_same'+document_id).val();
	$('#subdoclistdiv').hide();
 	$('#OtherBtnDiv,#listAbtnDiv').empty();
 	$('#savesubmitid').hide();
	if(is_same<2){
	//	$('#edocumentsTable1_wrapper').show();
		$('#subdoclistdiv').hide();
		$('#OtherBtnDiv').empty();
		$('#savesubmitid').hide();
		$('#OtherBtnDiv').append("<center><label>Add Documents</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-primary btn-sm' id='btnid' onclick='OtherAdditional()'><i class='fa fa-plus'></i></button></button><br><br>");

		$.ajax({
	    	type : "GET",
	        asyn : false,
	        contentType: "application/json", // NOT dataType!
	        url  :"getinputvaluebydocid",
	        data:{
	        	candidateid : candidateId,
	        	document_id : document_id,
	        	intiateid : intiateid,
	        	benficiary_type : benf_type,
	        	profile_id : profile_id,
	        	questionnarie_id: questionnarie_id,
	        	},
	        success  : function(response){
	        	 $('#edocumentsTable1').dataTable().fnClearTable();
	        	if(response.length>0 && response.length!=null){
	        		$('#edocumentsTable1_wrapper').show();
	        	
	        	if (tabledocse1) tabledocse1.clear();
	        
	    	 	 for(var i=0;i<response.length;i++){
	    	 		
	    	 		var candidateid=response[i].candidate_id;
	    			var documenttype_id=response[i].documenttype_id;
	    			 var document_id=response[i].document_id;
	    	        var file=response[i].file_name;	
	    	    	var encoded = encodeURIComponent(file);
	    	        var doctype=response[i].inputvalue;	
	    	        var status=response[i].status;	
    		        var edit='<input type="hidden" id="doctypeval'+document_id+'" value="'+doctype+'"><a class="btn btn-primary btn-sm"  style="color:white" data-toggle="modal" onclick="editdocid1('+document_id+');"  data-target="#updatemodal"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;<button type="button" class="btn btn-info btn-sm" data-toggle="modal"  data-target="#dochistorymodal1" onclick="getdocumentshistory('+document_id+')" ><i class="fa fa-history"></i></button>';
    	
    		      /*   $('#inputval').val(doctype);
    		           $('#updatefileid').text(file); */
    	
					 tabledocse1.row.add([
						''+doctype+'',
						'<a href="downloadh1bdocs?filename='+encoded+'&candidate_id='+candidateid+'" style="color:blue">'+file+'</a>',
						''+status+'',
						''+edit+'', ]).draw(); 
	    	 	   }
	    	 	 }else{
	    	 		$('#edocumentsTable1_wrapper').hide();
	    	 	 }
	        	}
	  });
		
	
	}else{
		$('#edocumentsTable1_wrapper').hide();
		
	$.ajax({
	    	type : "GET",
	        asyn : false,
	        contentType: "application/json", // NOT dataType!
	        url  :"getimmisubdocumentBydocid",
	        data:{
	        	candidateid : candidateId,
	        	document_id : document_id,
	        	intiateid : intiateid,
	        	benficiary_type : benf_type,
	        	profile_id : profile_id,
	        	questionnarie_id: questionnarie_id,
	        	},
	        success  : function(response){
	        	
	        	var docList=response.candidateList;
	        	var docsubdocList=response.candidatedocTypeList;
	        	//var subdocttypeList=response.subdocttypeList;
	        	var subdocttypeList=response.subDocumentTypeList
	        	if(docsubdocList.length>0){
	        		$('#savesubmitid').hide();
	        		$('#docsubdoclength').val(docsubdocList.length);
	        		$('#OtherBtnDiv').empty(); 
	        		 for(var i=0;i<docsubdocList.length;i++){
	 	    	        var document_id=docsubdocList[i].document_id;	
	 	    	 		var subcatName=docsubdocList[i].subcatName;	
	     				var fileName=docsubdocList[i].fileName;	
	     				var encoded = encodeURIComponent(fileName);
	 	    			$('#OtherBtnDiv').append("<input type='hidden' id='document_id"+document_id+"' name='document_id' value='"+document_id+"'><div class=' form-group row'><label class='col-sm-4 col-form-label'><span style='color: black;'>"+subcatName+"</span></label><span style='width:250px;'><a href='downloadh1bdocs?filename="+encoded+"&candidate_id="+candidateId+"' style='color:blue'>"+fileName+"</a></span><button type='button' data-toggle='modal'  data-target='#editdocmodal' class='btn btn-primary btn-sm'   onclick='updatemandatorydocs("+document_id+")'><i class='fa fa-edit'></i></button>&nbsp;&nbsp;<button type='button'  class='btn btn-info btn-sm' data-toggle='modal'  data-target='#dochistorymodal'  onclick='getdochistory("+document_id+","+docList[i].subcategory_id+","+docList[i].initiate_id+","+docList[i].candidateId+","+docList[i].h1b_checklist_id+")' ><i class='fa fa-history'></i></button></div></div>");
	 	    	 	   } 
	        	}else{
	        		if(docList.length==0){
	        		$('#OtherBtnDiv').empty();    
	    	 	  for(var i=0;i<docList.length;i++){
	    	        var documenttype_id=docList[i].document_id;	
	    	 		var subcategory=docList[i].subcatName;	
    				var initiateid=docList[i].initiate_id;	
    				var subdocid=docList[i].subcategory_id;	
    				var candid=docList[i].candidateId;	
    				var h1b_checklist_id=docList[i].h1b_checklist_id;
    			
	    			$('#OtherBtnDiv').append("<input type='hidden' id='subcategory"+h1b_checklist_id+"' name='subcategory' value='"+subcategory+"'><input type='hidden' id='subdocumentid' name='subdocumentid[]' value='"+subdocid+"'><input type='hidden' id='h1b_checklist_id' name='h1b_checklist_id[]' value='"+h1b_checklist_id+"'><div class=' form-group row'><label class='col-sm-4 col-form-label'><span style='color: black;'>"+docList[i].subcatName+"</span></label><input type='file' name='filename[]' id='filename[]' class='files' onchange='ValidateFileSize(this)' required></div></div>");
	    			//<button type='button' data-toggle='modal'  data-target='#editdocmodal' class='btn btn-primary btn-sm'   onclick='updateviewdocuments("+docList[i].h1b_checklist_id+","+docList[i].initiate_id+","+docList[i].subcategory_id+","+docList[i].candidateId+","+docList[i].document_id+")'><i class='fa fa-edit'></i></a></button>&nbsp;&nbsp;<button type='button'  class='btn btn-info btn-sm' data-toggle='modal'  data-target='#dochistorymodal' id='dochistoryid' onclick='getdochistory("+docList[i].document_id+","+docList[i].subcategory_id+","+docList[i].initiate_id+","+docList[i].candidateId+","+docList[i].h1b_checklist_id+")' ><i class='fa fa-history'></i></button>
	    			$('#subcatname').text(subcategory);
					$('#initiateid').val(initiateid);
					$('#candidateId').val(candid);
					$('#subdoclength').val(docList.length);
					$('#savesubmitid').show();
	    	 	     }  
	    	 	  }else{
			 		  $('#subdoclength').val(0);
			 	   }
	    			$('#uploaddocmodal').show();
	        	 }
	        	 $('#doc_subtype').select2({
	        			placeholder: "Select Doc Type",
	        		  });
	    	 	   $('#doc_subtype').empty();
	    	 	   if(subdocttypeList.length>0){
	    	 		  $('#subdoclistdiv').show();
	    	 		  for(var i=0;i<subdocttypeList.length;i++)
		     	       {
		        			$('#doc_subtype').append($('<option/>').attr("value", subdocttypeList[i].subdoctypeid).text(subdocttypeList[i].subdoctypename));
		               }
	    	 	   }else{
	    	 		  $('#subdoclistdiv').hide();
	    	 	   }
	            }
	  });
	}
}
var tablechistory = $('#filehistoryTable1').DataTable({
	"order" : [ [ 0, "desc" ] ],
	lengthMenu : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
	bLengthChange : true,
	paging : true
});
function getdocumentshistory(docid){
	$.ajax({
    	type : "GET",
        asyn : false,
        contentType: "application/json", // NOT dataType!
        url  :"getdocumentshistory",
        data:{
        	docid : docid,
        	},
        success  : function(response){
        	
			$('#filehistoryTable1').dataTable().fnClearTable();
			if (tablechistory) tablechistory.clear();
			for(var i=0;i<response.length;i++){
        	var subdocname=response[i].subdocument_name;
        	var file=response[i].fileName;
        	var submitted_date=response[i].submitted_date;
				tablechistory.row.add(
					          [ '' + subdocname + '',
								'' + file + '',
								'' +submitted_date + ''
								]).draw();
			}
        }
  });
}
function editdocid1(docid){
	var doctypeval=$('#doctypeval'+docid).val();
	$('#documentid2').val(docid);
	$('#edinputval').val(doctypeval);

}
var tablech = $('#filehistoryTable').DataTable({
	"order" : [ [ 0, "desc" ] ],
	lengthMenu : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
	bLengthChange : true,
	paging : true
});


function getdochistory(docid,subdocid,initiateid,candid,checklistid) {
	$.ajax({
		type : "GET",
		asyn : false,
		contentType : "application/json", // NOT dataType!
		url : "viewfilehistory",
		data : {
		docid      : docid,
		subdocid   : subdocid,
		initiateid : initiateid,
		candid     : candid,
		checklistid: checklistid
		},
		success : function(response) {
			if (tablech)tablech.clear();
			$('#filehistoryTable').dataTable().fnClearTable();
			if (response!="") {
			
			for (var i = 0; i < response.length; i++) {
				var encoded = encodeURIComponent(response[i].file_name);
			 	const decisionDate = '/Date('+response[i].submit_date+')/';
				const d = new Date(decisionDate.match(/\d+/)[0] * 1);
				const formattedDate = d.getFullYear()+'-'+("0"+(d.getMonth()+1)).slice(-2)+'-'+("0"+d.getDate()).slice(-2)
					tablech.row.add(
						          [ '' + response[i].documentsubtype_id + '',
									'<a href="downloadh1bdocs?filename='+encoded+'&candidate_id='+candid+'" style="color:blue">'+ response[i].file_name+'</a>',
								/* 	'' + response[i].status + '', */
									'' +formattedDate + ''
									]).draw();
				} 
			}
		
		}
	});
}



/*function editDocList(chklistid,docName,candidateid,clientid){
	$('#document_subtype,#doc_subtype').empty();
	 $('#chklistId,#chklist_Id').val('');
	// $('#h1bDocsModal,#h1bDocModal').modal('hide');
		//var candiname=$("#candidate option:selected").text();
	//alert("hi*****"+chklistid+docName);	
	if(docName=='Experience letters' || docName=='Paystubs' || docName=='W2s'){
		 $.ajax({
		    	type : "GET",
		        asyn : false,
		        contentType: "application/json", // NOT dataType!
		        url  :"getsubdocsbydocname",
		        data:{
		        	docName:docName,
		        	candidateid : candidateid,
	    			clientid : clientid,
		        	},
		        success  : function(response){
		        	var subDocTypeList=response.subDocumentTypeList;
		        	var docList=response.lcaHistoryList;
			    	//$('#document_subtype').append($('<option/>').attr("value", '').text("Select Document Sub Type"));
		        	for(var i=0;i<subDocTypeList.length;i++)
		     	       {
		        		if(subDocTypeList.length==1){
		        			$('#document_subtype').append($('<option/>').attr("value", subDocTypeList[i].subdocumenttype_id).text(subDocTypeList[i].document_type));
		        		    }
		               }
		        	if(docList!=""){
		        	    if (tableh1bd)
		        		tableh1bd.clear();
		    			for(var i=0;i<docList.length;i++){
		    				var encoded = encodeURIComponent(docList[i].file_name);
		    				tableh1bd.row.add([
		    				'<a href="downloadimmigrationdocs?filename='+encoded+'&candidate_id='+candidateid+'" style="color:blue">'+ docList[i].file_name+'</a>',
		    				'' + docList[i].doctypename	+ '',
		    				'' + docList[i].subdocname	+ '',
		    				'' + docList[i].inputval	+ '',
		    				'' + docList[i].submitted_on + '',
		    				'' + docList[i].empName	+ '',
		    				'<a class="btn btn-secondary btn-sm addButton" data-toggle="modal" disabled onclick="editH1BDocument(\''+docList[i].document_id+'\',\''+chklistid+'\',\''+docName+'\',\''+docList[i].file_name+'\',\''+candidateid+'\',\''+clientid+'\');" style="letter-spacing: 1px; background-color: #15aabf; color: white;"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;<a class="btn btn-secondary btn-sm addButton" data-toggle="modal" onclick="viewDocHistory(\''+docList[i].document_id+'\',\''+candidateid+'\',\''+docName+'\');"  title="View Document History" style="letter-spacing: 1px; background-color: #15aabf; color: white;"><i class="fa fa-history"></i></a>', ]).draw();
		    			}
		    			$('#h1docslist').show();
		        	}else{
		        		  $('#h1docslist').hide(); 
		        	  }
		        	$('#h1docsForm')[0].reset();
		        	$('#chklistId').val(chklistid);
		        	$('#h1candidateid').val(candidateid);$('#h1clientid').val(clientid);
		        	$('#h1docsaveid').hide();
		        	$('h5').text(docName+" Document Details");	
			        $('#h1bDocsModal').modal('show');
		            }
		  });
		 
	//	alert("upload Doc if");
	}else{
	//	alert("upload Doc else");
	$('#doc_subtype').select2({
		placeholder: "Select Doc Type",
	  });
	$('#doc_subtype').val('').trigger('change');
		 $.ajax({
		    	type : "GET",
		        asyn : false,
		        contentType: "application/json", // NOT dataType!
		        url  :"getsubdocsbydocname",
		        data:{
		        	docName:docName,
		        	candidateid : candidateid,
	    			clientid : clientid,
		        	},
		        success  : function(response){
		        	var subDocTypeList=response.subDocumentTypeList;
		        	var docList=response.lcaHistoryList;
		        	if(subDocTypeList.length==1){
			    	   $("#h1bDocId").show();
			    	   $("#h1bDoc").hide();
			    	 $('#docsubtype').empty();
		        	for(var i=0;i<subDocTypeList.length;i++)
		     	       {
		        		$('#docsubtype').append($('<option/>').attr("value", subDocTypeList[i].subdocumenttype_id).text(subDocTypeList[i].document_type));
		        		//$('#doc_subtype').val(subDocTypeList[i].subdocumenttype_id).trigger('change');
		               }
		        	 $('#h1bdocForm')[0].reset();
		        	 $('#h1bchklist_Id').val(chklistid); $('#h1bcandidate_id').val(candidateid);$('#h1bclient_id').val(clientid);
		        	}else{
		        		for(var i=0;i<subDocTypeList.length;i++)
			     	       {
			        		$('#doc_subtype').append($('<option/>').attr("value", subDocTypeList[i].subdocumenttype_id).text(subDocTypeList[i].document_type));
			               }
		        		$("#h1bDoc").show();
		        		 $('#h1docForm')[0].reset();
		        		 $('#listAbtnDiv').empty();
		        		 $('#chklist_Id').val(chklistid); $('#h1candidate_id').val(candidateid);$('#h1client_id').val(clientid);
		        		$("#h1bDocId").hide();
		        	}
		        	if(docList!=""){
		        	    if (tableh1b)
		        	    	tableh1b.clear();
		    			for(var i=0;i<docList.length;i++){
		    				var encoded = encodeURIComponent(docList[i].file_name);
		    				tableh1b.row.add([
		    				'<a href="downloadimmigrationdocs?filename='+encoded+'&candidate_id='+candidateid+'" style="color:blue">'+ docList[i].file_name+'</a>',
		    				'' + docList[i].doctypename	+ '',
		    				'' + docList[i].subdocname	+ '',
		    				'' + docList[i].submitted_on + '',
		    				'' + docList[i].empName	+ '',
		    				'<a class="btn btn-secondary btn-sm addButton" data-toggle="modal" disabled onclick="editH1BDocument(\''+docList[i].document_id+'\',\''+chklistid+'\',\''+docName+'\',\''+docList[i].file_name+'\',\''+candidateid+'\',\''+clientid+'\');" style="letter-spacing: 1px; background-color: #15aabf; color: white;"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;<a class="btn btn-secondary btn-sm addButton" data-toggle="modal" onclick="viewDocHistory(\''+docList[i].document_id+'\',\''+candidateid+'\',\''+docName+'\');"  title="View Document History" style="letter-spacing: 1px; background-color: #15aabf; color: white;"><i class="fa fa-history"></i></a>', ]).draw();
		    		     }
		    			$('#h1bdoclist').show();
		        	  }else{
		        		  $('#h1bdoclist').hide(); 
		        	  }
			        	//$('#h1docsubmitid').hide();
						$('h5').text(docName+" Document Details");	
				        $('#h1bDocModal').modal('show');
		            }
	     });
	}
}*/

function updatemandatorydocs(docid){

	$('#documentid1').val(docid);

}

$('#savedocumentsFormid').formValidation({
    framework: 'bootstrap',
    row: {
        valid: 'field-success',
        invalid: 'field-error'
    },
    fields: {
    	'filename[]':{
	          validators: {
	           	notEmpty: {
	                   message: 'Please select document '
	               }
	           }
	       }
      }
});

/* $("#savesubmitid").click(function(event) {
	event.preventDefault();
	$('#savedocumentsFormid').formValidation('validate');
	if (!$('#savedocumentsFormid').data('formValidation').isValid()) {
		 alert("No files selected");
	} else {
		 alert("yes  valid");
	 	if($('#filenameId').get(0).files.length === 0) {
			    alert("No files selected");
	     }else{
	    		$("#savesubmitid").attr("disabled", true);
	    		var form = $('#savedocumentsFormid')[0];
	    		var data = new FormData(form);
	    		$.ajax({
	    		    // url : "saveimmidocumentbydocId", //this is the submit URL
	    			type : "POST",
	    			enctype : 'multipart/form-data',
	    			data : data,
	    			processData : false,
	    			contentType : false,
	    			cache : false,
	    			success : function(data) {
	    				 if(data!=null || data!=""){
	    					 alert(data);
	    					 window.location.href="mydocuments";
	    	   	   	         }
	    	   	   	      else{
	    	   	   	          window.location.href="loginpage";
	    	   	   	   	      }
	    			}
	    		});
	     }
	}
}); */

//$(document).ready(function() {
/* $(".alldifferent").keyup(function() {
    var val=$(this).val();
    alert(val);
    if (val!='') {
        $(".alldifferent").not(this).each(function() {
            if($(this).val()==val) {
                alert("Error, Duplicate Document Name " + val);
                val=$(this).val('');
                return false; // stop the loop
            }
        });
    }
}); */
//});
$(".alldifferent").keyup(function() {
    var val = $(this).val();
    if (val != '') {
        $(".alldifferent").not(this).each(function() {
            if ($(this).val() == val) {
                alert("Error, Duplicate Barcode " + val);
                val = $(this).val('');
                return false; // stop the loop
            }
        });
    }
});

checkDuplicates = function(e){
	  var arr = [];
	  var fileInputs = document.querySelectorAll(".files");
	   fileInputs.forEach(function(input){
	    if(arr.length == 0){
	      arr.push(input.files[0].name);
	    } else {
	      if(arr.indexOf(input.files[0].name) > -1){
	        e.preventDefault();
	        alert("You can not send two or more same files.");
	      } else {
	        arr.push(input.files[0].name);
	      }
	    }
	});
};


/* $("#savesubmitid").click(function(event) {
	//	event.preventDefault();
	//alert(+"*******"+$("input[name='filename1[]']").length);
	var otherAddtnlinputs = document.getElementsByName("otherAddtnlinput[]");
	  var ret1 = true;
	  if(otherAddtnlinputs!='undefined'){
	  for (var x = 0; x < otherAddtnlinputs.length; x++) {       
	      if(otherAddtnlinputs[x].value == '' || otherAddtnlinputs[x].value == '0'){
	          ret1 = false;
	          break;
	          } else {ret1 = true;} 

	       }    
	     if (ret1 == false)
	     {
	       alert('Please Enter Document Name');
	     //  $('input[name="otherAddtnlinput[]"]').focus();
	       return ret1;        
	     }
	  }
	
	 var filenames = document.getElementsByName("filename1[]");
	  var ret = true;
	 if(filenames!='undefined'){
	  for (var x = 0; x < filenames.length; x++) {       
	      if(filenames[x].value == '' || filenames[x].value == '0'){
	          ret = false;
	          break;
	          } else {ret = true;} 

	       }    
	     if (ret == false)
	     {
	       alert('Please Upload File');
	      // $('input[name="filename[]"]').focus();
	       return ret;        
	     }
	    }
	     
		
		 var ret2 = true;	
	     $("[id='h1BtnCount']").each(function(){
					  var id=$(this).val();
					  var h1bFile =$("#h1bFile"+id).val();
					 if(h1bFile!=""){
						 ret2 = true;
					  }else{
						  ret2 = false;
						  alert('Please Upload File');
					  }
		 });
	     //alert(ret2);
	   	 if(ret == true && ret1 == true && ret2 == true){	 
	    		// alert("*****submit***");
	   		$("#savesubmitid").attr("disabled", true);
			var form = $('#saveimmidocumentbydocIdForm')[0];
			var data = new FormData(form);
			$.ajax({
				url : "saveimmidocumentbydocIdForm", //this is the submit URL
				type : "POST",
				enctype : 'multipart/form-data',
				data : data,
				processData : false,
				contentType : false,
				cache : false,
				success : function(data) {
					 if(data!="" || data!=null){
						//  alert("Documents uploaded successfully");
						  alert(data);
						//  $('#OtherBtnDiv,#listAbtnDiv').empty();
						//  $('#filename1[]').val('');
						//  document.getElementById("saveimmidocumentbydocId1").reset();
						  $('#CandDocVerifierStautsModal').modal('hide');
						//   location.reload();
						//  $("#candidate").trigger('change');
						  // delete io_cval;
						   $("#h1btab").trigger('click');
		   	   	         }
		   	   	      else{
		   	   	          window.location.href="loginpage";
		   	   	   	      }
				}
			});
	   		 
	    	 }
}); */
/* function checkForm(form)
{
	alert("hi");
	 var arr = [];
     var fileInputs = document.querySelectorAll(".files");
      fileInputs.forEach(function(input){
       if(arr.length == 0){
         arr.push(input.files[0].name);
       } else {
    	   alert("hi"+arr.indexOf(input.files[0].name));
         if(arr.indexOf(input.files[0].name) > -1){
           alert("You can not send two or more same files.");
           return false;
         } else {
           arr.push(input.files[0].name);
           return true;
         }
     }
  });
  //form.myButton.disabled = true;
 // form.myButton.value = "Please wait...";
 // return true;
} */


$('#updateuserdocformid').formValidation({
    framework: 'bootstrap',
    row: {
        valid: 'field-success',
        invalid: 'field-error'
    },
   
    fields: {
    	edinputval:{
	          validators: {
	           	notEmpty: {
	                   message: 'Please enter Document Name '
	               }
	           }
	       },
	       filenameid:{
		          validators: {
		           	notEmpty: {
		                   message: 'Please select Document  '
		               }
		           }
		       }
      }

});

$("#updatebuttonid").click(function(event) {
	event.preventDefault();
	$('#updateuserdocformid').formValidation('validate');
	if (!$('#updateuserdocformid').data('formValidation').isValid()) {
		// alert("No files selected");
	} else {
		var form = $('#updateuserdocformid')[0];
		var data = new FormData(form);
		$.ajax({
		
			url : "updateformbydocid", //this is the submit URL
			type : "POST",
			enctype : 'multipart/form-data',
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			success : function(response) {
				 if(response!=null || response!=""){
					 alert(response);
				
	   	   	         }
	   	   	      else{
	   	   	          window.location.href="loginpage";
	   	   	   	      }
			}
		});
	}
});

$('#updatemodal').on('hide.bs.modal', function()
{
	$('#updateuserdocformid').bootstrapValidator('resetForm', true);
});
$('#editdocmodal').on('hide.bs.modal', function()
{
	$('#updatedocformid').bootstrapValidator('resetForm', true);
});
$("#updatedocid").click(function(event) {
		event.preventDefault();
		$('#updatedocformid').formValidation('validate');
		if (!$('#updatedocformid').data('formValidation').isValid()) {
			// alert("No files selected");
		} else {
			$('#uploaddocmodal').hide();
			var form = $('#updatedocformid')[0];
			var data = new FormData(form);
			$.ajax({
				url : "updatedoc", //this is the submit URL
				type : "POST",
				enctype : 'multipart/form-data',
				data : data,
				processData : false,
				contentType : false,
				cache : false,
				success : function(data) {
					 if(data!=null || data!=""){
						 alert(data);
						 window.location.href="mydocuments";
		   	   	         }
		   	   	      else{
		   	   	          window.location.href="loginpage";
		   	   	   	      }
				}
			});
		}
});

function editH1BDocument(id,chklistid,docName,fname,candidateid,clientid){
	    $('#documentid,#editFile,#edchklistId,#eddocName').val('');
	    $('#edfileName').text('');
	    $('#documentid').val(id);
	    $('#edchklistId').val(chklistid);
	    $('#eddocName').val(docName);
	    $('#edcandidateid').val(candidateid);
	    $('#edclientid').val(clientid);
	    var encoded = encodeURIComponent(fname);
		$('#edfileName').append('<a href="downloadh1bdocs?filename='+encoded+'&candidate_id='+candidateid+'" style="color:blue">'+fname+'</a>');
		var title="";
	 	$('h5').text(docName+" Document Details");	 	
		$('#EditH1BDocumentModal').modal('show');
};	


//var io_cval = 0; 
$("#document_subtype" ).change(function() {
	var subdocid=$('#document_subtype').val();
	if(subdocid!=""){
		//OtherAdditional();
	}else{
		$('#OtherBtnDiv').empty();
		$('#OtherBtnCount').val('');
	}
});
var max_field=15;
//$('#savesubmitid').hide();
/* io_cval=0;  */


function OtherAdditional() { 
	var io_cval=$("input[name='filename[]']").length;
	$('#OtherBtnCount').val(io_cval);
	io_cval++;   
	  if(io_cval>=1){
		  $('#OtherBtnCount').val(io_cval);
		  $('#subdoclength').val(io_cval);
	 	 //  $('#h1docsaveid,#savesubmitid').show();
	    }else{
	    	 // io_cval=0; 
	    	  $('#OtherBtnCount').val(0);
	    	// $('#h1docsaveid,#savesubmitid').hide();
	    } 
	   $('#savesubmitid').show();
		$('#OtherBtnCount').val(io_cval);
	    var objTo = document.getElementById('OtherBtnDiv');
	    var divtest = document.createElement("div");
		divtest.setAttribute("class", "form-group row removeclass"+io_cval);
		var rdiv = 'removeclass'+io_cval;
	    divtest.innerHTML = '<br><br>&nbsp;&nbsp;&nbsp;&nbsp;<div class="col-sm-4"><input type="text" class="form-control alldifferent" id="otherAddtnlinput'+io_cval+'" name="otherAddtnlinput[]"  placeholder="Enter Document name" required>&nbsp;&nbsp;<input type="hidden" value="'+io_cval+'" name="OtherBtnCount"></div><div class="col-sm-4"><input type="file" class="form-control files" name="filename[]" id="filename[]" onchange="ValidateFileSize(this)" required></div><div class="input-group-btn">&nbsp;&nbsp;<button class="btn btn-danger btn-sm" type="button" onclick="removefieldsupporti9('+io_cval+');"> <span class="glyphicon glyphicon-minus" aria-hidden="true">-</span> </button></div></div></div></div>';
	  /* if(io_cval<=max_field){
	   	 objTo.appendChild(divtest);
	   }else if(io_cval>max_field){
	   	 alert("Cannot upload more than 15 documents!!");
	   } id="btnid" */
	    if (io_cval>=max_field){
		      objTo.appendChild(divtest);
		      alert("Can't upload more than 15 documents!!");
		      $('#btnid').attr('disabled', 'disabled');
	    }else{
		     objTo.appendChild(divtest);
	     }
	    $('#btnSubmit').show();
	   
}
function removefieldsupporti9(rid) {
	var iocval=$("input[name='filename[]']").length;
	$('.removeclass'+rid).remove();
	// alert(io_cval);
//	$('#h1docsaveid,#savesubmitid').hide();
	  iocval--;	
	  $('#OtherBtnCount').val(iocval);
	  $('#subdoclength').val(iocval);
	 if($("input[name='filename[]']").length<=max_field) {
          $('#btnid').removeAttr('disabled');
      }
	 if(iocval==0){
   	   $('#savesubmitid').hide();
      }	  
  /*  if(io_cval>=1){
	 //  $('#h1docsaveid,#savesubmitid').show();
    }else{
    	io_cval=0; 
  	   $('#OtherBtnCount').val(io_cval);
  	   $('#savesubmitid').hide();
    	//$('#h1docsaveid,#savesubmitid').hide();
    } */
}

var prevClicked = [];
var curClicked = [];
var difference = [];
var la_cval = 0; 
$('#doc_subtype').change(function(){
	//var sucdocId=$('#doc_subtype').val();
	//$('#allDocs').removeAttr("disabled");
//	alert($('#docsubdoclength').val());
	if($('#docsubdoclength').val()>0){
		$('#savesubmitid').show();
	}	
	
    curClic9ked = $(this).val();
    if(curClicked == null){
        curClicked=[];
    }
    clickType = getClickType();
    var clickedValue = diffArray(curClicked, prevClicked);
    prevClicked = curClicked;
    if(clickType=='select' && clickedValue!=""){
    	la_cval++;
    	var objTo = document.getElementById('listAbtnDiv');
	    var divtest = document.createElement("div");
		divtest.setAttribute("class", "form-group row removelass"+la_cval);
		divtest.setAttribute("name", clickedValue);
		var label=getOptionText(clickedValue);
		//divtest.innerHTML = '<label class="col-sm-4 control-label">'+clickedValue+'</label><input type="hidden" value="'+clickedValue+'" name="listASelectionId'+la_cval+'"><input type="hidden" value="'+la_cval+'" name="listABtnCount"><div class="col-sm-3"><div class="form-group row"> <input type="file" class="form-control" name="listAFile'+la_cval+'" placeholder="'+clickedValue+'" onchange="ValidateSize(this)" required></div></div><div class="col-sm-2" ><input type="text" id="fromdate'+la_cval+'" class="form-control text-left" title="Enter date in MM/DD/YYYY format only" name="fromdate'+la_cval+'" placeholder="Start Date" class="form-control"required></div><div class="col-sm-2"><input type="text" id="expirydate'+la_cval+'" class="form-control text-left" title="Enter date in MM/DD/YYYY format only" name="expirydate'+la_cval+'" placeholder="End Date" class="form-control"required></div><div class="input-group-btn"> <button class="btn btn-danger" type="button" onclick="removefieldi9(\''+la_cval+'\',\''+clickedValue+'\');"> <span class="glyphicon glyphicon-minus" aria-hidden="true">-</span> </button></div></div></div></div><div class="clear"></div>';
		divtest.innerHTML = '<label class="col-sm-4 control-label">'+label+'</label><input type="hidden" value="'+clickedValue+'" name="h1DocSelectionId'+la_cval+'"><input type="hidden" value="'+la_cval+'" name="h1BtnCount"><div class="col-sm-4"><div class="form-group row"> <input type="file" class="form-control" name="h1bFile'+la_cval+'" placeholder="'+clickedValue+'" onchange="ValidateFileSize(this)" required></div></div></div></div></div>';
		objTo.appendChild(divtest);
    }else{ 
    	   //  alert("else "+clickType);
    	     var objTo = document.getElementById('listAbtnDiv');
    	     var selectedNode = document.getElementsByName(clickedValue);
    	     //alert(selectedNode.length);
    	     if(selectedNode.length > 0 ){
    	       selectedNode[0].remove();
    	       la_cval--;
    	      }
    	    // alert(la_cval);
    	    
    	     if(selectedNode.length < 1 ){
    	    	 $('#savesubmitid').hide();
    	    	 alert($('#subdoclength').val());
    	    	 if($('#subdoclength').val()!=0){
	    	    	 	  $('#savesubmitid').show();
	    	    	}else{
	    	    		  $('#savesubmitid').hide();  
	    	    	}
    	     }
       }
   
});

function getOptionText(value){
    return $('#doc_subtype option[value="' + value + '"]').text();
}
function hellomethod(){}

function getClickType(){
    var prevLen = prevClicked.length;
    var CurLen = curClicked.length;
    if(prevLen < CurLen){
        return 'select';
    }
    else{
        return 'unselect';
    }
}

function diffArray(a1, a2) {
    var a = [], diff = [];
    for (var i = 0; i < a1.length; i++) {
        a[a1[i]] = true;
    }
    for (var i = 0; i < a2.length; i++) {
        if (a[a2[i]]) {
            delete a[a2[i]];
        } else {
            a[a2[i]] = true;
        }
    }
    for (var k in a) {
        diff.push(k);
    }
    return diff;
}  



$("#h1bdocsubmitid").click(function(event) {
	event.preventDefault();
	$('#h1bdocForm').formValidation('validate');
 if(!$('#h1bdocForm').data('formValidation').isValid()) {
     } else { 
		$("#h1bdocsubmitid").attr("disabled", true);
		var form = $('#h1bdocForm')[0];
		var data = new FormData(form);
		$.ajax({
			url : "saveH1BDocument", //this is the submit URL
			type : "POST",
			enctype : 'multipart/form-data',
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			success : function(data) {
				 if(data!="" || data!=null){
					alert("Documents added successfully");
				
					 
					 window.location.href="mydocuments";
				
					// updatedoc(a,b,c,d)
					// $('#h1btab').trigger('click');
					// $("#saveid").attr("disabled", false);
	   	   	         }
	   	   	      else{
	   	   	          window.location.href="loginpage";
	   	   	   	      }
			}
		});
	}
});

$("#h1docsaveid").click(function(event) {
	event.preventDefault();
	$('#h1docsForm').formValidation('validate');
 if (!$('#h1docsForm').data('formValidation').isValid()) {
	   // alert("Not Valid");
	   //$("#spanError").text("Please select atleast one Amendment");
	} else { 
		//alert("Valid");
		//$("#spanError").text('');
		$("#h1docsaveid").attr("disabled", true);
		var form = $('#h1docsForm')[0];
		var data = new FormData(form);
		$.ajax({
			url : "saveH1Documents", //this is the submit URL
			type : "POST",
			enctype : 'multipart/form-data',
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			success : function(data) {
				 if(data!="" || data!=null){
					alert("Documents added successfully");
					 $('#h1bDocsModal').modal('hide');
					// $('#h1btab').trigger('click');
					 window.location.href="mydocuments";
					// $("#saveid").attr("disabled", false);
	   	   	         }
	   	   	      else{
	   	   	          window.location.href="loginpage";
	   	   	   	      }
			}
		});
	}
});

$("#edh1docsaveid").click(function(event) {
	event.preventDefault();
	var chklistid=$("#edchklistId").val();
	var docName=$("#eddocName").val();
	var candidateid=$("#edcandidateid").val();
	var clientid=$("#edclientid").val();
	if($("#editFile").val()!=""){
		$("#h1docsaveid").attr("disabled", true);
		var form = $('#edh1docsForm')[0];
		var data = new FormData(form);
		$.ajax({
			url : "updateH1Documents", //this is the submit URL
			type : "POST",
			enctype : 'multipart/form-data',
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			success : function(data) {
				 if(data!="" || data!=null){
					  alert("Document updated successfully");
					  $('#EditH1BDocumentModal').modal('hide');
					  
						editDocList(chklistid,docName,candidateid,clientid);
	   	   	         }
	   	   	      else{
	   	   	          window.location.href="loginpage";
	   	   	   	      }
			}
		});
	}else{
		alert("Please upload a Document");
	}
});



$("#h1docsubmitid").click(function(event) {
	event.preventDefault();
	$('#h1docForm').formValidation('validate');
	// var h1bFile=$("input[name='h1bFile[]']").length;
	// alert(h1bFile);
 if(!$('#h1docForm').data('formValidation').isValid()) {
	   // alert("Not Valid");
	   //$("#spanError").text("Please select atleast one Amendment");
	} else { 
		//alert("Valid");
		//$("#spanError").text('');
		//$("#h1docsubmitid").attr("disabled", true);
		var form = $('#h1docForm')[0];
		var data = new FormData(form);
$.ajax({
			url : "saveH1Document", //this is the submit URL
			type : "POST",
			enctype : 'multipart/form-data',
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			success : function(data) {
				 if(data!="" || data!=null){
					alert("Documents added successfully");
					 $('#h1bDocModal').modal('hide');
					
				
					 window.location.href="mydocuments";
				
					// $('#h1btab').trigger('click');
					// $("#saveid").attr("disabled", false);
	   	   	         }
	   	   	      else{
	   	   	          window.location.href="loginpage";
	   	   	   	      }
			}
		});
	}
});
</script>
	<script type="text/javascript">
$('#updatedocformid').formValidation({
    framework: 'bootstrap',
    row: {
        valid: 'field-success',
        invalid: 'field-error'
    },
   
    fields: {
    	 filename1:{
	          validators: {
	           	notEmpty: {
	                   message: 'Please select Document  '
	               }
	           }
	       }
      }

});
$('#h1docForm').formValidation({
    framework: 'bootstrap',
    row: {
        valid: 'field-success',
        invalid: 'field-error'
    },
    fields: {
    	 doc_subtype:{
	          validators: {
	           	notEmpty: {
	                   message: 'Please select Document Type '
	               }
	           }
	       }
      }
});
$('#h1bdocForm').formValidation({       
    framework: 'bootstrap',
    row: {
        valid: 'field-success',
        invalid: 'field-error'
    },
    fields: {
    	h1bDoc:{
	          validators: {
	           	notEmpty: {
	                   message: 'Document is mandatory '
	               }
	           }
	       }
      }
});

var docCount=0;
var MAX_OPTIONS = 10;
/* $('#editformid').formValidation({
    framework: 'bootstrap',
    row: {
        valid: 'field-success',
        invalid: 'field-error'
    },
    fields: {
   	 filename1:{
	          validators: {
	           	notEmpty: {
	                   message: 'Document is mandatory '
	               }
	           }
	       }
    } */


$('#h1docsForm').formValidation({
    framework: 'bootstrap',
    row: {
        valid: 'field-success',
        invalid: 'field-error'
    },
    fields: {
    	 'h1document[]':{
	          validators: {
	           	notEmpty: {
	                   message: 'Document is mandatory '
	               }
	           }
	       },       
         'h1documentInput[]': {
       	  validators: {
	              	notEmpty: {
	                      message: 'Name is mandatory'
	                  },
	                  stringLength: {
	                      min: 2,
	                      max: 100,
	                      message: 'Name must be more than 2 and less than 100 characters long'
	                  },
	                  regexp: {
	                	  regexp: /^(?!\s\/&.-+$)(?:([a-zA-Z])[a-zA-Z &.\/-]*)?$/,
	                      message: 'Name accepts alphabetical, specical characters like &-./ allowed and Space allowed but is not at beginning or end of the word'
	                  }
	              }
	     }
      }
}).on('click', '.addButton', function() {
	docCount++;
	/* $('#element').removeAttr('style'); */
     var $template = $('#h1Documents'),
         $clone    = $template
                         .clone()
                         .removeAttr('style')
                         .removeAttr('id')
                         .attr('data-book-index', docCount)
                         .insertAfter($template);

     // Add new field
     $('#h1docsForm')
     .formValidation('addField', $clone.find('[name="h1documentInput[]"]'))
     .formValidation('addField', $clone.find('[name="h1document[]"]'));
     $('#h1docsaveid').show(); 
 })
 .on('click', '.removeButton', function() {
        var $row  = $(this).parents('.form-group'),
        index = $row.attr('data-book-index');
        $h1documentInput = $row.find('[name="h1documentInput[]"]');

     // Remove fields
     $('#h1docsForm')
     	  .formValidation('removeField', $row.find('[name="h1documentInput[' + index + ']"]'))
          .formValidation('removeField', $row.find('[name="h1document[' + index + ']"]'))
          .formValidation('removeField', $h1documentInput);
     // Remove element containing the fields
     $row.remove();
      }).on('added.field.fv', function(e, data) {
           if (data.field === 'h1documentInput[]') {
               if ($('#h1docsForm').find(':visible[name="h1documentInput[]"]').length >= MAX_OPTIONS) {
                   $('#h1docsForm').find('.addButton').attr('disabled', 'disabled');
               }
             }
        })
        .on('removed.field.fv', function(e, data) {
            if (data.field === 'h1documentInput[]') {
                 if ($('#h1docsForm').find(':visible[name="h1documentInput[]"]').length <= MAX_OPTIONS) {
                     $('#h1docsForm').find('.addButton').removeAttr('disabled');
                 }
                if($('#h1docsForm').find(':visible[name="h1documentInput[]"]').length<=1){
                	  $('#h1docsaveid').hide(); 
                 }else{
                	 $('#h1docsaveid').show(); 
                 }
             }
});
</script>
</body>
</html>