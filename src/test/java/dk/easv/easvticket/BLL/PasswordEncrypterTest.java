package dk.easv.easvticket.BLL;

import dk.easv.easvticket.DAL.Interfaces.IPasswordEncrypter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncrypterTest {

    private IPasswordEncrypter encrypter = new PasswordEncrypter();

    // 1. Hash is not null and follows the Argon2 encoded format
    @Test
    void hashedPassword_returnsCorrectFormat() {
        String hash = encrypter.hashedPassword("coord123");
        System.out.println("[Test 1] Generated hash: " + hash);

        assertNotNull(hash);
        assertTrue(hash.startsWith("$argon2id$"), "Hash should start with '$argon2id$'");
        assertTrue(hash.contains("m=65536"), "Hash should contain memory parameter");
        assertTrue(hash.contains("t=3"), "Hash should contain iterations parameter");
        assertTrue(hash.contains("p=1"), "Hash should contain parallelism parameter");
        System.out.println("[Test 1] PASSED\n");
    }

    // 2. Two hashes of the same password are different (random salt)
    @Test
    void hashedPassword_samePasswordProducesDifferentHashes() {
        String hash1 = encrypter.hashedPassword("myPassword123");
        String hash2 = encrypter.hashedPassword("myPassword123");
        System.out.println("[Test 2] Hash 1: " + hash1);
        System.out.println("[Test 2] Hash 2: " + hash2);
        System.out.println("[Test 2] Are they equal: " + hash1.equals(hash2));

        assertNotEquals(hash1, hash2, "Same password should produce different hashes due to random salt");
        System.out.println("[Test 2] PASSED\n");
    }

    // 3. Correct password verifies successfully
    @Test
    void verifyPassword_correctPasswordReturnsTrue() {
        String password = "securePassword!";
        String hash = encrypter.hashedPassword(password);
        boolean result = encrypter.verifyPassword(password, hash);
        System.out.println("[Test 3] Password: " + password);
        System.out.println("[Test 3] Hash: " + hash);
        System.out.println("[Test 3] Verify result: " + result);

        assertTrue(result, "Correct password should verify as true");
        System.out.println("[Test 3] PASSED\n");
    }

    // 4. Wrong password fails verification
    @Test
    void verifyPassword_wrongPasswordReturnsFalse() {
        String correct = "correctPassword";
        String wrong = "wrongPassword";
        String hash = encrypter.hashedPassword(correct);
        boolean result = encrypter.verifyPassword(wrong, hash);
        System.out.println("[Test 4] Correct password: " + correct);
        System.out.println("[Test 4] Wrong password tried: " + wrong);
        System.out.println("[Test 4] Hash: " + hash);
        System.out.println("[Test 4] Verify result (should be false): " + result);

        assertFalse(result, "Wrong password should verify as false");
        System.out.println("[Test 4] PASSED\n");
    }

    // 5. Malformed hash returns false instead of throwing
    @Test
    void verifyPassword_malformedHashReturnsFalse() {
        String malformed = "notAValidHash";
        boolean result = encrypter.verifyPassword("anyPassword", malformed);
        System.out.println("[Test 5] Malformed hash input: " + malformed);
        System.out.println("[Test 5] Verify result (should be false): " + result);

        assertFalse(result, "Malformed hash should return false, not throw an exception");
        System.out.println("[Test 5] PASSED\n");
    }
}