package service;

import chess.ChessGame;
import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import model.GameData;
import results.*;

import java.util.UUID;

public class GameService {
    GameDao gameDao;
    AuthDao authDao;
    public GameService(GameDao gameDao, AuthDao authDao){
        this.gameDao = gameDao;
        this.authDao = authDao;
    }

   public ListGamesResult listGames(String authToken) {
        try{
            if(authDao.getAllAuthData().containsKey(authToken)){
                return new ListGamesResult(null, gameDao.getAllGameData());
            }
            else{
                return new ListGamesResult("Error: unauthorized", null);
            }

        } catch(DataAccessException ex){
            return new ListGamesResult("Error: DataAccessException", null);
        }
   }


   public CreateGameResult createGame(String authToken, String gameName)  {
       try{
           if(!authDao.getAllAuthData().containsKey(authToken)){
               return new CreateGameResult(null, "Error: unauthorized");
           }
           else if(gameName == null || gameName.equals("")){
               return new CreateGameResult(null, "Error: bad request");
           }
           else {
               GameData gameData = new GameData(0, null, null, gameName, new ChessGame());
               GameData newGame = gameDao.addGame(gameData);
               return new CreateGameResult(newGame.gameID(),null);
           }
       } catch (DataAccessException ex){
           return new CreateGameResult(null, "Error: DataAccessException");
       }
   }


   public JoinGameResult joinGame(String authToken, String color, int gameID) {
       try {
           GameData gameData = gameDao.getGameData(gameID);
           if (!authDao.getAllAuthData().containsKey(authToken)) {
               return new JoinGameResult("Error: unauthorized");
           } else if (gameData == null) {
               return new JoinGameResult("Error: bad request");
           }

           if (color.equals("WHITE") || color.equals("BLACK")) {
               if (color.equals("WHITE")) {
                   if (gameData.whiteUsername() == null) {
                       gameDao.updateGame(new GameData(gameID, authDao.getAuthData(authToken).username(), gameData.blackUsername(), gameData.gameName(), gameData.game()));
                       return new JoinGameResult(null);
                   }
               } else {
                   if (gameData.blackUsername() != null) {
                       gameDao.updateGame(new GameData(gameID, gameData.whiteUsername(), authDao.getAuthData(authToken).username(), gameData.gameName(), gameData.game()));
                       return new JoinGameResult(null);
                   }
               }
           }
           return new JoinGameResult("Error: bad request");

       } catch (DataAccessException ex){
           return new JoinGameResult("Error: DataAccessException");
       }
   }

}
