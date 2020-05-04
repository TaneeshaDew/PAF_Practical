package com.caremarque.hospital.service;

import java.util.ArrayList;

import com.caremarque.hospital.model.Hospital;

public interface IHospitalService {

	public String createHospital(Hospital hospital);

	public String getHospital(String hospitalId);

	public String getHospitals();

	public String updateHospital(String hospitalId, String hospitalName, String phone, String regNo, String address,
			String Open_Hours, String Close_Hours,String email,String channelingFee);

	public String DeleteHospital(String hospitalId);

	public ArrayList<String> getHospitalIDs();

}
