package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.*;
import com.revature.models.User;
import com.revature.services.*;

public class RequestHelper {

    private static UserServices uServ = new UserServicesImpl(new UserDAOImpl());
	private static Logger log = LogManager.getLogger(RequestHelper.class);
	private static ObjectMapper oMapper = new ObjectMapper();

	public static void processAllUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("in requesthelper. getting users...");
		resp.setContentType("application/json");

		List<User> aUsers = uServ.findAllUsers();

		String json = oMapper.writeValueAsString(aUsers);

		PrintWriter out = resp.getWriter();
		out.println(json);
		log.info("leaving the requesthelper....");
	}

	public static void processUserBySearchParam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("inside of request helper...searching user by param...");
		BufferedReader reader = req.getReader();
		StringBuilder sBuilder = new StringBuilder();

		String line = reader.readLine();
		while(line != null){
			sBuilder.append(line);
			line = reader.readLine();
		}

		String bodString  = sBuilder.toString();
		String [] sepByAmp = bodString.split("&");

		List<String> values = new ArrayList<>();

		for(String pair : sepByAmp){
			values.add(pair.substring(pair.indexOf('=') + 1));
		}
		log.info("User attempted to register with information:\n " + bodString);

		if(bodString.startsWith("id")) {
			resp.setContentType("application/json");

			int id = Integer.parseInt(values.get(0));
			User user = uServ.findUserById(id);

			String json = oMapper.writeValueAsString(user);

			PrintWriter out = resp.getWriter();
			out.println(json);
		}	
	
	}

	public static void processRegistration(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("Inside of request helper...processRegistration...");
		BufferedReader reader = req.getReader();
		StringBuilder sBuilder = new StringBuilder();

		String line = reader.readLine();
		while(line != null){
			sBuilder.append(line);
			line = reader.readLine();
		}

		String bodString = sBuilder.toString();
		String [] sepByAmp = bodString.split("&");

		List<String> values = new ArrayList<>();

		for(String pair : sepByAmp) {
			values.add(pair.substring(pair.indexOf("=")+1));

		}
		log.info("User attempted to register with information:|n " + bodString);

		String username = values.get(0);
		String password = values.get(1);

		User user = new User(0, username, password);
		int targetID = uServ.register(user);

		if(targetID!=0) {
			PrintWriter pWriter = resp.getWriter();
			user.setId(targetID);
			log.info("New user: " + user);
			String json = oMapper.writeValueAsString(user);
			pWriter.print(json);
			System.out.println("JSON:\n " + json);

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("User has successfully been created.");
		}
		else{
			resp.setContentType("application/json");
		}	resp.setStatus(204);
		log.info("Leaving request helper now...");
	}

	public static void processLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.info("Inside of request helper...processUserUpdate...");
		BufferedReader reader = req.getReader();
		StringBuilder sBuilder = new StringBuilder();

		String line = reader.readLine();
		while(line != null) {
			sBuilder.append(line);
			line = reader.readLine();
		}

		String bodString = sBuilder.toString();
		String [] sepByAmp = bodString.split("&");

		List<String> values = new ArrayList<>();

		for(String pair : sepByAmp) {
			values.add(pair.substring(pair.indexOf("=") + 1));
		}
		log.info("User attempted to update with information:\n " + bodString);

		int id = Integer.parseInt(values.get(0));
		String username = values.get(1);
		String password = values.get(2);


		User tempUser = new User();
		tempUser.setId(id);
		tempUser.setUsername(username);
		tempUser.setPassword(password);

		boolean	isPresent = uServ.findUserById(id) != null;
		boolean isLoggedIn = uServ.login(username, password) != null;

		if(isPresent && isLoggedIn) {
			PrintWriter printWriter = resp.getWriter();
			log.info("Found user by ID...Log-In successful: " + tempUser);
			String json = oMapper.writeValueAsString(tempUser);
			printWriter.println(json);
			System.out.println("JSON\n" + json);

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("User has successfully been logged in");
		
		}
		else{
			resp.setContentType("application/json");
			resp.setStatus(400);
		}
		log.info("leaving request helper now...");



	}

	public static void processError(HttpServletRequest req, HttpServletResponse resp)throws IOException, ServletException{
		req.getRequestDispatcher("error.html").forward(req, resp);
	}

	public static void processUserupdate(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		log.info("Inside of request helper...processUserUpdate...");
		BufferedReader reader = req.getReader();
		StringBuilder sBuilder = new StringBuilder();

		String line = reader.readLine();
		while(line != null) {
			sBuilder.append(line);
			line = reader.readLine();
		}

		String bodString = sBuilder.toString();
		String [] sepByAmp = bodString.split("&");

		List<String> values = new ArrayList<>();

		for(String pair : sepByAmp) {
			values.add(pair.substring(pair.indexOf("=") + 1));
		}
		log.info("User attempted to update with information:\n " + bodString);

		int id = Integer.parseInt(values.get(0));
		String username = values.get(1);
		String password = values.get(2);


		User tempUser = new User();
		tempUser.setId(0);
		tempUser.setUsername(username);
		tempUser.setPassword(password);

		boolean isUpdated = uServ.editUser(tempUser);

		if(isUpdated) {
			PrintWriter printWriter = resp.getWriter();
			log.info("Edit successful! New user Info: " + tempUser);
			String json = oMapper.writeValueAsString(tempUser);
			printWriter.println(json);
			System.out.println("JSON\n" + json);

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("User has successfully been edited");
		
		}
		else{
			resp.setContentType("application/json");
			resp.setStatus(400);
		}
		log.info("leaving request helper now...");

	}

	public static void processUserDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		log.info("inside of request helper...processUserUpdate...");
		BufferedReader reader = req.getReader();
		StringBuilder sBuilder = new StringBuilder();

		String line = reader.readLine();
		while(line != null) {
			sBuilder.append(line);
			line = reader.readLine();
		}

		String bodString = sBuilder.toString();
		String [] sepByAmp = bodString.split("&");

		List<String> values = new ArrayList<>();

		for(String pair : sepByAmp) {
			values.add(pair.substring(pair.indexOf("=") +1));
		}

		log.info("User attempted to upate with information:\n " + bodString);

		int id = Integer.parseInt(values.get(0));
		String username = values.get(1);
		String password = values.get(2);

		User tempUser = new User();
		tempUser.setId(id);
		tempUser.setUsername(username);
		tempUser.setPassword(password);
		boolean isDeleted = uServ.deleteUser(tempUser);

		if(isDeleted){
			PrintWriter printWriter = resp.getWriter();
			log.info("Delete successful! Removed user: " + tempUser);
			String json = oMapper.writeValueAsString(tempUser);
			printWriter.println(json);
			System.out.println("JSON:\n" + json);

			resp.setContentType("application/json");
			resp.setStatus(200);
			log.info("User has successfully been edited.");

		}else {
			resp.setContentType("application/json");
			resp.setStatus(400);
		}
		log.info("leaving request helper now...");
	}
}
