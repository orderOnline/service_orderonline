package com.invsol.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.ws.rs.core.Response;

import com.invsol.constants.AppConstants;
import com.invsol.constants.QueryConstants;
import com.invsol.errorhandling.AppException;
import com.invsol.utilities.DBConnectionUtil;
import com.invsol.utilities.OTP_Generation;

public class ForgotPasswordImpl {

	public ForgotPasswordImpl() {

	}

	public boolean generateNewOTP(long phonenumber) throws AppException{
		// Method call to send OTP Code to user.
		String OTPCode = OTP_Generation.generateRandomOTP(6);
		// boolean smsSent =
		// SMSSendingUtility.sendOTPCodeforValidation(AppConstants.SMS_USERNAME,
		// AppConstants.SMS_PASSWORD, phoneNumber, AppConstants.SMS_MESSAGE +
		// OTPCode, AppConstants.SMS_SENDERID);
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_UPDATE_RESTAURANT_OTPCODE;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(OTPCode));
			stmt.setLong(2, phonenumber);
			System.out.println("final sql query=="+stmt.toString());
			int i = stmt.executeUpdate();
			if (i > 0){
				System.out.println("record updated");
			}else {
				System.out.println("record not updated");
				throw new AppException(Response.Status.NOT_IMPLEMENTED.getStatusCode(), 500, AppConstants.ERROR_SQL_QUERY_EXECUTION,
						"", "");
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
