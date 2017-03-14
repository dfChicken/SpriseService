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
public class User {

    private int uid;
    private String username;
    private String email;
    private String password;
    private String fname;
    private String lname;
    private String des;
    private int profile_photo_id;
    private int gender;
    private int user_status;
    private int user_activated;
    private Timestamp created;
    private Timestamp updated;
    private int posts;
    private int followers;
    private int following;

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

    public String getFirstName() {
        return fname;
    }

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

    public Timestamp getCreatedTime() {
        return created;
    }

    public void setCreatedTime(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdatedTime() {
        return updated;
    }

    public void setUpdatedTime(Timestamp updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "User{" + "uid=" + uid + ", username=" + username + ", email=" + email + ", password=" + password + ", fname=" + fname + ", lname=" + lname + ", des=" + des + ", profile_photo_id=" + profile_photo_id + ", gender=" + gender + ", user_status=" + user_status + ", user_activated=" + user_activated + ", created=" + created + ", updated=" + updated + '}';
    }

//    class LoginSuccess {
//        
//    }
}
