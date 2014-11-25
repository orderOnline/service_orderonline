package com.invsol.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import com.invsol.constants.AppConstants;
import com.invsol.constants.QueryConstants;
import com.invsol.dto.MenuDataObject;
import com.invsol.errorhandling.AppException;
import com.invsol.utilities.DBConnectionUtil;

public class MenuItemData {
	
	public MenuItemData(){
		
	}
	
	public MenuDataObject addMenuItem(int category_id, String item_name, int price)throws AppException{
		MenuDataObject newMenuItem = null;
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_INSERT_MENUITEM_DETAILS;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, item_name);
			stmt.setInt(2, price);
			stmt.setInt(4, category_id);
			System.out.println("final sql query==" + stmt.toString());
			int i = stmt.executeUpdate();
			if (i > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				while(rs.next()){
					System.out.println("id=="+rs.getInt("item_id"));
					newMenuItem = new MenuDataObject(rs.getInt("item_id"), item_name, price, category_id);
				}
			} else {
				System.out.println("record not inserted");
				throw new AppException(Response.Status.NOT_IMPLEMENTED.getStatusCode(), 500,
						AppConstants.ERROR_SQL_QUERY_EXECUTION, "", "");
			}
			conn.commit();
			return newMenuItem;
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
		return newMenuItem;
	}
	
	public MenuDataObject[] getMenuItems(int category_id) throws AppException{
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		
		//Code for getting row counts
		int rowCount = getMenuItemsCount(conn, category_id);
		MenuDataObject[] data = new MenuDataObject[rowCount];
		
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_SELECT_CATEGORY_MENUITEMS;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, category_id);
			ResultSet rs = stmt.executeQuery();
			int counter = 0;
	         while ( rs.next() ) {
	            int id = rs.getInt("item_id");
	            String  name = rs.getString("name");
	            int price = rs.getInt("price");
	            System.out.println( "ID = " + id );
	            System.out.println( "NAME = " + name );
	            data[counter] = new MenuDataObject(id, name, price, category_id);
	            counter++;
	         }
	         rs.close();
	         stmt.close();
	         conn.close();
	         return data;
		} catch (SQLException se) {
			System.out.println("got se==="+se.getMessage());
			if(se.getMessage().contains("duplicate")){
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

	private int getMenuItemsCount( Connection conn, int category_id ) throws AppException{
		int count = 0;
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_GET_COUNT_MENUITEMS;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, category_id);
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
			if (se.getMessage().contains("duplicate")) {
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
	
	public boolean updateMenuItem(int itemID, String itemName, int itemPrice) throws AppException {
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_UPDATE_MENUITEM;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, itemName);
			stmt.setInt(2, itemPrice);
			stmt.setInt(3, itemID);
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
	
	public boolean deleteMenuItem(int menuItemID) throws AppException{
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_DELETE_MENUITEM;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, menuItemID);
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
