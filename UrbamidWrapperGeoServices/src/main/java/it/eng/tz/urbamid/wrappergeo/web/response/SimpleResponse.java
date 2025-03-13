/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.web.response;

/**
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
public class SimpleResponse {
	
	private int statusCode;
	private String responseString;
	
	public SimpleResponse(int statusCode, String responseString) {
		this.statusCode = statusCode;
		this.responseString = responseString;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getResponseString() {
		return responseString;
	}
	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

}
