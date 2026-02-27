import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Classe que representa um usuário do sistema
 * Encapsula dados de usuário e gerencia a senha com hash
 */
public class User {
    private String username;
    private String passwordHash;
    private String email;
    private boolean isActive;
    private long createdAt;

    /**
     * Construtor do usuário
     * @param username Nome do usuário
     * @param email Email do usuário
     * @param password Senha em texto plano (será hasheada)
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.passwordHash = hashPassword(password);
        this.isActive = true;
        this.createdAt = System.currentTimeMillis();
    }

    /**
     * Hash SHA-256 da senha para armazenamento seguro
     * @param password Senha em texto plano
     * @return String hasheada em Base64
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao fazer hash da senha", e);
        }
    }

    /**
     * Verifica se a senha fornecida está correta
     * @param password Senha em texto plano a verificar
     * @return true se a senha está correta
     */
    public boolean verifyPassword(String password) {
        return this.passwordHash.equals(hashPassword(password));
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return isActive;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
}
