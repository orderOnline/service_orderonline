package com.invsol.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import com.invsol.constants.AppConstants;
import com.invsol.constants.QueryConstants;
import com.invsol.dto.BusinessUser;
import com.invsol.errorhandling.AppException;
import com.invsol.utilities.DBConnectionUtil;
import com.invsol.utilities.OTP_Generation;

public class RegisterUser {
	
public RegisterUser(){
		
	}
	
	public BusinessUser registerBusinessUser(long phoneNumber, String password, String gcm_reg_key) throws AppException{
		BusinessUser businessUserDetails = null;
		//Method call to send OTP Code to user.
		String OTPCode = OTP_Generation.generateRandomOTP(6);
		//boolean smsSent = SMSSendingUtility.sendOTPCodeforValidation(AppConstants.SMS_USERNAME, AppConstants.SMS_PASSWORD, phoneNumber, AppConstants.SMS_MESSAGE + OTPCode, AppConstants.SMS_SENDERID);
		if( true ){
			Connection conn = null;
			conn = DBConnectionUtil.getConnection();
			PreparedStatement stmt = null;
			String sql = QueryConstants.QUERY_AUTHORIZE_BUSINESS_USER;
			try {
				conn.setAutoCommit(false);
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setLong(1, phoneNumber);
				stmt.setString(2, password);
				stmt.setInt(3, Integer.parseInt(OTPCode));
				stmt.setString(4, gcm_reg_key);
				System.out.println("final sql query=="+stmt.toString());
				int i = stmt.executeUpdate();
				if (i > 0){
					ResultSet rs = stmt.getGeneratedKeys();
					while(rs.next()){
						System.out.println("id=="+rs.getInt("restaurant_id"));
						businessUserDetails = new BusinessUser(rs.getInt("restaurant_id"), phoneNumber);
					}
				}else {
					System.out.println("record not inserted");
					throw new AppException(Response.Status.NOT_IMPLEMENTED.getStatusCode(), 500, AppConstants.ERROR_SQL_QUERY_EXECUTION,
							"", "");
				}
				conn.commit();
				return businessUserDetails;
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
		}
		return businessUserDetails;
		
	}
	
	public boolean validateotpBusinessUser(String restaurant_id, String otpcode) throws AppException{
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_VALIDATE_RESTAURANT_OTPCODE;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(otpcode));
			System.out.println("final sql query=="+stmt.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("got row");
				System.out.println("id=="+rs.getInt("restaurant_id"));
				return true;
			}
	         rs.close();
	         stmt.close();
	         conn.close();
	         return false;
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
		return false;
	}

}
