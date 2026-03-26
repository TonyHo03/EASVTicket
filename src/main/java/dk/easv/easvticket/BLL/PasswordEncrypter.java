package dk.easv.easvticket.BLL;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncrypter {

    public static String hashedPassword(String password) {
        int logRounds = 14;
        String salt = BCrypt.gensalt(logRounds);
        return BCrypt.hashpw(password, salt);
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}