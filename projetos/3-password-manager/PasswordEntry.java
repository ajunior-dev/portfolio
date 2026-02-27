import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa uma entrada no gerenciador de senhas
 * Armazena: serviço, usuário, senha (hasheada), data de criação
 */
public class PasswordEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private String service;
    private String username;
    private String passwordHash;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;

    public PasswordEntry(String service, String username, String password) {
        this.service = service;
        this.username = username;
        this.passwordHash = PasswordManager.hashPassword(password);
        this.createdAt = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }

    // Getters
    public String getService() {
        return service;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setPassword(String password) {
        this.passwordHash = PasswordManager.hashPassword(password);
        this.lastModified = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("🔐 %s | 👤 %s | %s",
                service, username, createdAt.format(formatter));
    }
}
