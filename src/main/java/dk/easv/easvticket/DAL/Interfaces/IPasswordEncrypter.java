package dk.easv.easvticket.DAL.Interfaces;

public interface IPasswordEncrypter {
    String hashedPassword(String password);
    boolean verifyPassword(String password, String hashedPassword);
}