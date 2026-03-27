package dk.easv.easvticket.BLL;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordEncrypter {

    private static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    private static final int ITERATIONS   = 3;
    private static final int MEMORY       = 65536; // 64 MB
    private static final int PARALLELISM  = 1;

    public static String hashedPassword(String password) {
        return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return argon2.verify(hashedPassword, password.toCharArray());
    }
}