package it.eng.tz.urbamid.core.config.util;

import java.io.Serializable;

public class KeyInfo implements Serializable
{

	private static final long serialVersionUID = -5314725122242564475L;
	private String nomeKeyStore;
	private String passwordKeyStore;

	public KeyInfo()
	{
		super();
	}
	public KeyInfo(String nomeKeyStore, String passwordKeyStore)
	{
		super();
		this.nomeKeyStore = nomeKeyStore;
		this.passwordKeyStore = passwordKeyStore;
	}
	public String getNomeKeyStore()
	{
		return nomeKeyStore;
	}
	public void setNomeKeyStore(String nomeKeyStore)
	{
		this.nomeKeyStore = nomeKeyStore;
	}
	public String getPasswordKeyStore()
	{
		return passwordKeyStore;
	}
	public void setPasswordKeyStore(String passwordKeyStore)
	{
		this.passwordKeyStore = passwordKeyStore;
	}

	@Override
	public String toString()
	{
		return "KeyStoreInfo [nomeKeyStore=" + nomeKeyStore + ", passwordKeyStore=" + passwordKeyStore + "]";
	}
}