package dataaccess;

import model.GameData;
import java.util.HashMap;

public class MemoryGameDao implements GameDao{
    //gameID as key, GameData as value
    HashMap<Integer, GameData> games= new HashMap<>();

    @Override
    public void setGames(HashMap<Integer, GameData> games) throws DataAccessException {
        this.games = games;
    }

    @Override
    public HashMap<Integer, GameData> getAllGameData() throws DataAccessException {
        return games;
    }

    @Override
    public GameData getGameData(int gameID) throws DataAccessException {
        return games.get(gameID);

    }

    @Override
    public void deleteAllGames() throws DataAccessException {
        games = new HashMap<Integer, GameData>();
    }

    @Override
    public void addGame(GameData game) throws DataAccessException {
        int gameID = game.gameID();
        games.put(gameID, game);
    }
}
