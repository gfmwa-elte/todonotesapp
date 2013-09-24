package hu.plezervi.elte.gfmwa.todonotesapp.models;

import hu.plezervi.elte.gfmwa.todonotesapp.GFMWAException;
import hu.plezervi.elte.gfmwa.todonotesapp.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by plezerv on 2013.09.24..
 */
public class RegInfo {

    private String username;
    private String password;
    private String mailaddress;

    public RegInfo(String u, String p, String m) throws GFMWAException {
        this.username = u;
        this.password = Utils.sha1(p);
        this.mailaddress = m;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMailaddress() {
        return mailaddress;
    }

}
