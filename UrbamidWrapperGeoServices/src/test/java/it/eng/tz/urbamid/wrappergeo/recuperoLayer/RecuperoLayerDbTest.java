package it.eng.tz.urbamid.wrappergeo.recuperoLayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.urbamid.wrappergeo.web.entities.ResLayers;

/**
 * Classe di test per shp2pgsql eseguito via Apache Common Exec
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecuperoLayerDbTest {
	
	protected String GEOSERVER_ENDPOINT								= "https://urbamidplus-collaudo.comune.messina.it/geoserver";
	protected static final String GEOSERVER_WORKSPACES				= "/workspaces/";
	protected static final String GEOSERVER_DATASTORES				= "/datastores/";
	protected static final String GEOSERVER_TABLE_SUFFIX			= "u_geo_";
	protected static final String GEOSERVER_NATIVECRS				= "EPSG:4326";
	protected static final String GEOSERVER_SRS						= "EPSG:4326";			
	protected static final boolean GEOSERVER_ENABLED				= true;
	
	public final String METHOD_DELETE 	= "DELETE";
	public final String METHOD_POST 	= "POST";
	public final String METHOD_PUT 		= "PUT";
	public final String METHOD_GET 		= "GET";
	
	@Test
	public void test_1() {
		
		System.out.println("INIZIO ESECUZIONE");
		String nameWs = "urbamid";

		try {
			
			System.out.println("Verranno recuperati i layers del workspaces " + nameWs);
			String response = this.gsGetLayers();
			if( !response.isEmpty() ) {
				JSONObject obj = new JSONObject(response);
				JSONArray list = obj.getJSONObject("aaData").getJSONObject("layers").getJSONArray("layer");
				for (int i = 0; i<list.length(); i++) {
					JSONObject lay = list.getJSONObject(i);
					String responseLay = restCall(METHOD_GET, lay.getString("href"), "", "application/json", "application/json");
					
					JSONObject objDet = new JSONObject(responseLay);
					String nameDet = objDet.getJSONObject("layer").getJSONObject("resource").getString("name");
					String hrefDet = objDet.getJSONObject("layer").getJSONObject("resource").getString("href");
					String responseDetLay = restCall(METHOD_GET, hrefDet, "", "application/json", "application/json");
					System.out.println(nameDet+":"+responseDetLay);
				}
				System.out.println("Sono stati recuperati i layers del workspaces " + nameWs);
			}
		} catch (Exception e) {
			
			System.out.println("WRAPPERGEO SERVICE --> Non è stato possibile recuperare i layers del workspace");
		}
		
		System.out.println("FINE ESECUZIONE");
	}
	
	public String gsGetLayers() throws IOException {

		String url = "http://192.168.131.145:8084/urbamid/wrappergeo/rest/api/geoserver/layers?workspaces=urbamid";
		String response = restCall(METHOD_GET, url, "");
		return response;
	}
	
	public String restCall(String method, String url, String xmlPostContent) throws IOException {
		return restCall(method, url, xmlPostContent, "application/json", "application/json");
	}
	
	public String restCall(String method, String urlEncoded, String postData, String contentType, String accept)
			throws IOException {
		
		boolean doOut = !METHOD_DELETE.equals(method) && postData != null;

		URL url = new URL(urlEncoded);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(doOut);
		if (contentType != null && !"".equals(contentType)) {
			connection.setRequestProperty("Content-type", contentType);
			connection.setRequestProperty("Content-Type", contentType);
		}
		if (accept != null && !"".equals(accept)) {
			connection.setRequestProperty("Accept", accept);
		}
		connection.setRequestMethod(method.toString());

		connection.connect();
	    StringBuffer chaine = new StringBuffer("");
		InputStream inputStream = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        while ((line = rd.readLine()) != null) {
            chaine.append(line);
        }
		return chaine.toString();
	}
	
}
