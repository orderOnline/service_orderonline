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
import com.invsol.dto.MenuDataObject;
import com.invsol.errorhandling.AppException;
import com.invsol.utilities.DBConnectionUtil;

public class MenuItemData {
	
	public MenuItemData(){
		
	}
	
	public MenuDataObject addMenuItem(int cuisine_id, int category_id, String item_name, int price)throws AppException{
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
			stmt.setInt(3, cuisine_id);
			stmt.setInt(4, category_id);
			System.out.println("final sql query==" + stmt.toString());
			int i = stmt.executeUpdate();
			if (i > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				while(rs.next()){
					System.out.println("id=="+rs.getInt("item_id"));
					newMenuItem = new MenuDataObject(rs.getInt("item_id"), item_name, price, cuisine_id, category_id);
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

}
