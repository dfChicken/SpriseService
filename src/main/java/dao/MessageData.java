/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Notification;
import entity.User;
import fcm.FcmNotificationBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author dfChicken
 */
public class MessageData {

    public static boolean addUserFirebaseToken(int uid, String token) {
        boolean insertStatus = false;
        Connection dbConn = null;

        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception ex) {
                Logger.getLogger(MessageData.class.getName()).log(Level.SEVERE, null, ex);
            }

            String query = "insert into firebase_token(user_id, token) values (?,?)";

            PreparedStatement preparedStatement = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, uid);
            preparedStatement.setString(2, token);

            int records = preparedStatement.executeUpdate();
            if (records > 0) {
                insertStatus = true;
            }
            if (dbConn != null) {
                dbConn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return insertStatus;
    }

    public static boolean deleteUserFirebaseToken(int uid, String token) {
        int result = 0;
        Connection dbConn = null;
        Statement stmt = null;
        String sql = "delete from firebase_token where user_id = " + uid + " and token = \"" + token + "\"";

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            Logger.getLogger(InteractionData.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stmt = dbConn.createStatement();
            result = stmt.executeUpdate(sql);
            stmt.close();
            dbConn.close();
        } catch (SQLException ex) {
            Logger.getLogger(InteractionData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result > 0;
    }

    public static boolean updateUserFirebaseToken(int uid, String token, String oldToken) {
        int result = 0;
        Connection dbConn = null;
        Statement stmt = null;
        String sql = "update firebase_token set token = \"" + token + "\"\n"
                + "where user_id = " + uid + " and token = \"" + oldToken + "\"";

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            Logger.getLogger(InteractionData.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stmt = dbConn.createStatement();
            result = stmt.executeUpdate(sql);
            stmt.close();
            dbConn.close();
        } catch (SQLException ex) {
            Logger.getLogger(InteractionData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result > 0;
    }

    public static boolean addChatRoom(int sender_id, int receiver_id) {
        boolean insertStatus = false;
        Connection dbConn = null;

        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception ex) {
                Logger.getLogger(MessageData.class.getName()).log(Level.SEVERE, null, ex);
            }

            String query = "insert into firebase_chatted(sender_id, receiver_id) values (?,?)";

            PreparedStatement preparedStatement = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, sender_id);
            preparedStatement.setInt(2, receiver_id);

            int records = preparedStatement.executeUpdate();
            if (records > 0) {
                insertStatus = true;
            }
            if (dbConn != null) {
                dbConn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return insertStatus;
    }

    public static ArrayList<String> getUserFirebaseTokenList(int uid) {
        ArrayList<String> uFirebaseTokenList = new ArrayList<>();
        Connection dbConn = null;

        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String query = "select token from firebase_token where user_id = " + uid;

        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                uFirebaseTokenList.add(rs.getString("token"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (dbConn != null) {
                    dbConn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return uFirebaseTokenList;
    }

    public static ArrayList<User> getChattedFirebaseList(int currentId) {
        ArrayList<User> chattedUsers = new ArrayList<>();

        Connection dbConn = null;

        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String query = "select c.user_id, u.username, u.email , u.profile_photo_id, photos.image_url as 'profile_photo_url' from users u inner join\n"
                + "(select sender_id as 'user_id' from firebase_chatted where receiver_id = " + currentId + " \n"
                + "union\n"
                + "select receiver_id as 'user_id' from firebase_chatted where sender_id = " + currentId + "\n"
                + ") as c \n"
                + "on u.user_id = c.user_id\n"
                + "left join photos on u.profile_photo_id = photos.photo_id";

        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                User u = new User();
                u.setUid(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setProfile_photo_url(rs.getString("profile_photo_url"));
                u.setEmail(rs.getString("email"));
                chattedUsers.add(u);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (dbConn != null) {
                    dbConn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return chattedUsers;
    }

    public static ArrayList<Notification> getPhotosNotificationList(int uid) {
        ArrayList<Notification> notiList = new ArrayList<>();
        Connection dbConn = null;

        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String query = "select * from\n"
                + "(select u.username, '' as id, l.user_id, l.photo_id, l.date_created as timedate , p.image_url , 'like' as type from users u inner join likes l inner join (select * from photos where user_id = " + uid + ") as p\n"
                + "where l.user_id = u.user_id and l.photo_id = p.photo_id\n"
                + "union\n"
                + "select u.username, cm.comment_id as id, cm.user_id, cm.photo_id , cm.date_updated as timedate , p.image_url, 'comment' as type from users u inner join comments cm inner join (select * from photos where user_id = " + uid + ") as p\n"
                + "where cm.user_id = u.user_id and cm.photo_id = p.photo_id) as notification where user_id <> " + uid + "\n"
                + "order by timedate desc";
        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                Notification n = new Notification();
                n.setUsername(rs.getString("username"));
                n.setId(rs.getInt("id"));
                n.setUid(rs.getInt("user_id"));
                n.setPid(rs.getInt("photo_id"));
                n.setTimedate(rs.getTimestamp("timedate").getTime());
                n.setImage_url(rs.getString("image_url"));
                n.setType(rs.getString("type"));
                notiList.add(n);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (dbConn != null) {
                    dbConn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return notiList;
    }

    public static void pushMessageNotification(String message, int sender_id, int receiver_id) {
        ArrayList<String> tokenList = MessageData.getUserFirebaseTokenList(receiver_id);
        FcmNotificationBuilder fnb = new FcmNotificationBuilder();
        JSONObject jSONObject;
        if (tokenList.size() > 0) {
            for (int i = 0; i < tokenList.size(); i++) {
                jSONObject = fnb.getJsonBodyForPushMessage(tokenList.get(i), message, sender_id);
                fnb.pushNotification2(receiver_id, jSONObject);
            }
        }

    }

}
