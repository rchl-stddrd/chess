package handler;

import com.google.gson.Gson;
import dataaccess.AuthDao;
import dataaccess.UserDao;
import requests.LogoutRequest;
import results.LogoutResult;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    UserDao userDao;
    AuthDao authDao;

    public LogoutHandler(UserDao userDao, AuthDao authDao){
        this.userDao = userDao;
        this.authDao = authDao;
    }

    public Object handle(Request req, Response res){
        UserService userService = new UserService(userDao, authDao);
        LogoutRequest request = new LogoutRequest(req.headers("authorization"));
        LogoutResult logoutResult = userService.logout(request.authToken());
        if(logoutResult.message() == null){
            res.status(200);
        }
        else if(logoutResult.message().equals("Error: unauthorized")){
            res.status(401);
        }
        else if(logoutResult.message().equals("Error: DataAccessException")){
            res.status(500);
        }
        res.type("application/json");
        return new Gson().toJson(logoutResult);
    }

}
