package handler;

import com.google.gson.Gson;
import dataaccess.AuthDao;
import dataaccess.GameDao;
import requests.CreateGameRequest;
import results.CreateGameResult;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler implements Route {
    GameDao gameDao;
    AuthDao authDao;
    public CreateGameHandler(GameDao gameDao, AuthDao authDao){
        this.gameDao = gameDao;
        this.authDao = authDao;
    }

    public Object handle(Request req, Response res){
        GameService gameService = new GameService(gameDao, authDao);
        CreateGameRequest request = new Gson().fromJson(req.body(), CreateGameRequest.class);
        CreateGameResult createGameResult = gameService.createGame(request.gameName(),req.headers("authorization"));
        if(createGameResult.message() ==null){
            res.status(200);
        }
        else if(createGameResult.message().equals("Error: bad request")){
            res.status(400);
        }
        else if(createGameResult.message().equals("Error: unauthorized")){
            res.status(401);
        }
        else if(createGameResult.message().equals("Error: DataAccessException")){
            res.status(500);

        }
        res.type("application/json");
        return new Gson().toJson(createGameResult);
    }
}
