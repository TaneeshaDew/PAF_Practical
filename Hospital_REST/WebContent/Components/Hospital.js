//Page refresh moment
$(document).ready(function(){
	if($("#alertSuccess").text().trim() == ""){
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

//Save
$(document).on("click", "#btnSave", function(event){
	
	//Clear alerts
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	//Form validation
	var status = validateForm();
	if(status != true){
		
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	//If valid
	var type = ($("#hidHospitalIdSave").val() == "") ? "POST" : "PUT";
	
	$.ajax({
		
		url : "HospitalAPI",
		type : type,
		data : $("#hospitalCreation").serialize(),
		dataType : "text",
		complete : function(response, status){
			
			onHospitalRegisterComplete(response.responseText, status);
		}
	});
	
});

function onHospitalRegisterComplete(response, status){
	
	if(status == "success"){
		
		var resultSet = JSON.parse(response);
		
		if(resultSet.status.trim() == "success"){
			
			$("#alertSuccess").text("Successfully saved..!");
			$("#alertSuccess").show();
			
			$("#divHospitalsGrid").html(resultSet.data);	
			
		}else if(resultSet.status.trim() == "error"){
			
			$("#alertError").text(resultSet.data);
			$("#alertError").show(); 
		}
		
	}else if(status == "error"){
		$("#alertError").text("Error while saving.");
		$("#alertError").show(); 
	
	}else{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show(); 
	}
	
	$("#hidHospitalIdSave").val("");
	$("#hospitalCreation")[0].reset(); 
	
}	

//Update
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidHospitalIdSave").val($(this).closest("tr").find('#hidHospitalIdUpdate').val());
	$("#hospitalName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#address").val($(this).closest("tr").find('td:eq(2)').text());
	$("#phone").val($(this).closest("tr").find('td:eq(3)').text());
	$("#regNo").val($(this).closest("tr").find('td:eq(4)').text());
	$("#Open_Hours").val($(this).closest("tr").find('td:eq(5)').text());
	$("#Close_Hours").val($(this).closest("tr").find('td:eq(6)').text());
	$("#email").val($(this).closest("tr").find('td:eq(7)').text());
	$("#channelingFee").val($(this).closest("tr").find('td:eq(8)').text());
	
	
	
});

//Delete
$(document).on("click", ".btnRemove", function(event){
		
	$.ajax({
		url : "HospitalAPI",
		type : "DELETE",
		data : "hospitalId=" + $(this).data("hospitalid"),
		dataType : "text",
		complete : function(response, status){
			 
			onHospitalDeleteComplete(response.responseText, status);
		 }
	});
}); 

function onHospitalDeleteComplete(response, status){
	
	if (status == "success")
	 {
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success"){
			
			 $("#alertSuccess").text("Successfully deleted..!");
			 $("#alertSuccess").show();
			 
			 $("#divHospitalsGrid").html(resultSet.data);
			 
		} else if (resultSet.status.trim() == "error"){
			 $("#alertError").text(resultSet.data);
			 $("#alertError").show();
		}
		
	 } else if (status == "error")
	 {
		 $("#alertError").text("Error while deleting..!");
		 $("#alertError").show();
	 } else
	 {
		 $("#alertError").text("Unknown error while deleting..!");
		 $("#alertError").show();
	 } 
}

//Client-Model
function validateForm(){
	
	if($("#hospitalName").val().trim() == ""){
		return "Insert Hospital Name !";
	}
	var letterReg1 = /^[A-Za-z0-9\s]+$/;
	var tmphospitalName =  $("#hospitalName").val().trim();
	if(!tmphospitalName.match(letterReg1)){
		return "Hospital Name must have alphabet charaters only !";
	}
	
	
	if($("#address").val().trim() == ""){
		return "Insert a address !";
	}
	var tmpaddress =  $("#address").val().trim();
	if(!tmpaddress.match(letterReg1)){
		return "Address must have alphabet charaters only !";
	}
	
	
	if($("#phone").val().trim() == ""){
		return "Insert Phone Number !";
	}
	var contactReg = /^\d{10}$/;
	var tmpPhone =  $("#phone").val().trim();
	if(!tmpPhone.match(contactReg)){
		return "Insert a valid Phone Number !";
	}
	
	
	if($("#regNo").val().trim() == ""){
		return "Insert Reg Number !";
	}
	var letterReg1 = /^[A-Za-z0-9\s]+$/;
	var tmpregNo =  $("#regNo").val().trim();
	if(!tmpregNo.match(letterReg1)){
		return "Register Number must have alphabet charaters only !";
	}	
	
	
	if($("#email").val().trim() == ""){
		return "Insert Email !";
	}	
	var emailReg = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	var tmpEmail =  $("#email").val().trim();
	if(!tmpEmail.match(emailReg)){
		return "Insert a valid Email !";
	}
	
	
	if($("#channelingFee").val().trim() == ""){
		return "Insert Channeling Fee !";
	}
	var numberReg = /^[0-9]+$/;
	var tmpnum =  $("#channelingFee").val().trim();
	if(!tmpnum.match(numberReg)){
		return "Channel Fee must have numbers only !";
	}

	return true;
}