package it.eng.tz.urbamid.wrappergeo.service;

import java.util.Map;

import it.eng.tz.urbamid.wrappergeo.web.response.BaseResponse;
import it.eng.tz.urbamid.wrappergeo.web.response.ResponseData;


public interface RestService {

	public BaseResponse restGetPathParam(String url, Map<String, String> params);
	
	public BaseResponse restGetPathParam(String baseUrl, String method, Map<String, String> params);
	
	public BaseResponse restGet(String url);

	public ResponseData restGetRD(String url);
	
	public BaseResponse restGet(String baseUrl, String method);
	
	public BaseResponse restPost(String url, Object entity);
	
	public ResponseData restPostTable(String url, Object entity);
	
	public BaseResponse restPost(String baseUrl, String method, Object entity);
	
//	public BaseResponse restPut(String url, Object entity, Map<String, String> params);
//	
//	public BaseResponse restPut(String baseUrl, String method, Object entity, Map<String, String> params);
}
