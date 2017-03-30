/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Photo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dfChicken
 */
public class PhotoData {

    public static boolean insertPhoto(int uid, String caption, float lat, float longt,
            long size, String url, String down_url, int status, int isAvatar, Timestamp created, Timestamp updated) {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception ex) {
                Logger.getLogger(PhotoData.class.getName()).log(Level.SEVERE, null, ex);
            }

            String query = "insert into photos(user_id, caption, latitude, longtitude, image_size, image_url,"
                    + "downsized_image_url, image_status, is_avatar, date_created, date_updated) values (?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, uid);
            preparedStatement.setString(2, caption);
            preparedStatement.setFloat(3, lat);
            preparedStatement.setFloat(4, longt);
            preparedStatement.setLong(5, size);
            preparedStatement.setString(6, url);
            preparedStatement.setString(7, down_url);
            preparedStatement.setInt(8, status);
            preparedStatement.setInt(9, isAvatar);
            preparedStatement.setTimestamp(10, created);
            preparedStatement.setTimestamp(11, updated);

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

    public static boolean updatePhotoStatus(int pid, int uid, int status) throws SQLException {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception ex) {
                Logger.getLogger(PhotoData.class.getName()).log(Level.SEVERE, null, ex);
            }

            String query = "update photos set image_status = ?"
                    + " where photo_id = ? and user_id = ?";

            PreparedStatement preparedStatement = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, pid);
            preparedStatement.setInt(3, uid);

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
        return insertStatus;
    }

    public static boolean checkLikedPhoto(int uid, int pid) {
        boolean isLiked = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "select 1 from likes where likes.user_id = " + uid + " and likes.photo_id =" + pid;
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                //System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
                isLiked = true;
            }
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (dbConn != null) {
                dbConn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return isLiked;
    }

    public static Photo getSinglePhoto(int pid) {
        Photo p = null;
        Connection dbConn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String query = "select photos.photo_id, photos.user_id, photos.caption, photos.latitude, photos.longtitude, photos.image_size, \n"
                + "photos.image_url, photos.downsized_image_url, photos.image_status, photos.date_created, photos.date_updated, count(likes.user_id) as 'likes'\n"
                + "from photos left join likes on photos.photo_id = likes.photo_id and photos.photo_id = " + pid;
        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                p = new Photo();
                p.setPid(rs.getInt("photo_id"));
                p.setUid(rs.getInt("user_id"));
                p.setCaption(rs.getString("caption"));
                p.setLat(rs.getFloat("latitude"));
                p.setLongt(rs.getFloat("longtitude"));
                p.setSize(rs.getLong("image_size"));
                p.setUrl(rs.getString("image_url"));
                p.setDownsized_url(rs.getString("downsized_image_url"));
                p.setStatus(rs.getInt("image_status"));
                p.setCreatedTime(rs.getTimestamp("date_created").getTime());
                p.setUpdatedTime(rs.getTimestamp("date_updated").getTime());
                p.setLikes(rs.getInt("likes"));
//                System.out.println(p.getCreatedTime().getTime());
                p.setIsLiked(checkLikedPhoto(rs.getInt("user_id"), rs.getInt("photo_id")));
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

        if (null != p) {
            return p;
        } else {
            return null;
        }
    }

    public static boolean deletePhoto(int uid, int pid) {
        int result = 0;
        Connection dbConn = null;
        Statement stmt = null;
        String sql = "delete from photos where photo_id = " + pid + " and user_id = " + uid;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            Logger.getLogger(PhotoData.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            stmt = dbConn.createStatement();
            result = stmt.executeUpdate(sql);
            stmt.close();
            dbConn.close();
        } catch (SQLException ex) {
            Logger.getLogger(PhotoData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result > 0;
    }

    public static boolean updatePhotoCaption(int uid, int pid, String caption) throws SQLException {
        boolean result = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception ex) {
                Logger.getLogger(PhotoData.class.getName()).log(Level.SEVERE, null, ex);
            }

            String query = "update photos set caption = ?"
                    + " where photo_id = ? and user_id = ?";

            PreparedStatement preparedStatement = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, caption);
            preparedStatement.setInt(2, pid);
            preparedStatement.setInt(3, uid);

            int records = preparedStatement.executeUpdate();
            if (records > 0) {
                result = true;
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
        return result;
    }

    public static ArrayList<Photo> getTimelinePhotos(int uid) {
        ArrayList<Photo> timelinePhotos = new ArrayList<>();
        Connection dbConn = null;

        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String query = "select TimelinePhotos.*, ph.image_url as 'profile_image_url', count(comments.user_id) as 'comments' from\n"
                + "(select users.username, users.profile_photo_id, FetchPhotos.* from\n"
                + "(select FetchLikesComments.*, count(likes.user_id) as 'likes' from \n"
                + "(select p.* , if(likes.photo_id is null, 'false','true') as 'isLiked'\n"
                + "from \n"
                + "(select photos.* from photos inner join (select follows.follower_id as uid from follows where follows.user_id = " + uid + ") as Following\n"
                + "where photos.user_id = Following.uid \n"
                + "union\n"
                + "select * from photos where user_id = " + uid + "\n"
                + ") as p left join likes on likes.photo_id = p.photo_id and likes.user_id = " + uid + ")\n"
                + "as FetchLikesComments\n"
                + "left join likes on FetchLikesComments.photo_id = likes.photo_id\n"
                + "group by FetchLikesComments.photo_id ) \n"
                + "as FetchPhotos \n"
                + "inner join users where users.user_id = FetchPhotos.user_id \n"
                + ") as TimelinePhotos\n"
                + "left join photos ph on TimelinePhotos.profile_photo_id = ph.photo_id\n"
                + "left join comments on TimelinePhotos.photo_id = comments.photo_id\n"
                + "group by date_updated desc";
        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                Photo p = new Photo();
                p.setUid(rs.getInt("user_id"));
                p.setUsername(rs.getString("username"));
                p.setPid(rs.getInt("photo_id"));
                p.setProfile_photo_id(rs.getInt("profile_photo_id"));
                p.setProfile_image_url(rs.getString("profile_image_url"));
                p.setCaption(rs.getString("caption"));
                p.setLat(rs.getFloat("latitude"));
                p.setLongt(rs.getFloat("longtitude"));
                p.setSize(rs.getLong("image_size"));
                p.setUrl(rs.getString("image_url"));
                p.setDownsized_url(rs.getString("downsized_image_url"));
                p.setStatus(rs.getInt("image_status"));
                p.setCreatedTime(rs.getTimestamp("date_created").getTime());
                p.setUpdatedTime(rs.getTimestamp("date_updated").getTime());
                p.setLikes(rs.getInt("likes"));
                p.setComments(rs.getInt("comments"));
                p.setIsLiked(rs.getBoolean("isLiked"));
                timelinePhotos.add(p);
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
        return timelinePhotos;
    }

    public static ArrayList<Photo> getUserPhotos(int uid) {
        ArrayList<Photo> timelinePhotos = new ArrayList<>();
        Connection dbConn = null;

        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String query = "select FetchLikes.*, count(likes.user_id) as 'likes' from \n"
                + "(select p.* , if(likes.photo_id is null, 'false','true') as 'isLiked'\n"
                + "from (select photos.* from photos where photos.user_id = " + uid + ") as p\n"
                + "left join likes on likes.photo_id = p.photo_id and likes.user_id = " + uid + ") as FetchLikes\n"
                + "left join likes on FetchLikes.photo_id = likes.photo_id\n"
                + "group by FetchLikes.photo_id";
        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                Photo p = new Photo();
                p.setPid(rs.getInt("photo_id"));
                p.setUid(rs.getInt("user_id"));
                p.setCaption(rs.getString("caption"));
                p.setLat(rs.getFloat("latitude"));
                p.setLongt(rs.getFloat("longtitude"));
                p.setSize(rs.getLong("image_size"));
                p.setUrl(rs.getString("image_url"));
                p.setDownsized_url(rs.getString("downsized_image_url"));
                p.setStatus(rs.getInt("image_status"));
                p.setCreatedTime(rs.getTimestamp("date_created").getTime());
                p.setUpdatedTime(rs.getTimestamp("date_updated").getTime());
                p.setLikes(rs.getInt("likes"));
                p.setIsLiked(rs.getBoolean("isLiked"));
                timelinePhotos.add(p);
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
        return timelinePhotos;
    }

    public static ArrayList<Photo> getAllPhotosExceptUser(int uid) {
        ArrayList<Photo> allPhotos = new ArrayList<>();
        Connection dbConn = null;

        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String query = "select FetchLikes.*, count(likes.user_id) as 'likes' from \n"
                + "(select p.* , if(likes.photo_id is null, 'false','true') as 'isLiked'\n"
                + "from (select photos.* from photos where photos.user_id <> " + uid + ") as p\n"
                + "left join likes on likes.photo_id = p.photo_id and likes.user_id <> " + uid + ") as FetchLikes\n"
                + "left join likes on FetchLikes.photo_id = likes.photo_id\n"
                + "group by FetchLikes.photo_id";
        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                Photo p = new Photo();
                p.setPid(rs.getInt("photo_id"));
                p.setUid(rs.getInt("user_id"));
                p.setCaption(rs.getString("caption"));
                p.setLat(rs.getFloat("latitude"));
                p.setLongt(rs.getFloat("longtitude"));
                p.setSize(rs.getLong("image_size"));
                p.setUrl(rs.getString("image_url"));
                p.setDownsized_url(rs.getString("downsized_image_url"));
                p.setStatus(rs.getInt("image_status"));
                p.setCreatedTime(rs.getTimestamp("date_created").getTime());
                p.setUpdatedTime(rs.getTimestamp("date_updated").getTime());
                p.setLikes(rs.getInt("likes"));
                p.setIsLiked(rs.getBoolean("isLiked"));
                allPhotos.add(p);
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
        return allPhotos;
    }

    public static int getPhotoOwnerId(int pid) {
        int user_id = -1;
        Connection dbConn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            dbConn = DBConnection.createConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String query = "select user_id from photos where photos.photo_id = " + pid;

        try {
            st = dbConn.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                user_id = rs.getInt("user_id");
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

        return user_id;
    }
}
