package hu.plezervi.elte.gfmwa.todonotesapp;

/**
 * Created by plezerv on 2013.09.24..
 */
public class GFMWAException extends Exception {

    public GFMWAException(String msg) {
        super(msg);
    }

    public GFMWAException(Throwable reason) {
        super(reason);
    }
}
