package com.example.comfile.User;

import java.util.HashMap;
import java.util.Map;

public class UserAccount {

    private String idToken;
    private String emailId;
    private String password;
    private String name;
    private String phoneNum;
    private String address;
    private String profileMSG;
    private String profileImg;

    public UserAccount() {}

    public UserAccount(String emailId, String password, String name, String phoneNum, String address) {
        this.emailId = emailId;
        this.password = password;
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("emailId", emailId);
        userData.put("password", password);
        userData.put("name", name);
        userData.put("phoneNum", phoneNum);
        userData.put("address", address);
        return userData;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileMSG() {
        return profileMSG;
    }

    public void setProfileMSG(String profileMSG) {
        this.profileMSG = profileMSG;
    }

    public String getProfileImg(){return profileImg;}

    public void setProfileImg(String profileImg){this.profileImg = profileImg;}
}
