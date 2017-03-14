/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Timestamp;

/**
 *
 * @author dfChicken
 */
public class Comment {

    private String username;
    private int profile_photo_id;
    private String profile_image_url;
    private int uid;
    private int pid;
    private String content;
    private long created;
    private long updated;
    private int likes;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProfile_photo_id() {
        return profile_photo_id;
    }

    public void setProfile_photo_id(int profile_photo_id) {
        this.profile_photo_id = profile_photo_id;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatedTime() {
        return created;
    }

    public void setCreatedTime(long created) {
        this.created = created;
    }

    public long getUpdatedTime() {
        return updated;
    }

    public void setUpdatedTime(long updated) {
        this.updated = updated;
    }
}
