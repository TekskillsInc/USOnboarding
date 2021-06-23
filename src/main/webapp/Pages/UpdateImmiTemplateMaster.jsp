<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- <%@ taglib uri="http://java.sun.com/js/p/js/tl/core" prefix="c" %> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="resources/css/css/datepicker3.min.css" />
<link rel="stylesheet" href="resources/css/select2.min.css"/>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script> -->

</head>
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
</style>
<style>
 li {
  display:inline;
}


ul#closeChapter li {
		display: inline;
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
							<h5 class="card-header">Update Document Template</h5>
							<div class="card-body">
								<div class="row">
								<input type="hidden" name="empno" id="empno"value="${empno}">
									<div class="col-md-8 col-sm-12">
										 <form action="/" enctype="multipart/form-data"	id="saveCategoryForm">
										<!-- <form action="profilemaster" method="post" enctype="multipart/form-data"> -->
											
											<div>
												<fieldset>

													<div>

														<div class="form-group row">
															<label class="col-sm-5 col-form-label"><span
																style="color: red">*</span>Template Name</label>
															<div class="col-sm-5">
																<input type="text" class="form-control" name="profilename"
																	id="profilename"  value="${masterdto.profilename}"> <span id="catN"
																	style="color: red;"></span>
															</div>
														</div>
														
														<div class="form-group row">
															<label for="inputPassword" class="col-sm-5 control-label"><span
																style="color: red">*</span>Is This Template Dependent?</label>
															<div class="col-sm-5">
																	
														<%-- <c:choose>
												    <c:when test="${not empty pbcDto.applicantObj.income_maintenance}"> --%>
												   <c:if test="${masterdto.isdependent eq 'Yes'}">
														<label>Yes</label>&nbsp;<input type="radio"	id="isdependent" name="isdependent" value="Yes"	
													style="width: 20px; height: 20px;" required checked/>&nbsp;<label>No</label>&nbsp;
													<input type="radio" id="isdependent" name="isdependent" value="No"	style="width: 20px; height: 20px;" /> <br>
													</c:if>
													<c:if test="${masterdto.isdependent eq 'No'}">
													<label>Yes</label>&nbsp;<input type="radio"	id="isdependent" name="isdependent" value="Yes"	
													style="width: 20px; height: 20px;" required />&nbsp;<label>No</label>&nbsp;
													<input type="radio" id="isdependent" name="isdependent" value="No"	style="width: 20px; height: 20px;" checked/> <br>
													</c:if>
												    <%-- </c:when>
												    <c:otherwise>
												        <label for="q1-y"><input type="radio" name="income_maintenance${allbenfit.benefits_id}" id="income_maintenanceY" value="Yes" required/>Yes</label> 
														<label for="q1-n"><input type="radio" name="income_maintenance${allbenfit.benefits_id}" id="income_maintenanceN" value="No"  />No</label>
												    </c:otherwise>
												    </c:choose> --%>
																
															</div>
														</div>
														
													
														
														
														<div class="form-group row">
															<label class="col-sm-5 col-form-label"><span
																style="color: red"></span></label>
															<div class="col-sm-7"><br>
																<span style="color: blue;" id="subcolor"><i class='fa fa-circle'></i> Candidates Submits documents/forms under this Sub-Document Type </span>
															<br><span  id="subcolorblack"><i class='fa fa-circle'></i> Admin Uploads documents for this Sub-Document Type</span>
															</div>
														</div>
														<input type="hidden" id="profileListSize" name="profileListSize" value="${profileListSize}">
														<input type="hidden" id="subcatArray" name="subcatArray">
														<input type="hidden" id="profile_id" name="profile_id" value="${masterdto.profileid}">
														<input type="hidden" id="subcatvalues" name="subcatvalues">
														<input type="hidden" id="uncheckArray" name="uncheckArray">
														<input type="hidden" id="UCcatid" name="UCcatid">
														
														
														
														
														<%--  <div class="form-group row" style="display: none;">
															<label class="col-sm-5 col-form-label"><span
																style="color: red">*</span>Category</label>
															<div class="col-sm-5">
																<select class="form-control" id="profilename" name="profilename" required>
																	<option value="" disabled>--Select Initiate Type--</option>
																	<c:forEach var="initlist" items="${initiateList}">
																	<option value="${initlist.profile_id}">${initlist.profile_name}</option>
																	</c:forEach>
																	</select>
															</div>
														</div>  --%>
														 
													</div>
												<div class="act1-body" id="actiddiv">
												<div id="sucatview"></div>
												</div>
													
												</fieldset>
											</div>
											<div></div>
											<div class="text-center ml-5">
												<!-- <input  type="submit" class="btn btn-info btn-sm"
													 value="Submit"> -->
													<button  class="btn btn-info btn-sm"
													id="btnSubmit">Update</button> 
												
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
						
						<div class="card" id="responseCard">
							<h5 class="card-header">Documents Template Details</h5>
							<div class="card-body">
								<div class="table-responsive">
									<table class="table table-striped" id="vendorTable">
										<thead>
											<tr>
											<th scope="col">Profile Name</th>
												<th scope="col">Status</th>
												<th scope="col">Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="plist" items="${templateList}">
												<tr>
													<td>${plist.profile_name}</td>
													<td>${plist.status}</td>
													<td>
													<a class='btn btn-info btn-sm' data-toggle='modal' style="padding:3px;line-height:0.728571; background-color: #15aabf; color: white;"
																	onclick="editProfile(${plist.profile_id});">
																	<i class="fa fa-eye" aria-hidden="true"></i>
																</a>
																<%-- <a class='btn btn-danger btn-sm' data-toggle='modal' style="padding:3px;background-color:red;color:white;text-transform:INITIAL;line-height:0.728571;"
																	onclick="profileStatus('${plist.profile_id}','${plist.status}');">Inactive</a> --%>
													</td>
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
		<div>
		
	<c:forEach var="actlist" items="${documentList}">
		<input type="hidden" name="catid" id="catid" value="${actlist.documenttype_id}" />
		<input type="hidden" name="catname" id="catname" value="${actlist.document_type}" />
	</c:forEach>
	</div>
 
 <script src="resources/js/js/jquery-2.1.4.min.js"></script>  
<script src="resources/js/newjs/bootstrap.min.js"></script>
<script src="resources/js/newjs/formValidation.min.js"></script>
<script src="resources/js/newjs/frameworkbootstrap.min.js"></script>
<script src="resources/js/js/bootstrap-datepicker.min.js"></script>
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script> -->

<script type="text/javascript" src="resources/js/js/select2.min.js"></script>

<script>
  $('#Category').select2();
</script>
<script>
function editProfile(profileid) {
	/* var result = confirm("In order to edit this Grouping Profile, you will be redirected to a new tab.");
	if(result==true){ */
	window.open("updateimmitemplate?profileid=" + profileid );
	//}
}
$( "#actiddiv" ).load( "ajax/test.html", function() {
	var actIdPK =document.getElementsByName("catid");
	  var categoryid =[];	
	  var catname=[];
	  var ul=document.createElement('ul');
	  $("#actiddiv").empty();
	  for(var i = 0; i < actIdPK.length; i++){
		  categoryid[i]= document.getElementsByName("catid")[i].value;
	      catname[i]=document.getElementsByName("catname")[i].value;
	      var li = document.createElement("li");
			li.setAttribute("id","chapterview"+categoryid[i]);
			var div = document.createElement("div");
			div.innerHTML = '<p id="actToggl'+categoryid[i]+'" ><i id="angleright'+categoryid[i]+'" class="fas fa-angle-right"></i><i id="angledown'+categoryid[i]+'" style="display:none;" class="fas fa-angle-down"></i>&nbsp;&nbsp;&nbsp;<button id="viewchapid'+categoryid[i]+'"  style="padding:2px;border-radius: 12px;background-color:seagreen;color:white;line-height:0.728571;cursor: pointer;"  onclick="viewSubCategories('+categoryid[i]+')" >'+catname[i]+'</button>';
			li.appendChild(div);  
			ul.appendChild(li);
	  }
			$("#actiddiv").append(ul);
	  	});
$("#subcolor,#subcolorblack").hide();
function viewSubCategories(categoryid){
	var ul=document.createElement('ul');
	ul.setAttribute("id","sucat_id"+categoryid);
	$("#viewchapid"+categoryid).attr("disabled",true);
	var profile_id=$("#profile_id").val();
	var subcatArray=getProfileMasterListById(profile_id);
	$("#angleright"+categoryid).hide();	$("#angledown"+categoryid).show();
	$("#UCcatid").val(categoryid);
	$("#subcolor,#subcolorblack").show();
	$.ajax({
		type : "GET",
		asyn : false,
		contentType : "application/json", // NOT dataType!
		url : "getSubDocumentsByDocid?Categoryid=" + categoryid + "",
		success : function(response) {
			if(response.length==0){
				var li = document.createElement("li");
				
		        li.innerHTML='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:blue">No more Sub-Documents left under this Category</span>'
		        ul.appendChild(li);
				
			}else{
			 var subvalues=$("#subcatvalues").val();
			 var arraysubvalue = subvalues.split(',').map(function(item) {
				    return parseInt(item, 10);
				});
			var sval;
			 for(var i=0;i<response.length;i++){
				 var subcategory_id=response[i].subdocumenttype_id;
				 var subcatname=response[i].document_type;var subcattype=response[i].issubycand;
				 var li = document.createElement("li");
				 var splitstring;
				li.setAttribute("id", "subcategoryview"+subcategory_id);
					for(var j=0;j<arraysubvalue.length;j++){
						sval=arraysubvalue[j]
						 splitstring=sval.toString().split(",");
					if(arraysubvalue.includes(subcategory_id)) {
						if(subcattype==1){
						li.innerHTML='<input type="hidden" id="category" name="category" value="'+categoryid+'">&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" onclick="UnChecked('+categoryid+','+subcategory_id+')" id="subcategory'+categoryid+'" name="subcategory'+categoryid+'" value="'+subcategory_id+'" checked style="width: 16px;height: 16px;" ><span style="font-size"30px;"><span style="color:blue;"><b>'+subcatname+'</b></span>'
						}else{
							li.innerHTML='<input type="hidden" id="category" name="category" value="'+categoryid+'">&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" onclick="UnChecked('+categoryid+','+subcategory_id+')" id="subcategory'+categoryid+'" name="subcategory'+categoryid+'" value="'+subcategory_id+'" checked style="width: 16px;height: 16px;" ><span style="font-size"30px;">'+subcatname+'</span>'
						}
					 }else{
						 if(subcattype==1){
						 li.innerHTML='<input type="hidden" id="category" name="category" value="'+categoryid+'">&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" onclick="UnChecked('+categoryid+','+subcategory_id+')" id="subcategory'+categoryid+'" name="subcategory'+categoryid+'" value="'+subcategory_id+'" style="width: 20px;height: 16px;" ><span style="font-size"30px;"><span style="color:blue;"><b>'+subcatname+'</b></span>'
						 }else{
							 li.innerHTML='<input type="hidden" id="category" name="category" value="'+categoryid+'">&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" onclick="UnChecked('+categoryid+','+subcategory_id+')" id="subcategory'+categoryid+'" name="subcategory'+categoryid+'" value="'+subcategory_id+'" style="width: 20px;height: 16px;" ><span style="font-size"30px;">'+subcatname+'</span>' 
						 }
					} 
					} 
			        ul.appendChild(li);
					}
			}
		}
	});
		   $("#chapterview"+categoryid).append(ul); 
		}

		//var ucsubid=$('#UCcatid').val()
var uncheckarray=[];
function UnChecked(ucsubid,sucatid){
	var checkboxes = document.getElementById('subcategory'+ucsubid);
	 var subvalues=$("#subcatvalues").val();
			 var arraysubvalue = subvalues.split(',').map(function(item) {
				    return parseInt(item, 10);
				});
	if (checkboxes.checked==false){
	if(arraysubvalue.includes(sucatid)) {
		uncheckarray.push(sucatid);
	$("#uncheckArray").val(uncheckarray); 
		}
	}
}
function getProfileMasterListById(profile_id){
	$.ajax({
		type : "GET",
		asyn : false,
		contentType : "application/json", // NOT dataType!
		url : "getImmiProfileMasterListById?profile_id=" + profile_id + "",
		success : function(response) {
			var subcatArray=[];
			 for(var i=0;i<response.length;i++){
				 var subcategory_id=response[i].subcategory_id;
				 subcatArray.push(subcategory_id);
			 }
			 $("#subcatvalues").val(subcatArray);
			 return subcatArray;
		}
	});
	
}
</script>
<script type="text/javascript">

$('#saveCategoryForm').formValidation({
	framework : 'bootstrap',
	row : {
		valid : 'field-success',
		invalid : 'field-error'
	},
	fields : {
		profilename : {
			validators : {
				notEmpty : {
					message : 'Please enter Grouping Name. '
				},
             /*    stringLength: {
                    min: 2,
                    max: 80,
                    message: 'Grouping Name should have minimum 2 and maximum 80 characters. '
                },
                regexp: {
                	regexp: /^[^\s\d\@#$&*()_\/\\<>\[\]-]+([a-zA-Z0-9@#$&*()_\/<>\[\]\\-]+\s)*[a-zA-Z0-9@#$&*()_\/<>\[\]\\-]+$/,
                	message: 'Grouping name accepts alphanumerical characters. Spaces are not allowed at beginning or end of the field. Accepts symbols @#$&*()_-\/<>[]'
                }, */
			}
		}
	}
}); 



		$("#btnSubmit").click(function(event) {
			event.preventDefault();
			$('#saveCategoryForm').formValidation('validate');
			if (!$('#saveCategoryForm').data('formValidation').isValid()) {
				//alert("not Valid");
			} else {
				 var map3;
				var array = [];var catId =[];var catArray =[];
				var categoryIds =document.getElementsByName("category");
				for(var i = 0; i <categoryIds.length; i++){
					var catIdVal = document.getElementsByName("category")[i].value;
					  if(catId.includes(catIdVal)) {
						 continue;
					 } 
						/* var favorite = [];
						$. each($("input[name='subcategory"+catIdVal+"']:checked"), function(){
						favorite. push($(this). val());
						});
						$("#bgclist").val(favorite); */
						 
					 catId[i] = catIdVal;
					 var checkboxes = document.getElementsByName('subcategory'+catIdVal);
					  for (var checkbox of checkboxes) {
					    if (checkbox.checked){
					    	array.push( checkbox.value );
					    	 $("#subcatArray").val(array); 
					    	 $("#catArray").val(catId); 
					    }
					    
					  }
					    /* map3 = new Map([[catIdVal, [favorite]]]); 
				    	console.log(map3);
				    	$("#bgclist").val(map3); */  
				}
				
				if($('#profileListSize').val()>0){
					var profile_id=$("#profile_id").val();
				$("#btnSubmit").attr("disabled", true);
				var form = $('#saveCategoryForm')[0];
				var data = new FormData(form);
				//data.append("bgclist",map3);
				$.ajax({
					url : "updateimmitemplate", //this is the submit URL
					type : "POST",
					enctype : 'multipart/form-data',
					data : data,
					processData : false,
					contentType : false,
					cache : false,
					success : function(data) {
						 if(data!=null || data!=""){
			   	            	/* $('#addsummaryfields').html(data);
			   	      		  	$('#myModal').show();  */
							 alert(data);
			   	      		
		                     window.location.href="updateimmitemplate?profileid="+profile_id+"";
			   	   	         }
			   	   	      else{
			   	   	          window.location.href="loginpage";
			   	   	   	      }
					}
				});
				}else{
			//alert("Please select atleast one Sub Category to generate Grouing Profile");
					alert("Please select atleast one Sub Category to generate Grouping Profile");
				
				}
			}
		});
		
		
		
		

		var textInput = document.getElementById('profilename');
		var timeout = null;
		textInput.onkeyup = function(e) {
			clearTimeout(timeout);
			timeout = setTimeout(function() {
				var profilename=$('#profilename').val();
				 var empno=$('#empno').val();
		   if(empno!=null){
				if (profilename == "") {
					$('#catN').text('');
				} else {
					$.ajax({
						type : "GET",
						asyn : false,
						contentType : "application/json", // NOT dataType!
						//url : "checkprofilemaster?profilename=" + profilename + "",
						url : "checkimmiprofilemaster?profilename=" + profilename + "",
						success : function(response) {
							///alert(response)
							if (response !="") {
								$('#catN').text(profilename+' template already exist');
								$('#btnSubmit').attr("disabled", true);
							} else {
								$('#catN').text('');
								//$('#catN').text('Lable name already exist');
								$('#btnSubmit').attr("disabled", false);
							}
						}
					});
				}
			}else{
				window.location.href="loginpage";
				}
			}, 5);
		};
		
	</script>
	
	<script type="text/javascript">
	
	
	function profileStatus(profileid,status){
			var pstatus="";
			if(status=="Active" || status==""){
				pstatus="InActive";
			}else{
				pstatus="Active";
			}
			var result = confirm("Are you sure to "+pstatus+" the Profile?");
			
			if(result==true){
			$.ajax({
		        type: "GET",
		        url: "profileStatus?profileid="+profileid+"&status="+pstatus+"",
		        cache: false,
		        async: false, 
		        success: function(response){
			        
			        	 alert(response);
			        	 //window.location.href="profilemaster";
			        	 window.location.href="updateprofile?profileid="+profileid+"";
				         
		        },
		        error: function(){                                                                                 
		        }                                                       
		    }); 
			}else{}
		}
	</script>
</body>
</html>