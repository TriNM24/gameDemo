package binh.le.game.firebase.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;

import binh.le.game.ultis.Constants;

public class User{

    private String id;
    private String email;
    private int scoreGame1, scoreGame2, scoreGame3, scoreGame4;

    public User() {
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(Constants.User.ID, id);
        result.put(Constants.User.EMAIL, email);
        result.put(Constants.User.SCORE_GAME1, 0);
        result.put(Constants.User.SCORE_GAME2, 0);
        result.put(Constants.User.SCORE_GAME3, 0);
        result.put(Constants.User.SCORE_GAME4, 0);
        return result;
    }
}
