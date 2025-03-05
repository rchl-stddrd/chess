package server;

import com.google.gson.Gson;
import dataaccess.*;
import handler.ClearHandler;
import spark.*;

public class Server {
    UserDao userDao = new MemoryUserDao();
    AuthDao authDao = new MemoryAuthDao();
    GameDao gameDao = new MemoryGameDao();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.get("/db", (req,res)->{
            res.type("application/json");
            ClearHandler clearHandler = new ClearHandler();
            return new Gson().toJson(clearHandler.handle(userDao, authDao, gameDao));
        });


        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
