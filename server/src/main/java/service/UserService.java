package service;
import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import results.LoginResult;
import results.LogoutResult;
import results.RegisterResult;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.util.UUID;


public class UserService {
    UserDao users;
    AuthDao auths;

    public UserService(UserDao users, AuthDao auths){
        this.users = users;
        this.auths = auths;
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public RegisterResult register(UserData userData) throws DataAccessException{
        try {
            if (userData.username() == null || userData.password() == null || userData.email() == null) {
                return new RegisterResult(null, null, "Error: bad request");
            } else if (users.getAllUserData().containsKey(userData.username())) {
                return new RegisterResult(null, null, "Error: already taken");
            } else {
                users.addUser(userData);
                AuthData authData = new AuthData(generateToken(), userData.username());
                auths.addAuth(authData);
                return new RegisterResult(userData.username(), authData.authToken(), null);
            }
        }
        catch(DataAccessException ex){
            throw new DataAccessException(ex.getMessage());
        }
    }

    public LoginResult login(String username, String password) throws DataAccessException{
        try {
            if(users.getAllUserData().containsKey(username)) {
                UserData userData = users.getUserData(username);
                if (userData.password().equals(password)) {
                    AuthData authData = new AuthData(generateToken(), userData.username());
                    return new LoginResult(username, authData.authToken(), null);
                }
            }
            return new LoginResult(null, null, "Error: unauthorized");
        } catch (DataAccessException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public LogoutResult logout(String username) throws DataAccessException {
        try {
            if(auths.getAllAuthData().containsKey(username)) {
                auths.getAllAuthData().remove(username);
                return new LogoutResult(null);
            }
            else {
                return new LogoutResult("Error: unauthorized");
            }
        } catch (DataAccessException ex){
            throw new DataAccessException(ex.getMessage());
        }

    }


}
