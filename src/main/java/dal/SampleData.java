/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import static dal.UserData.registerUser;
import static dal.PhotoData.insertPhoto;
import static dal.InteractionData.addFollow;
import static dal.InteractionData.putPhotoLike;
import static dal.InteractionData.putComment;
import static dal.PhotoData.getTimelinePhotos;
import entity.Photo;
import fcm.FcmNotificationBuilder;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import util.Utils;
import static util.Utils.convertListToStringArray;

/**
 *
 * @author dfChicken
 */
public class SampleData {

    public static void main(String[] args) throws SQLException, Exception {
//        initUser();
//        initLike();
//        ArrayList<Photo> p = getTimelinePhotos(1);
//        for (int i = 0; i < p.size(); i++) {
//            System.out.println(p.get(i).toString());
//        }
//        initComment();
//        System.out.println(checkLogin("admin@gmail.com", "123"));
//        MessageData.pushMessageNotification("hay", 2, 1);
//        MessageData.pushPhotoNotification(MessageData.TYPE_LIKE,3,1);
//        PhotoData.deletePhoto(3, 3);
//        System.out.println( UserData.updateAvatar(2, 6));
        Pattern p = Pattern.compile("^[a-zA-Z0-9_.]+$");
        Matcher m = p.matcher("coinCard");
        if (m.matches()) {
            System.out.println("RegisterActivity" + "Match!");
        } else {
            System.out.println("cặc");
        }
//        FcmNotificationBuilder fcmNB = new FcmNotificationBuilder();
////        fcmNB.pushNotificationForMultiIds(tokenList);
//        for(int i=0;i<tokenList.size();i++){
//            fcmNB.pushNotificationForId(tokenList.get(i));
//        }
    }

    //initialize user data
    public static void initUser() throws Exception {
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestmp = new java.sql.Timestamp(time);
        registerUser("coinCard", "promo.chicken@gmail.com", "123", "Card", "Coin", "Hay lam!", 0, 1, 1, timestmp, timestmp);
        registerUser("dfChicken", "hainam.4795@gmail.com", "123", "Nam", "Nguyen", "Hay lam!", 0, 1, 1, timestmp, timestmp);
        registerUser("Jennifer", "thaomiuk2211@gmail.com", "2211", "Thao", "Thach", "Lac troi...", 0, 1, 1, timestmp, timestmp);
        registerUser("Mina", "Trangciuciu9692@gmail.com", "000", "Trang", "Huyen", "CC!?", 0, 1, 1, timestmp, timestmp);
        registerUser("NhocKaka", "ainguyenkaka@gmail.com", "111", "Ai", "Van", "@@", 0, 1, 1, timestmp, timestmp);
    }

    public static void initPhoto() throws SQLException {
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestmp = new java.sql.Timestamp(time);
        insertPhoto(1, "I'm sorry...", 0, 0, 57344, "http://instagram.fhan1-1.fna.fbcdn.net/t51.2885-15/e35/14677346_369695710088347_4233693574568345600_n.jpg", null, 1, 0, timestmp, timestmp, 0);
        insertPhoto(1, "First go to market with my love", 0, 0, 53248, "https://scontent.fhan1-1.fna.fbcdn.net/v/t1.0-9/15541441_1095055387271603_7045384722623337265_n.jpg?oh=918894b1eae1307f581a5a7a7585c84d&oe=5934349E", null, 1, 0, timestmp, timestmp, 0);
        insertPhoto(3, "I'm alone.. <3 <3", 0, 0, 90112, "http://scontent.fhan1-1.fna.fbcdn.net/v/t1.0-9/16683943_594452610750584_1621580591524776236_n.jpg?oh=46e696d008a2b4b15107a9e6b5147a30&oe=5900EC63", null, 1, 0, timestmp, timestmp, 0);
        insertPhoto(1, "My love...", 0, 0, 131072, "https://scontent.fhan1-1.fna.fbcdn.net/v/t1.0-9/15740931_1108288455948296_6211145670586087474_n.jpg?oh=fb54dbe3eaeaf80b9199641c0df11fa7&oe=58FD5347", null, 1, 0, timestmp, timestmp, 0);
        insertPhoto(4, "I'm preparing to sing, so all of you shut the fuck up..", 0, 0, 110592, "https://scontent.fhan1-1.fna.fbcdn.net/v/t31.0-8/p960x960/16700523_1169663416465459_8574099998480428994_o.jpg?oh=55d2d359a45361e4f0f91118d050924d&oe=593C1672", null, 1, 0, timestmp, timestmp, 0);
        insertPhoto(2, "I'm tired", 0, 0, 8192, "https://scontent.fhan1-1.fna.fbcdn.net/v/t1.0-9/12548911_1509163119390498_907125540162225688_n.jpg?oh=9131afb132a3b225f9e9f48a1d140984&oe=5905FE05", null, 1, 0, timestmp, timestmp, 0);
    }

    public static void initFollow() throws SQLException {
        addFollow(1, 3);
        addFollow(1, 4);
        addFollow(1, 5);
        addFollow(3, 1);
        addFollow(3, 2);
        addFollow(3, 4);
        addFollow(2, 4);
        addFollow(2, 5);
    }

    public static void initLike() throws SQLException {
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestmp = new java.sql.Timestamp(time);
        putPhotoLike(1, 3, timestmp);
        putPhotoLike(1, 5, timestmp);
        putPhotoLike(1, 6, timestmp);
        putPhotoLike(3, 1, timestmp);
        putPhotoLike(3, 2, timestmp);
        putPhotoLike(3, 4, timestmp);
        putPhotoLike(4, 1, timestmp);
        putPhotoLike(4, 2, timestmp);
        putPhotoLike(4, 3, timestmp);
        putPhotoLike(4, 4, timestmp);
        putPhotoLike(4, 6, timestmp);
        putPhotoLike(2, 1, timestmp);
    }

    public static void initComment() {
//        long time = System.currentTimeMillis();
//        java.sql.Timestamp timestmp = new java.sql.Timestamp(time);

//        putComment(1, 3, "Hay lam=]]", timestmp, timestmp);
//        putComment(4, 3, "bốc phét!", timestmp, timestmp);
//        putComment(3, 3, "coin", timestmp, timestmp);
//        putComment(2, 3, "card", timestmp, timestmp);
//        putComment(3, 4, "Hay lam=))", timestmp, timestmp);
    }

}
