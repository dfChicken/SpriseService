/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.io.Serializable;

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

    @JsonGetter("lname")
    public String getLastName() {
        return lname;
    }

    @JsonSetter("lname")
    public void setLastName(String lname) {
        this.lname = lname;
    }

    @JsonGetter("des")
    public String getDescription() {
        return des;
    }

    @JsonSetter("des")
    public void setDescription(String des) {
        this.des = des;
    }

    @JsonGetter("profile_photo_id")
    public int getProfilePhotoId() {
        return profile_photo_id;
    }

    @JsonSetter("profile_photo_id")
    public void setProfilePhotoId(int profile_photo_id) {
        this.profile_photo_id = profile_photo_id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @JsonGetter("user_status")
    public int getUserStatus() {
        return user_status;
    }

    @JsonSetter("user_status")
    public void setUserStatus(int user_status) {
        this.user_status = user_status;
    }

    @JsonGetter("user_activated")
    public int getUserActivated() {
        return user_activated;
    }

    @JsonSetter("user_activated")
    public void setUserActivated(int user_activated) {
        this.user_activated = user_activated;
    }

    @JsonGetter("created")
    public long getCreatedTime() {
        return created;
    }

    @JsonSetter("created")
    public void setCreatedTime(long created) {
        this.created = created;
    }

    @JsonGetter("updated")
    public long getUpdatedTime() {
        return updated;
    }

    @JsonSetter("updated")
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
