package it.eng.tz.urbamid.core.service;

import java.util.List;

import it.eng.tz.urbamid.core.model.UserProfile;



public interface UserProfileService {

	UserProfile findById(int id);

	UserProfile findByType(String type);
	
	List<UserProfile> findAll();
	
}
