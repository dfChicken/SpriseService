/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fcm;

import dao.MessageData;
import dao.UserData;
import entity.User;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import util.Utils;

/**
 *
 * @author dfChicken
 */
public class FcmNotificationBuilder {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String SERVER_API_KEY = "AAAABsoWDi0:APA91bGxAjorEWW66RIHeiUwMK-5cxqVtmp6sZEj5zUaPJhT9fRD9Sl39TwEliUokAC2wjnnvFNcRjM378ZxpVqDGyoKCE-qoHzScPy7AXBz8g2pnIYOTCaFSRqzwe_Woil9oojjNl07";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String AUTH_KEY = "key=" + SERVER_API_KEY;
    private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
    // json related keys
    private static final String KEY_TO = "to";
    private static final String KEY_REG_IDS = "registration_ids";
    private static final String KEY_NOTIFICATION = "notification";
    // json related data key for message notification push
    private static final String KEY_SENDER_NAME = "sender_name";
    private static final String KEY_TEXT = "text";
    private static final String KEY_EMAIL = "email";
    // json related data key for like or comment notification push
    private static final String KEY_PHOTO_ID = "pid";
    private static final String KEY_SENDER_ID = "sender_id";
    private static final String KEY_RECEIVER_ID = "receiver_id";
    private static final String KEY_PUSH_TYPE = "type";
    // object data
    private static final String KEY_DATA = "data";

//    private static final String KEY_FCM_TOKEN = "fcm_token";
    private ArrayList<String> tokenList;
    private String token;

    //không đề nghị sử dụng, phương thức này khiến máy chủ gửi quá nhiều response
    public void pushNotification(final int receiver_id, final JSONObject jSONObjectBody) {
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, jSONObjectBody.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new Request.Builder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .addHeader(AUTHORIZATION, AUTH_KEY)
                .url(FCM_URL)
                .post(requestBody)
                .build();

        Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                System.out.println("onGetAllUsersFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.getInt("failure") == 1) {
                    String failedToken = jSONObjectBody.getString(KEY_TO);
                    //tự động xóa failed id
                    System.out.println("Send push message failed for regId: " + jSONObjectBody.getString(KEY_TO));
                    MessageData.deleteUserFirebaseToken(receiver_id, failedToken);
                }

            }
        });
    }

    public void pushMsgNoti(final int receiver_id, final JSONObject jSONObjectBody) {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();

            HttpPost request = new HttpPost(FCM_URL);
            request.addHeader(AUTHORIZATION, AUTH_KEY);
            request.addHeader(CONTENT_TYPE, APPLICATION_JSON);

            HttpEntity entity = new StringEntity(jSONObjectBody.toString(), "UTF-8");
            request.setEntity(entity);

            CloseableHttpResponse response = client.execute(request);

            String responseAsString = null;
            int statusCode = response.getStatusLine().getStatusCode();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);
            responseAsString = out.toString("UTF-8");
            out.close();

//            System.out.println(responseAsString);
            JSONObject jsonObject = new JSONObject(responseAsString);
            if (jsonObject.getInt("failure") == 1) {
                String failedToken = jSONObjectBody.getString(KEY_TO);
                //tự động xóa failed id
                System.out.println("Send push message failed for regId: " + jSONObjectBody.getString(KEY_TO));
                MessageData.deleteUserFirebaseToken(receiver_id, failedToken);
            }

            response.close();
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(FcmNotificationBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public JSONObject getJsonMsgNotification(String token, String message, int sender_id, int receiver_id) {
        User sender = UserData.getUserDataLite(sender_id);

        JSONObject jsonObjectBody = new JSONObject();
        try {
            //token
            jsonObjectBody.put(KEY_TO, token);
            JSONObject jsonObjectData = new JSONObject();
            //data
            jsonObjectData.put(KEY_SENDER_NAME, sender.getUsername());
            jsonObjectData.put(KEY_TEXT, message);
            jsonObjectData.put(KEY_EMAIL, sender.getEmail());
            jsonObjectData.put(KEY_SENDER_ID, sender_id);
            jsonObjectData.put(KEY_RECEIVER_ID, receiver_id);
            //object data
            jsonObjectBody.put(KEY_DATA, jsonObjectData);
            System.out.println(jsonObjectBody.toString());
        } catch (JSONException e) {
            System.out.println("getValidJsonBody: An error occurred when create new json object!");
        }
        return jsonObjectBody;
    }

    public JSONObject getJsonPhotoNotification(String token, int photo_id, int sender_id, int receiver_id, int type) {
        User sender = UserData.getUserDataLite(sender_id);

        JSONObject jsonObjectBody = new JSONObject();
        try {
            //token
            jsonObjectBody.put(KEY_TO, token);
            JSONObject jsonObjectData = new JSONObject();
            //data
            jsonObjectData.put(KEY_PUSH_TYPE, type);
            jsonObjectData.put(KEY_PHOTO_ID, photo_id);
            jsonObjectData.put(KEY_SENDER_ID, sender_id);
            jsonObjectData.put(KEY_SENDER_NAME, sender.getUsername());
            jsonObjectData.put(KEY_RECEIVER_ID, receiver_id);

            //object data
            jsonObjectBody.put(KEY_DATA, jsonObjectData);
            System.out.println(jsonObjectBody.toString());
        } catch (JSONException e) {
            System.out.println("getValidJsonBody: An error occurred when create new json object!");
        }
        return jsonObjectBody;
    }

}
