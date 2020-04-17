package com.techelevator.model.User;

import java.util.List;

public interface UserDAO {

	public void saveUser(String userName, String password);

	public boolean searchForUsernameAndPassword(String userName, String password);

	public void updatePassword(String userName, String password, String newPassword);

	public Object getUserByUserName(String userName);

	public String getRoleByUserName(String userName);

	public void updateRole(String userName, String role);

	public boolean verifyUserIsntInDatabase(String userName);

	public int getIdByUserName(String userName);
	
	public List<User> getAllUsers();
	
	List<User> getGolfersByTeamId(int teamId);

}