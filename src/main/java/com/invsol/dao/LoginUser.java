package com.invsol.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.core.Response;

import com.invsol.constants.AppConstants;
import com.invsol.constants.QueryConstants;
import com.invsol.dto.BusinessUser;
import com.invsol.errorhandling.AppException;
import com.invsol.utilities.DBConnectionUtil;

public class LoginUser {
	
	public LoginUser(){
		
	}

	public BusinessUser loginBusinessUser(long phoneNumber, String password) throws AppException{
		BusinessUser userProfile = null;
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_AUTHENTICATE_RESTAURANT;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, phoneNumber);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
	         while ( rs.next() ) {
	            userProfile = new BusinessUser(rs.getInt("restaurant_id"), rs.getLong("phonenumber"));
	            userProfile.setAddress(rs.getString("address"));
	            userProfile.setCity(rs.getString("city"));
	            userProfile.setState(rs.getString("state"));
	            userProfile.setEmail(rs.getString("email"));
	            userProfile.setService_start_time(rs.getString("service_start_time"));
	            userProfile.setService_end_time(rs.getString("service_end_time"));
	            userProfile.setZipcode(rs.getInt("zipcode"));
	         }
	         rs.close();
	         stmt.close();
	         conn.close();
	         return userProfile;
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
				/*throw new AppException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), 500, AppConstants.ERROR_SQL_QUERY_EXECUTION,
						"", "");*/
			}
		}
		return userProfile;
	}
}
