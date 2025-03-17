package server;

import com.google.gson.Gson;
import dataaccess.*;
import handler.ClearHandler;
import handler.UserHandler;
import spark.*;

public class Server {
    UserDao userDao = new MemoryUserDao();
    AuthDao authDao = new MemoryAuthDao();
    GameDao gameDao = new MemoryGameDao();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.delete("/db", (req,res)->{//fix!! should be spark.get
            ClearHandler clearHandler = new ClearHandler();
            return clearHandler.handle(userDao, authDao, gameDao, req, res);
            //return null;
        });

//        Spark.post("/user", (req, res)-> {
//            UserHandler userHandler = new UserHandler();
//            return userHandler.registerHandle(userDao, req, res);
//        });


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
