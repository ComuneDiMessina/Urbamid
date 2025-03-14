/**
 * 
 */
package it.eng.tz.urbamid.wrappergeo.geoserver.entities;


/**
 * @author vijay charan venkatachalam at vijaycharan@genegis.net
 *
 * <status>
    <module>system-environment</module>
    <name>system-environment</name>
    <component>system-environment</component>
    <message>PATH=/usr/bin:/bin:/usr/sbin:/sbin
    SHELL=/usr/local/bin/bash
    </message>
    <isEnabled>true</isEnabled>
    <isAvailable>true</isAvailable>
  </status>
 */
public class GSStatus {
	
	private String module;
	private String name;
	//private String component;
	private String message;
	private Boolean isEnabled;
	private Boolean isAvailable;
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public Boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	
	

}
