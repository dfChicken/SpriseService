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
public class Photo {

    private int uid;
    private String username;
    private int profile_photo_id;
    private String profile_image_url;
    private int pid;
    private String caption;
    private float lat;
    private float longt;
    private int size;
    private String url;
    private String downsized_url;
    private int status;
    private long created;
    private long updated;

    private boolean isLiked;
    private int likes;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLongt() {
        return longt;
    }

    public void setLongt(float longt) {
        this.longt = longt;
    }

    public long getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDownsized_url() {
        return downsized_url;
    }

    public void setDownsized_url(String downsized_url) {
        this.downsized_url = downsized_url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public boolean isLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

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

    @Override
    public String toString() {
        return "Photo{" + "username=" + username + ", pid=" + pid + ", uid=" + uid + ", caption=" + caption + ", lat=" + lat + ", longt=" + longt + ", size=" + size + ", url=" + url + ", downsized_url=" + downsized_url + ", status=" + status + ", created=" + created + ", updated=" + updated + ", profile_photo_id=" + profile_photo_id + ", isLiked=" + isLiked + ", likes=" + likes + '}';
    }

}
