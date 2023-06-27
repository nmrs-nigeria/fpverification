/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.fpverification.db;

import org.openmrs.module.fpverification.Utils.Utils;
import org.openmrs.module.fpverification.fragment.controller.FpverificationHomeFragmentController;
import org.openmrs.module.fpverification.model.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NdrDBManager {
	
	private Statement presparent = null;
	
	public Connection conn = null;
	
	PreparedStatement pStatement1 = null;
	
	Statement pStatement = null;
	
	ResultSet rs = null;
	
	CallableStatement stmt = null;
	
	private ResultSet resultSet = null;
	
	private String sql;
	
	private ResultSet rs2 = null;
	
	public String parent = "";
	
	private Statement preschild = null;
	
	private Statement preschild2 = null;
	
	private Statement preschildparent = null;
	
	private Statement presnegativechild = null;
	
	public String jsonformat = "";
	
	public int gencount = 0;
	
	public NdrDBManager() {
		
	}
	
	public void openConnection() throws SQLException {
		DBConnection openmrsConn = Utils.getNmrsConnectionDetails();
		
		conn = DriverManager.getConnection(openmrsConn.getUrl(), openmrsConn.getUsername(), openmrsConn.getPassword());
	}
	
	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (pStatement != null) {
				pStatement.close();
			}
			if (pStatement1 != null) {
				pStatement1.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (rs2 != null) {
				rs2.close();
			}
		}
		catch (Exception ex) {
			
		}
	}
	
	public ResultSet getPatientBiometricsVerify(String startdate, String enddate, Integer patientid) throws Exception {
		
		try {
			String sql = "select b.*,p.`identifier` from `biometricverificationinfo` b left join patient_identifier p on p.patient_id = b.patient_Id where p.identifier_type =4 and b.patient_Id = ? and DATE(b.date_created) between ? and ?";
			pStatement1 = conn.prepareStatement(sql);
			pStatement1.setString(1, String.valueOf(patientid));
			pStatement1.setString(2, startdate);
			pStatement1.setString(3, enddate);
			rs = pStatement1.executeQuery();
			//fp.getPatientBiometricsVerifyContainer(rs);
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL Error" + e);
		}
		finally {}
		return rs;
	}
	
	public List<Integer> getPatientBiometricsVerifyDistinctList(String startdate, String enddate) throws Exception {
		List<Integer> list = new ArrayList<>();
		try {

			System.out.println("getPatientBiometricsVerifyDistinct");
			String sql2 = "SELECT distinct patient_Id FROM biometricverificationinfo where DATE(date_created) between ? and ? ";
			pStatement1 = conn.prepareStatement(sql2);
			pStatement1.setString(1, startdate);
			pStatement1.setString(2, enddate);

			rs2 = pStatement1.executeQuery();
			while (rs2.next()) {
				System.out.println("getPatientBiometricsVerifyDistinct" + rs2.getInt("patient_Id"));
				list.add(rs2.getInt("patient_Id"));
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL Error" + e);
		}
		finally {
		}

		return list;
	}
}
