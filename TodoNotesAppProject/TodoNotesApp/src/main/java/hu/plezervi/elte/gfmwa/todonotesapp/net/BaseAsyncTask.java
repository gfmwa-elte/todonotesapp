package hu.plezervi.elte.gfmwa.todonotesapp.net;

import android.content.Context;
import android.os.AsyncTask;
import hu.plezervi.elte.gfmwa.todonotesapp.GFMWAException;
import hu.plezervi.elte.gfmwa.todonotesapp.Globals;
import hu.plezervi.elte.gfmwa.todonotesapp.Utils;
import hu.plezervi.elte.gfmwa.todonotesapp.models.RegInfo;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by plezerv on 2013.09.24..
 */
public class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    protected Context context;

    public BaseAsyncTask(Context context) {
        this.context = context;
    }

    protected String doGet(String url) throws GFMWAException {
        try {
            Utils.debug(url);
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            for(String line = null; (line = reader.readLine()) != null;) {
                builder.append(line);
            }
            return builder.toString();
        } catch (Exception e) {
            throw new GFMWAException(e);
        }
    }

    protected String doPost(String url, String msg) throws GFMWAException {
        try {
            DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(msg, HTTP.UTF_8));
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            HttpResponse response = localDefaultHttpClient.execute(post);

            int code = response.getStatusLine().getStatusCode();

            if(code < 200 || code >= 300) {
                networkError(code);
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for(String line = null; (line = reader.readLine()) != null;) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (Exception x) {
            throw new GFMWAException(x);
        }
    }

    protected void networkError(int code) {

    }

    protected void process(Result result) {

    }

    protected void error() {

    }

    @Override
    protected Result doInBackground(Params... paramses) {
        return null;
    }

    @Override
    protected void onPostExecute(Result result) {
        if(result == null) error();
        else process(result);
    }
}
