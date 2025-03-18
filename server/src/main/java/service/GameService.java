package service;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import model.AuthData;
import model.GameData;
import results.*;

import java.util.ArrayList;
import java.util.List;

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

}
