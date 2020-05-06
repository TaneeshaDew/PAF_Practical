

package com.caremarque.hospital.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.caremarque.hospital.model.Hospital;
import com.caremarque.hospital.utils.CommonUtils;
import com.caremarque.hospital.utils.Constants;
import com.caremarque.hospital.utils.DBConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class HospitalServiceImpl implements IHospitalService {

	public static final Logger Log = Logger.getLogger(IHospitalService.class.getName());

	public static Connection con;
	public static Statement st;
	public static PreparedStatement preparedStatement;
	@Override
	public String createHospital(Hospital hospital) {
		

		String result = null;
		 

		String hospitalId = CommonUtils.generateHospitalIDs(getHospitalIDs());
		System.out.println("hospitalId " +hospitalId );

		try {
			con = DBConnection.getDBConnection();

			String query = "INSERT INTO hospital(hospitalId,hospitalName,address,phone,regNo,Open_Hours,Close_Hours,email,channelingFee)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			preparedStatement = con.prepareStatement(query);

			hospital.setHospitalId(hospitalId);
			preparedStatement.setString(Constants.COLUMN_INDEX_ONE, hospital.getHospitalId());
			preparedStatement.setString(Constants.COLUMN_INDEX_TWO, hospital.getHospitalName());
			preparedStatement.setString(Constants.COLUMN_INDEX_THREE, hospital.getAddress());
			preparedStatement.setString(Constants.COLUMN_INDEX_FOUR, hospital.getPhone());
			preparedStatement.setString(Constants.COLUMN_INDEX_FIVE, hospital.getRegNo());
			preparedStatement.setString(Constants.COLUMN_INDEX_SIX, hospital.getOpen_Hours());
			preparedStatement.setString(Constants.COLUMN_INDEX_SEVEN, hospital.getClose_Hours());
			preparedStatement.setString(Constants.COLUMN_INDEX_EIGHT, hospital.getEmail());
			preparedStatement.setString(Constants.COLUMN_INDEX_NINE, hospital.getChannelingFee());

			preparedStatement.executeUpdate();
			String newHospital = getHospitals();
			result = "{\"status\":\"success\", \"data\": \"" + newHospital + "\"}";

		} catch (Exception e) {

			result =  "{\"status\" : \"error\", \"data\" : \"Error while registering to the system..!\"}";
			System.err.println(e.getMessage());
			Log.log(Level.SEVERE, e.getMessage());
		} finally {

			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				Log.log(Level.SEVERE, e.getMessage());
			}
		}

		return result;
	}

	@Override
	public String getHospital(String hospitalId) {
		String result = null;
		ArrayList<Hospital> arrayList=new ArrayList<Hospital>();
		
		try {
			con=DBConnection.getDBConnection();
			
			String query = "SELECT * FROM hospital "
					+ "WHERE hospitalId = '"+ hospitalId + "'";
			
			PreparedStatement preparedStatement=con.prepareStatement(query);
			ResultSet rSet=preparedStatement.executeQuery();
			
			result = "<table border=\"1\"> <tr><th>Hospital_ID</th> " 
					+ "<th>HospitalName</th> " 
					+ "<th>Address</th> " 
					+ "<th>phone</th> "
					+ "<th>RegNo</th> " 
					+ "<th>Open_Hours</th> " 
					+ "<th>Close_Hours</th> "
					+ "<th>Email</th> " 
					+ "<th>ChannelingFee</th></tr>";

			while (rSet.next()) {

				Hospital hospital = new Hospital();

				
				hospital.setHospitalName(rSet.getString(Constants.COLUMN_INDEX_ONE));
				hospital.setAddress(rSet.getString(Constants.COLUMN_INDEX_TWO));
				hospital.setPhone(rSet.getString(Constants.COLUMN_INDEX_THREE));
				hospital.setRegNo(rSet.getString(Constants.COLUMN_INDEX_FOUR));
				hospital.setOpen_Hours(rSet.getString(Constants.COLUMN_INDEX_FIVE));
				hospital.setClose_Hours(rSet.getString(Constants.COLUMN_INDEX_SIX));
				hospital.setEmail(rSet.getString(Constants.COLUMN_INDEX_SEVEN));
				hospital.setChannelingFee(rSet.getString(Constants.COLUMN_INDEX_EIGHT));
				arrayList.add(hospital);

				result += "<tr><td>" + rSet.getString(Constants.COLUMN_INDEX_ONE) + "</td>";
				result += "<td>" + rSet.getString(Constants.COLUMN_INDEX_TWO) + "</td>";
				result += "<td>" + rSet.getString(Constants.COLUMN_INDEX_THREE) + "</td>";
				result += "<td>" + rSet.getString(Constants.COLUMN_INDEX_FOUR) + "</td>";
				result += "<td>" + rSet.getString(Constants.COLUMN_INDEX_FIVE) + "</td>";
				result += "<td>" + rSet.getString(Constants.COLUMN_INDEX_SIX) + "</td>";
				result += "<td>" + rSet.getString(Constants.COLUMN_INDEX_SEVEN) + "</td>";
				result += "<td>" + rSet.getString(Constants.COLUMN_INDEX_EIGHT) + "</td>";
				result += "<td>" + rSet.getString(Constants.COLUMN_INDEX_NINE) + "</td>";
				
				System.out.println("Data Retrived");

			}

			result += "</table>";

			
		} catch (Exception e) {
			Log.log(Level.SEVERE, e.getMessage());
		}finally {

			try {
				if(st != null) {
					st.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				Log.log(Level.SEVERE, e.getMessage());
			}
		
		}
		return result;
	}
	
	
	//TO REURN A HOSPITAL LIST
	public ArrayList<Hospital> getHospitalByID(String hospitalId){
		
		//String output = null;
		ArrayList<Hospital> arrayList = new ArrayList<Hospital>();
		
		try {
			con = DBConnection.getDBConnection();
			
			String query = "SELECT * FROM hospital"
					+ "WHERE hospitalId = '"+ hospitalId +"'";
			
			PreparedStatement pStatement = con.prepareStatement(query);
			ResultSet rs = pStatement.executeQuery();
			
			while(rs.next()) {
				Hospital hospital = new Hospital();
				hospital.setHospitalId(hospitalId);
				hospital.setHospitalName(rs.getString(Constants.COLUMN_INDEX_ONE));
				hospital.setAddress(rs.getString(Constants.COLUMN_INDEX_TWO));
				hospital.setPhone(rs.getString(Constants.COLUMN_INDEX_THREE));
				hospital.setRegNo(rs.getString(Constants.COLUMN_INDEX_FOUR));
				hospital.setOpen_Hours(rs.getString(Constants.COLUMN_INDEX_FIVE));
				hospital.setClose_Hours(rs.getString(Constants.COLUMN_INDEX_SIX));
				arrayList.add(hospital);
				
				System.out.println("Data Retrieved from DB...!");
			}
			
			
		
		} catch (Exception e) {
			Log.log(Level.SEVERE, e.getMessage());
		
		} finally {
			try {
				if(st != null) {
					st.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				Log.log(Level.SEVERE, e.getMessage());
			}
		}
		
		return arrayList;
	}

	@Override
	public String getHospitals() {
		
		String result = null;
		ResultSet rs = null;
		

		ArrayList<Hospital> arrayList = new ArrayList<Hospital>();

		try {
			con = DBConnection.getDBConnection();

			String query = "SELECT * from hospital";

			st = con.createStatement();
			rs = st.executeQuery(query);

			result ="<table class = 'table table-striped table-responsive' style='width:120%; margin-left: -40px'>" +
					 "<tr style='background-color:#1985a1; color:#ffffff;'><th>Hospital Id</th>" + "<th>Hospital Name</th>" + "<th>Address</th>" +
					 "<th>Phone</th>" + "<th>Reg_No</th>" + "<th>Opening_Hours</th>" + "<th>Closing_Hours</th>" +
					 "<th>Email</th>" + "<th>ChannelingFee</th>" + "<th>Update</th>" + "<th>Remove</th></tr>";
			
			
			while (rs.next()) {

				String hospitalId = rs.getString("hospitalId");
				String hospitalName = rs.getString("hospitalName");
				String address = rs.getString("address");
				String phone  = rs.getString("phone");
				String regNo  = rs.getString("regNo");
				String Open_Hours  = rs.getString("Open_Hours");
				String Close_Hours  = rs.getString("Close_Hours");
				String email  = rs.getString("email");
				String channelingFee  = rs.getString("channelingFee");
		
				System.out.println("GetAllAPtient : PatientId : " + hospitalId);

				result += "<tr><td><input id = 'hidHospitalIdUpdate' name = 'hidHospitalIdUpdate' type='hidden' value = '" + hospitalId + "'>" + hospitalId + "</td>";
				
				result += "<td>" + hospitalName + "</td>";
				result += "<td>" + address + "</td>";
				result += "<td>" + phone + "</td>";
				result += "<td>" + regNo + "</td>";
				result += "<td>" + Open_Hours + "</td>";
				result += "<td>" + Close_Hours + "</td>";
				result += "<td>" + email + "</td>";
				result += "<td>" + channelingFee + "</td>";
				
				result += "<td><input name = 'btnUpdate' type = 'button' value = 'Update' class = 'btnUpdate btn btn-success btn-sm'></td>"
						+ "<td><input name = 'btnRemove' type = 'button' value = 'Remove' class = 'btnRemove btn btn-danger btn-sm' data-hospitalid = '"+ hospitalId +"'>" 
						+ "</td></tr>";
				
				System.out.println("Data Retrived");

			}

			result += "</table>";

		} catch (Exception e) {
			

			result = "Error Occured.Cant read Hospital details";
			System.err.println(e.getMessage());
			Log.log(Level.SEVERE, e.getMessage());
		} finally {

			try {
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
			
				Log.log(Level.SEVERE, e.getMessage());
			}
		}
		return result;
	}

	@Override
	public String updateHospital(String hospitalId, String hospitalName, String address, String phone, String regNo,
			String Open_Hours, String Close_Hours,String email,String channelingFee) {
		
		String result = "";
		

		try {
			con = DBConnection.getDBConnection();
			
			String query = "UPDATE hospital SET  hospitalName = ?, address = ?, phone = ?, regNo = ?, Open_Hours = ?, Close_Hours = ?, email = ?, channelingFee = ? WHERE hospitalId = ?";
			preparedStatement = con.prepareStatement(query);

			
			preparedStatement.setString(Constants.COLUMN_INDEX_ONE, hospitalName);
			preparedStatement.setString(Constants.COLUMN_INDEX_TWO, address);
			preparedStatement.setString(Constants.COLUMN_INDEX_THREE, phone);
			preparedStatement.setString(Constants.COLUMN_INDEX_FOUR, regNo);
			preparedStatement.setString(Constants.COLUMN_INDEX_FIVE, Open_Hours);
			preparedStatement.setString(Constants.COLUMN_INDEX_SIX, Close_Hours);
			preparedStatement.setString(Constants.COLUMN_INDEX_SEVEN, email);
			preparedStatement.setString(Constants.COLUMN_INDEX_EIGHT, channelingFee);
			preparedStatement.setString(Constants.COLUMN_INDEX_NINE, hospitalId);

			preparedStatement.execute();

			System.out.println("Update HospitalID : " + hospitalId);
			String newHospital = getHospitals();
			
			result = "{\"status\":\"success\", \"data\": \"" + newHospital + "\"}";
			

		} catch (Exception e) {
			

			result = "{\"status\":\"error\", \"data\":\"Error while updating the patient details..!\"}"; 
			System.err.println(e.getMessage());
			Log.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				Log.log(Level.SEVERE, e.getMessage());
			}
		}
		return result;
	}

	@Override
	public String DeleteHospital(String hospitalId) {
		

		String result = "";
		Connection con = null;

		try {
			con = DBConnection.getDBConnection();

			String query = "DELETE FROM hospital WHERE hospitalId = ?";

			preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setString(Constants.COLUMN_INDEX_ONE, hospitalId);
			preparedStatement.execute();
			String newHospital = getHospitals();

			result = "{\"status\":\"success\", \"data\": \"" + newHospital + "\"}"; 


		} catch (Exception e) {

			result = "{\"status\":\"error\", \"data\":\"Error while deleting the patient account..!\"}"; 
			
			System.err.println(e.getMessage());
			Log.log(Level.SEVERE, e.getMessage());
		} finally {

			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

				if (con != null) {
					con.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	@Override
	public ArrayList<String> getHospitalIDs() {

		
		ResultSet resultSet = null;

		ArrayList<String> hospitalList = new ArrayList<String>();

		try {
			con = DBConnection.getDBConnection();

			String queryString = "SELECT hospitalId FROM hospital";

			preparedStatement = con.prepareStatement(queryString);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				hospitalList.add(resultSet.getString(1));

			}
		} catch (Exception e) {
			
			Log.log(Level.SEVERE, e.getMessage());

		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
			
				Log.log(Level.SEVERE, e.getMessage());
			}
		}
		System.out.println(hospitalList.size());
		return hospitalList;
	}
	
	public String createAppointment(String hospitalId) {
		
		Client client = Client.create();
		String result = "";
		ArrayList<Hospital> hospList = new ArrayList<Hospital>();
		hospList = getHospitalByID(hospitalId);
		
		WebResource webResource = client.resource("http://localhost:8088/Appointment_REST/myService/Payment/fromHospital");
		ObjectMapper mapper = new ObjectMapper();
		String jsonInput = "";
		
		try {
			jsonInput = mapper.writeValueAsString(hospList.get(0));
			System.out.println("JSON : " +jsonInput);
		} catch (Exception e) {
			Log.log(Level.SEVERE, e.getMessage());
		}
		
		try {
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, jsonInput);
			
			if(response.getStatus() != 201) {
				throw new RuntimeException("HTTP Error : " + response.getStatus());
			}
			result = response.getEntity(String.class);
		} catch (Exception e) {
			Log.log(Level.SEVERE, e.getMessage());
		}
			System.out.println("Response from the server...!");
			System.out.println(result);
			
			return result;
		
	}

}
