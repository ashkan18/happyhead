package com.happyhead;

import android.content.Context;
import android.content.res.AssetManager;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/2/14
 * Time: 7:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class AppConfig {
    public static final String ASSET_FILENAME_CONFJSON = "com.happyhead.conf.json";
    private static final Map<String, String> confParameters = new HashMap<String, String>();

    public AppConfig(Context ctx) {
        InputStream conf = null;
        BufferedReader reader = null;

        // open the file
        AssetManager am = ctx.getAssets();
        try {
            conf = am.open(ASSET_FILENAME_CONFJSON, AssetManager.ACCESS_BUFFER);
            reader = new BufferedReader(new InputStreamReader(conf));

            // load the entire file into working memory
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            // dump the loaded file into a json parser to handle the reading of the json
            JSONObject json = new JSONObject(sb.toString());

            // load all known keys into the hashmap for caching
            Iterator it = json.keys();
            String json_key;
            while (it.hasNext()) {
                json_key = (String) it.next();
                confParameters.put(json_key, json.optString(json_key, null));
            }

        } catch (Exception e) {

        }
    }

    public String getConfig(String key) {
        return confParameters.get(key);
    }
}
