package edu.acc.j2ee.hubbub5;

public class LoginValidator {
    public static boolean validate(LoginBean bean) {
        if (!bean.getUserName().matches("^\\w{4,12}$")) {
            return false;
        }
        if (!bean.getPassword().matches("^[^'\"&<>]{6,15}$")) {
            return false;
        }
        return true;
    }
}
