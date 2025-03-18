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
           return new CreateGameResult(0, "Error: DataAccessException");
       }
   }

//   public enum PlayerColor{
//        BLACK,
//        WHITE
//   }
//
//   public JoinGameResult joinGame(String authToken, PlayerColor color, int gameID){
//       try{
//           if(!authDao.getAllAuthData().containsKey(authToken)){
//               return new JoinGameResult(0, "Error: unauthorized");
//           }
//           else if(gameName == null || gameName.equals("")){
//               return new JoinGameResult(0, "Error: bad request");
//           }
//           else if(gameDao.getGameData(gameID).whiteUsername() != null && )
//           else {
//               GameData gameData = new GameData(0, null, null, gameName, new ChessGame());
//               GameData newGame = gameDao.addGame(gameData);
//               return new JoinGameResult(newGame.gameID(),null);
//           }
//       } catch (DataAccessException ex){
//           return new JoinGameResult(0, "Error: DataAccessException");
//       }
//   }

}
