package dataaccess;

import model.AuthData;
import java.util.HashMap;

public class MemoryAuthDao implements AuthDao{
    //authToken as key, AuthData as value
    HashMap<String, AuthData> auths = new HashMap<>();

    @Override
    public HashMap<String, AuthData> getAllAuthData(){
        return auths;
    }

    @Override
    public AuthData getAuthData(String authToken){
        return auths.get(authToken);
    }

    @Override
    public void setAuths(HashMap<String, AuthData> auths){
        this.auths = auths;
    }

    @Override
    public void deleteAllAuths(){
        auths = new HashMap<String, AuthData>();
    }

    @Override
    public void addAuth(AuthData auth){
        String authToken = auth.authToken();
        auths.put(authToken, auth);
    }

}
