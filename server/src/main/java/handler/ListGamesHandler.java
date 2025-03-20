package handler;

import com.google.gson.Gson;
import dataaccess.AuthDao;
import dataaccess.GameDao;
import requests.ListGamesRequest;
import results.ListGamesResult;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ListGamesHandler implements Route {
    AuthDao authDao;
    GameDao gameDao;

    public ListGamesHandler(GameDao gameDao, AuthDao authDao){
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public Object handle(Request req, Response res){
        GameService gameService = new GameService(gameDao, authDao);
        ListGamesRequest request = new ListGamesRequest(req.headers("authorization"));
        ListGamesResult listGamesResult = gameService.listGames(request.authToken());
        if(listGamesResult.message() ==null){
            res.status(200);
        }
        else if(listGamesResult.message().equals("Error: unauthorized")){
            res.status(401);
        }
        else if(listGamesResult.message().equals("Error: DataAccessException")){
            res.status(500);

        }
        res.type("application/json");
        return new Gson().toJson(listGamesResult);
    }
}
