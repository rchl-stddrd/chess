package results;

import model.GameData;

import java.util.HashMap;

public record ListGamesResult(String message, HashMap<Integer, GameData> games) {
}
