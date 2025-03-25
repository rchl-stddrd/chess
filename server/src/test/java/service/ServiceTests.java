package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import results.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {
    //User Service Tests
    UserDao users = new MemoryUserDao();
    AuthDao auths = new MemoryAuthDao();
    GameDao games = new MemoryGameDao();

    UserService userService = new UserService(users, auths);
    GameService gameService = new GameService(games, auths);

    @BeforeEach
    void clear() throws DataAccessException {
        users.deleteAllUsers();
        auths.deleteAllAuths();
        games.deleteAllGames();

    }

    @Test
    void clearDatabase() throws DataAccessException {
        users.addUser(new UserData("username", "password", "email"));
        users.addUser(new UserData("username1", "password1", "email1"));
        users.addUser(new UserData("username2", "password2", "email2"));

        auths.addAuth(new AuthData("1234", "username1"));
        auths.addAuth(new AuthData("2234", "username2"));
        auths.addAuth(new AuthData("3234", "username3"));

        games.addGame(new GameData(1, "white", "black", "1v1", new ChessGame()));
        games.addGame(new GameData(2, "white1", "black1", "1v2", new ChessGame()));
        games.addGame(new GameData(3, "white2", "black2", "1v3", new ChessGame()));

        ClearService clearService = new ClearService(users, auths, games);
        clearService.clear();

        assertEquals(0, (users.getAllUserData().size()));
        assertEquals(0, (auths.getAllAuthData().size()));
        assertEquals(0, (games.getAllGameData().size()));

    }

    @Test
    void registerUser() throws DataAccessException{
        users.addUser(new UserData("username", "password", "email"));
        users.addUser(new UserData("username1", "password1", "email1"));
        users.addUser(new UserData("username2", "password2", "email2"));

        userService = new UserService(users, auths);
        userService.register(new UserData("you", "passed", "email3"));

        assertNotNull(users.getUserData("you"));
        assertEquals("passed", users.getUserData("you").password());
        assertEquals("email3", users.getUserData("you").email());
    }
    @Test
    void registerUserAlreadyExists() throws DataAccessException{
        users.addUser(new UserData("username", "password", "email"));
        users.addUser(new UserData("username1", "password1", "email1"));
        users.addUser(new UserData("username2", "password2", "email2"));

        userService = new UserService(users, auths);
        RegisterResult result = userService.register(new UserData("username", "password", "email"));

        assertEquals("Error: already taken", result.message());
    }

    @Test
    void registerInvalidUser() throws DataAccessException{
        users.addUser(new UserData("username", "password", "email"));
        users.addUser(new UserData("username1", "password1", "email1"));
        users.addUser(new UserData("username2", "password2", "email2"));

        userService = new UserService(users, auths);
        RegisterResult registerResult = userService.register(new UserData(null, "password", "email"));

        assertEquals("Error: bad request", registerResult.message());
    }

    @Test
    void loginUser() throws DataAccessException{
        users.addUser(new UserData("username", "password", "email"));
        users.addUser(new UserData("username1", "password1", "email1"));
        users.addUser(new UserData("username2", "password2", "email2"));

        userService = new UserService(users, auths);
        LoginResult loginResult = userService.login("username", "password");

        assertEquals("username", loginResult.username());
        assertNotNull(loginResult.authToken());
    }

    @Test
    void loginWrongPassword() throws DataAccessException{
        users.addUser(new UserData("username", "password", "email"));
        users.addUser(new UserData("username1", "password1", "email1"));
        users.addUser(new UserData("username2", "password2", "email2"));

        userService = new UserService(users, auths);
        LoginResult loginResult = userService.login("username", "password1");

        assertEquals("Error: unauthorized", loginResult.message());
    }

    @Test
    void logoutUser() throws DataAccessException{
        users.addUser(new UserData("username1", "password", "email"));
        users.addUser(new UserData("username2", "password1", "email1"));
        users.addUser(new UserData("username3", "password2", "email2"));

        auths.addAuth(new AuthData("1234", "username1"));
        auths.addAuth(new AuthData("2234", "username2"));
        auths.addAuth(new AuthData("3234", "username3"));

        userService = new UserService(users,auths);
        LogoutResult logoutResult = userService.logout("username1");

        assert(!auths.getAllAuthData().containsKey("username1"));
    }

    @Test
    void logoutWrongUser() throws DataAccessException{
        users.addUser(new UserData("username1", "password", "email"));
        users.addUser(new UserData("username2", "password1", "email1"));
        users.addUser(new UserData("username3", "password2", "email2"));

        auths.addAuth(new AuthData("1234", "username1"));
        auths.addAuth(new AuthData("2234", "username2"));
        auths.addAuth(new AuthData("3234", "username3"));

        userService = new UserService(users,auths);
        LogoutResult logoutResult = userService.logout("username");

        assertEquals("Error: unauthorized", logoutResult.message());
    }

    @Test
    void authorizedlistGames() throws DataAccessException{
        games.addGame(new GameData(1, "white", "black", "gameName", new ChessGame()));
        games.addGame(new GameData(2, "white1", "black1", "gameName1", new ChessGame()));
        games.addGame(new GameData(3, "white2", "black2", "gameName2", new ChessGame()));

        users.addUser(new UserData("white", "password", "email"));
        users.addUser(new UserData("username2", "password1", "email1"));
        users.addUser(new UserData("username3", "password2", "email2"));

        auths.addAuth(new AuthData("1234", "white"));
        auths.addAuth(new AuthData("2234", "username2"));
        auths.addAuth(new AuthData("3234", "username3"));

        gameService = new GameService(games, auths);
        ListGamesResult listGamesResult = gameService.listGames("1234");

        assertEquals(games.getAllGameData(), listGamesResult.games());
    }

    @Test
    void unauthorizedListGames() throws DataAccessException{
        games.addGame(new GameData(1, "white", "black", "gameName", new ChessGame()));
        games.addGame(new GameData(2, "white1", "black1", "gameName1", new ChessGame()));
        games.addGame(new GameData(3, "white2", "black2", "gameName2", new ChessGame()));

        users.addUser(new UserData("white", "password", "email"));
        users.addUser(new UserData("username2", "password1", "email1"));
        users.addUser(new UserData("username3", "password2", "email2"));

        auths.addAuth(new AuthData("1234", "white"));
        auths.addAuth(new AuthData("2234", "username2"));
        auths.addAuth(new AuthData("3234", "username3"));

        gameService = new GameService(games, auths);
        ListGamesResult listGamesResult = gameService.listGames("1235");

        assertEquals("Error: unauthorized", listGamesResult.message());
    }

    @Test
    void validCreateGame() throws DataAccessException{
        users.addUser(new UserData("white", "password", "email"));

        auths.addAuth(new AuthData("1234", "white"));

        gameService = new GameService(games, auths);
        CreateGameResult createGameResult = gameService.createGame("gameName3", "1234");

        assertNotEquals(0, createGameResult.gameID());
        assertNotNull(createGameResult);
    }

    @Test
    void unauthorizedCreateGame() throws DataAccessException{
        games.addGame(new GameData(1, "white", "black", "gameName", new ChessGame()));
        games.addGame(new GameData(2, "white1", "black1", "gameName1", new ChessGame()));
        games.addGame(new GameData(3, "white2", "black2", "gameName2", new ChessGame()));

        users.addUser(new UserData("white", "password", "email"));

        auths.addAuth(new AuthData("1234", "white"));

        gameService = new GameService(games, auths);
        CreateGameResult createGameResult = gameService.createGame( "gameName3","1235");

        assertEquals("Error: unauthorized", createGameResult.message());
    }

    @Test
    void createGameWithNullName() throws DataAccessException{
        games.addGame(new GameData(1, "white", "black", "gameName", new ChessGame()));
        games.addGame(new GameData(2, "white1", "black1", "gameName1", new ChessGame()));
        games.addGame(new GameData(3, "white2", "black2", "gameName2", new ChessGame()));

        users.addUser(new UserData("white", "password", "email"));

        auths.addAuth(new AuthData("1234", "white"));

        gameService = new GameService(games, auths);
        CreateGameResult createGameResult = gameService.createGame( null,"1234");
        CreateGameResult createGameResult1 = gameService.createGame( "","1234");

        assertEquals("Error: bad request", createGameResult.message());
        assertEquals("Error: bad request", createGameResult1.message());
    }

    @Test
    void validJoinGame() throws DataAccessException{
        games.addGame(new GameData(1, null, "black", "gameName", new ChessGame()));
        games.addGame(new GameData(2, "white1", null, "gameName1", new ChessGame()));
        games.addGame(new GameData(3, "white2", "black2", "gameName2", new ChessGame()));

        users.addUser(new UserData("white", "password", "email"));

        auths.addAuth(new AuthData("1234", "white"));

        gameService = new GameService(games, auths);
        JoinGameResult joinGameResult = gameService.joinGame("1234", "WHITE", 1);
        JoinGameResult joinGameResult1 = gameService.joinGame("1234", "BLACK", 2);

        assertNull(joinGameResult.message());
        assertEquals(new GameData(1, "white", "black", "gameName", new ChessGame()), games.getGameData(1));
        assertEquals(new GameData(2, "white1", "white", "gameName1", new ChessGame()), games.getGameData(2));

    }

    @Test
    void unauthorizedJoinGame() throws DataAccessException{
        games.addGame(new GameData(1, null, "black", "gameName", new ChessGame()));
        games.addGame(new GameData(2, "white1", "black1", "gameName1", new ChessGame()));
        games.addGame(new GameData(3, "white2", "black2", "gameName2", new ChessGame()));

        users.addUser(new UserData("white", "password", "email"));

        auths.addAuth(new AuthData("1234", "white"));

        gameService = new GameService(games, auths);
        JoinGameResult joinGameResult = gameService.joinGame("1235", "WHITE", 1);

        assertEquals("Error: unauthorized", joinGameResult.message());
    }

    @Test
    void invalidJoinGame() throws DataAccessException{
        games.addGame(new GameData(1, null, "black", "gameName", new ChessGame()));
        games.addGame(new GameData(2, "white1", "black1", "gameName1", new ChessGame()));
        games.addGame(new GameData(3, "white2", "black2", "gameName2", new ChessGame()));

        users.addUser(new UserData("white", "password", "email"));

        auths.addAuth(new AuthData("1234", "white"));

        gameService = new GameService(games, auths);
        JoinGameResult joinGameResult = gameService.joinGame("1234", "white", 1);
        JoinGameResult joinGameResult1 = gameService.joinGame("1234", "WHITE", 4);
        JoinGameResult joinGameResult2 = gameService.joinGame("1234", "WHITE", 2);


        assertEquals("Error: bad request", joinGameResult.message());
        assertEquals("Error: bad request", joinGameResult1.message());
        assertEquals("Error: bad request", joinGameResult2.message());

    }
}