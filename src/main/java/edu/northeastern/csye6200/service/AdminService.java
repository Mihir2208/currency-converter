package edu.northeastern.csye6200.service;

public interface AdminService {
	/**
	 * Will validate that the given admin password
	 * is valid
	 * */
	boolean isValidAdminPassword(String password);
}
