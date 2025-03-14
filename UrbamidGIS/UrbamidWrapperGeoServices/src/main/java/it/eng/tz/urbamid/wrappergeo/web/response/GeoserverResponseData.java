package it.eng.tz.urbamid.wrappergeo.web.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoserverResponseData implements Serializable 
{

	private static final long serialVersionUID = -4505289972757137933L;

	private boolean success = false;

	@JsonProperty("aaData")
	private Object aaData;

	private int iTotalRecords;

	// Righe totali dopo applicazioni filtri
	private int iTotalDisplayRecords;

	private String message;

	private String sEcho;

	public boolean isSuccess()
	{
		return success;
	}

	public GeoserverResponseData()
	{

	}

	public GeoserverResponseData(boolean success, Object aaData, int iTotalRecords, int iTotalDisplayRecords, String sEcho)
	{
		super();
		this.success = success;
		this.aaData = aaData;
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		this.sEcho = sEcho;
	}

	public GeoserverResponseData(boolean success, Object aaData)
	{
		super();
		this.success = success;
		this.aaData = aaData;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public Object getAaData()
	{
		return aaData;
	}

	public void setAaData(Object aaData)
	{
		this.aaData = aaData;
	}

	public int getiTotalRecords()
	{
		return iTotalRecords;
	}

	/**
	 * NOT USE. USE setiTotalDisplayRecords
	 * 
	 * @param iTotalRecords
	 */
	@Deprecated()
	public void setiTotalRecords(int iTotalRecords)
	{
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords()
	{
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords)
	{
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getsEcho()
	{
		return sEcho;
	}

	public void setsEcho(String sEcho)
	{
		this.sEcho = sEcho;
	}

}

