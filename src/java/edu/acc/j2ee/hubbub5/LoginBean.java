package edu.acc.j2ee.hubbub5;

public class LoginBean implements java.io.Serializable {
    private String userName;
    private String password;
    
    public LoginBean(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    
    public LoginBean() {}

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }   
}
