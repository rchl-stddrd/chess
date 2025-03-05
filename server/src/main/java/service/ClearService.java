package service;

import dataaccess.*;

public class ClearService {
    public void clear(UserDao userDao, AuthDao authDao, GameDao gameDao){
        userDao.deleteAllUsers();
        authDao.deleteAllAuths();
        gameDao.deleteAllGames();
    }
}
