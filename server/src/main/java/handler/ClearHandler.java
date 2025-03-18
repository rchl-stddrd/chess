package handler;

import com.google.gson.Gson;
import dataaccess.AuthDao;
import dataaccess.GameDao;
import dataaccess.UserDao;
import service.ClearService;
import spark.*;
import results.ClearResult;

public class ClearHandler implements Route{
    UserDao users;
    AuthDao auths;
    GameDao games;

    public ClearHandler(UserDao users, AuthDao auths, GameDao games){
        this.users = users;
        this.auths = auths;
        this.games = games;
    }


    public String handle(Request req, Response res){
        ClearService clearService = new ClearService(users, auths, games);
        ClearResult clearResult = clearService.clear();
        if(clearResult.message() == null){
            res.status(200);
        }
        else{
            res.status(500);
        }
        res.type("application/json");
        return new Gson().toJson(clearResult);
    }
}
