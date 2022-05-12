package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.*;;

public class FrontController extends HttpServlet{
    private static final long serialVersionUID = 8339100247721381693L;
    private static Logger log = LogManager.getLogger(FrontController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        	//save the URI and rewrite it to determine what functionality the user is requesting based on that endpoint
		final String URI = req.getRequestURI().replace("/my-ec2-challenge/", "");
		log.info("URI: " + URI);
		
		switch (URI) {
		case "users": // query the DB and return a list of all users
			log.info("getting user list...");
			RequestHelper.processAllUsers(req, resp);
			break;
		case "user":
			log.info("search user by username or id. URI: " + URI);
			RequestHelper.processUserBySearchParam(req, resp);
			break;
		default:
			log.info("showing error message...");
			RequestHelper.processError(req, resp);
			break;
		}
        super.doDelete(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String URI = req.getRequestURI().replace("/my-ec2-challenge/", "");
		log.info("URI: " + URI);
		
		switch (URI) {
		case "register": 
			log.info("registering new user...");
			RequestHelper.processRegistration(req, resp);
			break;
        
        case "login":
            log.info("logging into system...");
            RequestHelper.processLogin(req, resp);
		default:
			log.info("showing error message...");
			RequestHelper.processError(req, resp);
			break;
		}
        super.doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String URI = req.getRequestURI().replace("/HelloFrontController/", "");
		log.info("URI: " + URI);
		
		switch (URI) {
		case "delete": 
			log.info("removing user...");
			RequestHelper.processUserDelete(req, resp);
			break;
		default:
			log.info("showing error message...");
			RequestHelper.processError(req, resp);
			break;
		}
        super.doPost(req, resp);
    }
    
    
}