package binh.le.game.firebase.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;

import binh.le.game.ultis.Constants;

public class User {

    private String id;
    private String email;
    private String name;
    private long scoreGame1, scoreGame2, scoreGame3, scoreGame4;

    public User() {
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
        this.name = email;
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

    public long getScoreGame1() {
        return scoreGame1;
    }

    public void setScoreGame1(long scoreGame1) {
        this.scoreGame1 = scoreGame1;
    }

    public long getScoreGame2() {
        return scoreGame2;
    }

    public void setScoreGame2(long scoreGame2) {
        this.scoreGame2 = scoreGame2;
    }

    public long getScoreGame3() {
        return scoreGame3;
    }

    public void setScoreGame3(long scoreGame3) {
        this.scoreGame3 = scoreGame3;
    }

    public long getScoreGame4() {
        return scoreGame4;
    }

    public void setScoreGame4(long scoreGame4) {
        this.scoreGame4 = scoreGame4;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(Constants.User.ID, id);
        result.put(Constants.User.EMAIL, email);
        result.put(Constants.User.NAME, name);
        return result;
    }
}
