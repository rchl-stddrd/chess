package dataaccess;

import model.AuthData;

import java.util.HashMap;

public interface AuthDao {

    public HashMap<String, AuthData> getAllAuthData() throws DataAccessException;
    public AuthData getAuthData(String authToken) throws DataAccessException;
    public void setAuths(HashMap<String, AuthData> auths) throws DataAccessException;
    public void deleteAllAuths() throws DataAccessException;
    public void addAuth(AuthData auth) throws DataAccessException;
}
