package dataaccess;
import model.UserData;

import java.util.HashMap;

public interface UserDao {
    public HashMap<String, UserData> getAllUserData() throws DataAccessException;
    public UserData getUserData(String username) throws DataAccessException;
    public void setUsers(HashMap<String, UserData> users) throws DataAccessException;
    public void deleteAllUsers() throws DataAccessException;
    public void addUser(UserData user) throws DataAccessException;
}
