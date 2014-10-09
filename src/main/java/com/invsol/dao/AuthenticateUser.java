package com.invsol.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.invsol.constants.QueryConstants;
import com.invsol.utilities.DBConnectionUtil;

public class AuthenticateUser {
	
	public AuthenticateUser(){
		
	}
	
	public void authenticateBusinessUser(int phoneNumber, String password){
		Connection conn = null;

		conn = DBConnectionUtil.getConnection();

		String s = null;
		String must_change_password = "no";

		PreparedStatement stmt = null;

		String sql = QueryConstants.QUERY_AUTHORIZE_BUSINESS_USER;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, phoneNumber);
			stmt.setString(2, password);
			int i = stmt.executeUpdate();
			if (i > 0)
			{
				System.out.println("inserted");
			} else {
				System.out.println("not inserted");
			}
			conn.commit();
		} catch (SQLException se) {

			se.printStackTrace();

		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se2) {

				se2.printStackTrace();
			}
		}
	}
}
