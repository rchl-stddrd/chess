package server;

import com.google.gson.Gson;
import dataaccess.*;
import handler.*;
import spark.*;

public class Server {
    UserDao userDao = new MemoryUserDao();
    AuthDao authDao = new MemoryAuthDao();
    GameDao gameDao = new MemoryGameDao();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.delete("/db", new ClearHandler(userDao, authDao, gameDao));

        Spark.post("/user", new RegisterHandler(userDao, authDao) );

        Spark.post("/session", new LoginHandler(userDao, authDao));

        Spark.delete("/session", new LogoutHandler(userDao, authDao));

        Spark.get("/game", new ListGamesHandler(gameDao, authDao));

        Spark.post("/game", new CreateGameHandler(gameDao, authDao));
//
//        Spark.put("/game", new JoinGameHandler());


        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    public static void main(String[] args){
        int port = Integer.parseInt(args[0]);
        Server server = new Server();
        server.run(port);
    }
}
