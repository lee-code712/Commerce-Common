package com.digital.v3.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.digital.v3.exception.ResErrorCodeException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpConnectionUtils {
	
	private static Map<String, String> baseUrls;
	
	static {		
		baseUrls = new HashMap<String, String>();
		baseUrls.put("API-G", "http://commerce-yr-gateway-svc");
		baseUrls.put("Auth", "http://commerce-yr-auth-svc");
		baseUrls.put("Inventory", "http://commerce-yr-inventory-svc");
		baseUrls.put("Product", "http://commerce-yr-product-svc");
		baseUrls.put("Person", "http://commerce-yr-person-svc");
		baseUrls.put("Order", "http://commerce-yr-order-svc");
	}
	
	public static String requestGET (String baseUrlKey, String targetUrl) 
			throws ResErrorCodeException {
		
		String response = "";
		HttpURLConnection conn = null;
		
		try {
			URL url = new URL(baseUrls.get(baseUrlKey) + targetUrl);
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				response = getResponseBody(conn);
			}
			else {
				response = getResponseError(conn);
				throw new ResErrorCodeException(response);
			}
		} catch (ResErrorCodeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return response;
	}
	
	public static String requestGET (String baseUrlKey, String targetUrl, String token) 
			throws ResErrorCodeException {
		
		String response = "";
		HttpURLConnection conn = null; 
		
		try {
			URL url = new URL(baseUrls.get(baseUrlKey) + targetUrl);
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setRequestProperty("Authorization", token); // Authorization header 추가
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				response = getResponseBody(conn);
			}
			else {
				response = getResponseError(conn);
				throw new ResErrorCodeException(response);
			}
		} catch (ResErrorCodeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		
		return response;
	}
	
	public static String requestPOST (String baseUrlKey, String targetUrl, Object reqObject) 
			throws ResErrorCodeException {
		
		String response = "";
		HttpURLConnection conn = null; 

		try {
			URL url = new URL(baseUrls.get(baseUrlKey) + targetUrl);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setDoOutput(true); // request body 포함 설정
			
			setRequestBody(conn, reqObject);

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				response = getResponseBody(conn);
			}
			else {
				response = getResponseError(conn);
				throw new ResErrorCodeException(response);
			}
		} catch (ResErrorCodeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return response;
	}
	
	public static String requestPOST (String baseUrlKey, String targetUrl, Object reqObject, String token) 
			throws ResErrorCodeException {
		
		String response = "";
		HttpURLConnection conn = null; 
		
		try {
			URL url = new URL(baseUrls.get(baseUrlKey) + targetUrl);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setRequestProperty("Authorization", token); // Authorization header 추가
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setDoOutput(true);	// request body 포함 설정
			
			setRequestBody(conn, reqObject);
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				response = getResponseBody(conn);
			}
			else {
				response = getResponseError(conn);
				throw new ResErrorCodeException(response);
			}
		} catch (ResErrorCodeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return response;
	}
	
	// request body에 data set
	private static void setRequestBody (HttpURLConnection conn, Object reqObject) throws Exception {
		
		String requestBody = "";
        if (reqObject != null) {
        	requestBody = objectToJson(reqObject);
        }
        
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		bw.write(requestBody);
		bw.flush();
		bw.close();
	}
	
	// response body에서 data get
	private static String getResponseBody (HttpURLConnection conn) throws Exception {
		
		Charset charset = Charset.forName("UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
		
		String inputLine;			
		StringBuffer sb = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			sb.append(inputLine);
		}
		br.close();
		
		return sb.toString();
	}
	
	// response error에서 data get
	private static String getResponseError (HttpURLConnection conn) throws Exception {

		Charset charset = Charset.forName("UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), charset));

		String inputLine;
		StringBuffer sb = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			sb.append(inputLine);
		}
		br.close();

		return sb.toString();
	}
	
	// get 파라미터 utf-8로 인코딩
	public static String encodeParams(String params) throws Exception {

		return URLEncoder.encode(params, "UTF-8").replaceAll("\\+", "%20");
	}
	
	// jsonString을 특정 class의 Object로 변환
	public static Object jsonToObject (String jsonStr, Class<?> cl) throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		Object resObj = objectMapper.readValue(jsonStr, cl);
	
		return resObj;
	}
	
	// Object를 jsonString으로 변환
	public static String objectToJson (Object obj) throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
	    String jsonStr = objectMapper.writeValueAsString(obj);
	    
	    return jsonStr;
	}
}
