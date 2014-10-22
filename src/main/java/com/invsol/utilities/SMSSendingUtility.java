package com.invsol.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SMSSendingUtility {

	public static boolean sendOTPCodeforValidation(String username, String password, String number, String message,
			String senderID) {
		try {
			String response = new String();
			// Construct The Post Data
			String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
			data += "&" + URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
			data += "&" + URLEncoder.encode("to", "UTF-8") + "=" + URLEncoder.encode(number, "UTF-8");
			data += "&" + URLEncoder.encode("msg", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");
			data += "&" + URLEncoder.encode("sid", "UTF-8") + "=" + URLEncoder.encode(senderID, "UTF-8");
			data += "&" + URLEncoder.encode("fl", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
			// Push the HTTP Request
			URL url = new URL("http://login.smsgatewayhub.com/smsapi/pushsms.aspx");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			// Read The Response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				// Process line…
				response += line;
			}
			wr.close();
			rd.close();
			System.out.println(response);
			return getSMSDeliveryReport(username, password, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static boolean getSMSDeliveryReport(String username, String password, String messageid) {
		try {
			String response = new String();
			// Construct The Post Data
			String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
			data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
			data += "&" + URLEncoder.encode("messageid", "UTF-8") + "=" + URLEncoder.encode(messageid, "UTF-8");
			// Push the HTTP Request
			URL url = new URL("http://login.smsgatewayhub.com/smsapi/checkdelivery.aspx");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			// Read The Response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				// Process line…
				response += line;
			}
			wr.close();
			rd.close();
			System.out.println("delivery report==="+response);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
