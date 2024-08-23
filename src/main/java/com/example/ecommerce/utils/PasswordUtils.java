package com.example.ecommerce.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordUtils {

    // Biểu thức chính quy kiểm tra mật khẩu
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$";

    // Phương thức kiểm tra mật khẩu theo quy tắc
    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}

