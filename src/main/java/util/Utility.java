/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author dfChicken
 */
public class Utility {

    public static String LOGIN = "login";
    public static String PHOTO = "photo";
    public static String COMMENT = "comment";
    public static String LIKE = "like";
    public static String COMMENT_LIKE = "comment_like";
    public static String FOLLOW = "follow";
    public static String TAG = "TAG";
    public static String DELETE = "delete";
    public static String UPDATE = "update";

    /**
     * Null check Method
     *
     * @param txt
     * @return
     */
    public static boolean isNotNull(String txt) {
        // System.out.println("Inside isNotNull");
        return txt != null && txt.trim().length() >= 0 ? true : false;
    }

    /**
     * Method to construct JSON with Error Msg
     *
     * @param tag
     * @param status
     * @param err_msg
     * @return
     */
    public static String constructJSON(String tag, boolean status, String err_msg) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("error_msg", err_msg);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }

    /**
     * Method to construct JSON
     *
     * @param tag
     * @param status
     * @return
     */
    public static String constructJSON(String tag, boolean status) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }

    public static String entityJsonResponse(String tag, int id, String type, boolean status) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("type", type);
            obj.put("id", id);
            obj.put("status", status);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }

    public static String loginSuccess(boolean status, int uid, String token) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", LOGIN);
            obj.put("status", status);
            obj.put("uid", uid);
            obj.put("token", token);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }

    public static String loginFailed(boolean status, String err_msg) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", LOGIN);
            obj.put("status", status);
            obj.put("error_msg", err_msg);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }

    public static String constructFailed(boolean status, String err_msg) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", "upload");
            obj.put("status", status);
            obj.put("error_msg", "" + err_msg);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }

}
