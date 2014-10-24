package com.invsol.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import javax.ws.rs.core.Response;

import com.invsol.constants.AppConstants;
import com.invsol.constants.QueryConstants;
import com.invsol.dto.BusinessUser;
import com.invsol.errorhandling.AppException;
import com.invsol.utilities.DBConnectionUtil;


public class ProfileData {

	public ProfileData() {

	}

	public boolean updateBusinessUserProfile(int restaurant_id, String name, String email,
			String serviceStartTime, String serviceEndTime, Integer[] closedOn, String address, String city, String state,
			int zipcode, Integer[] cuisine_id) throws AppException {
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_UPDATE_BUSINESS_USER_PROFILE;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setTime(3, Time.valueOf(serviceStartTime));
			stmt.setTime(4, Time.valueOf(serviceEndTime));
			Array closedOnArray = conn.createArrayOf("int4", closedOn);
			stmt.setArray(5, closedOnArray);
			stmt.setString(6, address);
			stmt.setString(7, city);
			stmt.setString(8, state);
			stmt.setInt(9, zipcode);
			stmt.setInt(10, restaurant_id);
			System.out.println("final sql query=="+stmt.toString());
			int i = stmt.executeUpdate();
			if (i > 0){
				System.out.println("record inserted");
			}else {
				System.out.println("record not inserted");
				throw new AppException(Response.Status.NOT_IMPLEMENTED.getStatusCode(), 500, AppConstants.ERROR_SQL_QUERY_EXECUTION,
						"", "");
			}
			
			//Code to insert Restaurant Cuisines Details
			stmt = null;
			sql = QueryConstants.QUERY_INSERT_RESTAURANT_CUISINES_DETAILS;
			conn.setAutoCommit(false);
			for (int j = 0; j < cuisine_id.length; j++) {
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, restaurant_id);
				stmt.setInt(2, cuisine_id[j]);
				System.out.println("final sql query=="+stmt.toString());
				int count = stmt.executeUpdate();
				if (count > 0){
					System.out.println("record inserted");
				}else {
					System.out.println("record not inserted");
					throw new AppException(Response.Status.NOT_IMPLEMENTED.getStatusCode(), 500, AppConstants.ERROR_SQL_QUERY_EXECUTION,
							"", "");
				}
			}
			
			conn.commit();
			return true;
		} catch (SQLException se) {
			System.out.println("got se==="+se.getMessage());
			if(se.getMessage().startsWith("Duplicate")){
				throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 401, AppConstants.ERROR_USER_ALREADY_REGISTERED,
				se.getMessage(), "");
			}
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se1) {
				System.out.println("got se1");
				se1.printStackTrace();
				
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se2) {
				System.out.println("got se2");
				se2.printStackTrace();
				throw new AppException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), 500, AppConstants.ERROR_SQL_QUERY_EXECUTION,
						"", "");
			}
		}
		return false;
	}
}
