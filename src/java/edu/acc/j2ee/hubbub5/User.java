package edu.acc.j2ee.hubbub5;

import java.util.Date;

public class User implements java.io.Serializable {   
    private String userName;
    private Date joinDate;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String zipCode;
    private int id;
    private int profileId;
    
    public User(String userName, Date joinDate, int id) {
        this.userName = userName;
        this.joinDate = joinDate;
        this.id = id;
    }
    
    public User(String userName, int id, int profileId) {
        this.userName = userName;
        this.id = id;
        this.profileId = profileId;
    }    

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }
}
