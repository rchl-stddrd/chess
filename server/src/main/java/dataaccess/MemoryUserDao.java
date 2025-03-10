package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDao implements UserDao{
    //username as key, UserData as value
    HashMap<String, UserData> users = new HashMap<>();

    @Override
    public HashMap<String, UserData> getAllUserData() throws DataAccessException {
        return users;
    }

    @Override
    public UserData getUserData(String username) throws DataAccessException {
        return users.get(username);
    }

    @Override
    public void setUsers(HashMap<String, UserData> users) throws DataAccessException {
        this.users = users;
    }

    @Override
    public void deleteAllUsers() throws DataAccessException {
        users = new HashMap<String, UserData>();
    }

    @Override
    public void addUser(UserData user) throws DataAccessException {
        String username = user.username();
        users.put(username, user);
    }
}
