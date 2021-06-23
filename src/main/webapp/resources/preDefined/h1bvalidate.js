$("#paftab").click(function(){
	$('#pafdiv').show();$('#lcadiv,#h1bdiv,#tasksdiv,#viewalltaskdetailsbtn').hide();
	$('#pafdetailsdiv').hide();
	$('#lcadiv,#tasksdiv,#h1EmpDocsDiv,#h1QuestionnaireDiv,#h1QuestionnaireDiv,#h1taskDiv,#allh1hdocListId,#init_h1bDiv,#dependId,#kidsId,#uinit_h1bDiv,#uinitsubh1bid').hide();
	var candidateid = document.getElementById('candidateid').value;
	var clientid="";
	$.ajax({
		type : "GET",
		asyn : false,
		contentType : "application/json", // NOT dataType!
		url : "getlcaquestionaries",
		data : {
			candidateid : candidateid,
			clientid : ""
		    },
		success : function(response) {
			var lcaList=response.alllcaList;
		
			$('#paflcalist').empty();
			$('#paflcalist').append($('<option/>').attr("value", '').text("Select LCA Number"));
			for (var i = 0; i < lcaList.length; i++) {
				var lca;
				if(lcaList[i].lca_number!=null){
					lca=lcaList[i].lca_number+"/ RN"+lcaList[i].lca_reference+" ("+lcaList[i].client_name+")";
				}else{
					lca="NA"+"-RN"+lcaList[i].lca_reference+" ("+lcaList[i].client_name+")";
				}
				$('#paflcalist').append($('<option/>').attr("value",lcaList[i].lca_id).text(lca));
			}
		}
	});
});
var paflisttable = $('#paflisttable').DataTable({
	"order" : [ [ 0, "desc" ] ],
	lengthMenu : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
	bLengthChange : true,
	paging : true
});

$("#paflcalist").change(function() {
	var lcaid = document.getElementById('paflcalist').value;
	var candidateid = document.getElementById('candidateid').value;
	$('#pafdetailsdiv').hide();
	if (lcaid == "") {
		$('#paflcalist').empty();
	} else {
		$.ajax({
			type : "GET",
			asyn : false,
			contentType : "application/json", // NOT dataType!
			url : "getpafdetailsbylcaid",
			data : {
				lcaid : lcaid,
				candidateid: candidateid,
				clientid : "",
			},
			success : function(response) {
				//if (paflisttable)paflisttable.clear();
				//var hsubdocname=$("#ah1document_subtype option:selected").text();
				//$('#paflisttable').dataTable().fnClearTable();
				if (response != "") {
				//	withoutpaftd  certifiedtd  withoutpafspan  certifiedspan
					var lcaList=response.alllcaList;
					var lcaObj=response.lcaObj;
					
					if(lcaList!=null){
					$('#paflcalist').empty();
					$('#paflcalist').append($('<option/>').attr("value", '').text("Select LCA Number"));
					for (var i = 0; i < lcaList.length; i++) {
						var lca;
						if(lcaList[i].lca_number!=null){
							lca=lcaList[i].lca_number+"/ RN"+lcaList[i].lca_reference+" ("+lcaList[i].client_name+")";
						}else{
							lca="NA"+"-RN"+lcaList[i].lca_reference+" ("+lcaList[i].client_name+")";
						}
						$('#paflcalist').append($('<option/>').attr("value",lcaList[i].lca_id).text(lca));
					 }
					 $("#paflcalist option[value="+lcaid+"]").attr('selected', 'selected');								
					
					}
					
					if(lcaObj.lca_submitted_date!=null){
						if(response.docCount==0 || response.docCount==1){
						//	$('#withoutpafspan').text(lcaObj.lca_number);
							$('#paftd').show();
							$('#withoutpaftd,#certifiedtd').hide();
						}else if(response.docCount==2){
							$('#withoutpafspan').text(lcaObj.lca_number+"_PAFWithout Notice of Posting");
							$('#withoutpaftd').show();
							$('#certifiedspan').text(lcaObj.lca_number+"_Certified");
							$('#certifiedtd').show();
						}else if(response.docCount==3){
							$('#withoutpafspan').text(lcaObj.lca_number+"_PAFWithout Notice of Posting");
							$('#withoutpaftd').show();
							$('#certifiedtd').show();
							$('#certifiedspan').text(lcaObj.lca_number+"_Certified");
							
						}
						$('#pafdetailsdiv').show();
					}else{
						alert("LCA submitted date is not available")
						$('#pafdetailsdiv').hide();
						$('#certifiedtd').hide();$('#certifiedspan').text('');
						$('#withoutpaftd').hide();$('#withoutpafspan').text();
					}
					
				}
			}
			
			
		});
		
		}
	
});


function downloadpaf(){
	var lcaid = document.getElementById('paflcalist').value;
	//alert(lcaid);
	window.location.href="downloadlcapdf?lcaid="+lcaid+"&doctype=PAF";
	}
	//function downloadNoticewithoutpaf(){
	$("#withoutpafspan").click(function(){
		var lcaid = document.getElementById('paflcalist').value;
		window.location.href="downloadlcapdf?lcaid="+lcaid+"&doctype=PAFWithoutNotice";
	});
	$("#certifiedspan").click(function(){
		var lcaid = document.getElementById('paflcalist').value;
		window.location.href="downloadlcapdf?lcaid="+lcaid+"&doctype=ApprovedLCA";
	});

var viewalltasks = $('#viewalltasktableid').DataTable({
		"order" : [ [ 0, "desc" ] ],
		lengthMenu : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		bLengthChange : true,
		paging : true
});
	
$("#taskstab").click(function(){
		$('#viewalltaskdetailsbtn').show();
		$('#tasksdiv').show();
		$('#lcadiv,#h1bdiv,#pafdiv,#tasksdiv').hide();
		var candidateid = document.getElementById('candidateid').value;
		var candiname=$("#candidate option:selected").text();
		$.ajax({
			type : "GET",
			asyn : false,
			contentType : "application/json", // NOT dataType!
			url : "getalltasks",
			data : {
				candidateid : candidateid,
			},
			success : function(response) {
				//alert(response.length)
				
				$('#viewalltasktableid').dataTable().fnClearTable();
	  			if (viewalltasks)viewalltasks.clear();
	  			
	  			for(var i=0;i<response.length;i++){
	  			
	  				var created_date = dateFormatdates(response[i].created_date);
	  			viewalltasks.row.add([
	  			'' + response[i].documenttype_name	+ '',
	  			'' + response[i].task_name	+ '',
	  			'' + created_date	+ '',
	  			'' + response[i].due_date	+ '',
	  			'' + response[i].task_status	+ '', 
	  			 '<a class="btn btn-info btn-sm" data-toggle="modal" style="color:white;" onclick="viewalltask('+response[i].task_id+')" ><i class="fa fa-eye"></i></a> ' ]).draw();
	  			}
			}
		});
});

function dateFormatdates(df){
	var date = new Date(df);
	var year = date.getFullYear();
    var month = (1 + date.getMonth()).toString();
        month = month.length > 1 ? month : '0' + month;
    var day = date.getDate().toString();
        day = day.length > 1 ? day : '0' + day;
    var ToDt=month + '/' + day + '/' +  year;
   // var ToDt=year + '-' + month + '-' +  day;
    return ToDt;
	}

function viewalltask(taskid){
	$('#alltaskid').val(taskid);
	$.ajax({
		type : "GET",
		asyn : false,
		contentType : "application/json", // NOT dataType!
		url : "gettaskdetailsbyid",
		data : {
			taskid : taskid
		},
		success : function(response) {
			 var dt=response.created_date;
				//$('#taskid').val(response.task_id);
				if(response.task_status=='Closed'){
					 $("#ualltaskstatus option[value='" + response.task_status + "']").attr('selected', 'selected').text('Closed');
					 //$("#btnSubmit").attr("disabled", true);
				}else{
				$('#ualltaskstatus').val(response.task_status);
				 $("#uallbtnUpdate").attr("disabled", false);
				}
				$('#uallduedate').val(response.due_date);
				$('#uallcreateddate').val($.date(dt));
				$('#ualldescription').val(response.task_description);
				$('#ualltask_name').val(response.task_name);
				$('#uallcomments').val(response.comments);
				var uh1dueDate=new Date($.date(dt));
				$('#uallduedate').datepicker('setStartDate', uh1dueDate);
		}
	});
	$('#updatealltaskmodal').modal('show');
}

$('#updateallTaskForm').formValidation({
	framework : 'bootstrap',
	row : {
		valid : 'field-success',
		invalid : 'field-error'
	},
	fields : {
		taskname : {
			validators : {
				notEmpty : {
					message : 'Please enter Task Name. '
				},
                stringLength: {
                    min: 2,
                    max: 80,
                    message: 'Task Name should have minimum 2 and maximum 80 characters. '
                },
                regexp: {
                	regexp: /^[^\s\d\@#$&*()_\/\\<>\[\]-]+([a-zA-Z0-9@#$&*()_\/<>\[\]\\-]+\s)*[a-zA-Z0-9@#$&*()_\/<>\[\]\\-]+$/,
                	message: 'Task Name accepts alphanumerical characters. Spaces are not allowed at beginning or end of the field. Accepts symbols @#$&*()_-\/<>[]'
                },
			}
		},
		description : {
			validators : {
				notEmpty : {
					message : 'Please enter Task Description. '
				},
                stringLength: {
                    min: 2,
                   // max: 80,
                    message: 'Task Description should have minimum 2 and maximum 80 characters. '
                },
                regexp: {
                	regexp: /^[^\s\d\@#$&*()_\/\\<>\[\]-]+([a-zA-Z0-9@#$&*()_\/<>\[\]\\-]+\s)*[a-zA-Z0-9@#$&*()_\/<>\[\]\\-]+$/,
                	message: 'Task Description accepts alphanumerical characters. Spaces are not allowed at beginning or end of the field. Accepts symbols @#$&*()_-\/<>[]'
                },
			}
		},
		duedate : {
			validators : {
				notEmpty : {
					message : 'Please select Due Date. '
				},
				regexp: {
                	regexp: /(0[1-9]|1[012]).(0[1-9]|1[0-9]|2[0-9]|3[01]).[0-9]{4}/,
                    message: 'please select Due Date in MM/DD/YYYY format'
                },
                date: {
                    format: 'MM/DD/YYYY', 
                    message: 'Due Date is not a valid'
                }
			}
		},
		comments : {
			validators : {
				notEmpty : {
					message : 'Please enter comments. '
				}
			}
		}
	}
});

$("#uallbtnUpdate").click(function(event) {
	event.preventDefault();
	$('#updateallTaskForm').formValidation('validate');
 if (!$('#updateallTaskForm').data('formValidation').isValid()) {
	 
	} else { 
		$("#uallbtnUpdate").attr("disabled", true);
		var form = $('#updateallTaskForm')[0];
		var data = new FormData(form);
		$.ajax({
			url : "updatetask", //this is the submit URL
			type : "POST",
			enctype : 'multipart/form-data',
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			success : function(data) {
				 if(data!=null || data!=""){
					// var taskid = document.getElementById('taskid').value;
					 $('#updatealltaskmodal').modal('hide');
					 $('#taskstab').trigger('click');
					 alert(data);
					 //viewtask(taskid);
	   	   	         }
	   	   	      else{
	   	   	          window.location.href="loginpage";
	   	   	   	      }
			}
		});
	}
});


var tableh1btasks = $('#allh1btasktableid').DataTable({
	"order" : [ [ 0, "desc" ] ],
	lengthMenu : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
	bLengthChange : true,
	paging : true
});
$("#h1btab").click(function(){
	$('#h1bdiv').show();
	//h1EmpDocsDiv
	$('#saveH1InitiationForm').bootstrapValidator('resetForm', true);
	$('#updateH1InitiationForm').bootstrapValidator('resetForm', true);
	$('#hdoctable,#htasktableid').dataTable().fnClearTable();
	$('#lcadiv,#pafdiv,#tasksdiv,#h1EmpDocsDiv,#h1QuestionnaireDiv,#h1taskDiv,#allh1hdocListId,#init_h1bDiv,#dependId,#kidsId,#uinit_h1bDiv,#uinitsubh1bid,#viewalltaskdetailsbtn').hide();
	$("#init_subh1bid").attr("disabled", false);
	var candidateid = document.getElementById('candidateid').value;
	var clientid = document.getElementById('clientid').value;
	var candiname=$("#candidate option:selected").text();
	$('#amcandidateid').val(candidateid);$('#amclientid').val(clientid);
	$('#init_h1bcandidateid').val(candidateid);
	$.ajax({
		type : "GET",
		asyn : false,
		contentType : "application/json", // NOT dataType!
		url : "gettaskdetails",
		data : {
			candidateid : candidateid,
			clientid : clientid,
			type:"H-1B"
		},
		success : function(response) {
		//	$('#htskDetails').empty();
			$('#allh1btasktableid').dataTable().fnClearTable();
			var tasklist=response.alltaskList;
			if(tasklist!=null){
	  			if (tableh1btasks)tableh1btasks.clear();
	  			for(var i=0;i<tasklist.length;i++){
	  				 var dt=tasklist[i].created_date;
	  	      tableh1btasks.row.add([
	  			'' + candiname	+ '',		  			
	  			'' + tasklist[i].task_name	+ '',
	  			'' + tasklist[i].task_description	+ '',
	  			'' + $.date(dt)	+ '',
	  			'' + tasklist[i].due_date	+ '',
	  			'' + tasklist[i].task_status	+ '',
	  			'<a class="btn btn-secondary btn-sm" data-toggle="modal" onclick="viewh1task('+tasklist[i].task_id+')" ><i class="fa fa-edit"></i></a>', ]).draw();
	  			}
		      }
			//alert("hi tasks");
			getDocumentList(candidateid);
		}
	});
});

$('#hdoctable1').DataTable();
var tablehb = $('#hdoctable').DataTable({
	"order" : [ [ 0, "desc" ] ],
	lengthMenu : [ [ 5,10, 15, -1 ], [ 5,10, 15, "All" ] ],
	bLengthChange : true,
	paging : true
});
var table1 = $('#employeeTableId').DataTable({
//	 "order": [[0, "desc"]],
 bLengthChange: true,
 paging: true
});

var initiatedhistory = $('#h1initatehistory').DataTable({
"order": [[0, "desc"]],
bLengthChange: true,
paging: true
});


function getDocumentList(candidateid){
	//alert("hi docs");
	$.ajax({
		type : "GET",
		asyn : false,
		contentType : "application/json", // NOT dataType!
		//url : "getdocumentlist",
		url : "getimmidocumentlist",
		data : {
			candidateid : candidateid
		},
		success : function(response) {
			var adminlist=response.adminList;
    	  	var candidateList=response.candidateList;
    	  	var initiatedList=response.initiatedList;
    	  	
    	  	$('#h1initatehistory').dataTable().fnClearTable();
			if(initiatedList!=null){
	  			if (initiatedhistory)initiatedhistory.clear();
	  		/*	for(var i=0;i<initiatedList.length;i++){
	  				initiatedhistory.row.add([
	  			'' + initiatedList[i].initiate_id	+ '',		  			
	  			'' + initiatedList[i].profileName	+ '',
	  			'' + initiatedList[i].subdate	+ '',
	  			'<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #15aabf; color: white;" data-toggle="modal" onclick="checkpetitionPacket(\''+initiatedList[i].initiate_id+'\',\''+initiatedList[i].candidateId+'\')" ><i class="fa fa-download"></i></a>', ]).draw();
	  			}*/
	  			for(var i=0;i<initiatedList.length;i++){
	  				var downloadscan;
	  				//alert(initiatedList[i].scanedDocCount);
	  				if(initiatedList[i].scanedDocCount>0){
	  					downloadscan='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #6aca2e; color: white;" data-toggle="modal" onclick="downloadscanedDocs(\''+initiatedList[i].initiate_id+'\',\''+initiatedList[i].candidateId+'\')" ><i class="fa fa-download"></i></a>';
	  				}else{
	  					downloadscan='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #greay; color: white;" data-toggle="modal" title="No Scanned Documents Available" disabled><i class="fa fa-download"></i></a>';
	  				}
	  				
	  				initiatedhistory.row.add([
	  			'' + initiatedList[i].initiate_id	+ '',		  			
	  			'' + initiatedList[i].profileName	+ '',
	  			'' + initiatedList[i].subdate	+ '',
	  			//'<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #15aabf; color: white;" data-toggle="modal" onclick="checkpetitionPacket(\''+initiatedList[i].initiate_id+'\',\''+initiatedList[i].candidateId+'\')" ><i class="fa fa-download"></i></a>',
	  			'<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #e62c0e; color: white;" data-toggle="modal" onclick="uploadscanedDocs(\''+initiatedList[i].initiate_id+'\',\''+initiatedList[i].candidateId+'\')" ><i class="fa fa-upload"></i></a>',
	  			''+downloadscan+'', ]).draw();
	  			}
		      } 
    	  	 $('#hdoctable').dataTable().fnClearTable();
    	  	 $('#employeeTableId').dataTable().fnClearTable();
		   if(tablehb)tablehb.clear();
		   if(adminlist!=null){
		   if(adminlist.length>0){
			   var status; var profile;var adminprs;
         	  for(var i=0;i<response.adminList.length;i++){
         		  if(response.adminList[i].approved_status==null){
         			  status="Pending";
         		  }else{
         			  status=response.adminList[i].approved_status;
         		  }
         		  if(response.adminList[i].profileName!=""){
         			  profile=response.adminList[i].profileName;
         		  }else{
         			  profile='<span class="label label-success" style="background-color:grey;color:white;">NA</span>';
         		  }
         		 /*  sarray.push(status);
         		  if(response.adminList[i].taskStatus!=""){
         			  array2.push(response.adminList[i].taskStatus);
         		  } */
         		  //alert(response.adminList[i].approved_status);
         		 if(response.adminList[i].approved_status!=null){
         			 //alert(response.adminList[i].printed_status);
         			if(response.adminList[i].printed_status!=null){
               		  if(response.adminList[i].printed_status=="Yes"){
               			     adminprs='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #15aabf; color: white;" data-toggle="modal" onclick="downloadcategories(\''+response.adminList[i].document_id+'\',\''+response.adminList[i].initiate_id+'\',\''+candidateid+'\')" ><i class="fa fa-download"></i></a><br><font color=red>Is it Printed?</font><br> Yes<input type="radio" name="aisprinted'+i+'" id="aisprintedyes'+i+'" onclick="getprintedYes(\''+response.adminList[i].document_id+'\',\''+response.adminList[i].initiate_id+'\',\''+response.adminList[i].profile_id+'\',\''+response.adminList[i].profile_type+'\',\''+candidateid+'\')" value="Yes" checked="true" style="width: 15px; height: 15px;"/> No<input type="radio" name="aisprinted'+i+'" id="aisprintedno'+i+'" onclick="getprintedNo(\''+response.adminList[i].document_id+'\',\''+response.adminList[i].initiate_id+'\',\''+response.adminList[i].profile_id+'\',\''+response.adminList[i].profile_type+'\',\''+candidateid+'\')" value="No"  style="width: 15px; height: 15px;"/>';
               		  }else{
               			    adminprs='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #15aabf; color: white;" data-toggle="modal" onclick="downloadcategories(\''+response.adminList[i].document_id+'\',\''+response.adminList[i].initiate_id+'\',\''+candidateid+'\')" ><i class="fa fa-download"></i></a><br><font color=red>Is it Printed?</font><br> Yes<input type="radio" name="aisprinted'+i+'" id="aisprintedyes'+i+'" onclick="getprintedYes(\''+response.adminList[i].document_id+'\',\''+response.adminList[i].initiate_id+'\',\''+response.adminList[i].profile_id+'\',\''+response.adminList[i].profile_type+'\',\''+candidateid+'\')" value="Yes" style="width: 15px; height: 15px;"/> No<input type="radio" name="aisprinted'+i+'" id="aisprintedno'+i+'" onclick="getprintedNo(\''+response.adminList[i].document_id+'\',\''+response.adminList[i].initiate_id+'\',\''+response.adminList[i].profile_id+'\',\''+response.adminList[i].profile_type+'\',\''+candidateid+'\')" value="No" checked="true" style="width: 15px; height: 15px;"/>';
               		  }
       	  		  }else{
       	  			         adminprs='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #15aabf; color: white;" data-toggle="modal" onclick="downloadcategories(\''+response.adminList[i].document_id+'\',\''+response.adminList[i].initiate_id+'\',\''+candidateid+'\')" ><i class="fa fa-download"></i></a><br><font color=red>Is it Printed?</font><br> Yes<input type="radio" name="aisprinted'+i+'" id="aisprintedyes'+i+'" onclick="getprintedYes(\''+response.adminList[i].document_id+'\',\''+response.adminList[i].initiate_id+'\',\''+response.adminList[i].profile_id+'\',\''+response.adminList[i].profile_type+'\',\''+candidateid+'\')" value="Yes" style="width: 15px; height: 15px;"/> No<input type="radio" name="aisprinted'+i+'" id="aisprintedno'+i+'" onclick="getprintedNo(\''+response.adminList[i].document_id+'\',\''+response.adminList[i].initiate_id+'\',\''+response.adminList[i].profile_id+'\',\''+response.adminList[i].profile_type+'\',\''+candidateid+'\')" value="No" checked="true" style="width: 15px; height: 15px;"/>';
       	  		      }
        			}else{
        				adminprs='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color:grey; color: white;" data-toggle="modal" disabled><i class="fa fa-download"></i></a><br><font color=red>Is it Printed?</font><br> Yes<input type="radio" name="isprinted'+i+'" id="isprintedyes'+i+'" value="Yes" style="width: 15px; height: 15px;" disabled/> No<input type="radio" name="aisprinted'+i+'" id="aisprintedno'+i+'"value="No"  style="width: 15px; height: 15px;"disabled/>';
                  }
         		
         		//alert(aprintsts);
         		 tablehb.row.add([
                 	''+profile+'',
                 	''+response.adminList[i].candidateName+'',
                 	'<div style=overflow-y:auto;overflow-x:auto;width:200px;max-height:200px;>'+response.adminList[i].catName+'</div>',
                 	/* '<div style=overflow-y:auto;overflow-x:auto;width:200px;max-height:200px;>'+response.adminList[i].subcatName+'</div>', */
                 	'<input type="hidden" id="statusTd'+response.adminList[i].document_id+'" value="'+status+'"/>'+status+'',
                 	'<a class="btn btn-success btn-sm" onclick="addH1BDoc(\''+response.adminList[i].document_id+'\',\''+status+'\',\''+response.adminList[i].catName+'\',\''+response.adminList[i].initiate_id+'\',\''+response.adminList[i].profile_id+'\',\''+response.adminList[i].profile_type+'\');" style="letter-spacing: 1px; background-color: #15aabf; color: white;"><i class="fa fa-eye"></i></a>',
                 	''+adminprs+'',
                 	]).draw();                  
                }
     	  	 }
		   }
		   if(response.candidateList!=null){
		   if(response.candidateList.length>0){
   	  		$("#candidatepanel").show();
   	  		var apprstatus;var cstatus;var printstatus;var printed_status;
   	  		for(var j=0;j<response.candidateList.length;j++){
   	  		      if(response.candidateList[j].approved_status==null){
       			     apprstatus="Pending";
       		      }else{
       			    apprstatus=response.candidateList[j].approved_status;
       		       }
           		 if(response.candidateList[j].candidate_status==null){
           			  cstatus="Pending";
           			  action='<a class="btn btn-default btn-sm" data-toggle="modal" onclick="updateCandDocVerifierStauts(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].catName+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+apprstatus+'\',\''+response.candidateList[j].is_same+'\');" style="letter-spacing: 1px; background-color:#15aabf; color: white;"><i class="fa fa-eye"></i></a>';
           		  }else{
           			  cstatus=response.candidateList[j].candidate_status;
           			  action='<a class="btn btn-success btn-sm" data-toggle="modal" onclick="updateCandDocVerifierStauts(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].catName+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+apprstatus+'\',\''+response.candidateList[j].is_same+'\');" style="letter-spacing: 1px; background-color: #15aabf; color: white;"><i class="fa fa-eye"></i></a>';
           		  } 
           		   if(response.candidateList[j].profileName!=""){
           			  cprofile=response.candidateList[j].profileName;
           		  }else{
           			  cprofile='<span class="label label-success" style="background-color:grey;color:white;">NA</span>';
           		  }
           	//	var document_id=response.candidateList[j].document_id;/'+response.candidateList[j].document_id+'
           		//printed_status=response.candidateList[j].printed_status;
           		//alert(response.candidateList[j].document_id+"****"+response.candidateList[j].printed_status);
           	/*	 if(response.candidateList[j].candidate_status!=null){
           			if(response.candidateList[j].printed_status!=null){
                 		  if(response.candidateList[j].printed_status=="Yes"){
                 			// alert(response.candidateList[j].catName+"***Y*"+response.candidateList[j].printed_status);
                 			 printstatus='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #15aabf; color: white;" data-toggle="modal" onclick="downloadcategories(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+candidateid+'\')" ><i class="fa fa-download"></i></a><br><font color=red>Is it Printed?</font><br> Yes<input type="radio" name="isprinted'+response.candidateList[j].document_id+'" id="isprintedyes'+response.candidateList[j].document_id+'" onclick="getprintedYes(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="Yes"  style="width: 15px; height: 15px;" checked/> No<input type="radio" name="isprinted'+response.candidateList[j].document_id+'" id="isprintedno'+response.candidateList[j].document_id+'" onclick="getprintedNo(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="No"  style="width: 15px; height: 15px;"/>';
                 		  }else { if(response.candidateList[j].printed_status=="No"){
                 			//alert(response.candidateList[j].catName+"**N**"+response.candidateList[j].printed_status);
                 			printstatus='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #15aabf; color: white;" data-toggle="modal" onclick="downloadcategories(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+candidateid+'\')" ><i class="fa fa-download"></i></a><br><font color=red>Is it Printed?</font><br> Yes<input type="radio" name="isprinted'+response.candidateList[j].document_id+'" id="isprintedyes'+response.candidateList[j].document_id+'"  onclick="getprintedYes(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="Yes" style="width: 15px; height: 15px;"/> No<input type="radio" name="isprinted'+response.candidateList[j].document_id+'" id="isprintedno'+response.candidateList[j].document_id+'" onclick="getprintedNo(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="No"   style="width: 15px; height: 15px;" checked/>';
                 		  }else {
                 			 alert(response.candidateList[j].document_id+"****"+response.candidateList[j].printed_status);
                 			 printstatus='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #15aabf; color: white;" data-toggle="modal" onclick="downloadcategories(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+candidateid+'\')" ><i class="fa fa-download"></i></a><br><font color=red>Is it Printed?</font><br> Yes<input type="radio" name="isprinted'+response.candidateList[j].document_id+'" id="isprintedyes'+response.candidateList[j].document_id+'"  onclick="getprintedYes(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="Yes" style="width: 15px; height: 15px;"> No<input type="radio" name="isprinted'+response.candidateList[j].document_id+'" id="isprintedno'+response.candidateList[j].document_id+'" onclick="getprintedNo(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="No"  checked="true" style="width: 15px; height: 15px;">';
                   		  }
         	  		  }else{
              			// alert(response.candidateList[j].catName+"****"+response.candidateList[j].printed_status);
              			 printstatus='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #15aabf; color: white;" data-toggle="modal" onclick="downloadcategories(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+candidateid+'\')" ><i class="fa fa-download"></i></a><br><font color=red>Is it Printed?</font><br> Yes<input type="radio" name="isprinted'+response.candidateList[j].document_id+'" id="isprintedyes'+response.candidateList[j].document_id+'" onclick="getprintedYes(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="Yes" style="width: 15px; height: 15px;"/> No<input type="radio" name="isprinted'+response.candidateList[j].document_id+'" id="isprintedno'+response.candidateList[j].document_id+'" onclick="getprintedNo(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="No" checked="true"  style="width: 15px; height: 15px;"/>';
                 		  }
          			}else{
          				printstatus='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: grey; color: white;" data-toggle="modal" disabled><i class="fa fa-download"></i></a><br><font color=red>Is it Printed?</font><br> Yes<input type="radio" name="isprinted'+response.candidateList[j].document_id+'" id="isprintedyes'+response.candidateList[j].document_id+'" onclick="getprintedYes(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="Yes" style="width: 15px; height: 15px;"disabled/>No<input type="radio" name="isprinted'+response.candidateList[j].document_id+'" id="isprintedno'+response.candidateList[j].document_id+'" onclick="getprintedNo(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="No"  style="width: 15px; height: 15px;"disabled/>';
                 }*/
           		   
           		 if(response.candidateList[j].candidate_status!=null){
            			if(response.candidateList[j].printed_status!=null){
                  		  if(response.candidateList[j].printed_status=="Yes"){
                  			 printstatus='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #15aabf; color: white;" data-toggle="modal" onclick="downloadcategories(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+candidateid+'\')" ><i class="fa fa-download"></i></a><br><font color=red>Is it Printed?</font><br> Yes<input type="radio" name="isprinted'+j+'" id="isprintedyes'+j+'" onclick="getprintedYes(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="Yes"  style="width: 15px; height: 15px;" checked/> No<input type="radio" name="isprinted'+j+'" id="isprintedno'+j+'" onclick="getprintedNo(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="No"  style="width: 15px; height: 15px;"/>';
                  		  }else { 
                  			printstatus='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #15aabf; color: white;" data-toggle="modal" onclick="downloadcategories(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+candidateid+'\')" ><i class="fa fa-download"></i></a><br><font color=red>Is it Printed?</font><br> Yes<input type="radio" name="isprinted'+j+'" id="isprintedyes'+j+'"  onclick="getprintedYes(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="Yes" style="width: 15px; height: 15px;"/> No<input type="radio" name="isprinted'+j+'" id="isprintedno'+j+'" onclick="getprintedNo(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="No"   style="width: 15px; height: 15px;" checked/>';
                  		  }
          	  		  }else{
               			 printstatus='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: #15aabf; color: white;" data-toggle="modal" onclick="downloadcategories(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+candidateid+'\')" ><i class="fa fa-download"></i></a><br><font color=red>Is it Printed?</font><br> Yes<input type="radio" name="isprinted'+j+'" id="isprintedyes'+j+'" onclick="getprintedYes(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="Yes" style="width: 15px; height: 15px;"/> No<input type="radio" name="isprinted'+j+'" id="isprintedno'+j+'" onclick="getprintedNo(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="No" checked="true"  style="width: 15px; height: 15px;"/>';
                  		  }
           			}else{
           				printstatus='<a class="btn btn-secondary btn-sm" style="letter-spacing: 0px; background-color: grey; color: white;" data-toggle="modal" disabled><i class="fa fa-download"></i></a><br><font color=red>Is it Printed?</font><br> Yes<input type="radio" name="isprinted'+j+'" id="isprintedyes'+j+'" onclick="getprintedYes(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="Yes" style="width: 15px; height: 15px;"disabled/>No<input type="radio" name="isprinted'+j+'" id="isprintedno'+j+'" onclick="getprintedNo(\''+response.candidateList[j].document_id+'\',\''+response.candidateList[j].initiate_id+'\',\''+response.candidateList[j].profile_id+'\',\''+response.candidateList[j].profile_type+'\',\''+candidateid+'\')" value="No"  style="width: 15px; height: 15px;"disabled/>';
                    }   
           		table1.row.add([
                	'<input type="hidden" name="csize" id="csize'+j+'" value="'+j+'"/>'+cprofile+'',
                	''+response.candidateList[j].profile_type+'',
                	''+response.candidateList[j].candidateName+'',
                	'<div style=overflow-y:auto;overflow-x:auto;width:200px;max-height:200px;><input type="hidden" id="tdcatid'+response.candidateList[j].document_id+'" value="'+response.candidateList[j].catName+'"/>'+response.candidateList[j].catName+'</div>',
                	/* '<div style=overflow-y:auto;overflow-x:auto;width:200px;max-height:200px;><input type="hidden" id="tdsubcatid'+response.candidateList[j].h1b_checklist_id+'" value="'+response.candidateList[j].subcatName+'"/>'+response.candidateList[j].subcatName+'</div>', */
                	'<input type="hidden" id="tdstatusid'+response.candidateList[j].document_id+'" value="'+cstatus+'"/>'+cstatus+'',
                	'<input type="hidden" id="h1bapprstatustd'+response.candidateList[j].document_id+'" value="'+apprstatus+'"/>'+apprstatus+'',
					''+action+'',
					''+printstatus+'',
            	]).draw();  
                  }
   	  	      }
		   }
		    var clientObj=response.clientObj;var h1bObj=response.h1bObj;var pbcObj=response.pbcObj;
			if(clientObj==null){
				$("#client_canstatus").text("Pending");$("#client_adminstatus").text("Pending");
				$("#cid").attr("disabled",true).prop("title","Candidate yet to be submitted");
			}
			else{
			  	if (clientObj.candidate_status == null) {
					$("#client_canstatus").text("Pending");
				} else {
					$("#cid").attr("disabled",false);
					$("#client_canstatus").text(clientObj.candidate_status);
				}
				if (clientObj.verifier_status == null) {
					$("#client_adminstatus").text("Pending");
				} else {
					$("#client_adminstatus").text(clientObj.verifier_status);
				}
			}
			
			if(h1bObj==null){
				$("#h1b_canstatus").text("Pending");$("#h1b_adminstatus").text("Pending");
				$("#hid").attr("disabled",true).prop("title","Candidate yet to be submitted");
			}
			else{
				if (h1bObj.candidate_status == null) {
					$("#h1b_canstatus").text("Pending");
				} else {
					$("#hid").attr("disabled",false);
					$("#h1b_canstatus").text(h1bObj.candidate_status);
				}
				
				if (h1bObj.verifier_status == null) {
					$("#h1b_adminstatus").text("Pending");
				} else {
					$("#h1b_adminstatus").text(h1bObj.verifier_status);
				}
			}
			
			if(pbcObj==null){
				$("#pbc_canstatus").text("Pending");$("#pbc_adminstatus").text("Pending");
				$("#pid").attr("disabled",true).prop("title","Candidate yet to be submitted");
			   }
			else{
				if (pbcObj.candidate_status == null) {
					$("#pbc_canstatus").text("Pending");
					$("#pid").attr("disabled",true).prop("title","Candidate yet to be submitted");
				} else {
					$("#pid").attr("disabled",false);
					$("#pbc_canstatus").text(pbcObj.candidate_status);
				}
				if (pbcObj.verifier_status == null) {
					$("#pbc_adminstatus").text("Pending");
				} else {
					$("#pbc_adminstatus").text(pbcObj.verifier_status);
				}
			}
		   
		   }
	});
}
		
		var collk = document.getElementsByClassName("collapsibleh1docs");
		var k;
		for (k = 0; k < collk.length; k++) {
			collk[k].addEventListener("click", function() {
				this.classList.toggle("active");
				var content = this.nextElementSibling;
				if (content.style.display === "block") {
					content.style.display = "none";
				} else {
					content.style.display = "block";
				}
			});
		}
		
		var collm = document.getElementsByClassName("collapsibleh1task");
		var m;
		for (m = 0; m < collm.length; m++) {
			collm[m].addEventListener("click", function() {
				this.classList.toggle("active");
				var content = this.nextElementSibling;
				if (content.style.display === "block") {
					content.style.display = "none";
				} else {
					content.style.display = "block";
				}
			});
		}
		var collm = document.getElementsByClassName("collapsibleh1h");
		var m;
		for (m = 0; m < collm.length; m++) {
			collm[m].addEventListener("click", function() {
				this.classList.toggle("active");
				var content = this.nextElementSibling;
				if (content.style.display === "block") {
					content.style.display = "none";
				} else {
					content.style.display = "block";
				}
			});
		}
		var collm = document.getElementsByClassName("collapsibleh1que");
		var m;
		for (m = 0; m < collm.length; m++) {
			collm[m].addEventListener("click", function() {
				this.classList.toggle("active");
				var content = this.nextElementSibling;
				if (content.style.display === "block") {
					content.style.display = "none";
				} else {
					content.style.display = "block";
				}
			});
		}
		
		//by purna
		function benefitsdetails() {
			var candId = document.getElementById('candidate').value;
			$('<a href="updatepbc?candidateId='+candId+'" target="blank"></a>')[0].click();
		   }
		//by Prashant
		function h1bdetails() {
			var candId = document.getElementById('candidate').value;
			$('<a href="h1bqueadmin?candidateId='+candId+'" target="blank"></a>')[0].click();
		   }
		function clientvendordetails() {
			var candId = document.getElementById('candidate').value;
			$('<a href="cvlqueadmin?candidateId='+candId+'" target="blank"></a>')[0].click();
		   }
		
		$('#saveH1InitiationForm').formValidation({
		    framework: 'bootstrap',
		    row: {
		        valid: 'field-success',
		        invalid: 'field-error'
		    },
		    fields: {
		  h1initVal: {
		            validators: {
		            	notEmpty: {
		                    message: 'Please select type '
		                }
		            }
		        },
		  marital_status: {
		        validators: {
		        	notEmpty: {
		                message: 'Please choose atleast one option'
		            }
		        }
		     },
		     kids_status: {
		    	 enabled: false,
		         validators: {
		         	notEmpty: {
		                 message: 'Please choose atleast one option'
		             }
		         }
		      },
		      spousefiling: {
			    	 enabled: false,
			         validators: {
			         	notEmpty: {
			                 message: 'Please choose atleast one option'
			             }
			         }
			      },
		 spousefilingead: {
			    	 enabled: false,
			         validators: {
			        	 notEmpty: {
			                 message: 'Please choose atleast one option'
			             }
			         }
			      },
			 kidsfiling: {
					    	 enabled: false,
					         validators: {
					        	 notEmpty: {
					                 message: 'Please choose atleast one option'
					             }
					         }
					      }
		    }
		}).on('change', '[name="h1initVal"]', function(e) {
			var h1initVal=$("#h1initVal option:selected").text();
			$("#kidsId,#spousefilingId,#kidsfilingId,#spousedependId,#kidsdependId").hide();
			$('input[name=marital_status]').attr('checked', false);
			 if(h1initVal=='H1B CAP'){
					//alert("if****"+h1initVal);
					 $('#spanmstatus').hide();
					 $('#saveH1InitiationForm').formValidation('enableFieldValidators','marital_status',false);
			 }else{
					//alert("else****"+h1initVal);
					 $('#spanmstatus').show();
					 $('#saveH1InitiationForm').formValidation('enableFieldValidators','marital_status',true);
			 }
		 	/* if(h1initVal=='H1B Transfer' || h1initVal=='H1B Extension'){
		 		alert("if****"+h1initVal);
			   $('#saveH1InitiationForm').formValidation('enableFieldValidators','marital_status',true);
		    }else{
		    	alert(h1initVal);
			   $('#saveH1InitiationForm').formValidation('enableFieldValidators', 'marital_status',false); 
		     } */
		}).on('change', '[name="marital_status"]', function(e) {
			var mstatus=$("input[name='marital_status']:checked").val();
			 if(mstatus=='Married'){
			   $("#kidsId").show();
			   $("#spousefilingId").show();
			   $('#saveH1InitiationForm').formValidation('enableFieldValidators','kids_status',true);
			   $('#saveH1InitiationForm').formValidation('enableFieldValidators','spousefiling',true);
		    }else if(mstatus=='Single'){
		       $("#kidsId,#spousefilingId,#kidsfilingId,#spousedependId,#kidsdependId").hide();
		       $("#spouseh4dependVal,#kidsh4dependVal").val('');
		       $("#kids_status,#kidsfiling,#spousefiling,#spousefilingead").prop('checked', false);
		       $('#saveH1InitiationForm').formValidation('enableFieldValidators', 'kids_status',false);
		       /* $('#saveH1InitiationForm').formValidation('enableFieldValidators', 'kidsh4dependVal',false); */
			   $('#saveH1InitiationForm').formValidation('enableFieldValidators', 'spousefilingead',false);
			   $('#saveH1InitiationForm').formValidation('enableFieldValidators', 'kidsfiling',false); 
			   $('#saveH1InitiationForm').formValidation('enableFieldValidators', 'spousefiling',false); 
		     } 
			 //spousedependId spousefilingId kidsdependId kidsdId spousefiling spouseh4dependVal    kidsfiling kidsh4dependVal
		}).on('change', '[name="spousefiling"]', function(e) {
			var status=$("input[name='spousefiling']:checked").val();
			 $('input[name=spousefilingead]').attr('checked', false);
			 if(status=='Yes'){
			  // $("#spousefilingead").prop('checked', false);
			   $("#spousedependId").show();
			   $('#saveH1InitiationForm').formValidation('enableFieldValidators','spousefilingead',true);
		    }else if(status=='No'){
		     //  $("#spousefilingead").prop('checked', false);
		       $("#spousedependId").hide();
			   $('#saveH1InitiationForm').formValidation('enableFieldValidators', 'spousefilingead',false); 
		     } 
			 // spousefilingId kidsdependId kidsdId spousefiling spouseh4dependVal    kidsfiling kidsh4dependVal
		}).on('change', '[name="kids_status"]', function(e) {
			var kstatus=$("input[name='kids_status']:checked").val();
			 $('input[name=kidsfiling]').attr('checked', false);
			 if(kstatus=='Yes'){
			   $("#kidsfilingId").show();
			   $('#saveH1InitiationForm').formValidation('enableFieldValidators','kidsfiling',true);
		    }else if(kstatus=='No'){
		       $("#kidsfilingId").hide();
			   $('#saveH1InitiationForm').formValidation('enableFieldValidators', 'kidsfiling',false); 
		     }
		});
		
		$('#updateH1InitiationForm').formValidation({
		    framework: 'bootstrap',
		    row: {
		        valid: 'field-success',
		        invalid: 'field-error'
		    },
		    fields: {
		  umarital_status: {
		        validators: {
		        	notEmpty: {
		                message: 'Please choose one value'
		            }
		        }
		     },
		     ukids_status: {
		    	 enabled: false,
		         validators: {
		         	notEmpty: {
		                 message: 'Please choose one value'
		             }
		         }
		      },
		      uspousefiling: {
			    	 enabled: false,
			         validators: {
			         	notEmpty: {
			                 message: 'Please choose one value'
			             }
			         }
			      },
			uspousefilingead: {
				    	 enabled: false,
				         validators: {
				        	 notEmpty: {
				                 message: 'Please choose atleast one option'
				             }
				         }
				      },
			 ukidsfiling: {
					    	 enabled: false,
					         validators: {
					        	 notEmpty: {
					                 message: 'Please choose one value'
					             }
					         }
					      }
		    }
		}).on('change', '[name="umarital_status"]', function(e) {
			var mstatus=$("input[name='umarital_status']:checked").val();
			 if(mstatus=='Married'){
			   $("#ukidsId").show();
			   $("#uspousefilingId").show();
			   $('#updateH1InitiationForm').formValidation('enableFieldValidators','ukids_status',true);
			   $('#updateH1InitiationForm').formValidation('enableFieldValidators','uspousefiling',true);
		    }else if(mstatus=='Single'){
		       $("#ukidsId,#uspousefilingId,#ukidsfilingId,#uspousedependId,#ukidsdependId").hide();
		       $("#uspouseh4dependVal,#ukidsh4dependVal").val('');
		       $("#kids_status1,#ukidsfiling1,#spousefiling1,#kids_status2,#kidsfiling2,#spousefiling2").prop('checked', false);
		       $('#updateH1InitiationForm').formValidation('enableFieldValidators', 'ukids_status',false);
		       $('#updateH1InitiationForm').formValidation('enableFieldValidators', 'ukidsh4dependVal',false);
			   $('#updateH1InitiationForm').formValidation('enableFieldValidators', 'uspouseh4dependVal',false);
			   $('#updateH1InitiationForm').formValidation('enableFieldValidators', 'ukidsfiling',false); 
			   $('#updateH1InitiationForm').formValidation('enableFieldValidators', 'uspousefiling',false); 
		     } 
			 //spousedependId spousefilingId kidsdependId kidsdId spousefiling spouseh4dependVal    kidsfiling kidsh4dependVal
		}).on('change', '[name="uspousefiling"]', function(e) {
			var mstatus=$("input[name='uspousefiling']:checked").val();
			 if(mstatus=='Yes'){
			   $("#uspousedependId").show();
			   $('#updateH1InitiationForm').formValidation('enableFieldValidators','uspousefilingead',true);
		    }else if(mstatus=='No'){
		       $("#uspousedependId").hide();
			   $('#updateH1InitiationForm').formValidation('enableFieldValidators', 'uspousefilingead',false); 
		     } 
			 // spousefilingId kidsdependId kidsdId spousefiling spouseh4dependVal    kidsfiling kidsh4dependVal
		}).on('change', '[name="ukids_status"]', function(e) {
			var kstatus=$("input[name='ukids_status']:checked").val();
			 if(kstatus=='Yes'){
			   $("#ukidsfilingId").show();
			   $('#updateH1InitiationForm').formValidation('enableFieldValidators','ukidsfiling',true);
		    }else if(kstatus=='No'){
		       $("#ukidsfilingId,#ukidsdependId").hide();
		       $("#kidsfiling1,#kidsfiling2").prop('checked', false);
			   $('#updateH1InitiationForm').formValidation('enableFieldValidators', 'ukidsfiling',false); 
		     }
		});/*.on('change', '[name="ukidsfiling"]', function(e) {
			var kstatus=$("input[name='ukidsfiling']:checked").val();
			 if(kstatus=='Yes'){
			   $("#ukidsdependId").show();
			   //kidsh4dependVal
			   $('#updateH1InitiationForm').formValidation('enableFieldValidators','ukidsh4dependVal',true);
		    }else if(kstatus=='No'){
		       $("#ukidsdependId").hide();
			   $('#updateH1InitiationForm').formValidation('enableFieldValidators', 'ukidsh4dependVal',false); 
		     }
		});	*/
		
		$("#init_reseth1bid").click(function(event) {
			  document.getElementById("saveH1InitiationForm").reset();
			  $('#saveH1InitiationForm').bootstrapValidator('resetForm', true);
			  $("#kidsId,#spousefilingId,#kidsfilingId,#spousedependId,#kidsdependId").hide();
		});
		
		$("#init_subh1bid").click(function(event) {
			event.preventDefault();
			$('#saveH1InitiationForm').formValidation('validate');
			if (!$('#saveH1InitiationForm').data('formValidation').isValid()) {
				//alert("not Valid");
			} else {
				$("#init_subh1bid").attr("disabled", true);
				var form = $('#saveH1InitiationForm')[0];
				var data = new FormData(form);
				$.ajax({
					url : "saveH1InitiationDetails", //this is the submit URL
					type : "POST",
					enctype : 'multipart/form-data',
					data : data,
					processData : false,
					contentType : false,
					cache : false,
					success : function(data) {
						 if(data!=null || data!=""){
							   alert(data);
							   document.getElementById("saveH1InitiationForm").reset();
							   $('#saveH1InitiationForm').bootstrapValidator('resetForm', true);
							   $("#init_h1bDiv").hide();
							  // $("#h1btab").trigger('click');
							   // $("#initiateh1b").trigger('click');
							  // $("#candidate").trigger('change');
							   $("#h1btab").trigger('click');
							 /*   $(".collapsibleh1docs").trigger('click'); */
			   	   	         }
			   	   	      else{
			   	   	          window.location.href="loginpage";
			   	   	   	      }
					}
				});
			}
		});
		
		$("#uinit_subh1bid").click(function(event) {
			event.preventDefault();
			$('#updateH1InitiationForm').formValidation('validate');
			if (!$('#updateH1InitiationForm').data('formValidation').isValid()) {
				//alert("not Valid");
			} else {
				$("#uinit_subh1bid").attr("disabled", true);
				var form = $('#updateH1InitiationForm')[0];
				var data = new FormData(form);
				$.ajax({
					url : "updateH1InitiationDetails", //this is the submit URL
					type : "POST",
					enctype : 'multipart/form-data',
					data : data,
					processData : false,
					contentType : false,
					cache : false,
					success : function(data) {
						 if(data!=null || data!=""){
							   alert(data);
							   document.getElementById("updateH1InitiationForm").reset();
							   $('#updateH1InitiationForm').bootstrapValidator('resetForm', true);
							   $("#uinit_h1bDiv").hide();
							  // $("#h1btab").trigger('click');
							   // $("#initiateh1b").trigger('click');
							//   $("#candidate").trigger('change');
							   $("#h1btab").trigger('click');
							 /*   $(".collapsibleh1docs").trigger('click'); */
			   	   	         }
			   	   	      else{
			   	   	          window.location.href="loginpage";
			   	   	   	      }
					}
				});
			}
		});

		$('#init_h1bDiv').hide();
		$("#initiateh1b").click(function(){
			var candidateid = document.getElementById('candidateid').value;
			$('#saveH1InitiationForm').bootstrapValidator('resetForm', true);
			$('#updateH1InitiationForm').bootstrapValidator('resetForm', true);
			document.getElementById("saveH1InitiationForm").reset();
			document.getElementById("updateH1InitiationForm").reset();
			$('#init_h1bDiv,#uinit_h1bDiv').hide();
			$("#uinit_subh1bid").attr("disabled", false);
			$("#kidsId,#spousefilingId,#kidsfilingId,#spousedependId,#kidsdependId").hide();
			$("#ukidsId,#uspousefilingId,#ukidsfilingId,#uspousedependId,#ukidsdependId").hide();
			 $.ajax({
					type : "GET",
					asyn : false,
					contentType : "application/json", // NOT dataType!
					url : "getinitiatebycandidate",
					data : {
						candidateid : candidateid
					    },
					success : function(response) {
					if(response!=""){
						var initList=response.profileList;
						var depinitList=response.dependentprofileList;
						if(response.profile_name!=null){
							alert("For this candidate "+response.profile_name+" is already initiated, do you want to continue.")
							$("#init_subh1bid").attr("disabled", false);
						//	$("#init_subh1bid").attr("title", "");
							$("#kidsId,#spousefilingId,#kidsfilingId,#spousedependId,#kidsdependId").hide();
							$('#init_h1bDiv,#initsubh1bid').show();
						   }else{
							   $("#init_subh1bid").attr("disabled", false);
								$("#kidsId,#spousefilingId,#kidsfilingId,#spousedependId,#kidsdependId").hide();
								$('#init_h1bDiv,#initsubh1bid').show();
						   }
						
						$('#h1initVal,#spouseh4dependVal,#kidsh4dependVal,#uh1initVal,#uspouseh4dependVal,#ukidsh4dependVal').empty();
						$('#h1initVal,#uh1initVal').append($('<option/>').attr("value", '').text("Select Type"));
						for (var i = 0; i < initList.length; i++) {
							$('#h1initVal,#uh1initVal').append($('<option/>').attr("value",initList[i].profile_id).text(initList[i].profile_name));
						   }
						/*$('#spouseh4dependVal,#uspouseh4dependVal').append($('<option/>').attr("value", '').text("Select Type"));
						for (var i = 0; i < depinitList.length; i++) {
							$('#spouseh4dependVal,#uspouseh4dependVal').append($('<option/>').attr("value",depinitList[i].profile_id).text(depinitList[i].profile_name));
						   }
						
						$('#kidsh4dependVal,#ukidsh4dependVal').append($('<option/>').attr("value", '').text("Select Type"));
						for (var i = 0; i < depinitList.length; i++) {
							$('#kidsh4dependVal,#ukidsh4dependVal').append($('<option/>').attr("value",depinitList[i].profile_id).text(depinitList[i].profile_name));
						   }*/
							
					  }
					}
				});
		}); 
			
		$("#initiateh4").click(function(){
			var candidateid = document.getElementById('candidateid').value;
			$('#updateH1InitiationForm').bootstrapValidator('resetForm', true);
			document.getElementById("updateH1InitiationForm").reset();
			$("#uinit_subh1bid").attr("disabled", false);
			$("#ukidsId,#uspousefilingId,#ukidsfilingId,#uspousedependId,#ukidsdependId").hide();
			$('#init_h1bDiv').hide();
			//ukidsfilingId
			 $.ajax({
					type : "GET",
					asyn : false,
					contentType : "application/json", // NOT dataType!
					url : "getinitiatebycandidate",
					data : {
						candidateid : candidateid
					    },
					success : function(response) {
					if(response!=""){
						var initList=response.profileList;
						var depinitList=response.dependentprofileList;
						$('#uh1initVal').text('');
						/* $('#uh1initVal').append($('<option/>').attr("value", '').text("Select Type"));
						for (var i = 0; i < initList.length; i++) {
							$('#h1initVal,#uh1initVal').append($('<option/>').attr("value",initList[i].profile_id).text(initList[i].profile_name));
						} */
						
						/* $('#uspouseh4dependVal').append($('<option/>').attr("value", '').text("Select Type"));
						for (var i = 0; i < depinitList.length; i++) {
							$('#spouseh4dependVal,#uspouseh4dependVal').append($('<option/>').attr("value",depinitList[i].profile_id).text(depinitList[i].profile_name));
						}
						
						$('#ukidsh4dependVal').append($('<option/>').attr("value", '').text("Select Type"));
						for (var i = 0; i < depinitList.length; i++) {
							$('#kidsh4dependVal,#ukidsh4dependVal').append($('<option/>').attr("value",depinitList[i].profile_id).text(depinitList[i].profile_name));
						} */
						$("#marital_status1"). prop("disabled", false);
						if(response.initateInfo!=null){
							var init=response.initateInfo;
							/* var $dropdown4=$("select[name='uh1initVal']");
							$dropdown4.find('option[value="'+init.profile_id+'"]').attr('selected', true); */
							$("#uh1initVal").text(response.profile_name);
							if(init.marital_status!=null){
							if(init.marital_status=='Married'){
								$("#marital_status2"). prop("checked", true);
								$("#marital_status1"). prop("disabled", true);
								$("#ukidsId,#uspousefilingId").show();
							}else{
								$("#marital_status1"). prop("checked", true);
								$("#ukidsId,#uspousefilingId,#uspousedependId").hide();
							  }
							}
							if(init.kids_status!=null){
							if(init.kids_status=='Yes'){
								$("#kids_status1"). prop("checked", true);
								$("#kids_status2"). prop("disabled", true);
								$("#ukidsfilingId").show();
							}else{
								$("#kids_status2"). prop("checked", true);
								$("#ukidsfilingId").hide();
							   }
						    }
							
							if(init.filingforspouse!=null){
								if(init.filingforspouse=='Yes'){
								  $("#uspousefilingId").show();
								  $("#spousefiling1"). prop("checked", true);
								  $("#spousefiling2"). prop("disabled", true);
								   if(init.filingspouse_ead!=null){
										if(init.filingspouse_ead=='Yes'){
										  $("#uspousedependId").show();
										  $("#uspousefilingead1"). prop("checked", true);
										  $("#uspousefilingead2"). prop("disabled", true);
								    	}else{
								    		$("#uspousedependId").show();
										    $("#uspousefilingead2"). prop("checked", true);
									    }
									 }
						    	}else{
						    		//$("#uspousefilingId").hide();
						    		$("#uspousedependId").hide();
								    $("#spousefiling2"). prop("checked", true);
							   }
							}
						 	if(init.filingforkid!=null){
							if(init.filingforkid=='Yes'){
								$("#kidsfiling1"). prop("checked", true);
								$("#kidsfiling2"). prop("disabled", true);
							  }else{
								  $("#kidsfiling2"). prop("checked", true);
							    }
							 }
							$('#uinitiate_id').val(init.initiate_id);
							$('#uinit_h1bDiv,#uinitsubh1bid').show();
						 }
					  }
					}
				});
		}); 
		
		$("#ah1bbtnSubmit").click(function(event) {
			event.preventDefault();
			$('#addh1bDetailsForm').formValidation('validate');
			if (!$('#addh1bDetailsForm').data('formValidation').isValid()) {
				//alert("not Valid");
			} else {
				 var retsn = true;
				  var scarn = [];
				  var afileInputs = document.querySelectorAll(".snfiles");
				  afileInputs.forEach(function(input){
					if(input.files[0]!='undefined'){
				    if(scarn.length == 0){
				    	scarn.push(input.files[0].name);
				    } else {
				        if(scarn.indexOf(input.files[0].name) > -1){
				       // alert("You can not send two or more same files.");
				    	  retsn = false;
				       } else {
				    	  scarn.push(input.files[0].name);
				    	  retsn = true;
				        }
				     }
				   }
				});
				   if (retsn == false)
				     {
					   alert("You can not send two or more same files.");
				       return retsn;        
				     }
				if(retsn==true){
				$("#ah1bbtnSubmit").attr("disabled", true);
				var form = $('#addh1bDetailsForm')[0];
				var data = new FormData(form);
				$.ajax({
					url : "saveh1bdocumentdetails", //this is the submit URL
					type : "POST",
					enctype : 'multipart/form-data',
					data : data,
					processData : false,
					contentType : false,
					cache : false,
					success : function(data) {
						 if(data!=null || data!=""){
							   alert(data);
							   $('#ah1bDocsModal').modal('hide');
							   document.getElementById("addh1bDetailsForm").reset();
							   $("#h1btab").trigger('click');
							 /*   $(".collapsibleh1docs").trigger('click'); */
			   	   	         }
			   	   	      else{
			   	   	          window.location.href="loginpage";
			   	   	   	      }
					}
				});
				}
			}
		});
		var docCount=0;
		var MAX_OPTIONS=15;
		$('#addh1bDetailsForm').formValidation({
		    framework: 'bootstrap',
		    row: {
		        valid: 'field-success',
		        invalid: 'field-error'
		    },
		    fields: {
		    	ah1document: {
		             validators: {
		             	notEmpty: {
		                     message: 'Document is mandatory'
		                 }
		             }
		         },
		         ah1document_subtype: {
		             validators: {
		             	notEmpty: {
		                     message: 'Please Select Document Sub Type'
		                 }
		             }
		         },
		        h1bNumber : {
		        	enabled: false,
		            validators: {
		            	notEmpty: {
		                    message: 'H1B Number is mandatory'
		                },
		                stringLength: {
		                    min: 16,
		                    max: 16,
		                    message: 'H1B Number must be 13 digits'
		                },
		                regexp: {
		                	regexp: /[A-Z]{3}-[0-9]{2}-[0-9]{3}-[0-9]{5}/,
		                    message: 'Please enter valid H1B Number'
		                 }
		            }
		         },
		       h1bstartdate: {
			   			enabled: false,
				            validators: {
				            	notEmpty: {
				                    message: 'Start Date is mandatory'
				                },
				                regexp: {
				                	regexp: /(0[1-9]|1[012]).(0[1-9]|1[0-9]|2[0-9]|3[01]).[0-9]{4}/,
				                    message: 'please enter Start Date in MM/DD/YYYY format'
				                },
				                date: {
				                    format: 'MM/DD/YYYY', 
				                    message: 'Start Date is not a valid, please enter in MM/DD/YYYY format'
				                }
				            }
				        },
			h1benddate: {
				        	enabled: false,
				            validators: {
				            	notEmpty: {
				                    message: 'End Date is mandatory'
				                },
				                regexp: {
				                	regexp: /(0[1-9]|1[012]).(0[1-9]|1[0-9]|2[0-9]|3[01]).[0-9]{4}/,
				                    message: 'please enter End Date in MM/DD/YYYY format'
				                },
				                date: {
				                    format: 'MM/DD/YYYY', 
				                    message: 'End Date is not a valid, please enter in MM/DD/YYYY format'
				                }
				        }
				 },
		   h1bvFromDate: {
		   			enabled: false,
			            validators: {
			            	notEmpty: {
			                    message: 'H1B Valid From Date is mandatory'
			                },
			                regexp: {
			                	regexp: /(0[1-9]|1[012]).(0[1-9]|1[0-9]|2[0-9]|3[01]).[0-9]{4}/,
			                    message: 'please enter H1B Valid From Date in MM/DD/YYYY format'
			                },
			                date: {
			                    format: 'MM/DD/YYYY', 
			                    message: 'H1B Valid From Date is not a valid, please enter in MM/DD/YYYY format'
			                }
			            }
			        },
		  h1bvToDate: {
			        	enabled: false,
			            validators: {
			            	notEmpty: {
			                    message: 'H1B Valid To Date is mandatory'
			                },
			                regexp: {
			                	regexp: /(0[1-9]|1[012]).(0[1-9]|1[0-9]|2[0-9]|3[01]).[0-9]{4}/,
			                    message: 'please enter H1B Valid To Date in MM/DD/YYYY format'
			                },
			                date: {
			                    format: 'MM/DD/YYYY', 
			                    message: 'H1B Valid To Date is not a valid, please enter in MM/DD/YYYY format'
			                }
			            }
			        },
		 i94Number : {
			        	enabled: false,
			            validators: {
			            	notEmpty: {
			                    message: 'I-94 Number is mandatory'
			                },
			                stringLength: {
			                    min: 15,
			                    max: 15,
			                    message: 'I-94 Number must be 15 digits'
			                }/* ,
			                regexp: {
			                	regexp: /[A-Z]{1}-[0-9]{3}-[0-9]{5}-[0-9]{6}/,
			                    message: 'Please enter valid I-94 Number'
			                 } */
			            }
			        },
	    i94vFromDate: {
			   			enabled: false,
				            validators: {
				            	notEmpty: {
				                    message: 'I-94 Valid From Date is mandatory'
				                },
				                regexp: {
				                	regexp: /(0[1-9]|1[012]).(0[1-9]|1[0-9]|2[0-9]|3[01]).[0-9]{4}/,
				                    message: 'please enter I-94 Valid From Date in MM/DD/YYYY format'
				                },
				                date: {
				                    format: 'MM/DD/YYYY', 
				                    message: 'I-94 Valid From Date is not a valid, please enter in MM/DD/YYYY format'
				                }
				            }
				        },
		  i94vToDate: {
				            enabled: false,
				            validators: {
				            	notEmpty: {
				                    message: 'I-94 Valid To Date is mandatory'
				                },
				                regexp: {
				                	regexp: /(0[1-9]|1[012]).(0[1-9]|1[0-9]|2[0-9]|3[01]).[0-9]{4}/,
				                    message: 'please enter I-94 Valid To Date in MM/DD/YYYY format'
				                },
				                date: {
				                    format: 'MM/DD/YYYY', 
				                    message: 'I-94 Valid To Date is not a valid, please enter in MM/DD/YYYY format'
				                }
				            }
				        },
			 'rfeh1document[]':{
		          validators: {
		           	notEmpty: {
		                   message: 'Document is mandatory '
		               }
		           }
		       },       
	         'rfeh1documentInput[]': {
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
	     var $template = $('#rfesuph1Documents'),
	         $clone    = $template
	                         .clone()
	                         .removeAttr('style')
	                         .removeAttr('id')
	                         .attr('data-book-index', docCount)
	                         .attr('class', "rfesuph1Docs")
	                         .insertAfter($template);

	     // Add new field
	     $clone.find('[name="rfeh1document[]"]').attr('class',"form-control snfiles");
	     $('#addh1bDetailsForm')
	     .formValidation('addField', $clone.find('[name="rfeh1documentInput[]"]'))
	     .formValidation('addField', $clone.find('[name="rfeh1document[]"]'));
	     $('#ah1bbtnSubId').show(); 
	 })
	 .on('click', '.removeButton', function() {
	        var $row  = $(this).parents('.form-group'),
	        index = $row.attr('data-book-index');
	        $h1documentInput = $row.find('[name="rfeh1documentInput[]"]');

	     // Remove fields
	     $('#addh1bDetailsForm')
	     	  .formValidation('removeField', $row.find('[name="rfeh1documentInput[' + index + ']"]'))
	          .formValidation('removeField', $row.find('[name="rfeh1document[' + index + ']"]'))
	          .formValidation('removeField', $h1documentInput);
	     // Remove element containing the fields
	     $row.remove();
	      }).on('added.field.fv', function(e, data) {
	           if (data.field === 'rfeh1documentInput[]') {
	               if ($('#addh1bDetailsForm').find(':visible[name="rfeh1documentInput[]"]').length >= MAX_OPTIONS) {
	                   $('#addh1bDetailsForm').find('.addButton').attr('disabled', 'disabled');
	               }
	             }
	        })
	        .on('removed.field.fv', function(e, data) {
	            if (data.field === 'rfeh1documentInput[]') {
	                 if ($('#addh1bDetailsForm').find(':visible[name="rfeh1documentInput[]"]').length <= MAX_OPTIONS) {
	                     $('#addh1bDetailsForm').find('.addButton').removeAttr('disabled');
	                 }
	                if($('#addh1bDetailsForm').find(':visible[name="rfeh1documentInput[]"]').length<=1){
	                	  $('#ah1bbtnSubId').hide(); 
	                 }else{
	                	 $('#ah1bbtnSubId').show(); 
	                 }
	             }
	});
		
		var tableh1bad = $('#immiApprovedDoctable').DataTable({
			 "order": [[0, "desc"]],
		   lengthMenu : [ [ 5,10, 15, -1 ], [ 5,10, 15, "All" ] ],
		   bLengthChange: true,
		   paging: true
		});
		function addH1BDoc(doctypeid,status,docName,intiateid,profile_id,profile_type){
			var candidateid = document.getElementById('candidateid').value;
			$('#h1bHeader').text('');
			 $.ajax({
			    	type : "GET",
			        asyn : false,
			        contentType: "application/json", // NOT dataType!
			        url  :"getimmisubdocumentbydoctypeid",
			        data:{
			        	candidateid : candidateid,
			        	intiateid : intiateid,
			        	document_id : doctypeid,
			        	"profile_id" : profile_id,
			        	"benficiary_type" : profile_type,
			        	},
			        success  : function(response){
			        	var subDocTypeList=response.subdocttypeListAdmin;
			        	var docList=response.ah1docList;
			        	//alert(subDocTypeList.length);
			        	$('#ah1document_subtype').empty();
				        $('#ah1document_subtype').append($('<option/>').attr("value", '').text("Select Document Sub Type"));
			        	for(var i=0;i<subDocTypeList.length;i++)
			     	       {
			        			$('#ah1document_subtype').append($('<option/>').attr("value", subDocTypeList[i].subdocumenttype_id).text(subDocTypeList[i].document_type));
			               }
			        	
			        	$('#ah1doctypeid').val(doctypeid);
			        	$('#ah1initiated').val(intiateid);
			        	$('#ah1dccandidateid').val(candidateid);
			        	
			        //	$('#ah1queid').val(candidateid);
			        	$('#ah1profileid').val(profile_id);
			        	$('#ah1bentype').val(profile_type);
			        	
			        	//approvedH1DocsId
			        	 if (tableh1bad) tableh1bad.clear();
			        	   $('#immiApprovedDoctable').dataTable().fnClearTable();
			        	 if(docList.length>0){
			        			$('#approvedH1DocsId').show();
			        			var haction;
			            	  for(var i=0;i<docList.length;i++){
			            		   // alert(response[i].beneficiary_Name);
			            			var fname=docList[i].file_name;
				 		          	var encoded = encodeURIComponent(fname);
				 		          	var inputval="";
				    				if(docList[i].inputval!=null){
				    					inputval=docList[i].inputval;
				    				 }
				    				
				    				 if(docList[i].subdoctypename=='H1B Approval Copy'){
				    					 haction='<a class="btn btn-success btn-sm" data-toggle="modal" onclick="editH1BAprovalDoc(\''+docList[i].document_id+'\',\''+docList[i].subdoctypename+'\',\''+docList[i].file_name+'\',\''+docList[i].h1bId+'\');" style="letter-spacing: 1px; background-color: #15aabf; color: white;"><i class="fa fa-edit"></i></a>'; 
				    				 }else if(docList[i].subdoctypename=='H1B RFE Copy'){
				    					// data-toggle="modal" data-target="#edith1bDocsModal"
				    					 haction='<a class="btn btn-success btn-sm" data-toggle="modal" onclick="editRFEDoc(\''+docList[i].document_id+'\',\''+docList[i].subdoctypename+'\',\''+inputval+'\',\''+docList[i].file_name+'\');" style="letter-spacing: 1px; background-color: #15aabf; color: white;"><i class="fa fa-edit"></i></a>';
				    				 }else if(docList[i].subdoctypename=='Receipt copy'){
				    					 haction='<a class="btn btn-success btn-sm" data-toggle="modal" onclick="editH1BDoc(\''+docList[i].document_id+'\',\''+docList[i].subdoctypename+'\',\''+docList[i].file_name+'\');" style="letter-spacing: 1px; background-color: #15aabf; color: white;"><i class="fa fa-edit"></i></a>'; 
				    				 }else{
				    					 haction='<a class="btn btn-success btn-sm" data-toggle="modal" " onclick="editH1BDoc(\''+docList[i].document_id+'\',\''+docList[i].subdoctypename+'\',\''+docList[i].file_name+'\');" style="letter-spacing: 1px; background-color: #15aabf; color: white;"><i class="fa fa-edit"></i></a>';  
				    				 }
				    				tableh1bad.row.add([
			                    	''+docList[i].document_id+'',
			                    	''+docList[i].beneficiary_Name+'',
			                    	'<a href="downloadh1bdocs?filename='+encoded+'&candidate_id='+candidateid+'" style="color:blue">'+docList[i].file_name+'</a>',
			                    	//'<span onclick="downloadDocCust('+response[i].file_name+','+response[i].filetype+','+custName+')" style="color: blue">'+response[i].file_name+'</span>',
			                    	//''+response[i].doctypename+'',
			                    	''+docList[i].subdoctypename+'',
			                    	''+inputval+'',
			                    	''+docList[i].submit_date+'',
									''+docList[i].posted_by+'',
									''+haction+'',
			                     ]).draw();                  
			                   }
			        	  	}else{
			        	  		$('#approvedH1DocsId').hide();
			        	  	}
			        /* 	if(docList!=""){
			        	    if (tableh1bd)
			        		tableh1bd.clear();
			    			for(var i=0;i<docList.length;i++){
			    				var encoded = encodeURIComponent(docList[i].file_name);
			    				var inputval="";
			    				if(docList[i].inputval!=null){
			    					inputval=docList[i].inputval;
			    				}else{
			    					inputval="";
			    				}
			    				tableh1bd.row.add([
			    				'<a href="downloadh1bdocs?filename='+encoded+'&candidate_id='+candidateid+'" style="color:blue">'+ docList[i].file_name+'</a>',
			    				'' + docList[i].doctypename	+ '',
			    				'' + docList[i].subdocname	+ '',
			    				'' +inputval+ '',
			    				'' + docList[i].submitted_on + '',
			    				'' + docList[i].empName	+ '',
			    				'<a class="btn btn-secondary btn-sm addButton" data-toggle="modal" disabled onclick="editH1BDocument(\''+docList[i].document_id+'\',\''+chklistid+'\',\''+docName+'\',\''+docList[i].file_name+'\',\''+candidateid+'\',\''+inputval+'\');" style="letter-spacing: 1px; background-color: #15aabf; color: white;"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;<a class="btn btn-secondary btn-sm addButton" data-toggle="modal" onclick="viewDocHistory(\''+docList[i].document_id+'\',\''+candidateid+'\',\''+docName+'\');"  title="View Document History" style="letter-spacing: 1px; background-color: #15aabf; color: white;"><i class="fa fa-history"></i></a>', ]).draw();
			    			}
			    			$('#h1docslist').show();
			        	}else{
			        		$('#h1docslist').hide(); 
			        	  } 
			        	$('#h1docsForm')[0].reset();
			        	$('#chklistId').val(chklistid);
			        	$('#h1candidateid').val(candidateid);$('#h1clientid').val(clientid);
			        	$('#h1docsaveid').hide();
			        	//$('#h1Documents').empty();*/
			        	 
			        	$('#approvedh1b').hide();
			        	$('#h1bHeader').text(docName +" Details");
			        	$('#addh1bDetailsForm').bootstrapValidator('resetForm', true);
			        	$("#ah1bbtnSubmit").attr("disabled", false);
				        $('#ah1bDocsModal').modal('show');
			            }
			  });
		}
		function editRFEDoc(documentid,subdoctypename,inputval,filename){
			$('#rfeh1docid,#h1edinputval').val('');
			$('#h1edfileSpan,#eh1Header').text('');
			$('#rfeh1docid').val(documentid);
			$('#h1edinputval').val(inputval);
			$('#h1edfileSpan').text(filename);
			$('#eh1Header').text(subdoctypename +" Details");
			$('#edith1bDocsModal').modal('show');
			
		 }
		function editH1BDoc(documentid,subdoctypename,filename){
			$('#eh1documentid').val('');
			$('#eh1documentid').val(documentid);
			$('#eh1edfileSpan').text(filename);
			$('#eh1bHeader').text(subdoctypename +" Details");
			$('#editdocmodal').modal('show');
		}
		
		$('#h1updateuserdocformid').formValidation({
		    framework: 'bootstrap',
		    row: {
		        valid: 'field-success',
		        invalid: 'field-error'
		    },
		   
		    fields: {
		    	h1edinputval:{
			          validators: {
			           	notEmpty: {
			                   message: 'Please enter Document Name '
			               }
			           }
			       },
			       h1filenameid: {
				          validators: {
				           	notEmpty: {
				                   message: 'Please select Document '
				               }
				           }
				       }
		      }

		});

		$("#h1updatebuttonid").click(function(event) {
			event.preventDefault();
			$('#h1updateuserdocformid').formValidation('validate');
			if (!$('#h1updateuserdocformid').data('formValidation').isValid()) {
				// alert("No files selected");
			} else {
				var form = $('#h1updateuserdocformid')[0];
				var data = new FormData(form);
				$.ajax({
					url : "updateh1rfedoc", //this is the submit URL
					type : "POST",
					enctype : 'multipart/form-data',
					data : data,
					processData : false,
					contentType : false,
					cache : false,
					success : function(response) {
						 if(response!=null || response!=""){
							 alert(response);
							 $('#edith1bDocsModal,#ah1bDocsModal').modal('hide');
							// document.getElementById("h1updateuserdocformid").reset();
							 $("#h1btab").trigger('click');
			   	   	         }
			   	   	      else{
			   	   	          window.location.href="loginpage";
			   	   	   	      }
					}
				});
			}
		});
		$('#ah1bDocsModal').on('hide.bs.modal', function()
				{
			 		$('#edith1bDocsModal,#editdocmodal').modal('hide');
				});
		$('#edith1bDocsModal').on('hide.bs.modal', function()
		{
			$("#h1updatebuttonid").attr("disabled", false);
			$('#h1updateuserdocformid').bootstrapValidator('resetForm', true);
		});
		$('#editdocmodal').on('hide.bs.modal', function()
		{
			$("#updatedocid").attr("disabled", false);
			$('#updatedocformid').bootstrapValidator('resetForm', true);
		});
		$('#edith1bApprovedDocModal').on('hidden.bs.modal', function()
		{
			/* $('#eh1bvFromDate,#eh1bvToDate,#ei94vFromDate,#ei94vToDate').val('').datepicker('update');
			 $("#eh1bvFromDate,#eh1bvToDate,#ei94vFromDate,#ei94vToDate").datepicker('setStartDate', null);
			 $("#eh1bvFromDate,#eh1bvToDate,#ei94vFromDate,#ei94vToDate").datepicker('setEndDate', null);*/
		   // $('#apph1bDetailsForm').bootstrapValidator('resetForm', true);
    	   // $("#apph1bbtnUpdate").attr("disabled", false);
		 });
		
		/*function ValidateSizeFile(file) {
			var candidateid = document.getElementById('candidateid').value;
		  	if($(file).val()!=""){
		  		 var FileSize = file.files[0].size/1024/1024;
				 var filename = file.files[0].name;
				 var type = $(file).val().split(".").pop().toLowerCase();
			        if (FileSize > 50) {
			            alert('File size exceeds 50 MB');
			            $(file).val('');
			        } else {
			        	if(type=="pdf" || type=="png" || type=="docx" || type=="doc"|| type=="jpg"|| type=="jpeg" || type=="xlsx" || type=="xls" || type=="pptx" || type=="txt" || type=="tiff"){
			        	$.ajax({
			        		type : "GET",
			 		        asyn : false,
			 		        contentType: "application/json",
			 		        url : "checkSelectedFileNameById",
							data : {
								"filename":filename,
					        	"candidateid" : candidateid,
							},
							success : function(response) {
							  if(response=='Uploaded'){
									alert("This file is already "+response + ". Please check");
									document.getElementById("updatedocformid").reset();
									$(file).val('');
								}else{
									     $('#msafileId').show();
									    $('#msafileName').text(filename); 
									 //   $('#btnSubmit').attr('disabled',false);
								}
							},
							error : function() {
							}
						});
			        	}else{
			    	    	 $(file).val('');
			    	    	 alert("Please upload file with docx, doc, png, jpg, jpeg, xls, xlsx, pptx, txt, tiff or pdf file format");
			    		    }
			        }
		  	}else{
		  		 $('#msafileName').text('');
		  		$('#msafileId').hide(); 
		  		//$('#btnSubmit').attr('disabled',false);
		  	}
		} */
		$("#updatedocid").click(function(event) {
				event.preventDefault();
				$('#updatedocformid').formValidation('validate');
				if (!$('#updatedocformid').data('formValidation').isValid()) {
					// alert("No files selected");
				} else {
					// alert("files selected");
					//$('#uploaddocmodal').hide();
					var form = $('#updatedocformid')[0];
					var data = new FormData(form);
					$.ajax({
						url : "updateh1bdoc", //this is the submit URL
						type : "POST",
						enctype : 'multipart/form-data',
						data : data,
						processData : false,
						contentType : false,
						cache : false,
						success : function(data) {
							 if(data!=null || data!=""){
								 alert(data);
								 $('#editdocmodal,#ah1bDocsModal').modal('hide');
								 document.getElementById("updatedocformid").reset();
								 $("#h1btab").trigger('click');
				   	   	         }
				   	   	      else{
				   	   	          window.location.href="loginpage";
				   	   	   	      }
						}
					});
				}
		});
		
		$('#updatedocformid').formValidation({
		    framework: 'bootstrap',
		    row: {
		        valid: 'field-success',
		        invalid: 'field-error'
		    },
		    fields: {
		    	 eh1bfilename:{
			          validators: {
			           	notEmpty: {
			                   message: 'Please select Document  '
			               }
			           }
			       }
		      }
		});
		
	  function editH1BAprovalDoc(documentid,subdoctypename,filename,h1bId){
			var candidateid = document.getElementById('candidateid').value;
			 $.ajax({
			    	type : "GET",
			        asyn : false,
			        contentType: "application/json", // NOT dataType!
			        url  :"geth1bDetalsById",
			        data:{
			        	"h1bId" : h1bId,
			        	},
			        success  : function(response){
			        	$('#apph1documentid,#apph1id').val('');
						$('#appfileSpan,#apph1bHeader').text('');
						$('#apph1id').val(h1bId);
						$('#apph1documentid').val(documentid);
						$('#eh1bNumber').val(response.receipt_number);
						$('#eh1bvFromDate').val(response.h1b_fromdate);
						$('#eh1bvToDate').val(response.h1b_todate);
						$('#ei94Number').val(response.i94_number);
						$('#ei94vFromDate').val(response.i94_fromdate);
						$('#ei94vToDate').val(response.i94_todate);
						$('#appfileSpan').text(filename);
			        	$('#apph1bHeader').text(subdoctypename +" Details");
			        	//$('#apprh1bDetailsForm').bootstrapValidator('resetForm', true);
			        	//$("#apph1bbtnSubmit").attr("disabled", false);
				        $('#edith1bApprovedDocModal').modal('show');
			            }
			  });
		   }
	  $('#apph1bDetailsForm').formValidation({
		    framework: 'bootstrap',
		    row: {
		        valid: 'field-success',
		        invalid: 'field-error'
		    },
		    fields: {
		    	apph1document: {
		             validators: {
		             	isEmpty: {
		                     message: 'Document is mandatory'
		                 }
		             }
		         },
		        eh1bNumber : {
		            validators: {
		            	notEmpty: {
		                    message: 'H1B Number is mandatory'
		                },
		                stringLength: {
		                    min: 16,
		                    max: 16,
		                    message: 'H1B Number must be 13 digits'
		                },
		                regexp: {
		                	regexp: /[A-Z]{3}-[0-9]{2}-[0-9]{3}-[0-9]{5}/,
		                    message: 'Please enter valid H1B Number'
		                 }
		            }
		         },
		   eh1bvFromDate: {
			            validators: {
			            	notEmpty: {
			                    message: 'H1B Valid From Date is mandatory'
			                },
			                regexp: {
			                	regexp: /(0[1-9]|1[012]).(0[1-9]|1[0-9]|2[0-9]|3[01]).[0-9]{4}/,
			                    message: 'please enter H1B Valid From Date in MM/DD/YYYY format'
			                },
			                date: {
			                    format: 'MM/DD/YYYY', 
			                    message: 'H1B Valid From Date is not a valid, please enter in MM/DD/YYYY format'
			                }
			            }
			        },
		  eh1bvToDate: {
			            validators: {
			            	notEmpty: {
			                    message: 'H1B Valid To Date is mandatory'
			                },
			                regexp: {
			                	regexp: /(0[1-9]|1[012]).(0[1-9]|1[0-9]|2[0-9]|3[01]).[0-9]{4}/,
			                    message: 'please enter H1B Valid To Date in MM/DD/YYYY format'
			                },
			                date: {
			                    format: 'MM/DD/YYYY', 
			                    message: 'H1B Valid To Date is not a valid, please enter in MM/DD/YYYY format'
			                }
			            }
			        },
		 ei94Number : {
			            validators: {
			            	notEmpty: {
			                    message: 'I-94 Number is mandatory'
			                },
			                stringLength: {
			                    min: 15,
			                    max: 15,
			                    message: 'I-94 Number must be 15 digits'
			                }/* ,
			                regexp: {
			                	regexp: /[A-Z]{1}-[0-9]{3}-[0-9]{5}-[0-9]{6}/,
			                    message: 'Please enter valid I-94 Number'
			                 } */
			            }
			        },
	    ei94vFromDate: {
				            validators: {
				            	notEmpty: {
				                    message: 'I-94 Valid From Date is mandatory'
				                },
				                regexp: {
				                	regexp: /(0[1-9]|1[012]).(0[1-9]|1[0-9]|2[0-9]|3[01]).[0-9]{4}/,
				                    message: 'please enter I-94 Valid From Date in MM/DD/YYYY format'
				                },
				                date: {
				                    format: 'MM/DD/YYYY', 
				                    message: 'I-94 Valid From Date is not a valid, please enter in MM/DD/YYYY format'
				                }
				            }
				        },
		  ei94vToDate: {
				            validators: {
				            	notEmpty: {
				                    message: 'I-94 Valid To Date is mandatory'
				                },
				                regexp: {
				                	regexp: /(0[1-9]|1[012]).(0[1-9]|1[0-9]|2[0-9]|3[01]).[0-9]{4}/,
				                    message: 'please enter I-94 Valid To Date in MM/DD/YYYY format'
				                },
				                date: {
				                    format: 'MM/DD/YYYY', 
				                    message: 'I-94 Valid To Date is not a valid, please enter in MM/DD/YYYY format'
				                }
				            }
				        }
	      }
	});
	  
		    $("#ah1document_subtype").change(function() {
			var hsubdocname=$("#ah1document_subtype option:selected").text();
			$('#ah1subdocname').val(hsubdocname);
			 $('#rfesupaddlbl').text('');
			 var hsubdoctypeid=$('#ah1document_subtype').val();
			var hdoctypeid=$('#ah1doctypeid').val();
			var hintiateid=$('#ah1initiated').val();
			var hcandidateid=$('#ah1dccandidateid').val();
			var hprofile_id=$('#ah1profileid').val();
			var hprofile_type=$('#ah1bentype').val();
			//alert(hintiateid);
			 var hsubdocname1 = document.getElementById('ah1document_subtype').value;
			 $('.rfesuph1Docs').empty();
			 $('#rfesupadd,#approvedh1b,#ah1docId,#ah1bbtnSubId').hide();
		    if(hsubdocname!=""){
		    //	alert(hsubdocname);
			if(hsubdocname=='H1B Approval Copy'){
				 // $('.rfesuph1Docs').empty();
				 // $('#rfesupadd').hide();
				 // $('#approvedh1b,#ah1docId,#ah1bbtnSubId').hide();
				    $.ajax({
				    	type : "GET",
				        asyn : false,
				        contentType: "application/json", // NOT dataType!
				        url  :"checksubdocsbyid",
				        data : {
				        	subdocid : hsubdoctypeid,
				        	candidateid : hcandidateid,
				        	initiateid : hintiateid,
				        	document_id : hdoctypeid,
				        	"profile_id" : hprofile_id,
				        	"benficiary_type" : hprofile_type,
						},
				        success  : function(response){
				        	 		if(response=='Exists'){
				        	 			alert("You have already uploaded details for selected "+hsubdocname);
				        	 			//$("#lcabtnSubmit").attr("disabled",true);
				        	 		}else{
				        	 			  $('#approvedh1b,#ah1docId,#ah1bbtnSubId').show();
				        				  //$('#rfesuph1Documents').empty();
				        				//$('#hstd,#hend').hide();
				        				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','h1bNumber',true);
				        				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','h1bvFromDate',true);
				        				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','h1bvToDate',true);
				        				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','i94Number',true);
				        				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','i94vFromDate',true);
				        				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','i94vToDate',true);
				        	 			  //$("#lcabtnSubmit").attr("disabled",false);
				        	 		}
				              }
					  });
			}else if(hsubdocname=='H1B RFE Copy'){
				      $('#rfesupadd').show();
				      $('#rfesupaddlbl').text('RFE Supporting Documents');
				      $('#approvedh1b,#ah1docId,#ah1bbtnSubId').hide();
					//$('#hstd,#hend').hide();
				      $('#addh1bDetailsForm').formValidation('enableFieldValidators','h1bNumber',false);
					  $('#addh1bDetailsForm').formValidation('enableFieldValidators','h1bvFromDate',false);
					  $('#addh1bDetailsForm').formValidation('enableFieldValidators','h1bvToDate',false);
					  $('#addh1bDetailsForm').formValidation('enableFieldValidators','i94Number',false);
					  $('#addh1bDetailsForm').formValidation('enableFieldValidators','i94vFromDate',false);
					  $('#addh1bDetailsForm').formValidation('enableFieldValidators','i94vToDate',false);
				}			
			else if(hsubdocname=='Receipt copy'){
				 // $('#rfesuph1Documents').empty();
				//$('.rfesuph1Docs').empty();
				// $('#approvedh1b,#rfesupadd,#ah1bbtnSubId').hide();
				 $.ajax({
				    	type : "GET",
				        asyn : false,
				        contentType: "application/json", // NOT dataType!
				        url  :"checksubdocsbyid",
				        data : {
				        	subdocid : hsubdoctypeid,
				        	candidateid : hcandidateid,
				        	initiateid : hintiateid,
				        	document_id : hdoctypeid,
				        	"profile_id" : hprofile_id,
				        	"benficiary_type" : hprofile_type,
						},
				        success  : function(response){
				        	 		if(response=='Exists'){
				        	 			alert("You have already uploaded details for selected "+hsubdocname);
				        	 			//$("#lcabtnSubmit").attr("disabled",true);
				        	 		}else{
				  $('#ah1docId,#ah1bbtnSubId').show();
				 // $('#hstd,#hend').show();
				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','h1bNumber',false);
				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','h1bvFromDate',false);
				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','h1bvToDate',false);
				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','i94Number',false);
				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','i94vFromDate',false);
				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','i94vToDate',false);
				        	 		}
			              }
				  });
			 }else{
				  $('#rfesupadd').show();
				  $('#rfesupaddlbl').text(''+hsubdocname+' Supporting Documents');
			      $('#approvedh1b,#ah1docId,#ah1bbtnSubId').hide();
			      $('#addh1bDetailsForm').formValidation('enableFieldValidators','h1bNumber',false);
				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','h1bvFromDate',false);
				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','h1bvToDate',false);
				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','i94Number',false);
				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','i94vFromDate',false);
				  $('#addh1bDetailsForm').formValidation('enableFieldValidators','i94vToDate',false);
			 }
		   }else{
			   $('.rfesuph1Docs').empty();
			   $('#rfesupadd,#approvedh1b,#ah1docId,#ah1bbtnSubId').hide();
		   }
		});
		    
		    
		$('#ah1bDocsModal').on('shown.bs.modal', function (){
			    $('.rfesuph1Docs').empty();
			    $('#ah1docId').hide();
			    $('#approvedh1b,#rfesupadd,#ah1bbtnSubId').hide();
		    	$('#addh1bDetailsForm').bootstrapValidator('resetForm', true);
		});
		
		
		$("#apph1bbtnUpdate").click(function(event) {
			event.preventDefault();
			$('#apph1bDetailsForm').formValidation('validate');
			if (!$('#apph1bDetailsForm').data('formValidation').isValid()) {
				//alert("not Valid");
			} else {
				$("#apph1bbtnUpdate").attr("disabled", true);
				var form = $('#apph1bDetailsForm')[0];
				var data = new FormData(form);
				$.ajax({
					url : "updateh1bapproveddocumentdetails", //this is the submit URL
					type : "POST",
					enctype : 'multipart/form-data',
					data : data,
					processData : false,
					contentType : false,
					cache : false,
					success : function(data) {
						 if(data!=null || data!=""){
							   alert(data);
							   $('#edith1bApprovedDocModal,#ah1bDocsModal').modal('hide');
							   $('#apph1bDetailsForm').bootstrapValidator('resetForm', true);
							   document.getElementById("apph1bDetailsForm").reset();
							   $("#h1btab").trigger('click');
							 /*   $(".collapsibleh1docs").trigger('click'); */
			   	   	         }
			   	   	      else{
			   	   	          window.location.href="loginpage";
			   	   	   	      }
					}
				});
			}
		});
		
		$('#edith1bApprovedDocModal').on('shown.bs.modal', function(){
			$("#apph1bbtnUpdate").attr("disabled", false);
			$("#eh1bvFromDate").datepicker({
			}).on('changeDate', function(selected) {
				 var minDate = new Date(selected.date.valueOf());
				 $('#eh1bvToDate').datepicker('setStartDate', minDate);
			    $('#apph1bDetailsForm').formValidation('revalidateField', 'eh1bvFromDate');
			});	
			$("#eh1bvToDate").datepicker({
			}).on('changeDate', function(selected) {
				 var minDate = new Date(selected.date.valueOf());
				 $('#eh1bvFromDate').datepicker('setEndDate', minDate);
			    $('#apph1bDetailsForm').formValidation('revalidateField', 'eh1bvToDate');
			});	
			$("#ei94vFromDate").datepicker({
			}).on('changeDate', function(selected) {
				 var minDate = new Date(selected.date.valueOf());
				 $('#ei94vToDate').datepicker('setStartDate', minDate);
			    $('#apph1bDetailsForm').formValidation('revalidateField', 'ei94vFromDate');
			});	
			$("#ei94vToDate").datepicker({
			}).on('changeDate', function(selected) {
				 var minDate = new Date(selected.date.valueOf());
				 $('#ei94vFromDate').datepicker('setEndDate', minDate);
			    $('#apph1bDetailsForm').formValidation('revalidateField', 'ei94vToDate');
			});	
	   });
		
		$("#h1bvFromDate").datepicker({
			autoclose : true
		}).on('changeDate', function(selected) {
			 var minDate = new Date(selected.date.valueOf());
			 $('#h1bvToDate').datepicker('setStartDate', minDate);
		    $('#addh1bDetailsForm').formValidation('revalidateField', 'h1bvFromDate');
		});
		/*$('#h1bvFromDate').on('show', function(e){
			//alert("show");
		    if (e.date ) {
		         $(this).data('stickyDate', e.date);
		    }
		    else {
		         $(this).data('stickyDate', null);
		    }
		});

		$('#h1bvFromDate').on('hide', function(e){
		    var stickyDate = $(this).data('stickyDate');
		    alert("hide"+e.date);
		    if (!e.date && stickyDate ) {
		        $(this).datepicker('setDate', stickyDate);
		        $(this).data('stickyDate', null);
		    }
		});
		*/
		$("#h1bvToDate").datepicker({
		}).on('changeDate', function(selected) {
			 var minDate = new Date(selected.date.valueOf());
			// alert(minDate);
			 $('#h1bvFromDate').datepicker('setEndDate', minDate);
		    $('#addh1bDetailsForm').formValidation('revalidateField', 'h1bvToDate');
		});	
		$("#i94vFromDate").datepicker({
		}).on('changeDate', function(selected) {
			 var minDate = new Date(selected.date.valueOf());
			 $('#i94vToDate').datepicker('setStartDate', minDate);
		    $('#addh1bDetailsForm').formValidation('revalidateField', 'i94vFromDate');
		});	
		$("#i94vToDate").datepicker({
		}).on('changeDate', function(selected) {
			 var minDate = new Date(selected.date.valueOf());
			 $('#i94vFromDate').datepicker('setEndDate', minDate);
		    $('#addh1bDetailsForm').formValidation('revalidateField', 'i94vToDate');
		});	
		$("#h1bstartdate").datepicker({
		}).on('changeDate', function(selected) {
		    $('#addh1bDetailsForm').formValidation('revalidateField', 'h1bstartdate');
		});	
		$("#h1benddate").datepicker({
		}).on('changeDate', function(selected) {
		    $('#addh1bDetailsForm').formValidation('revalidateField', 'h1benddate');
		});
		$('input[name="h1bNumber"]').mask('AAA-99-999-99999');	
		$('input[name="eh1bNumber"]').mask('AAA-99-999-99999');	
		function loadStatus1() {
			var slist = [ "Approved", "Rejected" ];
			$('#candocapprstatus').empty();
			$('#candocapprstatus').append(
					$('<option/>').attr("value", '').text("Select Status"));
			for (var i = 0; i < slist.length; i++) {
				$('#candocapprstatus').append(
						$('<option/>').attr("value", slist[i]).text(slist[i]));
			}
		}
		var tableh1bcd = $('#immiCandDoctable').DataTable({
			 "order": [[0, "desc"]],
		   lengthMenu : [ [ 5,10, 15, -1 ], [ 5,10, 15, "All" ] ],
		   bLengthChange: true,
		   paging: true
		});
		function updateCandDocVerifierStauts(docTypeId,docTypeName,initiateid,profile_id,profile_type,apprstatus,is_same){
			var candidateid = document.getElementById('candidateid').value;
			 // alert(initiateid);
			$("#doc_subtype").val('').trigger('change')
			$('#h1bHistory1').text('')
			 $('#savesubmitid').show();
			 $('#catname').text(docTypeName);
			 $('#docid').val(docTypeId);
			 $('#initiateid1').val(initiateid);  
			 $("#h1binitiateid1").val(initiateid);
			 $('#candidateId2').val(candidateid);
			 $('#profile_id').val(profile_id);  
			 $('#benf_type').val(profile_type);
			// var is_same=$('#doc_is_same'+document_id).val();
			$.ajax({
		    	type : "get",
		        url  : "getCandidateDocumentsByIds",
		        contentType:"application/json",
		        cache : false,
		        async : false,
		        data:{
		        	"docTypeId":docTypeId,
		        	"initiateid":initiateid,
		        	"candidateid" : candidateid,
		        	"profile_id" : profile_id,
		        	"profile_type" : profile_type,
		        	},
		        success  : function(response){
		        	var dochistoryList=response.dochistoryList;
		        	var docList=response.candidateList;
		        	var docsubdocList=response.candidatedocTypeList;
		        	var subdocttypeList=response.subDocumentTypeList
		        	   if (tableh1bcd) tableh1bcd.clear();
		        	   
		        	   $('#immiCandDoctable').dataTable().fnClearTable();
		        	  	if(dochistoryList!=""){
		            	  for(var i=0;i<dochistoryList.length;i++){
		            		   // alert(response[i].beneficiary_Name);
		            			var fname=dochistoryList[i].file_name;
			 		          	var encoded = encodeURIComponent(fname);
			 		          	var inputval="";
			    				if(dochistoryList[i].inputval!=null){
			    					inputval=dochistoryList[i].inputval;
			    				}
			    				tableh1bcd.row.add([
		                    	''+dochistoryList[i].document_id+'',
		                    	''+dochistoryList[i].beneficiary_Name+'',
		                    	'<a href="downloadh1bdocs?filename='+encoded+'&candidate_id='+candidateid+'" style="color:blue">'+dochistoryList[i].file_name+'</a>',
		                    	//'<span onclick="downloadDocCust('+response[i].file_name+','+response[i].filetype+','+custName+')" style="color: blue">'+response[i].file_name+'</span>',
		                    	//''+response[i].doctypename+'',
		                    	''+dochistoryList[i].subdoctypename+'',
		                    	''+inputval+'',
		                    	''+dochistoryList[i].submit_date+'',
								''+dochistoryList[i].posted_by+''
		                     ]).draw();                  
		                   }
		          	}else{
		          	  // alert("No data found");
		             } 
		        		if(docsubdocList.length>0){
			        		$('#savesubmitid').hide();
			        		$('#docsubdoclength').val(docsubdocList.length);
			        		$('#OtherBtnDiv').empty(); 
		        		}else{
		        			$('#OtherBtnDiv').empty();    
		        		}
		        	  	
		        	  	$('#subdoclistdiv').hide();
					 	$('#OtherBtnDiv,#listAbtnDiv').empty();
					 	$('#savesubmitid').hide();
		        	 // 	alert(is_same+"*****"+docsubdocList.length+"///"+subdocttypeList.length);
		        	  	if(is_same<2){
		        	  	//	alert(is_same+"***if**"+docsubdocList.length+"///"+subdocttypeList.length);
						 	//	$('#edocumentsTable1_wrapper').show();
						 	$('#subdoclistdiv').hide();
						 	$('#OtherBtnDiv').empty();
						 	$('#savesubmitid').hide();
						 	$('#OtherBtnDiv').append("<center><label>Add Documents</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-primary btn-sm' id='btnid' onclick='OtherAdditional()'><i class='fa fa-plus'></i></button></button>");
					 	}else{
					 	//	alert(is_same+"***else**"+docsubdocList.length+"///"+subdocttypeList.length);
					 		//alert(docsubdocList.length+"*************"+docList.length);
					 		 if(docsubdocList.length==0){
					 			$('#OtherBtnDiv').empty();  
				    	 	  for(var i=0;i<docList.length;i++){
				    	        var documenttype_id=docList[i].document_id;	
				    	 		var subcategory=docList[i].subcatName;	
			    				var initiateid=docList[i].initiate_id;	
			    				var subdocid=docList[i].subcategory_id;	
			    				var candid=docList[i].candidateId;	
			    				var h1b_checklist_id=docList[i].h1b_checklist_id;
				    			$('#OtherBtnDiv').append("<input type='hidden' id='subcategory"+h1b_checklist_id+"' name='subcategory' value='"+subcategory+"'><input type='hidden' id='subdocumentid' name='subdocumentid[]' value='"+subdocid+"'><input type='hidden' id='h1b_checklist_id' name='h1b_checklist_id[]' value='"+h1b_checklist_id+"'><div class=' form-group row'><label class='col-sm-4 col-form-label'><span style='color: black;'>"+docList[i].subcatName+"</span></label><input type='file' class='scnfiles' name='filename1[]' id='filename1[]' onchange='ValidateFileSize(this)' required></div></div>");
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
			    	 	 
		            if(dochistoryList.length>0){
			            	$("#cdverifierBtnId").attr("disabled", false);
			            	$("#h1bchdivId").show();
			            	$('#h1bchistoryId').attr('onclick', 'viewCommentHistory(' + docTypeId + ',' + initiateid + ',' + candidateid + ',' + profile_id + ',\''+profile_type+'\')');
			           }else{
			        		$("#h1bchdivId").hide();
			        		$("#cdverifierBtnId").attr("disabled", true);
			           }
		        var title="";
		    	
		    	$("#h1bcandicomments").val('');
				loadStatus1();
				//alert(apprstatustd+apprstatus);
				var apprstatustd = $("#h1bapprstatustd"+docTypeId).val();
				var $dropdown = $("select[name='candocapprstatus']");
				//alert(apprstatus);
				$dropdown.find('option[value="' + apprstatus + '"]').attr('selected',true);
				$("#h1bcandidateId").val(candidateid);
				$("#h1bdoctypeid").val(docTypeId);
				$("#h1bprofile_id").val(profile_id); 
				$("#h1bbenf_type").val(profile_type);
				$('#h1bHistory1').text(docTypeName+" Details");
				$("#savesubmitid").attr("disabled", false);
		        $('#CandDocVerifierStautsModal').modal('show');
		      },
		  //	''+response[i].docStatus+''
		     error: function(){  
		     alert('Error while request..');
		    }
		  });
		 }
		$("#savesubmitid").click(function(event) {
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
			     
				/*if($('#doc_subtype').val()!=""){
						alert('Please Select Value');
				 } */ 
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
			     
			     var retsc = true;
				  var scnarr = [];
				  var fileInputs = document.querySelectorAll(".scnfiles");
				   fileInputs.forEach(function(input){
				    if(scnarr.length == 0){
				    	scnarr.push(input.files[0].name);
				    } else {
				      if(scnarr.indexOf(input.files[0].name) > -1){
				       // alert("You can not send two or more same files.");
				    	  retsc = false;
				      } else {
				    	  scnarr.push(input.files[0].name);
				    	  retsc = true;
				      }
				    }
				});
				   
				   if (retsc == false)
				     {
					   alert("You can not send two or more same files.");
				       return retsc;        
				     }
			     
			     //alert(ret2);
			   	 if(ret == true && ret1 == true && ret2 == true && retsc == true){	 
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
		});
		/*$("#saveimmidocumentbydocId").validate({
			rules: {
			'filename[]' : {
			required: true,
			},
			'otherAddtnlinput[]': {
			required: true,
			}
			}
			});
		
		function validateForm() {
			  var x = document.forms["saveimmidocumentbydocId"]["filename[]"].value;
			  var y = document.forms["saveimmidocumentbydocId"]["otherAddtnlinput[]"].value;
			  if (y == "") {
				    alert("Document Name must be filled out");
				    return false;
				}else if (x == "") {
					    alert("File must be filled out");
					    return false;
				 } 
			}
		$('#saveimmidocumentbydocId').formValidation({
		    framework: 'bootstrap',
		    row: {
		        valid: 'field-success',
		        invalid: 'field-error'
		    },
		    fields: {
		    'filename[]': {
		            validators: {
		            	notEmpty: {
		                    message: 'Doument is required '
		                }
		            }
		        },
		     'otherAddtnlinput[]': {
		            validators: {
		            	notEmpty: {
		                    message: 'Please enter Doument Name'
		                }
		            }
		        }  
		    }
		});*/
		
		var max_field=15;
		//$('#savesubmitid').hide();
		/* io_cval=0;  */
		//$('#OtherBtnCount').val(io_cval);
		function OtherAdditional() { 
			var io_cval=$("input[name='otherAddtnlinput[]']").length;
			io_cval++;   
			  if(io_cval>=1){
				  $('#OtherBtnCount').val(io_cval);
				  $('#subdoclength').val(io_cval);
			    }else{
			    	  $('#OtherBtnCount').val(0);
			    } 
			   $('#savesubmitid').show();
				$('#OtherBtnCount').val(io_cval);
			    var objTo = document.getElementById('OtherBtnDiv');
			    var divtest = document.createElement("div");
				divtest.setAttribute("class", "form-group row removeclass"+io_cval);
				var rdiv = 'removeclass'+io_cval;
			    divtest.innerHTML = '<br>&nbsp;&nbsp;&nbsp;&nbsp;<div class="col-sm-4"><input type="text" class="form-control" name="otherAddtnlinput[]"  placeholder="Enter Document name" required>&nbsp;&nbsp;<input type="hidden" value="'+io_cval+'" name="OtherBtnCount"></div><div class="col-sm-4"><input type="file" class="form-control scnfiles" name="filename1[]" id="filename1[]" onchange="ValidateFileSize(this)" required></div><div class="input-group-btn">&nbsp;&nbsp;<button class="btn btn-danger btn-sm" type="button" onclick="removefieldsupporti9('+io_cval+');"> <span class="glyphicon glyphicon-minus" aria-hidden="true">-</span> </button></div></div></div></div>';
			    if (io_cval>=max_field){
				      objTo.appendChild(divtest);
				      alert("Can't upload more than 15 documents!!");
				      $('#btnid').attr('disabled', 'disabled');
			    }else{
				     objTo.appendChild(divtest);
			     }
			   // $('#saveimmidocumentbydocId').formValidation('update');
		}
		function removefieldsupporti9(rid) {
			var iocval=$("input[name='otherAddtnlinput[]']").length;
			// alert(rid+"////"+iocval);
			$('.removeclass'+rid).remove();
				iocval--;	
			  $('#OtherBtnCount').val(iocval);
			  $('#subdoclength').val(iocval);
			 if($("input[name='filename1[]']").length<=max_field) {
		          $('#btnid').removeAttr('disabled');
		      }
			// alert(rid+"****"+iocval);
			 if(iocval==0){
		   	   $('#savesubmitid').hide();
		      }	  
		}
		
		var prevClicked = [];
		var curClicked = [];
		var difference = [];
		var la_cval = 0; 
		$('#doc_subtype').change(function(){
			if($('#docsubdoclength').val()>0){
				$('#savesubmitid').show();
			  }
		    curClicked = $(this).val();
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
				divtest.innerHTML = '<label class="col-sm-4 control-label">'+label+'</label><input type="hidden" value="'+clickedValue+'" name="h1DocSelectionId'+la_cval+'"><input type="hidden" value="'+la_cval+'" name="h1BtnCount" id="h1BtnCount"><div class="col-sm-4"><div class="form-group row"> <input type="file" class="form-control" name="h1bFile'+la_cval+'" id="h1bFile'+la_cval+'"placeholder="'+clickedValue+'" onchange="ValidateFileSize(this)" required></div></div></div></div></div>';
				objTo.appendChild(divtest);
		    }else{ 
		    	    // alert("else "+clickType);
		    	     var objTo = document.getElementById('listAbtnDiv');
		    	     var selectedNode = document.getElementsByName(clickedValue);
		    	     //alert(selectedNode.length);
		    	     if(selectedNode.length > 0 ){
		    	       selectedNode[0].remove();
		    	       la_cval--;
		    	      }
		    	     if(selectedNode.length < 1 ){
		    	    	 $('#savesubmitid').hide();
		    	    	// alert($('#subdoclength').val());
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
		$('#updateCandDocVerifierStatusForm').formValidation({
		    framework: 'bootstrap',
		    row: {
		        valid: 'field-success',
		        invalid: 'field-error'
		    },
		    fields: {
		   candocapprstatus: {
		            validators: {
		            	notEmpty: {
		                    message: 'Please choose one value'
		                }
		            }
		        },
		     h1bcandicomments: {
		            validators: {
		            	notEmpty: {
		                    message: 'Please enter comments'
		                }
		            }
		        }  
		    }
		});
		$("#cdverifierBtnId").click(function(event) {
			event.preventDefault();
			$('#updateCandDocVerifierStatusForm').formValidation('validate');
			if (!$('#updateCandDocVerifierStatusForm').data('formValidation').isValid()) {
				//alert("not Valid");
			} else {
				$("#cdverifierBtnId").attr("disabled", true);
				var form = $('#updateCandDocVerifierStatusForm')[0];
				var data = new FormData(form);
				$.ajax({
					url : "updateCandDocVerifierStatus", //this is the submit URL
					type : "POST",
					enctype : 'multipart/form-data',
					data : data,
					processData : false,
					contentType : false,
					cache : false,
					success : function(data) {
						 if(data!=null || data!=""){
							   alert(data);
							   document.getElementById("updateCandDocVerifierStatusForm").reset();
								$('#updateCandDocVerifierStatusForm').bootstrapValidator('resetForm', true);
							   $('#CandDocVerifierStautsModal').modal('hide');
							   $("#h1btab").trigger('click');
							 /*   $(".collapsibleh1docs").trigger('click'); */
			   	   	         }
			   	   	      else{
			   	   	          window.location.href="loginpage";
			   	   	   	      }
					}
				});
			}
		});
		
		
		
		var tableh1bch = $('#CommentHistoryTable').DataTable({
			"order" : [ [ 0, "desc" ] ],
			lengthMenu : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
			bLengthChange : true,
			paging : true
		});
		function viewCommentHistory(docTypeId,initiateid1,candidateid,profile_id,profile_type) {
			var initiateid=$('#h1binitiateid1').val();
			$.ajax({
				type : "GET",
				asyn : false,
				contentType : "application/json", // NOT dataType!
				url : "viewImmiDocCommentHistoryByIds",
				data : {
					"docTypeId":docTypeId,
		        	"initiateid":initiateid,
		        	"candidateid" : candidateid,
		        	"profile_id" : profile_id,
		        	"profile_type" : profile_type,
				},
				success : function(response) {
					if (tableh1bch)
						tableh1bch.clear();
					$('#CommentHistoryTable').dataTable().fnClearTable();
					if (response != "") {
						for (var i = 0; i < response.length; i++) {
							tableh1bch.row.add(
									[ '' + response[i].commentid + '',
											'' + response[i].status + '',
											'' + response[i].comments + '',
											'' + response[i].subdate + '',
											'' +  response[i].posted_by + '' ]).draw();
						}
					}
					$('#ImmiDocCommenthistoryModal').modal('show');
				}
			});
}
		function checkpetitionPacket(initateid,candidateid) {
			$.ajax({
				type : "GET",
				asyn : false,
				contentType : "application/json", // NOT dataType!
				url : "checkpetitionPacket",
				data : {
		        	"initiateid":initateid,
		        	"candidateid" : candidateid,
				},
				success : function(response) {
					if(response=='Approved'){
						window.location.href='downloadpetitionPacket?initiateid='+initateid+'&candidateid='+candidateid+'';
					}else{
						alert(response)
					}
				}
			});
		}
		
		function downloadcategories(doctypeid,initateid,candidateid) {
			window.location.href='downloadcategories?initiateid='+initateid+'&candidateid='+candidateid+'&documenttypeid='+doctypeid+'';
			/*$.ajax({
				type : "GET",
				asyn : false,
				contentType : "application/json", // NOT dataType!
				url : "downloadcategories",
				data : {
		        	"initiateid":initateid,
		        	"candidateid" : candidateid,
		        	"documenttypeid" : doctypeid,
				},
				success : function(response) {
					alert()
					if(response=='Approved'){
						window.location.href='downloadcategories?initiateid='+initateid+'&candidateid='+candidateid+'&documenttypeid='+doctypeid+'';
					}else{
						alert(response)
					}
				}
			});*/
		}
		function getprintedNo(documenttypeid,initiateid,profileid,profiletype,candidateid){
			$.ajax({
				type : "GET",
				asyn : false,
				contentType : "application/json", // NOT dataType!
				url : "updateprintnostatus",
				data : {
		        	"initiateid":initiateid,
		        	"candidateid" : candidateid,
		        	"documenttypeid" : documenttypeid,
		        	"profileid" : profileid,
		        	"profiletype" : profiletype,
		        	"printedvalue" : "No",
				},
				success : function(response) {
					alert(response);
					   //$("#candidate").trigger('change');
					   $("#h1btab").trigger('click');
					 /*  $(".collapsibleh1docs").trigger('click');*/
					//"Documents printed status updated"
				}
			});
		}
		
		function getprintedYes(documenttypeid,initiateid,profileid,profiletype,candidateid){
			$.ajax({
				type : "GET",
				asyn : false,
				contentType : "application/json", // NOT dataType!
				url : "updateprintyesstatus",
				data : {
		        	"initiateid":initiateid,
		        	"candidateid" : candidateid,
		        	"documenttypeid" : documenttypeid,
		        	"profileid" : profileid,
		        	"profiletype" : profiletype,
		        	"printedvalue" : "Yes",
				},
				success : function(response) {
					alert(response);
					//$("#candidate").trigger('change');
					   $("#h1btab").trigger('click');
					//"Documents printed status updated"
				}
			});  
		}
		
		
		
		var tabledocse = $('#scantableid').DataTable({
			"order" : [ [ 0, "asc" ] ],
			lengthMenu: [[5,10, 20, 50, -1], [5,10, 20, 50, "All"]],
			bLengthChange : true,
			paging : true
		});
		
		function uploadscanedDocs(initiateid,canid){
			$("#scah1bbtnSubmit").attr("disabled", false);
			$("#h1scandocuments").modal("show")
			$('#sinitiated').val(initiateid);
			$('#scandidateid').val(canid);
			 $.ajax({
					type : "GET",
					asyn : false,
					contentType : "application/json", // NOT dataType!
					url : "getScanlist",
					data : {
						initiateid: initiateid,
						candidate_id:canid
					}, 
					success : function(response) {
						//alert(response.length)
						 $('#scantableid').dataTable().fnClearTable();
						 if (tabledocse) tabledocse.clear();
						 
					if(response!=null){
						
					
						
						for (var i = 0; i < response.length; i++) {	
							var input_value=response[i].inputval;
							var file_name=response[i].file_name;
							var initiate_id=response[i].initiate_id;
							var posted_by=response[i].posted_by;
							var candidate_id=response[i].beneficiary_Name;
							var status=response[i].status;
							var submitted_date=response[i].startdate;
							var encoded = encodeURIComponent(file_name);
							  tabledocse.row.add([
									''+candidate_id+'',
									'<a href="downloadh1bdocs?filename='+encoded+'&candidate_id='+canid+'" style="color:blue">'+response[i].file_name+'</a>',
									''+input_value+'',
									''+posted_by+'',
									'' +submitted_date+'', ]).draw();
							
						}
					}
					$('#addrow').empty();
					 $('#scah1bbtnSubId').hide();
					$("#h1scandocuments").modal("show")
					}
						
					});
		}
		// var count=0;
		
		// var buttoncount=$('#buttoncount').val();
		 $('#scah1bbtnSubId').hide();
		 $("#add").click(function(){ 
			 var count=$("input[name='filename[]']").length;
			 var candid=$("#candidate option:selected").val();
			 $('#scandidateid').val(candid);
			  count++;		
			  $('#buttoncount').val(count);
			  if(count>0){
				  $('#scah1bbtnSubId').show();
			  }else{
				  $('#scah1bbtnSubId').hide();
			  }
			 var html = '';
			    html += '<center><div class="form-control" style="border:0;"  id="forms'+count+'">';
		        html += '<input type="text" placeholder="Enter document name" id="inputval[]" name="inputval[]" pattern="[a-zA-Z0-9]+[a-zA-Z0-9.-/, ]+"  required >' ;
		        html += '<input type="file" name="filename[]" class="scanfiles"  id="filename[]" onchange="ValidateFileSize(this)" required>' ;
		        html += '<button type="button" id="remove'+count+'" onclick="remove('+count+');" style="letter-spacing: 1px; background-color: tomato; color: white;"><i class="fa fa-close"></i></button>' ;
		        html += '</div></center>';
		        $('#addrow').append(html);
		 });
		
		 function remove(id) {
			   var rcount=$("input[name='filename[]']").length;
			   $('#forms'+id).remove();
			   rcount--;
			   $('#buttoncount').val(rcount);
			   if(rcount>0){
					  $('#scah1bbtnSubId').show();
				  }else{
					  $('#scah1bbtnSubId').hide();
				  }
		 }
		 
		 $("#scah1bbtnSubmit").click(function(event) {
				//	event.preventDefault();
				//alert(+"*******"+);
					  var inputvals = document.getElementsByName("inputval[]");
					  var ret1 = true;
					  if(inputvals!='undefined'){
					  for (var x = 0; x < inputvals.length; x++) {       
					      if(inputvals[x].value == '' || inputvals[x].value == '0'){
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
					  
					  var filenames = document.getElementsByName("filename[]");
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
					 
					 var rets = true;
					  var scarr = [];
					  var fileInputs = document.querySelectorAll(".scanfiles");
					   fileInputs.forEach(function(input){
					    if(scarr.length == 0){
					    	scarr.push(input.files[0].name);
					    } else {
					      if(scarr.indexOf(input.files[0].name) > -1){
					       // alert("You can not send two or more same files.");
					        rets = false;
					      } else {
					    	  scarr.push(input.files[0].name);
					        rets = true;
					      }
					    }
					});
					   
					   if (rets == false)
					     {
						   alert("You can not send two or more same files.");
					       return rets;        
					     }
					 
				   	 if(ret == true && ret1 == true && rets == true){	 
				    		// alert("*****submit***");
				   		$("#scah1bbtnSubmit").attr("disabled", true);
						var form = $('#addh1bscanDetailsForm')[0];
						var data = new FormData(form);
						$.ajax({
							url : "addh1bscanDetailsForm", //this is the submit URL
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
									  $('#h1scandocuments').modal('hide');
									   $("#h1btab").trigger('click');
					   	   	         }
					   	   	      else{
					   	   	          window.location.href="loginpage";
					   	   	   	      }
							}
						});
				   		 
				    	 }
			});
		 
		 function downloadscanedDocs(initiateid,canid){
			 window.location.href='downloadscanedDocs?initiateid='+initiateid+'&candidateid='+canid;
			}
		