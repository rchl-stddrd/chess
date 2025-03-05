package handler;

import dataaccess.AuthDao;
import dataaccess.GameDao;
import dataaccess.UserDao;
import service.ClearService;

public class ClearHandler {

    public record ClearResult() {}

    public ClearResult handle(UserDao users, AuthDao auths, GameDao games){
        ClearService clearService = new ClearService();
        clearService.clear(users, auths, games);
        return new ClearResult();
    }
}
