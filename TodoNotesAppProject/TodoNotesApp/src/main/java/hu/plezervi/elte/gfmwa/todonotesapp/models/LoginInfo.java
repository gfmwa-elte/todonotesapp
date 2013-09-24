package hu.plezervi.elte.gfmwa.todonotesapp.models;

import hu.plezervi.elte.gfmwa.todonotesapp.GFMWAException;
import hu.plezervi.elte.gfmwa.todonotesapp.Utils;

/**
 * Created by plezerv on 2013.09.24..
 */
public class LoginInfo {
    private String username;
    private String password;

    public LoginInfo(String u, String p) {
        this.username = u;
        try {
            this.password = Utils.sha1(p);
        } catch (GFMWAException e) {
            this.password = "";
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
