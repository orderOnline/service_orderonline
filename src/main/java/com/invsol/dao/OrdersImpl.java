package com.invsol.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.invsol.constants.AppConstants;
import com.invsol.constants.QueryConstants;
import com.invsol.dto.CuisineDataObject;
import com.invsol.dto.OrderDataObject;
import com.invsol.errorhandling.AppException;
import com.invsol.utilities.DBConnectionUtil;

public class OrdersImpl {

	public OrdersImpl() {

	}

	public int createNewOrder(int restaurant_id, int consumer_id, long timestamp, String instructions,
			int orderTotal, JSONArray itemsArray) throws AppException {
		int order_id_gen = -1;
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		PreparedStatement stmt = null;
		String sql = QueryConstants.QUERY_INSERT_ORDER_DETAILS;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, restaurant_id);
			stmt.setInt(2, consumer_id);
			stmt.setTimestamp(3, new Timestamp(timestamp));
			stmt.setString(4, instructions);
			stmt.setInt(5, orderTotal);
			System.out.println("final sql query==" + stmt.toString());
			int i = stmt.executeUpdate();
			if (i > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				while(rs.next()){
					System.out.println("id=="+rs.getInt("order_id"));
					order_id_gen = rs.getInt("order_id");
					sql = QueryConstants.QUERY_INSERT_ORDERITEMS;
					conn.setAutoCommit(false);
					for( int index = 0; index < itemsArray.length(); index++){
						stmt = conn.prepareStatement(sql);
						stmt.setInt(1, (itemsArray.getJSONObject(index)).getInt(AppConstants.JSON_ITEM_ID));
						stmt.setInt(2, (itemsArray.getJSONObject(index)).getInt(AppConstants.JSON_QUANTITY));
						stmt.setInt(3, order_id_gen);
						int rowIndex = stmt.executeUpdate();
						if( rowIndex > 0){
							System.out.println("record inserted");
						}else{
							System.out.println("record not inserted");
						}
					}
				}
			} else {
				System.out.println("record not inserted");
				throw new AppException(Response.Status.NOT_IMPLEMENTED.getStatusCode(), 500,
						AppConstants.ERROR_SQL_QUERY_EXECUTION, "", "");
			}
			conn.commit();
			return order_id_gen;
		} catch (SQLException se) {
			System.out.println("got se===" + se.getMessage());
			if (se.getMessage().startsWith("Duplicate")) {
				throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 401,
						AppConstants.ERROR_USER_ALREADY_REGISTERED, se.getMessage(), "");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return order_id_gen;
	}
	
	public OrderDataObject getOrderGCMDetails( int orderID ) throws AppException{
		OrderDataObject dataObj = null;
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		
		Statement stmt = null;
		String sql = QueryConstants.QUERY_GET_ORDER_DETAILS;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
	         while ( rs.next() ) {
	            dataObj = new OrderDataObject();
	            dataObj.setConsumer_name(rs.getString("name"));
	            dataObj.setAddress(rs.getString("address") + " " + rs.getString("city") + " " + rs.getString("state") + " " + rs.getInt("zipcode"));
	            dataObj.setInstructions(rs.getString("instruction"));
	            dataObj.setTimestamp(rs.getTimestamp("timestamp").toString());
	            sql = QueryConstants.QUERY_GET_ORDERMENUITEMS_DETAILS;
	            conn.setAutoCommit(false);
				stmt = conn.createStatement();
				ResultSet records = stmt.executeQuery( sql );
				JSONArray itemsArray = new JSONArray();
				JSONObject tempItem = null;
		         while ( records.next() ) {
		        	 tempItem = new JSONObject();
		        	 tempItem.put("itemname", records.getString("name"));
		        	 tempItem.put("quantity", records.getInt("quantity"));
		        	 itemsArray.put(tempItem);
		         }
	            dataObj.setItemsArray(itemsArray);
	         }
	         conn.commit();
	         rs.close();
	         stmt.close();
	         conn.close();
	         return dataObj;
		} catch (SQLException se) {
			System.out.println("got se==="+se.getMessage());
			if(se.getMessage().startsWith("Duplicate")){
				throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 401, AppConstants.ERROR_USER_ALREADY_REGISTERED,
				se.getMessage(), "");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return dataObj;
	}
	
	public String getBusinessGCMRegKey(int restaurant_Id) throws AppException{
		String gcm_reg_key = new String();
		Connection conn = null;
		conn = DBConnectionUtil.getConnection();
		
		Statement stmt = null;
		String sql = QueryConstants.QUERY_GET_GCM_KEY_RESTAURANT;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
	         while ( rs.next() ) {
	            gcm_reg_key = rs.getString("gcm_reg_key");
	         }
	         rs.close();
	         stmt.close();
	         conn.close();
	         return gcm_reg_key;
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
		return gcm_reg_key;
	}
}
