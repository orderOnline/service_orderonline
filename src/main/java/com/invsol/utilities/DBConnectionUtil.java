package com.invsol.utilities;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnectionUtil {
	
	public static Connection getConnection()
	{
		Context ctx;
		try {
			ctx = new InitialContext();
			Context envCtx = (Context)ctx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/db_orderonline");
			
			
			/*InitialContext ctx = new InitialContext();
			DataSource ds =(DataSource)ctx.lookup("java:comp/env/jdbc/indus");*/
			Connection conn = ds.getConnection();
			return conn;
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
