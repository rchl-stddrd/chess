package service;
import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import results.RegisterResult;

import javax.swing.*;
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
            throw ex;
            //throw new DataAccessException(500, ex.getMessage());
        }
    }



}
