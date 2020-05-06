<%@page import="com.caremarque.hospital.service.HospitalServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hospital Service</title>

<!-- CSS -->
<link rel="stylesheet" href="Views/bootstrap.min.css">
<link rel="stylesheet" href="Views/hospital.css">

<!-- JS -->
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Hospital.js" type="text/javascript"></script>

 
</head>
<body>
	<div class="container">

		<form name="hospitalCreation" id="hospitalCreation"
			method="POST" action="Hospital.jsp">

			<div class="form-title">Add a Hospital</div>
			<br /> <br />
			<h4>Hospital Information</h4>
			
			<div class="row">
				<div class="col">
					<label>Hospital Name <label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Your HospitalName...!" name="hospitalName"
						id="hospitalName" class="form-control form-control-sm"><br />
				</div>
				<div class="col">
					<label>Hospital Address <label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Your Address...!" name="address"
						id="address" class="form-control form-control-sm"><br />
				</div>
			</div>
			
			<div class="row">
				<div class="col">
					<label>Mobile Number <label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Your Number...!" name="phone"
						id="phone" class="form-control form-control-sm"><br />
				</div>
				<div class="col">
					<label>Register Number <label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Your Registered_Number...!" name="regNo"
						id="regNo" class="form-control form-control-sm"><br />
				</div>
				
			</div>
			
			<div class="row">
					<div class="col">
					<label>Open Hours <label_1>*</label_1></label><br /> <input
						type="time" placeholder="Enter Opening Time...!" name="Open_Hours"
						id="Open_Hours" class="form-control form-control-sm"><br />
				</div>
					<div class="col">
					<label>Close Hours <label_1>*</label_1></label><br /> <input
						type="time" placeholder="Enter Closing Time...!" name="Close_Hours"
						id="Close_Hours" class="form-control form-control-sm"><br />
				</div>
			</div>
			
			<div class="row">
					<div class="col">
					<label>Email<label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Your Email...!" name="email"
						id="email" class="form-control form-control-sm"><br />
				</div>
				<div class="col">
					<label>Channeling Fee <label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Your ChannelingFee...!" name="channelingFee"
						id="channelingFee" class="form-control form-control-sm"><br />
				</div>
				
			</div>
		
			<input id="btnSave" name="btnSave" type="button"
				value="Add Hospital" class="btn btn-primary"> <input
				type="hidden" id="hidHospitalIdSave" name="hidHospitalIdSave"
				value=""> <br> <br>

		</form>
		
		<div id="alertSuccess" class="alert alert-success"></div>
		<div id="alertError" class="alert alert-danger"></div>
		
		<div id="divHospitalsGrid">
			<%
				HospitalServiceImpl hospitalServiceImpl = new HospitalServiceImpl();
				out.print(hospitalServiceImpl.getHospitals());
			%>
		</div>

	</div>
		
</body>
</html>