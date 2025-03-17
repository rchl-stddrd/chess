package service;

import dataaccess.*;
import results.ClearResult;

public class ClearService {
    UserDao userDao;
    AuthDao authDao;
    GameDao gameDao;

    public ClearService(UserDao userDao, AuthDao authDao, GameDao gameDao){
        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }
    public ClearResult clear(){
        try {
            userDao.deleteAllUsers();
        } catch (DataAccessException e) {
            return new ClearResult("Error: "+ e.getMessage());
        }
        try {
            authDao.deleteAllAuths();
        } catch (DataAccessException e) {
            return new ClearResult("Error: "+ e.getMessage());
        }
        try {
            gameDao.deleteAllGames();
        } catch (DataAccessException e) {
            return new ClearResult("Error: "+ e.getMessage());
        }
        return new ClearResult(null);
    }
}
