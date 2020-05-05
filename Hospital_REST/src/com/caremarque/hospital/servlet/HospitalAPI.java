package com.caremarque.hospital.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caremarque.hospital.model.Hospital;
import com.caremarque.hospital.service.HospitalServiceImpl;


@WebServlet("/HospitalAPI")
public class HospitalAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public HospitalAPI() {
        // TODO Auto-generated constructor stub
    }
	private static Map getParasMap(HttpServletRequest request) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		try {
		Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
		String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
		
		scanner.close();
		
		String[] params = queryString.split("&");
		for(String param : params) {
			String[] a = param.split("=");
			map.put(a[0], a[1]);
		}
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
		
		return map;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HospitalAPI");
		Hospital hospital=new Hospital();
		hospital.setHospitalName(request.getParameter("hospitalName"));
		hospital.setPhone(request.getParameter("phone"));
		hospital.setRegNo(request.getParameter("regNo"));
		hospital.setAddress(request.getParameter("address"));
		hospital.setOpen_Hours(request.getParameter("Open_Hours"));
		hospital.setClose_Hours(request.getParameter("Close_Hours"));
		hospital.setEmail(request.getParameter("email"));
		hospital.setChannelingFee(request.getParameter("channelingFee"));
		
		HospitalServiceImpl hospitalServiceImpl=new HospitalServiceImpl();
		String result = hospitalServiceImpl.createHospital(hospital);
		System.out.println(result);
		response.getWriter().write(result);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HospitalServiceImpl hospitalServiceImpl=new HospitalServiceImpl();
		
		Map paras = getParasMap(request);
		System.out.println("Hospital Id: " + paras.get("hidHospitalIDSave").toString());
		String result = hospitalServiceImpl.updateHospital(
				paras.get("hidHospitalIDSave").toString(),
				paras.get("hospitalName").toString(),
				paras.get("phone").toString(),
				paras.get("regNo").toString(),
				paras.get("address").toString(),
				paras.get("Open_Hours").toString(),
				paras.get("Close_Hours").toString(),
				paras.get("email").toString(),
				paras.get("channelingFee").toString());
				
		response.getWriter().write(result);
				
				
		
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HospitalServiceImpl hospitalServiceImpl=new HospitalServiceImpl();
		Map paras	=	getParasMap(request);
		String result = hospitalServiceImpl.DeleteHospital("hospitalId").toString();
		response.getWriter().write(result);
	}

}
