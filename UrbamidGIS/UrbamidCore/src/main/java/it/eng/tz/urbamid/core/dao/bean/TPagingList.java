package it.eng.tz.urbamid.core.dao.bean;

import java.util.List;

/**
 * Bean di supporto per la paginazione di una entity
 */
public class TPagingList<E>
{

	public TPagingList()
	{
		super();
	}

	public TPagingList(List<E> data, int totalrecord)
	{
		super();
		this.totalrecord = totalrecord;
		this.data = data;
	}

	/**
	 * 
	 */
	private int totalrecord;
	/**
	 * 
	 */
	private List<E> data;

	/**
	 * @return the totalrecord
	 */
	public int getTotalrecord()
	{
		return totalrecord;
	}

	/**
	 * @param totalrecord
	 *            the totalrecord to set
	 */
	public void setTotalrecord(int totalrecord)
	{
		this.totalrecord = totalrecord;
	}

	/**
	 * @return the data
	 */
	public List<E> getData()
	{
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<E> data)
	{
		this.data = data;
	}
}
