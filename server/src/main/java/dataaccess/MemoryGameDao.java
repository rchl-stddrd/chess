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
    public GameData addGame(GameData game) throws DataAccessException {
        int max = 0;
        for(int id : games.keySet()){
            if (id>max){
                max = id;
            }
        }
        GameData newGame = new GameData(max+1, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        games.put(newGame.gameID(), newGame);
        return newGame;
    }
}
