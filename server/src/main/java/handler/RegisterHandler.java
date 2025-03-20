package handler;

import com.google.gson.Gson;
import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.UserDao;
import model.UserData;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.Authentication;
import service.UserService;
import requests.*;
import results.*;
import spark.*;

public class RegisterHandler implements Route{
    UserDao userDao;
    AuthDao authDao;

    public RegisterHandler(UserDao userDao, AuthDao authDao){
        this.userDao = userDao;
        this.authDao = authDao;

       UserService userService = new UserService(userDao, authDao);
    }

    public Object handle( Request req, Response res) {
        UserService userService = new UserService(userDao, authDao);
        RegisterRequest request = new Gson().fromJson(req.body(), RegisterRequest.class);
        RegisterResult registerResult = userService.register(new UserData(request.username(), request.password(), request.email()));
        if(registerResult.message() ==null){
            res.status(200);
        }
        else if(registerResult.message().equals("Error: bad request")){
            res.status(400);
        }
        else if(registerResult.message().equals("Error: already taken")){
            res.status(403);
        }
        else if(registerResult.message().equals("Error: DataAccessException")){
            res.status(500);

        }
        res.type("application/json");
        return new Gson().toJson(registerResult);
    }
}