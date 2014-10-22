package com.invsol.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import com.invsol.constants.AppConstants;
import com.invsol.constants.QueryConstants;
import com.invsol.dto.CuisineDataObject;
import com.invsol.errorhandling.AppException;
import com.invsol.utilities.DBConnectionUtil;

public class CuisineData {
	
	public CuisineData(){
		
	}

	public CuisineDataObject[] getAllCuisines() throws AppException{
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		
		//Code for getting row counts
		int rowCount = getCuisinesCount(conn);
		CuisineDataObject[] data = new CuisineDataObject[rowCount];
		
		Statement stmt = null;
		String sql = QueryConstants.QUERY_GET_LIST_OF_ALL_CUISINES;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			int counter = 0;
	         while ( rs.next() ) {
	            int id = rs.getInt("cusine_id");
	            String  name = rs.getString("cusine_name");
	            System.out.println( "ID = " + id );
	            System.out.println( "NAME = " + name );
	            data[counter] = new CuisineDataObject(id, name);
	            counter++;
	         }
	         rs.close();
	         stmt.close();
	         conn.close();
	         return data;
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
		return null;
	}
	
	private int getCuisinesCount( Connection conn ) throws AppException{
		Statement stmt = null;
		String sql = QueryConstants.QUERY_GET_COUNT_CUISINES;
		int count = 0;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			while(rs.next()) {
				   count = rs.getInt("COUNT");
				   System.out.println("count is=="+count);
				}
	         rs.close();
	         stmt.close();
	         return count;
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
		}
		return count;
	}
}
