
package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.*;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.models.User;

public class UserServicesImpl implements UserServices {
    private UserDAO userDAO;
    private static Logger log = LogManager.getLogger(UserServicesImpl.class);

    public UserServicesImpl(UserDAOImpl daoImpl) {
        super();
        this.userDAO = daoImpl;
    }

    @Override
    public User login(String username, String password) {
        log.info("In service layer. Logging in user with creds: " + username + ", " + password);
        Optional<User> users = userDAO.selectAll().stream()
                .filter(u -> (u.getUsername().equals(username) && u.getPassword().equals(password))).findFirst();
        return (users.isPresent() ? users.get() : null);
    }

    @Override
    public int register(User user) {
        log.info("In service layer. Registering user: " + user);
        return userDAO.insert(user);
    }

    @Override
    public User findUserById(int id) {
        log.info("In service layer, searching user by id: " + id);
        return userDAO.selectById(id);
    }

    @Override
    public List<User> findAllUsers() {
        log.info("In service layer, finding all users...");
        return userDAO.selectAll();
    }

    @Override
    public boolean editUser(User user) {
        log.info("In service layer, editing user: " + user);
        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(User user) {
        log.info("In service layer, removing user: " + user);
        return userDAO.delete(user);
    }

}