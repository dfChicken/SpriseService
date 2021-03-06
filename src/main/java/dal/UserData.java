/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import static dal.PhotoData.checkLikedPhoto;
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
    public static boolean checkEmailExist(String email) {
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
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (dbConn != null) {
                    dbConn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
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
            Timestamp created, Timestamp updated) {

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
                ex.printStackTrace();
            } finally {
                try {
                    if (dbConn != null) {
                        dbConn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
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
                user.setCreatedTime(rs.getTimestamp("date_created").getTime());
                user.setUpdatedTime(rs.getTimestamp("date_updated").getTime());
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

    public static User getUserDataLite(int uid) {
        User user = null;
        Connection dbConn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String query = "select u.*, photos.image_url as 'profile_photo_url' from\n"
                + "(select * from users where user_id = " + uid + ") as u\n"
                + "left join photos on u.profile_photo_id = photos.photo_id";

        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                user = new User();
                user.setUid(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword("");
                user.setFirstName("");
                user.setLastName("");
                user.setDescription("");
                user.setProfilePhotoId(rs.getInt("profile_photo_id"));
                user.setProfile_photo_url(rs.getString("profile_photo_url"));
                user.setGender(rs.getInt("gender"));
                user.setUserStatus(rs.getInt("user_status"));
                user.setUserActivated(rs.getInt("user_activated"));
                user.setCreatedTime(rs.getTimestamp("date_created").getTime());
                user.setUpdatedTime(rs.getTimestamp("date_updated").getTime());
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
                user.setCreatedTime(rs.getTimestamp("date_created").getTime());
                user.setUpdatedTime(rs.getTimestamp("date_updated").getTime());
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

    public static boolean updateAvatar(int uid, int pid) {
        int result = 0;
        Connection dbConn = null;
        Statement st = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception ex) {
                Logger.getLogger(PhotoData.class.getName()).log(Level.SEVERE, null, ex);
            }

            String queryUpdatePhoto = "update photos set is_avatar = 1 where photo_id = " + pid;
            String queryUpdateAva = "update users set profile_photo_id = " + pid + " where user_id = " + uid;

            st = dbConn.createStatement();
            if (st.executeUpdate(queryUpdatePhoto) > 0) {
                if (st.executeUpdate(queryUpdateAva) > 0) {
                    result = 1;
                }
            }
            if (dbConn != null) {
                dbConn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
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
        return result > 0;
    }
}
