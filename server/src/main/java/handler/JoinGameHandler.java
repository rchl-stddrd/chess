package handler;

import com.google.gson.Gson;
import dataaccess.AuthDao;
import dataaccess.GameDao;
import requests.JoinGameRequest;
import results.JoinGameResult;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinGameHandler implements Route {
    GameDao gameDao;
    AuthDao authDao;

    public JoinGameHandler(GameDao gameDao, AuthDao authDao){
        this.gameDao = gameDao;
        this.authDao = authDao;
    }
    public Object handle(Request req, Response res){
        GameService gameService = new GameService(gameDao, authDao);
        JoinGameRequest request = new Gson().fromJson(req.body(), JoinGameRequest.class);
        JoinGameResult joinGameResult = gameService.joinGame(req.headers("authorization"), request.playerColor(), request.gameID());
        if(joinGameResult.message() ==null){
            res.status(200);
        }
        else if(joinGameResult.message().equals("Error: bad request")){
            res.status(400);
        }
        else if(joinGameResult.message().equals("Error: unauthorized")){
            res.status(401);
        }
        else if(joinGameResult.message().equals("Error: already taken")){
            res.status(403);
        }
        else if(joinGameResult.message().equals("Error: DataAccessException")){
            res.status(500);

        }
        res.type("application/json");
        return new Gson().toJson(joinGameResult);
    }
}
