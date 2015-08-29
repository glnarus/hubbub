package edu.acc.j2ee.hubbub5;

public class RegistrationBean implements java.io.Serializable {
    private String userName;
    private String password1;
    private String password2;
    private String firstName;
    private String lastName;
    private String email;
    private String zipCode;
    
    public RegistrationBean() {}
    
    public RegistrationBean(String un, String p1, String p2, String fn,
            String ln, String e, String z) {
        userName = un;
        password1 = p1;
        password2 = p2;
        firstName = fn;
        lastName = ln;
        email = e;
        zipCode = z;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
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
}
