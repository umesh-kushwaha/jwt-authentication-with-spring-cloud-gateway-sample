package com.umesh.auth.repo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class DataStore {

	private static final Map<String, String> USERAPIENTITY = new HashMap<>();

	private static final Map<String, String> USERINFOENTITY = new HashMap<>();

	private static final Map<String, String> USERROLEENTITY = new HashMap<>();

	static {
		USERAPIENTITY.put("1231", "u1");
		USERAPIENTITY.put("7891", "u2");
		USERAPIENTITY.put("8901", "u3");

		USERINFOENTITY.put("u1", "name1");
		USERINFOENTITY.put("u2", "name2");
		USERINFOENTITY.put("u3", "name3");

		USERROLEENTITY.put("u1", "ROLE_USER");
		USERROLEENTITY.put("u2", "ROLE_ADMIN");
		USERROLEENTITY.put("u3", "ROLE_MANAGER");

	}

	public String getUserIdForApikey(final String apiKey) {
		return USERAPIENTITY.get(apiKey);
	}

	public String getUserInfo(final String userId) {
		return USERINFOENTITY.get(userId);
	}

	public String getUserRole(final String userId) {
		return USERROLEENTITY.get(userId);
	}

}
