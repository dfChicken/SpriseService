/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.PhotoData.checkLikedPhoto;
import entity.Photo;
import entity.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dfChicken
 */
public class UserData {

    /**
     * Method to check whether email and pwd combination are correct
     *
     * @param email
     * @param pwd
     * @return
     * @throws Exception
     */
    public static boolean checkLogin(String email, String pwd) {
        boolean isUserAvailable = false;
        Connection dbConn = null;
        ResultSet rs = null;
        Statement st = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            st = dbConn.createStatement();
            String query = "SELECT * FROM users WHERE email = '" + email
                    + "' AND salted_password=" + "'" + pwd + "'";
            //System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                //System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
                isUserAvailable = true;
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
        return isUserAvailable;
    }

    public static int getUserId(String email, String pass) {
        Connection dbConn = null;
        Statement st = null;
        ResultSet rs = null;
        int uid = -1;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String query = "select * from users where email='" + email + "' and salted_password= '" + pass + "'";

        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                uid = rs.getInt("user_id");
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

        return uid;
    }

    /**
     * Method to check whether email is existed or not
     *
     * @param email
     * @return
     * @throws Exception
     */
    public static boolean checkEmailExist(String email) throws Exception {
        boolean isEmailExisted = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "SELECT * FROM users WHERE email = '" + email + "'";
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
                isEmailExisted = true;
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return isEmailExisted;
    }

    /**
     * Method to insert uname and pwd in DB
     *
     * @param name
     * @param uname
     * @param pwd
     * @return
     * @throws SQLException
     * @throws Exception
     */
    @Deprecated
    public static boolean insertUser(String name, String uname, String pwd) throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "INSERT into user(name, username, password) values('" + name + "'," + "'"
                    + uname + "','" + pwd + "')";
            //System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                insertStatus = true;
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return insertStatus;
    }

    /**
     * Method to insert new user
     *
     * @param usrname
     * @param email
     * @param password
     * @param fname
     * @param des
     * @param gender
     * @param status
     * @param activated
     * @param created
     * @param updated
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static boolean registerUser(String usrname, String email, String password, String fname,
            String lname, String des, int gender, int status, int activated,
            Timestamp created, Timestamp updated) throws SQLException, Exception {

        boolean insertStatus = false;
        Connection dbConn = null;

        if (!checkEmailExist(email)) {
            try {
                try {
                    dbConn = DBConnection.createConnection();
                } catch (Exception ex) {
                    Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
                }

                String query = "insert into users(username, email, salted_password, first_name, last_name, description,"
                        + "gender, user_status, user_activated, date_created, date_updated) values (?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement preparedStatement = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, usrname);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, fname);
                preparedStatement.setString(5, lname);
                preparedStatement.setString(6, des);
                preparedStatement.setInt(7, gender);
                preparedStatement.setInt(8, status);
                preparedStatement.setInt(9, activated);
                preparedStatement.setTimestamp(10, created);
                preparedStatement.setTimestamp(11, updated);

                int records = preparedStatement.executeUpdate();
                if (records > 0) {
                    insertStatus = true;
                }
            } catch (SQLException ex) {
                if (dbConn != null) {
                    dbConn.close();
                }
                throw ex;
            } finally {
                if (dbConn != null) {
                    dbConn.close();
                }
            }
        }

        return insertStatus;
    }

    public static User getUserData(int uid, int password) {
        User user = null;
        Connection dbConn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String query = "select * from users where user_id='" + uid + "' and salted_password= '" + password + "'";

        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                user = new User();
                user.setUid(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("salted_password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setDescription(rs.getString("description"));
                user.setProfilePhotoId(rs.getInt("profile_photo_id"));
                user.setGender(rs.getInt("gender"));
                user.setUserStatus(rs.getInt("user_status"));
                user.setUserActivated(rs.getInt("user_activated"));
                user.setCreatedTime(rs.getTimestamp("date_created"));
                user.setUpdatedTime(rs.getTimestamp("date_updated"));
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

        if (null != user) {
            return user;
        } else {
            return null;
        }
    }

    public static User getSingleUserWithDetails(int uid) {
        User user = null;
        Connection dbConn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String query = "select  u.*, x.followers, y.following, z.posts from \n"
                + "(select count(follower_id) as followers from follows where user_id = " + uid + ") as x,\n"
                + "(select count(user_id) as following from follows where follower_id = " + uid + ") as y,\n"
                + "(select count(photo_id) as posts from photos where user_id = " + uid + ") as z,\n"
                + "(select * from users where user_id = " + uid + ") as u";

        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                user = new User();
                user.setUid(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("salted_password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setDescription(rs.getString("description"));
                user.setProfilePhotoId(rs.getInt("profile_photo_id"));
                user.setGender(rs.getInt("gender"));
                user.setUserStatus(rs.getInt("user_status"));
                user.setUserActivated(rs.getInt("user_activated"));
                user.setCreatedTime(rs.getTimestamp("date_created"));
                user.setUpdatedTime(rs.getTimestamp("date_updated"));
                user.setPosts(rs.getInt("posts"));
                user.setFollowers(rs.getInt("followers"));
                user.setFollowing(rs.getInt("following"));
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

        if (null != user) {
            return user;
        } else {
            return null;
        }
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
}
