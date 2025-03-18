package dataaccess;

import model.GameData;

import java.util.HashMap;

public interface GameDao {

    public void setGames(HashMap<Integer, GameData> games) throws DataAccessException;
    public HashMap<Integer, GameData> getAllGameData() throws DataAccessException;
    public GameData getGameData(int gameID) throws DataAccessException;
    public void deleteAllGames() throws DataAccessException;
    public GameData addGame(GameData game) throws DataAccessException;
}
