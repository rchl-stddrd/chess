package results;

import model.GameData;

import java.util.List;

public record ListGamesResult(String message, List<GameData> games) {
}
