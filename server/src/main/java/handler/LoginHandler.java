package handler;

import com.google.gson.Gson;
import dataaccess.AuthDao;
import dataaccess.UserDao;
import requests.LoginRequest;
import results.LoginResult;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {
    UserDao userDao;
    AuthDao authDao;

    public LoginHandler(UserDao userDao, AuthDao authDao){
        this.userDao = userDao;
        this.authDao = authDao;
    }

    public Object handle(Request req, Response res){
        UserService userService = new UserService(userDao, authDao);
        LoginRequest request = new Gson().fromJson(req.body(), LoginRequest.class);
        LoginResult loginResult = userService.login(request.username(), request.password());
        if(loginResult.message() ==null){
            res.status(200);
        }
        else if(loginResult.message().equals("Error: unauthorized")){
            res.status(401);
        }
        else if(loginResult.message().equals("Error: DataAccessException")){
            res.status(500);

        }
        res.type("application/json");
        return new Gson().toJson(loginResult);
    }


}
