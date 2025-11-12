package util;

public class PasswordUtil {
    // Không hash, trả về mật khẩu gốc
    public static String hashPassword(String plainPassword) {
        return plainPassword;
    }

    // So sánh trực tiếp chuỗi plain text
    public static boolean checkPassword(String plainPassword, String storedPassword) {
        return plainPassword.equals(storedPassword);
    }
}
