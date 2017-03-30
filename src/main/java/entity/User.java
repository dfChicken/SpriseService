/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author dfChicken
 */
public class User implements Serializable {

    private int uid;
    private String username;
    private String email;
    private String password;
    private String fname;
    private String lname;
    private String des;
    private int profile_photo_id;
    private String profile_photo_url;
    private int gender;
    private int user_status;
    private int user_activated;
    private long created;
    private long updated;
    private int posts;
    private int followers;
    private int following;
    private boolean isFollowing;

    //have to create new getter and setter for jackson serialize
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
//end add new getter setter above!

    public boolean isIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonGetter("fname")
    public String getFirstName() {
        return fname;
    }

    @JsonSetter("fname")
    public void setFirstName(String fname) {
        this.fname = fname;
    }

    public String getLastName() {
        return lname;
    }

    public void setLastName(String lname) {
        this.lname = lname;
    }

    public String getDescription() {
        return des;
    }

    public void setDescription(String des) {
        this.des = des;
    }

    public int getProfilePhotoId() {
        return profile_photo_id;
    }

    public void setProfilePhotoId(int profile_photo_id) {
        this.profile_photo_id = profile_photo_id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getUserStatus() {
        return user_status;
    }

    public void setUserStatus(int user_status) {
        this.user_status = user_status;
    }

    public int getUserActivated() {
        return user_activated;
    }

    public void setUserActivated(int user_activated) {
        this.user_activated = user_activated;
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

    public String getProfile_photo_url() {
        return profile_photo_url;
    }

    public void setProfile_photo_url(String profile_photo_url) {
        this.profile_photo_url = profile_photo_url;
    }

    @Override
    public String toString() {
        return "User{" + "uid=" + uid + ", username=" + username + ", email=" + email + ", password=" + password + ", fname=" + fname + ", lname=" + lname + ", des=" + des + ", profile_photo_id=" + profile_photo_id + ", gender=" + gender + ", user_status=" + user_status + ", user_activated=" + user_activated + ", created=" + created + ", updated=" + updated + '}';
    }

//    class LoginSuccess {
//        
//    }
}
