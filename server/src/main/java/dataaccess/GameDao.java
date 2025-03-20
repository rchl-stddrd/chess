package dataaccess;

import model.GameData;

import java.util.HashMap;
import java.util.List;

public interface GameDao {

    public void setGames(HashMap<Integer, GameData> games) throws DataAccessException;
    public List<GameData> getAllGameData() throws DataAccessException;
    public GameData getGameData(int gameID) throws DataAccessException;
    public void deleteAllGames() throws DataAccessException;
    public GameData addGame(GameData game) throws DataAccessException;
    public void updateGame(GameData gameData) throws DataAccessException;
}
