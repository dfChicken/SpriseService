/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.fb;

/**
 *
 * @author dfChicken
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class FBGraph {

    private String accessToken;

    public FBGraph(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getFBGraph() {
        String graph = null;
        try {
            System.out.println("accessToken: " + accessToken);
            String g = "https://graph.facebook.com/me?fields=email,birthday,location,locale,age_range,currency,first_name,last_name,name_format,gender&" + accessToken;
            URL u = new URL(g);
            URLConnection c = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    c.getInputStream()));
            String inputLine;
            StringBuffer b = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                b.append(inputLine + "\n");
            }
            in.close();
            graph = b.toString();
            System.out.println("GraphJSON: " + graph);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in getting FB graph data. " + e);
        }
        return graph;
    }

    public Map<String, String> getGraphData(String fbGraph) {
        Map<String, String> fbProfile = new HashMap<String, String>();
        try {
            JSONObject json = new JSONObject(fbGraph);
            fbProfile.put("id", json.getString("id"));
            fbProfile.put("first_name", json.getString("first_name"));
            if (json.has("email")) {
                fbProfile.put("email", json.getString("email"));
            }
            if (json.has("gender")) {
                fbProfile.put("gender", json.getString("gender"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in parsing FB graph data. " + e);
        }
        return fbProfile;
    }
}
