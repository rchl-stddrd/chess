package dataaccess;

import model.AuthData;

import java.util.HashMap;

public interface AuthDao {

    public HashMap<String, AuthData> getAllAuthData();
    public AuthData getAuthData(String authToken);
    public void setAuths(HashMap<String, AuthData> auths);
    public void deleteAllAuths();
    public void addAuth(AuthData auth);
}
