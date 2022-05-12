package com.revature.services;

import com.revature.models.User;

import java.util.List;

public interface UserServices {

    public User login(String username, String password);
	
	public int register(User user);
	
	public User findUserById(int id);
	
	public List<User> findAllUsers();
	
	public boolean editUser(User user);
	
	public boolean deleteUser(User user);
    
}