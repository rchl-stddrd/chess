package dataaccess;

import model.GameData;

import java.util.HashMap;

public interface GameDao {

    public void setGames(HashMap<Integer, GameData> games);
    public HashMap<Integer, GameData> getAllGameData();
    public GameData getGameData(int gameID);
    public void deleteAllGames();
    public void addGame(GameData game);
}
