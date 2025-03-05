package dataaccess;

import model.GameData;
import java.util.HashMap;

public class MemoryGameDao implements GameDao{
    //gameID as key, GameData as value
    HashMap<Integer, GameData> games= new HashMap<>();

    @Override
    public void setGames(HashMap<Integer, GameData> games) {
        this.games = games;
    }

    @Override
    public HashMap<Integer, GameData> getAllGameData(){
        return games;
    }

    @Override
    public GameData getGameData(int gameID){
        return games.get(gameID);

    }

    @Override
    public void deleteAllGames(){
        games = new HashMap<Integer, GameData>();
    }

    @Override
    public void addGame(GameData game){
        int gameID = game.gameID();
        games.put(gameID, game);
    }
}
