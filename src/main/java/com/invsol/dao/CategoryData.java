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
import com.invsol.dto.CategoryDataObject;
import com.invsol.errorhandling.AppException;
import com.invsol.utilities.DBConnectionUtil;

public class CategoryData {

	public CategoryData() {

	}
	
	public CategoryDataObject[] getCategories(int restaurant_id) throws AppException{
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		
		//Code for getting row counts
		int rowCount = getCategoriesCount(conn, restaurant_id);
		CategoryDataObject[] data = new CategoryDataObject[rowCount];
		
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_SELECT_RESTAURANT_CATEGORIES;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, restaurant_id);
			ResultSet rs = stmt.executeQuery();
			int counter = 0;
	         while ( rs.next() ) {
	            int id = rs.getInt("category_id");
	            String  name = rs.getString("category_name");
	            System.out.println( "ID = " + id );
	            System.out.println( "NAME = " + name );
	            data[counter] = new CategoryDataObject(id, name);
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

	public CategoryDataObject addNewCategory(String categoryName) throws AppException {
		CategoryDataObject newCat = null;
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_INSERT_CATEGORY_DETAILS;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, categoryName);
			System.out.println("final sql query==" + stmt.toString());
			int i = stmt.executeUpdate();
			if (i > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				while(rs.next()){
					System.out.println("id=="+rs.getInt("category_id"));
					newCat = new CategoryDataObject(rs.getInt("category_id"), categoryName);
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
		return newCat;
	}
	
	public boolean updateCategory(int categoryID, String categoryName) throws AppException {
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_UPDATE_CATEGORY;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, categoryName);
			stmt.setInt(2, categoryID);
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
	
	private int getCategoriesCount( Connection conn, int restaurant_id ) throws AppException{
		int count = 0;
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_GET_COUNT_CATEGORIES;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, restaurant_id);
			System.out.println("final sql query==" + stmt.toString());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				   count = rs.getInt("COUNT");
				   System.out.println("count is=="+count);
				}
	         rs.close();
	         stmt.close();
	         return count;
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
		}
		return count;
	}
}
