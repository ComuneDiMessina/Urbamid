package it.eng.tz.urbamid.web.dao.bean.search;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

public class RequestData implements Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Display start point in the current data set.
	 */
	private int iDisplayStart;

	/**
	 * Number of records that the table can display in the current draw. It is
	 * expected that the number of records returned will be equal to this number,
	 * unless the server has fewer records to return.
	 */
	private int iDisplayLength;

	/**
	 * Global search field
	 */
	private String sSearch;

	/**
	 * Column being sorted on (you will need to decode this number for your
	 * database)
	 */
	private int iSortCol_0;

	/**
	 * Direction to be sorted - "desc" or "asc".
	 */
	private String sSortDir_0;

	/**
	 * Information for DataTables to use for rendering.
	 */
	private String sEcho;

	/**
	 * Se e' una query paginata
	 **/
	private boolean isPaginate;

	/**
	 * Linguaggio per il filtri (IT, EN)
	 */
	private Locale locale;

	/**
	 *
	 */
	@SuppressWarnings("rawtypes")
	private Map data;

	/**
	 * @return the iDisplayStart
	 */
	public int getiDisplayStart()
	{
		return iDisplayStart;
	}

	/**
	 * @param iDisplayStart
	 *            the iDisplayStart to set
	 */
	public void setiDisplayStart(int iDisplayStart)
	{
		this.iDisplayStart = iDisplayStart;
	}

	/**
	 * @return the iDisplayLength
	 */
	public int getiDisplayLength()
	{
		return iDisplayLength;
	}

	public int getiDisplayEnd()
	{
		return iDisplayLength + iDisplayStart;
	}

	/**
	 * @param iDisplayLength
	 *            the iDisplayLength to set
	 */
	public void setiDisplayLength(int iDisplayLength)
	{
		this.iDisplayLength = iDisplayLength;
	}

	/**
	 * @return the sSearch
	 */
	public String getsSearch()
	{
		return sSearch;
	}

	/**
	 * @param sSearch
	 *            the sSearch to set
	 */
	public void setsSearch(String sSearch)
	{
		this.sSearch = sSearch;
	}

	/**
	 * @return the iSortCol_0
	 */
	public int getiSortCol_0()
	{
		return iSortCol_0;
	}

	/**
	 * @param iSortCol_0
	 *            the iSortCol_0 to set
	 */
	public void setiSortCol_0(int iSortCol_0)
	{
		this.iSortCol_0 = iSortCol_0;
	}

	/**
	 * @return the sSortDir_0
	 */
	public String getsSortDir_0()
	{
		return sSortDir_0;
	}

	/**
	 * @param sSortDir_0
	 *            the sSortDir_0 to set
	 */
	public void setsSortDir_0(String sSortDir_0)
	{
		this.sSortDir_0 = sSortDir_0;
	}

	/**
	 * @return the sEcho
	 */
	public String getsEcho()
	{
		return sEcho;
	}

	/**
	 * @param sEcho
	 *            the sEcho to set
	 */
	public void setsEcho(String sEcho)
	{
		this.sEcho = sEcho;
	}

	/**
	 * @return the isPaginate
	 */
	public boolean isPaginate()
	{
		return isPaginate;
	}

	/**
	 * @param isPaginate
	 *            the isPaginate to set
	 */
	public void setPaginate(boolean isPaginate)
	{
		this.isPaginate = isPaginate;
	}

	/**
	 * @return the data
	 */
	@SuppressWarnings("rawtypes")
	public Map getData()
	{
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	@SuppressWarnings("rawtypes")
	public void setData(Map data)
	{
		this.data = data;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale()
	{
		return locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}
}
