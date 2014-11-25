package com.invsol.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import com.invsol.constants.AppConstants;
import com.invsol.constants.QueryConstants;
import com.invsol.dto.CategoryDataObject;
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
	
	public CuisineDataObject addNewCuisine(String cuisineName) throws AppException {
		CuisineDataObject newCat = null;
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_INSERT_CUISINE_DETAILS;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cuisineName);
			System.out.println("final sql query==" + stmt.toString());
			int i = stmt.executeUpdate();
			if (i > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				while(rs.next()){
					System.out.println("id=="+rs.getInt("cuisine_id"));
					newCat = new CuisineDataObject(rs.getInt("cuisine_id"), cuisineName);
				}
			} else {
				System.out.println("record not inserted");
				throw new AppException(Response.Status.NOT_IMPLEMENTED.getStatusCode(), 500,
						AppConstants.ERROR_SQL_QUERY_EXECUTION, "", "");
			}
			conn.commit();
			return newCat;
		} catch (SQLException se) {
			System.out.println("got se===" + se.getMessage());
			if (se.getMessage().contains("duplicate")) {
				throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 401,
						AppConstants.ERROR_CUISINE_ALREADY_EXIST, se.getMessage(), "");
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
				throw new AppException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), 500,
						AppConstants.ERROR_SQL_QUERY_EXECUTION, "", "");
			}
		}
		return newCat;
	}
	
	public boolean updateCuisine(int cuisineID, String cuisineName) throws AppException {
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_UPDATE_CUISINE;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, cuisineName);
			stmt.setInt(2, cuisineID);
			System.out.println("final sql query==" + stmt.toString());
			int i = stmt.executeUpdate();
			if (i > 0) {
				System.out.println("record updated");
				conn.commit();
				return true;
			} else {
				System.out.println("record not inserted");
				conn.commit();
				throw new AppException(Response.Status.NOT_IMPLEMENTED.getStatusCode(), 500,
						AppConstants.ERROR_SQL_QUERY_EXECUTION, "", "");
			}
		} catch (SQLException se) {
			System.out.println("got se===" + se.getMessage());
			if (se.getMessage().startsWith("Duplicate")) {
				throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 401,
						AppConstants.ERROR_USER_ALREADY_REGISTERED, se.getMessage(), "");
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
				throw new AppException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), 500,
						AppConstants.ERROR_SQL_QUERY_EXECUTION, "", "");
			}
		}
		return false;
	}
	
	public boolean deleteCuisine(int cuisineID) throws AppException{
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_DELETE_CUISINE;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cuisineID);
			stmt.executeUpdate();
			conn.commit();
	        stmt.close();
	        conn.close();
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
		}
		return false;
	}
}
