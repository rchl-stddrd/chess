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

   public ListGamesResult listGames(String authToken) throws DataAccessException{
        try{
            if(authDao.getAllAuthData().containsKey(authToken)){
                return new ListGamesResult("games: ", gameDao.getAllGameData());
            }
            else{
                return new ListGamesResult("Error: unauthorized", null);
            }

        } catch(DataAccessException ex){
            throw new DataAccessException(ex.getMessage());
        }
   }


   public CreateGameResult createGame(String authToken, String gameName) throws DataAccessException {
       try{
           if(!authDao.getAllAuthData().containsKey(authToken)){
               return new CreateGameResult(0, "Error: unauthorized");
           }
           else if(gameName == null || gameName.equals("")){
               return new CreateGameResult(0, "Error: bad request");
           }
           else {
               GameData gameData = new GameData(0, null, null, gameName, new ChessGame());
               GameData newGame = gameDao.addGame(gameData);
               return new CreateGameResult(newGame.gameID(),null);
           }
       } catch (DataAccessException ex){
           throw new DataAccessException(ex.getMessage());
       }
   }

}
