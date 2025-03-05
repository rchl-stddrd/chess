package dataaccess;
import model.UserData;

import java.util.HashMap;

public interface UserDao {
    public HashMap<String, UserData> getAllUserData();
    public UserData getUserData(String username);
    public void setUsers(HashMap<String, UserData> users);
    public void deleteAllUsers();
    public void addUser(UserData user);
}
