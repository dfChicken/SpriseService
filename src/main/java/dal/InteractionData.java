/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import static dal.PhotoData.checkLikedPhoto;
import entity.Comment;
import entity.Photo;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author dfChicken
 */
public class InteractionData {

    public static boolean addFollow(int uid, int fuid) {
        boolean insertStatus = false;
        Connection dbConn = null;

        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception ex) {
                Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
            }

            String query = "insert into follows(user_id, follower_id) values (?,?)";

            PreparedStatement preparedStatement = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, uid);
            preparedStatement.setInt(2, fuid);

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

    public static boolean deleteFollow(int uid, int follower_id) {
        int result = 0;
        Connection dbConn = null;
        Statement stmt = null;
        String sql = "delete from follows where user_id = " + uid + " and follower_id = " + follower_id;

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

    public static boolean putPhotoLike(int uid, int pid, Timestamp created) {
        boolean insertStatus = false;
        Connection dbConn = null;

        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception ex) {
                Logger.getLogger(InteractionData.class.getName()).log(Level.SEVERE, null, ex);
            }

            String query = "insert into likes(user_id, photo_id, date_created) values (?,?,?)";

            PreparedStatement preparedStatement = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, uid);
            preparedStatement.setInt(2, pid);
            preparedStatement.setTimestamp(3, created);

            int records = preparedStatement.executeUpdate();
            if (records > 0) {
                insertStatus = true;
            }
            if (dbConn != null) {
                dbConn.close();
            }
        } catch (SQLException ex) {
            System.out.println("Duplicate entry!");
        }

        return insertStatus;
    }

    public static boolean deletePhotoLike(int uid, int photo_id) {
        int result = 0;
        Connection dbConn = null;
        Statement stmt = null;
        String sql = "delete from likes where user_id = " + uid + " and photo_id = " + photo_id;

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

    public static boolean putComment(int uid, int pid, String content, Timestamp created, Timestamp updated) {
        boolean insertStatus = false;
        Connection dbConn = null;

        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception ex) {
                Logger.getLogger(InteractionData.class.getName()).log(Level.SEVERE, null, ex);
            }

            String query = "insert into comments(user_id, photo_id, content, date_created, date_updated) values (?,?,?,?,?)";

            PreparedStatement preparedStatement = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, uid);
            preparedStatement.setInt(2, pid);
            preparedStatement.setString(3, content);
            preparedStatement.setTimestamp(4, created);
            preparedStatement.setTimestamp(5, updated);

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

    public static boolean deleteComment(int cmid, int uid, int pid) {
        int result = 0;
        Connection dbConn = null;
        Statement stmt = null;
        String sql = "delete from comments where comment_id = " + cmid + " and user_id = " + uid + " and photo_id = " + pid;

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

    public static boolean putCmtLike(int uid, int cmid) {
        boolean insertStatus = false;
        Connection dbConn = null;

        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception ex) {
                Logger.getLogger(InteractionData.class.getName()).log(Level.SEVERE, null, ex);
            }

            String query = "insert into comments(user_id, comment_id) values (?,?)";

            PreparedStatement preparedStatement = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, uid);
            preparedStatement.setInt(2, cmid);

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

    public static boolean deleteCmtLike(int uid, int cid) {
        int result = 0;
        Connection dbConn = null;
        Statement stmt = null;
        String sql = "delete from comments_likes where user_id = " + uid + " and comment_id = " + cid;

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

    public static ArrayList<Comment> getPhotoComments(int pid) {
        ArrayList<Comment> commentList = new ArrayList<>();
        Connection dbConn = null;

        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String query = "select * from \n"
                + "(select u.username,u.profile_photo_id,  photos.image_url as 'profile_photo_url', FetchComments.* from users u inner join\n"
                + "(select c.*, count(cl.user_id) as 'likes' from comments c left join comments_likes cl on c.comment_id = cl.comment_id\n"
                + "group by c.date_updated desc) as FetchComments\n"
                + "on u.user_id = FetchComments.user_id\n"
                + "left join photos on u.profile_photo_id = photos.photo_id) as FetchCommentsAndUser\n"
                + "where FetchCommentsAndUser.photo_id = " + pid;
        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                Comment c = new Comment();
                c.setUid(rs.getInt("user_id"));
                c.setPid(rs.getInt("photo_id"));
                c.setContent(rs.getString("content"));
                c.setUsername(rs.getString("username"));
                c.setLikes(rs.getInt("likes"));
                c.setProfile_photo_id(rs.getInt("profile_photo_id"));
                c.setProfile_photo_url(rs.getString("profile_photo_url"));
                c.setCreatedTime(rs.getTimestamp("date_created").getTime());
                c.setUpdatedTime(rs.getTimestamp("date_updated").getTime());
                commentList.add(c);
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
        return commentList;
    }

    public static ArrayList<User> getFollowingUsers(int uid) {
        ArrayList<User> followingUserList = new ArrayList<>();
        Connection dbConn = null;

        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String query = "select c.user_id, u.username, u.email , u.profile_photo_id, photos.image_url as 'profile_photo_url', c.isFollowing from users u inner join\n"
                + "(\n"
                + "	select follower_id as 'user_id', 'true' as 'isFollowing' from follows where user_id = " + uid + "\n"
                + ")\n"
                + "as c \n"
                + "on u.user_id = c.user_id\n"
                + "left join photos on u.profile_photo_id = photos.photo_id";
        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                User u = new User();
                u.setUid(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setProfilePhotoId(rs.getInt("profile_photo_id"));
                u.setProfile_photo_url(rs.getString("profile_photo_url"));
                u.setIsFollowing(rs.getBoolean("isFollowing"));
                followingUserList.add(u);
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
        return followingUserList;
    }

    public static ArrayList<User> getFollowerUsers(int uid) {
        ArrayList<User> followerUserList = new ArrayList<>();
        Connection dbConn = null;

        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String query = "select c.user_id, u.username, u.email , u.profile_photo_id, photos.image_url as 'profile_photo_url', c.isFollowing from users u inner join\n"
                + "(\n"
                + "	select f.*, if(f2.user_id is null, 'false','true') as 'isFollowing' from \n"
                + "	(select * from follows where follower_id = " + uid + ") as f\n"
                + "	left join follows f2 on f2.user_id = f.follower_id and f2.follower_id = f.user_id\n"
                + ")\n"
                + "as c \n"
                + "on u.user_id = c.user_id\n"
                + "left join photos on u.profile_photo_id = photos.photo_id";
        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                User u = new User();
                u.setUid(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setProfilePhotoId(rs.getInt("profile_photo_id"));
                u.setProfile_photo_url(rs.getString("profile_photo_url"));
                u.setIsFollowing(rs.getBoolean("isFollowing"));
                followerUserList.add(u);
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
        return followerUserList;
    }

    public static ArrayList<User> getUsersByNamePrefix(int uid, String prefix) {
        ArrayList<User> followerUserList = new ArrayList<>();
        Connection dbConn = null;

        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String query = "";
        // @:all search all user!
        if (prefix.equals("@:all")) {
            query = "select f.user_id, f.username, f.email, f.first_name, f.last_name, f.profile_photo_id, \n"
                    + "photos.image_url as 'profile_photo_url', if(f2.user_id is null, 'false', 'true') as 'isFollowing' from\n"
                    + "	(select *  from users where user_id <> " + uid + ") as f\n"
                    + "left join follows f2 on f2.follower_id = f.user_id and f2.user_id = " + uid + "\n"
                    + "left join photos on f.profile_photo_id = photos.photo_id";
        } else {
            query = "select f.user_id, f.username, f.email, f.first_name, f.last_name, f.profile_photo_id, \n"
                    + "photos.image_url as 'profile_photo_url', if(f2.user_id is null, 'false', 'true') as 'isFollowing' from\n"
                    + "	(select *  from users where username like '%" + prefix + "%' and user_id <> " + uid + ") as f\n"
                    + "left join follows f2 on f2.follower_id = f.user_id and f2.user_id = " + uid + "\n"
                    + "left join photos on f.profile_photo_id = photos.photo_id";
        }
        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                User u = new User();
                u.setUid(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                u.setProfilePhotoId(rs.getInt("profile_photo_id"));
                u.setProfile_photo_url(rs.getString("profile_photo_url"));
                u.setIsFollowing(rs.getBoolean("isFollowing"));
                followerUserList.add(u);
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
        return followerUserList;
    }
}
