package edu.northeastern.csye6200.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.csye6200.constant.Constants;
import edu.northeastern.csye6200.service.AdminService;

public class AdminServiceImpl implements AdminService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	private static AdminService instance;
	
	private AdminServiceImpl() {
		LOGGER.debug("Initiliasing the service");
	}
	
	public static AdminService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new AdminServiceImpl();
		return instance;
	}
	
	@Override
	public boolean isValidAdminPassword(String password) {
		LOGGER.trace("checking valid admin password for: {}", password);
		boolean passwordMatch = password.equals(Constants.DEFAULT_ADMIN_PASSWORD);
		LOGGER.trace("admin password: {} is valid?: {}", password, passwordMatch);
		return passwordMatch;
	}

}
