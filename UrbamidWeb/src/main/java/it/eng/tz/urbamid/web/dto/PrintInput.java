package it.eng.tz.urbamid.web.dto;

import java.util.HashMap;
import java.util.Map;

public class PrintInput {

	private String imageDir;
	private String imageName;
	
	public Map<String, Object> getDataSources() {
		
       Map<String,Object> dataSources = new HashMap<>();
       dataSources.put("imageDir", imageDir);
       dataSources.put("imageName", imageName);
       return dataSources;
	}

	public String getImageDir() {
		return imageDir;
	}

	public void setImageDir(String imageDir) {
		this.imageDir = imageDir;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}

